package com.dce.business.service.trade;

import java.util.List;

import com.dce.business.entity.trade.TradeDo;

public interface ITradeService {

    /**
     * 查询成交记录 
     * @param userId
     * @return  
     */
    List<TradeDo> getTradeRecords(Integer userId);
}
