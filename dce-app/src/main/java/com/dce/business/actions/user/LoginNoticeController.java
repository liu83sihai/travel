package com.dce.business.actions.user;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
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
import com.dce.business.entity.user.UserDo;
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
	
	/**
	 * 	随机奖励范围
	 */
	private int range1 = 20;
	private int range2 = 200;
	
	
	

	@RequestMapping(value = "", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView toReg() {
        
        ModelAndView mav = new ModelAndView("user/loginNotice");
        mav.addObject("imgname", "timg.jpg");
        return mav;
    }
	
	

	@RequestMapping(value = "/loginNoticeResult", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView loginNoticeResult() {
        
        ModelAndView mav = new ModelAndView("user/loginNoticeResult");
        String hongbao = this.getString("hongbao");
        mav.addObject("hongbao", hongbao);
        String incomeType = this.getString("incomeType");
        String msg = "恭喜你获得红包";
        if(IncomeType.TYPE_REGISTER.name().equals(incomeType)) {
        	msg="恭喜你获得新人奖红包";
        }
        mav.addObject("msg", msg);
        return mav;
    }
	
	
	
	@RequestMapping(value = "/click", method = RequestMethod.POST)
	public Result<?> click() {
		
		
		int amt = 0; 
		Integer userId = this.getUserId();
		IncomeType inTyp = IncomeType.TYPE_HONGBAO;
		
		if(userId != null) {
			UserAccountDo userAccountDo = new UserAccountDo();
			userAccountDo.setUserId(userId);
			//如果是当前给他新人奖
			UserDo user = userService.getUser(userId);
			Date regTime = new Date (user.getRegTime());
			Date currentDay = new Date();
			currentDay = DateUtils.setHours(currentDay, 0);
			currentDay = DateUtils.setMinutes(currentDay, 0);
			currentDay = DateUtils.setSeconds(currentDay, 0);
			currentDay = DateUtils.setMilliseconds(currentDay, 0);
			
			
			if(regTime.after(currentDay)) {
				amt = getRandAmount(range2);
				inTyp = IncomeType.TYPE_REGISTER;
			}else {
				amt = getRandAmount(range1);				
			}
			userAccountDo.setAmount(new BigDecimal(amt));
			userAccountDo.setAccountType(AccountType.wallet_goods.getAccountType());
			if(amt > 0 ) {
				accountService.updateUserAmountById(userAccountDo , inTyp);
			}
		}
		Map<String,Object> ret = new HashMap<String,Object>();
		ret.put("incomeType", inTyp.getIncomeType());
		ret.put("amt",amt);
		return Result.successResult("ok", ret) ;
	}
	
	
	private int getRandAmount(int range){
        Date date = new Date();
        long timeMill = date.getTime();
        Random rand = new Random(timeMill);
        return rand.nextInt(range);
    }
	

}
