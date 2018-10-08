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
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.entity.page.Pagination;
import com.dce.business.entity.secrety.ModuleDo;
import com.dce.business.service.secrety.IModuleService;
import com.dce.manager.action.BaseAction;
import com.dce.manager.util.ResponseUtils;

@Controller
@RequestMapping("/homemng/module/*")
public class ModuleController extends BaseAction {

    @Resource
    protected IModuleService moduleService;

    @RequestMapping("moduleIndex")
    public String moduleIndex(ModelMap modelMap){
        return "/module/moduleIndex";
    }

    @RequestMapping("moduleDatas")
    @ResponseBody
    public void moduleDatas(Pagination<ModuleDo> modules, HttpServletRequest request,
                            HttpServletResponse response) {
        PageDo<ModuleDo> pageDo = PageDoUtil.getPage(modules);
        try {
            String moduleName = getString(request, "name");
            if(null!=moduleName){
                moduleName = URLDecoder.decode(moduleName, "UTF-8");
            }
            String moduleNick = getString(request,"module");
            if(null!=moduleNick){
                moduleNick = URLDecoder.decode(moduleNick, "UTF-8");
            }
            pageDo  = moduleService.getAllModules(pageDo,moduleName,moduleNick);
            if(pageDo.getTotalPage() == null){
                pageDo.setTotalCount(1L);
            }
            modules = PageDoUtil.getPageValue(modules, pageDo);
            outPrint(response, JSON.toJSONString(modules));
        } catch (UnsupportedEncodingException e) {
            logger.error("获取moduleDatas，格式化异常", e);
        }catch (Exception e){
            logger.error("获取moduleDatas异常", e);
        }
    }


    @RequestMapping("editModuleDialog")
    public String addModuleDialog(ModelMap modelMap){
        try{
            int id=getInt("moduleId");
            if(id!=-1){
                ModuleDo module=moduleService.getModuleById(id + 0L);
                modelMap.put("module", module);
            }
            return "/module/addModuleDialog";
        }catch(Exception e){
            logger.error("进入 /module/addModuleDialog 页面异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }
    }


    @RequestMapping("saveModule")
    @ResponseBody
    public void saveModule(@ModelAttribute ModuleDo module,HttpServletRequest request,
                           HttpServletResponse response){
        try{
            if(StringUtils.isBlank(module.getName())){
                ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
                return;
            }
            if(StringUtils.isBlank(module.getModule())){
                ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
                return;
            }
            int ret=moduleService.saveModule(module);
            ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
        }catch(Exception e){
            logger.error("saveModule异常",e);
            ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
        }
    }

    @RequestMapping("deleteModule/{moduleId}")
    @ResponseBody
    public void deleteModule(@PathVariable Integer moduleId,HttpServletRequest request,
                             HttpServletResponse response){
        try{
            if(moduleId!=null){
                int ret=moduleService.deleteModuleById(moduleId);
                ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
                return;
            }
            ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
        }catch(Exception e){
            logger.error("deleteModule异常",e);
            ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
        }

    }
}
