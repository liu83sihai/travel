package com.dce.business.dao.user;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.user.UserPromoteDo;


public interface IUserPromoteDo {
   
	
	
    int deleteByPrimaryKey(Integer promoteId);

  
    int insert(UserPromoteDo record);

    
    int insertSelective(UserPromoteDo record);

  
    UserPromoteDo selectByPrimaryKey(Integer promoteId);

  

    int updateByPrimaryKeySelective(UserPromoteDo record);

   
    int updateByPrimaryKey(UserPromoteDo record);
    
    /**
     * 分页多条件查询
     * @param map
     * @return
     */
    List<UserPromoteDo>  queryListPage(Map<String,Object> map);
    
    
    
    /**
     * 根据用户等级和购买的数量，判断用户升级的等级
     * @param map
     * @return
     */
    UserPromoteDo selectUserLevelAntBuyQty(Map<String,Object> map);
    
    
    /**
     * 修改升级条件的数量
     */
    int updataCount(UserPromoteDo record);
    
    
}