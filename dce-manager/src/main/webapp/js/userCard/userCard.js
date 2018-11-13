﻿
$(function(){
/*#############################################search form begin#################################*/	
		
	$("#searchuserCardForm #searchButton").on("click",function(){
		$("#tt_UserCard").datagrid('load',{
			'userName': $("#searchuserCardForm #searchStr").val(),
			'mobile':$("#searchuserCardForm #searchCodeStr").val()		
		});
	});
	
	$("#searchuserCardForm #resetButton").on("click",function(){
		$("#searchuserCardForm").form('reset');
	});
	
/*#############################################search form end#################################*/		
	
/*##########################grid init begin####################################################*/
/*##########################grid toolbar begin#################################################*/
	var toolbar_tt = [
					{
						iconCls:"icon-edit",
						text:"新增",
						handler:to_adduserCard
					}
	          	];
	
/*######################grid toolbar end##############################*/
/*######################grid columns begin##############################*/
	var columns_tt = [
      			[	 				
							{field:'id',title:'id',width:100,hidden:true},						
								{field:"userId",title:"用户ID",width:180,align:"center"},
								{field:"userName",title:"用户名",width:180,align:"center"},
								{field:"mobile",title:"用户手机",width:180,align:"center"},
								{field:"orderNo",title:"流水单号",width:180,align:"center"},
								{field:"userLevel",title:"用户等级",width:180,align:"center"},
								{field:"sex",title:"性别",width:180,align:"center"},
								{field:"cardNo",title:"第三方卡号",width:180,align:"center"},
								{field:"bankNo",title:"银行卡号",width:180,align:"center"},
								{field:"idNumber",title:"身份证号",width:180,align:"center"},
								{field:"remark",title:"备注",width:180,align:"center"},
								{field:"createDate",title:"创建时间",width:180,align:"center",formatter:dateTimeFormatter},
								{field:"updateDate",title:"修改时间",width:180,align:"center",formatter:dateTimeFormatter},
								{field:"status",title:"状态",width:180,align:"center"},
					{field:"操作",title:"操作",width:80,align:"left",
	 					formatter:function(value,row,index){
	 					  var str= ' <a href="javascript:void(0);" onclick="to_delUserCard(\''+row.id+'\',\''+row.status+'\');">重新激活</a>';
	 					  return str;
	 					}
	 				}	 				
	 			]
	 	];
/*######################grid columns end##############################*/
	
	$("#tt_UserCard").datagrid({
		url:httpUrl+"/userCard/listUserCard.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_areaUserCard').height()-10,
		width:$("#body").width(),
		rownumbers:true,
		fitColumns:true,
		singleSelect:false,//配合根据状态限制checkbox
		autoRowHeight:true,
		striped:true,
		checkOnSelect:false,//配合根据状态限制checkbox
		selectOnCheck:false,//配合根据状态限制checkbox
		loadFilter : function(data){
			return {
				'rows' : data.datas,
				'total' : data.total,
				'pageSize' : data.pageSize,
				'pageNumber' : data.page
			};
		},
		pagination:true,
		showPageList:true,
		pageSize:20,
		pageList:[10,20,30],
		idField:"id",
		columns:columns_tt,
		toolbar:toolbar_tt,
		queryParams:{
			'searchStr': $("#searchuserCardForm #searchStr").val(),
			'searchCodeStr':$("#searchuserCardForm #searchCodeStr").val()
		},
		onLoadSuccess:function(data){//根据状态限制checkbox
			
		}
	});
	
	/*$(window).resize(function (){
		domresize();
	 }); */
/*##########################grid init end###################################################*/
});


/**
 * 新增
 * @param id
 */
function to_adduserCard(){
	to_edituserCard('');
}

/**
 * 删除
 */

function to_delUserCard(id,status) {
	if (!id) {
		$.messager.alert("消息", "id不能为空");
		return;
	}
	if (1 == status) {
		$.messager.alert("消息", "当前卡号已激活");
		return;
	}
	$.messager.confirm("消息", "确认重新激活吗?", function(r) {
		if (r) {
			$.ajax({
				url : httpUrl + "/userCard/deleteUserCard.html?id=" + id,
				type : "post",
				data : {},
				success : function(data) {
					if (data.ret == 1) {
						$.messager.alert("消息", "激活成功");
						$('#tt_UserCard').datagrid('reload');
					} else {
						$.messager.alert("消息", "激活失败，请稍后再试");
					}
				}
			});
		}
	});
}


/**
 * 编辑
 * @param id
 */
function to_edituserCard(id){
	
	var url = httpUrl+"/userCard/addUserCard.html?&rand=" + Math.random()+"&id="+id;
	$('#editUserCardDiv').dialog({
		title: "新增",
		width: 760,
		height: 500,
		closed: false,
		closable:false,
		cache: false,
		href: url,
		modal: true,
		toolbar:[
				{
					iconCls:"icon-save",text:"保存",
					handler:save_UserCard
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editUserCardDiv").dialog("close");
				}
		}]
	});
}

function save_UserCard(){
	var formdata = $("#editUserCardForm").serialize();
	console.info("formdata");
	console.info(formdata);
	var  url =httpUrl+"/userCard/saveUserCard.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:$("#editUserCardForm").serialize(),
		 success: function(data){ 
			 if(data.code ==="0"){
				 $("#editUserCardDiv").dialog("close");
				 $('#tt_UserCard').datagrid('reload');
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}


function reloadDataGrid()
{
	$("tt_UserCard").datagrid("reload");
}




/*##########################公用方法##begin############################*/

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('tt_UserCard').datagrid('resize',{  
		height:$("#body").height()-$('#search_areaUserCard').height()-5,
		width:$("#body").width()
	});
	$('#search_areaUserCard').panel('resize',{
		width:$("#body").width()
	});
	$('#detailLoanDiv').dialog('resize',{  
		height:$("#body").height()-25,
		width:$("#body").width()-30
	});
}
 
/*##########################公用方法##end############################*/