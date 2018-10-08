package com.dce.business.service.bonus;

import java.math.BigDecimal;
import java.util.Date;

public interface IBonusDailyService {

    /**
     * 更新每日奖金总额 
     * @param userId 用户id
     * @param incomeType 流水类型
     * @param date 发生日期
     * @param amount 发生金额
     * @return  
     */
    int updateAmount(Integer userId, String incomeType, Date date, BigDecimal amount);
    
    /** 
     * 查询当日量碰总额
     * @param userId
     * @param incomeType
     * @return  
     */
    BigDecimal selectAmount(Integer userId, String incomeType);

}
