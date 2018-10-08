package com.dce.business.dao.award;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.award.Awardlist;

public interface AwardlistMapper {
   
	/**
	 * id条件删除
	 * @param awardid
	 * @return
	 */
    int deleteByPrimaryKey(Integer awardid);

    /**
     * 添加全部信息
     * @param record
     * @return
     */
    int insert(Awardlist record);

    /**
     * 选择性添加信息
     * @param record
     * @return
     */
    int insertSelective(Awardlist record);

   /**
    * id查询信息
    * @param awardid
    * @return
    */
    Awardlist selectByPrimaryKey(Integer awardid);

    /**
     * 选择性更新信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Awardlist record);

    /**
     * 更新全部信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(Awardlist record);
    
    /**
     * 多条件分页查询
     * @param map
     * @return
     */
    List<Awardlist> queryListPage(Map map);
    
    /**
     * 查询奖励金额
     * @param map
     * @return
     */
    Map<String,Object> conditionQueryAward(Map map);

    /**
     * 根据购买者等级和购买数量查询奖励记录
     * @param para
     * @return
     */
	Awardlist selectAwardByUserLevelAntBuyQty(Map<String, Object> para);
	
}