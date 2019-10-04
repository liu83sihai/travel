package com.dce.business.service.impl.award;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dce.business.common.enums.AccountType;
import com.dce.business.common.enums.IncomeType;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.util.CalculateUtils;
import com.dce.business.dao.user.IUserRefereeDao;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.award.Awardlist;
import com.dce.business.entity.dict.LoanDictDo;
import com.dce.business.entity.dict.LoanDictDtlDo;
import com.dce.business.entity.order.FeiHongOrder;
import com.dce.business.entity.order.Order;
import com.dce.business.entity.user.UserDo;
import com.dce.business.entity.user.UserRefereeDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.dict.ILoanDictService;
import com.dce.business.service.groovy.GroovyParse;
import com.dce.business.service.order.IFeiHongService;
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
	
	@Autowired
    private IFeiHongService feiHongService;

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
		//分红
		FeiHongOrder fhorder = orderService.selectFeiHongOrderByOrderId(order.getOrderid());
		if(null != fhorder) {
			feiHongService.doFeiHong(fhorder);
		}
		
		logger.warn("开始计算推荐人奖励"+order);
		if(order.getProfit() == null || BigDecimal.ZERO.compareTo(order.getProfit()) == 0) {
			logger.warn("订单利润等于0 不计算分润"+order.getOrderid());
			return;
		}
		
		Map<String, Object> contextMap = new HashMap<String, Object>();
		contextMap.put("buyer", buyer);
		contextMap.put("order", order);
		awardContextMap.set(contextMap);

		
		
		// 得到奖励配置
		LoanDictDo awardDict = dictService.getLoanDict("zhidu-ref-total-rate");
		if (awardDict == null) {
			throw new BusinessException("没有直推和团队奖现金账户跟积分账户比例配置", "error-refereeAward-001");
		}
		String[] awardArr=awardDict.getRemark().split(":");
		if(awardArr.length<2) {
			throw new BusinessException("直推和团队奖现金账户跟积分账户比例配置项：zhidu-ref-total-rate异常", "error-refereeAward-002");
		}
		BigDecimal moneyRate = new BigDecimal(awardArr[0]);
		//计算推荐奖和团队奖
		calRefAndGroupAward(buyer, order, moneyRate);
		
		//读加权平均配置
		List<LoanDictDtlDo> awardAvgDictLst = dictService.queryDictDtlListByDictCode("zhidu-avg-rate");
		if (awardAvgDictLst == null || awardAvgDictLst.isEmpty()) {
			throw new BusinessException("加权平均奖金比例配置", "error-refereeAward-002");
		}
		Map<String,LoanDictDtlDo> avgRateMap  = awardAvgDictLst.stream().collect(Collectors.toMap(LoanDictDtlDo::getCode, a -> a,(k1,k2)->k1));
			
		//董事加权平均
		dsavgAward(buyer, order, moneyRate,avgRateMap);
		//总监加权平均
		zjAvgAward(buyer, order, moneyRate, avgRateMap);
		
		logger.warn("结束计算推荐人奖励"+order);
		
	}

	//计算推荐奖和团队奖
	private void calRefAndGroupAward(UserDo buyer, Order order, BigDecimal moneyRate) {
		// 获取推荐人,5代推荐人
		Map<String, Object> params = new HashMap<>();// userid
		params.put("userid", buyer.getId());
		params.put("maxDistance", 1000);
		List<UserRefereeDo> list = userRefereeDao.select(params);
		if(list == null || list.isEmpty()) {
			logger.info("找不到推荐人, buyer:"+buyer.getId());
			return;
		}
				
		//推荐人列表
		UserDo[] refUserArray = buildRefUser(list);
		//推荐人对应的分红比例
		float[] rateArray = buildRate(refUserArray);
		
		for (int i = 0 ; i <refUserArray.length;i++) {
			if(rateArray[i] == 0) {
				logger.info("用户分润比例=0 userId:"+refUserArray[i].getId());
				continue;
			}
			
			// 计算奖励金额
			BigDecimal wardAmount = order.getProfit();
			BigDecimal rate = new BigDecimal(rateArray[i]);
			wardAmount = wardAmount.multiply(rate);
			if (wardAmount.compareTo(BigDecimal.ZERO) <= 0) {
				continue;
			}
			
			//奖励金额 按现金账户， 积分账户分
			BigDecimal moneyAccount = wardAmount.multiply(moneyRate);
			moneyAccount = new BigDecimal(CalculateUtils.round(moneyAccount.doubleValue(), 2));
			BigDecimal otherAccount =  wardAmount.subtract(moneyAccount);
			otherAccount =  new BigDecimal(CalculateUtils.round(otherAccount.doubleValue(), 2));
			IncomeType incomeTp = IncomeType.TYPE_AWARD_JIAJIN;
			if(i == 0 ) {
				incomeTp = IncomeType.TYPE_AWARD_REFEREE;
			}
			//现金账户
			UserAccountDo accontMoney = new UserAccountDo(moneyAccount, 
													refUserArray[i].getId(), 
													AccountType.wallet_money.name());
			buildAccountRemark(accontMoney);
			// 账户对象增加金额
			accountService.updateUserAmountById(accontMoney, incomeTp);
			
			UserAccountDo accont = new UserAccountDo(otherAccount, 
													 refUserArray[i].getId(), 
													 AccountType.wallet_travel.name());
			buildAccountRemark(accont);
			// 账户对象增加金额
			accountService.updateUserAmountById(accont, incomeTp);
			
		}
		//end 计算推荐奖和团队奖
	}

	//计算总监加权平均
	private void zjAvgAward(UserDo buyer, Order order, BigDecimal moneyRate, Map<String, LoanDictDtlDo> avgRateMap) {
		//给总监加权平均奖金
		List<UserDo> dsUserLst = userRefereeDao.selectRefUserByUserLevel(buyer.getId(),7);
		if(null == dsUserLst||dsUserLst.isEmpty()) {
			return;
		}
		
		BigDecimal dsAmt = order.getProfit().multiply(new BigDecimal(avgRateMap.get("7") ==null? "0" : avgRateMap.get("7").getRemark()));
		if(dsAmt.compareTo(BigDecimal.ZERO) <= 0) {
			return;
		}
		//现金账户，积分账户
		BigDecimal moneyAccount = dsAmt.multiply(moneyRate);
		BigDecimal otherAccount =  dsAmt.subtract(moneyAccount);
		moneyAccount =  new BigDecimal(CalculateUtils.round(moneyAccount.doubleValue(), 2));
		otherAccount =  new BigDecimal(CalculateUtils.round(otherAccount.doubleValue(), 2));
		
		for(UserDo ds : dsUserLst) {
			//现金账户
			UserAccountDo accontMoney = new UserAccountDo(moneyAccount, 
													ds.getId(), 
													AccountType.wallet_money.name());
			buildAccountRemark(accontMoney);
			// 账户对象增加金额
			accountService.updateUserAmountById(accontMoney, IncomeType.TYPE_AWARD_JIAJIN_AVG);
			
			UserAccountDo accont = new UserAccountDo(otherAccount, 
													 ds.getId(), 
													 AccountType.wallet_travel.name());
			buildAccountRemark(accont);
			// 账户对象增加金额
			accountService.updateUserAmountById(accont, IncomeType.TYPE_AWARD_JIAJIN_AVG);
		}
	}

	//计算董事加权平均
	private void dsavgAward(UserDo buyer, Order order, BigDecimal moneyRate,Map<String,LoanDictDtlDo> avgRateMap) {
		//給董事加权平均
		List<UserDo> dsUserLst = userRefereeDao.selectRefUserByUserLevel(buyer.getId(),8);
		if(null == dsUserLst|| dsUserLst.isEmpty()) {
			return;
		}
		//总的奖金
		BigDecimal dsAmt = order.getProfit().multiply(new BigDecimal(avgRateMap.get("8") ==null? "0" : avgRateMap.get("8").getRemark()));
		if(dsAmt.compareTo(BigDecimal.ZERO) <= 0) {
			return;
		}
		//总的奖金按现金账户， 积分账户分
		BigDecimal moneyAccount = dsAmt.multiply(moneyRate);
		moneyAccount =  new BigDecimal(CalculateUtils.round(moneyAccount.doubleValue(), 2));
		BigDecimal otherAccount =  dsAmt.subtract(moneyAccount);
		otherAccount =  new BigDecimal(CalculateUtils.round(otherAccount.doubleValue(), 2));
		for(UserDo ds : dsUserLst) {		
			//现金账户
			UserAccountDo accontMoney = new UserAccountDo(moneyAccount, 
													ds.getId(), 
													AccountType.wallet_money.name());
			buildAccountRemark(accontMoney);
			// 账户对象增加金额
			accountService.updateUserAmountById(accontMoney, IncomeType.TYPE_AWARD_JIAJIN_AVG);
			
			UserAccountDo accont = new UserAccountDo(otherAccount, 
													 ds.getId(), 
													 AccountType.wallet_travel.name());
			buildAccountRemark(accont);
			// 账户对象增加金额
			accountService.updateUserAmountById(accont, IncomeType.TYPE_AWARD_JIAJIN_AVG);
		}
		//end 董事加权平均
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

	private float[] buildRate(UserDo[] refUserArray) {
		//团队奖金比例
		List<LoanDictDtlDo> awardGroupDictLst = dictService.queryDictDtlListByDictCode("zhidu_group_level_cfg");
		if (awardGroupDictLst == null || awardGroupDictLst.isEmpty()) {
			throw new BusinessException("团队奖金比例没有配置", "error-refereeAward-002");
		}
		Map<String,LoanDictDtlDo> groupRateMap  = awardGroupDictLst.stream().collect(Collectors.toMap(LoanDictDtlDo::getCode, a -> a,(k1,k2)->k1));
		
		//普通 0 , vip  1, 商家 2, 设区合伙人 3， 城市合伙人 4， 省级合伙人 5， 股东 6  总监 7 董事 8
		//直推奖比率
		List<LoanDictDtlDo> awardRefDictLst = dictService.queryDictDtlListByDictCode("zhidu_ref_level_cfg");
		if (awardRefDictLst == null || awardRefDictLst.isEmpty()) {
			throw new BusinessException("直推奖金比例没有配置", "error-refereeAward-003");
		}
		Map<String,LoanDictDtlDo> refRateMap  = awardRefDictLst.stream().collect(Collectors.toMap(LoanDictDtlDo::getCode, a -> a,(k1,k2)->k1));
		
		
		//平级团队奖比例
		List<LoanDictDtlDo> awardSameLevelDictLst = dictService.queryDictDtlListByDictCode("zhidu_same_level_cfg");
		if (awardSameLevelDictLst == null || awardSameLevelDictLst.isEmpty()) {
			throw new BusinessException("团队奖金同级比例没有配置", "error-refereeAward-005");
		}
		Map<String,LoanDictDtlDo> sameLevelRateMap  = awardSameLevelDictLst.stream().collect(Collectors.toMap(LoanDictDtlDo::getCode, a -> a,(k1,k2)->k1));
		
		
		//返回结果： 推荐用户的分润比例数组
		float[] retArray  = new float[refUserArray.length];
		//直推
		retArray[0] = refRateMap.get(String.valueOf(refUserArray[0].getUserLevel())) == null? 0 :Float.parseFloat(refRateMap.get(String.valueOf(refUserArray[0].getUserLevel())).getRemark());
		
		for (int i = 1 ; i <refUserArray.length;i++) {
			byte currentUser = refUserArray[i].getUserLevel();
			retArray[i] =  groupRateMap.get(String.valueOf(currentUser))==null? 0 :Float.parseFloat(groupRateMap.get(String.valueOf(currentUser)).getRemark());
		}
		
		//不能比前面的等级低
		for (int i = refUserArray.length-1 ; i > 0 ;i--) {
			byte currentUser = refUserArray[i].getUserLevel();
			byte idx1User = refUserArray[i-1].getUserLevel();
			if(compareLevel(currentUser, idx1User)<0) {
				retArray[i] = 0 ;
			}
			//平级处理
			if(compareLevel(currentUser , idx1User) ==0) {
				retArray[i] = sameLevelRateMap.get(String.valueOf(currentUser))==null? 0:Float.parseFloat(sameLevelRateMap.get(String.valueOf(currentUser)).getRemark());
			}
		}
		
		//平级，多代同级 只分一代 ： 两种情况 1. 连续评级，2 非连续平级（中间隔低等级的）
		for (int i = refUserArray.length-1 ; i > 0 ;i--) {
			byte currentUser = refUserArray[i].getUserLevel();
			int foundSameCnt = 0;
			//往前找平级
			for (int k = i-1 ; k > 0 ;k--) {
				byte idx1User = refUserArray[k].getUserLevel();
				if(compareLevel(currentUser, idx1User)==0) {
					foundSameCnt++;
				}
			}
			
			if(foundSameCnt>=2) {
				//多代同级 只分一代处理
				retArray[i] = 0 ;
			}
		}
		return retArray;
	}

	private int compareLevel(byte currentUser, byte idx1User) {
		return currentUser - idx1User;
	}


	/**
	 * 	创建奖励备注
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

	
	
}
