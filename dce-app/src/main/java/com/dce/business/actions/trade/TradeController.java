package com.dce.business.actions.trade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.enums.AccountType;
import com.dce.business.common.enums.CurrencyType;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.DateUtil;
import com.dce.business.entity.account.UserAccountDo;
import com.dce.business.entity.dict.CtCurrencyDo;
import com.dce.business.entity.order.Order;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.account.IAccountService;
import com.dce.business.service.dict.ICtCurrencyService;
import com.dce.business.service.dict.ILoanDictService;
import com.dce.business.service.order.IOrderService;

/** 
 * 交易
 * @author parudy
 * @date 2018年3月25日 
 * @version v1.0
 */
@RestController
@RequestMapping("trade")
public class TradeController extends BaseController {
    private final static Logger logger = Logger.getLogger(TradeController.class);

    @Resource
    private ILoanDictService loanDictService;
    @Resource
    private IAccountService accountService;
    @Resource
    private ICtCurrencyService ctCurrencyService;
    @Resource
    private IOrderService orderService;

    /** 
     * 查询交易明细
     * @return  
     */
    @RequestMapping(value = "/records", method = RequestMethod.POST)
    public Result<?> getTradeRecords() {
        Integer userId = getUserId();

        logger.info("查询交易明细, userId:" + userId);

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("payStatus", 1); //已成交
        List<Order> list = orderService.selectOrder(params);

        return Result.successResult("查询成功", convertTradeInfo(list));
    }

    /** 
     * 进入交易页面，买入时，查询用户基本信息、包括当前价格等
     * @return  
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Result<?> getTradeInfo() {
        Integer userId = getUserId();
        String accountType = getString("accountType");
        logger.info("进入交易页面, userId:" + userId);

        //查询美元点
        UserAccountDo userAccountDo = accountService.getUserAccount(userId, AccountType.getAccountType(accountType));
        
        BigDecimal pointAmount = BigDecimal.ZERO;
        if(userAccountDo != null && userAccountDo.getAmount() != null){
        	pointAmount = userAccountDo.getAmount();
        }
        CtCurrencyDo ct = ctCurrencyService.selectByName(CurrencyType.IBAC.name());
//        LoanDictDtlDo dtl = loanDictService.getLoanDictDtl(DictCode.DEC2RMB.getCode());
//        LoanDictDtlDo rate = loanDictService.getLoanDictDtl(DictCode.TRANS_RATE.getCode());
//        BigDecimal price = new BigDecimal(dtl.getRemark());
//        BigDecimal low = price.multiply(new BigDecimal(0.9)).setScale(6, RoundingMode.HALF_UP);
//        BigDecimal high = price.multiply(new BigDecimal(1.1)).setScale(6, RoundingMode.HALF_UP);

        Map<String, Object> map = new HashMap<>();
        map.put("pointAmount", pointAmount); //美元点余额
        map.put("latestPrice", ct.getPrice_open()); //最新价格
        map.put("lowestPrice", ct.getPrice_down());//挂单范围最低价格
        map.put("highestPrice",ct.getPrice_up());//挂单范围最高价格
        
//        float _rate = Float.parseFloat(rate.getRemark());
//        _rate *= 100;
        map.put("feeRate", ct.getCurrency_sell_fee() + "%");//交易费率

        return Result.successResult("查询成功", map);
    }

    /** 
     * 交易页面基本信息
     * @return  
     */
    @RequestMapping(value = "/baseInfo", method = RequestMethod.GET)
    public Result<?> getTradeBaseInfo() {
        Integer userId = getUserId();
        logger.info("进入交易页面, userId:" + userId);

//        LoanDictDtlDo dtl = loanDictService.getLoanDictDtl(DictCode.DEC2RMB.getCode());
        
        Map<String,Object> result = orderService.getBaseInfo(DateUtil.dateToString(new Date()));
        CtCurrencyDo ct = ctCurrencyService.selectByName(CurrencyType.IBAC.name());
        
        Map<String, Object> map = new HashMap<>();
        map.put("latestPrice", ct.getPrice_open()); //当前价格
        //map.put("24hourHighestPrice", result.get("maxPrice") == null?dtl.getRemark():result.get("maxPrice")); //24小时最高价
        //map.put("24hourLowestPrice", result.get("minPrice") == null?dtl.getRemark():result.get("minPrice"));//24小时最低价
        map.put("24hourHighestPrice",ct.getPrice_up());
        map.put("24hourLowestPrice",ct.getPrice_down());
        map.put("24hourVolume", result.get("salnum") == null?0:result.get("salnum"));//24小时成交量
        map.put("rose", -0.18);//涨幅 
        map.put("roseRate", -5.29);//涨幅比例
        return Result.successResult("查询成功", map);
    }

//    /** 
//     * 以美元点购买DCE币
//     * @return  
//     */
//    @RequestMapping(value = "/buy", method = RequestMethod.POST)
//    public Result<?> buyCoin(@Valid TradeDo tradeDo, BindingResult bindingResult) {
//        logger.info("用户交易：" + JSON.toJSONString(tradeDo));
//
//        //参数校验
//        if (bindingResult.hasErrors()) {
//            List<ObjectError> errors = bindingResult.getAllErrors();
//            logger.info("用户交易，参数校验错误：" + JSON.toJSONString(errors));
//            return Result.failureResult(errors.get(0).getDefaultMessage());
//        }
//
//        return Result.successResult("购买成功");
//    }

    
    
    /** 
     * 获取当前是否允许交易的状态 :T 允许交易， F不允许交易
     * @return  
     */
    @RequestMapping(value = "/getTradeOpenStatu", method = RequestMethod.POST)
    public Result<?> getTradeOpenStatu() {
        logger.info("获取当前是否允许交易的状态");
        CtCurrencyDo ct = ctCurrencyService.selectByName(CurrencyType.IBAC.name());
        Result<String> result = Result.successResult("获取当前是否允许交易的状态成功");
        result.setData("T");
		if(ct == null || ct.getIs_lock() == null || 0 == ct.getIs_lock().intValue()){
			result.setData("F");
		}
        return result;
    }
    
    
    
    /** 
     * 我的收款码
     * @return  
     */
    @RequestMapping(value = "/getMyQRCode", method = RequestMethod.POST)
    public Result<?> getMyQRCode() {
        logger.info("我的收款码");
        String qrcode = accountService.getMyQRCode(this.getUserId());
        Result<String> result = Result.successResult("获取收款二维码成功");
        result.setData(qrcode);
        return result;
    }
    
    /** 
     * 根据收款码获取收款人信息
     * @return  
     */
    @RequestMapping(value = "/getReceiverQRCode", method = RequestMethod.POST)
    public Result<UserDo> getReceiverQRCode() {
        logger.info("根据收款码获取收款人信息");
        
        UserDo user = accountService.getReceiverQRCode(this.getString("qrCode"));
        Result<UserDo> result = Result.successResult("获取收款人成功");
        result.setData(user);
        return result;
    }
    
    /** 
     * 人民币转dce币
     * @return  
     */
    @RequestMapping(value = "/calRmb2Dce", method = RequestMethod.POST)
    public Result<?> calRmb2Dce() {
        logger.info("人民币转现金币");
        String amt = this.getString("amt");
        BigDecimal rmbAmt = null;
        try{
        	rmbAmt = new BigDecimal(amt);
        }catch(Exception e){
        	rmbAmt =null;
        }
        if(null == rmbAmt){
        	return Result.failureResult("请输入正确的消费金额");
        }
        try{
        	BigDecimal dceamt = loanDictService.rmb2Dce(rmbAmt);
            
        	return  Result.successResult("人民币转现金币成功", dceamt);
        }catch(BusinessException be){
        	return Result.failureResult(be.getMessage());
        }catch(Exception bee){
        	return Result.failureResult("转换人民币出错");
        }
       
        
    }
    
    
    /** 
     * 扫描支付
     * @return  
     */
    @RequestMapping(value = "/payByQRCode", method = RequestMethod.POST)
    public Result<?> payByQRCode() {
        logger.info("扫描支付");
        String qrCode = this.getString("qrCode");
        String amount = this.getString("amount");
        String pwd = this.getString("payPwd");
        
        return accountService.payByQRCode(this.getUserId(),qrCode,amount,pwd);
    }
    
    /** 
     * 转到别的以太方地址
     * @return  
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public Result<?> send() {
        logger.info("发送别的以太方地址");
        String qrCode = this.getString("receiveAddress");
        String amount = this.getString("amount");
        String pwd = this.getString("password");
        
        return accountService.send(this.getUserId(),qrCode,amount,pwd);
    }
    
    
    private List<Map<String, Object>> convertTradeInfo(List<Order> list) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Order orderDo : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("time", orderDo.getCreatetime());
            map.put("price", orderDo.getGoodsprice()); //单价
            map.put("num", orderDo.getQty()); //成交量
            map.put("totalmoney", orderDo.getTotalprice()); //总金额
            result.add(map);
        }

        return result;
    }

    public static void main(String[] args) {
        Long time = 1523635374000L;
        System.out.println(new Date(time));

//        java.sql.Date a = new java.sql.Date(1517466887);
//        System.out.println(DateUtil.dateToString(a));
        System.out.println();
    }
}
