
$(function(){
/*#############################################search form begin#################################*/	
		
	$("#searchactivityForm #searchButton").on("click",function(){
		$("#tt_Activity").datagrid('load',{
			'searchStr': $("#searchactivityForm #searchStr").val(),
			'searchCodeStr':$("#searchactivityForm #searchCodeStr").val()		
		});
	});
	
	$("#searchactivityForm #resetButton").on("click",function(){
		$("#searchactivityForm").form('reset');
	});
	
/*#############################################search form end#################################*/		
	
/*##########################grid init begin####################################################*/
/*##########################grid toolbar begin#################################################*/
	var toolbar_tt = [
					{
						iconCls:"icon-edit",
						text:"新增",
						handler:to_addactivity
					}
	          	];
	
/*######################grid toolbar end##############################*/
/*######################grid columns begin##############################*/
	var columns_tt = [
      			[	 				
							{field:'id',title:'id',width:100,hidden:true},						
								{field:"userId",title:"用户ID",width:180,align:"center"},
								{field:"synopsis",title:"描述",width:180,align:"center"},
								{field:"content",title:"内容",width:180,align:"center"},
								{field:"images",title:"图片",width:180,align:"center"},
								{field:"hitNum",title:"点赞数",width:180,align:"center"},
								{field:"createDate",title:"创建时间",width:180,align:"center",formatter:dateTimeFormatter},
								{field:"createName",title:"创建人",width:180,align:"center"},
								{field:"modifyDate",title:"更新时间",width:180,align:"center",formatter:dateTimeFormatter},
								{field:"modifyName",title:"更新人",width:180,align:"center"},
								{field:"status",title:"状态",width:180,align:"center"},
								{field:"remark",title:"备注",width:180,align:"center"},
					{field:"操作",title:"操作",width:80,align:"left",
	 					formatter:function(value,row,index){
	 					  var str= '<a href="javascript:void(0);" onclick="to_editactivity(\''+row.id+'\');">编辑</a>   <a href="javascript:void(0);" onclick="to_delActivity(\''+row.id+'\');">删除</a>';
	 					  return str;
	 					}
	 				}	 				
	 			]
	 	];
/*######################grid columns end##############################*/
	
	$("#tt_Activity").datagrid({
		url:httpUrl+"/activity/listActivity.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_areaActivity').height()-10,
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
			'searchStr': $("#searchactivityForm #searchStr").val(),
			'searchCodeStr':$("#searchactivityForm #searchCodeStr").val()
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
function to_addactivity(){
	to_editactivity('');
}

/**
 * 删除
 */

function to_delActivity(id) {
	if (!id) {
		$.messager.alert("消息", "id不能为空");
		return;
	}
	$.messager.confirm("消息", "确认删除吗，删除后不可恢复", function(r) {
		if (r) {
			$.ajax({
				url : httpUrl + "/activity/deleteActivity.html?id=" + id,
				type : "post",
				data : {},
				success : function(data) {
					if (data.ret == 1) {
						$.messager.alert("消息", "删除成功");
						$('#tt_Activity').datagrid('reload');
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
 * @param id
 */
function to_editactivity(id){
	
	var url = httpUrl+"/activity/addActivity.html?&rand=" + Math.random()+"&id="+id;
	$('#editActivityDiv').dialog({
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
					handler:save_Activity
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editActivityDiv").dialog("close");
				}
		}]
	});
}

function save_Activity(){
	var formdata = $("#editActivityForm").serialize();
	console.info("formdata");
	console.info(formdata);
	var  url =httpUrl+"/activity/saveActivity.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:$("#editActivityForm").serialize(),
		 success: function(data){ 
			 if(data.code ==="0"){
				 $("#editActivityDiv").dialog("close");
				 $('#tt_Activity').datagrid('reload');
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}


function reloadDataGrid()
{
	$("tt_Activity").datagrid("reload");
}




/*##########################公用方法##begin############################*/

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('tt_Activity').datagrid('resize',{  
		height:$("#body").height()-$('#search_areaActivity').height()-5,
		width:$("#body").width()
	});
	$('#search_areaActivity').panel('resize',{
		width:$("#body").width()
	});
	$('#detailLoanDiv').dialog('resize',{  
		height:$("#body").height()-25,
		width:$("#body").width()-30
	});
}
 
/*##########################公用方法##end############################*/