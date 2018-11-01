package com.dce.business.common.alipay.util;

/**
 * 支付宝公用参数
 * 
 * @author Administrator
 *
 */
public class AlipayConfig {

	public static String APPID = "2018110161918996";
	
	// 商户支付宝用户号
	public static String seller_id = "2088131651626830";

	// 私钥 pkcs8格式
	public static String RSA_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCNRyfVh+yUsHrvHQMhPGSKMylvmTeEHvR9UArBevT89lQpgtLeb+j/NnjA178x7N+Vlp/W1JN9mJt8YO+F8+VEg58H2cNGrTOxWjlAbl3X5kMGgChdedfM/nVEyR1sJeQs7GAtR3ejsdtCzTzm35xBa3XJm08CdNJXsdjaeUUc7vDqv871R7Q5CCDDWuLiDAADbidWB3btN+aN+tsss89+tst6EtKDTraGdw4rsj7C7yQkcETytuhqk7yMCE56TxItJ0nO2iraGVWScGjjGHSNwAY+QVvkYx2x1pZQfA6q8IWc45AGbDfN/HHhAXnXj/k4cPV1KBf3Hq+C5/YhuQrPAgMBAAECggEAUOG73HPE7htbRinF8PEyXiyMPGfrcKiIMHXysnJCb9BWvq03LEHFktcMqtrZiyuhlXJ2b/ejuPP1kG+usXHAgEkactQpB/2A6rhMrqaA82nQVZhpg5qOAb5sRMUfIxtOVqm9Ji8UP5NDhCscAtFiE9IBzTotMarQAYTAMdPbzYPjmBd1kIu7HaCVxCmc4OHT6CkvIFmLb1H+Uj6+ReRVClTN+jOlVK7r69owVQBnt0pXU/DA6Y8i7U5W1IfdRSrAsF8dzajG3HBabpNO6gTovIrPKArM3qhw1H50jF/gtxp5WPPBmfu4L0AdvIYYIOQXgRh7q8YItrHHYpUNA3pYAQKBgQDQqCuP914TmsrnmoGfxo0C7pi+/tNyqck50xmyYIjWLrDCSvjpawRQsfTIrd6BksiU2OHK3hyZQlJzXritoUrDkM+CjC32YCA/Exf/Th8YU2CUS58q6DBQOWL8MlxxOueqjo4CJfnKa/oirl33mp9lEVTMsnVaI5zPxIJAEOxNQQKBgQCtVUfYGb3hZnvA7sZ6YlruKpP3SUeWKT/biMqO1qFY2Bbmeab1l6lC0px/dymtTmBRvGGvSMjyt0HNsBqPZqmoRXI1ptgrRU35W1XPPGmRDj6dYJt/oY1qecQ3Pw/qIqq/2YQ0z4tNijxMYeHr3Y1lgKDSKTIcR3tWRVqUKkuEDwKBgD0scMkejvhUc5R/We2L+p+XcDTe5QNC//nyCCCh6LxSqAPukxdsBrHZ2phTUV7Pt/rbsycVA3FzXlTwulg9Z3v6orv1eSw4dnvHj0uMjdVF7JTNrvaXaEK88GZcJXoU+QKqFLxQgfF0wrsUP/xt2rl44VG6KGmq8zh+tmkuDPmBAoGADy+/obFvr4X5JchF34cqjF9yo2HqQb/Pdn6fsLAhZtVX/E2AUVg+Xjpxt8FLB4IscjzdGix4XrurqkfZq+LEzpd3EDhUDEaANG7KL+QNI8ZpugQy25Xd/Aw7ItQFou/yoeh+6tmcTOjlq5OP5ch9kP0aGg+98jhZGA/vhbfG8gUCgYA3eQ6/0l3o2tpz46L7xTPxsQHTzCSLZbGuh7Mr0/+4MYXIhHmQhf1L+AtckRpOki7O+u9vyoVU5rod8zc9/XP7fDDwHPuwYHuPLDQFtyyMbaiRTtX7S1URxKfoeJruhHVpBr3f4xtf7VfaqyJ70gyeVT4yFmJ2iRxLcQINJ4VGFA==";

	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArjutB3sSmZBYmkEGeA0fG/Y7gg3G17NOhYfQx0F1SsK5OWBHfsDc4hsnEMC9PxPOtXt3Tp+fAJYWGDubaz1UJqklW7bdc8Lv44vB+PWRdX5ehomxQO03sLNGrOi3QJrQUwQ9Qvt5CPbYNOEqq8HknoNx7TPrLaDu1ryLy1TU8GcsYpZXo4b4zXEloWPl+Tqz/1t1AafG+PsK/QxsmR7rJu9W10sq0SpMA462MZwGdeO4AW3tfDu8evbwAQZxhclfqIb0o9fRWNOJVsHqsGOrP16A5XC6dIzxdnXd74feFkkvvuOrsC/0wWCTLh1fPFkbYHn4p0ALyDaq2mYd0alHFQIDAQAB";

	// 请求支付宝的网关地址
	public static String URL = "https://openapi.alipay.com/gateway.do";

	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://app.zjzwly.com/dce-app/order/notify_url.do";
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
