package com.dce.business.dao.etherenum;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.etherenum.EthereumAccountDo;

public interface IEthereumAccountDao {
    int insert(EthereumAccountDo record);

    int insertSelective(EthereumAccountDo record);

    EthereumAccountDo getEthereumAccount(Map<String, Object> params);

    List<Map<String,Object>> selectEthereumAccountByPage(Map<String,Object> params);
    
    int updateBalance(EthereumAccountDo record);
}