package com.dce.business.service.third;

import java.util.List;
import java.util.Map;

import com.dce.business.common.result.Result;
import com.dce.business.entity.etherenum.EthAccountPlatformDo;
import com.dce.business.entity.page.PageDo;


public interface IEthereumPlatformService {

	/**
	 * 更改平台账户
	 */
	void changePlatformAccount(String newAccount);
	
	Result<?> createPlatformAccount(String account, String password);

	/**
	 * 获取平台账户
	 * @return
	 */
	EthAccountPlatformDo getPlatformAccount();

	/**
	 *  查询平台帐户
	 * @param params
	 * @return
	 */
	EthAccountPlatformDo queryPlatformAccount(Map<String, Object> params);
	
	List<EthAccountPlatformDo> selectList();
	
	Result<?> setDefault(String no);

	/**
	 * 平台以太坊帐户 转出
	 * @param accountNo
	 * @param receiveAddress
	 * @param amount
	 * @param pwd
	 * @return
	 */
	Result<?> sendPlatform(String accountNo, String receiveAddress,
			String amount, String pwd);
	
	PageDo<EthAccountPlatformDo> selectEthAccountPlatByPage(PageDo<EthAccountPlatformDo> page,Map<String, Object> params);
}
