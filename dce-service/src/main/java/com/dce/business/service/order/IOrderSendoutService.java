/*
 * Powered By  huangzl QQ: 272950754
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2018
 */

package com.dce.business.service.order;

/**
 * 订单发货
 */

import java.util.List;
import java.util.Map;

import com.dce.business.entity.order.OrderSendOut;
import com.dce.business.entity.page.PageDo;


public interface IOrderSendoutService{

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public OrderSendOut getById(Long id);
	
	/**
	 *根据条件查询列表
	 */
	public List<OrderSendOut> selectOrderSendout(OrderSendOut orderSendOut);
	
	/**
	 * 更新
	 */
	public int  updateOrderSendoutById(OrderSendOut newOrderSendOut);
	
	/**
	 * 新增
	 */
	public int addOrderSendout(OrderSendOut newOrderSendout);
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<OrderSendOut> selectOrderSendByPage(Map<String, Object> param, PageDo<OrderSendOut> page);
	
	
	/**
	 * 删除
	 */
	public int deleteById(Long id);
}
