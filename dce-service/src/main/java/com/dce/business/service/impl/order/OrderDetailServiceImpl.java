package com.dce.business.service.impl.order;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dce.business.dao.order.OrderDetailMapper;
import com.dce.business.entity.order.OrderDetail;
import com.dce.business.service.order.OrderDetailService;

@Service("orderDetailService")
public class OrderDetailServiceImpl implements OrderDetailService {
	
	@Resource
	private OrderDetailMapper orderDetailDao;

//	@Override
//	public int insert(OrderDetail orderDetail) {
//		return 0;
//	}
//
//	@Override
//	public int insertOrderDetailByBatch(List<OrderDetail> orderDetailList) {
//		return 0;
//	}

	@Override
	public List<OrderDetail> selectByOrderId(Integer orderId) {
		return orderDetailDao.selectByOrderId(orderId);
	}

}
