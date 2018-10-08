
$(function(){
/*#############################################search form begin#################################*/	
		
	$("#searchuserFeedbackForm #searchButton").on("click",function(){
		var dataUrl = httpUrl+"/userfeedback/listUserFeedback.html";
		$("#tt_UserFeedback").datagrid('options').url = dataUrl;
		$("#tt_UserFeedback").datagrid('load',{
			'userName': $("#searchuserFeedbackForm #userName").val(),
			'startDate':$("#searchuserFeedbackForm #startDate").datebox('getValue'),
			'endDate':$("#searchuserFeedbackForm #endDate").datebox('getValue')
		});
	});
	
	$("#searchuserFeedbackForm #resetButton").on("click",function(){
		$("#searchuserFeedbackForm").form('reset');
	});
	
/*#############################################search form end#################################*/		
	
/*##########################grid init begin####################################################*/
/*##########################grid toolbar begin#################################################*/
	
/*######################grid toolbar end##############################*/
/*######################grid columns begin##############################*/
	var columns_tt = [
      			[	 				
							{field:'feedbackid',title:'id',width:100,hidden:true},						
							{field:"mobile",title:"账号",width:180,align:"center"},
								{field:"truename",title:"反馈人姓名",width:180,align:"center"},
								{field:"feedbackcontent",title:"内容",width:180,align:"center"},
								{field:"createtime",title:"反馈时间",width:180,align:"center",formatter:dateTimeFormatter},
					{field:"操作",title:"操作",width:80,align:"left",
	 					formatter:function(value,row,index){
	 					  var str= '<a href="javascript:void(0);" onclick="to_edituserFeedback(\''+ row.feedbackid+ '\');">详情</a>  <a href="javascript:void(0);" onclick="deleteFeedBack(\''+row.feedbackid+'\');">删除</a>';
	 					  return str;
	 					}
	 				}	 				
	 			]
	 	];
/*######################grid columns end##############################*/
	
	$("#tt_UserFeedback").datagrid({
		url:httpUrl+"/userfeedback/listUserFeedback.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_areaUserFeedback').height()-10,
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
		queryParams:{
			'userName': $("#searchuserFeedbackForm #userName").val(),
			'startDate':$("#searchuserFeedbackForm #startDate").datebox('getValue'),
			'endDate':$("#searchuserFeedbackForm #endDate").datebox('getValue')
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
 *删除
 *@param id 
 */
function deleteFeedBack(id){
	if(!id){
		$.messager.alert("消息","id不能为空");
		return;
	}
	$.messager.confirm("消息","确认删除该反馈吗，删除后不可恢复",function(r){
		if(r){
			$.ajax({
				url:httpUrl+"/userfeedback/deleteYsFeedBack.html?id="+id,
				type:"post",
				data:{},
				success:function(data){
					if(data.ret==1){
						$.messager.alert("消息","删除成功");
						 $('#tt_UserFeedback').datagrid('reload');
					}else{
						$.messager.alert("消息","删除失败，请稍后再试");
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
function to_edituserFeedback(id){
	
	var url = httpUrl+"/userfeedback/addUserFeedback.html?&rand=" + Math.random()+"&id="+id;
	$('#editUserFeedbackDiv').dialog({
		title: "查看",
		width: 760,
		height: 500,
		closed: false,
		closable:false,
		cache: false,
		href: url,
		modal: true,
		toolbar:[
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editUserFeedbackDiv").dialog("close");
				}
		}]
	});
}

function save_UserFeedback(){
	var formdata = $("#editUserFeedbackForm").serialize();
	console.info("formdata");
	console.info(formdata);
	var  url =httpUrl+"/userfeedback/saveUserFeedback.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:$("#editUserFeedbackForm").serialize(),
		 success: function(data){ 
			 if(data.code ==="0"){
				 $("#editUserFeedbackDiv").dialog("close");
				 $('#tt_UserFeedback').datagrid('reload');
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}


function reloadDataGrid()
{
	$("#tt_UserFeedback").datagrid("reload");
}




/*##########################公用方法##begin############################*/

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('tt_UserFeedback').datagrid('resize',{  
		height:$("#body").height()-$('#search_areaUserFeedback').height()-5,
		width:$("#body").width()
	});
	$('#search_areaUserFeedback').panel('resize',{
		width:$("#body").width()
	});
	$('#detailLoanDiv').dialog('resize',{  
		height:$("#body").height()-25,
		width:$("#body").width()-30
	});
}
 
/*##########################公用方法##end############################*/