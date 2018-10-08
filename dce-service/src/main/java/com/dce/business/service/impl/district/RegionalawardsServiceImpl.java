/*
 * Powered By  huangzl QQ: 272950754
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2018
 */

package com.dce.business.service.impl.district;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.util.Constants;
import com.dce.business.dao.district.regionalawardsMapper;
import com.dce.business.entity.district.Regionalawards;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.district.IRegionalawardsService;

import org.springframework.transaction.annotation.Propagation;

/**
 * @author  huangzl QQ: 272950754
 * @version 1.0
 * @since 1.0
 */


@Service("regionalawardsService")
public class RegionalawardsServiceImpl implements IRegionalawardsService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
    private regionalawardsMapper  regionalawardsDao;
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Override
	public Regionalawards getById(Integer id){
	  return regionalawardsDao.selectByPrimaryKey(id);
	}
	/*
	*//**
	 *根据条件查询列表
	 *//*
	@Override
	public List<Regionalawards> selectRegionalawards(RegionalawardsExample example){
		return regionalawardsDao.selectByExample(example);
	}
	
	*/
	
	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateRegionalawardsById(Regionalawards newRegionalawardsDo){
		logger.debug("updateRegionalawards(Regionalawards: "+newRegionalawardsDo);
		return  regionalawardsDao.updateByPrimaryKeySelective(newRegionalawardsDo);		
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addRegionalawards(Regionalawards newRegionalawardsDo){
		logger.debug("addRegionalawards: "+newRegionalawardsDo);
		return regionalawardsDao.insertSelective(newRegionalawardsDo);
	}
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<Regionalawards> getRegionalawardsPage(Map<String, Object> param, PageDo<Regionalawards> page){
		logger.info("----getRegionalawardsPage----"+param);
        param.put(Constants.MYBATIS_PAGE, page);
        List<Regionalawards> list =  regionalawardsDao.queryListPage(param);
        page.setModelList(list);
        return page;
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteById(Integer id){
		logger.debug("deleteByIdRegionalawards:"+id);
		return  regionalawardsDao.deleteByPrimaryKey(id);		
	}

	/**
	 * 选择性更新信息
	 */
	@Override
	public int updateRegionalawardsSelective(Regionalawards newRegionalawardsDo) {
		return regionalawardsDao.updateByPrimaryKeySelective(newRegionalawardsDo);
	}

	/**
	 * 选择性添加
	 */
	@Override
	public int insertRegionalawardsSelective(Regionalawards newRegionalawardsDo) {
		return regionalawardsDao.insertSelective(newRegionalawardsDo);
	}

	@Override
	public List<Regionalawards> selectByPrimaryKeySelective(Regionalawards newRegionalawardsDo) {
		
		return regionalawardsDao.selectByPrimaryKeySelective(newRegionalawardsDo);
	}

}
