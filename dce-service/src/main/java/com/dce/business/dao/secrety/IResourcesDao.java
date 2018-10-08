/**
 * @fileName XXXX.java
 * @auther yepeng
 * @version 1.0
 * @date   2017-08-23 17:57:36
 */

 
package com.dce.business.dao.secrety;

/**
 * @author yepeng
 * @TODO
 */


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dce.business.entity.secrety.ResourcesDo;

@Repository
public interface IResourcesDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public ResourcesDo getById(Long id);
	
	/**
	 *根据条件查询列表
	 */
	public List<ResourcesDo> selectResources(Map<String, Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateResourcesById(ResourcesDo newResourcesDo);
	
	/**
	 * 新增
	 */
	public int addResources(ResourcesDo newResourcesDo);
	
	/**
	 * 删除
	 */
	public int deleteById(Long id);

	public List<Map<String, Object>> selectByUsersId(Long userId);

	public int updateResources(ResourcesDo r);

	public int addResourcesSelective(ResourcesDo r);


	public List<ResourcesDo> getResourcesInInAuthorityPage(Map<String, Object> params);

	public List<ResourcesDo> getResourceNotInAuthorityPage(Map<String, Object> params);

}
