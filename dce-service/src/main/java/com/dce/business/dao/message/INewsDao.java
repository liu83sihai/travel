package com.dce.business.dao.message;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.message.NewsDo;

public interface INewsDao {

	List<NewsDo> select();
	
	NewsDo selectByPrimaryKey(Integer id);
	
	/**
	 * 更新
	 */
	public int  updateYsNewsById(NewsDo newYsNewsDo);
	
	/**
	 * 新增
	 */
	public int addYsNews(NewsDo newYsNewsDo);

	List<NewsDo> queryListPage(Map<String, Object> param);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int deleteById(int id);
}
