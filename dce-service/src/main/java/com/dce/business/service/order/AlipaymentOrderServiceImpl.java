package com.dce.business.service.order;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dce.business.dao.alipaymentOrder.AlipaymentOrderMapper;
import com.dce.business.entity.alipaymentOrder.AlipaymentOrder;
import com.dce.business.service.impl.order.AlipaymentOrderService;

@Service("alipaymentOrderService")
public class AlipaymentOrderServiceImpl implements AlipaymentOrderService {

	@Resource 
	private AlipaymentOrderMapper alipaymentOrderDao;
	
	@Override
	public int createAlipayMentOrder(AlipaymentOrder record) {

		return alipaymentOrderDao.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(AlipaymentOrder record) {

		return alipaymentOrderDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public AlipaymentOrder selectByPrimaryKey(Integer id) {

		return alipaymentOrderDao.selectByPrimaryKey(id);
	}

	@Override
	public AlipaymentOrder selectByOrderCode(String orderCode) {

		return alipaymentOrderDao.selectByOrderCode(orderCode);
	}

}
