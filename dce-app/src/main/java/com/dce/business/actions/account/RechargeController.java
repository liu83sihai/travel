package com.dce.business.actions.account;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.service.account.IPayService;

@RestController
@RequestMapping("/recharge")
public class RechargeController extends BaseController {
    private final static Logger logger = Logger.getLogger(RechargeController.class);

    @Resource
    private IPayService payService;

    /** 
     * 用户充值
     * @return  
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result<?> recharge() {
        Integer userId = getUserId();
        String password = getString("password");
        String qty = getString("qty");

        Assert.hasText(password, "充值密码不能为空");
        Assert.hasText(qty, "充值以太坊数量不能为空");
        logger.info("用户充值, userId:" + userId + "; qty:" + qty);
        BigDecimal bdcQty = new BigDecimal(qty);
        if(bdcQty.compareTo(BigDecimal.ZERO) <= 0){
        	return Result.failureResult("请输入正确的充值数量");
        }
        
        Result<?> result = payService.recharge(getUserId(), password, bdcQty);
        logger.info("充值结果返回:" + JSON.toJSONString(result));
        return result;
    }
    
}
