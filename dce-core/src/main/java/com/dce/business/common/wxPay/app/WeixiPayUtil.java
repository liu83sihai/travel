/**
 * 
 */
package com.dce.business.common.wxPay.app;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


import net.sf.json.JSONObject;

/**
 * @author MBENBEN
 *
 */
public class WeixiPayUtil {
	private static final Logger logger = Logger.getLogger(WeixiPayUtil.class);
	
	// 参数组
	static String appid = "wx7a337545e02bdead";// 微信开放平台审核通过的应用APPID
	static String mch_id = "1510395671";
	static String notify ="http://app.zjzwly.com:8080/dce-app/order/notify_url.do";
	static String apiKey = "1dd7398de4ac33c541e609d22b19dc11";

	
	public static Map getWeixiPayInfo(String orderNo,String body,BigDecimal amount,int orderType)throws Exception{
		
		String createIp = "212.16.26.45";
		String tradeType = "APP";
		String nonceStr = UUID.randomUUID().toString();
		nonceStr = nonceStr.replace("-", "").substring(0,16);	
		
		BigDecimal totalPrcie = BigDecimal.ZERO;
		if( null != amount){
			totalPrcie = amount.multiply(new BigDecimal("100"));;
		}
		
		String notifyUrl = notify;
		String payDebug = "1";
		if("1".equals(payDebug)){
			totalPrcie = BigDecimal.ONE;
		}
	
		totalPrcie = totalPrcie.setScale(0);
		// 参数：开始生成签名
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("appid", appid);
		parameters.put("mch_id", mch_id);
		parameters.put("nonce_str", nonceStr);
		parameters.put("body", body);
//		parameters.put("detail", detail);
		parameters.put("attach", "attach");
		parameters.put("out_trade_no", orderNo);
		parameters.put("total_fee", totalPrcie);
//		parameters.put("time_start", timestamp);
//		parameters.put("time_expire", time_expire);
		parameters.put("notify_url", notifyUrl);
		parameters.put("trade_type", tradeType);
		parameters.put("spbill_create_ip", createIp);

		String sign = Md5Util.signMap(parameters, apiKey).toUpperCase();
		parameters.put("sign", sign);

		logger.info("签名是：" + sign);
		String returnCode = null;
		try {
			String result = HttpTool.post("https://api.mch.weixin.qq.com/pay/unifiedorder", parameters);
			logger.info("result是：" + result);
			Document doc = DocumentHelper.parseText(result);
			
			
			returnCode = xmlNodeValue(doc.getRootElement(), "return_code");
			
			if("SUCCESS".equalsIgnoreCase(returnCode)) {
				map.put("appid", appid);
				map.put("partnerid", mch_id);
				map.put("noncestr",xmlNodeValue(doc.getRootElement(), "nonce_str"));
			    String prepayId = xmlNodeValue(doc.getRootElement(), "prepay_id");
				map.put("prepayid",prepayId);
				map.put("package", "Sign=WXPay");
				
				long time = Calendar.getInstance().getTimeInMillis();
		    	String timeStamp = String.valueOf(time).substring(0, 10);
				map.put("timestamp", timeStamp);
				
				String appSign = Md5Util.signMap(map, apiKey).toUpperCase();
				logger.info("=================orderNo:" + orderNo + ";prepayId=" + prepayId);
				map.put("prepayId",prepayId);
				map.put("appSign", appSign);
				
				if(logger.isDebugEnabled()) {
					logger.debug("生成预支付订单("+")成功。");;
				}
			} else {
				logger.error("生成预支付订单("+")失败"+ result);
			}
			
			map.put("code",returnCode);
			map.put("message",xmlNodeValue(doc.getRootElement(), "return_msg"));
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("生成预支付订单("+")失败"+e.getMessage());
			throw e;
		}
		
		return map;
		
	}
	private static String xmlNodeValue(Element root, String nodeName) {
		return root.elementText(nodeName);
	}
	
	public static boolean checkSign(JSONObject json) {

        String signFromAPIResponse = json.get("sign").toString();

        if (signFromAPIResponse == "" || signFromAPIResponse == null) {
            logger.info("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
            return false;
        }

        return true;

    }
	
}
