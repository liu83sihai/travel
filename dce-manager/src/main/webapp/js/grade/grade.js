
$(function(){
/*#############################################search form begin#################################*/	
		
	$("#searchgradeForm #searchButton").on("click",function(){
		$("#tt_Grade").datagrid('load',{
			'searchStr': $("#searchgradeForm #searchStr").val(),
			'searchCodeStr':$("#searchgradeForm #searchCodeStr").val()		
		});
	});
	
	$("#searchgradeForm #resetButton").on("click",function(){
		$("#searchgradeForm").form('reset');
	});
	
/*#############################################search form end#################################*/		
	
/*##########################grid init begin####################################################*/
/*##########################grid toolbar begin#################################################*/
	var toolbar_tt = [
					{
						iconCls:"icon-edit",
						text:"新增",
						handler:to_addgrade
					}
	          	];
	
/*######################grid toolbar end##############################*/
/*######################grid columns begin##############################*/
	var columns_tt = [
      			[	 				
							{field:'id',title:'id',width:100,hidden:true},						
								{field:"gradeName",title:"编码",width:180,align:"center"},
								{field:"gradeMark",title:"编码",width:180,align:"center"},
								{field:"remark",title:"编码",width:180,align:"center"},
					{field:"操作",title:"操作",width:80,align:"left",
	 					formatter:function(value,row,index){
	 					  var str= '<a href="javascript:void(0);" onclick="to_editgrade(\''+row.id+'\');">编辑</a>';
	 					  return str;
	 					}
	 				}	 				
	 			]
	 	];
/*######################grid columns end##############################*/
	
	$("#tt_Grade").datagrid({
		url:httpUrl+"/grade/listGrade.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_areaGrade').height()-10,
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
			'searchStr': $("#searchgradeForm #searchStr").val(),
			'searchCodeStr':$("#searchgradeForm #searchCodeStr").val()
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
function to_addgrade(){
	to_edit('');
}
/**
 * 编辑
 * @param id
 */
function to_editgrade(id){
	
	var url = httpUrl+"/grade/addGrade.html?&rand=" + Math.random()+"&id="+id;
	$('#editGradeDiv').dialog({
		title: "编辑用户升级",
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
					handler:save_Grade
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editGradeDiv").dialog("close");
				}
		}]
	});
}

function save_Grade(){
	var formdata = $("#editGradeForm").serialize();
	console.info("formdata");
	console.info(formdata);
	var  url =httpUrl+"/grade/saveGrade.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:$("#editGradeForm").serialize(),
		 success: function(data){ 
			 if(data.code ==="0"){
				 $("#editGradeDiv").dialog("close");
				 $('tt_Grade').datagrid('reload');
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}


function reloadDataGrid()
{
	$("tt_Grade").datagrid("reload");
}




/*##########################公用方法##begin############################*/

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('tt_Grade').datagrid('resize',{  
		height:$("#body").height()-$('#search_areaGrade').height()-5,
		width:$("#body").width()
	});
	$('#search_areaGrade').panel('resize',{
		width:$("#body").width()
	});
	$('#detailLoanDiv').dialog('resize',{  
		height:$("#body").height()-25,
		width:$("#body").width()-30
	});
}
 
/*##########################公用方法##end############################*/