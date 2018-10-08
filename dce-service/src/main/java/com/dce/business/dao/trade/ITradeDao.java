package com.dce.business.dao.trade;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.trade.TradeDo;

public interface ITradeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TradeDo record);

    int insertSelective(TradeDo record);

    TradeDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TradeDo record);

    int updateByPrimaryKeyWithBLOBs(TradeDo record);

    int updateByPrimaryKey(TradeDo record);
    
    List<TradeDo> selectTrade(Map<String, Object> params);
}