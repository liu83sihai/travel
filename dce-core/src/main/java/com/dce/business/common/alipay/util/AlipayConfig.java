package com.dce.business.common.alipay.util;

/**
 * 支付宝公用参数
 * 
 * @author Administrator
 *
 */
public class AlipayConfig {

	//public static String APPID = "2016091700534967";
	public static String APPID = "2019053165358902";
	
	// 合作身份者ID
	//public static String partner = "2088231284681737";
	public static String seller_id = "2088531126130080";

	// 私钥 pkcs8格式
	//public static String RSA_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCzJbXi550b2uWWSGyu3YLRsTVBMe6yduFsdUcptmDnZqhn4uFQRfZ7rqOwyCDHpbkgiZqPt0Medz05MzlHWkS8rM7P2OxHHyKzJ/VG85GGkMOauzUQlD9ZRPbbY4y7Z4puTmpIuP7Izbad6kAsYcAL6RoOX0B5dAoPnLnDWJENvIcA+4Q3UIMYRsjuSn/2Wf1o7PxF20AmXUxU0Z1nBW/5TT1br6dAP1U+1tpdI7M+iU/rg5CKWMjJEE7gSCLBGhD/21Ho7p0pZtpDac6cK1O+bOspT3pfqCELvZ4GNIgeKszlqtaMCTbuApJNvwxAB3GyrAf6b9kkVpGkWNd4MD/NAgMBAAECggEBAIS5SZoEEsfSUwoWOj8LaaJFcbkNb1TU8onSEZj7v6twyQ++bUN1sflMF4YfnvmK9C8BA3u6QIXPvZIlOyh73jUSql4ezl6a2LB2yCd6yLQziFeYnuXbj1tp1qIPjPJC8bJrch7SwCCxF7zDEixPd2LbnixLsY7Jw7JxYMczVLQhWx8E6e0WPQ7TWBGBqIOyzH98dblnbZYKpYx98+Wolrg4v5WnQi8bcjqLKt55Ih4bMUsLVsNBukUd+3xGYUEufpHDsjASwQwzF+jgznMUHh1UpoLm1LeMsRK9Ww0v9kjgcKOls3c0vXzLlVl+E6MSlFnNSWuXkdQmaiw3C8aFywECgYEA8XtAzfM8H6Ufse3I/sk9g1bdF2EZRn7WtQ2YqkemmTqjz7ZkVJE4M9kpafrlRE9ireYJalq3iPzWXlHrOczjku/u1KEVDg2pCMvAfx4ClxDIjzQ/HHyDiQGSjMGKPM3Ynu/IDWAS1KZA+beZtB7uQM3cLPUYHTpmiIFwIBXwe6ECgYEAvesLjiG+j/Gl3gCB36F9MbWkGN7TS5zU+UmhHFr6gxkOWDdZGHaKWv5p9FBL3U/yXVymJM0wqcUo7W7185M2ClHBK8JAMp/AypTre9MNuZ+HYerkOkD8VHh6D5QCvNC2btqNMnKk66F/rcFPNlu3eVCH1msrkHLvvR1K7qGQNK0CgYAU7cnAD8S2OWl10lYJPwfFylF/JTs7kOKq7nK8z1jxDtQDvJsVeUCVvcyJEuWr9fVnfPhz70jY5pDtu8vXRpqysXjRlORuQ2DTElWsrr0qupmnT6/omCIOTcSVznBctLqBi+aawijfpUkcW31Gzyd3FmBuxQ9B+T3Jtu4w2bXuAQKBgQC8n29wkK81Q6mbXiGfPEKdVmxOjzCITpFvreMnyGw6PG50yzPgCgvL6BbPodhXTAnJFDWnI2wLkVfeyW/ZpQHtKdmTXnBoVyYhjdp7tthQ3dKWO7bE7LowIyAHzNcTNK+cVdAapKaLqHCo3++Oa3VENelT2kkAREc7Kpa7fxc8WQKBgQCQW/omM2IpwUEutarDbTTk0IQd0LjFv8uvtElExWpy3+KYG5UgQjxWKIc1Px0D4/yNa3otpNSpzHeTsmMpyEFh0+loklnxKSaMEVVfBiEIEQOK7/rnVHPm/BzKoM/8hbJ/WrS4am72TWq6TnwxJVOOAX2dZd336gZQrwTHEd3fdA==";
	public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC7Y73D+0uMgorVnqT3pXKodGIv+/sEKj9KLdXU0jmXO8NTKcTdqNZrRrzMMvFOv2CQ6vfERSuhCrG4PJlVvsmOPLbzroJwIX90J4H76njfQGfolv11V3Q0DfaBr80mATl2fuJhPyF5SMQZro6GA+Ln2Dn28Wx0SozoYNFpFpqYXIJZPHmZrngYJJudxIdVSMLwARtChYFe6XvYUcxju3DfvtLdJTsMgJcvjEXwy9oDyPOs1vUr6IGAGJ4bThSXmYbcIYE88Wo+rBlMOGRpNsit4VDf+NmJZ8SoLADhyZ/JZwy7BzNMfFm2mcSKKzngOfCA6JUUYWmnVa7z1Dxdvo5dAgMBAAECggEAfDGHtJsZIB/oudmyBfueOS0hYiID5GfHmHc2UB2gU64lLznwouPicCjquf9NVN6btpAz893QI6R90tg+28hdtypgKrLO+lgJK8d/a6GjyHrcIxfnFd0HQ/S5GoyUT4riNbm0ebUoj0R9y3OYPBPZT8VL7XFmatB6AQ0h3R9BCJasaYMnp5kNy3WMYmQh0WNlmcsYbdSxv3ckMyYPFMgNC7XOzR8t16rSH/Q9q8nyozznO3ATfqgV5f7iypQNxgohcNPaYEiy0cXM0kzFKooUYQyEZQvSxX7/jgWwM2VBVmK9JBb7d6oBp50Y8QVuR3c7HZyA8f02wvJAvhGWEOdlwQKBgQDohSx3dhG5Ud0enez0M9nebjJbsISA88jTBNOBvhTaVelT2MdYwP+n3ZIfukrdMWb4mj4ne2K2fMY8jHbp+MRWUc17a3ZEOYeMvaeg7WIuHrB7B4+X4G7THWdFCId/+/cXeGdEVd1FooI7ven1mQlOObh/K/TA2sitZdAG6rq+8QKBgQDOT+guukdjUQBJEaN1vyJQTgIbLFcE6J1Pj9YMxLFke7UNpiRChmQoBUEqqaAEKhqUZ8j07TPS4v7HItARdX/qEtQQ08R2MQlf9Rp7mCwxZPS4oEtdJy3aOc8Whnhrim94vdjXYNH1UhXGVEDw1tmT/gYNmz/O/AOWKSceCrbeLQKBgQDKuYmKYhuT2qcSEZjNVQPIJYWsYVs1Xtch/Bw4CzT9I1g6LU+otyRvBARpuy+YaIEhkMFOBQqMln7zvXfUCHuN0uZ3i7riQtJGx/gFm9I06bbunSn5vciCMimei5Av6suE4AoRD1fLZaNsWOlJovL1C+gqoHVVS6sZWyIjVbYZ0QKBgGqDcKCnRXHuANK4i4afzixdidLTmn2LRqQr41umNNtmzrukOfYqggzzYYiQGG/t+NkDmAlzaR0IWnce7wMEgaysWdXyLuzCNk/XL/v9PVum/n1PLHmEX7TZwaOkewkyaIiSox/W+a7I2TsShpCQq7YvIJrnuxK2o3dj+bEV4QFlAoGBAIzzLT32GfzXhUuckVUR44VDUmWdhCDTrUF41OkqOyHeLOSZoSRkH0YmbFNjfIxCEGScEPh3yPjqKAz4xDLNF6TznbB+sQZ78IHp7ocghcR3VyAbNE2tSt/WAXQTWmpidNBalBLGTCgKiTeBaHu8xkMm9k8sBRHy4/nf5t4LH4eY";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	//public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsAErTrxD/Sf/5hJ+vkDgp0ohJ/9/u4VuEsDIA2kmoAJv8BqsPlCDxAdlPHzb9ZXoTHKjP5UOJHOt7Wj+y8R5NxOKayaHiUhdEuvi3Jj3jOdfYLmvzSU0QxLCYCkwuKB56LoykSZK2ZjWmSEf6QWNlVwCvLK2BcWqGhGCauEhQIMGnVnAMsnNczPPpCu1soI4jJo2XdyS6gp94m2W7u7ZyKWFIijpgBNxqYESFz8rEUaCRJUdB8g3T1YeDSc8yi+eIof15o0h8mqSgWZ8bIMxenehVk8XlDf9Xdqvlm1tItPi6jr4bD0/Oxy6h7U3heKrKnBN0/8TMYS4nxg1ejHekwIDAQAB";
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuVt/Q2iOC+qKLIyvtNKNwtPmzmwVmpVYULmLhoIANNA0GwMiJXoV0cLvnsKsXcnqh7hzVpQpUGM3GCko3e1GcGaGlcTJSYaND9/GeQyB1gn0zbhcf0M0d8YnK9q19saoAoZqfVN8fOAjquAvQEhs1kvZVysSLJfLdU21nD9C9oqrAJH1rDhWgRZJxjnmFKJ7buDkhS3vF2yjDdmRrLUx4v+WsCbzsDN7wvBwgNJCW6F0rIew7BVxCD5aGq+36rolSMrFnYHUltl2cT6uHPTSHmtHzqVgiteMAaX4c69r28JMnHkSgkWv2GY/2Xq4+6JdpvMlANFuvzoGmL1GIistEQIDAQAB";

	// 请求支付宝的网关地址
	public static String URL = "https://openapi.alipay.com/gateway.do";

	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://app.zjzwly.com/dce-app/order/notifyUrl.do";

	// 编码
	public static String CHARSET = "UTF-8";

	// 签名方式
	public static String SIGNTYPE = "RSA2";
	
	//交易的超时时间	取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）
	public static String timeoutExpress = "30m";
	
}
