package com.dce.business.actions.account;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.common.token.TokenUtil;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.account.IPayService;
import com.dce.business.service.trade.IWithdrawService;
import com.dce.business.service.user.IUserService;

@RestController
@RequestMapping("/withdraw")
public class WithDrawController extends BaseController {
	private final static Logger logger = Logger.getLogger(WithDrawController.class);

	@Resource
	private IPayService payService;
	@Resource
	private IWithdrawService withdrawService;
	@Resource
	private IUserService userService;

	/**
	 * 用户提现
	 * 
	 * @return
	 */
	@RequestMapping(value = "/withdraws", method = RequestMethod.POST )
	public Result<?> withdraw(HttpServletRequest request) {
		
		String uri = "";
		String ts = request.getParameter(TokenUtil.TS);
		String sign = request.getParameter(TokenUtil.SIGN);
		Integer userId = getUserId();
		// 验证token
		boolean flag = TokenUtil.checkToken(uri,userId, ts, sign);
		if(!flag){
			return Result.failureResult("登录失效，请重新登录！");
		};
		
		
		String password = getString("password");
		String qty = getString("qty");
		String type = getString("type");
		String bank_no = getString("bank_no"); // 账号

		Assert.hasText(password, "提现密码不能为空");
		Assert.hasText(qty, "提现数量不能为空");
		Assert.hasText(type, "提现方式不能为空");
		if (!type.equals("2")) {
			Assert.hasText(bank_no, "账号不能为空");
		}
		logger.info("用户提现, userId:" + userId + "; qty:" + qty + "type:" + type);

		UserDo userdo = userService.getUser(userId);

		if (userdo == null) {

			return Result.failureResult("用户不存在");

		}

		return payService.withdraw(getUserId(), password, type, new BigDecimal(qty), bank_no);
	}

	/**
	 * 提现记录查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public Result<List<Map<String, Object>>> withraw2() {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("userid", getString("userId"));

		Assert.hasText(getString("userId"), "用户不存在");

		UserDo userdo = userService.getUser(getUserId());

		if (userdo == null) {

			return Result.failureResult("用户不存在");

		}

		List<Map<String, Object>> maps = withdrawService.getWithdrawRecords(map);

		if (maps == null) {

			return Result.failureResult("查询失败");
		}

		return Result.successResult("查询成功", maps);
	}
}
