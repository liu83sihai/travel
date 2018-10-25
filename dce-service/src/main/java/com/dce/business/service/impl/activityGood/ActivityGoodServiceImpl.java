package com.dce.business.service.impl.activityGood;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.dce.business.common.util.Constants;

import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.activityGood.ActivityGoodDo;
import com.dce.business.service.activityGood.IActivityGoodService;
import com.dce.business.dao.activityGood.IActivityGoodDao;


@Service("activityGoodService")
public class ActivityGoodServiceImpl implements IActivityGoodService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
    private IActivityGoodDao  activityGoodDao;
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Override
	public ActivityGoodDo getById(int id){
	  return activityGoodDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Override
	public List<ActivityGoodDo> selectActivityGood(ActivityGoodDo example){
		return activityGoodDao.selectActivityGood(example);
	}
	
	
	
	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateActivityGoodById(ActivityGoodDo newActivityGoodDo){
		logger.debug("updateActivityGood(ActivityGoodDo: "+newActivityGoodDo);
		return  activityGoodDao.updateActivityGoodById(newActivityGoodDo);		
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addActivityGood(ActivityGoodDo newActivityGoodDo){
		logger.debug("addActivityGood: "+newActivityGoodDo);
		return activityGoodDao.addActivityGood(newActivityGoodDo);
	}
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<ActivityGoodDo> getActivityGoodPage(Map<String, Object> param, PageDo<ActivityGoodDo> page){
		logger.info("----getActivityGoodPage----"+param);
        param.put(Constants.MYBATIS_PAGE, page);
        List<ActivityGoodDo> list =  activityGoodDao.selectActivityGoodByPage(param);
        page.setModelList(list);
        return page;
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteById(ActivityGoodDo activityGoodDo){
		return  activityGoodDao.deleteById(activityGoodDo);		
	}

}
