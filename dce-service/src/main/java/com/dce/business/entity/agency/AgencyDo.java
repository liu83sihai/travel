package com.dce.business.entity.agency;

import org.apache.ibatis.type.Alias;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


@Alias("agencyDo")
public class AgencyDo  implements java.io.Serializable{	
    private static final long serialVersionUID = 1L;

	private java.lang.Integer id;
	private java.lang.Integer userId;
	private java.lang.String userName;
	private java.lang.String mobile;
	private java.lang.String idCard;
	private java.lang.String bankNumber;
	private java.lang.String bankType;
	private java.lang.String idcardFront;
	private java.lang.String idcardBack;
	private java.lang.String province;
	private java.lang.String city;
	private java.lang.String cityCode;
	private java.sql.Date createDate;
	private java.lang.String createName;
	private java.sql.Date modifyDate;
	private java.lang.String modifyName;
	private java.lang.Integer status;
	private java.lang.Integer sex;
	private java.lang.String remark;
	


	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	

	
	public java.lang.String getCityCode() {
		return cityCode;
	}

	public void setCityCode(java.lang.String cityCode) {
		this.cityCode = cityCode;
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
	

	public void setIdCard(java.lang.String idCard) {
		this.idCard = idCard;
	}
	
	public java.lang.String getIdCard() {
		return this.idCard;
	}
	

	public void setBankNumber(java.lang.String bankNumber) {
		this.bankNumber = bankNumber;
	}
	
	public java.lang.String getBankNumber() {
		return this.bankNumber;
	}
	

	public void setBankType(java.lang.String bankType) {
		this.bankType = bankType;
	}
	
	public java.lang.String getBankType() {
		return this.bankType;
	}
	

	public void setIdcardFront(java.lang.String idcardFront) {
		this.idcardFront = idcardFront;
	}
	
	public java.lang.String getIdcardFront() {
		return this.idcardFront;
	}
	

	public void setIdcardBack(java.lang.String idcardBack) {
		this.idcardBack = idcardBack;
	}
	
	public java.lang.String getIdcardBack() {
		return this.idcardBack;
	}
	

	public void setProvince(java.lang.String province) {
		this.province = province;
	}
	
	public java.lang.String getProvince() {
		return this.province;
	}
	

	public void setCity(java.lang.String city) {
		this.city = city;
	}
	
	public java.lang.String getCity() {
		return this.city;
	}
	

	public void setCreateDate(java.sql.Date createDate) {
		this.createDate = createDate;
	}
	
	public java.sql.Date getCreateDate() {
		return this.createDate;
	}
	

	public void setCreateName(java.lang.String createName) {
		this.createName = createName;
	}
	
	public java.lang.String getCreateName() {
		return this.createName;
	}
	

	public void setModifyDate(java.sql.Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public java.sql.Date getModifyDate() {
		return this.modifyDate;
	}
	

	public void setModifyName(java.lang.String modifyName) {
		this.modifyName = modifyName;
	}
	
	public java.lang.String getModifyName() {
		return this.modifyName;
	}
	

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	
	

	public java.lang.Integer getSex() {
		return sex;
	}

	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("UserName",getUserName())
			.append("Mobile",getMobile())
			.append("IdCard",getIdCard())
			.append("BankNumber",getBankNumber())
			.append("BankType",getBankType())
			.append("IdcardFront",getIdcardFront())
			.append("IdcardBack",getIdcardBack())
			.append("Province",getProvince())
			.append("City",getCity())
			.append("CreateDate",getCreateDate())
			.append("CreateName",getCreateName())
			.append("ModifyDate",getModifyDate())
			.append("ModifyName",getModifyName())
			.append("Status",getStatus())
			.append("Remark",getRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof AgencyDo == false) return false;
		if(this == obj) return true;
		AgencyDo other = (AgencyDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

