package com.dce.business.service.impl.order.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dce.business.entity.order.Order;
import com.dce.business.service.order.IOrderService;
import com.dce.test.BaseTest;

public class OrderServiceTest  extends BaseTest{
	
	@Autowired
	private IOrderService orderService;
	
	@Test
	public void testOrderPay(){
		String gmtPayment = "2018-08-23";
		String orderCode = "XX3820180823135359745";
		orderService.orderPay(orderCode , gmtPayment);
	}
	
	@Test
	public void testCopyAddress(){
		String orderCode = "";
		Order order = orderService.selectByOrderCode(orderCode);
		orderService.mainOrderAddress(order);
	}

}
