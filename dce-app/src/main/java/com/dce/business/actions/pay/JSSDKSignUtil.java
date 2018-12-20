/**
 * Project Name:cfb-dk <br>
 * Package Name:com.hehenian.utils.excel <br>
 *
 * @author mk <br>
 * Date:2018-11-23 10:04 <br>
 */

package com.dce.business.actions.pay;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;

/**
 * ClassName: JSSDKSignUtil <br>
 * Description:  <br>
 * @author mk
 * @Date 2018-11-23 10:04 <br>
 * @version
 */
public class JSSDKSignUtil {

	private final static Logger logger = Logger.getLogger(WXUtil.class);
	
    public static String JSAPI_TICKET_URL = "http://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=";
    public static String JSAPI_GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";

    private static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        getAccessToken("","1234");
    }

    private static void demo1() {
        String access_token = "1234";
        // 注意 URL 一定要动态获取，不能 hardcode
        String url = "http://example.com";
        Map<String, String> ret = getSignInfo(access_token,url);
        for (Map.Entry entry : ret.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }

    
   public static String getWxAccessToken(String appId,String secret) {
        try{
            
            logger.info("appId:"+appId+",secret:"+secret+" get wx_dk_access_token query from jssdk");
            Map<String, String> accessToken = getAccessToken(appId,secret);
            logger.info("appId:"+appId+",secret:"+secret+" get wx_dk_access_token query from jssdk,result:"+accessToken);
            if(null != accessToken){
                return accessToken.get("access_token");
            }
        }catch (Exception e){
            logger.error("appId:"+appId+",secret:"+secret+" get wx_dk_access_token error.",e);
        }
        return null;
    }
    
    
    private static Map<String, String> getAccessToken(String appId,String secret) {
    	String url = JSAPI_GET_ACCESS_TOKEN_URL.replaceAll("APPID",appId).replaceAll("SECRET",secret);
        String jsonResult = restTemplate.getForObject(url, String.class);
        System.out.println("get from ["+url+"] access_token:"+jsonResult);
        return JSON.parseObject(jsonResult, Map.class);
    }

    public static Map<String, String> getSignInfo(String appId,String wxSecret, String url) {
        try {
        	String access_token = getWxAccessToken(appId,wxSecret);
        	return getSignInfo(access_token,url);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static Map<String, String> getSignInfo(String access_token, String url) {
    	try {
    		if(StringUtils.isBlank(access_token) || StringUtils.isBlank(url)){
    			System.out.println("access_token is null or url is null...");
    			return null;
    		}
    		return sign(getTicket(JSAPI_TICKET_URL,access_token),url);
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }

    private static String getTicket(String url,String access_token) {
        RestTemplate restTemplate = new RestTemplate();
        String jsapi_ticket = restTemplate.getForObject(url + access_token, String.class);
        System.out.println("jsapi_ticket="+jsapi_ticket);
        return jsapi_ticket;
    }

    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
