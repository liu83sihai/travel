/**
 * Project Name:jcpt-encrypt <br>
 * File Name:AESUtil.java <br>
 * Package Name:com.hehenian.encrypt.aes <br>
 * Copyright (c) 2017, 深圳市彩付宝网络技术有限公司 All Rights Reserved.
 */

package com.dce.business.common.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * ClassName: AESUtil <br>
 * Description: AES加密解密工具类
 * @version
 * @since JDK 1.6
 */
public class AESUtil {
  /**
   * KEY_ALGORITHM:秘钥算法.
   */
  private static final String KEY_ALGORITHM = "AES";
  /**
   * CIPHER_ALGORITHM:加密/解密算法 /工作模式 /填充方式.
   */
  private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认模式
  private static final String HASH_ALGORITHM = "SHA1PRNG";
  private static final String ENCODE = KeyUtil.DEFAULT_ENCODE;
  private static final int KEY_LENGTH = KeyUtil.AES_KEY_LENGTH;

  /**
   * 
   * toKey:转换加盐处理后的秘钥. <br>
   * 
   * @param key
   * @return
   * @throws Exception
   */
  private static SecretKeySpec toKey(String key) throws Exception {
    key = key + KeyUtil.getAesSalt();
    KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
    SecureRandom secureRandom = SecureRandom.getInstance(HASH_ALGORITHM);
    secureRandom.setSeed(key.getBytes(ENCODE));
    kgen.init(KEY_LENGTH, secureRandom);
    SecretKey secretKey = kgen.generateKey();
    byte[] enCodeFormat = secretKey.getEncoded();
    SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat,KEY_ALGORITHM);
    return secretKeySpec;
  }

  /**
   * 
   * encrypt:加密. <br>
   * @param msg 待加密内容
   * @param key 加密key
   * @return byte[] 
   * @throws Exception
   */
  public static byte[] encrypt(String msg, String key) throws Exception{
    SecretKeySpec secretKeySpec = toKey(key);
    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);// 创建密码器
    byte[] byteContent = msg.getBytes(ENCODE);
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec); // 初始化
    byte[] result = cipher.doFinal(byteContent);
    return result; // 加密
  }

  /**
   * 
   * encryptNotSalt:不加盐的加密. <br>
   * 
   * @param msg 待加密内容
   * @param key 加密key
   * @return byte[] 
   * @throws Exception
   */
  public static byte[] encryptNotSalt(String msg, String key) throws Exception{
    SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);// 创建密码器
    byte[] byteContent = msg.getBytes(ENCODE);
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec); // 初始化
    byte[] result = cipher.doFinal(byteContent);
    return result; // 加密
  }

  /**
   * 
   * encryptToStr:带盐加密成16进制字符串. <br>
   *
   * @param content 加密内容
   * @param key 加密key
   * @return String 加密后的字符串
   * @throws Exception
   */
  public static String encryptToStr(String content, String key) throws Exception {
    return parseByte2HexStr(encrypt(content, key));
  }
  
  /**
   * 
   * encryptToStrNotSalt:不带盐加密成16进制字符串. <br>
   *
   * @param content 加密内容
   * @param key 加密key
   * @return 加密后的字符串
   * @throws Exception
   */
  public static String encryptToStrNotSalt(String content, String key) throws Exception{
    return parseByte2HexStr(encryptNotSalt(content, key));
  }

  /**
   * 
   * encryptToBase64:使用AES加密，并转换成base64. <br>
   *
   * @param content 需要加密的内容
   * @param key 加密key
   * @return
   * @throws Exception
   */
  public static String encryptToBase64(String content, String key) throws Exception{
    String s = encryptToStr(content, key);
    return Base64Coder.encodeString(s);
  }

  /**
   * 
   * decrypt:解密. <br>
   *
   * @param data 待解密数据字节码
   * @param key key
   * @return byte[] 解密后的字节码
   * @throws Exception
   */
  public static byte[] decrypt(byte[] data, String key) throws Exception{
    SecretKeySpec secretKeySpec = toKey(key);
    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);// 创建密码器
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);// 初始化
    byte[] result = cipher.doFinal(data);
    return result; // 加密
  }

  /**
   * 
   * decryptNotSalt:解密不带盐. <br>
   *
   * @param data 待解密数据
   * @param key 秘钥
   * @return byte[] 解密后的数据
   * @throws Exception
   */
  public static byte[] decryptNotSalt(byte[] data, String key)throws Exception {
    SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
    Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);// 创建密码器
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);// 初始化
    byte[] result = cipher.doFinal(data);
    return result; // 加密
  }

 /**
  * 
  * decrypt:16进制字符串不带盐解密. <br>
  *
  * @param content 待解密字符串（16进制）
  * @param key 秘钥
  * @return byte[] 解密后的数据
  * @throws Exception
  */
  public static byte[] decrypt(String content, String key) throws Exception{
    return decrypt(parseHexStr2Byte(content), key);
  }

  /**
   * 
   * decryptNotSalt:16进制字符串带盐解密. <br>
   *
   * @param content 待解密字符串（16进制）
   * @param key 秘钥
   * @return byte[] 解密后的数据
   * @throws Exception
   */
  public static byte[] decryptNotSalt(String content, String key) throws Exception{
    return decryptNotSalt(parseHexStr2Byte(content), key);
  }


  /**
   * 
   * decryptToStr:解密后转换成系统默认编码的字符串--原文. <br>
   *
   * @param content 待解密字符串（16进制）
   * @param key 秘钥
   * @return String 原文
   * @throws Exception
   */
  public static String decryptToStr(String content, String key) throws Exception{
    return new String(decrypt(content, key), ENCODE);
  }

  /**
   * 
   * decryptToStrNotSalt:带盐解密后转换成系统默认编码的字符串--原文. <br>
   *
   * @param content 待解密字符串（16进制）
   * @param key 秘钥
   * @return String 原文
   * @throws Exception
   */
  public static String decryptToStrNotSalt(String content, String key) throws Exception{
    return new String(decryptNotSalt(content, key),ENCODE);
  }

  /**
   * 
   * decryptToStrFromBase64:解密. <br>
   *
   * @param content 待解密内容(base64加密的字符串)
   * @param keyWord 解密密钥
   * @return byte[] 
   * @throws Exception
   */
  public static String decryptToStrFromBase64(String content, String keyWord) throws Exception{
    String msg = Base64Coder.decodeString(content);
    return decryptToStr(msg, keyWord);
  }

  /**
   * 
   * parseByte2HexStr:将二进制转换成16进制. <br>
   *
   * @param buf
   * @return 
   */
  public static String parseByte2HexStr(byte buf[]) {
    if (buf == null) {
      return null;
    }
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < buf.length; i++) {
      String hex = Integer.toHexString(buf[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(hex.toUpperCase());
    }
    return sb.toString();
  }

 /**
  * 
  * parseHexStr2Byte:将16进制转换为二进制. <br>
  *
  * @param hexStr
  * @return
  */
  public static byte[] parseHexStr2Byte(String hexStr) {
    if (hexStr == null || hexStr.length() < 1) {
      return null;
    }
    byte[] result = new byte[hexStr.length() / 2];
    for (int i = 0; i < hexStr.length() / 2; i++) {
      int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
      int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
      result[i] = (byte) (high * 16 + low);
    }
    return result;
  }
  
  public static void main(String[] args) throws Exception {

    String content = "一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五一二三四五";//90 192
//    content = "123456789012345678901234567890";//30 64
//    content = "abcdefghijabcdefghijabcdefghij";//30 64
    String password = "1444";
    // 加密
    System.out.println("加密前：" + content);
//    System.out.println("加密前：" + content.getBytes().length);
    //AES 密文长度 = (原文长度 / 16) * 16 + 16
    byte[] encryptResult = encrypt(content, password);
    String encryptResultStr = parseByte2HexStr(encryptResult);
    System.out.println("加密后：" + encryptResultStr);
    System.out.println("加密后：HEX," + encryptResultStr.length());
    // 解密
    byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
    
    long start = System.currentTimeMillis();
    byte[] decryptResult = decrypt(decryptFrom, password);
    long end = System.currentTimeMillis();
    System.out.println("cost:"+(end-start));
    System.out.println("解密后：" + new String(decryptResult));

    String en = encryptToBase64(content, password);
    System.out.println("加密后：" + en);
    System.out.println("加密后：64," + en.length());
    System.out.println("解密后：" + decryptToStrFromBase64(en, password));
  }
}
