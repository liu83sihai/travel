package com.dce.business.service.groovy;

import java.util.HashMap;
import java.util.Map;


/**
 * 公式计算
 * 
 */
public class GroovyParse {
	/**
	 * 公式解析计算
	 */
	public static Object executeScript(String formula, Map<String, Object> map) {
		//ApplicationContext context = ApplicationContextUtil.getContext();
		//GroovyScriptEngine groovyScriptEngine = context.getBean(GroovyScriptEngine.class);
		GroovyScriptEngine groovyScriptEngine = new GroovyScriptEngine();
		Object value = groovyScriptEngine.executeObject(formula, map);
		return value;
	}

	public static void main(String[] args) {
		String formula = "println 'Hello World!';po = '9s00';return (a * b);";
		Map map = new HashMap();
		map.put("a", 900);
		map.put("b", 10);
		GroovyScriptEngine groovyScriptEngine = new GroovyScriptEngine();
		Object value = groovyScriptEngine.executeObject(formula, map);
		System.out.println(value);
		System.out.println(groovyScriptEngine.binding.getVariable("po"));
	}

}
