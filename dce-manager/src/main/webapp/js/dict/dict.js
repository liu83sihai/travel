
$(function(){
/*#############################################search form begin#################################*/	
		
	$("#searchloanDictForm #searchButton").on("click",function(){
		$("#tt_LoanDict").datagrid('load',{
			'searchStr': $("#searchloanDictForm #searchStr").val(),
			'searchCodeStr':$("#searchloanDictForm #searchCodeStr").val()		
		});
	});
	
	$("#searchloanDictForm #resetButton").on("click",function(){
		$("#searchloanDictForm").form('reset');
	});
	
/*#############################################search form end#################################*/		
	
/*##########################grid init begin####################################################*/
/*##########################grid toolbar begin#################################################*/
	var toolbar_tt = [
					{
						iconCls:"icon-edit",
						text:"新增",
						handler:to_addloanDict
					}
	          	];
	
/*######################grid toolbar end##############################*/
/*######################grid columns begin##############################*/
	var columns_tt = [
      			[	 				
							{field:'id',title:'id',width:100,hidden:true},						
								{field:"code",title:"编码",width:180,align:"center"},
								{field:"name",title:"名称",width:180,align:"center"},
								{field:"status",title:"状态",width:180,align:"center"},
								{field:"remark",title:"备注",width:180,align:"center"},
								{field:"createUserId",title:"创建用户ID",width:180,align:"center",hidden:true},
								{field:"updateUserId",title:"修改用户ID",width:180,align:"center",hidden:true},
								{field:"createTime",title:"创建时间",width:180,align:"center",formatter:dateTimeFormatter},
								{field:"updateTime",title:"修改时间",width:180,align:"center",formatter:dateTimeFormatter},
					{field:"操作",title:"操作",width:80,align:"left",
	 					formatter:function(value,row,index){
	 					  var str= '<a href="javascript:void(0);" onclick="to_editloanDict(\''+row.id+'\');">编辑</a>   ';
	 					  return str;
	 					}
	 				}	 				
	 			]
	 	];
/*######################grid columns end##############################*/
	
	$("#tt_LoanDict").datagrid({
		url:httpUrl+"/loandict/listLoanDict.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_areaLoanDict').height()-10,
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
			'searchStr': $("#searchloanDictForm #searchStr").val(),
			'searchCodeStr':$("#searchloanDictForm #searchCodeStr").val()
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
function to_addloanDict(){
	to_editloanDict('');
}

/**
 * 删除
 */

function to_delLoanDict(id) {
	if (!id) {
		$.messager.alert("消息", "id不能为空");
		return;
	}
	$.messager.confirm("消息", "确认删除吗，删除后不可恢复", function(r) {
		if (r) {
			$.ajax({
				url : httpUrl + "/loandict/deleteLoanDict.html?id=" + id,
				type : "post",
				data : {},
				success : function(data) {
					if (data.ret == 1) {
						$.messager.alert("消息", "删除成功");
						$('#tt_LoanDict').datagrid('reload');
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
function to_editloanDict(id){
	
	var url = httpUrl+"/loandict/addLoanDict.html?&rand=" + Math.random()+"&id="+id;
	$('#editLoanDictDiv').dialog({
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
					handler:save_LoanDict
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editLoanDictDiv").dialog("close");
				}
		}]
	});
}

function save_LoanDict(){
	var formdata = $("#editLoanDictForm").serialize();
	console.info("formdata");
	console.info(formdata);
	var  url =httpUrl+"/loandict/saveLoanDict.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:formdata,
		 success: function(data){ 
			 if(data.code ==="0"){
				 $("#editLoanDictDiv").dialog("close");
				 $('#tt_LoanDict').datagrid('reload');
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}


function reloadDataGrid()
{
	$("tt_LoanDict").datagrid("reload");
}




/*##########################公用方法##begin############################*/

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('tt_LoanDict').datagrid('resize',{  
		height:$("#body").height()-$('#search_areaLoanDict').height()-5,
		width:$("#body").width()
	});
	$('#search_areaLoanDict').panel('resize',{
		width:$("#body").width()
	});
	$('#detailLoanDiv').dialog('resize',{  
		height:$("#body").height()-25,
		width:$("#body").width()-30
	});
}
 
/*##########################公用方法##end############################*/