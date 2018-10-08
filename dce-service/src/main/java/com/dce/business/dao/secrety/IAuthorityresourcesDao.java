/**
 * @fileName XXXX.java
 * @auther yepeng
 * @version 1.0
 * @date   2017-08-23 17:59:15
 */

 
package com.dce.business.dao.secrety;

/**
 * @author yepeng
 * @TODO
 */


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dce.business.entity.secrety.AuthorityresourcesDo;

@Repository
public interface IAuthorityresourcesDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	 AuthorityresourcesDo getById(Long id);
	
	/**
	 *根据条件查询列表
	 */
	 List<AuthorityresourcesDo> selectAuthorityresources(Map<String, Object> parameterMap);
	
	/**
	 * 更新
	 */
	 int  updateAuthorityresourcesById(AuthorityresourcesDo newAuthorityresourcesDo);
	
	/**
	 * 新增
	 */
	 int addAuthorityresources(AuthorityresourcesDo newAuthorityresourcesDo);
	
	/**
	 * 删除
	 */
	 int deleteById(Long id);
	
	 int deleteRelationById(AuthorityresourcesDo newAuthorityresourcesDo);

	 List<Map<String,Object>> getAuthorities();

	 int checkRelationExist(AuthorityresourcesDo ar);

	 int addRelation(AuthorityresourcesDo ar);

}
