package com.dce.business.service.impl.message;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dce.business.common.result.Result;
import com.dce.business.common.util.Constants;
import com.dce.business.dao.notice.NoticeDoMapper;
import com.dce.business.entity.notice.NoticeDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.message.INoticeService;

@Service("noticeService")
public class NoticeServiceImpl implements INoticeService {
	
	private final static Logger logger = Logger.getLogger(NoticeServiceImpl.class);


	@Resource
	private NoticeDoMapper noticeDao;
	
	@Override
	public List<NoticeDo> selectNoticeList() {
		
		return noticeDao.selectByExample(null);
	}
	
	/**
	 * 查询
	 */
	@Override
	public NoticeDo selectNoticeById(Integer noticeId) {
		return noticeDao.selectByPrimaryKey(noticeId);
	}
	
	/**
	 * 新增
	 */
	@Override
	public int addNotice(NoticeDo noticedo) {
		// TODO Auto-generated method stub
		logger.info("----addNotice----");
		return noticeDao.insertSelective(noticedo);
	}

	/**
	 * 更新
	 */
	@Override
	public int updateNoticeById(NoticeDo noticedo) {
		logger.info("----updateNoticeByid----");
		return noticeDao.updateByPrimaryKeySelective(noticedo);
	}

	/**
	 * 删除
	 */
	@Override
	public int deleteNoticeById(Integer noticeId) {
		// TODO Auto-generated method stub
		logger.info("----deleteNoticeByid----");
		return noticeDao.deleteByPrimaryKey(noticeId);
	}

	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<NoticeDo> getNoticePage(Map<String, Object> param, PageDo<NoticeDo> page){
		logger.info("----getNoticePage----"+param);
        param.put(Constants.MYBATIS_PAGE, page);
        List<NoticeDo> list =  noticeDao.queryListPage(param);
        page.setModelList(list);
        return page;
	}

}
