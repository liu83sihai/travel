package com.dce.business.service.impl.pay;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.result.Result;

@Service("kjtCommandCallBack")
public class KJTCommandCallService implements ICommandCall {

	private Logger logger = Logger.getLogger(KJTCommandCallService.class);
	@Override
	public Result<?> parseRetVal(String retVal) {
		logger.info("快捷通返回值:"+retVal);
		if(StringUtils.isBlank(retVal)) {
			return Result.failureResult("调用第三方错误，没有返回值");
		}
		
		Result ret =  Result.successResult("ok");
		Map<String, Object> map = JSONObject.parseObject(retVal);
		if("S10000".equals(map.get("code"))) {
			logger.info("快捷通返回:"+map.get("biz_content"));
			ret.setData(map.get("biz_content"));
		}
		return ret;
	}

}
