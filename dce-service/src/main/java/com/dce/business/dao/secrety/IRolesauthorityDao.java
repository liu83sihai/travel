/**
 * @fileName XXXX.java
 * @auther yepeng
 * @version 1.0
 * @date   2017-08-23 17:59:31
 */

 
package com.dce.business.dao.secrety;

/**
 * @author yepeng
 * @TODO
 */


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dce.business.entity.secrety.AuthoritiesDo;
import com.dce.business.entity.secrety.RolesauthorityDo;

@Repository
public interface IRolesauthorityDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public RolesauthorityDo getById(Long id);
	
	/**
	 *根据条件查询列表
	 */
	public List<RolesauthorityDo> selectRolesauthority(Map<String, Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateRolesauthorityById(RolesauthorityDo newRolesauthorityDo);
	
	/**
	 * 新增
	 */
	public int addRolesauthority(RolesauthorityDo newRolesauthorityDo);
	
	/**
	 * 删除
	 */
	public int deleteById(Integer id);
	
	/*
	 * 删除角色对应权限
	 */
	public int deleteRolesauthority(RolesauthorityDo newRolesauthorityDo);



	public List<AuthoritiesDo> getAuthoritiesInRolesPage(Map<String, Object> map);

	public List<AuthoritiesDo> getAuthoritiesNotInRolesPage(Map<String, Object> map);


}
