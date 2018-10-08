/**
 * Project Name:jcpt-encrypt <br>
 * File Name:KeyUtil.java <br>
 * Package Name:com.hehenian.encrypt.util <br>
 * Copyright (c) 2017, 深圳市彩付宝网络技术有限公司 All Rights Reserved.
 */

package com.dce.business.common.util;

import org.apache.commons.lang3.StringUtils;


/**
 * ClassName: KeyUtil <br>
 * Description: 获取加密的key
 * @version
 * @since JDK 1.6
 */
public class KeyUtil {

    /**
     * 
     * getKey:获取加密秘钥. <br>
     *
     * @return
     * @throws Exception
     */
    public static String getKey() throws Exception {
        String encryptKey = KeyUtil.getAesKey();
        if (encryptKey == null || "".equals(encryptKey.trim())) {
            throw new Exception("获取AES.KEY为空");
        }
 
        return encryptKey;
    }

	public static String DEFAULT_ENCODE = "UTF-8";
	public static int RSA_KEY_LENGTH = 1024;
	public static int AES_KEY_LENGTH = 128;
	/** 
	 * 从配置文件读取获取aes加密长度
	 * @return  
	 */
	public static int  getAesEncryptLength() {
	    String length = PropertiesUtil.getProperty("HHYD.AES.ENCRYPT.LENGTH");
	    if (StringUtils.isEmpty(length)) {
	        return 90; //默认90
	    }
	    
	    return Integer.valueOf(length);
	}
	/** 
	 * 从配置文件读取获取aes key
	 * @return  
	 */
	public static String getAesKey() {
	    return PropertiesUtil.getProperty("HHYD.AES.KEY");
	}
	/** 
	 * 从配置文件读取获取aes salt
	 * @return  
	 */
	public static String getAesSalt() {
	    return PropertiesUtil.getProperty("HHYD.AES.SALT");
	}
}
