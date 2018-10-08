/**
 * @fileName XXXX.java
 * @auther yepeng
 * @version 1.0
 * @date   2017-08-23 17:57:23
 */

 
package com.dce.business.dao.secrety;

/**
 * @author yepeng
 * @TODO
 */


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dce.business.entity.secrety.ModuleDo;
import com.dce.business.entity.secrety.ResourcesDo;

@Repository
public interface IModuleDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public ModuleDo getById(Long id);
	
	/**
	 *根据条件查询列表
	 */
	public List<ModuleDo> selectModule(Map<String, Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateModuleById(ModuleDo newModuleDo);
	
	/**
	 * 新增
	 */
	public int addModule(ModuleDo newModuleDo);
	
	/**
	 * 删除
	 */
	public int deleteById(Long id);
	/**
	 * 2017年8月30日17:49:24
	 */
	public List<ResourcesDo> getUserModules(int userId);

	public List<ModuleDo> getAllModule();

	public List<ResourcesDo> getResourcesInModulePage(Map<String, Object> params);

}
