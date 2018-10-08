 
package com.dce.business.entity.secrety;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.Alias;
 
@Alias("managersDo")
public class ManagersDo implements Serializable{
 
	private static final long serialVersionUID = 1L;
	//columns START
	private Integer id;
	private String username;
	private String nickname;
	private String password;
	private String salt;
	private Boolean enabled;
	private Integer operator;
	private String ip;
	private String remark;
	private java.util.Date registerTime;
	private java.util.Date lastLoginTime;
	private java.util.Date passwordUpdateTime;
	private String mobile;
	private Integer deptId;

	//columns END
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String value) {
		this.username = value;
	}
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String value) {
		this.nickname = value;
	}
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String value) {
		this.password = value;
	}
	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String value) {
		this.salt = value;
	}
	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean value) {
		this.enabled = value;
	}
	public Integer getOperator() {
		return this.operator;
	}

	public void setOperator(Integer value) {
		this.operator = value;
	}
	public String getIp() {
		return this.ip;
	}

	public void setIp(String value) {
		this.ip = value;
	}
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String value) {
		this.remark = value;
	}
	public java.util.Date getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(java.util.Date value) {
		this.registerTime = value;
	}
	public java.util.Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(java.util.Date value) {
		this.lastLoginTime = value;
	}
	public java.util.Date getPasswordUpdateTime() {
		return this.passwordUpdateTime;
	}

	public void setPasswordUpdateTime(java.util.Date value) {
		this.passwordUpdateTime = value;
	}
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String value) {
		this.mobile = value;
	}

	/**
	 * @return the deptId
	 */
	public Integer getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Username",getUsername())
			.append("Nickname",getNickname())
			.append("Password",getPassword())
			.append("Salt",getSalt())
			.append("Enabled",getEnabled())
			.append("Operator",getOperator())
			.append("Ip",getIp())
			.append("Remark",getRemark())
			.append("RegisterTime",getRegisterTime())
			.append("LastLoginTime",getLastLoginTime())
			.append("PasswordUpdateTime",getPasswordUpdateTime())
			.append("Mobile",getMobile())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ManagersDo == false) return false;
		if(this == obj) return true;
		ManagersDo other = (ManagersDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

