package com.dce.business.entity.activityGood;

import org.apache.ibatis.type.Alias;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


@Alias("activityGoodDo")
public class ActivityGoodDo  implements java.io.Serializable{	
    private static final long serialVersionUID = 1L;

	private java.lang.Integer id;
	private java.lang.Integer userId;
	private java.lang.Integer activityId;
	private java.sql.Date createDate;
	


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
	

	public void setActivityId(java.lang.Integer activityId) {
		this.activityId = activityId;
	}
	
	public java.lang.Integer getActivityId() {
		return this.activityId;
	}
	

	public void setCreateDate(java.sql.Date createDate) {
		this.createDate = createDate;
	}
	
	public java.sql.Date getCreateDate() {
		return this.createDate;
	}
	
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("ActivityId",getActivityId())
			.append("CreateDate",getCreateDate())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ActivityGoodDo == false) return false;
		if(this == obj) return true;
		ActivityGoodDo other = (ActivityGoodDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

