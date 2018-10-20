package com.dce.business.entity.supplier;

import org.apache.ibatis.type.Alias;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


@Alias("supplierDo")
public class SupplierDo  implements java.io.Serializable{	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -39946431062908038L;
	private java.lang.Integer id;
	private java.lang.String supplierName;
	private java.lang.String province;
	private java.lang.String city;
	private java.lang.String businessNo;
	private java.lang.String businessImage;
	private java.lang.String cooperative;
	private java.lang.String linkMan;
	private java.lang.String linkPhone;
	private java.lang.String linkEmail;
	private java.lang.Integer status;
	private java.sql.Timestamp createTime;
	private java.sql.Timestamp auditTime;
	private java.lang.String auditInfo;
	private java.lang.String remark;
	


	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	

	public void setSupplierName(java.lang.String supplierName) {
		this.supplierName = supplierName;
	}
	
	public java.lang.String getSupplierName() {
		return this.supplierName;
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
	

	public void setBusinessNo(java.lang.String businessNo) {
		this.businessNo = businessNo;
	}
	
	public java.lang.String getBusinessNo() {
		return this.businessNo;
	}
	

	public void setBusinessImage(java.lang.String businessImage) {
		this.businessImage = businessImage;
	}
	
	public java.lang.String getBusinessImage() {
		return this.businessImage;
	}
	

	public void setCooperative(java.lang.String cooperative) {
		this.cooperative = cooperative;
	}
	
	public java.lang.String getCooperative() {
		return this.cooperative;
	}
	

	public void setLinkMan(java.lang.String linkMan) {
		this.linkMan = linkMan;
	}
	
	public java.lang.String getLinkMan() {
		return this.linkMan;
	}
	

	public void setLinkPhone(java.lang.String linkPhone) {
		this.linkPhone = linkPhone;
	}
	
	public java.lang.String getLinkPhone() {
		return this.linkPhone;
	}
	

	public void setLinkEmail(java.lang.String linkEmail) {
		this.linkEmail = linkEmail;
	}
	
	public java.lang.String getLinkEmail() {
		return this.linkEmail;
	}
	

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	

	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime;
	}
	
	public java.sql.Timestamp getCreateTime() {
		return this.createTime;
	}
	

	public void setAuditTime(java.sql.Timestamp auditTime) {
		this.auditTime = auditTime;
	}
	
	public java.sql.Timestamp getAuditTime() {
		return this.auditTime;
	}
	

	public void setAuditInfo(java.lang.String auditInfo) {
		this.auditInfo = auditInfo;
	}
	
	public java.lang.String getAuditInfo() {
		return this.auditInfo;
	}
	

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("SupplierName",getSupplierName())
			.append("Province",getProvince())
			.append("City",getCity())
			.append("BusinessNo",getBusinessNo())
			.append("BusinessImage",getBusinessImage())
			.append("Cooperative",getCooperative())
			.append("LinkMan",getLinkMan())
			.append("LinkPhone",getLinkPhone())
			.append("LinkEmail",getLinkEmail())
			.append("Status",getStatus())
			.append("CreateTime",getCreateTime())
			.append("AuditTime",getAuditTime())
			.append("AuditInfo",getAuditInfo())
			.append("Remark",getRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SupplierDo == false) return false;
		if(this == obj) return true;
		SupplierDo other = (SupplierDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

