

package com.dce.business.entity.secrety;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.type.Alias;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Alias("authoritiesDo")
public class AuthoritiesDo implements GrantedAuthority {
	
	private static final long serialVersionUID = -2199591630706329716L;
	
	//columns START
	private Integer id;
	private String name;
	private String authDesc;
	private Boolean enabled;
	private Boolean issys;
	private String module;
	private List<ResourcesDo> resources;


	public AuthoritiesDo(String name){
		this.name=name;
	}


	public AuthoritiesDo() {
		// TODO Auto-generated constructor stub
	}


	public void setResources(List<ResourcesDo> resources) {
		this.resources = resources;
	}


	public List<ResourcesDo> getResources() {
		return resources;
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
	public String getAuthDesc() {
		return this.authDesc;
	}

	public void setAuthDesc(String value) {
		this.authDesc = value;
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

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("AuthDesc",getAuthDesc())
			.append("Enabled",getEnabled())
			.append("Issys",getIssys())
			.append("Module",getModule())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof AuthoritiesDo == false) return false;
		if(this == obj) return true;
		AuthoritiesDo other = (AuthoritiesDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	@Override
	public String getAuthority() {
		return name;
	}
	
	public static List<AuthoritiesDo> load(List<Map<String, Object>> list) {
			AuthoritiesDo auth=null;
			Map<Integer,AuthoritiesDo> tmpMap=new HashMap<Integer,AuthoritiesDo>();
			for(Map<String,Object> data:list){
				Integer authId=Integer.parseInt(data.get("ID").toString());
				String resourceStr=data.get("RESOURCESTR").toString();
				int resourceId=Integer.parseInt(data.get("RESOURCEID").toString());
				String resourceName=data.get("RESOURCENAME").toString();
				auth=tmpMap.get(authId);
				if(auth==null){
					String authorityName=data.get("NAME").toString();
					auth=new AuthoritiesDo();
					auth.setId(authId);
					auth.setName(authorityName);
					auth.setResources(new ArrayList<ResourcesDo>());
					tmpMap.put(authId, auth);
				}
				ResourcesDo resource=new ResourcesDo();
				resource.setId(resourceId);
				resource.setName(resourceName);
				resource.setResourceStr(resourceStr);
				auth.getResources().add(resource);
			}
			return new ArrayList<AuthoritiesDo>(tmpMap.values());
	}
}

