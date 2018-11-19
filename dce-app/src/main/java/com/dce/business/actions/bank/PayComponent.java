package com.dce.business.actions.bank;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kjtpay.gateway.common.domain.base.RequestBase;
import com.kjtpay.gateway.common.util.security.SecurityService;

@Component("payComponent")
public class PayComponent {
	
	@Resource(name="securityService2")
	private SecurityService securityService2;
	
	/**
	 * 商户签名
	 * @param signData
	 * @return
	 */
    public String sign(RequestBase requestBase) {
		//RSA签名
		return securityService2.sign(requestBase, requestBase.getCharset());
    }
	
    
    /**
	 * 使用json字符串形式加密
	 * @param signType
	 * @param oriText
	 * @param charset
	 * @return
	 */
	public String encrypt( String oriText, String charset){
		//RSA加密
		return securityService2.encrypt(oriText, charset);
	}
	
}