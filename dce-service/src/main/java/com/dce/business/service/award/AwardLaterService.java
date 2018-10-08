package com.dce.business.service.award;

import java.util.List;
import java.util.Map;

import com.dce.business.dao.award.AwardDao;
import com.dce.business.entity.award.Award;

public interface AwardLaterService {
	
	/**
	 * id条件删除奖励记录
	 * @param awardid
	 * @return
	 */
    boolean deleteByPrimaryKey(Integer awardid);

    /**
     * 添加全部信息奖励记录
     * @param record
     * @return
     */
    boolean insert(Award record);

    /**
     * 选择性添加部分奖励记录
     * @param record
     * @return
     */
    boolean insertSelective(Award record);

    /**
     * id条件查询单条奖励记录
     * @param awardid
     * @return
     */
    Award selectByPrimaryKey(Integer awardid);
    
    /**
     * 多条件查询部分奖励记录（map为空时查询全部奖励记录）
     * @param map
     * @return
     */
    List<Award> selectAward(Map map);

    /**
     * 选择性修改部分记录记录
     * @param record
     * @return
     */
    boolean updateByPrimaryKeySelective(Award record);

    /**
     * 修改全部信息奖励记录
     * @param record
     * @return
     */
    boolean updateByPrimaryKey(Award record);

}
