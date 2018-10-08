package com.dce.business.service.impl.award;

import com.dce.business.entity.order.Order;
import com.dce.business.entity.user.UserDo;

/**
 * 奖金计算的接口
 * @author harry
 *
 */
public interface IAwardCalculator  {
	
	/**
	 * 计算奖励的方法
	 * @param buyUserId 购买者
	 * @param orderId   购买订单
	 * @return
	 */
	public  void doAward(UserDo buyer, Order order);
	
}
