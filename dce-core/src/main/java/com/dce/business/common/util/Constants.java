package com.dce.business.common.util;
/**
 * 迁移 到manager-api
 * 2017年8月31日14:48:26
 * @author tangkmf
 *
 */
public class Constants {

    public static final String EXECUTE_STATUS = "executeStatus";
    public static final String EXECUTE_SUCCESS = "1";
    public static final String EXECUTE_FAILURE = "0";

    /**
     * 成功状态码
     */
    public static final int SUCCESS = 1;

    /**
     * 失败状态码
     */
    public static final int FAIL = -1;

    /**
     * 不合法状态码
     */
    public static final int INVALID = -2;

    /**
     * 已经存在
     */
    public static final int HASEXSIT=-3;

    /**
     * 不存在状态码
     */
    public static final int NOTEXIT=-4;

    public static String FAILURESTRING = "failure";

    public static String COOKIE_DOMAIN = ".hehenian.com";

    public static String SESSION_KEY = "enFlag";

    public static String UNLOGIN_KEY="sid";

    public static String AUTHCODE_PREFIX="auth:";

    public static String CURRENT_USER_ID = "currentUserId";

    public static final String SESSION_LOGIN_TIME = "loginTime";

    public static final String SESSION_LOGIN_IP = "loginIp";

    public static final String SESSION_CREATE_TIME = "createTime";

    public static final String SESSION_RECOMMAND="recommand";

    public static final String COOKIE_VALID_EMAIL="validEMAIL";
    public static final String COOKIE_VALID_PHONE="validPhone";

    /**
     * 权限标签
     */
    public static final String AUTHORITIES = "authorities";

    /**
     * 验证不通过页面
     */
    public static final String INVALIDPAGE="invalid";

    /**
     * 登录状况常量
     *
     *
     */
    public static class LoginInfoStatus{

        /**
         * 用户ID
         */
        public static final int USERID=1;

        /**
         * 手机或者邮箱
         */
        public static final int PHONEOREMAIL=2;
    }

    public static final String PRE_HOUSE_APP_LOGIN_USER_ID= "house_app_login_user_id";

    /**
     * TOKEN 验证是否通过
     */
    public static final String TOKEN_STATUS="TOKEN_STATUS";

    /**
     * TOKEN 验证通过
     */
    public static final String TOKEN_STATUS_OK="TOKEN_OK";

    /**
     * 登陆标记的cookie名， 缓存到微信的客户端，cookie时长 内免登陆
     */
    public static final String LOGIN_FLAG="loginFlag";

    /**
     * 用户客户端平台： android;ios;pad;wx;homepage;
     * android app,  ios app,  pad app, wx 微信，homepage 官网
     */
    public static final String PLATFORM="platform";

    /**
     * 保存在session的用户对象
     */
    public static final String USER_ACCOUNT = "userAccount";

    /**
     * 保存在session的第三方用户对象
     */
    public static final String USER_THIRD = "userThird";

    /**
     * 保存在session的第三方用户对象,新
     */
    public static final String USER_CHANNEL_THIRD = "userChannelThird";

    /**
     * 保存在session的第三方用户扩展对象
     */
    public static final String USER_EXTEND_THIRD = "userExtendThird";

    /**
     * 渠道编码
     */
    public static final String CHANNEL_CODE = "channelCode";

    /**
     * 用Mybatis做翻页时，需要传一个KEY="page"的翻页对象
     */
    public static final String  MYBATIS_PAGE = "page";


    /**
     * 字符串：空
     */
    public final static String EMPTY_STR  = "";

    /**
     * 半角字符：空格
     */
    public final static String BLANK_STR  = " ";

    /**
     * 半角字符：斜杠
     */
    public final static String SLASH      = "/";

    /**
     * 半角字符：反斜杠
     */
    public final static String BACKSLASH  = "\\";

    /**
     * 半角字符：点
     */
    public final static String POINT      = ".";

    /**
     * 半角字符：逗号
     */
    public final static String COMMA      = ",";

    /**
     * 半角字符：下划线
     */
    public final static String UNDERLINE  = "_";

    /**
     * 半角字符：中划线
     */
    public final static String MIDDLELINE = "-";

    /**
     * 数字：0
     */
    public final static byte   ZERO       = 0;

    /**
     * 数字：1
     */
    public final static byte   ONE        = 1;

    /**
     * 数字：-1
     */
    public final static byte   MINUS_ONE  = -1;

    /**
     * 字母：x
     */
    public final static String X          = "x";

    /**
     * 提前结清手续费率
     */
    public static final double PRE_REPAY_RATE          = 0.03d;
    // 按月付息到期还本
    public static final double PRE_REPAY_RATE2         = 0.02d;
    // 一次付息到期还本
    public static final double PRE_REPAY_RATE3         = 0d;

    /**
     * 提前结清手续费编码
     */
    public static final String FEE_CODE_PRE            = "901";
    // 咨询费
    public static final String FEE_CODE_CONSULT        = "902";
    // 放款手续费
    public static final String FEE_CODE_SERVICE_CHARGE = "903";
    // 罚息
    public static final String FEE_CODE_FX             = "603";
    // 扣除还款金额
    public static final String FEE_CODE_REPAY          = "604";

    /**
     * 投资信息管理费率
     *
     * @since 1.0.0
     */
    public static final String INVEST_FEE_RATE         = "investFeeRate";

    /**
     * 密码加密串
     */
    public static String PASS_KEY = "GDgLwwdK270Qj155xho8lyTp";

    /**
     * 注册验证验证码第二阶段用到
     */
    public static String REG_PASS_KEY = "BGgjghdnhdw2axho8lyTp";

    /**
     * 设置用户支付密码
     */
    public static String CONF_PAY_PASS_KEY = "BFsjgergfbhnjkmpaylyTp";
    /**
     * 支付时加密TOKEN用到
     */
    public static String PAY_PASS_KEY = "Bdfesd34gbggffgfpaylyTp";


    /**
     * 发送短信验证码加密串
     */
    public static String SMS_PASS_KEY = "GDgLwwdK270dcdcsdrdfdgwsGp";

    /** 消息提示KEY */
    public static final String MESSAGE_KEY  = "message";

    public static final String CONSTANT_USERID = "CONSTANT_USERID";//请求必传用户ID
    public static final String CONSTANT_TOKEN = "CONSTANT_TOKEN"; //请求必传TOKEN
    public static final String CONSTANT_TS = "CONSTANT_TS"; //请求必传时间戳
    public static final String CONSTANT_USERID_UUID = "CONSTANT_USERID_UUID";//用户token主城部分UUID存储规则  CONSTANT_USERID_UUID+user
    public static final int TOKEN_TIMEOUT_TIME = 10*60; //单位秒
    public static final int PAY_TOKEN_TIMEOUT_TIME = 30*60; //单位秒
    public static final int SEND_TOKEN_SMS_TIME = 2*60; //单位秒
    public static final int SEND_TOKEN_SMS_IP_COUNT_TIME = 24*60*60; //单位秒
    public static final int SEND_TOKEN_SMS_IP_COUNT = 500;//相同IP每天可出发短信发送次数

    public static final String IP_COUNT_CACHE_FLAG = "IP_COUNT_CACHE_FLAG";//每个IP地址每天发送总数短信
    public static final String FDX_SENDMOBILECODE = "FDX_SENDMOBILECODE";//房大仙短信
    public static final String HHYD_SENDMOBILECODE = "HHYD_SENDMOBILECODE";//合花易贷短信

    public static final int REQUEST_TYPE_IP_COUNT_TIME = 3*60; //180秒,即3分钟
    public static final int REQUEST_TYPE_IP_COUNT = 1000;//同一ip同一个请求在180秒内最多请求的次数 1000
    public static final int REQUEST_TYPE_IP_LOCK_TIME = 12*60*60; //单位秒，即锁12小时


}
