package com.dce.business.actions.supplier;

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
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.entity.activity.ActivityDo;
import com.dce.business.entity.notice.NoticeDo;
import com.dce.business.entity.page.PageDo;

import com.dce.business.service.supplier.ISupplierService;
import com.dce.business.service.user.IUserService;
import com.dce.business.entity.supplier.SupplierDo;
import com.dce.business.entity.user.UserDo;
/**
 * 商家管理授权业务实现类
 *
 * @author  Fantasy
 * @date    generate
 * @version 1.0.0
 * @pageage com.dce.business.actions.SupplierController.java
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController  extends BaseController{
	private final static Logger logger = Logger.getLogger(SupplierController.class);
	@Resource
	private ISupplierService supplierService;
	
	@Resource
	private IUserService userService;
	/**
	 *  @apiDefine supplierSucces
	 *	@apiSuccess {java.lang.Integer}  id id
	 *	@apiSuccess {java.lang.Integer}  userId 空
	 *	@apiSuccess {java.lang.String}  supplierName 供应商名
	 *	@apiSuccess {java.lang.String}  synopsis 简介
	 *	@apiSuccess {java.lang.String}  busiImage 营业执照
	 *	@apiSuccess {java.lang.String}  shopImage 门店照片
	 *	@apiSuccess {java.lang.String}  city 省市/城市 广东-深圳-罗湖区
	 *	@apiSuccess {java.lang.String}  supplierAddress 详细地址
	 *	@apiSuccess {java.math.BigDecimal}  grade 评分
	 *	@apiSuccess {java.math.BigDecimal}  average 人均
	 *	@apiSuccess {java.lang.Integer}  supplierType 供应商类型  1： 区县代理 2：市代 3：省代
	 *	@apiSuccess {java.math.BigDecimal}  longitude 经度
	 *	@apiSuccess {java.math.BigDecimal}  latitude 纬度
	 *	@apiSuccess {java.math.BigDecimal}  distance 距离
	 *	@apiSuccess {java.lang.Integer}  status 状态 0:删除 1：正常 2:审核通过
	 *  @apiParam {java.lang.String}  shopDetailsUrl 商家明细地址
	 */
	
	/**
	 *  @apiDefine supplierParam
	 *	@apiParam {java.lang.Integer}  id 供应商
	 *	@apiParam {java.lang.Integer}  userId 空
	 *	@apiParam {java.lang.String}  supplierName 供应商名
	 *	@apiParam {java.lang.String}  synopsis 简介
	 *	@apiParam {java.lang.String}  busiImage 营业执照
	 *	@apiParam {java.lang.String}  shopImage 门店照片
	 *	@apiParam {java.lang.String}  city 省市/城市 拼装成'广东-深圳-罗湖区 '三级类型
	 *	@apiParam {java.lang.String}  cityCode 城市代码
	 *	@apiParam {java.lang.Integer}  supplierType 供应商类型  1： 区县代理 2：市代 3：省代
	 *	@apiParam {java.lang.String}  supplierAddress 详细地址
	 *	@apiParam {java.math.BigDecimal}  longitude 经度
	 *	@apiParam {java.math.BigDecimal}  latitude 纬度
	 *	@apiParam {java.lang.String}  protocolLink 协议地址
	
	 */
	
	/** 
	 * @api {GET} /supplier/index.do 商家管理列表 
	 * @apiName supplierList
	 * @apiGroup supplier 
	 * @apiVersion 1.0.0 
	 * @apiDescription 商家管理列表
	 *  
	 *	@apiParam {java.lang.String}  supplierName 供应商名查询
	 *  @apiParam {java.math.BigDecimal}  longitude 经度
	 *	@apiParam {java.math.BigDecimal}  latitude 纬度
	 *  * @apiUse pageParam 
	 
	 * @apiUse supplierSucces  
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
	 * 				userId 空
	 * 				supplierName 供应商名
	 * 				synopsis 简介
	 * 				content 详情
	 * 				linkValue 链接
	 * 				listImages 小图片
	 * 				bannerImages banner图
	 * 				busiImage 营业执照
	 * 				shopImage 门店照片
	 * 				city 省市/城市
	 * 				supplierAddress 详细地址
	 * 				telPhone 电话
	 * 				linkMan 联系人
	 * 				supplierType 类型
	 * 				grade 评分
	 * 				average 人均
	 * 				longitude 经度
	 * 				latitude 纬度
	 * 				hitNum 点击数
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
	 public Result<?> list(SupplierDo supplierDo) {
		 logger.info("查询商家管理...");
	
		 String pageNums = getString("pageNum");
		 String row = getString("rows");
		 Integer page = 0;
		 Integer srows = 10;
		// 不传 默认查询第一页
		if (StringUtils.isNotBlank(pageNums)) {
			page = Integer.parseInt(pageNums);
		}
		if (StringUtils.isNotBlank(row)) {
			srows = Integer.parseInt(row);
		}
		int startNum = page * srows;
		supplierDo.setRows(srows);
		supplierDo.setStartNum(startNum);
		
		if(StringUtils.isBlank(supplierDo.getSupplierName())){
			supplierDo.setSupplierName(null);
		}

//		Map<String,Object> paramMap = new HashMap<String,Object>();
//		if(StringUtils.isNotBlank(supplierDo.getSupplierName())){
//			paramMap.put("supplierName", supplierDo.getSupplierName());
//		}
//		paramMap.put("latitude", supplierDo.getLatitude());
//		paramMap.put("longitude", supplierDo.getLongitude());
		
		
//		PageDo<SupplierDo> supplierPage = new PageDo<SupplierDo>();
//		supplierPage.setCurrentPage(pageNum);
//		supplierPage.setPageSize(rows);
		
		List<SupplierDo> supplierList= supplierService.selectSupplier(supplierDo);
//		PageDo<SupplierDo> pageDo = supplierService.getSupplierPage(paramMap, supplierPage);
//		List<SupplierDo> supplierList = pageDo.getModelList();
		 
		List<Map<String, Object>> result = new ArrayList<>();
	    if (!CollectionUtils.isEmpty(supplierList)) {
	         for (SupplierDo supplier : supplierList) {

	             Map<String, Object> map = new HashMap<>();
	             map.put("id", supplier.getId());
	             map.put("userId", supplier.getUserId());
	             map.put("supplierName", supplier.getSupplierName());
	             map.put("synopsis", supplier.getSynopsis());
	             map.put("busiImage", supplier.getBusiImage());
	             map.put("shopImage", supplier.getShopImage());
	             map.put("city", supplier.getCity());
	             map.put("supplierAddress", supplier.getSupplierAddress());
	             map.put("grade", supplier.getGrade());
	             map.put("average", supplier.getAverage());
	             map.put("longitude", supplier.getLongitude());
	             map.put("latitude", supplier.getLatitude());
	             map.put("status", supplier.getStatus());
	             map.put("distance", supplier.getDistance());
	             map.put("shopDetailsUrl", "www.baidu.com");
	             result.add(map);
	           }
	        }
		 return Result.successResult("查询成功", result);
	 }
	 
	 /** 
	  * @api {GET} /supplier/getId.do 获取商家管理
	  * @apiName getSupplier
	  * @apiGroup supplier 
	  * @apiVersion 1.0.0 
	  * @apiDescription 获取商家管理
	  *  
	  *	@apiParam  {java.lang.Integer}  userId 用户ID
	 
	  * @apiUse supplierSucces  
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
	  * 				userId 空
	  * 				supplierName 供应商名
	  * 				synopsis 简介
	  * 				content 详情
	  * 				linkValue 链接
	  * 				listImages 小图片
	  * 				bannerImages banner图
	  * 				busiImage 营业执照
	  * 				shopImage 门店照片
	  * 				city 省市/城市
	  * 				supplierAddress 详细地址
	  * 				telPhone 电话
	  * 				linkMan 联系人
	  * 				supplierType 类型
	  * 				grade 评分
	  * 				average 人均
	  * 				longitude 经度
	  * 				latitude 纬度
	  * 				hitNum 点击数
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
	 public Result<?> getId(SupplierDo supplierDo) {
		 logger.info("获取商家管理...");
		 if(null == supplierDo.getUserId() || 0 == supplierDo.getUserId().intValue()){
			 return Result.failureResult("商家用户ID不能为空!");
		 }
		 
		 SupplierDo supplier = supplierService.getById(supplierDo.getUserId());
		 
		 return Result.successResult("获取商家管理成功", supplier);
	 }
	 
	 /** 
		 * @api {POST} /supplier/add.do 添加商家管理
		 * @apiName addSupplier
		 * @apiGroup supplier 
		 * @apiVersion 1.0.0 
		 * @apiDescription 添加商家管理 协议固定地址：http://app.zjzwly.com/dce-manager/supplier/protocolLink.html
		 *  
		 * @apiUse  supplierParam 
		 *  
		 * @apiUse  supplierSucces  
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
	  	 * 				userId 空
	  	 * 				supplierName 供应商名
	  	 * 				synopsis 简介
	  	 * 				content 详情
	  	 * 				linkValue 链接
	  	 * 				listImages 小图片
	  	 * 				bannerImages banner图
	  	 * 				busiImage 营业执照
	  	 * 				shopImage 门店照片
	  	 * 				city 省市/城市
	  	 * 				supplierAddress 详细地址
	  	 * 				telPhone 电话
	  	 * 				linkMan 联系人
	  	 * 				supplierType 类型
	  	 * 				grade 评分
	  	 * 				average 人均
	  	 * 				longitude 经度
	  	 * 				latitude 纬度
	  	 * 				hitNum 点击数
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
	 public Result<?> add( SupplierDo  supplierDo,HttpServletRequest request, HttpServletResponse response) {
		 
		 if(null == supplierDo.getUserId() || 0 == supplierDo.getUserId().intValue()){
			 return Result.failureResult("用户ID不能为空!");
		 }
		 SupplierDo  supplier =supplierService.getById(supplierDo.getUserId());
		 if(null != supplier){
			 return Result.successResult("当前商家已申请商家管理,请在用户当中查看",supplierDo);
		 }
		 
		 UserDo userDo =userService.getUser(supplierDo.getUserId());
		 if(null == userDo){
			 return Result.successResult("当前用户不存在,请联系管理员",userDo);
		 }
		 
		 
		 Assert.hasText(supplierDo.getSupplierName(), "供应商为空");
		 Assert.hasText(supplierDo.getSynopsis(), "供应商简介为空");
		 Assert.hasText(supplierDo.getBusiImage(), "营业执照为空");
		 Assert.hasText(supplierDo.getShopImage(), "门店为空");
		 Assert.hasText(supplierDo.getSupplierAddress(), "详细地址");
		 Assert.hasText(supplierDo.getCity(), "城市信息为空");

		 supplierDo.setLinkMan(userDo.getTrueName());
		 supplierDo.setTelPhone(userDo.getMobile());
		 supplierDo.setStatus(1);
		 supplierDo.setCreateDate(new Date(System.currentTimeMillis()));
		 supplierDo.setCreateName("前台增加商家管理");
		 
		 int parentId = userDo.getParentid();
		 if( 0 !=parentId ){
			 supplierDo.setParentId(parentId);
			 UserDo parentDo =userService.getUser(parentId);
			 supplierDo.setParentName(parentDo.getTrueName());
			
		 }
		 
		 supplierService.addSupplier(supplierDo);
		 
		 return Result.successResult("商家管理增加成功",supplierDo);
	 }
	 
	 /** 
	  * @api {POST} /supplier/edit.do 修改商家管理
	  * @apiName editSupplier
	  * @apiGroup supplier 
	  * @apiVersion 1.0.0 
	  * @apiDescription 修改商家管理
	  *  
	  * @apiUse  supplierParam 
	  *  
	  * @apiUse  supplierSucces  
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
	  * 				userId 空
	  * 				supplierName 供应商名
	  * 				synopsis 简介
	  * 				content 详情
	  * 				linkValue 链接
	  * 				listImages 小图片
	  * 				bannerImages banner图
	  * 				busiImage 营业执照
	  * 				shopImage 门店照片
	  * 				city 省市/城市
	  * 				supplierAddress 详细地址
	  * 				telPhone 电话
	  * 				linkMan 联系人
	  * 				supplierType 类型
	  * 				grade 评分
	  * 				average 人均
	  * 				longitude 经度
	  * 				latitude 纬度
	  * 				hitNum 点击数
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
	 public Result<?> edit( SupplierDo  supplierDo,HttpServletRequest request, HttpServletResponse response) {
		 
		 if(null == supplierDo.getId() || 0 == supplierDo.getId().intValue()){
			 return Result.failureResult("ID不能为空!");
		 }
//		 
		 supplierDo.setModifyDate(new Date(System.currentTimeMillis()));
		 supplierDo.setModifyName("前台修改风采");
		 supplierService.updateSupplierById(supplierDo);
		 return Result.successResult("商家管理修改成功",supplierDo);
	 }
	 
	 /** 
	  * @api {POST} /supplier/del.do 删除商家管理
	  * @apiName delSupplier
	  * @apiGroup supplier 
	  * @apiVersion 1.0.0 
	  * @apiDescription 删除商家管理
	  *  
	  * @apiParam {java.lang.Integer}  userId 用户ID
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
	 public Result<?> del( SupplierDo  supplierDo,HttpServletRequest request, HttpServletResponse response) {
		 
		 if(null == supplierDo.getUserId() || 0 == supplierDo.getUserId().intValue()){
			 return Result.failureResult("ID不能为空!");
		 }
//		 
		 supplierService.deleteById(supplierDo.getUserId());
		 return Result.successResult("商家管理删除成功");
	 }

}
