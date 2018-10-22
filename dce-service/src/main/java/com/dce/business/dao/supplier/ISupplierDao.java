
package com.dce.business.dao.supplier;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.supplier.SupplierDo;

import com.dce.business.entity.supplier.SupplierDo;



public interface ISupplierDao {

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
	 * 分面查询
	 * @param param
	 * @return
	 */
	public List<SupplierDo> selectSupplierByPage(Map<String, Object> param);
	
	/**
	 * 更新
	 */
	public int  updateSupplierById(SupplierDo newSupplierDo);
	
	/**
	 * 新增
	 */
	public int addSupplier(SupplierDo newSupplierDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

}
