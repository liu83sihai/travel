package com.dce.business.entity.order;

import java.math.BigDecimal;
import java.util.Date;

public class FeiHongLog {

	private Integer id; // 主键id
	private Integer orderid; // 订单id
	private Integer userid; // 用户id
	private BigDecimal feihongrate; //分红比率
	private BigDecimal feihongamt;//累计分红金额
	private Date createtime; // 订单创建之间
	private String msg; //运行时记录的消息
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public BigDecimal getFeihongrate() {
		return feihongrate;
	}

	public void setFeihongrate(BigDecimal feihongrate) {
		this.feihongrate = feihongrate;
	}

	public BigDecimal getFeihongamt() {
		return feihongamt;
	}

	public void setFeihongamt(BigDecimal feihongamt) {
		this.feihongamt = feihongamt;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


}