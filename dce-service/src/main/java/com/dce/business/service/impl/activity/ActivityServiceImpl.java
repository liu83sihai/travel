package com.dce.business.service.impl.activity;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.dce.business.common.util.Constants;

import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.activity.ActivityDo;
import com.dce.business.service.activity.IActivityService;
import com.dce.business.dao.activity.IActivityDao;


@Service("activityService")
public class ActivityServiceImpl implements IActivityService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
    private IActivityDao  activityDao;
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Override
	public ActivityDo getById(int id){
	  return activityDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Override
	public List<ActivityDo> selectActivity(ActivityDo example){
		return activityDao.selectActivity(example);
	}
	
	
	
	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateActivityById(ActivityDo newActivityDo){
		logger.debug("updateActivity(ActivityDo: "+newActivityDo);
		return  activityDao.updateActivityById(newActivityDo);		
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addActivity(ActivityDo newActivityDo){
		logger.debug("addActivity: "+newActivityDo);
		return activityDao.addActivity(newActivityDo);
	}
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<ActivityDo> getActivityPage(Map<String, Object> param, PageDo<ActivityDo> page){
		logger.info("----getActivityPage----"+param);
        param.put(Constants.MYBATIS_PAGE, page);
        List<ActivityDo> list =  activityDao.selectActivityByPage(param);
        page.setModelList(list);
        return page;
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteById(int id){
		logger.debug("deleteByIdActivity:"+id);
		return  activityDao.deleteById(id);		
	}

}
