package com.dce.business.entity.touch;

import java.math.BigDecimal;

public class TouchBonusRecordDo {
    private Integer id;

    private Integer userid;

    private BigDecimal balance;

    private BigDecimal balanceLeft;

    private BigDecimal balanceRight;

    private BigDecimal touchNumber;

    private BigDecimal bonus;

    private Integer relevantUserid;

    private Integer dis;

    private Integer ctime;

    private BigDecimal incomeLeft;

    private BigDecimal incomeRight;

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

    public BigDecimal getBalanceLeft() {
        return balanceLeft;
    }

    public void setBalanceLeft(BigDecimal balanceLeft) {
        this.balanceLeft = balanceLeft;
    }

    public BigDecimal getBalanceRight() {
        return balanceRight;
    }

    public void setBalanceRight(BigDecimal balanceRight) {
        this.balanceRight = balanceRight;
    }

    public BigDecimal getTouchNumber() {
        return touchNumber;
    }

    public void setTouchNumber(BigDecimal touchNumber) {
        this.touchNumber = touchNumber;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    public Integer getRelevantUserid() {
        return relevantUserid;
    }

    public void setRelevantUserid(Integer relevantUserid) {
        this.relevantUserid = relevantUserid;
    }

    public Integer getDis() {
        return dis;
    }

    public void setDis(Integer dis) {
        this.dis = dis;
    }

    public Integer getCtime() {
        return ctime;
    }

    public void setCtime(Integer ctime) {
        this.ctime = ctime;
    }

    public BigDecimal getIncomeLeft() {
        return incomeLeft;
    }

    public void setIncomeLeft(BigDecimal incomeLeft) {
        this.incomeLeft = incomeLeft;
    }

    public BigDecimal getIncomeRight() {
        return incomeRight;
    }

    public void setIncomeRight(BigDecimal incomeRight) {
        this.incomeRight = incomeRight;
    }
}