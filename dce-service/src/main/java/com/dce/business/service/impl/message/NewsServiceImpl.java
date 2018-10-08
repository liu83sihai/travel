package com.dce.business.service.impl.message;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dce.business.common.util.Constants;
import com.dce.business.dao.message.INewsDao;
import com.dce.business.entity.message.NewsDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.message.INewsService;

@Service("newsService")
public class NewsServiceImpl implements INewsService {
	
	Logger logger = Logger.getLogger(NewsServiceImpl.class);
	
	@Resource
	private INewsDao newsDao;
	

	@Override
	public List<NewsDo> selectNewsList() {
	
		return newsDao.select();
	}

	@Override
	public NewsDo selectNewsDetail(Integer newsId) {
		return newsDao.selectByPrimaryKey(newsId);
	}

	@Override
	public NewsDo selectLatestNews() {
		// TODO Auto-generated method stub
		return null;
	}
	


	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateYsNewsById(NewsDo newYsNewsDo){
		logger.debug("updateYsNews(NewsDo: "+newYsNewsDo);
		return  newsDao.updateYsNewsById(newYsNewsDo);		
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addYsNews(NewsDo newYsNewsDo){
		logger.debug("addYsNews: "+newYsNewsDo);
		return newsDao.addYsNews(newYsNewsDo);
	}
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<NewsDo> getYsNewsPage(Map<String, Object> param, PageDo<NewsDo> page){
		logger.info("----getYsNewsPage----"+param);
        param.put(Constants.MYBATIS_PAGE, page);
        
        List<NewsDo> list =  newsDao.queryListPage(param);
        page.setModelList(list);
        return page;
	}

	/**
	 * 删除
	 */
	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return newsDao.deleteById(id);
	}
}
