package com.dce.business.actions.meitulv;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;





/**
 * RSA加密解密和3DES加密解密工具类 <code>EncryptUtil.java</code>
 * <p>
 * <p>
 * Copyright 2015 All right reserved.
 * 
 * @version 1.0 </br>最后修改人 无
 */
public class EncryptUtil
{

	/**
	 * 默认编码
	 */
	private static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 日志对象
	 */
	private static Logger log = Logger.getLogger(EncryptUtil.class.getSimpleName());

	/**
	 * RSA
	 */
	private static final String RSA_ALGORITHM = "RSA";

	/**
	 * 签名
	 */
	public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

	/**
	 * 加载密钥，异常由调用者处理
	 * 
	 * @param in
	 *            密钥输入流
	 * @return Key 返回密钥对象
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Key loadKey(InputStream in) throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois = null;
		try
		{
			ois = new ObjectInputStream(in);
			Key key = (Key) ois.readObject();
			return key;
		}
		catch (ClassNotFoundException e)
		{
			log.error("class of key not found", e);
			throw e;
		}
		finally
		{
			if (ois != null)
			{
				ois.close();
			}
		}
	}

	/**
	 * 从字符串中加载公钥，异常由调用者处理
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static Key loadPublicKey(String publicKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException,
			NullPointerException
	{
		try
		{
			byte[] buffer = Base64.decodeBase64(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return keyFactory.generatePublic(keySpec);
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error("not support algorithm of secret[rsa]", e);
			throw e;
		}
		catch (InvalidKeySpecException e)
		{
			log.error("invalid key", e);
			throw e;
		}
		catch (NullPointerException e)
		{
			log.error(e);
			throw e;
		}
	}

	/**
	 * 从字符串中加载私钥，异常由调用者处理
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static Key loadPrivateKey(String privateKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException,
			NullPointerException
	{
		try
		{
			byte[] buffer = Base64.decodeBase64(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(keySpec);
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error("not support algorithm of secret[rsa]", e);
			throw e;
		}
		catch (InvalidKeySpecException e)
		{
			log.error("invalid key", e);
			throw e;
		}
		catch (NullPointerException e)
		{
			log.error(e);
			throw e;
		}
	}

	/**
	 * 从输入流中加载公钥，异常由调用者处理
	 * 
	 * @param is
	 *            公钥输入流
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public static String loadKeyString(InputStream is) throws IOException, NullPointerException
	{
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new InputStreamReader(is));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null)
			{
				if (readLine.charAt(0) == '-')
				{
					continue;
				}
				else
				{
					sb.append(readLine);
					sb.append('\r');
				}
			}
			return sb.toString();
		}
		catch (IOException e)
		{
			log.error("fail to read stream", e);
			throw e;
		}
		catch (NullPointerException e)
		{
			log.error("nothing input", e);
			throw e;
		}
		finally
		{
			if (br != null)
			{
				br.close();
			}
		}
	}

	/**
	 * 获取密钥文件输入流，异常由调用者处理
	 * 
	 * @param filePath
	 *            密钥文件完整路径
	 * @return java.io.InputStream
	 * @throws FileNotFoundException
	 */
	public static InputStream getInputStream(String filePath) throws FileNotFoundException
	{
		if (StringUtils.isNotBlank(filePath))
		{
			return new FileInputStream(filePath);
		}

		return null;
	}

	/**
	 * RSA加密，公钥或私钥，异常由调用者处理
	 * 
	 * @param key
	 *            秘钥
	 * @param src
	 *            源数据
	 * @return byte[] 加密后的数据
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] encrypt(Key key, byte[] src) throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException
	{
		if (src == null || src.length == 0)
		{
			throw new IllegalArgumentException("报文为空");
		}
		try
		{
			Cipher ciper = Cipher.getInstance(RSA_ALGORITHM);
			ciper.init(Cipher.ENCRYPT_MODE, key);
			return ciper.doFinal(src);
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error("not support algorithm of secret[rsa]", e);
			throw e;
		}
		catch (NoSuchPaddingException e)
		{
			log.error("no such padding", e);
			throw e;
		}
		catch (InvalidKeyException e)
		{
			log.error("invalid key", e);
			throw e;
		}
		catch (IllegalBlockSizeException e)
		{
			log.error("illegal block size", e);
			throw e;
		}
		catch (BadPaddingException e)
		{
			log.error("bad padding", e);
			throw e;
		}
	}

	/**
	 * RSA解密，私钥解密公钥加密的数据，异常由调用者处理
	 * 
	 * @param privateKey
	 *            私钥
	 * @param src
	 *            加密后的源数据
	 * @return byte[] 解密后的数据
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 */
	public static byte[] decrypt(Key privateKey, byte[] src) throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException
	{
		try
		{
			Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(src);
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error("not support algorithm of secret[rsa]", e);
			throw e;
		}
		catch (NoSuchPaddingException e)
		{
			log.error("no such padding", e);
			throw e;
		}
		catch (InvalidKeyException e)
		{
			log.error("invalid key", e);
			throw e;
		}
		catch (IllegalBlockSizeException e)
		{
			log.error("illegal block size", e);
			throw e;
		}
		catch (BadPaddingException e)
		{
			log.error("bad padding", e);
			throw e;
		}
	}

	/**
	 * 3DES加密，异常由调用者处理
	 * 
	 * @param src
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return byte[] 加密后的数据
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidKeySpecException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] encrypt(byte[] src, String password) throws UnsupportedEncodingException, InvalidKeyException,
			InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException
	{
		try
		{
			DESedeKeySpec dks = new DESedeKeySpec(password.getBytes(DEFAULT_CHARSET));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(dks);

			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, securekey);
			return cipher.doFinal(src);
		}
		catch (UnsupportedEncodingException e)
		{
			log.error("unsupported Encoding");
			throw e;
		}
		catch (InvalidKeySpecException e)
		{
			log.error("invalid key spec", e);
			throw e;
		}
		catch (InvalidKeyException e)
		{
			log.error("invalid key", e);
			throw e;
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error("not support algorithm of secret[3des]", e);
			throw e;
		}
		catch (NoSuchPaddingException e)
		{
			log.error("no such padding", e);
			throw e;
		}
		catch (IllegalBlockSizeException e)
		{
			log.error("illegal block size", e);
			throw e;
		}
		catch (BadPaddingException e)
		{
			log.error("bad padding", e);
			throw e;
		}
	}

	/**
	 * 3DES解密，异常由调用者处理
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidKeySpecException
	 */
	public static byte[] decrypt(byte[] content, String password) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException, InvalidKeySpecException
	{
		try
		{
			DESedeKeySpec dks = new DESedeKeySpec(password.getBytes("UTF-8"));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, securekey);
			return cipher.doFinal(content);
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error("not support algorithm of secret[3des]", e);
			throw e;
		}
		catch (NoSuchPaddingException e)
		{
			log.error("no such padding", e);
			throw e;
		}
		catch (InvalidKeyException e)
		{
			log.error("invalid key", e);
			throw e;
		}
		catch (IllegalBlockSizeException e)
		{
			log.error("illegal block size", e);
			throw e;
		}
		catch (BadPaddingException e)
		{
			log.error("bad padding", e);
			throw e;
		}
		catch (UnsupportedEncodingException e)
		{
			log.error("unsupported encoding", e);
			throw e;
		}
		catch (InvalidKeySpecException e)
		{
			log.error("invalid key spec", e);
			throw e;
		}
	}

	/**
	 * Base64编码,异常由调用者处理
	 * 
	 * @param src
	 * @return
	 */
	public static String encode(byte[] src)
	{
		return org.apache.commons.codec.binary.Base64.encodeBase64String(src);
	}

	/**
	 * Base64解码,异常由调用者处理
	 * 
	 * @param src
	 * @return
	 */
	public static byte[] decode(String src)
	{
		return org.apache.commons.codec.binary.Base64.decodeBase64(src);
	}
	public static String decode(byte[] src)
	{
		return org.apache.commons.codec.binary.Base64.encodeBase64String(src);
	}

	/**
	 * 用私钥对信息生成数字签名,异常由调用者处理
	 * 
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception
	{
		// 解密由base64编码的私钥
//		byte[] keyBytes = Base64Utils.decode(privateKey);
		byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes(DEFAULT_CHARSET));

		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);

		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);

		//return Base64Utils.encode(signature.sign());
		return new String(Base64.encodeBase64(signature.sign()));
	}

	/**
	 * 校验数字签名,异常由调用者处理
	 * 
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * 
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 * 
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception
	{

		// 解密由base64编码的公钥
//		byte[] keyBytes = Base64Utils.decode(publicKey);
		byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes(DEFAULT_CHARSET));

		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);

		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);

		// 验证签名是否正常
//		return signature.verify(Base64Utils.decode(sign));
		return signature.verify(Base64.decodeBase64(sign.getBytes(DEFAULT_CHARSET)));
	}
	public static boolean verify(byte[] data, Key publicKey, String sign) throws Exception
	{
		PublicKey pubKey = (PublicKey)publicKey;

		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);
		// 验证签名是否正常
		return signature.verify(Base64.decodeBase64(sign.getBytes(DEFAULT_CHARSET)));
	}
	
	
	public static void main(String[] args) {
		String filePath="E:/ssl/httpsCer/httpsSccba/user_sccba.pfx";
		//String publicKeyPath="E:/ssl/httpsCer/httpsSccba/publicKey.txt";
		//String privateKeyPath="E:/ssl/httpsCer/httpsSccba/privateKey.txt";
		
		try {
			//InputStream in =getInputStream(publicKeyPath);
			//String publicKey =loadKeyString(in);
			//Key pKey = loadPublicKey(publicKey);
			
			//读取公钥文件
//			Key pubKey = loadPublicKey(loadKeyString(getInputStream(publicKeyPath)));
			String puKey ="";
			
			//读取私钥文件
//			Key priKey = loadPrivateKey(loadKeyString(getInputStream(privateKeyPath)));
			String prKey ="";
			
			String testXml="privateSignEndpublicVerify";
			
			String sign= sign(testXml.getBytes(DEFAULT_CHARSET),prKey);
			
//			boolean flag =verify(testXml.getBytes(DEFAULT_CHARSET), puKey, sign);
//			System.out.println("flag:::::"+flag);
			
			String pwd= "9e0480bfc8934bc2bb71a787";
			String str = "hello world";
			String str2 = "hello world";
			
			str2 =new String(encrypt(str2.getBytes(DEFAULT_CHARSET), pwd),DEFAULT_CHARSET);
			System.out.println(str2);
			str2 = encode(str2.getBytes(DEFAULT_CHARSET));
			
			System.out.println(str2);
			str =encode(encrypt(str.getBytes(DEFAULT_CHARSET), pwd));
			System.out.println("3des加密base64转码后的字符串::::"+str);
			
			String enData = "uyfIApqiwmGtMrqO5KSW3g==";
			
			enData = new String(decrypt(decode(enData), pwd));
			System.out.println("base64解密3des解密后的字符串::::"+enData);
			
		//	------------------------------------------------------------------
		
		//	https://60.208.131.91:12866/open/oauth/token?client_id=Z5POT00&client_secret=Z5POT00&grant_type=client_credentials


				//1：获取password(随机密码)：（获取随机密码的java方式）
//						String password = "4beb57e9-1965-444f-9bcf-81d5c5079265";
//						//2：password加密src（EncryptUtil.encrypt(byte[] src, String password)），得到jsondata
//				        byte[] jsondata = EncryptUtil.encrypt(data.getBytes("UTF-8"),password);
//				        //3：base64转码jsonData，得到 jsonData_Base64；
//				        String jsonDataBase64 = EncryptUtil.encode(jsondata);
//				        //4: 处理随机密码：	
//				        //4.1：EncryptUtil.encrypt(Key key, byte[] password)用服务端提供的公钥加密随机密码,得到 password_encode
//						Key pubKey = EncryptUtil.loadPublicKey("服务端提供的公钥");
//				        byte[] passwordEncode = EncryptUtil.encrypt(pubKey, password.getBytes("UTF-8"));
//				        //	4.2：base64转码随机密码password_encode, 得到password_base64
//				        String passwordBase64 = EncryptUtil.encode(passwordEncode);
//				        //	4.3：计算加密后的随机密码的长度
//				        String keyRandomLength = null;
//				        int temp = removeSpaceAndEnter(passwordBase64).length();
//				        if( temp<1000 ){
//				        	keyRandomLength = "0"+temp;
//				        }else{
//				        	keyRandomLength = Integer.toString(temp);
//				        }
//				        //5:签名数据signature：
//				        //	5.1：EncryptUtil.sign()用客户端自己的私钥签名src ，得到 jsonData_signature；
//				   
//				        Key priKey = EncryptUtil.loadPrivateKey("用自己渠道的私钥"); 
//				        String jsonDataSignature= EncryptUtil.sign(data.getBytes("UTF-8"),EncryptUtil.encode(priKey.getEncoded()));
//				        //	5.2：base64转码jsonData_signature, 得到 signature_base64；
//				        String signatureBase64 = EncryptUtil.encode(jsonDataSignature.getBytes("UTF-8"));
//				        //6：拼接jsondata：
//				        //	0(第一位固定零)+0117(处理后的随机密码的长度;固定长度4位；不足的前面用0补齐) + 处理后的随机密码password_base64 + 加密后的jsonData_Base64
//				        String jsondataString = "0" + keyRandomLength + passwordBase64 + jsonDataBase64;
//				        System.out.println("String jsonData = \""+jsondataString+"\";");
//				        System.out.println("String signature = \""+signatureBase64+"\";");

			
		//
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


}
