package com.dce.business.service.impl.pay;

import com.kjtpay.gateway.common.domain.base.RequestBase;

public class BaseCommand  implements IKJTCommand{
	
	protected RequestBase requestBase;
	
	protected Integer userId;
	
	
	@Override
	public String getBizContent() {
		return this.requestBase.getBizContent();
	}

	@Override
	public void setBizContent(String bizContent) {
		this.requestBase.setBizContent(bizContent);
	}

	@Override
	public RequestBase getRequestBase() {
		return this.requestBase;
	}
	
	@Override
	public String getTradeInfo() {
		return "";
	}
	
	public Integer getUserId() {
		return userId;
	}

}
