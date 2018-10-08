
$(function(){
/*#############################################search form begin#################################*/	
		
	$("#searchorderSendoutForm #searchButton").on("click",function(){
		var dataUrl = httpUrl+"/ordersendout/listOrderSendout.html";
		$("#tt_OrderSendout").datagrid('options').url = dataUrl;
		$("#tt_OrderSendout").datagrid('load',{
			'startDate':$("#searchorderSendoutForm #startDate").datebox('getValue'),
			'endDate':$("#searchorderSendoutForm #endDate").datebox('getValue'),
		});
	});
	
	$("#searchorderSendoutForm #resetButton").on("click",function(){
		$("#searchorderSendoutForm").form('reset');
	});
	
/*#############################################search form end#################################*/		
	
/*##########################grid init begin####################################################*/
/*##########################grid toolbar begin#################################################*/
	/*var toolbar_tt = [
					{
						iconCls:"icon-edit",
						text:"新增",
						handler:to_addorderSendout
					}
	          	];*/
	
/*######################grid toolbar end##############################*/
/*######################grid columns begin##############################*/
	var columns_tt = [
      			[	 				
					{field:'id',title:'id',width:100,hidden:true},						
					{field:"orderCode",title:"订单编号",width:180,align:"center"},
					{field:"operator",title:"操作人",width:180,align:"center"},
					{field:"address",title:"收货地址",width:180,align:"center"},
					{field:"createTime",title:"发货时间",width:180,align:"center",formatter:dateTimeFormatter},
					/*{field:"操作",title:"操作",width:80,align:"left",
	 					formatter:function(value,row,index){
	 					  var str= '<a href="javascript:void(0);" onclick="to_editorderSendout(\''+row.id+'\');">发货</a>';
	 					  return str;
	 					}
	 				}	 */				
	 			]
	 	];
/*######################grid columns end##############################*/
	
	$("#tt_OrderSendout").datagrid({
		url:httpUrl+"/ordersendout/listOrderSendout.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_areaOrderSendout').height()-10,
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
		/*toolbar:toolbar_tt,*/
		queryParams:{
			'startDate':$("#searchorderForm #startDate").val(),
			'endDate':$("#searchorderForm #endDate").val()
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
/*function to_addorderSendout(){
	to_edit('');
}
*//**
 * 编辑
 * @param id
 *//*
function to_editorderSendout(id){
	
	var url = httpUrl+"/ordersendout/addOrderSendout.html?&rand=" + Math.random()+"&id="+id;
	$('#editOrderSendoutDiv').dialog({
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
					handler:save_OrderSendout
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editOrderSendoutDiv").dialog("close");
				}
		}]
	});
}

function save_OrderSendout(){
	var formdata = $("#editOrderSendoutForm").serialize();
	console.info("formdata");
	console.info(formdata);
	var  url =httpUrl+"/ordersendout/saveOrderSendout.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:$("#editOrderSendoutForm").serialize(),
		 success: function(data){ 
			 if(data.code ==="0"){
				 $("#editOrderSendoutDiv").dialog("close");
				 $('tt_OrderSendout').datagrid('reload');
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}

*/
function reloadDataGrid()
{
	$("tt_OrderSendout").datagrid("reload");
}




/*##########################公用方法##begin############################*/

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('tt_OrderSendout').datagrid('resize',{  
		height:$("#body").height()-$('#search_areaOrderSendout').height()-5,
		width:$("#body").width()
	});
	$('#search_areaOrderSendout').panel('resize',{
		width:$("#body").width()
	});
	$('#detailLoanDiv').dialog('resize',{  
		height:$("#body").height()-25,
		width:$("#body").width()-30
	});
}
 
/*##########################公用方法##end############################*/