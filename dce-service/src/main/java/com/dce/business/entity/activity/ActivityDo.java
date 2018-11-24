package com.dce.business.entity.activity;

import org.apache.ibatis.type.Alias;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


@Alias("activityDo")
public class ActivityDo  implements java.io.Serializable{	
    private static final long serialVersionUID = 1L;

	private java.lang.Integer id;
	private java.lang.Integer userId;
	private java.lang.String synopsis;
	private java.lang.String content;
	private java.lang.String images;
	private java.lang.Integer hitNum;
	private java.sql.Date createDate;
	private java.lang.String createName;
	private java.sql.Date modifyDate;
	private java.lang.String modifyName;
	private java.lang.Integer status;
	private java.lang.String remark;
	private java.lang.Integer activityGood;
	private java.lang.Integer activityCount;
	private String userName;
	private String trueName;
	private java.lang.Integer userFace;


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
	

	public void setImages(java.lang.String images) {
		this.images = images;
	}
	
	public java.lang.String getImages() {
		return this.images;
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
	
	

	public java.lang.Integer getActivityGood() {
		return activityGood;
	}

	public void setActivityGood(java.lang.Integer activityGood) {
		this.activityGood = activityGood;
	}

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTrueName() {
		return trueName;
	}
	
	

	public java.lang.Integer getActivityCount() {
		return activityCount;
	}

	public void setActivityCount(java.lang.Integer activityCount) {
		this.activityCount = activityCount;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public java.lang.Integer getUserFace() {
		return userFace;
	}

	public void setUserFace(java.lang.Integer userFace) {
		this.userFace = userFace;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("Synopsis",getSynopsis())
			.append("Content",getContent())
			.append("Images",getImages())
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
		if(obj instanceof ActivityDo == false) return false;
		if(this == obj) return true;
		ActivityDo other = (ActivityDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

