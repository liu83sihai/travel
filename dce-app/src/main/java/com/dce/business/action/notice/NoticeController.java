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
     * 公告列表
     * @return  
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
