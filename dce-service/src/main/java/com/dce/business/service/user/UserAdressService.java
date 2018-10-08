package com.dce.business.service.user;

import java.util.List;

import com.dce.business.entity.user.UserAddressDo;

public interface UserAdressService {
	
	/**
	 * 按主键删除收货地址
	 * @param addressid
	 * @return
	 */
	int deleteByPrimaryKeyInt(Integer addressid);
	
	// 根据主键获取用户地址信息
	UserAddressDo selectByPrimaryKey(Integer addressid);

	// 获取当前用户所有的地址
	List<UserAddressDo> selectByUserId(Integer userId);

	// 按主键选择更新收货地址
	int updateByPrimaryKeySelective(UserAddressDo record);
	
	// 插人收货地址
	int insertSelective(UserAddressDo record);

}
