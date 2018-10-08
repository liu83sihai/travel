var basePath="/dce-manager";
$(function() {
	/*
	 * #############################################search form
	 * begin#################################
	 */

	$("#searchregionalawardsForm #searchButton").on("click",function() {
		alert(document.getElementById("rewardbalance").value+"****"+document.getElementById("algebra").value);
		        var dataUrl = basePath+"/regionalawards/listRegionalawards.html";
		        $("#tt_Regionalawards").datagrid('options').url = dataUrl;
				$("#tt_Regionalawards").datagrid('load',{
					'rewardbalance':document.getElementById("rewardbalance").value,
					'algebra':document.getElementById("algebra").value	
							});
			});

	$("#searchregionalawardsForm #resetButton").on("click", function() {
		$("#searchregionalawardsForm").form('reset');
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
	var toolbar_tt = [ /*{
		iconCls : "icon-edit",
		text : "新增",
		handler : to_addregionalawards
	}*/ ];

	/* ######################grid toolbar end############################## */
	/* ######################grid columns begin############################## */
	var columns_tt = [ [
			{
				field : 'rewardsareaid',
				title : 'id',
				width : 100,
				hidden : true
			},
			{
				field : "rewardbalance",
				title : "区域奖励金额",
				width : 180,
				align : "center"
			},	
			{
				field : "remark",
				title : "备注",
				width : 180,
				align : "center"
			},
			{
				field : "操作",
				title : "操作",
				width : 180,
				align : "left",
				formatter : function(value, row, index) {
					var str = '<a href="javascript:void(0);" onclick="to_editregionalawards(\''
							+ row.rewardsareaid + '\');">编辑</a>';
					/*<a href="javascript:void(0);" onclick="deleteRegionalawards(\''
						+ row.rewardsareaid + '\');">删除</a>
*/					return str;
				}
			} ] ];
	/* ######################grid columns end############################## */

	$("#tt_Regionalawards").datagrid(
			{
				url : httpUrl
						+ "/regionalawards/listRegionalawards.html?&rand="
						+ Math.random(),
				height : $("#body").height()
						- $('#search_areaRegionalawards').height() - 10,
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
					'searchStr' : $("#searchregionalawardsForm #searchStr")
							.val(),
					'searchCodeStr' : $(
							"#searchregionalawardsForm #searchCodeStr").val()
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
function to_addregionalawards() {
	to_editregionalawards('');
}
/**
 * 编辑
 * 
 * @param id
 */
function to_editregionalawards(id) {
	// alert("id-------" + id);
	var url = httpUrl + "/regionalawards/addRegionalawards.html?&rand="
			+ Math.random() + "&id=" + id;
	$('#editRegionalawardsDiv').dialog({
		title : "新增",
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
			handler : save_Regionalawards
		}, {
			iconCls : "icon-no",
			text : "关闭",
			handler : function() {
				$("#editRegionalawardsDiv").dialog("close");
			}
		} ]
	});
}

/**
 * 删除
 */

function deleteRegionalawards(id) {
	if (!id) {
		$.messager.alert("id", "id不能为空");
		return;
	}
	$.messager.confirm("区域奖励", "确认删除该奖励吗，删除后不可恢复", function(r) {
		if (r) {
			$.ajax({
				url : httpUrl + "/regionalawards/deleteRegionalawards.html?id=" + id,
				type : "post",
				data : {},
				success : function(data) {
					if (data.ret == 1) {
						$.messager.alert("区域奖励", "删除成功");
						$('#tableGrid').datagrid('reload');
					} else {
						$.messager.alert("区域奖励", "删除失败，请稍后再试");
					}
				}
			});
		}
	});
}


// 保存更新
function save_Regionalawards() {
	var rewardbalance=$("#editRegionalawardsForm #rewardbalance").val();
	var algebra=$("#editRegionalawardsForm #algebra").val();
	var flag="";
	 var reg = /^[1-9]\d*$/;
	    	
	// 参数为空校验
	if($.isEmptyObject(rewardbalance)){
		$.messager.alert("提示","区域奖励不能为空");
		return flag=false;
	}
	
	/*if($.isEmptyObject(algebra)){
		$.messager.alert("提示","推荐代数不能为空");
		return flag=false;
	}*/
	
	if (!reg.test(rewardbalance)) {
    	$.messager.alert("提示", "区域奖励金额请填写整数", "error");
    	return flag=false;

    }
	
	/*if (!reg.test(algebra)) {
    	$.messager.alert("提示", "推荐代数请填写整数", "error");
    	return flag=false;

    }*/
	
	var formdata = $("#editRegionalawardsForm").serialize();
	console.info("formdata");
	console.info(formdata);
	var url = httpUrl + "/regionalawards/saveRegionalawards.html?&rand="
			+ Math.random();
	$.ajax({
		type : 'POST',
		dataType : 'json',
		url : url,
		data : $("#editRegionalawardsForm").serialize(),
		success : function(data) {
			$('#tt_Regionalawards').datagrid('reload');
			if (data.code === "0") {
				$("#editRegionalawardsDiv").dialog("close");
				$.messager.alert("提示", "操作成功", "info");
			} else {
				$.messager.alert("提示", "操作失败", "error");
			}
		}
	});
}

function reloadDataGrid() {
	$("tt_Regionalawards").datagrid("reload");
}

/* ##########################公用方法##begin############################ */





// 监听窗口大小变化
window.onresize = function() {
	setTimeout(domresize, 300);
};
// 改变表格和查询表单宽高
function domresize() {
	$('tt_Regionalawards').datagrid(
			'resize',
			
			{
				height : $("#body").height()
						- $('#search_areaRegionalawards').height() - 5,
				width : $("#body").width()
			});
	$('#search_areaRegionalawards').panel('resize', {
		width : $("#body").width()
	});
	$('#detailLoanDiv').dialog('resize', {
		height : $("#body").height() - 25,
		width : $("#body").width() - 30
	});
}

/* ##########################公用方法##end############################ */