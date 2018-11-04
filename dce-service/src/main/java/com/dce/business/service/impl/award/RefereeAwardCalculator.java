package com.dce.business.service.impl.award;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dce.business.common.enums.AccountType;
import com.dce.business.common.enums.IncomeType;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.dao.user.IUserRefereeDao;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.award.Awardlist;
import com.dce.business.entity.order.Order;
import com.dce.business.entity.user.UserDo;
import com.dce.business.entity.user.UserRefereeDo;
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
	
	@Resource IUserRefereeDao userRefereeDao;

	private ThreadLocal<Map<String, Object>> awardContextMap = new ThreadLocal<Map<String, Object>>();

	/**
	 * 计算推荐奖
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

		// 获取推荐人,5代推荐人
		Map<String, Object> params = new HashMap<>();// userid
		params.put("userid", buyer.getId());
		params.put("maxDistance", 5);
		List<UserRefereeDo> list = userRefereeDao.select(params);
		if(list == null || list.size()<1) {
			logger.info("找不到推荐人, buyer:"+buyer.getId());
			return;
		}
		
		// 得到奖励配置
		Awardlist award = awardlistService.getAwardConfigByQtyAndBuyerLevel(buyer.getUserLevel(), order.getQty());
		if (award == null) {
			throw new BusinessException("找不到购买者对应的奖励办法，请检查奖励办法的配置", "error-refereeAward-001");
		}
		
		UserRefereeDo[] refArray = (UserRefereeDo[])list.toArray();
		UserDo[] refUserArray = buildRefUser(refArray);
		double[] rateArray = buildRate(refUserArray,award);
		for (int i = 0 ; i <refArray.length;i++) {
			UserRefereeDo temp = refArray[i];
			
			UserDo refUser = userService.getUser(temp.getRefereeid());
			String awardConf = award.getAwardConfigByLevel(refUser.getUserLevel());
			// 多种奖励办法以;分隔
			String[] bAwardLst = awardConf.split(";");
			IncomeType awardsShow = IncomeType.TYPE_AWARD_JIAJIN;
			oneAward(temp.getId(), bAwardLst, order, awardsShow);
		}

	}

	private UserDo[] buildRefUser(UserRefereeDo[] refArray) {
		UserDo[] refUserArray  = new UserDo[refArray.length];
		for (int i = 0 ; i <refArray.length;i++) {
			UserRefereeDo temp = refArray[i];
			UserDo u =  userService.getUser(temp.getRefereeid());
			refUserArray[i] = u;
		}
		return refUserArray;
	}

	private double[] buildRate(UserDo[] refArray,Awardlist award) {
		//默认推荐分红比率
		double[] rateArray  = {49d,8d,5d,4d,3d};
		//普通 0 , vip  1, 商家 2, 设区合伙人 3， 城市合伙人 4， 省级合伙人 5， 股东 6  董事 7
		double[] firstRefRateArray  = {0d,49d,49d,57d,62d,66d,69d,5d};
		
		
		
		//直推
		double f= firstRefRateArray[refArray[0].getUserLevel()];
		rateArray[0] = f;
		
		for (int i = 1 ; i <refArray.length;i++) {
			int idx1 = i -1;
			int idx2 = i -2;
			byte currentUser = refArray[i].getUserLevel();
			byte  idx1User = 0;
			byte idx2User = 0;
			if(idx1>0) {
				idx1User = refArray[idx1].getUserLevel();
			}
			if(idx2>0) {
				idx2User = refArray[idx2].getUserLevel();
			}
			if(currentUser == idx1User && idx1User == idx2User) {
				rateArray[i]=0;
			}
			if(currentUser == idx1User && idx1User != idx2User) {
				rateArray[i]= 1d;
			}
			//如果是股东 
			if(currentUser == 6 && rateArray[i] == 1d) {
				rateArray[i]= 2d;
			}
			
		}
		
		return rateArray;
	}

	/**
	 * 逐个奖励处理
	 * 
	 * @param buyUserId
	 * @param bAwardLst
	 */
	private void oneAward(int buyUserId, String[] bAwardLst, Order order, IncomeType awardsShow) {
		// 获取购买者信息
		for (String oneAward : bAwardLst) {
			if (StringUtils.isBlank(oneAward)) {
				return;
			}
			// 解析单个奖励配置
			String[] awds = oneAward.split(",");
			// 计算奖励金额
			BigDecimal wardAmount = getAmtByAward(awds, order);
			// 获取奖励账户
			String accountType = Awardlist.getAccountTypeByAward(awds);
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
		map.put("profit", order.getProfit());
		//map.put("rate", getRate());
		return new BigDecimal(String.valueOf(GroovyParse.executeScript(formula, map)));
	}
}
