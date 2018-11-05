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
import org.springframework.transaction.annotation.Propagation;

import com.dce.business.common.util.Constants;
import com.dce.business.dao.district.districtMapper;
import com.dce.business.entity.district.District;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.district.IDistrictService;
import com.dce.business.service.user.IUserService;

/**
 * @author  huangzl QQ: 272950754
 * @version 1.0
 * @since 1.0
 */


@Service("districtService")
public class DistrictServiceImpl implements IDistrictService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
    private districtMapper  districtDao;
	@Resource
	private IUserService userService;
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Override
	public District getById(Integer id){
	  return districtDao.selectByPrimaryKey(id);
	}
	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateDistrictById(District newDistrictDo){
		logger.debug("updateDistrict(District: "+newDistrictDo);
		return  districtDao.updateByPrimaryKeySelective(newDistrictDo);		
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addDistrict(District newDistrictDo){
		logger.debug("addDistrict: "+newDistrictDo);
		return districtDao.insertSelective(newDistrictDo);
	}
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<UserDo> getDistrictPage(Map<String, Object> param, PageDo<UserDo> page){
		logger.info("----getDistrictPage----"+param);
       /* param.put(Constants.MYBATIS_PAGE, page);
       // List<UserDo>  list =  userService.selectDistrict(param);
        System.out.println("数据------"+list);
        page.setModelList(list);*/
        return page;
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteById(Integer id){
		logger.debug("deleteByIdDistrict:"+id);
		return  districtDao.deleteByPrimaryKey(id);		
	}
	
	/**
	 * 选择性更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateSelectiveDistrictById(District district) {
		
		return districtDao.updateByPrimaryKeySelective(district);
	}
	/**
	 * 选择性新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int insertSelective(District record) {
		
		return districtDao.insertSelective(record);
	}
	@Override
	public  District selectByPrimaryKeySelective(District record) {
		
		return districtDao.selectByPrimaryKeySelective(record);
	}
	@Override
	public  List<District> selectSelective(District record) {
		
		return districtDao.selectSelective(record);
	}


}
