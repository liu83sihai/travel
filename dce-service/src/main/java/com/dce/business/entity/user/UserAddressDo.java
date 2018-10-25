package com.dce.business.entity.user;

import java.util.Date;

/**
 * 收货地址实体类
 * @author XEY
 *
 */
public class UserAddressDo {
	// 收货地址id
    private Integer addressid;

    // 用户表id
    private Integer userid;

    // 收货姓名
    private String username;

    // 收货电话
    private String userphone;

    // 收货人所在的：省-市-区
    private String address;
    
    // 收货地址详情
    private String addressDetails;

    // 邮政编码
    private String postcode;
    
    // 备注
    private String remark;
    
    private String usertel;

    private Integer areaid1;

    private Integer areaid2;

    private Integer areaid3;

    private Integer communityid;

    private Byte isdefault;

    private Byte addressflag;

    private Date createtime;

    private Date updateTime;
    
    

	public String getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(String addressDetails) {
		this.addressDetails = addressDetails;
	}

	public Integer getAddressid() {
        return addressid;
    }

    public void setAddressid(Integer addressid) {
        this.addressid = addressid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getUsertel() {
        return usertel;
    }

    public void setUsertel(String usertel) {
        this.usertel = usertel;
    }

    public Integer getAreaid1() {
        return areaid1;
    }

    public void setAreaid1(Integer areaid1) {
        this.areaid1 = areaid1;
    }

    public Integer getAreaid2() {
        return areaid2;
    }

    public void setAreaid2(Integer areaid2) {
        this.areaid2 = areaid2;
    }

    public Integer getAreaid3() {
        return areaid3;
    }

    public void setAreaid3(Integer areaid3) {
        this.areaid3 = areaid3;
    }

    public Integer getCommunityid() {
        return communityid;
    }

    public void setCommunityid(Integer communityid) {
        this.communityid = communityid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Byte getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Byte isdefault) {
        this.isdefault = isdefault;
    }

    public Byte getAddressflag() {
        return addressflag;
    }

    public void setAddressflag(Byte addressflag) {
        this.addressflag = addressflag;
    }

    

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "UserAddressDo [addressid=" + addressid + ", userid=" + userid + ", username=" + username
				+ ", userphone=" + userphone + ", address=" + address + ", addressDetails=" + addressDetails
				+ ", postcode=" + postcode + ", remark=" + remark + ", usertel=" + usertel + ", areaid1=" + areaid1
				+ ", areaid2=" + areaid2 + ", areaid3=" + areaid3 + ", communityid=" + communityid + ", isdefault="
				+ isdefault + ", addressflag=" + addressflag + ", createtime=" + createtime + ", updateTime="
				+ updateTime + "]";
	}
}