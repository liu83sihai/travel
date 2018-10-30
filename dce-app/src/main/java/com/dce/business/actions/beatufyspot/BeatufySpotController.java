package com.dce.business.actions.beatufyspot;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.common.result.Result;
/**
 * 景区查询授权业务实现类
 *
 * @author  Fantasy
 * @date    generate
 * @version 1.0.0
 * @pageage com.dce.business.actions.BeatufyspotController.java
 */
@RestController
@RequestMapping("/beatufyspot")
public class BeatufySpotController {
	private final static Logger logger = Logger.getLogger(BeatufySpotController.class);
	
	/**
	 *  @apiDefine beatufyspotSucces
	 *	@apiSuccess {java.lang.Integer}  id id
	 *	@apiSuccess {String}  url 景区查询url
	 *	@apiSuccess {String}  channel 景区来源
	 */
	
	/** 
	 * @api {GET} /beatufyspot/index.do 景区查询列表
	 * @apiName beatufyspotList
	 * @apiGroup beatufyspot 
	 * @apiVersion 1.0.0 
	 * @apiDescription 景区查询列表
	 *  
	 * @apiUse beatufyspotSucces  
	 * @apiUse RETURN_MESSAGE
	
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "code": 0
	 *	"msg": 返回成功,
	 *	"data": {
	 *	    [
	 *			{
	 * 				id id
	 * 				url 景区查询url
	 * 				channel 景区来源
	 *			}
	 *		]
	 *	  }
	 *	}
	 */ 
	 @RequestMapping("/index")
	 public Result<?> list() {
		 logger.info("查询景区查询...");
		 Map<String, Object> ret = new HashMap<String,Object>();
		 Result result = Result.successResult("ok");
		 result.setData(ret);
		 ret.put("url", "https://wx.xinglvka.com/list.html");
		 ret.put("id", 1);
		 ret.put("channel", 1);
		 return Result.successResult("查询成功", result);
	 }
	 
}
