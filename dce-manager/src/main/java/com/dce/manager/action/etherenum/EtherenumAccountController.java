package com.dce.manager.action.etherenum;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.entity.page.Pagination;
import com.dce.business.service.third.IEthereumService;
import com.dce.manager.action.BaseAction;

@Controller
@RequestMapping("/etherenum")
public class EtherenumAccountController extends BaseAction {

	@Autowired
	private IEthereumService ethereumService;
	
	@RequestMapping("/index")
	public String index(){
		return "/etherenum/ethere_index";
	}
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/list")
	public void list(Pagination<Map<String,Object>> pagination,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			PageDo<Map<String,Object>> page = PageDoUtil.getPage(pagination);
			String userName = getString(request, "userName");
			String account = getString(request, "account");
			String startDate = getString(request, "startDate");
			String endDate = getString(request, "endDate");

			
			Map<String,Object> params = new HashMap<String,Object>();
			
			if(StringUtils.isNotBlank(userName)){
				params.put("userName", userName);
			}
			if(StringUtils.isNotBlank(account)){
				params.put("account", account);
			}
			if(StringUtils.isNotBlank(startDate)){
				params.put("startDate", startDate);
			}
			if(StringUtils.isNotBlank(endDate)){
				params.put("endDate", endDate);
			}
			PageDo<Map<String,Object>> usersList = ethereumService.selectEthereumAccountByPage(page, params);
			
			pagination = PageDoUtil.getPageValue(pagination, usersList);
			outPrint(response, JSON.toJSONString(pagination));
		} catch (Exception e) {
			logger.error("显示用户数据异常", e);
			throw new BusinessException("系统繁忙，请稍后再试");
		}
	}
}
