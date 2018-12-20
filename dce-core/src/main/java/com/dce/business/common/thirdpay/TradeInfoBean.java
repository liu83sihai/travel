package com.dce.business.common.thirdpay;

public class TradeInfoBean {

	private String subject ; //商品名称 非空
	private String out_trade_no ; //平台(商户)订单号 非空
	private String currency;  //币种 ， (默认人民币CNY，可空)
	private String price; // 商品单价 非空
	private String quantity; // 商品数量  非空
	private String total_amount; //交易金额 (交易金额=(商品单价×商品数量)，非空
	private String payee_identity_type="1";// (1-卖家会员ID，2-卖家登入账号，可空)
	private String payee_identity; //卖家会员ID或登入账号 非空
	private String biz_no; //业务号 (收支明细的备注，对账用，可空)
	private String show_url ; // 商品展示URL (收银台页面上，商品展示的超链接，可空)
	private String notify_url;// 服务器异步通知地址, (快捷通主动通知商户网站里指定的URL http/https路径，当订单完成后会回调商户并告知订单状态，可空)
	private String ensure_amount;
	private String gold_coin;
	private String deposit_amount;
	private String deposit_no;
	private String trade_ext;  //交易扩展参数 可空
	private String royalty_info; // 分润账号集列表 (最多支持10个分润账号，可空) 
	
	
	public TradeInfoBean() {
		
	}
	
	public TradeInfoBean(String subject, String out_trade_no, String currency, String price, String quantity,
			String total_amount, String payee_identity_type, String payee_identity, String biz_no, String show_url,
			String notify_url, String ensure_amount, String gold_coin, String deposit_amount, String deposit_no,
			String trade_ext, String royalty_info) {
		this.subject = subject;
		this.out_trade_no = out_trade_no;
		this.currency = currency;
		this.price = price;
		this.quantity = quantity;
		this.total_amount = total_amount;
		this.payee_identity_type = payee_identity_type;
		this.payee_identity = payee_identity;
		this.biz_no = biz_no;
		this.show_url = show_url;
		this.notify_url = notify_url;
		this.ensure_amount = ensure_amount;
		this.gold_coin = gold_coin;
		this.deposit_amount = deposit_amount;
		this.deposit_no = deposit_no;
		this.trade_ext = trade_ext;
		this.royalty_info = royalty_info;
	}
	
	
	@Override
	public String toString() {
		return "TradeInfoBean [subject=" + subject + ", out_trade_no=" + out_trade_no + ", currency=" + currency
				+ ", price=" + price + ", quantity=" + quantity + ", total_amount=" + total_amount
				+ ", payee_identity_type=" + payee_identity_type + ", payee_identity=" + payee_identity + ", biz_no="
				+ biz_no + ", show_url=" + show_url + ", notify_url=" + notify_url + ", ensure_amount=" + ensure_amount
				+ ", gold_coin=" + gold_coin + ", deposit_amount=" + deposit_amount + ", deposit_no=" + deposit_no
				+ ", trade_ext=" + trade_ext + ", royalty_info=" + royalty_info + "]";
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getPayee_identity_type() {
		return payee_identity_type;
	}
	public void setPayee_identity_type(String payee_identity_type) {
		this.payee_identity_type = payee_identity_type;
	}
	public String getPayee_identity() {
		return payee_identity;
	}
	public void setPayee_identity(String payee_identity) {
		this.payee_identity = payee_identity;
	}
	public String getBiz_no() {
		return biz_no;
	}
	public void setBiz_no(String biz_no) {
		this.biz_no = biz_no;
	}
	public String getShow_url() {
		return show_url;
	}
	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getEnsure_amount() {
		return ensure_amount;
	}
	public void setEnsure_amount(String ensure_amount) {
		this.ensure_amount = ensure_amount;
	}
	public String getGold_coin() {
		return gold_coin;
	}
	public void setGold_coin(String gold_coin) {
		this.gold_coin = gold_coin;
	}
	public String getDeposit_amount() {
		return deposit_amount;
	}
	public void setDeposit_amount(String deposit_amount) {
		this.deposit_amount = deposit_amount;
	}
	public String getDeposit_no() {
		return deposit_no;
	}
	public void setDeposit_no(String deposit_no) {
		this.deposit_no = deposit_no;
	}
	public String getTrade_ext() {
		return trade_ext;
	}
	public void setTrade_ext(String trade_ext) {
		this.trade_ext = trade_ext;
	}
	public String getRoyalty_info() {
		return royalty_info;
	}
	public void setRoyalty_info(String royalty_info) {
		this.royalty_info = royalty_info;
	}
	

}
