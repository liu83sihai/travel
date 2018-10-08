package com.dce.business.common.util;

import java.util.Date;
import java.util.Random;

/**
 * 交易流水单号帮助类，用于生成流水单号
 * @author Administrator
 *
 */
public class AccountCodeutil {

	/**
	 * 产生交易流水号
	 * @return
	 */
	public static String getAccountCode(){
		StringBuffer sb = new StringBuffer("xxjt");
		sb.append(DateUtil.YYYYMMDDHHMMSS.format(new Date())).append(random());
		return sb.toString();
	}
	
	/**
	 * 随机产生两位数
	 * @return
	 */
	public static String random(){
		Random random = new Random();
		return random.nextInt(100)+"";
	}
}
