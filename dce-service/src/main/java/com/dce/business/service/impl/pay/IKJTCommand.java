package com.dce.business.service.impl.pay;

import com.kjtpay.gateway.common.domain.base.RequestBase;

public interface IKJTCommand {
	
	RequestBase getRequestBase();
	
	String getBizContent();
	
	void setBizContent(String bizContent);
	
	String getTradeInfo();
	
	Integer getUserId();
	
	//发送短信验证码
	String COMMAND_GET_BANK_CARD_CODE="card_register_apply";
	//确认短信验证码
	String COMMAND_CHECK_BANK_CARD_CODE="card_register_advance";
	//即时支付
	String COMMAND_PAY_instant_trade="instant_trade";
	


}
