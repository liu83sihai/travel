package com.dce.business.dao.grade;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.grade.Grade;

public interface gradeMapper {

	int insert(Grade record);

	int insertSelective(Grade record);

	Grade selectByPrimaryKey(Integer gradeId);

	int updateByPrimaryKeySelective(Grade record);

	int updateByPrimaryKey(Grade record);
	
	int deleteByPrimaryKey(Integer id);
	
	/**
	 * 多条件分页查询
	 */
	List<Grade> queryListPage(Map<String,Object> map);
	
	/**
	 * 多条件查询
	 * @param param
	 * @return
	 */
	List<Grade> selectgreadname(Map<String, Object> param);
}