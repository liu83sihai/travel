package com.dce.business.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class MD5Encrypt {
    private final static Logger logger = Logger.getLogger(MD5Encrypt.class);
    private static final String ENCODE = "UTF-8";
    private static final String ENCNAME = "MD5";

    /**
     * 功能：MD5加密
     * @param strSrc 加密的源字符串
     * @return 加密串 长度32位(hex串)
     * @throws NoSuchAlgorithmException 
     * @throws UnsupportedEncodingException 
     */
    public static String getMessageDigest(String strSrc) {
        try {
            MessageDigest md = null;
            String strDes = null;

            byte[] bt = strSrc.getBytes(ENCODE);
            md = MessageDigest.getInstance(ENCNAME);
            md.update(bt);
            strDes = bytes2Hex(md.digest());
            return strDes;
        } catch (Exception e) {
            logger.error("MD5加密失败：", e);
        }

        return null;
    }

    /**
     * 将字节数组转为HEX字符串(16进制串)
     * @param bts 要转换的字节数组
     * @return 转换后的HEX串
     */
    private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
}
