package com.dce.business.dao.district;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.district.Regionalawards;

public interface regionalawardsMapper {
    
   
    /**
     * id条件删除
     * @param rewardsareaid
     * @return
     */
    int deleteByPrimaryKey(Integer rewardsareaid);

  /**
   * 添加全部信息
   * @param record
   * @return
   */
    int insert(Regionalawards record);

   /**
    * 选择性添加信息
    * @param record
    * @return
    */
    int insertSelective(Regionalawards record);

    /**
     * id条件查询
     * @param rewardsareaid
     * @return
     */
    Regionalawards selectByPrimaryKey(Integer rewardsareaid);


    /**
     * 选择性更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Regionalawards record);

  /**
   * 更新全部信息
   * @param record
   * @return
   */
    int updateByPrimaryKey(Regionalawards record);
    
    /**
     * 多条件分页查询
     * @param map
     * @return
     */
    List<Regionalawards>  queryListPage(Map<String,Object> map);
    
    /**
     * 多条件查询
     */
    List<Regionalawards> selectByPrimaryKeySelective(Regionalawards record);
}