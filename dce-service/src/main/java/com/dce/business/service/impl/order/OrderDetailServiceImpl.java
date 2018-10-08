package com.dce.business.service.impl.order;

import java.util.List;

import javax.annotation.Resource;

import com.dce.business.dao.order.OrderDetailMapper;
import com.dce.business.entity.order.OrderDetail;
import com.dce.business.service.order.OrderDetailService;

public class OrderDetailServiceImpl implements OrderDetailService {
	
	@Resource
	private OrderDetailMapper orderDetailDao;

	@Override
	public int insert(OrderDetail orderDetail) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertOrderDetailByBatch(List<OrderDetail> orderDetailList) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<OrderDetail> selectByOrderId(Integer orderId) {
		// TODO Auto-generated method stub
		return orderDetailDao.selectByOrderId(orderId);
	}

}
