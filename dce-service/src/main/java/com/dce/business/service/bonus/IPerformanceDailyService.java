package com.dce.business.service.bonus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dce.business.entity.touch.PerformanceDailyDo;

public interface IPerformanceDailyService {
	/**
	 * 更新每日奖金总额 
	 * @param userId 用户id
	 * @param date 发生日期
	 * @param balance 总业绩
	 * @param balance_left 左区业绩
	 * @param balance_right 右区业绩
	 * @return  
	 */
	int updateBalance(Integer userId, Date date, BigDecimal balance, BigDecimal balance_left, BigDecimal balance_right);

	/**
	 * 查询业绩
	 * @param userId 用户id
	 * @param date 发生日期
	 * @return
	 */
	PerformanceDailyDo getPerformanceDaily(Integer userId, Date date);
 
	List<PerformanceDailyDo> select(Map<String, Object> params);
}
