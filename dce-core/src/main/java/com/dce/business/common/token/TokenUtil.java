package com.dce.business.common.token;

import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dce.business.common.redis.JedisClient;
import com.dce.business.common.util.SpringBeanUtil;

public class TokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    public static final String TS = "ts"; // 时间戳参数名
    public static final String USER_ID = "userId"; // 用户id参数名
    public static final String SIGN = "sign"; // 签名参数名
    public static final Long TOKEN_TIMEOUT_TIME = 300 * 60L; // token失效时间，单位秒
    private static final String TOKEN_PREFIX = "token#";

    public static String createToken(Integer userId) {
        String str = UUID.randomUUID().toString() + userId;
        String token = new String(Base64.encodeBase64(str.getBytes()));

        setToken(userId, token);
        return token;
    }

    public static boolean checkToken(String uri, Integer userId, String ts, String sign) {

        long currentTime = System.currentTimeMillis();
        long requestTime = Long.valueOf(ts);
        System.out.println("currentTime:" + currentTime);
        long diffTime = (currentTime - requestTime) / 7200*30;
        if (diffTime > TOKEN_TIMEOUT_TIME) {
            logger.info("用户登录失效， userId:" + userId);
            logger.info("系统时间：" + currentTime);
            logger.info("请求时间戳：" + ts);
            logger.info("失效时间：" + TOKEN_TIMEOUT_TIME);
            return false;
        }

        String token = getToken(userId);
        String checkSign = md5(userId, ts, uri, token);
        boolean result = checkSign.equals(sign);
        if (!result) { //记录日志，方便排查原因
        	logger.info("客户端地址："+uri);
            logger.info("用户登录失效， userId:" + userId);
            logger.info("token：" + token);
            logger.info("请求签名：" + sign);
            logger.info("系统加签：" + checkSign);
        }

        return result;
    }

    /**
     * 	从缓存中获取用户令牌
     * 
     * @param userId 用户id
     * @return
     */
    private static String getToken(Integer userId) {
        JedisClient jedisClient = SpringBeanUtil.getBean("jedisClient");
        String token = jedisClient.getString(TOKEN_PREFIX + userId);

        return token;
    }

    public static void deleteToken(Integer userId) {
        JedisClient jedisClient = SpringBeanUtil.getBean("jedisClient");
        jedisClient.delete(TOKEN_PREFIX + userId);
    }

    /**
     * 	更新令牌
     * 
     * @param userId 用户id
     * @param token 令牌
     */
    private static void setToken(Integer userId, String token) {
        logger.info("用户token变更，userId:" + userId);
        logger.info("新token:" + token);
        JedisClient jedisClient = SpringBeanUtil.getBean("jedisClient");
        jedisClient.setString(TOKEN_PREFIX + userId, token, -1);
    }

    private static String md5(Integer userId, String ts, String uri, String token) {
        return DigestUtils.md5Hex(token + ts + uri);
    }
    
    public static void main(String[] args) {
        String userId="6";
        String ts="1536056498196";
        String token="MmNmYTY2MzQtNTcxZi00MjRkLTkyODktZDRhNWQ5NDcyZGI4Ng==";
        
        System.out.println(md5(Integer.valueOf(userId), ts, "", token));
    }
}
