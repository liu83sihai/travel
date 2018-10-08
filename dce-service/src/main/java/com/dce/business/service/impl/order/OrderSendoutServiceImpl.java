package com.dce.business.service.impl.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dce.business.common.util.Constants;
import com.dce.business.dao.order.OrderSendOutMapper;
import com.dce.business.entity.order.OrderSendOut;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.order.IOrderSendoutService;

/**
 * @author 
 * @version 1.0
 * @since 1.0
 */

@Service("orderSendoutService")
public class OrderSendoutServiceImpl implements IOrderSendoutService {

	@Autowired
	private OrderSendOutMapper orderSendOutMapper;

	@Override
	public OrderSendOut getById(Long id) {

		return orderSendOutMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<OrderSendOut> selectOrderSendout(OrderSendOut orderSendOut) {

		return orderSendOutMapper.selectByExample(orderSendOut);
	}

	@Override
	public int updateOrderSendoutById(OrderSendOut newOrderSendOut) {

		return orderSendOutMapper.updateByPrimaryKey(newOrderSendOut);
	}

	@Override
	public int addOrderSendout(OrderSendOut newOrderSendout) {

		return orderSendOutMapper.insertSelective(newOrderSendout);
	}

	/**
	 * 分页查询
	 */
	@Override
	public PageDo<OrderSendOut> selectOrderSendByPage(Map<String, Object> param, PageDo<OrderSendOut> page) {

		if(param == null){
			param = new HashMap<String,Object>();
		}
		param.put(Constants.MYBATIS_PAGE, page);
		List<OrderSendOut> list = orderSendOutMapper.selectOrderSendByPage(param);
		page.setModelList(list);
		
		return page;
	}

	@Override
	public int deleteById(Long id) {

		return orderSendOutMapper.deleteByPrimaryKey(id);
	}

}
