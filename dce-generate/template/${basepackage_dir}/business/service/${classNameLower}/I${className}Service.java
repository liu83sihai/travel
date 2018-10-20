<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.business.service.${classNameLower};

import java.util.List;
import java.util.Map;

import ${basepackage}.business.entity.page.PageDo;
import ${basepackage}.business.entity.${classNameLower}.${className}Do;

public interface I${className}Service{

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
	 * 更新
	 */
	public int  update${className}ById(${className}Do new${className}Do);
	
	/**
	 * 新增
	 */
	public int add${className}(${className}Do new${className}Do);
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<${className}Do> get${className}Page(Map<String, Object> param, PageDo<${className}Do> page);
	
	
	/**
	 * 删除
	 */
	public int deleteById(int id);
}
