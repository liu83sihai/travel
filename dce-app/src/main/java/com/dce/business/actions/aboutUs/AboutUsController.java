package com.dce.business.actions.aboutUs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.common.result.Result;
import com.dce.business.entity.aboutUs.AboutusDo;
import com.dce.business.service.aboutUs.IAboutusService;

@RestController
@RequestMapping("/aboutUs")
public class AboutUsController {
	private final static Logger logger = Logger.getLogger(AboutUsController.class);
	@Resource
	private IAboutusService aboutusService;
	
	/** 
	 * @api {GET} /aboutUs/index.do 项目介绍列表
	 * @apiName ysAboutList
	 * @apiGroup YsAbout 
	 * @apiVersion 1.0.0 
	 * @apiDescription 项目介绍列表
	 *  
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {String} model 返回成功信息
	 *  @apiSuccess {String}  url 介绍地址
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "result": {
	 *	"model": {
	 *		
	 * 	},
	 *	  "status": {
	 *	    "code": 200,
	 *	    "msg": "请求成功"
	 *	  }
	 *	}
	 */ 
	 @RequestMapping("/index")
	 public Result<?> list() {
		 logger.info("查询关于湘信.....");
	
		 //List<NewsDo> newsList = newsService.selectNewsList(Integer.parseInt(pageNum), Integer.parseInt(rows));
		 List<AboutusDo> aboutUs = aboutusService.getAllAboutUs();
		 Map<String, Object> map = new HashMap<>();
		 map.put("url",aboutUs.get(0).getUrl());
		 return Result.successResult("查询成功", map);
	 }

}
