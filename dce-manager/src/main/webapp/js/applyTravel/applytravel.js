var basePath = "/dce-manager";
$(function() {
	/*
	 * #############################################search form
	 * begin#################################
	 */

	$("#searchapplyTravelForm #searchButton")
			.on(
					"click",
					function() {
						var dataUrl = httpUrl
								+ "/applytravel/listApplyTravel.html";
						$("#tt_ApplyTravel").datagrid('options').url = dataUrl;
						$("#tt_ApplyTravel")
								.datagrid(
										'load',
										{
											'name' : $(
													"#searchapplyTravelForm #name")
													.val(),
											'startDate' : $(
													"#searchapplyTravelForm #startDate")
													.datebox('getValue'),
											'endDate' : $(
													"#searchapplyTravelForm #endDate")
													.datebox('getValue')
										});
					});

	$("#searchapplyTravelForm #resetButton").on("click", function() {
		$("#searchapplyTravelForm").form('reset');
	});

	/*
	 * #############################################search form
	 * end#################################
	 */

	/*
	 * ##########################grid init
	 * begin####################################################
	 */
	/*
	 * ##########################grid toolbar
	 * begin#################################################
	 */
	/*
	 * var toolbar_tt = [ { iconCls:"icon-edit", text:"新增",
	 * handler:to_addapplyTravel } ];
	 */

	/* ######################grid toolbar end############################## */
	/* ######################grid columns begin############################## */
	var columns_tt = [ [
			{
				field : 'id',
				title : 'id',
				width : 100,
				hidden : true
			},
			{
				field : "mobile",
				title : "账号",
				width : 150,
				align : "center"
			},
			{
				field : "level",
				title : "等级",
				width : 80,
				align : "center",
				formatter : function(value, row, index) {
					if (row.level == "0") {
						return "普通用户";
					} else if (row.level == "1") {
						return "会员";
					} else if (row.level == "2") {
						return "VIP";
					} else if (row.level == "3") {
						return "合伙人";
					} else if (row.level == "4") {
						return "分红股东";
					}
				}
			},
			{
				field : "name",
				title : "姓名",
				width : 100,
				align : "center"
			},
			{
				field : "sex",
				title : "性别",
				width : 50,
				align : "center",
				formatter : function(value, row, index) {
					if (row.sex == "0") {
						return "男";
					} else if (row.sex == "1") {
						return "女";
					}
				}
			},
			{
				field : "nation",
				title : "民族",
				width : 80,
				align : "center"
			},
			{
				field : "identity",
				title : "身份证",
				width : 150,
				align : "center"
			},
			{
				field : "phone",
				title : "手机号码",
				width : 150,
				align : "center"
			},
			{
				field : "address",
				title : "地址",
				width : 150,
				align : "center"
			},
			{
				field : "isbeen",
				title : "是否去过",
				width : 80,
				align : "center",
				formatter : function(value, row, index) {
					if (row.isbeen == "0") {
						return "是";
					} else if (row.isbeen == "1") {
						return "否";
					}
				}
			},
			{
				field : "people",
				title : "同行人数",
				width : 50,
				align : "center"
			},
			{
				field : "linename",
				title : "路线名称",
				width : 150,
				align : "center"
			},
			{
				field : "createtime",
				title : "申请时间",
				width : 150,
				align : "center",
				formatter : dateTimeFormatter
			},
			{
				field : "state",
				title : "状态",
				width : 80,
				align : "center",
				formatter : function(value, row, index) {
					if (row.state == "0") {
						return "通过";
					} else if (row.state == "1") {
						return "未通过";
					} else if (row.state == "2") {
						return "撤销";
					}
				}
			},
			{
				field : "操作",
				title : "操作",
				width : 150,
				align : "left",
				formatter : function(value, row, index) {
					if (row.state == 1) {
						return '<a href="javascript:void(0);" onclick="to_editapplyTravel(\''
								+ row.id
								+ '\');">编辑</a> <a href="javascript:void(0);" onclick="agree_applyTravel(\''
								+ row.id
								+ '\');">同意申请</a>  <a href="javascript:void(0);" onclick="deleteapplyTravelById(\''
								+ row.id + '\');">删除</a>';
					} else {
						return '<a href="javascript:void(0);" onclick="to_editapplyTravel(\''
								+ row.id
								+ '\');">编辑</a>  <a href="javascript:void(0);" onclick="deleteapplyTravelById(\''
								+ row.id + '\');">删除</a>';

					}
				}
			} ] ];
	/* ######################grid columns end############################## */

	$("#tt_ApplyTravel").datagrid(
			{
				url : httpUrl + "/applytravel/listApplyTravel.html?&rand="
						+ Math.random(),
				height : $("#body").height()
						- $('#search_areaApplyTravel').height() - 10,
				width : $("#body").width(),
				rownumbers : true,
				fitColumns : true,
				singleSelect : false,// 配合根据状态限制checkbox
				autoRowHeight : true,
				striped : true,
				checkOnSelect : false,// 配合根据状态限制checkbox
				selectOnCheck : false,// 配合根据状态限制checkbox
				loadFilter : function(data) {
					return {
						'rows' : data.datas,
						'total' : data.total,
						'pageSize' : data.pageSize,
						'pageNumber' : data.page
					};
				},
				pagination : true,
				showPageList : true,
				pageSize : 20,
				pageList : [ 10, 20, 30 ],
				idField : "id",
				columns : columns_tt,
				queryParams : {
					'name' : $("#searchapplyTravelForm #name").val(),
					'startDate' : $("#searchapplyTravelForm #startDate")
							.datebox('getValue'),
					'endDate' : $("#searchapplyTravelForm #endDate").datebox(
							'getValue')
				},
				onLoadSuccess : function(data) {// 根据状态限制checkbox

				}
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
 * 新增
 * 
 * @param id
 */
/*
 * function to_addapplyTravel(){ to_editapplyTravel('');
 * $('#editUserFeedbackDiv').dialog({ title: "新增", }); }
 */
/**
 * 编辑
 * 
 * @param id
 */
function to_editapplyTravel(id) {

	var url = httpUrl + "/applytravel/addApplyTravel.html?&rand="
			+ Math.random() + "&id=" + id;
	$('#editApplyTravelDiv').dialog({
		title : "编辑",
		width : 760,
		height : 500,
		closed : false,
		closable : false,
		cache : false,
		href : url,
		modal : true,
		toolbar : [ {
			iconCls : "icon-save",
			text : "保存",
			handler : save_ApplyTravel
		}, {
			iconCls : "icon-no",
			text : "关闭",
			handler : function() {
				$("#editApplyTravelDiv").dialog("close");
			}
		} ]
	});
}
/**
 * 同意申请
 */
function agree_applyTravel(id) {
	if (!id) {
		$.messager.alert("消息", "id不能为空");
		return;
	}
	$.messager.confirm("消息", "确认同意申请吗，同意后不可恢复", function(r) {
		if (r) {
			$.ajax({
				url : httpUrl + "/applytravel/agreeApply.html?id=" + id,
				type : "post",
				data : {},
				success : function(data) {
					if (data.ret == 1) {
						$.messager.alert("消息", "操作成功");
						$('#tt_ApplyTravel').datagrid('reload');
					} else {
						$.messager.alert("消息", "操作失败，请稍后再试");
					}
				}
			});
		}
	});
}

/**
 * 删除
 */
function deleteapplyTravelById(id) {
	if (!id) {
		$.messager.alert("消息", "id不能为空");
		return;
	}
	$.messager.confirm("消息", "确认删除该申请吗，删除后不可恢复", function(r) {
		if (r) {
			$.ajax({
				url : httpUrl + "/applytravel/deleteapplyTravelById.html?id="
						+ id,
				type : "post",
				data : {},
				success : function(data) {
					if (data.ret == 1) {
						$.messager.alert("消息", "删除成功");
						$('#tt_ApplyTravel').datagrid('reload');
					} else {
						$.messager.alert("消息", "删除失败，请稍后再试");
					}
				}
			});
		}
	});
}

function save_ApplyTravel() {

	var url = httpUrl + "/applytravel/saveApplyTravel.html?&rand="
			+ Math.random();

	// 创建表单数据对象
	var obj = new FormData();

	// 获取框中的数据
	var id = $("#editApplyTravelForm #id").val();
	var userid = $("#editApplyTravelForm #userid").val();
	var name = $("#editApplyTravelForm #name").val();
	var sex = $("#editApplyTravelForm #sex").combobox('getValue');
	var nation = $("#editApplyTravelForm #nation").val();
	var identity = $("#editApplyTravelForm #identity").val();
	var phone = $("#editApplyTravelForm #phone").val();
	var address = $("#address").val();
	var isbeen = $("#editApplyTravelForm #isbeen").combobox('getValue');
	var people = $("#editApplyTravelForm #people").combobox('getValue');
	var pathid = $("#editApplyTravelForm #pathid").combobox('getValue');
	var state = $("#editApplyTravelForm #state").combobox('getValue');
	if (name == null || name == "") {
		$.messager.alert("错误", "姓名不能为空");
		return;
	}
	if (nation == null || nation == "") {
		$.messager.alert("错误", "民族不能为空");
		return;
	}
	/*var reg =  /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
	if (!reg.test(identity)) {
		$.messager.alert("错误", "身份证输入不合法");
		return;
	}*/
	if (identity == null || identity == "") {
		$.messager.alert("错误", "身份证不能为空");
		return;
	}
	/*var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	if (!myreg.test(phone)) {
		$.messager.alert("错误", "请输入正确的手机号码");
		return;
	}*/
	if (phone == null || phone == "") {
		$.messager.alert("错误", "手机号码不能为空");
		return;
	}
	// 将数据添加至表单数据对象中
	obj.append("id", id);
	obj.append("userid", userid);
	obj.append("name", name);
	obj.append("sex", sex);
	obj.append("nation", nation);
	obj.append("id", id);
	obj.append("identity", identity);
	obj.append("phone", phone);
	obj.append("address", address);
	obj.append("isbeen", isbeen);
	obj.append("people", people);
	obj.append("pathid", pathid);
	obj.append("state", state);

	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : url,
		data : obj,
		processData : false,
		contentType : false,
		success : function(data) {
			if (data.code === "0") {
				$("#editApplyTravelDiv").dialog("close");
				$('#tt_ApplyTravel').datagrid('reload');
				$.messager.alert("提示", "操作成功", "info");
			} else {
				$.messager.alert("提示", "操作失败", "error");
			}
		}
	});
}

function reloadDataGrid() {
	$("tt_ApplyTravel").datagrid("reload");
}

/* ################***导出**begin*################### */
function export_excel() {
	// document.getElementById("exportExcel").disabled = true;
	// document.getElementById("exportExcel").value = "正在导出";
	var exportIframe = document.createElement('iframe');
	exportIframe.src = httpUrl + "/applytravel/export.html";

	exportIframe.style.display = 'none';
	document.body.appendChild(exportIframe);
}
/* ################***导出**end*################### */

/* ##########################公用方法##begin############################ */

// 监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 300);
};
// 改变表格和查询表单宽高
function domresize() {
	$('tt_ApplyTravel').datagrid(
			'resize',
			{
				height : $("#body").height()
						- $('#search_areaApplyTravel').height() - 5,
				width : $("#body").width()
			});
	$('#search_areaApplyTravel').panel('resize', {
		width : $("#body").width()
	});
	$('#detailLoanDiv').dialog('resize', {
		height : $("#body").height() - 25,
		width : $("#body").width() - 30
	});
}

/* ##########################公用方法##end############################ */