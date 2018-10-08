package com.dce.business.actions.order;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom2.JDOMException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dce.business.actions.common.BaseController;
import com.dce.business.common.alipay.util.AlipayConfig;
import com.dce.business.common.result.Result;
import com.dce.business.common.token.TokenUtil;
import com.dce.business.common.util.DateUtil;
import com.dce.business.common.wxPay.util.XMLUtil;
import com.dce.business.dao.account.IUserAccountDetailDao;
import com.dce.business.entity.alipaymentOrder.AlipaymentOrder;
import com.dce.business.entity.order.Order;
import com.dce.business.entity.order.OrderDetail;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.accountRecord.AccountRecordService;
import com.dce.business.service.bonus.IBonusLogService;
import com.dce.business.service.goods.ICTGoodsService;
import com.dce.business.service.impl.order.AlipaymentOrderService;
import com.dce.business.service.order.IOrderService;
import com.dce.business.service.user.IUserService;
import com.dce.business.service.user.UserAdressService;

@RestController
@RequestMapping("order")
public class OrderController extends BaseController {
	private final static Logger logger = Logger.getLogger(OrderController.class);

	@Resource
	private IOrderService orderService;
	@Resource
	private AccountRecordService accountRecordService;
	@Resource
	private UserAdressService addressService;
	@Resource
	private IAccountService accountService;
	@Resource
	private IBonusLogService bonusServiceLog;
	@Resource
	private IUserAccountDetailDao userAccountDetailDao;
	@Resource
	private ICTGoodsService ctGoodsService;
	@Resource
	private AlipaymentOrderService alipaymentOrderService;
	@Resource
	private IUserService userService;

	/**
	 * 用户订单列表显示
	 * 
	 * @return
	 */
	@RequestMapping(value = "/orderInquiry", method = RequestMethod.POST)
	public Result<?> getOrder(HttpServletRequest request) {

		Integer userId = getUserId();
		// 验证用户token参数
		//String uri = request.getRequestURI(); // 获得发出请求字符串的客户端地址
		String uri = "";
		String ts = request.getParameter(TokenUtil.TS);
		String sign = request.getParameter(TokenUtil.SIGN);
		// 验证token
		boolean flag = TokenUtil.checkToken(uri, Integer.valueOf(userId), ts, sign);
		if (!flag) {
			return Result.failureResult("登录失效，请重新登录！");
		}

		// 判断该用户是否存在
		UserDo user = userService.getUser(Integer.valueOf(userId));
		if (user == null) {
			return Result.successResult("该用户不存在！", new JSONArray());
		}

		List<Order> orderLitst = orderService.selectByUesrIdOneToMany(userId);

		if (orderLitst.size() == 0 || orderLitst.isEmpty()) {
			return Result.successResult("当前用户订单为空", new JSONArray());
		}
		logger.debug("获取用户所有订单=======》》》》》" + orderLitst);

		List<Map<String, Object>> newOrderlist = new ArrayList<>();
		// 设置商品名称
		for (Order order : orderLitst) {
			Map<String, Object> map = new HashMap<>();
			map.put("orderid", order.getOrderid());
			map.put("ordercode", order.getOrdercode());
			map.put("userid", order.getUserid());
			map.put("qty", order.getQty());
			map.put("salqty", order.getSalqty());
			map.put("totalprice", order.getTotalprice());
			map.put("giftAmount", order.getGiftAmount());
			map.put("createTime", order.getCreatetime());
			map.put("orderstatus", order.getOrderstatus());
			map.put("paystatus", order.getPaystatus());
			map.put("paytime", order.getPaytime());
			map.put("ordertype", order.getOrdertype());
			map.put("addressid", order.getAddressid());
			map.put("price", order.getGoodsprice());
			map.put("orderDetailList", order.getOrderDetailList());
			map.put("awardDetailLst", order.getAwardDetailLst());
			newOrderlist.add(map);
		}
		return Result.successResult("获取订单成功", newOrderlist);
	}

	/**
	 * 用户选择商品用来判断是否需要赠品
	 * 
	 * @return
	 */
	@RequestMapping(value = "/chooseGoods", method = RequestMethod.POST)
	public Result<?> chooseGoods(HttpServletRequest request, HttpServletResponse response) {

		//String uri = request.getRequestURI(); // 获得发出请求字符串的客户端地址
		String uri = "";
		String ts = request.getParameter(TokenUtil.TS);
		String sign = request.getParameter(TokenUtil.SIGN);
		String goods = request.getParameter("cart") == null ? "" : request.getParameter("cart");
		String userId = getString("userId") == null ? "" : request.getParameter("userId");

		// 假如获取参数某一个为空，直接返回结果至前端
		if (userId == "" || goods == "") {
			return Result.successResult("获取userId、cart参数为空！", new JSONArray());
		}

		// 验证token
		boolean flag = TokenUtil.checkToken(uri, Integer.valueOf(userId), ts, sign);
		if (!flag) {
			return Result.failureResult("登录失效，请重新登录！");
		}

		// 判断该用户是否存在
		UserDo user = userService.getUser(Integer.valueOf(userId));
		if (user == null) {
			return Result.successResult("该用户不存在！", new JSONArray());
		}

		Order order = new Order();
		order.setUserid(Integer.valueOf(userId));

		logger.info("获取的商品信息-------》》》》》" + goods);

		// 将商品信息的JSON数据解析为list集合
		List<OrderDetail> chooseGoodsLst = convertGoodsFromJson(goods);
		logger.info("======用户选择的商品信息：" + chooseGoodsLst + "=====用户id：" + userId);

		// 生成订单，保存订单和订单明细
		// orderService.chooseGoods(chooseGoodsLst, order);
		return orderService.chooseGoods(chooseGoodsLst, order);

	}

	/**
	 * 下单生成预支付订单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/createOrder", method = RequestMethod.POST)
	public Result<?> insertOrder(HttpServletRequest request, HttpServletResponse response) {

		// token验证参数
		//String uri = request.getRequestURI(); // 获得发出请求字符串的客户端地址
		String uri = "";
		String ts = request.getParameter(TokenUtil.TS);
		String sign = request.getParameter(TokenUtil.SIGN);

		// 订单参数
		String goods = request.getParameter("cart") == null ? "" : request.getParameter("cart");
		String premium = request.getParameter("premium") == null ? "" : request.getParameter("premium");
		String userId = getString("userId") == null ? "" : request.getParameter("userId");
		String addressId = getString("addressId") == null ? "" : request.getParameter("addressId");
		String orderType = getString("orderType") == null ? "" : request.getParameter("orderType");
		String orderId = getString("orderId") == null ? "" : request.getParameter("orderId");

		// 假如获取参数某一个为空，直接返回结果至前端
		if (userId == "" || goods == "" || addressId == "" || orderType == "" || StringUtils.isBlank(orderId)) {

			return Result.successResult("获取orderId,userId、addressId、orderType、cart参数为空！", new JSONArray());
		}

		// 验证token
		boolean flag = TokenUtil.checkToken(uri, Integer.valueOf(userId), ts, sign);
		if (!flag) {
			return Result.failureResult("登录失效，请重新登录！");
		}

		// 判断该用户是否存在
		UserDo user = userService.getUser(Integer.valueOf(userId));
		if (user == null) {
			return Result.successResult("该用户不存在！", new JSONArray());
		}

		Order order = new Order();
		order.setOrderid(Integer.valueOf(orderId));
		order.setUserid(Integer.valueOf(userId));
		order.setAddressid(Integer.valueOf(addressId));
		order.setOrdertype(orderType);

		logger.info("获取的商品信息-------》》》》》" + goods);

		// 将商品信息的JSON数据解析为list集合
		List<OrderDetail> chooseGoodsLst = convertGoodsFromJson(goods);

		// 将赠品信息的JSON数据解析为list集合
		List<OrderDetail> premiumList = convertGoodsFromJson(premium);

		logger.info("======用户选择的商品信息：" + chooseGoodsLst + "=====获取的赠品信息：" + premiumList + "=====获取的地址id：" + addressId
				+ "=====获取的支付方式：" + orderType + "=====用户id：" + userId);

		// 前端页面没有传orderId
		for (OrderDetail od : premiumList) {
			od.setOrderid(Integer.valueOf(orderId));
		}

		// 前端页面没有传orderId
		for (OrderDetail od : chooseGoodsLst) {
			od.setOrderid(Integer.valueOf(orderId));
		}

		// 生成预付单，保存订单和订单明显
		return orderService.saveOrder(premiumList, chooseGoodsLst, order, request, response);
	}

	/**
	 * 支付宝支付异步通知该接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/notify_url", method = RequestMethod.POST)
	public void notify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ret = "failure";
		try {
			logger.info("==================支付宝异步返回支付结果开始");
			// 1.从支付宝回调的request域中取值
			// 获取支付宝返回的参数集合
			Map<String, String[]> aliParams = request.getParameterMap();
			// 用以存放转化后的参数集合
			Map<String, String> conversionParams = new HashMap<String, String>();
			for (Iterator<String> iter = aliParams.keySet().iterator(); iter.hasNext();) {
				String key = iter.next();
				String[] values = aliParams.get(key);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				// valueStr = new String(valueStr.getBytes("ISO-8859-1"),
				// "uft-8");
				conversionParams.put(key, valueStr);
			}
			logger.info("==================支付宝返回参数集合：" + conversionParams);
			logger.info("==================原本的参数ALIPAY_PUBLIC_KEY：" + AlipayConfig.ALIPAY_PUBLIC_KEY + "\tCHARSET："
					+ AlipayConfig.CHARSET);

			String status = orderService.notify(conversionParams);
			logger.info("===========》》》》》验签结果：" + status);
			ret = "success";

		} catch (Exception e) {
			logger.error("支付宝异步返回支付结果处理失败", e);
			ret = "fail";

		} finally {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(ret);
			out.flush();
			out.close();
			System.out.println("支付宝异步通知返回：" + ret);
			logger.debug("==========最终返回给支付宝的验签结果==========" + ret);

			// return ret;
		}
	}

	/**
	 * 接收微信支付回调通知
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/tenpay/notify")
	public void getnotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("===============微信支付回调===========");
		PrintWriter writer = response.getWriter();
		InputStream inStream = request.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		String result = new String(outSteam.toByteArray(), "utf-8");
		logger.info("======微信支付通知结果：" + result);
		Map<String, String> map = null;
		try {
			// 解析微信通知返回的信息
			map = XMLUtil.doXMLParse(result);

		} catch (JDOMException e) {
			e.printStackTrace();
		}
		// 根据微信返回的订单号查询出是否存在该订单
		Order order = orderService.selectByOrderCode(map.get("out_trade_no"));
		System.out.println("根据微信通知查询出来的订单=========:" + order);

		String notifyStr = "";

		// 验证签名通过并且返回的订单的金额与商户金额相同
		if (order.getTotalprice().equals(map.get("total_fee"))) {
			if (map.get("result_code").equals("SUCCESS")) {
				// 若支付成功，则告知微信服务器收到通知
				if (map.get("return_code").equals("SUCCESS")) {
					AlipaymentOrder alipaymentOrder = alipaymentOrderService.selectByOrderCode(map.get("out_trade_no"));
					logger.info("==========订单号：" + Long.valueOf(map.get("out_trade_no")));

					logger.info("通知微信后台");
					alipaymentOrder.setOrderstatus(2);
					alipaymentOrder.setNotifytime(DateUtil.dateToString(new Date()));
					// 更新交易记录状态
					alipaymentOrderService.updateByPrimaryKeySelective(alipaymentOrder);

					// 支付成功处理
					orderService.orderPay(alipaymentOrder.getOrdercode(), alipaymentOrder.getCreatetime());

					// 处理业务完毕，将业务结果通知给微信
					notifyStr = XMLUtil.setXML("SUCCESS", "OK");

				} else {
					notifyStr = XMLUtil.setXML("FAIL", "获取微信支付通知结果为FAIL");
				}
			}
		} else {
			notifyStr = XMLUtil.setXML("FAIL", "签名和金额验证失败");
		}
		writer.write(notifyStr);
		writer.flush();
		writer.close();
	}

	/**
	 * 支付宝订单查询接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "/alipayQuery", method = RequestMethod.POST)
	public Result<?> queryOrderByOutTradeNo() {

		String outTradeNo = getString("outTradeNo") == null ? "" : getString("outTradeNo");
		if (outTradeNo == "") {
			Result.failureResult("支付宝订单参数outTradeNo为空！");
		}
		logger.info("========获取的订单号======：" + outTradeNo);

		return orderService.alipayQuery(outTradeNo);
	}

	/**
	 * json 转OrderDetail对象 json 格式 [ { "goodsId": "0001", "qty": "20",
	 * "price":"597" }, { "goodsId": "0002", "qty": "10", "price": "637" } ]
	 * 
	 * @param goods
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<OrderDetail> convertGoodsFromJson(String goods) {

		if (StringUtils.isBlank(goods)) {
			logger.info("解析的商品集合为空======》》》》");
			return Collections.EMPTY_LIST;
		}

		List<OrderDetail> orderList = new ArrayList<OrderDetail>();
		JSONArray jsonArray = JSONArray.parseArray(goods);

		for (int i = 0; i < jsonArray.size(); i++) {
			OrderDetail orderDetail = new OrderDetail();
			JSONObject obj = jsonArray.getJSONObject(i);
			// 过滤数量为0的商品
			if (Integer.valueOf(obj.getString("qty")) == 0) {
				continue;
			}
			orderDetail.setGoodsId(Integer.valueOf(obj.getString("goodsId")));
			orderDetail.setQuantity(Integer.valueOf(obj.getString("qty")));
			orderDetail.setPrice(Double.valueOf(obj.getString("price")));
			orderList.add(orderDetail);
		}
		return orderList;
	}
	/*
	 * private List<OrderDetail> convertGoodsFromJson(String goods) {
	 * 
	 * List<OrderDetail> orderList = new ArrayList<OrderDetail>();
	 * 
	 * if (goods != null && goods.startsWith("\ufeff")) { goods =
	 * goods.substring(1); }
	 * 
	 * JSONArray json = JSONArray.fromObject(goods); for (int i = 0; i <
	 * json.size(); i++) { OrderDetail orderDetail = new OrderDetail();
	 * JSONObject obj = JSONObject.fromObject(json.get(i));
	 * orderDetail.setGoodsId(Integer.valueOf(obj.getString("goodsId")));
	 * orderDetail.setQty(Integer.valueOf(obj.getString("qty")));
	 * orderDetail.setPrice(Double.valueOf(obj.getString("price")));
	 * orderList.add(orderDetail); }
	 * 
	 * List<OrderDetail> goodsList = new ArrayList<OrderDetail>(); Gson gson =
	 * new Gson(); goodsList = (List<OrderDetail>) gson.fromJson(goodsStr,
	 * OrderDetail.class); return goodsList;
	 * 
	 * return orderList; }
	 */

}