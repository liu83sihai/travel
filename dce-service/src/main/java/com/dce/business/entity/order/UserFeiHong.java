package com.dce.business.entity.order;

import java.math.BigDecimal;
import java.util.Date;

public class UserFeiHong {

	private Integer userid; // 用户id
	private BigDecimal orderaward; //订单收益  购买卡金额*4
	private BigDecimal feihongamt;//累计分红金额
	private Date createtime; // 创建日期
	private Date updatetime; // 修改日期
	private Integer status; //是否有效 1 有效， 0 无效
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public BigDecimal getOrderaward() {
		return orderaward;
	}
	public void setOrderaward(BigDecimal orderaward) {
		this.orderaward = orderaward;
	}
	public BigDecimal getFeihongamt() {
		return feihongamt;
	}
	public void setFeihongamt(BigDecimal feihongamt) {
		this.feihongamt = feihongamt;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

}