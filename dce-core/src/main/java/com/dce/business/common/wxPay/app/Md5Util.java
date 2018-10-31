package com.dce.business.common.wxPay.app;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Md5编码处理类，sign方法中加入了签名串排序
 * @author pm
 *
 */
public class Md5Util {

	private static Md5Util md5 = new Md5Util();

	public static Md5Util getInstance() {
		return md5;
	}
	
	/**
	 * 将map转换成待签名的字符串格式，然后MD5
	 * @param params 待签名的参数Map
	 * @param signKey 签名密钥
	 * @return 签名
	 */
	public static String signMap(Map<String, ?> params, String signKey) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < keys.size(); ++i) {
			String key = (String) keys.get(i);
            if ("sign".equals(key)) {
                continue;
            }
            Object value = params.get(key);
            String strValue = String.valueOf(value);
            if (null != value && !"".equals(strValue)) {
                content.append((i == 0 ? "" : "&") + key + "=" + strValue);
            }
		}
		return sign(content.toString(), signKey);
	}

	/**
	 * 签名字符串
	 * @param text 待签名字符串
	 * @param signKey 签名密钥 
	 * @return 签名
	 */
	private static String sign(String text, String signKey) {
		String signcontent = text +"&key="+signKey;
		try {
			return DigestUtils.md5Hex(signcontent.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
		    throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:UTF-8");
		}
	}

	public static boolean verify(Map<String, ?> map, String key, String sign) {
		String mySign = signMap(map, key);
    	return mySign.equalsIgnoreCase(sign);
    }
	
}
