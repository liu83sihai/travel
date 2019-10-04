package com.dce.business.service.impl.award;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dce.business.common.enums.AccountType;
import com.dce.business.common.enums.IncomeType;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.dao.order.OrderDetailMapper;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.goods.CTGoodsDo;
import com.dce.business.entity.order.Order;
import com.dce.business.entity.order.OrderDetail;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.goods.ICTGoodsService;
import com.dce.business.service.order.IOrderService;
import com.dce.business.service.user.IUserService;
import com.dce.business.service.userCard.IUserCardService;

/**
 * 购买用户奖金计算类
 * 
 * @author harry
 *
 */
@Service("buyerAwardCalculator")
public class BuyerAwardCalculator implements IAwardCalculator {

	private Logger logger = Logger.getLogger(getClass());


	@Resource
	private IUserService userService;

	// 账户
	@Resource
	private IAccountService accountService;
	
	@Resource
	private OrderDetailMapper orderDetailDao;
	
	@Resource
	private ICTGoodsService goodsService;
	
	@Resource
	private IUserCardService userCardService;
	
	@Resource
	private IOrderService orderService;
	
	private ThreadLocal<Map<String,Object>> awardContextMap = new ThreadLocal<Map<String,Object>>() ;
	
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
		Map<String,Object> contextMap = new HashMap<String,Object>();
		contextMap.put("buyer", buyer);
		contextMap.put("order", order);
		awardContextMap.set(contextMap);
		
		
		
		//购买旅游卡才可以升级
		boolean isBuyCard = false; 
		List<OrderDetail> orderDetailLst = orderDetailDao.selectByOrderId(order.getOrderid());
		CTGoodsDo goods = null;
		for(OrderDetail od : orderDetailLst) {
			goods = goodsService.selectById(Long.valueOf(od.getGoodsId()));
			if(goods.getGoodsFlag().intValue() == 1) {
				isBuyCard = true;
				//写分红订单
				updateUserFeiHongTotalAmt(buyer.getId(),od.getPrice()*od.getQuantity());
				break;
			}
			
		}
		
		if(isBuyCard == true) {
			UserDo userDo = new UserDo();
			userDo.setId(buyer.getId());
			userDo.setStatus((byte)0);
			UserDo u = userService.getUser(buyer.getId());
			if(u.getUserLevel().byteValue()<(goods.getMemberLevel()==null? (byte)1:goods.getMemberLevel().byteValue() )) {
				userDo.setUserLevel(goods.getMemberLevel()==null? (byte)1:goods.getMemberLevel().byteValue());//vip
			}
			userService.updateUserByBuy(userDo );
		}
		//送199 积分
		if(goods.getJf() != null && goods.getJf().intValue()>0) {
			UserAccountDo accont = new UserAccountDo(new BigDecimal(goods.getJf()), buyer.getId(), AccountType.wallet_travel.name());
			buildAccountRemark(accont);
			// 账户对象增加金额
			accountService.updateUserAmountById(accont, IncomeType.TYPE_PURCHASE_TRAVEL);
		}
		
		//增加旅游卡
		if(goods.getSendCard() != null && goods.getSendCard().intValue()>0) {
			UserAccountDo travelCardAccount = new UserAccountDo(new BigDecimal(order.getQty()), buyer.getId(), AccountType.wallet_active.name());
			travelCardAccount.setAmount(new BigDecimal(goods.getSendCard()));
			accountService.updateUserAmountById(travelCardAccount,IncomeType.TYPE_PURCHASE_TRAVEL);
		}
		
	}

	//购买旅游卡参加分红
	private void updateUserFeiHongTotalAmt(Integer buyerUserId, double buyAmt) {
		orderService.updateUserFeiHongTotalAmt(buyerUserId,buyAmt*4);
	}

	/**
	 * 创建奖励备注
	 * @param account
	 */
	private void buildAccountRemark(UserAccountDo account) {
		
		Map<String,Object> contextMap = awardContextMap.get();
		UserDo buyer =(UserDo)contextMap.get("buyer");
		Order order =(Order) contextMap.get("order");
		
		StringBuffer sb = new StringBuffer();
		sb.append("用户:").append(buyer.getUserName())
		  .append("购买:").append(order.getQty()).append("盒")
		  .append("获得:").append(account.getAmount());
		account.setRemark(sb.toString());
		account.setRelevantUser(String.valueOf(buyer.getId()));//关联用户
		
	}
	
	
	/**
	 * 逐个奖励处理
	 * 
	 * @param buyUserId
	 * @param bAwardLst
	 */
	private void oneAward(int buyUserId, String[] bAwardLst) {
		for (String oneAward : bAwardLst) {
			// 奖励金额
			BigDecimal wardAmount = getAmtByAward(oneAward);

			// 奖励进入那个账户类型
			String accountType = getAccountTypeByAward(oneAward);

			IncomeType incometype = null;
			if (accountType.equals("wallet_travel")) {
				incometype = incometype.TYPE_AWARD_BUYER;
			} else if (accountType.equals("wallet_goods")) {
				incometype = incometype.TYPE_CANCEL_BUY;
			} else if (accountType.equals("wallet_active")) {
				incometype = incometype.TYPE_CANCEL;
			}
			
			/*
			// 是否首次才能奖励
			// boolean isFirst = getIsFirstByAward(oneAward);

			/*
			 * //仅首次才给的奖励 if(isFirst){ //给每个奖励一个编号，根据编号查询是否已经获取过该奖励 String
			 * awardNo = getAwardNoByAward(oneAward);
			 * if(userIsFirst(buyUserId,awardNo,accountType) == false){ return;
			 * } }
			 */
			if (wardAmount.compareTo(BigDecimal.ZERO) > 0) {
				UserAccountDo accont = new UserAccountDo(wardAmount, buyUserId, accountType);
				buildAccountRemark(accont);
				// 账户对象增加金额
				accountService.updateUserAmountById(accont, incometype);
			}
		}
	}

	/**
	 * 判断用户是否是 首次获取该奖励
	 * 
	 * @param buyUserId
	 * @param awardNo
	 * @return
	 */
	/*
	 * private boolean userIsFirst(int buyUserId, String awardNo,String
	 * accountType) { UserAccountDo userAccount =
	 * accountService.selectUserAccount(buyUserId, accountType); //TODO 判断是否存在
	 * 
	 * return false; }
	 */

	/**
	 * 根据配置 用 - 分隔 ，获取奖励次数或金额，如果没有配置报错 配置格式： 1-wallet_travel-isFirst-A001-4人港澳游
	 * 表示 1次，旅游账户 奖励 4人港澳游 ， wallet_travel 查看{@link AccountType}
	 * 
	 * @param oneAward
	 * @return
	 */
	private String getAwardNoByAward(String oneAward) {
		String[] awds = oneAward.split("-");
		if (awds.length < 5) {
			throw new BusinessException("购买者对应的奖励办法没有正确配置，请检查奖励办法的配置", "error-buyerAward-005");
		}
		return awds[4].trim();
	}

	/**
	 * 根据配置 用 - 分隔 ，获取奖励次数或金额，如果没有配置报错 配置格式： 1-wallet_travel-isFirst-A001-4人港澳游
	 * 表示 1次，旅游账户 奖励 4人港澳游 ， wallet_travel 查看{@link AccountType}
	 * 
	 * @param oneAward
	 * @return
	 */
	private boolean getIsFirstByAward(String oneAward) {
		String[] awds = oneAward.split("-");
		if (awds.length < 4) {
			throw new BusinessException("购买者对应的奖励办法没有正确配置，请检查奖励办法的配置", "error-buyerAward-005");
		}
		return "isFirst".equalsIgnoreCase(awds[3].trim());
	}

	/**
	 * 根据配置 用 - 分隔 ，获取奖励次数或金额，如果没有配置报错 配置格式： 1-wallet_travel-isFirst-A001-4人港澳游
	 * 表示 1次，旅游账户 奖励 4人港澳游 ， wallet_travel 查看{@link AccountType}
	 * 
	 * @param oneAward
	 * @return
	 */
	private BigDecimal getAmtByAward(String oneAward) {
		String[] awds = oneAward.split("-");
		if (awds.length < 2) {
			throw new BusinessException("购买者对应的奖励办法没有正确配置，请检查奖励办法的配置", "error-buyerAward-003");
		}
		return new BigDecimal(awds[0].trim());
	}

	/**
	 * 根据配置 用 - 分隔 ，配置进什么账户类型，如果没有配置报错 配置格式： 1-wallet_travel-4人港澳游 表示 1次，旅游账户 奖励
	 * 4人港澳游 ， wallet_travel 查看{@link AccountType}
	 * 
	 * @param oneAward
	 * @return
	 */
	private String getAccountTypeByAward(String oneAward) {
		String[] awds = oneAward.split("-");
		if (awds.length < 2) {
			throw new BusinessException("购买者对应的奖励办法没有正确配置，请检查奖励办法的配置", "error-buyerAward-004");
		}
		return awds[1];
	}

}
