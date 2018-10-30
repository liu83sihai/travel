package com.dce.business.service.impl.agency;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.dce.business.common.util.Constants;

import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.agency.AgencyDo;
import com.dce.business.service.agency.IAgencyService;
import com.dce.business.dao.agency.IAgencyDao;


@Service("agencyService")
public class AgencyServiceImpl implements IAgencyService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
    private IAgencyDao  agencyDao;
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Override
	public AgencyDo getById(int id){
	  return agencyDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Override
	public List<AgencyDo> selectAgency(AgencyDo example){
		return agencyDao.selectAgency(example);
	}
	
	
	
	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateAgencyById(AgencyDo newAgencyDo){
		logger.debug("updateAgency(AgencyDo: "+newAgencyDo);
		return  agencyDao.updateAgencyById(newAgencyDo);		
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addAgency(AgencyDo newAgencyDo){
		logger.debug("addAgency: "+newAgencyDo);
		return agencyDao.addAgency(newAgencyDo);
	}
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<AgencyDo> getAgencyPage(Map<String, Object> param, PageDo<AgencyDo> page){
		logger.info("----getAgencyPage----"+param);
        param.put(Constants.MYBATIS_PAGE, page);
        List<AgencyDo> list =  agencyDao.selectAgencyByPage(param);
        page.setModelList(list);
        return page;
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteById(int id){
		logger.debug("deleteByIdAgency:"+id);
		return  agencyDao.deleteById(id);		
	}

}
