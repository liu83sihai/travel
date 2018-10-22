<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.business.service.impl.${classNameLower};
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.dce.business.common.util.Constants;

import ${basepackage}.business.entity.page.PageDo;
import ${basepackage}.business.entity.${classNameLower}.${className}Do;
import ${basepackage}.business.service.${classNameLower}.I${className}Service;
import ${basepackage}.business.dao.${classNameLower}.I${className}Dao;


@Service("${classNameLower}Service")
public class ${className}ServiceImpl implements I${className}Service {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
    private I${className}Dao  ${classNameLower}Dao;
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Override
	public ${className}Do getById(int id){
	  return ${classNameLower}Dao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Override
	public List<${className}Do> select${className}(${className}Do example){
		return ${classNameLower}Dao.select${className}(example);
	}
	
	
	
	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int update${className}ById(${className}Do new${className}Do){
		logger.debug("update${className}(${className}Do: "+new${className}Do);
		return  ${classNameLower}Dao.update${className}ById(new${className}Do);		
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int add${className}(${className}Do new${className}Do){
		logger.debug("add${className}: "+new${className}Do);
		return ${classNameLower}Dao.add${className}(new${className}Do);
	}
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<${className}Do> get${className}Page(Map<String, Object> param, PageDo<${className}Do> page){
		logger.info("----get${className}Page----"+param);
        param.put(Constants.MYBATIS_PAGE, page);
        List<${className}Do> list =  ${classNameLower}Dao.select${className}ByPage(param);
        page.setModelList(list);
        return page;
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteById(int id){
		logger.debug("deleteById${className}:"+id);
		return  ${classNameLower}Dao.deleteById(id);		
	}

}
