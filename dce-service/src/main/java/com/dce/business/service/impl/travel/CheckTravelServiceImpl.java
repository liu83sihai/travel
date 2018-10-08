package com.dce.business.service.impl.travel;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dce.business.dao.travel.TravelDoMapper;
import com.dce.business.entity.travel.TravelDo;
import com.dce.business.service.travel.ICheckTravelService;

@Service("checkTravelService")
public class CheckTravelServiceImpl implements ICheckTravelService {

	@Resource
	private TravelDoMapper travelDao;
	
	@Override
	public List<TravelDo> userApplyTravelList(Integer userid) {
		// TODO Auto-generated method stub
		return travelDao.selectByUserId(userid);
	}

}
