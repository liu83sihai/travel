package com.dce.business.service.impl.dict;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dce.business.common.enums.CurrencyType;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.dao.dict.ICtCurrencyDao;
import com.dce.business.entity.dict.CtCurrencyDo;
import com.dce.business.service.dict.ICtCurrencyService;

@Service("ctCurrencyService")
public class CtCurrencyServiceImpl implements ICtCurrencyService {

	@Resource
	private ICtCurrencyDao ctCurrencyDao;
	
	@Override
	public CtCurrencyDo selectByName(String currency_name) {
		if("DCE".equals(currency_name)){
			currency_name = "IBAC";
		}
		return ctCurrencyDao.selectByName(currency_name);
	}

	
	@Override
	public BigDecimal rmb2Dce(BigDecimal rmb) {
		
		CtCurrencyDo ct = ctCurrencyDao.selectByName(CurrencyType.IBAC.name());
		if(null == ct || ct.getPrice_open() == null || BigDecimal.ZERO.equals(ct.getPrice_open()) ){
			throw new BusinessException("没有配置最新价格");
		}
		return rmb.divide(ct.getPrice_open(), 6, RoundingMode.HALF_UP);
	}


	@Override
	public Result<?> update(CtCurrencyDo ct) {
		int i = ctCurrencyDao.update(ct);
		if(i > 0){
			return Result.successResult("修改成功!");
		}
		return Result.failureResult("修改失败!");
	}

	
}
