/**
 * @fileName XXXX.java
 * @auther yepeng
 * @version 1.0
 * @date   2017-08-23 17:58:03
 */


package com.dce.business.service.secrety;

/**
 * @author yepeng
 * @TODO
 */

import java.util.List;
import java.util.Map;

import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.secrety.AuthoritiesDo;
import com.dce.business.entity.secrety.AuthorityresourcesDo;
import com.dce.business.entity.secrety.ResourcesDo;


public interface IAuthoritiesService {

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
	public int updateAuthoritiesById(AuthoritiesDo newAuthoritiesDo);

	/**
	 * 新增
	 */
	public int addAuthorities(AuthoritiesDo newAuthoritiesDo);

	/**
	 * 删除
	 */
	public int deleteById(Long id);

	public PageDo<AuthoritiesDo> getAuthority(PageDo<AuthoritiesDo> page, String name, String authDesc);

	public PageDo<AuthoritiesDo> getInOrNotAuthoritiesByRoleId(PageDo<AuthoritiesDo> pageDo, int roleId, boolean b);

	public Map<String, List<String>> getResourceMap();

	public PageDo<ResourcesDo> getResourcesInOrNotAuthority(PageDo<ResourcesDo> page, int authorityId, boolean b,
                                                            String filterName, String filterLinks);

	public int addAuthorityResource(AuthorityresourcesDo ar);

	public int deleteAuthorityResource(AuthorityresourcesDo ar);

	public AuthoritiesDo getAuthorities(int parseInt);

	public List<Map<String,Object>> getAuthorities();

	public List<AuthoritiesDo> getGrantedAuthority(long id);


}
