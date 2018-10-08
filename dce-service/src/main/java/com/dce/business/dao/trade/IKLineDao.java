package com.dce.business.dao.trade;

import java.util.Map;

import com.dce.business.entity.trade.KLineDo;
import com.dce.business.entity.trade.MADo;

public interface IKLineDao {
    int deleteByPrimaryKey(Long id);

    int insert(KLineDo record);

    int insertSelective(KLineDo record);

    KLineDo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(KLineDo record);

    int updateByPrimaryKey(KLineDo record);
    
    KLineDo selectQty(Map<String, Object> params);
    
    KLineDo selectPrice(Map<String, Object> params);
    
    MADo selectMA(Map<String, Object> params);
}