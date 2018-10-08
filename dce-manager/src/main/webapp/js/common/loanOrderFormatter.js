function loanStatusFormatter(value, row, index) {
	if (value == "DRAFT") {
		return "草稿"
	}
	if (value == "PENDING") {
		return "待处理";
	}
	if (value == "PROCESSING") {
		return "处理中";
	}
	if (value == "AUDITED") {
		return "已审核";
	}
	if (value == "TREATY") {
		return "已签约";
	}
	if (value == "SUBJECTED") {
		return "已上标";
	}
	if (value == "REPAYING") {
		return "还款中";
	}
	if (value == "REPAYED") {
		return "已还清";
	}
	if (value == "NOPASS") {
		return "拒绝";
	}
	if (value == "INVALID") {
		return "失效";
	}
	if (value == "LD_CONFIRM") {
		return "地产确认放款";
	}
	if (value == "LD_CANCLE") {
		return "地产商取消";
	}
	if (value == "LD_NOPASS") {
		return "地产商不通过";
	}
	if (value == "LD_NOLOAN") {
		return "地产不同意放款";
	}
	if (value == "FAIL_LOAN") {
		return "放款失败";
	}
	if (value == "WAIT_LAND_PAY") {
		return "待地产商支付手续费";
	}
	if (value == "WAIT_CUSTOMER_PAY") {
		return "待客户支付手续费";
	}
	if (value == "OVERDUE") {
		return "已逾期	";
	}
}

function repayTypeFormatter(value, row, index) {
	if (value == 'SLD') {
		return '一次先付息到期还本';
	}
	if (value == 'FPIC') {
		return '等本等息';
	}
	if (value == 'LP') {
		return '等额本金';
	}
	if (value == 'IIFP') {
		return '一次付息到期还款';
	}
	if (value == 'MIFP') {
		return '按月付息到期还本';
	}
	if (value == 'PI') {
		return '等额本息';
	}
	if (value == 'LSR2') {
		return '到期一次性还本付息（日）';
	}
	if (value == 'MIFPBN') {
		return '每月付息每半年还本';
	}
	if (value == 'MIFPYN') {
		return '每月付息每一年还本';
	}
	if (value == 'YTFXDQHB') {
		return '每月头付息，到期还本';
	}
}

function capitalChannelFormatter(value, row, index) {
	if (value == "TLPAY") {
		return "P2P通联";
	} else if (value == "LLPAY") {
		return "P2P连连";
	} else if (value == "95EPAY") {
		return "P2P双乾";
	} else if (value == "QUICKPAY") {
		return "P2P快钱";
	} else if (value == "XD_TLPAY") {
		return "彩通小贷通联";
	} else if (value == "XD_95EPAY") {
		return "彩通小贷双乾";
	} else if (value == "YNXT_HYJ") {
		return "云南信托-花易借";
	} else if (value == "YNXT_CD") {
		return "云南信托-车贷";
	} else if (value == "BANKPAY") {
		return "银行支付";
	} else if (value == "OTHER") {
		return "其他";
	} else if (value == "HF") {
		return "汇付";
	} else {
		return value;
	}
}

function subjectTypeFormatter(value, row, index) {
	if (value == '1') {
		return '汇付';
	}
	if (value == '2') {
		return '通联';
	}
	if (value == '3') {
		return '散标';
	}
}
