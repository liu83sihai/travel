package com.dce.business.entity.award;

import java.math.BigDecimal;

public class AwardConfig {
    private Long id;

    private Integer userlevel;

    private BigDecimal rate1;

    private BigDecimal rate2;

    private BigDecimal rate3;

    private BigDecimal rate4;

    private BigDecimal rate5;

    private BigDecimal rate6;

    private BigDecimal rate7;

    private BigDecimal rate8;

    private String awardtypename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(Integer userlevel) {
        this.userlevel = userlevel;
    }

    public BigDecimal getRate1() {
        return rate1;
    }

    public void setRate1(BigDecimal rate1) {
        this.rate1 = rate1;
    }

    public BigDecimal getRate2() {
        return rate2;
    }

    public void setRate2(BigDecimal rate2) {
        this.rate2 = rate2;
    }

    public BigDecimal getRate3() {
        return rate3;
    }

    public void setRate3(BigDecimal rate3) {
        this.rate3 = rate3;
    }

    public BigDecimal getRate4() {
        return rate4;
    }

    public void setRate4(BigDecimal rate4) {
        this.rate4 = rate4;
    }

    public BigDecimal getRate5() {
        return rate5;
    }

    public void setRate5(BigDecimal rate5) {
        this.rate5 = rate5;
    }

    public BigDecimal getRate6() {
        return rate6;
    }

    public void setRate6(BigDecimal rate6) {
        this.rate6 = rate6;
    }

    public BigDecimal getRate7() {
        return rate7;
    }

    public void setRate7(BigDecimal rate7) {
        this.rate7 = rate7;
    }

    public BigDecimal getRate8() {
        return rate8;
    }

    public void setRate8(BigDecimal rate8) {
        this.rate8 = rate8;
    }

    public String getAwardtypename() {
        return awardtypename;
    }

    public void setAwardtypename(String awardtypename) {
        this.awardtypename = awardtypename == null ? null : awardtypename.trim();
    }
}