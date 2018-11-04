package com.dce.business.service.dict;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.dce.business.entity.dict.LoanDictDo;
import com.dce.business.entity.dict.LoanDictDtlDo;
import com.dce.business.entity.page.PageDo;

public interface ILoanDictService {

    /**
     * 查询单个字典明细信息<br/>
     * 并且状态有效
     * 
     * @param dictCode 字典编码
     * @param dictDtlCode 字典明细编码
     * @return
     */
    LoanDictDtlDo getLoanDictDtl(String dictCode, String dictDtlCode);

    LoanDictDo getDictById(long dictId);

    /**
     * 查询字典列表
     * @param searchItem
     * @return
     */
    public PageDo<LoanDictDo> queryListPage(Map<String, Object> searchItem, PageDo<LoanDictDo> page) ;
    /**
     * 查询单个字典明细信息<br/>
     * 并且状态有效
     * 
     * @param dictId 字典主表的id
     * @param dictDtlCode 字典明细编码
     * @return
     */
    LoanDictDtlDo getLoanDictDtl(Long dictId, String dictDtlCode);

    /**
     * 通过code查询字典t_loan_dict 
     * 并且状态有效
     * 
     * @param dictCode 字典编码
     * @return
     */
    LoanDictDo getLoanDict(String dictCode);
    
    LoanDictDtlDo getLoanDictDtl(String dictCode);

    List<LoanDictDtlDo> queryDictDtlListByDictCode(String dictCode);

    List<LoanDictDtlDo> getDictDetailByDictId(Long dictId);


    /**
     * 更新数据字典明细
     * @param dictDtlDo
     * @return
     */
    int updateDictDtl(LoanDictDtlDo dictDtlDo);
   
    /**
     * 删除明细
     * @param id
     * @return
     */
    int deleteByDictId(Long id);

    /**
     * 新增数据字典明细
     * @param dictDtlDo
     * @return
     */
    int addDictDtl(LoanDictDtlDo dictDtlDo);

    int addDict(LoanDictDo loanDictDo);

    int updateDict(LoanDictDo loanDictDo);

    List<LoanDictDtlDo> getDictDetail(Map<String, Object> param);
    
	LoanDictDtlDo getLoanDictDtlByDictDtlCode(String dictDtlCode);
	
	void updateDictDtlRemark(List<LoanDictDtlDo> list);
	
	/**
     * 查询所有字典记录及其子记录
     * @return
     */
    List<LoanDictDo> selectLoanDictLinkDtl();
    
    /**
     * 人民币转dce
     * @param rmb
     * @return
     */
    BigDecimal rmb2Dce(BigDecimal rmb);
}
