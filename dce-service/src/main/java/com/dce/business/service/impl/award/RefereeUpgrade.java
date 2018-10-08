package com.dce.business.service.impl.award;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.dce.business.common.enums.AccountType;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.entity.district.District;
import com.dce.business.entity.order.Order;
import com.dce.business.entity.user.UserDo;
import com.dce.business.entity.user.UserPromoteDo;
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
	 * 根据购买者购买数量确定用户会员等级和给会员的奖励 计算奖励的方法
	 * 
	 * @param buyUserId
	 *            购买者
	 * @param orderId
	 *            购买订单
	 * @return
	 */
	@Override
	public void doAward(UserDo buyer, Order order) {
	
		if (buyer.getUserLevel() == 4) {
			
			logger.error("此用戶為以是最高等级");
			return;
		}

		// 得到奖励记录
		UserPromoteDo promote = userPromoteService.selectUserLevelAntBuyQty((Integer.valueOf(buyer.getUserLevel())),
				order.getQty());

		if (promote == null) {
			
			logger.error("用户购买数量无法升级");
			return;
		}

		String buyerAward = promote.getPromoteLevel().toString();
		if (StringUtils.isBlank(buyerAward)) {
			throw new BusinessException("购买者对应的奖励办法没有正确配置，请检查奖励办法的配置", "error-buyerAward-002");
		}

		// 多种奖励办法以;分隔
		String[] bAwardLst = buyerAward.split(";");
		onepromote(buyer.getUserLevel(), bAwardLst, buyer);
	}

	/**
	 * 升级处理
	 * 
	 * @param buyUserId
	 * @param bAwardLst
	 * @param buyer
	 */
	private void onepromote(byte buyUserId, String[] bAwardLst, UserDo buyer) {
		for (String onepromote : bAwardLst) {
			// 用户要升级的等级
			String promoteLevel = onepromote;

			if (buyUserId < Byte.valueOf(promoteLevel)) {
				UserDo userDo = new UserDo();
				userDo.setId(buyer.getId());
				userDo.setUserLevel(Byte.valueOf(promoteLevel));
				if (userService.updateLevel(userDo)) {
					shareholder(buyer);
					logger.error("用户升级成功");
				}
			}
		}
	}

	/**
	 * 升级为股东处理
	 */

	public void shareholder(UserDo buyer) {
		// 获取推荐人推荐城市合伙人的个数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userLevel", 3);
		map.put("refereeid", buyer.getRefereeid());
		List<UserDo> listuser = userService.selectUserCondition(map);

		if (listuser == null | listuser.size() < 5) {
			logger.error("推荐城市合伙人少于5个，不能升级为城市合伙人");
			return;
		}

		UserDo userDo = new UserDo();
		userDo.setId(buyer.getRefereeid());
		userDo.setUserLevel((byte)4);
		System.err.println(JSON.toJSON(userService.updateLevel(userDo)));

	}

	/**
	 * 根据配置 用 - 分隔 ，配置进什么账户类型，如果没有配置报错 配置格式： 1-wallet_travel-4人港澳游 表示 1次，旅游账户 奖励
	 * 4人港澳游 ， wallet_travel 查看{@link AccountType}
	 * 
	 * @param oneAward
	 * @return
	 */

}
