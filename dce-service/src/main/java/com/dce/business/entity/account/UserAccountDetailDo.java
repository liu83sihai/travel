
package com.dce.business.entity.account;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.Alias;

@Alias("userAccountDetailDo")
public class UserAccountDetailDo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	//columns START
	private Long id;
	private Integer userId;
	private BigDecimal amount;
	private String moreOrLess;
	/**
	 * 余额
	 */
	private BigDecimal balanceAmount;

	//账户类型
	private String accountType;
	private String remark;
	/** 
	 * 没特别说明为积分提成
	 * 购买股权 1:积分购股权 2：现金购买股权 3上一级直推积分提成 4:管理积分奖 5：报单中心积分奖  6：省代提成积分奖  7市代提成积分奖
	 * 8  静态分红金额  9消费提成 10 中奖金币 11中奖积分  12 竞猜消费积分 13 竞猜消费金币 14 竞猜提成  15 竞猜报单中心提成
	 * 16 竞拍消费积分  17 竞拍消费金币  18 上一级直推金提成  19:管理金币提成 20：报单中心金币提成  21：省代金币提成 22市代金币提成
	 * 23 消费金币提成 24 静态分红积分  25 充值　 26 推荐一人获取一金币
	 */

	/**
	 * 1-100 充值
	 * 100-200  金币
	 * 200-300 积分
	 * 
	 */
	private Integer incomeType;
	private java.util.Date createTime;
	//columns END

	private String userName;

	private String seqId;

	private String transactionObject; //转出对象
	
	private String userLevel;

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	private String relevantUser; //发生对象用户
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer value) {
		this.userId = value;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal value) {
		this.amount = value;
	}

	public String getMoreOrLess() {
		return this.moreOrLess;
	}

	public void setMoreOrLess(String value) {
		this.moreOrLess = value;
	}

	public Integer getIncomeType() {
		return this.incomeType;
	}

	public void setIncomeType(Integer value) {
		this.incomeType = value;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Id", getId()).append("UserId", getUserId())
				.append("Amount", getAmount()).append("MoreOrLess", getMoreOrLess()).append("IncomeType", getIncomeType())
				.append("CreateTime", getCreateTime()).toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof UserAccountDetailDo == false)
			return false;
		if (this == obj)
			return true;
		UserAccountDetailDo other = (UserAccountDetailDo) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getTransactionObject() {
		return transactionObject;
	}

	public void setTransactionObject(String transactionObject) {
		this.transactionObject = transactionObject;
	}

	public String getRelevantUser() {
		return relevantUser;
	}

	public void setRelevantUser(String relevantUser) {
		this.relevantUser = relevantUser;
	}
}
