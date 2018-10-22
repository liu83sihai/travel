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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.common.result.Result;
import com.dce.business.entity.activity.ActivityDo;
import com.dce.business.entity.notice.NoticeDo;

import com.dce.business.service.supplier.ISupplierService;
import com.dce.business.entity.supplier.SupplierDo;
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
public class SupplierController {
	private final static Logger logger = Logger.getLogger(SupplierController.class);
	@Resource
	private ISupplierService supplierService;
	
	/**
	 *  @apiDefine supplierSucces
	 *	@apiSuccess {java.lang.Integer}  id id
	 *	@apiSuccess {java.lang.String}  supplierName 供应商名
	 *	@apiSuccess {java.lang.String}  synopsis 简介
	 *	@apiSuccess {java.lang.String}  content 详情
	 *	@apiSuccess {java.lang.String}  linkValue 链接
	 *	@apiSuccess {java.lang.String}  listImages 小图片
	 *	@apiSuccess {java.lang.String}  bannerImages banner图
	 *	@apiSuccess {java.lang.String}  supplierAddress 地址
	 *	@apiSuccess {java.lang.String}  telPhone 电话
	 *	@apiSuccess {java.lang.String}  linkMan 联系人
	 *	@apiSuccess {java.lang.Integer}  supplierType 类型
	 *	@apiSuccess {java.math.BigDecimal}  grade 评分
	 *	@apiSuccess {java.math.BigDecimal}  average 人均
	 *	@apiSuccess {java.math.BigDecimal}  longitude 经度
	 *	@apiSuccess {java.math.BigDecimal}  latitude 纬度
	 *	@apiSuccess {java.lang.Integer}  hitNum 点击数
	 *	@apiSuccess {java.sql.Date}  createDate 创建时间
	 *	@apiSuccess {java.lang.String}  createName 创建人
	 *	@apiSuccess {java.sql.Date}  modifyDate 更新时间
	 *	@apiSuccess {java.lang.String}  modifyName 更新人
	 *	@apiSuccess {java.lang.Integer}  status 状态(0:删除  1:正常)
	 *	@apiSuccess {java.lang.String}  remark 备注
	 */
	
	/**
	 *  @apiDefine supplierParam
	 *	@apiParam {java.lang.Integer}  id id
	 *	@apiParam {java.lang.String}  supplierName 供应商名
	 *	@apiParam {java.lang.String}  synopsis 简介
	 *	@apiParam {java.lang.String}  content 详情
	 *	@apiParam {java.lang.String}  linkValue 链接
	 *	@apiParam {java.lang.String}  listImages 小图片
	 *	@apiParam {java.lang.String}  bannerImages banner图
	 *	@apiParam {java.lang.String}  supplierAddress 地址
	 *	@apiParam {java.lang.String}  telPhone 电话
	 *	@apiParam {java.lang.String}  linkMan 联系人
	 *	@apiParam {java.lang.Integer}  supplierType 类型
	 *	@apiParam {java.math.BigDecimal}  grade 评分
	 *	@apiParam {java.math.BigDecimal}  average 人均
	 *	@apiParam {java.math.BigDecimal}  longitude 经度
	 *	@apiParam {java.math.BigDecimal}  latitude 纬度
	 *	@apiParam {java.lang.Integer}  hitNum 点击数
	 *	@apiParam {java.sql.Date}  createDate 创建时间
	 *	@apiParam {java.lang.String}  createName 创建人
	 *	@apiParam {java.sql.Date}  modifyDate 更新时间
	 *	@apiParam {java.lang.String}  modifyName 更新人
	 *	@apiParam {java.lang.Integer}  status 状态(0:删除  1:正常)
	 *	@apiParam {java.lang.String}  remark 备注
	 */
	
	/** 
	 * @api {GET} /supplier/index.do 商家管理列表
	 * @apiName supplierList
	 * @apiGroup supplier 
	 * @apiVersion 1.0.0 
	 * @apiDescription 商家管理列表
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
	 * 				id id
	 * 				supplierName 供应商名
	 * 				synopsis 简介
	 * 				content 详情
	 * 				linkValue 链接
	 * 				listImages 小图片
	 * 				bannerImages banner图
	 * 				supplierAddress 地址
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
	 * 				status 状态(0:删除  1:正常)
	 * 				remark 备注
	 *			}
	 *		]
	 *	  }
	 *	}
	 */ 
	 @RequestMapping("/index")
	 public Result<?> list() {
		 logger.info("查询商家管理...");
	
		 SupplierDo supplierDo = new SupplierDo();
		 List<SupplierDo> supplierList = supplierService.selectSupplier(supplierDo);
		 List<Map<String, Object>> result = new ArrayList<>();
	        if (!CollectionUtils.isEmpty(supplierList)) {
	            for (SupplierDo supplier : supplierList) {

	                Map<String, Object> map = new HashMap<>();
	                map.put("id", supplier.getId());
	                map.put("supplierName", supplier.getSupplierName());
	                map.put("synopsis", supplier.getSynopsis());
	                map.put("content", supplier.getContent());
	                map.put("linkValue", supplier.getLinkValue());
	                map.put("listImages", supplier.getListImages());
	                map.put("bannerImages", supplier.getBannerImages());
	                map.put("supplierAddress", supplier.getSupplierAddress());
	                map.put("telPhone", supplier.getTelPhone());
	                map.put("linkMan", supplier.getLinkMan());
	                map.put("supplierType", supplier.getSupplierType());
	                map.put("grade", supplier.getGrade());
	                map.put("average", supplier.getAverage());
	                map.put("longitude", supplier.getLongitude());
	                map.put("latitude", supplier.getLatitude());
	                map.put("hitNum", supplier.getHitNum());
	                map.put("createDate", supplier.getCreateDate());
	                map.put("createName", supplier.getCreateName());
	                map.put("modifyDate", supplier.getModifyDate());
	                map.put("modifyName", supplier.getModifyName());
	                map.put("status", supplier.getStatus());
	                map.put("remark", supplier.getRemark());
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
	  *	@apiParam  {java.lang.Integer}  id 商家管理ID
	 
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
	  * 				supplierName 供应商名
	  * 				synopsis 简介
	  * 				content 详情
	  * 				linkValue 链接
	  * 				listImages 小图片
	  * 				bannerImages banner图
	  * 				supplierAddress 地址
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
	  * 				status 状态(0:删除  1:正常)
	  * 				remark 备注
	  *			}
	  *		]
	  *	  }
	  *	}
	  */ 
	 @RequestMapping("/getId")
	 public Result<?> getId(SupplierDo supplierDo) {
		 logger.info("获取商家管理...");
		 if(null == supplierDo.getId() || 0 == supplierDo.getId()){
			 return Result.failureResult("商家管理ID不能为空!");
		 }
		 
		 SupplierDo supplier = supplierService.getById(supplierDo.getId());
		 
		 return Result.successResult("获取商家管理成功", supplier);
	 }
	

}
