package com.dce.business.service.impl.order;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dce.business.dao.order.IOrderAddressDao;
import com.dce.business.entity.order.OrderAddressDo;
import com.dce.business.service.order.IOrderAdressService;

@Service("orderAdressService")
public class OrderAdressServiceImpl implements IOrderAdressService {

	@Resource
	private IOrderAddressDao orderAdressDao;

	/**
	 * 根据id获取地址
	 */
	@Override
	public OrderAddressDo selectByPrimaryKey(Integer addressid) {

		return orderAdressDao.selectByPrimaryKey(addressid);
	}

	/**
	 * 获取当前用户的所有地址（查询）
	 */
	@Override
	public List<OrderAddressDo> selectByUserId(Integer userId) {

		return orderAdressDao.selectByUserId(Long.valueOf(userId));
	}




	/**
	 * 修改收货地址
	 */
	@Override
	public int updateByPrimaryKeySelective(OrderAddressDo record) {

		return orderAdressDao.updateByPrimaryKeySelective(record);
	}

	/**
	 * 添加收货地址
	 */
	@Override
	public int insertSelective(OrderAddressDo record) {

		return orderAdressDao.insertSelective(record);
	}



	/**
	 * 按主键删除收货地址
	 */
	@Override
	public int deleteByPrimaryKeyInt(Integer addressid) {
		
		return orderAdressDao.deleteByPrimaryKey(addressid);
	}

}
