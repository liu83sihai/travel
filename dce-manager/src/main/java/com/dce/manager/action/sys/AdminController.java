package com.dce.manager.action.sys;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.util.Constants;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.entity.page.Pagination;
import com.dce.business.entity.secrety.AuthoritiesDo;
import com.dce.business.entity.secrety.ManagersDo;
import com.dce.business.entity.secrety.ModuleDo;
import com.dce.business.entity.secrety.ResourcesDo;
import com.dce.business.entity.secrety.RolesDo;
import com.dce.business.entity.secrety.UsersrolesDo;
import com.dce.business.service.secrety.IAuthoritiesService;
import com.dce.business.service.secrety.IManagerUserService;
import com.dce.business.service.secrety.IModuleService;
import com.dce.manager.action.BaseAction;
import com.dce.manager.util.ResponseUtils;

@Controller
@RequestMapping("/homemng/menuAdmin/*")
public class AdminController extends BaseAction {

    @Resource
    private IManagerUserService managerUserService;
    @Resource
    private IModuleService moduleService;
    @Resource
    private IAuthoritiesService authorityService;

    @RequestMapping("index")
    public String menuAdmin(ModelMap modelMap){
        return "/menuAdmin/index";
    }

    @RequestMapping("listDatas")
    @ResponseBody
    public void menuDatas(Pagination<ResourcesDo> pagination, HttpServletRequest request,
                          HttpServletResponse response){
        try{
            PageDo<ResourcesDo> page = PageDoUtil.getPage(pagination);
            String name = getString(request,"name");
            if(null != name){
                try {
                    name = URLDecoder.decode(name,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    name = "";
                }
            }
            PageDo<ResourcesDo> usersList=moduleService.getResources(page, name);
            pagination = PageDoUtil.getPageValue(pagination, usersList);
            outPrint(response, JSON.toJSON(pagination));
        }catch(Exception e){
            logger.error("显示数据异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }
    }

    @RequestMapping("addMenuDialog")
    public String addMenuDialog(ModelMap modelMap){
        try{
            int id=getInt("id");
            if(id!=-1){
                ResourcesDo r=moduleService.getOneResource(id);
                if(r!=null){
                    modelMap.put("resource", r);
                }
            }
            List<ModuleDo> moduleList=moduleService.getAllModules();
            modelMap.put("modules", moduleList);
            return "/menuAdmin/addMenuDialog";
        }catch(Exception e){
            logger.error("跳转到新增菜单页面异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }

    }

    /**
     * 新增菜单
     * @param r
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping("addMenu")
    public void addMenu(@ModelAttribute ResourcesDo r,HttpServletRequest request,
                        HttpServletResponse response){
        try{
            if(!validateResources(r)){
                ResponseUtils.renderJson(response, null, "{\"ret\":-1,\"msg\":\"新增菜单失败\"}");
                return;
            }
            int ret=moduleService.saveResources(r);
            if(ret== Constants.SUCCESS){
                ResponseUtils.renderJson(response, null,"{\"ret\":1,\"msg\":\"新增菜单成功\"}");
            }else{
                ResponseUtils.renderJson(response, null,"{\"ret\":-1,\"msg\":\"新增菜单失败\"}");
            }
        }catch(Exception e){
            logger.error("新增菜单异常",e);
            ResponseUtils.renderJson(response, null,"{\"ret\":-1,\"msg\":\"新增菜单失败\"}");
        }

    }

    private boolean validateResources(ResourcesDo r){
        if(StringUtils.isBlank(r.getName())){
            return false;
        }
        if(StringUtils.isBlank(r.getResourceStr())){
            return false;
        }
        if(StringUtils.isBlank(r.getModule())){
            return false;
        }
        return true;
    }


    @ResponseBody
    @RequestMapping("deleteOneResource")
    public void deleteOneResource(HttpServletRequest request,
                                  HttpServletResponse response){
        try{
            String id=getString("id");
            if(StringUtils.isNotBlank(id) && id.matches("\\d+")){
                int ret=moduleService.deleteOneResource(Integer.parseInt(id));
                ResponseUtils.renderJson(response, null,"{\"ret\":"+ret+"}");
            }else{
                ResponseUtils.renderJson(response, null,"{\"ret\":-1,\"msg\":\"删除失败\"}");
            }
        }catch(Exception e){
            logger.error("删除模块异常",e);
            ResponseUtils.renderJson(response, null,"{\"ret\":-1,\"msg\":\"删除失败\"}");
        }

    }

    @RequestMapping("userAdmin")
    public String userAdmin(ModelMap modelMap) {
        return "/userAdmin/index";
    }

    @ResponseBody
    @RequestMapping("userDatas")
    public void userDatas(Pagination<ManagersDo> pagination, HttpServletRequest request,
                          HttpServletResponse response) {
        try{
            PageDo<ManagersDo> page = PageDoUtil.getPage(pagination);
            String userName = getString(request, "username");
            String nickName = getString(request, "nickname");
            if (null != userName) {
                try {
                    userName = URLDecoder.decode(userName, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    logger.error("显示用户数据,userName转码失败", e);
                    userName = "";
                }
            }
            if (null != nickName) {
                try {
                    nickName = URLDecoder.decode(nickName, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    logger.error("显示用户数据,nickName转码失败", e);
                    nickName = "";
                }
            }

            Integer deptId = this.getUserInfos().getDeptId();
            PageDo<ManagersDo> usersList = null;
            if(null != deptId){
                usersList = managerUserService.getDeptManagersByPage(page, userName,nickName,deptId);
            }else{
                usersList = managerUserService.getManagersByPage(page, userName,nickName);
            }
            pagination = PageDoUtil.getPageValue(pagination, usersList);
            outPrint(response, JSON.toJSONString(pagination));
        }catch(Exception e){
            logger.error("显示用户数据异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }
    }

    @RequestMapping("checkUserRoles/{userId}")
    public String checkUserRoles(@PathVariable String userId,ModelMap modelMap){
        try{
            if(StringUtils.isBlank(userId) || !userId.matches("\\d+")){
                return Constants.INVALIDPAGE;
            }
            ManagersDo managers = managerUserService.getUserById(Integer.parseInt(userId));
            if(managers==null){
                return Constants.INVALIDPAGE;
            }
            modelMap.put("user", managers);
            return "/userAdmin/checkUserRoles";
        }catch(Exception e){
            logger.error("查看用户角色异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }
    }

    @RequestMapping("getUserAllRoles")
    public void getUserAllRoles(HttpServletRequest request,
                                HttpServletResponse response) {
        try{
            int userId = getInt("userId");
            if (userId == -1) {
                return;
            }
            List<RolesDo> userRoleList = managerUserService.getUserAllRoles(userId);
            outPrint(response, JSON.toJSONString(userRoleList));
        }catch(Exception e){
            logger.error("获取用户的所有角色异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }

    }

    @RequestMapping("addUserDialog")
    public String addUserDialog(ModelMap modelMap){
        try{
            int userId=getInt("userId");
            if(userId!=-1){
                ManagersDo user=managerUserService.getUserById(userId);
                modelMap.put("user", user);
            }
            return "/userAdmin/addUserDialog";
        }catch(Exception e){
            logger.error("跳转到新增用户页面异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }

    }

    /**
     * 新增用户
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping("addUser")
    public void addUser(@ModelAttribute ManagersDo user,HttpServletRequest request,
                        HttpServletResponse response){
        try{
            if(!validateUserInfo(user)){
                ResponseUtils.renderJson(response, null,"{\"ret\":-1,\"msg\":\"新增用户失败\"}");
                return;
            }
            ManagersDo manager = managerUserService.getUser(user.getUsername().trim());
            if (manager.getId() != null && user.getId() == null ) {
                ResponseUtils.renderJson(response, null,"{\"ret\":-1,\"msg\":\"用户名已存在\"}");
                return;
            }

            user.setOperator(this.getUserId());
            user.setIp(this.getIpAddr());

            Integer deptId = this.getUserInfos().getDeptId();
            if(null != deptId){
                user.setDeptId(deptId);
            }

            int ret=managerUserService.addUsers(user);
            if(ret==1){
                manager = managerUserService.getUser(user.getUsername());
                ResponseUtils.renderJson(response, null,"{\"ret\":1,\"msg\":\"新增用户成功\"}");
            }else{
                ResponseUtils.renderJson(response, null,"{\"ret\":-1,\"msg\":\"新增用户失败\"}");
            }
        }catch(Exception e){
            logger.error("新增用户异常",e);
            ResponseUtils.renderJson(response, null,"{\"ret\":-1,\"msg\":\"系统繁忙，请稍后再试\"}");
        }
    }

    /**
     * 验证新增用户
     * @param user
     * @return
     */
    public boolean validateUserInfo(ManagersDo user){
        if(StringUtils.isBlank(user.getUsername())){
            return false;
        }
        if(StringUtils.isBlank(user.getPassword())){
            return false;
        }
        if(StringUtils.isBlank(user.getNickname())){
            return false;
        }
        if(StringUtils.isBlank(user.getMobile())){
            return false;
        }
        return true;
    }

    @RequestMapping("deleteUser/{userId}")
    @ResponseBody
    public void deleteOneUser(@PathVariable String userId,HttpServletRequest request,
                              HttpServletResponse response){
        try{
            if (StringUtils.isBlank(userId) || !userId.matches("\\d+")) {
                ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
                return;
            }
            int ret = managerUserService.deleteOneUser(Integer.parseInt(userId));
            ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
        }catch(Exception e){
            logger.error("删除用户异常",e);
            ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
        }
    }

    @RequestMapping("rolesIndex")
    public String rolesIndex(ModelMap modelMap){
        return "/roleAdmin/rolesIndex";
    }


    /**
     * 角色列表
     * @param pagination
     * @param request
     * @param response
     */
    @RequestMapping("roleList")
    public void roleDatas(Pagination<RolesDo> pagination, HttpServletRequest request,
                          HttpServletResponse response) {
        PageDo<RolesDo> page = PageDoUtil.getPage(pagination);
        String roleName = getString("name");
        try {
            if (null != roleName) {
                roleName = URLDecoder.decode(roleName, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("角色列表,roleName转码失败", e);
            roleName = "";
        }

        try{
            Integer deptId = this.getUserInfos().getDeptId();
            PageDo<RolesDo> roleList=null;
            if(deptId != null){
                roleList = managerUserService.getDeptRolesByPage(page, roleName,deptId);
            }else{
                roleList = managerUserService.getRolesByPage(page, roleName);
            }
            pagination = PageDoUtil.getPageValue(pagination, roleList);
            outPrint(response, JSON.toJSONString(pagination));
        }catch(Exception e){
            logger.error("角色列表查询异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }

    }

    @RequestMapping("addRoleDialog")
    public String addRoleDialog(ModelMap modelMap){
        try{
            int roleId=getInt("roleId");
            if(roleId!=-1){
                RolesDo role=managerUserService.getOneRoleById(roleId);
                modelMap.put("role", role);
            }
            return "/roleAdmin/addRoleDialog";
        }catch(Exception e){
            logger.error("跳转到添加角色页面异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }

    }

    /**
     * 新增或者修改角色
     * @param role
     * @param request
     * @param response
     */
    @RequestMapping("addOneRole")
    @ResponseBody
    public void addOneRole(@ModelAttribute RolesDo role,HttpServletRequest request,
                           HttpServletResponse response){
        try{
            if(!validateRole(role)){
                ResponseUtils.renderJson(response, null, "{\"ret\":-1,\"msg\":\"新增角色失败\"}");
                return;
            }
            int ret = managerUserService.updateOneRole(role);
            ResponseUtils.renderJson(response, null, "{\"ret\":"+ret+",\"msg\":\"新增角色失败\"}");
        }catch(Exception e){
            logger.error("新增或修改角色异常",e);
            ResponseUtils.renderJson(response, null, "{\"ret\":-1,\"msg\":\"系统繁忙，请稍后再试\"}");
        }
    }

    private boolean validateRole(RolesDo role){
        if(StringUtils.isBlank(role.getName())){
            return false;
        }
        if(StringUtils.isBlank(role.getRoleDesc())){
            return false;
        }
        return true;
    }

    /**
     * 删除一角色
     * @param roleId
     * @param request
     * @param response
     */
    @RequestMapping("deleteOneRole/{roleId}")
    @ResponseBody
    public void deleteOneRole(@PathVariable String roleId,HttpServletRequest request,
                              HttpServletResponse response){
        try{
            if(StringUtils.isNotBlank(roleId) && roleId.matches("\\d+")){
                int ret=managerUserService.deleteOneRole(Integer.parseInt(roleId));
                ResponseUtils.renderJson(response, null,"{\"ret\":"+ret+"}");
            }
        }catch(Exception e){
            logger.error("删除角色异常",e);
            ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
        }

    }


    /**
     * 显示角色权限
     * @param modelMap
     * @return
     */
    @RequestMapping("showRolesAuthorityDialog/{roleId}")
    public String showRolesAuthorityDialog(@PathVariable String roleId,ModelMap modelMap){
        try{
            if(StringUtils.isNotBlank(roleId) && roleId.matches("\\d+")){
                RolesDo role=managerUserService.getOneRoleById(Integer.parseInt(roleId));
                modelMap.put("role", role);
            }
            return "/roleAdmin/showRolesAuthorityDialog";
        }catch(Exception e){
            logger.error("显示角色权限异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }
    }


    /**
     * 获取在角色下或者不在角色下的权限
     * @param pagination
     * @param request
     * @param response
     */
    @RequestMapping("authoritiesInRolesOrNot")
    @ResponseBody
    public void getInOrNotAuthoritiesByRoleId(Pagination<AuthoritiesDo> pagination, HttpServletRequest request,
                                              HttpServletResponse response){
        try{
            int roleId=getInt("roleId");
            int inRoleOrNot=getInt("inRoleOrNot");

            PageDo<AuthoritiesDo> pageDo = PageDoUtil.getPage(pagination);

            pageDo = authorityService.getInOrNotAuthoritiesByRoleId(pageDo, roleId, inRoleOrNot==1);

            Pagination<AuthoritiesDo> authList = PageDoUtil.getPageValue(pagination, pageDo);
            outPrint(response, JSON.toJSONString(authList ));
        }catch(Exception e){
           logger.error("获取在角色下或者不在角色下的权限异常",e);
           throw new BusinessException("系统繁忙，请稍后再试");
        }
    }

    /**
     * 角色绑定用户的方法
     * @param roleId
     * @param modelMap
     * @return
     */
    @RequestMapping("rolesBindUsers/{roleId}")
    public String rolesBindUsers(@PathVariable String roleId,ModelMap modelMap){
        try{
            if(StringUtils.isBlank(roleId) ||!roleId.matches("\\d+")){
                return Constants.INVALIDPAGE;
            }
            RolesDo role=managerUserService.getOneRoleById(Integer.parseInt(roleId));
            if(role==null){
                return Constants.INVALIDPAGE;
            }else{
                modelMap.put("role", role);
                return "/roleAdmin/rolesBindUsers";
            }
        }catch(Exception e){
           logger.error("角色绑定用户的方法异常",e);
           throw new BusinessException("系统繁忙，请稍后再试");
        }

    }

    @RequestMapping("users2Roles")
    @ResponseBody
    public void users2Roles(Pagination<ManagersDo> pagination, HttpServletRequest request,
                            HttpServletResponse response) {
        PageDo<ManagersDo> page = PageDoUtil.getPage(pagination);
        int roleId = getInt("roleId");
        int inOrNot = getInt("inOrNot");
        //角色ID不为空，并且制定是在角色内或者非该角色标识才进行查询
        try {
            if (roleId != -1 && inOrNot != -1) {
                String userName = getString(request, "username");
                if (null != userName) {
                    userName = URLDecoder.decode(userName, "UTF-8");
                }
                String nickName = getString(request, "nickname");
                if (null != nickName) {
                    nickName = URLDecoder.decode(nickName, "UTF-8");
                }

                PageDo<ManagersDo> list = null;
                Integer deptId = this.getUserInfos().getDeptId();
                if(null != deptId){
                    list = managerUserService.getDeptManagersInOrNotInRolesByPage(page, roleId, inOrNot == 1,userName,nickName,deptId);
                }else{
                    list = managerUserService.getManagersInOrNotInRolesByPage(page, roleId, inOrNot == 1,userName,nickName);
                }
                pagination = PageDoUtil.getPageValue(pagination, list);
                outPrint(response, JSON.toJSONString(pagination));
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("获取用户角色,转码失败", e);
        }catch (Exception e){
            logger.error("获取用户角色异常", e);
        }
    }


    @RequestMapping("addUserRoles")
    @ResponseBody
    public void addUsersRoles(@ModelAttribute UsersrolesDo userRoles, HttpServletRequest request,
                              HttpServletResponse response){
        try{
            if(!checkUsersRoles(userRoles)){
                ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
                return;
            }
            userRoles.setEnabled(true);
            int ret=managerUserService.addUsersRoles(userRoles);
            ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
        }catch(Exception e){
           logger.error("添加用户角色异常",e);
            ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
        }
    }


    @RequestMapping("deleteUserRoles")
    @ResponseBody
    public void deleteUsersRoles(@ModelAttribute UsersrolesDo userRoles,HttpServletRequest request,
                                 HttpServletResponse response){
        try{
            if(!checkUsersRoles(userRoles)){
                ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
                return;
            }
            int ret=managerUserService.deleteUsersRoles(userRoles);
            ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
        }catch(Exception e){
           logger.error("删除用户角色异常",e);
            ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
        }

    }

    @RequestMapping("resetUserPwd")
    @ResponseBody
    public void resetLoginUserPwd(HttpServletRequest request,
                                  HttpServletResponse response){

        try{
            String newPassword=getString("newPassword");
            String userId=getString(request,"userId");
            String newPasswordAgain=getString("newPasswordAgain");
            //旧密码、新密码都不能为空，确认密码必须等于新密码
            if( StringUtils.isBlank(userId)||StringUtils.isBlank(newPassword)
                    || StringUtils.isBlank(newPasswordAgain)
                    || !newPassword.equals(newPasswordAgain)){
                ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
                return;
            }
            int ret=managerUserService.resetUserPwd(Integer.valueOf(userId),  newPassword);
//            if (ret > 0) {
//                //记录密码更新时间
//                loanUserService.savePwdUpdateTime(Long.valueOf(userId), Platform.MANAGER.type(), "");
//            }
            ResponseUtils.renderJson(response, null, "{\"ret\":"+ret+"}");
        }catch(Exception e){
           logger.error("修改用户密码异常",e);
            ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
        }

    }

    private boolean checkUsersRoles(UsersrolesDo ur){
        if(ur.getRoleId()!=null && ur.getUserId()!=null){
            return true;
        }
        return false;
    }
}

