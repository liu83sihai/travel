package com.dce.business.common.thirdpay;

public class BizInfoBean {
	
	private String  payer_identity_type =  "1" ; //买家标识类型 (买家快捷通会员标识类型，默认1，1-快捷通会员ID，2-快捷通会员登入号，可空)
	private String  payer_identity="anonymous";  // 买家会员ID或登入账号 (如没有快捷通会员ID和登入账号，则填写固定值：anonymous，非空)
	private String  payer_platform_type= "1"; //买家平台类型 (固定值：1，可空)
	private String  payer_ip; // 买家公网IP地址 (用户在商户平台下单时候的ip地址，非商户服务器的ip地址，公网IP,6-32位，非空)
	private String  pay_method; // 支付方式   (根据不同的业务场景选择合适的支付方式，可空)  "{\"pay_product_code\":\"35\",\"amount\":\"0.19\",\"bank_code\":\"WECHATAPP\"}",
	private String  inexpectant_pay_product_code;//不期望使用的支付方式
	private String  biz_product_code = "20601" ;//业务产品码 (取值参考接口文档，非空)
	private String  cashier_type="H5"; //收银台类型 (SDK-SDK收银台，WEB-WEB收银台，H5-H5收银台，非空)
	private String  timeout_express ; //(该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：10m～7d。m-分钟，h-小时，d-天。默认2h。该参数数值不接受小数点，如 1.5h，可转换为 90m，可空)
	private String  trade_info ; //交易信息 非空  "{\"subject\":\"苹果\",\"out_trade_no\":\"20181220155353162527\",\"currency\":\"\",\"price\":\"0.19\",\"quantity\":\"1\",\"total_amount\":\"0.19\",\"payee_identity_type\":\"1\",\"payee_identity\":\"200000151629\",\"biz_no\":\"\",\"show_url\":\"\",\"notify_url\":\"http://10.255.4.54:8002/AlphaTest/gateway/asyncNotify.do\",\"ensure_amount\":\"\",\"gold_coin\":\"\",\"deposit_amount\":\"\",\"deposit_no\":\"\",\"trade_ext\":\"\",\"royalty_info\":\"\"}",
	private String  terminal_info; //终端信息域  (存放终端类型(terminal_type)、ip(ip)等信息字段，非空)
								  //终端类型，标识发送支付指令的终端类型，00电脑，01手机，02平板设备，03可穿戴设备，04数字电视，99其他 "{\"terminal_type\":\"01\",\"ip\":\"122.224.203.210\"}",
	private String  merchant_custom; // 商户自定义域  (商户自定义域：是否自动跳转收银台(go_cashier),默认Y，传N不跳转。商户业务分类(merchant_biz_type)、商户合并标记号（合并账单）(merchant_merge_flag)会在交易记录，统一账单、日账单体现商户特色业务。可空)  "{\"go_cashier\":\"Y\",\"need_login\":\"N\"}",
	private String  return_url; //返回页面路径 (当前页面自动跳转到商户网站里指定页面的http/htts路径，可空)  "http://122.224.203.210:18380/demo/instant_trade/instantReturnUrlResponse.jsp"
	
	
	public BizInfoBean() {
		
	}
	
	public BizInfoBean(String payer_identity_type, String payer_identity, String payer_platform_type, String payer_ip,
			String pay_method, String inexpectant_pay_product_code, String biz_product_code, String cashier_type,
			String timeout_express, String trade_info, String terminal_info, String merchant_custom,
			String return_url) {
		this.payer_identity_type = payer_identity_type;
		this.payer_identity = payer_identity;
		this.payer_platform_type = payer_platform_type;
		this.payer_ip = payer_ip;
		this.pay_method = pay_method;
		this.inexpectant_pay_product_code = inexpectant_pay_product_code;
		this.biz_product_code = biz_product_code;
		this.cashier_type = cashier_type;
		this.timeout_express = timeout_express;
		this.trade_info = trade_info;
		this.terminal_info = terminal_info;
		this.merchant_custom = merchant_custom;
		this.return_url = return_url;
	}
	
	public String getPayer_identity_type() {
		return payer_identity_type;
	}
	public void setPayer_identity_type(String payer_identity_type) {
		this.payer_identity_type = payer_identity_type;
	}
	public String getPayer_identity() {
		return payer_identity;
	}
	public void setPayer_identity(String payer_identity) {
		this.payer_identity = payer_identity;
	}
	public String getPayer_platform_type() {
		return payer_platform_type;
	}
	public void setPayer_platform_type(String payer_platform_type) {
		this.payer_platform_type = payer_platform_type;
	}
	public String getPayer_ip() {
		return payer_ip;
	}
	public void setPayer_ip(String payer_ip) {
		this.payer_ip = payer_ip;
	}
	public String getPay_method() {
		return pay_method;
	}
	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}
	public String getInexpectant_pay_product_code() {
		return inexpectant_pay_product_code;
	}
	public void setInexpectant_pay_product_code(String inexpectant_pay_product_code) {
		this.inexpectant_pay_product_code = inexpectant_pay_product_code;
	}
	public String getBiz_product_code() {
		return biz_product_code;
	}
	public void setBiz_product_code(String biz_product_code) {
		this.biz_product_code = biz_product_code;
	}
	public String getCashier_type() {
		return cashier_type;
	}
	public void setCashier_type(String cashier_type) {
		this.cashier_type = cashier_type;
	}
	public String getTimeout_express() {
		return timeout_express;
	}
	public void setTimeout_express(String timeout_express) {
		this.timeout_express = timeout_express;
	}
	public String getTrade_info() {
		return trade_info;
	}
	public void setTrade_info(String trade_info) {
		this.trade_info = trade_info;
	}
	public String getTerminal_info() {
		return terminal_info;
	}
	public void setTerminal_info(String terminal_info) {
		this.terminal_info = terminal_info;
	}
	public String getMerchant_custom() {
		return merchant_custom;
	}
	public void setMerchant_custom(String merchant_custom) {
		this.merchant_custom = merchant_custom;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	@Override
	public String toString() {
		return "BizInfoBean [payer_identity_type=" + payer_identity_type + ", payer_identity=" + payer_identity
				+ ", payer_platform_type=" + payer_platform_type + ", payer_ip=" + payer_ip + ", pay_method="
				+ pay_method + ", inexpectant_pay_product_code=" + inexpectant_pay_product_code + ", biz_product_code="
				+ biz_product_code + ", cashier_type=" + cashier_type + ", timeout_express=" + timeout_express
				+ ", trade_info=" + trade_info + ", terminal_info=" + terminal_info + ", merchant_custom="
				+ merchant_custom + ", return_url=" + return_url + "]";
	}
	
	

}
