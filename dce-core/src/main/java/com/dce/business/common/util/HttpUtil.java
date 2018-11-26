package com.dce.business.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

public class HttpUtil {
	private final static Logger logger = Logger.getLogger(HttpUtil.class);
	private final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	public static Map<String, Object> get(String url) {
		logger.info("http请求，url:" + url);
		Long time = System.currentTimeMillis();
		CloseableHttpResponse response = null;
		BufferedReader rd = null;
		try (CloseableHttpClient httpclient = HttpClients.createDefault();) {
			HttpGet httpGet = new HttpGet(url);
			response = httpclient.execute(httpGet);
			InputStream inputStream = response.getEntity().getContent();
			rd = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String line = "";
			StringBuffer result = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			logger.info("http请求，url:" + url + "; 耗时ms" + (System.currentTimeMillis() - time) + "; response:" + result);
			Map<String, Object> map = JSONObject.parseObject(result.toString(),
					new TypeReference<Map<String, Object>>() {
					});
			return map;
		} catch (Exception e) {
			logger.error("http请求失败，url:" + url, e);
		} finally {
			if (response != null) {
				try {

					response.close();
				} catch (Exception e) {
					logger.error(e);
				}
			}
			if (rd != null) {
				try {

					rd.close();
				} catch (Exception e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	public static Map<String, Object> post(String url, JSONObject jsonObject) {
		logger.info("http请求，url:" + url + "; params:" + jsonObject.toJSONString());
		Long time = System.currentTimeMillis();
		CloseableHttpResponse response = null;
		try (CloseableHttpClient httpclient = HttpClients.createDefault();) {
			HttpPost httpPost = new HttpPost(url);
			StringEntity se = new StringEntity(jsonObject.toJSONString());
			se.setContentType("application/json;charset=UTF-8");

			httpPost.setEntity(se);

			response = httpclient.execute(httpPost);
			HttpEntity entity = null;
			entity = response.getEntity();
			String result = EntityUtils.toString(entity, "UTF-8");

			logger.info("http请求，url:" + url + "; 耗时ms" + (System.currentTimeMillis() - time) + "; response:" + result);

			Map<String, Object> map = JSONObject.parseObject(result, new TypeReference<Map<String, Object>>() {
			});
			return map;
		} catch (Exception e) {
			logger.error("http请求失败，url:" + url, e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	public static String httpPost(String url, Map<String, String> param) throws Throwable {

		logger.info("http请求，url:" + url + "; params:" + param);
		Long time = System.currentTimeMillis();
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			for (String key : param.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(key, param.get(key)));
			}

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, "UTF-8");

			logger.info("http请求，url:" + url + "; 耗时ms" + (System.currentTimeMillis() - time) + "; response:" + result);

			// Map<String, Object> map = JSONObject.parseObject(result, new
			// TypeReference<Map<String, Object>>() {
			// });
			return result;
		} catch (Exception e) {
			logger.error("http请求失败，url:" + url, e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}
	
	public static String httpPostAndHeader(String url, Map<String, String> param,Map<String,String> headerMap) throws Throwable {

		logger.info("http请求，url:" + url + "; params:" + param);
		Long time = System.currentTimeMillis();
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			
			for (String key : headerMap.keySet()) {
				httpPost.setHeader(key, headerMap.get(key));
			}
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			for (String key : param.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(key, param.get(key)));
			}

			
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, "UTF-8");

			logger.info("http请求，url:" + url + "; 耗时ms" + (System.currentTimeMillis() - time) + "; response:" + result);

			// Map<String, Object> map = JSONObject.parseObject(result, new
			// TypeReference<Map<String, Object>>() {
			// });
			return result;
		} catch (Exception e) {
			logger.error("http请求失败，url:" + url, e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}
	

	public static String httpCertPost(String url, Map<String, String> param, String signs, String certFile,
			String certPassord) throws Throwable {

		// 加载证书
		// KeyStore keyStore = KeyStore.getInstance("PKCS12");
		// keyStore.load(new FileInputStream(new File("certFile")),
		// certPassord.toCharArray());
		// SSLContext sslcontext = SSLContexts.custom().
		// //忽略掉对服务器端证书的校验
		// .loadTrustMaterial(new TrustStrategy() {
		// @Override
		// public boolean isTrusted(X509Certificate[] chain, String authType)
		// throws CertificateException {
		// return true;
		// }
		// })
		//
		// //加载服务端提供的truststore(如果服务器提供truststore的话就不用忽略对服务器端证书的校验了)
		// //.loadTrustMaterial(new File("D:\\truststore.jks"),
		// "123456".toCharArray(),
		// // new TrustSelfSignedStrategy())
		// .loadKeyMaterial(keyStore, "cmcc".toCharArray())
		// .build();
		// SSLConnectionSocketFactory sslConnectionSocketFactory = new
		// SSLConnectionSocketFactory(
		// sslcontext,
		// new String[]{"TLSv1"},
		// null,
		// SSLConnectionSocketFactory.getDefaultHostnameVerifier());
		// CloseableHttpClient httpclient = HttpClients.custom()
		// .setSSLSocketFactory(sslConnectionSocketFactory)
		// .build();
		logger.info("http请求，url:" + url + "; params:" + param);
		Long time = System.currentTimeMillis();
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			for (String key : param.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(key, param.get(key)));
			}

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
			httpPost.setHeader("SINGS", signs);

			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity, "UTF-8");

			logger.info("http请求，url:" + url + "; 耗时ms" + (System.currentTimeMillis() - time) + "; response:" + result);

			// Map<String, Object> map = JSONObject.parseObject(result, new
			// TypeReference<Map<String, Object>>() {
			// });
			return result;
		} catch (Exception e) {
			logger.error("http请求失败，url:" + url, e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	public static String httpCertPostNotSSl(String url, Map<String, String> param) throws Throwable {
		String result = "";
		DataOutputStream out = null;
		BufferedReader in = null;
		HttpURLConnection conn;
		try {
			trustAllHosts();
			URL realUrl = new URL(url);
			// 通过请求地址判断请求类型(http或者是https)
			if (realUrl.getProtocol().toLowerCase().equals("https")) {
				HttpsURLConnection https = (HttpsURLConnection) realUrl.openConnection();
				https.setHostnameVerifier(DO_NOT_VERIFY);
				conn = https;
			} else {
				conn = (HttpURLConnection) realUrl.openConnection();
			}
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "text/plain;charset=utf-8");
			conn.setRequestProperty("SINGS", param.get("SINGS"));
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
		    //设置是否使用缓存，post请求不使用缓存
			conn.setUseCaches(false);
			// 获取URLConnection对象对应的输出流
//			out = new PrintWriter(conn.getOutputStream());
			conn.connect();   
			out = new DataOutputStream(conn.getOutputStream());
			// 发送请求参数
//			out.print(paramUrl);
			StringBuffer sBuffer = new StringBuffer();
			for (String key : param.keySet()) {
				if("SINGS" != key){
					sBuffer.append(key + "=" + param.get(key) + "&");
				}
			}
			byte[] contxt = sBuffer.toString().getBytes("UTF-8");
			out.write(contxt);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {// 使用finally块来关闭输出流、输入流
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	private static void trustAllHosts() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}

			public void checkClientTrusted(X509Certificate[] chain, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) {
			}
		} };
		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userId", 11111);
		jsonObject.put("password", "aaddddddda");
		// post(url, jsonObject);
	}

}
