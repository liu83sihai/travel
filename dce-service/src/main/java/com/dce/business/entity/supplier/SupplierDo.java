package com.dce.business.entity.supplier;

import org.apache.ibatis.type.Alias;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


@Alias("supplierDo")
public class SupplierDo  implements java.io.Serializable{	
    private static final long serialVersionUID = 1L;

	private java.lang.Integer id;
	private java.lang.Integer userId;
	private java.lang.String supplierName;
	private java.lang.String synopsis;
	private java.lang.String content;
	private java.lang.String linkValue;
	private java.lang.String listImages;
	private java.lang.String bannerImages;
	private java.lang.String busiImage;
	private java.lang.String shopImage;
	private java.lang.String city;
	private java.lang.String cityCode;
	private java.lang.String supplierAddress;
	private java.lang.String telPhone;
	private java.lang.String linkMan;
	private java.lang.Integer supplierType;
	private java.math.BigDecimal grade;
	private java.math.BigDecimal average;
	private java.math.BigDecimal longitude;
	private java.math.BigDecimal latitude;
	private java.math.BigDecimal distance;
	private java.lang.Integer hitNum;
	private java.sql.Date createDate;
	private java.lang.String createName;
	private java.sql.Date modifyDate;
	private java.lang.String modifyName;
	private java.lang.Integer status;
	private java.lang.String remark;
	
	


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
	

	public void setSupplierName(java.lang.String supplierName) {
		this.supplierName = supplierName;
	}
	
	public java.lang.String getSupplierName() {
		return this.supplierName;
	}
	

	public void setSynopsis(java.lang.String synopsis) {
		this.synopsis = synopsis;
	}
	
	public java.lang.String getSynopsis() {
		return this.synopsis;
	}
	

	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	

	public void setLinkValue(java.lang.String linkValue) {
		this.linkValue = linkValue;
	}
	
	public java.lang.String getLinkValue() {
		return this.linkValue;
	}
	

	public void setListImages(java.lang.String listImages) {
		this.listImages = listImages;
	}
	
	public java.lang.String getListImages() {
		return this.listImages;
	}
	

	public void setBannerImages(java.lang.String bannerImages) {
		this.bannerImages = bannerImages;
	}
	
	public java.lang.String getBannerImages() {
		return this.bannerImages;
	}
	

	public void setBusiImage(java.lang.String busiImage) {
		this.busiImage = busiImage;
	}
	
	public java.lang.String getBusiImage() {
		return this.busiImage;
	}
	

	public void setShopImage(java.lang.String shopImage) {
		this.shopImage = shopImage;
	}
	
	public java.lang.String getShopImage() {
		return this.shopImage;
	}
	

	public void setCity(java.lang.String city) {
		this.city = city;
	}
	
	public java.lang.String getCity() {
		return this.city;
	}
	

	public void setSupplierAddress(java.lang.String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}
	
	public java.lang.String getSupplierAddress() {
		return this.supplierAddress;
	}
	

	public void setTelPhone(java.lang.String telPhone) {
		this.telPhone = telPhone;
	}
	
	public java.lang.String getTelPhone() {
		return this.telPhone;
	}
	

	public void setLinkMan(java.lang.String linkMan) {
		this.linkMan = linkMan;
	}
	
	public java.lang.String getLinkMan() {
		return this.linkMan;
	}
	

	public void setSupplierType(java.lang.Integer supplierType) {
		this.supplierType = supplierType;
	}
	
	public java.lang.Integer getSupplierType() {
		return this.supplierType;
	}
	

	public void setGrade(java.math.BigDecimal grade) {
		this.grade = grade;
	}
	
	public java.math.BigDecimal getGrade() {
		return this.grade;
	}
	

	public void setAverage(java.math.BigDecimal average) {
		this.average = average;
	}
	
	public java.math.BigDecimal getAverage() {
		return this.average;
	}
	

	public void setLongitude(java.math.BigDecimal longitude) {
		this.longitude = longitude;
	}
	
	public java.math.BigDecimal getLongitude() {
		return this.longitude;
	}
	

	public void setLatitude(java.math.BigDecimal latitude) {
		this.latitude = latitude;
	}
	
	public java.math.BigDecimal getLatitude() {
		return this.latitude;
	}
	

	public void setHitNum(java.lang.Integer hitNum) {
		this.hitNum = hitNum;
	}
	
	public java.lang.Integer getHitNum() {
		return this.hitNum;
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
	
	
	

	public java.math.BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(java.math.BigDecimal distance) {
		this.distance = distance;
	}
	
	

	public java.lang.String getCityCode() {
		return cityCode;
	}

	public void setCityCode(java.lang.String cityCode) {
		this.cityCode = cityCode;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("SupplierName",getSupplierName())
			.append("Synopsis",getSynopsis())
			.append("Content",getContent())
			.append("LinkValue",getLinkValue())
			.append("ListImages",getListImages())
			.append("BannerImages",getBannerImages())
			.append("BusiImage",getBusiImage())
			.append("ShopImage",getShopImage())
			.append("City",getCity())
			.append("SupplierAddress",getSupplierAddress())
			.append("TelPhone",getTelPhone())
			.append("LinkMan",getLinkMan())
			.append("SupplierType",getSupplierType())
			.append("Grade",getGrade())
			.append("Average",getAverage())
			.append("Longitude",getLongitude())
			.append("Latitude",getLatitude())
			.append("HitNum",getHitNum())
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
		if(obj instanceof SupplierDo == false) return false;
		if(this == obj) return true;
		SupplierDo other = (SupplierDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

