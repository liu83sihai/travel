package com.dce.business.service.impl.pay;

import com.alibaba.fastjson.JSONObject;

public class GetBankCodeCommand  implements IKJTCommand{
	
	private String idno;
	private String realName;
	private String mobile;
	private String cardNo;
	private String serviceName;
	
	private JSONObject getBankCodeCommand;
	
	
	
	public GetBankCodeCommand(String serviceName,String idno, String realName, String mobile,String cardNo){
		getBankCodeCommand = new JSONObject();
		getBankCodeCommand.put("idno", idno);
		getBankCodeCommand.put("idno", idno);
		getBankCodeCommand.put("idno", idno);
		this.serviceName = serviceName;
	}

	@Override
	public String getBizContent() {
		return getBankCodeCommand.toJSONString();
	}
	
	
	@Override
	public String getServiceName() {
		return this.serviceName;
	}
	
	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

}
