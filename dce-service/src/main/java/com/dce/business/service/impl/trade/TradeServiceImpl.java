package com.dce.business.service.impl.trade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dce.business.dao.trade.ITradeDao;
import com.dce.business.entity.trade.TradeDo;
import com.dce.business.service.trade.ITradeService;

/** 
 * 交易
 * @author parudy
 * @date 2018年3月25日 
 * @version v1.0
 */
@Service("tradeService")
public class TradeServiceImpl implements ITradeService {
    @Resource
    private ITradeDao tradeDao;

    @Override
    public List<TradeDo> getTradeRecords(Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userid", userId);
        return tradeDao.selectTrade(params);
    }
}
