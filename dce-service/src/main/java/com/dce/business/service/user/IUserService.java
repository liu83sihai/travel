package com.dce.business.service.user;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.dce.business.common.result.Result;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.entity.user.UserParentDo;

public interface IUserService {

	/**
	 * 用户个人信息修改
	 * 
	 * @param userDo
	 * @return
	 */
	Result<?> update(UserDo userDo);

	/**
	 * 根据用户名查用户
	 * 
	 * @param userName
	 * @return
	 */
	UserDo userName(String userName);

	/**
	 * 查询用户
	 * 
	 * @param userId
	 * @return
	 */
	UserDo getUser(Integer userId);

	/**
	 * 用户注册
	 * 
	 * @param userDo
	 * @return
	 */
	Result<?> reg(UserDo userDo);

	/**
	 * 新増会员
	 * 
	 * @param userDo
	 * @return
	 */
	Result<?> addUserInfo(UserDo userDo);

	/**
	 * 模糊查询用户
	 * 
	 * @param userName
	 * @return
	 */
	List<UserDo> searchLikeUserName(String userName);

	/**
	 * 更新用户的总释放静态
	 * 
	 * @param staticAmount
	 * @param userId
	 */
	void updateStatic(BigDecimal staticAmount, int userId);

	/**
	 * 更新用户以及父节点的总释放静态
	 * 
	 * @param staticAmount
	 * @param userId
	 */
	void updateAllPerformance(BigDecimal staticAmount, Integer userId);

	/**
	 * 更新量碰数量
	 * 
	 * @param touchedAmount
	 * @param userId
	 */
	void updateTouched(BigDecimal touchedAmount, int userId);

	/**
	 * 用户登录密码修改
	 * 
	 * @param userDo
	 * @return
	 */
	Result<?> updateByPrimaryKeyLogPass(UserDo userDo);

	/**
	 * 用户支付密码修改
	 * 
	 * @param userDo
	 * @return
	 */
	Result<?> updateByPrimaryKeyPayPass(UserDo userDo);

	/**
	 * 用户信息认证
	 * 
	 * @param userDo
	 * @return
	 */
	Result<?> Authentication(UserDo userDo);

	/**
	 * 查看我的团队
	 * 
	 * @param userId
	 * @return
	 */
	List<UserParentDo> getMyMember(Map<String, Object> params);

	/**
	 * 查看组织结构树
	 * 
	 * @param 用户ID
	 * @param level
	 *            查询层级
	 * @return
	 */
	List<Map<String, Object>> listMyOrg(Integer userId, int level);

	/**
	 * 查看直推树
	 * 
	 * @return
	 */
	List<Map<String, Object>> listMyRef(Integer userId, int startRow, int pageSize);

	/**
	 * 查询分页
	 * 
	 * @param params
	 * @return
	 */
	List<UserDo> selectPage(Map<String, Object> params);

	/**
	 * 查询所有手机号
	 * 
	 * @param params
	 * @return
	 */
	List<UserDo> selectMobile(Map<String, Object> params);

	/**
	 * 设置用户级别
	 * 
	 * @param userCode
	 * @param userLevel
	 * @return
	 */
	Result<?> setUserLevel(String userCode, String userLevel);

	PageDo<Map<String,Object>> selectUserByPage(PageDo<Map<String,Object>> page, Map<String, Object> params);

	PageDo<UserDo> selectEthAccountByPage(PageDo<UserDo> page, Map<String, Object> params);

	List<Map<String,Object>> shareList(Map<String,Object> map);
	/**
	 * 统计报单金额
	 * 
	 * @param params
	 * @return
	 */
	Long selectBaoDanAmount(Map<String, Object> params);

	/**
	 * 选择性多条件查
	 * 
	 * @param map
	 * @return
	 */
	List<UserDo> selectUserCondition(Map<String, Object> map);
	
	
	
	
	/**
	 * 分配区域方法
	 * @param params
	 * @return
	 */
	public int updateUserDistrict(UserDo user);

	// 下单购买商品之后，用户状态激活
	int updateUserStatus(Map<String, Object> params);

	PageDo<UserDo> getUserDistrictPage(Map<String, Object> param, PageDo<UserDo> page);

	/**
	 * 购买之后成为vip
	 * @param userDo
	 */
	int updateUserByBuy(UserDo userDo);

}
