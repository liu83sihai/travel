package com.dce.business.common.enums;

public enum OrderType {
	
	TYPE_BAODAN(1,"报单"),
	TYPE_JIAJIN(2,"加金"),
	TYPE_FUTOU(3,"复投"),
	GD_BUY(1,"挂单买入"),
	GD_SAL(2,"挂单卖出");
	
	private int orderType;
    private String remark;
    
    OrderType(int orderType,String remark){
    	this.orderType = orderType;
    	this.remark = remark;
    }

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
    
}
