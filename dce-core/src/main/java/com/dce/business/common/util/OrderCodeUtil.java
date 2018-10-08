package com.dce.business.common.util;

import java.util.Date;
import java.util.Random;

public final class OrderCodeUtil {

    /** 
     * 订单号
     * @param userId
     * @return  
     */
    public static String genOrderCode(Integer userId) {
        StringBuffer sb = new StringBuffer("XX");
        sb.append(userId).append(DateUtil.YYYYMMDDHHMMSS.format(new Date())).append(random());
        return sb.toString();
    }

    /**
     * 产生随机的三位数
     * @return
     */
    private static String random() {
        Random rad = new Random();
        return rad.nextInt(1000) + "";
    }
}
