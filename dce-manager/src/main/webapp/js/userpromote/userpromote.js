$(function() {
	/*
	 * #############################################search form
	 * begin#################################
	 */

	$("#searchuserPromoteForm #searchButton").on("click", function() {
		$("#tt_UserPromote").datagrid('load', {
			'searchStr' : $("#searchuserPromoteForm #searchStr").val(),
			'searchCodeStr' : $("#searchuserPromoteForm #searchCodeStr").val()
		});
	});

	$("#searchuserPromoteForm #resetButton").on("click", function() {
		$("#searchuserPromoteForm").form('reset');
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
	var toolbar_tt = [  ];

	/* ######################grid toolbar end############################## */
	/* ######################grid columns begin############################## */
	var columns_tt = [ [
			{
				field : 'promoteId',
				title : 'id',
				width : 100,
				hidden : true
			},
			{
				field : "userLevel",
				title : "当前用户等级",
				width : 180,
				align : "center"
			},
			{
				field : "promoteLevel",
				title : "升级等级",
				width : 180,
				align : "center"
			},
			{
				field : "minQty",
				title : "购买最小数量",
				width : 180,
				align : "center"
			},
			{
				field : "maxQty",
				title : "购买最大数量",
				width : 180,
				align : "center"
			},
			{
				field : "操作",
				title : "操作",
				width : 80,
				align : "left",
				formatter : function(value, row, index) {
					var str = '<a href="javascript:void(0);" onclick="to_edituserPromote(\''
							+ row.promoteId + '\');">编辑</a>';
					return str;
				}
			} ] ];
	/* ######################grid columns end############################## */

	$("#tt_UserPromote")
			.datagrid(
					{
						url : httpUrl
								+ "/userpromote/listUserPromote.html?&rand="
								+ Math.random(),
						height : $("#body").height()
								- $('#search_areaUserPromote').height() - 10,
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
						toolbar : toolbar_tt,
						queryParams : {
							'searchStr' : $("#searchuserPromoteForm #searchStr")
									.val(),
							'searchCodeStr' : $(
									"#searchuserPromoteForm #searchCodeStr")
									.val()
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
/*function to_adduserPromote() {
	to_edituserPromote("");
}*/
/**
 * 编辑
 * 
 * @param id
 */
function to_edituserPromote(id) {

	var url = httpUrl + "/userpromote/addUserPromote.html?&rand="
			+ Math.random() + "&id=" + id;
	$('#editUserPromoteDiv').dialog({
		title : "编辑用户升级管理",
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
			handler : save_UserPromote
		}, {
			iconCls : "icon-no",
			text : "关闭",
			handler : function() {
				$("#editUserPromoteDiv").dialog("close");
			}
		} ]
	});
}

function save_UserPromote() {
	var formdata = $("#editUserPromoteForm").serialize();
	console.info("formdata");
	console.info(formdata);
	var url = httpUrl + "/userpromote/saveUserPromote.html?&rand="
			+ Math.random();
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : url,
		data : $("#editUserPromoteForm").serialize(),
		success : function(data) {
			if (data.code === "0") {
				$("#editUserPromoteDiv").dialog("close");
				$('#tt_UserPromote').datagrid('reload');
				$.messager.alert("提示", "操作成功", "info");
			} else {
				$.messager.alert("提示", "操作失败", "error");
			}
		}
	});
}

function reloadDataGrid() {
	$("tt_UserPromote").datagrid("reload");
}

/* ##########################公用方法##begin############################ */

// 监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 300);
};
// 改变表格和查询表单宽高
function domresize() {
	$('tt_UserPromote').datagrid(
			'resize',
			{
				height : $("#body").height()
						- $('#search_areaUserPromote').height() - 5,
				width : $("#body").width()
			});
	$('#search_areaUserPromote').panel('resize', {
		width : $("#body").width()
	});
	$('#detailLoanDiv').dialog('resize', {
		height : $("#body").height() - 25,
		width : $("#body").width() - 30
	});
}

/* ##########################公用方法##end############################ */