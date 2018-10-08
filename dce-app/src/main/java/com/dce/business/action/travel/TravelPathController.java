package com.dce.business.action.travel;

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
import com.dce.business.entity.travel.TravelPathDo;
import com.dce.business.service.travel.ITravelPathService;

@RestController
@RequestMapping("/travelPath")
public class TravelPathController {
	private final static Logger logger = Logger.getLogger(TravelPathController.class);
	
	
	@Resource
	private ITravelPathService travelPathService;
	
	/**
	 * 返回所有路线给前台
	 */
	@RequestMapping(value = "/allPath", method = RequestMethod.GET)
    public Result<?> list() {

        logger.info("查询所有路线.....");

        List<TravelPathDo> pathList = travelPathService.selectAll();
        List<Map<String, Object>> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(pathList)) {
            for (TravelPathDo message : pathList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", message.getPathid());
                map.put("lineName", message.getLinename());
                map.put("state", message.getState());  //状态 (已开发0/马上推出1/正在开发2)
                map.put("remake", message.getRemake());
                result.add(map);
            }
        }

        return Result.successResult("查询成功", result);
    }
}
