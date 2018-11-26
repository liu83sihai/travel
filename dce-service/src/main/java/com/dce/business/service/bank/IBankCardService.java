
package com.dce.business.service.bank;

import java.util.List;

import com.dce.business.common.result.Result;
import com.dce.business.entity.bank.BankCardDo;
import com.dce.business.entity.page.PageDo;

/**
 * @author liminglmf
 * @data 2017年5月9日
 */
public interface IBankCardService {
    /**
     * 根据用户ID
     * @param userId
     * @param payChannelType 渠道  0 :快钱  1:通联
     * @param cardType 
     * @return
     */
    BankCardDo selectDefaultBankcard(Long userId, String payChannelType, String cardType);
    
    /**
     * 根据用户id和渠道编码查询用户的银行卡
     * (目前用于彩白条 彩白条用户过来时  只传了银行卡号 而在保存的时候不能将该卡号执成默认的  因为彩生活用户有可能也是花易借用户)
     * @param userId
     * @param payChannelType
     * @return
     */
    BankCardDo selectBankcardByUserIdAndChannalCode(Long userId, String payChannelType);

    /** 
     * 根据银行卡号查询 绑卡信息
     * @param cardNo
     * @param payChannelType 渠道  0 :快钱  1:通联
     * @param cardType
     * @return  
     */
    BankCardDo selectBankcardByCardNo(String cardNo, String payChannelType, String cardType);

    /** 
     * 查询用户是否存在银行卡
     * @param userId 用户id
     * @return  
     */
    boolean isExistBankCard(Long userId);

    /**
     * 保存绑卡信息
     * @param bankCard
     * @return
     */
    Result<?> saveBankCardInfo(BankCardDo bankCardDo);

    /**
    * 根据用户ID查询银行卡
    * @param userId
    * @param payChannelType 渠道  0 :快钱  1:通联
    * @param cardType 
    * @return
    */
    BankCardDo getBankCardByUserId(Long userId, String payChannelType, String cardType);

    List<BankCardDo> getByUserIdAndIsDefault(Long userId, String isDefault);

    /** 
     * 查询银行卡，分页查询
     * @param param
     * @param page
     * @return  
     */
    PageDo<BankCardDo> selectBankcardPage(BankCardDo param, PageDo<BankCardDo> page);

    /**
     * 后台管理员解绑客户银行卡
     * @param id
     * @return
     */
    Result<?> unBindBankCard(Long id);

    /** 
     * 查询绑卡信息
     * @param id 
     * @return  
     */
    BankCardDo getBankCard(Long id);

    /** 
     * 管理平台修改绑卡信息
     * @param id 绑卡id
     * @param branchBankName 支行信息
     * @param provinceId 省份
     * @param cityId 城市
     * @param isDefault 是否默认 
     */
    void updateBankCard(Long id, String branchBankName, String provinceId, String cityId, Integer isDefault);

	Result<?> updateJustOneDefaultCard(Long userId, Long bankId);
	
	/**
	 * 将卡设置为绑定状态
	 * @param bankId 设置为绑定状态的银行卡id
	 * @return
	 */
	Result<?> setBankCard2Binded(Long userId, Long bankId);
	
	/**
     * 保存彩白条绑卡信息
     * @param bankCard
     * @return
     */
    Result<?> saveCbtBankCardInfo(BankCardDo bankCardDo);

    /**
     *	绑卡的时候 获取短信验证码
     * @param idNo
     * @param cardUserName
     * @param mobile
     * @param cardNo
     * @param userId
     * @return
     */
	public Result<?> getBankCardCode(String idNo, 
									 String cardUserName, 
									 String mobile, 
									 String cardNo,
									 Integer userId) throws Throwable;

	/**
	 * 	 绑卡时验证短信验证码， 如果首次绑卡 还会支付
	 * @param userId
	 * @param bankId
	 * @param mobileCode
	 * @param mobile
	 * @param cardNo
	 * @param idNo
	 * @param tokenId
	 * @return
	 */
	Result<?> bindBankCard(Integer userId, 
							String bankId, 
							String mobileCode, 
							String mobile, 
							String cardNo, 
							String idNo,
							String tokenId,
							String money,
							String bankAccountName,
							String orderCode) throws Throwable;
}
