
package com.dce.manager.action.supplier;

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

import com.dce.business.service.supplier.ISupplierService;
import com.dce.business.entity.supplier.SupplierDo;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/supplier")
public class SupplierController extends BaseAction{
	//默认多列排序,example: username desc,createTime asc
	//protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Resource
	private ISupplierService supplierService;

	/**
     * 去列表页面
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        return "supplier/listSupplier";
    }
	
	@RequestMapping("/listSupplier")
    public void listSupplier(NewPagination<SupplierDo> pagination,
    							  ModelMap model,
    							  HttpServletResponse response) {

        logger.info("----listSupplier----");
        try{
            PageDo<SupplierDo> page = PageDoUtil.getPage(pagination);
            String supplierName = getString("supplierName");
            Map<String,Object> param = new HashMap<String,Object>();
            if(StringUtils.isNotBlank(supplierName)){
                param.put("supplierName",supplierName);
                model.addAttribute("supplierName",supplierName);
            }
            String linkPhone = getString("linkPhone");
            if(StringUtils.isNotBlank(linkPhone)){
                param.put("linkPhone", linkPhone);
                model.addAttribute("linkPhone",linkPhone);
            }
            page = supplierService.getSupplierPage(param, page);
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
    @RequestMapping("/addSupplier")
    public String addSupplier(String id, ModelMap modelMap, HttpServletResponse response) {
        logger.info("----addSupplier----");
        try{
            if(StringUtils.isNotBlank(id)){
                SupplierDo supplierDo = supplierService.getById(Integer.valueOf(id));
                if(null != supplierDo){
                    modelMap.addAttribute("supplier", supplierDo);
                }
            }
            return "supplier/addSupplier";
        }catch(Exception e){
            logger.error("跳转到数据字典编辑页面异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }

    }

    /**
     * 保存更新
     *
     * @return
     * @date: 2015年4月21日 12:49:05
     */
    @RequestMapping("/saveSupplier")
    @ResponseBody
    public void saveSupplier(SupplierDo supplierDo, 
    							  HttpServletRequest request, 
    							  HttpServletResponse response) {
        logger.info("----saveSupplier------");
        try{
            Integer id = supplierDo.getId();
            Long userId = new Long(this.getUserId());
            
            int i = 0;
            if (id != null && id.intValue()>0) {
            	// supplierDo.setUpdateBy(userId);
            	// supplierDo.setUpdateTime(new Date());
                i = supplierService.updateSupplierById(supplierDo);
            } else {
//				supplierDo.setCreateBy(userId);
//            	supplierDo.setCreateTime(new Date());
                i = supplierService.addSupplier(supplierDo);
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
        logger.info("----end saveSupplier--------");
    }
    
    /**
     * 删除
     */
    @RequestMapping("/deletesupplier")
    public void deletesupplier(String id,HttpServletRequest request,
            HttpServletResponse response) {
        logger.info("----deletesupplier----");
         try{
             if (StringUtils.isBlank(id) || !id.matches("\\d+")) {
            	 logger.info(id);
                 ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
                 return;
             }
             int ret = supplierService.deleteById(Integer.valueOf(id));
             ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
         }catch(Exception e){
             logger.error("删除异常",e);
             ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
         }
     }
    
}

