package com.dce.manager.action.user;

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
import com.dce.business.entity.account.UserAccountDetailDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.entity.page.Pagination;
import com.dce.business.service.account.IAccountService;
import com.dce.manager.action.BaseAction;


@Controller
@RequestMapping("/userAccountDetail")
public class UserAccountDetailController extends BaseAction {

	@Autowired
	private IAccountService accountService;
	
	@RequestMapping("/index")
	public String index(){
		return "/userAccount/detail_index";
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/list")
	public void list(Pagination<UserAccountDetailDo> pagination,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			PageDo<UserAccountDetailDo> page = PageDoUtil.getPage(pagination);
			String userName = getString(request, "userName");
			String startDate = getString(request, "startDate");
			String endDate = getString(request, "endDate");
			String seacrchAccountType = getString(request, "seacrchAccountType");
			String remark = getString(request, "remark");
			
			Map<String,Object> params = new HashMap<String,Object>();
			
			if(StringUtils.isNotBlank(userName)){
				params.put("userName", userName);
			}
			if(StringUtils.isNotBlank(startDate)){
				params.put("startDate", startDate);
			}
			if(StringUtils.isNotBlank(endDate)){
				params.put("endDate", endDate);
			}
			if(StringUtils.isNotBlank(remark)){
				params.put("remark", remark);
			}
			if(StringUtils.isNotBlank(seacrchAccountType)){
				params.put("accountType", seacrchAccountType);
			}
			
			PageDo<UserAccountDetailDo> accoutList = accountService.selectUserAccountDetailByPage(page, params);
			
			pagination = PageDoUtil.getPageValue(pagination, accoutList);
			
			outPrint(response, JSON.toJSONString(pagination));
		} catch (Exception e) {
			logger.error("显示用户账户详情数据异常", e);
			throw new BusinessException("系统繁忙，请稍后再试");
		}
	}
}
