/**
 * Project Name:thrones <br/>
 * File Name:Base64Coder.java <br/>
 * Package Name:com.hehenian.thrones.common.tools <br/>
 * Copyright (c) 2016, 深圳市彩付宝网络技术有限公司 All Rights Reserved.
 */

package com.dce.business.common.util;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;


/**
 * 
 * ClassName: Base64Coder <br>
 * Description: 对字符串使用默认编码进行base64编码 <br>
 * @version
 * @since JDK 1.6
 */
public class Base64Coder {

  /**
   * 
   * encodeString:获取base64编码 . <br>
   * @param content 原始待加密字符串
   * @return String 失败时返回null
   */
  public static String encodeString(String content) {
    try {
      byte[] data = content.getBytes(KeyUtil.DEFAULT_ENCODE);
      return encodBase64String(data);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 
   * decodeString:解码 . <br>
   * @param content
   *          base64加密字符串
   * @return String 失败时返回null
   */
  public static String decodeString(String content) {
    try {
      byte[] data = decodeBase64(content);
      return new String(data,KeyUtil.DEFAULT_ENCODE);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public static String encodBase64String(byte[] bytes){
    return Base64.encodeBase64String(bytes);
  }
  
  public static byte[] decodeBase64(String content){
    return Base64.decodeBase64(content);
  }
  
  public static void main(String[] args) {
	String uuid = UUID.randomUUID().toString().substring(0, 10);
	String useid = uuid + "51";
	String ecode = encodeString(useid);
	System.out.println("加密：" +ecode);
	
	System.out.println("解密：" +decodeString(ecode));
	
}

}
