package com.dce.business.service.impl.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.dce.business.common.alipay.util.AlipayConfig;
import com.dce.business.common.enums.AccountType;
import com.dce.business.common.enums.IncomeType;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.Constants;
import com.dce.business.common.util.DateUtil;
import com.dce.business.common.util.OrderCodeUtil;
import com.dce.business.common.wxPay.app.WeixiPayUtil;
import com.dce.business.common.wxPay.handler.PrepayIdRequestHandler;
import com.dce.business.common.wxPay.util.MD5Util;
import com.dce.business.common.wxPay.util.WXPayConfig;
import com.dce.business.common.wxPay.util.WXUtil;
import com.dce.business.dao.order.IOrderDao;
import com.dce.business.dao.order.OrderDetailMapper;
import com.dce.business.dao.trade.IKLineDao;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.alipaymentOrder.AlipaymentOrder;
import com.dce.business.entity.goods.CTGoodsDo;
import com.dce.business.entity.order.Order;
import com.dce.business.entity.order.OrderAddressDo;
import com.dce.business.entity.order.OrderDetail;
import com.dce.business.entity.order.OrderDetailExample;
import com.dce.business.entity.order.OrderPayDetail;
import com.dce.business.entity.order.OrderSendOut;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.user.UserAddressDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.award.IAwardService;
import com.dce.business.service.dict.ICtCurrencyService;
import com.dce.business.service.dict.ILoanDictService;
import com.dce.business.service.goods.ICTGoodsService;
import com.dce.business.service.order.IOrderAdressService;
import com.dce.business.service.order.IOrderSendoutService;
import com.dce.business.service.order.IOrderService;
import com.dce.business.service.user.IUserService;
import com.dce.business.service.user.UserAdressService;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {
	private final static Logger logger = Logger.getLogger(OrderServiceImpl.class);
	@Resource
	private OrderDetailMapper orderDetailDao;
	@Resource
	private IOrderDao orderDao;
	@Resource
	private IAccountService accountService;
	@Resource
	private IKLineDao kLineDao;
	@Resource
	private ILoanDictService loanDictService;
	@Resource
	private ICtCurrencyService ctCurrencyService;
	@Resource
	private IOrderSendoutService orderSendOutService;
	@Resource
	private ICTGoodsService ctGoodsService;
	@Resource
	private AlipaymentOrderService alipaymentOrderService;
	@Resource
	private IAwardService awardService;
	@Resource
	private IUserService userService;

	@Resource
	private UserAdressService userAdressService;
	@Resource
	private IOrderAdressService orderAdressService;

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer addOrder(Order order) {
		if(StringUtils.isBlank(order.getOrdercode())) {
			// 产生订单编号
			String orderCode = OrderCodeUtil.genOrderCode(order.getUserid());
			order.setOrdercode(orderCode);
		}
		orderDao.insertSelective(order);
		return order.getOrderid();
	}

	public Map<String, Object> getBaseInfo(String date) {

		Map<String, Object> result = orderDao.getBaseInfo(date);
		if (result == null) {
			result = new HashMap<String, Object>();
		}
		return result;
	}

	@Override
	public int updateAwardStatusByOrder(Order order) {
		if (order == null) {
			return 0;
		}
		return orderDao.updateAwardStatusByOrder(order);
	}

	/**
	 * 后台分页查询
	 */
	@Override
	public PageDo<Map<String, Object>> selectOrderByPage(PageDo<Map<String, Object>> page, Map<String, Object> params) {
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		params.put(Constants.MYBATIS_PAGE, page);
		List<Map<String, Object>> list = orderDao.selectOrderByPage(params);

		// 查询出赠品和商品详情
		for (Map<String, Object> order : list) {
			Integer orderId = Integer.valueOf(order.get("orderid").toString());
			List<OrderDetail> orderDetail = orderDetailDao.selectByOrderId(orderId);
			// 拼接商品和赠品
			StringBuffer orderStr = new StringBuffer();
			StringBuffer awardStr = new StringBuffer();
			for (OrderDetail detail : orderDetail) {
				// 过滤没有明细的订单
				if (null == detail.getGoodsId()) {
					continue;
				}
				// 设置商品名称
				CTGoodsDo goods = ctGoodsService.selectById(Long.valueOf(detail.getGoodsId()));
				// 赠品
				if (detail.getRemark().equals("0")) {
					detail.setGoodsName(goods.getTitle());
					awardStr.append(detail.getGoodsName());
					awardStr.append(detail.getQuantity() + "盒");
					awardStr.append(" ");
					// 商品
				} else {
					detail.setGoodsName(goods.getTitle());
					orderStr.append(detail.getGoodsName());
					orderStr.append(detail.getQuantity() + "盒");
					orderStr.append(" ");
				}
			}
			order.put("orderDetailList", orderStr);
			order.put("awardDetailLst", awardStr);
		}
		page.setModelList(list);
		return page;
	}

	/**
	 * 获取用户所有的订单
	 */
	@Override
	public List<Order> selectByUesrId(Integer userId) {

		return orderDao.selectByUesrId(userId);
	}

	/**
	 * 生成一个订单
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int insertOrder(Order order) {
		// 订单明细表插入数据
		return orderDao.insertSelective(order);
	}

	/**
	 * （支付）更新订单
	 */
	@Override
	public int updateOrder(Order order) {
		if (order == null) {
			return 0;
		}
		return orderDao.updateByPrimaryKeySelective(order);
	}

	@Override
	public Order selectByPrimaryKey(Integer orderId) {

		return orderDao.selectByPrimaryKey(orderId);
	}

	@Override
	public Order selectByOrderCode(String orderCode) {

		return orderDao.selectByOrderCode(orderCode);
	}

	@Override
	public int updateByOrderCodeSelective(Order order) {

		return orderDao.updateByOrderCodeSelective(order);
	}

	@Override
	public List<Order> selectOrder(Map<String, Object> params) {

		return orderDao.selectOrder(params);
	}

	// 查询总业绩
	@Override
	public Map<String, Object> selectSum(Map<String, Object> paraMap) {

		return orderDao.selectSum(paraMap);
	}

	/**
	 * 订单记录
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Order> selectByUesrIdOneToMany(Integer userId) {

		List<Order> list = orderDao.selectByUesrIdOneToMany(userId);
		if (null == list || list.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		for (Order order : list) {
			List<OrderDetail> orderDetail = order.getOrderDetailList();
			if (orderDetail != null) {
				for (OrderDetail detail : orderDetail) {
					// 设置商品名称
					CTGoodsDo goods = ctGoodsService.selectById(Long.valueOf(detail.getGoodsId()));
					detail.setGoodsName(goods.getTitle());
				}
			}
		}

		return list;
	}

	/**
	 * 支付成功，更新订单状态，计算奖励处理
	 * 
	 * @return 处理出错抛异常
	 */
	public void orderPay(String ordercode, String gmtPayment) {
		try {
			// 根据订单编号查询出订单
			Order order = orderDao.selectByOrderCode(ordercode);
			logger.debug("根据订单编号查询出的订单：" + order);
			logger.debug("=============订单支付成功，开始处理逻辑业务=========》》》：更新订单表状态，奖励计算，激活用户状态");
			logger.debug("获取的订单支付状态========》》》》》" + order.getPaystatus());
			// 如果订单状态为支付失败状态才进行更新
			Map<String, Object> paraMap = new HashMap<String, Object>();
			// 付款状态为已支付
			paraMap.put("newStatus", 1);
			paraMap.put("oldStatus", 0);
			// 支付时间
			paraMap.put("payTime", gmtPayment);
			paraMap.put("orderId", order.getOrderid());
			logger.debug("更新订单状态的参数=======》》》》》" + paraMap);
			// 更新订单表状态,从未付改成已付
			int i = orderDao.updateOrderStatusByOldStatus(paraMap);
			if (i <= 0) {
				throw new BusinessException("重复通知，order：" + order, "lipay002");
			}

			// 激活用户状态
			UserDo buyer = userService.getUser(order.getUserid());
			logger.debug("根据支付宝回调返回的信息获取用户状态=======》》》》》" + buyer.isActivated());
			// 未激活的用户去激活
			if (buyer.getIsActivated().intValue() != 1) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", order.getUserid());
				map.put("isActivated", 1);
				int j = userService.updateUserStatus(map);
				logger.debug("用户状态激活结果=====" + j);
			}
			// 计算奖励， 如果计算过程失败，这个服务不会抛异常， 会记录在订单的奖金计算状态的字段， 后台管理员可以重新计算
			// 假如已经计算过奖励就不再重复计算
			logger.debug("用户奖励状态======》》》》" + order.getAwardStatus());
			if (order.getAwardStatus() == null || order.getAwardStatus().equals("")
					|| !(order.getAwardStatus().equals("success"))) {
				awardService.calcAward(order.getUserid(), order.getOrderid());
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("订单支付成功，处理逻辑业务失败！", e);
			throw e;
		}
	}

	/**
	 * 保存订单商品明细或赠品明细
	 */
	@Override
	public Order buyOrder(Order order, int goodstype, List<OrderDetail> orderDetail) {
		logger.debug("获取订单id=====>>>>" + order.getOrderid());
		try {
			// 获取订单对象
			Order oldOrder = orderDao.selectByPrimaryKey(order.getOrderid());
			logger.debug("获取的订单明细类型=====》》》》" + goodstype);
			if (orderDetail == null) {
				return oldOrder;
			}

			for (OrderDetail detail : orderDetail) {
				detail.setOrderid(oldOrder.getOrderid());
				detail.setRemark("1");
				orderDetailDao.insertSelective(detail);
			} 
		} catch (Exception e) {
			logger.error("添加订单明细失败！！！" + e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("保存订单明细返回的订单对象========》》》》》" + order);
		return order;
	}

	
	
	public Order savePayDetail(Order order, int goodstype, List<OrderPayDetail> payList) {
		logger.debug("获取订单id=====>>>>" + order.getOrderid());
		try {
			// 获取订单对象
			Order oldOrder = orderDao.selectByPrimaryKey(order.getOrderid());
			logger.debug("获取的订单明细类型=====》》》》" + goodstype);
			if (payList == null) {
				logger.error("支付类型未空：===》"+payList);
				return oldOrder;
			}

			for (OrderPayDetail detail : payList) {
				detail.setOrderid(oldOrder.getOrderid());
				detail.setRemark("1");
				orderDetailDao.insertPayDetail(detail);
			} 
		} catch (Exception e) {
			logger.error("添加订单支付明细失败！！！" + e);
			e.printStackTrace();
			throw e;
		}
		logger.debug("添加订单支付明细========》》》》》" + order);
		return order;
	}

	
	/**
	 * 计算赠品的差价
	 * 
	 * @return
	 */
	private Double countPremiumPriceSpread(List<OrderDetail> premiumList) {

		logger.debug("计算赠品差价开始=========》》》》》");
		double price = 40.0; // 每盒差价
		double totalprice = 0; // 总差价

		if (premiumList.isEmpty() || premiumList.size() == 0) {
			logger.debug("赠品集合为空，不计算差价========》》》》");
			return totalprice;
		}

		// 赠品是鹿无忧时
		for (OrderDetail premium : premiumList) {
			if (premium.getGoodsId() == 1001 || premium.getGoodsId() == 1002) {
				// 一、当赠品为男版或者女版时
				if (premiumList.size() <= 1) {
					if (premiumList.get(0).getGoodsId() == 1002) { // 女版差价
						totalprice = premiumList.get(0).getQuantity() * price;
						logger.debug("当赠品为女版时补的差价：" + totalprice);
					}

					// 二、当赠品为混合版时
				} else {
					for (int j = 0; j < premiumList.size(); j++) {
						// 计算出需补的差价
						if (premiumList.get(j).getGoodsId() == 1002) {
							logger.debug("获取的女版赠品的数量======》》》》" + premiumList.get(j).getQuantity());
							totalprice = premiumList.get(j).getQuantity() * price;
						}
					}
					logger.debug("当赠品为混合版时补的差价：" + totalprice);
				}
			}
		}
		return totalprice;
	}

	/**
	 * 支付成功，保存订单和订单明细，获取签名后的订单信息，并返回加签后的订单信息
	 * 
	 * @param chooseGoodsLst
	 */
	public Result<Map<String,String>> saveOrder( List<OrderPayDetail> payLst,List<OrderDetail> chooseGoodsLst, Order order,
			HttpServletRequest request, HttpServletResponse response)throws Exception {

		// 维护订单地址
		Integer orderAddressId = mainOrderAddress(order);
		order.setAddressid(orderAddressId);
		return this.createOrderToPay(payLst,chooseGoodsLst, order, request, response);
		
	}

	
	

	/**
	 * 从用户地址copy到订单地址
	 * 
	 * @param order
	 * @return
	 */
	public Integer mainOrderAddress(Order order) {
		UserAddressDo userAddressDo = userAdressService.selectByPrimaryKey(order.getAddressid());
		String address = userAddressDo.getAddress();
		String userName = userAddressDo.getUsername();
		String userPhone = userAddressDo.getUserphone();
		Integer userId = userAddressDo.getUserid();
		
		if (address==null || userName==null || userPhone==null || userId==null) {
			throw new BusinessException("从用户地址中获取收货人信息为空！！！");
		}

		OrderAddressDo orderAddressDo = new OrderAddressDo();
		orderAddressDo.setAddress(address);
		orderAddressDo.setAddressDetails(userAddressDo.getAddressDetails());
		orderAddressDo.setUsername(userName);
		orderAddressDo.setUserphone(userPhone);
		orderAddressDo.setUserid(userId);

		
	    //BeanUtils.copyProperties(userAddressDo, orderAddressDo );
		//orderAdressService.deleteByPrimaryKeyInt(order.getAddressid());
		//orderAddressDo.setAddressid(null); 
		orderAdressService.insertSelective(orderAddressDo);
		return orderAddressDo.getAddressid();
	}
	
	
	/**
	 * 	积分商品转换支付方式：改成从积分支付
	 * @param payLst
	 * @param chooseGoodsLst
	 * @return
	 */
	private List<OrderPayDetail> getJFPayLst(List<OrderPayDetail> payLst, 
											 List<OrderDetail> chooseGoodsLst,
											 Order order) {
		OrderDetail orderDetail = chooseGoodsLst.get(0);
		CTGoodsDo goods = ctGoodsService.selectById(Long.valueOf(orderDetail.getGoodsId()));
		if( 2 == goods.getGoodsFlag().intValue()) {
			if(payLst == null) {
				payLst = new ArrayList<OrderPayDetail>();
			}
			payLst.clear();
			
			OrderPayDetail orderPayDetail = new OrderPayDetail();
			if(goods.getShopCatId1().intValue() == 1) { //积分商城爆品用红包
				orderPayDetail.setAccountType(AccountType.wallet_goods.name());
			}else {
				orderPayDetail.setAccountType(AccountType.wallet_travel.name());
			}
			BigDecimal jifei = order.getTotalprice().subtract(goods.getPostage());
			orderPayDetail.setPayAmt(jifei);
			orderPayDetail.setRemark("积分商品用积分兑换");
			payLst.add(orderPayDetail );			
		}
		return payLst;
	}

	/**
	 * 若传过来的订单id为空，则重新生成订单
	 * 
	 * @param premiumList
	 * @param chooseGoodsLst
	 * @param order
	 * @param request
	 * @param response
	 * @return
	 */
	private Result<Map<String,String>> createOrderToPay(List<OrderPayDetail> payLst,
											List<OrderDetail> chooseGoodsLst,
											Order order, 
											HttpServletRequest request, 
											HttpServletResponse response)throws Exception {

		
		// 选择的商品信息为空
		if (chooseGoodsLst.size() == 0) {
			logger.warn("获取的商品清单为空=====》》》");
			return Result.failureResult("商品清单为空");
		}

		if (order == null) {
			logger.warn("获取的商品信息为空（地址id、支付方式、用户id）=====》》》");
			return Result.failureResult("获取的商品信息为空（地址id、支付方式、用户id）");
		}
		

		// 产生订单编号
		String orderCode = OrderCodeUtil.genOrderCode(order.getUserid());
		logger.info("产生订单号=====》》》" + orderCode);

		
		// 循环遍历出商品信息，计算商品总价格和商品总数量
		for (OrderDetail orderDetail : chooseGoodsLst) {
			CTGoodsDo goods = ctGoodsService.selectById(Long.valueOf(orderDetail.getGoodsId()));
			orderDetail.setGoodsName(goods.getTitle()); // 获取商品名称
			orderDetail.setProfit(goods.getProfit());
			orderDetail.setPrice(goods.getShopPrice().doubleValue());
			orderDetail.setPostage(goods.getPostage());
			orderDetail.setGoodsFlag(goods.getGoodsFlag());
		}
		

		// 创建订单
		order.setOrdercode(orderCode); // 订单号
		Date date = new Date();
		order.setCreatetime(DateUtil.dateformat(date));// 订单创建时间
		order.setOrderstatus("0"); // 未发货状态
		order.setPaystatus("0"); // 未支付状态
		order.setOrderDetailList(chooseGoodsLst); // 订单商品明细
		//计算订单，订单总金额，商品总数量，订单利润
		order.calOrderProfit();
		
		//积分商品支付
		payLst = getJFPayLst(payLst,chooseGoodsLst,order);
		order.setPayDetailList(payLst);		
		order.calNonCashAmt();
		
		/*
		Result checkRet =  order.checkPayAmt();
		if(checkRet.isSuccess() == false) {
			return checkRet;
		}
		*/
		
		// 添加订单
		if(order.getOrderid() != null) {
			OrderDetailExample example = new OrderDetailExample();
			example.createCriteria().andOrderidEqualTo(order.getOrderid());
			// 删除原来的明细
			orderDetailDao.deleteByExample(example);
			orderDetailDao.deletePayDetailByOrderId(order.getOrderid());
			orderDao.updateByPrimaryKeySelective(order);
		}else {
			orderDao.insertSelective(order);
		}
		logger.info("==========》》》》》插入的订单信息：" + order);
		// 添加商品明细
		order = buyOrder(order, 1, chooseGoodsLst);
		order = savePayDetail(order, 1, payLst);
		
		
		//现金支付
		if(order.getCashAmt().compareTo(BigDecimal.ZERO)>0) {
			// 获取加签后的订单
			return getSignByPayType(request, response, order);
			//return payByScanBarcode(request, response, order);
		}else {
			//扣其他支付账户的金额		
			subAccountAmt(payLst, order);
			//如果不需要现金支付，其他账户扣款成功，更新订单支付状态
			orderPay(order.getOrdercode(),DateUtil.YYYY_MM_DD_MM_HH_SS.format(new Date()));
		}
		
		Map<String,String> payRet = new HashMap<String,String>();
		payRet.put("payType", "H5");
		payRet.put("payString", "http://app.zjzwly.com/dce-app/order/paySucc.do");
		//"payType" : payType   H5,WX,Ali
		//"payString": payString //唤起支付json字符串
		return Result.successResult("支付成功", payRet);

	}

	private void subAccountAmt(List<OrderPayDetail> payLst, Order order) {
		for( OrderPayDetail pDetail : payLst) {
			if(pDetail.getPayAmt().compareTo(BigDecimal.ZERO)>0) {
				UserAccountDo userAccountDo = new UserAccountDo();
				userAccountDo.setAccountType(pDetail.getAccountType());
				userAccountDo.setAmount(pDetail.getPayAmt().negate());
				userAccountDo.setUserId(order.getUserid());
				accountService.updateUserAmountById(userAccountDo , IncomeType.TYPE_GOODS_BUY);
			}
		}
	}

	/**
	 * 根据支付方式获取对应加签后的订单
	 * payRet.put("payType", "H5");
	 * payRet.put("payString", "http://app.zjzwly.com/dce-app/mall/paySucc.do");
	 * 	//"payType" : payType   H5,WX,Ali
	 * @param request
	 * @param response
	 * @param order
	 * @return
	 */
	private Result<Map<String,String>> payByScanBarcode(HttpServletRequest request, 
														HttpServletResponse response, 
														Order order)throws Exception {
		if (order.getOrdertype() == null) {
			return Result.failureResult("获取的订单支付方式为空！！！");
		}
		Map<String,String> payRetMap = new HashMap<String,String>();
		
		// 判断支付方式，生成预支付订单
		if (order.getOrdertype().equals("1")) {
			// 微信支付
			Map weixinMap = WeixiPayUtil.getWeixiPayInfo(order.getOrdercode(), "订单微信支付", order.getCashAmt(), 1);
			payRetMap.put("payType", "WX");
			payRetMap.put("payString", weixinMap.toString());
		} else if (order.getOrdertype().equals("2")) { //支付宝支付
			payRetMap.put("payType", "Ali");
			String aliPayString =  getAlipayorderStr(order);
			payRetMap.put("payString",aliPayString);
		} else if (order.getOrdertype().equals("3")) {//银行卡支付
			payRetMap.put("payType", "H5");
			//payRetMap.put("payString","http://app.zjzwly.com/dce-app/bank/bankCardPay.do?orderId="+order.getOrderid());
			payRetMap.put("payString","http://app.zjzwly.com/dce-app/barcode/toBarcodePay.do?orderId="+order.getOrderid());
		}else {//账户支付
			return Result.failureResult("订单需要现金支付，没有给出正确的现金支付方式");
		}
		return Result.successResult("支付订单成功", payRetMap);
	}
	
	

	/**
	 * 根据支付方式获取对应加签后的订单
	 * payRet.put("payType", "H5");
	 * payRet.put("payString", "http://app.zjzwly.com/dce-app/mall/paySucc.do");
	 * 	//"payType" : payType   H5,WX,Ali
	 * @param request
	 * @param response
	 * @param order
	 * @return
	 */
	private Result<Map<String,String>> getSignByPayType(HttpServletRequest request, 
														HttpServletResponse response, 
														Order order)throws Exception {
		if (order.getOrdertype() == null) {
			return Result.failureResult("获取的订单支付方式为空！！！");
		}
		Map<String,String> payRetMap = new HashMap<String,String>();
		
		// 判断支付方式，生成预支付订单
		if (order.getOrdertype().equals("1")) {
			// 微信支付
			Map weixinMap = WeixiPayUtil.getWeixiPayInfo(order.getOrdercode(), "订单微信支付", order.getCashAmt(), 1);
			payRetMap.put("payType", "WX");
			payRetMap.put("payString", weixinMap.toString());
		} else if (order.getOrdertype().equals("2")) { //支付宝支付
			payRetMap.put("payType", "Ali");
			String aliPayString =  getAlipayorderStr(order);
			payRetMap.put("payString",aliPayString);
		} else if (order.getOrdertype().equals("3")) {//银行卡支付
			payRetMap.put("payType", "H5");
			payRetMap.put("payString","http://app.zjzwly.com/dce-app/bank/bankCardPay.do?orderId="+order.getOrderid());
		}else {//账户支付
			return Result.failureResult("订单需要现金支付，没有给出正确的现金支付方式");
		}
		return Result.successResult("支付订单成功", payRetMap);
	}

	/**
	 * 第三方银行卡支付
	 * @param order
	 * @return
	 */
	private Result<String> getBankPayorderStr(Order order) {


		String orderString = "";
		logger.debug("============支付宝下单，获取的商户订单号为：===========" + order.getOrdercode());

		

		//测试都有1分钱 
		//order.setTotalprice(new BigDecimal("0.01"));
		
		// 创建支付宝订单记录
		AlipaymentOrder alipaymentOrder = new AlipaymentOrder();
		alipaymentOrder.setOrderid(order.getOrderid());
		alipaymentOrder.setOrdercode(order.getOrdercode());
		alipaymentOrder.setTotalamount(order.getCashAmt()); // 交易金额
		alipaymentOrder.setOrderstatus(0);
		alipaymentOrder.setRemark("支付宝支付");

		// 实例化客户端（参数：网关地址、商户APPID、商户私钥、编码、支付宝公钥、加密类型）获取预付订单信息
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
					AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
					AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);

			// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
			AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();

			// SDK已经封装掉了公共参数，这里只需要传入业务参数
			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
			// 订单明细
			model.setBody(order.getOrderDetailList().toString());

			// 设置未付款支付宝交易的超时时间，一旦超时，该笔交易就会自动被关闭。当用户进入支付宝收银台页面（不包括登录页面），
			// 会触发即刻创建支付宝交易，此时开始计时。
			model.setTimeoutExpress(AlipayConfig.timeoutExpress);
			// 【必填】商户订单号
			model.setOutTradeNo(order.getOrdercode());
			// 【必填】支付金额
			model.setTotalAmount(order.getCashAmt().toString());
			//model.setTotalAmount("0.01");
			// 【必填】销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
			model.setProductCode("QUICK_MSECURITY_PAY");
			// 【必填】商品的标题/交易标题/订单标题/订单关键字等
			model.setSubject("无界新零售消费");

			ali_request.setBizModel(model);
			// 设置后台异步通知的地址，在手机端支付成功后支付宝会通知后台，手机端的真实支付结果依赖于此地址
			ali_request.setNotifyUrl(AlipayConfig.notify_url);

			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse ali_response = alipayClient.sdkExecute(ali_request); // 返回支付宝订单信息(预处理)
			// 可以直接给客户端请求，无需再做处理
			orderString = ali_response.getBody();
			alipaymentOrderService.createAlipayMentOrder(alipaymentOrder);// 创建新的商户支付宝订单记录

		} catch (AlipayApiException e) {
			logger.debug("生成支付宝预付单失败！！！！");
			e.printStackTrace();
		}
		return Result.successResult("生成支付宝预付订单", orderString);
	
	}

	/**
	 * 获取签名后的订单信息，并返回加签后的支付宝订单信息
	 * 
	 * @param order
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public String getAlipayorderStr(Order order) {

		String orderString = "";
		logger.debug("============支付宝下单，获取的商户订单号为：===========" + order.getOrdercode());

		

		//测试都有1分钱 
		//order.setTotalprice(new BigDecimal("0.01"));
		
		// 创建支付宝订单记录
		AlipaymentOrder alipaymentOrder = new AlipaymentOrder();
		alipaymentOrder.setOrderid(order.getOrderid());
		alipaymentOrder.setOrdercode(order.getOrdercode());
		alipaymentOrder.setTotalamount(order.getCashAmt()); // 交易金额
		alipaymentOrder.setOrderstatus(0);
		alipaymentOrder.setRemark("支付宝支付");

		// 实例化客户端（参数：网关地址、商户APPID、商户私钥、编码、支付宝公钥、加密类型）获取预付订单信息
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
					AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
					AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);

			// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
			AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();

			// SDK已经封装掉了公共参数，这里只需要传入业务参数
			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
			// 订单明细
			model.setBody(order.getOrderDetailList().toString());

			// 设置未付款支付宝交易的超时时间，一旦超时，该笔交易就会自动被关闭。当用户进入支付宝收银台页面（不包括登录页面），
			// 会触发即刻创建支付宝交易，此时开始计时。
			model.setTimeoutExpress(AlipayConfig.timeoutExpress);
			// 【必填】商户订单号
			model.setOutTradeNo(order.getOrdercode());
			// 【必填】支付金额
			model.setTotalAmount(order.getCashAmt().toString());
			//model.setTotalAmount("0.01");
			// 【必填】销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
			model.setProductCode("QUICK_MSECURITY_PAY");
			// 【必填】商品的标题/交易标题/订单标题/订单关键字等
			model.setSubject("乐游环球消费");

			ali_request.setBizModel(model);
			// 设置后台异步通知的地址，在手机端支付成功后支付宝会通知后台，手机端的真实支付结果依赖于此地址
			ali_request.setNotifyUrl(AlipayConfig.notify_url);

			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse ali_response = alipayClient.sdkExecute(ali_request); // 返回支付宝订单信息(预处理)
			// 可以直接给客户端请求，无需再做处理
			orderString = ali_response.getBody();
			alipaymentOrderService.createAlipayMentOrder(alipaymentOrder);// 创建新的商户支付宝订单记录

		} catch (AlipayApiException e) {
			logger.debug("生成支付宝预付单失败！！！！");
			e.printStackTrace();
			throw new BusinessException("生成支付宝预付订单失败");
		}
		return orderString;
	}

	/**
	 * 支付宝异步通知处理，验签
	 */
	@Override
	public String notify(Map<String, String> conversionParams) throws Exception {
		logger.debug("==================支付宝异步通知逻辑处理=============");
		System.out.println("==================支付宝异步通知逻辑处理=1============");

		// 签名验证(对支付宝返回的数据验证，确定是支付宝返回的)
		/*
		boolean signVerified = false;

		try {
			// 调用SDK验证签名
			signVerified = AlipaySignature.rsaCheckV1(conversionParams, AlipayConfig.ALIPAY_PUBLIC_KEY,
					AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);

		} catch (AlipayApiException e) {
			e.printStackTrace();
			logger.error("支付回调验签失败", e);
			throw new BusinessException("验签失败", "alipay001");
		}

		// 对验签进行处理
		if (!signVerified) {
			logger.debug("============验签不通过 ！");
			return "fail";
		}
		*/

		// 验签通过
		// 获取需要保存的数据
		boolean isPass = checkAlipayParamer(conversionParams);
		if (isPass == false) {
			return "fail";
		}

		String tradeStatus = conversionParams.get("trade_status");// 获取交易状态
		String gmtPayment = conversionParams.get("gmt_payment");// 交易付款时间

		// 记录通知日志
		boolean logStatus = logAlipayNotify(conversionParams);
		logger.debug("记录通知日志结果======》》》》" + logStatus);

		// 支付成功做业务逻辑
		if ("TRADE_FINISHED".equals(tradeStatus) || "TRADE_SUCCESS".equals(tradeStatus)) {
			String outTradeNo = conversionParams.get("out_trade_no");// 获取商户之前传给支付宝的订单号（商户系统的唯一订单号）
			/**
			 * 	扣其他账户金额,由原来的支付前，改成支付后
			 */
			Order order = orderDao.selectByOrderCode(outTradeNo);
			List<OrderPayDetail> payLst= orderDetailDao.selectPayDetailByOrderId(order.getOrderid());
			this.subAccountAmt(payLst, order);
			//end 扣其他账户金额
			orderPay(outTradeNo, gmtPayment);
		}
		return "success";
	}

	/**
	 * 校验 out_trade_no、total_amount、sellerId、app_id
	 * 
	 * @param conversionParams
	 * @return
	 */
	private boolean checkAlipayParamer(Map<String, String> conversionParams) {

		String appId = conversionParams.get("app_id");// 支付宝分配给开发者的应用Id
		String outTradeNo = conversionParams.get("out_trade_no");// 获取商户之前传给支付宝的订单号（商户系统的唯一订单号）
		String totalAmount = conversionParams.get("total_amount");// 订单金额:本次交易支付的订单金额，单位为人民币（元）
		String sellerId = conversionParams.get("seller_id");// 卖家支付宝用户号

		logger.debug("需要校验的参数=======》》》out_trade_no：" + outTradeNo + "======》》》total_amount：" + totalAmount
				+ "=====》》》seller_id：" + sellerId + "=====》》》app_id：" + appId);

		Order order = orderDao.selectByOrderCode(outTradeNo); // 查询出对应的订单记录
		logger.debug("根据支付宝的通知信息查询出对应的订单信息===========》》》" + order);

		// 支付宝官方建议校验的值（out_trade_no、total_amount、sellerId、app_id）
		// 目前测试中totalAmount.equals(order.getTotalprice())，totalAmount先暂时设置为0.01
		if (order == null || order.getTotalprice().compareTo(new BigDecimal(totalAmount))!=0 || !sellerId.equals(AlipayConfig.seller_id)
				|| !AlipayConfig.APPID.equals(appId)) {
			logger.debug("==========支付宝官方建议校验的值（out_trade_no、total_amount、sellerId、app_id）,不一致！返回fail");
			return false;
		}
		return true;
	}

	/**
	 * 记录阿里通知记录
	 * 
	 * @param conversionParams
	 */
	private boolean logAlipayNotify(Map<String, String> conversionParams) {

		String outTradeNo = conversionParams.get("out_trade_no");// 获取商户之前传给支付宝的订单号（商户系统的唯一订单号）
		String sellerId = conversionParams.get("seller_id");// 卖家支付宝用户号
		String tradeStatus = conversionParams.get("trade_status");// 获取交易状态
		String gmtPayment = conversionParams.get("gmt_payment");// 交易付款时间
		String notifyTime = conversionParams.get("notify_time");// 通知时间:yyyy-MM-dd
																// HH:mm:ss
		String gmtCreate = conversionParams.get("gmt_create");// 交易创建时间:yyyy-MM-dd
																// HH:mm:ss
		String gmtRefund = conversionParams.get("gmt_refund");// 交易退款时间
		String gmtClose = conversionParams.get("gmt_close");// 交易结束时间
		String tradeNo = conversionParams.get("trade_no");// 支付宝的交易号
		String sellerEmail = conversionParams.get("seller_email");// 卖家支付宝账号
		String receiptAmount = conversionParams.get("receipt_amount");// 实收金额:商家在交易中实际收到的款项，单位为元
		String invoiceAmount = conversionParams.get("invoice_amount");// 开票金额:用户在交易中支付的可开发票的金额
		String buyerPayAmount = conversionParams.get("buyer_pay_amount");// 付款金额:用户在交易中支付的金额

		AlipaymentOrder alipaymentOrder = alipaymentOrderService.selectByOrderCode(outTradeNo);
		// 修改数据库支付宝订单记录(因为要保存每次支付宝返回的信息到数据库里，以便以后查证)
		alipaymentOrder.setNotifytime(notifyTime);
		alipaymentOrder.setGmtcreatetime(gmtPayment);
		alipaymentOrder.setGmtrefundtime(gmtRefund);
		alipaymentOrder.setCreatetime(gmtCreate);
		alipaymentOrder.setGmtclosetime(gmtClose);
		alipaymentOrder.setTradeno(tradeNo);
		alipaymentOrder.setSelleremail(sellerEmail);
		alipaymentOrder.setSellerid(sellerId);
		alipaymentOrder.setReceptamount(new BigDecimal(receiptAmount));
		alipaymentOrder.setInvoiceamount(Double.valueOf(invoiceAmount));
		alipaymentOrder.setBuyerpayamount(Double.valueOf(buyerPayAmount));

		switch (tradeStatus) // 判断交易结果
		{
		case "TRADE_FINISHED": // 交易结束并不可退款
			alipaymentOrder.setOrderstatus(3);
			break;
		case "TRADE_SUCCESS": // 交易支付成功
			alipaymentOrder.setOrderstatus(2);
			break;
		case "TRADE_CLOSED": // 未付款交易超时关闭或支付完成后全额退款
			alipaymentOrder.setOrderid(1);
			break;
		case "WAIT_BUYER_PAY": // 交易创建并等待买家付款
			alipaymentOrder.setOrderstatus(0);
			break;
		default:
			break;
		}
		logger.debug("======支付宝交易状态======》》》：" + tradeStatus);
		// 更新支付记录表中的状态
		int returnResult = alipaymentOrderService.updateByPrimaryKeySelective(alipaymentOrder);
		return returnResult > 0;
	}

	/**
	 * 微信生成预支付订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Result<String> getWXPayStr(HttpServletRequest request, HttpServletResponse response, Order order)
			throws Exception {

		// 判断订单金额是否有误
		if (Integer.valueOf(order.getTotalprice().toString()) < 0) {
			return Result.failureResult("订单金额有误，生成微信预支付订单失败！");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		// 获取生成预支付订单的请求类
		PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);
		BigDecimal totalFee = order.getTotalprice();
		logger.debug("======获取封装的订单总金额=====" + totalFee);
		// 测试时，每次支付一分钱，微信支付所传的金额是以分为单位的，因此实际开发中需要x100
		int total_fee = Integer.valueOf(totalFee.multiply(new BigDecimal(100)).toString());
		logger.debug("=========》》》total_fee:" + total_fee);
		prepayReqHandler.setParameter("appid", WXPayConfig.APP_ID);
		prepayReqHandler.setParameter("body", WXPayConfig.BODY);
		prepayReqHandler.setParameter("mch_id", WXPayConfig.MCH_ID);
		String nonce_str = WXUtil.getNonceStr();
		prepayReqHandler.setParameter("nonce_str", nonce_str); // 随机字符串
		prepayReqHandler.setParameter("notify_url", WXPayConfig.NOTIFY_URL); // 接收微信支付异步通知回调地址
		String out_trade_no = order.getOrdercode();
		prepayReqHandler.setParameter("out_trade_no", out_trade_no); // 订单号
		prepayReqHandler.setParameter("spbill_create_ip", request.getRemoteAddr()); // 终端IP，即用户端实际ip
		String timestamp = WXUtil.getTimeStamp();
		prepayReqHandler.setParameter("time_start", timestamp); // 交易起始时间
		prepayReqHandler.setParameter("trade_type", "APP"); // 交易类型
		// 测试时，每次支付一分钱，上线之后需要放开此代码
		// prepayReqHandler.setParameter("total_fee",
		// String.valueOf(total_fee));
		prepayReqHandler.setParameter("total_fee", "1");

		// 注意签名（sign）的生成方式，具体见官方文档（传参都要参与生成签名，且参数名按照字典序排序，最后接上APP_KEY,转化成大写）
		prepayReqHandler.setParameter("sign", prepayReqHandler.createSHA1Sign());
		prepayReqHandler.setGateUrl(WXPayConfig.GATEURL);
		// 提交预支付订单，获取prepay_id
		String prepayid = prepayReqHandler.sendPrepay();

		// 若获取prepay_id（预支付交易会话标识）成功，将相关信息返回客户端
		if (prepayid != null && !prepayid.equals("")) {
			// 创建微信订单支付记录
			AlipaymentOrder alipaymentOrder = new AlipaymentOrder();
			alipaymentOrder.setRemark("微信支付");
			alipaymentOrder.setTotalamount(new BigDecimal(total_fee / 100));
			alipaymentOrder.setCreatetime(DateUtil.dateToString(new Date()));
			alipaymentOrder.setOrdercode(out_trade_no);
			alipaymentOrder.setOrderid(order.getOrderid());
			alipaymentOrder.setOrderstatus(0);
			alipaymentOrderService.createAlipayMentOrder(alipaymentOrder);

			// 重新生成签名，将数据传输给APP
			// 参与签名的字段名为appid，partnerid，prepayid，nonce_str，timestamp，package。注意：package的值格式为Sign=WXPay
			String signs = "appid=" + WXPayConfig.APP_ID + "&noncestr=" + nonce_str + "&package=Sign=WXPay&partnerid="
					+ WXPayConfig.PARTNER_ID + "&prepayid=" + prepayid + "&timestamp=" + timestamp + "&key="
					+ WXPayConfig.APP_KEY;

			map.put("code", 0);
			map.put("info", "success");
			map.put("prepayid", prepayid);
			// 签名方式与上面类似
			map.put("sign", MD5Util.MD5Encode(signs, "utf8").toUpperCase());
			map.put("appid", WXPayConfig.APP_ID);
			map.put("timestamp", timestamp); // 等于请求prepayId时的time_start
			map.put("noncestr", nonce_str); // 与请求prepayId时值一致
			map.put("package", "Sign=WXPay"); // 固定常量
			map.put("partnerid", WXPayConfig.PARTNER_ID);
			return Result.successResult("获取微信预支付订单成功", map.toString());
		} else {
			return Result.failureResult("获取prepayid失败，生成微信预支付订单失败");
		}
	}

	// 更新订单状态发货
	@Override
	public int updateByPrimaryKeySelective(Order order, Integer userId) {

		// 添加一条记录到订单发货表中
		OrderSendOut orderSendOut = new OrderSendOut();
		orderSendOut.setOrderId(Long.valueOf(order.getOrderid()));
		orderSendOut.setAddress(order.getAddress());
		orderSendOut.setCreateTime(DateUtil.YYYY_MM_DD_MM_HH_SS.format(new Date()));
		orderSendOut.setOperatorId(Long.valueOf(userId));
		int i = orderSendOutService.addOrderSendout(orderSendOut);

		if (i <= 0) {
			return 0;
		}
		return orderDao.updateByPrimaryKeySelective(order);
	}

	/**
	 * 向支付宝发起订单查询请求
	 * 
	 * @param outTradeNo
	 * @return
	 */
	@Override
	public Result<?> alipayQuery(String outTradeNo) {
		logger.debug("==================向支付宝发起查询，查询商户订单号为：" + outTradeNo);

		if (outTradeNo == "") {
			logger.debug("支付宝查询的订单编号为空！");
			return Result.failureResult("查询的支付宝订单号为空！！！");
		}
		try {
			// 实例化客户端（参数：网关地址、商户appid、商户私钥、格式、编码、支付宝公钥、加密类型）
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
					AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
					AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);

			AlipayTradeQueryRequest alipayTradeQueryRequest = new AlipayTradeQueryRequest();
			alipayTradeQueryRequest.setBizContent("{" + "\"out_trade_no\":\"" + outTradeNo + "\"" + "}");
			AlipayTradeQueryResponse alipayTradeQueryResponse = alipayClient.execute(alipayTradeQueryRequest);
			if (alipayTradeQueryResponse.isSuccess()) {

				AlipaymentOrder alipaymentOrder = alipaymentOrderService.selectByOrderCode(outTradeNo);
				// 修改数据库支付宝订单表
				alipaymentOrder.setOrdercode(alipayTradeQueryResponse.getTradeNo());
				alipaymentOrder.setBuyerlogonid(alipayTradeQueryResponse.getBuyerLogonId());
				alipaymentOrder.setTotalamount(new BigDecimal(alipayTradeQueryResponse.getTotalAmount()));
				alipaymentOrder.setReceptamount(new BigDecimal(alipayTradeQueryResponse.getReceiptAmount()));
				alipaymentOrder.setInvoiceamount(Double.parseDouble(alipayTradeQueryResponse.getInvoiceAmount()));
				alipaymentOrder.setBuyerpayamount(Double.parseDouble(alipayTradeQueryResponse.getBuyerPayAmount()));
				switch (alipayTradeQueryResponse.getTradeStatus()) // 判断交易结果
				{
				case "TRADE_FINISHED": // 交易结束并不可退款
					alipaymentOrder.setOrderstatus(3);
					;
					break;
				case "TRADE_SUCCESS": // 交易支付成功
					alipaymentOrder.setOrderstatus(2);
					break;
				case "TRADE_CLOSED": // 未付款交易超时关闭或支付完成后全额退款
					alipaymentOrder.setOrderstatus(1);
					break;
				case "WAIT_BUYER_PAY": // 交易创建并等待买家付款
					alipaymentOrder.setOrderstatus(0);
					break;
				default:
					break;
				}
				alipaymentOrderService.updateByPrimaryKeySelective(alipaymentOrder); // 更新表记录
				return Result.successResult(alipaymentOrder.getOrderstatus() + "");

			} else {
				logger.debug("==================调用支付宝查询接口失败！");
			}
		} catch (AlipayApiException e) {
			logger.error("程序异常，支付宝订单查询失败！");
			e.printStackTrace();
		}
		return Result.failureResult("向支付宝发起订单查询失败");
	}

	/**
	 * 根据条件导出Excel订单数据
	 */
	@Override
	public List<Order> selectOrderByCondition(Map<String, Object> paraMap) {

		List<Order> list = new ArrayList<Order>();
		list = orderDao.selectOrderByCondition(paraMap);
		logger.debug("获取的订单数据=====》》》" + list);

		for (Order order : list) {
			if ("1".equals(order.getPaystatus())) {
				order.setPaystatus("已付");
			} else {
				order.setPaystatus("未付");
			}

			if ("1".equals(order.getOrdertype())) {
				order.setOrdertype("微信");
			} else {
				order.setOrdertype("支付宝");
			}

			if ("1".equals(order.getOrderstatus())) {
				order.setOrderstatus("已发货");
			} else {
				order.setOrderstatus("未发货");
			}
			// 获取收货人的信息
			OrderAddressDo orderAddress = orderDao.selectAddressByOrder(order.getOrderid());
			if (orderAddress == null) {
				continue;
			}
			order.setPhone(orderAddress.getUserphone());
			order.setTrueName(orderAddress.getUsername());
			order.setAddress(orderAddress.getAddress());

			// 获取每条订单的商品详情
			List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
			orderDetailList = order.getOrderDetailList();
			// 拼接订单详情
			StringBuffer str = new StringBuffer();
			for (OrderDetail orderDetail : orderDetailList) {
				if (orderDetail == null) {
					continue;
				}
				logger.debug("订单id=====》》》" + orderDetail.getOrderid());
				CTGoodsDo goods = ctGoodsService.selectById(Long.valueOf(orderDetail.getGoodsId()));
				orderDetail.setGoodsName(goods.getTitle()); // 获取商品名称
				str.append(orderDetail.getGoodsName());
				str.append(orderDetail.getQuantity() + "盒");
				str.append(" ");
				order.setRemark(str.toString());
				logger.debug("拼接好的订单详情=====》》》" + order.getRemark());
			}
		}
		return list;
	}

	/**
	 * 通过用户等级和购买商品数量，计算赠品
	 */
	@Override
	public Result<?> chooseGoods(List<OrderDetail> chooseGoodsLst, Order order) {

		// 选择的商品信息为空
		if (chooseGoodsLst.size() == 0) {
			logger.debug("获取的商品清单为空=====》》》");
			return Result.successResult("商品清单为空");
		}

		if (order == null) {
			logger.debug("获取的商品信息为空（用户id）=====》》》");
			return Result.successResult("获取的商品信息为空（用户id）");
		}

		Integer quantity = 0; // 商品总数量
		BigDecimal totalprice = new BigDecimal(0); // 订单总金额
		BigDecimal price = new BigDecimal(0); // 商品总金额
		Integer salqty = 0; // 赠品数量

		// 循环遍历出商品信息，计算商品总价格和商品总数量
		for (OrderDetail orderDetail : chooseGoodsLst) {
			CTGoodsDo goods = ctGoodsService.selectById(Long.valueOf(orderDetail.getGoodsId()));
			orderDetail.setGoodsName(goods.getTitle()); // 获取商品名称
			quantity += orderDetail.getQuantity(); // 商品总数量
			price = BigDecimal.valueOf(orderDetail.getPrice() * (orderDetail.getQuantity())).add(price); // 商品总金额
			logger.debug("商品总金额---------》》》》》》》》》" + price);
		}

		// 计算赠品
		// 判断用户等级
		UserDo user = userService.getUser(order.getUserid());
		Byte userLevel = user.getUserLevel();
		// 普通用户、会员购买5盒送一盒
		if (userLevel.intValue() == 0 || userLevel.intValue() == 1) {
			if (quantity >= 5 && quantity < 50) {
				salqty = 1;
			}
		}
		// 普通用户、会员、VIP买50盒送十八盒
		if (userLevel.intValue() != 3 && userLevel.intValue() != 4) {
			if (quantity >= 50) {
				salqty = 18;
			}
		}

		// 订单总金额
		totalprice = price;
		// 创建订单
		Date date = new Date();
		order.setCreatetime(DateUtil.dateformat(date));// 订单创建时间
		order.setOrderstatus("0"); // 未发货状态
		order.setPaystatus("0"); // 未支付状态
		order.setQty(quantity); // 商品总数量
		order.setTotalprice(totalprice); // 订单总金额
		order.setSalqty(new BigDecimal(salqty)); // 赠品总数量
		order.setGoodsprice(price); // 商品总金额
		order.setOrderDetailList(chooseGoodsLst); // 订单商品明细

		// 保存订单
		orderDao.insertSelective(order);
		logger.debug("==========》》》》》添加的订单信息：" + order);

		// 添加商品明细
		order = buyOrder(order, 1, chooseGoodsLst);

		// 赠品信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("salqty", salqty);
		map.put("userLevel", userLevel);
		map.put("orderId", order.getOrderid());

		logger.debug("userId:" + order.getUserid() + "获取赠品成功:" + map);
		return Result.successResult("获取赠品成功", map);
	}

	@Override
	public List<Order> sumAmount() {
		return orderDao.sumAmount();
	}

	@Override
	public String thirdPayNotify(Map<String, Object> map) {
		if(null == map || map.isEmpty()) {
			return "fail";
		}
		/* TRADE_SUCCESS 交易成功，用户付款成功
  		   TRADE_FINISHED 交易结束，付款金额已结算给商户
		   TRADE_CLOSED	交易关闭，交易失败
		*/
		String tradeStatus = (String)map.get("trade_status");
		String orderCode = (String) map.get("outer_trade_no");
		if("TRADE_SUCCESS".equals(tradeStatus) ||"TRADE_FINISHED".equals(tradeStatus)) {
			String payTime=(String)map.get("gmt_payment");
			orderPay(orderCode,payTime);
		}
		/*
		else if("TRADE_CLOSED".equals(tradeStatus)) {
			Order order = new Order();
			order.setOrdercode(orderCode);
			order.setPaystatus("2");
			orderDao.updateByOrderCodeSelective(order);
		}
		*/
		return "success";
	}

	@Override
	public List<Order> selectOrderAndDetail(Map<String, Object> queryMap) {
		return orderDao.selectOrderAndDetail(queryMap);
	}
}
