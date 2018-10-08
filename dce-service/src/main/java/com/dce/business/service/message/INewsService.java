package com.dce.business.service.message;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.message.NewsDo;
import com.dce.business.entity.page.PageDo;

public interface INewsService {

	
	public List<NewsDo> selectNewsList();
	
	public NewsDo selectNewsDetail(Integer newsId);
	
	public NewsDo selectLatestNews();
	
	
	/**
	 * 更新
	 */
	public int  updateYsNewsById(NewsDo newYsNewsDo);
	
	/**
	 * 新增
	 */
	public int addYsNews(NewsDo newYsNewsDo);
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<NewsDo> getYsNewsPage(Map<String, Object> param, PageDo<NewsDo> page);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int deleteById(int id);
	
	
}
