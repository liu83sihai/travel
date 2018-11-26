package com.dce.business.service.impl.pay;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dce.business.common.result.Result;
import com.dce.business.common.util.DateUtil;
import com.dce.business.common.util.HttpUtil;
import com.dce.business.service.pay.IKJTPayService;
import com.kjtpay.gateway.common.domain.base.RequestBase;

@Service("payKJTServiceImpl")
public class PayKJTServiceImpl implements IKJTPayService {
	
	private Logger logger = Logger.getLogger(PayKJTServiceImpl.class);
	
	@Resource
	private PayComponent payComponent;
	
	@Resource
	private ICommandCall kjtCommandCall;
	
	//公共配置
	private static final String charset="UTF-8";
	private static final String format="JSON";
	private final String partnerId ="200000151629";
	private final String signType ="RSA";
	private String kjtUrl = "https://c1gateway.kjtpay.com/recv.do";
	
	

	/**
	 * 执行 post请求
	 * @param requestBase
	 * @return
	 * @throws Throwable
	 */
	private String posKJTRequest(RequestBase requestBase) throws Throwable{
		
		Map<String, String> param = new HashMap<String,String>();
		//url编码
		param.put("biz_content", URLEncoder.encode(requestBase.getBizContent(), charset));
		param.put("charset", URLEncoder.encode(requestBase.getCharset(), charset));
		param.put("format", URLEncoder.encode(requestBase.getFormat(), charset));
		param.put("partner_id", URLEncoder.encode(requestBase.getPartnerId(), charset));
		param.put("request_no", URLEncoder.encode(requestBase.getRequestNo(), charset));
		param.put("service", URLEncoder.encode(requestBase.getService(), charset));
		param.put("sign_type", URLEncoder.encode(requestBase.getSignType(), charset));
		param.put("timestamp", URLEncoder.encode(requestBase.getTimestamp(), charset));
		param.put("version", URLEncoder.encode(requestBase.getVersion(), charset));
		param.put("sign", URLEncoder.encode(requestBase.getSign(), charset));
		
		//post
		String retVal = HttpUtil.httpPost(kjtUrl, param);
		logger.info("kjt return msg:"+retVal);
		
		return retVal;
		
	}

	private Result<?> executeCommand(IKJTCommand kjtCommand)throws Throwable {
		
		RequestBase requestBase = kjtCommand.getRequestBase();
		//加密bizContent字段
		String bizContent = requestBase.getBizContent();
		String encryptBizContent = payComponent.encrypt(bizContent, charset);
		requestBase.setBizContent(encryptBizContent);
		
		//加签
		String requestNo = UUID.randomUUID().toString();
		requestNo = requestNo.replace("-", "");  
		requestBase.setRequestNo(requestNo);
		requestBase.setCharset(charset);
		requestBase.setFormat(format);
		requestBase.setPartnerId(partnerId);
		requestBase.setSignType(signType);
		requestBase.setTimestamp(DateUtil.YYYY_MM_DD_MM_HH_SS.format(new Date()));
		String sign = payComponent.sign(requestBase);
		requestBase.setSign(sign);
	
		String retVal =  posKJTRequest(requestBase);
		return KJTCallFactory.getCommandCallByCommand(requestBase.getService()).parseRetVal(retVal);
		
	}
	
	@Override
	public Result<?> executeGetBankCardCode(String idno, 
											String realName, 
											String mobile,
											String cardNo,
											Integer userId) throws Throwable {
		IKJTCommand  getCardCodeCommand  = new GetBankCodeCommand(IKJTCommand.COMMAND_GET_BANK_CARD_CODE,
																  "1.1",
				                                                  idno,
				                                                  realName,
				                                                  mobile,
				                                                  cardNo,
				                                                  userId);
		return executeCommand(getCardCodeCommand);
	}



	@Override
	public Result<?> executeCheckCode(String tokenId, String verifyCode, Integer userId) throws Throwable{
		IKJTCommand checkCodeCommand = new CheckCodeCommand(IKJTCommand.COMMAND_CHECK_BANK_CARD_CODE
				                                            ,"1.0"
				                                            ,tokenId
				                                            ,verifyCode
				                                            ,userId);
		
		return executeCommand(checkCodeCommand );
	}

	
	@Override
	public Result<?> executePayInstantTrade(String payProductCode
									      	  ,String amount
									      	  ,String tokenId
									      	  ,String signingPay
									      	  ,String bankCardNo
									      	  ,String phoneNum
									      	  ,String bankAccountName
									      	  ,String cvv2
									      	  ,String validDate
									      	  ,String idNo
									      	  ,Integer userId) throws Throwable{
		
		IKJTCommand instantTradeCommand = new InstantTradeCommand(IKJTCommand.COMMAND_PAY_instant_trade
				                                            	  ,"1.0"
				                                            	  ,payProductCode
				                                            	  ,amount
				                                            	  ,tokenId
				                                            	  ,signingPay
				                                            	  ,bankCardNo
				                                            	  ,phoneNum
				                                            	  ,bankAccountName
				                                            	  ,cvv2
				                                            	  ,validDate
				                                            	  ,idNo
				                                            	  ,userId);
		return executeCommand(instantTradeCommand );
	}
	
	
}
