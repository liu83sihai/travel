package com.dce.business.dao.user;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.user.UserRefereeDo;

public interface IUserRefereeDao {
	int deleteByPrimaryKey(Integer id);

	int insert(UserRefereeDo record);

	/**
	 * 添加推荐人关系列表
	 * 
	 * @param record
	 * @return
	 */
	int insertSelective(UserRefereeDo record);

	UserRefereeDo selectByPrimaryKey(Integer id);

	/**
	 * 修改用户推荐人关系
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(UserRefereeDo record);

	int updateByPrimaryKey(UserRefereeDo record);

	/**
	 * 查询团队成员
	 */
	List<UserRefereeDo> select(Map<String, Object> params);

	/**
	 * 查看直推树
	 * 
	 * @return
	 */
	List<Map<String, Object>> listMyRef(Map<String, Object> params);

	List<Map<String, Object>> selectMyGroup(Map<String, Object> params);

}