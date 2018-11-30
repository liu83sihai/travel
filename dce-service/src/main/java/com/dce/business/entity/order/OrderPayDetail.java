package com.dce.business.entity.order;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderPayDetail implements Serializable {

	private String accountType;
	private BigDecimal payAmt;
	private Integer orderid; // 订单主键id
	private String remark;
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public BigDecimal getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String toString() {
		return "OrderPayDetail [accountType=" + accountType + ", payAmt=" + payAmt + ", orderid=" + orderid
				+ ", remark=" + remark + "]";
	}
	
	
	
}
