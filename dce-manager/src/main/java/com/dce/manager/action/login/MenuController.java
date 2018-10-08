package com.dce.manager.action.login;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.entity.secrety.ModuleDo;
import com.dce.business.entity.secrety.ResourcesDo;
import com.dce.business.entity.secrety.UserInfos;
import com.dce.business.service.secrety.IAuthorityresourcesService;
import com.dce.business.service.secrety.IModuleService;
import com.dce.manager.action.BaseAction;

@Controller
@RequestMapping("/menu/")
public class MenuController extends BaseAction {

    @Resource
    private IModuleService moduleService;
    
    @Resource
    private IAuthorityresourcesService resourceService;
    
    
    @RequestMapping("index")
    public String menuDemo(ModelMap modelMap) {
        try{
            UserInfos user = getUserInfos();
            modelMap.put("user", user);

            int userId = getUserId();
            if(userId == 0){
            	return "/login/login";
            }
            HttpSession session = getSession();
            if (session.getAttribute("resourcesList") == null) {
                //获取菜单资源
                //AuthorityResourcesDao authorityResourcesDao = (AuthorityResourcesDao) SpringBeanUtil.getBean("authorityResourcesDao");
                List<ResourcesDo> resourcesList = resourceService.getResourcesByUserId(Long.valueOf(userId)); //authorityResourcesDao.getResourcesByUserId(userId);

                session.setAttribute("resourcesList", resourcesList);
            }
            return "/menu/index";
        }catch(Exception e){
           logger.error("进入后台管理首页异常",e);
           throw new BusinessException("系统繁忙，请稍后再试");
        }
    }

    /**
     * 获取用户可访问的菜单
     */
    @ResponseBody
    @RequestMapping("/getMenus")
    public void getMenus(HttpServletRequest request, HttpServletResponse response) {
        try{
            int userId = getUserId();
            List<ModuleDo> modules = moduleService.getUserModules(userId);
            outPrint(response, JSONArray.toJSON(modules));
        }catch(Exception e){
           logger.error("获取用户可访问的菜单异常",e);
        }
    }
}
