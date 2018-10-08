package com.dce.business.entity.message;

public class MessageAndNewsDo {

	  private Integer id ;//'消息ID',
	  private Integer pid ;// '消息父ID',
	  private String title ;// '消息内容',
	  private String content ;// '消息内容',
	  private Integer type ;// '0系统消息,1评论消息,2私信消息',
	  private Integer to_uid ;//'接收用户ID',
	  private Integer from_uid ;// '私信消息发信用户ID',
	  private Integer is_read ;//'是否已读',
	  private Long ctime ;// '发送时间',
	  private Long utime ;// '更新时间',
	  private Integer sort ;// '排序',
	  private Integer status ;// '状态',
	  
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getTo_uid() {
		return to_uid;
	}
	public void setTo_uid(Integer to_uid) {
		this.to_uid = to_uid;
	}
	public Integer getFrom_uid() {
		return from_uid;
	}
	public void setFrom_uid(Integer from_uid) {
		this.from_uid = from_uid;
	}
	public Integer getIs_read() {
		return is_read;
	}
	public void setIs_read(Integer is_read) {
		this.is_read = is_read;
	}
	public Long getCtime() {
		return ctime;
	}
	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}
	public Long getUtime() {
		return utime;
	}
	public void setUtime(Long utime) {
		this.utime = utime;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	  
	  
}
