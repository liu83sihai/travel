
package com.dce.manager.action.dict;

import java.util.Map;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

import com.dce.business.entity.dict.LoanDictDo;
import com.dce.business.entity.dict.LoanDictDtlDo;
import com.dce.business.entity.page.NewPagination;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.service.dict.ILoanDictService;
import com.dce.business.common.result.Result;

import com.dce.business.common.exception.BusinessException;
import com.dce.manager.action.BaseAction;
import com.dce.manager.util.ResponseUtils;

import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/loandict")
public class LoanDictController extends BaseAction{
	@Resource
	private ILoanDictService loandictService;

	/**
     * 去列表页面
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        return "dict/listLoanDict";
    }
	
	@RequestMapping("/listLoanDict")
    public void listLoanDict(NewPagination<LoanDictDo> pagination,
    							  ModelMap model,
    							  HttpServletResponse response) {

        logger.info("----listLoanDict----");
        try{
            PageDo<LoanDictDo> page = PageDoUtil.getPage(pagination);
            String name = getString("name");
            Map<String,Object> param = new HashMap<String,Object>();
            if(StringUtils.isNotBlank(name)){
                param.put("name",name);
                model.addAttribute("name",name);
            }
            
            page = loandictService.queryListPage(param,page);
            
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
    @RequestMapping("/addLoanDict")
    public String addLoanDict(String id, ModelMap modelMap, HttpServletResponse response) {
        logger.info("----addLoanDict----");
        try{
            if(StringUtils.isNotBlank(id)){
                LoanDictDo loandictDo = loandictService.getDictById(Integer.valueOf(id));
                if(null != loandictDo){
                	
                	 List<LoanDictDtlDo> ldDtl =loandictService.getDictDetailByDictId(Long.valueOf(id));
                	 loandictDo.setDtlList(ldDtl);
                    modelMap.addAttribute("loandict", loandictDo);
                }
            }
            return "dict/addLoanDict";
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
    @RequestMapping("/saveLoanDict")
    @ResponseBody
    public void saveLoanDict(LoanDictDo loandictDo, 
    							  HttpServletRequest request, 
    							  HttpServletResponse response) {
    	logger.info("----saveLoanDict------");
    	String travelCard = getString("travelCardValue");
    	String travelLine = getString("travelLineValue");
    	String pointGood = getString("pointGoodValue");
    	String vipGood = getString("vipGoodValue");
    	String supplierSale = getString("supplierSaleValue");
        try{
        	Long id = loandictDo.getId();
            Long userId = new Long(this.getUserId());
            
            int i = 0;
            if (id != null && id.intValue()>0) {
            	 loandictDo.setUpdateUserId(userId);
            	loandictDo.setUpdateTime(new Date(System.currentTimeMillis()));;
                i = loandictService.updateDict(loandictDo);
            } else {
				loandictDo.setCreateUserId(userId);
				loandictDo.setUpdateUserId(userId);
	            loandictDo.setUpdateTime(new Date(System.currentTimeMillis()));
				loandictDo.setCreateTime(new Date(System.currentTimeMillis()));
				loandictDo.setStatus("T");
                i = loandictService.addDict(loandictDo);
                
                LoanDictDo newLoandict = loandictService.getLoanDict(loandictDo.getCode());
                id = newLoandict.getId();
            }

            if (i <= 0) {
                outPrint(response,this.toJSONString(Result.failureResult("操作失败")));
                return;
            }
            //写入明细表 先删后加
           if(id >0){
        	   loandictService.deleteByDictId(id);
        	   LoanDictDtlDo ldDo = new LoanDictDtlDo();
        	   ldDo.setCode(travelCard);
        	   ldDo.setName("travelCard");
        	   ldDo.setDictId(id);
        	   ldDo.setStatus("T");
        	   ldDo.setRemark("商品品类付款方式");
        	   loandictService.addDictDtl(ldDo);
        	   
        	   ldDo.setCode(travelLine);
        	   ldDo.setName("travelLine");
        	   loandictService.addDictDtl(ldDo);
        	   
        	   ldDo.setCode(pointGood);
        	   ldDo.setName("pointGood");
        	   loandictService.addDictDtl(ldDo);
        	   
        	   ldDo.setCode(vipGood);
        	   ldDo.setName("vipGood");
        	   loandictService.addDictDtl(ldDo);
        	   
        	   ldDo.setCode(supplierSale);
        	   ldDo.setName("supplierSale");
        	   loandictService.addDictDtl(ldDo);
           }
            
            
            outPrint(response, this.toJSONString(Result.successResult("操作成功")));
        }catch(Exception e){
            logger.error("保存更新失败",e);
            outPrint(response, this.toJSONString(Result.failureResult("操作失败")));
        }
        logger.info("----end saveLoanDict--------");
    }
    
    /**
     * 删除
     */
    @RequestMapping("/deleteLoanDict")
    public void deleteLoanDict(String id,HttpServletRequest request,
            HttpServletResponse response) {
        logger.info("----deleteloandict----");
         try{
             if (StringUtils.isBlank(id) || !id.matches("\\d+")) {
            	 logger.info(id);
                 ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
                 return;
             }
//             int ret = loandictService.deleteById(Integer.valueOf(id));
             ResponseUtils.renderJson(response, null, "{\"ret\":" + 0 + "}");
         }catch(Exception e){
             logger.error("删除异常",e);
             ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
         }
     }
    
}

