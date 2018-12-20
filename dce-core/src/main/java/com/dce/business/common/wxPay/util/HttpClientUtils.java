package com.dce.business.common.wxPay.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.exception.BusinessException;

public class HttpClientUtils {
	
	private static final Logger logger = Logger.getLogger(HttpClientUtils.class);

	private static final String parameterJoin = "&";
	private static final String parameterEqual = "=";

	/**
	 * 
	 * 将map的key和value以 ?key=value的方式拼成url
	 *
	 * zhangyunhmf
	 *
	 */
	public static String makeUrl(String baseUrl,Map<String, String> parameterMap) {
		
		StringBuffer parasb = new StringBuffer(baseUrl);
		if (null != parameterMap) {
			parasb.append("?");
			for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
				parasb.append(entry.getKey()).append(parameterEqual)
						.append(entry.getValue()).append(parameterJoin);

			}
		}
		return parasb.substring(0, parasb.length() - 1);
	}

	public static String appendParameter(String baseUrl,
			Map<String, String> parameterMap) {
		StringBuffer parasb = new StringBuffer(baseUrl);
		if (null != parameterMap) {
			parasb.append(parameterJoin);
			for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
				String val = "";
				try {
					if (entry.getValue() != null) {
						val = URLEncoder.encode(entry.getValue(), "UTF-8");
					}
				} catch (UnsupportedEncodingException e) {
					logger.error("error: ", e);
					val = entry.getValue();
				}
				parasb.append(entry.getKey()).append(parameterEqual)
						.append(val).append(parameterJoin);

			}
		}
		return parasb.substring(0, parasb.length() - 1);
	}

	

	public static String get(String url) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		logger.info("调用url：" + url);// http://lcdev.hehenian.cn/loan.do?loanDate=2015/06/08&sign=9991b60c7dd3407108a22638f56190a4
		try {
			HttpGet httpGet = new HttpGet(url);
			response = httpclient.execute(httpGet);
			InputStream inputStream = response.getEntity().getContent();
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    inputStream, "UTF-8"));
			String line = "";
			StringBuffer resultString = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				resultString.append(line);
			}

			logger.info(url + "回复:" + resultString.toString());

			return resultString.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("发送请求错误:" + e.getMessage());
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception ignore) {
			}
		}
	}

    public static String get(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        BufferedReader reader = null;
        try {
            String newUrl = appendParameter(url, params);
            logger.info("newUrl=" + newUrl);
            HttpGet httpGet = new HttpGet(newUrl);
            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    httpGet.addHeader(header.getKey(), header.getValue());
                }
            }

            httpclient = HttpClients.createDefault();
            response = httpclient.execute(httpGet);
            InputStream inputStream = response.getEntity().getContent();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            StringBuffer resultString = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                resultString.append(line);
            }

            logger.info(url + " 回复:" + resultString.toString());

            return resultString.toString();
        } catch (Exception e) {
            logger.error("get 请求异常", e);
            throw new BusinessException("发送请求错误:" + e.getMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpclient != null) {
                    httpclient.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ignore) {
                //do nothing
            }
        }
    }

    public static String get(String url, Map<String, String> params) throws Exception {
        return get(url, params, null);
    }

	public static CloseableHttpResponse getResponse2postUrl(String url, Map<String, String> params) throws Exception {

		logger.debug("调用参数：" + params);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			for (String key : params.keySet()) {
				nameValuePairs
						.add(new BasicNameValuePair(key, params.get(key)));
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

			response = httpclient.execute(httpPost);

			int statusCode = response.getStatusLine().getStatusCode();
			if (301 == statusCode || 302 == statusCode) {
				String newUrl = response.getFirstHeader("Location").getValue();
				Header[] cookies = response.getHeaders("Set-Cookie");
				httpPost = new HttpPost(newUrl);
				for (Header h : cookies) {
					httpPost.addHeader(h);
				}
				response.close();
				response = httpclient.execute(httpPost);
			}
			return response;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("发送请求错误:" + e.getMessage());
		} finally {
			// try {
			// response.close();
			// httpclient.close();
			// } catch (Exception ignore) {
			// }
		}
	}

	public static CloseableHttpResponse getResponseUrl(String url, Map<String, String> params) throws Exception {
		logger.debug("调用参数：" + params);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			for (String key : params.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

			response = httpclient.execute(httpPost);
			return response;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("发送请求错误:" + e.getMessage());
		} finally {
			// try {
			// response.close();
			// httpclient.close();
			// } catch (Exception ignore) {
			// }
		}
	}
	
    public static String post(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        BufferedReader reader = null;
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            for (String key : params.keySet()) {
                nameValuePairs.add(new BasicNameValuePair(key, params.get(key)));
            }
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

            if (headers != null) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    httpPost.addHeader(header.getKey(), header.getValue());
                }
            }

            logger.info("url=" + httpPost.getURI());
            logger.info("params:" + JSON.toJSONString(params));
            long time = System.currentTimeMillis();

            httpclient = HttpClients.createDefault();
            response = httpclient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            if (301 == statusCode || 302 == statusCode) {
                String newUrl = response.getFirstHeader("Location").getValue();
                Header[] cookies = response.getHeaders("Set-Cookie");
                httpPost = new HttpPost(newUrl);
                for (Header h : cookies) {
                    httpPost.addHeader(h);
                }
                response.close();
                response = httpclient.execute(httpPost);
            }

            InputStream inputStream = response.getEntity().getContent();
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line = "";
            StringBuffer resultString = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                resultString.append(line);
            }
            logger.info("post请求url:" + url + ",  回复:" + URLDecoder.decode(resultString.toString(), "UTF-8"));
            logger.info("请求耗时ms：" + (System.currentTimeMillis() - time));
            return resultString.toString();
        } catch (Exception e) {
            logger.error("post 请求异常：", e);
            throw new BusinessException("发送请求错误:" + e.getMessage());
        } finally {
            try {
                response.close();
                if (response != null) {
                    response.close();
                }
                if (httpclient != null) {
                    httpclient.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ignore) {
                //do nothing
            }
        }
    }

	public static String post(String url, Map<String, String> params) throws Exception {
	    return post(url, params, null);
	}


    /**
	 * 上标
	 */
	public static String  sbPost(String url, Map<String, String> params)
			throws Exception {
		logger.debug("调用参数：" + params);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			for (String key : params.keySet()) {
				nameValuePairs
						.add(new BasicNameValuePair(key, params.get(key)));
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

			response = httpclient.execute(httpPost);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					inputStream));

			String line = "";
			StringBuffer resultString = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				resultString.append(line);
			}
			try {
				logger.debug("上标回复:"
						+ URLDecoder.decode(resultString.toString(), "UTF-8"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			return resultString.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("发送请求错误:" + e.getMessage());
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception ignore) {
			}
		}
	}

	/**
	 * 生成PDF协议
	 * 
	 * @auther liminglmf
	 * @date 2015年7月3日
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getCallBack(String url, Map<String, String> params)
			throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		logger.info("调用url：" + url);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			for (String key : params.keySet()) {
				nameValuePairs
						.add(new BasicNameValuePair(key, params.get(key)));
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
			response = httpclient.execute(httpPost);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					inputStream));

			String line = "";
			StringBuffer resultString = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				resultString.append(line);
			}
			logger.info(url + "返回:" + resultString.toString());
			return resultString.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("发送请求错误:" + e.getMessage());
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception ignore) {
			}
		}
	}

	public static String genNewUrl(String url, Map<String, String> params) {
		StringBuffer parasb = new StringBuffer(url);
		if (null != params) {
			parasb.append("?");
			for (Map.Entry<String, String> entry : params.entrySet()) {
				String val = "";
				try {
					if (entry.getValue() != null) {
						val = URLEncoder.encode(entry.getValue(), "UTF-8");
					}
				} catch (UnsupportedEncodingException e) {
					logger.error("error: ", e);
					val = entry.getValue();
				}
				parasb.append(entry.getKey()).append(parameterEqual)
						.append(val).append(parameterJoin);

			}
		}
		return parasb.substring(0, parasb.length() - 1);
	}

	/**
	 * 
	 * @auther liminglmf
	 * @date 2015年7月3日
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String commonPost(String url, Map<String, String> params)
			throws Exception {
		logger.debug("请求URL:>>>" + url + "调用参数：>>>>" + params);

		CloseableHttpClient httpclient = null;
		// 创建httpclient
		try {
			if (url.startsWith("https://")) {
				httpclient = HttpClientBuilder.initHttpClient();
			} else {
				httpclient = HttpClients.custom().build();
			}
		} catch (KeyManagementException e) {
			logger.error("证书异常", e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("证书异常", e);
		} catch (Exception e) {
			logger.error("创建Httpclient异常 如何是https请查看证书是否异常url:" + url, e);
		}

		CloseableHttpResponse response = null;
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			for (String key : params.keySet()) {
				nameValuePairs
						.add(new BasicNameValuePair(key, params.get(key)));
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

			response = httpclient.execute(httpPost);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					inputStream));

			String line = "";
			StringBuffer resultString = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				resultString.append(line);
			}
			try {
				logger.debug("请求" + url + "回复>>>>>:"
						+ URLDecoder.decode(resultString.toString(), "UTF-8"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			return resultString.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("发送请求错误:" + e.getMessage());
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception ignore) {
			}
		}
	}


	public static JSONObject httpsGetRequest(String requestUrl) {
		JSONObject jsonObject = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		try {
			httpClient = HttpClientBuilder.initHttpClient();
			HttpGet get = new HttpGet();
			get.setURI(new URI(requestUrl));
			response = httpClient.execute(get);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					inputStream));

			String line = "";
			StringBuffer resultString = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				resultString.append(line);
			}
			try {
				logger.info("请求" + requestUrl + "回复>>>>>:"
						+ URLDecoder.decode(resultString.toString(), "UTF-8"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			jsonObject = JSONObject.parseObject(resultString.toString());
			return jsonObject;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("发送请求错误:" + e.getMessage());
		} finally {
			try {
				if (null != response) {
					response.close();
				}
				if(null != httpClient){
					httpClient.close();
				}
			} catch (IOException e) {
				logger.error("error: ", e);
			}
		}
	}

	public static InputStream getPDFInputStream(String url,
			Map<String, String> params) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		logger.debug("调用url：" + url);
		try {
			String newUrl = genNewUrl(url, params);
			HttpGet httpGet = new HttpGet(newUrl);
			response = httpclient.execute(httpGet);
			InputStream inputStream = response.getEntity().getContent();
			return inputStream;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("发送请求错误:" + e.getMessage());
		}
		// finally {
		// try {
		// response.close();
		// httpclient.close();
		// } catch (Exception ignore) {
		// }
		// }
	}

	/**
	 * 调用电子签章PDF协议
	 * 
	 * @date 2016年8月28日
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getEsignCallBack(String url, Map<String, String> params)
			throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		logger.info("调用url：" + url);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			for (String key : params.keySet()) {
				nameValuePairs.add(new BasicNameValuePair(key, (String) params
						.get(key)));
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
			response = httpclient.execute(httpPost);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					inputStream));

			String line = "";
			StringBuffer resultString = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				resultString.append(line);
			}
			logger.info(url + "返回:" + resultString.toString());
			return resultString.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error("发送请求错误:" + e.getMessage());
			throw e;
		} finally {
			try {
				response.close();
				httpclient.close();
			} catch (Exception ignore) {
			}
		}

	}

	/**
	 * 发送post请求
     * @param url 请求路径
     * @param param 请求json数据
     * @return
	 */
	public static String doPost(String url, JSONObject param){
		HttpPost httpPost = null;
		String result = null;
		try{
		    logger.info("post url:" + url + "; param:" + param);
			CloseableHttpClient client = HttpClients.createDefault();
			httpPost = new HttpPost(url);
			if(param != null){
				StringEntity se = new StringEntity(param.toString(),"utf-8");
				//se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json; charset="));
				httpPost.setEntity(se); //post方法中，加入json数据
				httpPost.setHeader("Content-Type","application/json");
			}

			HttpResponse response = client.execute(httpPost);
			if(response != null){
				HttpEntity resEntity = response.getEntity();
				if(resEntity != null){
					result = EntityUtils.toString(resEntity,"utf-8");
				}
			}
			 logger.info("post url:" + url + "; response:" + result);
		}catch(Exception e){
		    logger.error("post异常，url:" + url, e);
		}
		//System.out.println("返回结果:\n"+result);
		return result;
	}

	public static void main(String[] args) {
		try {
			//Map<String, String> params = new HashMap<String, String>();
			JSONObject jo = new JSONObject();
			String url="https://apist.baiqishi.com/services/decision";
			jo.put("partnerId","fansfinancial");
			jo.put("verifyKey","76fa57c59a31449089e28c6a79ef9058");
			jo.put("appId","test");
			jo.put("eventType","register");
			jo.put("certNo","130625197602135713");
			jo.put("name","谢磊");

			String jsonData= HttpClientUtils.doPost(url,jo);
			System.out.println(jsonData);
			JSONObject jsonObject = JSONObject.parseObject(jsonData);
			JSONArray jsonArray = jsonObject.getJSONArray("strategySet");
			for(int i = 0; i< jsonArray.size(); i++){ //hitRules
				JSONObject	jsonObj = jsonArray.getJSONObject(i);
				JSONArray ja = jsonObj.getJSONArray("hitRules");
				for (int j = 0; j<ja.size(); j++) {
					JSONObject	b = ja.getJSONObject(i);
					//规则决策结果
					System.out.println(b.getString("decision"));
					//规则ID
					System.out.println(b.getString("ruleId"));
					//规则名称
					System.out.println(b.getString("ruleName"));
					//规则风险系数
					System.out.println(b.getString("score"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 有证书的https请求
	 * 
	 * @api {get} /commonPost/:id HttpClientUtils.java
	 * @apiVersion 0.2.0
	 * @apiName  commonPost
	 * @apiGroup  com.hehenian.utils
	 * @apiDescription This is the Description
	 *
	 * @apiParam {Number}  @param myStore  证书存放路径
	 * @apiParam {Number}  @param mypassword  证书密码
	 * @apiParam {Number}  @param url	请求路径
	 * @apiParam {Number}  @param jsonStr 请求json格式字符串参数
	 * @apiParam {Number}  @return
	 *
	 * @apiSuccess {String} firstname  Firstname of the User.
	 * @apiSuccess {String} lastname   Lastname of the User.
	 * @apiSuccess {Date}   registered Date of Registration.
	 *
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *     {
	 *       "firstname": "John",
	 *       "lastname": "Doe"
	 *     }
	 *
	 * @apiError UserNotFound The id of the User was not found.
	 *
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *       "error": "UserNotFound"
	 *     }
	 * @apiSampleRequest http://api.github.com/some_path/
	 *
	 * zhangcymf
	 *
	 */
	public static String commonPost(String keystore, String password, String url,String jsonStr)  {
		String result = null;
		SSLContext sslcontext = null;
		try {
			logger.info("请求：url={" + url + "},jsonStr={"+jsonStr+"}");
			if (keystore != null && keystore.length() > 0) {
				KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
				// 加载证书文件
				FileInputStream instream = new FileInputStream(new File(keystore));
				try {
					trustStore.load(instream, password.toCharArray());
				} finally {
					instream.close();
				}

				sslcontext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
					// 信任所有
					public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						return true;
					}
				}).loadTrustMaterial(trustStore).build();
			} else {
				sslcontext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
					// 信任所有
					public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						return true;
					}
				}).build();
			}

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register("http", PlainConnectionSocketFactory.INSTANCE).register("https", sslsf).build();

			CookieStore cookieStore = new BasicCookieStore();
			PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
					socketFactoryRegistry);
			connManager.setMaxTotal(110);

			org.apache.http.impl.client.HttpClientBuilder httpClientBuilder = HttpClients.custom();
			
			httpClientBuilder.setSSLSocketFactory(sslsf).setConnectionManager(connManager)
					.setDefaultCookieStore(cookieStore);
			CloseableHttpClient httpclient = httpClientBuilder.build();

			try
			{
				HttpPost httpPost = new HttpPost(url);
				if(jsonStr!=null)
				{
					httpPost.setEntity(new StringEntity(jsonStr, "utf-8"));
				}
				RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(70000).setConnectTimeout(70000).build();//设置请求和传输超时时间 
				httpPost.setConfig(requestConfig);
				
				System.out.println("请求内容:" + httpPost.getRequestLine());
				CloseableHttpResponse response = httpclient.execute(httpPost);

				try {
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						result = EntityUtils.toString(response.getEntity());
						 logger.info("请求URL({"+url+"}) 返回结果：{"+result+"}");

					} else {
						throw new BusinessException("http请求错误,错误码:" + response.getStatusLine().getStatusCode());
					}

				} finally {
					response.close();
				}

			} finally {
				httpclient.close();
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return result;
	}
}