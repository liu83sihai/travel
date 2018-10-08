package com.dce.business.service.order;

import java.util.List;

import com.dce.business.entity.order.OrderAddressDo;

public interface IOrderAdressService {
	
	/**
	 * 按主键删除收货地址
	 * @param addressid
	 * @return
	 */
	int deleteByPrimaryKeyInt(Integer addressid);
	
	// 根据主键获取用户地址信息
	OrderAddressDo selectByPrimaryKey(Integer addressid);

	// 获取当前用户所有的地址
	List<OrderAddressDo> selectByUserId(Integer userId);

	// 按主键选择更新收货地址
	int updateByPrimaryKeySelective(OrderAddressDo record);
	
	// 插人收货地址
	int insertSelective(OrderAddressDo record);

}
