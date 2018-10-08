package com.dce.business.entity.etherenum;

import java.util.Date;

public class EthereumTransInfoDo {
    private Long id;

    private Integer userid;

    private String fromaccount;

    private String toaccount;

    private String amount;

    private String actualamount;

    private String pointamount;

    private String gas;

    private String gaslimit;

    private String actualgas;

    private String confirmed;

    private String status;

    private String hash;

    /**
     * 1,  充值， 2 提现， 3 会员转出 4， 平台转出
     */
    private Integer type;

    private Date createtime;

    private Date updatetime;
    
    /**
     * 提现手续费
     */
    private String withdrawFee;
    private Integer withdrawalsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getFromaccount() {
        return fromaccount;
    }

    public void setFromaccount(String fromaccount) {
        this.fromaccount = fromaccount;
    }

    public String getToaccount() {
        return toaccount;
    }

    public void setToaccount(String toaccount) {
        this.toaccount = toaccount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getActualamount() {
        return actualamount;
    }

    public void setActualamount(String actualamount) {
        this.actualamount = actualamount;
    }

    public String getPointamount() {
        return pointamount;
    }

    public void setPointamount(String pointamount) {
        this.pointamount = pointamount;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getGaslimit() {
        return gaslimit;
    }

    public void setGaslimit(String gaslimit) {
        this.gaslimit = gaslimit;
    }

    public String getActualgas() {
        return actualgas;
    }

    public void setActualgas(String actualgas) {
        this.actualgas = actualgas;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

	public String getWithdrawFee() {
		return withdrawFee;
	}

	public void setWithdrawFee(String withdrawFee) {
		this.withdrawFee = withdrawFee;
	}

	public Integer getWithdrawalsId() {
		return withdrawalsId;
	}

	public void setWithdrawalsId(Integer withdrawalsId) {
		this.withdrawalsId = withdrawalsId;
	}

	
    
}