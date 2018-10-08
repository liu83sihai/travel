package com.dce.business.dao.etherenum;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.etherenum.EthereumTransInfoDo;

public interface IEthereumTransInfoDao {
    int deleteByPrimaryKey(Long id);

    int insert(EthereumTransInfoDo record);

    int insertSelective(EthereumTransInfoDo record);

    EthereumTransInfoDo selectByPrimaryKey(Long id);
    

    int updateByPrimaryKeySelective(EthereumTransInfoDo record);
    
    int updateByPrimaryByStatus(Map<String, Object> params);

    
    int updateByWithdrawId(EthereumTransInfoDo record);

    int updateByPrimaryKey(EthereumTransInfoDo record);
    
    EthereumTransInfoDo select(Map<String, Object> params);
    
    List<EthereumTransInfoDo> selectParam(Map<String, Object> params);

    /**
     * 查询以太坊流水
     * 
     * @param params
     * @return
     */
	List<Map<String, Object>> queryEthTrans(Map<String, Object> params);
	
	int queryEthTransCount(Map<String, Object> params);
	
	/**
	 * 分页查询
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectEthTransByPage(Map<String, Object> params);
}