package com.dce.business.dao.user;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.user.UserDo;

public interface IUserDao {

	// 下单购买商品之后，用户状态激活
	int updateUserStatus(Map<String, Object> params);

	int deleteByPrimaryKey(Integer id);

	/**
	 * 添加信息
	 * 
	 * @param record
	 * @return
	 */
	int insert(UserDo record);

	/**
	 * 注册（添加信息，有非空判断）
	 * 
	 * @param record
	 * @return
	 */
	int insertSelective(UserDo record);

	/**
	 * 通过userId查询所有用户所有信息
	 * 
	 * @param id
	 * @return
	 */
	UserDo selectByPrimaryKey(Integer id);

	/**
	 * 用户个人信息修改
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(UserDo record);

	/**
	 * 登录密码
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeyLogPass(Map<String, Object> params);

	/**
	 * 支付密码
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeyPayPass(Map<String, Object> params);

	int updateByPrimaryKeyWithBLOBs(UserDo record);

	int updateByPrimaryKey(UserDo record);

	/**
	 * 查询所有手机号
	 * 
	 * @param params
	 * @return
	 */
	List<UserDo> selectMobile(Map<String, Object> params);

	/**
	 * 查询所有用户名（手机号）
	 * 
	 * @param params
	 * @return
	 */
	List<UserDo> selectUser(Map<String, Object> params);

	List<UserDo> list(Map<String, Object> params);

	void updateStatic(Map<String, Object> params);

	void updateTouched(Map<String, Object> params);

	void updatePerformance(Map<String, Object> params);

	void addRefereeNumber(Integer id);

	void addSonNumber(Integer id);

	/**
	 * 查询分页
	 * 
	 * @param params
	 * @return
	 */
	List<UserDo> selectFenYe(Map<String, Object> params);

	/**
	 * 分页查询 加入拦截器后的实现方式
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> selectByPage(Map<String, Object> params);

	/**
	 * 
	 * @param params
	 * @return
	 */
	List<UserDo> selectEthAccountByPage(Map<String, Object> params);

	/**
	 * 统计报单金额
	 * 
	 * @param params
	 * @return
	 */
	Long selectBaoDanAmount(Map<String, Object> params);

	/**
	 * 选择性多条件查询
	 * 
	 * @param map
	 * @return
	 */
	List<UserDo> selectUserCondition(Map<String, Object> map);
	
	/**
	 * 区域查询
	 */
	List<UserDo> selectDistrictPage(Map<String,Object> map);
	
	
	/**
	 * 修改有用戶等级方法
	 */
	int updateLevel(UserDo record);

}