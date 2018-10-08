package com.dce.business.dao.bonus;

import com.dce.business.entity.bonus.BonusLogDo;

public interface IBonusLogDao {
    int insert(BonusLogDo record);

    int insertSelective(BonusLogDo record);
    
    BonusLogDo selectBonusLogById(Integer recordId);
    
    int updateRedoStatusById(Integer recordId);
}