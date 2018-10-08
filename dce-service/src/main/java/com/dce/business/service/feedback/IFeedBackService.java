package com.dce.business.service.feedback;

import java.util.List;
import java.util.Map;

import com.dce.business.common.result.Result;
import com.dce.business.entity.feedback.FeedBackDo;
import com.dce.business.entity.page.PageDo;

public interface IFeedBackService {
	/**
	 * 新增
	 * @param feedBackDo
	 * @return
	 */
	public Result<?> feedBack(FeedBackDo feedBackDo);
	
	
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public FeedBackDo getById(Long id);
	
	/**
	 *根据条件查询列表
	 */
	public List<FeedBackDo> selectUserFeedback(Map parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateUserFeedbackById(FeedBackDo newUserFeedbackDo);
	
	/**
	 * 删除
	 * @param newsId
	 * @return
	 */
	public int deleteByPrimaryKey(Integer feedBackId);
	
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<FeedBackDo> getUserFeedbackPage(Map<String, Object> param, PageDo<FeedBackDo> page);


	
	
}
