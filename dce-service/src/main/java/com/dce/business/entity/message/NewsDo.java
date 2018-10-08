package com.dce.business.entity.message;

import java.util.Date;


public class NewsDo {

	  private Integer id ;//'消息ID',
	  private String title ;// '消息内容',
	  private String content ;// '消息内容',
	  private String image ;//图片
	  private String author ;//作者
	  private Integer topNews ;//是否置顶（0是/1否）
	  private String remark ;//备注
	  private Integer status ;//状态
	  private Date createDate  ;//创建时间
	  private String createName ;//创建人
	  private Date updateDate  ;//更新时间
	  private String updateName ;//更新人
	  
	  
	  
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Integer getTopNews() {
		return topNews;
	}
	public void setTopNews(Integer topNews) {
		this.topNews = topNews;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date creatDate) {
		this.createDate = creatDate;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	   
	
	  
	  
	  
}
