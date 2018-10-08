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
import com.dce.business.dao.bonus.IBonusDailyDao;
import com.dce.business.entity.bonus.BonusDailyDo;
import com.dce.business.service.bonus.IBonusDailyService;

@Service("bonusDailyService")
public class BonusDailyServiceImpl implements IBonusDailyService {

    @Resource
    private IBonusDailyDao bonusDailyDao;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateAmount(Integer userId, String incomeType, Date date, BigDecimal amount) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("incomeType", incomeType);
        params.put("date", DateUtil.dateToString(date)); //只需要年月日
        params.put("amount", amount);

        int result = bonusDailyDao.updateAmount(params);
        if (result > 0) {
            return result; //更新成功
        }

        BonusDailyDo bonusDailyDo = new BonusDailyDo();
        bonusDailyDo.setUserId(userId);
        bonusDailyDo.setAmount(amount);
        bonusDailyDo.setIncomeType(incomeType);
        bonusDailyDo.setDate(date);
        bonusDailyDo.setUpdateTime(new Date());

        return bonusDailyDao.insertSelective(bonusDailyDo);
    }

    @Override
    public BigDecimal selectAmount(Integer userId, String incomeType) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("incomeType", incomeType);
        params.put("date", DateUtil.dateToString(new Date())); //只需要年月日

        List<BonusDailyDo> list = bonusDailyDao.select(params);
        if (CollectionUtils.isEmpty(list)) {
            return BigDecimal.ZERO;
        }

        return list.get(0).getAmount() == null ? BigDecimal.ZERO : list.get(0).getAmount();
    }
}
