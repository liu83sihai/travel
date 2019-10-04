package com.dce.business.entity.order;

import java.math.BigDecimal;
import java.util.Date;

public class FeiHongOrder {

	private Integer orderid; // 订单主键id
	private String ordercode; // 订单编号
	private Integer userid; // 用户id
	private Integer qty; // 商品总数量
	private BigDecimal totalprice; // 订单总金额
	private String orderstatus; // 订单状态（1已发货0未发货）
	private BigDecimal feihongrate; //分红比率
	private Date startdate; // 开始分红时间
	private Date enddate; // 结束分红时间
	private String ordertype; //分红订单类型
	private Date lastrundate; //  上次分红日期
	private BigDecimal totalfeihongamt;//累计分红金额
	private Integer buyerid; //购买用户id
	private Date createtime; // 订单创建之间
	
	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public BigDecimal getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public BigDecimal getFeihongrate() {
		return feihongrate;
	}

	public void setFeihongrate(BigDecimal feihongrate) {
		this.feihongrate = feihongrate;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public Date getLastrundate() {
		return lastrundate;
	}

	public void setLastrundate(Date lastrundate) {
		this.lastrundate = lastrundate;
	}

	public BigDecimal getTotalfeihongamt() {
		return totalfeihongamt;
	}

	public void setTotalfeihongamt(BigDecimal totalfeihongamt) {
		this.totalfeihongamt = totalfeihongamt;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getBuyerid() {
		return buyerid;
	}

	public void setBuyerid(Integer buyerid) {
		this.buyerid = buyerid;
	}

	@Override
	public String toString() {
		return "FeiHongOrder [orderid=" + orderid + ", ordercode=" + ordercode + ", userid=" + userid + ", qty=" + qty
				+ ", totalprice=" + totalprice + ", orderstatus=" + orderstatus + ", feihongrate=" + feihongrate
				+ ", startdate=" + startdate + ", enddate=" + enddate + ", ordertype=" + ordertype + ", lastrundate="
				+ lastrundate + ", totalfeihongamt=" + totalfeihongamt + ", buyerid=" + buyerid + ", createtime="
				+ createtime + "]";
	}




}