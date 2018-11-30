package com.dce.business.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dce.business.common.result.Result;

public class MeituLvUtil {
	private final static Logger logger = Logger.getLogger(MeituLvUtil.class);
	
	final static String MEITU_LOGIN="https://qiuapi.meitulv.com/virtual_open";
	final static String MEITU_LIST="https://qiuapi.meitulv.com/services_list";
	final static String QIUCODE="356a192b7913b04c54574d18c28d4111";
	
	final static String PRIVATEKEY ="48e0e503e262e9b7416de7c308b17e";
	
	final String certFileName = "qiuapi.meitulv.com.cer"; 
	final String certFilePassword = "48e0e503e262e9b7416de7c308b17e"; 
	
	
	 /**
	  * 登录行旅通
	  * @param userName
	  * @param mobile
	  * @param cardNo
	  * @return 1:激活成功 其他激活失败
	  */
	 public static String virtualOpen(String userName,String mobile,String cardNo) {
		 logger.info("登录行旅通,userName:" + userName + ";mobile:"+mobile +";cardNo:" + cardNo);
		 String result = "0";
		 String pnum = mobile;
		 String uname = userName;
//		 String unameCode= getUnicode(uname);
//		 String sings = Sha1Util.getSha1(PRIVATEKEY+uname+QIUCODE+pnum).substring(2, 34);
//		 String sings = DigestUtils.shaHex(PRIVATEKEY+uname+QIUCODE+pnum).substring(2, 34);
		 String sings = SHA1Tools.encode(PRIVATEKEY+uname+QIUCODE+pnum);
//		 System.out.println(DigestUtils.shaHex(PRIVATEKEY+uname+QIUCODE+pnum));
		 Map<String,String> param = new HashMap<String,String>();
		 param.put("UID", cardNo);
		 param.put("PNUM",pnum);
		 param.put("UNAME", uname);
		 param.put("QIUCODE", QIUCODE);
		 param.put("DAYS", "365");
		 param.put("SINGS", sings);
		 
		 try {
			 result =  HttpUtil.httpCertPostNotSSl(MEITU_LOGIN, param);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		 return result;
	 }
	 
	 /**
	  * 登录行旅通
	  * @param userName
	  * @param mobile
	  * @param cardNo
	  * @return 1:激活成功 其他激活失败
	  */
	 public static String virtualOpenSSL(String userName,String mobile,String cardNo) {
		 logger.info("登录行旅通,userName:" + userName + ";mobile:"+mobile +";cardNo:" + cardNo);
		 Map<String, String> param = new HashMap<>();
			
			param.put( "UID", cardNo);
			
			param.put( "PNUM", mobile);
			
			param.put( "UNAME",userName);
			
//			param.put("IN("INUM", "610111198810100025");
			
			param.put("QIUCODE",QIUCODE);
			
			param.put("DAYS", "365");
			String sings = SHA1Tools
					.encode(PRIVATEKEY + userName + QIUCODE + mobile);
			String retStr =HttpClientUtil.doPost("https://qiuapi.meitulv.com/virtual_open",  param, sings);
		 
		 return retStr;
	 }
	 
	
	 
	 public static void main(String[] args) throws UnsupportedEncodingException {
		 MeituLvUtil  meitu = new MeituLvUtil();
		 String name ="陈红梅";
//		 String nameStr = URLEncoder.encode(name, "UTF-8");
		 String mobile = "13857112374";
		 String cardNo = "UID12312312123132";
		 String result =  meitu.virtualOpen(name,mobile,cardNo);
		 System.out.println("=============" + result);
//		 meitu.list();
	}

}
