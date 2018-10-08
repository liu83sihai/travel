package com.dce.business.entity.district;

import com.dce.business.entity.user.UserDo;

public class District extends UserDo{
   
	//区域id
    private Integer districtId;

   //区域地址名称
    private String distrctName;

   //区域管理员id
    private Integer userId;

    //是否赋予区域
    private Integer districtStatus;

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public String getDistrctName() {
		return distrctName;
	}

	public void setDistrctName(String distrctName) {
		this.distrctName = distrctName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDistrictStatus() {
		return districtStatus;
	}

	public void setDistrictStatus(Integer districtStatus) {
		this.districtStatus = districtStatus;
	}
}