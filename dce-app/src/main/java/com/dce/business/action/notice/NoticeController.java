package com.dce.business.action.notice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.common.result.Result;
import com.dce.business.entity.notice.NoticeDo;
import com.dce.business.service.message.INoticeService;

/**
 * 公告
 * 
 * @author NI
 *
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
	private final static Logger logger = Logger.getLogger(NoticeController.class);
	@Resource
	private INoticeService noticeService;
	
	/** 
	 * @api {GET} /notice/list.do 公告列表
	 * @apiName notice
	 * @apiGroup notice 
	 * @apiVersion 1.0.0 
	 * @apiDescription 公告列表 
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {String} model 返回成功信息
	 * @apiSuccess {int}  id 公告id
	 * @apiSuccess {int}  top_notice 置顶公告（0是/1否)
	 * @apiSuccess {String}  title 公告标题
	 * @apiSuccess {String}  content 公告类容
	 * @apiSuccess {date}  create_date 创建时间
	 * @apiSuccess {String}  image 列表图
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 * "code": 0,
	 * "msg": "请求成功"
	 * "data":{
	 *   	[
	 *   	{"id": "1",
     *   	 "topNotice":"0",
     *   	 "title": "12154545",
     *   	 "content": "12321",
     *   	 "create_date": 2018-8-6 14:55 ,
     *   	 "image": "c:/img/xx.jpg",
     *   	},
     *   	{"id": "1",
     *   	 "topNotice":"0",
     *   	 "title": "12154545",
     *   	 "content": "12321",
     *   	 "create_date": 2018-8-6 14:55 ,
     *   	 "image": "c:/img/xx.jpg",
     *   	}
     *    ]
	 *  }
	 *}
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<?> list() {

        logger.info("查询公告.....");
        
        //查出公告列表
        List<NoticeDo> noticeList = noticeService.selectNoticeList();
        List<Map<String, Object>> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(noticeList)) {
            for (NoticeDo message : noticeList) {

                Map<String, Object> map = new HashMap<>();
                map.put("id", message.getId());
                map.put("title", message.getTitle());
                map.put("topNotice", message.getTopNotice());
                map.put("content", message.getContent());
                map.put("create_date", message.getCreateDate());
                map.put("image", message.getImage());
                result.add(map);
            }
        }

        return Result.successResult("查询成功", result);
    }
}
