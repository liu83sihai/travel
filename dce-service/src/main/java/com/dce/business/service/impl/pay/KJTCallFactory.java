package com.dce.business.service.impl.pay;

import com.dce.business.common.util.SpringBeanUtil;

public class KJTCallFactory {

    public static ICommandCall getCommandCallByCommand(String command) {
    	 return SpringBeanUtil.getBean(command+"CallBack");
    }

}
