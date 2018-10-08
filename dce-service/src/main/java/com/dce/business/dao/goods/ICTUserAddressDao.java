package com.dce.business.dao.goods;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.goods.CTUserAddressDo;

public interface ICTUserAddressDao {

	int insert(CTUserAddressDo address);
	
	int update(CTUserAddressDo address);
	
	int deleteByPrimaryKey(Long addressId);
	
	List<CTUserAddressDo> select(Map<String,Object> params);
	
	CTUserAddressDo selectByPrimaryKey(Integer addressId);
}
