package com.dce.manager.action.withdraw;

import java.io.IOException;
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

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.pay.util.Trans;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.DataDecrypt;
import com.dce.business.common.util.ExeclTools;
import com.dce.business.entity.message.NewsDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.page.PageDoUtil;
import com.dce.business.entity.page.Pagination;
import com.dce.business.entity.trade.WithdrawalsDo;
import com.dce.business.entity.travel.TravelDo;
import com.dce.business.entity.travel.TravelDoExample;
import com.dce.business.service.account.IPayService;
import com.dce.business.service.impl.account.PayServiceImpl;
import com.dce.business.service.trade.IWithdrawService;
import com.dce.manager.action.BaseAction;

@Controller
@RequestMapping("/withdraw")
public class WithDrawController extends BaseAction {

	@Resource
    private IWithdrawService withdrawService;
	@Resource
	private IPayService payService;
	
	@RequestMapping("/index")
	public String index() {
		return "/withdraw/index";
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/list")
	public void list(Pagination<Map<String, Object>> pagination,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			PageDo<Map<String, Object>> page = PageDoUtil.getPage(pagination);
			String userName = getString(request, "userName");
			String startDate = getString(request, "startDate");
			String endDate = getString(request, "endDate");
			String type = getString("type");
			Map<String, Object> params = new HashMap<String, Object>();

			if (StringUtils.isNotBlank(userName)) {
				params.put("userName", userName);
			}
			if (StringUtils.isNotBlank(startDate)) {
				params.put("startDate", startDate);
			}
			if (StringUtils.isNotBlank(endDate)) {
				params.put("endDate", endDate);
			}
			if (StringUtils.isNotBlank(type)) {
				params.put("type", type);
			}
			PageDo<Map<String, Object>> orderList = withdrawService.selectWithDrawByPage(page, params);
			Long amount = withdrawService.selectWithDrawTotallAmount(params);
			Map<String,Object> sum = new HashMap<String,Object>();
			if(StringUtils.isBlank(userName)){
				sum.put("user_name", "提现统计:");
			}else{
				sum.put("user_name", userName);
			}
			sum.put("amount", amount);
			orderList.getModelList().add(sum);
			
			pagination = PageDoUtil.getPageValue(pagination, orderList);
			outPrint(response, JSON.toJSONString(pagination));
		} catch (Exception e) {
			logger.error("显示用户数据异常", e);
			throw new BusinessException("系统繁忙，请稍后再试");
		}
	}
	 /**
     * 审批提现
     * @return
     */
    @RequestMapping(value = "/auditWithdraw", method = { RequestMethod.GET, RequestMethod.POST })
    public void auditWithdraw(HttpServletResponse response) {
        String withdrawId = getString("withdrawId");
        String auditResult = getString("auditResult");
        
        Result<?> result = Result.successResult("操作成功", withdrawId);
        try {
        	result = withdrawService.auditWithdrawById(auditResult, Integer.valueOf(withdrawId));
        } catch (BusinessException e) {
            logger.error("提现错误:", e);
            result = Result.failureResult(e.getMessage());
        } catch (Exception e) {
            logger.error("提现错误:", e);
            result = Result.failureResult("提现错误");
        }
        outPrint(response, JSON.toJSONString(result));
    }
    
    
    @RequestMapping(value = "/auditWithdraw_bank", method = { RequestMethod.GET, RequestMethod.POST })
    public void auditWithdraw_bank(HttpServletResponse response) {
        String withdrawId = getString("withdrawId");
        
        Result<?> result = Result.successResult("操作成功", withdrawId);
        try {
        	result = withdrawService.auditWithdrawById_bank(Integer.valueOf(withdrawId));
        } catch (BusinessException e) {
            logger.error("提现错误:", e);
            result = Result.failureResult(e.getMessage());
        } catch (Exception e) {
            logger.error("提现错误:", e);
            result = Result.failureResult("提现错误");
        }
        outPrint(response, JSON.toJSONString(result));
    }
    /**
     * 查看转账详情
     * @return 
     * @return
     */
    @RequestMapping(value = "/queryWithdraw")
    public String queryWithdraw(HttpServletResponse response,ModelMap modelMap) {
        String withdrawId = getString("withdrawId");
        System.out.println(withdrawId);
        try {
			if (StringUtils.isNotBlank(withdrawId)) {
				WithdrawalsDo withdraws = withdrawService.selectByPrimaryKey(Integer.parseInt(withdrawId));
				if (null != withdraws) {
					withdraws.setOrderId(DataDecrypt.decrypt(withdraws.getOrderId()));
					withdraws.setOutbizno(DataDecrypt.decrypt(withdraws.getOutbizno()));
					Trans trans=payService.withdraw(withdraws);
					modelMap.addAttribute("queryWithdraw", trans);
				}
			}
			return "withdraw/viewWithDraw";
		} catch (Exception e) {
			logger.error("跳转异常", e);
			throw new BusinessException("系统繁忙，请稍后再试");
		}
    }
    
    /**
	 * 导出数据
	 */
	@RequestMapping("/export")
	public void export(HttpServletResponse response,String type,String userName,String startDate,String endDate) throws IOException {
		try {
			Long time = System.currentTimeMillis();
			WithdrawalsDo example = new WithdrawalsDo();
			Map<String, Object> map = new HashMap<String, Object>();
			System.out.println("type:"+type);
			if (StringUtils.isNotBlank(userName)) {
				map.put("userName", userName);
			}
			if (StringUtils.isNotBlank(startDate)) {
				map.put("endDate", startDate);
			}if (StringUtils.isNotBlank(endDate)) {
				map.put("endDate", endDate);
			}if (StringUtils.isNotBlank(type)) {
				map.put("type", type);
			}
			
			List<WithdrawalsDo> applytravelLst = withdrawService.selectByExample(map);

			String excelHead = "数据导出";
			String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String fileName = URLEncoder.encode(excelHead + date + ".xls", "utf-8");
			List<String[]> excelheaderList = new ArrayList<String[]>();
			String[] excelheader = { "用户名", "姓名", "手机号码", "提现金额", "申请时间", "审批时间", "提现状态", "提现手续费", "是否到账", "提现方式", "提现账号", "到账日期 " };
			excelheaderList.add(0, excelheader);
			String[] excelData = { "user_name", "true_name", "mobile", "amount", "withdrawDate",
					"confirmDate", "processStatus","fee", "withdraw_status", "type","bankNo","paymentDate"};
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
