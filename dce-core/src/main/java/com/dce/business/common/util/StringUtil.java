package com.dce.business.common.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
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

    public static String FilteSqlInAndScript(String input) {
        if (StringUtils.isBlank(input)) {
            return "";
        }
        if (StringUtils.isNumeric(input)) {
            return input;
        }

        //需要过滤的关键字
        String[][] tokens = new String[][] { { "update", "ｕｐｄａｔｅ" }, { "select", "ｓｅｌｅｃｔ" }, { "drop", "ｄｒｏｐ" }, { "delete", "ｄｅｌｅｔｅ" },
            { "exec", "ｅｘｅｃ" }, { "create", "ｃｒｅａｔｅ" }, { "where", "ｗｈｅｒｅ" }, { "truncate", "ｔｒｕｎｃａｔｅ" }, { "insert", "ｉｎｓｅｒｔ" },
            //{ ">", "&gt;" },
            //{ "<", "&lt;" }, 
            { " or ", " ｏｒ " }, { " and ", " ａｎｄ " }, { " from ", " ｆｒｏｍ " } };

        for (String[] token : tokens) {
            input = replace(input, token);
        }

        return input;
    }

    private static String replace(String input, String[] token) {
        while (input.toLowerCase().indexOf(token[0]) > -1) {
            int beginIndex = input.toLowerCase().indexOf(token[0]);
            int endIndex = beginIndex + token[0].length();

            input = input.substring(0, beginIndex) + token[1] + input.substring(endIndex);
        }

        return input;
    }
}
