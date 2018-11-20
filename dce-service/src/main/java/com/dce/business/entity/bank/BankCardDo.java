package com.dce.business.entity.bank;

import java.io.Serializable;
import java.util.Date;

public class BankCardDo implements Serializable {
	private static final long serialVersionUID = 5614019183528963293L;
	private Long id;// 主键
	private String cardUserName;// 用户名
	private String bankName;// 开户行
	private String branchBankName;// 支行
	private String cardNo;// 卡号
	private String cardNoMantissa;// 卡号

	private Integer cardMode;// 卡类，根据系统提供的卡类项进行多个设置
	private Integer cardStatus;// 卡状态( 默认 2申请中) 1 已绑定 2 申请中 3 失败
	
	private Date auditTime;// 审核时间
	private Long userId;// 用户id

	private Long auditUser;// 审核人
	private String remark;// 审核意见
	private String openBankCode;// 开户行编码
	private String bankCode;//标准银行编码
	private String provinceId;// 省份代号
	private String cityId;// 地区代号
	private String region; //区代号
	private Integer isDefault;// 是否为默认的取现银行卡,(1是,0不是)
	private Integer isExpress;//快捷充值卡标示(1是,0不是)
	
	private String province;
	private String city;
	private String regionStr;

	private Date createTime;// 申请时间
	private Date updateTime;// 更换时间
	
	private String bankMobile;//银行预留手机号
	
	
	private String cardType;//卡属性(0对私,1对公)
	private String payChannelType;//渠道类型 0:块钱  1:通联  9:后台管理系统
	
	private String cardNoShow;//手机展示用
	private String idNo;
	private String realName;
	private String mobile;
	
	private String branchOrgCode;
	
	public String getBranchOrgCode() {
		return branchOrgCode;
	}

	public void setBranchOrgCode(String branchOrgCode) {
		this.branchOrgCode = branchOrgCode;
	}

	/**
	 * 银行卡类型  个人卡  对公卡
	 * @author liminglmf
	 */
	public enum CardTypeEnum {
		PRIVATE("0", "对私"), PUBLIC("1", "对公");
        private String value;
        private String remark;

        private CardTypeEnum(String value, String remark) {
            this.value = value;
            this.remark = remark;
        }
        public String value() {
            return value;
        }
        public String remark() {
            return remark;
        }
	}
	/**
	 * 绑卡渠道枚举
	 * @author liminglmf
	 */
	public enum PayChannelTypeEnum {
		QUICK("0", "快钱"), ALLIN("1", "通联"), CBT("6", "彩白条"), CYJ("7", "彩易借");
        private String value;
        private String remark;

        private PayChannelTypeEnum(String value, String remark) {
            this.value = value;
            this.remark = remark;
        }
        public String value() {
            return value;
        }
        public String remark() {
            return remark;
        }
	}
	
	public static void main(String[] args) {
		System.out.println(PayChannelTypeEnum.QUICK.value);
		System.out.println(PayChannelTypeEnum.QUICK.remark);
	}
	
	public String getBankMobile() {
		return bankMobile;
	}

	public void setBankMobile(String bankMobile) {
		this.bankMobile = bankMobile;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegionStr() {
		return regionStr;
	}

	public void setRegionStr(String regionStr) {
		this.regionStr = regionStr;
	}

	public String getCardNoShow() {
		return cardNoShow;
	}
	
	public void setCardNoShow(String cardNoShow) {
		this.cardNoShow = cardNoShow;
	}
	
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardUserName() {
		return cardUserName;
	}

	public void setCardUserName(String cardUserName) {
		this.cardUserName = cardUserName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
//		if(cardNo!=null&&cardNo.length()>5){
//			this.setCardNoMantissa(cardNo.substring(cardNo.length()-4, cardNo.length()));
//		}
	}

	public Integer getCardMode() {
		return cardMode;
	}

	public void setCardMode(Integer cardMode) {
		this.cardMode = cardMode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(Integer cardStatus) {
		this.cardStatus = cardStatus;
	}


	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

    public Long getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(Long auditUser) {
		this.auditUser = auditUser;
	}

	public String getOpenBankCode() {
		return openBankCode;
	}

	public void setOpenBankCode(String openBankCode) {
		this.openBankCode = openBankCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	/** 
     * @return isExpress 
     */
    public Integer getIsExpress() {
        return isExpress;
    }

    /**
     * @param isExpress the isExpress to set
     */
    public void setIsExpress(Integer isExpress) {
        this.isExpress = isExpress;
    }

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getPayChannelType() {
		return payChannelType;
	}

	public void setPayChannelType(String payChannelType) {
		this.payChannelType = payChannelType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCardNoMantissa() {
	    if(cardNo!=null&&cardNo.length()>5){
	        return cardNo.substring(cardNo.length()-4, cardNo.length());
            //this.setCardNoMantissa(cardNo.substring(cardNo.length()-4, cardNo.length()));
        }
		return cardNoMantissa;
	}

	public void setCardNoMantissa(String cardNoMantissa) {
		this.cardNoMantissa = cardNoMantissa;
	}
 
    @Override
	public String toString() {
		return "BankCardDo [id=" + id + ", cardUserName=" + cardUserName + ", bankName=" + bankName + ", branchBankName=" + branchBankName + ", cardNo=" + cardNo + ", cardMode=" + cardMode
				+ ", cardStatus=" + cardStatus + ", auditTime=" + auditTime + ", userId=" + userId + ", auditUser=" + auditUser + ", remark=" + remark + ", openBankCode=" + openBankCode
				+ ", bankCode=" + bankCode + ", provinceId=" + provinceId + ", cityId=" + cityId + ", region=" + region + ", isDefault=" + isDefault + ", isExpress=" + isExpress + ", province="
				+ province + ", city=" + city + ", regionStr=" + regionStr + ", createTime=" + createTime + ", updateTime=" + updateTime + ", cardNoShow=" + cardNoShow + ", cardType=" + cardType
				+ ", payChannelType=" + payChannelType + ", idNo=" + idNo + ", realName=" + realName + ", mobile=" + mobile + "]";
	}

}
