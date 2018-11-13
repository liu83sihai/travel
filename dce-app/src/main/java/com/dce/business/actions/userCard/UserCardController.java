package com.dce.business.actions.userCard;

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
import com.dce.business.entity.activity.ActivityDo;
import com.dce.business.entity.notice.NoticeDo;
import com.dce.business.entity.page.PageDo;

import com.dce.business.service.userCard.IUserCardService;
import com.dce.business.entity.userCard.UserCardDo;
/**
 * 用户激活卡管理授权业务实现类
 *
 * @author  Fantasy
 * @date    generate
 * @version 1.0.0
 * @pageage com.dce.business.actions.UserCardController.java
 */
@RestController
@RequestMapping("/userCard")
public class UserCardController  extends BaseController{
	private final static Logger logger = Logger.getLogger(UserCardController.class);
	@Resource
	private IUserCardService userCardService;
	
	/**
	 *  @apiDefine userCardSucces
	 *	@apiSuccess {java.lang.Integer}  id id
	 *	@apiSuccess {java.lang.Integer}  userId 用户ID
	 *	@apiSuccess {java.lang.String}  userName 用户名
	 *	@apiSuccess {java.lang.String}  mobile 用户手机
	 *	@apiSuccess {java.lang.String}  orderNo 流水单号
	 *	@apiSuccess {java.lang.Integer}  userLevel 用户等级
	 *	@apiSuccess {java.lang.Integer}  sex 性别
	 *	@apiSuccess {java.lang.String}  cardNo 第三方卡号
	 *	@apiSuccess {java.lang.String}  bankNo 银行卡号
	 *	@apiSuccess {java.lang.String}  idNumber 身份证号
	 *	@apiSuccess {java.lang.String}  remark 备注
	 *	@apiSuccess {java.sql.Date}  createDate 创建时间
	 *	@apiSuccess {java.sql.Date}  updateDate 修改时间
	 *	@apiSuccess {java.lang.Integer}  status 状态
	 */
	
	/**
	 *  @apiDefine userCardParam
	 *	@apiParam {java.lang.Integer}  id id
	 *	@apiParam {java.lang.Integer}  userId 用户ID
	 *	@apiParam {java.lang.String}  userName 用户名
	 *	@apiParam {java.lang.String}  mobile 用户手机
	 *	@apiParam {java.lang.String}  orderNo 流水单号
	 *	@apiParam {java.lang.Integer}  userLevel 用户等级
	 *	@apiParam {java.lang.Integer}  sex 性别
	 *	@apiParam {java.lang.String}  cardNo 第三方卡号
	 *	@apiParam {java.lang.String}  bankNo 银行卡号
	 *	@apiParam {java.lang.String}  idNumber 身份证号
	 *	@apiParam {java.lang.String}  remark 备注
	 *	@apiParam {java.sql.Date}  createDate 创建时间
	 *	@apiParam {java.sql.Date}  updateDate 修改时间
	 *	@apiParam {java.lang.Integer}  status 状态
	 */
	
	/** 
	 * @api {GET} /userCard/index.do 用户激活卡列表
	 * @apiName userCardList
	 * @apiGroup userCard 
	 * @apiVersion 1.0.0 
	 * @apiDescription 用户激活卡列表
	 *  
	 * @apiUse pageParam  
	 
	 * @apiUse userCardSucces  
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
	 * 				userName 用户名
	 * 				mobile 用户手机
	 * 				orderNo 流水单号
	 * 				userLevel 用户等级
	 * 				sex 性别
	 * 				cardNo 第三方卡号
	 * 				bankNo 银行卡号
	 * 				idNumber 身份证号
	 * 				remark 备注
	 * 				createDate 创建时间
	 * 				updateDate 修改时间
	 * 				status 状态
	 *			}
	 *		]
	 *	  }
	 *	}
	 */ 
	 @RequestMapping("/index")
	 public Result<?> list() {
		 logger.info("查询用户激活卡...");
	
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
		PageDo<UserCardDo> userCardPage = new PageDo<UserCardDo>();
		userCardPage.setCurrentPage(pageNum);
		userCardPage.setPageSize(rows);
		PageDo<UserCardDo> pageDo = userCardService.getUserCardPage(paramMap, userCardPage);
		List<UserCardDo> userCardList = pageDo.getModelList();
		 
		List<Map<String, Object>> result = new ArrayList<>();
	    if (!CollectionUtils.isEmpty(userCardList)) {
	         for (UserCardDo userCard : userCardList) {

	             Map<String, Object> map = new HashMap<>();
	             map.put("id", userCard.getId());
	             map.put("userId", userCard.getUserId());
	             map.put("userName", userCard.getUserName());
	             map.put("mobile", userCard.getMobile());
	             map.put("orderNo", userCard.getOrderNo());
	             map.put("userLevel", userCard.getUserLevel());
	             map.put("sex", userCard.getSex());
	             map.put("cardNo", userCard.getCardNo());
	             map.put("bankNo", userCard.getBankNo());
	             map.put("idNumber", userCard.getIdNumber());
	             map.put("remark", userCard.getRemark());
	             map.put("createDate", userCard.getCreateDate());
	             map.put("updateDate", userCard.getUpdateDate());
	             map.put("status", userCard.getStatus());
	                result.add(map);
	           }
	        }
		 return Result.successResult("查询成功", result);
	 }
	 
	 /** 
	  * @api {GET} /userCard/getId.do 获取用户激活卡
	  * @apiName getUserCard
	  * @apiGroup userCard 
	  * @apiVersion 1.0.0 
	  * @apiDescription 获取用户激活卡
	  *  
	  *	@apiParam  {java.lang.Integer}  id 用户激活卡ID
	 
	  * @apiUse userCardSucces  
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
	  * 				userName 用户名
	  * 				mobile 用户手机
	  * 				orderNo 流水单号
	  * 				userLevel 用户等级
	  * 				sex 性别
	  * 				cardNo 第三方卡号
	  * 				bankNo 银行卡号
	  * 				idNumber 身份证号
	  * 				remark 备注
	  * 				createDate 创建时间
	  * 				updateDate 修改时间
	  * 				status 状态
	  *			}
	  *		]
	  *	  }
	  *	}
	  */ 
	 @RequestMapping("/getId")
	 public Result<?> getId(UserCardDo userCardDo) {
		 logger.info("获取用户激活卡...");
		 if(null == userCardDo.getId() || 0 == userCardDo.getId()){
			 return Result.failureResult("用户激活卡ID不能为空!");
		 }
		 
		 UserCardDo userCard = userCardService.getById(userCardDo.getId());
		 
		 return Result.successResult("获取用户激活卡成功", userCard);
	 }
	 
	 /** 
		 * @api {POST} /userCard/add.do 添加用户激活卡
		 * @apiName addUserCard
		 * @apiGroup userCard 
		 * @apiVersion 1.0.0 
		 * @apiDescription 添加用户激活卡
		 *  
		 * @apiUse  userCardParam 
		 *  
		 * @apiUse  userCardSucces  
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
	  	 * 				userName 用户名
	  	 * 				mobile 用户手机
	  	 * 				orderNo 流水单号
	  	 * 				userLevel 用户等级
	  	 * 				sex 性别
	  	 * 				cardNo 第三方卡号
	  	 * 				bankNo 银行卡号
	  	 * 				idNumber 身份证号
	  	 * 				remark 备注
	  	 * 				createDate 创建时间
	  	 * 				updateDate 修改时间
	  	 * 				status 状态
		 *			}
		 *		]
		 *	  }
		 *	}
		 */ 
	 @RequestMapping(value = "/add", method = RequestMethod.POST)
	 public Result<?> add( UserCardDo  userCardDo,HttpServletRequest request, HttpServletResponse response) {
		 
//		 if(null == userCardDo.getUserId() || 0 == userCardDo.getUserId()){
//			 return Result.failureResult("用户ID不能为空!");
//		 }
//		 
//		 if(StringUtils.isBlank(UserCardDo.getContent())){
//			 return Result.failureResult("内容为空!");
//		 }
//		 userCardDo.setStatus(1);
//		 userCardDo.setCreateDate(new Date(System.currentTimeMillis()));
//		 userCardDo.setCreateName("前台增加用户激活卡");
		 userCardService.addUserCard(userCardDo);
		 return Result.successResult("用户激活卡增加成功",userCardDo);
	 }
	 
	 /** 
	  * @api {POST} /userCard/edit.do 修改用户激活卡
	  * @apiName editUserCard
	  * @apiGroup userCard 
	  * @apiVersion 1.0.0 
	  * @apiDescription 修改用户激活卡
	  *  
	  * @apiUse  userCardParam 
	  *  
	  * @apiUse  userCardSucces  
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
	  * 				userName 用户名
	  * 				mobile 用户手机
	  * 				orderNo 流水单号
	  * 				userLevel 用户等级
	  * 				sex 性别
	  * 				cardNo 第三方卡号
	  * 				bankNo 银行卡号
	  * 				idNumber 身份证号
	  * 				remark 备注
	  * 				createDate 创建时间
	  * 				updateDate 修改时间
	  * 				status 状态
	  *			}
	  *		]
	  *	  }
	  *	}
	  */ 
	 @RequestMapping(value = "/edit", method = RequestMethod.POST)
	 public Result<?> edit( UserCardDo  userCardDo,HttpServletRequest request, HttpServletResponse response) {
		 
//		 if(null == userCardDo.getId() || 0 == userCardDo.getId()){
//			 return Result.failureResult("ID不能为空!");
//		 }
//		 
//		 userCardDo.setModifyDate(new Date(System.currentTimeMillis()));
//		 userCardDo.setModifyName("前台修改风采");
		 userCardService.updateUserCardById(userCardDo);
		 return Result.successResult("用户激活卡修改成功",userCardDo);
	 }
	 
	 /** 
	  * @api {POST} /userCard/del.do 删除用户激活卡
	  * @apiName delUserCard
	  * @apiGroup userCard 
	  * @apiVersion 1.0.0 
	  * @apiDescription 删除用户激活卡
	  *  
	  * @apiUse  userCardParam 
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
	 public Result<?> del( UserCardDo  userCardDo,HttpServletRequest request, HttpServletResponse response) {
		 
		 if(null == userCardDo.getId() || 0 == userCardDo.getId()){
			 return Result.failureResult("ID不能为空!");
		 }
//		 
		 userCardService.deleteById(userCardDo.getId());
		 return Result.successResult("用户激活卡删除成功");
	 }

}
