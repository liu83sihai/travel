package com.dce.business.service.impl.account;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.dce.business.common.util.DateUtil;
import com.dce.business.dao.account.ITransOutDailyDao;
import com.dce.business.entity.account.TransOutDailyDo;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.account.ITransOutDailyService;

@Service("transOutDailyService")
public class TransOutDailyServiceImpl implements ITransOutDailyService {

	@Resource
	private ITransOutDailyDao transOutDailyDao;
	@Resource
	private IAccountService accountService;

	private final static BigDecimal rate = new BigDecimal("0.2"); //转出比例，应该设置字典

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public boolean tryTransOut(Integer userId, String accountType, BigDecimal amount) {
		Date date = new Date();

		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("accountType", accountType);
		params.put("date", DateUtil.dateToString(date));

		List<TransOutDailyDo> list = transOutDailyDao.select(params);

		//用户当天没有转出过
		if (CollectionUtils.isEmpty(list)) {
			UserAccountDo accountDo = accountService.selectUserAccount(userId, accountType);
			Assert.notNull(accountDo, "账户不存在");
			BigDecimal limitTransOutAmount = accountDo.getAmount().multiply(rate).setScale(6, RoundingMode.HALF_UP);

			//允许转出
			if (limitTransOutAmount.compareTo(amount) >= 0) {
				TransOutDailyDo transOutDailyDo = new TransOutDailyDo();
				transOutDailyDo.setUserId(userId);
				transOutDailyDo.setAccountType(accountType);
				transOutDailyDo.setDate(date);
				transOutDailyDo.setAmount(amount);
				transOutDailyDo.setMaxAmount(limitTransOutAmount);
				transOutDailyDo.setUpdateTime(new Date());

				transOutDailyDao.insertSelective(transOutDailyDo);
				return true;
			} else {
				return false;
			}
		}

		TransOutDailyDo transOutDailyDo = list.get(0);
		BigDecimal totalAmount = amount.add(transOutDailyDo.getAmount()).setScale(6, RoundingMode.HALF_UP);
		if (totalAmount.compareTo(transOutDailyDo.getMaxAmount()) <= 0) {
			params.put("amount", amount);
			transOutDailyDao.updateAmount(params);
			return true;
		}

		return false;
	}
}
