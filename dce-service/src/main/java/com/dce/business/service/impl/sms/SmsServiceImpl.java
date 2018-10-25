package com.dce.business.service.impl.sms;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.DataEncrypt;
import com.dce.business.dao.sms.ISmsDao;
import com.dce.business.dao.user.IUserDao;
import com.dce.business.entity.sms.SmsDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.sms.ISmsService;

@Service("smsService")
public class SmsServiceImpl implements ISmsService{
	
	Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
	
	@Resource
	DceSmsService yunRongSmsImpl;
	@Resource
	IUserDao userDao;
	
	@Resource 
	ISmsDao smsDao;
	
	//page： 扫描支付： scan，  卖单：  sale，  美元转让：  tran
	@Override
	public void send(Integer userId, String page) {
		UserDo user = userDao.selectByPrimaryKey(userId);
		try {
			 
			String smscode = IdentifyCodeGenerator.generateIdentifyCode(4);
			if(StringUtils.isEmpty(user.getMobile())){
				new BusinessException("你还未设置手机号码");
			}
			logger.debug("发送手机号码：{}",user.getMobile());
			String smsSend = yunRongSmsImpl.smsSend(smscode, user.getMobile());
			logger.debug("send:"+smsSend);
			if(smsSend.length()>4){
				SmsDo record = new SmsDo();
				record.setRecievers(user.getMobile());
				record.setMessage(smscode);
				record.setBusinessType(page);
				smsDao.insertSelective(record);
			}else{
				 new BusinessException("发送失败");
			}
			 
		} catch (Throwable e) {
			logger.error(e.getMessage());
			throw new BusinessException("发送失败");
		}		
	}

//	@Override
//	public boolean checkSms(Integer userId, String page, String smsCode) {
//		UserDo user = userDao.selectByPrimaryKey(userId);
//		SmsDo smsDo = smsDao.getLastIdentifyCode(user.getMobile(),page);
//		return smsCode.equals(smsDo.getMessage());
//		return true;
//	}
	
	@Override
	public Result<?> checkSms(Integer userId, String page, String smsCode) {
		UserDo user = userDao.selectByPrimaryKey(userId);
		if(StringUtils.isBlank(smsCode)){
			return Result.failureResult("无效的交易密码");
		}
		 //2、校验密码是否正确
        String password = DataEncrypt.encrypt(smsCode);
        if (!user.getTwoPassword().equals(password)) {
            return Result.failureResult("交易密码不正确");
        }
        
		return Result.successResult("交易密码校验成功");
	}

}
