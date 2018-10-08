package com.dce.business.entity.order;

public class OrderDetail {

	private Integer orderdetailid; // 订单明细表，主键

	private Integer orderid; // 订单表id

	private Integer goodsId; // 商品id

	private Integer quantity; // 商品数量

	private Double price; // 商品单价

	private String goodsName; // 商品名称

	private String remark; // 0为赠品1为商品

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getOrderdetailid() {
		return orderdetailid;
	}

	public void setOrderdetailid(Integer orderdetailid) {
		this.orderdetailid = orderdetailid;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderDetail [orderdetailid=" + orderdetailid + ", orderid=" + orderid + ", goodsId=" + goodsId
				+ ", quantity=" + quantity + ", price=" + price + ", goodsName=" + goodsName + ", remark=" + remark
				+ "]";
	}

}