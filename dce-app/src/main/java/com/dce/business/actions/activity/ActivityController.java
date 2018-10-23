package com.dce.business.actions.activity;

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

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.entity.notice.NoticeDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.activity.IActivityService;
import com.dce.business.entity.activity.ActivityDo;
/**
 * 活动风彩管理授权业务实现类
 *
 * @author  Fantasy
 * @date    generate
 * @version 1.0.0
 * @pageage com.dce.business.actions.ActivityController.java
 */
@RestController
@RequestMapping("/activity")
public class ActivityController extends BaseController{
	private final static Logger logger = Logger.getLogger(ActivityController.class);
	@Resource
	private IActivityService activityService;
	
	/**
	 *  @apiDefine activitySucces
	 *	@apiSuccess {java.lang.Integer}  id id
	 *	@apiSuccess {java.lang.Integer}  userId 用户ID
	 *	@apiSuccess {java.lang.String}  synopsis 描述
	 *	@apiSuccess {java.lang.String}  content 内容
	 *	@apiSuccess {java.lang.String}  images 图片
	 *	@apiSuccess {java.lang.Integer}  hitNum 点赞数
	 *	@apiSuccess {java.sql.Date}  createDate 创建时间
	 *	@apiSuccess {java.lang.String}  createName 创建人
	 *	@apiSuccess {java.sql.Date}  modifyDate 更新时间
	 *	@apiSuccess {java.lang.String}  modifyName 更新人
	 *	@apiSuccess {java.lang.Integer}  status 状态
	 *	@apiSuccess {java.lang.String}  remark 备注
	 */
	
	
	
	/** 
	 * @api {GET} /activity/index.do 活动风彩列表
	 * @apiName activityList
	 * @apiGroup activity 
	 * @apiVersion 1.0.0 
	 * @apiDescription 活动风彩列表
	 *  
	 * @apiUse pageParam  
	 *   
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
	 * 				id id
	 * 				userId 用户ID
	 * 				synopsis 描述
	 * 				content 内容
	 * 				images 图片
	 * 				hitNum 点赞数
	 * 				createDate 创建时间
	 * 				createName 创建人
	 * 				modifyDate 更新时间
	 * 				modifyName 更新人
	 * 				status 状态
	 * 				remark 备注
	 *			}
	 *		]
	 *	  }
	 *	}
	 */ 
	 @RequestMapping("/index")
	 
	public Result<?> list() {
		logger.info("查询活动风彩...");

		String pageNums = getString("pageNum");
		String row = getString("rows");
		// 不传 默认查询第一页
		if (StringUtils.isNotBlank(pageNums)) {
			pageNum = Long.parseLong(pageNums);
		}
		if (StringUtils.isNotBlank(row)) {
			rows = Long.parseLong(row);
		}

		 Map<String,Object> paramMap = new HashMap<String,Object>();
		PageDo<ActivityDo> activityPage = new PageDo<ActivityDo>();
		activityPage.setCurrentPage(pageNum);
		activityPage.setPageSize(rows);
		PageDo<ActivityDo> pageDo = activityService.getActivityPage(paramMap, activityPage);
		List<ActivityDo> activityList = pageDo.getModelList();
		List<Map<String, Object>> result = new ArrayList<>();
		if (!CollectionUtils.isEmpty(activityList)) {
			for (ActivityDo activity : activityList) {

				Map<String, Object> map = new HashMap<>();
				map.put("id", activity.getId());
				map.put("userId", activity.getUserId());
				map.put("synopsis", activity.getSynopsis());
				map.put("content", activity.getContent());
				map.put("images", activity.getImages());
				map.put("hitNum", activity.getHitNum());
				map.put("createDate", activity.getCreateDate());
				map.put("createName", activity.getCreateName());
				map.put("modifyDate", activity.getModifyDate());
				map.put("modifyName", activity.getModifyName());
				map.put("status", activity.getStatus());
				map.put("remark", activity.getRemark());
				result.add(map);
			}
		}
		return Result.successResult("查询成功", result);
	}
	 
	 /** 
	  * @api {GET} /activity/getId.do 获取活动风彩
	  * @apiName getActivity
	  * @apiGroup activity 
	  * @apiVersion 1.0.0 
	  * @apiDescription 获取活动风彩
	  *  
	  *	@apiParam  {java.lang.Integer}  id 风采ID
	 
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
	  * 				id id
	  * 				userId 用户ID
	  * 				synopsis 描述
	  * 				content 内容
	  * 				images 图片
	  * 				hitNum 点赞数
	  * 				createDate 创建时间
	  * 				createName 创建人
	  * 				modifyDate 更新时间
	  * 				modifyName 更新人
	  * 				status 状态
	  * 				remark 备注
	  *			}
	  *		]
	  *	  }
	  *	}
	  */ 
	 @RequestMapping("/getId")
	 
	 public Result<?> getId(ActivityDo activityDo) {
		 logger.info("获取活动风彩...");
		 if(null == activityDo.getId() || 0 == activityDo.getId()){
			 return Result.failureResult("风采ID不能为空!");
		 }
		 
		 ActivityDo activity = activityService.getById(activityDo.getId());
		 
		 return Result.successResult("获取活动风彩成功", activity);
	 }
	 
	 
	 /** 
		 * @api {POST} /activity/add.do 添加活动风彩
		 * @apiName addActivity
		 * @apiGroup activity 
		 * @apiVersion 1.0.0 
		 * @apiDescription 添加活动风彩
		 *  
		 *	@apiParam  {java.lang.Integer}  userId 用户ID
		 *	@apiParam  {java.lang.String}  content 内容
		 *	@apiParam  {java.lang.String}  images 图片
		 *
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
		 * 				id id
		 * 				userId 用户ID
		 * 				synopsis 描述
		 * 				content 内容
		 * 				images 图片
		 * 				hitNum 点赞数
		 * 				createDate 创建时间
		 * 				createName 创建人
		 * 				modifyDate 更新时间
		 * 				modifyName 更新人
		 * 				status 状态
		 * 				remark 备注
		 *			}
		 *		]
		 *	  }
		 *	}
		 */ 
	 @RequestMapping(value = "/add", method = RequestMethod.POST)
	 public Result<?> add(ActivityDo activityDo,HttpServletRequest request, HttpServletResponse response) {
		 
		 if(null == activityDo.getUserId() || 0 == activityDo.getUserId()){
			 return Result.failureResult("用户ID不能为空!");
		 }
		 
		 if(StringUtils.isBlank(activityDo.getContent())){
			 return Result.failureResult("发表风采内容为空!");
		 }
		 activityDo.setHitNum(0);
		 activityDo.setStatus(1);
		 activityDo.setCreateDate(new Date(System.currentTimeMillis()));
		 activityDo.setCreateName("前台增加风采");
		 activityService.addActivity(activityDo);
		 return Result.successResult("风采增加成功",activityDo);
	 }
	 
	 /** 
	  * @api {POST} /activity/edit.do 编辑活动风彩
	  * @apiName editActivity
	  * @apiGroup activity 
	  * @apiVersion 1.0.0 
	  * @apiDescription 编辑活动风彩
	  *  
	  *	@apiParam  {java.lang.Integer}  id 风采ID
	  *	@apiParam  {java.lang.String}  content 内容
	  *	@apiParam  {java.lang.String}  images 图片
	  *
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
	  * 				id id
	  * 				userId 用户ID
	  * 				synopsis 描述
	  * 				content 内容
	  * 				images 图片
	  * 				hitNum 点赞数
	  * 				createDate 创建时间
	  * 				createName 创建人
	  * 				modifyDate 更新时间
	  * 				modifyName 更新人
	  * 				status 状态
	  * 				remark 备注
	  *			}
	  *		]
	  *	  }
	  *	}
	  */ 
	 @RequestMapping(value = "/edit", method = RequestMethod.POST)
	 public Result<?> edit(ActivityDo activityDo,HttpServletRequest request, HttpServletResponse response) {
		 
		 if(null == activityDo.getId() || 0 == activityDo.getId()){
			 return Result.failureResult("风采ID不能为空!");
		 }
		
		 activityDo.setModifyDate(new Date(System.currentTimeMillis()));
		 activityDo.setModifyName("前台修改风采");
		 activityService.updateActivityById(activityDo);
		 return Result.successResult("风采修改成功",activityDo);
	 }
	 
	 /** 
	  * @api {POST} /activity/del.do 删除活动风彩
	  * @apiName delActivity
	  * @apiGroup activity 
	  * @apiVersion 1.0.0 
	  * @apiDescription 删除活动风彩
	  *  
	  *	@apiParam  {java.lang.Integer}  id 风采ID
	  *
	  * @apiUse RETURN_MESSAGE
	  * @apiSuccessExample Success-Response: 
	  *  HTTP/1.1 200 OK 
	  * {
	  *  "code": 0
	  *	"msg": 返回成功,
	  *	"data": {}
	  *	}
	  */ 
	 @RequestMapping(value = "/del", method = RequestMethod.POST)
	 public Result<?> del(ActivityDo activityDo,HttpServletRequest request, HttpServletResponse response) {
		 
		 if(null == activityDo.getId() || 0 == activityDo.getId()){
			 return Result.failureResult("风采ID不能为空!");
		 }
		 activityService.deleteById(activityDo.getId());
		 return Result.successResult("风采删除成功");
	 }

}
