package com.dce.business.service.impl.award;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dce.business.entity.order.Order;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.award.IAwardlistService;
import com.dce.business.service.district.IDistrictService;
import com.dce.business.service.order.IOrderService;
import com.dce.business.service.user.IUserPromoteService;
import com.dce.business.service.user.IUserService;

/**
 * 推荐用户升级
 * 
 * @author harry
 *
 */
@Service("refereeUpgrade")
public class RefereeUpgrade implements IAwardCalculator {

	private Logger logger = Logger.getLogger(getClass());

	@Resource
	private IAwardlistService awardlistService;

	@Resource
	private IUserService userService;

	// 账户
	@Resource
	private IAccountService accountService;

	@Resource
	private IUserPromoteService userPromoteService;

	@Resource
	private IDistrictService districtService;

	@Resource
	private IOrderService orderService;
	
	/**
	 * 推荐用户升级
	 * 
	 * @param buyUserId
	 *            购买者
	 * @param orderId
	 *            购买订单
	 * @return
	 */
	@Override
	public void doAward(UserDo buyer, Order order) {
		
		UserDo refUser = userService.getUser(buyer.getRefereeid());
		if(refUser == null){
			return;
		}
		
		//商家和代理商不升级
		if(refUser.getUserType().intValue() == 1 || refUser.getUserType().intValue() == 2) {
			return ;
		}
		
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("refereeid", buyer.getRefereeid());
		//购买用户的推荐人，推荐的用户清单
		List<UserDo> refLst = userService.selectUserCondition(params);
		if(refLst == null || refLst.size()<1) {
			logger.warn("没有满足升级条件 订单用户buyer:"+buyer.getId()+" ===推荐用户:"+buyer.getRefereeid());
			return;
		}
		
		//vip个数
		int cntVip = 0;
		//商家个数
		int cntLevel2 = 0; 
		//社区合伙人个数
		int cntLevel3 = 0; 
		int cntLevel4 = 0;
		int cntLevel5 = 0;
		
		for(UserDo u : refLst) {
			byte level = u.getUserLevel();
			if(level>=1) {
				cntVip++;
			}
			if(level==2) {
				cntLevel2++;
			}
			if(level>=3) {
				cntLevel3++;
			}
			if(level>=4) {
				cntLevel4++;
			}
			if(level>=5) {
				cntLevel5++;
			}
		}
		
		byte upgradeLevel = 0;
		if(cntVip>5 || cntLevel2>=1) {
			upgradeLevel = 3;
		}
		if(cntLevel3>5 || cntLevel2>=3) {
			upgradeLevel = 4;
		}
		if(cntLevel4>5 || cntLevel2>=5) {
			upgradeLevel = 5;
		}
		if(cntLevel5>5 || cntLevel2>=7) {
			upgradeLevel = 6;
		}
		
		UserDo user = new UserDo();
		user.setId(buyer.getRefereeid());
		user.setUserLevel(upgradeLevel);
		userService.updateUserByBuy(user);
	}

	


}
