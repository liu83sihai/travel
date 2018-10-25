
$(function(){
/*#############################################search form begin#################################*/	
		
	$("#searchactivityGoodForm #searchButton").on("click",function(){
		$("#tt_ActivityGood").datagrid('load',{
			'searchStr': $("#searchactivityGoodForm #searchStr").val(),
			'searchCodeStr':$("#searchactivityGoodForm #searchCodeStr").val()		
		});
	});
	
	$("#searchactivityGoodForm #resetButton").on("click",function(){
		$("#searchactivityGoodForm").form('reset');
	});
	
/*#############################################search form end#################################*/		
	
/*##########################grid init begin####################################################*/
/*##########################grid toolbar begin#################################################*/
	var toolbar_tt = [
					{
						iconCls:"icon-edit",
						text:"新增",
						handler:to_addactivityGood
					}
	          	];
	
/*######################grid toolbar end##############################*/
/*######################grid columns begin##############################*/
	var columns_tt = [
      			[	 				
							{field:'id',title:'id',width:100,hidden:true},						
								{field:"userId",title:"空",width:180,align:"center"},
								{field:"activityId",title:"空",width:180,align:"center"},
								{field:"createDate",title:"创建时间",width:180,align:"center",formatter:dateTimeFormatter},
					{field:"操作",title:"操作",width:80,align:"left",
	 					formatter:function(value,row,index){
	 					  var str= '<a href="javascript:void(0);" onclick="to_editactivityGood(\''+row.id+'\');">编辑</a>   <a href="javascript:void(0);" onclick="to_delActivityGood(\''+row.id+'\');">删除</a>';
	 					  return str;
	 					}
	 				}	 				
	 			]
	 	];
/*######################grid columns end##############################*/
	
	$("#tt_ActivityGood").datagrid({
		url:httpUrl+"/activitygood/listActivityGood.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_areaActivityGood').height()-10,
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
			'searchStr': $("#searchactivityGoodForm #searchStr").val(),
			'searchCodeStr':$("#searchactivityGoodForm #searchCodeStr").val()
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
function to_addactivityGood(){
	to_editactivityGood('');
}

/**
 * 删除
 */

function to_delActivityGood(id) {
	if (!id) {
		$.messager.alert("消息", "id不能为空");
		return;
	}
	$.messager.confirm("消息", "确认删除吗，删除后不可恢复", function(r) {
		if (r) {
			$.ajax({
				url : httpUrl + "/activityGood/deleteActivityGood.html?id=" + id,
				type : "post",
				data : {},
				success : function(data) {
					if (data.ret == 1) {
						$.messager.alert("消息", "删除成功");
						$('#tt_ActivityGood').datagrid('reload');
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
function to_editactivityGood(id){
	
	var url = httpUrl+"/activitygood/addActivityGood.html?&rand=" + Math.random()+"&id="+id;
	$('#editActivityGoodDiv').dialog({
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
					handler:save_ActivityGood
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editActivityGoodDiv").dialog("close");
				}
		}]
	});
}

function save_ActivityGood(){
	var formdata = $("#editActivityGoodForm").serialize();
	console.info("formdata");
	console.info(formdata);
	var  url =httpUrl+"/activitygood/saveActivityGood.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:$("#editActivityGoodForm").serialize(),
		 success: function(data){ 
			 if(data.code ==="0"){
				 $("#editActivityGoodDiv").dialog("close");
				 $('#tt_ActivityGood').datagrid('reload');
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}


function reloadDataGrid()
{
	$("tt_ActivityGood").datagrid("reload");
}




/*##########################公用方法##begin############################*/

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('tt_ActivityGood').datagrid('resize',{  
		height:$("#body").height()-$('#search_areaActivityGood').height()-5,
		width:$("#body").width()
	});
	$('#search_areaActivityGood').panel('resize',{
		width:$("#body").width()
	});
	$('#detailLoanDiv').dialog('resize',{  
		height:$("#body").height()-25,
		width:$("#body").width()-30
	});
}
 
/*##########################公用方法##end############################*/