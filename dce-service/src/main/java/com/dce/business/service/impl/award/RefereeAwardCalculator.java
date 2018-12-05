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
import com.dce.business.common.util.CalculateUtils;
import com.dce.business.dao.user.IUserRefereeDao;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.award.Awardlist;
import com.dce.business.entity.dict.LoanDictDo;
import com.dce.business.entity.order.Order;
import com.dce.business.entity.user.UserDo;
import com.dce.business.entity.user.UserRefereeDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.dict.ILoanDictService;
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
	private ILoanDictService dictService;

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
		logger.warn("开始计算推荐人奖励"+order);
		if(order.getProfit() == null || BigDecimal.ZERO.compareTo(order.getProfit()) == 0) {
			logger.warn("订单利润等于0 不计算分润"+order.getOrderid());
			return;
		}
		
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
		LoanDictDo awardDict = dictService.getLoanDict("zhidu-ref-total-rate");
		if (awardDict == null) {
			throw new BusinessException("找不到购买者对应的奖励办法，请检查奖励办法的配置", "error-refereeAward-001");
		}
		String[] awardArr=awardDict.getRemark().split(":");
		if(awardArr.length<2) {
			throw new BusinessException("查奖励配置项：zhidu-ref-total-rate异常", "error-refereeAward-002");
		}
		BigDecimal moneyRate = new BigDecimal(awardArr[0]);
		BigDecimal travelRate = new BigDecimal(awardArr[1]);
		
		UserDo[] refUserArray = buildRefUser(list);
		int[] rateArray = buildRate(refUserArray);
		
		for (int i = 0 ; i <refUserArray.length;i++) {

			if(rateArray[i] == 0) {
				logger.info("用户分润比例=0 userId:"+refUserArray[i].getId());
				continue;
			}
			
			// 计算奖励金额
			BigDecimal wardAmount = order.getProfit();
			BigDecimal rate = new BigDecimal(rateArray[i]/100d);
			wardAmount = wardAmount.multiply(rate);
			
			//50%现金账户， 50%积分账户
			BigDecimal moneyAccount = wardAmount.multiply(moneyRate);
			moneyAccount = new BigDecimal(CalculateUtils.round(moneyAccount.doubleValue(), 2));
			BigDecimal otherAccount =  wardAmount.subtract(moneyAccount);
			otherAccount =  new BigDecimal(CalculateUtils.round(otherAccount.doubleValue(), 2));
			if (moneyAccount.compareTo(BigDecimal.ZERO) > 0) {
				//现金账户
				UserAccountDo accontMoney = new UserAccountDo(moneyAccount, 
														refUserArray[i].getId(), 
														AccountType.wallet_money.name());
				buildAccountRemark(accontMoney);
				// 账户对象增加金额
				accountService.updateUserAmountById(accontMoney, IncomeType.TYPE_AWARD_JIAJIN);
				
				UserAccountDo accont = new UserAccountDo(otherAccount, 
														 refUserArray[i].getId(), 
														 AccountType.wallet_travel.name());
				buildAccountRemark(accont);
				// 账户对象增加金额
				accountService.updateUserAmountById(accont, IncomeType.TYPE_AWARD_JIAJIN);
			}
		
			
		}

		
		//找董事
		List<UserDo> dsUserLst = userRefereeDao.selectRefUserByUserLevel(buyer.getId(),8);
		if(null == dsUserLst) {
			return;
		}
		
		for(UserDo ds : dsUserLst) {
			BigDecimal dsAmt = order.getProfit().multiply(new BigDecimal("0.05"));
			
			//50%现金账户， 50%积分账户
			BigDecimal moneyAccount = dsAmt.multiply(moneyRate);
			moneyAccount =  new BigDecimal(CalculateUtils.round(moneyAccount.doubleValue(), 2));
			BigDecimal otherAccount =  dsAmt.subtract(moneyAccount);
			otherAccount =  new BigDecimal(CalculateUtils.round(otherAccount.doubleValue(), 2));
			
			if (moneyAccount.compareTo(BigDecimal.ZERO) > 0) {
				//现金账户
				UserAccountDo accontMoney = new UserAccountDo(moneyAccount, 
														ds.getId(), 
														AccountType.wallet_money.name());
				buildAccountRemark(accontMoney);
				// 账户对象增加金额
				accountService.updateUserAmountById(accontMoney, IncomeType.TYPE_AWARD_JIAJIN);
				
				UserAccountDo accont = new UserAccountDo(otherAccount, 
														 ds.getId(), 
														 AccountType.wallet_travel.name());
				buildAccountRemark(accont);
				// 账户对象增加金额
				accountService.updateUserAmountById(accont, IncomeType.TYPE_AWARD_JIAJIN);
			}
		}
		
		
		//找总监
		dsUserLst = userRefereeDao.selectRefUserByUserLevel(buyer.getId(),7);
		if(null == dsUserLst) {
			return;
		}
		
		for(UserDo ds : dsUserLst) {
			BigDecimal dsAmt = order.getProfit().multiply(new BigDecimal("0.03"));
			
			//50%现金账户， 50%积分账户
			BigDecimal moneyAccount = dsAmt.multiply(moneyRate);
			BigDecimal otherAccount =  dsAmt.subtract(moneyAccount);
			moneyAccount =  new BigDecimal(CalculateUtils.round(moneyAccount.doubleValue(), 2));
			otherAccount =  new BigDecimal(CalculateUtils.round(otherAccount.doubleValue(), 2));
			if (moneyAccount.compareTo(BigDecimal.ZERO) > 0) {
				//现金账户
				UserAccountDo accontMoney = new UserAccountDo(moneyAccount, 
														ds.getId(), 
														AccountType.wallet_money.name());
				buildAccountRemark(accontMoney);
				// 账户对象增加金额
				accountService.updateUserAmountById(accontMoney, IncomeType.TYPE_AWARD_JIAJIN);
				
				UserAccountDo accont = new UserAccountDo(otherAccount, 
														 ds.getId(), 
														 AccountType.wallet_travel.name());
				buildAccountRemark(accont);
				// 账户对象增加金额
				accountService.updateUserAmountById(accont, IncomeType.TYPE_AWARD_JIAJIN);
			}
		}
		
		logger.warn("结束计算推荐人奖励"+order);
		
	}

	private UserDo[] buildRefUser(List<UserRefereeDo> refArray) {
		UserDo[] refUserArray  = new UserDo[refArray.size()];
		for (int i = 0 ; i <refArray.size();i++) {
			UserRefereeDo temp = refArray.get(i);
			UserDo u =  userService.getUser(temp.getRefereeid());
			refUserArray[i] = u;
		}
		return refUserArray;
	}

	private int[] buildRate(UserDo[] refArray) {
		//默认推荐分红比率
		int[] rateArray  = {49,8,5,4,3};
		//普通 0 , vip  1, 商家 2, 设区合伙人 3， 城市合伙人 4， 省级合伙人 5， 股东 6  总监 7 董事 8
		int[] firstRefRateArray  = {0,49,49,57,62,66,69,69,69};
		//分润用户等级
		byte[] userLevelArray = new byte[refArray.length];
		for (int i = 0 ; i <refArray.length;i++) {
			byte currentUserLevel = refArray[i].getUserLevel();
			//商家跟社区合伙人平级，先处理成一致
			if(currentUserLevel == 2) {
				currentUserLevel =3;
			}
			//董事跟股东一样处理
			if(currentUserLevel == 7) {
				currentUserLevel = 6;
			}
			
			//总监跟股东一样处理
			if(currentUserLevel == 8) {
				currentUserLevel = 6;
			}
			
			userLevelArray[i] = currentUserLevel;
		}
		
		//推荐用户的分润比例数组
		int[] retArray  = new int[refArray.length];
		
		//直推
		int f= firstRefRateArray[refArray[0].getUserLevel()];
		retArray[0] = f;
		
		for (int i = 1 ; i <userLevelArray.length;i++) {
			int idx1 = i -1;
			int idx2 = i -2;
			byte currentUser = userLevelArray[i];
			byte  idx1User = 0;
			byte idx2User = 0;
			if(idx1>=0) {
				idx1User = userLevelArray[idx1];
			}
			if(idx2>=0) {
				idx2User = userLevelArray[idx2];
			}
			
			//普通用户和vip不参与
			if(currentUser == 0 || currentUser == 1 ) {
				retArray[i] = 0 ;
				continue;
			}
			
			//不能比前面的等级低
			if(currentUser < idx1User) {
				retArray[i] = 0 ;
				continue;
			}
			
			//平级只分一代
			if(currentUser == idx1User && idx1User == idx2User) {
				retArray[i]=0;
				continue;
			}
			
			//平级处理
			if(currentUser == idx1User && idx1User != idx2User) {
				retArray[i]= 1;
				//如果是股东 
				if(currentUser == 6 && retArray[i] == 1d) {
					retArray[i]= 2;
				}
				continue;
			}
			//不相邻的平级， 如果前一个是0，继续查看前面
			boolean findNext = false;
			if(retArray[i-1] ==0) {
				findNext =true;
			}
			boolean isSamelevel = false;
			if(findNext) {
				for(int k = i-2; k>=0;k--) {
					if(retArray[k] != 0) {
						if(userLevelArray[k]> currentUser) {
							retArray[i] = 0;
							isSamelevel = true;
							break;
						}
						//等于 1,2，表示上一个已经是平级
						if(userLevelArray[k]==currentUser &&  retArray[k]==1) {
							retArray[i] = 0;
							isSamelevel = true;
							break;
						}
						if(userLevelArray[k]==currentUser &&  retArray[k]==2) {
							retArray[i] = 0;
							isSamelevel = true;
							break;
						}
					}
				}
				if(isSamelevel) {
					continue;
				}
			}
			retArray[i] = rateArray[i];
		}
		
		//计算 股东差级
		int total = 0;
		for(int j = 0; j < retArray.length ;j++) {
			if(userLevelArray[j]==6 ) {
				int remainRate = 69-total;
				if(remainRate>=0) {
					retArray[j] = remainRate;
				}else {
					retArray[j] = 0;
				}
				
				break;
			}
			total=total+retArray[j];
			 
		}
		return retArray;
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
				.append(CalculateUtils.round(account.getAmount().doubleValue()));
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
