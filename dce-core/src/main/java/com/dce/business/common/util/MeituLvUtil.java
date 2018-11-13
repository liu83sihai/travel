package com.dce.business.common.util;

import java.io.UnsupportedEncodingException;
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
		 String sings = DigestUtils.shaHex(PRIVATEKEY+uname+QIUCODE+pnum).substring(2, 34);
//		 System.out.println(Sha1Util.getSha1(PRIVATEKEY+uname+QIUCODE+pnum));
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
	 
	
	 
	 public static void main(String[] args) {
		 MeituLvUtil  meitu = new MeituLvUtil();
		 String name ="LIU";
		 String mobile = "13723410575";
		 String cardNo = "UID4421452144";
		 String result =  meitu.virtualOpen(name,mobile,cardNo);
		 System.out.println("=============" + result);
//		 meitu.list();
	}

}
