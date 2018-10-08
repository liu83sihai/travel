package com.dce.business.service.user;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.user.UserStaticDo;

public interface IUserStaticService {

    List<UserStaticDo> select(Map<String, Object> params);
    
    List<UserStaticDo> selectSepicStatic(Map<String, Object> params);
    
    

    /** 
     * 更新已返账户金额和状态
     * @param id  
     */
    void updateStaticMoney(Integer id);

    int insert(UserStaticDo userStaticDo);

    /**
     * 是否不释放
     * @param userid
     * @return
     */
	boolean getUnStaticAward(Integer userid);
	
	int updateStatic(UserStaticDo staticDo);
}
