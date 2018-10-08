package com.dce.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({ "classpath:config/applicationContext.xml","classpath:config/dispatcher-servlet.xml"})
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {
	static {
		System.setProperty("catalina.home", "C:/java/apache-tomcat-8.0.39");
	}

	@Test
	@Rollback(true)
	public void testHello() {
		System.out.println("helloworld");
	}
}
