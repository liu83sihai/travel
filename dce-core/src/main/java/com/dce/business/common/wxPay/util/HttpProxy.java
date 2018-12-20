package com.dce.business.common.wxPay.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class HttpProxy {
	
	// 代理服务器
    //final static String ProxyHost = "proxy.abuyun.com";
    //final static Integer ProxyPort = 9010;
	//abuyun代理隧道验证信息
	//final static String ProxyUser = "HQ6BS1IW4G53947P";
    //final static String ProxyPass = "A89F0C6B12A2AFE2";

    // IP切换协议头
    final static String ProxyHeadKey = "Proxy-Switch-Ip";
    final static String ProxyHeadVal = "yes";
    
    //代理验证权限头
    final static String ProxyAuthorization = "Proxy-Authorization";
    
    // 定义申请获得的appKey和appSecret
    final static String appkey = "98853966";
    final static String secret = "67e51113419e01171b5969024cd4a130";
    final static String proxyIP = "s2.proxy.mayidaili.com";
    final static int proxyPort = 8123;
 	
 	public static String getAuthHeader() {
 		
 		// 创建参数表
 		Map<String, String> paramMap = new HashMap<String, String>();
 		paramMap.put("app_key", appkey);
 		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		format.setTimeZone(TimeZone.getTimeZone("GMT+8"));// 使用中国时间，以免时区不同导致认证错误
 		paramMap.put("timestamp", format.format(new Date()));
 		
 		// 对参数名进行排序
 		String[] keyArray = paramMap.keySet().toArray(new String[0]);
 		Arrays.sort(keyArray);
 		
 		// 拼接有序的参数名-值串
 		StringBuilder stringBuilder = new StringBuilder();
 		stringBuilder.append(secret);
 		for (String key : keyArray) {
 			stringBuilder.append(key).append(paramMap.get(key));
 		}
 		
 		stringBuilder.append(secret);
 		String codes = stringBuilder.toString();
 		
 		// MD5编码并转为大写， 这里使用的是Apache codec
 		String sign = org.apache.commons.codec.digest.DigestUtils.md5Hex(codes).toUpperCase();
 		
 		paramMap.put("sign", sign);
 		
 		// 拼装请求头Proxy-Authorization的值，这里使用 guava 进行map的拼接
 		//String authHeader = "MYH-AUTH-MD5 " + Joiner.on('&').withKeyValueSeparator("=").join(paramMap);
 		String authHeader = "MYH-AUTH-MD5 " + joinMap(paramMap);
 		
 		return authHeader;
 	}

 	private static String joinMap(Map<String, String> paramMap){
 		StringBuilder sb = new StringBuilder();
 		for(Map.Entry<String, String> row : paramMap.entrySet()){
 			sb.append("&").append(row.getKey()).append("=").append(row.getValue());
 		}
 		return sb.toString().substring(1);
 	}
}
