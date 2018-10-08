package com.dce.business.service.impl.award;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dce.business.common.enums.AccountType;
import com.dce.business.common.enums.IncomeType;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.award.Awardlist;
import com.dce.business.entity.order.Order;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.award.IAwardlistService;
import com.dce.business.service.groovy.GroovyParse;
import com.dce.business.service.order.IOrderService;
import com.dce.business.service.user.IUserService;

/**
 * 推荐人奖金计算类
 * 
 * @author harry
 *
 */
@Service("refereeAwardCalculator")
public class RefereeAwardCalculator implements IAwardCalculator {

	private Logger logger = Logger.getLogger(getClass());

	@Resource
	private IAwardlistService awardlistService;

	@Resource
	private IUserService userService;

	// 账户
	@Resource
	private IAccountService accountService;

	@Resource
	private IOrderService orderService;

	private ThreadLocal<Map<String, Object>> awardContextMap = new ThreadLocal<Map<String, Object>>();

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

		Map<String, Object> contextMap = new HashMap<String, Object>();
		contextMap.put("buyer", buyer);
		contextMap.put("order", order);
		awardContextMap.set(contextMap);

		// 获取推荐人
		UserDo ref1 = userService.getUser(buyer.getRefereeid());
		if (ref1 == null) {
			logger.info("会员userId=" + buyer.getId() + "购买订单id=" + order.getOrderid() + "推荐人没有查找到");
			return;
		}
		// 第二个推荐人
		UserDo ref2 = userService.getUser(ref1.getRefereeid());
		if (ref2 == null) {
			logger.info("会员userId=" + buyer.getId() + "购买订单id=" + order.getOrderid() + "推荐人没有查找到");
		}

		// 得到奖励记录
		Awardlist award = awardlistService.getAwardConfigByQtyAndBuyerLevel(buyer.getUserLevel(), order.getQty());

		// 分级奖励
		if ((buyer.getUserLevel() == 0 && order.getQty() >= 5 && order.getQty() < 50)
				|| (buyer.getUserLevel() == 1 && order.getQty() >= 5 && order.getQty() < 50)) {
			distributionone(buyer.getRefereeid(), order.getQty(), award, order);
			return;
		}

		if (award == null) {
			throw new BusinessException("找不到购买者对应的奖励办法，请检查奖励办法的配置", "error-refereeAward-001");
		}

		if (ref1 != null) {

			if (ref1.getUserLevel() < 2 && order.getQty() > 0 && order.getQty() < 5) {
				// 体验奖
				experiencePrize(ref1.getId(), order.getQty(), award, order);
			} else {
				String awardConf = getAwardConfByRefLevel(ref1.getUserLevel(), 1, award);
				// 多种奖励办法以;分隔
				String[] bAwardLst = awardConf.split(";");
				IncomeType awardsShow = IncomeType.TYPE_AWARD_FUTOU;
				if (order.getQty() >= 50 && buyer.getUserLevel() < 3 && ref1.getUserLevel() > 1) {
					awardsShow = IncomeType.TYPE_GD_SAL;
				}
				if (ref1.getUserLevel() < 2 && order.getQty() >= 5) {
					logger.debug("用户等级不支持享用5盒以上的奖励");
					return;
				}
				oneAward(ref1.getId(), bAwardLst, order, awardsShow);
			}
		}

		// 推荐人为空，下一个
		if (ref2 != null) {
			String awardConf = getAwardConfByRefLevel(ref2.getUserLevel(), 2, award);
			// 多种奖励办法以;分隔
			String[] bAwardLst = awardConf.split(";");
			IncomeType awardsShow = IncomeType.TYPE_AWARD_JIAJIN;
			oneAward(ref2.getId(), bAwardLst, order, awardsShow);
		}

	}

	/**
	 * 逐个奖励处理
	 * 
	 * @param buyUserId
	 * @param bAwardLst
	 */
	private void oneAward(int buyUserId, String[] bAwardLst, Order order, IncomeType awardsShow) {
		// 获取购买者信息
		UserDo user = userService.getUser(order.getUserid());
		int num = 0;
		for (String oneAward : bAwardLst) {
			num += 1;
			if (StringUtils.isBlank(oneAward)) {
				return;
			}
			// 判断是否隔代发放奖励
			if (num == 2) {
				awardsShow = awardsShow.TYPE_AWARD_FUTOU;

				if (user.getRefereeid() != buyUserId) {
					logger.debug("隔代发放奖励");
					return;
				}
			}
			// 解析单个奖励配置
			String[] awds = oneAward.split(",");
			// 计算奖励金额
			BigDecimal wardAmount = getAmtByAward(awds, order);
			// 获取奖励账户
			String accountType = getAccountTypeByAward(awds);

			if (wardAmount.compareTo(BigDecimal.ZERO) > 0) {
				UserAccountDo accont = new UserAccountDo(wardAmount, buyUserId, accountType);
				buildAccountRemark(accont);
				// 账户对象增加金额
				accountService.updateUserAmountById(accont, awardsShow);
			}
		}
	}

	/**
	 * 创建奖励备注
	 * 
	 * @param account
	 */
	private void buildAccountRemark(UserAccountDo account) {

		Map<String, Object> contextMap = awardContextMap.get();
		UserDo buyer = (UserDo) contextMap.get("buyer");
		Order order = (Order) contextMap.get("order");

		StringBuffer sb = new StringBuffer();
		sb.append("用户:").append(buyer.getUserName()).append("购买:").append(order.getQty()).append("盒").append("获得:")
				.append(account.getAmount());
		account.setRemark(sb.toString());
		account.setRelevantUser(String.valueOf(buyer.getId()));// 关联用户

	}

	/**
	 * 
	 * @param refUser
	 *            推荐用户
	 * @param refSort
	 *            推荐人顺序 1： 第一推荐人， 2：第二推荐人
	 * @return
	 */
	private String getAwardConfByRefLevel(byte refUserLevel, int refSort, Awardlist award) {
		String awardConf = null;
		if (1 == refSort) {
			switch (refUserLevel) {
			case 0:
				awardConf = award.getP1Level0();
				break;
			case 1:
				awardConf = award.getP1Level1();
				break;
			case 2:
				awardConf = award.getP1Level2();
				break;
			case 3:
				awardConf = award.getP1Level3();
				break;
			case 4:
				awardConf = award.getP1Level4();
				break;
			}
		}
		if (2 == refSort) {
			switch (refUserLevel) {
			case 0:
				awardConf = award.getP2Level0();
				break;
			case 1:
				awardConf = award.getP2Level1();
				break;
			case 2:
				awardConf = award.getP2Level2();
				break;
			case 3:
				awardConf = award.getP2Level3();
				break;
			case 4:
				awardConf = award.getP2Level4();
				break;
			}
		}

		if (awardConf == null) {
			throw new BusinessException("购买者对应的奖励办法没有正确配置，请检查奖励办法的配置", "error-refereeAward-002");
		}
		return awardConf;
	}

	/**
	 * 根据配置 用 - 分隔 ，获取奖励次数或金额，如果没有配置报错 配置格式： 1-wallet_travel-4人港澳游 表示 1次，旅游账户 奖励
	 * 4人港澳游 ， wallet_travel 查看{@link AccountType}
	 * 
	 * @param oneAward
	 * @return
	 */
	private BigDecimal getAmtByAward(String[] awds, Order order) {

		if (awds.length < 2) {
			throw new BusinessException("购买者对应的奖励办法没有正确配置，请检查奖励办法的配置", "error-refereeAward-003");
		}
		String formula = awds[0].trim();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("n", order.getQty());
		return new BigDecimal(String.valueOf(GroovyParse.executeScript(formula, map)));
	}

	/**
	 * 根据配置 用 - 分隔 ，配置进什么账户类型，如果没有配置报错 配置格式： 1-wallet_travel-4人港澳游 表示 1次，旅游账户 奖励
	 * 4人港澳游 ， wallet_travel 查看{@link AccountType}
	 * 
	 * @param oneAward
	 * @return
	 */
	private String getAccountTypeByAward(String[] awds) {
		if (awds.length < 2) {
			throw new BusinessException("购买者对应的奖励办法没有正确配置，请检查奖励办法的配置", "error-refereeAward-004");
		}
		return awds[1];
	}

	// 800元推荐人奖励 userid 推荐人id
	private void distributionone(int userid, int count, Awardlist award, Order order) {

		// 获取推荐人信息
		UserDo userone = userService.getUser(userid);

		if (userone == null) {
			logger.error("推荐人为空,800奖励失败");
			return;
		}

		if (userone.getUserLevel() > 1) {
			String awardConf = getAwardConfByRefLevel(userone.getUserLevel(), 1, award);
			// 多种奖励办法以;分隔
			String[] bAwardLst = awardConf.split(";");
			IncomeType awardsShow = IncomeType.TYPE_AWARD_BAODAN;
			oneAward(userone.getId(), bAwardLst, order, awardsShow);
			// 派发500奖励
			distributiontwo(userone.getRefereeid(), count, award, order);

		} else {
			distributionone(userone.getRefereeid(), count, award, order);
		}
	}

	// 500元推荐人奖励 userid 推荐人id
	private void distributiontwo(int userid, int count, Awardlist award, Order order) {

		// 获取推荐人信息
		UserDo userone = userService.getUser(userid);

		if (userone == null) {
			logger.error("推荐人为空,500奖励失败");
			return;
		}

		if (userone.getUserLevel() > 1) {
			String awardConf = getAwardConfByRefLevel(userone.getUserLevel(), 2, award);
			// 多种奖励办法以;分隔
			String[] bAwardLst = awardConf.split(";");
			IncomeType awardsShow = IncomeType.TYPE_AWARD_JIAJIN;
			oneAward(userone.getId(), bAwardLst, order, awardsShow);
		} else {
			distributiontwo(userone.getRefereeid(), count, award, order);
		}
	}

	// 会员300体验奖发放 userid 推荐人id
	public void experiencePrize(int userid, int count, Awardlist award, Order order) {
		Map<String, Object> map = new HashMap<>();
		UserDo user = userService.getUser(userid);
		map.put("userId", userid);
		map.put("incomeType", IncomeType.TYPE_AWARD_EXPERIENCE.getIncomeType());
		if ((!accountService.selectUserAccountDetail(map).isEmpty())
				|| accountService.selectUserAccountDetail(map).size() > 0) {
			logger.error("已享受会员体验奖");
			return;
		}
		String awardConf = getAwardConfByRefLevel(user.getUserLevel(), 1, award);
		// 多种奖励办法以;分隔
		String[] bAwardLst = awardConf.split(";");
		IncomeType awardsShow = IncomeType.TYPE_AWARD_EXPERIENCE;
		oneAward(user.getId(), bAwardLst, order, awardsShow);
	}

}
