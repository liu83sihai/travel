package com.dce.business.service.impl.userCard;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dce.business.common.enums.AccountType;
import com.dce.business.common.enums.IncomeType;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.Constants;
import com.dce.business.common.util.DateUtil;
import com.dce.business.common.util.MeituLvUtil;
import com.dce.business.dao.userCard.IUserCardDao;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.entity.userCard.UserCardDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.user.IUserService;
import com.dce.business.service.userCard.IUserCardService;


@Service("userCardService")
public class UserCardServiceImpl implements IUserCardService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
    private IUserCardDao  userCardDao;
	
	@Resource
	private IUserService userService;
	
	@Resource
	private IAccountService accountService;
	
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

	/**
	  * 前提约束： 只有一张卡不允许转赠， 不能激活其他用户， 已激活的卡不能转赠
	  * 转赠的过程：1. 如果用户不存在，自动注册用户，用户通过找回密码登录，并且推荐人是当前用户, 转赠完成后此卡属于转赠后的用户
	  *         2. 如果用户已存在， 给用户添加一张新卡，如果用户未激活，并且激活此用户
	  * 
	  */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Result<?> convertCard(UserCardDo userCardDo) {
		UserCardDo tmpCard = userCardDao.getById(userCardDo.getId());
		if(tmpCard.getStatus().intValue() != 0) {
			return Result.failureResult("无效卡");
		}
		if(StringUtils.isBlank(userCardDo.getMobile())) {
			return Result.failureResult("接受人手机号不能为空");
		}
		if(StringUtils.isBlank(userCardDo.getUserName())) {
			return Result.failureResult("接受人真实姓名不能为空");
		}
			

		Integer oldUserId = userCardDo.getUserId();
		if (null == oldUserId) {
			return Result.failureResult("无效的转赠用户");
		}

		UserDo oldUserDo = userService.getUser(oldUserId);
		if (null == oldUserDo) {
			return Result.failureResult("转赠用户不存在");
		}
		
		UserCardDo selectExample = new UserCardDo();
		selectExample.setUserId(oldUserId);
		selectExample.setStatus(0);
		List<UserCardDo> userCardLst = userCardDao.selectUserCard(selectExample);
		if(userCardLst == null || userCardLst.size()<2) {
			return Result.failureResult("转赠用户没有多余的旅游可转赠");
		}
		
		//查找接受人
		Map<String, Object> paraM = new HashMap<String,Object>();
		paraM.put("mobile", userCardDo.getMobile());
		List<UserDo> userLst = userService.selectUserCondition(paraM);
		UserDo acceptUser = null;
		//接受人不存在注册一个
		if(userLst == null || userLst.isEmpty()) {
			acceptUser = new UserDo();
			acceptUser.setRefereeid(userCardDo.getUserId());
			acceptUser.setRefereeUserMobile(oldUserDo.getMobile());
			acceptUser.setMobile(userCardDo.getMobile());
			acceptUser.setUserName(userCardDo.getMobile());
			acceptUser.setTrueName(userCardDo.getUserName());
			acceptUser.setUserPassword("123456");
			acceptUser.setTwoPassword("123456");
			acceptUser.setStatus((byte)(0));
			acceptUser.setUserLevel((byte)(1));
			Result<?> result = userService.reg(acceptUser);
		}else {
			acceptUser = userLst.get(0);
			UserDo tmpAcceptUer = new UserDo();
			tmpAcceptUer.setId(acceptUser.getId());
			tmpAcceptUer.setStatus((byte)(1));
			tmpAcceptUer.setUserLevel((byte)(1));
			//激活已经存在的用户
			userService.updateUserByBuy(tmpAcceptUer);
		}
		
		//旅游卡转赠
		accountService.convertBetweenAccount(oldUserDo.getId(), 
				 							 acceptUser.getId(), 
				 							 new BigDecimal(1), 
				 							 AccountType.wallet_active.getAccountType(), 
				 							 AccountType.wallet_active.getAccountType(), 
				 							 IncomeType.TYPE_POINT_OUT, 
				 							 IncomeType.TYPE_POINT_IN);
		
		//修改原卡为转赠
		userCardDo.setStatus(2);
		userCardDo.setRemark("原用户ID：" + oldUserId + "赠送给新用户：" + userCardDo.getMobile());
		userCardDo.setUpdateDate(new Date(System.currentTimeMillis()));
		this.updateUserCardById(userCardDo);
		
		return Result.successResult("转赠成功");
		
	}
}
