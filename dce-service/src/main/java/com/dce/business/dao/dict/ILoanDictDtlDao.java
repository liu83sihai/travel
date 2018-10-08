package com.dce.business.dao.dict;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.dict.LoanDictDtlDo;

public interface ILoanDictDtlDao {
    int deleteByPrimaryKey(Long id);

    int insert(LoanDictDtlDo record);

    int insertSelective(LoanDictDtlDo record);

    LoanDictDtlDo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanDictDtlDo record);

    int updateByPrimaryKey(LoanDictDtlDo record);

    /**
     * 查询明细
     * 
     * @param params
     * @return
     */
    List<LoanDictDtlDo> selectLoanDictDtl(Map<String, Object> params);

	List<LoanDictDtlDo> queryDictDtlList(String dictCode);
	
	int batchUpdateRemark(Map<String, Object> params);
}