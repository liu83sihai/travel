package com.dce.business.action.travel;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.entity.travel.TravelPathDo;
import com.dce.business.service.travel.ITravelPathService;

@RestController
@RequestMapping("/travelPath")
public class TravelPathController extends BaseController{
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
	 * @apiSuccess {String} pathid 记录ID,
	 * @apiSuccess {String} linename 路线名称,
	 * @apiSuccess {String} state  路线状态  是否开通,
	 * @apiSuccess {String} remake 备注,
	 * @apiSuccess {String} bannerImg banner页面img url,
	 * @apiSuccess {String} detailImg 详情页面，详情图片,
	 * @apiSuccess {String} iconImg   列表页面 小图片url,
	 * @apiSuccess {String} starLevel 景区 星级,
	 * @apiSuccess {String} price  景区价格,
	 * @apiSuccess {String} score  景区评分,
	 * @apiSuccess {String} outline  景区概要,
	 * @apiSuccess {String} detailUrl 景区详情外部链接
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
	
	
	@RequestMapping(value = "/travelDetail", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView travelDetail() {
        String travelPathId = getString("travelPathId");
        ModelAndView mav = new ModelAndView("travelpath/travelDetail");
        mav.addObject("travelPathId", travelPathId);
        TravelPathDo  travelPath = travelPathService.selectByPrimaryKey(Integer.valueOf(travelPathId));
        if(StringUtils.isNotBlank(travelPath.getBannerImg())){
        	mav.addObject("bannerImgs", travelPath.getBannerImg().split(";"));
        }
        if(StringUtils.isNotBlank(travelPath.getDetailImg())){
        	mav.addObject("detailImgs", travelPath.getDetailImg().split(";"));
        }
        mav.addObject("travelPath", travelPath);
        return mav;
    }
	
}
