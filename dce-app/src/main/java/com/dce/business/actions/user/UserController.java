package com.dce.business.actions.user;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.dce.business.actions.common.BaseController;
import com.dce.business.common.enums.AccountType;
import com.dce.business.common.enums.DictCode;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.common.token.TokenUtil;
import com.dce.business.common.util.DataEncrypt;
import com.dce.business.common.util.NumberUtil;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.dict.LoanDictDtlDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.dict.ILoanDictService;
import com.dce.business.service.message.INewsService;
import com.dce.business.service.user.IUserService;
import com.dce.business.service.user.UserAdressService;

/**
 * 账户处理器，注册、登录等
 * 
 * @author parudy
 * @date 2018年3月24日
 * @version v1.0
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
	private final static Logger logger = Logger.getLogger(UserController.class);

	@Resource
	private IUserService userService;
	@Resource
	private IAccountService accountService;
	@Resource
	private INewsService newsService;
	@Resource
	private ILoanDictService loanDictService;
	@Resource
	private UserAdressService addressService;
	// @Resource
	// private IReleaseService staticAwardService;

	/** 
	 * @api {POST} /user/reg.do 用户注册
	 * @apiName reg
	 * @apiGroup user 
	 * @apiVersion 1.0.0 
	 * @apiDescription 用户注册
	 * 
	 * @apiParam {String} userName 手机号码
	 * @apiParam {String} userPassword 登录密码
	 * @apiParam {String} refereeUserMobile 用户推荐人，填写用户手机号
	 * @apiParam {String} twoPassword 支付密码
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {int} id 用户ID
	 * @apiSuccess {String} token 用户token
	 * @apiSuccess {String} certification 真名
	 * @apiSuccess {int} refereeNumber 推荐人数
	 * 
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *	}
	 */ 
	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public Result<?> reg(@Valid UserDo userDo, BindingResult bindingResult) {
		logger.info("用户注册");
		// 参数校验
		if (bindingResult.hasErrors()) {
			List<ObjectError> errors = bindingResult.getAllErrors();
			logger.info("用户注册，参数校验错误：" + JSON.toJSONString(errors));
			return Result.failureResult(errors.get(0).getDefaultMessage());
		}

		Result<?> result = userService.reg(userDo);

		logger.info("用户注册结果:" + JSON.toJSONString(result));
		return result;
	}

	/** 
	 * @api {POST} /user/login.do 用户登录
	 * @apiName login
	 * @apiGroup user 
	 * @apiVersion 1.0.0 
	 * @apiDescription 用户登录
	 * 
	 * @apiParam {String} userName 手机号码
	 * @apiParam {String} password 用户密码
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {int} id 用户ID
	 * @apiSuccess {String} token 用户token
	 * @apiSuccess {String} certification 真名
	
	 * @apiSuccess {int} refereeNumber 推荐人数
	 * 
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *	}
	 */ 
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result<?> login() {
		// String mobile = getString("mobile");
		String userName = getString("userName");
		String password = getString("password");

		Assert.hasText(userName, "请输入手机号");
		Assert.hasText(password, "请输入密码");

		userName = userName.trim();
		password = password.trim();

		password = DataEncrypt.encrypt(password);

		logger.info("用户登录, userName:" + userName + "; password:" + password);
		UserDo userDo = userService.userName(userName);
		Assert.notNull(userDo, "手机号不存在");

		if (!userName.equals(userDo.getUserName()) || !password.equals(userDo.getUserPassword())) {
			return Result.failureResult("手机号或者密码不正确");
		}

		if (userDo.getStatus().intValue() != 0) {
			return Result.failureResult("当前用户已被锁定,不允许登录!");
		}
		String token = TokenUtil.createToken(userDo.getId());
		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		map.put("userId", userDo.getId());
		map.put("certification", userDo.getCertification());
		
		return Result.successResult("登录成功", map);
	}

	/** 
	 * @api {POST} /user/logout.do 用户注销
	 * @apiName logout
	 * @apiGroup user 
	 * @apiVersion 1.0.0 
	 * @apiDescription 用户注销
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {String} msg 注销成功
	 * 
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {}
	 */ 
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public Result<?> logout() {
		Integer userId = getUserId();

		logger.info("用户注销, userId:" + userId);

		TokenUtil.deleteToken(userId);

		return Result.successResult("注销成功");
	}

	/** 
	 * @api {POST} /user/alterpass.do 修改登录密码
	 * @apiName alterpass
	 * @apiGroup user 
	 * @apiVersion 1.0.0 
	 * @apiDescription 修改登录密码
	 * 
	 * @apiParam {String} userId 用户ID
	 * @apiParam {String} userPassword 用户需修改的密码
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {String} msg 注销成功
	 * 
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {}
	 */ 
	@RequestMapping(value = "/alterpass", method = RequestMethod.POST)
	public Result<?> alterUser(UserDo userDo) {
		try {
			Integer userId = getUserId();
			Assert.hasText(userDo.getUserPassword(), "修改的密码为空");
			logger.info("修改用户登录密码的ID，userId:" + userId);
			userDo.setId(userId);
			// 登录密码加密
			if (StringUtils.isNotBlank(userDo.getUserPassword())) {
				userDo.setUserPassword(DataEncrypt.encrypt(userDo.getUserPassword()));
			}
			return userService.updateByPrimaryKeyLogPass(userDo);
		} catch (Exception e) {
			return Result.failureResult("用户未登录,密码修改失败");
		}
	}

	/** 
	 * @api {POST} /user/updPayPass.do 修改支付密码
	 * @apiName updPayPass
	 * @apiGroup user 
	 * @apiVersion 1.0.0 
	 * @apiDescription 修改支付密码
	 * 
	 * @apiParam {String} userId 用户ID
	 * @apiParam {String} twoPassword 用户需修改的密码
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {String} msg 注销成功
	 * 
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {}
	 */ 
	@RequestMapping(value = "/updPayPass", method = RequestMethod.POST)
	public Result<?> updPayPass(UserDo userDo) {
		try {
			Integer userId = getUserId();
			Assert.hasText(userDo.getTwoPassword(), "支付密码不能为空");
			logger.info("修改用户支付密码，userId:" + userId); // 更改支付密码的用户信息
			userDo.setId(userId);
			// 支付密码加密
			if (StringUtils.isNotBlank(userDo.getTwoPassword())) {
				userDo.setTwoPassword(DataEncrypt.encrypt(userDo.getTwoPassword()));
			}
			return userService.updateByPrimaryKeyPayPass(userDo);
		} catch (Exception e) {
			return Result.failureResult("用户未登录,支付密码修改失败");
		}
	}

	/**
	 * 模糊搜索用户列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Result<?> list() {
		String userName = getString("userName");
		String type = getString("type"); // 查询类型，type=1表示推荐人、type=2表示接点人
		logger.info("模糊查询, userName:" + userName + "; type:" + type);

		Assert.hasText(userName, "请输入用户名");

		List<UserDo> list = userService.list(userName);

		return Result.successResult("查询成功", list);
	}
	
	/** 
	 * @api {POST} /user/shareList.do 分享排榜
	 * @apiName shareList
	 * @apiGroup user 
	 * @apiVersion 1.0.0 
	 * @apiDescription 分享排榜
	 * 
	 * @apiUse pageParam  
	 * 
	 * @apiSuccess {String} msg 返回成功信息
	 * @apiUse RETURN_MESSAGE
	 
	 * @apiSuccess {int} id 用户ID
	 * @apiSuccess {String} userName 用户名称
	 * @apiSuccess {String} trueName 真名
	 * @apiSuccess {String} mobile 手机号码
	 * @apiSuccess {int} refereeNumber 推荐人数
	 * 
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "result": {
	 *	"model": {
	 *		
	 * 	},
	 *	  "status": {
	 *	    "code": 200,
	 *	    "msg": "请求成功"
	 *	  }
	 *	}
	 */ 
	@RequestMapping(value = "/shareList", method = RequestMethod.POST)
	public Result<List<Map<String, Object>>> shareList() {
		String pageNums = getString("pageNum");
		String row = getString("rows");
		// 不传 默认查询第一页
		if (StringUtils.isNotBlank(pageNums)) {
			pageNum = Integer.parseInt(pageNums);
		}
		if (StringUtils.isNotBlank(row)) {
			rows = Integer.parseInt(row);
		}
		
		int offset = (int) ((pageNum - 1) * rows);
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("offset", offset);
		paramMap.put("rows", rows);

		List<Map<String, Object>> shareList = userService.shareList(paramMap);
		
		return Result.successResult("分享排榜", shareList);
	}

	/** 
	 * @api {POST} /user/getUserInfo.do 获取用户信息
	 * @apiName getUserInfo
	 * @apiGroup user 
	 * @apiVersion 1.0.0 
	 * @apiDescription 获取用户信息
	 * 
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {int} id 用户ID
	 * @apiSuccess {String} token 用户token
	 *
	 *@apiSuccess {int} userLevel 用户等级，默认为：0：普通用户；1：会员;2：VIP；3：城市合伙人；4：股东
	 *@apiSuccess {String} mobile 手机号
	 *@apiSuccess {String} trueName 用户姓名
	 *@apiSuccess {String} idnumber 身份证号码
	 *@apiSuccess {String} sex 默认为：0（无）；男为：1；女为：2
	 *@apiSuccess {String} refereeid 用户推荐人的手机号码
	 *@apiSuccess {String} banknumber 银行卡卡号
	 *@apiSuccess {String} banktype 银行卡开户行
	 *@apiSuccess {String} certification 用户认证状态，默认为：0（未认证） 1:已实名认证
	 *@apiSuccess {String} userImage 用记头像
	 *@apiSuccess {String} idcardFront 用户身份证正面
	 *@apiSuccess {String} idcardBack  用户身份背面
	 *@apiSuccess {Bigdecimal} amount  现金用户
	 *@apiSuccess {Bigdecimal} originalAmount  抵用券
	 *@apiSuccess {Bigdecimal} pointAmount  积分
	 * 
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *	}
	 */ 
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Result<?> getUserInfo() {
		Integer userId = getUserId();

		logger.info("查询用户基本信息，userId:" + userId);

		// 用户信息
		UserDo userDo = userService.getUser(userId);
		UserDo newUserDo = new UserDo();
		newUserDo.setId(userDo.getId());
		newUserDo.setUserName(userDo.getUserName());
		newUserDo.setTrueName(userDo.getTrueName());
		newUserDo.setUserLevel(userDo.getUserLevel());
		newUserDo.setReleaseTime(userDo.getReleaseTime()); // 释放时间
		newUserDo.setUserFace(userDo.getUserFace());
		newUserDo.setRefereeNumber(userDo.getRefereeNumber());
		newUserDo.setUserLevelName(userDo.getUserLevel() + "");
		//用户身份证信息
		newUserDo.setUserImage(userDo.getUserImage());
		newUserDo.setIdcardBack(userDo.getIdcardBack());
		newUserDo.setIdcardFront(userDo.getIdcardFront());
		
		
		// 用户等级
		List<LoanDictDtlDo> leves = loanDictService.queryDictDtlListByDictCode(DictCode.BaoDanFei.getCode());
		if (!CollectionUtils.isEmpty(leves)) {
			for (LoanDictDtlDo dtl : leves) {
				if (dtl.getCode().equals(userDo.getUserLevel() + "")) {
					newUserDo.setUserLevelName(dtl.getName());
					break;
				}
			}
		}
		// 推荐人
		if (userDo.getRefereeid() != null) {
			UserDo referee = userService.getUser(userDo.getRefereeid());
			if (referee != null) {
				newUserDo.setRefereeUserName(referee.getUserName());
			}
		}

		// 接点人
		if (userDo.getParentid() != null) {
			UserDo parent = userService.getUser(userDo.getParentid());
			if (parent != null) {
				newUserDo.setParentUserName(parent.getUserName());
			}
		}

		// 财务信息
		Map<String, Object> accountInfo = new HashMap<>();
		accountInfo.put("amount", NumberUtil.formatterBigDecimal(getAccountAmount(userId, AccountType.wallet_money))); // 现金账户
		accountInfo.put("originalAmount",
				NumberUtil.formatterBigDecimal(getAccountAmount(userId, AccountType.wallet_travel))); // 抵用券
		accountInfo.put("pointAmount",
				NumberUtil.formatterBigDecimal(getAccountAmount(userId, AccountType.wallet_goods))); // 积分
//		accountInfo.put("frozenDeposit",
//				NumberUtil.formatterBigDecimal(getAccountAmount(userId, AccountType.wallet_active))); // 

		Map<String, Object> topInfoMap = new HashMap<>();
		topInfoMap.put("decCount", "");
		topInfoMap.put("total", 0);
		topInfoMap.put("canUse", 0);
		topInfoMap.put("apply", 0);

		// DCE最新消息
//		NewsDo message = newsService.selectLatestNews();
//		if (message != null) {
//			topInfoMap.put("dceMsg", message.getTitle());
//		} else {
//
//			topInfoMap.put("dceMsg", "");
//		}
		Map<String, Object> map = new HashMap<>();
		map.put("userInfo", newUserDo);
		map.put("userAccountDo", accountInfo);
		map.put("topInfo", topInfoMap);
		return Result.successResult("查询成功", map);
	}

	/**
	 * 查询账户余额
	 * 
	 * @param userId
	 * @param accountType
	 * @return
	 */
	private BigDecimal getAccountAmount(Integer userId, AccountType accountType) {
		UserAccountDo userAccountDo = accountService.getUserAccount(userId, accountType);
		if (userAccountDo != null && userAccountDo.getAmount() != null) {
			return userAccountDo.getAmount();
		}

		return BigDecimal.ZERO;
	}

	/** 
	 * @api {POST} /user/authentication.do 用户认证
	 * @apiName authentication
	 * @apiGroup user 
	 * @apiVersion 1.0.0 
	 * @apiDescription 用户认证
	 * 
	 * @apiParam {Integer} userId 用户ID
	 * @apiParam {String} trueName 真名
	 * @apiParam {String} mobile 手机
	 * @apiParam {String} idnumber 身份证号
	 * @apiParam {String} sex 性别 1男 2女
	 * @apiParam {String} banknumber 卡号
	 * @apiParam {String} banktype 开户行
	 * @apiParam {String} idcardFront 身份证正面像
	 * @apiParam {String} idcardBack 身份证背面像
	 * @apiParam {String} userImage 用户图像
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {String} msg 认证成功
	 * 
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {}
	 */ 
	@RequestMapping(value = "/authentication", method = RequestMethod.POST)
	public Result<?> authentication() {
		try {

			String  userId = getString("userId");
			String trueName = getString("trueName");
			String mobile = getString("mobile");
			String idnumber = getString("idnumber");
			String sex = getString("sex");
			String banknumber = getString("banknumber");// 卡号
			String banktype = getString("banktype");// 开卡行
			String idcardFront = getString("idcardFront");// 开卡行
			String idcardBack = getString("idcardBack");// 开卡行
			String userImage = getString("userImage");// 开卡行

			System.err.println("shuju----" + trueName);

			logger.info("用户信息，userId:" + userId);

			Assert.hasText(userId, "用户ID为空");
			Assert.hasText(trueName, "姓名不能为空");
			Assert.hasText(mobile, "手机号码不能为空");
			Assert.hasText(idnumber, "身份证不能为空");
			Assert.hasText(sex, "性别不能为空");
			Assert.hasText(banktype, "开卡行不能为空");
			Assert.hasText(banknumber, "卡号不能为空");
			Assert.hasText(idcardFront, "身份证正背照片不能为空");
			Assert.hasText(idcardBack, "身份证背面照片不能为空");
			// 用户信息
			UserDo userDo = new UserDo();
			userDo.setId(Integer.valueOf(userId));
			userDo.setMobile(mobile);
			userDo.setTrueName(trueName);
			userDo.setIdnumber(idnumber);
			userDo.setSex(Integer.parseInt(sex));
			userDo.setBanknumber(banknumber);
			userDo.setBanktype(banktype);
			
			userDo.setIdcardFront(idcardFront);
			userDo.setIdcardBack(idcardBack);
			userDo.setUserImage(idcardBack);
			// 更改用户认证状态
			userDo.setCertification(1);

			// 手机号验证

			/*
			 * String REGEX_MOBILE =
			 * "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
			 * Pattern p = Pattern.compile(REGEX_MOBILE); Matcher m =
			 * p.matcher(mobile); if (!m.matches()) { return
			 * Result.failureResult("手机号码错误"); }
			 */

			// 身份号验证
			/*
			 * if (!isIDNumber(idnumber)) {
			 * 
			 * return Result.failureResult("该身份证号不合法"); }
			 */

			System.out.println("用户信息----------》》》" + userDo);

			return userService.Authentication(userDo);

		} catch (IllegalArgumentException t) {
			t.printStackTrace();
			logger.error("用户信息认证失败", t);
			return Result.failureResult(t.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户信息认证失败", e);
			return Result.failureResult("用户信息认证失败");
		}

	}

	/**
	 * 身份证校验
	 * 
	 * @param IDNumber
	 * @return
	 */
	public static boolean isIDNumber(String IDNumber) {
		// 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
		String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|"
				+ "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";

		boolean matches = IDNumber.matches(regularExpression);

		// 判断第18位校验值
		if (matches) {

			if (IDNumber.length() == 18) {
				try {
					char[] charArray = IDNumber.toCharArray();
					// 前十七位加权因子
					int[] idCardWi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
					// 这是除以11后，可能产生的11位余数对应的验证码
					String[] idCardY = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
					int sum = 0;
					for (int i = 0; i < idCardWi.length; i++) {
						int current = Integer.parseInt(String.valueOf(charArray[i]));
						int count = current * idCardWi[i];
						sum += count;
					}
					char idCardLast = charArray[17];
					int idCardMod = sum % 11;
					if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase())) {
						return true;
					} else {
						System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() + "错误,正确的应该是:"
								+ idCardY[idCardMod].toUpperCase());
						return false;
					}

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("异常:" + IDNumber);
					return false;
				}
			}

		}
		return matches;
	}

	/*
	 * 校验过程： 1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
	 * 2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
	 * 3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
	 */
	/**
	 * 校验银行卡卡号
	 */
	public static boolean checkBankCard(String bankCard) {
		if (bankCard.length() < 15 || bankCard.length() > 19) {
			return false;
		}
		char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
		if (bit == 'N') {
			return false;
		}
		return bankCard.charAt(bankCard.length() - 1) == bit;
	}

	/**
	 * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
	 * 
	 * @param nonCheckCodeBankCard
	 * @return
	 */
	public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
		if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
				|| !nonCheckCodeBankCard.matches("\\d+")) {
			// 如果传的不是数据返回N
			return 'N';
		}
		char[] chs = nonCheckCodeBankCard.trim().toCharArray();
		int luhmSum = 0;
		for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
			int k = chs[i] - '0';
			if (j % 2 == 0) {
				k *= 2;
				k = k / 10 + k % 10;
			}
			luhmSum += k;
		}
		return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
	}

	@RequestMapping(value = "toLevel", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView tosetLevel() {
		ModelAndView mv = new ModelAndView("jjzd/set_user_level");
		List<LoanDictDtlDo> KHJB = loanDictService.queryDictDtlListByDictCode(DictCode.KHJB.getCode());
		mv.addObject("KHJB", KHJB);
		return mv;
	}

	/**
	 * 空单激活
	 * 
	 * @return
	 */
	@RequestMapping(value = "/setUserLevel", method = { RequestMethod.GET, RequestMethod.POST })
	public Result<?> setUserLevel() {
		String userCode = getString("userCode");
		String userLevel = getString("userLevel");
		logger.info("修改用户等级:userCode=" + userCode + ",userLevel=" + userLevel);
		if (StringUtils.isBlank(userCode)) {
			return Result.failureResult("请输入用户编码!");
		}
		if (StringUtils.isBlank(userLevel)) {
			return Result.failureResult("请选择用户级别");
		}
		try {
			Result<?> result = userService.setUserLevel(userCode.trim(), userLevel.trim());
			logger.info("修改用户等级结果:" + JSON.toJSONString(result));
			return result;
		} catch (BusinessException e) {
			logger.info("修改报错BusinessException:", e);
			return Result.failureResult(e.getMessage());
		} catch (Exception e) {
			logger.info("修改报错Exception:", e);
			return Result.failureResult("修改失败!");
		}
	}

	@RequestMapping(value = "listTotal", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView listTotal() {
		ModelAndView mv = new ModelAndView("listTotal");
		List<LoanDictDtlDo> KHJB = loanDictService.queryDictDtlListByDictCode(DictCode.KHJB.getCode());
		mv.addObject("KHJB", KHJB);
		return mv;
	}

	/**
	 * 查询用户等级
	 * 
	 * @return
	 */

	@RequestMapping(value = "/inqLevel", method = RequestMethod.GET)
	public Result<?> inqLevel() {
		Integer userId = getUserId();
		logger.info("查询用户等级，userId:" + userId); // 用户信息
		UserDo userDo = userService.getUser(userId);
		Map<String, Object> map = new HashMap<>();
		map.put("userLevel", userDo.getUserLevel()); // 用户等级
		return Result.successResult("查询用户等级成功", map);
	}

	/**
	 * 查询用户名
	 * 
	 * @return
	 */
	@RequestMapping(value = "/inqName", method = RequestMethod.GET)
	public Result<?> inqName() {
		Integer userId = getUserId();
		logger.info("查询用户用户名，userId:" + userId); // 用户信息
		UserDo userDo = userService.getUser(userId);
		Map<String, Object> map = new HashMap<>();
		map.put("userName", userDo.getUserName()); // 用户名
		return Result.successResult("查询用户名成功", map);
	}

	/**
	 * 个人中心查询个人信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/personalInfo", method = RequestMethod.GET)
	public Result<?> getUser() {
		Integer userId = getUserId();
		logger.info("查询用户基本信息，userId:" + userId);
		// 用户信息
		UserDo userDo = userService.getUser(userId);

		Map<String, Object> map = new HashMap<>();
		map.put("trueName", userDo.getTrueName()); // 姓名
		map.put("mobile", userDo.getMobile()); // 手机号码
		map.put("userLevel", userDo.getUserLevel()); // 用户等级
		map.put("idnumber", userDo.getIdnumber()); // 用户身份证号
		map.put("sex", userDo.getSex()); // 用户性别
		map.put("refereeUserMobile", userDo.getRefereeUserMobile()); // 用户推荐人
		map.put("banknumber", userDo.getBanknumber());// 银行卡卡号
		map.put("banktype", userDo.getBanktype());// 银行卡开户行
		return Result.successResult("查询成功", map);
	}

	/** 
	 * @api {POST} /user/infoUser.do 修改用户
	 * @apiName infoUser
	 * @apiGroup user 
	 * @apiVersion 1.0.0 
	 * @apiDescription 修改用户
	 * 
	 * @apiParam {String} trueName 真名
	 * @apiParam {String} mobile 手机
	 * @apiParam {String} idnumber 身份证号
	 * @apiParam {String} sex 性别
	 * @apiParam {String} banknumber 卡号
	 * @apiParam {String} banktype 开户行
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {String} msg 修改成功
	 * 
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {}
	 */ 
	@RequestMapping(value = "/infoUser", method = RequestMethod.POST)
	public Result<?> updateUser() {
		try {
			Integer userId = getUserId();
			String userPassword = getString("userPassword"); // 登录密码
			String twoPassword = getString("twoPassword"); // 支付密码
			String trueName = getString("trueName"); // 真实姓名
			String mobile = getString("mobile"); // 手机号
			String idnumber = getString("idnumber"); // 身份证
			String banknumber = getString("banknumber"); // 银行卡
			String banktype = getString("banktype"); // 银行类型

			logger.info("修改用户信息，userId:" + userId);

			// Assert.hasText(trueName, "姓名不能为空");
			// Assert.hasText(mobile, "手机号码不能为空");
			// Assert.hasText(idnumber, "身份证不能为空");
			// Assert.hasText(banktype, "银行不能为空");
			// Assert.hasText(bankUserName, "开户名不能为空");
			// Assert.hasText(banknumber, "银行卡号不能为空");

			logger.info("修改用户信息:trueName=" + trueName);
			logger.info("修改用户信息:mobile=" + mobile);
			logger.info("修改用户信息:idunmber=" + idnumber);
			logger.info("修改用户信息:banknumber=" + banknumber);
			logger.info("修改用户信息:banktype=" + banktype);
			logger.info("修改用户信息:userLevel=" + userPassword);
			logger.info("修改用户信息:userLevel=" + twoPassword);

			
			// 用户信息
			UserDo userDo = new UserDo();
			userDo.setId(userId);

			if (StringUtils.isNotBlank(trueName)) {
				trueName = trueName.replaceAll(" ", "");
				userDo.setTrueName(trueName);
			}

			if (StringUtils.isNotBlank(mobile)) {
				mobile = mobile.replaceAll(" ", "");
				userDo.setMobile(mobile);
			}

			// if(StringUtils.isNotBlank(email)){
			// email = email.replaceAll(" ","");
			// userDo.setEmail(email);
			// }

			if (StringUtils.isNotBlank(idnumber)) {
				idnumber = idnumber.replaceAll(" ", "");
				userDo.setIdnumber(idnumber);
			}

			if (StringUtils.isNotBlank(banktype)) {
				banktype = banktype.replaceAll(" ", "");
				userDo.setBanktype(banktype);
			}

			// if (StringUtils.isNotBlank(bankUserName)) {
			// bankUserName = bankUserName.replaceAll(" ", "");
			// userDo.setBankUserName(bankUserName);
			// }

			if (StringUtils.isNotBlank(banknumber)) {
				banknumber = banknumber.replaceAll(" ", "");
				userDo.setBanknumber(banknumber);
			}
			if (StringUtils.isNotBlank(userPassword)) {
				userPassword = DataEncrypt.encrypt(userPassword);
				userDo.setUserPassword(userPassword);
			}
			if (StringUtils.isNotBlank(twoPassword)) {
				twoPassword = DataEncrypt.encrypt(twoPassword);
				userDo.setTwoPassword(twoPassword);
			}
			// 认证状态
//			if (trueName != null || mobile != null || idnumber != null || banknumber != null || banktype != null) {
//				userDo.setCertification(1);
//			}

			return userService.update(userDo);
		} catch (Exception e) {
			return Result.failureResult("用户信息修改失败");
		}
	}

}
