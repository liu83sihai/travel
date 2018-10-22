package ${basepackage}.business.actions.${classNameLower};

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.common.result.Result;
import com.dce.business.entity.activity.ActivityDo;
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
	 *  @apiDefine ${classNameLower}Succes
	 <#list table.columns as column>		
	 *	@apiSuccess {${column.javaType}}  ${column.columnNameLower} ${column.remarks}
	</#list>
	 */
	
	/**
	 *  @apiDefine ${classNameLower}Param
	 <#list table.columns as column>		
	 *	@apiParam {${column.javaType}}  ${column.columnNameLower} ${column.remarks}
	</#list>
	 */
	
	/** 
	 * @api {GET} /${classNameLower}/index.do ${TABLE_INFO}列表
	 * @apiName ${classNameLower}List
	 * @apiGroup ${classNameLower} 
	 * @apiVersion 1.0.0 
	 * @apiDescription ${TABLE_INFO}列表
	 *  
	 * @apiUse pageParam  
	 
	 * @apiUse activitySucces  
	 * @apiUse RETURN_MESSAGE
	
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
	 
	 /** 
	  * @api {GET} /${classNameLower}/getId.do 获取${TABLE_INFO}
	  * @apiName get${className}
	  * @apiGroup ${classNameLower} 
	  * @apiVersion 1.0.0 
	  * @apiDescription 获取${TABLE_INFO}
	  *  
	  *	@apiParam  {java.lang.Integer}  id ${TABLE_INFO}ID
	 
	  * @apiUse ${classNameLower}Succes  
	  * @apiUse RETURN_MESSAGE
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
	 @RequestMapping("/getId")
	 public Result<?> getId(${className}Do ${classNameLower}Do) {
		 logger.info("获取${TABLE_INFO}...");
		 if(null == ${classNameLower}Do.getId() || 0 == ${classNameLower}Do.getId()){
			 return Result.failureResult("${TABLE_INFO}ID不能为空!");
		 }
		 
		 ${className}Do ${classNameLower} = ${classNameLower}Service.getById(${classNameLower}Do.getId());
		 
		 return Result.successResult("获取${TABLE_INFO}成功", ${classNameLower});
	 }
	 
	 /** 
		 * @api {POST} /${classNameLower}/add.do 添加${TABLE_INFO}
		 * @apiName add${className}
		 * @apiGroup ${classNameLower} 
		 * @apiVersion 1.0.0 
		 * @apiDescription 添加${TABLE_INFO}
		 *  
		 * @apiUse  ${classNameLower}Param 
		 *  
		 * @apiUse  ${classNameLower}Succes  
		 * @apiUse RETURN_MESSAGE
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
	 @RequestMapping(value = "/add", method = RequestMethod.POST)
	 public Result<?> add( ${className}Do  ${classNameLower}Do,HttpServletRequest request, HttpServletResponse response) {
		 
//		 if(null == ${className}Do.getUserId() || 0 == ${className}Do.getUserId()){
//			 return Result.failureResult("用户ID不能为空!");
//		 }
//		 
//		 if(StringUtils.isBlank(${className}Do.getContent())){
//			 return Result.failureResult("内容为空!");
//		 }
//		 activityDo.setStatus(1);
//		 activityDo.setCreateDate(new Date(System.currentTimeMillis()));
//		 activityDo.setCreateName("前台增加${TABLE_INFO}");
		 ${classNameLower}Service.add${className}(${classNameLower}Do);
		 return Result.successResult("${TABLE_INFO}增加成功",${classNameLower}Do);
	 }
	 
	 /** 
	  * @api {POST} /${classNameLower}/edit.do 修改${TABLE_INFO}
	  * @apiName edit${className}
	  * @apiGroup ${classNameLower} 
	  * @apiVersion 1.0.0 
	  * @apiDescription 修改${TABLE_INFO}
	  *  
	  * @apiUse  ${classNameLower}Param 
	  *  
	  * @apiUse  ${classNameLower}Succes  
	  * @apiUse RETURN_MESSAGE
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
	 @RequestMapping(value = "/edit", method = RequestMethod.POST)
	 public Result<?> edit( ${className}Do  ${classNameLower}Do,HttpServletRequest request, HttpServletResponse response) {
		 
//		 if(null == ${className}Do.getId() || 0 == ${className}Do.getId()){
//			 return Result.failureResult("ID不能为空!");
//		 }
//		 
//		 activityDo.setModifyDate(new Date(System.currentTimeMillis()));
//		 activityDo.setModifyName("前台修改风采");
		 ${classNameLower}Service.update${className}ById(${classNameLower}Do);
		 return Result.successResult("${TABLE_INFO}修改成功",${classNameLower}Do);
	 }
	 
	 /** 
	  * @api {POST} /${classNameLower}/del.do 删除${TABLE_INFO}
	  * @apiName del${className}
	  * @apiGroup ${classNameLower} 
	  * @apiVersion 1.0.0 
	  * @apiDescription 删除${TABLE_INFO}
	  *  
	  * @apiUse  ${classNameLower}Param 
	  *  
	  * @apiUse RETURN_MESSAGE
	  * @apiSuccessExample Success-Response: 
	  *  HTTP/1.1 200 OK 
	  * {
	  *  "code": 0
	  *	"msg": 删除成功,
	  *	"data": {}
	  *	}
	  */ 
	 @RequestMapping(value = "/del", method = RequestMethod.POST)
	 public Result<?> del( ${className}Do  ${classNameLower}Do,HttpServletRequest request, HttpServletResponse response) {
		 
		 if(null == ${classNameLower}Do.getId() || 0 == ${classNameLower}Do.getId()){
			 return Result.failureResult("ID不能为空!");
		 }
//		 
		 ${classNameLower}Service.deleteById(${classNameLower}Do.getId());
		 return Result.successResult("${TABLE_INFO}删除成功");
	 }

}
