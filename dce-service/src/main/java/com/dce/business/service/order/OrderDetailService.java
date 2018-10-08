package com.dce.business.service.order;

import java.util.List;

import com.dce.business.entity.order.OrderDetail;

/**
 * 订单明细
 * 
 * @author Administrator
 *
 */
public interface OrderDetailService {

	// 插入订单明细
	int insert(OrderDetail orderDetail);

	// 批量插入订单明细
	int insertOrderDetailByBatch(List<OrderDetail> orderDetailList);

	// 根据订单id查询订单明细
	List<OrderDetail> selectByOrderId(Integer orderId);

}
