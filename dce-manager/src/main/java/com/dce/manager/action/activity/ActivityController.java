
package com.dce.manager.action.activity;

import java.util.Map;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import javax.annotation.Resource;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.ui.Model;
import com.dce.business.entity.page.NewPagination;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.common.result.Result;

import com.dce.business.common.exception.BusinessException;
import com.dce.manager.action.BaseAction;
import com.dce.manager.util.ResponseUtils;

import com.dce.business.service.activity.IActivityService;
import com.dce.business.entity.activity.ActivityDo;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseAction{
	@Resource
	private IActivityService activityService;

	/**
     * 去列表页面
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        return "activity/listActivity";
    }
	
	@RequestMapping("/listActivity")
    public void listActivity(NewPagination<ActivityDo> pagination,
    							  ModelMap model,
    							  HttpServletResponse response) {

        logger.info("----listActivity----");
        try{
            PageDo<ActivityDo> page = PageDoUtil.getPage(pagination);
            String companyName = getString("searchPolicyName");
            Map<String,Object> param = new HashMap<String,Object>();
            if(StringUtils.isNotBlank(companyName)){
                param.put("policyName",companyName);
                model.addAttribute("searchPolicyName",companyName);
            }
            String managerName = getString("searManagerName");
            if(StringUtils.isNotBlank(managerName)){
                param.put("managerName", managerName);
                model.addAttribute("searManagerName",managerName);
            }
            page = activityService.getActivityPage(param, page);
            pagination = PageDoUtil.getPageValue(pagination, page);
            outPrint(response, JSONObject.toJSON(pagination));
        }catch(Exception e){
            logger.error("查询清单异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }
    }
	
	
	  
    /**
     * 编辑页面
     *
     * @return
     */
    @RequestMapping("/addActivity")
    public String addActivity(String id, ModelMap modelMap, HttpServletResponse response) {
        logger.info("----addActivity----");
        try{
            if(StringUtils.isNotBlank(id)){
                ActivityDo activityDo = activityService.getById(Integer.valueOf(id));
                if(null != activityDo){
                    modelMap.addAttribute("activity", activityDo);
                }
            }
            return "activity/addActivity";
        }catch(Exception e){
            logger.error("跳转到数据字典编辑页面异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }

    }

    /**
     * 保存更新
     *
     * @return
     * @date: 2018年10月21日 12:49:05
     */
    @RequestMapping("/saveActivity")
    @ResponseBody
    public void saveActivity(ActivityDo activityDo, 
    							  HttpServletRequest request, 
    							  HttpServletResponse response) {
        logger.info("----saveActivity------");
        try{
            Integer id = activityDo.getId();
            Long userId = new Long(this.getUserId());
            
            //关联前台ct_user用户
            activityDo.setUserId(1);
            
            int i = 0;
            if (id != null && id.intValue()>0) {
                i = activityService.updateActivityById(activityDo);
            } else {
                i = activityService.addActivity(activityDo);
            }

            if (i <= 0) {
                outPrint(response,this.toJSONString(Result.failureResult("操作失败")));
                return;
            }
            outPrint(response, this.toJSONString(Result.successResult("操作成功")));
        }catch(Exception e){
            logger.error("保存更新失败",e);
            outPrint(response, this.toJSONString(Result.failureResult("操作失败")));
        }
        logger.info("----end saveActivity--------");
    }
    
    /**
     * 删除
     */
    @RequestMapping("/deleteActivity")
    public void deleteActivity(String id,HttpServletRequest request,
            HttpServletResponse response) {
        logger.info("----deleteactivity----");
         try{
             if (StringUtils.isBlank(id) || !id.matches("\\d+")) {
            	 logger.info(id);
                 ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
                 return;
             }
             int ret = activityService.deleteById(Integer.valueOf(id));
             ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
         }catch(Exception e){
             logger.error("删除异常",e);
             ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
         }
     }
    
}

