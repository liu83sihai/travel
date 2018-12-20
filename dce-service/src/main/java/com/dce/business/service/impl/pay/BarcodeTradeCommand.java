package com.dce.business.service.impl.pay;

import com.alibaba.fastjson.JSONObject;
import com.kjtpay.gateway.common.domain.base.RequestBase;

public class BarcodeTradeCommand extends BaseCommand {

	public BarcodeTradeCommand(String serviceName
					      	  ,String version
					      	  ,String payProductCode
					      	  ,String amount
					      	  ,String appId
					      	  ,String targetOrganization
					      	  ,Integer userId){

		JSONObject barcodePayCommand = new JSONObject();
		barcodePayCommand.put("pay_product_code",payProductCode);//51-快捷借记卡，52-快捷贷记卡
		barcodePayCommand.put("amount", amount);//金额
		barcodePayCommand.put("target_organization", targetOrganization);//目标机构 WECHAT:微信
		barcodePayCommand.put("app_id", appId);//微信appid
		
		this.userId = userId;
		requestBase = new RequestBase();
		requestBase.setVersion(version);
		requestBase.setService(serviceName);
		requestBase.setBizContent(barcodePayCommand.toJSONString());
	}


}
