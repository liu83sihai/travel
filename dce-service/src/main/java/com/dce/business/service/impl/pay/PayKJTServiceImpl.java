package com.dce.business.service.impl.pay;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
	
	private static final String charset="UTF-8";
	private static final String format="JSON";
	private final String partnerId ="200000151629";
	private final String signType ="RSA";
	
	private String kjtUrl = "https://c1gateway.kjtpay.com/recv.do";
	
	
	private RequestBase getKJTRequestBase(String bizContent) {
		return getKJTRequestBase(bizContent,null);
	}
	
	private RequestBase getKJTRequestBase(String bizContent ,String version) {
		RequestBase requestBase = new RequestBase();
		requestBase.setBizContent(bizContent);
		if(StringUtils.isBlank(version)){
			version = "1.0";
		}
		requestBase.setVersion(version);
		return requestBase;
	}
	
	private Result<?> posKJTRequest(RequestBase requestBase) throws Throwable{
		String requestNo = UUID.randomUUID().toString();
		requestBase.setRequestNo(requestNo);
		requestBase.setCharset(charset);
		requestBase.setFormat(format);
		requestBase.setPartnerId(partnerId);
		requestBase.setSignType(signType);
		requestBase.setTimestamp(DateUtil.YYYY_MM_DD_MM_HH_SS.format(new Date()));
		
		String sign = payComponent.sign(requestBase);
		requestBase.setSign(sign);
		
		Map<String, String> param = new HashMap<String,String>();
		param.put("biz_content", URLEncoder.encode(requestBase.getBizContent(), charset));
		param.put("charset", URLEncoder.encode(charset, charset));
		param.put("format", URLEncoder.encode(format, charset));
		param.put("partner_id", URLEncoder.encode(requestBase.getPartnerId(), charset));
		param.put("request_no", URLEncoder.encode(requestBase.getRequestNo(), charset));
		param.put("service", URLEncoder.encode(requestBase.getService(), charset));
		param.put("sign_type", URLEncoder.encode(requestBase.getSignType(), charset));
		param.put("timestamp", URLEncoder.encode(requestBase.getTimestamp(), charset));
		param.put("version", URLEncoder.encode(requestBase.getVersion(), charset));
		param.put("sign", URLEncoder.encode(sign, charset));
		
		//post
		String retVal = HttpUtil.httpPost(kjtUrl, param);
		logger.info("kjt return msg:"+retVal);
		
		return Result.successResult("ok");
	}

	private Result<?> executeCommand(IKJTCommand getCardCodeCommand)
			throws Throwable {
		RequestBase requestBase = getKJTRequestBase(getCardCodeCommand.getBizContent());
		requestBase.setService(getCardCodeCommand.getServiceName());
		return posKJTRequest(requestBase);
	}
	
	@Override
	public Result<?> executeGetBankCardCode(String idno, String realName, String mobile,String cardNo) throws Throwable {
		IKJTCommand  getCardCodeCommand  = new GetBankCodeCommand(IKJTCommand.COMMAND_GET_BANK_CARD_CODE,idno,realName,mobile,cardNo);
		return executeCommand(getCardCodeCommand);
	}


	@Override
	public Result<?> executeCheckCode() throws Throwable{
		IKJTCommand checkCodeCommand = new CheckCodeCommand();
		return executeCommand(checkCodeCommand );
	}
	

}
