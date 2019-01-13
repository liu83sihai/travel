package com.dce.business.actions.user;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.enums.AccountType;
import com.dce.business.common.enums.IncomeType;
import com.dce.business.common.result.Result;
import com.dce.business.dao.sms.ISmsDao;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.dict.ILoanDictService;
import com.dce.business.service.message.INewsService;
import com.dce.business.service.order.IOrderService;
import com.dce.business.service.user.IUserService;
import com.dce.business.service.user.UserAdressService;
import com.dce.business.service.userCard.IUserCardService;

/**
 * 领红包，签到，广告
 * 
 * @author parudy
 * @date 2018年3月24日
 * @version v1.0
 */
@RestController
@RequestMapping("/loginNotice")
public class LoginNoticeController extends BaseController {
	private final static Logger logger = Logger.getLogger(LoginNoticeController.class);

	@Resource
	private IUserService userService;
	@Resource
	private IAccountService accountService;
	@Resource
	private INewsService newsService;
	@Resource
	private ILoanDictService loanDictService;
	@Resource
	private UserAdressService addressService;
	@Resource 
	private ISmsDao smsDao;
	@Value("${regUrl}")
	private  String regUrl;
	@Resource
	private IUserCardService userCardService;

	@Resource
	private IOrderService orderService;
	
	

	@RequestMapping(value = "", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView toReg() {
        
        ModelAndView mav = new ModelAndView("user/loginNotice");
        mav.addObject("imgname", "timg.jpg");
        return mav;
    }
	
	
	
	@RequestMapping(value = "/click", method = RequestMethod.POST)
	public ModelAndView click() {
		
		ModelAndView  noticeresult = new ModelAndView("user/loginNoticeResult");
		noticeresult.addObject("hongbao", 0);
		Integer userId = this.getUserId();
		if(userId != null) {
			UserAccountDo userAccountDo = new UserAccountDo();
			userAccountDo.setUserId(userId);
			int amt = getRandAmount();
			noticeresult.addObject("hongbao", amt);
			userAccountDo.setAmount(new BigDecimal(amt));
			userAccountDo.setAccountType(AccountType.wallet_goods.getAccountType());
			accountService.updateUserAmountById(userAccountDo , IncomeType.TYPE_SHARED);
		}
		return noticeresult ;
	}
	
	
	private int getRandAmount(){
        Date date = new Date();
        long timeMill = date.getTime();
        Random rand = new Random(timeMill);
        return rand.nextInt(20);
    }
	

}
