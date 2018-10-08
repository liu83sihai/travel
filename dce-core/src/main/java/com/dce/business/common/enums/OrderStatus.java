package com.dce.business.common.enums;

public enum OrderStatus {

	/**
	 * 撤销,无效
	 */
	invalid(0,"无效"),
	/**
	 * 有效
	 */
	effective(1,"有效");
	
	private int code;
	private String remark;
	
	OrderStatus(int code,String remark){
		this.code = code;
		this.remark = remark;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
