package com.dce.business.service.groovy;

import groovy.lang.GroovyShell;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Groovy脚本引擎
 *
 */
public class GroovyScriptEngine  {
	private Log logger = LogFactory.getLog(GroovyScriptEngine.class);
	public GroovyBinding binding = new GroovyBinding();

	public void execute(String script, Map<String, Object> vars) {
		executeObject(script, vars);
	}

	private void setParameters(GroovyShell shell, Map<String, Object> vars) {
		if (vars == null)
			return;
		Set<?> set = vars.entrySet();
		for (Iterator<?> it = set.iterator(); it.hasNext();) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) it.next();
			shell.setVariable((String) entry.getKey(), entry.getValue());
		}
	}

	public boolean executeBoolean(String script, Map<String, Object> vars) {
		Boolean rtn = (Boolean) executeObject(script, vars);
		return rtn.booleanValue();
	}

	public String executeString(String script, Map<String, Object> vars) {
		String str = (String) executeObject(script, vars);
		return str;
	}

	public int executeInt(String script, Map<String, Object> vars) {
		Integer rtn = (Integer) executeObject(script, vars);
		return rtn.intValue();
	}

	public float executeFloat(String script, Map<String, Object> vars) {
		Float rtn = (Float) executeObject(script, vars);
		return rtn.floatValue();
	}

	public Object executeObject(String script, Map<String, Object> vars) {
		this.logger.debug("执行:" + script);
		this.binding.clearVariables();
		GroovyShell shell = new GroovyShell(this.binding);
		setParameters(shell, vars);

		script = script.replace("&apos;", "'").replace("&quot;", "\"").replace("&gt;", ">").replace("&lt;", "<").replace("&nuot;", "\n").replace("&amp;", "&");

		Object rtn = shell.evaluate(script);
		//this.binding.clearVariables();
		return rtn;
	}

}