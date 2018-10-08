package com.dce.business.common.result;

public final class Result<T> {
	public final static String successCode = "0"; //为0成功，其他失败
	public final static String failureCode = "1"; //通过失败编码    

	private String code;
	private String msg;
	private T data;

	private Result(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static <T> Result<T> result(String code, String msg, T data) {
		return new Result<T>(code, msg, data);
	}

	public static <T> Result<T> successResult(String msg) {
		return new Result<T>(successCode, msg, null);
	}

	public static <T> Result<T> successResult(String msg, T data) {
		return new Result<T>(successCode, msg, data);
	}

	public static <T> Result<T> failureResult(String msg) {
		return new Result<T>(failureCode, msg, null);
	}

	public boolean isSuccess() {
		return successCode.equals(getCode());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
