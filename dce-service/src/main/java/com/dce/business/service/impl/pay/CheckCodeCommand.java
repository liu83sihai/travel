package com.dce.business.service.impl.pay;

import com.alibaba.fastjson.JSONObject;
import com.kjtpay.gateway.common.domain.base.RequestBase;

public class CheckCodeCommand extends BaseCommand  {

	public CheckCodeCommand(String serviceName, 
							String version, 
							String tokenId, 
							String verifyCode,
							Integer userId) {

		JSONObject getBankCodeCommand = new JSONObject();
		getBankCodeCommand.put("token_id", tokenId);//银行卡协议号，上一步发送短信验证码的返回
		getBankCodeCommand.put("verify_code", verifyCode);//手机验证码
		getBankCodeCommand.put("extension", "");//备注
		
		this.userId = userId;
		requestBase = new RequestBase();
		requestBase.setVersion(version);
		requestBase.setService(serviceName);
		requestBase.setBizContent(getBankCodeCommand.toJSONString());
	
	}


}
