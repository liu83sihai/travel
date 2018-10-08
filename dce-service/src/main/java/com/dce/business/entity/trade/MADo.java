package com.dce.business.entity.trade;

import java.math.BigDecimal;

public class MADo {
    private BigDecimal volume;//成交量
    private BigDecimal avgPrice; //均价

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }
}
