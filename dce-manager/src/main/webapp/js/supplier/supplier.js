
$(function(){
/*#############################################search form begin#################################*/	
		
	$("#searchsupplierForm #searchButton").on("click",function(){
		$("#tt_Supplier").datagrid('load',{
			'searchStr': $("#searchsupplierForm #searchStr").val(),
			'searchCodeStr':$("#searchsupplierForm #searchCodeStr").val()		
		});
	});
	
	$("#searchsupplierForm #resetButton").on("click",function(){
		$("#searchsupplierForm").form('reset');
	});
	
/*#############################################search form end#################################*/		
	
/*##########################grid init begin####################################################*/
/*##########################grid toolbar begin#################################################*/
	var toolbar_tt = [
					{
						iconCls:"icon-edit",
						text:"新增",
						handler:to_addsupplier
					}
	          	];
	
/*######################grid toolbar end##############################*/
/*######################grid columns begin##############################*/
	var columns_tt = [
      			[	 				
							{field:'id',title:'id',width:100,hidden:true},						
								{field:"userId",title:"用户Id",width:80,align:"center"},
								{field:"supplierName",title:"供应商名",width:180,align:"center"},
								{field:"synopsis",title:"简介",width:180,align:"center"},
								{field:"content",title:"详情",width:180,align:"center"},
								{field:"linkValue",title:"链接",width:180,align:"center"},
								{field:"listImages",title:"小图片",width:180,align:"center"},
								{field:"bannerImages",title:"banner图",width:180,align:"center"},
								{field:"busiImage",title:"营业执照",width:180,align:"center"},
								{field:"shopImage",title:"门店照片",width:180,align:"center"},
								{field:"city",title:"省市/城市",width:120,align:"center"},
								{field:"supplierAddress",title:"详细地址",width:180,align:"center"},
								{field:"grade",title:"评分",width:80,align:"center"},
								{field:"average",title:"人均",width:80,align:"center"},
								{field:"longitude",title:"经度",width:80,align:"center"},
								{field:"latitude",title:"纬度",width:80,align:"center"},
								{field:"hitNum",title:"点击数",width:180,align:"center"},
								{field:"createDate",title:"创建时间",width:120,align:"center",formatter:dateTimeFormatter},
								{field:"createName",title:"创建人",width:120,align:"center"},
								{field:"modifyDate",title:"更新时间",width:120,align:"center",formatter:dateTimeFormatter},
								{field:"modifyName",title:"更新人",width:120,align:"center"},
								{field:"status",title:"状态",width:180,align:"center"},
								{field:"remark",title:"备注",width:180,align:"center"},
					{field:"操作",title:"操作",width:80,align:"left",
	 					formatter:function(value,row,index){
	 					  var str= '<a href="javascript:void(0);" onclick="to_editsupplier(\''+row.userId+'\');">编辑</a>    <a href="javascript:void(0);" onclick="to_auditsupplier(\''+row.id+'\',\''+row.userId+'\',\''+row.city+'\');">审核</a>    <a href="javascript:void(0);" onclick="to_delSupplier(\''+row.id+'\');">删除</a>';
	 					  return str; 
	 					}
	 				}	 				
	 			]
	 	];
/*######################grid columns end##############################*/
	
	$("#tt_Supplier").datagrid({
		url:httpUrl+"/supplier/listSupplier.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_areaSupplier').height()-10,
		width:$("#body").width(),
		rownumbers:true,
		fitColumns:false,
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
			'supplierName': $("#searchsupplierForm #searchStr").val(),
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
function to_addsupplier(){
	to_editsupplier('');
}

/**
 * 删除
 */

function to_delSupplier(id) {
	if (!id) {
		$.messager.alert("消息", "id不能为空");
		return;
	}
	$.messager.confirm("消息", "确认删除吗，删除后不可恢复", function(r) {
		if (r) {
			$.ajax({
				url : httpUrl + "/supplier/deleteSupplier.html?id=" + id,
				type : "post",
				data : {},
				success : function(data) {
					if (data.ret == 1) {
						$.messager.alert("消息", "删除成功");
						$('#tt_Supplier').datagrid('reload');
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
function to_editsupplier(id){
	
	var url = httpUrl+"/supplier/addSupplier.html?&rand=" + Math.random()+"&id="+id;
	$('#editSupplierDiv').dialog({
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
					handler:save_Supplier
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editSupplierDiv").dialog("close");
				}
		}]
	});
}

/**
 * 审核
 * @param id
 */
function to_auditsupplier(id,userId,district){
	$.messager.confirm("消息", "审核确认", function(r) {
		if (r) {
			$.ajax({
				url : httpUrl + "/supplier/aduitSupplier.html?id=" + id,
				type : "post",
				data : {
					"userId":userId,
					"district":district
				},
				success : function(data) {
					if (data.ret == 1) {
						$.messager.alert("消息", "审核成功");
						$('#tt_Supplier').datagrid('reload');
					}else if (data.ret == 2) {
						$.messager.alert("消息", "审核失败,当前区域已存在商家");
							$('#tt_Supplier').datagrid('reload');
					} else {
						$.messager.alert("消息", "审核失败，请稍后再试");
					}
				}
			});
		}
	});
}

function save_Supplier(){
	var formdata = $("#editSupplierForm").serialize();
	console.info("formdata");
	console.info(formdata);
	var  url =httpUrl+"/supplier/saveSupplier.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:$("#editSupplierForm").serialize(),
		 success: function(data){ 
			 if(data.code ==="0"){
				 $("#editSupplierDiv").dialog("close");
				 $('#tt_Supplier').datagrid('reload');
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}


function reloadDataGrid()
{
	$("tt_Supplier").datagrid("reload");
}




/*##########################公用方法##begin############################*/

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('tt_Supplier').datagrid('resize',{  
		height:$("#body").height()-$('#search_areaSupplier').height()-5,
		width:$("#body").width()
	});
	$('#search_areaSupplier').panel('resize',{
		width:$("#body").width()
	});
	$('#detailLoanDiv').dialog('resize',{  
		height:$("#body").height()-25,
		width:$("#body").width()-30
	});
}
 
/*##########################公用方法##end############################*/