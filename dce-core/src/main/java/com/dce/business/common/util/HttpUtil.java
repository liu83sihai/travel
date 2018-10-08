package com.dce.business.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
            Map<String, Object> map = JSONObject.parseObject(result.toString(), new TypeReference<Map<String, Object>>() {
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
    
    
    public static String  httpPost(String url,Map<String, String> param)throws Throwable {

		logger.info("http请求，url:" + url + "; params:" + param);
		Long time = System.currentTimeMillis();
		CloseableHttpResponse response = null;
		try{
			CloseableHttpClient httpclient = HttpClients.createDefault();
		    HttpPost httpPost = new HttpPost(url);
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		    for (String key : param.keySet()) {
		    	nameValuePairs.add(new BasicNameValuePair(key, param.get(key)));
		    }

		    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
		    response = httpclient.execute(httpPost);
		    HttpEntity entity =  response.getEntity();
		    String result = EntityUtils.toString(entity, "UTF-8");
		
		    logger.info("http请求，url:" + url + "; 耗时ms" + (System.currentTimeMillis() - time) + "; response:" + result);
		
//		    Map<String, Object> map = JSONObject.parseObject(result, new TypeReference<Map<String, Object>>() {
//		    });
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

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", 11111);
        jsonObject.put("password", "aaddddddda");
        //post(url, jsonObject);
    }

}
