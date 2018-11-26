package com.dce.business.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 利用HttpClient进行post请求的工具类
 * 
 * @ClassName: HttpClientUtil
 * @Description: TODO
 * @author lsk
 * 
 */
public class HttpClientUtil {

	private static String charSet = "UTF-8";

	public static String doPost(String url, Map<String, String> param, String sings) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = SSLClient.createSSLClientDefault();
			// 参数集合
			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			// 遍历参数并添加到集合
			for (Map.Entry<String, String> entry : param.entrySet()) {
				postParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			HttpEntity paramEntity = new UrlEncodedFormEntity(postParams, charSet);
			httpPost = new HttpPost(url);
			httpPost.setEntity(paramEntity);
			httpPost.addHeader("SINGS", sings);
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charSet);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
}