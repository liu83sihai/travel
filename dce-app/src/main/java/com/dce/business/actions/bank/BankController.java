package com.dce.business.actions.bank;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.entity.bank.BankCardDo;
import com.dce.business.entity.bank.BankDo;
import com.dce.business.service.bank.IBankCardService;
import com.dce.business.service.bank.IBankService;
import com.dce.business.service.pay.IKJTPayService;

@RestController
@RequestMapping("bank")
public class BankController extends BaseController {
	private Logger logger = Logger.getLogger(BankController.class);
    @Resource
    private IBankService bankService;
    
    @Resource
    private IBankCardService bankCardService;
    
    @Resource
    private IKJTPayService  kjtPayService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<?> getBankList() {
        List<BankDo> list = bankService.getBankList();
        return Result.successResult("查询成功", list);
    }
    
    /**
     * 去添加新的银行卡信息
     * @return
     */
    @RequestMapping("/toBindBankCard")
    public ModelAndView toBindCard(HttpServletRequest request){
    	
    	ModelAndView mav = new ModelAndView("bank/bindBankCard");
    	List<BankDo> list = bankService.getBankList();
    	mav.addObject("bankCodes",list);
		String mobile = "";
		String realName = "";
		String idNo = "";
		String bankCardNum = "";
		mav.addObject("realName",realName);
		mav.addObject("idNo",idNo);
		mav.addObject("mobile",mobile);
		mav.addObject("bankCardNum",bankCardNum);
    	return mav;
    }
    
    
    /**
     * 验证卡信息获取验证码
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/getBankCardCode")
    public Result<?> getBankCardCode(HttpServletRequest request,Model model) {
    	try {
    		
    		Integer userId = getUserId();
	    	String bankId = getString("bankId");//
	    	String cardUserName = getString("cardUserName");
	    	String cardNo = getString("cardNo");
	    	String mobile = getString("mobile");//
	    	String idNo = getString("idNo");//
	    	
	  
        	if(StringUtils.isBlank(cardUserName)){
            	return Result.failureResult("银行所属户名不能为空");
            }
        	if(StringUtils.isBlank(mobile)){
            	return Result.failureResult("手机号不能为空");
            }
        	if(StringUtils.isBlank(cardNo)){
            	return Result.failureResult("银行卡号不能为空");
            }
        	if(StringUtils.isBlank(idNo)){
            	return Result.failureResult("身份证不能为空");
            }
        	
        	Result<?> retResult = kjtPayService.executeGetBankCardCode(idNo,cardUserName,mobile,cardNo);
        	return retResult;
    	}catch (Throwable e){
        	logger.error("error", e);
            return Result.failureResult("系统正忙请稍后重试");
        }
    }
    
    
    
    /**
     * 验证卡信息获取验证码
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/checkCardGetCode")
    public Result<?> checkCardGetCode(HttpServletRequest request,Model model) {
    	try {
    		Integer userId = getUserId();
	    	String bankId = getString("bankId");//
	    	String cardUserName = getString("cardUserName");
	    	String cardNo = getString("cardNo");
	    	String mobile = getString("mobile");//
	    	String idNo = getString("idNo");//
	    	
	  
        	if(StringUtils.isBlank(cardUserName)){
            	return Result.failureResult("银行所属户名不能为空");
            }
        	if(StringUtils.isBlank(mobile)){
            	return Result.failureResult("手机号不能为空");
            }
        	if(StringUtils.isBlank(cardNo)){
            	return Result.failureResult("银行卡号不能为空");
            }
        	if(StringUtils.isBlank(idNo)){
            	return Result.failureResult("身份证不能为空");
            }
        	
        	
        	BankCardDo card = bankCardService.selectBankcardByCardNo(cardNo,null,null);
        	Result<?> retResult = kjtPayService.executeCheckCode();
        	return retResult;
    	}catch (Throwable e){
        	logger.error("error", e);
            return Result.failureResult("系统正忙请稍后重试");
        }
    }
    
    /**
     * 绑定银行卡
     * @param request
     * @return
     */
    @RequestMapping(value = "/bindBankCard")
    @ResponseBody
    public Result<?> bindBank(HttpServletRequest request,Model model) {
        try {
        	Integer userId = this.getUserId();
        	String bankId = getString("bankId");//
        	String branchBankName = getString("branchBankName");//
        	String cardUserName = getString("cardUserName");
        	String cardNo = getString("cardNo");//
        	String mobile = getString("mobile");
        	String mobileCode = getString("mobileCode");
        	String addressInput = getString("addressInput");
        	String idNo = getString("idNo");//
        	String token = getString("token");//
        	String externalRefNumber = getString("externalRefNumber");
        	String userType = getString("userType");//0  主借款人   2 共同借款人  3 有房担保人
        	if(StringUtils.isBlank(userType)){
        		userType="0";
        	}
        	if(("1").equals(userType)){
        		userType = "2";
    		}else if(("2").equals(userType)){
    			userType = "3";
    		}else{
    			userType = "0";
    		}
        	
        	if(StringUtils.isBlank(bankId)){
            	return Result.failureResult("所属银行不能为空");
            }
        	if(StringUtils.isBlank(cardUserName)){
            	return Result.failureResult("银行所属户名不能为空");
            }
        	if(StringUtils.isBlank(mobile)){
            	return Result.failureResult("手机号不能为空");
            }
        	if(StringUtils.isBlank(mobileCode)){
            	return Result.failureResult("验证码不能为空");
            }
        	if(StringUtils.isBlank(cardNo)){
            	return Result.failureResult("银行卡号不能为空");
            }
        	if(StringUtils.isBlank(addressInput)){
            	return Result.failureResult("地理位置不能为空");
            }
        	if(StringUtils.isBlank(idNo)){
            	return Result.failureResult("身份证不能为空");
            }
        	if(StringUtils.isBlank(token) || StringUtils.isBlank(externalRefNumber)){
            	return Result.failureResult("请获取验证码");
            }
        	
        	
        }catch (Exception e){
        	logger.error("error", e);
            return Result.failureResult("系统正忙请稍后重试");
        }
        return Result.failureResult("系统正忙请稍后重试");
    }
}
