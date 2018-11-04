package com.dce.business.dao.order;

import com.dce.business.entity.order.OrderDetail;
import com.dce.business.entity.order.OrderDetailExample;
import com.dce.business.entity.order.OrderPayDetail;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderDetailMapper {

	long countByExample(OrderDetailExample example);

	int deleteByExample(OrderDetailExample example);

	int deleteByPrimaryKey(Integer orderdetailid);

	int insert(OrderDetail record);
	
	//根据订单id查询订单明细
	List<OrderDetail> selectByOrderId(Integer orderId);

	int insertSelective(OrderDetail record);

	List<OrderDetail> selectByExample(OrderDetailExample example);

	OrderDetail selectByPrimaryKey(Integer orderdetailid);

	int updateByExampleSelective(@Param("record") OrderDetail record, @Param("example") OrderDetailExample example);

	int updateByExample(@Param("record") OrderDetail record, @Param("example") OrderDetailExample example);

	int updateByPrimaryKeySelective(OrderDetail record);

	int updateByPrimaryKey(OrderDetail record);

	/**
	 * 支付明细
	 * @param detail
	 */
	void insertPayDetail(OrderPayDetail detail);

	/**
	 * 删除支付明细
	 * @param orderid
	 */
	void deletePayDetailByOrderId(Integer orderid);
}