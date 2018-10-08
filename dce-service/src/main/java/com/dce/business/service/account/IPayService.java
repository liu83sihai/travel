package com.dce.business.service.account;

import java.math.BigDecimal;

import com.dce.business.common.pay.util.Trans;
import com.dce.business.common.result.Result;
import com.dce.business.entity.trade.WithdrawalsDo;

public interface IPayService {
	/** 
	 * 充值
	 * @param userId
	 * @param password
	 * @param qty
	 * @return  
	 */
	Result<?> recharge(Integer userId, String password, BigDecimal qty);

	/**
	 * 审批提现 
	 * @param userId
	 * @param password
	 * @param qty
	 * @return  
	 */
	Result<?> withdraw(Integer withdrawId, Integer userId, BigDecimal qty,String bankNo,String trueName);
	
	Trans withdraw(WithdrawalsDo withdraw);
	/**
	 * 提现 
	 * @param userId
	 * @param password
	 * @param qty
	 * @return  
	 */
	Result<?> withdraw(Integer userId, String password,String type,BigDecimal qty,String bank_no);
	
	 /**
     * 钱包金额转出
     * @param userId 用户id
     * @param qty 转出额度
     * @param accountType 钱包类型
     * @param receiver 接收人
     * @param password 交易密码
     * @return
     */
    Result<?> transOut(Integer userId, BigDecimal qty, String accountType, String receiver,String password);
    
    /**
	 * 购买商品支付
	 * @param userId
	 * @param totalPrice
	 * @return
	 */
	Result<?> buyGoods(Integer userId, BigDecimal totalPrice);
}
