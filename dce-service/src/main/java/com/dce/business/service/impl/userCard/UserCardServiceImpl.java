package com.dce.business.service.impl.userCard;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.dce.business.common.util.Constants;
import com.dce.business.common.util.DateUtil;
import com.dce.business.common.util.MeituLvUtil;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.userCard.UserCardDo;
import com.dce.business.service.userCard.IUserCardService;
import com.dce.business.dao.userCard.IUserCardDao;


@Service("userCardService")
public class UserCardServiceImpl implements IUserCardService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
    private IUserCardDao  userCardDao;
	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	@Override
	public UserCardDo getById(int id){
	  return userCardDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	@Override
	public List<UserCardDo> selectUserCard(UserCardDo example){
		return userCardDao.selectUserCard(example);
	}
	
	
	
	/**
	 * 更新
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateUserCardById(UserCardDo newUserCardDo){
		logger.debug("updateUserCard(UserCardDo: "+newUserCardDo);
		return  userCardDao.updateUserCardById(newUserCardDo);		
	}
	
	/**
	 * @return 1 成功，2 已激活  3 激活失败，4 缺少参数
	 * 激活卡
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int activeUserCard(UserCardDo userCardDo){
		logger.debug("activeUserCard: "+userCardDo);
		try {
			
			if( null == userCardDo.getId() ){
				return 4;
			}
			
			UserCardDo tmpCard = userCardDao.getById(userCardDo.getId());
			//已激活
			if( 1 == tmpCard.getStatus().intValue()){
				return 2;
			}
			
			
			String result = MeituLvUtil.virtualOpen(userCardDo.getUserName(), userCardDo.getMobile(), tmpCard.getCardNo());
			int status = 0;
			String remark = "激活卡失败,失败代码：" + result;
			if ("1".equals(result)) {
				status = 1;
				remark = "激活卡成功";
			}else {
				remark="用户卡激活失败,失败原因：" + result;
			}
			
			tmpCard.setStatus(status);
			tmpCard.setRemark(remark);
			tmpCard.setUpdateDate(new Date(System.currentTimeMillis()));
			userCardDao.updateUserCardById(tmpCard);
			return 1;
			
		}catch(Exception e) {
			logger.error("激活卡失败:"+userCardDo);
			logger.error(e);
			return 3;
		}
	}
	
	
	/**
	 * 状态  1 已激活，0待激活，2已赠送，3 已过期
	 * 新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addUserCard(UserCardDo newUserCardDo){
		logger.debug("addUserCard: "+newUserCardDo);
		
		//新增激活卡
		if(StringUtils.isBlank(newUserCardDo.getCardNo())){
			String cardNo = genUIDCode(newUserCardDo.getUserId());
			newUserCardDo.setCardNo(cardNo);
		}
		newUserCardDo.setStatus(0);
		newUserCardDo.setRemark("待激活");
		newUserCardDo.setCreateDate(new Date());
		return userCardDao.addUserCard(newUserCardDo);
	}
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	public PageDo<UserCardDo> getUserCardPage(Map<String, Object> param, PageDo<UserCardDo> page){
		logger.info("----getUserCardPage----"+param);
        param.put(Constants.MYBATIS_PAGE, page);
        List<UserCardDo> list =  userCardDao.selectUserCardByPage(param);
        page.setModelList(list);
        return page;
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteById(int id){
		logger.debug("deleteByIdUserCard:"+id);
		return  userCardDao.deleteById(id);		
	}

    /** 
     * 卡号号
     * @param userId
     * @return  
     */
    public String genUIDCode(Integer userId) {
        StringBuffer sb = new StringBuffer("UID");
        sb.append(userId).append(DateUtil.YYYYMMDDHHMMSS.format(new Date())).append(random());
        return sb.toString();
    }

    /**
     * 产生随机的三位数
     * @return
     */
    private static String random() {
        Random rad = new Random();
        return rad.nextInt(1000) + "";
    }

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void batchAddUserCard(Integer userId, int qty) {
		for(int i = 0 ; i < qty;i++) {
			UserCardDo newUserCardDo = new UserCardDo();
			newUserCardDo.setUserId(userId);
			this.addUserCard(newUserCardDo );			
		}
		
	}
}
