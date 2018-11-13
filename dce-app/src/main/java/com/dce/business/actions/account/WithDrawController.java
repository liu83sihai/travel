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
import com.dce.business.common.enums.AccountType;
import com.dce.business.common.result.Result;
import com.dce.business.common.token.TokenUtil;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.account.IAccountService;
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
	
	@Resource
	private IAccountService  accountService;

	/**
	 * 用户提现
	 * 
	 * @return
	 */
	/** 
	 * @api {POST} /withdraw/withdraws.do 提交兑换申请
	 * @apiName withdraws
	 * @apiGroup withdraw 
	 * @apiVersion 1.0.0 
	 * @apiDescription 提交兑换申请
	 * 
	 * @apiParam {String} userId 用户id
	 *  @apiParam {Decimal} qty	是	用户要提现的金额（整百整百的取如100,200,300）
	 *  @apiParam {String} password	交易密码
	 *  @apiParam {int} type	是	提现类型 1支付宝 2银行卡 3微信
	 *  @apiParam {String} bank_no	是	账号 （提现到银行卡，账号不显示）
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccessExample Success-Response: 
	 * HTTP/1.1 200 OK 
	 *   {
	*    "code": "0",
	*    "msg": "兑换申请提交成功",
	*    "data":{
	*       
	*      }
	*  }
	**/
	@RequestMapping(value = "/withdraws", method = RequestMethod.POST )
	public Result<?> withdraw(HttpServletRequest request) {
		
		String uri = "";
		String ts = request.getParameter(TokenUtil.TS);
		String sign = request.getParameter(TokenUtil.SIGN);
		Integer userId = getUserId();
		// 验证token
//		boolean flag = TokenUtil.checkToken(uri,userId, ts, sign);
//		if(!flag){
//			return Result.failureResult("登录失效，请重新登录！");
//		};
		
		
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
	/** 
	 * @api {POST} /withdraw/info.do 兑换记录查询
	 * @apiName info
	 * @apiGroup withdraw 
	 * @apiVersion 1.0.0 
	 * @apiDescription 兑换记录查询列表查询
	 * 
	 * @apiParam {String} userId 用户id
	 *  
	 * @apiSuccess {Decimal}   amount	double	提现金额
	*  @apiSuccess {date}   confirmDateStr	date	确认时间
	*  @apiSuccess {String}   user_name	int	用户名
	*  @apiSuccess {Decimal}   confirmedAmt	 成交金额
	*  @apiSuccess {Decimal}   fee	double	手续费
	*  @apiSuccess {String}   mobile	int	手机号
	*  @apiSuccess {String}   processStatusStr	varchar	审批状态
	*  @apiSuccess {String}   remark	varchar	备注
	*  @apiSuccess {String}   withdrawStatus	varchar	提现状态
	*  @apiSuccess {date}   withdrawDateStr	date	提现申请时间
	*  @apiSuccess {String}   bank_no	varchar	账号
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccessExample Success-Response: 
	 * HTTP/1.1 200 OK 
	 *   {
	*    "code": "0",
	*    "msg": "查询成功",
	*    "data": [
	*      {
	*        "amount": 500,
	*        "confirmDateStr": "",
	*        "fack_receive": 0,
	*        "user_name": "A005",
	*        "fee": 0,
	*        "mobile": "88",
	*        "processStatusStr": "待审批",
	*        "money_type": "0",
	*        "remark": "",
	*        "bank_no": "56999896655",
	*        "type": "1",
	*        "userid": 40,
	*        "withdrawStatus": "未到账",
	*        "withdraw_date": 1534500274,
	*        "bank": "",
	*        "true_name": "gh",
	*        "confirm_date": 0,
	*        "process_status": "1",
	*        "name": "",
	*        "withdrawDateStr": "2018-08-17 18:04:34",
	*        "id": 13,
	*        "payment_date": 0
	*      }]
	*  }
	**/
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public Result<List<Map<String, Object>>> withraw2() {

		Integer userId = getUserId();
		Assert.notNull(userId, "用户不存在");
		UserDo userdo = userService.getUser(userId);
		if (userdo == null) {
			return Result.failureResult("用户不存在");
		}
		
//		//总的现金券金额
//		UserAccountDo account = accountService.getUserAccount(userId,AccountType.wallet_money);
//
//		Map<String, Object> result = new HashMap<String, Object>();
//		if (account == null || account.getAmount() == null) {
//			result.put("totalAmt", "0.0");
//		} else {
//			result.put("totalAmt", account.getAmount());
//		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userId);
		List<Map<String, Object>> maps = withdrawService.getWithdrawRecords(map);

		if (maps == null) {
			return Result.failureResult("查询失败");
		}

		return Result.successResult("查询成功", maps);
	}
}
