package com.dce.business.service.impl.pay;

import com.alibaba.fastjson.JSONObject;
import com.kjtpay.gateway.common.domain.base.RequestBase;

public class InstantTradeCommand extends BaseCommand {

	public InstantTradeCommand(String serviceName,
							  String version,
							  String idno, 
							  String realName, 
							  String mobile,
							  String cardNo,
							  Integer userId
							  ){

		JSONObject getBankCodeCommand = new JSONObject();
		getBankCodeCommand.put("bank_account_name", realName);
		getBankCodeCommand.put("certificates_type", "01");//证件类型
		getBankCodeCommand.put("certificates_number", idno);//证件号码，与证件类型匹配使用
		getBankCodeCommand.put("bank_card_no", cardNo);
		getBankCodeCommand.put("phone_num", mobile);
		getBankCodeCommand.put("pay_product_code", "51");//支付产品码
		getBankCodeCommand.put("extension", "");//备注
		getBankCodeCommand.put("cvv2", "");//安全码，信用卡必传
		getBankCodeCommand.put("valid_date", "");//信用卡有效期YYYY/MM
		
		this.userId = userId;
		
		requestBase = new RequestBase();
		requestBase.setVersion(version);
		requestBase.setService(serviceName);
		requestBase.setBizContent(getBankCodeCommand.toJSONString());
	}


}
