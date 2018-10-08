package com.dce.business.common.wxPay.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;


public class PayCommonUtil {
	/**
	 * 创建微信交易对象
	 */
	public static SortedMap<Object, Object> getWXPrePayID() {
		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", WXPayConfig.APP_ID);
		parameters.put("mch_id", WXPayConfig.MCH_ID);
		parameters.put("nonce_str", PayCommonUtil.CreateNoncestr());
		parameters.put("fee_type", "CNY");
		parameters.put("notify_url", WXPayConfig.NOTIFY_URL);
		parameters.put("trade_type", "APP");
		return parameters;
	}

	/**
	 * 再次签名，支付
	 */
	public static SortedMap<Object, Object> startWXPay(String result) {
		try {
			Map<String, String> map = XMLUtil.doXMLParse(result);
			SortedMap<Object, Object> parameterMap = new TreeMap<Object, Object>();
			parameterMap.put("appid", WXPayConfig.APP_ID);
			parameterMap.put("partnerid", WXPayConfig.MCH_ID);
			parameterMap.put("prepayid", map.get("prepay_id"));
			parameterMap.put("package", "Sign=WXPay");
			parameterMap.put("noncestr", PayCommonUtil.CreateNoncestr());
			// 本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
			parameterMap.put("timestamp",
					Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0, 10)));
			String sign = PayCommonUtil.createSign("UTF-8", parameterMap);
			parameterMap.put("sign", sign);
			return parameterMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建随机数
	 * 
	 * @param length
	 * @return
	 */
	public static String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	/**
	 * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * 
	 * @return boolean
	 */
	public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + WXPayConfig.APP_KEY);
		// 算出摘要
		String mysign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toLowerCase();
		String tenpaySign = ((String) packageParams.get("sign")).toLowerCase();
		// System.out.println(tenpaySign + " " + mysign);
		return tenpaySign.equals(mysign);
	}

	/**
	 * @Description：创建sign签名
	 * @param characterEncoding
	 *            编码格式
	 * @param parameters
	 *            请求参数
	 * @return
	 */
	public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + WXPayConfig.APP_KEY);
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;
	}

	/**
	 * @Description：将请求参数转换为xml格式的string
	 * @param parameters
	 *            请求参数
	 * @return
	 */
	public static String getRequestXml(SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * @Description：返回给微信的参数
	 * @param return_code
	 *            返回编码
	 * @param return_msg
	 *            返回信息
	 * @return
	 */
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}

	/**
	 * 发送https请求
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return 返回微信服务器响应的信息
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		/*try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new TrustManagerUtil() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			// conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			// log.error("连接超时：{}", ce);
		} catch (Exception e) {
			// log.error("https请求异常：{}", e);
		}*/
		return null;
	}

	/**
	 * 发送https请求
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod) {
		JSONObject jsonObject = null;
		/*try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new TrustManagerUtil() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			// conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setConnectTimeout(3000);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			// conn.setRequestProperty("content-type",
			// "application/x-www-form-urlencoded");
			// 当outputStr不为null时向输出流写数据
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer);
		} catch (ConnectException ce) {
			// log.error("连接超时：{}", ce);
		} catch (Exception e) {
			System.out.println(e);
			// log.error("https请求异常：{}", e);
		}*/
		return jsonObject;
	}

	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 接收微信的异步通知
	 * 
	 * @throws IOException
	 */
	public static String reciverWx(HttpServletRequest request) throws IOException {
		InputStream inputStream;
		StringBuffer sb = new StringBuffer();
		inputStream = request.getInputStream();
		String s;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while ((s = in.readLine()) != null) {
			sb.append(s);
		}
		in.close();
		inputStream.close();
		return sb.toString();
	}

	/**
	 * 产生num位的随机数
	 * 
	 * @return
	 */
	public static String getRandByNum(int num) {
		String length = "1";
		for (int i = 0; i < num; i++) {
			length += "0";
		}
		Random rad = new Random();
		String result = rad.nextInt(Integer.parseInt(length)) + "";
		if (result.length() != num) {
			return getRandByNum(num);
		}
		return result;
	}

	/**
	 * 返回当前时间字符串
	 * 
	 * @return yyyyMMddHHmmss
	 */
	public static String getDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat();
		return sdf.format(new Date());
	}

	/**
	 * 将日志保存至指定路径
	 * 
	 * @param path
	 * @param str
	 */
	public static void saveLog(String path, String str) {
		File file = new File(path);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			fos.write(str.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveE(String path, Exception exception) {
		try {
			int i = 1 / 0;
		} catch (final Exception e) {
			try {
				new PrintWriter(new BufferedWriter(new FileWriter(path, true)), true).println(new Object() {
					public String toString() {
						StringWriter stringWriter = new StringWriter();
						PrintWriter writer = new PrintWriter(stringWriter);
						e.printStackTrace(writer);
						StringBuffer buffer = stringWriter.getBuffer();
						return buffer.toString();
					}
				});
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}
}
