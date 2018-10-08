package com.dce.business.service.impl.third;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.Constants;
import com.dce.business.common.util.DataEncrypt;
import com.dce.business.common.util.HttpUtil;
import com.dce.business.dao.etherenum.IEthAccountPlatformDao;
import com.dce.business.dao.etherenum.IEthereumAccountDao;
import com.dce.business.dao.etherenum.IEthereumTransInfoDao;
import com.dce.business.entity.etherenum.EthAccountPlatformDo;
import com.dce.business.entity.etherenum.EthereumAccountDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.dict.ICtCurrencyService;
import com.dce.business.service.third.IEthereumPlatformService;
import com.dce.business.service.third.IEthereumService;

/** 
 * 以太坊接口
 * @author parudy
 * @date 2018年4月2日 
 * @version v1.0
 */
@Service("ethereumPlatformService")
public class EthereumPlatformServiceImpl implements IEthereumPlatformService {

	private final static Logger logger = Logger.getLogger(EthereumPlatformServiceImpl.class);

    @Resource
    private IEthAccountPlatformDao ethAccountDao;
    @Resource
    private IEthereumAccountDao ethereumAccountDao;
    @Resource
    private IEthereumService ethereumService;
    @Resource
    private ICtCurrencyService ctCurrencyService;
    @Resource
    private IEthereumTransInfoDao ethereumTransInfoDao;
    /**开户**/
    @Value("#{sysconfig['ethereum.creataccount.url']}")
    private String creatAccountUrl;

    /**
     * 更改平台账户
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void changePlatformAccount(String newAccount) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", newAccount);
        EthereumAccountDo ethereumAccount = ethereumAccountDao.getEthereumAccount(params);
        if (ethereumAccount == null) {
            throw new BusinessException("无效账户，设置失败");
        }
        ethAccountDao.invalidPlatformAccount();
        EthAccountPlatformDo newPlatformAccount = new EthAccountPlatformDo();
        newPlatformAccount.setAccount(newAccount);
        newPlatformAccount.setPassword(ethereumAccount.getPassword());
        ethAccountDao.insertPlatformAccount(newPlatformAccount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Result<?> createPlatformAccount(String account, String password) {
        logger.info("以太坊开户, account:" + account);

        Map<String, Object> params = new HashMap<>();
        params.put("no", account);
        List<EthAccountPlatformDo> list = ethAccountDao.select(params);
        if (!CollectionUtils.isEmpty(list)) {
            return Result.failureResult("以太坊平台账户编码已存在");
        }

        JSONObject jsonParams = buildCommonParams();
        String sign = sign(new String[] { account, account });
        jsonParams.put("userId", account);
        jsonParams.put("password", password);
        String url = creatAccountUrl + "?sign=" + sign;

        Long time = System.currentTimeMillis();
        logger.info("请求以太坊开户：");
        Map<String, Object> result = HttpUtil.post(url, jsonParams);
        logger.info("请求以太坊开户耗时ms：" + (System.currentTimeMillis() - time));

        if (result == null || result.get("status") == null || !"OK".equals(result.get("status").toString()) || result.get("account") == null
                || StringUtils.isBlank(result.get("account").toString())) {
            return Result.failureResult("以太坊开户失败");
        }

        //保存以太坊账户信息
        EthAccountPlatformDo accountDo = new EthAccountPlatformDo();
        accountDo.setPassword(DataEncrypt.encrypt(password)); //加密存储
        accountDo.setNo(account);
        accountDo.setAccount(result.get("account").toString());
        accountDo.setCreateTime(new Date());
        accountDo.setUpdateTime(new Date());
        accountDo.setStatus("T");
        accountDo.setBalance("0");
        accountDo.setIsDefault(0);
        ethAccountDao.insertPlatformAccount(accountDo);

        Result<String> ret = Result.successResult("以太坊开户成功");
        ret.setData(accountDo.getAccount());
        return ret;
    }

    private JSONObject buildCommonParams() {
        JSONObject json = new JSONObject();
        json.put("id", UUID.randomUUID().toString()); //请求id, 由客户端生成，服务端原样返回
        json.put("key", "."); // 公钥，用于签名鉴权。公钥和私钥为事先分配的字符串
        // json.put("nonce", UUID.randomUUID().toString()); //随机数或者递增数值，用于增强签名随机性

        return json;
    }

    /**
     * 获取平台账户
     */
    @Override
    public EthAccountPlatformDo getPlatformAccount() {
        Map<String, Object> params = new HashMap<>();
        params.put("isDefault", 1);
        List<EthAccountPlatformDo> list = ethAccountDao.select(params);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        return list.get(0);
    }

    //加签
    private String sign(String[] fields) {
        return null;
    }

    @Override
    public List<EthAccountPlatformDo> selectList() {
        Map<String, Object> params = new HashMap<>();
        List<EthAccountPlatformDo> list = ethAccountDao.select(params);
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Result<?> setDefault(String no) {
        List<EthAccountPlatformDo> list = selectList();
        for (EthAccountPlatformDo item : list) {
            item.setIsDefault(0);
            ethAccountDao.updateByPrimaryKeySelective(item);
        }

        EthAccountPlatformDo record = new EthAccountPlatformDo();
        record.setNo(no);
        record.setIsDefault(1);
        int i = ethAccountDao.updateByPrimaryKeySelective(record);

        if (i > 0) {
            return Result.successResult("设置以太坊默认账号成功");
        }
        return Result.failureResult("设置以太坊默认账号失败");
    }
	
    
    @Override
	public EthAccountPlatformDo queryPlatformAccount(Map<String, Object> params) {
         List<EthAccountPlatformDo> list = ethAccountDao.select(params);
         if (!CollectionUtils.isEmpty(list)) {
             return list.get(0);
         }
         return null;
	}

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Result<?> sendPlatform(String accountNo, String receiveAddress, String amount, String pwd) {
        Result<?> result = Result.successResult("发送成功");

        Map<String, Object> params = new HashMap<>();
        params.put("account", accountNo);
        EthAccountPlatformDo ethAccountPlatformDo = queryPlatformAccount(params);
        if (ethAccountPlatformDo == null) {
            result.setCode(Result.failureCode);
            result.setMsg("平台账户不存在");
            return result;
        }

        //校验密码是否正确
        String encryptPwd = DataEncrypt.encrypt(pwd);
        if (StringUtils.isBlank(encryptPwd) || !encryptPwd.equals(ethAccountPlatformDo.getPassword())) {
            result.setCode(Result.failureCode);
            result.setMsg("密码错误");
            return result;
        }

        BigDecimal amt = null;
        try {
            //校验金额
            amt = new BigDecimal(amount);
            if (amt.compareTo(BigDecimal.ZERO) <= 0) {
                result.setCode(Result.failureCode);
                result.setMsg("金额错误");
                return result;
            }

            if (amt.compareTo(new BigDecimal("0.1")) <= 0) {
                result.setCode(Result.failureCode);
                result.setMsg("发送金额不能小于0.1以太坊");
                return result;
            }
        } catch (Exception e) {
            result.setCode(Result.failureCode);
            result.setMsg("金额错误");
            return result;
        }

        String toAccount = receiveAddress; //转入账号

        result = ethereumService.trans(0, accountNo, toAccount, pwd, amt, amount, 4,BigDecimal.ZERO);

        //以太坊转账失败
        if (!result.isSuccess()) {
            return result;
        }

        return result;
    }

	@Override
	public PageDo<EthAccountPlatformDo> selectEthAccountPlatByPage(
			PageDo<EthAccountPlatformDo> page, Map<String, Object> params) {
		if(params == null){
			params = new HashMap<String,Object>();
		}
		params.put(Constants.MYBATIS_PAGE, page);
		List<EthAccountPlatformDo> list = ethAccountDao.selectByPage(params);
		page.setModelList(list);
		return page;
	}
    
    /** 
     * 以太坊交易费
     * @return  
     */
//    private BigDecimal getGas() {
//        CtCurrencyDo ct = ctCurrencyService.selectByName(CurrencyType.DCE.name());
//
//        if (ct != null && ct.getWith_fee() != null) {
//            return ct.getWith_fee();
//        }
//
//        return BigDecimal.ZERO;
//    }
}
