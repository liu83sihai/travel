package com.dce.business.entity.goods;

import java.util.Date;

public class CTUserAddressDo {

	  private Integer addressId;
	  private Integer userId;
	  private String userName ;
	  private String userPhone ;
	  private String userTel ;
	  private Integer areaId1 ;
	  private Integer areaId2 ;
	  private Integer areaId3 ;
	  private Integer communityId ;
	  private String address ;
	  private String postCode ;
	  private Integer isDefault ;//DEFAULT '0',
	  private Integer addressFlag ;// DEFAULT '1',
	  private Date createTime ;
	  private Date updateTime ;
	  
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public Integer getAreaId1() {
		return areaId1;
	}
	public void setAreaId1(Integer areaId1) {
		this.areaId1 = areaId1;
	}
	public Integer getAreaId2() {
		return areaId2;
	}
	public void setAreaId2(Integer areaId2) {
		this.areaId2 = areaId2;
	}
	public Integer getAreaId3() {
		return areaId3;
	}
	public void setAreaId3(Integer areaId3) {
		this.areaId3 = areaId3;
	}
	public Integer getCommunityId() {
		return communityId;
	}
	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getAddressFlag() {
		return addressFlag;
	}
	public void setAddressFlag(Integer addressFlag) {
		this.addressFlag = addressFlag;
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
	  
	  
}
