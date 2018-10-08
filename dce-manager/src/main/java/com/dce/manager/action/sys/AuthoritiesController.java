package com.dce.manager.action.sys;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
import com.dce.business.entity.secrety.AuthorityresourcesDo;
import com.dce.business.entity.secrety.ResourcesDo;
import com.dce.business.entity.secrety.RolesDo;
import com.dce.business.entity.secrety.RolesauthorityDo;
import com.dce.business.service.secrety.IAuthoritiesService;
import com.dce.business.service.secrety.IManagerUserService;
import com.dce.manager.action.BaseAction;
import com.dce.manager.util.ResponseUtils;

@Controller
@RequestMapping("/homemng/authority/*")
public class AuthoritiesController extends BaseAction {

    @Resource
    protected IAuthoritiesService authorityService;

    @Resource
    protected IManagerUserService managerUserService;

    @RequestMapping("authorityIndex")
    public String authorityIndex(ModelMap modelMap) {
        return "/authority/authorityIndex";
    }

    /**
     * 权限列表
     *
     * @param pagination
     * @param request
     * @param response
     */
    @RequestMapping("authoritiesList")
    @ResponseBody
    public void listDatas(Pagination<AuthoritiesDo> pagination,
                          HttpServletRequest request, HttpServletResponse response) {
        String name = getString("name");
        try {
            if (null != name) {
                name = URLDecoder.decode(name, "UTF-8");
            }
            String authDesc = getString(request, "authDesc");// 备注
            if (null != authDesc) {
                authDesc = URLDecoder.decode(authDesc, "UTF-8");
            }
            PageDo<AuthoritiesDo> page = PageDoUtil.getPage(pagination);
            page = authorityService.getAuthority(page, name, authDesc);
            pagination = PageDoUtil.getPageValue(pagination, page);
            outPrint(response, JSON.toJSONString(pagination));

        } catch (UnsupportedEncodingException e) {
            logger.error("显示权限列表异常", e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }
    }

    /**
     * 修改权限
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("editAuthority")
    public String editAuthority(ModelMap modelMap) {
        try{
            int authId = getInt("authId");
            if (authId != -1) {
                AuthoritiesDo auth=authorityService.getById(authId + 0L);
                modelMap.put("auth", auth);
            }
            return "/authority/editAuthority";
        }catch(Exception e){
            logger.error("修改权限异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }
    }

    /**
     * 保存权限
     *
     * @param auth
     * @param request
     * @param response
     */
    @RequestMapping("saveAuthority")
    @ResponseBody
    public void saveAuthority(@ModelAttribute AuthoritiesDo auth,
                              HttpServletRequest request, HttpServletResponse response) {
        try{
            if (StringUtils.isBlank(auth.getName())
                    || !auth.getName().matches("DK_ROLE_(.*)")) {// 新增的权限名称，必须以“DK_ROLE_”开头
                ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
                return;
            }
            int ret = 0;
            if(auth.getId() == null){
                ret = authorityService.addAuthorities(auth);
            }else{
                ret = authorityService.updateAuthoritiesById(auth);
            }
            ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
        }catch(Exception e){
            logger.error("保存权限异常",e);
            ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
        }

    }

    @RequestMapping("deleteOneAuth/{authId}")
    @ResponseBody
    public void deleteAuthority(@PathVariable Integer authId,
                                HttpServletRequest request, HttpServletResponse response) {
        try{
            if (authId == null) {
                ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
                return;
            }
            int ret=authorityService.deleteById(Long.valueOf(authId));
            ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
        }catch(Exception e){
            logger.error("删除权限异常",e);
            ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
        }

    }

    @RequestMapping("bindResources/{authId}")
    public String bindResources(@PathVariable String authId, ModelMap modelMap) {
        try{
            if (StringUtils.isBlank(authId) || !authId.matches("\\d+")) {
                return Constants.INVALIDPAGE;
            }
            AuthoritiesDo auth = authorityService
                    .getById(Integer.parseInt(authId) + 0L);
            if (auth == null) {
                return Constants.INVALIDPAGE;
            } else {
                modelMap.put("auth", auth);
                return "/authority/bindResources";
            }
        }catch(Exception e){
            logger.error("进入/authority/bindResources页面异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }

    }

    /**
     * 获取在权限中的菜单
     *
     * @param page
     * @param request
     * @param response
     */
    @RequestMapping("resourcesInAuthorities")
    @ResponseBody
    public void resourcesInAuthorities(Pagination<ResourcesDo> pagination,
                                       HttpServletRequest request, HttpServletResponse response) {
        try{
            int authorityId = getInt("authorityId");
            int inOrNot = getInt("inOrNot");
            String filterName = getString(request, "name");
            String filterLinks = getString(request, "resourceStr");
            if (StringUtils.isNotBlank(filterName)) {
                filterName = URLDecoder.decode(filterName, "utf-8");
            }
            if (StringUtils.isNotBlank(filterLinks)) {
                filterLinks = URLDecoder.decode(filterLinks, "utf-8");
            }
            PageDo<ResourcesDo> page = PageDoUtil.getPage(pagination);
            page = authorityService.getResourcesInOrNotAuthority(page, authorityId,
                    inOrNot == 1, filterName, filterLinks);
            pagination = PageDoUtil.getPageValue(pagination, page);
            outPrint(response, JSON.toJSONString(pagination));
        }catch (UnsupportedEncodingException e){
            logger.error("获取在权限中的菜单,格式化异常", e);
        }catch(Exception e){
            logger.error("获取在权限中的菜单异常",e);
        }
    }

    /**
     * 新增权限菜单关系
     *
     * @param ar
     * @param request
     * @param response
     */
    @RequestMapping("addResource2Authority")
    @ResponseBody
    public void addResource2Authority(@ModelAttribute AuthorityresourcesDo ar,
                                      HttpServletRequest request, HttpServletResponse response) {
        if (!checkRelation(ar)) {
            ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
            return;
        }
        ar.setEnabled(true);
        int ret = authorityService.addAuthorityResource(ar);
        ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
    }

    /**
     * 删除权限菜单对应关系
     *
     * @param ar
     * @param request
     * @param response
     */
    @RequestMapping("deleteResource2Authority")
    @ResponseBody
    public void deleteResource2Authority(
            @ModelAttribute AuthorityresourcesDo ar,
            HttpServletRequest request, HttpServletResponse response) {
        if (!checkRelation(ar)) {
            ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
            return;
        }
        int ret = authorityService.deleteAuthorityResource(ar);
        ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
    }

    private boolean checkRelation(AuthorityresourcesDo ar) {
        if (ar.getResourceId() == null || ar.getAuthorityId() == null) {
            return false;
        }
        return true;
    }

    @RequestMapping("setRolesIntoAuthorityDialog/{authId}")
    public String setRolesIntoAuthorityDialog(@PathVariable String authId,
                                              ModelMap modelMap) {
        if (StringUtils.isBlank(authId) || !authId.matches("\\d+")) {
            return Constants.INVALIDPAGE;
        }
        AuthoritiesDo auth = authorityService.getAuthorities(Integer
                .parseInt(authId));
        if (auth == null) {
            return Constants.INVALIDPAGE;
        }
        modelMap.put("auth", auth);
        return "/authority/setRolesIntoAuthorityDialog";
    }

    @RequestMapping("rolesInAuthority")
    @ResponseBody
    public void rolesInAuthority(Pagination<RolesDo> pagination,
                                 HttpServletRequest request, HttpServletResponse response) {
        PageDo<RolesDo> page = PageDoUtil.getPage(pagination);
        int authId = getInt("authId");
        int inOrNot = getInt("inOrNot");
        String roleName = getString(request, "name");
        String roleDesc = getString(request, "roleDesc");
        try {
            if (null != roleName) {

                roleName = URLDecoder.decode(roleName, "UTF-8");
            }
            if (null != roleDesc) {

                roleDesc = URLDecoder.decode(roleDesc, "UTF-8");
            }
            if (authId == -1 || inOrNot == -1) {
                return;
            }
            PageDo<RolesDo> rolesList = managerUserService
                    .getRolesInOrNotInAuthoritiesByPage(page, authId,
                            inOrNot == 1, roleName, roleDesc);
            pagination = PageDoUtil.getPageValue(pagination, rolesList);
            outPrint(response, JSON.toJSONString(pagination));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("addRoles2Authority")
    @ResponseBody
    public void addRoles2Authority(@ModelAttribute RolesauthorityDo ra,
                                   HttpServletRequest request, HttpServletResponse response) {
        if (!checkRolesAuthority(ra)) {
            ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
            return;
        }
        ra.setEnabled(true);
        int ret = managerUserService.addRolesAuthority(ra);
        ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
    }

    @RequestMapping("deleteRoles2Authority")
    @ResponseBody
    public void deleteRoles2Authority(@ModelAttribute RolesauthorityDo ra,
                                      HttpServletRequest request, HttpServletResponse response) {
        if (!checkRolesAuthority(ra)) {
            ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
            return;
        }
        int ret = managerUserService.deleteRolesAuthority(ra);
        ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
    }

    private boolean checkRolesAuthority(RolesauthorityDo ra) {
        if (ra.getAuthorityId() != null && ra.getRoleId() != null) {
            return true;
        }
        return false;
    }

}
