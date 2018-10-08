package com.dce.business.service.impl.bonus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dce.business.common.util.DateUtil;
import com.dce.business.dao.touch.IPerformanceDailyDao;
import com.dce.business.entity.touch.PerformanceDailyDo;
import com.dce.business.service.bonus.IPerformanceDailyService;

@Service("performanceDailyService")
public class PerformanceDailyServiceImpl implements IPerformanceDailyService {
	@Resource
	private IPerformanceDailyDao performanceDailyDao;

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateBalance(Integer userId, Date date, BigDecimal balance, BigDecimal balance_left, BigDecimal balance_right) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("date", DateUtil.dateToString(date)); //只需要年月日
		params.put("balance", balance);
		params.put("balance_left", balance_left);
		params.put("balance_right", balance_right);

		int result = performanceDailyDao.updateBalance(params);
		if (result > 0) {
			return result; //更新成功
		}

		PerformanceDailyDo performanceDailyDo = new PerformanceDailyDo();
		performanceDailyDo.setUserid(userId);
		performanceDailyDo.setBalance(balance);
		performanceDailyDo.setBalance_left(balance_left);
		performanceDailyDo.setBalance_right(balance_right);
		performanceDailyDo.setDate(date);
		performanceDailyDo.setUpdateTime(new Date());

		return performanceDailyDao.insertSelective(performanceDailyDo);
	}

	@Override
	public PerformanceDailyDo getPerformanceDaily(Integer userId, Date date) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("date", DateUtil.dateToString(date)); //只需要年月日

		List<PerformanceDailyDo> list = performanceDailyDao.select(params);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		return list.get(0);
	}

	@Override
	public List<PerformanceDailyDo> select(Map<String, Object> params) {
		return performanceDailyDao.select(params);
	}
}
