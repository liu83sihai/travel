var basePath="/dce-manager";
$(function(){
/*#############################################search form begin#################################*/
    //产品类型

	$("#searchForm #searchButton").on("click",function(){
		var dataUrl = basePath+ "/ethcenter/list.html";
		$("#ethereCenterCountTable").datagrid('options').url = dataUrl;
		$("#ethereCenterCountTable").datagrid('load',{
//			'userName': $("#searchForm #userName").val(),
//			'startDate':$("#searchForm #user_reg_startDate").datebox('getValue'),
//			'endDate':$("#searchForm #user_reg_endDate").datebox('getValue')
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
	                {
	          			iconCls: 'icon-edit',
	          			text:'添加平台以太坊账户',
	          			handler:addEthUserAccount
	          		}
	          	];

	/*######################grid toolbar end##############################*/
	var columns_tt = [
      			[
	 				{field:"no",title:"编号",width:30,align:"center"},
	 				{field:"account",title:"以太坊账户",width:80,align:"center"},
	 				{field:"isDefault",title:"是否默认账户",width:30,align:"center",
	 					formatter:function(value,row,index){
	 						if(value == 1){
	 							return "是";
	 						}
	 						return "否";
	 					}
	 				},
	 				{field:"edit",title:"操作",width:80,align:"center",
	 					formatter:function(value,row,index){
	 						 return '<a href="javascript:void(0);"  onclick="setDefault(\''+row.no+'\');">设置为默认账户</a>   ' +
	 						 '<a href="javascript:void(0);"  onclick="send(\''+row.no+'\');">发送</a>';
	 					}
	 				}
	 			]
	 	];

	/*######################grid columns end##############################*/

	$("#ethereCenterCountTable").datagrid({
		url: basePath+"/ethcenter/list.html",
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
		idField:"no",
//		frozenColumns : [[{field:'ck',checkbox:true}]],
		columns:columns_tt,
		toolbar:toolbar_tt
	});

/*##########################grid init end###################################################*/
});


function reloadDataGrid()
{
	$("#ethereCenterCountTable").datagrid("reload");
}

function setDefault(accountNo) {
   var msg = "是否确认设置为默认账户？";
   
   $.messager.confirm("确认", msg , function (r) {  
       if (r) {  
       	$.ajax({
       		url:basePath+"/ethcenter/default.html",
       		type:"post",
       		dataType: 'json',
       		data:{
       			"accountNo":accountNo
       		},
       		success:function(ret){
       			if(ret.code==0){
       				$.messager.alert("成功", "设置为默认账户成功");
       				reloadDataGrid();
       			}else{
       				$.messager.alert("失败",ret.msg);
       			}
       		}
       	});
       }  
   });
   
}

function send(no){
	
	var url = basePath+"/ethcenter/queryPlatFormEth.html?accountNo="+ no;
	$('#editAccountCenterDiv').dialog({
		title: "设置用户级别",
		width: 600,
		height: 400,
		closed: false,
		closable:false,
		cache: false,
		href: url,
		modal: true,
		toolbar:[
				{
					iconCls:"icon-save",text:"提交",
					handler:save_edit
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editAccountCenterDiv").dialog("close");
				}
		}]
	});
}

function save_edit(){
	
	var centerAccount = $("#centerAccount").val();
	var centerAccountBalance = $("#centerAccountBalance").val();
	var receiveAddress = $("#receiveAddress").val();
	var amount = $("#amount").val();
	var password = $("#password").val();
	
	if (receiveAddress == "" || receiveAddress == undefined) {
		$.messager.alert("信息","请输入接收账号");
		return false;
	}
	if (amount == "" || amount == undefined) {
		$.messager.alert("信息","请输入发送金额");
        return false;
    }
	if (password == "" || password == undefined) {
		$.messager.alert("信息","请输入平台以太坊账号密码");
        return false;
    }
	if (parseFloat(amount) > parseFloat(centerAccountBalance)) {
		$.messager.alert("信息","发送金额不能大于平台以太坊账号余额");
		return false;
	}
	
	var msg = "是否确认发送金额？"; 
	
	$.messager.confirm("确认", msg , function (r) {  
	       if (r) {  
	       	$.ajax({
	       		url:basePath+"/ethcenter/tranPlatFormEth.html",
	       		type:"post",
	       		dataType: 'json',
	       		data:{
	       			"centerAccount":centerAccount,
	       			"receiveAddress":receiveAddress,
	       			"amount":amount,
	       			"password":password
	       		},
	       		success:function(ret){
	       			if(ret.code==0){
	       				$.messager.alert("成功", "发送成功");
	       				$("#editAccountCenterDiv").dialog("close");
	       				reloadDataGrid();
	       			}else{
	       				$.messager.alert("失败",ret.msg);
	       			}
	       		}
	       	});
	       }  
	   });
	
}


function addEthUserAccount(){
	
	var url = basePath+"/ethcenter/addPlatFormEth.html";
	$('#editAccountCenterDiv').dialog({
		title: "设置用户级别",
		width: 600,
		height: 400,
		closed: false,
		closable:false,
		cache: false,
		href: url,
		modal: true,
		toolbar:[
				{
					iconCls:"icon-save",text:"提交",
					handler:save_add
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editAccountCenterDiv").dialog("close");
				}
		}]
	});
}

function save_add(){
	var centerAccount = $("#centerAccount").val();
	var centerPassword = $("#centerPassword").val();
	
	if (centerAccount == "" || centerAccount == undefined) {
		$.messager.alert("信息","请输入中心账户编码");
		return false;
	}
	if (centerPassword == "" || centerPassword == undefined) {
		$.messager.alert("信息","请输入中心账户密码");
        return false;
    }
	
	$.ajax({
   		url:basePath+"/ethcenter/setCenterAcc.html",
   		type:"post",
   		dataType: 'json',
   		data:{
   			"centerAccount":centerAccount,
   			"password":centerPassword
   		},
   		success:function(ret){
   			if(ret.code==0){
   				$.messager.alert("成功", "发送成功");
   				$("#editAccountCenterDiv").dialog("close");
   				reloadDataGrid();
   			}else{
   				$.messager.alert("失败",ret.msg);
   			}
   		}
   	});
}
/*##########################公用方法##begin############################*/
/**
 * 得到选中的行
 * @returns {String}
 */
function get_ids(){
	var ids = '';
	var rows = $('#ethereCenterCountTable').datagrid('getChecked');
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
	var rows = $('#ethereCenterCountTable').datagrid('getChecked');
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
	$('#ethereCenterCountTable').datagrid('resize',{
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
