package com.dce.business.entity.order;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDo {
    private Long orderId;

    private String orderCode;

    private Integer userId;

    private Long goodsId;

    private BigDecimal qty;

    private BigDecimal price;

    private BigDecimal totalPrice;

    private String recAddress;

    private Date createTime;

    private Integer orderStatus;

    private Integer payStatus;

    private Date payTime;

    private Integer orderType;

    private Long matchOrderId;
    
    private BigDecimal salqty;
    
    private String accountType; //账户类型

    /**
     * 获取剩余的挂单数量
     * @return
     */
    public BigDecimal getRemainQty(){
    	return this.qty.subtract(this.salqty == null? BigDecimal.ZERO:this.salqty);
    }
    
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRecAddress() {
        return recAddress;
    }

    public void setRecAddress(String recAddress) {
        this.recAddress = recAddress;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Long getMatchOrderId() {
        return matchOrderId;
    }

    public void setMatchOrderId(Long matchOrderId) {
        this.matchOrderId = matchOrderId;
    }

	public BigDecimal getSalqty() {
		return salqty;
	}

	public void setSalqty(BigDecimal salqty) {
		this.salqty = salqty;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
}