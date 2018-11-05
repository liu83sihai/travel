
package com.dce.business.dao.agency;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.supplier.SupplierDo;

import com.dce.business.entity.agency.AgencyDo;



public interface IAgencyDao {

	/**
	 * 根据用户ID 查询
	 * @parameter userId
	 */
	public AgencyDo getById(int userId);
	
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public AgencyDo getByKeyId(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<AgencyDo> selectAgency(AgencyDo example);
	/**
	 * 分面查询
	 * @param param
	 * @return
	 */
	public List<AgencyDo> selectAgencyByPage(Map<String, Object> param);
	
	/**
	 * 更新
	 */
	public int  updateAgencyById(AgencyDo newAgencyDo);
	
	/**
	 * 新增
	 */
	public int addAgency(AgencyDo newAgencyDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

}
