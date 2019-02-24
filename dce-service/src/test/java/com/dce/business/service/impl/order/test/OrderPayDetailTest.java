package com.dce.business.service.impl.order.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.dce.business.dao.order.OrderDetailMapper;
import com.dce.business.entity.order.OrderPayDetail;
import com.dce.test.BaseTest;

public class OrderPayDetailTest  extends BaseTest{
	
	@Resource
	private OrderDetailMapper orderDetailDao;
	
	@Test
	public void testOrderPay(){
		List<OrderPayDetail> payLst= orderDetailDao.selectPayDetailByOrderId(1513);
		System.out.println(payLst.get(0));
	}

}
