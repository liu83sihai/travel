/*
 * Powered By  huangzl QQ: 272950754
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2018
 */


package com.dce.manager.action.travelPath;

import java.io.File;
import java.util.Date;
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
import com.dce.business.entity.page.NewPagination;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.entity.travel.TravelPathDo;
import com.dce.business.service.travel.ITravelPathService;
import com.dce.manager.action.BaseAction;
import com.dce.manager.util.ResponseUtils;


@Controller
@RequestMapping("/path")
public class PathController extends BaseAction{
	//默认多列排序,example: username desc,createTime asc
	//protected static final String DEFAULT_SORT_COLUMNS = null; 
	@Resource
	private ITravelPathService travelPathService;
	
	@Value("#{sysconfig['uploadPath']}")
	private String uploadPath;
	
	@Value("#{sysconfig['readImgUrl']}")
	private String readImgUrl;
	/**
     * 去列表页面
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model){
        return "path/listPath";
    }
	
	@RequestMapping("/listPath")
    public void listPath(NewPagination<TravelPathDo> pagination,
    							  ModelMap model,
    							  HttpServletResponse response) {

        logger.info("----listPath11----");
        try{
            PageDo<TravelPathDo> page = PageDoUtil.getPage(pagination);
            Map<String,Object> param = new HashMap<String,Object>();
        
            String linename = getString("linename");
            System.out.println(linename);
			if (StringUtils.isNotBlank(linename)) {
				param.put("linename", linename);
				model.addAttribute("linename", linename);
			}
            
            page = travelPathService.getTravelPathPage(param, page);
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
    @RequestMapping("/addPath")
    public String addPath(String id, ModelMap modelMap, HttpServletResponse response) {
        logger.info("----addPath----");
        try{
            if(StringUtils.isNotBlank(id)){
                TravelPathDo TravelPathDo = travelPathService.selectByPrimaryKey(Integer.valueOf(id));
                if(null != TravelPathDo){
                    modelMap.addAttribute("travelpath", TravelPathDo);
                }
            }
            return "path/addPath";
        }catch(Exception e){
            logger.error("跳转到数据字典编辑页面异常",e);
            throw new BusinessException("系统繁忙，请稍后再试");
        }

    }

    /**
     * 删除
     */
    @RequestMapping("/deletePath")
    public void deleteYsNotice(String id,HttpServletRequest request,
            HttpServletResponse response) {
        logger.info("----deletePath----");
         try{
             if (StringUtils.isBlank(id) || !id.matches("\\d+")) {
            	 logger.info(id);
                 ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
                 return;
             }
             int ret = travelPathService.deletePathById(Integer.valueOf(id));
             ResponseUtils.renderJson(response, null, "{\"ret\":" + ret + "}");
         }catch(Exception e){
             logger.error("删除公告异常",e);
             ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
         }
     }
    
    
    /**
     * 保存更新
     *
     * @return
     * @author: huangzlmf
     * @date: 2015年4月21日 12:49:05
     */
    @RequestMapping("/savePath")
    @ResponseBody
    public void savePath(TravelPathDo TravelPathDo,@RequestParam(value = "bannerImg1", required = false)  CommonsMultipartFile  bannerImg1,
    		@RequestParam(value = "detailImg1", required = false)  CommonsMultipartFile  detailImg1,
    		@RequestParam(value = "iconImg1", required = false)  CommonsMultipartFile  iconImg1,
    							  HttpServletRequest request, 
    							  HttpServletResponse response) {
        logger.info("----savePath------");
        if(null != bannerImg1 ){
				try {
					String bannerPath = "";
//					for (CommonsMultipartFile banner : bannerImg1) {
						// 文件保存路径
						String filePath = uploadPath + "/" + bannerImg1.getOriginalFilename();
						// 转存文件
						bannerImg1.transferTo(new File(filePath));
						bannerPath =getReadImgUrl(filePath,readImgUrl) ;
//					}
					// 存数据库
					TravelPathDo.setBannerImg(bannerPath);

				} catch (Exception e) {
					e.printStackTrace();
				}

		};
        if(null != detailImg1 ){
        	try {
        		String bannerPath = "";
//					for (CommonsMultipartFile banner : bannerImg1) {
        		// 文件保存路径
        		String filePath = uploadPath + "/" + detailImg1.getOriginalFilename();
        		// 转存文件
        		detailImg1.transferTo(new File(filePath));
        		bannerPath = getReadImgUrl(filePath,readImgUrl) ;
//					}
        		// 存数据库
        		TravelPathDo.setDetailImg(bannerPath);
        		
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        	
        }
        if(null != iconImg1 ){
        	try {
        		String bannerPath = "";
//					for (CommonsMultipartFile banner : bannerImg1) {
        		// 文件保存路径
        		String filePath = uploadPath + "/" + iconImg1.getOriginalFilename();
        		// 转存文件
        		iconImg1.transferTo(new File(filePath));
        		bannerPath  = getReadImgUrl(filePath,readImgUrl) ;
//					}
        		// 存数据库
        		TravelPathDo.setIconImg(bannerPath);
        		
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        	
        }
        try{
        	Integer id = TravelPathDo.getPathid();
        	
            int i = 0;
            if (id != null && id.intValue()>0) {
                i = travelPathService.updatePathById(TravelPathDo);
            } else {
                i = travelPathService.addPath(TravelPathDo);
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
        logger.info("----end savePath--------");
    }
    
	
	
}

