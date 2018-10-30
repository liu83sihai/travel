
package com.dce.manager.action.agency;

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

import com.dce.business.service.agency.IAgencyService;
import com.dce.business.entity.agency.AgencyDo;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/agency")
public class AgencyController extends BaseAction{
	@Resource
	private IAgencyService agencyService;

	/**
     * 去列表页面
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        return "agency/listAgency";
    }
	
	@RequestMapping("/listAgency")
    public void listAgency(NewPagination<AgencyDo> pagination,
    							  ModelMap model,
    							  HttpServletResponse response) {

        logger.info("----listAgency----");
        try{
            PageDo<AgencyDo> page = PageDoUtil.getPage(pagination);
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
            page = agencyService.getAgencyPage(param, page);
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
    @RequestMapping("/addAgency")
    public String addAgency(String id, ModelMap modelMap, HttpServletResponse response) {
        logger.info("----addAgency----");
        try{
            if(StringUtils.isNotBlank(id)){
                AgencyDo agencyDo = agencyService.getById(Integer.valueOf(id));
                if(null != agencyDo){
                    modelMap.addAttribute("agency", agencyDo);
                }
            }
            return "agency/addAgency";
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
    @RequestMapping("/saveAgency")
    @ResponseBody
    public void saveAgency(AgencyDo agencyDo, 
    							  HttpServletRequest request, 
    							  HttpServletResponse response) {
        logger.info("----saveAgency------");
        try{
            Integer id = agencyDo.getId();
            Long userId = new Long(this.getUserId());
            
            int i = 0;
            if (id != null && id.intValue()>0) {
            	 //agencyDo.setModifyName(this.getUserName() + ":" + userId);
            	//agencyDo.setModifyDate(new Date(System.currentTimeMillis()));;
                i = agencyService.updateAgencyById(agencyDo);
            } else {
//				agencyDo.setCreateName(this.getUserName() + ":" + userId);
//				agencyDo.setCreateDate(new Date(System.currentTimeMillis()));
//				agencyDo.setStatus(1);
                i = agencyService.addAgency(agencyDo);
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
        logger.info("----end saveAgency--------");
    }
    
    /**
     * 删除
     */
    @RequestMapping("/deleteAgency")
    public void deleteAgency(String id,HttpServletRequest request,
            HttpServletResponse response) {
        logger.info("----deleteagency----");
         try{
             if (StringUtils.isBlank(id) || !id.matches("\\d+")) {
            	 logger.info(id);
                 ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
                 return;
             }
             int ret = agencyService.deleteById(Integer.valueOf(id));
             ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
         }catch(Exception e){
             logger.error("删除异常",e);
             ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
         }
     }
    
}

