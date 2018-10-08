package com.dce.business.service.travel;

import java.util.List;
import java.util.Map;

import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.travel.TravelPathDo;


public interface ITravelPathService {
	
	public List<TravelPathDo> selectAll();
	
	TravelPathDo selectByPrimaryKey(Integer id);
	
	public int addPath(TravelPathDo ravelPathDo);
	
	public int updatePathById(TravelPathDo ravelPathDo);
	
	public int deletePathById(Integer id);
	
	
	public PageDo<TravelPathDo> getTravelPathPage(Map<String, Object> param, PageDo<TravelPathDo> page);

}
