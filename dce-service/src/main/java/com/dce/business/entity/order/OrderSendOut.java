package com.dce.business.entity.order;

public class OrderSendOut {

	private Long id; // 订单发货id

	private Long orderId; // 订单id

	private Long operatorId; // 操作人id

	private String address; // 地址

	private String createTime; // 创建时间

	private String operator; // 操作人
	
	private String orderCode; //订单编号

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String string) {
		this.createTime = string;
	}

	@Override
	public String toString() {
		return "OrderSendOut [id=" + id + ", orderId=" + orderId + ", operatorId=" + operatorId + ", address=" + address
				+ ", createTime=" + createTime + ", operator=" + operator + ", orderCode=" + orderCode + "]";
	}

}