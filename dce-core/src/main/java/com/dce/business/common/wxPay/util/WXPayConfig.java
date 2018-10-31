package com.dce.business.common.wxPay.util;

/**
 * 微信配置参数
 * 
 * @author Administrator
 *
 */
public class WXPayConfig {

	// 微信开发平台应用ID
	public static final String APP_ID = "wx7a337545e02bdead";

	// 应用对应的凭证
	public static final String APP_SECRET = "1dd7398de4ac33c541e609d22b19dc11";

	// 应用对应的密钥
	public static final String APP_KEY = "dfsfdvdfvgk32423423oGdfsfdsvBO66";

	// 微信支付商户号
	public static final String MCH_ID = "1495273442";

	// 订单或者商品描述
	public static final String BODY = "湘信商城消费";

	// 商户号对应的密钥
	//public static final String PARTNER_key = "123466";

	// 商户id
	public static final String PARTNER_ID = "14698sdfs402dsfdew402";

	// 常量固定值
	public static final String GRANT_TYPE = "client_credential";
	
	/**
	 * 获取预支付id的接口url
	 */
	// public static String GATEURL =
	// "https://api.mch.weixin.qq.com/pay/unifiedorder";

	// 微信服务器回调通知url
	public static String NOTIFY_URL = "http://www.58xxjt.com/order/tenpay/notify";

	public static String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token";// 获取access_token对应的url
	public static String EXPIRE_ERRCODE = "42001";// access_token失效后请求返回的errcode
	public static String FAIL_ERRCODE = "40001";// 重复获取导致上一次获取的access_token失效,返回错误码
	public static String GATEURL = "https://api.weixin.qq.com/pay/genprepay?access_token=";// 获取预支付id的接口url
	public static String ACCESS_TOKEN = "access_token";// access_token常量值
	public static String ERRORCODE = "errcode";// 用来判断access_token是否失效的值
	public static String SIGN_METHOD = "sha1";// 签名算法常量值
	// package常量值
	public static String packageValue = "bank_type=WX&body=%B2%E2%CA%D4&fee_type=1&input_charset=GBK&notify_url=http%3A%2F%2F127.0.0.1%3A8180%2Ftenpay_api_b2c%2FpayNotifyUrl.jsp&out_trade_no=2051571832&partner=1900000109&sign=10DA99BCB3F63EF23E4981B331B0A3EF&spbill_create_ip=127.0.0.1&time_expire=20131222091010&total_fee=1";
	//public static String traceid = "testtraceid001";// 测试用户id

}
