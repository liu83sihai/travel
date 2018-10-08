package com.dce.business.service.impl.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dce.business.dao.user.IUserStaticDao;
import com.dce.business.entity.user.UserStaticDo;
import com.dce.business.service.user.IUserStaticService;

@Service("userStatic")
public class UserStaticServiceImpl implements IUserStaticService {
    @Resource
    private IUserStaticDao userStaticDao;

    @Override
    public List<UserStaticDo> select(Map<String, Object> params) {
        return userStaticDao.select(params);
    }
    
    @Override
    public List<UserStaticDo> selectSepicStatic(Map<String, Object> params) {
        return userStaticDao.selectSepicStatic(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void updateStaticMoney(Integer id) {
        userStaticDao.updateStaticMoney(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insert(UserStaticDo userStaticDo) {
        return userStaticDao.insertSelective(userStaticDo);
    }

	@Override
	public boolean getUnStaticAward(Integer userid) {
		List<Map<String,Object>> existLst = userStaticDao.selectUnStaticByUserId(userid);
		if(CollectionUtils.isEmpty(existLst)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public int updateStatic(UserStaticDo staticDo) {
		
		return userStaticDao.updateByPrimaryKeySelective(staticDo);
	}
}
