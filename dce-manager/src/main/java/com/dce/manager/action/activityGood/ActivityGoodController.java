
package com.dce.manager.action.activityGood;

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

import com.dce.business.service.activityGood.IActivityGoodService;
import com.dce.business.entity.activityGood.ActivityGoodDo;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/activitygood")
public class ActivityGoodController extends BaseAction{
	@Resource
	private IActivityGoodService activityGoodService;

	/**
     * 去列表页面
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        return "activitygood/listActivityGood";
    }
	
	@RequestMapping("/listActivityGood")
    public void listActivityGood(NewPagination<ActivityGoodDo> pagination,
    							  ModelMap model,
    							  HttpServletResponse response) {

        logger.info("----listActivityGood----");
        try{
            PageDo<ActivityGoodDo> page = PageDoUtil.getPage(pagination);
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
            page = activityGoodService.getActivityGoodPage(param, page);
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
    @RequestMapping("/addActivityGood")
    public String addActivityGood(String id, ModelMap modelMap, HttpServletResponse response) {
        logger.info("----addActivityGood----");
        try{
            if(StringUtils.isNotBlank(id)){
                ActivityGoodDo activitygoodDo = activityGoodService.getById(Integer.valueOf(id));
                if(null != activitygoodDo){
                    modelMap.addAttribute("activitygood", activitygoodDo);
                }
            }
            return "activitygood/addActivityGood";
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
    @RequestMapping("/saveActivityGood")
    @ResponseBody
    public void saveActivityGood(ActivityGoodDo activitygoodDo, 
    							  HttpServletRequest request, 
    							  HttpServletResponse response) {
        logger.info("----saveActivityGood------");
        try{
            Integer id = activitygoodDo.getId();
            Long userId = new Long(this.getUserId());
            
            int i = 0;
            if (id != null && id.intValue()>0) {
            	 //activitygoodDo.setModifyName(this.getUserName() + ":" + userId);
            	//activitygoodDo.setModifyDate(new Date(System.currentTimeMillis()));;
                i = activityGoodService.updateActivityGoodById(activitygoodDo);
            } else {
//				activitygoodDo.setCreateName(this.getUserName() + ":" + userId);
//				activitygoodDo.setCreateDate(new Date(System.currentTimeMillis()));
//				activitygoodDo.setStatus(1);
                i = activityGoodService.addActivityGood(activitygoodDo);
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
        logger.info("----end saveActivityGood--------");
    }
    
    /**
     * 删除
     */
    @RequestMapping("/deleteActivityGood")
    public void deleteActivityGood(String id,HttpServletRequest request,
            HttpServletResponse response) {
        logger.info("----deleteactivitygood----");
         try{
             if (StringUtils.isBlank(id) || !id.matches("\\d+")) {
            	 logger.info(id);
                 ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
                 return;
             }
//             int ret = activityGoodService.deleteById(Integer.valueOf(id));
             ResponseUtils.renderJson(response, null, "{\"ret\":" + 1 + "}");
         }catch(Exception e){
             logger.error("删除异常",e);
             ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
         }
     }
    
}

