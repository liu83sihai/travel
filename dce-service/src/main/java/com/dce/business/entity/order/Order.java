package com.dce.business.entity.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.dce.business.common.enums.AccountType;
import com.dce.business.common.result.Result;

public class Order {

	private Integer orderid; // 订单主键id

	private String ordercode; // 订单编号

	private Integer userid; // 用户id

	private Integer qty; // 商品总数量

	private BigDecimal totalprice; // 订单总金额

	private BigDecimal goodsprice; // 商品金额

	private BigDecimal giftAmount; // 赠品金额
	
	private BigDecimal salqty; //赠品数量

	private String createtime; // 订单创建之间

	private String orderstatus; // 订单状态（1已发货0未发货）

	private String paystatus; // 付款状态（1已付0待付）

	private String paytime; // 支付时间

	private String ordertype; // 订单支付方式（1微信2支付宝 3 银行卡）

	private Integer addressid; // 收获地址表id，从该表中获取收货人的信息

	private Integer alipayStatus; // 支付状态：0支付失败1支付成功2未确定状态

	private List<OrderDetail> orderDetailList; // 订单商品明细

	private List<OrderDetail> awardDetailLst; // 订单奖励明细

	private String remark; // 商品详情

	private String trueName; // 客户姓名

	private String address; // 收货地址

	private String phone; // 手机号码

	private String recaddress;

	private String awardStatus;

	private String awardRemark;

	private Long matchorderid;

	private String accounttype;

	private Long goodsid;
	
	private BigDecimal profit; //订单利润

	//订单支付明细
	private List<OrderPayDetail> payDetailList;

	private BigDecimal cashAmt = BigDecimal.ZERO;

	private BigDecimal postage; //邮费
	
	private String goodsFlag; // 1 旅游卡 2 积分商品 3 会员商品 4 旅游路线 5 代理商保证金

	private String shopCatId1; //  1 爆款商品, 2 常规商品
	
	public String getShopCatId1() {
		return shopCatId1;
	}

	public void setShopCatId1(String shopCatId1) {
		this.shopCatId1 = shopCatId1;
	}

	public String getGoodsFlag() {
		return goodsFlag;
	}

	public void setGoodsFlag(String goodsFlag) {
		this.goodsFlag = goodsFlag;
	}

	public BigDecimal getPostage() {
		return postage;
	}

	public void setPostage(BigDecimal postage) {
		this.postage = postage;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAwardStatus() {
		return awardStatus;
	}

	public void setAwardStatus(String awardStatus) {
		this.awardStatus = awardStatus;
	}

	public String getAwardRemark() {
		return awardRemark;
	}

	public void setAwardRemark(String awardRemark) {
		this.awardRemark = awardRemark;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public BigDecimal getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}

	public String getRecaddress() {
		return recaddress;
	}

	public void setRecaddress(String recaddress) {
		this.recaddress = recaddress;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}

	public String getPaytime() {
		return paytime;
	}

	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public Integer getAddressid() {
		return addressid;
	}

	public void setAddressid(Integer addressid) {
		this.addressid = addressid;
	}

	public Integer getAlipayStatus() {
		return alipayStatus;
	}

	public void setAlipayStatus(Integer alipayStatus) {
		this.alipayStatus = alipayStatus;
	}

	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public List<OrderDetail> getAwardDetailLst() {
		return awardDetailLst;
	}

	public void setAwardDetailLst(List<OrderDetail> awardDetailLst) {
		this.awardDetailLst = awardDetailLst;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getMatchorderid() {
		return matchorderid;
	}

	public void setMatchorderid(Long matchorderid) {
		this.matchorderid = matchorderid;
	}

	public BigDecimal getSalqty() {
		return salqty;
	}

	public void setSalqty(BigDecimal salqty) {
		this.salqty = salqty;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public Long getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(Long goodsid) {
		this.goodsid = goodsid;
	}

	public BigDecimal getGoodsprice() {
		return goodsprice;
	}

	public void setGoodsprice(BigDecimal goodsprice) {
		this.goodsprice = goodsprice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(BigDecimal giftAmount) {
		this.giftAmount = giftAmount;
	}

	
	public void setPayDetailList(List<OrderPayDetail> payLst) {
		this.payDetailList = payLst;		
	}
	
	public List<OrderPayDetail> getPayDetailList() {
		return payDetailList;
	}
	
	public void setCashAmt(BigDecimal cashAmt) {
		this.cashAmt = cashAmt;
	}

	public Result<String> checkPayAmt() {
		if(this.getTotalprice().compareTo(BigDecimal.ZERO)<=0) {
			return Result.failureResult("总付款金额不能小于等于0");
		}
		
		
		BigDecimal walletTravel = BigDecimal.ZERO; //积分
		
		// 循环遍历出商品信息，计算商品总价格和商品总数量
		for (OrderDetail orderDetail : this.orderDetailList) {
			//积分商品,累计积分
			if(2 == orderDetail.getGoodsFlag().intValue()){
				walletTravel = BigDecimal.valueOf(orderDetail.getPrice() * orderDetail.getQuantity()).add(walletTravel);
			}
		}
		
		BigDecimal walletTravel2 = BigDecimal.ZERO; //积分
		for(OrderPayDetail pDetail : payDetailList) {
			if(AccountType.wallet_travel.getAccountType().equals(pDetail.getAccountType())) {
				walletTravel2.add(pDetail.getPayAmt());
			}
		}
		if(walletTravel2.compareTo(walletTravel) != 0 ) {
			return Result.failureResult("积分商品需要用积分支付");
		}
		return Result.successResult("ok");
	}
	
	public void calOrderProfit() {
		Integer quantity = 0; // 商品总数量
		BigDecimal totalprice = new BigDecimal(0); // 订单总金额
		Integer salqty = 0; // 赠品数量
		BigDecimal profit = BigDecimal.ZERO; //订单利润
		BigDecimal postage = BigDecimal.ZERO; //邮费
		
		// 循环遍历出商品信息，计算商品总价格和商品总数量
		for (OrderDetail orderDetail : this.orderDetailList) {
			quantity += orderDetail.getQuantity(); // 商品总数量
			totalprice = BigDecimal.valueOf(orderDetail.getPrice() * (orderDetail.getQuantity())).add(totalprice); // 商品总金额
			BigDecimal oneGoodsProfit = orderDetail.getProfit().multiply(new BigDecimal(orderDetail.getQuantity()));
			profit = profit.add(oneGoodsProfit);
			postage = postage.add(orderDetail.getPostage().multiply(new BigDecimal(orderDetail.getQuantity())));
		}
		
		
		
		this.profit = profit;
		this.qty = quantity;
		this.goodsprice = totalprice;
		this.totalprice = totalprice.add(postage);
		this.postage = postage;
				
	}
	
	public BigDecimal getCashAmt() {
		return this.cashAmt;
	}

	public void calNonCashAmt() {
		
		//如果非积分商品，计算规则
		if(!"2".equals(this.goodsFlag) ) {
			if(null == this.payDetailList||this.payDetailList.size()<1) {
				cashAmt = this.totalprice;
				return;
			}
			
			OrderPayDetail pay = payDetailList.get(0);
			BigDecimal tmpPrice = pay.getPayAmt(); 
			//邮费必须是现金
			if(tmpPrice.compareTo(this.totalprice)>0) {
				this.cashAmt = this.postage;
				pay.setPayAmt(this.totalprice.subtract(this.postage));
			}else {
				this.cashAmt = this.goodsprice.subtract(tmpPrice);
				this.cashAmt = cashAmt.add(this.postage);
			}
		
		//如果是积分商品，支付方式 现金支付邮费， 抵扣积分
		}else{
			this.payDetailList = new ArrayList<OrderPayDetail>();
			OrderPayDetail pay = new OrderPayDetail();
			this.payDetailList.add(pay);
			pay.setPayAmt(this.goodsprice);
			this.cashAmt = this.postage;
			
			if("2".equals(shopCatId1)) {
				pay.setAccountType(AccountType.wallet_travel.getAccountType());
			}
			
			if("1".equals(shopCatId1)) {
				pay.setAccountType(AccountType.wallet_goods.getAccountType());
			}
		}	
		
		
	}

	@Override
	public String toString() {
		return "Order [orderid=" + orderid + ", ordercode=" + ordercode + ", userid=" + userid + ", qty=" + qty
				+ ", totalprice=" + totalprice + ", goodsprice=" + goodsprice + ", giftAmount=" + giftAmount
				+ ", salqty=" + salqty + ", createtime=" + createtime + ", orderstatus=" + orderstatus + ", paystatus="
				+ paystatus + ", paytime=" + paytime + ", ordertype=" + ordertype + ", addressid=" + addressid
				+ ", alipayStatus=" + alipayStatus + ", orderDetailList=" + orderDetailList + ", awardDetailLst="
				+ awardDetailLst + ", remark=" + remark + ", trueName=" + trueName + ", address=" + address + ", phone="
				+ phone + ", recaddress=" + recaddress + ", awardStatus=" + awardStatus + ", awardRemark=" + awardRemark
				+ ", matchorderid=" + matchorderid + ", accounttype=" + accounttype + ", goodsid=" + goodsid + "]";
	}

	

}