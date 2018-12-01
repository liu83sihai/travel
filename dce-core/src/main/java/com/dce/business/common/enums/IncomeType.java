package com.dce.business.common.enums;

/** 
 * 账户流水类型
 * @author parudy
 * @date 2018年3月26日 
 * @version v1.0
 */
public enum IncomeType {
    /**
     * 静态释放
     */
    TYPE_STATIC(1, "静态释放"),
    /**
     * 静态释放
     */
    ACCOUNT_OUT(21, "现金账户转出"),
    ACCOUNT_IN(22, "现金账户转入"),
    PAY_OUT(23, "二维码付款支付"),
    PAY_IN(24, "二维码付款收入"),
    /** 
     * 购买订单
     * @return  
     */
    TYPE_PURCHASE(12, "购买订单"),
    
    /** 
     * 购买旅游卡
     * @return  
     */
    TYPE_PURCHASE_TRAVEL(122, "购买旅游卡"),
    
    /** 
     * 卖出订单
     * @return  
     */
    TYPE_SELL(11, "卖出订单"),
    /** 
     * 活动奖
     * @return  
     */
    TYPE_CANCEL(13, "活动奖"),
    
    /** 
     * 赠送商品奖
     * @return  
     */
    TYPE_CANCEL_BUY(14, "赠送商品奖"),
    
    /** 
     * 充值
     * @return  
     */
    TYPE_RECHARGE(21, "充值"),
    /**
     * 提现
     */
    TYPE_WITHDRAW(22, "提现"),
    /**
     * 提现拒绝
     */
    TYPE_BACK_WITHDRAW(23, "提现拒绝"),
    /**
     * 推荐奖励
     */
    TYPE_AWARD_REFEREE(311, "推荐奖励"),
    /**
     * 区域奖励
     */
    TYPE_AWARD_LEADER(321, "区域奖励"),
	
    /**
     * 会员奖励
     */
    TYPE_AWARD_BUYER(331, "旅游奖励"),
    
    /**
     * 会员体验奖
     */
    TYPE_AWARD_EXPERIENCE(3331, "会员体验奖"),
    /**
     * 互助奖励
     */
    TYPE_AWARD_PASSIVITYLEADER(341, "被动区域奖励"),
    /**
     * 报单
     */
    TYPE_AWARD_BAODAN(401, "一级分销奖"),
    /**
     * 加金
     */
    TYPE_AWARD_JIAJIN(411, "二级分销奖"),
    /**
     * 复投
     */
    TYPE_AWARD_FUTOU(421, "零售奖"),
    /**
     * 挂单买入
     */
    TYPE_GD_SAL(501,"开拓奖"),
    /**
     * 挂单买入
     */
    TYPE_GD_BUY(502,"挂单买入"), 
    TYPE_PAY_QRCODE(601,"扫码支付"),
    /**
     * 交易手续费
     */
    TYPE_TRADE_FEE(702, "交易手续费"),
    /*
    * 美元点转入
    */
    TYPE_POINT_IN(801, "转入"),
    /*
    * 美元点转出
    */
    TYPE_POINT_OUT(802, "转出"),
    /**
     * 商城消费
     */
    TYPE_GOODS_BUY(902, "商城消费"),
    /**
     * 持币生息
     */
    TYPE_INTEREST(1001, "持币生息"),
    /**
     * 分享奖
     */
    TYPE_SHARED(1002, "分享奖"),
    /**
     * 奖金钱包释放
     */
    TYPE_REALESE_BONUS(1003, "奖金币钱包释放"),
    /**
     * 原始钱包释放
     */
    TYPE_REALESE_ORIGINAL(1004, "原始币钱包释放"),
    /**
     * 日息钱包释放
     */
    TYPE_REALESE_INTEREST(1005, "日息币钱包释放"),
    /**
     * 释放币钱包释放
     */
    TYPE_REALESE_RELEASE(1006, "释放币钱包释放")
    ;

    private int incomeType;
    private String remark;

    private IncomeType(int incomeType, String remark) {
        this.incomeType = incomeType;
        this.remark = remark;
    }

    public int getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(int incomeType) {
        this.incomeType = incomeType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static IncomeType getByType(int type) {
        for (IncomeType t : IncomeType.values()) {
            if (t.getIncomeType() == type) {
                return t;
            }
        }
        return IncomeType.TYPE_STATIC;
    }
}
