package com.dce.business.actions.order;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dce.business.actions.common.BaseController;
import com.dce.business.common.alipay.util.AlipayConfig;
import com.dce.business.common.enums.AccountType;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.common.token.TokenUtil;
import com.dce.business.common.util.DateUtil;
import com.dce.business.common.wxPay.util.XMLUtil;
import com.dce.business.dao.account.IUserAccountDetailDao;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.alipaymentOrder.AlipaymentOrder;
import com.dce.business.entity.bank.BankDo;
import com.dce.business.entity.dict.LoanDictDo;
import com.dce.business.entity.goods.CTGoodsDo;
import com.dce.business.entity.order.Order;
import com.dce.business.entity.order.OrderDetail;
import com.dce.business.entity.order.OrderPayDetail;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.accountRecord.AccountRecordService;
import com.dce.business.service.bonus.IBonusLogService;
import com.dce.business.service.dict.ILoanDictService;
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
	
	@Resource
	private ILoanDictService  dictService;
	
	/**
	 * 订单支付方式
	 * 
	 * @return
	 */
	/** 
	 * @api {POST} /order/getOrderPayType.do 订单支付方式
	 * @apiName getOrderPayType
	 * @apiGroup order 
	 * @apiVersion 1.0.0 
	 * @apiDescription 订单支付方式
	 * 
	 * @apiParam {String} userId 用户id
	 * @apiParam {json} chooseGoods 购物车商品列表
	 * @apiParam {int} chooseGoods.goodsId 商品id
	 * @apiParam {decimal} chooseGoods.qty 购买数量
	 * 
	 * @apiSuccess {Object[]} payList	支付方式列表
	*  @apiSuccess {String} payList.payCode 支付账户类别
	*  @apiSuccess {Decimal} payList.totalAmt	账户余额
	*  @apiSuccess {Decimal} payList.useableAmt	本次可用金额
	*  @apiSuccess {String} remark	备注
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccessExample Success-Response: 
	 * HTTP/1.1 200 OK 
	*{
	*    "code": "0",
	*    "msg": "订单支付方式成功",
	*    "data": {
	*         }
	*}
	**/
	@RequestMapping(value = "/getOrderPayType", method = RequestMethod.POST)
	public Result<?> getOrderPayType(HttpServletRequest request) {

		Integer userId = getUserId();
		// 验证用户token参数
		//String uri = request.getRequestURI(); // 获得发出请求字符串的客户端地址
//		String uri = "";
//		String ts = request.getParameter(TokenUtil.TS);
//		String sign = request.getParameter(TokenUtil.SIGN);
//		// 验证token
//		boolean flag = TokenUtil.checkToken(uri, Integer.valueOf(userId), ts, sign);
//		if (!flag) {
//			return Result.failureResult("登录失效，请重新登录！");
//		}

		String goods = request.getParameter("chooseGoods") == null ? "" : request.getParameter("chooseGoods");
		
		// 将商品信息的JSON数据解析为list集合
		List<OrderDetail> chooseGoodsLst = convertGoodsFromJson(goods);
		logger.info("======用户选择的商品信息：" + chooseGoodsLst + "=====用户id：" + userId);
		
		// 假如获取参数某一个为空，直接返回结果至前端
		if (userId ==  null) {
			return Result.failureResult("请登录!");
		}
		
		// 商品信息
		if (StringUtils.isBlank(goods)) {
			return Result.failureResult("chooseGoods参数为空！");
		}
		if(chooseGoodsLst == null ||chooseGoodsLst.size()<1) {
			return Result.failureResult("提交的购买商品为空!");
		}
				
		// 判断该用户是否存在
		UserDo user = userService.getUser(Integer.valueOf(userId));
		if (user == null) {
			return Result.failureResult("该用户不存在！");
		}
		
		return getGoodsPay(userId, chooseGoodsLst);
		
	}


	/**
	 * 根据商品获取，商品配置的支付方式
	 * @param userId
	 * @param chooseGoodsLst
	 * @return
	 */
	private Result<?>  getGoodsPay(Integer userId, List<OrderDetail> chooseGoodsLst) {
		
		if(null == chooseGoodsLst || chooseGoodsLst.size()<1) {
			return Result.failureResult("请选择商品");
		}
		
		// 财务信息
		List<Map<String, Object>> accountInfo = new ArrayList<Map<String,Object>>();
		OrderDetail orderDetail = chooseGoodsLst.get(0);
		CTGoodsDo gDo = ctGoodsService.selectById(Long.valueOf(orderDetail.getGoodsId()));
		String  payType = gDo.getPayType();
		if(StringUtils.isNotBlank(payType)) {
			String[] payArr = payType.split(",");
			for(String pay :payArr) {
				UserAccountDo amount = accountService.getUserAccount(userId, AccountType.getAccountType(pay)); 
				Map<String, Object> payMap1 = new HashMap<String,Object>();
				payMap1.put("payCode", amount.getAccountType());
				payMap1.put("totalAmt", amount.getAmount());
				payMap1.put("useableAmt", amount.getAmount());
				accountInfo.add(payMap1);
			}
		}
		
		if(accountInfo.size()<1) {
			return Result.failureResult("没有找到合适的支付方式");
		}

		Map<String ,Object> ret = new HashMap<String,Object>();
		ret.put("remark", "");
		ret.put("payList", accountInfo);
		return Result.successResult("获取订单支付方式成功", ret);
		
	}
	
	/**
	 * 下单的时候检查支付方式
	 * @param chooseGoodsLst
	 * @param payLst
	 * @return
	 */
	private boolean checkPay(Long userId,List<OrderDetail> chooseGoodsLst, List<OrderPayDetail> payLst) {
		Result ret = this.getGoodsPay(userId.intValue(), chooseGoodsLst);
		if(ret.isSuccess() == false) {
			logger.error("支付检查失败，商品没有正确配置支付方式");
			return false;
		}
		Map<String ,Object> payMap = (Map)ret.getData();
		List<Map<String, Object>> accountInfoLst = (List) payMap.get("payList");
		for(OrderPayDetail pDetail : payLst) {
			boolean found = false;
			for(Map<String,Object> m : accountInfoLst) {
				String payCode = (String)m.get("payCode");
				found = pDetail.getAccountType().equalsIgnoreCase(payCode);
				//如果支持这种方式，检查金额
				if(found && (!AccountType.wallet_bank.getAccountType().equals(payCode))) {
					BigDecimal useAmt =(BigDecimal) m.get("useableAmt");
					if(pDetail.getPayAmt().compareTo(useAmt)>0) {
						logger.error("支付检查失败,配置的支付:"+m+"  实际支付："+pDetail.toString());
						return false;
					}
					
				}
				if(found) {
					break;
				}
			}
			if(false == found) {
				logger.error("支付检查失败，商品没有配置这种支付方式："+pDetail);
				return false;
			}
		}
		return true;
	}
	

	/**
	 * 用户订单列表显示
	 * 
	 * @return
	 */
	/** 
	 * @api {POST} /order/orderInquiry.do 用户订单列表显示
	 * @apiName orderInquiry
	 * @apiGroup order 
	 * @apiVersion 1.0.0 
	 * @apiDescription 用户订单列表显示
	 * 
	 * @apiParam {String} userId 用户id
	 * 
	 * *  @apiSuccess {int} orderid	int	订单id主键
	*  @apiSuccess {int} userid	int	用户id
	*  @apiSuccess {String} ordercode	int	订单编号
	*  @apiSuccess {int} qty	int	商品总数量
	*  @apiSuccess {int} salqty	int	赠品数量
	*  @apiSuccess {decimal} totalprice	decimal(20,6)	订单总金额（商品总金额加差价）
	*  @apiSuccess {decimal} giftAmount	decimal(20,6)	差价（选择公主版赠品要补的）
	*  @apiSuccess {decimal} goodsprice	decimal(20,6)	商品总金额
	*  @apiSuccess {String} addressid	String	地址id
	*  @apiSuccess {String} createTime	timestamp	订单创建时间
	*  @apiSuccess {String} paytime	timestamp	订单支付时间
	*  @apiSuccess {int} orderstatus	int	订单状态：1已发货 0未发货
	*  @apiSuccess {int} paystatus	int	付款状态：1支付成功 0支付失败
	*  @apiSuccess {timestamp} paytime	timestamp	支付时间
	*  @apiSuccess {int} ordertype	int	支付方式：1微信 2支付宝
	*  @apiSuccess {Object[]} orderDetailList	String	商品明细：quantity商品数量；price商品单价；remark 0为赠品1为商品
	*  @apiSuccess {String} orderDetailList.quantity 商品数量
	*  @apiSuccess {String} orderDetailList.price 商品单价
	*  @apiSuccess {String} orderDetailList.remark 0为赠品1为商品
	*  @apiSuccess {String} goodsName	String	商品名称
	 * @apiUse RETURN_MESSAGE
	 * @apiSuccessExample Success-Response: 
	 * HTTP/1.1 200 OK 
	*{
	*    "code": "0",
	*    "msg": "获取订单成功",
	*    "data": [
	*        {
	*            "orderid": 13,
	*            "ordercode": "XX520180908144655443",
	*            "userid": 5,
	*            "qty": 5,
	*            "totalprice": 2985,
	*            "goodsprice": 2985,
	*            "giftAmount": 0,
	*            "salqty": 1,
	*            "createtime": "2018-09-08 14:46:55",
	*            "orderstatus": "0",
	*            "paystatus": "1",
	*            "paytime": "2018-09-08 14:46:09",
	*            "ordertype": "2",
	*            "addressid": 6,
	*            "orderDetailList": [
	*                {
	*                    "orderdetailid": 25,
	*                    "orderid": 13,
	*                    "goodsId": 1001,
	*                    "quantity": 5,
	*                    "price": 597,
	*                    "goodsName": "鹿无忧男士尊享版",
	*                    "remark": "1"
	*                },
	*                {
	*                    "orderdetailid": 26,
	*                    "orderid": 13,
	*                    "goodsId": 1001,
	*                    "quantity": 1,
	*                    "price": 597,
	*                    "goodsName": "鹿无忧男士尊享版",
	*                    "remark": "0"
	*                }
	*            ]
	*        },
	*        {
	*            "orderid": 12,
	*            "userid": 5,
	*            "qty": 6,
	*            "totalprice": 3622,
	*            "goodsprice": 3622,
	*            "salqty": 1,
	*            "createtime": "2018-09-08 14:46:03",
	*            "orderstatus": "0",
	*            "paystatus": "1",
	*            "paytime": "2018-09-08 14:46:03",
	*            "orderDetailList": [
	*                {
	*                    "orderdetailid": 22,
	*                    "orderid": 12,
	*                    "goodsId": 1001,
	*                    "quantity": 5,
	*                    "price": 597,
	*                    "goodsName": "鹿无忧男士尊享版",
	*                    "remark": "1"
	*                },
	*                {
	*                    "orderdetailid": 23,
	*                    "orderid": 12,
	*                    "goodsId": 1002,
	*                    "quantity": 1,
	*                    "price": 637,
	*                    "goodsName": "哈根达斯",
	*                    "remark": "1"
	*                }
	*            ]
	*        },
	*        {
	*            "orderid": 11,
	*            "userid": 5,
	*            "qty": 5,
	*            "totalprice": 2985,
	*            "goodsprice": 2985,
	*            "salqty": 1,
	*            "createtime": "2018-09-08 14:45:51",
	*            "orderstatus": "0",
	*            "paystatus": "1",
	*            "paytime": "2018-09-08 14:45:51",
	*            "orderDetailList": [
	*                {
	*                    "orderdetailid": 21,
	*                    "orderid": 11,
	*                    "goodsId": 1001,
	*                    "quantity": 5,
	*                    "price": 597,
	*                    "goodsName": "鹿无忧男士尊享版",
	*                    "remark": "1"
	*                }
	*            ]
	*        }
	*    ],
	*    "success": true
	*}
	**/
	@RequestMapping(value = "/orderInquiry", method = RequestMethod.POST)
	public Result<?> getOrder(HttpServletRequest request) {

		Integer userId = getUserId();
		// 验证用户token参数
		//String uri = request.getRequestURI(); // 获得发出请求字符串的客户端地址
//		String uri = "";
//		String ts = request.getParameter(TokenUtil.TS);
//		String sign = request.getParameter(TokenUtil.SIGN);
//		// 验证token
//		boolean flag = TokenUtil.checkToken(uri, Integer.valueOf(userId), ts, sign);
//		if (!flag) {
//			return Result.failureResult("登录失效，请重新登录！");
//		}

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

		return Result.successResult("获取订单成功", orderLitst);
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
			return Result.failureResult("该用户不存在！");
		}

		Order order = new Order();
		order.setUserid(Integer.valueOf(userId));

		logger.info("获取的商品信息-------》》》》》" + goods);

		// 将商品信息的JSON数据解析为list集合
		List<OrderDetail> chooseGoodsLst = convertGoodsFromJson(goods);
		logger.info("======用户选择的商品信息：" + chooseGoodsLst + "=====用户id：" + userId);

		// 生成订单，保存订单和订单明细
		return orderService.chooseGoods(chooseGoodsLst, order);

	}

	/**
	 * 下单生成预支付订单
	 * 
	 * @return
	 */
	
	/** 
	 * @api {POST} /order/createOrder.do 生成预支付订单
	 * @apiName createOrder
	 * @apiGroup order 
	 * @apiVersion 1.0.0 
	 * @apiDescription 生成预支付订单
	 * 
	 * @apiParam {String} userId 用户id
	 * @apiParam {String} orderType 支付方式 1微信2支付宝3其他 
	 * @apiParam {String} addressId 订单送货地址id
	 * @apiParam {json} cart 商品信息：qty商品数量；goodsId商品编号；price商品单价
	 * @apiParam {json} payList 支付信息：accountType 账户类型；payAmt 支付金额      账户类别说明    券账户类别 “wallet_money”：”现金券账户” “wallet_travel”： “换购积分券账户” “wallet_goods”： “抵用券账户” "cash" ：代表用支付宝和微信的现金支付
	 * 
	 * @apiUse RETURN_MESSAGE
	
	 * @apiSuccessExample Success-Response: 
	 *  HTTP/1.1 200 OK 
	 * {
	 *  "code": 0
	 *	"msg": 返回成功,
	 *	"data": {
	 *			"payType" : payType   H5,WX,Ali
	 *          "payString": payString //唤起支付json字符串
	 *	  }
	 *	}
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
		String payList = request.getParameter("payList") == null ? "" : request.getParameter("payList");
		String userId = request.getParameter("userId") == null ? "" : request.getParameter("userId");
		String addressId = request.getParameter("addressId") == null ? "" : request.getParameter("addressId");
		String orderType = request.getParameter("orderType") == null ? "" : request.getParameter("orderType");
		//String orderId = getString("orderId") == null ? "" : request.getParameter("orderId");

		// 假如获取参数某一个为空，直接返回结果至前端
		if (userId == "" || goods == "" || addressId == "" || orderType == "" ) {

			return Result.successResult("获取userId、addressId、orderType、cart参数为空！", new JSONArray());
		}

		// 验证token
//		boolean flag = TokenUtil.checkToken(uri, Integer.valueOf(userId), ts, sign);
//		if (!flag) {
//			return Result.failureResult("登录失效，请重新登录！");
//		}

		// 判断该用户是否存在
		UserDo user = userService.getUser(Integer.valueOf(userId));
		if (user == null) {
			return Result.failureResult("该用户不存在！");
		}

		Order order = new Order();
		//order.setOrderid(Integer.valueOf(orderId));
		order.setUserid(Integer.valueOf(userId));
		order.setAddressid(Integer.valueOf(addressId));
		order.setOrdertype(orderType);

		logger.info("获取的商品信息-------》》》》》" + goods);

		// 将商品信息的JSON数据解析为list集合
		List<OrderDetail> chooseGoodsLst = convertGoodsFromJson(goods);
		//支付明细
		List<OrderPayDetail> payLst = convertPayJson(payList);
		
		LoanDictDo dictDo = dictService.getLoanDict("checkPay");
		if(null != dictDo) {
			if("T".equalsIgnoreCase(dictDo.getRemark())) {
				boolean isOk = checkPay(Long.valueOf(userId),chooseGoodsLst,payLst);
				if(false == isOk) {
					return Result.failureResult("不正确的支付方式！");
				}
			}
		}

		logger.info("======用户选择的商品信息：" + chooseGoodsLst  + "=====获取的地址id：" + addressId
				+ "=====获取的支付方式：" + orderType + "=====用户id：" + userId+"====支付明细："+payList);

		// 生成预付单，保存订单和订单明显
		return orderService.saveOrder(payLst,chooseGoodsLst, order, request, response);
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
	@RequestMapping(value = "/notifyUrl", method = RequestMethod.POST)
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
     *	 去添加新的银行卡信息
     * @return
     */
    @RequestMapping("/paySucc")
    public ModelAndView paySucc(HttpServletRequest request){
    	
    	ModelAndView mav = new ModelAndView("order/paySucc");
    	return mav;
    }
    

	
	/**
	 * payListJson 转OrderPayDetail对象 json 格式 [ { "payAmt": "0001", "qty": "20",
	 * "price":"597" }, { "goodsId": "0002", "qty": "10", "price": "637" } ]
	 * 
	 * @param goods
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<OrderPayDetail> convertPayJson(String payListJson) {

		if (StringUtils.isBlank(payListJson)) {
			logger.info("解析的支付为空======》》》》");
			throw new BusinessException("没有正确的提交支付方式");
		}

		List<OrderPayDetail>  payLst = new ArrayList<OrderPayDetail>();
		JSONArray jsonArray = JSONArray.parseArray(payListJson);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			BigDecimal payAmt  =  new BigDecimal (obj.getString("payAmt") );
			// 过滤支付金额为0的
			if (payAmt.compareTo(BigDecimal.ZERO) == 0) {
				continue;
			}
			OrderPayDetail payDetail = new OrderPayDetail();
			payDetail.setPayAmt(payAmt);
			String accountType = obj.getString("accountType");
			payDetail.setAccountType(accountType);
			payLst.add(payDetail);			
		}
		return payLst;
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
			if (Integer.valueOf(obj.getString("qty")).intValue() == 0) {
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