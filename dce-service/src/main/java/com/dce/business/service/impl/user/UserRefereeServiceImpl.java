package com.dce.business.service.impl.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dce.business.dao.user.IUserRefereeDao;
import com.dce.business.entity.user.UserDo;
import com.dce.business.entity.user.UserRefereeDo;
import com.dce.business.service.user.IUserRefereeService;

@Service("userRefereeService")
public class UserRefereeServiceImpl implements IUserRefereeService  {
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Resource
	private IUserRefereeDao userRefereeDao;
	
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public List<UserDo> selectRefUserByUserLevel(Integer userId, int level) {
		return userRefereeDao.selectRefUserByUserLevel(userId, level);
	}

	@Override
	public List<UserRefereeDo> select(Map<String, Object> params) {
		return userRefereeDao.select(params);
	}

	
	

}
