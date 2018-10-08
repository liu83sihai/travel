package com.dce.business.actions.account;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.DateUtil;
import com.dce.business.common.util.OrderCodeUtil;
import com.dce.business.entity.order.OrderDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.entity.user.UserParentDo;
import com.dce.business.service.grade.IGradeService;
import com.dce.business.service.order.IOrderService;
import com.dce.business.service.user.IUserParentService;
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
	private IGradeService gradeService;

	/**
	 * 团队成员详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "/teamDetails", method = RequestMethod.POST)
	public Result<Map<String, Object>> teamDetails() {

		int userId = getUserId();

		Assert.hasText("userId", "用户为空");

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("userId", userId);

		logger.info("用户id----->>" + userId);

		UserDo userdo = userService.getUser(userId);

		if (userdo == null) {

			return Result.failureResult("用户不存在");

		}

		List<Map<String, Object>> list = userParentService.TeamDetails(params);

		Map<String, Object> map1 = new HashMap<String, Object>();

		List<Object> listone = new ArrayList<>();
		System.out.println("团员--------》》" + list);
		String level = "";
		try {
			for (int j = 0; j <= 4; j++) {
				Map<String, Object> map = new HashMap<String, Object>();
				List<Map<String, Object>> maplist = new ArrayList<>();

				/*
				 * if(j==0){ level="普通用户"; }else if(j==1){ level="会员"; }else
				 * if(j==2){ level="vip"; }else if(j==3){ level="城市合伙人"; }else
				 * if(j==4){ level="股东"; }
				 */

				Map<String, Object> param = new HashMap<String, Object>();
				param.put("grade_mark", j);

				map.put("user_level", gradeService.selectgreadname(param).get(j).getGradeName());

				for (int i = 0; i < list.size(); i++) {

					if (list.get(i).get("user_level").equals(j)) {
						maplist.add(list.get(i));
					}
					System.out.println("级别-------》》》" + list);
				}
				map.put("user", maplist);
				listone.add(map);

			}
			map1.put("tuanduilist", listone);

			if (orderService.selectSum(params) == null) {

				map1.put("totalYJ", "0");
			} else {

				map1.put("totalYJ", orderService.selectSum(params).get("Totalperformance"));
			}

		} catch (IllegalArgumentException t) {
			// TODO Auto-generated catch block
			t.printStackTrace();
			return Result.failureResult(t.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
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
		// String password = getString("password");
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