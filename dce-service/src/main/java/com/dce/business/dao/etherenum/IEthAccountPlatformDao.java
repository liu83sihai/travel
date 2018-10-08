package com.dce.business.dao.etherenum;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.etherenum.EthAccountPlatformDo;

public interface IEthAccountPlatformDao {
    List<EthAccountPlatformDo> select(Map<String, Object> params);

    /**
     * 失效原有的账户
     */
	void invalidPlatformAccount();

	/**
	 * 新增平台账户
	 * @param newPlatformAccount
	 */
	void insertPlatformAccount(EthAccountPlatformDo newPlatformAccount);
	
	 
	/** 
	 * 更新操作
	 * @param record
	 * @return  
	 */
	int updateByPrimaryKeySelective(EthAccountPlatformDo record);
	
	
	List<EthAccountPlatformDo> selectByPage(Map<String, Object> params);
}