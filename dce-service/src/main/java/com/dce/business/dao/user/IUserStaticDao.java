package com.dce.business.dao.user;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.user.UserStaticDo;

public interface IUserStaticDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserStaticDo record);

    int insertSelective(UserStaticDo record);

    UserStaticDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserStaticDo record);

    int updateByPrimaryKey(UserStaticDo record);
    
    List<UserStaticDo> select(Map<String, Object> params);
    
    List<UserStaticDo> selectSepicStatic(Map<String, Object> params);
    
    
    
    void updateStaticMoney(Integer id);

    /**
     * 是否不需要释放
     * @param userid
     * @return
     */
	List<Map<String, Object>> selectUnStaticByUserId(Integer userId);
}