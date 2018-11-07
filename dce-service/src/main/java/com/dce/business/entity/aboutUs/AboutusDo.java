/*
 * Powered By  huangzl QQ: 272950754
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2018
 */

package com.dce.business.entity.aboutUs;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.ibatis.type.Alias;

/**
 * @author  huangzl QQ: 272950754
 * @version 1.0
 * @since 1.0
 */


@Alias("aboutusDo")
public class AboutusDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.Integer id;
	private java.lang.String url;
	private Date createTime; // 订单创建之间
    private String summarry;
    private String aboutusBanner;
    private String  detailImg;
    
    
	public String getSummarry() {
		return summarry;
	}

	public void setSummarry(String summarry) {
		this.summarry = summarry;
	}

	public String getAboutusBanner() {
		return aboutusBanner;
	}

	public void setAboutusBanner(String aboutusBanner) {
		this.aboutusBanner = aboutusBanner;
	}

	public String getDetailImg() {
		return detailImg;
	}

	public void setDetailImg(String detailImg) {
		this.detailImg = detailImg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createtime) {
		this.createTime = createtime;
	}

	//columns END
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	public java.lang.String getUrl() {
		return this.url;
	}
	
	public void setUrl(java.lang.String value) {
		this.url = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Url",getUrl())
			.append("createTime",getCreateTime())
			.toString();
	}
	
}

