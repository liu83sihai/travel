package com.dce.business.service.user;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.user.UserDo;
import com.dce.business.entity.user.UserRefereeDo;

public interface IUserRefereeService {

	List<UserDo> selectRefUserByUserLevel(Integer userId, int level);
	
	
	/**
	 * 查询团队成员
	 */
	List<UserRefereeDo> select(Map<String, Object> params);
	

}
