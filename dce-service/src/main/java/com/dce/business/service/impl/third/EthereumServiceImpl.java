package com.dce.business.service.impl.third;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.dce.business.common.enums.CurrencyType;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.Constants;
import com.dce.business.common.util.DataEncrypt;
import com.dce.business.common.util.HttpUtil;
import com.dce.business.dao.etherenum.IEthereumAccountDao;
import com.dce.business.dao.etherenum.IEthereumTransInfoDao;
import com.dce.business.entity.dict.CtCurrencyDo;
import com.dce.business.entity.etherenum.EthereumAccountDo;
import com.dce.business.entity.etherenum.EthereumTransInfoDo;
import com.dce.business.entity.page.PageDo;
import com.dce.business.service.dict.ICtCurrencyService;
import com.dce.business.service.third.IEthereumService;

/** 
 * 以太坊接口
 * @author parudy
 * @date 2018年4月2日 
 * @version v1.0
 */
@Service("ethereumService")
public class EthereumServiceImpl implements IEthereumService {
    private final static Logger logger = Logger.getLogger(EthereumServiceImpl.class);

    @Resource
    private IEthereumAccountDao ethereumAccountDao;
    @Resource
    private IEthereumTransInfoDao ethereumTransInfoDao;
    @Resource
    private ICtCurrencyService ctCurrencyService;

    /**开户**/
    @Value("#{sysconfig['ethereum.creataccount.url']}")
    private String creatAccountUrl;
    /**查询行情**/
    @Value("#{sysconfig['ethereum.marketprice.url']}")
    private String marketPriceUrl;
    /**查询账户余额**/
    @Value("#{sysconfig['ethereum.blance.url']}")
    private String blanceUrl;
    /**转账**/
    @Value("#{sysconfig['ethereum.trans.url']}")
    private String transUrl;
    /**查询转账结果**/
    @Value("#{sysconfig['ethereum.trans.result.url']}")
    private String transResultUrl;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Result<?> creatAccount(Integer userId, String password) {
        logger.info("以太坊开户, UserID:" + userId);

        JSONObject params = buildCommonParams();
        String sign = sign(new String[] { userId.toString(), userId.toString() });
        params.put("userId", userId);
        params.put("password", password);
        String url = creatAccountUrl + "?sign=" + sign;

        Long time = System.currentTimeMillis();
        logger.info("请求以太坊开户：");
        Map<String, Object> result = HttpUtil.post(url, params);
        logger.info("请求以太坊开户耗时ms：" + (System.currentTimeMillis() - time));

        if (result == null || result.get("status") == null || !"OK".equals(result.get("status").toString()) || result.get("account") == null
                || StringUtils.isBlank(result.get("account").toString())) {
            return Result.failureResult("以太坊开户失败");
        }

        //保存以太坊账户信息
        EthereumAccountDo accountDo = new EthereumAccountDo();
        accountDo.setUserid(userId);
        accountDo.setPassword(DataEncrypt.encrypt(password)); //加密存储
        accountDo.setAccount(result.get("account").toString());
        accountDo.setCreatetime(new Date());
        accountDo.setUpdatetime(new Date());
        accountDo.setBalance("0");
        ethereumAccountDao.insertSelective(accountDo);

        Result<String> ret = Result.successResult("以太坊开户成功");
        ret.setData(accountDo.getAccount());
        return ret;
    }

    @Override
    public BigDecimal getMarketPrice() {
        logger.info("查询市场行情");
        Map<String, Object> result = HttpUtil.get(marketPriceUrl);
        if (result == null || result.get("status") == null || !"OK".equals(result.get("status").toString())) {
            return null;
        }

        //只返回美元价格
        return new BigDecimal(result.get("usd").toString());
    }
 
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Result<?> trans(Integer userId, String fromAccount, String toAccount, String password, BigDecimal amount, String pointAmount,
            Integer type,BigDecimal fee) {
        return this.trans(userId, fromAccount, toAccount, password, amount, pointAmount, type, fee, null);
    }
    
    @Async
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Result<?> trans(Integer userId, String fromAccount, String toAccount, String password, BigDecimal amount, String pointAmount,
            Integer type,BigDecimal fee,Integer withdrawId) {
        logger.info("以太坊转账");
        logger.info("userId:" + userId);
        logger.info("fromAccount:" + fromAccount);
        logger.info("toAccount:" + toAccount);
        logger.info("amount:" + amount);

        BigDecimal gas = getGas();
        logger.info("gas:" + gas);
        logger.info("gasLimit:" + gas);

        String sign = sign(new String[] { fromAccount, toAccount, amount.toString(), gas.toString() });

        JSONObject params = buildCommonParams();
        params.put("account", fromAccount);
        params.put("password", password);
        params.put("to", toAccount);
        params.put("value", amount);
        params.put("gas", gas);
        params.put("gasLimit", gas);
        String url = transUrl + "?sign=" + sign;

        Map<String, Object> response = HttpUtil.post(url, params);

        //记录转账流水，只要有hash值就记流水
        if (response != null && response.get("hash") != null) {
            EthereumTransInfoDo transInfo = new EthereumTransInfoDo();
            transInfo.setUserid(userId);
            transInfo.setFromaccount(fromAccount);
            transInfo.setToaccount(toAccount);
            transInfo.setAmount(amount.toString());
            //transInfo.setActualamount(map.get("value").toString());
            transInfo.setPointamount(pointAmount);
            transInfo.setGas(gas.toString());
            transInfo.setGaslimit(gas.toString());
            //transInfo.setActualgas(map.get("gas").toString());
            transInfo.setHash(response.get("hash").toString());
            transInfo.setType(type);
            transInfo.setCreatetime(new Date());
            transInfo.setUpdatetime(new Date());
            transInfo.setWithdrawFee(fee.toString());
            transInfo.setWithdrawalsId(withdrawId);
            ethereumTransInfoDao.insertSelective(transInfo);
        }

        Result<?> result = Result.successResult("正在处理中", response);

        return result;
    }

    /** 
     * 以太坊交易费
     * @return  
     */
    private BigDecimal getGas() {
        CtCurrencyDo ct = ctCurrencyService.selectByName(CurrencyType.IBAC.name());

        if (ct != null && ct.getWith_fee() != null) {
            return ct.getWith_fee();
        }

        return BigDecimal.ZERO;
    }

    @Override
    public Result<Map<String, Object>> getTransResult(String hash) {
        logger.info("查询转账结果");
        logger.info("hash:" + hash);

        String url = transResultUrl + hash;
        Map<String, Object> result = HttpUtil.get(url);
        if (result == null || result.get("status") == null || !"OK".equals(result.get("status").toString())) {
            return Result.failureResult("转账结果确认失败");
        }

        //转账结果确认
        if (result.get("confirmed") != null && "true".equals(result.get("confirmed").toString())) {
            return Result.successResult("转账完成", result);
        }
        return Result.failureResult("转账结果确认失败");
    }

    @Override
    public Map<String, String> getBalance(String account) {
        logger.info("查询账户余额");
        logger.info("account:" + account);

        String url = blanceUrl + account;
        Map<String, Object> result = HttpUtil.get(url);
        if (result == null || result.get("status") == null || !"OK".equals(result.get("status").toString())) {
            return null;
        }

        Map<String, String> map = new HashMap<>();
        map.put("account", result.get("account").toString());
        map.put("balance", result.get("balance").toString());
        return map;
    }

    private JSONObject buildCommonParams() {
        JSONObject json = new JSONObject();
        json.put("id", UUID.randomUUID().toString()); //请求id, 由客户端生成，服务端原样返回
        json.put("key", "."); // 公钥，用于签名鉴权。公钥和私钥为事先分配的字符串
        // json.put("nonce", UUID.randomUUID().toString()); //随机数或者递增数值，用于增强签名随机性

        return json;
    }

    //加签
    private String sign(String[] fields) {
        return null;
    }

    @Override
    public EthereumAccountDo getByUserId(Integer userId) {
        logger.info("查询用户以太坊本地账户信息....");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        return ethereumAccountDao.getEthereumAccount(params);
    }

	@Override
	public BigDecimal getEthernumAmount(Integer userId) {
		BigDecimal ethereumAmount = BigDecimal.ZERO;
		EthereumAccountDo ethereumAccountDo = getByUserId(userId);
		if (ethereumAccountDo != null && StringUtils.isNotBlank(ethereumAccountDo.getAccount())) {
			Map<String, String> map = getBalance(ethereumAccountDo.getAccount());
			if (map != null) {
				ethereumAmount = new BigDecimal(map.get("balance"));
				ethereumAccountDo.setBalance(ethereumAmount.setScale(8,RoundingMode.HALF_DOWN).toString());
				ethereumAccountDao.updateBalance(ethereumAccountDo);
			}
		}
		return ethereumAmount;
	}

	@Override
	public PageDo<Map<String, Object>> selectEthereumAccountByPage(
			PageDo<Map<String, Object>> page, Map<String, Object> params) {
		
		if(params == null){
			params = new HashMap<String,Object>();
		}
		params.put(Constants.MYBATIS_PAGE, page);
		List<Map<String,Object>> list = ethereumAccountDao.selectEthereumAccountByPage(params);
		page.setModelList(list);
		return page;
	}
}
