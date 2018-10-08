/**
 * Project Name:jcpt-encrypt <br>
 * File Name:PropertiesUtil.java <br>
 * Package Name:com.hehenian.encrypt.util <br>
 * Copyright (c) 2017, 深圳市彩付宝网络技术有限公司 All Rights Reserved.
 */

package com.dce.manager.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * ClassName: PropertiesUtil <br>
 * Description: 属性文件加载工具类
 * @author zhangyunhua
 * @Date 2017年10月23日下午3:04:05 <br>
 * @version
 */
public class PropertiesUtil {
    private static Logger logger = Logger.getLogger(PropertiesUtil.class);
    public static String DEFAULT_ENCODE = "UTF-8";
    
    private static Properties configProperties = new Properties();


    public static String getProperty(String key) {
        return configProperties.getProperty(key);
    }


    private static void loadProperties() {
        String catalinaHome = System.getProperty("catalina.home");
        if (StringUtils.isBlank(catalinaHome)) {
            throw new RuntimeException("catalina.home 为空");
        }

        catalinaHome = catalinaHome + "/conf/hehenian.properties";
        logger.info("属性catalina.home" + catalinaHome);
        try {
            configProperties.load(new BufferedInputStream(new FileInputStream(catalinaHome)));
        } catch (IOException e) {
            logger.error("hehenian.properties 加载失败");
            throw new RuntimeException("hehenian.properties 加载失败");
        }

    }

    static {
        loadProperties();
        if(configProperties == null){
        	logger.error("启动失败，不能加载属性配置文件");
        	throw new RuntimeException("启动失败，不能加载属性配置文件");
        }
    }
}
