package com.dce.business.actions.news;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.DateUtil;
import com.dce.business.entity.message.NewsDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.message.INewsService;

/** 
 * 新闻公告、消息列表
 * @author parudy
 * @date 2018年4月4日 
 * @version v1.0
 */
@RestController
@RequestMapping("/news")
public class NewsController extends BaseController {

    private final static Logger logger = Logger.getLogger(NewsController.class);

    @Resource
    private INewsService newsService;
 

    /** 
	 * @api {GET} /news/list.do 新闻列表
	 * @apiName index
	 * @apiGroup news 
	 * @apiVersion 1.0.0 
	 * @apiDescription 新闻列表
	 *  
	 * @apiUse pageParam  
	 *   
	  *	@apiSuccess {java.lang.Integer}  id 新闻ID
	  *	@apiSuccess {String}  title 标题
	  *	@apiSuccess {String}  content 新闻详情外部链接url
	  *	@apiSuccess {String}  createDate 日期
	  *	@apiSuccess {String}  content 内容
	  *	@apiSuccess {String}  image 图片
	  *
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "code": 0
	 *	"msg": 返回成功,
	 *	"data": {}
	 *	}
	 */ 
    @RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<?> list() {

		logger.info("查询新闻公告.....");

		String pageNum = getString("pageNum"); // 当前页
		String rows = getString("rows"); // 每页显示条数

		if (StringUtils.isBlank(pageNum)) {
			pageNum = "1";
		}

		if (StringUtils.isBlank(rows)) {
			rows = "10";
		}

		logger.info("查询新闻公告列表:pageNum=" + pageNum + ",rows=" + rows);

		Map<String, Object> papamMap = new HashMap<String, Object>();
		PageDo<NewsDo> page = new PageDo<NewsDo>();
		page.setPageSize(Long.valueOf(rows));
		page.setCurrentPage(Long.valueOf(pageNum));
		PageDo<NewsDo> newsPage = newsService.getYsNewsPage(papamMap, page);
		List<NewsDo> newsList = newsPage.getModelList();
		// List<NewsDo> newsList = newsService.selectNewsList();
		List<Map<String, Object>> result = new ArrayList<>();
		if (!CollectionUtils.isEmpty(newsList)) {
			for (NewsDo message : newsList) {

				Map<String, Object> map = new HashMap<>();
				map.put("id", message.getId());
				map.put("title", message.getTitle());
				map.put("content", message.getContent());
				map.put("createDate", DateUtil.dateToString(message.getCreateDate()));
				map.put("image", message.getImage());
				result.add(map);
			}
		}

		return Result.successResult("查询成功", result);
	}
   
}
