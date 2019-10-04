package com.dce.business.dao.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dce.business.entity.order.FeiHongLog;
import com.dce.business.entity.order.FeiHongOrder;
import com.dce.business.entity.order.Order;
import com.dce.business.entity.order.OrderAddressDo;
import com.dce.business.entity.order.UserFeiHong;

public interface IOrderDao {
	int deleteByPrimaryKey(Long orderId);

	Order selectByPrimaryKey(Integer orderId);

	int updateByPrimaryKeySelective(Order record);

	List<Order> selectOrder(Map<String, Object> paraMap);

	int updateOrderStatusByOldStatus(Map<String, Object> paraMap);

	int updateMatchOrder(Order order);

	Map<String, Object> getBaseInfo(@Param("queryTime") String queryTime);

	List<Map<String, Object>> selectOrderForReport(Map<String, Object> paraMap);

	int selectOrderForReportCount(Map<String, Object> paraMap);

	List<Map<String, Object>> selectOrderByPage(Map<String, Object> paraMap);

	List<Order> selectOrderByCondition(Map<String, Object> paraMap);

	Long selectGuadanAmount(Map<String, Object> paraMap);

	// 获取当前用户所有的订单
	List<Order> selectByUesrId(Integer userId);

	// 生成一个订单
	int insertSelective(Order order);

	// 根据主键id查询订单
	Order selectByPrimaryKey(long orderId);

	// 用户支付，更新订单状态
	int updateByPrimaryKey(Order order);

	// 根据订单编号查询订单
	Order selectByOrderCode(String orderCode);

	// 根据订单编号更新订单
	int updateByOrderCodeSelective(Order order);

	// 发货，更新订单状态
	int sendOutOrder(Integer orderid);

	// 一对多联表查询订单
	List<Order> selectByUesrIdOneToMany(Integer userId);

	// 查询总业绩
	Map<String, Object> selectSum(Map<String, Object> paraMap);

	// 奖励计算成功清空奖励状态
	int updateAwardStatusByOrder(Order order);

	// 查询奖励状态
	Order selectAwardStatus(String orderCode);

	// 根据订单查询出收货人的信息
	OrderAddressDo selectAddressByOrder(Integer orderId);
	
	//获取订单总金额
	List<Order> sumAmount();

	/**
	 *	  订单主从连接查询
	 * @param queryMap
	 * @return
	 */
	List<Order> selectOrderAndDetail(Map<String, Object> queryMap);

	/**
	 *   分红订单
	 * @param feiHongOrder
	 */
	void insertFeiHongOrder(FeiHongOrder feiHongOrder);

	/**
	 * 	查询分红订单
	 * @param selectParaMap
	 * @return
	 */
	List<FeiHongOrder> selectFeiHongOrder(Map<String, Object> selectParaMap);

	void logFeiHong(FeiHongLog fhlog);

	void updateFeiHong(FeiHongOrder fhorder);

	UserFeiHong selectUserFeiHongTotalAmt(@Param("userid") Integer buyerUserId);

	void updateUserFeiHongOrderAmt(@Param("userid") Integer buyerUserId, @Param("orderaward") Double orderaward);

	void updateUserFeiHongAmt(@Param("userid") Integer buyerUserId, @Param("feihongamt") Double feihongamt);
	
	void insertUserFeiHongTotalAmt(UserFeiHong userFeiHong);

	FeiHongOrder selectFeiHongOrderByOrderId(@Param("orderid")Integer orderid);
}