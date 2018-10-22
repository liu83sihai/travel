package com.dce.business.service.activity;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.activity.ActivityDo;

public interface IActivityService{

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public ActivityDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<ActivityDo> selectActivity(ActivityDo example);
	
	/**
	 * 更新
	 */
	public int  updateActivityById(ActivityDo newActivityDo);
	
	/**
	 * 新增
	 */
	public int addActivity(ActivityDo newActivityDo);
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<ActivityDo> getActivityPage(Map<String, Object> param, PageDo<ActivityDo> page);
	
	
	/**
	 * 删除
	 */
	public int deleteById(int id);
}
