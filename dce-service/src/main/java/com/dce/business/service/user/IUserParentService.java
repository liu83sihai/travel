package com.dce.business.service.user;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.user.UserParentDo;

public interface IUserParentService {

	/**
	 * 查询 parent table
	 * @param param
	 * @return
	 */
	List<UserParentDo> select(Map<String, Object> param);
	
	/**
     * 团队成员详情（分级别）
     * @param params
     * @return
     */
    List<Map<String,Object>> TeamDetails(Map<String,Object> params);

}
