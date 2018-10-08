package com.dce.business.service.impl.sms;

public interface DceSmsService {

	public abstract String smsSend( String content,
			String mobile) throws Throwable;

}