package com.dce.business.service.impl.bank;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.dce.business.common.exception.BusinessException;
import com.dce.business.common.result.Result;
import com.dce.business.common.util.Constants;
import com.dce.business.common.util.DataEncrypt;
import com.dce.business.dao.bank.IBankCardDao;
import com.dce.business.entity.bank.BankCardDo;
import com.dce.business.entity.bank.BankCardDo.PayChannelTypeEnum;
import com.dce.business.entity.page.PageDo;
import com.dce.business.entity.user.UserDo;
import com.dce.business.service.bank.IBankCardService;
import com.dce.business.service.pay.IKJTPayService;
import com.dce.business.service.user.IUserService;

/**
 * @author liminglmf
 * @data 2017年5月9日
 */
@Service("bankCardService")
public class BankcardServiceImpl implements IBankCardService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Resource
    private IBankCardDao bankCardDao;
    @Resource
    private IUserService userService;
    
    @Resource
    private IKJTPayService  kjtPayService;
    
   
    @Override
    public BankCardDo selectDefaultBankcard(Long userId, String payChannelType, String cardType) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("isDefault", 1);
        param.put("cardStatus", 1);//绑卡状态  1: 已绑定  4: 解绑
        param.put("payChannelType", payChannelType);//0-快钱渠道，1-通联渠道
        param.put("cardType", cardType);
        List<BankCardDo> bankCardList = bankCardDao.selectBankcard(param);
        if (bankCardList != null && bankCardList.size() > 0) {
            return bankCardList.get(0);
        }
        return null;
    }

    @Override
    public BankCardDo selectBankcardByUserIdAndChannalCode(Long userId, String payChannelType) {

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("payChannelType", payChannelType);
        List<BankCardDo> bankCardList = bankCardDao.selectBankcard(param);
        if (bankCardList != null && bankCardList.size() > 0) {
            return bankCardList.get(0);
        }
        return null;

    }

    @Override
    public BankCardDo getBankCardByUserId(Long userId, String payChannelType, String cardType) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("payChannelType", payChannelType);
        param.put("cardType", cardType);
        List<BankCardDo> bankCardList = bankCardDao.selectBankcard(param);
        if (bankCardList != null && bankCardList.size() > 0) {
            return bankCardList.get(0);
        }
        return null;
    }

    @Override
    public BankCardDo selectBankcardByCardNo(String cardNo, String payChannelType, String cardType) {
        //加密处理 2017.07.10
        cardNo = DataEncrypt.encrypt(cardNo);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("cardNo", cardNo);
        param.put("payChannelType", payChannelType);//0-快钱渠道，1-通联渠道
        param.put("cardType", cardType);//0-对私,1-对公
        List<BankCardDo> bankCardList = bankCardDao.selectBankcard(param);
        if (bankCardList != null && bankCardList.size() > 0) {
            return bankCardList.get(0);
        }
        return null;
    }

    @Override
    public boolean isExistBankCard(Long userId) {
        logger.info("查询用户是否存在银行卡，userID:" + userId);
        if (userId == null) {
            return false;
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        params.put("payChannelType", PayChannelTypeEnum.QUICK.value());
        List<BankCardDo> list = bankCardDao.selectBankcard(params);

        return !CollectionUtils.isEmpty(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Result<?> saveBankCardInfo(BankCardDo bankCardDo) {
        logger.info("保存银行卡信息：" + JSON.toJSONString(bankCardDo));

        //加密处理 2017.07.10
        bankCardDo.setCardNo(DataEncrypt.encrypt(bankCardDo.getCardNo()));
        bankCardDo.setCardUserName(DataEncrypt.encrypt(bankCardDo.getCardUserName()));

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", bankCardDo.getUserId());
        param.put("isDefault", 1);//是否默认 0 false  1 true
        param.put("payChannelType", bankCardDo.getPayChannelType());//分支付渠道
        param.put("cardType", bankCardDo.getCardType());//分卡类型，对公或对私
        List<BankCardDo> bankCardList = bankCardDao.selectBankcard(param);

        //将用户渠道默认的卡 重置为 不默认
        int updateCount = 1;
        if (bankCardList != null && bankCardList.size() > 0) {
            BankCardDo bankCardOld = new BankCardDo();
            bankCardOld.setId(bankCardList.get(0).getId());
            bankCardOld.setIsDefault(0);
            updateCount = bankCardDao.updateByPrimaryKeySelective(bankCardOld);
        }
        //将用户渠道默认的卡 重置为 不默认 成功后 
        if (updateCount > 0) {
            //判断该用户是否有绑定过该卡 有则更新 无 则新增
            param.clear();
            param.put("userId", bankCardDo.getUserId());
            param.put("cardNo", bankCardDo.getCardNo());
            param.put("payChannelType", bankCardDo.getPayChannelType());//分支付渠道
            param.put("cardType", bankCardDo.getCardType());//分卡类型，对公或对私
            BankCardDo cardOld = null;
            bankCardList = bankCardDao.selectBankcard(param);
            if (bankCardList != null && bankCardList.size() > 0) {
                cardOld = bankCardList.get(0);
            }

            int i = 1;
            if (cardOld != null) {
                bankCardDo.setId(cardOld.getId());
                i = bankCardDao.updateByPrimaryKeySelective(bankCardDo);
                if (i < 1) {
                    logger.info("buildResult1==" + bankCardDo + " 用户卡修改失败");
                    return Result.failureResult(bankCardDo + " 用户卡修改失败");
                }
            } else {
                i = bankCardDao.insertSelective(bankCardDo);
                if (i < 1) {
                    logger.info("buildResult1==" + bankCardDo + " 用户卡新增失败");
                    return Result.failureResult(bankCardDo + " 用户卡新增失败");
                }
            }
            Result<Integer> result =Result.successResult("成功");
            result.setData(Integer.valueOf(i));
            return result;
        } else {
            logger.info("buildResult2==" + bankCardDo + " 用户卡新增失败");
            return Result.failureResult( bankCardDo + "用户卡新增失败");
        }
    }

    @Override
    public List<BankCardDo> getByUserIdAndIsDefault(Long userId, String isDefault) {
        if (userId == null && StringUtils.isBlank(isDefault)) {
            return null;
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", userId);
        param.put("isDefault", isDefault);
        return bankCardDao.selectBankcard(param);
    }

    @Override
    public PageDo<BankCardDo> selectBankcardPage(BankCardDo bankcardDo, PageDo<BankCardDo> page) {

        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(bankcardDo.getMobile())) {
            Map<String, Object> map = new HashMap<String,Object>();
            map.put("mobile", bankcardDo.getMobile());
            List<UserDo> userLst = userService.selectUserCondition(map);
            if (userLst == null || userLst.size()<1) {
                logger.error("查询银行卡用户不存在，mobile:" + bankcardDo.getMobile());
                return null;
            }
            params.put("userId", userLst.get(0).getId());
        }

        if (StringUtils.isNotBlank(bankcardDo.getCardUserName())) {
            params.put("cardUserName", bankcardDo.getCardUserName());
        }
        if (StringUtils.isNotBlank(bankcardDo.getCardNo())) {
            params.put("cardNo", bankcardDo.getCardNo());
        }
        if (bankcardDo.getCardStatus() != null) {
            params.put("cardStatus", bankcardDo.getCardStatus());
        }
        if (bankcardDo.getIsDefault() != null) {
            params.put("isDefault", bankcardDo.getIsDefault());
        }
        params.put("payChannelType", "0"); //渠道类型：快钱

        params.put(Constants.MYBATIS_PAGE, page);
        List<BankCardDo> list = bankCardDao.selectBankcardPage(params);
        page.setModelList(list);
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Result<?> unBindBankCard(Long id) {
        logger.info("解绑银行卡，id:" + id);

        BankCardDo bankCard = bankCardDao.selectByPrimaryKey(id);
        if (bankCard == null) {
            return Result.failureResult("无此卡信息,失败");
        }

        Long userId = bankCard.getUserId();
        String cardNo = bankCard.getCardNo();
        String bankCode = bankCard.getBankCode();
        if (userId == null || StringUtils.isBlank(cardNo) || StringUtils.isBlank(bankCode)) {
            logger.error("绑卡信息不完整：userId:" + userId + "; cardNo:" + cardNo + "; bankcode:" + bankCode);
            return Result.failureResult("卡信息不完整,失败");
        }

        //2017.06.23解绑卡快钱失败时，也将绑卡标志置为解绑状态
//        boolean bindResult = payService.deleteBindCard(userId, cardNo, bankCode);
//        if (!bindResult) {
//            logger.error("快钱解绑卡失败");
//            //return ResultSupport.buildErrorResult("第三方解绑,失败");
//        }

        //加密处理2017.07.10
        BankCardDo newbankCard = new BankCardDo();
        newbankCard.setId(bankCard.getId());
        newbankCard.setCardStatus(4);
        newbankCard.setIsDefault(0); //表示解绑为非默认卡
        newbankCard.setUpdateTime(new Date());
        int count = bankCardDao.updateByPrimaryKeySelective(newbankCard);
        if (count > 0) {
            return Result.successResult("解绑成功", null);
        }
        return Result.failureResult("解绑失败");
    }

    @Override
    public BankCardDo getBankCard(Long id) {
        logger.info("查询绑卡信息，id:" + id);
        Assert.notNull(id, "id不能为空");
        return bankCardDao.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void updateBankCard(Long id, String branchBankName, String provinceId, String cityId, Integer isDefault) {
        Assert.notNull(id, "银行卡id不能为空");
        logger.info("修改绑卡信息：id:" + id + "; branchBankName:" + branchBankName + "; provinceId:" + provinceId + "; cityId:" + cityId + "; isDefault:"
                + isDefault);

        //设置该银行卡为默认银行卡
        if (isDefault != null && isDefault == 1) {
            BankCardDo bankCardDo = bankCardDao.selectByPrimaryKey(id);
            if (bankCardDo == null || bankCardDo.getUserId() == null) {
                throw new BusinessException("银行卡不存在，id：" + id);
            }
            //设置为是默认的银行卡的时候需要将该用户的其他银行卡设置为非默认的
            Map<String, Object> paraMap = new HashMap<String, Object>();
            paraMap.put("userId", bankCardDo.getUserId());
            paraMap.put("isDefault", "1");
            paraMap.put("payChannelType", bankCardDo.getPayChannelType());//分支付渠道
            paraMap.put("cardType", bankCardDo.getCardType());//分卡类型，对公或对私
            List<BankCardDo> bankCardList = bankCardDao.selectBankcard(paraMap);
            if (!CollectionUtils.isEmpty(bankCardList)) {
                //BankCardDo bankCardOld = bankCardList.get(0);
                BankCardDo newBankCard = new BankCardDo();
                newBankCard.setId(bankCardList.get(0).getId());
                newBankCard.setIsDefault(0);
                int updateCount = bankCardDao.updateByPrimaryKeySelective(newBankCard);
                logger.info("修改了：" + updateCount + "条银行卡记录为非默认的银行卡！");
            }
        }

        BankCardDo newLoanBankcardDo = new BankCardDo();
        newLoanBankcardDo.setId(id);
        newLoanBankcardDo.setBranchBankName(branchBankName);
        newLoanBankcardDo.setProvinceId(provinceId);
        newLoanBankcardDo.setCityId(cityId);
        newLoanBankcardDo.setIsDefault(isDefault);
        newLoanBankcardDo.setUpdateTime(new Date());

        logger.info("修改银行卡信息：" + JSON.toJSONString(newLoanBankcardDo));
        int i = bankCardDao.updateByPrimaryKeySelective(newLoanBankcardDo);
        if (i < 1) {
            throw new BusinessException("修改绑卡信息失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Result<?> updateJustOneDefaultCard(Long userId, Long bankId) {
        List<BankCardDo> bankList = getByUserIdAndIsDefault(userId, "1");
        if (bankList != null) {
            for (BankCardDo bankCardDo : bankList) {
                BankCardDo newbankCardDo = new BankCardDo();
                newbankCardDo.setId(bankCardDo.getId());
                newbankCardDo.setIsDefault(0);
                newbankCardDo.setUpdateTime(new Date());
                bankCardDao.updateByPrimaryKeySelective(newbankCardDo);
            }
        }

        BankCardDo bankCardDo = new BankCardDo();
        bankCardDo.setId(bankId);
        bankCardDo.setIsDefault(1);
        bankCardDao.updateByPrimaryKeySelective(bankCardDo);
        return Result.successResult("选择唯一的上标银行卡成功！");
    }

    @Override
    public Result<?> setBankCard2Binded(Long userId, Long bankId) {
        if (bankId == null) {
            return Result.failureResult("传入的银行卡id为空！");
        }
        Map<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("userId", userId);
        paraMap.put("isDefault", "1");
        List<BankCardDo> bankCardList = bankCardDao.selectBankcard(paraMap);
        if (!CollectionUtils.isEmpty(bankCardList)) {
            for (BankCardDo bankCardDo : bankCardList) {
                BankCardDo newbankCardDo = new BankCardDo();
                newbankCardDo.setId(bankCardDo.getId());
                newbankCardDo.setCardStatus(4);
                newbankCardDo.setUpdateTime(new Date());
                bankCardDao.updateByPrimaryKeySelective(newbankCardDo);
            }
        }

        BankCardDo bankCardDo = new BankCardDo();
        bankCardDo.setId(bankId);
        bankCardDo.setCardStatus(1);
        bankCardDao.updateByPrimaryKeySelective(bankCardDo);
        return Result.successResult("绑定银行卡成功！");
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Result<?> saveCbtBankCardInfo(BankCardDo bankCardDo) {
        bankCardDo.setCardNo(DataEncrypt.encrypt(bankCardDo.getCardNo()));
        bankCardDo.setCardUserName(DataEncrypt.encrypt(bankCardDo.getCardUserName()));

        Map<String, Object> params = new HashMap<>();
        params.put("userId", bankCardDo.getUserId());
        params.put("cardNo", bankCardDo.getCardNo());
        params.put("payChannelType", bankCardDo.getPayChannelType());
        List<BankCardDo> list = bankCardDao.selectBankcard(params);

        int count = 0;
        //卡已存在，更新
        if (!CollectionUtils.isEmpty(list)) {
            bankCardDo.setId(list.get(0).getId());
            count = bankCardDao.updateByPrimaryKeySelective(bankCardDo);
        } else {
            count = bankCardDao.insertSelective(bankCardDo);
        }

        if (count == 1) {
            return Result.successResult("保存银行卡成功", bankCardDo);
        } else {
            return Result.failureResult("保存银行卡失败");
        }
    }

	@Override
	public Result<?> getBankCardCode(String idNo, 
									 String cardUserName, 
									 String mobile, 
									 String cardNo,
									 Integer userId) throws Throwable {
		
		Map<String, Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("cardNo", cardNo);
		List<BankCardDo> cardLst = bankCardDao.selectBankcard(param);
		
		BankCardDo record = null;
		if(null == cardLst || cardLst.size()<1) {
			record = new BankCardDo();
			record.setUserId(Long.valueOf(userId));
			record.setCardNo(cardNo);
			record.setBankMobile(mobile);
			record.setCardUserName(cardUserName);
			record.setCardStatus(0);
			bankCardDao.insertSelective(record );
		}else {
			record = cardLst.get(0);
			if(record.getCardStatus().intValue() == 1) {
				return Result.successResult("此银行卡已绑定");
			}
		}
		Result<?> ret =  kjtPayService.executeGetBankCardCode(idNo,cardUserName,mobile,cardNo,userId);
		if(ret.isSuccess()) {
			Map<String,Object> dataModel = (Map)ret.getData();
			if(null != dataModel) {
				String token = (String) dataModel.get("token");
				if(null != token) {
					record.setToken(token);
					bankCardDao.updateByPrimaryKeySelective(record);
				}
			}
		}
		return ret;
	}

	@Override
	public Result<?> bindBankCard(Integer userId, 
								  String bankId, 
								  String mobileCode, 
								  String mobile, 
								  String cardNo,
								  String idNo, 
								  String tokenId, 
								  String amount,
								  String bankAccountName,
								  String orderCode) throws Throwable {
		
		//Result<?> chckRet = kjtPayService.executeCheckCode(tokenId, mobileCode, userId);
		
		String signingPay = "Y";
		String cvv2 ="";
		String validDate = "";
		Result<?> tradeRet = kjtPayService.executePayInstantTrade("51", 
											 amount, 
											 tokenId, 
											 signingPay, 
											 cardNo,
											 mobile,
											 bankAccountName,
											 cvv2, 
											 validDate, 
											 idNo, 
											 userId);
		
		return tradeRet;
	}
}
