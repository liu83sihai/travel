package com.dce.manager.action.etherenum;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.entity.page.Pagination;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.third.IEthereumService;
import com.dce.business.service.user.IUserService;
import com.dce.manager.action.BaseAction;

@Controller
@RequestMapping("/ethopenAcct")
public class EtherenumOpenAccountController extends BaseAction {

	@Resource
    private IUserService userService;
	@Resource
    private IEthereumService ethereumService;
	
	@RequestMapping("/index")
	public String index(){
		return "/etherenum/open_index";
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/list")
	public void list(Pagination<UserDo> pagination,
			HttpServletRequest request, HttpServletResponse response) {
		
        String keyword = getString("keyword");

        Map<String, Object> params = new HashMap<String, Object>();
        
        if (StringUtils.isNotBlank(keyword)) {
            params.put("keyword", keyword);
        }
		try {
			PageDo<UserDo> page = PageDoUtil.getPage(pagination);
			
			PageDo<UserDo> result = userService.selectEthAccountByPage(page, params);
			
			pagination = PageDoUtil.getPageValue(pagination, result);
			outPrint(response, JSON.toJSONString(pagination));
			
		}catch (Exception e) {
			logger.error("显示用户数据异常", e);
			throw new BusinessException("系统繁忙，请稍后再试");
		}
	}
	
	
	@RequestMapping(value = "/create", method = { RequestMethod.GET, RequestMethod.POST })
    public void createAccount(HttpServletResponse response) {
		try{
	        Integer userId = Integer.valueOf(getString("userid"));
	        String password = getRandomString(8); //生成随机密码
	        Result<?> result = ethereumService.creatAccount(userId, password);
	        if(result.isSuccess()){
	        	
	        	outPrint(response, JSON.toJSONString(result));
	        }else{
	        	
	        	outPrint(response, JSON.toJSONString(Result.failureResult("开户失败")));
	        }
		}catch(Exception e){
			logger.error("开户报错",e);
			outPrint(response, JSON.toJSONString(Result.failureResult("开户失败")));
		}
    }
	
	private static String getRandomString(int length) {
        //随机字符串的随机字符库
        String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();
    }
}
