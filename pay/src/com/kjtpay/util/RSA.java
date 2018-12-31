package com.kjtpay.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * <p>RSA签名,加解密处理核心文件</p>
 * @author raoxianyin
 * @version $Id: RSA.java, v 0.1 2017-09-24 下午2:33:53 raoxianyin Exp $
 */
public class RSA {

    /**
     * 签名算法
     */
    public static final String  SIGNATURE_ALGORITHM = "SHA1withRSA";
    /**
     * 加密算法RSA
     */
    public static final String  KEY_ALGORITHM       = "RSA";
    /**
     * RSA最大加密明文大小
     */
    private static final int    MAX_ENCRYPT_BLOCK   = 245;//117

    /**
     * RSA最大解密密文大小
     */
    private static final int    MAX_DECRYPT_BLOCK   = 256;//128

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY          = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY         = "RSAPrivateKey";

    private static Logger       logger              = LoggerFactory.getLogger(RSA.class);
    
    
    static{
		  try{
		   Security.addProvider(new BouncyCastleProvider());
		  }catch(Exception e){
		   e.printStackTrace();
		  }
	 }
    

    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     * @param length 密钥长度
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair(Integer length) throws Exception {
    	
    	if (null == length || length%64!=0){
    		return null;
    	}
    	
    	KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA","BC");
    	SecureRandom random = new SecureRandom();
		keygen.initialize(length, random);
		KeyPair pair = keygen.generateKeyPair();
		//PKCS#8格式实体
		PrivateKey privateKey = pair.getPrivate();
		PublicKey publicKey = pair.getPublic();
    	
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;

    }

    /**
     * 签名字符串
     *
     * @param text
     *            需要签名的字符串
     * @param privateKey 私钥(BASE64编码)
     *
     * @param input_charset
     *            编码格式
     * @return 签名结果(BASE64编码)
     */
    public static String sign(String text, String privateKey, String charset) throws Exception {
    	try{
    		byte[] privateKeyBytes = Base64.decodeBase64(privateKey);
    		String chackPrivateKey = new String(Base64.encodeBase64(privateKeyBytes));
    		
    		//校验私钥privateKey位数
    		if(!chackPrivateKey.equals(privateKey.replaceAll("[\n\r]*", ""))){
    			throw new Exception("密钥位数不对");
    		}
    		
//    		//按byte读
//    		InputStream is = new ByteArrayInputStream(privateKey.getBytes());
//    		Base64InputStream bis = new Base64InputStream(is);
//    		byte[] privateKeyBytes = new byte[privateKey.getBytes().length]; 
//    		bis.read(privateKeyBytes);
//    		bis.close();
    		
    		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
    		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
    		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
    		signature.initSign(privateK);
    		signature.update(getContentBytes(text, charset));
    		byte[] result = signature.sign();
    		return Base64.encodeBase64String(result);
    	}catch(java.security.spec.InvalidKeySpecException e){
    		throw new Exception("签名失败");
    	}

    }
    

    /**
     * 字符串验签
     *
     * @param text
     *            需要签名的字符串
     * @param sign
     *            客户签名结果
     * @param publicKey
     *            公钥(BASE64编码)
     * @param input_charset
     *            编码格式
     * @return 验签结果
     */
    public static boolean verify(String text, String sign, String publicKey, String charset) throws Exception {
        byte[] publicKeyBytes = Base64.decodeBase64(publicKey);
        String chackPublicKey = new String(Base64.encodeBase64(publicKeyBytes));
		
		//校验公钥publicKey位数
		if(!chackPublicKey.equals(publicKey.replaceAll("[\n\r]*", ""))){
			throw new Exception("密钥位数不对");
		}
    	
//    	//按byte读
//		InputStream is = new ByteArrayInputStream(publicKey.getBytes());
//		Base64InputStream bis = new Base64InputStream(is);
//		byte[] publicKeyBytes = new byte[publicKey.getBytes().length]; 
//		bis.read(publicKeyBytes);
//		bis.close();
    	
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(getContentBytes(text, charset));
        return signature.verify(Base64.decodeBase64(sign));


    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
                                                                                     throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;

    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
                                                                                   throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;

    }
    
    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据(BASE64编码)
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String encryptedData,String publicKey) throws Exception{
    	if(encryptedData != null && encryptedData.length()>=1){
    		byte[] encrypted = Base64.decodeBase64(encryptedData);
    		byte[] decrypt = RSA.decryptByPublicKey(encrypted, publicKey);
    		
    		return new String(decrypt);
    	}
    	
    	return null;
    }
    
    

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;

    }

    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;

    }
    
    
    /**
     * <p>
     * 私钥加密(BASE64编码)
     * </p>
     *
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String data, String privateKey) throws Exception {
    	if(data != null && data.length()>=1){
    		byte[] encrypt = data.getBytes();
    		byte[] encrypted = RSA.encryptByPrivateKey(encrypt, privateKey);
    		
    		return Base64.encodeBase64String(encrypted);
    	}
    	
    	return null;
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @param keyType 密钥格式 PKCS#1:非java语言用  PKCS#8:java语言用
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap, String keyType) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        
        if("PKCS#8".equalsIgnoreCase(keyType)){
        	//按Base64 byte输出
        	OutputStream os = new ByteArrayOutputStream();
        	Base64OutputStream bos = new Base64OutputStream(os);
        	bos.write(key.getEncoded(), 0, key.getEncoded().length);
        	bos.close();
        	return os.toString();
        	
//        	//这种方式不是按byte输出的
//        	return Base64.encodeBase64String(key.getEncoded());
        	
        }else if("PKCS#1".equalsIgnoreCase(keyType)){
        	//转成PKCS#1格式
        	
        	//以下是bcprov-jdk15-1.45.jar代码
        	StringWriter sw = new StringWriter();
            PEMWriter pemW = new PEMWriter(sw, "BC");
            pemW.writeObject(key);
            pemW.close();
            String pemString = sw.getBuffer().toString().replaceAll("-----(\\s|\\w)+-----[\r\n]*", "");
        	
            return pemString;
        	
//            //以下是bcprov-jdk15on-1.52.jar代码
//            byte[] privBytes = key.getEncoded();
//            PrivateKeyInfo pkInfo = PrivateKeyInfo.getInstance(privBytes);
//        	
//    		ASN1Encodable encodable = pkInfo.parsePrivateKey();
//    		ASN1Primitive primitive = encodable.toASN1Primitive();
//    		byte[] privateKeyPKCS1 = primitive.getEncoded();
//    		
//    		PemObject pemObject = new PemObject("", privateKeyPKCS1);
//    		StringWriter stringWriter = new StringWriter();
//    		PemWriter pemWriter = new PemWriter(stringWriter);
//    		pemWriter.writeObject(pemObject);
//    		pemWriter.close();
//    		String pemString = stringWriter.toString().replaceAll("-----(BEGIN|END)\\s-----[\r\n]*", "");
//    		
//        	return pemString;
        }else{
        	return "暂不支持其他密钥格式";
        }
    }

    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        
        //按Base64 byte输出
    	OutputStream os = new ByteArrayOutputStream();
    	Base64OutputStream bos = new Base64OutputStream(os);
    	bos.write(key.getEncoded(), 0, key.getEncoded().length);
    	bos.close();
    	return os.toString();
        
//    	//这种方式不是按byte输出的
//        return Base64.encodeBase64String(key.getEncoded());
    }
    
    
    public static void main(String[] args) {
		try {
			Map<String, Object> keyMap = RSA.genKeyPair(1024);
			
			String publicKey = RSA.getPublicKey(keyMap);
			String privateKey = RSA.getPrivateKey(keyMap, "PKCS#8");
			
			System.out.println("publicKey:"+publicKey);
			System.out.println("privateKey:"+privateKey);
			System.out.println("---------------------------");
			
			//---私钥加密-----------------
			byte[] encryptByte = RSA.encryptByPrivateKey("{'aaa_bb':'加解密测试'}".getBytes(), privateKey);
			String encryptStr = Base64.encodeBase64String(encryptByte);
			System.out.println("encryptStr:"+encryptStr);
			
			//---公钥解密-----------------
			byte[] decryptByte = RSA.decryptByPublicKey(Base64.decodeBase64(encryptStr), publicKey);
			String decryptStr = new String(decryptByte);
			System.out.println("decryptStr:"+decryptStr);
			System.out.println("---------------------------");
			
			//---私钥加密-----------------
			String encryptString = RSA.encryptByPrivateKey("{'11_22':'加解密测试'}", privateKey);
			System.out.println("encryptString:"+encryptString);
			
			//---公钥解密-----------------
			String decryptString = RSA.decryptByPublicKey(encryptString, publicKey);
			System.out.println("decryptString:"+decryptString);
			System.out.println("---------------------------");
			
			//---私钥签名-----------------
			String sign = RSA.sign("{'cc_dd':'签名测试'}", privateKey, "utf-8");
			System.out.println("sign:"+sign);
			
			//---公钥验签-----------------
			boolean isVerify = RSA.verify("{'cc_dd':'签名测试'}", sign, publicKey, "utf-8");
			System.out.println("isVerify:"+isVerify);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
}