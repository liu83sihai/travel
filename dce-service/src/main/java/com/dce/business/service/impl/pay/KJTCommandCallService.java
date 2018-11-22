package com.dce.business.service.impl.pay;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.result.Result;

@Service("kjtCommandCallService")
public class KJTCommandCallService implements ICommandCall {

	private Logger logger = Logger.getLogger(KJTCommandCallService.class);
	@Override
	public Result<?> parseRetVal(IKJTCommand kjtCommand, String retVal) {
		logger.info("返回值："+retVal);
		if(StringUtils.isBlank(retVal)) {
			return Result.failureResult("调用第三方错误，没有返回值");
		}
		
		Result ret =  Result.successResult("ok");
		
		Map<String, Object> map = JSONObject.parseObject(retVal);
		if("S10000".equals(map.get("code"))) {
			System.out.println(map.get("biz_content"));
			ret.setData(map.get("biz_content"));
		}
		//{"code":"S10000","msg":"接口调用成功","sign":"MyRotIXjWiew+thSH6lBIu2WsmPJx2rN+hNeEoT9+EFd7/XS8ndrVBLHubb+M8zaQc1Imqym0n+gFWG/Dj69XBrQEWtE4dThO4spcYrQJRsdJkElRBH2y8os4KgrCrJHgGgonfrWfy0yLBEMxoRi6m4IE4+AqfFmyXpxwJQ9FEBS53xBg4DrfH2XGNlKf6C3mTTvyBX/avBixOi7a6y5Rg8aIzAqFAuhk56f7SrOlJPQPQwyMPzd41FFsVnVS70YQ5zce1IdRUVCv7sk3HVVlLiq/4F3bsqJFfpLhyMUQ27gdX2Bf34vsOPYebmVok5BjKJ1RvTvWtRpU+212/89wQ==","charset":"UTF-8","sign_type":"RSA","biz_content":{"token_id":"Z247330000132018112112171832069783"}}
		return ret;
	}

}
