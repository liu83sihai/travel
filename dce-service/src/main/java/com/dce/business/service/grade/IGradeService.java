/*
 * Powered By  huangzl QQ: 272950754
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2018
 */

package com.dce.business.service.grade;

import java.util.List;

/**
 * @author  huangzl QQ: 272950754
 * @version 1.0
 * @since 1.0
 */

import java.util.Map;

import com.dce.business.entity.grade.Grade;
import com.dce.business.entity.page.PageDo;


public interface IGradeService{

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public Grade getById(Integer id);
	
	/**
	 * 更新
	 */
	public int  updateGradeById(Grade newGradeDo);
	
	/**
	 * 新增
	 */
	public int addGrade(Grade newGradeDo);
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<Grade> getGradePage(Map<String, Object> param, PageDo<Grade> page);
	
	
	/**
	 * 多条件查询
	 * @param param
	 * @return
	 */
	public List<Grade> selectgreadname(Map<String, Object> param);
	
	
	/**
	 * 删除
	 */
	public int deleteById(Integer id);
}
