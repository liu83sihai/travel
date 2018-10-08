package com.dce.business.service.impl.bank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dce.business.dao.bank.IBankDao;
import com.dce.business.entity.bank.BankDo;
import com.dce.business.service.bank.IBankService;

@Service("bankService")
public class BankServiceImpl implements IBankService {
    @Resource
    private IBankDao bankDao;

    @Override
    public List<BankDo> getBankList() {
        Map<String, Object> params = new HashMap<>();
        params.put("bankFlag", 1);
        return bankDao.selectBank(params);
    }
}
