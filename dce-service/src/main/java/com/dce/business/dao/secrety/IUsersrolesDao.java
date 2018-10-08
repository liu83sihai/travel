/**
 * @fileName XXXX.java
 * @auther yepeng
 * @version 1.0
 * @date   2017-08-23 17:59:01
 */

 
package com.dce.business.dao.secrety;

/**
 * @author yepeng
 * @TODO
 */


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dce.business.entity.secrety.UsersrolesDo;

@Repository
public interface IUsersrolesDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public UsersrolesDo getById(Long id);
	
	/**
	 *根据条件查询列表
	 */
	public List<UsersrolesDo> selectUsersroles(Map<String, Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateUsersrolesById(UsersrolesDo newUsersrolesDo);
	
	/**
	 * 新增
	 */
	public int addUsersroles(UsersrolesDo newUsersrolesDo);
	
	/**
	 * 删除
	 */
	public int deleteById(Integer id);
	
	/**
	 * 删除用户角色对应
	 */
	public int deleteUserRoles(UsersrolesDo ur);
	
	public int countUsersroles(UsersrolesDo ur);

}
