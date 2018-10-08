package com.dce.business.common.util;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class TokenUtil {
	
	
	private static Logger logger = Logger.getLogger(TokenUtil.class);
    
    //扫描支付
    public static String PAY_QR_CODE = "PAY_QR_CODE";
    

    /**
     * 生成token并设置在session中
     * @param request
     * @param bizCode 业务code
     */
    public static String putToken(HttpServletRequest request, String bizCode) {
    	logger.info("生成session token.......");
        String token = new String(Base64.encodeBase64(UUID.randomUUID().toString().getBytes()));
        request.getSession().setAttribute(bizCode, token);
        logger.info("token的key为:" + bizCode + ",token值为:" + token);
        return token;
    }

    /**
     * 获取token
     * @param request
     * @param bizCode 业务code
     */
    public static boolean checkToken(HttpServletRequest request, String bizCode) {
    	logger.info("进入token判断.....");
        String token = request.getParameter("token");
        logger.info("传入的token值为:" + token);
        
        if (StringUtils.isBlank(token)) {
            return false;
        }
        String sessionToken = (String) request.getSession().getAttribute(bizCode) == null ? "" : (String) request.getSession().getAttribute(bizCode);
        logger.info("保存在session中的token名为:" + bizCode + " ,值为:" + sessionToken);
        request.getSession().removeAttribute(bizCode);
        return token.equalsIgnoreCase(sessionToken);
    }

}
