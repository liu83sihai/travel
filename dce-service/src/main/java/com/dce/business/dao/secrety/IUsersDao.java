package com.dce.business.dao.secrety;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dce.business.entity.secrety.ManagersDo;

/*
 *  @autour lizjmf
 *  迁移代码
 */
@Repository
public interface IUsersDao {

    /**
     * 获取用户详细信息
     * @param userName
     * @return
     */
    ManagersDo getUserDetailsByUserName(String userName);

    /** 
     * 通过手机号查询用户
     * @param mobile
     * @return  
     */
    ManagersDo getUserDetailsByMobile(String mobile);
    
    /**
     * 新增一个用户
     * @param user
     * @return
     */
    int addUser(ManagersDo user);

    /**
     * 修改用户
     * @param user
     * @return
     */
    int updateUser(ManagersDo user);

    /**
     * 用于密码失效，表user_pwd_updateTime
     * 有记录，即修改，更新时间
     * @param user
     * @return
     */
    int updateTimeOfPwd(int id, int userId);

    /**
     * 用于密码失效，表user_pwd_updateTime
     * 没有记录，即刚注册，记录时间
     * @param userId
     * @return
     */
    int addTimeOfPwd(int userId);

    /**
     * 
     * @param pagination
     * @param username
     * @return
     */
    List<ManagersDo> getUsersByPage(Map<String, Object> params);


    /**
     * 根据id获取用户信息
     * @param userId
     * @return
     */
    ManagersDo getUserById(int userId);

    /**
     * 根据id删除用户
     * @param userId
     * @return
     */
    int deleteUser(int userId);

    /**
     * 获取角色下的人员
     * @param page
     * @param roleId
     * @return
     */
    List<ManagersDo> getUsersInRolesByPage(Map<String, Object> params);

    /**
     *
     * @param page
     * @param roleId
     * @return
     */
    List<ManagersDo> getUsersNotInRolesByPage(Map<String, Object> params);

    List<ManagersDo> getCouldFollowOrderPersons(String roleDesc);



	/**
	 * 
	 *
	 * zhangyunhmf
	 *
	 */
    List<ManagersDo> getDeptUsersByPage(Map<String, Object> params);

	/**
	 * 添加用户部门关系
	 * zhangyunhmf
	 *
	 */
    void addDeptUser(ManagersDo user);

	/**
	 * 
	 * 分配了某角色部门用户
	 * zhangyunhmf
	 *
	 */
    List<ManagersDo> getDeptUsersInRolesByPage(Map<String, Object> params);

	/**
	 * 未分配某角色部门用户
	 *
	 * zhangyunhmf
	 *
	 */
    List<ManagersDo> getDeptUsersNotInRolesByPage(Map<String, Object> params);
}
