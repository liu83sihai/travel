package com.dce.business.service.impl.sms;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dce.business.common.util.HttpUtil;

@Service("yunRongSmsImpl")
public class YunRongSmsImpl implements DceSmsService {
	
	private static String userName = "jslhy"; 
    private  static String password = "rF3mI2tN";
    
    private static String md5Digest(String plaintext) throws Throwable {
        Map<String, String> params = new HashMap<String, String>();
        params.put("plaintext", plaintext);
        return HttpUtil.httpPost("http://47.98.61.138:9001/md5Digest.do", params);
    }

    
	@Override
	public String  smsSend(String content,String mobile) throws Throwable {
		String pwd = md5Digest(password);
		StringBuffer sb = new StringBuffer();
		sb.append(userName).append(pwd.trim());
		pwd = md5Digest(sb.toString());
	    Map<String, String> params = new HashMap<String, String>();
		params.put("username", userName);
		params.put("password", pwd.trim());
		params.put("content", "【加斯链】您的验证码为："+content+",30分钟内有效。为了您的账户安全，请勿将验证码告知他人。如非本人操作，请联系加斯链客服");
		params.put("mobile",mobile);
	    String result =  HttpUtil.httpPost("http://47.98.61.138:9001/smsSend.do", params);
	    System.out.println(result);
	    return result.trim();
	}


}
