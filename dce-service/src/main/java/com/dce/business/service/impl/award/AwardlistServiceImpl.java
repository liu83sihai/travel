/*
 * Powered By  huangzl QQ: 272950754
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2018
 */

package com.dce.business.service.impl.award;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dce.business.common.util.Constants;
import com.dce.business.dao.award.AwardlistMapper;
import com.dce.business.entity.award.Awardlist;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.award.IAwardlistService;

import org.springframework.transaction.annotation.Propagation;

/**
 * @author  huangzl QQ: 272950754
 * @version 1.0
 * @since 1.0
 */


@Service("awardlistService")
public class AwardlistServiceImpl implements IAwardlistService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Resource
    private AwardlistMapper  awardlistDao;
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Override
	public Awardlist getById(Integer id){
	  return awardlistDao.selectByPrimaryKey(id);
	}
	
	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateAwardlistById(Awardlist newAwardlistDo){
		logger.debug("updateAwardlist(Awardlist: "+newAwardlistDo);
		return  awardlistDao.updateByPrimaryKeySelective(newAwardlistDo);		
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addAwardlist(Awardlist newAwardlistDo){
		logger.debug("addAwardlist: "+newAwardlistDo);
		return awardlistDao.insertSelective(newAwardlistDo);
	}
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<Awardlist> getAwardlistPage(Map<String, Object> param, PageDo<Awardlist> page){
		logger.info("----getAwardlistPage----"+param);
        param.put(Constants.MYBATIS_PAGE, page);
        List<Awardlist> list =  awardlistDao.queryListPage(param);
        page.setModelList(list);
        return page;
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteById(Integer id){
		logger.debug("deleteByIdAwardlist:"+id);
		return  awardlistDao.deleteByPrimaryKey(id);		
	}

	/**
	 * 选择性更新信息
	 */
	@Override
	public int updateAwardlistSelective(Awardlist newAwardlistDo) {
		return awardlistDao.updateByPrimaryKeySelective(newAwardlistDo);
	}

	/**
	 * 选择性添加信息
	 */
	@Override
	public int addAwardlistSelective(Awardlist newAwardlistDo) {
		return awardlistDao.insertSelective(newAwardlistDo);
	}

	/**
	 * 查询推荐人奖励金额
	 */
	@Override
	public Map<String, Object> conditionQueryAward(Map map) {
		if(map ==null){
			logger.error("无条件判断");
			return null;
		}
		return awardlistDao.conditionQueryAward(map);
	}

	@Override
	public Awardlist getAwardConfigByQtyAndBuyerLevel(Byte userLevel, int buyQty) {
		if( null== userLevel){
			userLevel=0;
		}
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("buyerLecel", userLevel);
		map.put("buyQty", buyQty);
		return awardlistDao.selectAwardByUserLevelAntBuyQty(map);
	}

}
