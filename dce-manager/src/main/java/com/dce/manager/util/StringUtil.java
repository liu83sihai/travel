package com.dce.manager.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * 工具类
 * @author wanglfmf
 * @date 2017年4月28日
 */ 
public class StringUtil {

    public static String[] spiltString(String s){
        if (StringUtils.isBlank(s)){
            return null;
        }
        String regEx = "[' ']+";
        return s.split(regEx);
    }

    public static String dealStringTrim(String s){
        String regEx = "[' ']+"; // 一个或多个空格
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);
        return m.replaceAll(",").trim();
    }

    /**
     * 密码长度8~16位数字、字母、符号至少包含2种 
     * @param pwd
     * @return  
     */
    public static boolean checkPassword(String pwd) {
        if (null == pwd) {
            return false;
        }
        if (pwd.length() < 8 || pwd.length() > 16) {
            return false;
        }
        Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher m = p.matcher(pwd);
        boolean result = m.find();
        int count = 0;
        if (result) {
            count++;
        }

        Pattern p1 = Pattern.compile("[0-9]");
        m = p1.matcher(pwd);
        result = m.find();
        if (result) {
            count++;
        }

        Pattern p2 = Pattern.compile("[^\\w]");
        m = p2.matcher(pwd);
        result = m.find();
        if (result) {
            count++;
        }

        if (count >= 2) {
            return true;
        }
        return false;
    }

    /**
     * 数字转成double类型
     * 
     * @param v1
     * @return
     */
    public static double strToDouble(String v1) {
        return strToDouble(v1, 0);
    }

    /**
     * 
     * @param v1
     *            字符的数字转double
     * @param defaultVal
     *            默认值
     * @return
     */
    public static double strToDouble(String v1, double defaultVal) {
        if (null == v1 || "".equals(v1)) {
            return defaultVal;
        }

        BigDecimal b1 = new BigDecimal(v1);
        return b1.doubleValue();
    }

    /**
     * 数字转成long类型
     * 
     * @param v1
     * @return
     */
    public static long strToLong(String v1) {
        return strToLong(v1, 0);
    }

    /**
     * 
     * @param v1
     *            字符的数字转long
     * @param defaultVal
     *            默认值
     * @return
     */
    public static long strToLong(String v1, long defaultVal) {
        if (null == v1 || "".equals(v1)) {
            return defaultVal;
        }

        BigDecimal b1 = new BigDecimal(v1);
        return b1.longValue();
    }

    /**
     * 数字转成long类型
     * 
     * @param v1
     * @return
     */
    public static int strToInt(String v1) {
        return strToInt(v1, 0);
    }

    /**
     * 
     * @param v1
     *            字符的数字转long
     * @param defaultVal
     *            默认值
     * @return
     */
    public static int strToInt(String v1, int defaultVal) {
        if (null == v1 || "".equals(v1)) {
            return defaultVal;
        }

        BigDecimal b1 = new BigDecimal(v1);
        return b1.intValue();
    }

    /**
     * @Description 描述方法作用
     * @author huangzl QQ: 272950754
     * @date 2016年6月29日
     * @param object
     * @return
     */
    public static String getValue(Object object) {
        if (object == null) {
            return "";
        } else {
            return object.toString();
        }
    }

    /**
     * 处理字符为空的时候返回默认值
     * 
     * @param str
     * @param defaultValue
     * @return
     * @author: zhangyunhmf
     * @date: 2014年9月23日下午2:21:31
     */
    public static String strToStr(String str, String defaultValue) {
        String Result = defaultValue;

        if ((str != null) && (!str.isEmpty())) {
            Result = str;
        }

        return Result;
    }

    public static String FilteSqlInAndScript(String input) {
        if (StringUtils.isBlank(input)) {
            return "";
        }
        if (StringUtils.isNumeric(input)) {
            return input;
        }

        //需要过滤的关键字
        String[][] tokens = new String[][] { 
            { "update", "ｕｐｄａｔｅ" }, 
            { "select", "ｓｅｌｅｃｔ" }, 
            { "drop", "ｄｒｏｐ" }, 
            { "delete", "ｄｅｌｅｔｅ" },
            { "exec", "ｅｘｅｃ" }, 
            { "create", "ｃｒｅａｔｅ" }, 
            { "where", "ｗｈｅｒｅ" }, 
            { "truncate", "ｔｒｕｎｃａｔｅ" }, 
            { "insert", "ｉｎｓｅｒｔ" }, 
            //{ ">", "&gt;" },
            //{ "<", "&lt;" }, 
            { " or ", " ｏｒ " },
            { " and ", " ａｎｄ "},
            { " from ", " ｆｒｏｍ "}};

        for (String[] token : tokens) {
            input = replace(input, token);
        }
        
        return input;
//        return input.replaceAll("update", "ｕｐｄａｔｅ").replaceAll("select", "s e l e c t").replaceAll("SELECT", "s e l e c t")
//                .replaceAll("UPDATE", "ｕｐｄａｔｅ").replaceAll("drop", "ｄｒｏｐ").replaceAll("DROP", "ｄｒｏｐ").replaceAll("delete", "ｄｅｌｅｔｅ")
//                .replaceAll("DELETE", "ｄｅｌｅｔｅ").replaceAll("exec", "ｅｘｅｃ").replaceAll("EXEC", "ｅｘｅｃ").replaceAll("create", "ｃｒｅａｔｅ")
//                .replaceAll("CREATE", "ｃｒｅａｔｅ").replaceAll("execute", "ｅｘｅｃｕｔｅ").replaceAll("EXECUTE", "ｅｘｅｃｕｔｅ").replaceAll("where", "ｗｈｅｒｅ")
//                .replaceAll("WHERE", "ｗｈｅｒｅ").replaceAll("truncate", "ｔｒｕｎｃａｔｅ").replaceAll("TRUNCATE", "ｔｒｕｎｃａｔｅ").replaceAll("insert", "ｉｎｓｅｒｔ")
//                .replaceAll("INSERT", "ｉｎｓｅｒｔ");
        
    }
    
    private static String replace(String input, String[] token) {
        while (input.toLowerCase().indexOf(token[0]) > -1) {
            int beginIndex = input.toLowerCase().indexOf(token[0]);
            int endIndex = beginIndex + token[0].length();

            input = input.substring(0, beginIndex) + token[1] + input.substring(endIndex);
        }

        return input;
    }
    
    /**
     * 过滤用户输入要保护应用程序免遭跨站点脚本编制的攻击，
     * 请通过 将敏感字符转换为其对应的字符实体来清理HTML。
     * 这些是HTML 敏感字符：< > " ' % ; )  ( & +  
     * 以下示例通过将敏感字符转换为其对应的字符实体，来过滤指定字符 串   
     */
    public static String scriptingFilter(String value) {
        if (value == null) {
            return null;
        }
        StringBuffer result = new StringBuffer(value.length());
        for (int i = 0; i < value.length(); ++i) {
            switch (value.charAt(i)) {
            case '<':
                result.append("&lt;");
                break;
            case '>':
                result.append("&gt;");
                break;
            case '"':
                result.append("&quot;");
                break;
            case '\'':
                result.append("&#39;");
                break;
            case '%':
                result.append("&#37;");
                break;
            case ';':
                result.append("&#59;");
                break;
            case '(':
                result.append("&#40;");
                break;
            case ')':
                result.append("&#41;");
                break;
            case '&':
                result.append("&amp;");
                break;
            case '+':
                result.append("&#43;");
                break;
            default:
                result.append(value.charAt(i));
                break;
            }
        }
        return new String(result);
    }

    public static String fullString(long i, int length) {
        String result = String.valueOf(i);
        while (result.length() < length) {
            result = "0" + result;
        }
        return result;
    }

    /**
     * @Description: WML 编码
     * @param str
     * @return
     * @author: zhanbmf
     * @date 2015-3-29 下午5:42:22
     */
    public static String encodeWML(String str) {
        if (str == null)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
            case 36: // '$'
            case 255:
                break;

            case 38: // '&'
                sb.append("&amp;");
                break;

            case 9: // '\t'
                sb.append("  ");
                break;

            case 60: // '<'
                sb.append("&lt;");
                break;

            case 62: // '>'
                sb.append("&gt;");
                break;

            case 34: // '"'
                sb.append("&quot;");
                break;

            case 10: // '\n'
                sb.append("<br/>");
                break;

            default:
                if (c < 0 || c > '\037')
                    sb.append(c);
                break;
            }
        }

        return sb.toString();
    }

    /**
     * @Description: net unicode解码
     * @param str
     * @return
     * @author: zhanbmf
     * @date 2015-3-29 下午5:43:12
     */
    public static String decodeNetUnicode(String str) {
        String pStr = "&#(\\d+);";
        Pattern p = Pattern.compile(pStr);
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        String s;
        for (; m.find(); m.appendReplacement(sb, Matcher.quoteReplacement(s))) {
            String mcStr = m.group(1);
            int charValue = NumberUtils.toInt(mcStr, -1);
            s = charValue <= 0 ? "" : (new StringBuilder(String.valueOf((char) charValue))).toString();
        }

        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * @Description: sql编码
     * @param sql
     * @return
     * @author: zhanbmf
     * @date 2015-3-29 下午5:44:01
     */
    public static String encodeSQL(String sql) {
        if (sql == null)
            return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);
            switch (c) {
            case 92: // '\\'
                sb.append("\\\\");
                break;

            case 13: // '\r'
                sb.append("\\r");
                break;

            case 10: // '\n'
                sb.append("\\n");
                break;

            case 9: // '\t'
                sb.append("\\t");
                break;

            case 8: // '\b'
                sb.append("\\b");
                break;

            case 39: // '\''
                sb.append("''");
                break;

            case 34: // '"'
                sb.append("\\\"");
                break;

            default:
                sb.append(c);
                break;
            }
        }

        return sb.toString();
    }

}
