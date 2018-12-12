package com.dce.business.service.impl.account;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dce.business.common.enums.AccountType;
import com.dce.business.common.enums.DictCode;
import com.dce.business.common.enums.IncomeType;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.Constants;
import com.dce.business.common.util.DataDecrypt;
import com.dce.business.common.util.DataEncrypt;
import com.dce.business.common.util.PayUtil;
import com.dce.business.dao.account.IUserAccountDao;
import com.dce.business.dao.account.IUserAccountDetailDao;
import com.dce.business.dao.etherenum.IEthereumTransInfoDao;
import com.dce.business.dao.trade.IWithdrawalsDao;
import com.dce.business.dao.user.IUserDao;
import com.dce.business.dao.user.IUserParentDao;
import com.dce.business.entity.account.UserAccountDetailDo;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.dict.LoanDictDtlDo;
import com.dce.business.entity.etherenum.EthereumAccountDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.travel.TravelDoExample;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.dict.ILoanDictService;
import com.dce.business.service.third.IEthereumPlatformService;
import com.dce.business.service.third.IEthereumService;
import com.dce.business.service.user.IUserService;
import com.dce.business.service.user.IUserStaticService;
import com.dce.business.service.userCard.IUserCardService;

/**
 * 用户资金账户
 * 
 * @author parudy
 * @date 2018年3月25日
 * @version v1.0
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {
	private final static Logger logger = Logger.getLogger(AccountServiceImpl.class);

	@Resource
	private IUserAccountDao userAccountDao;
	@Resource
	private IUserAccountDetailDao userAccountDetailDao;
	@Resource
	private IUserDao userDao;
	@Resource
	private IUserParentDao userParentDao;
	@Resource
	private IUserStaticService userStaticService;
	@Resource
	private IUserService userService;
	@Resource
	private ILoanDictService loanDictService;
	@Resource
	private IEthereumService ethereumService;
	@Resource
	private IEthereumTransInfoDao ethereumTransInfoDao;
	@Resource
	private IEthereumPlatformService ethereumPlatformService;

	@Resource
	private IWithdrawalsDao withdrawDao;
	@Resource
	private IUserCardService userCardService;

	@Value("#{sysconfig['huishang.openAccount.url']}")
	private String ethereum_blance_url;

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public boolean createAccount(UserAccountDo userAccountDo) {
		userAccountDo.setUpdateTime(new Date());

		int i = userAccountDao.insertSelective(userAccountDo);
		return i > 0;
	}

	@Override
	public UserAccountDo getUserAccount(Integer userId, AccountType accountType) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("accountType", accountType.getAccountType());
		List<UserAccountDo> list = userAccountDao.selectAccount(params);

		// System.err.println("用户-----》》》"+list+"---yonghu id----->>"+userId);

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		return list.get(0);
	}

	@Override
	public List<UserAccountDo> selectUserAccount(Map<String, Object> params) {
		return userAccountDao.selectAccount(params);
	}

	// 1、原始仓账户；2、美元点账户
	@Override
	public UserAccountDo selectUserAccount(Integer userId, String accountType) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("accountType", accountType);

		List<UserAccountDo> list = userAccountDao.selectAccount(params);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		return list.get(0);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateUserAmountById(UserAccountDo userAccountDo, IncomeType incomeType) {
		BigDecimal amount = userAccountDo.getAmount();

		// 金额为0时不需要对账户变更操作，不需要记流水
		if (BigDecimal.ZERO.compareTo(amount) == 0) {
			return 0;
		}

		// 增加用户收益、消费金额
		if (amount.compareTo(BigDecimal.ZERO) >= 0) {
			userAccountDo.setConsumeAmount(BigDecimal.ZERO);
			userAccountDo.setIncomeAmount(amount);
		} else {
			userAccountDo.setIncomeAmount(BigDecimal.ZERO);
			userAccountDo.setConsumeAmount(amount.negate());
		}

		Integer userId = userAccountDo.getUserId();
		if (userId == null) {
			throw new BusinessException("更新账户余额用户Id未空");
		}
		
		String accountType = userAccountDo.getAccountType();
		// 判断用户是否存在此帐户 没有刚增加
		UserAccountDo udo = selectUserAccount(userId, accountType);
		userAccountDo.setUpdateTime(new Date());
		int result = 0;
		if (null == udo && amount.compareTo(BigDecimal.ZERO) >= 0) {
			result = userAccountDao.insertSelective(userAccountDo);
		} else {
			result = userAccountDao.updateUserAmountById(userAccountDo);
		}

		if (result < 1) {
			throw new BusinessException("余额不足");
		}

		updateUserAmountDetail(amount, incomeType, 
							  userId, accountType, 
							  userAccountDo.getRemark(),
							  userAccountDo.getSeqId(),
							  userAccountDo.getRelevantUser());
		
		//增加旅游卡
		if(AccountType.wallet_active.getAccountType().equalsIgnoreCase(userAccountDo.getAccountType())&& userAccountDo.getAmount().intValue()> 0) {
			userCardService.batchAddUserCard(userId,userAccountDo.getAmount().intValue());
		}

		return result;
	}

	/**
	 * 更新用户明细贪信息
	 * 
	 * @param amount
	 * @param useType
	 * @param userId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	private boolean updateUserAmountDetail(BigDecimal amount, IncomeType incomeType, Integer userId, String accountType,
			String remark, String seqId, String relevantUserid) {
		// 读取账户余额
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("accountType", accountType);
		List<UserAccountDo> accounts = userAccountDao.selectAccount(params);
		BigDecimal balance = accounts.get(0).getAmount();

		// 增加用户消费列表
		UserAccountDetailDo uaDetail = new UserAccountDetailDo();
		uaDetail.setAmount(amount);   //本次操作金额
		uaDetail.setCreateTime(new Date());
		uaDetail.setIncomeType(incomeType.getIncomeType());
		uaDetail.setMoreOrLess(moreOrLessStr(amount)); //资金变动方向
		uaDetail.setAccountType(accountType);
		uaDetail.setBalanceAmount(balance); //余额
		uaDetail.setSeqId(seqId);
		uaDetail.setRelevantUser(relevantUserid);
		uaDetail.setRemark(remark+":"+incomeType.getRemark());
		
		uaDetail.setUserId(userId);
		userAccountDetailDao.addUserAccountDetail(uaDetail);
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void convertBetweenAccount(Integer sourceUserId, Integer targetUserId, BigDecimal amount, String fromAccount,
			String toAccount, IncomeType sourceMsg, IncomeType targetMsg) {
		convertBetweenAccount(sourceUserId, targetUserId, amount, amount, fromAccount, toAccount, sourceMsg, targetMsg);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void convertBetweenAccount(Integer sourceUserId, Integer targetUserId, BigDecimal sourceAmount,
			BigDecimal targetAmount, String fromAccount, String toAccount, IncomeType sourceMsg, IncomeType targetMsg) {
		String seqId = UUID.randomUUID().toString();
		UserDo targetUserDo = userDao.selectByPrimaryKey(targetUserId);
		UserDo sourceUserDo = userDao.selectByPrimaryKey(sourceUserId);

		userAccountDetailDao.addAccountTranLog(seqId, sourceUserId, targetUserId, sourceAmount, fromAccount, toAccount,
				sourceMsg);
		UserAccountDo source = new UserAccountDo();
		source.setUserId(sourceUserId);
		source.setAmount(sourceAmount.negate());
		source.setAccountType(fromAccount);
		source.setSeqId(seqId);
		source.setRelevantUser(targetUserDo.getUserName());
		source.setRemark("用户:"+sourceUserDo.getMobile());
		this.updateUserAmountById(source, sourceMsg);

		UserAccountDo target = new UserAccountDo();
		target.setUserId(targetUserId);
		target.setAmount(targetAmount);
		target.setAccountType(toAccount);
		target.setSeqId(seqId);
		target.setRelevantUser(sourceUserDo.getUserName());
		target.setRemark("用户:"+sourceUserDo.getMobile());
		this.updateUserAmountById(target, targetMsg);
	}

	private String moreOrLessStr(BigDecimal amount) {
		String type = "+";
		if (BigDecimal.ZERO.compareTo(amount) > 0) {
			type = "-";
		}

		return type;

	}

	@Override
	public List<UserAccountDetailDo> selectUserAccountDetail(Map<String, Object> params) {
		return userAccountDetailDao.selectUserAccountDetail(params);
	}

	@Override
	public Result<?> currentInit(Integer userId, AccountType type) {
		if (userId == null) {
			return Result.failureResult("用户ID为空!");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();

		UserDo user = userDao.selectByPrimaryKey(userId);
		resultMap.put("userName", user.getUserName());
		resultMap.put("userLevelName",
				StringUtils.isBlank(user.getUserLevelName()) ? user.getUserLevel() : user.getUserLevelName());
		UserAccountDo account = selectUserAccount(userId, type.getAccountType());
		if (account != null) {
			resultMap.put("amount", account.getAmount());
		} else {

			resultMap.put("amount", "0.0");
		}
		// 查询报单等级及等级额度范围
		List<LoanDictDtlDo> dtlList = loanDictService.queryDictDtlListByDictCode(DictCode.BaoDanFei.getCode());

		resultMap.put("userLeves", dtlList);

		return Result.successResult("查询成功!", resultMap);
	}

	@Override
	public String getMyQRCode(Integer userId) {
		String qrcode = userAccountDao.getMyQRCode(userId);
		if (StringUtils.isBlank(qrcode)) {
			qrcode = PayUtil.getPayCode(UUID.randomUUID().toString());
			userAccountDao.insertQRCode(qrcode, userId);
		}
		return qrcode;
	}

	@Override
	public Result<?> payByQRCode(Integer userId, String qrCode, String amount, String pwd) {

		Result<?> result = Result.successResult("扫描码支付成功");
		UserDo user = userDao.selectByPrimaryKey(userId);
		// 判断用户是否允许扫描支付
		if (user.getUndoOpts() != null && user.getUndoOpts().contains("601")) {
			result.setCode(Result.failureCode);
			result.setMsg("扫描支付功能受限");
			return result;
		}

		// 校验密码是否正确
		pwd = DataEncrypt.encrypt(pwd);
		// if(StringUtils.isBlank(pwd) ||
		// !MD5Encrypt.getMessageDigest(pwd).equals(user.getTwoPassword())){
		if (StringUtils.isBlank(pwd) || !pwd.equals(user.getTwoPassword())) {
			result.setCode(Result.failureCode);
			result.setMsg("密码错误");
			return result;
		}

		BigDecimal amt = null;
		try {
			// 校验金额
			amt = new BigDecimal(amount);
			if (amt.compareTo(BigDecimal.ZERO) <= 0) {
				result.setCode(Result.failureCode);
				result.setMsg("金额错误");
				return result;
			}
		} catch (Exception e) {
			result.setCode(Result.failureCode);
			result.setMsg("金额错误");
			return result;
		}

		Integer targetUserId = userAccountDao.getUserIdByQRCode(qrCode);

		// //人民币转DCE币
		// BigDecimal dceAmt = loanDictService.rmb2Dce(amt);
		//
		// //查询美元点比DCE币的比例 N:1
		// LoanDictDtlDo MYDBXCC =
		// loanDictService.getLoanDictDtl(DictCode.Point2RMB.getCode());
		// BigDecimal decp = new BigDecimal(MYDBXCC.getRemark());
		// //计算需要的美元点
		// BigDecimal targetAmt = dceAmt.multiply(decp);
		// targetAmt = targetAmt.divide(new BigDecimal(1), 6,
		// RoundingMode.HALF_UP);

		convertBetweenAccount(userId, targetUserId, amt, amt, AccountType.wallet_money.name(),
				AccountType.wallet_money.name(), IncomeType.TYPE_PAY_QRCODE, IncomeType.TYPE_PAY_QRCODE);

		return result;
	}

	/**
	 * 发送给其他账号
	 * 
	 * @param userId
	 * @param qrCode
	 * @param amount
	 * @param pwd
	 * @return
	 */
	@Async
	@Override
	public Result<?> send(Integer userId, String receiveAddress, String amount, String pwd) {

		Result<?> result = Result.successResult("发送成功");
		UserDo user = userDao.selectByPrimaryKey(userId);

		// 校验密码是否正确
		pwd = DataEncrypt.encrypt(pwd);
		if (StringUtils.isBlank(pwd) || !pwd.equals(user.getTwoPassword())) {
			result.setCode(Result.failureCode);
			result.setMsg("密码错误");
			return result;
		}

		BigDecimal amt = null;
		try {
			// 校验金额
			amt = new BigDecimal(amount);
			if (amt.compareTo(BigDecimal.ZERO) <= 0) {
				result.setCode(Result.failureCode);
				result.setMsg("金额错误");
				return result;
			}

			if (amt.compareTo(new BigDecimal("0.1")) <= 0) {
				result.setCode(Result.failureCode);
				result.setMsg("发送金额不能小于0.1以太坊");
				return result;
			}

		} catch (Exception e) {
			result.setCode(Result.failureCode);
			result.setMsg("金额错误");
			return result;
		}

		String toAccount = receiveAddress; // 转入账号
		EthereumAccountDo ethAccount = ethereumService.getByUserId(userId);
		String password = ethAccount.getPassword(); // 交易密码
		String fromAccount = ethAccount.getAccount();// 转出账号

		result = ethereumService.trans(userId, fromAccount, toAccount, DataDecrypt.decrypt(password), amt,
				amount.toString(), 3, BigDecimal.ZERO);

		return result;
	}

	/**
	 * 根据收款码获取收款人信息
	 */
	@Override
	public UserDo getReceiverQRCode(String qrCode) {
		Integer targetUserId = userAccountDao.getUserIdByQRCode(qrCode);
		return userDao.selectByPrimaryKey(targetUserId);
	}

	@Override
	public PageDo<Map<String, Object>> selectAccountInfomByPage(PageDo<Map<String, Object>> page,
			Map<String, Object> params) {

		if (params == null) {
			params = new HashMap<String, Object>();
		}
		params.put(Constants.MYBATIS_PAGE, page);

		List<Map<String, Object>> list = userAccountDao.selectAccountInfomByPage(params);
		page.setModelList(list);
		return page;
	}

	@Override
	public List<UserAccountDo> sumAccount() {

		return userAccountDao.sumAccount(Collections.<String, Object> emptyMap());
	}

	@Override
	public PageDo<UserAccountDetailDo> selectUserAccountDetailByPage(PageDo<UserAccountDetailDo> page,
			Map<String, Object> params) {

		if (params == null) {
			params = new HashMap<String, Object>();
		}
		params.put(Constants.MYBATIS_PAGE, page);
		List<UserAccountDetailDo> list = userAccountDetailDao.selectUserAccountDetailByPage(params);
		page.setModelList(list);
		return page;
	}

	@Override
	public List<UserAccountDetailDo> selectUserAccountDetailByUserId(Map map) {

		return userAccountDetailDao.selectUserAccountDetailByUserId(map);
	}

	@Override
	public PageDo<Map<String, Object>> selectAccountInfoByPage(PageDo<Map<String, Object>> page,
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserAccountDo selectAmountByAccountType(Map<String, Object> parameterMap) {

		return userAccountDao.selectAmountByAccountType(parameterMap);
	}

	/**
	 * 获取当前用户的账户余额
	 * 
	 * @return
	 */
	public BigDecimal getUserAmount(Integer userId) {

		String accountType = AccountType.wallet_money.getAccountType();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("accountType", accountType);
		logger.info("账户查询的参数---------》》》》》" + param);

		UserAccountDo userAccount = selectAmountByAccountType(param);
		logger.info("获取的最新的用户账户记录--------》》》》》" + userAccount);
		if(userAccount != null){
			return userAccount.getAmount();
		}
		return new BigDecimal("0");
	}

	/**
	 * 流水记录
	 * 
	 * @param userId
	 *            用户id
	 * @param totalPrice
	 *            交易金额
	 * @param moreOrless
	 *            增加/减少
	 * @param incomeType
	 *            操作对应的数字，例如“提现”对应数字22
	 * @return
	 */
	@Override
	public int addUserAccountDetail(Integer userId, BigDecimal totalPrice, String moreOrLess, Integer incomeType) {

		UserAccountDetailDo userAccountDetail = new UserAccountDetailDo();
		userAccountDetail.setUserId(userId); // 用户ID
		userAccountDetail.setAmount(totalPrice); // 交易金额
		userAccountDetail.setCreateTime(new Date()); // 交易时间
		userAccountDetail.setMoreOrLess(moreOrLess); // 增加/减少
		userAccountDetail.setIncomeType(incomeType);
		userAccountDetail.setRemark(IncomeType.getByType(incomeType).getRemark());
		String seqId = UUID.randomUUID().toString();
		userAccountDetail.setSeqId(seqId); // 流水序列号
		userAccountDetail.setBalanceAmount(getUserAmount(userId)); // 获取账户的总金额

		return userAccountDetailDao.addUserAccountDetail(userAccountDetail);
	}

	// 1、账户余额
	@Override
	public UserAccountDo selectUserAccount2(Integer userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);

		List<UserAccountDo> list = userAccountDao.selectAccount

		(params);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		return list.get(0);
	}

	// 余额修改，提现申请
	@Override
	public int updateMoney(Map<String, Object> param) {
		// TODO Auto-generated method stub
		if (param == null) {
			return 0;
		}
		return userAccountDao.updateMoney(param);
	}

	@Override
	public List<Map<String,Object>> exportQuery(Map map) {
		// TODO Auto-generated method stub
		
		return userAccountDao.exportQuery(map);
	}

	@Override
	public List<UserAccountDo> sumAccount(Map emptyMap) {
		return userAccountDao.sumAccount(emptyMap);
	}
}
