package com.dce.business.service.activityGood;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.activityGood.ActivityGoodDo;

public interface IActivityGoodService{

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public ActivityGoodDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<ActivityGoodDo> selectActivityGood(ActivityGoodDo example);
	
	/**
	 * 更新
	 */
	public int  updateActivityGoodById(ActivityGoodDo newActivityGoodDo);
	
	/**
	 * 新增
	 */
	public int addActivityGood(ActivityGoodDo newActivityGoodDo);
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<ActivityGoodDo> getActivityGoodPage(Map<String, Object> param, PageDo<ActivityGoodDo> page);
	
	
	/**
	 * 删除
	 */
	public int deleteById(ActivityGoodDo activityGoodDo);
}
