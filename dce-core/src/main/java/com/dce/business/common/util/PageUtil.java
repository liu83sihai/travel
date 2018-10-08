package com.dce.business.common.util;


public class PageUtil {
	
	public static int getEndRow(int startPage,int pageSize){
		if(startPage>1){
			return (startPage - 1) * pageSize;
	    } else {
	        return 0;
		}
	}
}
