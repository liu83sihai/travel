
package com.dce.business.dao.activityGood;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.supplier.SupplierDo;

import com.dce.business.entity.activityGood.ActivityGoodDo;



public interface IActivityGoodDao {

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
	 * 分面查询
	 * @param param
	 * @return
	 */
	public List<ActivityGoodDo> selectActivityGoodByPage(Map<String, Object> param);
	
	/**
	 * 更新
	 */
	public int  updateActivityGoodById(ActivityGoodDo newActivityGoodDo);
	
	/**
	 * 新增
	 */
	public int addActivityGood(ActivityGoodDo newActivityGoodDo);
	
	/**
	 * 删除
	 */
	public int deleteById(ActivityGoodDo activityGoodDo);

}
