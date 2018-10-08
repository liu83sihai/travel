package com.dce.manager.action.user;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.enums.IncomeType;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.ExeclTools;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.entity.page.Pagination;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.order.IOrderService;
import com.dce.business.service.user.IUserService;
import com.dce.manager.action.BaseAction;

@Controller
@RequestMapping("/userAccount")
public class UserAccountController extends BaseAction {

	@Autowired
	private IAccountService accountService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IOrderService orderService;
	
	@RequestMapping("/index")
	public String index(){
		return "/userAccount/index";
	}
	
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/list")
	public void list(Pagination<Map<String,Object>> pagination,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			PageDo<Map<String,Object>> page = PageDoUtil.getPage(pagination);
			String userName = getString(request, "userName");
			
			Map<String,Object> params = new HashMap<String,Object>();
			
			if(StringUtils.isNotBlank(userName)){
				params.put("userName", userName);
			}
			
			PageDo<Map<String,Object>> accoutList = accountService.selectAccountInfomByPage(page, params);
			if(accoutList != null && !CollectionUtils.isEmpty(accoutList.getModelList())){
				Map<String,Object> total = new HashMap<String,Object>();
			}
			//当前合计
			if(accoutList != null && !CollectionUtils.isEmpty(accoutList.getModelList())){
				Map<String,Object> total = new HashMap<String,Object>();
				total.put("userName", "本页合计:");
				BigDecimal wallet_money = BigDecimal.ZERO;
				BigDecimal wallet_travel = BigDecimal.ZERO;
				BigDecimal wallet_goods = BigDecimal.ZERO;
				BigDecimal wallet_active = BigDecimal.ZERO;
				
				for(Map<String,Object> temp : accoutList.getModelList()){
					wallet_money =wallet_money.add((BigDecimal) temp.get("wallet_money"));
					wallet_travel = wallet_travel.add((BigDecimal) temp.get("wallet_travel"));
					wallet_goods = wallet_goods.add((BigDecimal) temp.get("wallet_goods"));
					wallet_active = wallet_active.add((BigDecimal) temp.get("wallet_active"));
				}
				total.put("wallet_money", wallet_money);
				total.put("wallet_travel", wallet_travel);
				total.put("wallet_goods", wallet_goods);
				total.put("wallet_active", wallet_active);

				accoutList.getModelList().add(total);
			}
			
			//账户金额汇总
			List<UserAccountDo> subList = accountService.sumAccount();
			if(!CollectionUtils.isEmpty(subList)){
				Map<String,Object> sum = new HashMap<String,Object>();
				sum.put("userName", "账户金额汇总:");
				for(UserAccountDo acc : subList){
					if(acc.getAmount() == null){
						sum.put(acc.getAccountType(), BigDecimal.ZERO);
					}else{
						sum.put(acc.getAccountType(), acc.getAmount());
					}
				}
				accoutList.getModelList().add(sum);
			}
			
			pagination = PageDoUtil.getPageValue(pagination, accoutList);
			outPrint(response, JSON.toJSONString(pagination));
		} catch (Exception e) {
			logger.error("显示用户数据异常", e);
			throw new BusinessException("系统繁忙，请稍后再试");
		}
	}
	

	
	@RequestMapping(value = "/changeMoney", method = {RequestMethod.GET,RequestMethod.POST})
	public String changeMoney(ModelMap model){
		String type = getString("type");
		String userId = getString("userId");
		UserDo user = userService.getUser(Integer.parseInt(userId));
		model.addAttribute("type", type);
		model.put("user", user);
		return "/userAccount/changeMoney";
	}
	
	@RequestMapping(value = "/addAmount", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public void addAmount(HttpServletResponse response){
		
		String userId = getString("userId");
		logger.info("修改用户等级:userId=" + userId);
		
		String accountType = getString("accountType");
		String qty = getString("qty");
		
		String type = getString("type");
		
		if(StringUtils.isBlank(type)){
			outPrint(response,  Result.failureResult("未知的充值类型!"));
			return;
		}
		
		if(StringUtils.isBlank(userId)){
			outPrint(response,  Result.failureResult("请选择充值用户!"));
			return;
		}
		if(StringUtils.isBlank(accountType)){
			outPrint(response,  Result.failureResult("请输入账户类型!"));
			return;
		}
		if(StringUtils.isBlank(qty)){
			outPrint(response,  Result.failureResult("请输入充值金额!"));
			return;
		}
		
		try{
			UserAccountDo account = new UserAccountDo();
			account.setAccountType(accountType);
			// 0 为划扣  1为充值
			if("0".equals(type)){
				
				account.setAmount(new BigDecimal(qty).negate());
			}else{
				
				account.setAmount(new BigDecimal(qty));
			}
			account.setUserId(Integer.parseInt(userId));
			account.setRemark("后台管理用户[" + getUserName() + ("0".equals(type)?"]转出":"]转入"));
			int flag = accountService.updateUserAmountById(account, "0".equals(type)?IncomeType.TYPE_POINT_OUT:IncomeType.TYPE_POINT_IN);
			logger.info("充值结果:" + flag);
			if(flag > 0){
				
				outPrint(response,JSON.toJSONString(Result.successResult(("0".equals(type)?"转出":"转入") + "成功!")));
			}else{
				
				outPrint(response,JSON.toJSONString(Result.failureResult(("0".equals(type)?"转出":"转入") + "失败!")));
			}
		}catch(BusinessException e){
			logger.info("充值报错BusinessException:",e);
			outPrint(response, JSON.toJSONString(Result.failureResult(("0".equals(type)?"转出":"转入") + "失败!")));
		}catch(Exception e){
			logger.info("充值报错Exception:",e);
			outPrint(response, JSON.toJSONString(Result.failureResult(("0".equals(type)?"转出":"转入") + "失败!")));
		}
		
	}
	
	
	@RequestMapping("/sumAccount")
	public String sumAccount(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> params = new HashMap<String,Object>();
		List<UserAccountDo> select = accountService.sumAccount(Collections.EMPTY_MAP);
		Map<String, Object> sum=orderService.selectSum(params);
		String s="0.000000";
		if(sum!=null){
			Object ob = sum.get("Totalperformance").toString();
			s=ob.toString();
		}
		request.setAttribute("Totalperformance", s);
		request.setAttribute("sumList", select);
		return "/userAccount/sumAccount";
	}
	
	
	/**
	 * 导出数据
	 */
	@RequestMapping("/export")
	public void export(HttpServletResponse response) throws IOException {
		try {
			Long time = System.currentTimeMillis();
			//TravelDoExample example = new TravelDoExample();
			Map<String, Object> map=new HashMap<String,Object>();
			List<Map<String, Object>> applytravelLst = accountService.exportQuery(map);
			
			
			System.err.println("对象------》》》"+JSONObject.toJSON(applytravelLst));

			String excelHead = "数据导出";
			String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String fileName = URLEncoder.encode(excelHead + date + ".xls", "utf-8");
			List<String[]> excelheaderList = new ArrayList<String[]>();
			String[] excelheader = { "用户名", "账户余额（元）", "奖励旅游（次）", "奖励商品（盒）", "奖励活动（场）"};
			excelheaderList.add(0, excelheader);
			String[] excelData = { "userName","wallet_money", "wallet_travel", "wallet_goods", "wallet_active"};
			HSSFWorkbook wb = ExeclTools.execlExport(excelheaderList, excelData, excelHead, applytravelLst);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			wb.write(response.getOutputStream());
			time = System.currentTimeMillis() - time;
			logger.info("导出数据，导出耗时(ms)：" + time);
		} catch (Exception e) {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().println("下载失败");
			logger.error("导出数据，Excel下载失败", e);
			logger.error("导出数据异常", e);
			throw new BusinessException("系统繁忙，请稍后再试");
		} finally {
			response.flushBuffer();
		}

	}
	
}
