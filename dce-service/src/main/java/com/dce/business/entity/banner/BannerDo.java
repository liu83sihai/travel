package com.dce.business.entity.banner;

import org.apache.ibatis.type.Alias;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


@Alias("bannerDo")
public class BannerDo  implements java.io.Serializable{	
    private static final long serialVersionUID = 1L;

	private java.lang.Integer id;
	private java.lang.String icoName;
	private java.lang.String icoImage;
	private java.lang.Integer icoType;
	private java.lang.Integer linkType;
	private java.lang.String linkValue;
	private java.lang.String hintValue;
	private java.lang.Integer sort;
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
	

	public void setIcoName(java.lang.String icoName) {
		this.icoName = icoName;
	}
	
	public java.lang.String getIcoName() {
		return this.icoName;
	}
	

	public void setIcoImage(java.lang.String icoImage) {
		this.icoImage = icoImage;
	}
	
	public java.lang.String getIcoImage() {
		return this.icoImage;
	}
	

	public void setIcoType(java.lang.Integer icoType) {
		this.icoType = icoType;
	}
	
	public java.lang.Integer getIcoType() {
		return this.icoType;
	}
	

	public void setLinkType(java.lang.Integer linkType) {
		this.linkType = linkType;
	}
	
	public java.lang.Integer getLinkType() {
		return this.linkType;
	}
	

	public void setLinkValue(java.lang.String linkValue) {
		this.linkValue = linkValue;
	}
	
	public java.lang.String getLinkValue() {
		return this.linkValue;
	}
	

	public void setHintValue(java.lang.String hintValue) {
		this.hintValue = hintValue;
	}
	
	public java.lang.String getHintValue() {
		return this.hintValue;
	}
	

	public void setSort(java.lang.Integer sort) {
		this.sort = sort;
	}
	
	public java.lang.Integer getSort() {
		return this.sort;
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
	
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("IcoName",getIcoName())
			.append("IcoImage",getIcoImage())
			.append("IcoType",getIcoType())
			.append("LinkType",getLinkType())
			.append("LinkValue",getLinkValue())
			.append("HintValue",getHintValue())
			.append("Sort",getSort())
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
		if(obj instanceof BannerDo == false) return false;
		if(this == obj) return true;
		BannerDo other = (BannerDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

