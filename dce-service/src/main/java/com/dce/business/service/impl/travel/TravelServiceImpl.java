package com.dce.business.service.impl.travel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dce.business.common.result.Result;
import com.dce.business.common.util.Constants;
import com.dce.business.dao.travel.TravelDoMapper;
import com.dce.business.dao.travel.TravelPathMapper;
import com.dce.business.dao.user.IUserDao;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.travel.TravelDo;
import com.dce.business.entity.travel.TravelDoExample;
import com.dce.business.entity.travel.TravelPathDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.travel.ITravelApplyService;

@Service("travelApplyService")
public class TravelServiceImpl implements ITravelApplyService {

	private final static Logger logger = Logger.getLogger(TravelServiceImpl.class);

	@Resource
	private TravelDoMapper travelApplyDao;

	@Resource
	private IUserDao userDao;
	
	
	@Resource
	private TravelPathMapper rravelPathDao;

	/**
	 * 查看所有
	 */
	@Override
	public Result<?> travelApply(TravelDo travelDo) {
		logger.info("----travelApply----");

		int id = travelDo.getUserid();

		UserDo user = userDao.selectByPrimaryKey(id);
		
		int pathid = travelDo.getPathid();
		
		TravelPathDo path = rravelPathDao.selectByPrimaryKey(pathid);

		if (user.getUserLevel() == 1 && travelDo.getPeople() > 2) {
			return Result.failureResult("用户等级为会员,只能申请1-2人同行");
		} else if ("智慧游学".equals(path.getLinename()) && travelDo.getPeople() > 2) {
			return Result.failureResult("智慧游学,只能申请1-2人同行");
		} else {
			int result = travelApplyDao.insertSelective(travelDo);

			return result > 0 ? Result.successResult("申请成功!") : Result.failureResult("系统繁忙");
		}

	}

	/**
	 * 根据id查看
	 */
	@Override
	public TravelDo selectByPrimaryKey(Integer applyTravelid) {
		// TODO Auto-generated method stub
		logger.info("----selectByPrimaryKey----");
		return travelApplyDao.selectByPrimaryKey(applyTravelid);
	}

	/**
	 * 添加
	 */
	@Override
	public int addapplyTravel(TravelDo travelDo) {
		// TODO Auto-generated method stub
		logger.info("----addapplyTravel----");
		return travelApplyDao.insertSelective(travelDo);
	}

	/**
	 * 根据id修改
	 */
	@Override
	public int updateapplyTravelById(TravelDo travelDo) {
		// TODO Auto-generated method stub
		logger.info("----updateapplyTravelById----");
		return travelApplyDao.updateByPrimaryKey(travelDo);
	}

	/**
	 * 根据id删除
	 */
	@Override
	public int deleteapplyTravelById(Integer applyTravelId) {
		// TODO Auto-generated method stub
		logger.info("----deleteapplyTravelById----");
		return travelApplyDao.deleteByPrimaryKey(applyTravelId);
	}

	/**
	 * 分页查询
	 */
	@Override
	public PageDo<TravelDo> getTravelapplyTravelPage(Map<String, Object> param, PageDo<TravelDo> page) {
		// TODO Auto-generated method stub
		logger.info("----getTravelapplyTravelPage----");
		param.put(Constants.MYBATIS_PAGE, page);
		List<TravelDo> list = travelApplyDao.queryListPage(param);
		logger.info("----list----" + list);
		page.setModelList(list);
		return page;
	}

	/**
	 * 通过id修改状态值 实现审批旅游申请
	 */
	@Override
	public int updateapplyStateById(Integer applyTravelid) {
		// TODO Auto-generated method stub
		logger.info("----updateapplyStateById----");
		return travelApplyDao.updateapplyStateById(applyTravelid);
	}

	@Override
	public List<TravelDo> selectByExample(TravelDoExample example) {
		// TODO Auto-generated method stub
		logger.info("----selectByExample----");
		List<TravelDo> result = travelApplyDao.selectByExample(example);

		for (TravelDo travel : result) {
			if ("0".equals(travel.getSex())) {
				travel.setSex("男");
			} else if ("1".equals(travel.getSex())) {
				travel.setSex("女");
			}
			if ("0".equals(travel.getIsbeen())) {
				travel.setIsbeen("是");
			} else if ("1".equals(travel.getIsbeen())) {
				travel.setIsbeen("否");
			}
			if ("0".equals(travel.getState())) {
				travel.setState("通过");
			} else if ("1".equals(travel.getState())) {
				travel.setState("未通过");
			} else if ("2".equals(travel.getState())) {
				travel.setState("撤销");
			}
		}

		return result;
	}

	@Override
	public Result<?> ravelRevokeById(Integer applyTravelid) {
		// TODO Auto-generated method stub
		logger.info("----ravelRevokeById----");

		TravelDo travel = travelApplyDao.selectByPrimaryKey(applyTravelid);
		if ("0".equals(travel.getState())) {
			return Result.failureResult("已通过的申请不能撤销");
		} else {
			int result = travelApplyDao.ravelRevokeById(applyTravelid);
			return result > 0 ? Result.successResult("撤销成功") : Result.failureResult("撤销失败");
		}
	}
}
