package com.dce.business.dao.secrety;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dce.business.entity.secrety.AuthoritiesDo;

@Repository
public interface IAuthoritiesDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public AuthoritiesDo getById(Long id);
	
	/**
	 *根据条件查询列表
	 */
	public List<AuthoritiesDo> selectAuthorities(Map<String, Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateAuthoritiesById(AuthoritiesDo newAuthoritiesDo);
	
	/**
	 * 新增
	 */
	public int addAuthorities(AuthoritiesDo newAuthoritiesDo);
	
	/**
	 * 删除
	 */
	public int deleteById(Long id);

	public List<AuthoritiesDo> getAuthorityPage(Map<String, Object> params);

	//public List<Map<String,Object>> getGrantedAuthority(int id);

    List<AuthoritiesDo> getGrantedAuthority(Long id);
}
