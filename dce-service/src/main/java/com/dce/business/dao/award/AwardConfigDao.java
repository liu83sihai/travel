package com.dce.business.dao.award;

import java.util.List;

import com.dce.business.entity.award.AwardConfig;

public interface AwardConfigDao {
	/**
	 * 条件删除某个等级
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Long id);

    /**
     * 添加一个等级
     * @param record
     * @return
     */
    int insert(AwardConfig record);

    /**
     * 条件添加等级
     * @param record
     * @return
     */
    int insertSelective(AwardConfig record);

    /**
     * 根据id条件查询等级
     * @param id
     * @return
     */
    AwardConfig selectByPrimaryKey(Long id);
    
    /**
     * 查询全部等级信息
     */
    List<AwardConfig>   selectAward();

    /**
     * id条件修改等级部分地方
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(AwardConfig record);

    /**
     * id条件修改等级全部地方
     * @param record
     * @return
     */
    int updateByPrimaryKey(AwardConfig record);
    
}