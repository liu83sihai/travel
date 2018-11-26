package com.dce.business.actions.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.enums.AccountType;
import com.dce.business.common.enums.IncomeType;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.DateUtil;
import com.dce.business.common.util.NumberUtil;
import com.dce.business.entity.account.UserAccountDetailDo;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.etherenum.EthereumAccountDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.account.IPayService;
import com.dce.business.service.third.IEthereumService;
import com.dce.business.service.user.IUserService;

@RestController
@RequestMapping("/account")
public class AccountController extends BaseController {
	private final static Logger logger = Logger.getLogger(AccountController.class);
	@Resource
	private IAccountService accountService;
	@Resource
	private IEthereumService etservice;
	@Resource
	private IPayService payService;
	@Resource
	private IUserService userService;

	/** 
	 * 财务流水
	 * @param userDo
	 * @param bindingResult
	 * @return  
	 */
	@RequestMapping(value = "/flow", method = RequestMethod.POST)
	public Result<?> flow() {
		Integer userId = getUserId();
		String accountType = getString("accountType");

		Assert.hasText(accountType, "账户类型不能为空");
		logger.info("财务流水, userId:" + userId);

		String startTime = getString("startTime");
		String endTime = getString("endTime");

		Map<String, Object> params = new HashMap<>();
		params.put("accountType", accountType);
		params.put("userId", userId);
		if (StringUtils.isNotBlank(startTime)) {
			params.put("startTime", startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			params.put("endTime", endTime);
		}
		List<UserAccountDetailDo> list = accountService.selectUserAccountDetail(params);
		List<Map<String, Object>> result = new ArrayList<>();
		for (UserAccountDetailDo detail : list) {
			Map<String, Object> map = new HashMap<>();
			map.put("userId", detail.getUserId()); //用户名
			map.put("userName", detail.getUserName()); //用户名

			if (null != detail.getIncomeType()) {
				IncomeType type = IncomeType.getByType(detail.getIncomeType());
				map.put("flowType", type == null ? "" : type.getRemark()); //流水类型
			} else {
				map.put("flowType", ""); //流水类型
			}

			map.put("amount", NumberUtil.formatterBigDecimal(detail.getAmount().abs())); //变更数量
			map.put("balanceAmount", NumberUtil.formatterBigDecimal(detail.getBalanceAmount())); //余额
			map.put("createTime", DateUtil.dateToString(detail.getCreateTime()));
			map.put("remark", detail.getRemark());
			
			if (IncomeType.TYPE_POINT_OUT.getIncomeType() == detail.getIncomeType().intValue() || IncomeType.TYPE_POINT_IN.getIncomeType() == detail.getIncomeType().intValue()) {
                map.put("transactionObject", detail.getTransactionObject());
            } else {
                map.put("transactionObject", "");
            }
			
			result.add(map);
		}

		return Result.successResult("查询成功", result);
	}

	@RequestMapping(value = "/ethereum", method = { RequestMethod.POST, RequestMethod.GET })
	public Result<?> ethereum() {
		Integer userId = getUserId();

		BigDecimal totalIncome = BigDecimal.ZERO;
		BigDecimal ethereumNum = etservice.getEthernumAmount(userId);
		EthereumAccountDo etAccount = etservice.getByUserId(userId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("ethereumNum", NumberUtil.formatterBigDecimal(ethereumNum));
		result.put("totalIncome", NumberUtil.formatterBigDecimal(totalIncome));
		result.put("etAccount", etAccount == null ? "" : etAccount.getAccount());

		return Result.successResult("查询成功!", result);
	}

	/** 
	 * 账户基本信息
	 * @return  
	 */
	@RequestMapping(value = "/baseInfo", method = RequestMethod.GET)
	public Result<?> getTradeBaseInfo() {
		Integer userId = getUserId();
		logger.info("账户基本信息, userId:" + userId);

		UserAccountDo account = accountService.selectUserAccount(userId, AccountType.wallet_money.name());
		Map<String, Object> map = new HashMap<>();
		if (account != null && account.getAmount() != null) {

			map.put("pointAmount", account.getAmount()); //美元点余额
		} else {

			map.put("pointAmount", "0.0"); //美元点余额
		}

		UserAccountDo current = accountService.selectUserAccount(userId, AccountType.wallet_travel.name());

		if (current != null && current.getAmount() != null) {

			map.put("coinAmount", current.getAmount()); //奖金币余额
		} else {

			map.put("coinAmount", "0.0"); //奖金币余额
		}

		return Result.successResult("查询成功", map);
	}

	
	/** 
	 * @api {POST} /account/amount.do 券账户余额
	 * @apiName amount
	 * @apiGroup accountRecord 
	 * @apiVersion 1.0.0 
	 * @apiDescription 查询券账户余额
	 * 
	 * @apiParam {String} userId 用户id
	 * @apiParam {String} accountType  券账户类别 “wallet_money”：”现金券账户” “wallet_travel”： “换购积分券账户” “wallet_goods”： “抵用券账户”
	 * 
	 * @apiSuccess {Decimal} amount  余额
	 * @apiSuccess {String} accountType 券账户类别 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccessExample Success-Response: 
	 * HTTP/1.1 200 OK 
	 * * {
	*    "msg": "获取券账户余额成功",
	*    "code": "0",
	*    "data": 
	*        {
	*            "amount": 200
	*        }
	*  }
	**/
	@RequestMapping(value = "/amount", method = { RequestMethod.GET, RequestMethod.POST })
	public Result<?> amount() {
		
		Integer userId = getUserId();
		String accountType = getString("accountType"); 
		
		
//		UserDo user = userService.getUser(userId);
		UserAccountDo account = accountService.getUserAccount(userId,AccountType.getAccountType(accountType));

		Map<String, Object> result = new HashMap<String, Object>();
		if (account == null || account.getAmount() == null) {
			result.put("amount", "0.0");
			result.put("accountType", accountType);
//			result.put("trueName", user.getTrueName());
//			result.put("mobile", user.getMobile());
			return Result.successResult("获取账户信息成功!", result);
		} else {
			result.put("amount", account.getAmount());
			result.put("accountType", accountType);
//			result.put("trueName", user.getTrueName());
//			result.put("mobile", user.getMobile());
			return Result.successResult("获取账户信息成功!", result);
		}
	}

	/**
	 * 原始仓加金、复投 | 报单初始化
	 * @return
	 */
	@RequestMapping(value = "/currentInit", method = { RequestMethod.GET, RequestMethod.POST })
	public Result<?> currentInit() {
		Integer userId = getUserId();
		String accountType = getString("accountType");

		if (StringUtils.isBlank(accountType)) {
			return Result.failureResult("请选择查询的账户类别!");
		}

		AccountType type = AccountType.getAccountType(accountType);
		if (type == null) {
			return Result.failureResult("账户类别不存在");
		}
		return accountService.currentInit(userId, type);
	}

	/**
	 * 查询 现持仓、原始仓、美元点 余额
	 * @return
	 */
	@RequestMapping(value = "/transOut", method = RequestMethod.POST)
	public Result<?> transOut() {
		String accountType = getString("accountType");
		String qty = getString("qty");
		String receiver = getString("receiver");
		String password = getString("password");
		
		logger.info("美元点转出:qty=" + qty + ",receiver=" + receiver);

		Assert.hasText(accountType, "钱包类型不能为空");
		Assert.hasText(password, "交易密码不能为空");
		if (StringUtils.isBlank(qty)) {
			return Result.failureResult("数量不能为空!");
		}

		if (StringUtils.isBlank(receiver)) {
			return Result.failureResult("接收人不能为空!");
		}

		BigDecimal qtyVal = new BigDecimal(qty);
		if (qtyVal.compareTo(BigDecimal.ZERO) <= 0) {
			return Result.failureResult("转出数量必须大于0!");
		}

		Integer userId = getUserId();
		return payService.transOut(userId, qtyVal, accountType, receiver,password);
	}
	
	
	/** 
	 * @api {POST} /account/convertBetweenAccount.do 两个账户之间金额转换
	 * @apiName convertBetweenAccount
	 * @apiGroup accountRecord 
	 * @apiVersion 1.0.0 
	 * @apiDescription 两个账户之间金额转换
	 * 
	 * @apiParam {String} sourceUserId  转出用户id
	 * @apiParam {String} targetUser  转入用户手机号码
	 * @apiParam {Decimal} sourceAmount  转出金额
	 * @apiParam {String} fromAccount  转出账户类型 券账户类别 “wallet_money”：”现金券账户” “wallet_travel”： “换购积分券账户” “wallet_goods”： “抵用券账户”
	 * @apiParam {String} toAccount  转入账户类型,默认为空与转出账户一样 
	 * @apiParam {String} payPassword 转出人支付密码  
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccessExample Success-Response: 
	 * HTTP/1.1 200 OK 
	 * * {
	*    "msg": "获取券账户余额成功",
	*    "code": "0",
	*    "data": 
	*        {
	*            "amount": 200
	*        }
	*  }
	**/
	@RequestMapping(value = "/convertBetweenAccount", method = RequestMethod.POST)
	public Result<?> convertBetweenAccount(Integer sourceUserId, String targetUser, BigDecimal sourceAmount,
			 String fromAccount, String toAccount,String payPassword) {
		
		
		logger.info("账户转出:sourceUserId=" + sourceUserId + ",targetUserId=" + targetUser);
		
		Assert.hasText(fromAccount, "钱包类型不能为空");
		Assert.hasText(payPassword, "交易密码不能为空");
		Assert.hasText(targetUser, "转入用户不能为空");
		
		
		if (null == sourceUserId || null == targetUser ) {
			return Result.failureResult("转入转出用户不能为空!");
		}
		
		if (null == sourceAmount) {
			return Result.failureResult("转出金额不能为空!");
		}
		
		if (sourceAmount.compareTo(BigDecimal.ZERO) <= 0) {
			return Result.failureResult("转出数量必须大于0!");
		}
		
		return payService.transOut(sourceUserId, sourceAmount, fromAccount, targetUser,payPassword);
	}
}
