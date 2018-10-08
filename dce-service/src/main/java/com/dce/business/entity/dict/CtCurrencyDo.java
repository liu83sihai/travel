package com.dce.business.entity.dict;

import java.io.Serializable;
import java.math.BigDecimal;

public class CtCurrencyDo implements Serializable {

	private static final long serialVersionUID = -7638374113425716358L;
	private Integer id;
	private String currency_name;// '货币名称',
	private String currency_logo;// '货币logo',
	private String currency_mark;// '英文标识',
	private String currency_content;
	private BigDecimal currency_all_money;// '总市值',
	private BigDecimal currency_all_num;// COMMENT '币总数量',
	private BigDecimal currency_buy_fee;// '买入手续费',
	private BigDecimal currency_sell_fee;// '卖出手续费',
	private String sell_pre;// '卖出比例。每次可卖持有的百分',
	private BigDecimal with_fee;// '提币手续费',
	private String currency_url;// '该币种的链接地址',
	private Integer trade_currency_id;// '可以进行交易的币种',
	private Integer is_line;// '0',
	private Integer is_lock;// '是否交易 0 是交易许可 1是交易不许可',
	private Integer port_number;//
	private Integer add_time;//
	private Integer status;//
	private String rpc_url;//
	private String rpc_pwd;//
	private String rpc_user;//
	private Integer currency_all_tibi;// '最大提币额',
	private String detail_url;// '详情跳转链接',
	private String qianbao_url;// '钱包储存路径',
	private String qianbao_key;// '钱包密钥',
	private BigDecimal price_fx;// '发行价格',
	private BigDecimal price_up;// '涨停',
	private BigDecimal price_open;// '开盘价',
	private BigDecimal price_down;// '跌停',
	private BigDecimal price_znew;
	private Integer sort;
	private Integer currency_digit_num;
	private String guanwang_url;
	private Integer utime;
	private Integer is_ctstatus;// '充币状态0不可1可',
	private Integer is_tbstatus;// '提币状态0不可1可',
	private BigDecimal guadanum;// '卖出挂单量',
	private BigDecimal buynum;// '买进量',
	private BigDecimal guabuynum;// '买入挂单量',
	private Integer is_award_switch; //'奖金开关：0 关  1：开
	private Integer is_shifang_switch; //'释放开关：0 关  1：开

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCurrency_name() {
		return currency_name;
	}

	public void setCurrency_name(String currency_name) {
		this.currency_name = currency_name;
	}

	public String getCurrency_logo() {
		return currency_logo;
	}

	public void setCurrency_logo(String currency_logo) {
		this.currency_logo = currency_logo;
	}

	public String getCurrency_mark() {
		return currency_mark;
	}

	public void setCurrency_mark(String currency_mark) {
		this.currency_mark = currency_mark;
	}

	public String getCurrency_content() {
		return currency_content;
	}

	public void setCurrency_content(String currency_content) {
		this.currency_content = currency_content;
	}

	public BigDecimal getCurrency_all_money() {
		return currency_all_money;
	}

	public void setCurrency_all_money(BigDecimal currency_all_money) {
		this.currency_all_money = currency_all_money;
	}

	public BigDecimal getCurrency_all_num() {
		return currency_all_num;
	}

	public void setCurrency_all_num(BigDecimal currency_all_num) {
		this.currency_all_num = currency_all_num;
	}

	public BigDecimal getCurrency_buy_fee() {
		return currency_buy_fee;
	}

	public void setCurrency_buy_fee(BigDecimal currency_buy_fee) {
		this.currency_buy_fee = currency_buy_fee;
	}

	public BigDecimal getCurrency_sell_fee() {
		return currency_sell_fee;
	}

	public void setCurrency_sell_fee(BigDecimal currency_sell_fee) {
		this.currency_sell_fee = currency_sell_fee;
	}

	public String getSell_pre() {
		return sell_pre;
	}

	public void setSell_pre(String sell_pre) {
		this.sell_pre = sell_pre;
	}

	public BigDecimal getWith_fee() {
		return with_fee;
	}

	public void setWith_fee(BigDecimal with_fee) {
		this.with_fee = with_fee;
	}

	public String getCurrency_url() {
		return currency_url;
	}

	public void setCurrency_url(String currency_url) {
		this.currency_url = currency_url;
	}

	public Integer getTrade_currency_id() {
		return trade_currency_id;
	}

	public void setTrade_currency_id(Integer trade_currency_id) {
		this.trade_currency_id = trade_currency_id;
	}

	public Integer getIs_line() {
		return is_line;
	}

	public void setIs_line(Integer is_line) {
		this.is_line = is_line;
	}

	public Integer getIs_lock() {
		return is_lock;
	}

	public void setIs_lock(Integer is_lock) {
		this.is_lock = is_lock;
	}

	public Integer getPort_number() {
		return port_number;
	}

	public void setPort_number(Integer port_number) {
		this.port_number = port_number;
	}

	public Integer getAdd_time() {
		return add_time;
	}

	public void setAdd_time(Integer add_time) {
		this.add_time = add_time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRpc_url() {
		return rpc_url;
	}

	public void setRpc_url(String rpc_url) {
		this.rpc_url = rpc_url;
	}

	public String getRpc_pwd() {
		return rpc_pwd;
	}

	public void setRpc_pwd(String rpc_pwd) {
		this.rpc_pwd = rpc_pwd;
	}

	public String getRpc_user() {
		return rpc_user;
	}

	public void setRpc_user(String rpc_user) {
		this.rpc_user = rpc_user;
	}

	public Integer getCurrency_all_tibi() {
		return currency_all_tibi;
	}

	public void setCurrency_all_tibi(Integer currency_all_tibi) {
		this.currency_all_tibi = currency_all_tibi;
	}

	public String getDetail_url() {
		return detail_url;
	}

	public void setDetail_url(String detail_url) {
		this.detail_url = detail_url;
	}

	public String getQianbao_url() {
		return qianbao_url;
	}

	public void setQianbao_url(String qianbao_url) {
		this.qianbao_url = qianbao_url;
	}

	public String getQianbao_key() {
		return qianbao_key;
	}

	public void setQianbao_key(String qianbao_key) {
		this.qianbao_key = qianbao_key;
	}

	public BigDecimal getPrice_fx() {
		return price_fx;
	}

	public void setPrice_fx(BigDecimal price_fx) {
		this.price_fx = price_fx;
	}

	public BigDecimal getPrice_up() {
		return price_up;
	}

	public void setPrice_up(BigDecimal price_up) {
		this.price_up = price_up;
	}

	public BigDecimal getPrice_open() {
		return price_open;
	}

	public void setPrice_open(BigDecimal price_open) {
		this.price_open = price_open;
	}

	public BigDecimal getPrice_down() {
		return price_down;
	}

	public void setPrice_down(BigDecimal price_down) {
		this.price_down = price_down;
	}

	public BigDecimal getPrice_znew() {
		return price_znew;
	}

	public void setPrice_znew(BigDecimal price_znew) {
		this.price_znew = price_znew;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getCurrency_digit_num() {
		return currency_digit_num;
	}

	public void setCurrency_digit_num(Integer currency_digit_num) {
		this.currency_digit_num = currency_digit_num;
	}

	public String getGuanwang_url() {
		return guanwang_url;
	}

	public void setGuanwang_url(String guanwang_url) {
		this.guanwang_url = guanwang_url;
	}

	public Integer getUtime() {
		return utime;
	}

	public void setUtime(Integer utime) {
		this.utime = utime;
	}

	public Integer getIs_ctstatus() {
		return is_ctstatus;
	}

	public void setIs_ctstatus(Integer is_ctstatus) {
		this.is_ctstatus = is_ctstatus;
	}

	public BigDecimal getGuadanum() {
		return guadanum;
	}

	public void setGuadanum(BigDecimal guadanum) {
		this.guadanum = guadanum;
	}

	public BigDecimal getBuynum() {
		return buynum;
	}

	public void setBuynum(BigDecimal buynum) {
		this.buynum = buynum;
	}

	public BigDecimal getGuabuynum() {
		return guabuynum;
	}

	public void setGuabuynum(BigDecimal guabuynum) {
		this.guabuynum = guabuynum;
	}

	public Integer getIs_award_switch() {
		return is_award_switch;
	}

	public void setIs_award_switch(Integer is_award_switch) {
		this.is_award_switch = is_award_switch;
	}

	public Integer getIs_shifang_switch() {
		return is_shifang_switch;
	}

	public void setIs_shifang_switch(Integer is_shifang_switch) {
		this.is_shifang_switch = is_shifang_switch;
	}

	public Integer getIs_tbstatus() {
		return is_tbstatus;
	}

	public void setIs_tbstatus(Integer is_tbstatus) {
		this.is_tbstatus = is_tbstatus;
	}

}
