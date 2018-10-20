package ${basepackage}.business.actions.${classNameLower};

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.common.result.Result;
import com.dce.business.entity.notice.NoticeDo;

import ${basepackage}.business.service.${classNameLower}.I${className}Service;
import ${basepackage}.business.entity.${classNameLower}.${className}Do;
/**
 * ${TABLE_NAME}授权业务实现类
 *
 * @author  Fantasy
 * @date    generate
 * @version 1.0.0
 * @pageage ${basepackage}.business.actions.${className}Controller.java
 */
@RestController
@RequestMapping("/${classNameLower}")
public class ${className}Controller {
	private final static Logger logger = Logger.getLogger(${className}Controller.class);
	@Resource
	private I${className}Service ${classNameLower}Service;
	
	/** 
	 * @api {GET} /${classNameLower}/index.do ${TABLE_INFO}列表
	 * @apiName ${classNameLower}List
	 * @apiGroup ${classNameLower} 
	 * @apiVersion 1.0.0 
	 * @apiDescription ${TABLE_INFO}列表
	 *  
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {String} msg 返回成功信息
	 * @apiSuccess {String} code 返回成功编码
	<#list table.columns as column>		
	 *	@apiSuccess {${column.javaType}}  ${column.columnNameLower} ${column.remarks}
	</#list>
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "code": 0
	 *	"msg": 返回成功,
	 *	"data": {
	 *	    [
	 *			{
	 <#list table.columns as column>		
	 * 				${column.columnNameLower} ${column.remarks}
	 </#list>			 
	 *			}
	 *		]
	 *	  }
	 *	}
	 */ 
	 @RequestMapping("/index")
	 public Result<?> list() {
		 logger.info("查询${TABLE_INFO}...");
	
		 ${className}Do ${classNameLower}Do = new ${className}Do();
		 List<${className}Do> ${classNameLower}List = ${classNameLower}Service.select${className}(${classNameLower}Do);
		 List<Map<String, Object>> result = new ArrayList<>();
	        if (!CollectionUtils.isEmpty(${classNameLower}List)) {
	            for (${className}Do ${classNameLower} : ${classNameLower}List) {

	                Map<String, Object> map = new HashMap<>();
	                <#list table.columns as column>
	                map.put("${column.columnNameLower}", ${classNameLower}.get${column.columnName}());
	                </#list>
	                result.add(map);
	            }
	        }
		 return Result.successResult("查询成功", result);
	 }

}
