package com.dce.business.dao.order;

import com.dce.business.entity.order.OrderSendOut;
import com.dce.business.entity.order.OrderSendOutExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 订单发货
 * @author Administrator
 *
 */
public interface OrderSendOutMapper {

	long countByExample(OrderSendOutExample example);

	int deleteByExample(OrderSendOutExample example);

	int deleteByPrimaryKey(Long id);

	int insert(OrderSendOut record);

	int insertSelective(OrderSendOut record);

	List<OrderSendOut> selectByExample(OrderSendOut orderSendOut);

	OrderSendOut selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") OrderSendOut record, @Param("example") OrderSendOutExample example);

	int updateByExample(@Param("record") OrderSendOut record, @Param("example") OrderSendOutExample example);

	int updateByPrimaryKeySelective(OrderSendOut record);

	int updateByPrimaryKey(OrderSendOut record);
	
	List<OrderSendOut> selectOrderSendByPage(Map<String, Object> paraMap);
}