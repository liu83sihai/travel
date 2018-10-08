package com.dce.business.dao.account;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.account.TransOutDailyDo;

public interface ITransOutDailyDao {
	int deleteByPrimaryKey(Long id);

	int insert(TransOutDailyDo record);

	int insertSelective(TransOutDailyDo record);

	TransOutDailyDo selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(TransOutDailyDo record);

	int updateByPrimaryKey(TransOutDailyDo record);
	
	List<TransOutDailyDo> select(Map<String, Object> params);
	
	int updateAmount(Map<String, Object> params);
}