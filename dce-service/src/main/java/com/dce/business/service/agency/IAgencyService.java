package com.dce.business.service.agency;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.agency.AgencyDo;

public interface IAgencyService{

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public AgencyDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<AgencyDo> selectAgency(AgencyDo example);
	
	/**
	 * 更新
	 */
	public int  updateAgencyById(AgencyDo newAgencyDo);
	
	/**
	 * 新增
	 */
	public int addAgency(AgencyDo newAgencyDo);
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<AgencyDo> getAgencyPage(Map<String, Object> param, PageDo<AgencyDo> page);
	
	
	/**
	 * 删除
	 */
	public int deleteById(int id);
}
