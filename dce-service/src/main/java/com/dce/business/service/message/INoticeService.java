package com.dce.business.service.message;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.notice.NoticeDo;
import com.dce.business.entity.page.PageDo;


public interface INoticeService {
	public List<NoticeDo> selectNoticeList();
	
	public NoticeDo selectNoticeById(Integer newsId);
	
	public int addNotice(NoticeDo noticedo);
	
	public int updateNoticeById(NoticeDo noticedo);
	
	public int deleteNoticeById(Integer noticeId);
	
	public PageDo<NoticeDo> getNoticePage(Map<String, Object> param, PageDo<NoticeDo> page);
}
