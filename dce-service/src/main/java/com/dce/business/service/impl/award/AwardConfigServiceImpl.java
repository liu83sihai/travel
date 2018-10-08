package com.dce.business.service.impl.award;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dce.business.common.enums.AccountType;
import com.dce.business.common.enums.IncomeType;
import com.dce.business.dao.account.IUserAccountDao;
import com.dce.business.dao.award.AwardConfigDao;
import com.dce.business.dao.award.AwardlistMapper;
import com.dce.business.dao.district.districtMapper;
import com.dce.business.dao.district.regionalawardsMapper;
import com.dce.business.dao.user.IUserDao;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.award.AwardConfig;
import com.dce.business.entity.district.District;
import com.dce.business.entity.district.Regionalawards;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.award.AwardConfigService;
import com.dce.business.service.impl.district.DistrictServiceImpl;
import com.dce.business.service.impl.district.RegionalawardsServiceImpl;
import com.dce.business.service.user.IUserService;
import com.dce.business.service.user.UserAdressService;

@Service("awardConfigService")
public class AwardConfigServiceImpl implements AwardConfigService {

	private final static Logger logger = LoggerFactory.getLogger(AwardConfigServiceImpl.class);
	@Resource
	private IUserDao userDao;

	@Resource
	private IUserService userService;
	
	//地址
	@Resource
	private UserAdressService userAdressService;

	// 奖励记录
	@Resource
	private AwardlistServiceImpl awardlistService;

	@Resource
	private AwardConfigDao awardConfigDao;

	// 区域奖励记录
	@Resource
	private regionalawardsMapper regionlDao;

	// 区域管理
	@Resource
	private DistrictServiceImpl districtService;

	// 账户
	@Resource
	private IAccountService accountService;

	/*
	 * id条件删除等级 (non-Javadoc)
	 * 
	 * @see
	 * com.dce.business.service.award.AwardConfigService#deleteByPrimaryKey(java
	 * .lang.Long)
	 */
	@Override
	public boolean deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		logger.info("id条件删除等级记录");
		boolean flag = false;
		if (id != null && id != 0) {
			flag = awardConfigDao.deleteByPrimaryKey(id) > 0;
		}

		return flag;
	}

	/**
	 * 添加等级
	 */
	@Override
	public boolean insert(AwardConfig record) {
		// TODO Auto-generated method stub
		logger.info("添加等级");
		boolean flag = false;
		if (record != null) {
			flag = awardConfigDao.insert(record) > 0;
		}
		return flag;
	}

	/**
	 * 条件添加等级
	 */
	@Override
	public boolean insertSelective(AwardConfig record) {
		// TODO Auto-generated method stub
		logger.info("条件添加等级");
		boolean flag = false;
		if (record != null) {
			flag = awardConfigDao.insert(record) > 0;
		}
		return flag;
	}

	/**
	 * id条件查询等级
	 */
	@Override
	public AwardConfig selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		logger.info("id条件查询等级");
		AwardConfig fig = null;
		if (id != null && id != 0) {
			fig = awardConfigDao.selectByPrimaryKey(id);
		}
		return fig;
	}

	/**
	 * id条件修改部分信息
	 */
	@Override
	public boolean updateByPrimaryKeySelective(AwardConfig record) {
		// TODO Auto-generated method stub
		logger.info("id条件修改部分信息");
		boolean flag = false;
		if (record != null) {
			flag = awardConfigDao.updateByPrimaryKeySelective(record) > 0;
		}
		return flag;
	}

	/**
	 * id条件修改全部信息
	 */

	@Override
	public boolean updateByPrimaryKey(AwardConfig record) {
		// TODO Auto-generated method stub
		logger.info("id条件修改全部信息");
		boolean flag = false;
		if (record != null) {
			flag = awardConfigDao.updateByPrimaryKeySelective(record) > 0;
		}
		return flag;
	}

	/**
	 * 查询全部等级信息
	 */
	@Override
	public List<AwardConfig> selectAward() {
		// TODO Auto-generated method stub

		return awardConfigDao.selectAward();
	}

	/**
	 * 用户购买商品升级方法 userid 购买者id count 数量
	 */
	@Override
	public Integer userUpgrade(Integer userid, int count) {
		// 返回升级级别
		Integer level = null;
		try {

			// 获取购买者的信息
			UserDo user = userDao.selectByPrimaryKey(userid);

			if (user != null) {

				// 获取用户等级
				int userLevel = user.getUserLevel();
				UserDo updatauser = new UserDo();

				switch (userLevel) {
				case 0:
					if (count >= 1 && 5 > count) {
						// 普通用户升级为会员
						updatauser.setUserLevel((byte) 1);
						if (userDao.updateByPrimaryKeySelective(user) > 0) {
							level = 1;
						}

					} else if (count >= 5 && 50 > count) {
						// 普通用户升级为vip
						updatauser.setUserLevel((byte) 2);
						if (userDao.updateByPrimaryKeySelective(user) > 0) {
							level = 2;
						}

					} else if (count >= 50) {
						// 普通用户升级为城市合伙人，升级为城市合伙人，要添加一条记录，并且判断用户推荐人是否有资格升级为股东
						updatauser.setUserLevel((byte) 3);
						if (userDao.updateByPrimaryKeySelective(user) > 0) {
							// 获得区域代表资格，生成一条数据，等待后台封地
							District dis = new District();
							dis.setUserId(userid);
							districtService.insertSelective(dis);
							level = 3;
						}
					}
					break;
				case 1:
					if (count >= 5 && 50 > count) {
						// 会员用户升级为vip
						updatauser.setUserLevel((byte) 2);
						if (userDao.updateByPrimaryKeySelective(user) > 0) {

						}
						level = 2;
					} else if (count >= 50) {
						// 会员用户升级为城市合伙人，升级为城市合伙人，要添加一条记录，并且判断用户推荐人是否有资格升级为股东
						updatauser.setUserLevel((byte) 3);
						if (userDao.updateByPrimaryKeySelective(user) > 0) {
							// 获得区域代表资格，生成一条数据，等待后台封地
							District dis = new District();
							dis.setUserId(userid);
							districtService.insertSelective(dis);
							level = 3;
						}

					}
					break;
				case 2:
					if (count >= 50) {
						// vip用户升级为，升级为城市合伙人，要添加一条记录，并且判断用户推荐人是否有资格升级为股东
						updatauser.setUserLevel((byte) 3);
						if (userDao.updateByPrimaryKeySelective(user) > 0) {
							// 获得区域代表资格，生成一条数据，等待后台封地
							District dis = new District();
							dis.setUserId(userid);
							districtService.insertSelective(dis);
							level = 3;
						}
						// 判断该购买者的推荐人是否满足升级为股东的条件,首先必须为城市合伙人，如何推荐5人为城市合伙人
						if (user.getId() == 3) {
							if (upgradePartner((int) user.getUserLevel())) {
								level = 4;
							}
						}

					}
					break;
				default:
					level = -1;
					break;

				}
			} else {
				logger.error("无此用户信息");
			}

		} catch (Exception e) {
			logger.error("用户升级异常");
		}

		return level;
	}

	/**
	 * 用户升级为股东方法 userid 推荐人id
	 */
	@Override
	public boolean upgradePartner(Integer userid) {

		// 返回值
		boolean flag = false;
		try {

			// 获取推荐人的信息
			UserDo user = userDao.selectByPrimaryKey(userid);
			// 防止空指针
			if (user != null) {
				// 推荐人必须为城市合伙人才能升级
				if (user.getUserLevel() == 3) {
					// 获取推荐人推荐城市合伙人的个数
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userLevel", 3);
					map.put("refereeid", userid);
					List<UserDo> listuser = userDao.selectUser(map);
					if (listuser.size() >= 5) {
						UserDo updatauser = new UserDo();
						// 推荐人升级为股东
						updatauser.setUserLevel((byte) 4);
						if (userDao.updateByPrimaryKeySelective(updatauser) > 0) {
							// 升级股东成功
							flag = true;
						} else {
							// 升级失败
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error("升级股东异常");
		}

		return flag;
	}

	/**
	 * 发放奖励方法 userid 购买者id count 数量 area 区域地址
	 * 
	 */
	@Override
	public boolean grantAward(int userid, int count, Integer addressid) {
		// 返回值
		boolean flag = false;

		try {

			// 区域奖励,先进行区域奖励，再进行升级
			areaAward(addressid, count);

			// 获取购买者的信息
			UserDo buyerUser = userDao.selectByPrimaryKey(userid);
			// 获取推荐人的信息refereeid
			UserDo presenterUser = userDao.selectByPrimaryKey(buyerUser.getRefereeid());

			// 获取第二代推荐人的信息
			UserDo presenterUserTow = userDao.selectByPrimaryKey(presenterUser.getRefereeid());

			// 判断用户是否有推荐人
			if (presenterUser != null) {

				// 推荐奖励
				if (count >= 1 && count < 5) {
					// 1-5盒没有到5
					awardone(buyerUser, presenterUser, presenterUserTow, count);
					flag = true;

				} else if (count >= 5 && count < 50) {
					// 5-50没有到50
					awardtwo(buyerUser, presenterUser, presenterUserTow, count);
					flag = true;
				} else if (count >= 50) {
					// 50盒以上
					awardthree(buyerUser, presenterUser, presenterUserTow, count);
					flag = true;
				}

			}

			// 用户升级方法
			userUpgrade(userid, count);

		} catch (Exception e) {
			logger.error("发放奖励失败");
		}

		return flag;
	}

	/**
	 * 
	 * 发放区域奖励方法 areaid 区域地址 count 购买数量
	 * 
	 */
	@Override
	public boolean areaAward(int addressid, int count) {
		// 查询购买者填写地址所在区域的管理者信息 实体类字段district 数据库字段district
		// 返回值
		boolean flag = false;
		try {
			//获取发货地址
		   String area=userAdressService.selectByPrimaryKey(addressid).getAddress();

			// 判断该地区是否有区域代表
			Map<String, Object> map = new HashMap<>();
			map.put("district", area);
			List<UserDo> userLst = userDao.selectUser(map);

			if (userLst != null && userLst.size() == 1) {

				UserDo areaUser = userLst.get(0);
				// 进行区域代表奖励50/盒
				// 获取总奖金
				Map<String, Object> maps = new HashMap<>();
				maps.put("algebra", 0);
				List<Regionalawards> regional = regionlDao.queryListPage(maps);
				int sum = Integer.valueOf(regional.get(0).getRewardbalance()) * count;
				// 获取账户当前余额
				UserAccountDo accont = new UserAccountDo();
				// 相加之后余额
				accont.setAmount(accont.getAmount().add(BigDecimal.valueOf(sum)));
				accont.setUserId(areaUser.getId());
				accont.setAccountType(AccountType.wallet_money.toString());
				accountService.updateUserAmountById(accont, IncomeType.TYPE_AWARD_LEADER);

				// 发放20元区域奖励
				twentyAward(areaUser.getRefereeid(), count);

				flag = true;
			} else {
				logger.error("该区域无管理员");
			}
		} catch (Exception e) {
			logger.error("区域奖励发送失败");
		}

		return flag;
	}

	// 推荐人id count 数量
	public boolean twentyAward(Integer id, int count) {

		// 返回值
		boolean flase = false;

		try {
			// 查询推荐人信息
			UserDo user = userService.getUser(id);
			// 防止空指针
			if (user != null) {
				// 判断用户是否为城市合伙人
				if (user.getUserLevel() == 3) {
					// 进行区域代表奖励20/盒
					// 获取总奖金
					Map<String, Object> mapstwo = new HashMap<>();
					mapstwo.put("algebra", 1);
					List<Regionalawards> regionaltwo = regionlDao.queryListPage(mapstwo);
					int sumtwo = Integer.valueOf(regionaltwo.get(0).getRewardbalance()) * count;
					// 获取账户当前余额
					UserAccountDo acconttwo = new UserAccountDo();
					// 相加之后余额
					acconttwo.setAmount(BigDecimal.valueOf(sumtwo));
					acconttwo.setUserId(user.getId());
					acconttwo.setAccountType(AccountType.wallet_money.toString());
					accountService.updateUserAmountById(acconttwo, IncomeType.TYPE_AWARD_LEADER);
					flase = true;
				} else {
					// 如果该用户没有推荐人，则终止循环不派送20元奖励
					if (user.getRefereeid() == null || user.getRefereeid() == 0) {
						return false;
					}
					// 递归循环，直到找到城市合伙人
					twentyAward(user.getRefereeid(), count);
				}
			}

		} catch (Exception e) {
			logger.error("发放区域20元奖励异常");
		}
		return flase;
	}

	/**
	 * 
	 * 判断1-5盒数量的方法,小余5盒
	 * 
	 * @param buyerUser
	 *            购买者的信息
	 * @param presenterUser
	 *            获取推荐人的信息
	 * @param presenterUserTow
	 *            获取第二代推荐人的信息
	 * @param count
	 *            商品数量
	 * @return
	 */
	public boolean awardone(UserDo buyerUser, UserDo presenterUser, UserDo presenterUserTow, int count) {
		// 接受参数
		Map<String, Object> map = new HashMap<>();
		// 接受奖励记录
		Map<String, Object> mapaward = new HashMap<>();
		// 接受奖励金额
		BigDecimal sum = new BigDecimal(0);
		// 购买者等级
		map.put("buyerLecel", (int) buyerUser.getUserLevel());
		// 购买盒数
		map.put("buyQty", 1);
		// 推荐人等级
		map.put("p1Level" + presenterUser.getUserLevel(), presenterUser.getUserLevel());
		// 得到奖励记录
		mapaward = awardlistService.conditionQueryAward(map);
		// 判断获取的记录是唯一的，防止空指针
		if (mapaward != null && mapaward.size() == 1 && presenterUser.getUserLevel() != 0) {
			// 判断推荐人是否是会员
			if (presenterUser.getUserLevel() == 1) {
				// 判断该会员是否是首次分享
				if (awardlistService.getById(buyerUser.getRefereeid()) == null) {

					// 获取奖励的金额
					sum = ((BigDecimal) mapaward.get("award"));
					// 获取账户当前余额
					UserAccountDo accont = new UserAccountDo();
					// 相加之后余额
					accont.setAmount(sum);
					accont.setUserId(presenterUser.getId());
					accont.setAccountType(AccountType.wallet_money.toString());
					accountService.updateUserAmountById(accont, IncomeType.TYPE_AWARD_REFEREE);

				}
			} else {
				// 获取奖励的金额
				sum = ((BigDecimal) mapaward.get("award")).multiply(BigDecimal.valueOf(count));
				// 获取账户当前余额
				UserAccountDo accont = new UserAccountDo();
				// 相加之后余额
				accont.setAmount(sum);
				accont.setUserId(presenterUser.getId());
				accont.setAccountType(AccountType.wallet_money.toString());
				accountService.updateUserAmountById(accont, IncomeType.TYPE_AWARD_REFEREE);

			}
		}
		return false;

	}

	/**
	 * 
	 * 判断50以上盒数量的方法
	 * 
	 * @param buyerUser
	 *            购买者的信息
	 * @param presenterUser
	 *            获取推荐人的信息
	 * @param presenterUserTow
	 *            获取第二代推荐人的信息
	 * @param count
	 *            商品数量
	 * @return
	 */
	public boolean awardthree(UserDo buyerUser, UserDo presenterUser, UserDo presenterUserTow, int count) {
		// 接受参数
		Map<String, Object> map = new HashMap<>();
		// 接受奖励记录
		Map<String, Object> mapaward = new HashMap<>();
		// 接受奖励金额
		BigDecimal sum = new BigDecimal(0);
		// 购买者等级
		map.put("buyerLecel", (int) buyerUser.getUserLevel());
		// 购买盒数
		map.put("buyQty", 50);
		// 推荐人等级
		map.put("p1Level" + presenterUser.getUserLevel(), presenterUser.getUserLevel());
		// 得到奖励记录
		mapaward = awardlistService.conditionQueryAward(map);
		// 判断获取的记录是唯一的，防止空指针
		if (mapaward != null && mapaward.size() == 1 && presenterUser.getUserLevel() != 0) {
			// 判断推荐人是否是会员
			if (presenterUser.getUserLevel() == 1) {
				// 判断该会员是否是首次分享
				if (awardlistService.getById(buyerUser.getRefereeid()) == null) {

					// 获取奖励的金额
					if (count > 50) {
						sum = sum.add(moreFiveaward(buyerUser, presenterUser, count));
					}
					sum = sum.add((BigDecimal) mapaward.get("award"));
					// 获取账户当前余额
					UserAccountDo accont = new UserAccountDo();
					// 相加之后余额
					accont.setAmount(sum);
					accont.setUserId(presenterUser.getId());
					accont.setAccountType(AccountType.wallet_money.toString());
					accountService.updateUserAmountById(accont, IncomeType.TYPE_AWARD_REFEREE);

				}
			} else {
				// 获取奖励的金额
				sum = (BigDecimal) mapaward.get("award");
				// 获取账户当前余额
				UserAccountDo accont = new UserAccountDo();
				// 相加之后余额
				accont.setAmount(sum);
				accont.setUserId(presenterUser.getId());
				accont.setAccountType(AccountType.wallet_money.toString());
				accountService.updateUserAmountById(accont, IncomeType.TYPE_AWARD_REFEREE);
			}
		}
		return false;
	}

	/**
	 * 
	 * 判断5-50盒数量的方法
	 * 
	 * @param buyerUser
	 *            购买者的信息
	 * @param presenterUser
	 *            获取推荐人的信息
	 * @param presenterUserTow
	 *            获取第二代推荐人的信息
	 * @param count
	 *            商品数量
	 * @return
	 */
	public boolean awardtwo(UserDo buyerUser, UserDo presenterUser, UserDo presenterUserTow, int count) {
		// 接受参数
		Map<String, Object> map = new HashMap<>();
		// 接受奖励记录
		Map<String, Object> mapaward = new HashMap<>();
		// 接受奖励金额
		BigDecimal sum = new BigDecimal(0);
		// 购买者等级
		map.put("buyerLecel", (int) buyerUser.getUserLevel());
		// 购买盒数
		map.put("buyQty", 5);
		// 推荐人等级
		map.put("p1Level" + presenterUser.getUserLevel(), presenterUser.getUserLevel());
		// 得到奖励记录
		mapaward = awardlistService.conditionQueryAward(map);
		// 判断获取的记录是唯一的，防止空指针
		if (mapaward != null && mapaward.size() == 1 && presenterUser.getUserLevel() != 0) {
			// 判断推荐人是否是会员
			if (presenterUser.getUserLevel() == 1) {
				// 判断该会员是否是首次分享
				if (awardlistService.getById(buyerUser.getRefereeid()) == null) {

					// 获取奖励的金额
					if (count > 50) {
						sum = sum.add(moreFiveaward(buyerUser, presenterUser, count));
					}
					sum = sum.add((BigDecimal) mapaward.get("award"));
					// 获取账户当前余额
					UserAccountDo accont = new UserAccountDo();
					// 相加之后余额
					accont.setAmount(sum);
					accont.setUserId(presenterUser.getId());
					accont.setAccountType(AccountType.wallet_money.toString());
					accountService.updateUserAmountById(accont, IncomeType.TYPE_AWARD_REFEREE);

				}
			} else {
				// 获取奖励的金额
				sum = (BigDecimal) mapaward.get("award");
				// 获取账户当前余额
				UserAccountDo accont = new UserAccountDo();
				// 相加之后余额
				// 计算5盒以上50以下的中的奖励
				if (count > 5 && count < 50) {
					sum = sum.add(moreFiveaward(buyerUser, presenterUser, count));
				}
				accont.setAmount(sum);
				accont.setUserId(presenterUser.getId());
				accont.setAccountType(AccountType.wallet_money.toString());
				accountService.updateUserAmountById(accont, IncomeType.TYPE_AWARD_REFEREE);

				if (buyerUser.getUserLevel() == 0 || buyerUser.getUserLevel() == 1) {
					// 第二代奖励
					awardtnt(buyerUser, presenterUser, presenterUserTow, count);

				}
			}
		}
		return false;
	}

	/**
	 * 第二代奖励
	 * 
	 * @param buyerUser
	 *            购买者信息
	 * @param presenterUser
	 *            推荐人信息
	 * @param presenterUserTow
	 *            第二代推荐人信息
	 * @param count
	 *            购买数量
	 * @return
	 */
	public boolean awardtnt(UserDo buyerUser, UserDo presenterUser, UserDo presenterUserTow, int count) {
		// 接受参数
		Map<String, Object> map = new HashMap<>();
		// 接受奖励记录
		Map<String, Object> mapaward = new HashMap<>();
		// 接受奖励金额
		BigDecimal sum = new BigDecimal(0);
		// 购买者等级
		map.put("buyerLecel", (int) buyerUser.getUserLevel());
		// 购买盒数
		map.put("buyQty", 5);
		// 推荐人等级
		map.put("p2Level" + presenterUserTow.getUserLevel(), presenterUserTow.getUserLevel());
		// 得到奖励记录
		mapaward = awardlistService.conditionQueryAward(map);
		// 判断获取的记录是唯一的，防止空指针
		if (mapaward != null && mapaward.size() == 1 && presenterUser.getUserLevel() != 0) {

			// 获取奖励的金额
			sum = (BigDecimal) mapaward.get("award");
			// 获取账户当前余额
			UserAccountDo accont = new UserAccountDo();
			// 增加余额
			accont.setAmount(sum);
			accont.setUserId(presenterUserTow.getId());
			accont.setAccountType(AccountType.wallet_money.toString());
			accountService.updateUserAmountById(accont, IncomeType.TYPE_AWARD_REFEREE);
		}
		return false;
	}

	/**
	 * 
	 * 判断5盒以上数量或50盒以上的方法
	 * 
	 * @param buyerUser
	 *            购买者的信息
	 * @param presenterUser
	 *            获取推荐人的信息
	 * @param presenterUserTow
	 *            获取第二代推荐人的信息
	 * @param count
	 *            商品数量
	 * @return
	 */
	public BigDecimal moreFiveaward(UserDo buyerUser, UserDo presenterUser, int count) {

		// 接受参数
		Map<String, Object> map = new HashMap<>();
		// 接受奖励记录
		Map<String, Object> mapaward = new HashMap<>();
		// 接受奖励金额
		BigDecimal sum = new BigDecimal(0);
		// 购买者等级
		map.put("buyerLecel", (int) buyerUser.getUserLevel());
		// 购买盒数
		map.put("buyQty", 1);
		// 推荐人等级
		map.put("p1Level" + presenterUser.getUserLevel(), presenterUser.getUserLevel());
		// 得到奖励记录
		mapaward = awardlistService.conditionQueryAward(map);
		// 判断获取的记录是唯一的，防止空指针
		if (mapaward != null && mapaward.size() == 1 && presenterUser.getUserLevel() != 0) {

			// 获取奖励的金额
			if (count > 5 && count < 50) {
				count = count - 5;
			} else if (count > 50) {
				count = count - 50;
			}
			sum = ((BigDecimal) mapaward.get("award")).multiply(BigDecimal.valueOf(count));

		}

		return sum;
	}

	public boolean travel(UserDo buyerUser) {

		// 只有普通用户和会员用户可以触发旅游
		if (buyerUser.getUserLevel() == 0 || buyerUser.getUserLevel() == 0) {

		}

		return false;
	}

	/**
	 * 
	 * 旅游奖励发放
	 * 
	 * @return
	 */
	public boolean travelAward(int userid, int count) {

		// 设置返回值
		boolean flag = false;
		try {
			// 获取购买者信息
			UserDo user = userService.getUser(userid);
			// 防止空指针
			if (user != null) {
				UserAccountDo accont = new UserAccountDo();
				accont.setAmount(BigDecimal.valueOf(1));
				accont.setUserId(user.getId());
				accont.setAccountType(AccountType.wallet_travel.toString());
				accountService.updateUserAmountById(accont,IncomeType.TYPE_PURCHASE);
				flag=true;
			}
		} catch (Exception e) {
			logger.error("旅游奖励发放异常");
		}
		return flag;
	}

}
