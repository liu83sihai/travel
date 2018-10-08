package com.dce.business.service.impl.travel;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.dce.business.common.util.Constants;
import com.dce.business.dao.travel.TravelPathMapper;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.travel.TravelPathDo;
import com.dce.business.service.travel.ITravelPathService;

@Service("travelPathService")
public class TravelPathServiceImpl implements ITravelPathService {
	
	private final static Logger logger = Logger.getLogger(TravelPathServiceImpl.class);

	@Resource
	private TravelPathMapper travelPathMapperdao;
	
	/**
	 * 查看所有
	 */
	@Override
	public List<TravelPathDo> selectAll() {
		logger.info("----selectAll----");
		return travelPathMapperdao.selectPathAll();
	}

	
	/**
	 * 根据id查看
	 */
	@Override
	public TravelPathDo selectByPrimaryKey(Integer pathid) {
		// TODO Auto-generated method stub
		logger.info("----selectPathByid----");
		return travelPathMapperdao.selectByPrimaryKey(pathid);
	}

	
	/**
	 * 添加路线 
	 */
	@Override
	public int addPath(TravelPathDo ravelPathDo) {
		// TODO Auto-generated method stub
		logger.info("----addPathByid----");
		return travelPathMapperdao.insertSelective(ravelPathDo);
	}

	
	/**
	 * 更新路线
	 */
	@Override
	public int updatePathById(TravelPathDo ravelPathDo) {
		// TODO Auto-generated method stub
		logger.info("----updatePathByid----");
		return travelPathMapperdao.updateByPrimaryKeySelective(ravelPathDo);
	}

	
	/**
	 *根据id删除路线 
	 */
	@Override
	public int deletePathById(Integer pathId) {
		// TODO Auto-generated method stub
		logger.info("----deletePathByid----");
		return travelPathMapperdao.deleteByPrimaryKey(pathId);
	}

	
	/**
	 * 分页查询
	 */
	@Override
	public PageDo<TravelPathDo> getTravelPathPage(Map<String, Object> param, PageDo<TravelPathDo> page) {
		logger.info("----getPathPage----"+param);
        param.put(Constants.MYBATIS_PAGE, page);
        List<TravelPathDo> list =  travelPathMapperdao.queryListPage(param);
        page.setModelList(list);
        return page;
	}

}
