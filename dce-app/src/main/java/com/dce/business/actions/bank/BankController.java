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
import com.dce.business.common.token.TokenUtil;
import com.dce.business.entity.bank.BankCardDo;
import com.dce.business.entity.bank.BankDo;
import com.dce.business.entity.order.Order;
import com.dce.business.service.bank.IBankCardService;
import com.dce.business.service.bank.IBankService;
import com.dce.business.service.order.IOrderService;

@RestController
@RequestMapping("bank")
public class BankController extends BaseController {
	private Logger logger = Logger.getLogger(BankController.class);
    @Resource
    private IBankService bankService;
    
    @Resource
    private IBankCardService bankCardService;
    
    @Resource
    private  IOrderService  orderService;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<?> getBankList() {
        List<BankDo> list = bankService.getBankList();
        return Result.successResult("查询成功", list);
    }
    
    /**
     *	 去添加新的银行卡信息
     * @return
     */
    @RequestMapping("/toAddBankCard")
    public ModelAndView toAddBankCard(HttpServletRequest request,Model model){
    	
    	BankCardDo bank = this.makeBankAndCommonParam(request, model);
		//已绑卡
		if(bank != null && bank.getCardStatus().intValue() == 1) {
			return toBankCardManager(request,model);
		}
		
		ModelAndView mav = new ModelAndView("bank/bindBankCard");
    	//银行列表
    	List<BankDo> list = bankService.getBankList();
    	mav.addObject("bankCodes",list);
    	return mav;
    }
    
    
   
    private BankCardDo makeBankAndCommonParam(HttpServletRequest request,Model model){
    	
    	//app 传来的参数
    	Integer userId = this.getUserId();
    	String ts = request.getParameter(TokenUtil.TS);
        String sign = request.getParameter(TokenUtil.SIGN);
        
        request.setAttribute(TokenUtil.TS , ts);
        request.setAttribute(TokenUtil.SIGN , sign);
        request.setAttribute(TokenUtil.USER_ID , userId);
    	
    	//查询是否已绑卡
		String isDefault = "1";
		List<BankCardDo> bankLst = bankCardService.getByUserIdAndIsDefault(Long.valueOf(userId), isDefault );
		BankCardDo bank = null;
		if(bankLst != null && bankLst.size()>0  ) {
			bank = bankLst.get(0);
			request.setAttribute("bank", bank);
		}
		return bank;
    }
    
    
    /**
     *	 去添加新的银行卡信息
     * @return
     */
    @RequestMapping("/bankCardPay")
    public ModelAndView BankCardPay(HttpServletRequest request,Model model){
    	
    	ModelAndView mav = new ModelAndView("bank/bankCardPay");
    	BankCardDo bank = this.makeBankAndCommonParam(request,model);
    	//未绑卡,去绑卡
    	if(bank != null && bank.getCardStatus().intValue() != 1) {
    		return toAddBankCard(request,model);
    	}
    	//支付订单id
    	String orderId = request.getParameter("orderId");
    	Order order = orderService.selectByPrimaryKey(Integer.valueOf(orderId));
    	mav.addObject("order", order);    	
    	return mav;
    }
    
    
    /**
     *	 去添加新的银行卡信息
     * @return
     */
    @RequestMapping("/submitPay")
    public Result<?> submitPay(HttpServletRequest request,Model model){
    	//支付订单id
    	String orderId = request.getParameter("orderId");
    	return bankCardService.pay(orderId);
    }
    
    
    
    
    /**
     *	 验证卡信息获取验证码
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
            	return Result.failureResult("银行开户名不能为空");
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
        	
        	Result<?> retResult = bankCardService.getBankCardCode(idNo,cardUserName,mobile,cardNo,userId);
        	return retResult;
    	}catch (Throwable e){
        	logger.error("error", e);
            return Result.failureResult("系统正忙请稍后重试");
        }
    }
    
    
    /**
              *   绑定银行卡
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
        	String tokenId = getString("externalRefNumber"); //协议支付号
        	
        	String money = getString("payMoney");
        	String orderCode = getString("orderCode");
        	
        	
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
        	if(StringUtils.isBlank(token) || StringUtils.isBlank(tokenId)){
            	return Result.failureResult("请获取验证码");
            }
        	
        	Result<?> retResult = bankCardService.bindBankCard(userId,bankId,mobileCode,mobile,cardNo,idNo,tokenId,money,cardUserName,orderCode);
        	
        	return retResult;
        }catch (Throwable e){
        	logger.error("error", e);
            return Result.failureResult("系统正忙请稍后重试");
        }
    }
    
    
    /**
     * 	去银行卡管理页面
     * @return
     */
    @RequestMapping("/toBindBankCard")
    public ModelAndView toBankCardManager(HttpServletRequest request,Model model){
    	
    	ModelAndView mav = new ModelAndView("bank/bankCardManager");
    	
    	//app 传来的参数
    	Integer userId = this.getUserId();
    	String ts = request.getParameter(TokenUtil.TS);
        String sign = request.getParameter(TokenUtil.SIGN);
        
        request.setAttribute(TokenUtil.TS , ts);
        request.setAttribute(TokenUtil.SIGN , sign);
        request.setAttribute(TokenUtil.USER_ID , userId);
        
    	
    	String isDefault = "1";
    	List<BankCardDo>  list = bankCardService.getByUserIdAndIsDefault(Long.valueOf(userId), isDefault);
    	//处理显示卡变*************1254
    	if(list != null && list.size() > 0){
    		for (BankCardDo bankCardDo : list) {
    			String cardNo = bankCardDo.getCardNo();
    			if(StringUtils.isNotBlank(cardNo)){
    				int count = cardNo.length() - 4;
    				String xCount = "";
    				for (int i = 0; i < count; i++) {
    					xCount += "*";
					}
    				bankCardDo.setCardNoShow(xCount+cardNo.substring(cardNo.length() - 4,cardNo.length()));
    			}
			}
    	}
    	model.addAttribute("bankCardList",list);
    	
    	return mav;
    }
}
