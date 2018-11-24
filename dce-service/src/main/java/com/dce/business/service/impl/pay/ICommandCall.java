package com.dce.business.service.impl.pay;

import com.dce.business.common.result.Result;

public interface ICommandCall {

	Result<?> parseRetVal(String retVal);

}
