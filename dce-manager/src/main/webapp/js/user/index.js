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
				var dataUrl = basePath + "/user/list.html";
				$("#usertable").datagrid('options').url = dataUrl;
				$("#usertable").datagrid(
						'load',
						{
							'userName' : $("#searchForm #userName").val(),
							'userMobile' : $("#searchForm #userMobile").val(),
							'startDate' : $("#searchForm #user_reg_startDate").datebox('getValue'),
							'endDate' : $("#searchForm #user_reg_endDate").datebox('getValue'),
							'userLevel' : $("#searchForm #user_level").val(),
							'address' : $("#searchForm #address").val()
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
	var toolbar_tt = [ {
		iconCls : 'icon-edit',
		text : '新增会员',
		handler : editVip
	}, {
		iconCls : 'icon-edit',
		text : '查看推荐人',
		handler : queryOrgTree
	}, {
		iconCls : 'icon-edit',
		text : '导出客户信息',
		handler : export_excel
	}, {
		iconCls : 'icon-edit',
		text : '修改客户信息',
		handler : editUser
	} ];

	/* ######################grid toolbar end############################## */
	var columns_tt = [ [
			{
				field : 'id',
				title : 'id',
				width : 30,
				halign : "center",
				align : "left",
				checkbox : true
			},
			{
				field : "user_name",
				title : "用户名[姓名]",
				width : 125,
				align : "center",
				formatter : function(value, row, index) {
					var str = "";
					if (value != null && value != undefined) {
						str += value;
					}
					if (row.true_name != null && row.true_name != undefined) {
						str += "[" + row.true_name + "]";
					} else if (row.true_name != null
							&& row.true_name != undefined) {
						str += "[未认证]";
					}
					return str;
				}
			},
			{
				field : "user_level_name",
				title : "级别",
				width : 65,
				align : "center"
			},
			{
				field : "mobile",
				title : "手机号码",
				width : 75,
				align : "center"
			},
			{
				field : "refereeUserMobile",
				title : "推荐人",
				width : 75,
				align : "center"
			},
			{
				field : "user_password",
				title : "登录密码",
				width : 65,
				align : "center"
			},
			{
				field : "two_password",
				title : "支付密码",
				width : 65,
				align : "center"
			},
			{
				field : "address",
				title : "收货地址",
				width : 100,
				align : "center"
			},
			{
				field : "banknumber",
				title : "银行卡号",
				width : 125,
				align : "center"
			},
			{
				field : "reg_time",
				title : "注册时间",
				width : 115,
				align : "center",
				formatter : function(value, row, index) {
					if (value == null || value == 0 || value == undefined) {
						return "";
					} else {
						return formatDate(value);
					}
				}
			},
			{
				field : "certification",
				title : "认证状态",
				width : 65,
				align : "center",
				formatter : function(value, row, index) {
					if (value == null || value == undefined) {
						return "";
					}
					if (value == "1") {
						return "已认证";
					} else {
						return "未认证";
					}
				}
			},
			{
				field : "status",
				title : "冻结状态",
				width : 65,
				align : "center",
				formatter : function(value, row, index) {
					if (value == null || value == undefined) {
						return "";
					}
					if (value == "1") {
						return "已冻结";
					} else {
						return "已解冻";
					}
				}
			},
			{
				field : "edit",
				title : "操作",
				width : 70,
				align : "center",
				formatter : function(value, row, index) {
					var href = "";
					if (row.status == '0') {
						href = href
								+ '<a href="javascript:void(0);"  onclick="to_lock('
								+ row.id + ',\'' + 1 + '\');">冻结账户</a>';
					} else if (row.status == '1') {

						href = href
								+ '<a href="javascript:void(0);"  onclick="to_lock('
								+ row.id + ',\'' + 0 + '\');">解冻账户</a>';
					}

					if (row.certification != undefined
							&& row.certification != 1) {
						href = href
								+ ' <a href="javascript:void(0);"  onclick="baoKongDan('
								+ row.id + ');">认证</a>';
					} else if (row.certification == "") {
						href = href + '';

					} else if (row.certification == null) {
						href = href + '';

					}

					return href;
				}
			}, ] ];

	/* ######################grid columns end############################## */

	$("#usertable").datagrid({
		url : basePath + "/user/list.html",
		height : $("#body").height() - $('#search_area').height() - 10,
		width : $("#body").width(),
		rownumbers : true,
		fitColumns : true,
		singleSelect : true,// 配合根据状态限制checkbox
		autoRowHeight : true,
		striped : true,
		checkOnSelect : true,// 配合根据状态限制checkbox
		selectOnCheck : true,// 配合根据状态限制checkbox
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

function to_lock(userId, optType) {

	var msg = optType == '0' ? '解冻' : '冻结';
	$.messager.confirm("确认", "确认" + msg + "账户", function(r) {
		if (r) {
			$.ajax({
				url : basePath + "/user/lockUser.html",
				type : "post",
				dataType : 'json',
				data : {
					"userId" : userId,
					"optType" : optType
				},
				success : function(ret) {
					if (ret.code == 0) {
						$.messager.alert("成功", msg + "成功");
						reloadDataGrid(); // 重新加载数据网格
					} else {
						$.messager.alert("失败", ret.msg);
					}
				}
			});
		}
	});

}

function baoKongDan(id) {
	var url = basePath + "/user/toActivity.html?userId=" + id;
	$('#activityUserDiv').dialog({
		title : "用户认证",
		width : 450,
		height : 550,
		closed : false,
		closable : false,
		cache : false,
		href : url,
		modal : true,
		toolbar : [ {
			iconCls : "icon-save",
			text : "保存",
			handler : save_activity
		}, {
			iconCls : "icon-no",
			text : "关闭",
			handler : function() {
				$("#activityUserDiv").dialog("close");
			}
		} ]
	});

}
/**
 * 认证（激活）
 */
function save_activity() {
	var userId = $("#act_level_userId").val();
	var trueName = $("#act_trueName").val();
	var mobile = $("#act_user_mobile").val();
	var idnumber = $("#act_user_idnumber").val();
	var banknumber = $("#act_user_banknumber").val();
	var banktype = $("#act_user_banktype").val();
	var sex = $("#act_change_sex").combobox('getValue');
	var userLevel = $("#act_change_level").combobox('getValue');

	$.ajax({
		url : basePath + "/user/saveActivity.html",
		type : "post",
		dataType : 'json',
		data : {
			"userId" : userId,
			"trueName" : trueName,
			"mobile" : mobile,
			"idnumber" : idnumber,
			"banknumber" : banknumber,
			"banktype" : banktype,
			"sex" : sex,
			"userLevel" : userLevel
		},
		success : function(ret) {
			if (ret.code == 0) {
				$.messager.alert("成功", ret.msg);
				$("#activityUserDiv").dialog("close");
				reloadDataGrid();// 重新加载数据网格
			} else {
				$.messager.alert("失败", ret.msg);
			}
		}
	});
}

function editVip() {
	var url = basePath + "/user/vipAdmin.html";
	$('#newVipDiv').dialog({
		title : "新增一个会员",
		width : 450,
		height : 550,
		closed : false,
		closable : false,
		cache : false,
		href : url,
		modal : true,
		toolbar : [ {
			iconCls : "icon-save",
			text : "保存",
			handler : save_vip
		}, {
			iconCls : "icon-no",
			text : "关闭",
			handler : function() {
				$("#newVipDiv").dialog("close");
			}
		} ]
	});
}
/**
 * 把页面新増会员的数据传到后台
 */
function save_vip() {
	var userName = $("#vip_user_login_name").val();
	var userPassword = $("#vip_user_login_password").val();
	var twoPassword = $("#vip_user_two_password").val();
	var refereeUserMobile = $("#vip_user_refereeUserMobile").val();
	var trueName = $("#vip_trueName").val();
	var mobile = $("#vip_user_mobile").val();
	var idnumber = $("#vip_user_idnumber").val();
	var banknumber = $("#vip_user_banknumber").val();
	var banktype = $("#vip_user_banktype").val();
	var sex = $("#vip_change_sex").combobox('getValue');
	var userLevel = $("#vip_change_level").combobox('getValue');

	$.ajax({
		url : basePath + "/user/memberAdmin.html",
		type : "post",
		dataType : 'json',
		data : {
			"userName" : userName,
			"userPassword" : userPassword,
			"twoPassword" : twoPassword,
			"refereeUserMobile" : refereeUserMobile,
			"trueName" : trueName,
			"mobile" : mobile,
			"idnumber" : idnumber,
			"banknumber" : banknumber,
			"banktype" : banktype,
			"sex" : sex,
			"userLevel" : userLevel
		},
		success : function(ret) {
			if (ret.code == 0) {
				$.messager.alert("成功", ret.msg);
				$("#newVipDiv").dialog("close");
				reloadDataGrid();// 重新加载数据网格
			} else {
				$.messager.alert("失败", ret.msg);
			}
		}
	});
}

function editUser() {
	var id = get_id();
	if (id == null || id == "") {
		return;
	}
	var url = basePath + "/user/edit.html?userId=" + id;
	$('#editLevelDiv').dialog({
		title : "修改用户信息",
		width : 450,
		height : 550,
		closed : false,
		closable : false,
		cache : false,
		href : url,
		modal : true,
		toolbar : [ {
			iconCls : "icon-save",
			text : "保存",
			handler : save_edit
		},{
			iconCls : "icon-save",
			text : "修改推荐人",
			handler : save_edit_ref
		}, {
			iconCls : "icon-no",
			text : "关闭",
			handler : function() {
				$("#editLevelDiv").dialog("close");
			}
		} ]
	});
}
/**
 * 把页面修改的数据传到后台
 */
function save_edit() {
	var userId = $("#change_level_userId").val();
	var userName = $("#edit_user_login_name").val();
	var userPassword = $("#edit_user_login_password").val();
	var twoPassword = $("#edit_user_two_password").val();
	var trueName = $("#edit_trueName").val();
	var mobile = $("#edit_user_mobile").val();
	var idnumber = $("#edit_user_idnumber").val();
	var banknumber = $("#edit_user_banknumber").val();
	var banktype = $("#edit_user_banktype").val();
	var sex = $("#edit_change_sex").combobox('getValue');
	var userLevel = $("#edit_change_level").combobox('getValue');

	$.ajax({
		url : basePath + "/user/saveEdit.html",
		type : "post",
		dataType : 'json',
		data : {
			"userId" : userId,
			"userName" : userName,
			"userPassword" : userPassword,
			"twoPassword" : twoPassword,
			"trueName" : trueName,
			"mobile" : mobile,
			"idnumber" : idnumber,
			"banknumber" : banknumber,
			"banktype" : banktype,
			"sex" : sex,
			"userLevel" : userLevel
		},
		success : function(ret) {
			if (ret.code == 0) {
				$.messager.alert("成功", ret.msg);
				$("#editLevelDiv").dialog("close");
				reloadDataGrid();// 重新加载数据网格
			} else {
				$.messager.alert("失败", ret.msg);
			}
		}
	});
}
/**
 * 把页面修改推荐人
 */
function save_edit_ref() {
	var userId = $("#change_level_userId").val();
	var refereeUserMobile = $("#edit_user_refereeUserMobile").val();
	
	$.ajax({
		url : basePath + "/user/updateUserReferee.html",
		type : "post",
		dataType : 'json',
		data : {
			"userId" : userId,
			"refereeUserMobile" : refereeUserMobile
		},
		success : function(ret) {
			if (ret.code == 0) {
				$.messager.alert("成功", ret.msg);
				$("#editLevelDiv").dialog("close");
				reloadDataGrid();// 重新加载数据网格
			} else {
				$.messager.alert("失败", ret.msg);
			}
		}
	});
}

// 重新加载数据网格
function reloadDataGrid() {
	$("#usertable").datagrid("reload");
}

/**
 * 订单明细
 * 
 * @param id
 */
function queryOrgTree() {
	var id = get_id();
	if (id == null || id == "") {
		return;
	}
	var url = basePath + "/user/orgtree.html?&rand=" + Math.random()
			+ "&userId=" + id;
	var content = '<iframe src="'
			+ url
			+ '" width="100%" height="99%" frameborder="0" scrolling="no"></iframe>';
	$('#userOrgTreeDiv').dialog({
		title : "查看直推树",
		content : content,
		width : 600,
		height : 500,
		closed : false,
		closable : false,
		cache : false,
		// href: url,
		modal : true,
		toolbar : [ {
			iconCls : "icon-no",
			text : "关闭",
			handler : function() {
				$("#userOrgTreeDiv").dialog("close");
			}
		} ]
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
	var userName = $("#searchForm #userName").val();
	var userMobile = $("#searchForm #userMobile").val();
	var startDate = $("#searchForm #user_reg_startDate").datebox('getValue');
	var endDate = $("#searchForm #user_reg_endDate").datebox('getValue');
	

	var exportIframe = document.createElement('iframe');
	exportIframe.src = basePath + "/user/export.html" + "?userName=" + userName
			+ "&userMobile=" + userMobile + "&startDate=" + startDate
			+ "&endDate=" + endDate;

	exportIframe.style.display = 'none';
	document.body.appendChild(exportIframe);
}
/* ################***导出**end*################### */

/* ##########################公用方法##begin############################ */
/**
 * 得到选中的行
 * 
 * @returns {String}
 */
function get_ids() {
	var ids = '';
	var rows = $('#tt').datagrid('getChecked');
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
	var rows = $('#usertable').datagrid('getChecked');
	if (rows.length == 0) {
		$.messager.alert("提示", "请选择需要操作的数据", "info");
		return "";
	} else if (rows.length > 1) {
		$.messager.alert("提示", "每次只能操作一条数据", "info");
		return "";
	} else {
		return rows[0].id;
	}
}

// 监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 300);
};
// 改变表格和查询表单宽高
function domresize() {
	$('#usertable').datagrid('resize', {
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