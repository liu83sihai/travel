package com.dce.business.common.thirdpay;

public class BarcodePayMethodBean {

	private String pay_product_code; //支付产品码，68:条码-APP-借记卡69:条码-APP-综合  非空
	private String amount; //应付金额，取值范围为[0.01，1000000000000.00)，精确到小数点后两位 非空
	private String app_id; // 公众号appid，请填写已进件成功的APP_ID。若以平台方名义进件，请填写平台方APP_ID   非空
	private String target_organization ="WECHAT"; //目标机构 WECHAT:微信  非空
	
	
	
	
	public BarcodePayMethodBean(String payProductCode, 
								String amount, 
								String appId, 
								String targetOrganization) {
		
		this.pay_product_code = payProductCode;
		this.amount = amount;
		this.app_id = appId;
		this.target_organization = targetOrganization;
	}




	public String getPay_product_code() {
		return pay_product_code;
	}




	public void setPay_product_code(String pay_product_code) {
		this.pay_product_code = pay_product_code;
	}




	public String getAmount() {
		return amount;
	}




	public void setAmount(String amount) {
		this.amount = amount;
	}




	public String getApp_id() {
		return app_id;
	}




	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}




	public String getTarget_organization() {
		return target_organization;
	}




	public void setTarget_organization(String target_organization) {
		this.target_organization = target_organization;
	}

	
}
