
package com.dce.business.dao.activity;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.supplier.SupplierDo;

import com.dce.business.entity.activity.ActivityDo;



public interface IActivityDao {

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
	 * 分面查询
	 * @param param
	 * @return
	 */
	public List<ActivityDo> selectActivityByPage(Map<String, Object> param);
	
	/**
	 * 更新
	 */
	public int  updateActivityById(ActivityDo newActivityDo);
	
	/**
	 * 新增
	 */
	public int addActivity(ActivityDo newActivityDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

}
