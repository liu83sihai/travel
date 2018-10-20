<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   

package ${basepackage}.business.dao.${classNameLower};

import java.util.List;
import java.util.Map;

import com.dce.business.entity.supplier.SupplierDo;

import ${basepackage}.business.entity.${classNameLower}.${className}Do;



public interface I${className}Dao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public ${className}Do getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<${className}Do> select${className}(${className}Do example);
	/**
	 * 分面查询
	 * @param param
	 * @return
	 */
	public List<${className}Do> select${className}ByPage(Map<String, Object> param);
	
	/**
	 * 更新
	 */
	public int  update${className}ById(${className}Do new${className}Do);
	
	/**
	 * 新增
	 */
	public int add${className}(${className}Do new${className}Do);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

}
