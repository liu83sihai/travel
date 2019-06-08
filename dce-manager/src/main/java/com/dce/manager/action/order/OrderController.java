package com.dce.manager.action.order;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.DateUtil;
import com.dce.business.common.util.ExeclTools;
import com.dce.business.entity.order.Order;
import com.dce.business.entity.order.OrderDo;
import com.dce.business.entity.page.NewPagination;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.service.award.IAwardService;
import com.dce.business.service.order.IOrderService;
import com.dce.manager.action.BaseAction;
import com.dce.manager.util.ResponseUtils;

/**
 * @author 订单控制类
 * @version 1.0
 * @since 1.0
 */

@Controller
@RequestMapping("/order")
public class OrderController extends BaseAction {
	@Resource
	private IOrderService orderService;
	@Resource
	private IAwardService awardService;

	/**
	 * 去列表页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "order/listOrder";
	}

	/**
	 * 订单列表分页页面
	 * 
	 * @param pagination
	 * @param model
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/listOrder")
	public void listOrder(NewPagination<OrderDo> pagination, ModelMap model, HttpServletResponse response) {

		logger.info("---------listOrder---------");

		try {
			PageDo<Map<String, Object>> page = PageDoUtil.getPage(pagination);
			Map<String, Object> param = new HashMap<String, Object>();

			String userName = getString("userName");
			if (StringUtils.isNotBlank(userName)) {
				param.put("trueName", userName);
				model.addAttribute("userName", userName);
			}

			String startDate = getString("startDate");
			if (StringUtils.isNotBlank(startDate)) {
				param.put("startDate", startDate);
				model.addAttribute("startDate", startDate);
			}

			String endDate = getString("endDate");
			if (StringUtils.isNotBlank(endDate)) {
				param.put("endDate", endDate);
				model.addAttribute("endDate", endDate);
			}
			
			String orderStatus = getString("orderStatus");
			if (StringUtils.isNotBlank(orderStatus)) {
				param.put("orderStatus", orderStatus);
				model.addAttribute("orderStatus", orderStatus);
			}
			//支付成功的订单
			String payStatus = getString("payStatus");
			if (StringUtils.isNotBlank(payStatus)) {
				param.put("payStatus", payStatus);
				model.addAttribute("payStatus", payStatus);
			}

			//本页合计
			PageDo<Map<String,Object>> orderList = orderService.selectOrderByPage(page, param);
			if(orderList != null && !CollectionUtils.isEmpty(orderList.getModelList())){
				Map<String,Object> total = new HashMap<String,Object>();
				total.put("ordercode", "本页合计");
				BigDecimal pageTotalprice= BigDecimal.ZERO; 
				for(Map<String,Object> temp : orderList.getModelList()){
					pageTotalprice = pageTotalprice.add((BigDecimal)temp.get("totalprice"));
				}
				total.put("totalprice", pageTotalprice);
				orderList.getModelList().add(total);
				
				
			}
			
			//所有订单总金额
			List<Order> listorder = orderService.sumAmount();
			if(listorder != null && !CollectionUtils.isEmpty(listorder)){
				Map<String,Object> sum = new HashMap<String,Object>();
				sum.put("ordercode", "订单总金额");
				for(Order order : listorder){
					if(order != null){
						if(null != order.getTotalprice()){
							sum.put("totalprice", order.getTotalprice());
						}else{
							sum.put("totalprice", BigDecimal.ZERO);
						}
					}else{
						sum.put("totalprice", BigDecimal.ZERO);
					}
				}
				orderList.getModelList().add(sum);
			}
			
			pagination = PageDoUtil.getPageValue(pagination, orderList);
			outPrint(response, JSONObject.toJSON(pagination));
			
		} catch (Exception e) {
			logger.error("查询订单异常", e);
			throw new BusinessException("系统繁忙，请稍后再试");
		}
	}

	/**
	 * 重新计算奖励
	 * 
	 * @param userId
	 * @param orderId
	 * @param response
	 */
	@RequestMapping("/recalculteReward")
	public void RecalculateReward(HttpServletResponse response) {

		String userId = getString("userId");
		String orderId = getString("orderId");
		logger.debug("重新计算奖励，获取参数=====》》》》》userId："+userId+"orderId："+orderId);
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(orderId)) {
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
			return;
		}
		//先查询出该条订单是否计算奖励出错
		Order order = orderService.selectByPrimaryKey(Integer.valueOf(orderId));
		if(order == null){
			ResponseUtils.renderJson(response, null, "{\"ret\":-2}");
			return;
		}
		logger.debug("重新计算奖励之前先查询出的订单信息=====》》》"+order);
		if(!order.getAwardStatus().equals("fail")){
			ResponseUtils.renderJson(response, null, "{\"ret\":-3}");
			return;
		}
		// 计算奖励， 如果在APP支付后计算奖励失败， 后台重新计算奖励
		try {
			awardService.calcAward(Integer.valueOf(userId), Integer.valueOf(orderId));
			ResponseUtils.renderJson(response, null, "{\"ret\":1}");
		} catch (Exception e) {
			logger.error("后台重新计算奖励错误！！！"+e.getMessage());
			ResponseUtils.renderJson(response, null, "{\"ret\":-1}");
			e.printStackTrace();
		}
	}

	@RequestMapping("/export")
	public void exportOrder(HttpServletResponse response, String orderStatus,String userName,String startDate,String endDate) {
		logger.debug("获取的下拉框值======》》》》》paystatus" + orderStatus);
		Long startTime = System.currentTimeMillis();

		try {
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(startDate)) {
				map.put("startDate", startDate);
			}
			if (StringUtils.isNotBlank(endDate)) {
				map.put("endDate", endDate);
			}
			if (StringUtils.isNotBlank(orderStatus)) {
				map.put("orderStatus", orderStatus);
			}
			if (StringUtils.isNotBlank(userName)) {
				map.put("userName", userName);
			}
			//付款成功的订单
			map.put("payStatus", 1);
			
			// 条件查询订单
			List<Order> orderList = new ArrayList<Order>();
			orderList = orderService.selectOrderByCondition(map);

			String excelHead = "订单导出";
			String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String fileName = URLEncoder.encode(excelHead + date + ".xls", "utf-8");
			List<String[]> excelheaderList = new ArrayList<String[]>();
			String[] excelheader = { "订单编号", "总金额（元）", "手机号码", "数量（盒）", "收货人", "下单时间", "付款状态", "支付方式", "收货地址", "商品详情",
					"赠品详情","是否发货" };
			excelheaderList.add(0, excelheader);
			String[] excelData = { "ordercode", "totalprice", "phone", "qty", "trueName", "createtime", "paystatus",
					"ordertype", "address", "remark","awardRemark", "orderstatus" };
			HSSFWorkbook wb = ExeclTools.execlExport(excelheaderList, excelData, excelHead, orderList);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			wb.write(response.getOutputStream());
			startTime = System.currentTimeMillis() - startTime;
			logger.info("导出数据，导出耗时(ms)：" + startTime);

		} catch (UnsupportedEncodingException e) {
			logger.error("不支持字符集，导出订单数据失败！！！" + e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("IO错误，导出订单数据失败！！！" + e);
			e.printStackTrace();
		}
	}

	/**
	 * 发货
	 * 
	 * @param orderid
	 * @param modelMap
	 * @param response
	 */
	@RequestMapping("/sendOut")
	public void sendOut(String orderid, ModelMap modelMap, HttpServletResponse response) {
		logger.info("获取的订单编号：" + orderid);
		Integer userId = this.getUserId();

		try {
			if (StringUtils.isNotBlank(orderid)) {
				// 先查询出订单
				Order order = orderService.selectByPrimaryKey(Integer.valueOf(orderid));
				logger.info("先查询出的订单------》》》》》" + order);
				int i = 0;
				if (order != null) {
					// 更新订单状态发货
					order.setOrderstatus("1");
					i = orderService.updateByPrimaryKeySelective(order, userId);
				}
				if (i <= 0) {
					outPrint(response, this.toJSONString(Result.failureResult("发货失败")));
					return;
				}
				outPrint(response, this.toJSONString(Result.successResult("发货成功")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("系统繁忙，请稍后再试");
		}
	}

	/**
	 * 保存更新
	 *
	 * @return
	 * @author: huangzlmf
	 * @date: 2015年4月21日 12:49:05
	 */
	@RequestMapping("/saveOrder")
	@ResponseBody
	public void saveOrder(Order order, HttpServletRequest request, HttpServletResponse response) {
		logger.info("----saveOrder------");
		try {
			Integer id = order.getOrderid();
			Integer userId = this.getUserId();

			int i = 0;
			if (id != null && id.intValue() > 0) {
				order.setUserid(userId);
				order.setCreatetime(DateUtil.dateToString(new Date()));
				i = orderService.updateOrder(order);
			} else {
				order.setUserid(userId);
				order.setCreatetime(DateUtil.dateToString(new Date()));

				i = orderService.addOrder(order);
			}

			if (i <= 0) {
				outPrint(response, this.toJSONString(Result.failureResult("操作失败")));
				return;
			}
			outPrint(response, this.toJSONString(Result.successResult("操作成功")));
		} catch (Exception e) {
			logger.error("保存更新失败", e);
			outPrint(response, this.toJSONString(Result.failureResult("操作失败")));
		}
		logger.info("----end saveOrder--------");
	}

}
