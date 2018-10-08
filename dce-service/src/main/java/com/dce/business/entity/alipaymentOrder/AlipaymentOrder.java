package com.dce.business.entity.alipaymentOrder;

import java.math.BigDecimal;

public class AlipaymentOrder {

	private Integer id;

	private Integer orderid;

	private String ordercode; //商户系统的唯一订单号

	private Integer orderstatus; // 交易状态：0交易创建并等待买家付款 1未付款交易超时关闭或支付完成后全额退款 2交易支付成功 3交易结束并不可退款

	private BigDecimal totalamount; //订单金额:本次交易支付的订单金额，单位为人民币（元）

	private BigDecimal receptamount; //实收金额:商家在交易中实际收到的款项，单位为元

	private String createtime; //交易创建时间

	private String notifytime; //支付宝通知时间

	private String gmtcreatetime; //交易付款时间

	private String gmtrefundtime; //交易退款时间

	private String gmtclosetime; //交易结束时间

	private String tradeno; //支付宝的交易号

	private String buyerlogonid; //买家支付宝账号

	private String sellerid; //卖家支付宝用户号

	private String selleremail; //卖家支付宝用户号

	private Double invoiceamount; //开票金额:用户在交易中支付的可开发票的金额

	private Double buyerpayamount; //付款金额:用户在交易中支付的金额
	
	private String remark; // 备注
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Integer getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(Integer orderstatus) {
		this.orderstatus = orderstatus;
	}

	public BigDecimal getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(BigDecimal totalamount) {
		this.totalamount = totalamount;
	}

	public BigDecimal getReceptamount() {
		return receptamount;
	}

	public void setReceptamount(BigDecimal receptamount) {
		this.receptamount = receptamount;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getNotifytime() {
		return notifytime;
	}

	public void setNotifytime(String notifytime) {
		this.notifytime = notifytime;
	}

	public String getGmtcreatetime() {
		return gmtcreatetime;
	}

	public void setGmtcreatetime(String gmtcreatetime) {
		this.gmtcreatetime = gmtcreatetime;
	}

	public String getGmtrefundtime() {
		return gmtrefundtime;
	}

	public void setGmtrefundtime(String gmtrefundtime) {
		this.gmtrefundtime = gmtrefundtime;
	}

	public String getGmtclosetime() {
		return gmtclosetime;
	}

	public void setGmtclosetime(String gmtclosetime) {
		this.gmtclosetime = gmtclosetime;
	}

	public String getTradeno() {
		return tradeno;
	}

	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
	}

	public String getBuyerlogonid() {
		return buyerlogonid;
	}

	public void setBuyerlogonid(String buyerlogonid) {
		this.buyerlogonid = buyerlogonid;
	}

	public String getSellerid() {
		return sellerid;
	}

	public void setSellerid(String sellerid) {
		this.sellerid = sellerid;
	}

	public String getSelleremail() {
		return selleremail;
	}

	public void setSelleremail(String selleremail) {
		this.selleremail = selleremail;
	}

	public Double getInvoiceamount() {
		return invoiceamount;
	}

	public void setInvoiceamount(Double invoiceamount) {
		this.invoiceamount = invoiceamount;
	}

	public Double getBuyerpayamount() {
		return buyerpayamount;
	}

	public void setBuyerpayamount(Double buyerpayamount) {
		this.buyerpayamount = buyerpayamount;
	}

	@Override
	public String toString() {
		return "AlipaymentOrder [id=" + id + ", orderid=" + orderid
				+ ", ordercode=" + ordercode + ", orderstatus=" + orderstatus
				+ ", totalamount=" + totalamount + ", receptamount="
				+ receptamount + ", createtime=" + createtime + ", notifytime="
				+ notifytime + ", gmtcreatetime=" + gmtcreatetime
				+ ", gmtrefundtime=" + gmtrefundtime + ", gmtclosetime="
				+ gmtclosetime + ", tradeno=" + tradeno + ", buyerlogonid="
				+ buyerlogonid + ", sellerid=" + sellerid + ", selleremail="
				+ selleremail + ", invoiceamount=" + invoiceamount
				+ ", buyerpayamount=" + buyerpayamount + ", remark=" + remark
				+ "]";
	}

}