package com.dce.business.service.impl.feedback;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dce.business.common.result.Result;
import com.dce.business.common.util.Constants;
import com.dce.business.dao.feedback.FeedBackMapper;
import com.dce.business.entity.feedback.FeedBackDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.feedback.IFeedBackService;

@Service("feedBackService")
public class FeedBackServiceImpl implements IFeedBackService {
	
	private final Logger logger = Logger.getLogger(this.getClass());

	@Resource
	private FeedBackMapper feedBackDao;

	/**
	 * 新增
	 */
	@Override
	public Result<?> feedBack(FeedBackDo feedBackDo) {
		int result = feedBackDao.insertSelective(feedBackDo);

		return result > 0 ? Result.successResult("反馈成功!") : Result.failureResult("系统繁忙");
	}
	
	
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Override
	public FeedBackDo getById(Long id){
	  return feedBackDao.selectByPrimaryKey(id.intValue());
	}
	
	/**
	 *根据条件查询列表
	 */
	@Override
	public List<FeedBackDo> selectUserFeedback(Map parameterMap) {
		return feedBackDao.selectFeedBack(parameterMap);
	}
	
	
	
	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateUserFeedbackById(FeedBackDo newUserFeedbackDo){
		logger.debug("updateUserFeedback(FeedBackDo: "+newUserFeedbackDo);
		return  feedBackDao.updateByPrimaryKeySelective(newUserFeedbackDo);		
	}
	
	
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<FeedBackDo> getUserFeedbackPage(Map<String, Object> param, PageDo<FeedBackDo> page){
		logger.info("----getUserFeedbackPage----"+param);
        param.put(Constants.MYBATIS_PAGE, page);
        List<FeedBackDo> list =  feedBackDao.queryListPage(param);
        page.setModelList(list);
        return page;
	}

	/**
	 * 删除
	 * @param feedBackId
	 * @return
	 */
	@Override
	public int deleteByPrimaryKey(Integer feedBackId){
		// TODO Auto-generated method stub
		logger.info("----deleteFeedBackByid----");
		return feedBackDao.deleteByPrimaryKey(feedBackId);
	}



}
