package com.dce.business.dao.district;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.district.District;

public interface districtMapper {
   
   /**
    * id条件删除
    * @param districtid
    * @return
    */
    int deleteByPrimaryKey(Integer districtId);

   /**
    * 添加全部信息
    * @param record
    * @return
    */
    int insert(District record);

    /**
     * 选择性添加
     * @param record
     * @return
     */
    int insertSelective(District record);

    /**
     * id条件查询
     * @param districtid
     * @return
     */
    District selectByPrimaryKey(Integer districtId);
    
   /**
    * 选择性更新
    * @param record
    * @return
    */
    int updateByPrimaryKeySelective(District record);

  /**
   * 更新全部信息
   * @param record
   * @return
   */
    int updateByPrimaryKey(District record);
    
    /**
     * 分页查询
     */
    List<District> queryListPage(Map<String,Object>  map);
    
    /**
     * 多条件查询
     */
    District  selectByPrimaryKeySelective(District record);
    
}