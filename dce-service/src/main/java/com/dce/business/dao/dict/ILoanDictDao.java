package com.dce.business.dao.dict;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.dict.LoanDictDo;

public interface ILoanDictDao {
    int deleteByPrimaryKey(Long id);

    int insert(LoanDictDo record);

    int insertSelective(LoanDictDo record);

    LoanDictDo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanDictDo record);

    int updateByPrimaryKey(LoanDictDo record);
    
    List<LoanDictDo> queryListPage(Map<String,Object> searchItem );

    /**
     * 查询数据字典
     * 
     * @param params
     * @return
     */
    List<LoanDictDo> selectLoanDict(Map<String, Object> params);
    
    /**
     * 查询所有字典记录及其子记录
     * @return
     */
    List<LoanDictDo> selectLoanDictLinkDtl();
}