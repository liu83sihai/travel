package com.dce.business.service.supplier;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.supplier.SupplierDo;

public interface ISupplierService{

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public SupplierDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<SupplierDo> selectSupplier(SupplierDo example);
	
	/**
	 * 更新
	 */
	public int  updateSupplierById(SupplierDo newSupplierDo);
	
	/**
	 * 新增
	 */
	public int addSupplier(SupplierDo newSupplierDo);
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<SupplierDo> getSupplierPage(Map<String, Object> param, PageDo<SupplierDo> page);
	
	
	/**
	 * 删除
	 */
	public int deleteById(int id);
}
