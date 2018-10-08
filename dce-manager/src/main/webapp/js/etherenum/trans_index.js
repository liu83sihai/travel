var basePath="/dce-manager";
$(function(){
/*#############################################search form begin#################################*/
    //产品类型

	$("#searchForm #searchButton").on("click",function(){
		var dataUrl = basePath+ "/ethertrans/list.html";
		$("#ethereTransTable").datagrid('options').url = dataUrl;
		$("#ethereTransTable").datagrid('load',{
			'userName': $("#searchForm #userName").val(),
			'startDate':$("#searchForm #user_reg_startDate").datebox('getValue'),
			'endDate':$("#searchForm #user_reg_endDate").datebox('getValue')
		});
	});

	$("#searchForm #resetButton").on("click",function(){
		$("#searchForm").form('reset');
	});

/*#############################################search form end#################################*/

/*##########################grid init begin###################################################*/
	var toolbar_tt = [
	                /* {
	          			iconCls: 'icon-excel',
	          			text:'导出',
	          			handler:export_excel
	          		},'-',{
	          			iconCls: 'icon-edit',
	          			text:'同步放款数据',
	          			handler:syn_loan_data
	          		}*/
	          	];

	/*######################grid toolbar end##############################*/
	var columns_tt = [
      			[
	 			 	{field:'userId',title:'用户id',width:30,halign:"center", align:"left",hidden:true},
	 				{field:"user_name",title:"用户名",width:30,align:"center"},
	 				{field:"fromAccount",title:"转出账户地址",width:80,align:"center"},
	 				{field:"toAccount",title:"转入账户地址",width:80,align:"center"},
	 				{field:"amount",title:"转账以太坊数额",width:60,align:"center",
	 					formatter:function(value,row,index){
	 						if(value.indexOf('.') != -1){
	 							var arr = value.split('.');
	 							var pointStr = arr[1];
	 							if(pointStr.length > 8){
	 								pointStr = pointStr.substring(0,8);
	 							}
	 							return arr[0] + "." + pointStr;
	 						}else{
	 							return value;
	 						}
	 					}
	 				},
	 				{field:"actualAmount",title:"实际转出金额",width:60,align:"center",
	 					formatter:function(value,row,index){
	 						if(value.indexOf('.') != -1){
	 							var arr = value.split('.');
	 							var pointStr = arr[1];
	 							if(pointStr.length > 8){
	 								pointStr = pointStr.substring(0,8);
	 							}
	 							return arr[0] + "." + pointStr;
	 						}else{
	 							return value;
	 						}
	 					}
	 				},
	 				{field:"pointAmount",title:"转账现金币数额",width:60,align:"center"},
	 				{field:"gas",title:"交易费",width:30,align:"center"},
	 				{field:"gasLimit",title:"最大交易费",width:30,align:"center"},
	 				{field:"ActualGas",title:"实际交易费",width:30,align:"center"},
	 				{field:"confirmed",title:"是否确认",width:20,align:"center"},
	 				{field:"status",title:"状态",width:20,align:"center"},
	 				{field:"type",title:"交易类型",width:80,align:"center"},
	 				{field:"createTime",title:"交易时间",width:80,align:"center",
	 					formatter:function(value,row,index){
	 						if(value > 0){
	 							return dateTimeFormatter(value);
	 						}
	 						return value;
	 					}
	 				}
	 			]
	 	];

	/*######################grid columns end##############################*/

	$("#ethereTransTable").datagrid({
		url: basePath+"/ethertrans/list.html",
		height:$("#body").height()-$('#search_area').height()-10,
		width:$("#body").width(),
		rownumbers:true,
		fitColumns:true,
		singleSelect:false,//配合根据状态限制checkbox
		autoRowHeight:true,
		striped:true,
		checkOnSelect:false,//配合根据状态限制checkbox
		selectOnCheck:false,//配合根据状态限制checkbox
		pagination:true,
		showPageList:true,
		pageSize:20,
		pageList:[10,20,30],
		idField:"loanId",
//		frozenColumns : [[{field:'ck',checkbox:true}]],
		columns:columns_tt,
		toolbar:toolbar_tt
	});

/*##########################grid init end###################################################*/
});


function reloadDataGrid()
{
	$("#ethereTransTable").datagrid("reload");
}


/*##########################公用方法##begin############################*/
/**
 * 得到选中的行
 * @returns {String}
 */
function get_ids(){
	var ids = '';
	var rows = $('#ethereTransTable').datagrid('getChecked');
	if(rows.length == 0){
		$.messager.alert("提示","请选择需要操作的数据", "info");
		return;
	}else{
		for(var i=0; i<rows.length; i++){
			ids += rows[i].loanId + ',';
		}
		ids = ids.substring(0, ids.length - 1);
		return ids;
	}
}
/**
 * 得到一条数据
 * @returns {String}
 */
function get_id(){
	var rows = $('#ethereTransTable').datagrid('getChecked');
	if(rows.length == 0){
		$.messager.alert("提示","请选择需要操作的数据","info");
		return;
	}else if(rows.length > 1){
		$.messager.alert("提示","每次只能操作一条数据","info");
		return;
	}else{
		return rows[0].loanId;
	}
}

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('#ethereTransTable').datagrid('resize',{
		height:$("#body").height()-$('#search_area').height()-5,
		width:$("#body").width()
	});
	$('#search_area').panel('resize',{
		width:$("#body").width()
	});
	$('#detailLoanDiv').dialog('resize',{
		height:$("#body").height()-25,
		width:$("#body").width()-30
	});
}
