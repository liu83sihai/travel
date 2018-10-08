/**
 * @fileName XXXX.java
 * @auther yepeng
 * @version 1.0
 * @date   2017-08-23 17:57:23
 */

 
package com.dce.business.service.impl.secrety;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dce.business.common.util.Constants;
import com.dce.business.dao.secrety.IModuleDao;
import com.dce.business.dao.secrety.IResourcesDao;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.secrety.ModuleDo;
import com.dce.business.entity.secrety.ResourcesDo;
import com.dce.business.service.secrety.IModuleService;


@Service("moduleService")
public class ModuleServiceImpl implements IModuleService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
    private IModuleDao moduleDao;
	@Resource
	private IResourcesDao resourcesDao;

	
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Override
	public ModuleDo getById(Long id){
	  return moduleDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Override
	public List<ModuleDo> selectModule(Map<String,Object> parameterMap){
		return moduleDao.selectModule(parameterMap);
	}
	
	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateModuleById(ModuleDo newModuleDo){
		logger.debug("updateModule(ModuleDo: "+newModuleDo);
		return moduleDao.updateModuleById(newModuleDo);
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addModule(ModuleDo newModuleDo){
		logger.debug("addModule: "+newModuleDo);
		return moduleDao.addModule(newModuleDo);
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteById(Long id){
		logger.debug("deleteByIdModule:"+id);
		int rst = moduleDao.deleteById(id);		
		return rst;
	}

	
	/**
	 * 
	 * 通过module查找module对象
	 *
	 * zhangyunhmf
	 *
	 */
	private ModuleDo getModuleByModule(List<ModuleDo> moduleLst, String module){
		
		if(StringUtils.isBlank(module)){
			return null;
		}
		
		if(CollectionUtils.isEmpty(moduleLst)){
			return null;
		}
		
		for(ModuleDo m : moduleLst){
			if(m.getModule().equals(module)){
				return m;
			}
		}
		return null;
	}
	
	
	@Override
	public List<ModuleDo> getUserModules(int userId) {
		
		List<ModuleDo> moduleLst = moduleDao.getAllModule();
		
		List<ResourcesDo> userResourceLst = moduleDao.getUserModules(userId);
		Map<String,ModuleDo> userModules=new HashMap<String,ModuleDo>();
		
		//组装module,将resource 添加到module resourcesList
		for(ResourcesDo  oneResource: userResourceLst){
			String module = oneResource.getModule();
			ModuleDo m = userModules.get(module);
			if(m==null){
				m = this.getModuleByModule(moduleLst, module);
			}
			if(m == null){
				continue;
			}
			
			//设置module 顺序
			if("SYS".equals(module)){
				m.setOrder(-1);
			}
			// 设置菜单显示顺序
			String url= oneResource.getResourceStr();
			if("/menuAdmin/userAdmin.html".equals(url)){
				oneResource.setOrder(1);
			}else if("/menuAdmin/rolesIndex.html".equals(url)){
				oneResource.setOrder(2);
			}else if("/authority/authorityIndex.html".equals(url)){
				oneResource.setOrder(3);
			}else{
				oneResource.setOrder(999);
			}
			
			m.addResource(oneResource);
			userModules.put(module, m);
			
		}
		
        List<ModuleDo> modules = new ArrayList<ModuleDo>(userModules.values());
        Collections.sort(modules);
        return modules;
	}
	
	
	@Override
	public PageDo<ResourcesDo> getResources(PageDo<ResourcesDo> page, String name) {
	     return getResourcesInModule(page,name);
	}
	
	@Override
	public PageDo<ResourcesDo> getResourcesInModule(PageDo<ResourcesDo> page, String resourceName) {
		Map<String,Object> params = new HashMap<>();
		params.put(Constants.MYBATIS_PAGE, page);
		params.put("name", resourceName);
		params.put("resourceStr", resourceName);
		
		List<ResourcesDo> datas = moduleDao.getResourcesInModulePage(params);
		page.setModelList(datas);
		return page;
		
	}

	@Override
	public int deleteOneResource(Integer parseInt) {
		int ret = resourcesDao.deleteById(parseInt + 0L);
        return ret == 1 ? Constants.SUCCESS : Constants.FAIL;
	}

	@Override
	public int saveResources(ResourcesDo r) {
		 r.setEnabled(true);
        r.setIssys(false);
        r.setIcon(r.getIcon() == null ? "icon-nav" : r.getIcon());

        int ret = -1;
        if (r.getId() != null) {
            ret = resourcesDao.updateResourcesById(r);
        } else {
            ret = resourcesDao.addResourcesSelective(r);
        }
        if (ret == 1) {
            return Constants.SUCCESS;
        } else {
            return Constants.FAIL;
        }
	}

	@Override
	public ResourcesDo getOneResource(int id) {
		return resourcesDao.getById(id + 0L);
	}

	@Override
	public List<ModuleDo> getAllModules() {
		return moduleDao.getAllModule();
	}

	@Override
	public PageDo<ModuleDo> getAllModules(PageDo<ModuleDo> page, String moduleName, String moduleNick) {
		
		List<ModuleDo> collection = moduleDao.getAllModule();
		List<ModuleDo> mTempList = new ArrayList<ModuleDo>(collection.size());
		
		if (StringUtils.isBlank(moduleName) && StringUtils.isBlank(moduleNick)) {
			mTempList = collection;
		} else {
			for (ModuleDo m : collection) {
				if (StringUtils.isNotBlank(moduleName) && m.getModule().contains(moduleName)) {
					mTempList.add(m);
				}
				if (StringUtils.isNotBlank(moduleNick) && m.getName().contains(moduleNick)) {
					mTempList.add(m);
				}
			}
		}
		page.setModelList(mTempList);
		return page;
	}
	 /**
     * 检查module是否以dk_开头
     * @param module
     * @return
     */
    private boolean checkHead(ModuleDo module) {

        if (null == module) {

            return false;
        }

        return module.getModule().toUpperCase().startsWith("DK_");
    }

	@Override
	public int saveModule(ModuleDo module) {
		 //检查ｍｏｄｕｌｅ是否以ｄｋ＿开头
        boolean isWithHead = checkHead(module);
        if (!isWithHead) {
            throw new RuntimeException("module 需要以DK_开头");
        }

        int ret = Constants.FAIL;
        if (module.getId() != null) {
            ret = moduleDao.updateModuleById(module);
        } else {
            ret = moduleDao.addModule(module);
        }     
        return ret == 1 ? Constants.SUCCESS : Constants.FAIL;
	}

	@Override
	public ModuleDo getModuleById(long id) {
		
		return this.getById(id);
	}

	@Override
	public int deleteModuleById(Integer moduleId) {
		// TODO Auto-generated method stub
		return this.deleteById(moduleId + 0L);
	}
	
	
}
