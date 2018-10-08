package com.dce.business.service.award;

/**
 * 计算奖励service
 */

public interface IAwardService {
	
	/**
	 * 计算奖励的方法
	 * @param buyUserId 购买者
	 * @param orderId   购买订单
	 * @return
	 */
	public void calcAward(Integer buyUserId, Integer orderId);
}
