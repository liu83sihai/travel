package com.dce.business.actions.sms;


import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.service.sms.ISmsService;

@RestController
@RequestMapping("/sms")
public class SmsController extends BaseController {
	
  //  private final static Logger logger = Logger.getLogger(SmsController.class);

    @Resource
    private ISmsService smsService;

    /** 
     * 发送短信 
     * @param page： 扫描支付： scan，  卖单：  sale，  美元转让：  tran
     * @return  
     */
    @RequestMapping(value = "send", method = RequestMethod.POST)
    public Result<?> sendSms() {
        Integer userId = getUserId();
        String page = getString("page");
        Result<?> result = Result.successResult("发送成功");
        try{
        	smsService.send(userId,page);
        }catch(BusinessException be){
        	result = Result.failureResult(be.getMessage());
	    }catch(Exception e){
	    	result = Result.failureResult("请重试");
	    }
        return result;
    }
    
//    /** 
//     * 发送短信
//     * @return  
//     */
//    @RequestMapping(value = "checkSms", method = RequestMethod.POST)
//    public Result<?> checkSms() {
//    	Integer userId = getUserId();
//    	String page = getString("page");
//    	String smsCode = getString("smsCode");
//    	Result result = Result.successResult("验证通过");
//    	try{
//    		boolean ret = smsService.checkSms(userId,page,smsCode);
//    		if(ret == false){
//    			result =  Result.failureResult("验证码错误");
//    		}
//    	}catch(Exception e){
//    		result = Result.failureResult("验证码错误");
//    	}
//    	return result;
//    }
    /** 
     * 发送短信
     * @return  
     */
    @RequestMapping(value = "checkSms", method = {RequestMethod.POST,RequestMethod.GET})
    public Result<?> checkSms() {
    	Integer userId = getUserId();
    	String page = getString("page");
    	String smsCode = getString("smsCode");
    	Result<?> result = Result.successResult("验证通过");
    	try{
    		return smsService.checkSms(userId,page,smsCode);
    	}catch(Exception e){
    		result = Result.failureResult("验证码错误");
    	}
    	return result;
    }

}
