package com.dce.business.common.util;

import java.math.BigDecimal;

public class NumberUtil {
	
	public static BigDecimal formatterBigDecimal(BigDecimal bgd){
		if(null == bgd){return BigDecimal.ZERO;}
		
		return bgd.setScale(6, BigDecimal.ROUND_HALF_UP);
	}
	
	public static void main(String[] args) {
		System.out.println(formatterBigDecimal(new BigDecimal("0.000030")));
	}

}
