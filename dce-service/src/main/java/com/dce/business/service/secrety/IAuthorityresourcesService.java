package com.dce.business.service.secrety;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.secrety.AuthorityresourcesDo;
import com.dce.business.entity.secrety.ResourcesDo;

public interface IAuthorityresourcesService {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public AuthorityresourcesDo getById(Long id);
	
	/**
	 *根据条件查询列表
	 */
	public List<AuthorityresourcesDo> selectAuthorityresources(Map<String, Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateAuthorityresourcesById(AuthorityresourcesDo newAuthorityresourcesDo);
	
	/**
	 * 新增
	 */
	public int addAuthorityresources(AuthorityresourcesDo newAuthorityresourcesDo);
	
	/**
	 * 删除
	 */
	public int deleteById(Long id);
	/**
	 * 根据用户ID去获取对应的权限菜单
	 * @param userId 用户ID
	 * @return 权限菜单
	 */
	public List<ResourcesDo> getResourcesByUserId(Long userId);
}
