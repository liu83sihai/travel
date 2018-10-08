package com.dce.business.entity.trade;

import java.math.BigDecimal;

public class WithdrawalsDo {
    private Integer id;
    
    private String user_name;
    
    public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getTrue_name() {
		return true_name;
	}

	public void setTrue_name(String true_name) {
		this.true_name = true_name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	private String true_name;
    
    private String mobile;

    private Long withdrawDate;

    private BigDecimal amount;

    private String  orderId;
    
    public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	private String withdraw_status;

    private Long paymentDate;

    private Long confirmDate;

    private Integer userid;

    private BigDecimal fee;

    private String outbizno;

    public String getOutbizno() {
		return outbizno;
	}

	public void setOutbizno(String outbizno) {
		this.outbizno = outbizno;
	}

	private String bank;

    private String bankContent;

    private String bankNo;

    private String processStatus;

    private String type;

    private String moneyType;

    private String remark;

    
    public String getWithdraw_status() {
		return withdraw_status;
	}

	public void setWithdraw_status(String withdraw_status) {
		this.withdraw_status = withdraw_status;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

   


	public Long getWithdrawDate() {
		return withdrawDate;
	}

	public void setWithdrawDate(Long withdrawDate) {
		this.withdrawDate = withdrawDate;
	}

	public Long getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Long paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Long getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Long confirmDate) {
		this.confirmDate = confirmDate;
	}

	public void setWithdrawDate(long withdrawDate) {
		this.withdrawDate = withdrawDate;
	}


    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankContent() {
        return bankContent;
    }

    public void setBankContent(String bankContent) {
        this.bankContent = bankContent;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
	@Override
	public String toString() {
		return "WithdrawalsDo [id=" + id + ", withdrawDate=" + withdrawDate
				+ ", amount=" + amount + ", orderId=" + orderId
				+ ", paymentDate=" + paymentDate + ", confirmDate="
				+ confirmDate + ", userid=" + userid + ", fee=" + fee
				+ ", outbizno=" + outbizno + ", bank=" + bank + ", bankContent="
				+ bankContent + ", bankNo=" + bankNo + ", processStatus="
				+ processStatus + ", type=" + type + ", moneyType=" + moneyType
				+ ", remark=" + remark + ", getId()=" + getId()
				+ ", getAmount()=" + getAmount() + ", getOrderId()="
				+ getOrderId() + ", getWithdrawDate()=" + getWithdrawDate()
				+ ", getPaymentDate()=" + getPaymentDate()
				+ ", getConfirmDate()=" + getConfirmDate() + ", getUserid()="
				+ getUserid() + ", getFee()=" + getFee() + ", getOutbizno()="
				+ getOutbizno() + ", getBank()=" + getBank()
				+ ", getBankContent()=" + getBankContent() + ", getBankNo()="
				+ getBankNo() + ", getProcessStatus()=" + getProcessStatus()
				+ ", getType()=" + getType() + ", getMoneyType()="
				+ getMoneyType() + ", getRemark()=" + getRemark()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
    
}