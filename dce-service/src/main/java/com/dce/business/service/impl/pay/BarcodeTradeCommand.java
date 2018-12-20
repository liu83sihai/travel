package com.dce.business.service.impl.pay;


import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.thirdpay.BarcodePayMethodBean;
import com.dce.business.common.thirdpay.BizInfoBean;
import com.dce.business.common.thirdpay.TradeInfoBean;
import com.kjtpay.gateway.common.domain.base.RequestBase;

public class BarcodeTradeCommand extends BaseCommand {

	public BarcodeTradeCommand(String serviceName
					      	   ,String version
					      	   ,BarcodePayMethodBean barcodePayBean 
					      	   ,TradeInfoBean tradeInfo
					      	   ,BizInfoBean bizinfo
					      	   ,Integer userId){

		this.userId = userId;
		
		requestBase = new RequestBase();
		requestBase.setVersion(version);
		requestBase.setService(serviceName);
		
		bizinfo.setTrade_info(JSONObject.toJSONString(tradeInfo));
		bizinfo.setPay_method(JSONObject.toJSONString(barcodePayBean));
		String bizContnt= JSONObject.toJSONString(bizinfo);
		JSONObject bizJson = ConvertUtil.convertParm(serviceName,bizContnt);
		requestBase.setBizContent(bizJson.toJSONString());
	}


}
