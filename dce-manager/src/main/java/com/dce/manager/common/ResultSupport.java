package com.dce.manager.common;

import java.io.Serializable;

/**
 * 
 * @author: liuzg
 * 
 * @date 2014年7月27日 下午3:40:33
 */
public class ResultSupport<T> implements IResult<T>, Serializable {

    public final static String successCode = "0";
    public final static String errorCode = "1";
    private static final long serialVersionUID = 1L;
    private String resultCode; // 请求结果状态码 , 返回代码为 0 表示成功， 其他表示失败
    private String resultMessage; // 返回信息信息
    private T model; // 模型对象

    public ResultSupport() {
    }

    public ResultSupport(String resultCode) {
        this.resultCode = resultCode;
    }

    public ResultSupport(String resultCode, String msg) {
        this.resultCode = resultCode;
        this.resultMessage = msg;
    }

    public ResultSupport(String resultCode, T model) {
        this.resultCode = resultCode;
        this.model = model;
    }

    public ResultSupport(String resultCode, String resultMessage, T model) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.model = model;
    }

    public boolean isSuccess() {
        return successCode.equals(this.resultCode);
    }
	
    public String getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public static <T> IResult<T> buildResult(String code) {
        return new ResultSupport<T>(code);
    }

    public static <T> IResult<T> buildResult(String code, String msg) {
        return new ResultSupport<T>(code, msg);
    }

    public static <T> IResult<T> buildResult(String code, T model) {
        return new ResultSupport<T>(code, model);
    }

    public static <T> IResult<T> buildResult(String code, String message, T model) {
        return new ResultSupport<T>(code, message, model);
    }

  
    public static <T> IResult<T> buildErrorResult(String msg) {
    	return new ResultSupport<T>(ResultSupport.errorCode, msg);
    }

    public static <T> IResult<T> buildSuccessResult(T model) {
        return new ResultSupport<T>(ResultSupport.successCode, model);
    }

    public static <T> IResult<T> buildSuccessResult(String msg, T model) {
        return new ResultSupport<T>(ResultSupport.successCode, msg, model);
    }

}
