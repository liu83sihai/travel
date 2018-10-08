/*
 * Powered By  huangzl QQ: 272950754
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2018
 */

package com.dce.business.service.district;

import java.util.List;

/**
 * @author  huangzl QQ: 272950754
 * @version 1.0
 * @since 1.0
 */

import java.util.Map;

import com.dce.business.entity.district.Regionalawards;
import com.dce.business.entity.page.PageDo;


public interface IRegionalawardsService{

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public Regionalawards getById(Integer id);
	
	/**
	 * 更新
	 */
	public int  updateRegionalawardsById(Regionalawards newRegionalawardsDo);
	
	/**
	 * 选择性更新
	 */
	public int updateRegionalawardsSelective(Regionalawards newRegionalawardsDo);
	/**
	 * 新增
	 */
	public int addRegionalawards(Regionalawards newRegionalawardsDo);
	
	/**
	 * 选择性新增
	 */
	public int insertRegionalawardsSelective(Regionalawards newRegionalawardsDo);
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<Regionalawards> getRegionalawardsPage(Map<String, Object> param, PageDo<Regionalawards> page);
	
	
	/**
	 * 删除
	 */
	public int deleteById(Integer id);
	
	/**
	 * 多条件查询
	 */
	List<Regionalawards> selectByPrimaryKeySelective(Regionalawards newRegionalawardsDo);
}
