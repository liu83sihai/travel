 
package com.dce.business.entity.secrety;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.Alias;
 

@Alias("resourcesDo")
public class ResourcesDo implements  Serializable{
 
	private static final long serialVersionUID = 1L;
	//columns START
	private Integer id;
	private String name;
	private String resourceDesc;
	private String resourceStr;
	private Boolean enabled;
	private Boolean issys;
	private String module;
	private String icon;
	private String imgUrl;
	private String resouceType;

	/**
	 * 排序
	 */
	private int order;
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

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
	public String getResourceDesc() {
		return this.resourceDesc;
	}

	public void setResourceDesc(String value) {
		this.resourceDesc = value;
	}
	public String getResourceStr() {
		return this.resourceStr;
	}

	public void setResourceStr(String value) {
		this.resourceStr = value;
	}
	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean value) {
		this.enabled = value;
	}
	public Boolean getIssys() {
		return this.issys;
	}

	public void setIssys(Boolean value) {
		this.issys = value;
	}
	public String getModule() {
		return this.module;
	}

	public void setModule(String value) {
		this.module = value;
	}
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String value) {
		this.icon = value;
	}
	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String value) {
		this.imgUrl = value;
	}
	public String getResouceType() {
		return this.resouceType;
	}

	public void setResouceType(String value) {
		this.resouceType = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("ResourceDesc",getResourceDesc())
			.append("ResourceStr",getResourceStr())
			.append("Enabled",getEnabled())
			.append("Issys",getIssys())
			.append("Module",getModule())
			.append("Icon",getIcon())
			.append("ImgUrl",getImgUrl())
			.append("ResouceType",getResouceType())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ResourcesDo == false) return false;
		if(this == obj) return true;
		ResourcesDo other = (ResourcesDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

