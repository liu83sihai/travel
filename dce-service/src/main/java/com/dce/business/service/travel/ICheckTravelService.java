package com.dce.business.service.travel;

import java.util.List;

import com.dce.business.entity.travel.TravelDo;


public interface ICheckTravelService {
	public List<TravelDo> userApplyTravelList(Integer userid);
}
