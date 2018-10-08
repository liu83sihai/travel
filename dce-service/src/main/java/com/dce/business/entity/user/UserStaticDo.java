package com.dce.business.entity.user;

import java.math.BigDecimal;
import java.util.Date;

public class UserStaticDo {
    private Integer id;

    private Integer userid;

    private BigDecimal money;

    private BigDecimal totalmoney;

    private BigDecimal yfBonus;

    private Byte status;

    private Byte type;

    private Integer statictime;
    
    private Date endDate; //静态释放终止时间

    public enum StaticType {
        BaoDan((byte) 1, "保单"), JiaJin((byte) 2, "加金"), FuTou((byte) 3, "复投");

        private byte type;
        private String remark;

        private StaticType(byte type, String remark) {
            this.type = type;
            this.remark = remark;
        }

        public byte getType() {
            return type;
        }

        public void setType(byte type) {
            this.type = type;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(BigDecimal totalmoney) {
        this.totalmoney = totalmoney;
    }

    public BigDecimal getYfBonus() {
        return yfBonus;
    }

    public void setYfBonus(BigDecimal yfBonus) {
        this.yfBonus = yfBonus;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getStatictime() {
        return statictime;
    }

    public void setStatictime(Integer statictime) {
        this.statictime = statictime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}