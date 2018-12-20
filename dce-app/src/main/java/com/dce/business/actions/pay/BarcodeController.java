package com.dce.business.actions.pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.dce.business.actions.common.BaseController;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.common.thirdpay.BarcodePayMethodBean;
import com.dce.business.common.thirdpay.BarcodeResult;
import com.dce.business.common.thirdpay.BizInfoBean;
import com.dce.business.common.thirdpay.ThirdPayConfig;
import com.dce.business.common.thirdpay.TradeInfoBean;
import com.dce.business.common.util.CalculateUtils;
import com.dce.business.entity.goods.CTGoodsDo;
import com.dce.business.entity.order.Order;
import com.dce.business.entity.order.OrderDetail;
import com.dce.business.service.goods.ICTGoodsService;
import com.dce.business.service.order.IOrderService;
import com.dce.business.service.order.OrderDetailService;
import com.dce.business.service.pay.IKJTPayService;	

/**
 * 
 * @author
 *
 */
@Controller
@RequestMapping(value = "/barcode")
public class BarcodeController  extends BaseController{
	
	 private static final Logger logger = Logger.getLogger(BarcodeController.class);

	 private static final String JPG = "image/jpeg;charset=GB2312";

	 
     private final static String oauth2_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
     private static final String JS_SDK_URL = "http://app.zjzwly.com/barcode/toBarcodePay.do";

	
     @Resource
     private IKJTPayService  kjtPayService;
	  
     @Resource
     private IOrderService orderService;
     @Resource
     private OrderDetailService orderDetailService;
     @Resource
 	 private ICTGoodsService ctGoodsService;
	
	/**
	 * 生成二维码图片
	 * 
	 * @param request
	 * @param response
	 * @author: zhanbmf
	 * @throws IOException 
	 * @date 2015-3-31 下午3:36:21
	 */
	@RequestMapping(value = "paybarcode")
	public void paybarcode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Integer userId = getUserId();
		if(userId == null){
			throw new BusinessException("无效的用户id");
		}
		
	    String redirectUrl = "http://app.zjzwly.com/dce-app/barcode/doPay.do";
	    String barcodeContent = MessageFormat.format(oauth2_url, new Object[] { WXConfig.APPID, URLEncoder.encode(redirectUrl, "utf-8") });
	    logger.info("二维码内容：" + barcodeContent);
          
		response.setContentType(JPG);// 设定输出的类型  
		OutputStream responseStream = response.getOutputStream();		
		QRCodeUtil.encodePR(barcodeContent, 0, 0, responseStream);
		responseStream.flush();
		responseStream.close();
	}
	
	 /**
     *	 去扫码支付页面
     * @return
     */
    @RequestMapping("/toBarcodePay")
    public ModelAndView paySucc(HttpServletRequest request){
    	
    	ModelAndView mav = new ModelAndView("pay/wxPayByBarcode");
        try {
        	Integer userId = getUserId();
        	String orderId = this.getString("orderId");
        	mav.addObject("userId", userId);
        	mav.addObject("orderId", orderId);
        	mav.addObject("appId" , WXConfig.APPID);

        	/*
        	//签名wxjssdk ，签名参数
            Map<String, String> signInfo = JSSDKSignUtil.getSignInfo(WXConfig.APPID,WXConfig.WX_SECRET, JS_SDK_URL );
            logger.info("获取jssdk签名信息:token="+",url="+JS_SDK_URL + "=====>result:"+signInfo);
            if(null != signInfo){
            	mav.addAllObjects(signInfo);
            }
            */
        	
        } catch (Exception e) {
            return mav.addObject("error","系统繁忙，请稍后再试");
        }
        return mav;
        
    }
    
	
	
	/**
	 * 微信扫二维码后的回调页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doPay", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView doPay() {
		
		ModelAndView mav = new ModelAndView("pay/callWxAPP");
		
		String code = this.getString("code");
		WXUtil.pickOpenId(code, WXConfig.APPID, WXConfig.WX_SECRET);
		
		String orderId = this.getString("orderId");
		logger.info("========获取的订单orderId======：" + orderId);
		Order order = orderService.selectByPrimaryKey(Integer.valueOf(orderId));
		List<OrderDetail> ordDetailLst = orderDetailService.selectByOrderId(Integer.valueOf(orderId));
		OrderDetail orderDetail = ordDetailLst.get(0);
		CTGoodsDo goods = ctGoodsService.selectById(Long.valueOf(orderDetail.getGoodsId()));
		
		String amount = CalculateUtils.round(order.getTotalprice().doubleValue());
		String price = CalculateUtils.round(orderDetail.getPrice().doubleValue());
		
		Integer userId = order.getUserid();
		
		try {
			BarcodePayMethodBean  barcodePayBean = new BarcodePayMethodBean("68",
																			amount,
																			WXConfig.APPID,
																			"WECHAT"
																			);
			
			String requestIp = this.getIpAddr();
			JSONObject terminalInfo  = new JSONObject();
			terminalInfo.put("ip", requestIp);
			terminalInfo.put("terminal_type", "01");
			
			BizInfoBean  bizinfo = new BizInfoBean("1", 
													ThirdPayConfig.partnerId, 
													"1", 
													requestIp,
													null,
													"", 
													"20601", 
													"API",
													"", 
													"", 
													terminalInfo.toJSONString(), 
													"",
													URLEncoder.encode("http://app.zjzwly.com/barcode/payResult.do","utf-8"));
			
			String  notify_url = URLEncoder.encode("http://app.zjzwly.com/barcode/payResultNotify.do","utf-8");
			
			String goodsName = goods.getTitle();
			TradeInfoBean tradeInfo = new TradeInfoBean(goodsName, 
														order.getOrdercode(), 
														"", 
														price,
														order.getQty().toString(),
														amount, 
														"1", 
														ThirdPayConfig.partnerId,  
														orderId, 
														"",
														notify_url, 
														"", 
														"", 
														"", 
														"",
														"", 
														"");
			
			Result<?> prePayOrderResult= kjtPayService.executeBarcodePay(barcodePayBean,tradeInfo,bizinfo,userId);
			
			if(prePayOrderResult.isSuccess()) {
				String bizContent = (String) prePayOrderResult.getData();
				BarcodeResult  barcodePayResult = JSONObject.parseObject(bizContent,BarcodeResult.class);
				mav.addObject("app_id", barcodePayResult.getApp_id());
				mav.addObject("inst_amount", barcodePayResult.getInst_amount());
				mav.addObject("nonce_str", barcodePayResult.getNonce_str());
				mav.addObject("pay_sign", barcodePayResult.getPay_sign());
				mav.addObject("prepay_id", barcodePayResult.getPrepay_id());
				mav.addObject("partner_id", barcodePayResult.getPartner_id());
				mav.addObject("time_stamp", barcodePayResult.getTime_stamp());
			}
			
			logger.info("扫码后第三方返回：code:"+prePayOrderResult.getCode()+" data:"+prePayOrderResult.getData());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	
	
	/**
	 * 第三方支付异步通知该接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/payResultNotify", method = {RequestMethod.POST,RequestMethod.GET})
	public void notify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ret = "failure";
		try {
			InputStream  inputStream = request.getInputStream();
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String line = "";
			StringBuffer result = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			logger.info("==================第三方支付异步返回支付结果:"+result.toString());

			Map<String, Object> map = JSONObject.parseObject(result.toString(),new TypeReference<Map<String, Object>>() {});

			String status = orderService.thirdPayNotify(map);
			logger.info("===========》》》》》第三方支付處理结果：" + status);
			ret = "success";

		} catch (Exception e) {
			logger.error("第三方支付异步返回支付结果处理失败", e);
			ret = "fail";

		} finally {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(ret);
			out.flush();
			out.close();
			System.out.println("第三方支付异步通知返回：" + ret);
			logger.debug("==========最终返回给第三方支付的验签结果==========" + ret);
		}
	}
	
	
	/**
	 * 微信扫二维码页面的提交
	 * 
	 * 第三方下预付订单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/submitPay", method = RequestMethod.POST)
	public Result<?> submitPay() {
		return null;
	}
	
}
