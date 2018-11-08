/*
 * Powered By  huangzl QQ: 272950754
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2018
 */


package com.dce.manager.action.aboutUs;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.DateUtil;
import com.dce.business.common.util.ExeclTools;
import com.dce.business.entity.aboutUs.AboutusDo;
import com.dce.business.entity.page.NewPagination;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.service.aboutUs.IAboutusService;
import com.dce.manager.action.BaseAction;
import com.dce.manager.util.ResponseUtils;



/**
 * @author  huangzl QQ: 272950754
 * @version 1.0
 * @since 1.0
 */

@Controller
@RequestMapping("/aboutus")
public class AboutusController extends BaseAction{
	@Resource
	private IAboutusService aboutusService;
	@Value("#{sysconfig['aboutusUrl']}")
	private String aboutusUrl;

	/**
     * 去列表页面
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        return "aboutus/listAboutus";
    }
	
	@RequestMapping("/listAboutus")
    public void listAboutus(NewPagination<AboutusDo> pagination,
    							  ModelMap model,
    							  HttpServletResponse response) {

        logger.info("----listAboutus----");
        try{
        	PageDo<Map<String, Object>> page = PageDoUtil.getPage(pagination);
			Map<String, Object> param = new HashMap<String, Object>();
            page = aboutusService.getAboutusPage(param, page);
            pagination = PageDoUtil.getPageValue(pagination, page);
            outPrint(response, JSONObject.toJSON(pagination));
        }catch(Exception e){
            logger.error("查询清单异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }
    }
	
	
	  
    /**
     * 编辑页面
     *
     * @return
     */
    @RequestMapping("/addAboutus")
    public String addAboutus(String id, ModelMap modelMap, HttpServletResponse response) {
        logger.info("----addAboutus----");
        try{
            if(StringUtils.isNotBlank(id)){
                AboutusDo aboutusDo = aboutusService.getById(Integer.parseInt(id));
                if(null != aboutusDo){
                    modelMap.addAttribute("aboutus", aboutusDo);
                }
            }
            return "aboutus/addAboutus";
        }catch(Exception e){
            logger.error("跳转到数据字典编辑页面异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }

    }

    /**
     * 保存更新
     *
     * @return
     * @author: huangzlmf
     * @date: 2015年4月21日 12:49:05
     */
    @RequestMapping("/saveAboutus")
    @ResponseBody
    public void saveAboutus(AboutusDo aboutusDo, 
    							  HttpServletRequest request, 
    							  HttpServletResponse response) {
        logger.info("----saveAboutus------");
        try{
            Integer id = aboutusDo.getId();
            
            //访问地址
            aboutusDo.setUrl(aboutusUrl);
            int i = 0;
            if (id != null && id.intValue()>0) {
                i = aboutusService.updateAboutusById(aboutusDo);
            } else {
                i = aboutusService.addAboutus(aboutusDo);
            }

            if (i <= 0) {
                outPrint(response,this.toJSONString(Result.failureResult("操作失败")));
                return;
            }
            outPrint(response, this.toJSONString(Result.successResult("操作成功")));
        }catch(Exception e){
            logger.error("保存更新失败",e);
            outPrint(response, this.toJSONString(Result.failureResult("操作失败")));
        }
        logger.info("----end saveAboutus--------");
    }
    
    @RequestMapping("/deleteAboutUs")
    public void deleteAboutUs(String id, HttpServletRequest request, HttpServletResponse response){
    	logger.info("----deleteAboutUs----");
		try {
			if (StringUtils.isBlank(id) || !id.matches("\\d+")) {
				ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
				return;
			}
			int ret = aboutusService.deleteById(Integer.valueOf(id));
			ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
		} catch (Exception e) {
			logger.error("删除新闻异常", e);
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
		}
    }
    
	
	
}

