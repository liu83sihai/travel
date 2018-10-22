<#include "/macro.include"/> 
<#include "/custom.include"/> 
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case> 

$(function(){
/*#############################################search form begin#################################*/	
		
	$("#search${classNameLower}Form #searchButton").on("click",function(){
		$("#tt_${className}").datagrid('load',{
			'searchStr': $("#search${classNameLower}Form #searchStr").val(),
			'searchCodeStr':$("#search${classNameLower}Form #searchCodeStr").val()		
		});
	});
	
	$("#search${classNameLower}Form #resetButton").on("click",function(){
		$("#search${classNameLower}Form").form('reset');
	});
	
/*#############################################search form end#################################*/		
	
/*##########################grid init begin####################################################*/
/*##########################grid toolbar begin#################################################*/
	var toolbar_tt = [
					{
						iconCls:"icon-edit",
						text:"新增",
						handler:to_add${classNameLower}
					}
	          	];
	
/*######################grid toolbar end##############################*/
/*######################grid columns begin##############################*/
	var columns_tt = [
      			[	 				
	 				<#list table.columns as column>
						<#if column.pk>						
							{field:'id',title:'id',width:100,hidden:true},						
						</#if>
					</#list>		
					<#list table.columns as column>
						<#if !column.htmlHidden>
						   <#if column.isDateTimeColumn>
								{field:"${column.columnNameLower}",title:"${column.remarks}",width:180,align:"center",formatter:dateTimeFormatter},
                           <#else>
								{field:"${column.columnNameLower}",title:"${column.remarks}",width:180,align:"center"},
                           </#if>  						   
						</#if>
					</#list>				
					{field:"操作",title:"操作",width:80,align:"left",
	 					formatter:function(value,row,index){
	 					  var str= '<a href="javascript:void(0);" onclick="to_edit${classNameLower}(\''+row.id+'\');">编辑</a>   <a href="javascript:void(0);" onclick="to_del${className}(\''+row.id+'\');">删除</a>';
	 					  return str;
	 					}
	 				}	 				
	 			]
	 	];
/*######################grid columns end##############################*/
	
	$("#tt_${className}").datagrid({
		url:httpUrl+"/${classNameLowerCase}/list${className}.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_area${className}').height()-10,
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
			'searchStr': $("#search${classNameLower}Form #searchStr").val(),
			'searchCodeStr':$("#search${classNameLower}Form #searchCodeStr").val()
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
function to_add${classNameLower}(){
	to_edit${classNameLower}('');
}

/**
 * 删除
 */

function to_del${className}(id) {
	if (!id) {
		$.messager.alert("消息", "id不能为空");
		return;
	}
	$.messager.confirm("消息", "确认删除吗，删除后不可恢复", function(r) {
		if (r) {
			$.ajax({
				url : httpUrl + "/${classNameLower}/delete${className}.html?id=" + id,
				type : "post",
				data : {},
				success : function(data) {
					if (data.ret == 1) {
						$.messager.alert("消息", "删除成功");
						$('#tt_${className}').datagrid('reload');
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
function to_edit${classNameLower}(id){
	
	var url = httpUrl+"/${classNameLowerCase}/add${className}.html?&rand=" + Math.random()+"&id="+id;
	$('#edit${className}Div').dialog({
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
					handler:save_${className}
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#edit${className}Div").dialog("close");
				}
		}]
	});
}

function save_${className}(){
	var formdata = $("#edit${className}Form").serialize();
	console.info("formdata");
	console.info(formdata);
	var  url =httpUrl+"/${classNameLowerCase}/save${className}.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:$("#edit${className}Form").serialize(),
		 success: function(data){ 
			 if(data.code ==="0"){
				 $("#edit${className}Div").dialog("close");
				 $('#tt_${className}').datagrid('reload');
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}


function reloadDataGrid()
{
	$("tt_${className}").datagrid("reload");
}




/*##########################公用方法##begin############################*/

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('tt_${className}').datagrid('resize',{  
		height:$("#body").height()-$('#search_area${className}').height()-5,
		width:$("#body").width()
	});
	$('#search_area${className}').panel('resize',{
		width:$("#body").width()
	});
	$('#detailLoanDiv').dialog('resize',{  
		height:$("#body").height()-25,
		width:$("#body").width()-30
	});
}
 
/*##########################公用方法##end############################*/