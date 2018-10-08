package com.dce.business.service.impl.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dce.business.dao.user.IUserParentDao;
import com.dce.business.entity.user.UserParentDo;
import com.dce.business.service.user.IUserParentService;
@Service("userParentService")
public class UserParentServiceImpl implements IUserParentService {

	@Resource
	IUserParentDao userParentDao;
	
	@Override
	public List<UserParentDo> select(Map<String, Object> param) {
		return userParentDao.select(param);
	}
	
	@Override
	public List<Map<String, Object>> TeamDetails(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return userParentDao.TeamDetails(params);
	}

}
