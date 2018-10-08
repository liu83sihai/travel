package com.dce.business.common.util;

import org.apache.commons.codec.binary.Base64;

public class PayUtil {
	
	
	//private static Logger logger = Logger.getLogger(PayUtil.class);
    
    /**
     * 生成支付码
     * @param request
     * @param bizCode 业务code
     */
    public static String getPayCode(String qrCode) {
        return new String(Base64.encodeBase64(qrCode.getBytes()));
    }

}
