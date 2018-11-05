
$(function(){
/*#############################################search form begin#################################*/	
		
	$("#searchagencyForm #searchButton").on("click",function(){
		$("#tt_Agency").datagrid('load',{
			'searchStr': $("#searchagencyForm #searchStr").val(),
			'searchCodeStr':$("#searchagencyForm #searchCodeStr").val()		
		});
	});
	
	$("#searchagencyForm #resetButton").on("click",function(){
		$("#searchagencyForm").form('reset');
	});
	
/*#############################################search form end#################################*/		
	
/*##########################grid init begin####################################################*/
/*##########################grid toolbar begin#################################################*/
	var toolbar_tt = [
					{
						iconCls:"icon-edit",
						text:"新增",
						handler:to_addagency
					}
	          	];
	
/*######################grid toolbar end##############################*/
/*######################grid columns begin##############################*/
	var columns_tt = [
      			[	 				
							{field:'id',title:'id',width:100,hidden:true},						
								{field:"userId",title:"用户ID",width:80,align:"center"},
								{field:"city",title:"城市",width:150,align:"center"},
								{field:"cityCode",title:"城市代码",width:150,align:"center"},
								{field:"createDate",title:"创建时间",width:120,align:"center",formatter:dateTimeFormatter},
								{field:"createName",title:"创建人",width:120,align:"center"},
								{field:"modifyDate",title:"更新时间",width:120,align:"center",formatter:dateTimeFormatter},
								{field:"modifyName",title:"更新人",width:120,align:"center"},
								{field:"status",title:"状态",width:60,align:"center"},
								{field:"remark",title:"备注",width:80,align:"center"},
					{field:"操作",title:"操作",width:180,align:"left",
	 					formatter:function(value,row,index){
	 					  var str= '<a href="javascript:void(0);" onclick="to_editagency(\''+row.userId+'\');">编辑</a>   <a href="javascript:void(0);" onclick="to_auditAgency(\''+row.id+'\',\''+row.status+'\');">审核</a>   <a href="javascript:void(0);" onclick="to_delAgency(\''+row.userId+'\');">删除</a>';
	 					  return str;
	 					}
	 				}	 				
	 			]
	 	];
/*######################grid columns end##############################*/
	
	$("#tt_Agency").datagrid({
		url:httpUrl+"/agency/listAgency.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_areaAgency').height()-10,
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
			'searchStr': $("#searchagencyForm #searchStr").val(),
			'searchCodeStr':$("#searchagencyForm #searchCodeStr").val()
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
function to_addagency(){
	to_editagency('');
}

/**
 * 删除
 */

function to_delAgency(id) {
	if (!id) {
		$.messager.alert("消息", "id不能为空");
		return;
	}
	$.messager.confirm("消息", "确认删除吗，删除后不可恢复", function(r) {
		if (r) {
			$.ajax({
				url : httpUrl + "/agency/deleteAgency.html?id=" + id,
				type : "post",
				data : {},
				success : function(data) {
					if (data.ret == 1) {
						$.messager.alert("消息", "删除成功");
						$('#tt_Agency').datagrid('reload');
					} else {
						$.messager.alert("消息", "删除失败，请稍后再试");
					}
				}
			});
		}
	});
}
/**
 * 删除
 */

function to_auditAgency(id,status) {
	if (!id) {
		$.messager.alert("消息", "id不能为空");
		return;
	}

	if (!status == 1) {
		$.messager.alert("消息", "当前代理非待审核状态");
		return;
	}
	$.messager.confirm("消息", "确认审核吗，审核后不可恢复", function(r) {
		if (r) {
			$.ajax({
				url : httpUrl + "/agency/auditAgency.html?id=" + id,
				type : "post",
				data : {},
				success : function(data) {
					if (data.ret == 1) {
						$.messager.alert("消息", "审核成功");
						$('#tt_Agency').datagrid('reload');
					} else {
						if (data.ret == -1) {
							$.messager.alert("消息", "当前代理非待审核状态");
						}
						if (data.ret == -2) {
							$.messager.alert("消息", "前用户已申请代理");
						}
						if (data.ret == -3) {
							$.messager.alert("消息", "前区域已有用户申请");
						}
						
						$.messager.alert("消息", "审核失败，请稍后再试");
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
function to_editagency(id){
	
	var url = httpUrl+"/agency/addAgency.html?&rand=" + Math.random()+"&id="+id;
	$('#editAgencyDiv').dialog({
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
					handler:save_Agency
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editAgencyDiv").dialog("close");
				}
		}]
	});
}

function save_Agency(){
	var formdata = $("#editAgencyForm").serialize();
	console.info("formdata");
	console.info(formdata);
	var  url =httpUrl+"/agency/saveAgency.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:$("#editAgencyForm").serialize(),
		 success: function(data){ 
			 if(data.code ==="0"){
				 $("#editAgencyDiv").dialog("close");
				 $('#tt_Agency').datagrid('reload');
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}


function reloadDataGrid()
{
	$("tt_Agency").datagrid("reload");
}




/*##########################公用方法##begin############################*/

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('tt_Agency').datagrid('resize',{  
		height:$("#body").height()-$('#search_areaAgency').height()-5,
		width:$("#body").width()
	});
	$('#search_areaAgency').panel('resize',{
		width:$("#body").width()
	});
	$('#detailLoanDiv').dialog('resize',{  
		height:$("#body").height()-25,
		width:$("#body").width()-30
	});
}
 
/*##########################公用方法##end############################*/