$(function() {
	/*
	 * #############################################search form
	 * begin#################################
	 */

	$("#searchpathForm #searchButton").on("click", function() {
		var dataUrl = httpUrl + "/path/listPath.html";
		$("#tt_Path").datagrid('options').url = dataUrl;
		$("#tt_Path").datagrid('load', {
			'linename' : $("#searchpathForm #linename").val(),
		});
	});

	$("#searchpathForm #resetButton").on("click", function() {
		$("#searchpathForm").form('reset');
	});

	/*
	 * #############################################search form
	 * end################################# /**
	 */

	/*
	 * ##########################grid init
	 * begin####################################################
	 */
	/*
	 * ##########################grid toolbar
	 * begin#################################################
	 */
	var toolbar_tt = [ {
		iconCls : "icon-edit",
		text : "新增",
		handler : to_addpath
	} ];

	/* ######################grid toolbar end############################## */
	/* ######################grid columns begin##############################  
	var columns_tt = [ [
			{
				field : 'pathid',
				title : 'id',
				width : 100,
				hidden : true
			},
			{
				field : "linename",
				title : "路线名称",
				width : 180,
				align : "center"
			},
			{
				field : "state",
				title : "状态",
				width : 180,
				align : "center",
				formatter : function(value, row, index) {
					if (row.state == "0") {
						return "已开发";
					} else if (row.state == "1") {
						return "马上推出";
					} else if (row.state == "2") {
						return "正在开发";
					}
				}
			},
			{
				field : "remake",
				title : "备注",
				width : 180,
				align : "center"
			},
			{
				field : "操作",
				title : "操作",
				width : 80,
				align : "left",
				formatter : function(value, row, index) {
					var str = '<a href="javascript:void(0);" onclick="to_editpath(\''
							+ row.pathid
							+ '\');">编辑</a> <a href="javascript:void(0);" onclick="deletePath(\''
							+ row.pathid + '\');">删除</a>';
					return str;
				}
			} ] ];
	*/
	var columns_tt = [
	        			[	 				
	  							{field:'pathid',title:'pathid',width:100,hidden:true},						
	  								{field:"linename",title:"路线名称",width:180,align:"center"},
	  								{field:"state",title:"开放状态 ",width:180,align:"center",
	  									formatter : function(value, row, index) {
	  										if (row.state == "0") {
	  											return "已开发";
	  										} else if (row.state == "1") {
	  											return "马上推出";
	  										} else if (row.state == "2") {
	  											return "正在开发";
	  										}
	  									}},
	  								{field:"remake",title:"备注",width:180,align:"center"},
	  								{field:"bannerImg",title:"banner图片",width:180,align:"center"},
	  								{field:"detailImg",title:"详情图片",width:180,align:"center"},
	  								{field:"iconImg",title:"列表小图",width:180,align:"center"},
	  								{field:"starLevel",title:"星级",width:180,align:"center"},
	  								{field:"price",title:"价格",width:180,align:"center"},
	  								{field:"score",title:"评分",width:180,align:"center"},
	  								{field:"outline",title:"概要",width:180,align:"center"},
	  								{field:"detailUrl",title:"详情外部链接	",width:180,align:"center"},
	  					{field:"操作",title:"操作",width:80,align:"left",
	  	 					formatter:function(value,row,index){
	  	 					  var str= '<a href="javascript:void(0);" onclick="to_editpath(\''+row.pathid+'\');">编辑</a>   <a href="javascript:void(0);" onclick="deletePath(\''+row.pathid+'\');">删除</a>';
	  	 					  return str;
	  	 					}
	  	 				}	 				
	  	 			]
	  	 	];
	/* ######################grid columns end############################## */

	$("#tt_Path").datagrid({
		url : httpUrl + "/path/listPath.html?&rand=" + Math.random(),
		height : $("#body").height() - $('#search_areaPath').height() - 10,
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
			'linename' : $("#searchpathForm #linename").val(),
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
function to_addpath() {
	to_editpath('');
	$('#editUserFeedbackDiv').dialog({
		title : "新增",
	});
}

/**
 * 删除
 */

function deletePath(id) {
	if (!id) {
		$.messager.alert("消息", "id不能为空");
		return;
	}
	$.messager.confirm("消息", "确认删除该路线吗，删除后不可恢复", function(r) {
		if (r) {
			$.ajax({
				url : httpUrl + "/path/deletePath.html?id=" + id,
				type : "post",
				data : {},
				success : function(data) {
					if (data.ret == 1) {
						$.messager.alert("消息", "删除成功");
						$('#tt_Path').datagrid('reload');
					} else {
						$.messager.alert("消息", "删除失败，请稍后再试");
					}
				}
			});
		}
	});
}

/**
 * 编辑
 * 
 * @param id
 */
function to_editpath(id) {

	var url = httpUrl + "/path/addPath.html?&rand=" + Math.random() + "&id="
			+ id;
	$('#editPathDiv').dialog({
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
			handler : save_Path
		}, {
			iconCls : "icon-no",
			text : "关闭",
			handler : function() {
				$("#editPathDiv").dialog("close");
			}
		} ]
	});
}

function save_Path() {

	var obj = new FormData();
//	var obj =  $("#editPathForm").serialize();;

	var pathid = $("#editPathForm #pathid").val();
	var linename = $("#editPathForm #linename").val();
	var state = $("#editPathForm #state").datebox('getValue');
	var remake = $("#editPathForm #remake").val();
	
	var starLevel = $("#editPathForm #starLevel").val();
	var price = $("#editPathForm #price").val();
	var score = $("#editPathForm #score").val();
	var outline = $("#editPathForm #outline").val();
	var detailUrl = $("#editPathForm #detailUrl").val();
	
	var bannerImg = document.getElementById("bannerImg").files[0];
	var detailImg = document.getElementById("detailImg").files[0];
	var iconImg = document.getElementById("iconImg").files[0];
	
	
	if (linename == null || linename == "") {
		$.messager.alert("错误", "请填写路线名称");
		return;
	}

	obj.append("pathid", pathid);
	obj.append("linename", linename);
	obj.append("state", state);
	obj.append("remake", remake);
	
	obj.append("starLevel", starLevel);
	obj.append("price", price);
	obj.append("score", score);
	obj.append("outline", outline);
	obj.append("detailUrl", detailUrl);
	
	if(bannerImg){
		obj.append("bannerImg1", bannerImg);
	}
	if(detailImg){
		obj.append("detailImg1", detailImg);
	}
	if(iconImg){
		obj.append("iconImg1", iconImg);
	}
	var url = httpUrl + "/path/savePath.html?&rand=" + Math.random();
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : url,
		data : obj,
		processData : false,
		contentType : false,
		success : function(data) {
			if (data.code === "0") {
				$("#editPathDiv").dialog("close");
				$('#tt_Path').datagrid('reload');
				$.messager.alert("提示", "操作成功", "info");
			} else {
				$.messager.alert("提示", "操作失败", "error");
			}
		}
	});
}

function reloadDataGrid() {
	$("#tt_Path").datagrid("reload");
}

/* ##########################公用方法##begin############################ */

// 监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 300);
};
// 改变表格和查询表单宽高
function domresize() {
	$('tt_Path').datagrid('resize', {
		height : $("#body").height() - $('#search_areaPath').height() - 5,
		width : $("#body").width()
	});
	$('#search_areaPath').panel('resize', {
		width : $("#body").width()
	});
	$('#detailLoanDiv').dialog('resize', {
		height : $("#body").height() - 25,
		width : $("#body").width() - 30
	});
}

/* ##########################公用方法##end############################ */