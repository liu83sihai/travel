package com.dce.business.dao.dict;

import org.apache.ibatis.annotations.Param;

import com.dce.business.entity.dict.CtCurrencyDo;

public interface ICtCurrencyDao {
	
	CtCurrencyDo selectByName(@Param("currency_name") String currency_name);

	int update(CtCurrencyDo ct);
}
