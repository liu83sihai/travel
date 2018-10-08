package com.dce.business.actions.common;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 基础数据接口
 * 
 * @author MBENBEN
 *
 */
@Controller
@RequestMapping("/baseIntf")
public class BaseInfoIntf  {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BaseInfoIntf.class);

	/** 
	 * @apiDefine CODE_200
	 * @apiSuccess (Reponse 200) {number} code 200
	 * @apiSuccess (Reponse 200) {String} msg 操作说明
	 * @apiSuccessExample {json} Response 200 Example
	 *   HTTP/1.1 200 OK
	 *   {
	 *     "code": 200,
	 *     "msg": "操作成功"
	 *   }
	 */
	
	/** 
	 * @apiDefine LIST_PARAM
	 * @apiParam {Integer} [page=1] 页码  
	 * @apiParam {Integer} [rows=10] 每页行数  
	 * @apiParam {String} [sort=createDate] 排序字段,取实体值  
	 * @apiParam {String} [order=asc] 排序方式，可取asc,desc  
	 *
	 */
	
	
	/** 
	 *  @apiDefine ERROR_309
	 *  @apiError 309 对应<code>309</code> 用户ID为空  
     *  @apiErrorExample {json} 309 Error-Response: 
     *  用户ID为空
     *  { 
     *   code:309, 
     *   msg:'用户ID为空', 
     *  } 
	 */
	/** 
	 *  @apiDefine ERROR_310
	 *  @apiError 310 对应<code>310</code> 用户ID未找到关连用户
	 *  @apiErrorExample {json} 310 Error-Response: 
	 *  用户ID未找到用户
	 *  { 
	 *   code:310, 
	 *   msg:'用户ID未找到关连用户', 
	 *  } 
	 */
	
	/** 
	 *  @apiDefine CODE_401
	 *  @apiError 401 对应<code>401</code> 操作失败  
	 *  @apiErrorExample {json} 401 Error-Response: 
	 *   {
	 *     "code": 401
	 *     "message": "操作失败"
	 *   }
	 */
	
	/**
	 * @apiDefine CODE_400
	 * @apiSuccess (Response 400) {number} code 400
	 * @apiSuccess (Response 400) {string} [message] 返回结果为空
	 * @apiSuccessExample {json} Response 400 Example
	 *   HTTP/1.1 400 Internal Server Error
	 *   {
	 *     "code": 400
	 *     "message": "返回结果为空"
	 *   }
	 */
	
	/**
	 * @apiDefine ERROR_405
	 * @apiError 405 对应<code>405</code> 远程服务器调用失败
	 * @apiErrorExample {json} Response 405 Example
	 *   HTTP/1.1 405 Internal Server Error
	 *   {
	 *		"model": null,
     *		"success": false,
     *		"errorMessage": 远程服务器调用失败,
     *		"resultCode": 405
	 *   }
	 */
	/**
	 * @apiDefine ERROR_406
	 * @apiError 406 对应<code>406</code> 操作对象找不到主键ID
	 * @apiErrorExample {json} Response 406 Example
	 *   HTTP/1.1 406 Internal Server Error
	 *   {
	 *		"model": null,
	 *		"success": false,
	 *		"errorMessage": 操作对象找不到主键ID,
	 *		"resultCode": 406
	 *   }
	 */
	/**
	 * @apiDefine ERROR_407
	 * @apiError 407 对应<code>407</code> 对象主键ID找不到实体类型
	 * @apiErrorExample {json} Response 407 Example
	 *   HTTP/1.1 407 Internal Server Error
	 *   {
	 *		"model": null,
	 *		"success": false,
	 *		"errorMessage": 对象主键ID找不到实体类型,
	 *		"resultCode": 407
	 *   }
	 */
	
	/**
	 * @apiDefine RETURN_MESSAGE
	 * @apiSuccess {String} resultCode 结果码 
     * @apiSuccess {String} errorMessage 错误消息说明 
     * @apiSuccess {Boolean} success 是否成功 
	 */
	

	

}
