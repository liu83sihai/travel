package com.dce.business.service.pay;

import com.dce.business.common.result.Result;

public interface IKJTPayService {

	/**
	 * 获取短信验证码
	 * @return
	 */
	Result<?> executeGetBankCardCode(String idno, String realName, String mobile,String cardNo) throws Throwable;

	/**
	 * 验证短信验证码
	 * @return
	 */
	Result<?> executeCheckCode() throws Throwable;
	
	

}
