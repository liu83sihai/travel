package com.dce.business.entity.trade;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class TradeDo {
    private Integer id;

    private String orderno;

    private Integer userid;

    private Integer curid;

    private Integer tradeid;

    private Integer entrustid;

    @NotNull(message = "买入价格不能为空")
    private BigDecimal price;

    @NotNull(message = "买入数量不能为空")
    private BigDecimal num;

    private BigDecimal totalmoney;

    @NotNull(message = "交易费率不能为空")
    private BigDecimal fee;

    private String type;

    private Integer ctime;

    private Integer utime;

    private Byte status;

    private String remark;

    @NotNull(message = "交易类型不能为空")
    private Integer tradeType; //交易类型

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getCurid() {
        return curid;
    }

    public void setCurid(Integer curid) {
        this.curid = curid;
    }

    public Integer getTradeid() {
        return tradeid;
    }

    public void setTradeid(Integer tradeid) {
        this.tradeid = tradeid;
    }

    public Integer getEntrustid() {
        return entrustid;
    }

    public void setEntrustid(Integer entrustid) {
        this.entrustid = entrustid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public BigDecimal getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(BigDecimal totalmoney) {
        this.totalmoney = totalmoney;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCtime() {
        return ctime;
    }

    public void setCtime(Integer ctime) {
        this.ctime = ctime;
    }

    public Integer getUtime() {
        return utime;
    }

    public void setUtime(Integer utime) {
        this.utime = utime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }
}