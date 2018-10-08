package com.dce.business.common.enums;

/** 
 * 交易类型
 * @author parudy
 * @date 2018年3月26日 
 * @version v1.0
 */
public enum TradeType {
    /**
     * 市价
     */
    MarketPrice(1, "市价"),
    /**
     * 挂单
     */
    PendingOrders(2, "挂单");

    private int tradeType;
    private String remark;

    TradeType(int tradeType, String remark) {
        this.tradeType = tradeType;
        this.remark = remark;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
