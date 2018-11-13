package com.dce.business.entity.userCard;

import org.apache.ibatis.type.Alias;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


@Alias("userCardDo")
public class UserCardDo  implements java.io.Serializable{	
    private static final long serialVersionUID = 1L;

	private java.lang.Integer id;
	private java.lang.Integer userId;
	private java.lang.String userName;
	private java.lang.String mobile;
	private java.lang.String orderNo;
	private java.lang.Integer userLevel;
	private java.lang.Integer sex;
	private java.lang.String cardNo;
	private java.lang.String bankNo;
	private java.lang.String idNumber;
	private java.lang.String remark;
	private Date createDate;
	private Date updateDate;
	private java.lang.Integer status;
	


	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	

	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}
	
	public java.lang.String getUserName() {
		return this.userName;
	}
	

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	
	public java.lang.String getMobile() {
		return this.mobile;
	}
	

	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}
	
	public java.lang.String getOrderNo() {
		return this.orderNo;
	}
	

	public void setUserLevel(java.lang.Integer userLevel) {
		this.userLevel = userLevel;
	}
	
	public java.lang.Integer getUserLevel() {
		return this.userLevel;
	}
	

	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}
	
	public java.lang.Integer getSex() {
		return this.sex;
	}
	

	public void setCardNo(java.lang.String cardNo) {
		this.cardNo = cardNo;
	}
	
	public java.lang.String getCardNo() {
		return this.cardNo;
	}
	

	public void setBankNo(java.lang.String bankNo) {
		this.bankNo = bankNo;
	}
	
	public java.lang.String getBankNo() {
		return this.bankNo;
	}
	

	public void setIdNumber(java.lang.String idNumber) {
		this.idNumber = idNumber;
	}
	
	public java.lang.String getIdNumber() {
		return this.idNumber;
	}
	

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	

	public void setCreateDate( Date createDate) {
		this.createDate = createDate;
	}
	
	public  Date getCreateDate() {
		return this.createDate;
	}
	

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public Date getUpdateDate() {
		return this.updateDate;
	}
	

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("UserName",getUserName())
			.append("Mobile",getMobile())
			.append("OrderNo",getOrderNo())
			.append("UserLevel",getUserLevel())
			.append("Sex",getSex())
			.append("CardNo",getCardNo())
			.append("BankNo",getBankNo())
			.append("IdNumber",getIdNumber())
			.append("Remark",getRemark())
			.append("CreateDate",getCreateDate())
			.append("UpdateDate",getUpdateDate())
			.append("Status",getStatus())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserCardDo == false) return false;
		if(this == obj) return true;
		UserCardDo other = (UserCardDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

