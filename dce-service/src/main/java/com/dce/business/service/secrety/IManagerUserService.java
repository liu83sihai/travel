package com.dce.business.service.secrety;


import java.util.List;

import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.secrety.ManagersDo;
import com.dce.business.entity.secrety.RolesDo;
import com.dce.business.entity.secrety.RolesauthorityDo;
import com.dce.business.entity.secrety.UsersrolesDo;

public interface IManagerUserService {

    /**
     * 新增一个用户
     * @param user
     * @return
     */
    int addUsers(ManagersDo user);


    
    /**
     * 获取用户列表
     * @param PageDo
     * @param username
     * @param nickName
     * @return
     */
    PageDo<ManagersDo> getManagersByPage(PageDo<ManagersDo> page, String username);
    PageDo<ManagersDo> getManagersByPage(PageDo<ManagersDo> page, String username, String nickName);

    /**
     * 根据id获取用户
     * @param userId
     * @return
     */
    ManagersDo getUserById(int userId);

    /** 
     * 通过用户名查询用户
     * @param username
     * @return  
     */
    ManagersDo getUser(String username);

    /** 
     * 判断是否白名单
     * @param username
     * @return  
     */
    boolean isWhiteList(String username);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    int deleteOneUser(int userId);

    /**
     * 获取一个角色中的用户（或者不在角色中的用户）
     * @param page
     * @param roleId
     * @param flag true表示在角色中的用户，false表示不在角色中的用户
     * @return
     */
    PageDo<ManagersDo> getManagersInOrNotInRolesByPage(PageDo<ManagersDo> page, int roleId, boolean flag);

    /**
     * 根据用户名和昵称，获取一个角色中的用户（或者不在角色中的用户）
     * @param page
     * @param roleId
     * @param flag
     * @param userName
     * @param nickName
     * @return
     */
    PageDo<ManagersDo> getManagersInOrNotInRolesByPage(PageDo<ManagersDo> page, int roleId, boolean flag, String userName, String nickName);

    /**
     * 获取角色列表
     * @param PageDo
     * @param roleName
     * @return
     */
    PageDo<RolesDo> getRolesByPage(PageDo<RolesDo> page, String roleName);

    /**
     * 根据id获取角色
     * @param roleId
     * @return
     */
    RolesDo getOneRoleById(int roleId);

    /**
     * 删除一个角色
     * @param roleId
     * @return
     */
    int deleteOneRole(int roleId);

    /**
     * 新增或者一个角色
     * @param role
     * @return
     */
    int updateOneRole(RolesDo role);

    /**
     * 新增用户角色关系
     * @param ur
     * @return
     */
    int addUsersRoles(UsersrolesDo ur);

    /**
     * 删除用户角色关系
     * @param ur
     * @return
     */
    int deleteUsersRoles(UsersrolesDo ur);

    /**
     * 获取在权限下的角色
     * @param page
     * @param authId
     * @return
     */
    PageDo<RolesDo> getRolesInOrNotInAuthoritiesByPage(PageDo<RolesDo> page, int authId, boolean inOrNot);

    PageDo<RolesDo> getRolesInOrNotInAuthoritiesByPage(PageDo<RolesDo> page, int authId, boolean inOrNot, String roleName, String roleDesc);

    /**
     * 获取用户所拥有的角色
     * @param userId
     * @return
     */
    List<RolesDo> getUserAllRoles(int userId);
 
    /**
     * 新增角色权限关系
     * @param ra
     * @return
     */
    int addRolesAuthority(RolesauthorityDo ra);

    /**
     * 删除角色和权限的关系
     * @param ra
     * @return
     */
    int deleteRolesAuthority(RolesauthorityDo ra);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     * @return
     */
    int resetCurrentUserPwd(int userId, String oldPassword, String newPassword);

    /**
     * 管理员重置用户密码
     * @param userId
     * @param newPassword
     * @return
     */
    int resetUserPwd(int userId, String newPassword);

    List<ManagersDo> getCouldFollowOrderPersons(String roleDesc);

   



	/**
	 * 
	 * zhangyunhmf
	 *
	 */
    PageDo<ManagersDo> getDeptManagersByPage(PageDo<ManagersDo> page, String userName,
                                             String nickName, Integer deptId);



	/**
	 *
	 * 查询部门的角色
	 * zhangyunhmf
	 *
	 */
    PageDo<RolesDo> getDeptRolesByPage(PageDo<RolesDo> page, String roleName, Integer deptId);



	/**
	 * 部门管理员查看管理的用户
	 * zhangyunhmf
	 *
	 */
    PageDo<ManagersDo> getDeptManagersInOrNotInRolesByPage(PageDo<ManagersDo> page, int roleId,
                                                           boolean b, String userName, String nickName, Integer deptId);
    
}
