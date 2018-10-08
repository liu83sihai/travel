package com.dce.business.actions.bank;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dce.business.actions.common.BaseController;
import com.dce.business.common.result.Result;
import com.dce.business.entity.bank.BankDo;
import com.dce.business.service.bank.IBankService;

@RestController
@RequestMapping("bank")
public class BankController extends BaseController {
    @Resource
    private IBankService bankService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result<?> getBankList() {
        List<BankDo> list = bankService.getBankList();
        return Result.successResult("查询成功", list);
    }
}
