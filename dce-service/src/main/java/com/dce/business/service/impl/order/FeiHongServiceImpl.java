package com.dce.business.service.impl.order;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dce.business.common.enums.IncomeType;
import com.dce.business.common.util.CalculateUtils;
import com.dce.business.common.util.DateUtil;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.dict.LoanDictDo;
import com.dce.business.entity.order.FeiHongLog;
import com.dce.business.entity.order.FeiHongOrder;
import com.dce.business.entity.order.UserFeiHong;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.dict.ILoanDictService;
import com.dce.business.service.order.IFeiHongService;
import com.dce.business.service.order.IOrderService;

@Service("feiHongService")
public class FeiHongServiceImpl implements IFeiHongService {
	private final static Logger logger = Logger.getLogger(FeiHongServiceImpl.class);
	
	@Resource
	private ILoanDictService dictService;

	// 账户
	@Resource
	private IAccountService accountService;

	@Resource
	private IOrderService orderService;
	

	@Override
	public void doFeiHong(FeiHongOrder fhorder) {
		logger.warn("开始计算分红"+fhorder.getOrderid());
		String errMsg = "";
		BigDecimal wardAmount = BigDecimal.ZERO;
		//check
		boolean canFeiHong =chkFeiHongDate(fhorder);
		if(canFeiHong == false) {
			return;
		}
		if( !"1".equals(fhorder.getOrderstatus())) {
			logger.warn("分红订单状态无效："+fhorder.toString());
			errMsg = "分红订单状态无效";
			return;
		}
		
		UserFeiHong userFeiHong = orderService.selectFeiHongUser(fhorder.getUserid());
		if(null == userFeiHong) {
			errMsg = "用户没有购买订单不能参与分红";
			logger.warn("用户没有购买订单不能参与分红:"+fhorder.getUserid());
			return;
		}
		canFeiHong = checkFeiHongMaxAmt(userFeiHong);
		if(canFeiHong == true) {
			errMsg = "已超出分红上限";
			logger.warn("已超出分红上限:"+fhorder.getOrderid());
			return;
		}
		
		try {
			
			// 得到分红配置
			String[] feiHongConfig=getFeiHongConfig();
			
			// 计算分红金额
			BigDecimal rate = fhorder.getFeihongrate();
			wardAmount = fhorder.getTotalprice().multiply(rate);
			if (wardAmount.compareTo(BigDecimal.ZERO) <= 0) {
				errMsg="分红金额为0";
				return;
			}
			
			BigDecimal newTotalFeiHongAmt = CalculateUtils.add(userFeiHong.getFeihongamt(),wardAmount);
			if(newTotalFeiHongAmt.compareTo(userFeiHong.getOrderaward())>0) {
				logger.warn("已超出订单收益:"+userFeiHong.getUserid());
				wardAmount = new BigDecimal(CalculateUtils.sub(userFeiHong.getOrderaward().doubleValue(), userFeiHong.getFeihongamt().doubleValue()));
			}
			orderService.updateUserFeiHongAmt(userFeiHong.getUserid(), wardAmount);
			//按账户分配
			for(String feihongAccountConfig : feiHongConfig) {
				String[] oneAccountConfigs = feihongAccountConfig.split("-");
				BigDecimal moneyRate = new BigDecimal(oneAccountConfigs[1]);
				BigDecimal moneyAccount = wardAmount.multiply(moneyRate);
				moneyAccount = new BigDecimal(CalculateUtils.round(moneyAccount.doubleValue(), 2));
				IncomeType incomeTp = IncomeType.TYPE_STATIC;
				//现金账户
				UserAccountDo accontMoney = new UserAccountDo(moneyAccount, 
															  fhorder.getUserid(), 
															  oneAccountConfigs[0]);
				buildAccountRemark(accontMoney,fhorder);
				// 账户对象增加金额
				accountService.updateUserAmountById(accontMoney, incomeTp);
			}
			
			logger.warn("结束计算分红"+fhorder.getOrderid());
		}catch(Exception e) {
			logger.error("分红出错:"+fhorder.toString());
			logger.error(e);
			errMsg=e.getMessage()==null?"分红出错":e.getMessage().substring(0,200);
		}finally {
			logFeiHong(fhorder,errMsg,wardAmount);
		}
	}
	

	private String[] getFeiHongConfig() {
		LoanDictDo dictDo  = dictService.getLoanDict("fenhong-account-cfg");
		return dictDo.getRemark().split(";");
	}


	private void logFeiHong(FeiHongOrder fhorder,String errMsg,BigDecimal wardAmount) {
		
		BigDecimal total = new BigDecimal(CalculateUtils.round(wardAmount.doubleValue(), 2));
		
		if(StringUtils.isBlank(errMsg)) {
			double totalFeiHong = CalculateUtils.add(total.doubleValue(), fhorder.getTotalfeihongamt().doubleValue());
			fhorder.setTotalfeihongamt(new BigDecimal(totalFeiHong));
			fhorder.setLastrundate(new Date());
			orderService.updateFeiHong(fhorder);
		}
		
		FeiHongLog fhlog = new FeiHongLog();
		fhlog.setCreatetime(new Date());
		fhlog.setOrderid(fhorder.getOrderid());
		fhlog.setFeihongamt(total);
		fhlog.setFeihongrate(fhorder.getFeihongrate());
		fhlog.setUserid(fhorder.getUserid());
		fhlog.setMsg(errMsg);
		orderService.logFeiHong(fhlog);
		
	}


	/**
	 * 	检查是否超出分红上限
	 * @param fhorder
	 * @return
	 */
	private boolean checkFeiHongMaxAmt(UserFeiHong userFeiHong) {
		if(null == userFeiHong) {
			return false;
		}
		int compareResult = userFeiHong.getFeihongamt().compareTo(userFeiHong.getOrderaward());
		return compareResult>=0;
	}


	/**
	 * 	是否是能整除7
	 * @param fhorder
	 * @return
	 */
	private  boolean chkFeiHongDate(FeiHongOrder fhorder) {
		 Date startDate = DateUtils.ceiling(fhorder.getStartdate(), Calendar.DAY_OF_MONTH);
		 Date endDate = DateUtils.ceiling(new Date(), Calendar.DAY_OF_MONTH);
		 int diffDays = DateUtil.diffdays(startDate, endDate);
		return diffDays%7 == 0;
	}

	/**
	 * 	创建奖励备注
	 * 
	 * @param account
	 */
	private void buildAccountRemark(UserAccountDo account,FeiHongOrder fhorder) {
		StringBuffer sb = new StringBuffer();
		sb.append("订单").append(fhorder.getOrdercode()).append("分红获得:")
				.append(CalculateUtils.round(account.getAmount().doubleValue()));
		account.setRemark(sb.toString());
		account.setRelevantUser(String.valueOf(fhorder.getBuyerid()));// 关联用户
	}
	

	public static void main(String[] args) {
		
	}

}
