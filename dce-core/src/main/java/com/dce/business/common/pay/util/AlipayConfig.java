package com.dce.business.common.pay.util;

/**
 * 支付宝公用参数
 * 
 * @author Administrator
 *
 */
public class AlipayConfig {

	public static String APPID = "2016091700534967";
	
	// 合作身份者ID
	public static String partner = "2088231284681737";

	// 私钥 pkcs8格式
	public static String RSA_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCzJbXi550b2uWWSGyu3YLRsTVBMe6yduFsdUcptmDnZqhn4uFQRfZ7rqOwyCDHpbkgiZqPt0Medz05MzlHWkS8rM7P2OxHHyKzJ/VG85GGkMOauzUQlD9ZRPbbY4y7Z4puTmpIuP7Izbad6kAsYcAL6RoOX0B5dAoPnLnDWJENvIcA+4Q3UIMYRsjuSn/2Wf1o7PxF20AmXUxU0Z1nBW/5TT1br6dAP1U+1tpdI7M+iU/rg5CKWMjJEE7gSCLBGhD/21Ho7p0pZtpDac6cK1O+bOspT3pfqCELvZ4GNIgeKszlqtaMCTbuApJNvwxAB3GyrAf6b9kkVpGkWNd4MD/NAgMBAAECggEBAIS5SZoEEsfSUwoWOj8LaaJFcbkNb1TU8onSEZj7v6twyQ++bUN1sflMF4YfnvmK9C8BA3u6QIXPvZIlOyh73jUSql4ezl6a2LB2yCd6yLQziFeYnuXbj1tp1qIPjPJC8bJrch7SwCCxF7zDEixPd2LbnixLsY7Jw7JxYMczVLQhWx8E6e0WPQ7TWBGBqIOyzH98dblnbZYKpYx98+Wolrg4v5WnQi8bcjqLKt55Ih4bMUsLVsNBukUd+3xGYUEufpHDsjASwQwzF+jgznMUHh1UpoLm1LeMsRK9Ww0v9kjgcKOls3c0vXzLlVl+E6MSlFnNSWuXkdQmaiw3C8aFywECgYEA8XtAzfM8H6Ufse3I/sk9g1bdF2EZRn7WtQ2YqkemmTqjz7ZkVJE4M9kpafrlRE9ireYJalq3iPzWXlHrOczjku/u1KEVDg2pCMvAfx4ClxDIjzQ/HHyDiQGSjMGKPM3Ynu/IDWAS1KZA+beZtB7uQM3cLPUYHTpmiIFwIBXwe6ECgYEAvesLjiG+j/Gl3gCB36F9MbWkGN7TS5zU+UmhHFr6gxkOWDdZGHaKWv5p9FBL3U/yXVymJM0wqcUo7W7185M2ClHBK8JAMp/AypTre9MNuZ+HYerkOkD8VHh6D5QCvNC2btqNMnKk66F/rcFPNlu3eVCH1msrkHLvvR1K7qGQNK0CgYAU7cnAD8S2OWl10lYJPwfFylF/JTs7kOKq7nK8z1jxDtQDvJsVeUCVvcyJEuWr9fVnfPhz70jY5pDtu8vXRpqysXjRlORuQ2DTElWsrr0qupmnT6/omCIOTcSVznBctLqBi+aawijfpUkcW31Gzyd3FmBuxQ9B+T3Jtu4w2bXuAQKBgQC8n29wkK81Q6mbXiGfPEKdVmxOjzCITpFvreMnyGw6PG50yzPgCgvL6BbPodhXTAnJFDWnI2wLkVfeyW/ZpQHtKdmTXnBoVyYhjdp7tthQ3dKWO7bE7LowIyAHzNcTNK+cVdAapKaLqHCo3++Oa3VENelT2kkAREc7Kpa7fxc8WQKBgQCQW/omM2IpwUEutarDbTTk0IQd0LjFv8uvtElExWpy3+KYG5UgQjxWKIc1Px0D4/yNa3otpNSpzHeTsmMpyEFh0+loklnxKSaMEVVfBiEIEQOK7/rnVHPm/BzKoM/8hbJ/WrS4am72TWq6TnwxJVOOAX2dZd336gZQrwTHEd3fdA==";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsAErTrxD/Sf/5hJ+vkDgp0ohJ/9/u4VuEsDIA2kmoAJv8BqsPlCDxAdlPHzb9ZXoTHKjP5UOJHOt7Wj+y8R5NxOKayaHiUhdEuvi3Jj3jOdfYLmvzSU0QxLCYCkwuKB56LoykSZK2ZjWmSEf6QWNlVwCvLK2BcWqGhGCauEhQIMGnVnAMsnNczPPpCu1soI4jJo2XdyS6gp94m2W7u7ZyKWFIijpgBNxqYESFz8rEUaCRJUdB8g3T1YeDSc8yi+eIof15o0h8mqSgWZ8bIMxenehVk8XlDf9Xdqvlm1tItPi6jr4bD0/Oxy6h7U3heKrKnBN0/8TMYS4nxg1ejHekwIDAQAB";

	// 请求支付宝的网关地址
	public static String URL = "https://openapi.alipaydev.com/gateway.do";

	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://www.xxx.com/alipay/notify_url.do";

	// 编码
	public static String CHARSET = "UTF-8";

	// 签名方式
	public static String SIGNTYPE = "RSA2";
	
}
