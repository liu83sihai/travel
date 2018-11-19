package com.dce.business.actions.bank;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.dce.business.entity.bank.BankDo;
import com.dce.business.service.bank.IBankService;

@RestController
@RequestMapping("bank")
public class BankController extends BaseController {
	private Logger logger = Logger.getLogger(BankController.class);
    @Resource
    private IBankService bankService;

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
            	return ResultSupport.buildResult("3","银行所属户名不能为空");
            }
        	if(StringUtils.isBlank(mobile)){
            	return ResultSupport.buildResult("3","手机号不能为空");
            }
        	if(StringUtils.isBlank(cardNo)){
            	return ResultSupport.buildResult("2","银行卡号不能为空");
            }
        	if(StringUtils.isBlank(idNo)){
            	return ResultSupport.buildResult("2","身份证不能为空");
            }
        	
        	//组装银行code数据
        	Map<String, Object> param = new HashMap<String, Object>();
        	param = new HashMap<String,Object>();
        	param.put("bankCodeId", bankId);
        	List<BankCodeDo> bankCodes = bankCodeService.selectTdBankCode(param);
        	BankCodeDo bankCode = null;
        	if(bankCodes != null && bankCodes.size() > 0){
        		bankCode = bankCodes.get(0);
        		String regex = bankCode.getRegex();
        		if(StringUtils.isNotBlank(regex)){ //广发银行，和工商银行没有给到规则
	        		regex = regex.replace("[", "").replace("]", "").replace("\"", "");
	        		String[] reg = regex.split(",");
	        		int count = 0;
	        		if(reg.length > 0){
	        			for (int i = 0; i < reg.length; i++) {
	        				if(cardNo.startsWith(reg[i])){
	        					count ++;
	        				}
						}
	        		}
	        		if(count == 0){
	        			logger.info("银行卡号"+cardNo+"所属银行验证失败");
	        			return ResultSupport.buildResult("10","银行卡号"+cardNo+"所属银行验证失败");
	        		}
        		}
        	}else{
        		return ResultSupport.buildResult("4","银行信息错误");
        	}
        	
        	//验证银行卡号是否已经绑定
        	param = new HashMap<String, Object>();
        	param.put("cardNo", cardNo);
        	//param.put("cardStatus", 1);
        	param.put("payChannelType","0");//0-快钱渠道，1-通联渠道
        	param.put("cardType","0");//0-对私,1-对公
        	BankCardDo card = bankCardService.selectBankcardByCardNo(param);
        	if(card != null ){
        		if(card.getCardStatus() == 1){
        			if(!card.getCardNo().equals(cardNo)){
            			logger.info("查询匹配银行卡信息错误");
            			return ResultSupport.buildResult("11","查询匹配银行卡信息错误");
            		}
            		if(!card.getUserId().equals(userId)){
            			logger.info("银行卡号"+cardNo+"已被其它帐号绑定");
            			return ResultSupport.buildResult("8","银行卡号"+cardNo+"已被其它帐号绑定");
            			
            		}else{
            			logger.info("银行卡号"+cardNo+"已被绑定");
            			return ResultSupport.buildResult("9","银行卡号"+cardNo+"已被绑定");
            		}
        		}else if (card.getCardStatus() == 4){
        			if(!card.getUserId().equals(userId)){
            			logger.info("银行卡号"+cardNo+"已被其它帐号绑定");
            			return ResultSupport.buildResult("8","银行卡号"+cardNo+"已被其它帐号绑定");
            			
            		}/*else{
            			logger.info("银行卡号"+cardNo+"已被绑定");
            			return ResultSupport.buildResult("9","银行卡号"+cardNo+"已被绑定");
            		}*/
        		}
        	}
        	
        	//第三方同一银行只能绑定一张借记卡和一张借贷卡，我们只做借记卡
        	param = new HashMap<String, Object>();
        	param.put("userId", userId);
        	//param.put("cardStatus", 1);
        	param.put("bankCode", bankCode.getBankCode());
        	param.put("payChannelType","0");//0-快钱渠道，1-通联渠道
        	param.put("cardType","0");//0-对私,1-对公
        	List<BankCardDo> listBankCard = bankCardService.selectBankcard(param);
        	if(listBankCard != null && listBankCard.size() > 1){
        		return ResultSupport.buildResult("5","同一银行只能绑定一张卡");
        	}
        	
        	Map<String, String> respParam = quickPayService.checkCard(bankCode.getBankCode(),userId,cardUserName,cardNo,idNo,mobile,userType);
        	if(respParam == null){
	    		return ResultSupport.buildResult("4","第三方系统正忙,稍候重试");
	    	}
        	if("00".equals(respParam.get("responseCode"))){
	    		ResultSupport<Map> result = new ResultSupport("0","验卡成功,验证码已发送到手机"); 
    			result.setModel(respParam);
	    		return result;
	    	}else{
	    		return ResultSupport.buildResult("15",respParam.get("responseTextMessage"));
	    	}
    	}catch (Exception e){
        	logger.error("error", e);
            return ResultSupport.buildResult("1","系统正忙请稍后重试");
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
        	Long userId = getUserId(request);
        	String bankId = getString(request,"bankId");//
        	String branchBankName = getString(request,"branchBankName");//
        	String cardUserName = getString(request,"cardUserName");
        	String cardNo = getString(request,"cardNo");//
        	String mobile = getString(request,"mobile");
        	String mobileCode = getString(request,"mobileCode");
        	String addressInput = getString(request,"addressInput");
        	String idNo = getString(request,"idNo");//
        	String token = getString(request,"token");//
        	String externalRefNumber = getString(request,"externalRefNumber");
        	String userType = BaseCommonUtil.getString(request, "userType");//0  主借款人   2 共同借款人  3 有房担保人
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
            	return ResultSupport.buildResult("2","所属银行不能为空");
            }
        	if(StringUtils.isBlank(cardUserName)){
            	return ResultSupport.buildResult("2","银行所属户名不能为空");
            }
        	if(StringUtils.isBlank(mobile)){
            	return ResultSupport.buildResult("2","手机号不能为空");
            }
        	if(StringUtils.isBlank(mobileCode)){
            	return ResultSupport.buildResult("2","验证码不能为空");
            }
        	if(StringUtils.isBlank(cardNo)){
            	return ResultSupport.buildResult("2","银行卡号不能为空");
            }
        	if(StringUtils.isBlank(addressInput)){
            	return ResultSupport.buildResult("2","地理位置不能为空");
            }
        	if(StringUtils.isBlank(idNo)){
            	return ResultSupport.buildResult("2","身份证不能为空");
            }
        	if(StringUtils.isBlank(token) || StringUtils.isBlank(externalRefNumber)){
            	return ResultSupport.buildResult("2","请获取验证码");
            }
        	
        	//新业务 只能绑定一张卡
        	BankCardDo cardHave = bankCardService.selectDefaultBankcard(userId,"0","0");
        	if(cardHave != null){
        		return ResultSupport.buildResult("12","暂时只能绑定一张卡");
        	}
        	
        	//验证银行卡号是否已经绑定
        	Map<String, Object> param = new HashMap<String, Object>();
        	param = new HashMap<String, Object>();
        	param.put("cardNo", cardNo);
        	//param.put("cardStatus", 1);
        	param.put("payChannelType","0");//0-快钱渠道，1-通联渠道
        	param.put("cardType","0");//0-对私,1-对公
        	BankCardDo card = bankCardService.selectBankcardByCardNo(param);
        	if(card != null ){
        		if(card.getCardStatus() == 1){
        			if(!card.getCardNo().equals(cardNo)){
            			logger.info("查询匹配银行卡信息错误");
            			return ResultSupport.buildResult("11","查询匹配银行卡信息错误");
            		}
            		if(!card.getUserId().equals(userId)){
            			logger.info("银行卡号"+cardNo+"已被其它帐号绑定");
            			return ResultSupport.buildResult("8","银行卡号"+cardNo+"已被其它帐号绑定");
            			
            		}else{
            			logger.info("银行卡号"+cardNo+"已被绑定");
            			return ResultSupport.buildResult("9","银行卡号"+cardNo+"已被绑定");
            		}
        		}else if (card.getCardStatus() == 4){
        			if(!card.getUserId().equals(userId)){
            			logger.info("银行卡号"+cardNo+"已被其它帐号绑定");
            			return ResultSupport.buildResult("8","银行卡号"+cardNo+"已被其它帐号绑定");
            			
            		}/*else{
            			logger.info("银行卡号"+cardNo+"已被绑定");
            			return ResultSupport.buildResult("9","银行卡号"+cardNo+"已被绑定");
            		}*/
        		}
        	}
        	
        	//组装银行code数据
        	param = new HashMap<String,Object>();
        	param.put("bankCodeId", bankId);
        	List<BankCodeDo> bankCodes = bankCodeService.selectTdBankCode(param);
        	BankCodeDo bankCode = null;
        	if(bankCodes != null && bankCodes.size() > 0){
        		bankCode = bankCodes.get(0);
        		String regex = bankCode.getRegex();
        		if(StringUtils.isNotBlank(regex)){ //广发银行，和工商银行没有给到规则
	        		regex = regex.replace("[", "").replace("]", "").replace("\"", "");
	        		String[] reg = regex.split(",");
	        		int count = 0;
	        		if(reg.length > 0){
	        			for (int i = 0; i < reg.length; i++) {
	        				if(cardNo.startsWith(reg[i])){
	        					count ++;
	        				}
						}
	        		}
	        		if(count == 0){
	        			logger.info("银行卡号"+cardNo+"所属银行验证失败");
	        			return ResultSupport.buildResult("10","银行卡号"+cardNo+"所属银行验证失败");
	        		}
        		}
        	}else{
        		return ResultSupport.buildResult("4","银行信息错误");
        	}
        	
        	
        	
        	String[] addArr = addressInput.split(",");
        	String provinceId = null;//
        	String cityId = null;
        	String region = null;

        	String province = null;//
        	String city = null;
        	String regionStr = null;
        	
        	if(addArr.length == 2){
        		provinceId = addArr[0];
        		if(provinceId != null){
        			LoanCityDo cityDo = loanCityService.getCityByCityCode(provinceId);
        			province = cityDo.getCityName();
        		}
        		cityId = addArr[1];
        		if(cityId != null){
        			LoanCityDo cityDo = loanCityService.getCityByCityCode(cityId);
        			city = cityDo.getCityName();
        		}
        	}else if(addArr.length == 3){
        		provinceId = addArr[0];
        		if(provinceId != null){
        			LoanCityDo cityDo = loanCityService.getCityByCityCode(provinceId);
        			province = cityDo.getCityName();
        		}
        		cityId = addArr[1];
        		if(cityId != null){
        			LoanCityDo cityDo = loanCityService.getCityByCityCode(cityId);
        			city = cityDo.getCityName();
        		}
        		region = addArr[2];
        		if(region != null){
        			LoanCityDo cityDo = loanCityService.getCityByCityCode(region);
        			regionStr = cityDo.getCityName();
        		}
        	}
        	
        	//第三方同一银行只能绑定一张借记卡和一张借贷卡，我们只做借记卡
        	param = new HashMap<String, Object>();
        	param.put("userId", userId);
        	param.put("cardStatus", 1);
        	param.put("bankCode", bankCode.getBankCode());
        	param.put("payChannelType","0");//0-快钱渠道，1-通联渠道
        	param.put("cardType","0");//0-对私,1-对公
        	List<BankCardDo> listBankCard = bankCardService.selectBankcard(param);
        	if(listBankCard != null && listBankCard.size() > 1){
        		return ResultSupport.buildResult("5","同一银行只能绑定一张卡");
        	}
        	
        	//去块钱绑卡
        	//绑卡   建行借记卡：6227001376710050739   6227001376710050739
    		//reqXml.put("pan", "6227001376710050739");//
    		//reqXml.put("phoneNO", "18823704752");// 手机号码
    		//reqXml.put("validCode", "995893");// 手机验证码
    		//reqXml.put("token", "1167676");// 令牌
    		//reqXml.put("bankCode", "CCB");
        	//reqXml.put("customerId", "3965865");
    		//q.bindCardStep2(reqXml);
        	//quickPayService.checkCard(bankCode.getBankCode(),userId,cardUserName,cardNo,idNo,mobile);
        	Map<String,String> respParam = quickPayService.bindCard(bankCode.getBankCode(),userId,cardUserName,cardNo,idNo,mobile,mobileCode,token,externalRefNumber,userType);
        	if(respParam == null){
	    		return ResultSupport.buildResult("4","第三方系统正忙,稍候重试");
	    	}
        	if("00".equals(respParam.get("responseCode"))){
        		logger.info("块钱端绑卡成功......用户ID="+userId+"户名="+cardUserName+"卡号="+cardNo);
        		BankCardDo  bankCard = new BankCardDo();
            	bankCard.setBankName(bankCode.getBankName());
            	bankCard.setOpenBankCode(bankCode.getCode());
            	bankCard.setBankCode(bankCode.getBankCode());
            	bankCard.setBranchBankName(branchBankName);
            	bankCard.setCardUserName(cardUserName);
            	bankCard.setCardNo(cardNo);
            	bankCard.setProvinceId(provinceId);
            	bankCard.setCityId(cityId);
            	bankCard.setRegion(region);
            	bankCard.setProvince(province);
            	bankCard.setCity(city);
            	bankCard.setRegionStr(regionStr);
            	bankCard.setPayChannelType("0");
            	bankCard.setUserId(userId);
            	bankCard.setCardMode(1);
            	bankCard.setCardStatus(1);
            	bankCard.setBankMobile(mobile);
            	Date date = new Date();
            	bankCard.setCreateTime(date);
            	bankCard.setUpdateTime(date);
            	
            	bankCard.setIsDefault(1);
            	bankCard.setIsExpress(1);
            	bankCard.setPayChannelType("0");
            	bankCard.setCardType("0");
            	
            	Result<?> result = bankCardService.addCardInfoLingShi(bankCard);
            	if(result.isSuccess()){
            		return Result.successResult("绑卡成功");
            	}
	    	}else if("C0".equals(respParam.get("responseCode"))){
	    		//等待块钱返回 =======暂未处理
	    		return ResultSupport.buildResult("1",respParam.get("responseTextMessage"));
	    	}else{
	    		return ResultSupport.buildResult("1",respParam.get("responseTextMessage"));
	    	}
        }catch (Exception e){
        	logger.error("error", e);
            return Result.failureResult("系统正忙请稍后重试");
        }
        return Result.failureResult("系统正忙请稍后重试");
    }
}
