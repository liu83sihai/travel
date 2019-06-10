package com.dce.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@RunWith(JUnit4ClassRunner.class)  
@ContextConfiguration(locations = { "classpath:config/applicationContext.xml" })
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {
    static {

        System.setProperty("catalina.home", "G:\\run_time\\tomcat\\apache-tomcat-7.0.62");
    }
    
    
}