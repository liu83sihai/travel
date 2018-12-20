package com.dce.business.actions.pay;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.wxPay.util.HttpClientUtils;


public class WXUtil {
	
    private final static Logger logger = Logger.getLogger(WXUtil.class);

    private static final String openIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";//获取openId接口url地址

    /**
     * @param code 微信里打开的url带回的参数：获取网页授权凭证code
     * @param request
     * @param appId
     * @param secret
     * @return
     */
    public static WeiXinOauth2TokenDo pickOpenId(String code,String appId,String secret) {
        logger.info("获取网页授权凭证code:" + code);

        String requestUrl = openIdUrl.replaceAll("APPID", appId).replaceAll("SECRET", secret);
        if (StringUtils.isNotBlank(code)) {
            requestUrl = openIdUrl.replaceAll("APPID", appId).replaceAll("SECRET", secret).replaceAll("CODE", code);
        }
        WeiXinOauth2TokenDo weiXinOauth2TokenDo = null;
        if (!"authdeny".equals(code)) {
            weiXinOauth2TokenDo = getOauth2AccessTokenDo(requestUrl);
        }

        logger.info("获取网页授权凭证:" + JSON.toJSONString(weiXinOauth2TokenDo));
        return weiXinOauth2TokenDo;
    }

    private static WeiXinOauth2TokenDo getOauth2AccessTokenDo(String requestUrl) {
        WeiXinOauth2TokenDo wat = new WeiXinOauth2TokenDo();
        try {
            JSONObject jsonObject = HttpClientUtils.httpsGetRequest(requestUrl);
            logger.info("请求微信获取openId返回:"+jsonObject.toJSONString());
            if (null != jsonObject) {
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInteger("expires_in")==null?7200:jsonObject.getInteger("expires_in"));
                wat.setRefeshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            }
        } catch (Exception e) {
            logger.info("获取网页授权凭证失败 :", e);
        }
        return wat;
    }
}
