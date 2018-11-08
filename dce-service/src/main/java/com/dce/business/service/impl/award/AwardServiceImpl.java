package com.dce.business.service.impl.award;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.dce.business.entity.order.Order;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.award.IAwardService;
import com.dce.business.service.order.IOrderService;
import com.dce.business.service.user.IUserService;

@Component("awardService")
public class AwardServiceImpl implements IAwardService,InitializingBean, ApplicationContextAware  {

	private static ApplicationContext applicationContext;
	
	private final static Logger logger = LoggerFactory.getLogger(AwardServiceImpl.class);
	

	private List<IAwardCalculator> awardCalculatorList;
	
	@Resource
	private IOrderService orderService;

	@Resource
	private IUserService userService;
	

	public void setAwardCalculatorList(List<IAwardCalculator> awardCalculatorList) {
		this.awardCalculatorList = awardCalculatorList;
	}


	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void calcAward(Integer buyUserId, Integer orderId) {
		logger.info("计算奖励开始 购买者ID:"+buyUserId+" 订单id"+orderId);
		try{
			Assert.notNull(buyUserId, "购买者ID不能为空");
			Assert.notNull(orderId, "订单ID不能为空");
			
			Order order = orderService.selectByPrimaryKey(orderId);
			UserDo buyer = userService.getUser(buyUserId);
			
			Assert.notNull(buyUserId, "无效的购买者ID");
			Assert.notNull(orderId, "无效的订单ID");
			
			for(IAwardCalculator awardCalc : awardCalculatorList){
				awardCalc.doAward(buyer, order);
			}
			Order orders =  new Order();
			orders.setOrderid(orderId);
			orders.setAwardStatus("success");
			orderService.updateOrder(orders);
		}catch(Exception e){
			logger.info("计算奖励出错 购买者ID:"+buyUserId+" 订单id"+orderId);
			logger.error("计算奖励出错 购买者ID:"+buyUserId+" 订单id"+orderId, e);
			e.printStackTrace();
			Order order =  new Order();
			order.setOrderid(orderId);
			order.setAwardStatus("fail");
			order.setAwardRemark(e.getMessage());
			orderService.updateOrder(order);
		}
		logger.info("计算奖励结束 购买者ID:"+buyUserId+" 订单id"+orderId);
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		IAwardCalculator buyerCalc = (IAwardCalculator)applicationContext.getBean("buyerAwardCalculator");
		IAwardCalculator refCalc =   (IAwardCalculator)applicationContext.getBean("refereeAwardCalculator");
		IAwardCalculator upgradeCalc = (IAwardCalculator)applicationContext.getBean("refereeUpgrade");
		System.out.println("======================================buyerCalc:"+buyerCalc.getClass());
		System.out.println("======================================refCalc:"+refCalc.getClass());
		System.out.println("======================================upgradeCalc:"+upgradeCalc.getClass());
		
		awardCalculatorList.add(buyerCalc);
		awardCalculatorList.add(refCalc);
		awardCalculatorList.add(upgradeCalc);
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;		
	}

}
