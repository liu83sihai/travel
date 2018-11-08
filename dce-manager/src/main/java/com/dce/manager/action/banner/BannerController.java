
package com.dce.manager.action.banner;

import java.io.File;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.entity.banner.BannerDo;
import com.dce.business.entity.page.NewPagination;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.service.banner.IBannerService;
import com.dce.manager.action.BaseAction;
import com.dce.manager.util.ResponseUtils;


@Controller
@RequestMapping("/banner")
public class BannerController extends BaseAction{
	//默认多列排序,example: username desc,createTime asc
	//protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Resource
	private IBannerService bannerService;

	@Value("#{sysconfig['uploadPath']}")
	private String uploadPath;
	
	@Value("#{sysconfig['readImgUrl']}")
	private String readImgUrl;
	
	@Value("#{sysconfig['goodsDetailUrl']}")
	private String goodsDetailUrl;
	
	/**
     * 去列表页面
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        return "banner/listBanner";
    }
	
	@RequestMapping("/listBanner")
    public void listBanner(NewPagination<BannerDo> pagination,
    							  ModelMap model,
    							  HttpServletResponse response) {

        logger.info("----listBanner----");
        try{
            PageDo<BannerDo> page = PageDoUtil.getPage(pagination);
            String companyName = getString("searchPolicyName");
            Map<String,Object> param = new HashMap<String,Object>();
            if(StringUtils.isNotBlank(companyName)){
                param.put("policyName",companyName);
                model.addAttribute("searchPolicyName",companyName);
            }
            String managerName = getString("searManagerName");
            if(StringUtils.isNotBlank(managerName)){
                param.put("managerName", managerName);
                model.addAttribute("searManagerName",managerName);
            }
            page = bannerService.getBannerPage(param, page);
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
    @RequestMapping("/addBanner")
    public String addBanner(String id, ModelMap modelMap, HttpServletResponse response) {
        logger.info("----addBanner----");
        try{
            if(StringUtils.isNotBlank(id)){
                BannerDo bannerDo = bannerService.getById(Integer.valueOf(id));
                if(null != bannerDo){
                    modelMap.addAttribute("banner", bannerDo);
                }
            }
            return "banner/addBanner";
        }catch(Exception e){
            logger.error("跳转到数据字典编辑页面异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }

    }

    /**
     * 保存更新
     *
     * @return
     * @date: 2015年4月21日 12:49:05
     */
    @RequestMapping("/saveBanner")
    @ResponseBody
    public void saveBanner(BannerDo bannerDo, 
						  HttpServletRequest request, 
						  HttpServletResponse response) {
        
        try{
            Integer id = bannerDo.getId();
            Long userId = new Long(this.getUserId());
            
            StringBuffer sb  = new StringBuffer();
    		sb.append(goodsDetailUrl).append("?goodsId=").append(bannerDo.getGoodsId()).append("&page=goods");
            bannerDo.setLinkValue(sb.toString());
            
            int i = 0;
            if (id != null && id.intValue()>0) {
            	 bannerDo.setModifyName(this.getUserName() + ":" + userId);
            	 bannerDo.setModifyDate(new Date(System.currentTimeMillis()));
                i = bannerService.updateBannerById(bannerDo);
            } else {
            	bannerDo.setCreateDate(new Date(System.currentTimeMillis()));
            	bannerDo.setStatus(1);
				bannerDo.setCreateName(this.getUserName() + ":" + userId);
                i = bannerService.addBanner(bannerDo);
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
        logger.info("----end saveBanner--------");
    }
    
    /**
     * 删除
     */
    @RequestMapping("/deleteBanner")
    public void deleteBanner(String id,HttpServletRequest request,
            HttpServletResponse response) {
        logger.info("----deletebanner----");
         try{
             if (StringUtils.isBlank(id) || !id.matches("\\d+")) {
            	 logger.info(id);
                 ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
                 return;
             }
             int ret = bannerService.deleteById(Integer.valueOf(id));
             ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
         }catch(Exception e){
             logger.error("删除异常",e);
             ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
         }
     }
    
}

