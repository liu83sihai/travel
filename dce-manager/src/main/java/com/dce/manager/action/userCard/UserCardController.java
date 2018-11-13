
package com.dce.manager.action.userCard;

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

import com.dce.business.service.userCard.IUserCardService;
import com.dce.business.entity.userCard.UserCardDo;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/userCard")
public class UserCardController extends BaseAction{
	@Resource
	private IUserCardService userCardService;

	/**
     * 去列表页面
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        return "userCard/listUserCard";
    }
	
	@RequestMapping("/listUserCard")
    public void listUserCard(NewPagination<UserCardDo> pagination,
    							  ModelMap model,
    							  HttpServletResponse response) {

        logger.info("----listUserCard----");
        try{
            PageDo<UserCardDo> page = PageDoUtil.getPage(pagination);
            String userName = getString("userName");
            Map<String,Object> param = new HashMap<String,Object>();
            if(StringUtils.isNotBlank(userName)){
                param.put("userName",userName);
                model.addAttribute("userName",userName);
            }
            String mobile = getString("mobile");
            if(StringUtils.isNotBlank(mobile)){
                param.put("mobile", mobile);
                model.addAttribute("mobile",mobile);
            }
            page = userCardService.getUserCardPage(param, page);
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
    @RequestMapping("/addUserCard")
    public String addUserCard(String id, ModelMap modelMap, HttpServletResponse response) {
        logger.info("----addUserCard----");
        try{
            if(StringUtils.isNotBlank(id)){
                UserCardDo usercardDo = userCardService.getById(Integer.valueOf(id));
                if(null != usercardDo){
                    modelMap.addAttribute("usercard", usercardDo);
                }
            }
            return "userCard/addUserCard";
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
    @RequestMapping("/saveUserCard")
    @ResponseBody
    public void saveUserCard(UserCardDo usercardDo, 
    							  HttpServletRequest request, 
    							  HttpServletResponse response) {
        logger.info("----saveUserCard------");
        try{
            Integer id = usercardDo.getId();
            Long userId = new Long(this.getUserId());
            
            int i = 0;
            if (id != null && id.intValue()>0) {
            	 //usercardDo.setModifyName(this.getUserName() + ":" + userId);
            	//usercardDo.setModifyDate(new Date(System.currentTimeMillis()));;
                i = userCardService.updateUserCardById(usercardDo);
            } else {
//				usercardDo.setCreateName(this.getUserName() + ":" + userId);
//				usercardDo.setCreateDate(new Date(System.currentTimeMillis()));
//				usercardDo.setStatus(1);
                i = userCardService.addUserCard(usercardDo);
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
        logger.info("----end saveUserCard--------");
    }
    
    /**
     * 删除
     */
    @RequestMapping("/deleteUserCard")
    public void deleteUserCard(String id,HttpServletRequest request,
            HttpServletResponse response) {
        logger.info("----deleteusercard----");
         try{
             if (StringUtils.isBlank(id) || !id.matches("\\d+")) {
            	 logger.info(id);
                 ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
                 return;
             }
             
             UserCardDo uc = userCardService.getById(Integer.valueOf(id));
             int ret = userCardService.activeUserCard(uc);
             ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
         }catch(Exception e){
             logger.error("删除异常",e);
             ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
         }
     }
    
}

