package com.dce.business.actions.pay;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
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
	@RequestMapping(value = "/doPay", method = RequestMethod.POST)
	public ModelAndView doPay() {
		
		ModelAndView mav = new ModelAndView("pay/callWxAPP");
		
		String code = this.getString("code");
		WXUtil.pickOpenId(code, WXConfig.APPID, WXConfig.WX_SECRET);
		
		String outTradeNo = getString("outTradeNo") == null ? "" : getString("outTradeNo");
		if (outTradeNo == "") {
			Result.failureResult("支付宝订单参数outTradeNo为空！");
		}
		logger.info("========获取的订单号======：" + outTradeNo);

		Integer userId = getUserId();
		if(userId == null){
			//throw new BusinessException("无效的用户id");
			userId = 1;
		}
		
		String amount = "0.01";
		
		try {
			Result<?> prePayOrderResult= kjtPayService.executeBarcodePay(amount, userId);
			if(prePayOrderResult.isSuccess()) {
				String bizContent = (String) prePayOrderResult.getData();
			}
			logger.info("扫码后第三方返回：code:"+prePayOrderResult.getCode()+" data:"+prePayOrderResult.getData());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return mav;
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
		
		String code = this.getString("code");
		WXUtil.pickOpenId(code, WXConfig.APPID, WXConfig.WX_SECRET);
		
		String outTradeNo = getString("outTradeNo") == null ? "" : getString("outTradeNo");
		if (outTradeNo == "") {
			Result.failureResult("订单参数outTradeNo为空！");
		}
		logger.info("========获取的订单号======：" + outTradeNo);
		
		Integer userId = getUserId();
		if(userId == null){
			//throw new BusinessException("无效的用户id");
			userId = 1;
		}
		
		String amount = "0.01";
		
		try {
			//第三方下预付订单
			return kjtPayService.executeBarcodePay(amount, userId);
		} catch (Throwable e) {
			e.printStackTrace();
			return Result.failureResult(e.getMessage());
		}
	}
	
}
