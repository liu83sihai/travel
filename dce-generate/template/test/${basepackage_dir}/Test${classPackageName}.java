/**
 * 
 */
package com.jzy.eip.module.masterdata;

import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ${basepackage}.domain.*;
import ${basepackage}.facade.*;


/**
 * @author LiuSiHai
 *
 * generate
 * 
 * ${basepackage}.${className}
 */
public class Test${classPackageName} {
	
	@Test
	public void Test${className}Add() {
		
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("*Context.xml");
		I${classPackageName}Facade ${classNameLower}Facde = (I${classPackageName}Facade)beanFactory.getBean("${classNameLower}Facade");
		
		${className} ${classNameLower}= new ${className}();
		<#list table.columns as column>
		<#if (column.isDateTimeColumn)>
		${classNameLower}.set${column.columnName}(new Date());		
		<#else >
		${classNameLower}.set${column.columnName}("test");
		</#if>
		</#list>
		
		${classNameLower}Facde.saveOrUpdate(${classNameLower});
	}
	
	@Test
	public void Test${className}Find() {
		
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("*Context.xml");
		I${classPackageName}Facade ${classNameLower}Facde = (I${classPackageName}Facade)beanFactory.getBean("${classNameLower}Facade");
		List<${className}> list = ${classNameLower}Facde.list();
		
		for (${className} ${classNameLower} : list) {
			System.out.println(${classNameLower}.getId());
		}
	}
}
