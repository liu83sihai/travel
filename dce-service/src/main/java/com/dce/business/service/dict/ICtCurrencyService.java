package com.dce.business.service.dict;

import java.math.BigDecimal;

import com.dce.business.common.result.Result;
import com.dce.business.entity.dict.CtCurrencyDo;

public interface ICtCurrencyService {

	CtCurrencyDo selectByName(String currency_name);

	/**
	 * 人民币转dce
	 * @param rmb
	 * @return
	 */
	BigDecimal rmb2Dce(BigDecimal rmb);
	
	Result<?> update(CtCurrencyDo ct);
}
