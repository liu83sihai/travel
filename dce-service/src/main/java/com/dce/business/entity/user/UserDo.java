package com.dce.business.entity.user;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 会员管理实体类
 * 
 * @author XEY
 *
 */
public class UserDo {
	// 会员管理主键（用）
	private Integer id;

	// 登录用户名或昵称
	@NotBlank(message = "登录用户名手机号码为空")
	private String userName;

	// 证件姓名
	// @NotBlank(message = "姓名不能为空")
	private String trueName;

	private String email;

	// 手机号
	private String mobile;

	// 登录密码
	@NotBlank(message = "密码不能为空")
	private String userPassword;

	@NotBlank(message = "支付密码不能为空")
	private String twoPassword;

	private Integer userFace;

	private BigDecimal balanceIntegral;

	// 证件性别
	private Integer sex;

	private String displaySex;

	public String getDisplaySex() {
		displaySex = null;
		if (this.getSex() == 0) {
			displaySex = "未认证";
		} else if (this.getSex() == 1) {
			displaySex = "男";
		} else if (this.getSex() == 2) {
			displaySex = "女";
		}
		return displaySex;
	}

	public void setDisplaySex(String displaySex) {
		this.displaySex = displaySex;
	}

	private Long loginTimes;

	private Long lastLoginTime;

	private String lastLoginIp;

	private String regIp;

	// 注册时间
	private Long regTime;

	// 会员禁用，
	private Byte status;

	private String displayStatus;

	public String getDisplayStatus() {
		displayStatus = null;
		if (this.getIsActivated() == null) {
			return "空";
		}
		if (this.getStatus().intValue() == 0) {
			displayStatus = "使用中";
		} else if (this.getStatus().intValue() == 1) {
			displayStatus = "已冻结";
		}
		return displayStatus;
	}

	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}

	private BigDecimal balanceBonus;

	private BigDecimal balanceRepeat;

	private BigDecimal balanceCash;

	private BigDecimal balanceDongjie;

	private BigDecimal balanceShopping;

	private BigDecimal forzenShopping;

	private BigDecimal totalBonus;

	private BigDecimal totalRepeat;

	private BigDecimal totalCash;

	private BigDecimal totalIntegral;

	private BigDecimal totalShopping;

	// 用户推荐人ID
	private Integer refereeid;

	// 上级id
	private Integer parentid;

	// 认证状态
	private Integer certification;

	// 报单金额
	private BigDecimal baodan_amount;

	// 推荐人用户名(无用）
	// @NotBlank(message = "推荐人不能为空")
	private String refereeUserName;

	// 推荐人的手机号
	//@NotBlank(message = "推荐人手机号不能为空")
	private String refereeUserMobile;

	// 接点人用户名
	// @NotBlank(message = "接点人不能为空")
	private String parentUserName;

	private Integer refereeNumber;

	private Byte refereeStatus;

	private Integer sonNumber;

	private Byte isServerCenter;

	private Integer myServerCenter;

	private Integer userscore;

	private Integer userType;

	private Byte userGroup;

	// 用户等级
	private Byte userLevel;
	private String displayUserLevel;
	
	//身份证正反面
	private String idcardFront;
	private String idcardBack;
	private String userImage;

	public static String   getUserLevelName(int userLevel) {
		if (userLevel == 0) {
			return "普通会员";
		} else if (userLevel == 1) {
			return "VIP";
		} else if (userLevel == 2) {
			return "商家";
		} else if (userLevel == 3) {
			return "社区合伙人";
		} else if (userLevel == 4) {
			return "城市合伙人";
		}else if (userLevel == 5) {
			return "省级合伙人";
		}else if (userLevel == 6) {
			return "股东";
		}else if (userLevel == 7) {
			return "总监";
		}else if (userLevel == 8) {
			return "董事";
		}
		return "";
	}
	
	public String getDisplayUserLevel() {
		displayUserLevel = null;
//		if (this.getIsActivated() == null) {
//			return "空";
//		}
		if (this.getUserLevel().intValue() == 0) {
			displayUserLevel = "普通会员";
		} else if (this.getUserLevel().intValue() == 1) {
			displayUserLevel = "VIP";
		} else if (this.getUserLevel().intValue() == 2) {
			displayUserLevel = "商家";
		} else if (this.getUserLevel().intValue() == 3) {
			displayUserLevel = "社区合伙人";
		} else if (this.getUserLevel().intValue() == 4) {
			displayUserLevel = "城市合伙人";
		}else if (this.getUserLevel().intValue() == 5) {
			displayUserLevel = "省级合伙人";
		}else if (this.getUserLevel().intValue() == 6) {
			displayUserLevel = "股东";
		}
		return displayUserLevel;
	}

	public void setDisplayUserLevel(String displayUserLevel) {
		this.displayUserLevel = displayUserLevel;
	}

	private Byte userPost;

	private BigDecimal regMoney;

	private BigDecimal yfMoney;

	private BigDecimal fdMoney;

	private BigDecimal totalPerformance;

	private BigDecimal touchedPerformance;

	private Long activationTime;

	private Integer userQq;

	private String userWechat;

	private String openid;

	private String register;

	private String expressPassword;

	private BigDecimal totalDividends;

	private Integer dividendsDays;

	private Integer country;

	private Integer province;

	private Integer city;

	private Integer darea;

	private BigDecimal balanceEt;

	// 身份证号码
	private String idnumber;

	// 身份证照片url
	private String identity;

	// 开卡行
	private String banktype;

	private String bankUserName;

	// 卡号
	private String banknumber;

	private String bankContent;

	private String question1;

	private String question2;

	private String question3;

	private String answer1;

	private String answer2;

	private String answer3;

	private Integer groupid;

	private String ticket;

	private BigDecimal balanceGouwu;

	private Byte qrcodeStatus;

	private Long bonusTime;

	private Byte isEmpty;

	private BigDecimal balanceHeart;

	private Integer kuoLevel;

	private Byte backfillStatus;

	private BigDecimal backfillMoney;

	private BigDecimal backfillSheng;

	private Integer guadanNum;

	private BigDecimal allstatic;

	private Integer dis;

	private Long releaseTime;

	private Byte isshop;

	private Byte isimport;

	// @NotNull(message = "请选择左右区")
	private Byte pos;

	private Byte isout;

	private String touchDistance;

	private String userLevelName;

	private BigDecimal ableTouchQty = BigDecimal.ZERO; // 本次可量碰数量

	private String ethAccount; // 以太坊账户

	/**
	 * 用户不允许的动作权限：具体看{@IncomeType}， 空值表示不限制 存储以逗号分隔：601,702
	 */
	private String undoOpts;

	/**
	 * 用户转让的方向:up,down 存储以逗号分隔: up,down 空值不受限制
	 */
	private String tranDirect;

	// 用户是否已激活（状态），
	private Integer isActivated;
	private String displayIsActivated;
	
	//用户付款码
	private String payCode;

	public String getDisplayIsActivated() {
		displayIsActivated = null;
		if (this.getIsActivated() == null) {
			return "未激活(空)";
		}
		if (this.getIsActivated().intValue() == 0) {
			displayIsActivated = "未激活";
		} else if (this.getIsActivated().intValue() == 1) {
			displayIsActivated = "激活";
		}
		return displayIsActivated;
	}

	public void setDisplayIsActivated(String displayIsActivated) {
		this.displayIsActivated = displayIsActivated;
	}

	// 用户所属区域
	private String district;

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}


	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getCertification() {
		return certification;
	}

	public void setCertification(Integer certification) {
		this.certification = certification;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getUndoOpts() {
		return undoOpts;
	}

	public void setUndoOpts(String undoOpts) {
		this.undoOpts = undoOpts;
	}

	public String getTranDirect() {
		return tranDirect;
	}

	public void setTranDirect(String tranDirect) {
		this.tranDirect = tranDirect;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getTwoPassword() {
		return twoPassword;
	}

	public void setTwoPassword(String twoPassword) {
		this.twoPassword = twoPassword;
	}

	public Integer getUserFace() {
		return userFace;
	}

	public void setUserFace(Integer userFace) {
		this.userFace = userFace;
	}

	public BigDecimal getBalanceIntegral() {
		return balanceIntegral;
	}

	public void setBalanceIntegral(BigDecimal balanceIntegral) {
		this.balanceIntegral = balanceIntegral;
	}



	public Long getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Long loginTimes) {
		this.loginTimes = loginTimes;
	}

	public Long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public Long getRegTime() {
		return regTime;
	}

	public void setRegTime(Long regTime) {
		this.regTime = regTime;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public BigDecimal getBalanceBonus() {
		return balanceBonus;
	}

	public void setBalanceBonus(BigDecimal balanceBonus) {
		this.balanceBonus = balanceBonus;
	}

	public BigDecimal getBalanceRepeat() {
		return balanceRepeat;
	}

	public void setBalanceRepeat(BigDecimal balanceRepeat) {
		this.balanceRepeat = balanceRepeat;
	}

	public BigDecimal getBalanceCash() {
		return balanceCash;
	}

	public void setBalanceCash(BigDecimal balanceCash) {
		this.balanceCash = balanceCash;
	}

	public BigDecimal getBalanceDongjie() {
		return balanceDongjie;
	}

	public void setBalanceDongjie(BigDecimal balanceDongjie) {
		this.balanceDongjie = balanceDongjie;
	}

	public BigDecimal getBalanceShopping() {
		return balanceShopping;
	}

	public void setBalanceShopping(BigDecimal balanceShopping) {
		this.balanceShopping = balanceShopping;
	}

	public BigDecimal getForzenShopping() {
		return forzenShopping;
	}

	public void setForzenShopping(BigDecimal forzenShopping) {
		this.forzenShopping = forzenShopping;
	}

	public BigDecimal getTotalBonus() {
		return totalBonus;
	}

	public void setTotalBonus(BigDecimal totalBonus) {
		this.totalBonus = totalBonus;
	}

	public BigDecimal getTotalRepeat() {
		return totalRepeat;
	}

	public void setTotalRepeat(BigDecimal totalRepeat) {
		this.totalRepeat = totalRepeat;
	}

	public BigDecimal getTotalCash() {
		return totalCash;
	}

	public void setTotalCash(BigDecimal totalCash) {
		this.totalCash = totalCash;
	}

	public BigDecimal getTotalIntegral() {
		return totalIntegral;
	}

	public void setTotalIntegral(BigDecimal totalIntegral) {
		this.totalIntegral = totalIntegral;
	}

	public BigDecimal getTotalShopping() {
		return totalShopping;
	}

	public void setTotalShopping(BigDecimal totalShopping) {
		this.totalShopping = totalShopping;
	}

	public Integer getRefereeid() {
		return refereeid;
	}

	public void setRefereeid(Integer refereeid) {
		this.refereeid = refereeid;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public Integer getRefereeNumber() {
		return refereeNumber;
	}

	public void setRefereeNumber(Integer refereeNumber) {
		this.refereeNumber = refereeNumber;
	}

	public Byte getRefereeStatus() {
		return refereeStatus;
	}

	public void setRefereeStatus(Byte refereeStatus) {
		this.refereeStatus = refereeStatus;
	}

	public Integer getSonNumber() {
		return sonNumber;
	}

	public void setSonNumber(Integer sonNumber) {
		this.sonNumber = sonNumber;
	}

	public Byte getIsServerCenter() {
		return isServerCenter;
	}

	public void setIsServerCenter(Byte isServerCenter) {
		this.isServerCenter = isServerCenter;
	}

	public Integer getMyServerCenter() {
		return myServerCenter;
	}

	public void setMyServerCenter(Integer myServerCenter) {
		this.myServerCenter = myServerCenter;
	}

	public Integer getUserscore() {
		return userscore;
	}

	public void setUserscore(Integer userscore) {
		this.userscore = userscore;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Byte getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(Byte userGroup) {
		this.userGroup = userGroup;
	}

	public Byte getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Byte userLevel) {
		this.userLevel = userLevel;
	}

	public Byte getUserPost() {
		return userPost;
	}

	public void setUserPost(Byte userPost) {
		this.userPost = userPost;
	}

	public BigDecimal getRegMoney() {
		return regMoney;
	}

	public void setRegMoney(BigDecimal regMoney) {
		this.regMoney = regMoney;
	}

	public BigDecimal getYfMoney() {
		return yfMoney;
	}

	public void setYfMoney(BigDecimal yfMoney) {
		this.yfMoney = yfMoney;
	}

	public BigDecimal getFdMoney() {
		return fdMoney;
	}

	public void setFdMoney(BigDecimal fdMoney) {
		this.fdMoney = fdMoney;
	}

	public BigDecimal getTotalPerformance() {
		return totalPerformance;
	}

	public void setTotalPerformance(BigDecimal totalPerformance) {
		this.totalPerformance = totalPerformance;
	}

	public Long getActivationTime() {
		return activationTime;
	}

	public void setActivationTime(Long activationTime) {
		this.activationTime = activationTime;
	}

	public Integer getUserQq() {
		return userQq;
	}

	public void setUserQq(Integer userQq) {
		this.userQq = userQq;
	}

	public String getUserWechat() {
		return userWechat;
	}

	public void setUserWechat(String userWechat) {
		this.userWechat = userWechat;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public String getExpressPassword() {
		return expressPassword;
	}

	public void setExpressPassword(String expressPassword) {
		this.expressPassword = expressPassword;
	}

	public BigDecimal getTotalDividends() {
		return totalDividends;
	}

	public void setTotalDividends(BigDecimal totalDividends) {
		this.totalDividends = totalDividends;
	}

	public Integer getDividendsDays() {
		return dividendsDays;
	}

	public void setDividendsDays(Integer dividendsDays) {
		this.dividendsDays = dividendsDays;
	}

	public Integer getCountry() {
		return country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getDarea() {
		return darea;
	}

	public void setDarea(Integer darea) {
		this.darea = darea;
	}

	public BigDecimal getBalanceEt() {
		return balanceEt;
	}

	public void setBalanceEt(BigDecimal balanceEt) {
		this.balanceEt = balanceEt;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getBanktype() {
		return banktype;
	}

	public void setBanktype(String banktype) {
		this.banktype = banktype;
	}

	public String getBankUserName() {
		return bankUserName;
	}

	public void setBankUserName(String bankUserName) {
		this.bankUserName = bankUserName;
	}

	public String getBanknumber() {
		return banknumber;
	}

	public void setBanknumber(String banknumber) {
		this.banknumber = banknumber;
	}

	public String getBankContent() {
		return bankContent;
	}

	public void setBankContent(String bankContent) {
		this.bankContent = bankContent;
	}

	public String getQuestion1() {
		return question1;
	}

	public void setQuestion1(String question1) {
		this.question1 = question1;
	}

	public String getQuestion2() {
		return question2;
	}

	public void setQuestion2(String question2) {
		this.question2 = question2;
	}

	public String getQuestion3() {
		return question3;
	}

	public void setQuestion3(String question3) {
		this.question3 = question3;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public BigDecimal getBalanceGouwu() {
		return balanceGouwu;
	}

	public void setBalanceGouwu(BigDecimal balanceGouwu) {
		this.balanceGouwu = balanceGouwu;
	}

	public Byte getQrcodeStatus() {
		return qrcodeStatus;
	}

	public void setQrcodeStatus(Byte qrcodeStatus) {
		this.qrcodeStatus = qrcodeStatus;
	}

	public Long getBonusTime() {
		return bonusTime;
	}

	public void setBonusTime(Long bonusTime) {
		this.bonusTime = bonusTime;
	}

	public Byte getIsEmpty() {
		return isEmpty;
	}

	public void setIsEmpty(Byte isEmpty) {
		this.isEmpty = isEmpty;
	}

	public BigDecimal getBalanceHeart() {
		return balanceHeart;
	}

	public void setBalanceHeart(BigDecimal balanceHeart) {
		this.balanceHeart = balanceHeart;
	}

	public Integer getKuoLevel() {
		return kuoLevel;
	}

	public void setKuoLevel(Integer kuoLevel) {
		this.kuoLevel = kuoLevel;
	}

	public Byte getBackfillStatus() {
		return backfillStatus;
	}

	public void setBackfillStatus(Byte backfillStatus) {
		this.backfillStatus = backfillStatus;
	}

	public BigDecimal getBackfillMoney() {
		return backfillMoney;
	}

	public void setBackfillMoney(BigDecimal backfillMoney) {
		this.backfillMoney = backfillMoney;
	}

	public BigDecimal getBackfillSheng() {
		return backfillSheng;
	}

	public void setBackfillSheng(BigDecimal backfillSheng) {
		this.backfillSheng = backfillSheng;
	}

	public Integer getGuadanNum() {
		return guadanNum;
	}

	public void setGuadanNum(Integer guadanNum) {
		this.guadanNum = guadanNum;
	}

	public BigDecimal getAllstatic() {
		return allstatic;
	}

	public void setAllstatic(BigDecimal allstatic) {
		this.allstatic = allstatic;
	}

	public Integer getDis() {
		return dis;
	}

	public void setDis(Integer dis) {
		this.dis = dis;
	}

	public Long getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Long releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Byte getIsshop() {
		return isshop;
	}

	public void setIsshop(Byte isshop) {
		this.isshop = isshop;
	}

	public Byte getIsimport() {
		return isimport;
	}

	public void setIsimport(Byte isimport) {
		this.isimport = isimport;
	}

	public Byte getPos() {
		return pos;
	}

	public void setPos(Byte pos) {
		this.pos = pos;
	}

	public Byte getIsout() {
		return isout;
	}

	public void setIsout(Byte isout) {
		this.isout = isout;
	}

	public String getTouchDistance() {
		return touchDistance;
	}

	public void setTouchDistance(String touchDistance) {
		this.touchDistance = touchDistance;
	}

	public String getRefereeUserName() {
		return refereeUserName;
	}

	public void setRefereeUserName(String refereeUserName) {
		this.refereeUserName = refereeUserName;
	}

	public String getParentUserName() {
		return parentUserName;
	}

	public void setParentUserName(String parentUserName) {
		this.parentUserName = parentUserName;
	}

	public BigDecimal getTouchedPerformance() {
		return touchedPerformance;
	}

	public void setTouchedPerformance(BigDecimal touchedPerformance) {
		this.touchedPerformance = touchedPerformance;
	}

	public String getUserLevelName() {
		return userLevelName;
	}

	public void setUserLevelName(String userLevelName) {
		this.userLevelName = userLevelName;
	}

	public BigDecimal getAbleTouchQty() {
		return ableTouchQty;
	}

	public void setAbleTouchQty(BigDecimal ableTouchQty) {
		this.ableTouchQty = ableTouchQty;
	}

	public String getEthAccount() {
		return ethAccount;
	}

	public void setEthAccount(String ethAccount) {
		this.ethAccount = ethAccount;
	}

	public BigDecimal getBaodan_amount() {
		return baodan_amount;
	}

	public void setBaodan_amount(BigDecimal baodan_amount) {
		this.baodan_amount = baodan_amount;
	}

	public Integer getIsActivated() {
		return isActivated;
	}

	public void setIsActivated(Integer isActivated) {
		this.isActivated = isActivated;
	}

	
	public String getIdcardFront() {
		return idcardFront;
	}

	public void setIdcardFront(String idcardFront) {
		this.idcardFront = idcardFront;
	}

	public String getIdcardBack() {
		return idcardBack;
	}

	public void setIdcardBack(String idcardBack) {
		this.idcardBack = idcardBack;
	}

	/**
	 * 判断是否激活
	 * 
	 * @return
	 */
	public boolean isActivated() {
		if (userLevel != null && userLevel.intValue() > 0) {
			return true;
		}

		return false;
	}

	public String getRefereeUserMobile() {
		return refereeUserMobile;
	}

	public void setRefereeUserMobile(String refereeUserMobile) {
		this.refereeUserMobile = refereeUserMobile;
	}
	
	

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	
	

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	@Override
	public String toString() {
		return "UserDo [id=" + id + ", userName=" + userName + ", trueName=" + trueName + ", email=" + email
				+ ", mobile=" + mobile + ", userPassword=" + userPassword + ", twoPassword=" + twoPassword
				+ ", userFace=" + userFace + ", balanceIntegral=" + balanceIntegral + ", sex=" + sex + ", displaySex="
				+ displaySex + ", loginTimes=" + loginTimes + ", lastLoginTime=" + lastLoginTime + ", lastLoginIp="
				+ lastLoginIp + ", regIp=" + regIp + ", regTime=" + regTime + ", status=" + status + ", displayStatus="
				+ displayStatus + ", balanceBonus=" + balanceBonus + ", balanceRepeat=" + balanceRepeat
				+ ", balanceCash=" + balanceCash + ", balanceDongjie=" + balanceDongjie + ", balanceShopping="
				+ balanceShopping + ", forzenShopping=" + forzenShopping + ", totalBonus=" + totalBonus
				+ ", totalRepeat=" + totalRepeat + ", totalCash=" + totalCash + ", totalIntegral=" + totalIntegral
				+ ", totalShopping=" + totalShopping + ", refereeid=" + refereeid + ", parentid=" + parentid
				+ ", certification=" + certification + ", baodan_amount=" + baodan_amount + ", refereeUserName="
				+ refereeUserName + ", refereeUserMobile=" + refereeUserMobile + ", parentUserName=" + parentUserName
				+ ", refereeNumber=" + refereeNumber + ", refereeStatus=" + refereeStatus + ", sonNumber=" + sonNumber
				+ ", isServerCenter=" + isServerCenter + ", myServerCenter=" + myServerCenter + ", userscore="
				+ userscore + ", userType=" + userType + ", userGroup=" + userGroup + ", userLevel=" + userLevel
				+ ", displayUserLevel=" + displayUserLevel + ", userPost=" + userPost + ", regMoney=" + regMoney
				+ ", yfMoney=" + yfMoney + ", fdMoney=" + fdMoney + ", totalPerformance=" + totalPerformance
				+ ", touchedPerformance=" + touchedPerformance + ", activationTime=" + activationTime + ", userQq="
				+ userQq + ", userWechat=" + userWechat + ", openid=" + openid + ", register=" + register
				+ ", expressPassword=" + expressPassword + ", totalDividends=" + totalDividends + ", dividendsDays="
				+ dividendsDays + ", country=" + country + ", province=" + province + ", city=" + city + ", darea="
				+ darea + ", balanceEt=" + balanceEt + ", idnumber=" + idnumber + ", identity=" + identity
				+ ", banktype=" + banktype + ", bankUserName=" + bankUserName + ", banknumber=" + banknumber
				+ ", bankContent=" + bankContent + ", question1=" + question1 + ", question2=" + question2
				+ ", question3=" + question3 + ", answer1=" + answer1 + ", answer2=" + answer2 + ", answer3=" + answer3
				+ ", groupid=" + groupid + ", ticket=" + ticket + ", balanceGouwu=" + balanceGouwu + ", qrcodeStatus="
				+ qrcodeStatus + ", bonusTime=" + bonusTime + ", isEmpty=" + isEmpty + ", balanceHeart=" + balanceHeart
				+ ", kuoLevel=" + kuoLevel + ", backfillStatus=" + backfillStatus + ", backfillMoney=" + backfillMoney
				+ ", backfillSheng=" + backfillSheng + ", guadanNum=" + guadanNum + ", allstatic=" + allstatic
				+ ", dis=" + dis + ", releaseTime=" + releaseTime + ", isshop=" + isshop + ", isimport=" + isimport
				+ ", pos=" + pos + ", isout=" + isout + ", touchDistance=" + touchDistance + ", userLevelName="
				+ userLevelName + ", ableTouchQty=" + ableTouchQty + ", ethAccount=" + ethAccount + ", undoOpts="
				+ undoOpts + ", tranDirect=" + tranDirect + ", isActivated=" + isActivated + ", displayIsActivated="
				+ displayIsActivated + ", district=" + district + "]";
	}
	
}
