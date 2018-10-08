package com.dce.business.service.impl.trade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dce.business.common.enums.AccountType;
import com.dce.business.common.enums.IncomeType;
import com.dce.business.common.enums.MessageType;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.pay.util.Trans;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.Constants;
import com.dce.business.common.util.DataEncrypt;
import com.dce.business.dao.etherenum.IEthereumTransInfoDao;
import com.dce.business.dao.trade.IWithdrawalsDao;
import com.dce.business.dao.user.IUserDao;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.etherenum.EthereumTransInfoDo;
import com.dce.business.entity.message.MessageAndNewsDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.trade.WithdrawalsDo;
import com.dce.business.entity.travel.TravelDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.account.IPayService;
import com.dce.business.service.impl.travel.TravelServiceImpl;
import com.dce.business.service.message.IMessageService;
import com.dce.business.service.trade.IWithdrawService;

/** 
 * 交易
 * @author parudy
 * @date 2018年3月25日 
 * @version v1.0
 */
@Service("withdrawService")
public class WithdrawServiceImpl implements IWithdrawService {

	private final static Logger logger = Logger.getLogger(TravelServiceImpl.class);

	@Resource
    private IWithdrawalsDao withdrawDao;
    @Resource
    private IAccountService accountService;
    @Resource
    private IMessageService messageService;
    @Resource
    private IUserDao userDao;
    @Resource
    private IEthereumTransInfoDao ethereumTransInfoDao;
    @Resource
    private IPayService payService;

    @Override
    public List<Map<String, Object>> getWithdrawRecords(Map<String, Object> param) {
        return withdrawDao.selectWithdraw(param);
    }

    /**
     * @param auditResult 2 : 同意支付; 3:拒绝
     */
    @Override
    public Result<?> auditWithdrawById(String auditResult, Integer withdrawId) {
    	
    	Result<?> result = Result.successResult("审核成功!") ;
    	
        WithdrawalsDo withdrawDo = withdrawDao.selectByPrimaryKey(withdrawId);
        
        WithdrawalsDo withdraw = new WithdrawalsDo();
        withdraw.setId(withdrawId);
        withdraw.setProcessStatus(auditResult);
        withdraw.setConfirmDate(new Date().getTime()/1000);
        //审核通过同意
        if ("2".equals(auditResult)||"4".equals(auditResult)) {
        	check(withdrawId); //重复性校验
        	if(withdrawDo.getType().equals("1")){
        		System.out.println("trueName:"+withdrawDo.getMoneyType());
                result = payService.withdraw(withdrawDo.getId(),withdrawDo.getUserid(), withdrawDo.getAmount(),withdrawDo.getBankNo(),withdrawDo.getMoneyType());
        	}
            if(result.isSuccess()){
            	result.setMsg("提现成功！");
            }
        } else if ("3".equals(auditResult)) {
            // 返还钱数
            UserAccountDo updateAccount = new UserAccountDo();
            updateAccount.setAccountType(AccountType.wallet_money.getAccountType());
            updateAccount.setAmount(withdrawDo.getAmount());
            updateAccount.setUserId(withdrawDo.getUserid());
            accountService.updateUserAmountById(updateAccount, IncomeType.TYPE_BACK_WITHDRAW);
           
            addMessage(withdrawDo.getUserid(), withdrawDo.getAmount());
            
        }
        
        //更新
        if ( "4".equals(auditResult)) {
            withdrawDao.auditWithdrawById4(withdraw);
        }else if("2".equals(auditResult)){
        	withdrawDao.auditWithdrawById2(withdraw);
        }else if("3".equals(auditResult)){
        	withdrawDao.auditWithdrawById3(withdraw);
        }
        
        return result;
    }

    private void check(Integer withdrawId) {
        Map<String, Object> params = new HashMap<>();
        params.put("withdrawalsId", withdrawId);
        List<EthereumTransInfoDo> list = ethereumTransInfoDao.selectParam(params);
        if (!CollectionUtils.isEmpty(list)) {
            throw new BusinessException("提现正在处理中，请勿重复操作");
        }
    }

    /** 
     * 用户提现被拒绝，写日志
     * @param userId
     * @param qty  
     */
    private void addMessage(Integer userId, BigDecimal qty) {
        UserDo userDo = userDao.selectByPrimaryKey(userId);
        String content = "用户" + userDo.getUserName() + "提现美元点" + qty.toString() + "被拒绝";
        MessageAndNewsDo msg = new MessageAndNewsDo();
        msg.setContent(content);
        msg.setTo_uid(userId);
        msg.setType(MessageType.TYPE_SYSMSG.getType());
        messageService.addMessage(msg);
    }

	@Override
	public int selectWithdrawCount(Map<String, Object> param) {
		
		return withdrawDao.selectWithdrawCount(param);
	}

	@Override
	public PageDo<Map<String, Object>> selectWithDrawByPage(
			PageDo<Map<String, Object>> page, Map<String, Object> param) {
		
		if(param == null){
			param = new HashMap<String,Object>();
		}
		param.put(Constants.MYBATIS_PAGE, page);
		List<Map<String, Object>> list = withdrawDao.selectWithDrawByPage(param);
		page.setModelList(list);
		return page;
	}
	
    @Override
	public Long selectWithDrawTotallAmount(Map<String, Object> param) {
		
		return withdrawDao.selectWithDrawTotallAmount(param);
	}

	
	@Override
	public WithdrawalsDo selectByPrimaryKey(Integer withdrawId) {
		// TODO Auto-generated method stub
		return withdrawDao.selectByPrimaryKey(withdrawId);
	}

	@Override
	public List<WithdrawalsDo> selectByExample(Map<String,Object> param) {
		// TODO Auto-generated method stub
		logger.info("----selectByExample----");
		List<WithdrawalsDo> result = withdrawDao.selectByExample(param);

		for (WithdrawalsDo withdraw : result) {
			if ("1".equals(withdraw.getProcessStatus())) {
				withdraw.setProcessStatus("待审批");
			} else if ("2".equals(withdraw.getProcessStatus())) {
				withdraw.setProcessStatus("已审批");
			}else if ("3".equals(withdraw.getProcessStatus())) {
				withdraw.setProcessStatus("已拒绝");
			}
			if ("0".equals(withdraw.getWithdraw_status())) {
				withdraw.setWithdraw_status("未到账");
			} else if ("1".equals(withdraw.getWithdraw_status())) {
				withdraw.setWithdraw_status("已到账");
			}
			if ("1".equals(withdraw.getType())){
				withdraw.setType("支付宝");
			}else if("2".equals(withdraw.getType())){
				withdraw.setType("银行卡");
			}else if("3".equals(withdraw.getType())){
				withdraw.setType("微信");
			}
			

		}

		return result;
	}

	@Override
	public Result<?> auditWithdrawById_bank(Integer withdrawId) {
		// TODO Auto-generated method stub
		

    	Result<?> result = Result.successResult("审核成功!") ;
    	WithdrawalsDo withdraw = new WithdrawalsDo();
        withdraw.setWithdraw_status("1");
        withdraw.setProcessStatus("2");

		withdraw.setPaymentDate((new Date()).getTime() / 1000);
		withdraw.setId(withdrawId);
		int i=withdrawDao.updateWithDrawStatus(withdraw);
        if(i>0){
            result.setMsg("提现成功！");
         }
       
        
        return result;
	}
	
	
}
