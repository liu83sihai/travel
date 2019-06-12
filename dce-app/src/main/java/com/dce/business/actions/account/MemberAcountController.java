package com.dce.business.actions.account;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.DateUtil;
import com.dce.business.common.util.OrderCodeUtil;
import com.dce.business.entity.dict.LoanDictDtlDo;
import com.dce.business.entity.order.OrderDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.entity.user.UserParentDo;
import com.dce.business.entity.user.UserRefereeDo;
import com.dce.business.service.dict.ILoanDictService;
import com.dce.business.service.grade.IGradeService;
import com.dce.business.service.order.IOrderService;
import com.dce.business.service.user.IUserParentService;
import com.dce.business.service.user.IUserRefereeService;
import com.dce.business.service.user.IUserService;

/**
 * 会员充值、提现
 * 
 * @author zhangcymf
 * 
 */
@RestController
@RequestMapping("/memberAccount")
public class MemberAcountController extends BaseController {

	private final static Logger logger = Logger.getLogger(MemberAcountController.class);

	@Resource
	private IOrderService orderService;
	@Resource
	private IUserService userService;

	@Resource
	private IUserParentService userParentService;
	
	@Resource
	private IUserRefereeService userRefereeService;
	
	@Resource
	private IGradeService gradeService;
	
    @Autowired
    private ILoanDictService loanDictService;

	/**
	 * 团队成员详情
	 * 
	 * @return
	 */
	/** 
	 * @api {POST} /memberAccount/teamDetails.do 团队成员
	 * @apiName orderInquiry
	 * @apiGroup memberAccount 
	 * @apiVersion 1.0.0 
	 * @apiDescription 团队成员
	 * 
	 * @apiParam {String} userId 用户id
	 * 
	 * @apiSuccess {Decimal} totalYJ	总业绩
	 * @apiSuccess {int} personCount	团队总人数
	 * @apiSuccess {Decimal} communityYJ	小区业绩
	 * @apiSuccess {int} user_level	int	用户级别
	 * @apiSuccess {int} refereeid	int	推荐人id
	 * @apiSuccess {String} true_name	用户真实姓名
	 * @apiSuccess {String} user_name	用户名
	 * @apiSuccess {int} id	int	用户id
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccessExample Success-Response: 
	 * HTTP/1.1 200 OK 
	 * * {
	*   "code": "0",
	*   "msg": "查询成功",
	*   "data": {
	*     "totalYJ": "0",
	*     "personCount":团队总人数
	*     "tuanduilist": [*     
	*       {
	*         "user_level": "vip",
	*         "user": [
	*           {
	*             "refereeid": 40,
	*             "true_name": "yu",
	*             "user_name": "A006",
	*             "mobile": "88",
	*             "user_level": 2,
	*             "id": 41
	*           }
	*         ]
	*       },
	*       {
	*         "user_level": "城市合伙人",
	*         "user": [
	*           {
	*             "refereeid": 40,
	*             "true_name": "突击",
	*             "user_name": "A008",
	*             "mobile": "666",
	*             "user_level": 3,
	*             "id": 42
	*           }
	*         ]
	*       }
	*     ]
	*   },
	*   "success": true
	* }
	**/
	@RequestMapping(value = "/teamDetails", method = RequestMethod.POST)
	public Result<Map<String, Object>> teamDetails() {

		int userId = getUserId();
		Assert.hasText("userId", "用户为空");


		logger.info("用户id----->>" + userId);
		UserDo userdo = userService.getUser(userId);
		if (userdo == null) {
			return Result.failureResult("用户不存在");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("refereeid", userId);
		params.put("distance", 1);
		List<UserRefereeDo> refUserLst = userRefereeService.select(params);
		Map<String, Object> map1 = new HashMap<String, Object>();

		List<Object> listone = new ArrayList<>();
		try {
			//按级别分类
			Map<Byte,List> levelMap = new HashMap<Byte,List>();
			
			Map<String, Object> paraMap = new HashMap<String,Object>();
			for (UserRefereeDo refUser :refUserLst ) {
				UserDo myUser = userService.getUser(refUser.getUserid());
				if(null == myUser) {
					continue;
				}
				List<Map<String,Object>> levelLst = levelMap.get(myUser.getUserLevel());
				if(null == levelLst) {
					levelLst = new ArrayList<Map<String,Object>>();
					levelMap.put(myUser.getUserLevel(), levelLst);
				}
				Map<String,Object> person = new HashMap<String,Object>();
				person.put("true_name", myUser.getTrueName());
				person.put("user_name", myUser.getUserName());
				
				paraMap.clear();
				paraMap.put("userId", myUser.getId());
				Map<String, Object> totalMap = orderService.selectSum(paraMap);
				if(null != totalMap && totalMap.get("Totalperformance") != null) {
					person.put("mobile",totalMap.get("Totalperformance"));
				}else {
					person.put("mobile",0);
				}
				person.put("refereeid", myUser.getRefereeid());
				person.put("user_level", myUser.getUserLevel());
				person.put("id", myUser.getId());
				levelLst.add(person);
			}
			
			// 0-7级
			for (int j = 0; j <= 8; j++) {
				Map<String, Object> map = new HashMap<String, Object>();
				List<Map> maplist = new ArrayList<Map>();
				LoanDictDtlDo memberLevelDict = loanDictService.getLoanDictDtl("member_type", String.valueOf(j));
				map.put("user_level", memberLevelDict == null?"" : memberLevelDict.getName());
				List<Map<String,Object>> levelLst = levelMap.get((byte)j);
				map.put("user", levelLst == null? Collections.emptyList(): levelLst);
				listone.add(map);
			}
			
			map1.put("tuanduilist", listone);
			
			//团队总业绩
			paraMap.clear();
			paraMap.put("userId", userdo.getId());
			Map<String, Object> totalMap = orderService.selectSum(paraMap);
			if(null != totalMap && totalMap.get("Totalperformance") != null) {
				map1.put("totalYJ",totalMap.get("Totalperformance"));
			}else {
				map1.put("totalYJ","0");
			}
			//总推荐人数
			paraMap.clear();
			paraMap.put("refereeid", userId);
			List<UserRefereeDo> refUserLst2 = userRefereeService.select(paraMap);
			map1.put("personCount", refUserLst2==null? 0 : refUserLst2.size());
			//计算小区业绩
			BigDecimal totalYj = BigDecimal.ZERO;
			BigDecimal maxYj = BigDecimal.ZERO;
			for(List<Map<String,Object>>  l : levelMap.values()) {
				for(Map<String,Object> m : l) {
					BigDecimal yj =  new BigDecimal(m.get("mobile").toString());
					totalYj =totalYj.add(yj);
					if(yj.compareTo(maxYj)>0) {
						maxYj = yj;
					}
				}
			}
			//除最大一个数的和=小区业绩
			map1.put("communityYJ", totalYj.subtract(maxYj));
			
		} catch (IllegalArgumentException t) {
			t.printStackTrace();
			return Result.failureResult(t.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
			return Result.failureResult("查询失败");
		}

		return Result.successResult("查询成功", map1);

	}

	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	public Result<?> recharge() {
		Integer userId = getUserId();
		String price = getString("price");
		String qty = getString("qty");
		logger.info("会员充值, userId:" + userId + "; price:" + price + "; qty:" + qty);
		Assert.hasText(price, "买入价格不能为空");
		Assert.hasText(qty, "买入数量不能为空");

		OrderDo orderDo = new OrderDo();
		orderDo.setOrderCode(OrderCodeUtil.genOrderCode(userId));
		orderDo.setOrderType(Integer.valueOf(1));
		orderDo.setUserId(userId);
		orderDo.setPrice(new BigDecimal(price));
		orderDo.setQty(new BigDecimal(qty));
		orderDo.setTotalPrice(orderDo.getQty().multiply(orderDo.getPrice()).setScale(4, RoundingMode.HALF_UP));
		orderDo.setOrderStatus(1); // 有效
		orderDo.setPayStatus(0); // 未成交
		orderDo.setCreateTime(new Date());
		// Long orderId = orderService.addOrder(orderDo);

		return Result.successResult("充值成功", 2);
	}

	/**
	 * 查看用户的所有子节点
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listMyMember", method = RequestMethod.POST)
	public Result<List<UserParentDo>> listMyMember() {

		Map<String, Object> params = new HashMap<String, Object>();

		String pageNum = getString("pageNum");
		String isActivity = getString("isActivity"); // 是否激活 1激活 2未激活
		String startTime = getString("startTime");
		String endTime = getString("endTime");
		String searchStr = getString("searchStr");

		if (StringUtils.isNotBlank(startTime)) {
			try {
				params.put("startTime", DateUtil.parserStr(startTime, 0).getTime());
			} catch (ParseException e) {
				logger.error("时间格式错误startTime:" + e);
				Result.failureResult("时间格式错误,查询失败!");
			}
		}
		if (StringUtils.isNotBlank(endTime)) {
			try {
				params.put("endTime", DateUtil.parserStr(endTime, 1).getTime());
			} catch (ParseException e) {
				logger.error("时间格式错误endTime:" + e);
				Result.failureResult("时间格式错误,查询失败!");
			}
		}
		if (StringUtils.isNotBlank(searchStr)) {
			searchStr = searchStr.trim();
			boolean result = searchStr.matches("[0-9]+");
			if (result) {
				if (searchStr.length() > 3) {
					params.put("mobile", searchStr);
				}
			} else if (searchStr.contains("@")) {
				params.put("email", searchStr);
			} else {
				params.put("userName", searchStr);
			}
		}
		if (StringUtils.isBlank(isActivity)) {
			isActivity = null; // 默认查所有
		}
		// 不传 默认查询第一页
		if (StringUtils.isBlank(pageNum)) {
			pageNum = "1";
		}
		// 默认每页显示10条
		int rows = 200;

		int offset = (Integer.parseInt(pageNum) - 1) * rows;

		Integer userId = getUserId();

		params.put("refereeId", userId);
		params.put("offset", offset);
		params.put("rows", rows);
		params.put("isActivity", isActivity);
		params.put("distance", 1);

		List<UserParentDo> userList = userService.getMyMember(params);
		return Result.successResult("查询成功", userList);
	}

	/**
	 * 查看组织结构树
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listMyOrg", method = RequestMethod.POST)
	public Result<List<Map<String, Object>>> listMyOrg() {
		String parentId = getString("parentId");
		String userName = getString("userName");// 用户编号
		String queryModel = getString("queryModel");// 查询方式 0 ：parentId， 1
													// userName
		if (StringUtils.isBlank(parentId) && StringUtils.isBlank(userName)) {
			return Result.failureResult("请选择要查询的用户!");
		}
		String level = getString("level");
		if (StringUtils.isBlank(level)) {
			level = "1";
		}

		Integer paraParentId = null;

		if ("1".equals(queryModel)) {
			UserDo user = userService.userName(userName);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userid", user.getId());
			param.put("parentid", this.getUserId());
			List<UserParentDo> uParents = userParentService.select(param);
			if (CollectionUtils.isEmpty(uParents)) {
				return Result.failureResult("错误的参数");
			}
			paraParentId = user.getId();
		} else {
			paraParentId = Integer.parseInt(parentId);
		}

		List<Map<String, Object>> userList = userService.listMyOrg(paraParentId, Integer.parseInt(level));
		return Result.successResult("查看组织结构树", userList);
	}

	/**
	 * 查看直推树
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listMyRef", method = RequestMethod.POST)
	public Result<List<Map<String, Object>>> listMyRef() {
		Integer userId = getUserId();
		String refereeid = getString("userid");

		logger.info("查看直推树:refereeid=" + refereeid);
		if (StringUtils.isBlank(refereeid)) {
			refereeid = userId + "";
		}
		String pageNum = getString("pageNum");
		// 不传 默认查询第一页
		if (StringUtils.isBlank(pageNum)) {
			pageNum = "1";
		}

		// 默认每页显示10条
		int rows = 100;

		int offset = (Integer.parseInt(pageNum) - 1) * rows;

		List<Map<String, Object>> userList = userService.listMyRef(Integer.parseInt(refereeid), offset, rows);
		String hadReq = (String) getRequest().getSession().getAttribute("hadReq");
		if (userId.intValue() == Integer.parseInt(refereeid) && StringUtils.isNotBlank(hadReq)) {
			UserDo user = userService.getUser(userId);
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("user_name", user.getTrueName() + "[" + user.getUserName() + "]");
			root.put("total_performance", user.getTotalPerformance());
			root.put("referee_number", user.getRefereeNumber());
			root.put("user_level", user.getUserLevel());
			root.put("reg_time", user.getRegTime());
			root.put("userid", user.getId());
			root.put("refereeid", 0);
			root.put("open", true);

			userList.add(root);
		}

		getRequest().getSession().removeAttribute("hadReq");
		return Result.successResult("我的推荐", userList);
	}

}