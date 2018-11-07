
$(function(){
/*#############################################search form begin#################################*/	
		
	$("#searchaboutusForm #searchButton").on("click",function(){
		$("#tt_Aboutus").datagrid('load',{
			'searchStr': $("#searchaboutusForm #searchStr").val(),
			'searchCodeStr':$("#searchaboutusForm #searchCodeStr").val()		
		});
	});
	
	$("#searchaboutusForm #resetButton").on("click",function(){
		$("#searchaboutusForm").form('reset');
	});
	
/*#############################################search form end#################################*/		
	
/*##########################grid init begin####################################################*/
/*##########################grid toolbar begin#################################################*/
	var toolbar_tt = [
					{
						iconCls:"icon-edit",
						text:"新增",
						handler:to_addaboutus
					}
	          	];
	
/*######################grid toolbar end##############################*/
/*######################grid columns begin##############################*/
	var columns_tt = [
      			[	 				
							{field:'id',title:'id',width:100,hidden:true},						
							{field:"url",title:"url地址",width:180,align:"center"},
							{field:"createTime",title:"创建时间",width:180,align:"center",formatter:dateTimeFormatter},
					{field:"操作",title:"操作",width:80,align:"left",
	 					formatter:function(value,row,index){
	 					  var str= '<a href="javascript:void(0);" onclick="to_editaboutus(\''
								+ row.id
								+ '\');">编辑</a>  <a href="javascript:void(0);" onclick="to_deleteaboutus(\''
								+ row.id + '\');">删除</a>';
	 					  return str;
	 					}
	 				}	 				
	 			]
	 	];
/*######################grid columns end##############################*/
	
	$("#tt_Aboutus").datagrid({
		url:httpUrl+"/aboutus/listAboutus.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_areaAboutus').height()-10,
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
			'searchStr': $("#searchaboutusForm #searchStr").val(),
			'searchCodeStr':$("#searchaboutusForm #searchCodeStr").val()
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
function to_addaboutus(){
	to_editaboutus('');
}
/**
 * 编辑
 * @param id
 */
function to_editaboutus(id){
	
	var url = httpUrl+"/aboutus/addAboutus.html?&rand=" + Math.random()+"&id="+id;
	$('#editAboutusDiv').dialog({
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
					handler:save_Aboutus
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editAboutusDiv").dialog("close");
				}
		}]
	});
}

function save_Aboutus(){
	var id =$("#editAboutusForm #id").val();
    var url =$("#editAboutusForm #url").val();
    var summarry =$("#editAboutusForm #summarry").val();
    var aboutusBanner =$("#editAboutusForm #aboutusBanner").val();
    var detailImg =$("#editAboutusForm #detailImg").val();
	
	var  url =httpUrl+"/aboutus/saveAboutus.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:{"id":id,"url":url,"summarry":summarry,"aboutusBanner":aboutusBanner,"detailImg":detailImg},
		 success: function(data){ 
			 if(data.code ==="0"){
				 $("#editAboutusDiv").dialog("close");
				 $('tt_Aboutus').datagrid('reload');
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}
function to_deleteaboutus(id) {
	if (!id) {
		$.messager.alert("关于我们", "id不能为空");
		return;
	}
	$.messager.confirm("关于我们", "确认删除该选项吗，删除后不可恢复", function(r) {
		if (r) {
			$.ajax({
				url : httpUrl + "/aboutus/deleteAboutUs.html?id=" + id,
				type : "post",
				data : {},
				success : function(data) {
					if (data.ret == 1) {
						$.messager.alert( "删除成功");
						$('#tableGrid').datagrid('reload');
					} else {
						$.messager.alert( "删除失败，请稍后再试");
					}
				}
			});
		}
	});
}

function reloadDataGrid()
{
	$("tt_Aboutus").datagrid("reload");
}




/*##########################公用方法##begin############################*/

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('tt_Aboutus').datagrid('resize',{  
		height:$("#body").height()-$('#search_areaAboutus').height()-5,
		width:$("#body").width()
	});
	$('#search_areaAboutus').panel('resize',{
		width:$("#body").width()
	});
	$('#detailLoanDiv').dialog('resize',{  
		height:$("#body").height()-25,
		width:$("#body").width()-30
	});
}
 
/*##########################公用方法##end############################*/