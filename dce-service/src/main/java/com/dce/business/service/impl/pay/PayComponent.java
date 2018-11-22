package com.dce.business.service.impl.pay;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kjtpay.gateway.common.domain.base.RequestBase;
import com.kjtpay.gateway.common.util.security.SecurityService;

@Component("payComponent")
public class PayComponent {
	
	@Resource(name="securityService")
	private SecurityService rsaSecurityService;
	
	/**
	 * 商户签名
	 * @param signData
	 * @return
	 */
    public String sign(RequestBase requestBase) {
		//RSA签名
		return rsaSecurityService.sign(requestBase, requestBase.getCharset());
    }
    
	/**
	 * 商户签名
	 * @param signData
	 * @return
	 */
    public String sign(Map<String,String> param) {
		//RSA签名
		return rsaSecurityService.sign(param, "UTF-8");
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
		return rsaSecurityService.encrypt(oriText, charset);
	}
	
}