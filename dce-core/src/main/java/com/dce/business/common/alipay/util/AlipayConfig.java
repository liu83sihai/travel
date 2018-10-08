package com.dce.business.common.alipay.util;

/**
 * 支付宝公用参数
 * 
 * @author Administrator
 *
 */
public class AlipayConfig {

	public static String APPID = "2018080960938624";
	
	// 商户支付宝用户号
	public static String seller_id = "2088231284681737";

	// 私钥 pkcs8格式
	public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDjZ0gpwSCFoXiWuyb4oUTPAJdkamb8Ay0Yq4XvDzcjsw0pt7rGJWEpnRoFZ1MVNuKAi5dF6EwWpTHEXgdmc2DNBZ78ARz0xwgMbIVaS/cXoH2SvlNMu0GAURGMycU6iXp5eu8zSRQUP8QZZJVS0Ag15xWy/cZ4cM1Fvqmx/JR/7LpWv0RMqIwFTgb9dzrq0R6+nKdcb8IxVEhArWqcfYTwZDSkk4os83CAla2FkR+QUJQeCtc5+QYbmNyiM0IYVNfKYg7ZaEc2POe7a0SQCCh8jQ6ZJ6JkvHufIjFDDKMBMKuEhoNwYl256mjjRX3RxY8452hsB052ZGNZXWXaqPBBAgMBAAECggEBALMMMhx2LHYrAfikWPXwfG4ESC9PO9D3az1+Z9EniMG6DAYp3IyBB/Ss9r0Ecr1EXqjZv/zR/37hCMNVSbxX4gLrhxdihPsR7vthQsoq/92Bhv3Qj2PeGJ/AtPHGOF1zu5QEwGwAAShEYNGhiZUPqTdPwJ0TBX71Shq9GX3DouTo8oWpc1hXENqopX6idesET7ASXquuYvwWW0vi7tvwf/A8PrhPD8ycZfiQVtkfsd3nl8qye9+u5NkjCCrTYxHEzc7EWCzdchwIFIqrRH/JFuF9T8i4wv5G20Sc0pKa6KLnpN6D4Dmyz8Mcbmj7RnZQgGv7dGgiUJ3BvZyO4F6VNcECgYEA8r9Hqn+hhxfwVjklRfUd5t4NavnKa6QdRvTFeU1P+Jr04l8bi/1KNnK9/a9B7Re5arZIijrHBWfOIy5Bfat3qXyDrKP71N3x3jElqcqGEn+JsVUNjF+x2NuBYlfbRZXJrh5wv1tHdyUs+RMHr/iWx2DR6jbx9VdAbnrhaZNSVhkCgYEA79GNajbyIHzcEKEyCgHtm4FGhtEvUGrT1bi4/doImmekLr3qnVnqBDS8gfbP5Ozm99ykZamvqnexdK/sAMHHkTi6frgm0nRr/wsojSDLlPnSedE2xxj9tZWc89oxjV/4bo6FBVkxD1KDQT27AFcIQjfdSo3QmQuuWxEx2LM0oGkCgYBJY/dg3vGRDHXHO8O/INqHgD8CRr8iSB3p4/sIL42sFf1Pq32Cl3Omfo5ecmw2KGQQlUfdMuQVCEtUa5m1SvAEt/Z/bZynwOptSOKgMPDUCucwTAyqkgwNCmBVwUY0BZNSJzXjRM+YgA7WxyAd3cSIRwdwDmicymJahKA98SKFkQKBgFGUgXy+UEOI9fuaOkF5QnEB2xIFmwjKLmGnwxZn00FjPFDCPktkyNzBa9byNOp1RGTQ5APJY36j1PQS3D+tq8vU9mwhDOPOwuYxz4uiul5lVZom93FnKPdae9fgQ7cKDKLfTJTzMGM7+HZ7H2AQOzj7dAXmlC1b8eX8xW4oGCDxAoGAAJiODUwCe0rzRZZL61OQx+TDLiOpl5r+nnB9qp6C/6zr0EppN9C4YmHNpcIZdf7HTZ5LIRXl8bZcbAKIM3g5xsvQJ4XVcdX3lQgX9iTNq+95i+/wYZ27YVJ9ZUTzHxqbPKqZzpHbKGF9Ma86K7nFqpswUiKDQzwRZRzDuKG1cfM=";

	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApKVKnMg5/5V/T4HVP+ecji6l3SoKyeBM4HbDsIvPDAPeh2v5nCihl8x2f+/00pkxmzu51tfIrguJ7H9euTT1blzRwpws7NTg01GrD34mWGSFcL4Q61wLX6dCYP23DxJ1UbaouZ59shL6i6gYJe43+mS3pbdVJtDn0T/7+EckxHq7tENhqeTK6vn1S+d7rA19FEPxt/EVla3sU1VOUylX/2doLhALTkAFtjeCFEtafekXfTA/YGMXDr20ebmQ5GWleRtKva8tgRger/DVL6vcVfJDecuaSndCrTbhqsopEYIxfcBA07z9o9+wtFgqdxQAGxTkd8ZW1tUdGZxzAcXHCQIDAQAB";

	// 请求支付宝的网关地址
	public static String URL = "https://openapi.alipay.com/gateway.do";

	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://47.106.123.116:8080/dce-app/order/notify_url.do";
	//public static String notify_url = "http://193073pl14.imwork.net:46973/dce-app/order/notify_url.do";
	
	// 返回格式
	public static String FORMAT = "JSON";

	// 编码
	public static String CHARSET = "UTF-8";

	// 签名方式
	public static String SIGNTYPE = "RSA2";
	
	//交易的超时时间	取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）
	public static String timeoutExpress = "30m";
	
}
