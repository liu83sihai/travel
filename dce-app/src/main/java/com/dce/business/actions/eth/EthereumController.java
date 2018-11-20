package com.dce.business.actions.eth;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.enums.DictCode;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.ExeclTools;
import com.dce.business.entity.dict.LoanDictDtlDo;
import com.dce.business.entity.etherenum.EthAccountPlatformDo;
import com.dce.business.entity.etherenum.EthereumAccountDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.dict.ILoanDictService;
import com.dce.business.service.third.IEthereumPlatformService;
import com.dce.business.service.third.IEthereumService;
import com.dce.business.service.third.IEthereumTransInfoService;
import com.dce.business.service.trade.IWithdrawService;
import com.dce.business.service.user.IUserService;

@RestController
@RequestMapping("/eth")
public class EthereumController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(EthereumController.class);

    @Resource
    private IEthereumService ethereumService;
    @Resource
    private IUserService userService;
    @Resource
    private IEthereumPlatformService platformService;

    @Resource
    private IWithdrawService withdrawService;
    @Resource
    private IEthereumTransInfoService ethTranService;

    @Resource
	private ILoanDictService loanDictService;
    
    @Value("#{sysconfig['app.host']}")
    private String hostUrl;

    @RequestMapping(value = "/accounts", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView list() {
        String userName = getString("userName");
        String trueName = getString("trueName");
        String mobile = getString("mobile");
        String keyword = getString("keyword");
        String page = getString("p");

        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(userName)) {
            params.put("userName", userName);
        }
        if (StringUtils.isNotBlank(trueName)) {
            params.put("trueName", trueName);
        }
        if (StringUtils.isNotBlank(mobile)) {
            params.put("mobile", mobile);
        }
        if (StringUtils.isNotBlank(keyword)) {
            params.put("keyword", keyword);
        }

        List<UserDo> list = userService.selectPage(params);
        Integer totalPage = list.size() / 10;
        if (list.size() % 10 != 0) {
            totalPage++;
        }

        Integer currentPage = 1;
        if (StringUtils.isNotBlank(page)) {
            currentPage = Integer.valueOf(page);
            if (currentPage < 1) {
                currentPage = 1;
            }
            params.put("offset", (currentPage - 1) * 10);
        } else {
            params.put("offset", currentPage - 1);
        }

        list = userService.selectPage(params);

        Integer startPage = getStartPage(currentPage);
        ModelAndView mav = new ModelAndView("openAccount");
        mav.addObject("hostUrl", hostUrl);
        mav.addObject("userList", list);
        mav.addObject("currentPage", currentPage);
        mav.addObject("startPage", startPage);
        mav.addObject("endPage", getEndPage(startPage, totalPage));
        mav.addObject("totalPage", totalPage);
        return mav;
    }

    @RequestMapping(value = "/account/create", method = { RequestMethod.GET, RequestMethod.POST })
    public Result<?> createAccount() {
        Integer userId = Integer.valueOf(getString("userid"));
        String password = getRandomString(8); //生成随机密码
        return ethereumService.creatAccount(userId, password);
    }

    @RequestMapping(value = "/account/createMyAcc", method = { RequestMethod.GET, RequestMethod.POST })
    public Result<?> createMyAccount() {
        Integer userId = this.getUserId();
        EthereumAccountDo userAcc = ethereumService.getByUserId(userId);
        if (userAcc == null || StringUtils.isBlank(userAcc.getAccount())) {
            String password = getRandomString(8); //生成随机密码
            return ethereumService.creatAccount(userId, password);
        } else {
            return Result.successResult("创建成功", userAcc.getAccount());
        }

    }

    @RequestMapping(value = "/listWithdraw", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView listWithdraw() {
        String userName = getString("userName");
        String status = getString("processStatus");
        String startDate = getString("startDate");
        String endDate = getString("endDate");
        String page = getString("p");

        int rows = 10;
        int offset = 0;
        if (StringUtils.isBlank(page)) {
            page = "1";
        }
        int currentPage = Integer.parseInt(page);
        if (currentPage - 1 > 0) {
            offset = (currentPage - 1) * rows;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("rows", rows);

        if (StringUtils.isNotBlank(userName)) {
            params.put("userName", userName);
        }
        if (StringUtils.isNotBlank(status)) {
            params.put("processStatus", status);
        }
        if (StringUtils.isNotBlank(startDate)) {
            params.put("startDate", startDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
            params.put("endDate", endDate);
        }

        List<Map<String, Object>> withdrawLst = withdrawService.getWithdrawRecords(params);

        int totalSize = withdrawService.selectWithdrawCount(params);

        Integer totalPage = totalSize / rows;
        if (totalSize % rows != 0) {
            totalPage++;
        }

        Integer startPage = getStartPage(currentPage);
        ModelAndView mav = new ModelAndView("withdraw");
        mav.addObject("withdrawList", withdrawLst);
        mav.addObject("currentPage", currentPage);
        mav.addObject("startPage", startPage);
        mav.addObject("endPage", getEndPage(startPage, totalPage));
        mav.addObject("totalPage", totalPage);
        mav.addObject("processStatus", status);
        mav.addObject("userName", userName);
        mav.addObject("startDate", startDate);
        mav.addObject("endDate", endDate);
        return mav;
    }

    /**
     * 查询以太坊的交易流水
     * @return
     */
    @RequestMapping(value = "/queryEth", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView queryEth() {

        String userName = getString("userName");
        String startDate = getString("startDate");
        String endDate = getString("endDate");

        String page = getString("p");

        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(userName)) {
            params.put("userName", userName);
        }
        if (StringUtils.isNotBlank(startDate)) {
            params.put("startDate", startDate);
        }
        if (StringUtils.isNotBlank(endDate)) {
            params.put("endDate", endDate);
        }

        if (StringUtils.isBlank(page)) {
            page = "1";
        }

        int rows = 10;
        int offset = (Integer.parseInt(page) - 1) * rows;
        params.put("offset", offset);
        params.put("rows", rows);

        List<Map<String, Object>> ethList = ethTranService.queryEthTrans(params);

        int total = ethTranService.queryEthTransCount(params);

        Integer totalPage = total / 10;
        if (total % 10 != 0) {
            totalPage++;
        }

        Integer currentPage = 1;
        if (StringUtils.isNotBlank(page)) {
            currentPage = Integer.valueOf(page);
        }

        Integer startPage = getStartPage(currentPage);
        ModelAndView mav = new ModelAndView("listEth");
        mav.addObject("ethList", ethList);
        mav.addObject("currentPage", currentPage);
        mav.addObject("startPage", startPage);
        mav.addObject("endPage", getEndPage(startPage, totalPage));
        mav.addObject("totalPage", totalPage);
        mav.addObject("userName", userName);
        mav.addObject("startDate", startDate);
        mav.addObject("endDate", endDate);
        return mav;
    }

    /**
     * 查询平台以太坊帐户 
     * @return
     */
    @RequestMapping(value = "/queryPlatFormEth", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView queryPlatFormEth() {

        String accountNo = getString("accountNo");

        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(accountNo)) {
            params.put("no", accountNo);
        }

        EthAccountPlatformDo platFormAcct = platformService.queryPlatformAccount(params);
        Map<String, String> retAcc = ethereumService.getBalance(platFormAcct.getAccount());
        ModelAndView mav = new ModelAndView("transAmount");
        if (retAcc != null) {
            mav.addObject("balance", retAcc.get("balance"));
        }
        if (null != platFormAcct) {
            mav.addObject("accountNo", platFormAcct.getAccount());
        }
        return mav;
    }

    /**
     * 平台以太坊帐户 转出
     * @return
     */
    @RequestMapping(value = "/tranPlatFormEth", method = { RequestMethod.GET, RequestMethod.POST })
    public Result<?> tranPlatFormEth() {

        String accountNo = getString("centerAccount");
        String receiveAddress = getString("receiveAddress");
        String amount = getString("amount");
        String pwd = getString("password");
        return platformService.sendPlatform(accountNo, receiveAddress, amount, pwd);
    }

    /**
     * 查询当前用户以太坊的交易流水
     * @return
     */
    @RequestMapping(value = "/queryMyEth", method = { RequestMethod.GET, RequestMethod.POST })
    public Result<?> queryMyEth() {

        Integer userId = this.getUserId();

        String page = getString("pageNum");

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);

        List<Map<String, Object>> ethList = ethTranService.queryEthTrans(params);
        for (Map<String, Object> map : ethList) {
        	String withdrawfee = (String) map.get("withdrawFee");
        	String actualGas = (String) map.get("ActualGas");
        	if(StringUtils.isNotBlank(actualGas) ){ //手续费相加
        		if(StringUtils.isNotBlank(withdrawfee) ){
        			BigDecimal fee = new BigDecimal(actualGas).add(new BigDecimal(withdrawfee));
        			map.put("ActualGas", fee.toString());
        		}
        	}
        	
            if (map.get("type") != null && ((Integer) map.get("type")).intValue() != 1) { //不是转入的时候，修改账号显示的问题
                map.put("fromAccount", map.get("toAccount"));
            }
        }

        Integer totalPage = ethList.size() / 10;
        if (ethList.size() % 10 != 0) {
            totalPage++;
        }

        Integer currentPage = 1;
        if (StringUtils.isNotBlank(page)) {
            currentPage = Integer.valueOf(page);
            if (currentPage < 1) {
                currentPage = 1;
            }
            params.put("offset", (currentPage - 1) * 10);
        } else {
            params.put("offset", currentPage - 1);
        }

        Result<List<Map<String, Object>>> result = Result.successResult("");
        result.setData(ethList);
        return result;
    }

    /**
     * 获取eth当前价格
     * @return
     */
    @RequestMapping(value = "/getEthPrice", method = { RequestMethod.GET, RequestMethod.POST })
    public Result<?> getEthPrice() {
        Result<Map<String,Object>> result = Result.successResult("操作成功");
        try {
            BigDecimal usdPrice = ethereumService.getMarketPrice(); //美元价格
            Map<String,Object> data = new HashMap<String,Object>();
            data.put("price", usdPrice);
            BigDecimal rate = null;
            LoanDictDtlDo loanDictDtlDo = loanDictService.getLoanDictDtl(DictCode.Point2RMB.getCode(), DictCode.Point2RMB.getCode());
            if(loanDictDtlDo != null && loanDictDtlDo.getRemark() != null){
            	
            	 rate = new BigDecimal(loanDictDtlDo.getRemark());
            }else{
            	
            	 rate = new BigDecimal("1");
            }
            data.put("rate", rate);
            result.setData(data);
        } catch (Exception e) {
            Result.failureResult(e.getMessage());
        }
        return result;
    }

    /**
     * 审批提现
     * @return
     */
    @RequestMapping(value = "/auditWithdraw", method = { RequestMethod.GET, RequestMethod.POST })
    public Result<?> auditWithdraw() {
        String withdrawId = getString("withdrawId");
        String auditResult = getString("auditResult");
        Result<?> result = Result.successResult("操作成功", withdrawId);
        try {
            withdrawService.auditWithdrawById(auditResult, Integer.valueOf(withdrawId));
        } catch (BusinessException e) {
            logger.error("提现错误:", e);
            Result.failureResult(e.getMessage());
        } catch (Exception e) {
            logger.error("提现错误:", e);
            Result.failureResult("提现错误，请不要重复操作");
        }
        return result;
    }

    /**
     * 保存中心账户
     * @return
     */
    @RequestMapping(value = "/setCenterAcc", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView setCenterAcc() {
        String centerAccount = getString("centerAccount");
        String password = getString("password");
        ModelAndView mav = new ModelAndView("redirect:/eth/platform/accounts.do");
        Result<?> result = Result.successResult("设置成功", centerAccount);
        try {
            platformService.createPlatformAccount(centerAccount, password);
        } catch (Exception e) {
            Result.failureResult(e.getMessage());
        }
        mav.addObject("opt", result.getCode());
        return mav;
    }

    /**
     * 查询中心账户
     * @return
     */
    @RequestMapping(value = "/getCenterAcc", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView getCenterAcc() {
        ModelAndView mav = new ModelAndView("centerAccount");
        return mav;
    }

    public static String getRandomString(int length) {
        //随机字符串的随机字符库
        String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        return sb.toString();
    }

    private Integer getStartPage(Integer currentPage) {
        Integer startPage = 1;
        if (currentPage > 5) {
            startPage = currentPage - 5;
        }

        return startPage;
    }

    private Integer getEndPage(Integer startPage, Integer totalPage) {
        Integer endPage = startPage + 10;
        if (endPage > totalPage) {
            endPage = totalPage;
        }

        return endPage;
    }

    /**
     * 导出用户数据
     */
    @RequestMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        try {
            Long time = System.currentTimeMillis();

            String userName = getString("userName");
            String startDate = getString("startDate");
            String endDate = getString("endDate");
            String status = getString("processStatus");
            
            Map<String, Object> params = new HashMap<>();
            if (StringUtils.isNotBlank(userName)) {
                params.put("userName", userName);
            }
            if (StringUtils.isNotBlank(startDate)) {
                params.put("startDate", startDate);
            }
            if (StringUtils.isNotBlank(endDate)) {
                params.put("endDate", endDate);
            }
            if (StringUtils.isNotBlank(status)) {
                params.put("processStatus", status);
            }
            List<Map<String, Object>> withdrawLst = withdrawService.getWithdrawRecords(params);
            String excelHead = "数据导出";
            String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String fileName = URLEncoder.encode(excelHead + date + ".xls", "utf-8");
            List<String[]> excelheaderList = new ArrayList<String[]>();
            String[] excelheader = { "用户名", "姓名", "手机号码", "提现金额", "申请时间", "审批时间", "提现状态", "提现手续费" };
            excelheaderList.add(0, excelheader);
            String[] excelData = { "user_name", "true_name", "mobile", "amount", "withdrawDateStr", "confirmDateStr", "processStatusStr", "fee" };
            HSSFWorkbook wb = ExeclTools.execlExport(excelheaderList, excelData, excelHead, withdrawLst);
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            wb.write(response.getOutputStream());
            time = System.currentTimeMillis() - time;
            logger.info("导出用户数据，导出耗时(ms)：" + time);
        } catch (Exception e) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("下载失败");
            logger.error("导出用户数据，Excel下载失败", e);
            logger.error("导出用户数据异常", e);
            throw new BusinessException("系统繁忙，请稍后再试");
        } finally {
            response.flushBuffer();
        }

    }

    @RequestMapping(value = "/platform/accounts", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView platformList() {
        List<EthAccountPlatformDo> list = platformService.selectList();

        ModelAndView mav = new ModelAndView("platformAccount");
        mav.addObject("hostUrl", hostUrl);
        mav.addObject("platformList", list);
        return mav;
    }

    @RequestMapping(value = "/platform/accounts/default", method = { RequestMethod.GET, RequestMethod.POST })
    public Result<?> setDefault() {
        String accountNo = getString("accountNo");
        logger.info("设置默认账号，accountNO:" + accountNo);
        return platformService.setDefault(accountNo);
    }

}
