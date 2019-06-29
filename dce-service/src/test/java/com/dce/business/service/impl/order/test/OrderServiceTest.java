package com.dce.business.service.impl.order.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.dce.business.entity.order.Order;
import com.dce.business.entity.order.OrderDetail;
import com.dce.business.entity.order.OrderPayDetail;
import com.dce.business.service.order.IOrderService;
import com.dce.test.BaseTest;

public class OrderServiceTest  extends BaseTest{
	
	@Autowired
	private IOrderService orderService;
	
//	@Test
//	public void testOrderPay(){
//		String gmtPayment = "2018-08-23";
//		String orderCode = "XX3820180823135359745";
//		orderService.orderPay(orderCode , gmtPayment);
//	}
	@Test
	public void testInsertOrder() throws Exception{
		String gmtPayment = "2018-08-23";
		String orderCode = "XX79220190611234230322";
		Order order = orderService.selectByOrderCode(orderCode);
		Order newOrder = new Order();
		BeanUtils.copyProperties(order, newOrder);
		newOrder.setOrderid(null);
		newOrder.setOrdercode(null);
		newOrder.setTotalprice(null);
		newOrder.setGoodsprice(null);
		newOrder.setPostage(null);
		newOrder.setCashAmt(null);
		List<OrderPayDetail> payLst = new ArrayList<OrderPayDetail>();
		List<OrderDetail> chooseGoodsLst = new ArrayList<OrderDetail>();
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setGoodsId(1102);
		orderDetail.setQuantity(2);
		chooseGoodsLst.add(orderDetail);
		
		OrderPayDetail p = new OrderPayDetail();
		p.setAccountType("wallet_money");
		p.setPayAmt(new BigDecimal("508"));
		//payLst.add(p);
		
		orderService.saveOrder(payLst, chooseGoodsLst, newOrder, null, null);
	}
	
//	@Test
//	public void testCopyAddress(){
//		String orderCode = "";
//		Order order = orderService.selectByOrderCode(orderCode);
//		orderService.mainOrderAddress(order);
//	}

}
