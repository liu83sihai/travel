package com.test;


import org.junit.Test;

import com.dce.business.common.util.AESUtil;
import com.dce.business.common.util.KeyUtil;



public class DataEncryptTest {

	@Test
	public void encrypt(){
		try {
			System.out.println(AESUtil.encryptToStr("123456", KeyUtil.getKey()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
