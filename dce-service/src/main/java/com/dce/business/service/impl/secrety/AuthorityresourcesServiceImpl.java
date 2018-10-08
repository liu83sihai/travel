
package com.dce.business.service.impl.secrety;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dce.business.dao.secrety.IAuthorityresourcesDao;
import com.dce.business.dao.secrety.IResourcesDao;
import com.dce.business.entity.secrety.AuthorityresourcesDo;
import com.dce.business.entity.secrety.ResourcesDo;
import com.dce.business.service.secrety.IAuthorityresourcesService;


@Service("authorityresourcesService")
public class AuthorityresourcesServiceImpl implements IAuthorityresourcesService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
    private IAuthorityresourcesDao authorityresourcesDao;
    @Resource
    private IResourcesDao resourcesDao;
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Override
	public AuthorityresourcesDo getById(Long id){
	  return authorityresourcesDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Override
	public List<AuthorityresourcesDo> selectAuthorityresources(Map<String,Object> parameterMap){
		return authorityresourcesDao.selectAuthorityresources(parameterMap);
	}
	
	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateAuthorityresourcesById(AuthorityresourcesDo newAuthorityresourcesDo){
		logger.debug("updateAuthorityresources(AuthorityresourcesDo: "+newAuthorityresourcesDo);
		return authorityresourcesDao.updateAuthorityresourcesById(newAuthorityresourcesDo);
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addAuthorityresources(AuthorityresourcesDo newAuthorityresourcesDo){
		logger.debug("addAuthorityresources: "+newAuthorityresourcesDo);
		return authorityresourcesDao.addAuthorityresources(newAuthorityresourcesDo);
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteById(Long id){
		logger.debug("deleteByIdAuthorityresources:"+id);
		return authorityresourcesDao.deleteById(id);
	}
	
	@Override
	public List<ResourcesDo> getResourcesByUserId(Long userId) {
		List<Map<String,Object>> resource = resourcesDao.selectByUsersId(userId);
		List<ResourcesDo> resourcesList = new ArrayList<ResourcesDo>();
		/**
		 * 整理成相应的权限列表
		 */
		for(Map<String,Object> data:resource){
//			Integer authId=Integer.parseInt(data.get("ID").toString());
			String resourceStr=data.get("RESOURCESTR").toString();
			int resourceId=Integer.parseInt(data.get("RESOURCEID").toString());
			String resourceName=data.get("RESOURCENAME").toString();
			
			ResourcesDo resourcesDo=new ResourcesDo();
			resourcesDo.setId(resourceId);
			resourcesDo.setName(resourceName);
			resourcesDo.setResourceStr(resourceStr);
			resourcesList.add(resourcesDo);
		}
		return resourcesList;
	}

}
