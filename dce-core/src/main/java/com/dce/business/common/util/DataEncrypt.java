/**
 * Project Name:jcpt-encrypt <br>
 * File Name:DataEncrypt.java <br>
 * Package Name:com.hehenian.encrypt <br>
 */

package com.dce.business.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.dce.business.common.exception.BusinessException;


/**
 * ClassName: DataEncrypt <br>
 * Description: 数据加密
 * @version
 * @since JDK 1.6
 */
public class DataEncrypt {
    private static Logger logger = Logger.getLogger(DataEncrypt.class);

    /**
     * 
     * encrypt:数据加密，返回16进制字符串. <br>
     *
     * @param content 待加密内容
     * @return String 加密后的内容
     * @throws Exception
     */
    public static String encrypt(String content) throws BusinessException {
        if (StringUtils.isBlank(content)) {
            return content;
        }

        if (content.getBytes().length > KeyUtil.getAesEncryptLength()) {
            throw new IllegalArgumentException("加密内容过长");
        }
        try {
            //判断是否已经加密过，如果加密过，避免重复加密
            if (DataDecrypt.isCanDecrypt(content)) {
                logger.error("已经加密过，不需要再次加密，内容：" + content);
                return content;
            }
            String encrptStr = AESUtil.encryptToStr(content, KeyUtil.getKey());
            logger.info("加密前：" + content + "; 加密后：" + encrptStr);
            return encrptStr;
        } catch (Exception e) {
            logger.error("加密失败，加密字段：" + content, e);
            throw new BusinessException("加密失败");
        }
    }

    public static void main(String[] args) {
    	System.setProperty("catalina.home", "D:/apache-tomcat-7.0.79");
        String idno = "jy123456";
        try {
            System.out.println(encrypt(idno));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
