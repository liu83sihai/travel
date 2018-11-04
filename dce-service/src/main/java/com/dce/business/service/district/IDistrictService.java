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

import com.dce.business.entity.district.District;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.user.UserDo;


public interface IDistrictService{

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public District getById(Integer id);
	
	/**
	 * 更新
	 */
	public int  updateDistrictById(District newDistrictDo);
	
	/**
	 * 选择性更新
	 */
	public int updateSelectiveDistrictById(District district);
	
	/**
	 * 新增
	 */
	public int addDistrict(District newDistrictDo);
	

	/**
	 * 选择性新增
	 */
	 public int insertSelective(District record);
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<UserDo> getDistrictPage(Map<String, Object> param, PageDo<UserDo> page);
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public List<District> getDistrict(Map<String,Object>  map);
	
	
	/**
	 * 删除
	 */
	public int deleteById(Integer districtId);
	
	/**
	 * 多条件查询
	 */
	District selectByPrimaryKeySelective(District record);

}
