package com.dce.business.common.enums;

public enum PayStatus {

	paying(0,"待付"),
	payed(1,"已付");
	
	private int code;
	private String remark;
	
	PayStatus(int code,String remark){
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
