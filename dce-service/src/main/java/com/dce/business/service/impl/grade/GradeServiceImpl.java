/*
 * Powered By  huangzl QQ: 272950754
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2018
 */

package com.dce.business.service.impl.grade;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dce.business.common.util.Constants;
import com.dce.business.dao.grade.gradeMapper;
import com.dce.business.entity.grade.Grade;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.grade.IGradeService;

import org.springframework.transaction.annotation.Propagation;

/**
 * @author  huangzl QQ: 272950754
 * @version 1.0
 * @since 1.0
 */


@Service("gradeService")
public class GradeServiceImpl implements IGradeService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
    private gradeMapper  gradeDao;
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Override
	public Grade getById(Integer id){
	  return gradeDao.selectByPrimaryKey(id);
	}
	
	
	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateGradeById(Grade newGradeDo){
		logger.debug("updateGrade(Grade: "+newGradeDo);
		return  gradeDao.updateByPrimaryKeySelective(newGradeDo);		
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addGrade(Grade newGradeDo){
		logger.debug("addGrade: "+newGradeDo);
		return gradeDao.insertSelective(newGradeDo);
	}
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<Grade> getGradePage(Map<String, Object> param, PageDo<Grade> page){
		logger.info("----getGradePage----"+param);
        param.put(Constants.MYBATIS_PAGE, page);
        List<Grade> list =  gradeDao.queryListPage(param);
        page.setModelList(list);
        return page;
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteById(Integer id){
		logger.debug("deleteByIdGrade:"+id);
		return  gradeDao.deleteByPrimaryKey(id);		
	}


	@Override
	public List<Grade> selectgreadname(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return gradeDao.selectgreadname(param);
	}

}
