package com.dce.business.service.accountRecord;

import java.util.List;

import com.dce.business.entity.etherenum.EthAccountDetailDo;

/**
 * 交易流水类
 * @author Administrator
 *
 */
public interface AccountRecordService {

	 //插入单条交易流水记录
	 int insert(EthAccountDetailDo record);
	 
	 //查询所有的交易记录
	 List<EthAccountDetailDo> selectByUserId(Integer userId);
}
