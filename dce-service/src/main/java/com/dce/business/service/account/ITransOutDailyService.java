package com.dce.business.service.account;

import java.math.BigDecimal;

public interface ITransOutDailyService {

	/**
	 * 尝试转出
	 * @param userId
	 * @param accountType
	 * @param amount
	 * @return
	 */
	boolean tryTransOut(Integer userId, String accountType, BigDecimal amount);
}
