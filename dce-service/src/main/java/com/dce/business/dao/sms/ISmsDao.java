package com.dce.business.dao.sms;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dce.business.entity.sms.SmsDo;

public interface ISmsDao {
	
    int deleteByPrimaryKey(Long messageId);

    int insert(SmsDo record);

    int insertSelective(SmsDo record);

    SmsDo selectByPrimaryKey(Long messageId);

    int updateByPrimaryKeySelective(SmsDo record);

    int updateByPrimaryKey(SmsDo record);

    /**
     * 查询最后发送的短信内容
     * 
     * @param recievers 接收人
     * @return
     */
    SmsDo getLastIdentifyCode(@Param("recievers") String recievers,@Param("businessType") String businessType);

    /** 
     * 获取今天发送的短信数
     * @param recievers 接收人
     * @return  
     */
    Integer getTodayNotifyCount(String recievers);

    /** 
     * 查询分页
     * @param params
     * @return  
     */
    List<SmsDo> getNotifyMessageList(Map<String, Object> params);

    /** 
     * 分页查询，统计总数
     * @param params
     * @return  
     */
    Integer count(Map<String, Object> params);

}