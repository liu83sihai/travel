package com.dce.business.dao.touch;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.touch.PerformanceDailyDo;

public interface IPerformanceDailyDao {
	int deleteByPrimaryKey(Integer id);

	int insert(PerformanceDailyDo record);

	int insertSelective(PerformanceDailyDo record);

	PerformanceDailyDo selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(PerformanceDailyDo record);

	int updateByPrimaryKey(PerformanceDailyDo record);
	
	int updateBalance(Map<String, Object> params);
	
	List<PerformanceDailyDo> select(Map<String, Object> params);
}