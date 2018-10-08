 
package com.dce.business.entity.secrety;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.Alias;
 
@Alias("authorityresourcesDo")
public class AuthorityresourcesDo implements Serializable{
 
	private static final long serialVersionUID = 1L;
	//columns START
	private Integer id;
	private Integer authorityId;
	private Integer resourceId;
	private Boolean enabled;
	//columns END
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	public Integer getAuthorityId() {
		return this.authorityId;
	}

	public void setAuthorityId(Integer value) {
		this.authorityId = value;
	}
	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer value) {
		this.resourceId = value;
	}
	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean value) {
		this.enabled = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("AuthorityId",getAuthorityId())
			.append("ResourceId",getResourceId())
			.append("Enabled",getEnabled())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof AuthorityresourcesDo == false) return false;
		if(this == obj) return true;
		AuthorityresourcesDo other = (AuthorityresourcesDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

