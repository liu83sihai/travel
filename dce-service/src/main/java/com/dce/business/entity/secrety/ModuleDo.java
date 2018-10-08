package com.dce.business.entity.secrety;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.Alias;


 
@Alias("moduleDo")
public class ModuleDo implements Serializable, Cloneable,Comparable<ModuleDo>{
 
	private static final long serialVersionUID = 1L;
	//columns START
	private Integer id;
	private String module;
	private String name;
	private String moduleDesc;
	private String imgUrl;
	private java.util.Date createTime;
	private List<ResourcesDo> resources;
	private int order;


	public void addResource(ResourcesDo resourceDo){

		if(resources == null){
			resources = new ArrayList<ResourcesDo>();
		}

		if(this.resources.contains(resourceDo)){
			return;
		}
		this.resources.add(resourceDo);
	}


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
	public String getModule() {
		return this.module;
	}

	public void setModule(String value) {
		this.module = value;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}
	public String getModuleDesc() {
		return this.moduleDesc;
	}

	public void setModuleDesc(String value) {
		this.moduleDesc = value;
	}
	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String value) {
		this.imgUrl = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Module",getModule())
			.append("Name",getName())
			.append("ModuleDesc",getModuleDesc())
			.append("ImgUrl",getImgUrl())
			.append("CreateTime",getCreateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ModuleDo == false) return false;
		if(this == obj) return true;
		ModuleDo other = (ModuleDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	public List<ResourcesDo> getResources() {
		return resources;
	}

	public void setResources(List<ResourcesDo> resources) {
		this.resources = resources;
	}
	

	@Override
	public int compareTo(ModuleDo o) {
		return this.order<o.order?1:-1;
	}
}

