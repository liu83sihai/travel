package com.dce.business.dao.award;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.award.Award;

public interface AwardDao {
	/**
	 * id条件删除奖励记录
	 * 
	 * @param awardid
	 * @return
	 */
	int deleteByPrimaryKey(Integer awardid);

	/**
	 * 添加全部信息奖励记录
	 * 
	 * @param record
	 * @return
	 */
	int insert(Award record);

	/**
	 * 选择性添加部分奖励记录
	 * 
	 * @param record
	 * @return
	 */
	int insertSelective(Award record);

	/**
	 * id条件查询单条奖励记录
	 * 
	 * @param awardid
	 * @return
	 */
	Award selectByPrimaryKey(Integer awardid);

	/**
	 * 多条件查询部分奖励记录（map为空时查询全部奖励记录）
	 * 
	 * @param map
	 * @return
	 */
	List<Award> selectAward(Map map);

	/**
	 * 选择性修改部分记录记录
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(Award record);

	/**
	 * 修改全部信息奖励记录
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(Award record);
}