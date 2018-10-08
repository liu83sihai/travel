package com.dce.business.dao.bank;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.bank.BankDo;

public interface IBankDao {
    int deleteByPrimaryKey(Integer id);

    int insert(BankDo record);

    int insertSelective(BankDo record);

    BankDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BankDo record);

    int updateByPrimaryKey(BankDo record);
    
    List<BankDo> selectBank(Map<String, Object> params);
}