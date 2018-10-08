package com.dce.business.entity.touch;

import java.math.BigDecimal;
import java.util.Date;

public class PerformanceDailyDo {
    private Integer id;

    private Integer userid;

    private BigDecimal balance;

    private BigDecimal balance_left;

    private BigDecimal balance_right;

    private Date date;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance_left() {
        return balance_left;
    }

    public void setBalance_left(BigDecimal balance_left) {
        this.balance_left = balance_left;
    }

    public BigDecimal getBalance_right() {
        return balance_right;
    }

    public void setBalance_right(BigDecimal balance_right) {
        this.balance_right = balance_right;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}