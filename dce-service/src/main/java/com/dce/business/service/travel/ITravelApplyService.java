package com.dce.business.service.travel;

import java.util.List;
import java.util.Map;

import com.dce.business.common.result.Result;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.travel.TravelDo;
import com.dce.business.entity.travel.TravelDoExample;

public interface ITravelApplyService {
	public Result<?> travelApply(TravelDo travelDo);
	
	List<TravelDo> selectByExample(TravelDoExample example);

	TravelDo selectByPrimaryKey(Integer applyTravelid);

	public int addapplyTravel(TravelDo travelDo);

	public int updateapplyTravelById(TravelDo travelDo);

	public int deleteapplyTravelById(Integer applyTravelId);

	public PageDo<TravelDo> getTravelapplyTravelPage(Map<String, Object> param, PageDo<TravelDo> page);	
	
	public int updateapplyStateById(Integer applyTravelid);
	
	public Result<?>ravelRevokeById(Integer applyTravelid);
}
