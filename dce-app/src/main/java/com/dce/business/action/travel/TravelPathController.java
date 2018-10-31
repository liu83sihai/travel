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
	/** 
	 * @api {POST} /travelPath/allPath.do 旅游路线
	 * @apiName allPath
	 * @apiGroup travel 
	 * @apiVersion 1.0.0 
	 * @apiDescription 旅游路线列表查询
	 * 
	*  
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccessExample Success-Response: 
	 * HTTP/1.1 200 OK 
	 * * {
	*    "msg": "获取旅游路线成功",
	*    "code": "0",
	*    "data": 
	*        {
	*           
	*        }        
	*  }
	**/
	@RequestMapping(value = "/allPath", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<?> list() {
        logger.info("查询所有路线.....");
        List<TravelPathDo> pathList = travelPathService.selectAll();
        return Result.successResult("查询成功", pathList);
    }
}
