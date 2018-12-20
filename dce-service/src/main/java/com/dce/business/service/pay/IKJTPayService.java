package com.dce.business.service.pay;

import com.dce.business.common.result.Result;
import com.dce.business.common.thirdpay.BarcodePayMethodBean;
import com.dce.business.common.thirdpay.BizInfoBean;
import com.dce.business.common.thirdpay.TradeInfoBean;

public interface IKJTPayService {

	/**
	 * 获取短信验证码
	 * @return
	 */
	Result<?> executeGetBankCardCode(String idno, String realName, String mobile,String cardNo,Integer userId) throws Throwable;

	/**
	 * 验证短信验证码
	 * @return
	 */
	Result<?> executeCheckCode(String tokenId,String verifyCode,Integer userId) throws Throwable;
	
	
	/**
	 * 	协议支付
	 * @param payProductCode
	 * @param amount
	 * @param tokenId
	 * @param signingPay
	 * @param bankCardNo
	 * @param phoneNum
	 * @param bankAccountName
	 * @param cvv2
	 * @param validDate
	 * @param certificatesType
	 * @param idNo
	 * @param userId
	 * @return
	 * @throws Throwable
	 */
	Result<?> executePayInstantTrade(String payProductCode
	      	  ,String amount
	      	  ,String tokenId
	      	  ,String signingPay
	      	  ,String bankCardNo
	      	  ,String phoneNum
	      	  ,String bankAccountName
	      	  ,String cvv2
	      	  ,String validDate
	      	  ,String idNo
	      	  ,Integer userId) throws Throwable;

	/**
	 * 微信二维码支付
	 * @param amount
	 * @param userId
	 * @return
	 * @throws Throwable
	 */
	Result<?> executeBarcodePay(BarcodePayMethodBean barcodePayBean, 
								TradeInfoBean tradeInfo, 
								BizInfoBean bizinfo,
								Integer userId) throws Throwable;

}
