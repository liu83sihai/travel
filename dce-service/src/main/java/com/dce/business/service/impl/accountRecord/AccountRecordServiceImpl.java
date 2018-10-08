package com.dce.business.service.impl.accountRecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dce.business.common.util.AccountCodeutil;
import com.dce.business.dao.etherenum.EthAccountDetailDoMapper;
import com.dce.business.entity.etherenum.EthAccountDetailDo;
import com.dce.business.entity.etherenum.EthAccountDetailDoExample;
import com.dce.business.service.accountRecord.AccountRecordService;

@Service("accountRecordService")
public class AccountRecordServiceImpl implements AccountRecordService {
	
	@Autowired
	private EthAccountDetailDoMapper ethAccountDetailDao;

	//插入交易流水
	public int insert(EthAccountDetailDo ethAccountDetailDo) {
		int s=0;
		System.out.println("ssssssssss222222===="+ethAccountDetailDo.getAmount());
		if(ethAccountDetailDo != null){
			System.out.println("liu======"+ethAccountDetailDo.getAmount());
			// 账户类型0-个人账户；1-平台账户
			ethAccountDetailDo.setEthaccounttype(0);
			//设置交易流水号
			ethAccountDetailDo.setSerialno(AccountCodeutil.getAccountCode()); 
			System.out.println("getCode"+ethAccountDetailDo.getSerialno());
			s=ethAccountDetailDao.insert(ethAccountDetailDo);
			System.err.println("插入结果-------》》》》》》"+s);
		}
		return s;
	}

	//查询当前用户的所有交易流水
	@Override
	public List<EthAccountDetailDo> selectByUserId(Integer userId) {
		
		EthAccountDetailDoExample example= new EthAccountDetailDoExample();
		example.createCriteria().andUseridEqualTo(userId);
		return ethAccountDetailDao.selectByExample(example);
	}

}
