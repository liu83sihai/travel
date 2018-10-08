
/**
*    
* json帮助类
* 实现功能：转换 ServiceDataObject生成的json字符串为JSONArray对象 、
* 将json字符串转换成List<Map>(List中是Map类型)、
* 将单个json对象转化为Map类型、设置单属性的JSON对象、将json转为数组、
* 将Collection转换成JSON、将数组转化为JSON、将Map转为JSON、
* 将JSON转换成Map、将POJO转换成JSON、将JSON转换成POJO、将JSON转换成String、
* 将List 转换成JSON
* [Product]
*    cr.cw.com
* [Copyright]
*     Copyright 2017 Zhongzhi Tech. Co. Ltd. All Rights Reserved
* [FileName]
*     JSONUtils.java
* [Author]
*	yuanguangjie
* [Date]
*	2017年11月7日 下午11:40:03  
*/
package com.dce.business.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author yuanguangjie
 *
 */
@SuppressWarnings("unchecked")
public class JSONUtils {

	/**
	 * 1， 转换 ServiceDataObject生成的json字符串为JSONArray对象
	 * 
	 * @param datasJSON
	 * @return
	 */
	public static JSONArray createJSONArray(String datasJSON) {
		String datas = JSONUtils.json2String(datasJSON, "datas");
		JSONArray results = new JSONArray();
		JSONArray datasJSONArray = JSONArray.fromObject(datas);
		Iterator<JSONObject> iterator = datasJSONArray.iterator();
		while (iterator.hasNext()) {
			JSONObject data = (JSONObject) iterator.next();
			JSONObject eachDataObject = data.getJSONObject("data");
			results.element(eachDataObject);
		}
		return results;
	}

	/**
	 * 将json字符串转换成List<Map>(List中是Map类型).
	 * 
	 * @param json
	 *            the json
	 * @return List<Map>
	 * 
	 */
	public static List<Map<String, Object>> jsonToList(String json) {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		String JSON = json.substring(1, json.length() - 1);
		JSON = JSON.replaceAll("},", "}^");
		StringTokenizer strTokenizer = new StringTokenizer(JSON, "^");
		while (strTokenizer.hasMoreTokens()) {
			String token = strTokenizer.nextToken();
			Map<String, Object> paraMap = getMap4Json(token);
			listMap.add(paraMap);
		}

		return listMap;
	}

	/**
	 * 将单个json对象转化为Map类型.
	 * 
	 * @param jsonString
	 *            the json string
	 * @return the 4Json
	 * 
	 */
	public static Map<String, Object> getMap4Json(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		String key;
		Object value;
		Map<String, Object> valueMap = new HashMap<String, Object>();
		Iterator<String> keyIter = jsonObject.keys();
		while (keyIter.hasNext()) {
			key = keyIter.next();
			value = String.valueOf(jsonObject.get(key));
			valueMap.put(key, value);
		}
		return valueMap;
	}

	/**
	 * 设置单属性的JSON对象.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return {a:1,b:8}
	 * 
	 */
	public static String string2json(String key, String value) {
		JSONObject object = new JSONObject();
		object.put(key, value);
		return object.toString();
	}

	/**
	 * 将json转为数组.
	 * 
	 * @param json
	 *            the json
	 * @param valueClz
	 *            the value clz
	 * @return 对应的数组
	 * 
	 */
	public static Object json2Array(String json, Class<?> valueClz) {
		JSONArray jsonArray = JSONArray.fromObject(json);
		return JSONArray.toArray(jsonArray, valueClz);
	}

	/**
	 * 将Collection转换成JSON.
	 * 
	 * @param object
	 *            the object
	 * @return the string
	 * @param:object为集合
	 */
	public static String collection2json(Object object) {
		JSONArray jsonArray = JSONArray.fromObject(object);
		return jsonArray.toString();
	}

	/**
	 * 将数组转化为JSON.
	 * 
	 * @param object
	 *            the object
	 * @return the string
	 * 
	 */
	public static String array2json(Object object) {
		JSONArray jsonArray = JSONArray.fromObject(object);
		return jsonArray.toString();
	}

	/**
	 * 将Map转为JSON.
	 * 
	 * @param object
	 *            the object
	 * @return the string
	 * 
	 */
	public static String map2json(Map<String, Object> object) {
		JSONObject jsonObject = JSONObject.fromObject(object);
		return jsonObject.toString();
	}

	/**
	 * 将JSON转换成Map.
	 * 
	 * @param keyArray
	 *            the key array
	 * @param json
	 *            the json
	 * @param valueClz
	 *            the value clz
	 * @return the map
	 * @param:valueClz为Map中value的Class,
	 * @param:keyArray为Map的key
	 */
	@SuppressWarnings("rawtypes")
	public static Map json2Map(Object[] keyArray, String json, Class<?> valueClz) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map classMap = new HashMap();
		for (int i = 0; i < keyArray.length; i++) {
			classMap.put(keyArray[i], valueClz);
		}
		return (Map) JSONObject.toBean(jsonObject, Map.class, classMap);
	}

	/**
	 * 将POJO转换成JSON.
	 * 
	 * @param object
	 *            the object
	 * @return the string
	 * 
	 */
	public static String bean2json(Object object) {
		JSONObject jsonObject = JSONObject.fromObject(object);
		return jsonObject.toString();
	}

	/**
	 * 将JSON转换成POJO.
	 * 
	 * @param json
	 *            the json
	 * @param beanClz
	 *            the bean clz
	 * @return the object
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public static Object json2Object(String json, Class beanClz) {
		return JSONObject.toBean(JSONObject.fromObject(json), beanClz);
	}

	/**
	 * 从json对象集合表达式中得到一个java对象列表
	 * 
	 * @param jsonString
	 * @param pojoClass
	 * @return
	 */
	public static List getList4Json(String jsonString, Class pojoClass) {

		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;

		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {

			jsonObject = jsonArray.getJSONObject(i);
			pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			list.add(pojoValue);

		}
		return list;

	}

	/**
	 * 将JSON转换成String.
	 * 
	 * @param json
	 *            the json
	 * @param key
	 *            the key
	 * @return the string
	 */
	public static String json2String(String json, String key) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		return jsonObject.get(key).toString();
	}

	/**
	 * 将List 转换成JSON.
	 * 
	 * @param list
	 *            the list
	 * @return the string
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public static String list2json(List list) {
		JSONArray jsonArray2 = JSONArray.fromObject(list);
		return jsonArray2.toString();
	}

}