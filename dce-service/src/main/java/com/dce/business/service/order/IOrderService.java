package com.dce.business.service.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dce.business.common.result.Result;
import com.dce.business.entity.order.FeiHongLog;
import com.dce.business.entity.order.FeiHongOrder;
import com.dce.business.entity.order.Order;
import com.dce.business.entity.order.OrderDetail;
import com.dce.business.entity.order.OrderPayDetail;
import com.dce.business.entity.order.UserFeiHong;
import com.dce.business.entity.page.PageDo;

public interface IOrderService {

	// 获取当前用户所有的订单
	List<Order> selectByUesrId(Integer userId);

	// 生成一个订单
	int insertOrder(Order order);

	// 根据主键id查询订单
	Order selectByPrimaryKey(Integer orderId);

	// 根据订单编号查询订单
	Order selectByOrderCode(String orderCode);

	// 一对多联表查询订单
	List<Order> selectByUesrIdOneToMany(Integer userId);

	// 根据订单编号更新订单
	int updateByOrderCodeSelective(Order order);

	// 根据条件打印订单数据
	List<Order> selectOrderByCondition(Map<String, Object> paraMap);

	// 更新订单状态发货
	int updateByPrimaryKeySelective(Order record, Integer userId);

	// 奖励计算成功清空奖励状态
	int updateAwardStatusByOrder(Order order);

	Integer addOrder(Order order);

	List<Order> selectOrder(Map<String, Object> params);

	int updateOrder(Order order);

	public Map<String, Object> getBaseInfo(String date);

	public PageDo<Map<String, Object>> selectOrderByPage(PageDo<Map<String, Object>> page, Map<String, Object> params);

	/**
	 * 保存订单商品明细
	 * 
	 * @param order
	 * @return
	 */
	Order buyOrder(Order order, int goodstype, List<OrderDetail> orderDetail);

	/**
	 * 
	 * @param chooseGoodsLst
	 * @return
	 */
	Result<Map<String,String>> saveOrder( List<OrderPayDetail> payLst,
										  List<OrderDetail> chooseGoodsLst, 
										  Order order,
										  HttpServletRequest request, 
										  HttpServletResponse response)throws Exception;

	// 查询总业绩
	Map<String, Object> selectSum(Map<String, Object> paraMap);

	public String getAlipayorderStr(Order order);

	public String notify(Map<String, String> conversionParams) throws Exception;

	public Result<String> getWXPayStr(HttpServletRequest request, HttpServletResponse response, Order order)
			throws Exception;

	public Result<?> alipayQuery(String outTradeNo);

	public void orderPay(String orderCode, String gmtPayment);

	/**
	 * 选择商品查询赠品
	 * 
	 * @param chooseGoodsLst
	 * @param order
	 */
	Result<?> chooseGoods(List<OrderDetail> chooseGoodsLst, Order order);

	/**
	 * 从用户地址copy到订单地址
	 * 
	 * @param order
	 * @return
	 */
	public Integer mainOrderAddress(Order order);

	// 获取订单总金额
	List<Order> sumAmount();

	/**
	 * 第三方異步通知的處理
	 * @param map
	 * @return
	 */
	String thirdPayNotify(Map<String, Object> map);

	/**
	 *	  订单主从连接查询
	 * @param queryMap
	 * @return
	 */
	List<Order> selectOrderAndDetail(Map<String, Object> queryMap);

	//分红订单api
	List<FeiHongOrder> selectFeiHongOrder(Map<String,Object> selectParaMap);

	void logFeiHong(FeiHongLog fhlog);

	void updateFeiHong(FeiHongOrder fhorder);
	
	void insertFeiHongOrder(Order order, Integer refUserId);

	//用户总的分红和总下单金额
	void updateUserFeiHongTotalAmt(Integer buyerUserId, double buyAmt);
	//查询用户分红
	UserFeiHong selectFeiHongUser(Integer userid);

	void updateUserFeiHongAmt(Integer userid, BigDecimal wardAmount);

	//根据订单id 查找分红订单
	FeiHongOrder selectFeiHongOrderByOrderId(Integer orderid);
}
