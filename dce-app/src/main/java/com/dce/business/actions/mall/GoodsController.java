package com.dce.business.actions.mall;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.entity.goods.CTGoodsDo;
import com.dce.business.entity.goods.CTUserAddressDo;
import com.dce.business.service.goods.ICTGoodsService;
import com.dce.business.service.goods.ICTUserAddressService;
import com.dce.business.service.user.IUserService;

@RestController
@RequestMapping("mall")
public class GoodsController extends BaseController {

	private final static Logger logger = Logger.getLogger(GoodsController.class);
	
	@Value("${uploadPath}")
	private String fileServerDir;
	
	@Resource
	private ICTGoodsService ctGoodsService;
	@Resource
	private ICTUserAddressService ctUserAddressService;
	@Resource
	private IUserService userDo;
	
	
	/** 
	 * @api {POST} /mall/list.do 商城，商品列表
	 * @apiName list
	 * @apiGroup mall 
	 * @apiVersion 1.0.0 
	 * @apiDescription 商品列表
	 * @apiUse pageParam
	 * @apiParam {int} shopCatId1 2: 会员商品   1: 积分商品 
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {Object[]} hotGoodsList  爆款商品       
	 * @apiSuccess {int} hotGoodsList.goodsId 商品id
	 * @apiSuccess {String} hotGoodsList.title 商品标题
	 * @apiSuccess {String} hotGoodsList.GoodsDesc 商品的描述
	 * @apiSuccess {json} hotGoodsList.goodsImg 商品图片地址
	 * @apiSuccess {json} hotGoodsList.goodsBanner 商品详情页面banner图片地址
	 * @apiSuccess {String} hotGoodsList.goodsDetailImg 商品详情图片地址
	 * @apiSuccess {double} hotGoodsList.shopPrice 商品价格
	 * @apiSuccess {double} hotGoodsList.marketPrice 商品优惠价格
	 * @apiSuccess {int} hotGoodsList.saleCount 已售数量
	 * @apiSuccess {String} hotGoodsList.brandName 商品品牌名称
	 * @apiSuccess {String} hotGoodsList.cateName 商品类别名称
	 * @apiSuccess {Decimal} hotGoodsList.profit 商品利润
	 * @apiSuccess {String} hotGoodsList.detailLink 商品详情连接
	 * @apiSuccess {String} hotGoodsList.goodsFlag //商品类别 1： 旅游卡， 2： 积分商品， 3： 会员商品 ，4 旅游路线  5 代理商保证金 6 用户升级
	 * @apiSuccess {String} hotGoodsList.postage //邮费
	 * 
	 * @apiSuccess {Object[]} normalGoodsList  正常商品      
	 * @apiSuccess {int} normalGoodsList.goodsId 商品id
	 * @apiSuccess {String} normalGoodsList.title 商品标题
	 * @apiSuccess {String} normalGoodsList.GoodsDesc 商品的描述
	 * @apiSuccess {json} normalGoodsList.goodsImg 商品图片地址
	 * @apiSuccess {json} normalGoodsList.goodsBanner 商品详情页面banner图片地址
	 * @apiSuccess {String} normalGoodsList.goodsDetailImg 商品详情图片地址
	 * @apiSuccess {double} normalGoodsList.shopPrice 商品价格
	 * @apiSuccess {double} normalGoodsList.marketPrice 商品优惠价格
	 * @apiSuccess {int} normalGoodsList.saleCount 已售数量
	 * @apiSuccess {String} normalGoodsList.brandName 商品品牌名称
	 * @apiSuccess {String} normalGoodsList.cateName 商品类别名称 
	 * @apiSuccess {Decimal} normalGoodsList.profit 商品利润
	 * @apiSuccess {String} normalGoodsList.detailLink 商品详情连接
	 * @apiSuccess {String} normalGoodsList.goodsFlag //商品类别 1： 旅游卡， 2： 积分商品， 3： 会员商品 ，4 旅游路线  5 代理商保证金 6 用户升级
	 * @apiSuccess {String} normalGoodsList.postage //邮费
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "code": 0
	 *	"msg": 返回成功,
	 *	"data": {"hotGoodsList":[
	 *		      "goodsId": "1",
	 *		      "title": "鹿无忧",
	 *		      "goodsDtails": "提神抗疲劳",
	 *		      "goodsImg": 
	 *		                {
	 *		                 "img1":"d:/sasd.jgp",
	 *		                 "img2":"d:/sasd.jgp",
	 *		                  } ,
	 *		      "saleTime": "2018-8-6 10：19：56",
	 *		      "shopPrice":4999.00,
	 *		    ],
	 *          "normalGoodsList":[
	 *		      "goodsId": "1",
	 *		      "title": "鹿无忧",
	 *		      "goodsDtails": "提神抗疲劳",
	 *		      "goodsImg": 
	 *		                {
	 *		                 "img1":"d:/sasd.jgp",
	 *		                 "img2":"d:/sasd.jgp",
	 *		                  } ,
	 *		      "saleTime": "2018-8-6 10：19：56",
	 *		      "shopPrice":4999.00,
	 *		    ],
	 *	}
	 */	
	@RequestMapping(value = "/list", method = {RequestMethod.POST,RequestMethod.GET})
	public Result<?> list() {
		
		 String pageNum = getString("pageNum");  //当前页码
		 String rows = getString("rows");   //每页显示记录数
		 //因为前台传的商品类别跟后台不一致转换一下
		 String goodsFlag = getString("shopCatId1");  // 2 积分商品  3 会员商品
		 if(goodsFlag == null) {
			 goodsFlag = "2";
		 }
		 int goodsCat = Integer.valueOf(goodsFlag)+1;
		 //end 因为前台传的商品类别跟后台不一致转换一下
		 logger.info("查询商品列表：查询页码--" + pageNum);
		 
		 if(StringUtils.isBlank(pageNum)){  //如果为空则查询第一页
			 pageNum = "1";
		 }
		 
		 if(StringUtils.isBlank(rows)){  //如果为空  默认显示10条
			 rows = "50";
		 }
		 
		 Map<String,Object> maps=new HashMap<String, Object>();
		 maps.put("pageNum", pageNum);
		 maps.put("rows", rows);
		 
		 Map<String, Object> param = new HashMap<String,Object>();
		 param.put("goodsFlag", goodsCat);
		 param.put("shopCatId1", "1");
		 List<CTGoodsDo> hotGoodsList=ctGoodsService.selectByPage(Integer.parseInt(pageNum), Integer.parseInt(rows), param );
		 param.clear();
		 param.put("shopCatId1", "2");
		 param.put("goodsFlag", goodsCat);
		 List<CTGoodsDo> normalGoodsList=ctGoodsService.selectByPage(Integer.parseInt(pageNum), Integer.parseInt(rows),param);
		 
		 Map<String,List<CTGoodsDo>> goodsLst = new HashMap<String,List<CTGoodsDo>>();
		 goodsLst.put("hotGoodsList", hotGoodsList);
		 goodsLst.put("normalGoodsList", normalGoodsList);
		 
		 return Result.successResult("商品获取成功!", goodsLst);
	}
	
	
	/** 
	 * @api {POST} /mall/listTravelCard.do 加入我们购买旅游卡
	 * @apiName listTravelCard
	 * @apiGroup mall 
	 * @apiVersion 1.0.0 
	 * @apiDescription 加入我们购买旅游卡
	 * @apiUse pageParam
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {Object[]} travelCardList  爆款商品       
	 * @apiSuccess {int} travelCardList.goodsId 商品id
	 * @apiSuccess {String} travelCardList.title 商品标题
	 * @apiSuccess {String} travelCardList.GoodsDesc 商品的描述
	 * @apiSuccess {json} travelCardList.goodsImg 商品图片地址
	 * @apiSuccess {json} travelCardList.goodsBanner 商品详情页面banner图片地址
	 * @apiSuccess {String} travelCardList.goodsDetailImg 商品详情图片地址
	 * @apiSuccess {double} travelCardList.shopPrice 商品价格
	 * @apiSuccess {double} travelCardList.marketPrice 商品优惠价格
	 * @apiSuccess {int} travelCardList.saleCount 已售数量
	 * @apiSuccess {String} travelCardList.brandName 商品品牌名称
	 * @apiSuccess {String} travelCardList.cateName 商品类别名称
	 * @apiSuccess {Decimal} travelCardList.profit 商品利润
	 * @apiSuccess {String} travelCardList.detailLink 商品详情连接
	 * @apiSuccess {String} travelCardList.goodsFlag //商品类别 1： 旅游卡， 2： 积分商品， 3： 会员商品 ，4 旅游路线  5 代理商保证金
	 * @apiSuccess {String} travelCardList.postage //邮费
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "code": 0
	 *	"msg": 返回成功,
	 *	"data": {[
	 *		      {"goodsId": "1",
	 *		      "title": "鹿无忧",
	 *		      "GoodsDesc": "提神抗疲劳",
	 *		      "goodsImg": 
	 *		                {
	 *		                 "img1":"d:/sasd.jgp",
	 *		                 "img2":"d:/sasd.jgp",
	 *		                  } ,
	 *			  "goodsBanner":{
	 *		                 "img1":"d:/sasd.jgp",
	 *		                 "img2":"d:/sasd.jgp",
	 *		                  } ,
	 *            "goodsDetailImg":"http://sasd.jgp",
	 *		      "saleTime": "2018-8-6 10：19：56",
	 *		      "shopPrice":4999.00,
	 *			  "marketPrice":4000.00,
	 *			  "saleCount": 30}
	 *		    ] }
	 *	}
	 */	
	@RequestMapping(value = "/listTravelCard", method = {RequestMethod.POST,RequestMethod.GET})
	public Result<?> listTravelCard() {

		
		 String pageNum = getString("pageNum");  //当前页码
		 String rows = getString("rows");   //每页显示记录数
		 
		 logger.info("查询商品列表：查询页码--" + pageNum);
		 
		 if(StringUtils.isBlank(pageNum)){  //如果为空则查询第一页
			 pageNum = "1";
		 }
		 
		 if(StringUtils.isBlank(rows)){  //如果为空  默认显示10条
			 rows = "50";
		 }
		 
		 Map<String, Object> param = new HashMap<String,Object>();
		 param.put("goodsFlag", 1);
		 List<CTGoodsDo> cardGoodsList=ctGoodsService.selectByPage(Integer.parseInt(pageNum), Integer.parseInt(rows), param );
		 return Result.successResult("商品获取成功!", cardGoodsList);
	
	}
	
	
	@RequestMapping(value = "/product/detail", method = {RequestMethod.GET})
	public Result<?> detail(){
		
		String productId = getString("productId");
		Assert.hasText(productId, "要查询的商品id不能为空");
		
		CTGoodsDo goods = ctGoodsService.selectById(Long.parseLong(productId));
		
		if(goods != null){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("productName", goods.getTitle());
			map.put("soldQty", goods.getSaleCount());
			map.put("price", goods.getMarketPrice());
			map.put("barginPrice", goods.getShopPrice());
			map.put("imgUrl", goods.getGoodsImg());
			map.put("goodsStock", goods.getGoodsStock()); //库存
			return Result.successResult("商品查询成功!", map);
		}else{
			return Result.failureResult("所查商品不存在!");
		}
	}
	
	
	/** 
	 * @api {POST} /mall/proxyfee.do 代理商，保证金支付
	 * @apiName proxyfee
	 * @apiGroup mall 
	 * @apiVersion 1.0.0 
	 * @apiDescription 代理商，保证金支付
	 * @apiParam {int} userId 用户id
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {int} goodsId 商品id
	 * @apiSuccess {String} title 商品标题
	 * @apiSuccess {String} GoodsDesc 商品的描述
	 * @apiSuccess {json} goodsImg 商品图片地址
	 * @apiSuccess {json} goodsBanner 商品详情页面banner图片地址
	 * @apiSuccess {String} goodsDetailImg 商品详情图片地址
	 * @apiSuccess {double} shopPrice 商品价格
	 * @apiSuccess {double} marketPrice 商品优惠价格
	 * @apiSuccess {int} saleCount 已售数量
	 * @apiSuccess {String} brandName 商品品牌名称
	 * @apiSuccess {String} cateName 商品类别名称
	 * @apiSuccess {Decimal} profit 商品利润
	 * @apiSuccess {String} detailLink 商品详情连接
	 * @apiSuccess {String} goodsFlag //商品类别 1： 旅游卡， 2： 积分商品， 3： 会员商品 ，4 旅游路线  5 代理商保证金
	 * @apiSuccess {String} postage //邮费
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "code": 0
	 *	"msg": 返回成功,
	 *	"data": {
	 *		      "goodsId": "1",
	 *		      "title": "鹿无忧",
	 *		      "goodsDtails": "提神抗疲劳",
	 *		      "goodsImg": 
	 *		                {
	 *		                 "img1":"d:/sasd.jgp",
	 *		                 "img2":"d:/sasd.jgp",
	 *		                  } ,
	 *		      "saleTime": "2018-8-6 10：19：56",
	 *		      "shopPrice":4999.00,
	 *		    }
	 *	}
	 */	
	@RequestMapping(value = "/proxyfee", method = {RequestMethod.GET,RequestMethod.POST})
	public Result<?> proxyFee(){
		Integer userId = getUserId();
		
		if(null == userId) {
			return Result.failureResult("缺少用户id参数!");
		}
		
		Map<String, Object> param = new HashMap<String,Object>();
		 param.put("goodsFlag", 5);
		 List<CTGoodsDo> hotGoodsList=ctGoodsService.selectByPage(1, 20, param );
		 
		
		if(hotGoodsList != null && hotGoodsList.size()>0){
			return Result.successResult("代理商保证金查询成功!", hotGoodsList.get(0));
		}else{
			return Result.failureResult("没有配置代理商保证金!");
		}
	}
	
	
	@RequestMapping(value = "/goodsDetail", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView goodsDetail() {
        String goodsId = getString("goodsId");
        ModelAndView mav = new ModelAndView("goods/goodsDetail");
        mav.addObject("goodsId", goodsId);
        CTGoodsDo goods = ctGoodsService.selectById(Long.valueOf(goodsId));
        if(StringUtils.isNotBlank(goods.getGoodsBanner())){
        	mav.addObject("bannerImgs", goods.getGoodsBanner().split(","));
        }
        if(StringUtils.isNotBlank(goods.getGoodsDetailImg())){
        	mav.addObject("detailImgs", goods.getGoodsDetailImg().split(","));
        }
        mav.addObject("goods", goods);
        return mav;
    }
	
	/** 
	 * @api {POST} /mall/goodsDetailAPI.do 商品详情api
	 * @apiName goodsDetailAPI
	 * @apiGroup mall 
	 * @apiVersion 1.0.0 
	 * @apiDescription 商品详情api
	 * @apiParam {int} goodsId  商品Id
	 * 
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccess {Object[]} goods  商品对象       
	 * @apiSuccess {int} goods.goodsId 商品id
	 * @apiSuccess {String} goods.title 商品标题
	 * @apiSuccess {String} goods.GoodsDesc 商品的描述
	 * @apiSuccess {json} goods.goodsImg 商品图片地址
	 * @apiSuccess {json} goods.goodsBanner 商品详情页面banner图片地址
	 * @apiSuccess {String} goods.goodsDetailImg 商品详情图片地址
	 * @apiSuccess {double} goods.shopPrice 商品价格
	 * @apiSuccess {double} goods.marketPrice 商品优惠价格
	 * @apiSuccess {int} goods.saleCount 已售数量
	 * @apiSuccess {String} goods.brandName 商品品牌名称
	 * @apiSuccess {String} goods.cateName 商品类别名称
	 * @apiSuccess {Decimal} goods.profit 商品利润
	 * @apiSuccess {String} goods.detailLink 商品详情连接
	 * @apiSuccess {String} goods.goodsFlag //商品类别 1： 旅游卡， 2： 积分商品， 3： 会员商品 ，4 旅游路线  5 代理商保证金 
	 * @apiSuccess {String} goods.postage //邮费 
	 * 
	 * @apiSuccess {String[]} bannerImgs  商品banner图片src数组
	 * @apiSuccess {String[]} detailImgs  商品明细图片src数组
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "code": 0
	 *	"msg": 返回成功,
	 *	"data": {"goods":{
	 *		      "goodsId": "1",
	 *		      "title": "鹿无忧",
	 *		      "goodsDtails": "提神抗疲劳",
	 *		      "goodsImg": 
	 *		                {
	 *		                 "img1":"d:/sasd.jgp",
	 *		                 "img2":"d:/sasd.jgp",
	 *		                  } ,
	 *		      "saleTime": "2018-8-6 10：19：56",
	 *		      "shopPrice":4999.00,
	 *		    },
	 *          "detailImgs":[
	 *		      "http://xxx1",
	 *		      "http://xxx2"
	 *		    ],"bannerImgs":[
	 *		      "http://xxx1",
	 *		      "http://xxx2"
	 *		    ]
	 *	}
	 */	
	@RequestMapping(value = "/goodsDetailAPI", method = { RequestMethod.GET, RequestMethod.POST })
    public Map<String,Object> goodsDetailAPI() {
        String goodsId = getString("goodsId");
        Map<String,Object> mav = new HashMap<String,Object>();
        mav.put("goodsId", goodsId);
        CTGoodsDo goods = ctGoodsService.selectById(Long.valueOf(goodsId));
        if(StringUtils.isNotBlank(goods.getGoodsBanner())){
        	mav.put("bannerImgs", goods.getGoodsBanner().split(","));
        }
        if(StringUtils.isNotBlank(goods.getGoodsDetailImg())){
        	mav.put("detailImgs", goods.getGoodsDetailImg().split(","));
        }
        mav.put("goods", goods);
        return mav;
    }
	

	@RequestMapping(value = "/address", method = {RequestMethod.POST})
	public Result<?> address(){
		
		String address = getString("address");
		String receiveName = getString("receiveName");
		String receivePhone = getString("receivePhone");
		String addressId = getString("addressId");
		Assert.hasText(address, "收货地址不能为空");
		Assert.hasText(receiveName, "收货人不能为空");
		Assert.hasText(receivePhone, "收货人电话不能为空");
		
		Integer userId = getUserId();
		
		CTUserAddressDo addressDo = new CTUserAddressDo();
		addressDo.setAddress(address);
		addressDo.setUserName(receiveName);
		addressDo.setUserPhone(receivePhone);
		addressDo.setUserTel(receivePhone);
		addressDo.setUserId(userId);
		if(StringUtils.isNotBlank(addressId)){
			addressDo.setAddressId(Integer.parseInt(addressId));
		}
		return ctUserAddressService.save(addressDo);
	}
	
	@RequestMapping(value = "/address", method = { RequestMethod.GET })
	public Result<?> getAddress() {

		Integer userId = getUserId();

		CTUserAddressDo addressDo = ctUserAddressService.getAddress(userId);
		return Result.successResult("查询成功", addressDo);
	}
	
	
	/**
	 * IO流读取图片
	 * @param filePath
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/img", method = {RequestMethod.GET})
	
	public void img(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ServletOutputStream out = null;
		FileInputStream ips = null;
		try {
			String filePath = this.getString("filePath");
			ips = new FileInputStream(fileServerDir+filePath);
			response.setContentType("image/png");
			out = response.getOutputStream();
			//读取文件流
			int len = 0;
			byte[] buffer = new byte[1024 * 10];
			while ((len = ips.read(buffer)) != -1){
				out.write(buffer,0,len);
			}
			out.flush();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			out.close();
			ips.close();
		}
		 
	 }
	
}
