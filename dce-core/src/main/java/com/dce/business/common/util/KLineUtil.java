package com.dce.business.common.util;

import java.util.Date;

import com.dce.business.common.enums.KLineTypeEnum;

/** 
 * K线计算 
 * @author parudy
 * @date 2018年4月1日 
 * @version v1.0
 */
public final class KLineUtil {
    public static Date getStartDate(KLineTypeEnum kLineType) {
        if (KLineTypeEnum.KLINE_TYPE_HOUR.equals(kLineType)) {
            return DateUtil.getDate(new Date(), 0, null, null);
        } else if (KLineTypeEnum.KLINE_TYPE_DAY.equals(kLineType)) {
            return DateUtil.getDate(new Date(), 0, 0, null);
        } else if (KLineTypeEnum.KLINE_TYPE_WEEK.equals(kLineType)) {
            return DateUtil.getDate(new Date(), 0, 0, null);
        }

        return null;
    }

    public static Date getNextDate(KLineTypeEnum kLineType, Date date) {
        if (KLineTypeEnum.KLINE_TYPE_HOUR.equals(kLineType)) {
            return DateUtil.getDate(date, 0, -1, null);
        } else if (KLineTypeEnum.KLINE_TYPE_DAY.equals(kLineType)) {
            return DateUtil.getDate(date, 0, 0, -1);
        } else if (KLineTypeEnum.KLINE_TYPE_WEEK.equals(kLineType)) {
            return DateUtil.getDate(date, 0, 0, -7);
        }

        return null;
    }

    public static Date getMADate(KLineTypeEnum kLineType, Date date, int maNum) {
        if (KLineTypeEnum.KLINE_TYPE_HOUR.equals(kLineType)) {
            return DateUtil.getDate(date, 0, -1 * maNum, null);
        } else if (KLineTypeEnum.KLINE_TYPE_DAY.equals(kLineType)) {
            return DateUtil.getDate(date, 0, 0, -1 * maNum);
        } else if (KLineTypeEnum.KLINE_TYPE_WEEK.equals(kLineType)) {
            return DateUtil.getDate(date, 0, 0, -7 * maNum);
        }

        return null;
    }

    public static void main(String[] args) {
        Date date = getStartDate(KLineTypeEnum.KLINE_TYPE_DAY);

        System.out.println(date);
    }
}
