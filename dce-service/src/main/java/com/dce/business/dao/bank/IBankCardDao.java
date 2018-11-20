package com.dce.business.dao.bank;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.bank.BankCardDo;

public interface IBankCardDao {
    int deleteByPrimaryKey(Long id);

    int insert(BankCardDo record);

    int insertSelective(BankCardDo record);

    BankCardDo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BankCardDo record);

    int updateByPrimaryKey(BankCardDo record);

    List<BankCardDo> selectBankcard(Map<String, Object> param);

    /** 
     * 查询绑定银行卡分页
     * @param param
     * @return  
     */
    List<BankCardDo> selectBankcardPage(Map<String, Object> param);
}