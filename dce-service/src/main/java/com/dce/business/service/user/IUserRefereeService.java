package com.dce.business.service.user;

import java.util.List;

import com.dce.business.entity.user.UserDo;

public interface IUserRefereeService {

	List<UserDo> selectRefUserByUserLevel(Integer userId, int level);

}
