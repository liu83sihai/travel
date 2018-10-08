 
package com.dce.business.entity.secrety;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.Alias;

 

@Alias("rolesDo")
public class RolesDo implements Serializable{
 
	private static final long serialVersionUID = 1L;
	//columns START
	private Integer id;
	private String name;
	private String roleDesc;
	private Boolean enabled;
	//columns END
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}
	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String value) {
		this.roleDesc = value;
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
			.append("Name",getName())
			.append("RoleDesc",getRoleDesc())
			.append("Enabled",getEnabled())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof RolesDo == false) return false;
		if(this == obj) return true;
		RolesDo other = (RolesDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

