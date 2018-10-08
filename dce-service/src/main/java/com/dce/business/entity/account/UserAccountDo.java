package com.dce.business.entity.account;

import java.math.BigDecimal;
import java.util.Date;

public class UserAccountDo {
    private Integer id;

    private Integer userId;

    private String accountType;

    //账户金额
    private BigDecimal amount;
    //奖品
    private String totalConsumeAmount;

    private BigDecimal totalInocmeAmount;

    private BigDecimal incomeAmount;

    private BigDecimal consumeAmount;

    private String withdrawTotalDeposit;

    private Date updateTime;

    private String remark;
    
    /**
     * 流水id
     */
    private String seqId;
    
    private String relevantUser; //发生对象用户
    
    public UserAccountDo(){
    	
    }

    /**
     * 常用参数的构造函数
     * @param amount  金额
     * @param userId  用户id
     * @param accountType 账号类别
     */
    public UserAccountDo(BigDecimal amount, int userId,
			String accountType) {
		this.amount = amount;
		this.userId = userId;
		this.accountType = accountType;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

   

    public String getTotalConsumeAmount() {
		return totalConsumeAmount;
	}

	public void setTotalConsumeAmount(String totalConsumeAmount) {
		this.totalConsumeAmount = totalConsumeAmount;
	}

	public BigDecimal getTotalInocmeAmount() {
        return totalInocmeAmount;
    }

    public void setTotalInocmeAmount(BigDecimal totalInocmeAmount) {
        this.totalInocmeAmount = totalInocmeAmount;
    }

    public BigDecimal getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public BigDecimal getConsumeAmount() {
        return consumeAmount;
    }

    public void setConsumeAmount(BigDecimal consumeAmount) {
        this.consumeAmount = consumeAmount;
    }

    public String getWithdrawTotalDeposit() {
        return withdrawTotalDeposit;
    }

    public void setWithdrawTotalDeposit(String withdrawTotalDeposit) {
        this.withdrawTotalDeposit = withdrawTotalDeposit;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getRelevantUser() {
		return relevantUser;
	}

	public void setRelevantUser(String relevantUser) {
		this.relevantUser = relevantUser;
	}

	@Override
	public String toString() {
		return "UserAccountDo [id=" + id + ", userId=" + userId + ", accountType=" + accountType + ", amount=" + amount
				+ ", totalConsumeAmount=" + totalConsumeAmount + ", totalInocmeAmount=" + totalInocmeAmount
				+ ", incomeAmount=" + incomeAmount + ", consumeAmount=" + consumeAmount + ", withdrawTotalDeposit="
				+ withdrawTotalDeposit + ", updateTime=" + updateTime + ", remark=" + remark + ", seqId=" + seqId
				+ ", relevantUser=" + relevantUser + "]";
	}
	
}