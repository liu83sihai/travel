package com.dce.business.service.impl.order;

import com.dce.business.entity.alipaymentOrder.AlipaymentOrder;

public interface AlipaymentOrderService {

	int createAlipayMentOrder(AlipaymentOrder record); // 创建支付宝订单
	
	int updateByPrimaryKeySelective(AlipaymentOrder record);
	
	AlipaymentOrder selectByPrimaryKey(Integer id);
	
	AlipaymentOrder selectByOrderCode(String orderCode);
}
