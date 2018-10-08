/**
 * Project Name:jcpt-encrypt <br>
 * File Name:DataDecrypt.java <br>
 * Package Name:com.hehenian.encrypt <br>
 * Copyright (c) 2017, 深圳市彩付宝网络技术有限公司 All Rights Reserved.
 */

package com.dce.business.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * ClassName: DataDecrypt <br>
 * Description: 数据解密
 * @version
 * @since JDK 1.6
 */
public class DataDecrypt {
    private static Logger logger = Logger.getLogger(DataDecrypt.class);

    /**
     * decrypt:数据解密，返回原文. <br>
     * @param content 待解密16进制字符内容
     * @return String 解密后的原文
     * @throws Exception
     */
    public static String decrypt(String content) {
        if (StringUtils.isBlank(content)) {
            return content;
        }
        //密文长度为16的倍数
        if (content.length() % 16 != 0) {
            logger.info("密文长度不是16的倍数：" + content);
            return content;
        }
        try {
            String decrptStr = AESUtil.decryptToStr(content, KeyUtil.getKey());
            return decrptStr;
        } catch (Exception e) {
            logger.error("解密失败,解密内容：" + content, e);
            //            throw new BusinessException("解密失败");
            return content;
        }
    }

    /** 
     * 判断是否能解密
     * @param content
     * @return  
     */
    public static boolean isCanDecrypt(String content) {
        if (StringUtils.isBlank(content)) {
            return false;
        }
        //密文长度为16的倍数
        if (content.length() % 16 != 0) {
            logger.info("密文长度不是16的倍数：" + content);
            return false;
        }
        try {
            AESUtil.decryptToStr(content, KeyUtil.getKey());
            return true;
        } catch (Exception e) {
            logger.error("解密失败,解密内容：" + content);
            //            throw new BusinessException("解密失败");
            return false;
        }
    }
    
    public static void main(String[] args) {
    	System.setProperty("catalina.home", "C:\\Program Files\\Apache Software Foundation\\apache-tomcat-7.0.88");
		System.out.println(decrypt("B5D26895BC35CA09848D16150A0B3E1C"));
	}
}
