package com.dce.business.common.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

/** 
 * HmacSHA256加密
 * @author parudy
 * @date 2018年4月2日 
 * @version v1.0
 */
public class HmacSHA256 {
    private final static Logger logger = Logger.getLogger(HmacSHA256.class);

    /**   
    * sha256_HMAC加密    
    * @param message 消息   
    * @param secret  秘钥 
    * @return 加密后字符串  
    */
    public static String hmac_SHA256(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
            logger.info(hash);
        } catch (Exception e) {
            logger.error("加密失败， message:" + message, e);
        }
        return hash;
    }

    /**     
     * 将加密后的字节数组转换成字符串   
     *    
     * @param b 字节数组    
     * @return 字符串     
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }
    
    public static void main(String[] args) {
        String str = "http://www.newsmth.net/nForum/#!mainpage";
        System.out.println(str);
        String key = "3E97D4C85D103DE0765DB925F6E14F4F";
        System.out.println(hmac_SHA256(str, key));
        
        String pubKey = MD5Encrypt.getMessageDigest(key);
        System.out.println(pubKey);
        System.out.println(hmac_SHA256(str, pubKey));
    }

}
