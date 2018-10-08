/*
 * Powered By  huangzl QQ: 272950754
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2018
 */

package com.dce.business.service.impl.aboutUs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dce.business.common.util.Constants;
import com.dce.business.dao.aboutUs.IAboutusDao;
import com.dce.business.entity.aboutUs.AboutusDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.aboutUs.IAboutusService;

/**
 * @author  huangzl QQ: 272950754
 * @version 1.0
 * @since 1.0
 */


@Service("aboutusService")
public class AboutusServiceImpl implements IAboutusService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
    private IAboutusDao  aboutusDao;
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Override
	public AboutusDo getById(Integer id){
	  return aboutusDao.getById(id);
	}
	
	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateAboutusById(AboutusDo newAboutusDo){
		logger.debug("updateAboutus(AboutusDo: "+newAboutusDo);
		return  aboutusDao.updateAboutusById(newAboutusDo);		
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addAboutus(AboutusDo newAboutusDo){
		logger.debug("addAboutus: "+newAboutusDo);
		return aboutusDao.addAboutus(newAboutusDo);
	}
	
	
	/**
	 * 删除
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteById(Integer id){
		logger.debug("deleteByIdAboutus:"+id);
		return  aboutusDao.deleteById(id);		
	}


	@Override
	public PageDo<Map<String, Object>> getAboutusPage(Map<String, Object> param, PageDo<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		logger.info("----getAboutusPage----"+param);
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		param.put(Constants.MYBATIS_PAGE, page);
		List<Map<String, Object>> list = aboutusDao.selectAboutUsByPage(param);
		page.setModelList(list);
        return page;
	}

	@Override
	public List<AboutusDo> getAllAboutUs() {
		// TODO Auto-generated method stub
		return aboutusDao.getAllAboutUs();
	}

}
