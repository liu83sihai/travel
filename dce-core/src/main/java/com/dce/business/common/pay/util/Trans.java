package com.dce.business.common.pay.util;

public class Trans {

	private String order_id;
	private String status;
	private String pay_date;
	private String arrival_time_end;
	private String order_fee;
	
	private String fail_reason;
	private String out_biz_no;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPay_date() {
		return pay_date;
	}
	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}
	public String getArrival_time_end() {
		return arrival_time_end;
	}
	public void setArrival_time_end(String arrival_time_end) {
		this.arrival_time_end = arrival_time_end;
	}
	public String getOrder_fee() {
		return order_fee;
	}
	public void setOrder_fee(String order_fee) {
		this.order_fee = order_fee;
	}
	public String getFail_reason() {
		return fail_reason;
	}
	public void setFail_reason(String fail_reason) {
		this.fail_reason = fail_reason;
	}
	public String getOut_biz_no() {
		return out_biz_no;
	}
	public void setOut_biz_no(String out_biz_no) {
		this.out_biz_no = out_biz_no;
	}

	
}
