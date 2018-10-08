/**
 * @fileName XXXX.java
 * @auther yepeng
 * @version 1.0
 * @date   2017-08-23 17:57:43
 */

 
package com.dce.business.dao.secrety;

/**
 * @author yepeng
 * @TODO
 */


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dce.business.entity.secrety.RolesDo;

@Repository
public interface IRolesDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public RolesDo getById(Long id);
	
	/**
	 *根据条件查询列表
	 */
	public List<RolesDo> selectRoles(Map<String, Object> parameterMap);

	/**
	 * 更新
	 */
	public int  updateRolesById(RolesDo newRolesDo);

	/**
	 * 新增
	 */
	public int addRoles(RolesDo newRolesDo);

	/**
	 * 删除
	 */
	public int deleteById(Long id);

	//public List<RolesDo> getRolesByPage(String roleName);

	public RolesDo getOneRoleById(int roleId);

	public int deleteRole(int roleId);

	public List<RolesDo> getRolesInAuthorityByPage(Map<String, Object> params);

	public List<RolesDo> getRolesNotInAuthorityByPage(Map<String, Object> params);


	//根据角色名称模糊查询角色列表
	public List<RolesDo> getRolesByPage(Map<String, Object> params);

	//根据userId关联usersroles和managers查询角色（查询某个用户的所有角色）
	public List<RolesDo> getRolesByUserId(Map<String, Object> params);

	/**
	 * 查找部门角色
	 * zhangyunhmf
	 *
	 */
    public List<RolesDo> getDeptRolesByPage(Map<String, Object> params);
	
}











