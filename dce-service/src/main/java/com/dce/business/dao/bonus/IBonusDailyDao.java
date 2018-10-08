package com.dce.business.dao.bonus;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.bonus.BonusDailyDo;

public interface IBonusDailyDao {
    int deleteByPrimaryKey(Long id);

    int insert(BonusDailyDo record);

    int insertSelective(BonusDailyDo record);

    BonusDailyDo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BonusDailyDo record);

    int updateByPrimaryKey(BonusDailyDo record);
    
    /**
     * 更新每日奖励统计额 
     * @param params
     * @return  
     */
    int updateAmount(Map<String, Object> params);
    
    List<BonusDailyDo> select(Map<String, Object> params);
} 