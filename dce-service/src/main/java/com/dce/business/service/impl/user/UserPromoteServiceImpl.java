/*
 * Powered By  huangzl QQ: 272950754
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2018
 */

package com.dce.business.service.impl.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.util.Constants;
import com.dce.business.dao.user.IUserPromoteDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.user.UserPromoteDo;
import com.dce.business.service.user.IUserPromoteService;

/**
 * @author  huangzl QQ: 272950754
 * @version 1.0
 * @since 1.0
 */


@Service("userPromoteService")
public class UserPromoteServiceImpl implements IUserPromoteService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
    private IUserPromoteDo  userPromoteDao;
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Override
	public UserPromoteDo getById(Integer id){
		
	  return userPromoteDao.selectByPrimaryKey(id);
	}

	
	
	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateUserPromoteById(UserPromoteDo newUserPromoteDo){
		logger.debug("updateUserPromote(userPromoteDo: "+newUserPromoteDo);
		return  userPromoteDao.updateByPrimaryKeySelective(newUserPromoteDo);		
	}
	
	/**
	 * 新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addUserPromote(UserPromoteDo newUserPromoteDo){
		logger.debug("addUserPromote: "+newUserPromoteDo);
		return userPromoteDao.insertSelective(newUserPromoteDo);
	}
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<UserPromoteDo> getUserPromotePage(Map<String, Object> param, PageDo<UserPromoteDo> page){
		logger.info("----getUserPromotePage----"+param);
        param.put(Constants.MYBATIS_PAGE, page);
        List<UserPromoteDo> list =  userPromoteDao.queryListPage(param);
        page.setModelList(list);
        return page;
	}



	@Override
	public int deleteById(Integer id) {
		// TODO Auto-generated method stub
		return userPromoteDao.deleteByPrimaryKey(id);
	}


	/**
	 * 根据用户等级和购买的数量，判断用户升级的等级
	 */
	@Override
	public UserPromoteDo selectUserLevelAntBuyQty(Integer userLevel, int buyQty) {
		Map<String,Object> map=new HashMap<>();
		map.put("userLevel", userLevel);
		map.put("buyQty", buyQty);
		
		return userPromoteDao.selectUserLevelAntBuyQty(map);
	}



	@Override
	public int updataCount(UserPromoteDo record) {
		// TODO Auto-generated method stub
		return userPromoteDao.updataCount(record);
	}
	


}
