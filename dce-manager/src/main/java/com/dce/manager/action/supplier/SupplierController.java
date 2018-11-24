
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
import com.dce.business.service.user.IUserService;
import com.dce.business.entity.supplier.SupplierDo;
import com.dce.business.entity.user.UserDo;

import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/supplier")
public class SupplierController extends BaseAction{
	@Resource
	private ISupplierService supplierService;
	
	@Resource
	private IUserService userService;


	/**
     * 去列表页面
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        return "supplier/listSupplier";
    }
    /**
     * 去列表页面
     * @param model
     * @return
     */
    @RequestMapping("/protocolLink")
    public String protocolLink(Model model){
    	return "supplier/protocol";
    }
	
	@RequestMapping("/listSupplier")
    public void listSupplier(NewPagination<SupplierDo> pagination,
    							  ModelMap model,
    							  HttpServletResponse response) {

        logger.info("----listSupplier----");
        try{
            PageDo<SupplierDo> page = PageDoUtil.getPage(pagination);
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
                    String busiImage = supplierDo.getBusiImage();
                    if(StringUtils.isNotBlank(busiImage)){
	                    String[] busiImages = busiImage.split(",");
	                    modelMap.addAttribute("busiImages", busiImages);
                    }
                    
                    String shopImage = supplierDo.getShopImage();
                    if(StringUtils.isNotBlank(shopImage)){
	                    String[] shopImages = shopImage.split(",");
	                    modelMap.addAttribute("shopImages", shopImages);
                    }
                   
                    String bannerImage = supplierDo.getBannerImages();
                    if(StringUtils.isNoneBlank(bannerImage)){
	                    String[] bannerImages = bannerImage.split(",");
	                    modelMap.addAttribute("bannerImages", bannerImages);
                    }
//                    String 
                    
                    
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
     * @date: 2018年10月21日 12:49:05
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
            	 //supplierDo.setModifyName(this.getUserName() + ":" + userId);
            	//supplierDo.setModifyDate(new Date(System.currentTimeMillis()));;
//                i = supplierService.updateSupplierById(supplierDo);
                i = 1;
            } else {
//				supplierDo.setCreateName(this.getUserName() + ":" + userId);
//				supplierDo.setCreateDate(new Date(System.currentTimeMillis()));
//				supplierDo.setStatus(1);
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
    @RequestMapping("/deleteSupplier")
    public void deleteSupplier(String id,HttpServletRequest request,
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
    /**
     * 审核
     */
    @RequestMapping("/aduitSupplier")
    public void aduitSupplier(String id,String userId,String district,HttpServletRequest request,
    		HttpServletResponse response) {
    	logger.info("----aduitSupplier----");
    	try{
    		if (StringUtils.isBlank(id) || !id.matches("\\d+")) {
    			logger.info(id);
    			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
    			return;
    		}
    		//更新供应商
			SupplierDo supplierDo = new SupplierDo();
			supplierDo.setId(Integer.valueOf(id));
			supplierDo.setStatus(2);
    		int ret = supplierService.updateSupplierById(supplierDo);
    		//更新用户
    		UserDo userDo = new UserDo();
    		userDo.setId(Integer.valueOf(userId));
    		userDo.setUserType(1);
    		userDo.setUserLevel((byte)2);
    		userDo.setDistrict(district);
    		userService.update(userDo);
    		
    		ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
    	}catch(Exception e){
    		logger.error("审核异常",e);
    		ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
    	}
    }
    
}

