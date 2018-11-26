package com.dce.business.service.impl.pay;

import com.alibaba.fastjson.JSONObject;
import com.kjtpay.gateway.common.domain.base.RequestBase;

public class InstantTradeCommand extends BaseCommand {

	public InstantTradeCommand(String serviceName
					      	  ,String version
					      	  ,String payProductCode
					      	  ,String amount
					      	  ,String tokenId
					      	  ,String signingPay
					      	  ,String bankCardNo
					      	  ,String phoneNum
					      	  ,String bankAccountName
					      	  ,String cvv2
					      	  ,String validDate
					      	  ,String idNo
					      	  ,Integer userId){

		JSONObject getBankCodeCommand = new JSONObject();
		getBankCodeCommand.put("pay_product_code",payProductCode);//51-快捷借记卡，52-快捷贷记卡
		getBankCodeCommand.put("amount", amount);//金额
		getBankCodeCommand.put("token_id", tokenId);//协议号： 调用协议支付-签约触发短信验证,协议支付-签约确认获得该值,参考:1.协议支付-签约触发短信验证   2.协议支付-签约确认   3.协议支付-签约结果查询  
		getBankCodeCommand.put("signing_pay", "N");//签约并支付:需要使用时固定传:Y,新卡第一次签约快捷通时使用,加上下面的银行卡四要素字段进行签约并支付.当token_id有值和signing_pay=Y时会报错，不使用签约并支付时该字段为空或者设置N。
		getBankCodeCommand.put("bank_card_no", bankCardNo);//银行卡号，字母数字signing_pay=Y时必填
		getBankCodeCommand.put("phone_num", phoneNum);//手机号码，数字signing_pay=Y时必填
		getBankCodeCommand.put("bank_account_name", bankAccountName);//银行卡账户名，不能包含数字signing_pay=Y时必填
		getBankCodeCommand.put("cvv2", "");//安全码，信用卡必传
		getBankCodeCommand.put("valid_date", "");//信用卡有效期YYYY/MM
		getBankCodeCommand.put("certificates_type", "01");//证件类型参考附录证件类型signing_pay=Y时必填
		getBankCodeCommand.put("certificates_number", idNo);//证件号码signing_pay=Y时必填
		
		this.userId = userId;
		requestBase = new RequestBase();
		requestBase.setVersion(version);
		requestBase.setService(serviceName);
		requestBase.setBizContent(getBankCodeCommand.toJSONString());
	}


}
