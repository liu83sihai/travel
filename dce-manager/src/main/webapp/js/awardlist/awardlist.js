
$(function(){
/*#############################################search form begin#################################*/	
		
	$("#searchawardlistForm #searchButton").on("click",function(){
		$("#tt_Awardlist").datagrid('load',{
			'searchStr': $("#searchawardlistForm #searchStr").val(),
			'searchCodeStr':$("#searchawardlistForm #searchCodeStr").val()		
		});
	});
	
	$("#searchawardlistForm #resetButton").on("click",function(){
		$("#searchawardlistForm").form('reset');
	});
	
/*#############################################search form end#################################*/		
	
/*##########################grid init begin####################################################*/
/*##########################grid toolbar begin#################################################*/
	var toolbar_tt = [
/*					{
						iconCls:"icon-edit",
						text:"新增",
						handler:to_addawardlist
					}*/
	          	];
	
/*######################grid toolbar end##############################*/
/*######################grid columns begin##############################*/
	var columns_tt = [
      			[	 				
							{field:'awardid',title:'id',width:100,hidden:true},						
								{field:"buyerLecel",title:"购买者等级",width:180,align:"center"},
								{field:"p1Level0",title:"第一代推荐人普通等级奖励",width:180,align:"center"},
								{field:"p1Level1",title:"第一代推荐人会员等级奖励",width:180,align:"center"},
								{field:"p1Level2",title:"第一代推荐人VIP等级奖励",width:180,align:"center"},
								{field:"p1Level3",title:"第一代推荐人城市合伙人等级奖励",width:180,align:"center"},
								{field:"p1Level4",title:"第一代推荐人股东等级奖励",width:180,align:"center"},
								{field:"p2Level0",title:"第二代推荐人普通等级奖励",width:180,align:"center"},
								{field:"p2Level1",title:"第二代推荐人会员等级奖励",width:180,align:"center"},
								{field:"p2Level2",title:"第二代推荐人VIP等级奖励",width:180,align:"center"},
								{field:"p2Level3",title:"第二代推荐人城市合伙人等级奖励",width:180,align:"center"},
								{field:"p2Level4",title:"第二代推荐人股东等级奖励",width:180,align:"center"},
								{field:"buyerAward",title:"购买者奖励",width:180,align:"center"},
								/*{field:"buyQty",title:"编码",width:180,align:"center"},*/
					{field:"操作",title:"操作",width:80,align:"left",
	 					formatter:function(value,row,index){
	 					  var str= '<a href="javascript:void(0);" onclick="to_editawardlist(\''+row.awardid+'\');">编辑</a>';
	 					  return str;
	 					}
	 				}	 				
	 			]
	 	];
/*######################grid columns end##############################*/
	
	$("#tt_Awardlist").datagrid({
		url:httpUrl+"/awardlist/listAwardlist.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_areaAwardlist').height()-10,
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
			'searchStr': $("#searchawardlistForm #searchStr").val(),
			'searchCodeStr':$("#searchawardlistForm #searchCodeStr").val()
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
/*function to_addawardlist(){
	to_editawardlist('');
}*/
/**
 * 编辑
 * @param id
 */
function to_editawardlist(id){
	
	var url = httpUrl+"/awardlist/addAwardlist.html?&rand=" + Math.random()+"&id="+id;
	$('#editAwardlistDiv').dialog({
		title: "会员奖励制度管理",
		width: 1200,
		height: 800,
		closed: false,
		closable:false,
		cache: false,
		href: url,
		modal: true,
		toolbar:[
				{
					iconCls:"icon-save",text:"保存",
					handler:save_Awardlist
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editAwardlistDiv").dialog("close");
				}
		}]
	});
}

function save_Awardlist(){
	var formdata = $("#editAwardlistForm").serialize();
	console.info("formdata");
	console.info(formdata);
	var  url =httpUrl+"/awardlist/saveAwardlist.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:$("#editAwardlistForm").serialize(),
		 success: function(data){ 
			 if(data.code ==="0"){
				 $("#editAwardlistDiv").dialog("close");
				 $('tt_Awardlist').datagrid('reload');
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}


function reloadDataGrid()
{
	$("tt_Awardlist").datagrid("reload");
}




/*##########################公用方法##begin############################*/

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('tt_Awardlist').datagrid('resize',{  
		height:$("#body").height()-$('#search_areaAwardlist').height()-5,
		width:$("#body").width()
	});
	$('#search_areaAwardlist').panel('resize',{
		width:$("#body").width()
	});
	$('#detailLoanDiv').dialog('resize',{  
		height:$("#body").height()-25,
		width:$("#body").width()-30
	});
}
 
/*##########################公用方法##end############################*/