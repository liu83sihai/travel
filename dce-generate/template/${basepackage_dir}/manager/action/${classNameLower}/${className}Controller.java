<#include "/custom.include">
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>   
<#assign classNameLowerCase = className?lower_case>   
<#assign pkJavaType = table.idColumn.javaType>   

package ${basepackage}.manager.action.${classNameLower};

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

import ${basepackage}.business.service.${classNameLower}.I${className}Service;
import ${basepackage}.business.entity.${classNameLower}.${className}Do;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/${classNameLowerCase}")
public class ${className}Controller extends BaseAction{
	@Resource
	private I${className}Service ${classNameFirstLower}Service;

	/**
     * 去列表页面
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        return "${classNameLowerCase}/list${className}";
    }
	
	@RequestMapping("/list${className}")
    public void list${className}(NewPagination<${className}Do> pagination,
    							  ModelMap model,
    							  HttpServletResponse response) {

        logger.info("----list${className}----");
        try{
            PageDo<${className}Do> page = PageDoUtil.getPage(pagination);
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
            page = ${classNameLowerCase}Service.get${className}Page(param, page);
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
    @RequestMapping("/add${className}")
    public String add${className}(String id, ModelMap modelMap, HttpServletResponse response) {
        logger.info("----add${className}----");
        try{
            if(StringUtils.isNotBlank(id)){
                ${className}Do ${classNameLowerCase}Do = ${classNameFirstLower}Service.getById(Integer.valueOf(id));
                if(null != ${classNameLowerCase}Do){
                    modelMap.addAttribute("${classNameLowerCase}", ${classNameLowerCase}Do);
                }
            }
            return "${classNameLowerCase}/add${className}";
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
    @RequestMapping("/save${className}")
    @ResponseBody
    public void save${className}(${className}Do ${classNameLowerCase}Do, 
    							  HttpServletRequest request, 
    							  HttpServletResponse response) {
        logger.info("----save${className}------");
        try{
            Integer id = ${classNameLowerCase}Do.getId();
            Long userId = new Long(this.getUserId());
            
            int i = 0;
            if (id != null && id.intValue()>0) {
            	 //${classNameLowerCase}Do.setModifyName(this.getUserName() + ":" + userId);
            	//${classNameLowerCase}Do.setModifyDate(new Date(System.currentTimeMillis()));;
                i = ${classNameFirstLower}Service.update${className}ById(${classNameLowerCase}Do);
            } else {
//				${classNameLowerCase}Do.setCreateName(this.getUserName() + ":" + userId);
//				${classNameLowerCase}Do.setCreateDate(new Date(System.currentTimeMillis()));
//				${classNameLowerCase}Do.setStatus(1);
                i = ${classNameFirstLower}Service.add${className}(${classNameLowerCase}Do);
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
        logger.info("----end save${className}--------");
    }
    
    /**
     * 删除
     */
    @RequestMapping("/delete${className}")
    public void delete${className}(String id,HttpServletRequest request,
            HttpServletResponse response) {
        logger.info("----delete${classNameLowerCase}----");
         try{
             if (StringUtils.isBlank(id) || !id.matches("\\d+")) {
            	 logger.info(id);
                 ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
                 return;
             }
             int ret = ${classNameLowerCase}Service.deleteById(Integer.valueOf(id));
             ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
         }catch(Exception e){
             logger.error("删除异常",e);
             ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
         }
     }
    
}

