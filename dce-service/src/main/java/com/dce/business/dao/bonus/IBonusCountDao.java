package com.dce.business.dao.bonus;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.bonus.BonusCountDo;

public interface IBonusCountDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BonusCountDo record);

    int insertSelective(BonusCountDo record);

    BonusCountDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BonusCountDo record);

    int updateByPrimaryKey(BonusCountDo record);

	List<Map<String, Object>> selectBonus(Map<String, Object> paraMap,
			int startRow, int pageSize);
}