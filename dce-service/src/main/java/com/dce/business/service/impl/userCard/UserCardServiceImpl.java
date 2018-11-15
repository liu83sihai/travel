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
	 * 激活卡
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int activeUserCard(UserCardDo userCardDo){
		logger.debug("addUserCard: "+userCardDo);
		try {
			//向第三方提交
			String userName = userCardDo.getUserName();
			String mobile = userCardDo.getMobile();
			String orderNo = userCardDo.getOrderNo();
			Integer userId =userCardDo.getUserId();
			
			if(StringUtils.isBlank(userName) || StringUtils.isBlank(mobile) 
					|| StringUtils.isBlank(orderNo) || null == userId ){
				return 4;
			}
			
			
			UserCardDo paramUserCard = new UserCardDo();
			paramUserCard.setOrderNo(orderNo);
			List<UserCardDo> userCardList = userCardDao.selectUserCard(paramUserCard);
			//当前订单已存在
			if(null != userCardList && userCardList.size() > 0){
				UserCardDo userCard = userCardList.get(0);
				int status = userCard.getStatus();
				//已激活
				if( 1 == status){
					return 2;
				}else{ //重新激活
					
					String cardNo=userCard.getCardNo();
					String result = MeituLvUtil.virtualOpen(userName, mobile, cardNo);
					if( "1".equals(result)){
						//激活成功,更新用户卡表
						userCard.setStatus(1);
						userCard.setUpdateDate(new Date());
						userCard.setRemark("用户卡激活成功");
						userCardDao.updateUserCardById(userCard);
						return 1;
					}else{
						userCard.setUpdateDate(new Date());
						userCard.setRemark("用户卡激活失败,失败原因：" + result);
						userCardDao.updateUserCardById(userCard);
						return 3;
					}
					
				}
			}
			
			return userCardDao.addUserCard(userCardDo);
		}catch(Exception e) {
			logger.error("激活卡失败:"+userCardDo);
			logger.error(e);
			return 3;
		}
	}
	
	
	/**
	 * 新增
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int addUserCard(UserCardDo newUserCardDo){
		logger.debug("addUserCard: "+newUserCardDo);
		//向第三方提交
		
		//新增激活卡
		String cardNo = genUIDCode(newUserCardDo.getUserId());
		String result = MeituLvUtil.virtualOpen(newUserCardDo.getUserName(), newUserCardDo.getMobile(), cardNo);
		int status = 0;
		String remark = "激活卡失败,失败代码："+result;
		if( "1".equals(result)){
			status = 1;
			remark = "激活卡成功";
		}
		newUserCardDo.setStatus(status);
		newUserCardDo.setRemark(remark);
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
}
