package com.dce.business.common.enums;

public enum KLineTypeEnum {
    KLINE_TYPE_HOUR("hour"), // 小时K线图
    KLINE_TYPE_DAY("day"), // 天K线图
    KLINE_TYPE_WEEK("week"),// 周K线图
    KLINE_TYPE_MONTH("month");// 月K线图

    private String type;

    private KLineTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static KLineTypeEnum getKLineType(String type) {
        for (KLineTypeEnum item : KLineTypeEnum.values()) {
            if (item.getType().equals(type)) {
                return item;
            }
        }

        return null;
    }
}
