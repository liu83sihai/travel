var basePath = "/dce-manager";
$(function() {
	/*
	 * #############################################search form
	 * begin#################################
	 */

	$("#searchysNewsForm #searchButton").on("click", function() {
		var dataUrl = basePath + "/ysnews/listYsNews.html";
		$("#tt_YsNews").datagrid('options').url = dataUrl;
		$("#tt_YsNews").datagrid('load', {
			'title' : $("#searchysNewsForm #title").val()
		/*
		 * , 'createName' : $("#searchysNewsForm #createName") .val(),
		 * 'updateName' : $("#searchysNewsForm #updateName") .val(), 'startDate' :
		 * $("#searchysNewsForm #startDate") .datebox('getValue'), 'endDate' :
		 * $("#searchysNewsForm #endDate") .datebox('getValue')
		 */
		});
	});

	$("#searchysNewsForm #resetButton").on("click", function() {
		$("#searchysNewsForm").form('reset');
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
	var toolbar_tt = [ {
		iconCls : "icon-edit",
		text : "新增",
		handler : to_addysNews
	} ];

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
				field : "title",
				title : "标题",
				width : 180,
				align : "center"
			},			
			{ field : "content", title : "内容简介", width : 180, align : "center" },
			{ field : "author", title : "作者", width : 180, align : "center" }, 
			{ field : "topNews", title : "置顶新闻", width : 180, align : "center",
			  formatter : function(value, row, index) { 
				  	if (row.topNews == "0") {
				  			return "未置顶";  } 
				  	else if (row.topNews == "1") {
				  		return "已置顶"; } }
			}, 
			{field : "remark", title : "app显示地址", width : 180, align : "center" },
			{ field : "status", title : "状态", width : 180, align : "center",
			 formatter : function(value, row, index) { if (row.status == "0") {
			  return "未发布"; } else if (row.status == "1") { return "已发布"; } } 
			},			 
			{
				field : "createDate",
				title : "创建日期",
				width : 180,
				align : "center",
				formatter : dateTimeFormatter
			},			
			{
				field : "操作",
				title : "操作",
				width : 180,
				align : "center",
				formatter : function(value, row, index) {
					var str = '<a href="javascript:void(0);" onclick="to_editysNews(\''
							+ row.id
							+ '\');">编辑</a>    <a href="javascript:void(0);" onclick="deleteNotice(\''
							+ row.id + '\');">删除</a>';

					return str;
				}
			} ] ];
	/* ######################grid columns end############################## */

	$("#tt_YsNews").datagrid({
		url : httpUrl + "/ysnews/listYsNews.html?&rand=" + Math.random(),
		height : $("#body").height() - $('#search_areaYsNews').height() - 10,
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
			'searchStr' : $("#searchysNewsForm #searchStr").val(),
			'searchCodeStr' : $("#searchysNewsForm #searchCodeStr").val()
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
function to_addysNews() {
	to_editysNews('');
}
/**
 * 编辑
 * 
 * @param id
 */
function to_editysNews(id) {

	var url = httpUrl + "/ysnews/addYsNews.html?&rand=" + Math.random()
			+ "&id=" + id;
	$('#editYsNewsDiv').dialog({
		title : "修改",
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
			handler : save_YsNews
		}, {
			iconCls : "icon-no",
			text : "关闭",
			handler : function() {
				$("#editYsNewsDiv").dialog("close");
			}
		} ]
	});
}

/**
 * 保存更新
 */
function save_YsNews() {

	var object = new FormData();

	// 获取表单数据
	var id = document.getElementById("id").value;
	var title = $("#editYsNewsForm #title").val();
	var image = $("#editYsNewsForm #image").val();
	var content = $("#editYsNewsForm #content").val();
	var author = document.getElementById("author").value; 
	var topNews = $("#editYsNewsForm #topNews").combobox('getValue'); 
	var remark = document.getElementById("remark").value; 
	var status = $("#editYsNewsForm #status").combobox('getValue');

	if ($.isEmptyObject(title)) {
		$.messager.alert("提示", "新闻标题不能为空");
		return;
	} 

	
	// 校验姓名
	// checkName(author);
	/*
	 * var pattern = new RegExp( "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）;—|{}【】‘；：”“'。，、？]");
	 * if (pattern.test($.trim(author))) { $.messager.alert("提示",
	 * "姓名只能是汉字字母或数字", "error"); return; } if (author.length > 10) {
	 * $.messager.alert("提示", "姓名过长，请您最多输入10个汉字。", "error"); return; } if
	 * (author.length < 2) { $.messager.alert("提示", "姓名必须大于2个汉字", "error");
	 * return; }
	 */



	object.append("id", id);
	object.append("title", title);
	object.append("content", content);
	object.append("author", author);
	object.append("topNews", topNews); 
	object.append("remark", remark);
	object.append("status", status);
	object.append("image", image);
	var url = httpUrl + "/ysnews/saveYsNews.html?&rand=" + Math.random();
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : url,
		data : object,
		processData : false,
		contentType : false,
		success : function(data) {
			if (data.code === "0") {
				$("#editYsNewsDiv").dialog("close");
				$('#tt_YsNews').datagrid('reload');
				$.messager.alert("提示", "操作成功", "info");
			} else {
				$.messager.alert("提示", "操作失败", "error");
			}
		}
	});
}

/**
 * 删除
 */

function deleteNotice(id) {
	if (!id) {
		$.messager.alert("新闻", "id不能为空");
		return;
	}
	$.messager.confirm("新闻", "确认删除该新闻吗，删除后不可恢复", function(r) {
		if (r) {
			$.ajax({
				url : httpUrl + "/ysnews/deleteYsNews.html?id=" + id,
				type : "post",
				data : {},
				success : function(data) {
					if (data.ret == 1) {

						$.messager.alert("新闻", "删除成功");
						$('#tt_YsNews').datagrid('reload');
						$('#tableGrid').datagrid('reload');
					} else {
						$.messager.alert("新闻", "删除失败，请稍后再试");
					}
				}
			});
		}
	});
}

function reloadDataGrid() {
	$("tt_YsNews").datagrid("reload");
}

/* ##########################公用方法##begin############################ */

// 图片校验
function checkType() {

	var fileName = document.getElementById("image").value;

	var seat = fileName.lastIndexOf(".");

	var extension = fileName.substring(seat).toLowerCase();
	var allowed = [ ".jpg", ".gif", ".png", ".jpeg" ];
	for (var i = 0; i < allowed.length; i++) {
		if (allowed[i] != extension) {
			return true;
		}
	}
	alert("不支持" + extension + "格式");
	return false;
}

// 监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 300);
};
// 改变表格和查询表单宽高
function domresize() {
	$('tt_YsNews').datagrid('resize', {
		height : $("#body").height() - $('#search_areaYsNews').height() - 5,
		width : $("#body").width()
	});
	$('#search_areaYsNews').panel('resize', {
		width : $("#body").width()
	});
	$('#detailLoanDiv').dialog('resize', {
		height : $("#body").height() - 25,
		width : $("#body").width() - 30
	});
}

/* ##########################公用方法##end############################ */