package com.dce.business.common.wxPay.util;

import java.util.Date;
import java.util.Random;

import com.dce.business.common.util.DateUtil;

public class WXUtil {
	
	public static String getNonceStr() {
		Random random = new Random();
		return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "GBK");
	}

	public static String getTimeStamp() {
		return DateUtil.YYYYMMDDHHMMSS.format(new Date());
	}
	
}
