package com.dce.business.entity.trade;

import java.math.BigDecimal;
import java.util.Date;

import com.dce.business.common.enums.KLineTypeEnum;

/** 
 * K线图 
 * @author parudy
 * @date 2018年3月31日 
 * @version v1.0
 */
public class KLineDo {
    private String date; //日期
    private BigDecimal open; //开盘价
    private BigDecimal close;//收盘价
    private BigDecimal high; //最高价
    private BigDecimal low;//最低价
    private BigDecimal volume;//成交量
    private MADo ma5;
    private MADo ma10;
    private MADo ma20;
    private BigDecimal price;
    private BigDecimal qty;
    private BigDecimal totalAmount;//成交额
    private Date ctime;
    
    private KLineTypeEnum klineType;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public MADo getMa5() {
        return ma5;
    }

    public void setMa5(MADo ma5) {
        this.ma5 = ma5;
    }

    public MADo getMa10() {
        return ma10;
    }

    public void setMa10(MADo ma10) {
        this.ma10 = ma10;
    }

    public MADo getMa20() {
        return ma20;
    }

    public void setMa20(MADo ma20) {
        this.ma20 = ma20;
    }

	public KLineTypeEnum getKlineType() {
		return klineType;
	}

	public void setKlineType(KLineTypeEnum klineType) {
		this.klineType = klineType;
	}
    
}
