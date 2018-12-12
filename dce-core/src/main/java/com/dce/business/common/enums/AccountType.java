package com.dce.business.common.enums;

/** 
 * 账户类型
 * @author parudy
 * @date 2018年3月26日 
 * @version v1.0
 */
public enum AccountType {
	/**现金账户*/
	wallet_money("wallet_money", "现金账户"),
	/**旅游*/
	wallet_travel("wallet_travel", "积分"),
	/**日息钱包*/
	wallet_goods("wallet_goods", "抵用券"),
	/**赠送活动：沙龙*/
	wallet_active("wallet_active", "旅游卡"),
	/*银行卡支付*/
	wallet_bank("bank", "银行卡"), wallet_ALI("Ali", "支付宝"), wallet_WX("WX", "微信");

    private String accountType;
    private String remark;

    AccountType(String accountType, String remark) {
        this.accountType = accountType;
        this.remark = remark;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static AccountType getAccountType(String accountType) {
        for (AccountType type : AccountType.values()) {
            if (type.getAccountType().equals(accountType)) {
                return type;
            }
        }
        return null;
    }
}
