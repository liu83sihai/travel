package com.dce.business.actions.dict;

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
import com.dce.business.entity.agency.AgencyDo;
import com.dce.business.entity.dict.LoanDictDo;
import com.dce.business.entity.dict.LoanDictDtlDo;
import com.dce.business.entity.notice.NoticeDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.dict.ILoanDictService;

/**
 * 字典管理授权业务实现类
 *
 * @author  Fantasy
 * @date    generate
 * @version 1.0.0
 * @pageage com.dce.business.actions.DictController.java
 */
@RestController
@RequestMapping("/dict")
public class LoanDictController  extends BaseController{
	private final static Logger logger = Logger.getLogger(LoanDictController.class);
	@Resource
	private ILoanDictService loandictService;
	
	/**
	 *  @apiDefine dictSucces
	 *	@apiSuccess {java.lang.Long}  id 主键ID
	 *	@apiSuccess {java.lang.String}  code 编码
	 *	@apiSuccess {java.lang.String}  name 名称
	 *	@apiSuccess {java.lang.String}  status 状态
	 *	@apiSuccess {java.lang.String}  remark 备注
	 *	@apiSuccess {java.lang.Long}  createUserId 创建用户ID
	 *	@apiSuccess {java.lang.Long}  updateUserId 修改用户ID
	 *	@apiSuccess {java.sql.Timestamp}  createTime 创建时间
	 *	@apiSuccess {java.sql.Timestamp}  updateTime 修改时间
	 */
	
	/**
	 *  @apiDefine dictParam
	 *	@apiParam {java.lang.Long}  id 主键ID
	 *	@apiParam {java.lang.String}  code 编码
	 *	@apiParam {java.lang.String}  name 名称
	 *	@apiParam {java.lang.String}  status 状态
	 *	@apiParam {java.lang.String}  remark 备注
	 *	@apiParam {java.lang.Long}  createUserId 创建用户ID
	 *	@apiParam {java.lang.Long}  updateUserId 修改用户ID
	 *	@apiParam {java.sql.Timestamp}  createTime 创建时间
	 *	@apiParam {java.sql.Timestamp}  updateTime 修改时间
	 */
	
	/** 
	 * @api {GET} /dict/getPay.do 商品付款方式
	 * @apiName getPay
	 * @apiGroup dict 
	 * @apiVersion 1.0.0 
	 * @apiDescription 商品付款方式
	 *  
	 *  
	 * @apiUse RETURN_MESSAGE
	 *	@apiSuccess {java.lang.String}  code 付款方式,多种以分号隔开，中间=表示可以%比，例如1=60表示200元的商品只能用120元的现金付款，  1=80：现金80%付款,2=100:积分100可付款,3=40:现金账户,4=100:券
	 *	@apiSuccess {java.lang.String}  name 名称 travelCard:旅游卡，travelLine：旅游路线，pointGood：积分商品,vipGood:会员商品，supplierSale：商家消费
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "code": 0
	 *	"msg": 返回成功,
	 *	"data": {
	 *	    [
	 *		]
	 *	  }
	 *	}
	 */ 
	 @RequestMapping("/getPay")
	public Result<?> getPay() {

		logger.info("获取商品付款方式...");
		LoanDictDo loandictDo = loandictService.getLoanDict("payment");
		if (null != loandictDo) {
			List<LoanDictDtlDo> ldDtl = loandictService.getDictDetailByDictId(loandictDo.getId());
			return Result.successResult("查询成功", ldDtl);
		}

		return Result.successResult("查询失败", loandictDo);
	}
	 

}
