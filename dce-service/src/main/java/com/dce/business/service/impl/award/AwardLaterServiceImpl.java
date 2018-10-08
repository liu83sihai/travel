package com.dce.business.service.impl.award;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dce.business.dao.award.AwardDao;
import com.dce.business.entity.award.Award;
import com.dce.business.service.award.AwardLaterService;
@Service("awardLaterService")
public class AwardLaterServiceImpl implements AwardLaterService{

	@Resource
	private AwardDao awarddao;
	
	
	@Override
	public boolean deleteByPrimaryKey(Integer awardid) {
		// TODO Auto-generated method stub
		boolean flag=false;
		if(awardid!=null&&awardid!=0){
			flag=awarddao.deleteByPrimaryKey(awardid)>0;
		}
		return flag;
	}

	@Override
	public boolean insert(Award record) {
		// TODO Auto-generated method stub
		boolean flag=false;
		if(record!=null){
			flag=awarddao.insert(record)>0;
		}
		return flag;
	}

	@Override
	public boolean insertSelective(Award record) {
		// TODO Auto-generated method stub
		boolean flag=false;
		if(record!=null){
			flag=awarddao.insertSelective(record)>0;
		}
		return flag;
	}

	@Override
	public Award selectByPrimaryKey(Integer awardid) {
		// TODO Auto-generated method stub
		Award award=new Award();
		if(awardid!=null&&awardid!=0){
			award=awarddao.selectByPrimaryKey(awardid);
		}else{
			award=null;
		}
		return award;
	}

	@Override
	public List<Award> selectAward(Map map) {
		// TODO Auto-generated method stub
		List<Award> list=new ArrayList<Award>();
		if(!map.isEmpty()){
			list=awarddao.selectAward(map);
		}
		return list;
	}

	@Override
	public boolean updateByPrimaryKeySelective(Award record) {
		// TODO Auto-generated method stub
		
		boolean flag=false;
		if(record!=null){
			flag=awarddao.updateByPrimaryKeySelective(record)>0;
		}
		return flag;
	}

	@Override
	public boolean updateByPrimaryKey(Award record) {
		// TODO Auto-generated method stub
		boolean flag=false;
		if(record!=null){
			flag=awarddao.updateByPrimaryKey(record)>0;
		}
		return flag;
	}

}
