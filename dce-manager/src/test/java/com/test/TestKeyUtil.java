package com.test;

import org.apache.commons.lang3.StringUtils;

import com.dce.business.common.util.PropertiesUtil;

public class TestKeyUtil {

	/**
     * 
     * getKey:获取加密秘钥. <br>
     *
     * @return
     * @throws Exception
     */
    public static String getKey() throws Exception {
        String encryptKey = "963123E172844AA9DFAF3F099159BBFA48EE8D6C7268E7F4336D676FE9AA6E22BCE296351FCC9CB69502A4D75FBD7984";
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
