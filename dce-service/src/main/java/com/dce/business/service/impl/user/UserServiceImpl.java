package com.dce.business.service.impl.user;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dce.business.common.enums.AccountType;
import com.dce.business.common.enums.DictCode;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.Constants;
import com.dce.business.common.util.DataEncrypt;
import com.dce.business.dao.account.IUserAccountDao;
import com.dce.business.dao.touch.ITouchBonusRecordDao;
import com.dce.business.dao.user.IUserDao;
import com.dce.business.dao.user.IUserParentDao;
import com.dce.business.dao.user.IUserRefereeDao;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.dict.LoanDictDtlDo;
import com.dce.business.entity.district.District;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.entity.user.UserParentDo;
import com.dce.business.entity.user.UserRefereeDo;
import com.dce.business.service.bonus.IPerformanceDailyService;
import com.dce.business.service.dict.ILoanDictService;
import com.dce.business.service.district.IDistrictService;
import com.dce.business.service.third.IEthereumService;
import com.dce.business.service.user.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Resource
	private IUserDao userDao;
	@Resource
	private IUserParentDao userParentDao;
	@Resource
	private IUserRefereeDao userRefereeDao;
	@Resource
	private IUserAccountDao userAccountDao;
	@Resource
	private ILoanDictService loanDictService;
	@Resource
	private IEthereumService ethereumService;
	@Resource
	private ITouchBonusRecordDao touchBonusRecordDao;
	@Resource
	private IPerformanceDailyService performanceDailyService;
	@Resource
	private IDistrictService districtService;

	/**
	 * 查询用户名是否存在
	 */
	@Override
	public UserDo userName(String userName) {
		Map<String, Object> params = new HashMap<>();
		params.put("userName", userName);
		List<UserDo> list = userDao.selectUser(params);

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		return list.get(0);
	}

	/**
	 * 用户注册
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Result<?> reg(UserDo userDo) {
		// 推荐用户
		UserDo ref = null;
		if (StringUtils.isNotBlank(userDo.getRefereeUserMobile())) {

			Map<String, Object> params = new HashMap<String, Object>();

			params.put("mobile", userDo.getRefereeUserMobile());

			List<UserDo> refUserLst = this.selectMobile(params);
			if (refUserLst == null || refUserLst.size() < 1) {
				return Result.failureResult("推荐人不存在");
			}
			ref = refUserLst.get(0);
		}

		// 判断注册用户名是否为空
		userDo.setUserName(userDo.getUserName().trim());
		UserDo oldUser = userName(userDo.getUserName());
		if (oldUser != null) {
			return Result.failureResult("用户已存在");
		}

		if (ref != null) {
			userDo.setRefereeid(ref.getId());// 获取用户推荐人id
			userDo.setParentid(ref.getId());// 获取上级id
		}
		// 密码加密处理
		userDo.setRegTime(new Date().getTime());
		userDo.setUserPassword(DataEncrypt.encrypt(userDo.getUserPassword())); // 登录密码
		userDo.setTwoPassword(DataEncrypt.encrypt(userDo.getTwoPassword())); // 支付密码

		// 用户注册
		int result = userDao.insertSelective(userDo);

		if (ref != null) {
			// 维护父节点关系
			maintainUserParent(userDo.getId(), ref.getId(), userDo.getPos());
			// 维护推荐人关系
			maintainUserReferee(userDo.getId(), ref.getId());
		}

		// 维护賬戶
		maintainUserAccount(userDo.getId());

		return result > 0 ? Result.successResult("注册成功!",userDo.getId()) : Result.failureResult("注册失败");
	}

	// 维护賬戶
	private void maintainUserAccount(Integer userId) {
		String[] accountTypes = new String[] { AccountType.wallet_money.name(), AccountType.wallet_active.name(),
				AccountType.wallet_goods.name(), AccountType.wallet_travel.name() };
		// 新增账号
		for (String accountType : accountTypes) {
			UserAccountDo record = new UserAccountDo();
			record.setAccountType(accountType);
			record.setAmount(BigDecimal.ZERO);
			record.setUserId(userId);
			record.setUpdateTime(new Date());
			userAccountDao.insertSelective(record);
		}
	}

	/**
	 * 查询用户所有信息
	 */
	@Override
	public UserDo getUser(Integer userId) {
		return userDao.selectByPrimaryKey(userId);
	}

	/**
	 * 维护父节点关系表
	 * 
	 * @param userId
	 * @param parentId
	 */
	private void maintainUserParent(Integer userId, Integer parentId, Byte lr) {
		// 1、接点人的下级人数加一
		userDao.addSonNumber(parentId);

		// 2、直接父级
		UserParentDo userParentDo = new UserParentDo();
		userParentDo.setParentid(parentId);
		userParentDo.setUserid(userId);
		userParentDo.setDistance(1);
		// userParentDo.setPosition(lr.toString());
		userParentDo.setNetwork(null);
		userParentDo.setLrDistrict(lr);
		userParentDao.insertSelective(userParentDo);

		// 3、间接父级
		Map<String, Object> params = new HashMap<>();
		params.put("userid", parentId);
		List<UserParentDo> list = userParentDao.select(params);
		for (UserParentDo temp : list) {
			UserParentDo up = new UserParentDo();
			up.setUserid(userId);
			up.setParentid(temp.getParentid());
			up.setDistance(temp.getDistance() + 1);
			// up.setPosition(getPosition(temp.getPosition(), lr));
			up.setNetwork(null);
			up.setLrDistrict(temp.getLrDistrict());
			userParentDao.insertSelective(up);
			userDao.addSonNumber(temp.getParentid()); // 接点人的下级人数加一
		}
	}

	/**
	 * 查询所属位置 position=(parentPosition-1)*2 + lr
	 * 
	 * @param parentPosition
	 * @param lr
	 * @return
	 */
	private String getPosition(String parentPosition, Byte lr) {
		try {
			Integer position = (Integer.valueOf(parentPosition) - 1) * 2 + lr;
			return position.toString();
		} catch (Exception e) {
			logger.error("position计算异常：", e);
		}

		return "0";
	}

	/**
	 * 维护推荐人关系表
	 * 
	 * @param userId
	 * @param parentId
	 */
	private void maintainUserReferee(Integer userId, Integer refereeId) {

		// 2、直接推荐人
		UserRefereeDo userRefereeDo = new UserRefereeDo();
		userRefereeDo.setUserid(userId);
		userRefereeDo.setRefereeid(refereeId);
		userRefereeDo.setDistance(1); // 直接推荐人
		userRefereeDao.insertSelective(userRefereeDo);

		// 3、间接推荐人
		Map<String, Object> params = new HashMap<>();// userid
		params.put("userid", refereeId);
		List<UserRefereeDo> list = userRefereeDao.select(params);
		for (UserRefereeDo temp : list) {
			UserRefereeDo ur = new UserRefereeDo();
			ur.setUserid(userId);
			ur.setRefereeid(temp.getRefereeid()); // 间接推荐人
			ur.setDistance(temp.getDistance() + 1);
			userRefereeDao.insertSelective(ur);
		}
	}

	@Override
	public List<UserDo> list(String userName) {
		Map<String, Object> params = new HashMap<>();
		params.put("userName", userName);
		return userDao.list(params);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void updateStatic(BigDecimal staticAmount, int userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("staticAmount", staticAmount);
		userDao.updateStatic(params);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void updateAllPerformance(BigDecimal staticAmount, Integer userId) {
		// 查询所有的父节点
		Map<String, Object> params = new HashMap<>();
		params.put("userid", userId);
		List<UserParentDo> list = userParentDao.select(params);

		// 更新自己的
		updatePerformance(staticAmount, userId);

		// 更新父节点
		for (UserParentDo parent : list) {
			updatePerformance(staticAmount, parent.getParentid());
		}
	}

	private void updatePerformance(BigDecimal performance, int userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("performance", performance);
		userDao.updatePerformance(params);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void updateTouched(BigDecimal touchedAmount, int userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("touchedAmount", touchedAmount);
		userDao.updateTouched(params);
	}

	@Override
	public List<UserDo> selectUser(Map<String, Object> params) {
		return userDao.selectUser(params);
	}

	/**
	 * 支付密码
	 */
	@Override
	public Result<?> updateByPrimaryKeyPayPass(UserDo userDo) {
		if (userDo == null || userDo.getId() == null) {
			return Result.failureResult("修改支付密码参数错误!");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userDo.getId());
		params.put("twoPassword", userDo.getTwoPassword());
		int flag = userDao.updateByPrimaryKeyPayPass(params);
		if (flag > 0) {
			return Result.successResult("修改支付密码成功");
		} else {

			return Result.failureResult("修改支付密码失败");
		}
	}

	/**
	 * 登录密码
	 */
	@Override
	public Result<?> updateByPrimaryKeyLogPass(UserDo userDo) {
		System.out.println("xiugaidenglumima" + userDo.getUserPassword());
		if (userDo == null || userDo.getId() == null) {
			return Result.failureResult("修改登录密码参数错误!");
		}
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userDo.getId());
		params.put("userPassword", userDo.getUserPassword());
		int flag = userDao.updateByPrimaryKeyLogPass(params);
		if (flag > 0) {
			return Result.successResult("修改登录密码成功");
		} else {

			return Result.failureResult("修改登录密码失败");
		}
	}

	/**
	 * 此方法注释，重写， 2018-07-08
	 */
	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<Map<String, Object>> listMyOrg(Integer userId,int
	 * level) { List<Map<String, Object>> ret =
	 * userParentDao.listMyOrg(userId,level); if(CollectionUtils.isEmpty(ret)){
	 * return Collections.EMPTY_LIST; }
	 * 
	 * BigDecimal leftPerformance = BigDecimal.ZERO; BigDecimal rightPerformance
	 * = BigDecimal.ZERO;
	 * 
	 * //填充左右业绩 for(Map<String,Object> m : ret){ Long recordUserId =
	 * (Long)m.get("id");
	 * 
	 * calPerformance(recordUserId, m);
	 * 
	 * //统计root节点左右业绩 if(Integer.parseInt(m.get("id").toString()) !=
	 * userId.intValue() && "2".equals(m.get("lr_district").toString())){ //右区
	 * 
	 * rightPerformance = rightPerformance.add((BigDecimal) m.get("lr_amount"));
	 * rightPerformance = rightPerformance.add((BigDecimal) m.get("lf_amount"));
	 * rightPerformance = rightPerformance.add((BigDecimal)
	 * m.get("baodan_amount")).setScale(2, RoundingMode.HALF_DOWN); }
	 * if(Integer.parseInt(m.get("id").toString()) != userId.intValue() &&
	 * "1".equals(m.get("lr_district").toString())){ //左区
	 * 
	 * leftPerformance = leftPerformance.add((BigDecimal) m.get("lr_amount"));
	 * leftPerformance = leftPerformance.add((BigDecimal) m.get("lf_amount"));
	 * leftPerformance = leftPerformance.add((BigDecimal)
	 * m.get("baodan_amount")).setScale(2, RoundingMode.HALF_DOWN); } }
	 * 
	 * 
	 * for(Map<String,Object> m : ret){ Long recordUserId = (Long)m.get("id");
	 * 
	 * if(recordUserId.intValue() == userId.intValue()){ m.put("lf_amount",
	 * leftPerformance); m.put("lr_amount", rightPerformance); break; } } return
	 * ret; }
	 * 
	 * 
	 * private void calPerformance(Long userId, Map<String, Object> m) {
	 * BigDecimal todayPerformance = BigDecimal.ZERO; BigDecimal
	 * totalPerformance = BigDecimal.ZERO; BigDecimal leftPerformance =
	 * BigDecimal.ZERO; BigDecimal rightPerformance = BigDecimal.ZERO;
	 * BigDecimal todayLeftPerformance = BigDecimal.ZERO; BigDecimal
	 * todayRightPerformance = BigDecimal.ZERO; try { //总业绩 TouchBonusRecordDo
	 * touchBonusRecordDo =
	 * touchBonusRecordDao.getUserTouchBonusRecord(userId.intValue()); if
	 * (touchBonusRecordDo != null) { if (touchBonusRecordDo.getBalanceLeft() !=
	 * null) { totalPerformance =
	 * totalPerformance.add(touchBonusRecordDo.getBalanceLeft());
	 * leftPerformance =
	 * leftPerformance.add(touchBonusRecordDo.getBalanceLeft()); } if
	 * (touchBonusRecordDo.getBalanceRight() != null) { totalPerformance =
	 * totalPerformance.add(touchBonusRecordDo.getBalanceRight());
	 * rightPerformance =
	 * rightPerformance.add(touchBonusRecordDo.getBalanceRight()); } }
	 * 
	 * //当日业绩 PerformanceDailyDo performanceDailyDo =
	 * performanceDailyService.getPerformanceDaily(userId.intValue(), new
	 * Date()); if (performanceDailyDo != null) { if
	 * (performanceDailyDo.getBalance() != null) { todayPerformance =
	 * todayPerformance.add(performanceDailyDo.getBalance()); } if
	 * (performanceDailyDo.getBalance_left() != null) { todayLeftPerformance =
	 * todayLeftPerformance.add(performanceDailyDo.getBalance_left()); } if
	 * (performanceDailyDo.getBalance_right() != null) { todayRightPerformance =
	 * todayRightPerformance.add(performanceDailyDo.getBalance_right()); } } }
	 * catch (Exception e) { logger.error("统计总业绩异常：", e); }
	 * m.put("today_performance", todayPerformance); m.put("total_performance",
	 * totalPerformance); m.put("lf_amount", leftPerformance);
	 * m.put("lr_amount", rightPerformance); m.put("today_lf_amount",
	 * todayLeftPerformance); m.put("today_lr_amount", todayRightPerformance); }
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> listMyOrg(Integer userId, int level) {
		List<Map<String, Object>> ret = userParentDao.listMyOrg(userId, level);
		if (CollectionUtils.isEmpty(ret)) {
			return Collections.EMPTY_LIST;
		}

		// BigDecimal leftPerformance = BigDecimal.ZERO;
		// BigDecimal rightPerformance = BigDecimal.ZERO;

		// 填充左右业绩
		for (Map<String, Object> m : ret) {
			Long recordUserId = (Long) m.get("id");
			getyj(m, 1, recordUserId); // 统计左区总业绩
			getyj(m, 2, recordUserId); // 统计右区总业绩

			getTodayyj(m, 1, recordUserId); // 统计左区总业绩
			getTodayyj(m, 2, recordUserId); // 统计右区总业绩

		}
		return ret;
	}

	private void getyj(Map<String, Object> m, int lr, Long recordUserId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentid", recordUserId);
		params.put("lr_district", lr);
		// 左右业绩
		BigDecimal yj = BigDecimal.ZERO;
		Map<String, Object> yjMap = userParentDao.getYJ(params);
		if (null != yjMap && !yjMap.isEmpty()) {
			yj = (BigDecimal) yjMap.get("yj");
		}

		if (yj == null) {
			yj = BigDecimal.ZERO;
		}

		yj = yj.setScale(2, RoundingMode.HALF_UP);
		if (1 == lr) {
			m.put("lf_amount", yj);
		} else {
			m.put("lr_amount", yj);
		}

		BigDecimal totalPerformance = BigDecimal.ZERO;
		if (m.get("total_performance") != null) {
			totalPerformance = (BigDecimal) m.get("total_performance");
		}

		totalPerformance = totalPerformance.add(yj).setScale(2, RoundingMode.HALF_UP);
		m.put("total_performance", totalPerformance); // 总业绩
	}

	/**
	 * 计算今日业绩
	 * 
	 * @param m
	 * @param lr
	 * @param recordUserId
	 */
	private void getTodayyj(Map<String, Object> m, int lr, Long recordUserId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentid", recordUserId);
		params.put("lr_district", lr);
		// 左右业绩
		BigDecimal yj = BigDecimal.ZERO;
		Map<String, Object> yjMap = userParentDao.getTodayYJ(params);
		if (null != yjMap && !yjMap.isEmpty()) {
			yj = (BigDecimal) yjMap.get("yj");
		}

		yj = yj.setScale(2, RoundingMode.HALF_UP);
		if (1 == lr) {
			m.put("today_lf_amount", yj);
		} else {
			m.put("today_lr_amount", yj);
		}

		BigDecimal totalPerformance = BigDecimal.ZERO;
		if (m.get("today_performance") != null) {
			totalPerformance = (BigDecimal) m.get("today_performance");
		}

		totalPerformance = totalPerformance.add(yj).setScale(2, RoundingMode.HALF_UP);
		m.put("today_performance", totalPerformance); // 总业绩
	}

	// private void calPerformance(Long userId, Map<String, Object> m) {
	// BigDecimal todayPerformance = BigDecimal.ZERO;
	// BigDecimal totalPerformance = BigDecimal.ZERO;
	// BigDecimal leftPerformance = BigDecimal.ZERO;
	// BigDecimal rightPerformance = BigDecimal.ZERO;
	// BigDecimal todayLeftPerformance = BigDecimal.ZERO;
	// BigDecimal todayRightPerformance = BigDecimal.ZERO;
	// try {
	// //总业绩
	// TouchBonusRecordDo touchBonusRecordDo =
	// touchBonusRecordDao.getUserTouchBonusRecord(userId.intValue());
	// if (touchBonusRecordDo != null) {
	// if (touchBonusRecordDo.getBalanceLeft() != null) {
	// totalPerformance =
	// totalPerformance.add(touchBonusRecordDo.getBalanceLeft());
	// leftPerformance =
	// leftPerformance.add(touchBonusRecordDo.getBalanceLeft());
	// }
	// if (touchBonusRecordDo.getBalanceRight() != null) {
	// totalPerformance =
	// totalPerformance.add(touchBonusRecordDo.getBalanceRight());
	// rightPerformance =
	// rightPerformance.add(touchBonusRecordDo.getBalanceRight());
	// }
	// }
	//
	// //当日业绩
	// PerformanceDailyDo performanceDailyDo =
	// performanceDailyService.getPerformanceDaily(userId.intValue(), new
	// Date());
	// if (performanceDailyDo != null) {
	// if (performanceDailyDo.getBalance() != null) {
	// todayPerformance = todayPerformance.add(performanceDailyDo.getBalance());
	// }
	// if (performanceDailyDo.getBalance_left() != null) {
	// todayLeftPerformance =
	// todayLeftPerformance.add(performanceDailyDo.getBalance_left());
	// }
	// if (performanceDailyDo.getBalance_right() != null) {
	// todayRightPerformance =
	// todayRightPerformance.add(performanceDailyDo.getBalance_right());
	// }
	// }
	// } catch (Exception e) {
	// logger.error("统计总业绩异常：", e);
	// }
	// m.put("today_performance", todayPerformance);
	// m.put("total_performance", totalPerformance);
	// m.put("lf_amount", leftPerformance);
	// m.put("lr_amount", rightPerformance);
	// m.put("today_lf_amount", todayLeftPerformance);
	// m.put("today_lr_amount", todayRightPerformance);
	// }

	@Override
	public List<Map<String, Object>> listMyRef(Integer userId, int startRow, int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("refereeId", userId);
		params.put("startRow", startRow);
		params.put("pageSize", pageSize);
		params.put("distance", 1);
		return userRefereeDao.listMyRef(params);
	}

	@Override
	public List<UserDo> selectPage(Map<String, Object> params) {
		return userDao.selectFenYe(params);
	}

	@Override
	public Result<?> setUserLevel(String userCode, String userLevel) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userCode);
		List<UserDo> users = userDao.selectUser(params);
		if (CollectionUtils.isEmpty(users)) {
			throw new BusinessException("用户" + userCode + "不存在");
		}
		if (users.size() > 1) {
			throw new BusinessException("用户名" + userCode + "存在多个");
		}

		List<LoanDictDtlDo> listDtl = loanDictService.queryDictDtlListByDictCode(DictCode.KHJB.getCode());
		if (CollectionUtils.isEmpty(listDtl)) {
			throw new BusinessException("用户级别未设置!");
		}
		// 判断级别是否在库中存在
		boolean haveIn = false;
		for (LoanDictDtlDo dtl : listDtl) {
			if (userLevel.equals(dtl.getCode())) {
				haveIn = true;
				break;
			}
		}
		if (!haveIn) {
			throw new BusinessException("用户级别不存在!");
		}

		UserDo user = users.get(0);
		// 修改用户的等级
		UserDo _user = new UserDo();
		_user.setId(user.getId());
		_user.setUserLevel(Byte.parseByte(userLevel));
		_user.setActivationTime(new Date().getTime());
		_user.setIsEmpty(new Byte("1")); // 1 空单 ； 0 实单
		userDao.updateByPrimaryKeySelective(_user);
		// 修改推荐人的推荐数
		// userDao.addRefereeNumber(user.getRefereeid());
		return Result.successResult("修改成功!");
	}

	@Override
	public PageDo<UserDo> selectEthAccountByPage(PageDo<UserDo> page, Map<String, Object> params) {
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		params.put(Constants.MYBATIS_PAGE, page);
		List<UserDo> list = userDao.selectEthAccountByPage(params);
		page.setModelList(list);
		return page;
	}

	@Override
	public Long selectBaoDanAmount(Map<String, Object> params) {
		return userDao.selectBaoDanAmount(params);
	}

	/**
	 * 实名认证
	 */
	@Override
	public Result<?> Authentication(UserDo userDo) {
		// TODO Auto-generated method stub

		if (userDo == null || userDo.getId() == null) {

			return Result.failureResult("认证用户信息参数错误!");
		}

		UserDo user = userDao.selectByPrimaryKey(userDo.getId());

		if (user == null || user.equals("")) {

			return Result.failureResult("该用户不存在!");

		}

		// 用户状态验证
		/**
		 * 2018-11-08 暂时放开
		 */
//		if (user.getCertification() == 1) {
//
//			return Result.failureResult("该用户已认证");
//		}

		// 手机号验证
		if (userDo.getMobile() != null) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", userDo.getMobile());

			List<UserDo> user1 = userDao.selectUserCondition(map);

			if (!user1.isEmpty()) {

				if (user1.get(0).getId() != userDo.getId()) {

					return Result.failureResult("该手机号已存在");
				}
			}
		}

		// 身份证号验证
//		if (userDo.getIdnumber() != null) {
//
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("idnumber", userDo.getIdnumber());
//
//			List<UserDo> user1 = userDao.selectUserCondition(map);
//
//			if (!user1.isEmpty()) {
//
//				System.err.println("用户id******" + user1.get(0).getId());
//				if (user1.get(0).getId() != userDo.getId()) {
//
//					return Result.failureResult("该身份证号已存在");
//
//				}
//			}
//
//		}

		int flag = userDao.updateByPrimaryKeySelective(userDo);
		if (flag > 0) {
			return Result.successResult("认证成功");
		} else {

			return Result.failureResult("认证失败");
		}
	}

	/**
	 * 查看团员
	 * 
	 * @param params
	 * @return
	 */
	@Override
	public List<UserParentDo> getMyMember(Map<String, Object> params) {
		// TODO Auto-generated method stub

		return userParentDao.select(params);
	}

	public static void main(String[] args) {
		DataEncrypt data = new DataEncrypt();
		System.out.println(data.encrypt("123456"));

	}

	@Override
	public List<UserDo> selectUserCondition(Map<String, Object> map) {

		return userDao.selectUserCondition(map);
	}

	/**
	 * 查询所有用户的手机号
	 */
	@Override
	public List<UserDo> selectMobile(Map<String, Object> params) {
		return userDao.selectMobile(params);
	}

	/**
	 * 新增会员（需维护账户关系与推荐人关系）
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Result<?> addUserInfo(UserDo userDo) {
		UserDo ref = null;

		// 判断新增用户名是否为空
		userDo.setUserName(userDo.getUserName().trim());
		UserDo oldUser = userName(DataEncrypt.encrypt(userDo.getUserName()));
		if (oldUser != null) {
			logger.info("用户已存在");
			return Result.failureResult("用户已存在");
		}

		userDo.setRegTime(new Date().getTime());// 新增时间（注册时间）
		userDo.setIsActivated(1);// 激活状态
		userDo.setCertification(1);// 认证状态
		userDo.setRegTime(new Date().getTime());// 保存当前时间
		// 密码加密处理
		userDo.setUserPassword(DataEncrypt.encrypt(userDo.getUserPassword())); // 登录密码
		userDo.setTwoPassword(DataEncrypt.encrypt(userDo.getTwoPassword())); // 支付密码

		// 推荐用户：查出所有用户的手机号，判断用户的填写的推荐人是否存在
		if (StringUtils.isNotBlank(userDo.getRefereeUserMobile())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("mobile", userDo.getRefereeUserMobile());

			List<UserDo> refUserLst = this.selectMobile(params);
			if (refUserLst == null || refUserLst.size() < 1) {
				return Result.failureResult("推荐人不存在");
			}
			ref = refUserLst.get(0);
		}

		// 新增的会员信息
		logger.info("用户信息:userName=" + userDo.getUserName());
		logger.info("用户信息:trueName=" + userDo.getTrueName());
		logger.info("用户信息:mobile=" + userDo.getMobile());
		logger.info("用户信息:login_password=" + userDo.getUserPassword());
		logger.info("用户信息:seconde_password=" + userDo.getTwoPassword());
		logger.info("用户信息:userLevel=" + userDo.getUserLevel());
		logger.info("用户信息:refereeUserMobile=" + userDo.getRefereeUserMobile());
		logger.info("用户信息:isActivated=" + userDo.getIsActivated());
		logger.info("用户信息:certification=" + userDo.getCertification());
		logger.info("用户信息:sex=" + userDo.getSex());
		logger.info("用户信息:idunmber=" + userDo.getIdnumber());
		logger.info("用户信息:banknumber=" + userDo.getBanknumber());
		logger.info("用户信息:banktype=" + userDo.getBanktype());
		// 新增会员
		int result = userDao.insertSelective(userDo);
		// 维护推荐人关系
		if (ref != null) {
			userDo.setRefereeid(ref.getId());// 获取用户推荐人id
			userDo.setParentid(ref.getId());// 获取上级id
		}
		if (ref != null) {
			// 维护父节点关系
			maintainUserParent(userDo.getId(), ref.getId(), userDo.getPos());
			// 维护推荐人关系
			maintainUserReferee(userDo.getId(), ref.getId());
		}
		// 维护賬戶
		maintainUserAccount(userDo.getId());
		return result > 0 ? Result.successResult("service：新增成功!") : Result.failureResult("service：新增失败");
	}


	@Override
	public Result<?> update(UserDo userDo) {
		if (userDo == null || userDo.getId() == null) {
			return Result.failureResult("修改用户信息参数错误!");
		}

		int flag = userDao.updateByPrimaryKeySelective(userDo);
		if (flag > 0) {
			return Result.successResult("修改成功");
		} else {

			return Result.failureResult("修改失败");
		}
	}

	/**
	 * 下单购买商品之后，用户状态激活
	 */
	@Override
	public int updateUserStatus(Map<String, Object> params) {

		return userDao.updateUserStatus(params);
	}

	/**
	 * 单独修改用户等级方法
	 */
	@Override
	public boolean updateLevel(UserDo record) {
		boolean flag = false;
		if (userDao.updateLevel(record) > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 后台客户分页査询
	 */
	@Override
	public PageDo<Map<String, Object>> selectUserByPage(PageDo<Map<String, Object>> page, Map<String, Object> param) {
		// TODO Auto-generated method stub
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		param.put(Constants.MYBATIS_PAGE, page);
		List<Map<String, Object>> list = userDao.selectByPage(param);
		logger.debug("分页查询用户信息：" + list);
		// 过滤重复的用户地址
		// for(int i=0 ; i<list.size(); i++){
		// if(i==list.size()){
		// break;
		// }
		// if(list.get(i).get("id").equals(list.get(i+1).get("id"))){
		// list.remove(i+1);
		// }
		// }
		page.setModelList(list);
		return page;
	}

	/**
	 * 区域管理查询
	 */
	@Override
	public PageDo<UserDo> getUserDistrictPage(Map<String, Object> param, PageDo<UserDo> page) {
		// TODO Auto-generated method stub
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		param.put(Constants.MYBATIS_PAGE, page);
		List<UserDo> list = userDao.selectDistrictPage(param);
		page.setModelList(list);
		return page;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateUserDistrict(UserDo user) {
		int i = 0;
		if (user != null && user.getDistrict() != null) {
			i = userDao.updateLevel(user);
			District district = new District();
			district.setUserId(user.getId());
			District oldDistrict = districtService.selectByPrimaryKeySelective(district);
			if (oldDistrict == null) {
				district.setDistrctName(user.getDistrict());
				district.setDistrictStatus(1);
				districtService.insertSelective(district);
			} else {
				district.setDistrctName(user.getDistrict());
				district.setDistrictId(oldDistrict.getDistrictId());
				int ret = districtService.updateDistrictById(district);
				if (ret < 1) {
					throw new BusinessException("重复设置区域代表" + user.getDistrict());
				}
			}
		}
		return i;
	}
	
	
	public List<Map<String,Object>> shareList(Map<String,Object> map){
		return userDao.shareList(map);
	}
}
