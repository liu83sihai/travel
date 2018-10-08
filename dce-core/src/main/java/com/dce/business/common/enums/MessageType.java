package com.dce.business.common.enums;

public enum MessageType {

	/**
	 * 系统消息
	 */
	TYPE_SYSMSG(0,"系统消息"),
	/**
	 * 评论消息
	 */
	TYPE_PLMSG(1,"评论消息"),
	/**
	 * 私信消息
	 */
	TYPE_SXNEWS(2,"私信消息"),
	/**
	 * 意见反馈
	 */
	FEED_BACK(3,"意见反馈"),
	/**
	 * 新闻消息
	 */
	TYPE_NEWS(4,"新闻消息"),
	/**
	 * DEC消息
	 */
	TYPE_DECMSG(5,"DEC消息");
	
	private int type;
	private String remark;
	
	MessageType(int type,String remark){
		this.type = type;
		this.remark = remark;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
