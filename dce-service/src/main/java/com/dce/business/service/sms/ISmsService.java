package com.dce.business.service.sms;

import com.dce.business.common.result.Result;

public interface ISmsService {

	/**
	 * 发送短信验证码
	 * @param userId
	 * @param page
	 */
	void send(Integer userId, String page);

	/**
	 * 验证短信验证码
	 * @param userId
	 * @param page
	 * @param smsCode
	 * @return
	 */
	Result<?> checkSms(Integer userId, String page, String smsCode);

}
