package com.dce.business.dao.touch;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.touch.TouchBonusRecordDo;

public interface ITouchBonusRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TouchBonusRecordDo record);

    int insertSelective(TouchBonusRecordDo record);

    TouchBonusRecordDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TouchBonusRecordDo record);

    int updateByPrimaryKey(TouchBonusRecordDo record);

    List<TouchBonusRecordDo> select(Map<String, Object> params);
    
    List<TouchBonusRecordDo> selectList(Map<String, Object> params);
    
    /**
     * 查询用户最后一次量碰记录 
     * @param userId
     * @return  
     */
    TouchBonusRecordDo getUserTouchBonusRecord(Integer userId);
    
    /**
     * 计算总业绩 
     * @param userId
     * @return  
     */
    Map<String, Object> calTotalPerformance(Integer userId);
}