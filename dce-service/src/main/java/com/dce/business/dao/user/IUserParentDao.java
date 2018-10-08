package com.dce.business.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dce.business.entity.user.UserParentDo;

public interface IUserParentDao {
	
    int deleteByPrimaryKey(Integer id);

    int insert(UserParentDo record);

    int insertSelective(UserParentDo record);

    UserParentDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserParentDo record);

    int updateByPrimaryKey(UserParentDo record);
    
    
    List<Map<String,Object>> selectMyGroup(Map<String,Object> params);
    
    /**
     * 团队成员详情（分级别）
     * @param params
     * @return
     */
    List<Map<String,Object>> TeamDetails(Map<String,Object> params);
   
    /*
     * 查询团队成员
     */
    List<UserParentDo> select(Map<String, Object> params);

    /**
	 * 查看组织结构树
	 * @param 用户ID
	 * @param level 查询层级
	 * @return
	 */
	List<Map<String, Object>> listMyOrg(@Param("parentId")Integer userId, @Param("distance")int level);
	
	Integer selectPosition(Map<String, Object> params);
	
	/**
	 * 取左右区业绩
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> selectPerformance(Map<String, Object> params);

	Map<String, Object> getYJ(Map<String, Object> params);
	
	Map<String, Object> getTodayYJ(Map<String, Object> params);
}