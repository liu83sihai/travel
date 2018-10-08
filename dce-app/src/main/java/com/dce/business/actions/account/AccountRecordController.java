package com.dce.business.actions.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.dce.business.actions.common.BaseController;
import com.dce.business.common.util.DateUtil;
import com.dce.business.entity.account.UserAccountDetailDo;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.service.account.IAccountService;

/**
 * 交易流水控制类
 * @author Administrator
 *
 */
@RestController
@RequestMapping("accountRecord")
public class AccountRecordController extends BaseController {

	private final static Logger logger = Logger.getLogger(AccountController.class);
	
	@Resource
	private IAccountService accountService;
	
	/**
	 * 获取用户交易流水记录
	 * @return
	 */
	@RequestMapping(value = "/selectAccountRecord", method=RequestMethod.GET)
	public Map<String,Object> selectAccountRecord(){
		
		Integer userId = getUserId();
		String accountType = getString("accountType"); 
		logger.info("获取当前用户的id："+userId);
		
		
		//当前账户总额度
		Map<String,Object> map = new HashMap<String,Object>();
		UserAccountDo currentUserAccountDo = accountService.selectUserAccount(userId, accountType==null?"wallet_money":accountType);
		map.put("balance", currentUserAccountDo.getAmount()); //当前用户的账户余额
		
		//查询账户流水
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("userId", userId);
		paraMap.put("accountType", accountType);
		List<UserAccountDetailDo> list = accountService.selectUserAccountDetailByUserId(paraMap);
		
		if(list.isEmpty() || list.size() ==0){
			map.put("code", "0");
			map.put("msg", "用户交易流水记录为空");
			map.put("data", new JSONArray());
			return map;
		}
		
		List<Map<String,Object>> accountlist = new ArrayList<>();
		for(UserAccountDetailDo userAccountDetail : list){
			Map<String,Object> map2 = new HashMap<>();
			map2.put("id", userAccountDetail.getId());
			map2.put("userId", userAccountDetail.getUserId());
			map2.put("amount", userAccountDetail.getAmount());
			map2.put("moreOrLess", userAccountDetail.getMoreOrLess());
			map2.put("balanceAmount", userAccountDetail.getBalanceAmount());
			map2.put("createTime", DateUtil.YYYY_MM_DD_MM_HH_SS.format(userAccountDetail.getCreateTime()));
			map2.put("remark", userAccountDetail.getRemark());
			map2.put("seqId", userAccountDetail.getSeqId());
			map2.put("accountType", userAccountDetail.getAccountType());
			accountlist.add(map2);
		}
		map.put("data", accountlist);
		
		return map;
	}
	
}
