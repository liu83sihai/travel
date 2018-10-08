package com.dce.business.dao.common;

import com.dce.business.entity.common.BonusConfigDDo;

public interface IBonusConfigDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BonusConfigDDo record);

    int insertSelective(BonusConfigDDo record);

    BonusConfigDDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BonusConfigDDo record);

    int updateByPrimaryKeyWithBLOBs(BonusConfigDDo record);

    int updateByPrimaryKey(BonusConfigDDo record);
}