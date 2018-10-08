package com.dce.business.entity.award;

import java.util.Date;

public class Award {
	//奖励记录id
    private Integer awardid;

    //奖励时间
    private Date awardtime;

    //奖励用户的id
    private Integer userid;

    //奖励用户的金额
    private Double awardmoney;

    //奖励事由
    private String awardreason;

    //'奖励旅游·状态 1 使用 0 未使用',
    private Integer awardusedstatus;

    //奖励是否有效 1 有效 0 无效',
    private Integer awardstatus;

    //奖励类别
    private Integer awardtype;

    //奖励消费时间
    private Date awardusedtime;

    //修改时间
    private Date awardupdatetime;

    //创建时间
    private Date awardcreatetime;

    //奖励操作名称
    private String awardoperationname;

    public Integer getAwardid() {
        return awardid;
    }

    public void setAwardid(Integer awardid) {
        this.awardid = awardid;
    }

    public Date getAwardtime() {
        return awardtime;
    }

    public void setAwardtime(Date awardtime) {
        this.awardtime = awardtime;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Double getAwardmoney() {
        return awardmoney;
    }

    public void setAwardmoney(Double awardmoney) {
        this.awardmoney = awardmoney;
    }

    public String getAwardreason() {
        return awardreason;
    }

    public void setAwardreason(String awardreason) {
        this.awardreason = awardreason == null ? null : awardreason.trim();
    }

    public Integer getAwardusedstatus() {
        return awardusedstatus;
    }

    public void setAwardusedstatus(Integer awardusedstatus) {
        this.awardusedstatus = awardusedstatus;
    }

    public Integer getAwardstatus() {
        return awardstatus;
    }

    public void setAwardstatus(Integer awardstatus) {
        this.awardstatus = awardstatus;
    }

    public Integer getAwardtype() {
        return awardtype;
    }

    public void setAwardtype(Integer awardtype) {
        this.awardtype = awardtype;
    }

    public Date getAwardusedtime() {
        return awardusedtime;
    }

    public void setAwardusedtime(Date awardusedtime) {
        this.awardusedtime = awardusedtime;
    }

    public Date getAwardupdatetime() {
        return awardupdatetime;
    }

    public void setAwardupdatetime(Date awardupdatetime) {
        this.awardupdatetime = awardupdatetime;
    }

    public Date getAwardcreatetime() {
        return awardcreatetime;
    }

    public void setAwardcreatetime(Date awardcreatetime) {
        this.awardcreatetime = awardcreatetime;
    }

    public String getAwardoperationname() {
        return awardoperationname;
    }

    public void setAwardoperationname(String awardoperationname) {
        this.awardoperationname = awardoperationname == null ? null : awardoperationname.trim();
    }
}