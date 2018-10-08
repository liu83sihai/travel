package com.dce.manager.common;

/**
 * 
 * @author: liuzg
 * @date 2014年7月27日 下午3:39:02
 */
public interface IResult<T> {

    /**
     * 请求是否成功
     * 
     * @return
     * @date: 2014年2月27日下午3:48:21
     */
    boolean isSuccess();

    /**
     * 获取结果代码
     * 
     * @return
     * @date: 2014年2月27日下午3:49:20
     */
    String getResultCode();

    /**
     * 设置结果代码
     * 
     * @param resultCode
     * @date: 2014年2月27日下午3:49:53
     */
    void setResultCode(String resultCode);

    /**
     * 获取模型对象信息
     * 
     * @return
     * @date: 2014年2月27日下午3:52:18
     */
    T getModel();

    /**
     * 设置模型对象信息
     * 
     * @param model
     * @date: 2014年2月27日下午3:52:37
     */
    void setModel(T model);

    void setResultMessage(String resultMessage);

    String getResultMessage();
}
