package com.dce.business.actions.banner;

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
import com.dce.business.entity.notice.NoticeDo;

import com.dce.business.service.banner.IBannerService;
import com.dce.business.entity.banner.BannerDo;
/**
 * 图标管理管理授权业务实现类
 *
 * @author  Fantasy
 * @date    generate
 * @version 1.0.0
 * @pageage com.dce.business.actions.BannerController.java
 */
@RestController
@RequestMapping("/banner")
public class BannerController {
	private final static Logger logger = Logger.getLogger(BannerController.class);
	@Resource
	private IBannerService bannerService;
	
	/** 
	 * @api {GET} /banner/index.do 图标管理列表
	 * @apiName bannerList
	 * @apiGroup banner 
	 * @apiVersion 1.0.0 
	 * @apiDescription 图标管理列表
	 *  
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {String} msg 返回成功信息
	 * @apiSuccess {String} code 返回成功编码
	 *	@apiSuccess {java.lang.Integer}  id id
	 *	@apiSuccess {java.lang.String}  icoName 图标名称
	 *	@apiSuccess {java.lang.String}  icoImage 图片
	 *	@apiSuccess {java.lang.Integer}  icoType 图标类型（1:banner图,2:导航小图标）
	 *	@apiSuccess {java.lang.Integer}  linkType 链接类型（0:无 1:超链接  2:程序链接）
	 *	@apiSuccess {java.lang.String}  linkValue 链接值
	 *	@apiSuccess {java.lang.String}  hintValue 提示内容
	 *	@apiSuccess {java.lang.Integer}  sort 排序
	 *	@apiSuccess {java.sql.Date}  createDate 创建时间
	 *	@apiSuccess {java.lang.String}  createName 创建人
	 *	@apiSuccess {java.sql.Date}  modifyDate 更新时间
	 *	@apiSuccess {java.lang.String}  modifyName 更新人
	 *	@apiSuccess {java.lang.Integer}  status 状态(0:删除  1:正常)
	 *	@apiSuccess {java.lang.String}  remark 备注
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "code": 0
	 *	"msg": 返回成功,
	 *	"data": {
	 *	    [
	 *			{
	 * 				id id
	 * 				icoName ico_name
	 * 				icoImage ico_image
	 * 				icoType ico_type
	 * 				linkType link_type
	 * 				linkValue link_value
	 * 				hintValue hint_value
	 * 				sort sort
	 * 				createDate create_date
	 * 				createName create_name
	 * 				modifyDate modify_date
	 * 				modifyName modify_name
	 * 				status status
	 * 				remark remark
	 *			}
	 *		]
	 *	  }
	 *	}
	 */ 
	 @RequestMapping("/index")
	 public Result<?> list() {
		 logger.info("查询图标管理...");
	
		 BannerDo bannerDo = new BannerDo();
		 List<BannerDo> bannerList = bannerService.selectBanner(bannerDo);
		 List<Map<String, Object>> result = new ArrayList<>();
	        if (!CollectionUtils.isEmpty(bannerList)) {
	            for (BannerDo banner : bannerList) {

	                Map<String, Object> map = new HashMap<>();
	                map.put("id", banner.getId());
	                map.put("icoName", banner.getIcoName());
	                map.put("icoImage", banner.getIcoImage());
	                map.put("linkType", banner.getLinkType());
	                map.put("linkValue", banner.getLinkValue());
	                map.put("remark", banner.getRemark());
	                result.add(map);
	            }
	        }
		 return Result.successResult("查询成功", result);
	 }

}
