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
import com.dce.business.entity.dict.LoanDictDo;
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
	private ILoanDictService dictService;
	
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
	 * @api {GET} /dict/index.do 字典管理列表
	 * @apiName dictList
	 * @apiGroup dict 
	 * @apiVersion 1.0.0 
	 * @apiDescription 字典管理列表
	 *  
	 * @apiUse pageParam  
	 
	 * @apiUse dictSucces  
	 * @apiUse RETURN_MESSAGE
	
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "code": 0
	 *	"msg": 返回成功,
	 *	"data": {
	 *	    [
	 *			{
	 * 				id 主键ID
	 * 				code 编码
	 * 				name 名称
	 * 				status 状态
	 * 				remark 备注
	 * 				createUserId 创建用户ID
	 * 				updateUserId 修改用户ID
	 * 				createTime 创建时间
	 * 				updateTime 修改时间
	 *			}
	 *		]
	 *	  }
	 *	}
	 */ 
	 @RequestMapping("/index")
	 public Result<?> list() {
		 logger.info("查询字典管理...");
	
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
		PageDo<LoanDictDo> dictPage = new PageDo<LoanDictDo>();
		dictPage.setCurrentPage(pageNum);
		dictPage.setPageSize(rows);
//		PageDo<LoanDictDo> pageDo = dictService.queryListPage
//		List<LoanDictDo> dictList = pageDo.getModelList();
		 
//		List<Map<String, Object>> result = new ArrayList<>();
//	    if (!CollectionUtils.isEmpty(dictList)) {
//	         for (LoanDictDo dict : dictList) {
//
//	             Map<String, Object> map = new HashMap<>();
//	             map.put("id", dict.getId());
//	             map.put("code", dict.getCode());
//	             map.put("name", dict.getName());
//	             map.put("status", dict.getStatus());
//	             map.put("remark", dict.getRemark());
//	             map.put("createUserId", dict.getCreateUserId());
//	             map.put("updateUserId", dict.getUpdateUserId());
//	             map.put("createTime", dict.getCreateTime());
//	             map.put("updateTime", dict.getUpdateTime());
//	                result.add(map);
//	           }
//	        }
		 return Result.successResult("查询成功", paramMap);
	 }
	 

}
