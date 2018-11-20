package com.dce.business.actions.agency;

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
import com.dce.business.entity.supplier.SupplierDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.agency.IAgencyService;
import com.dce.business.service.district.IDistrictService;
import com.dce.business.service.user.IUserService;
import com.dce.business.entity.agency.AgencyDo;
import com.dce.business.entity.district.District;
/**
 * 代理管理授权业务实现类
 *
 * @author  Fantasy
 * @date    generate
 * @version 1.0.0
 * @pageage com.dce.business.actions.AgencyController.java
 */
@RestController
@RequestMapping("/agency")
public class AgencyController  extends BaseController{
	private final static Logger logger = Logger.getLogger(AgencyController.class);
	@Resource
	private IAgencyService agencyService;
	
	@Resource
	private IDistrictService districtService;
	
	@Resource
	private IUserService userService;
	
	/**
	 *  @apiDefine agencySucces
	 *	@apiSuccess {java.lang.Integer}  id id
	 *	@apiSuccess {java.lang.Integer}  userId 用户ID
	 *	@apiSuccess {java.lang.String}  userName 姓名
	 *	@apiSuccess {java.lang.String}  mobile 手机号码
	 *	@apiSuccess {java.lang.String}  idCard 身份证
	 *	@apiSuccess {java.lang.String}  bankNumber 银行卡
	 *	@apiSuccess {java.lang.String}  bankType 银行卡类型
	 *	@apiSuccess {java.lang.String}  idcardFront 身份证正面照
	 *	@apiSuccess {java.lang.String}  idcardBack 身份证反面照
	 *	@apiSuccess {java.lang.String}  city 城市 广东-深圳-罗湖区
	 *	@apiSuccess {java.sql.Date}  createDate 创建时间
	 *	@apiSuccess {java.lang.String}  createName 创建人
	 *	@apiSuccess {java.sql.Date}  modifyDate 更新时间
	 *	@apiSuccess {java.lang.String}  modifyName 更新人
	 *	@apiSuccess {java.lang.Integer}  status 状态(0:删除  1:正常 2:审核通过)
	 *	@apiSuccess {java.lang.String}  remark 备注
	 */
	
	/**
	 *  @apiDefine agencyParam
	 *	@apiParam {java.lang.Integer}  userId 用户ID
	 *	@apiParam {java.lang.String}  city 城市 广东-深圳-罗湖区
	 *	@apiParam {java.lang.String}  cityCode 城市代码
	 */
	
	/** 
	 * {GET} /agency/index.do 代理管理列表
	 * @apiName agencyList
	 * @apiGroup agency 
	 * @apiVersion 1.0.0 
	 * @apiDescription 代理管理列表
	 *  
	 * @apiUse pageParam  
	 
	 * @apiUse agencySucces  
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
	 * 				userName 姓名
	 * 				mobile 手机号码
	 * 				idCard 身份证
	 * 				bankNumber 银行卡
	 * 				bankType 银行卡类型
	 * 				idcardFront 身份证正面照
	 * 				idcardBack 身份证反面照
	 * 				city 城市
	 * 				createDate 创建时间
	 * 				createName 创建人
	 * 				modifyDate 更新时间
	 * 				modifyName 更新人
	 * 				status 状态(0:删除  1:正常 2:审核通过)
	 * 				remark 备注
	 *			}
	 *		]
	 *	  }
	 *	}
	 */ 
	 @RequestMapping("/index")
	 public Result<?> list() {
		 logger.info("查询代理管理...");
	
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
		PageDo<AgencyDo> agencyPage = new PageDo<AgencyDo>();
		agencyPage.setCurrentPage(pageNum);
		agencyPage.setPageSize(rows);
		PageDo<AgencyDo> pageDo = agencyService.getAgencyPage(paramMap, agencyPage);
		List<AgencyDo> agencyList = pageDo.getModelList();
		 
		List<Map<String, Object>> result = new ArrayList<>();
	    if (!CollectionUtils.isEmpty(agencyList)) {
	         for (AgencyDo agency : agencyList) {

	             Map<String, Object> map = new HashMap<>();
	             map.put("id", agency.getId());
	             map.put("userId", agency.getUserId());
	             map.put("userName", agency.getUserName());
	             map.put("mobile", agency.getMobile());
	             map.put("idCard", agency.getIdCard());
	             map.put("bankNumber", agency.getBankNumber());
	             map.put("bankType", agency.getBankType());
	             map.put("idcardFront", agency.getIdcardFront());
	             map.put("idcardBack", agency.getIdcardBack());
	             map.put("province", agency.getProvince());
	             map.put("city", agency.getCity());
	             map.put("createDate", agency.getCreateDate());
	             map.put("createName", agency.getCreateName());
	             map.put("modifyDate", agency.getModifyDate());
	             map.put("modifyName", agency.getModifyName());
	             map.put("status", agency.getStatus());
	             map.put("remark", agency.getRemark());
	                result.add(map);
	           }
	        }
		 return Result.successResult("查询成功", result);
	 }
	 
	 /** 
	  * @api {GET} /agency/getId.do 获取代理管理
	  * @apiName getAgency
	  * @apiGroup agency 
	  * @apiVersion 1.0.0 
	  * @apiDescription 获取代理管理
	  *  
	  *	@apiParam  {java.lang.Integer}  userId 用户ID
	 
	  * @apiUse agencySucces  
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
	  * 				userName 姓名
	  * 				mobile 手机号码
	  * 				idCard 身份证
	  * 				bankNumber 银行卡
	  * 				bankType 银行卡类型
	  * 				idcardFront 身份证正面照
	  * 				idcardBack 身份证反面照
	  * 				province 省份
	  * 				city 城市
	  * 				createDate 创建时间
	  * 				createName 创建人
	  * 				modifyDate 更新时间
	  * 				modifyName 更新人
	  * 				status 状态(0:删除  1:正常 2:审核通过)
	  * 				remark 备注
	  *			}
	  *		]
	  *	  }
	  *	}
	  */ 
	 @RequestMapping("/getId")
	 public Result<?> getId(District agencyDo) {
		 logger.info("获取代理管理...");
		 if(null == agencyDo.getUserId() || 0 == agencyDo.getUserId().intValue()){
			 return Result.failureResult("代理用户ID不能为空!");
		 }
		 
		 District district = districtService.getById(agencyDo.getUserId());
		 
		 return Result.successResult("获取代理管理成功", district);
	 }
	 
	 /** 
		 * @api {POST} /agency/add.do 添加代理管理
		 * @apiName addAgency
		 * @apiGroup agency 
		 * @apiVersion 1.0.0 
		 * @apiDescription 添加代理管理
		 *  
		 * @apiUse  agencyParam 
		 *  
		 * @apiUse  agencySucces  
		 * @apiUse RETURN_MESSAGE
		 * @apiSuccessExample Success-Response: 
		 *  HTTP/1.1 200 OK 
		 * {
		 *  "code": 0
		 *	"msg": 返回成功,
		 *	"data": {
		 *	    []
		 *	  }
		 *	}
		 */ 
	 @RequestMapping(value = "/add", method = RequestMethod.POST)
	 public Result<?> add( AgencyDo  agencyDo,HttpServletRequest request, HttpServletResponse response) {
		 
		 if(null == agencyDo.getUserId() || 0 == agencyDo.getUserId().intValue()){
			 return Result.failureResult("用户ID不能为空!");
		 }
		 AgencyDo  agency =agencyService.getById(agencyDo.getUserId());
		 if(null != agency){
			 return Result.successResult("当前用户已申请代理,请在用户当中查看",agency);
		 }
		 
		 UserDo userDo =userService.getUser(agencyDo.getUserId());
		 if(null == userDo){
			 return Result.successResult("当前用户不存在,请联系管理员",userDo);
		 }
		 
//		 Assert.hasText(agencyDo.getUserName(), "姓名为空");
//		 Assert.hasText("" + agencyDo.getSex(), "性别为空");
//		 Assert.hasText(agencyDo.getMobile(), "手机号码为空");
//		 Assert.hasText(agencyDo.getBankNumber(), "银行号码为空");
//		 Assert.hasText(agencyDo.getBankType(), "银行开户行为空");
//		 Assert.hasText(agencyDo.getIdcardFront(), "身份证正面为空");
//		 Assert.hasText(agencyDo.getIdcardBack(), "身份证背面图像为空");
		 Assert.hasText(agencyDo.getCity(), "代理城市为空");

		 agencyDo.setUserName(userDo.getTrueName());
		 agencyDo.setMobile(userDo.getMobile());
		 agencyDo.setStatus(1);
		 agencyDo.setCreateDate(new Date(System.currentTimeMillis()));
		 agencyDo.setCreateName("前台增加代理管理");
		 agencyService.addAgency(agencyDo);
		 
		 
		 District  district = new District();
		 district.setUserId(agencyDo.getUserId());
		 district.setDistrict(agencyDo.getCity());
		 district.setDistrictStatus(0);
		 districtService.addDistrict(district);
		 
		 return Result.successResult("代理管理增加成功",agencyDo);
	 }
	 
	 /** 
	  * @api {POST} /agency/edit.do 修改代理管理
	  * @apiName editAgency
	  * @apiGroup agency 
	  * @apiVersion 1.0.0 
	  * @apiDescription 修改代理管理
	  *  
	  * @apiUse  agencyParam 
	  *  
	  * @apiUse  agencySucces  
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
	  * 				userName 姓名
	  * 				mobile 手机号码
	  * 				idCard 身份证
	  * 				bankNumber 银行卡
	  * 				bankType 银行卡类型
	  * 				idcardFront 身份证正面照
	  * 				idcardBack 身份证反面照
	  * 				province 省份
	  * 				city 城市
	  * 				createDate 创建时间
	  * 				createName 创建人
	  * 				modifyDate 更新时间
	  * 				modifyName 更新人
	  * 				status 状态(0:删除  1:正常 2:审核通过)
	  * 				remark 备注
	  *			}
	  *		]
	  *	  }
	  *	}
	  */ 
	 @RequestMapping(value = "/edit", method = RequestMethod.POST)
	 public Result<?> edit( AgencyDo  agencyDo,HttpServletRequest request, HttpServletResponse response) {
		 
		 if(null == agencyDo.getId() || 0 == agencyDo.getId().intValue()){
			 return Result.failureResult("ID不能为空!");
		 }
//		 
		 agencyDo.setModifyDate(new Date(System.currentTimeMillis()));
		 agencyDo.setModifyName("前台修改风采");
		 agencyService.updateAgencyById(agencyDo);
		 return Result.successResult("代理管理修改成功",agencyDo);
	 }
	 
	 /** 
	  * @api {POST} /agency/del.do 删除代理管理
	  * @apiName delAgency
	  * @apiGroup agency 
	  * @apiVersion 1.0.0 
	  * @apiDescription 删除代理管理
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
	 public Result<?> del( AgencyDo  agencyDo,HttpServletRequest request, HttpServletResponse response) {
		 
		 if(null == agencyDo.getUserId() || 0 == agencyDo.getUserId().intValue()){
			 return Result.failureResult("ID不能为空!");
		 }
//		 
		 agencyService.deleteById(agencyDo.getUserId());
		 return Result.successResult("代理管理删除成功");
	 }

}
