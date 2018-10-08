var basePath = "/dce-manager";
$(function() {
	/*
	 * #############################################search form
	 * begin#################################
	 */
	// 产品类型
	$("#searchForm #searchButton").on(
			"click",
			function() {
				var dataUrl = basePath + "/order/list.html";
				$("#ordertable").datagrid('options').url = dataUrl;
				$("#ordertable").datagrid(
						'load',
						{
							'userName' : $("#searchForm #userName").val(),
							'startDate' : $("#searchForm #user_reg_startDate")
									.datebox('getValue'),
							'endDate' : $("#searchForm #user_reg_endDate")
									.datebox('getValue')
						});
			});

	$("#searchForm #resetButton").on("click", function() {
		$("#searchForm").form('reset');
	});

	/*
	 * #############################################search form
	 * end#################################
	 */

	/*
	 * ##########################grid init
	 * begin###################################################
	 */
	var toolbar_tt = [];

	/* ######################grid toolbar end############################## */
	var columns_tt = [ [
	/*
	 * {field:'id',title:'id',width:30,halign:"center",
	 * align:"left",hidden:true},
	 */
	{
		field : "orderId",
		title : "ID",
		width : 30,
		align : "center"
	}, {
		field : "user_name",
		title : "用户名",
		width : 80,
		align : "center",
		formatter : function(value, row, index) {
			var result = "";
			if (value != null && value != undefined) {
				result = result + value;
			}
			if (row.true_name != null && row.true_name != undefined) {
				result = result + "[" + row.true_name + "]";
			}
			return result;
		}
	}, {
		field : "user_level",
		title : "客户级别",
		width : 30,
		align : "center",
		formatter : function(value, row, index) {
			if (value == undefined) {
				return "";
			}
			return "F" + value;
		}
	}, {
		field : "qty",
		title : "目标数量",
		width : 30,
		align : "center"
	}, {
		field : "salqty",
		title : "已达数量",
		width : 30,
		align : "center"
	}, {
		field : "createTime",
		title : "充值时间",
		width : 80,
		align : "center",
		formatter : function(value, row, index) {
			if (value == null || value == 0) {
				return "";
			} else {

				return formatDate(value);
			}
		}
	}, {
		field : "orderType",
		title : "订单类型",
		width : 80,
		align : "center",
		formatter : function(value, row, index) {
			if (value == 1) {
				return "挂单买入";
			} else {

				return "挂单卖出";
			}
		}
	}, {
		field : "orderStatus",
		title : "订单状态",
		width : 30,
		align : "center",
		formatter : function(value, row, index) {
			if (value == "1") {
				return "有效";
			} else if (value == "2") {
				return "无效";
			}
			// else if(value == "3"){
			// return "已拒绝";
			// }else if(value == "4"){
			// return "已完成";
			// }
		}
	}
	// {field:"edit",title:"审核",width:100,align:"center",
	// formatter:function(value,row,index){
	// if(row.orderStatus=="1"){
	// return '<a href="javascript:void(0);"
	// onclick="checkOrder('+row.orderId+',\''+2+'\');">通过</a> |' +
	// '<a href="javascript:void(0);"
	// onclick="checkOrder('+row.orderId+',\''+3+'\');">拒绝</a>';
	// }
	// }
	// }
	] ];

	/* ######################grid columns end############################## */

	$("#ordertable").datagrid({
		url : basePath + "/order/list.html",
		height : $("#body").height() - $('#search_area').height() - 10,
		width : $("#body").width(),
		rownumbers : true,
		fitColumns : true,
		singleSelect : false,// 配合根据状态限制checkbox
		autoRowHeight : true,
		striped : true,
		checkOnSelect : false,// 配合根据状态限制checkbox
		selectOnCheck : false,// 配合根据状态限制checkbox
		/*
		 * loadFilter : function(data){ return { 'rows' : data.datas, 'total' :
		 * data.total, 'pageSize' : data.pageSize, 'pageNumber' : data.page }; },
		 */

		pagination : true,
		showPageList : true,
		pageSize : 20,
		pageList : [ 10, 20, 30 ],
		idField : "id",
		// frozenColumns : [[{field:'ck',checkbox:true}]],
		columns : columns_tt,
		toolbar : toolbar_tt
	});

	/*
	 * $(window).resize(function (){ domresize(); });
	 */
	/*
	 * ##########################grid init
	 * end###################################################
	 */
});

/**
 * 使用方法: 开启:MaskUtil.mask(); 关闭:MaskUtil.unmask();
 * 
 * MaskUtil.mask('其它提示文字...');
 */
var MaskUtil = (function() {

	var $mask, $maskMsg;

	var defMsg = '正在处理，请稍待。。。';

	function init() {
		if (!$mask) {
			$mask = $("<div class=\"datagrid-mask mymask\"></div>").appendTo(
					"body");
		}
		if (!$maskMsg) {
			$maskMsg = $(
					"<div class=\"datagrid-mask-msg mymask\">" + defMsg
							+ "</div>").appendTo("body").css({
				'font-size' : '12px'
			});
		}

		$mask.css({
			width : "100%",
			height : $(document).height()
		});

		var scrollTop = $(document.body).scrollTop();

		$maskMsg.css({
			left : ($(document.body).outerWidth(true) - 190) / 2,
			top : (($(window).height() - 45) / 2) + scrollTop
		});

	}

	return {
		mask : function(msg) {
			init();
			$mask.show();
			$maskMsg.html(msg || defMsg).show();
		},
		unmask : function() {
			$mask.hide();
			$maskMsg.hide();
		}
	}

}());

function reloadDataGrid() {
	$("#ordertable").datagrid("reload");
}

function checkOrder(orderId, optType) {

	var msg = optType == '2' ? '通过' : '拒绝';
	$.messager.confirm("确认", "确认" + msg + "账户", function(r) {
		if (r) {
			$.ajax({
				url : basePath + "/order/checkOrder.html",
				type : "post",
				dataType : 'json',
				data : {
					"orderId" : orderId,
					"optType" : optType
				},
				success : function(ret) {
					if (ret.code == 0) {
						$.messager.alert("成功", msg + "成功");
						reloadDataGrid();
					} else {
						$.messager.alert("失败", ret.msg);
					}
				}
			});
		}
	});

}

/**
 * 格式化日期时间
 */
function formatDate(value) {
	var date = new Date(value);// long转换成date
	var year = date.getFullYear().toString();
	var month = (date.getMonth() + 1);
	var day = date.getDate().toString();
	var h = date.getHours();
	var m = date.getMinutes()
	var s = date.getSeconds();
	if (month < 10) {
		month = "0" + month;
	}
	if (day < 10) {
		day = "0" + day;
	}
	return year + "-" + month + "-" + day + " " + h + ":" + m + ":" + s;
}
/* ################***导出**begin*################### */
function export_excel() {
	var searchStr = $("#searchForm #searchStr").val();
	var productCode = $("#searchForm #search_productCode").combobox('getValue');
	var loanStatus = $("#searchForm #search_loanStatus").combobox('getValue');
	var loanType = $("#searchForm #search_loanType").combobox('getValue');
	var cityCode = $("#searchForm #search_city").combobox('getValue');
	var channelType = $("#searchForm #search_channelType").combobox('getValue');
	var startDate = $("#searchForm #search_startDate").datebox('getValue');
	var endDate = $("#searchForm #search_endDate").datebox('getValue');
	var processNextStep = $("#searchForm #search_processNextStep").combobox(
			'getValue');
	// document.getElementById("exportExcel").disabled = true;
	// document.getElementById("exportExcel").value = "正在导出";
	var exportIframe = document.createElement('iframe');
	exportIframe.src = basePath + "/loan/export/excel.html" + "?searchStr="
			+ searchStr + "&productCode=" + productCode + "&loanType="
			+ loanType + "&loanStatus=" + loanStatus + "&cityCode=" + cityCode
			+ "&channelType=" + channelType + "&startDate=" + startDate
			+ "&endDate=" + endDate + "&processNextStep=" + processNextStep;

	exportIframe.style.display = 'none';
	document.body.appendChild(exportIframe);
}
/* ################***导出**end*################### */

/* ##########################公用方法##begin############################ */
/**0
 * 得到选中的行
 * 
 * @returns {String}
 */
function get_ids() {
	var ids = '';
	var rows = $('#ordertable').datagrid('getChecked');
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择需要操作的数据", "info");
		return false;
	} else {
		for (var i = 0; i < rows.length; i++) {
			ids += rows[i].loanId + ',';
		}
		ids = ids.substring(0, ids.length - 1);
		return ids;
	}
}
/**
 * 得到一条数据
 * 
 * @returns {String}
 */
function get_id() {
	var rows = $('#ordertable').datagrid('getChecked');
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择需要操作的数据", "info");
		return false;
	} else if (rows.length > 1) {
		$.messager.alert("提示", "每次只能操作一条数据", "info");
		return;
	} else {
		return rows[0].loanId;
	}
}

// 监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 300);
};
// 改变表格和查询表单宽高
function domresize() {
	$('#ordertable').datagrid('resize', {
		height : $("#body").height() - $('#search_area').height() - 5,
		width : $("#body").width()
	});
	$('#search_area').panel('resize', {
		width : $("#body").width()
	});
	$('#detailLoanDiv').dialog('resize', {
		height : $("#body").height() - 25,
		width : $("#body").width() - 30
	});
}
/* ##########################公用方法##end############################ */