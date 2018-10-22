
$(function(){
/*#############################################search form begin#################################*/	
		
	$("#searchbannerForm #searchButton").on("click",function(){
		$("#tt_Banner").datagrid('load',{
			'searchStr': $("#searchbannerForm #searchStr").val(),
			'searchCodeStr':$("#searchbannerForm #searchCodeStr").val()		
		});
	});
	
	$("#searchbannerForm #resetButton").on("click",function(){
		$("#searchbannerForm").form('reset');
	});
	
/*#############################################search form end#################################*/		
	
/*##########################grid init begin####################################################*/
/*##########################grid toolbar begin#################################################*/
	var toolbar_tt = [
					{
						iconCls:"icon-edit",
						text:"新增",
						handler:to_addbanner
					}
	          	];
	
/*######################grid toolbar end##############################*/
/*######################grid columns begin##############################*/
	var columns_tt = [
      			[	 				
							{field:'id',title:'id',width:100,hidden:true},						
								{field:"icoName",title:"图标名称",width:180,align:"center"},
								{field:"icoImage",title:"图片",width:180,align:"center"},
								{field:"icoType",title:"图标类型",width:180,align:"center",
									formatter : function(value, row, index) {
										if (value == 1) {
											return "banner图";
										} else if (value == 2) {
											return "导航小图标";
										}
									}},
								{field:"linkType",title:"链接类型",width:180,align:"center",
									formatter : function(value, row, index) {
										if (value == "1") {
											return "超链接";
										} else if (value == "2") {
											return "程序链接";
										}else{
											return "无";
										}
									}},
								{field:"linkValue",title:"链接值",width:180,align:"center"},
								{field:"hintValue",title:"提示内容",width:180,align:"center"},
								{field:"sort",title:"排序",width:180,align:"center"},
								{field:"createDate",title:"创建时间",width:180,align:"center" ,formatter:dateTimeFormatter},
								{field:"createName",title:"创建人",width:180,align:"center"},
								{field:"modifyDate",title:"更新时间",width:180,align:"center",formatter:dateTimeFormatter },
								{field:"modifyName",title:"更新人",width:180,align:"center"},
								{field:"remark",title:"备注",width:180,align:"center"},
					{field:"操作",title:"操作",width:80,align:"left",
	 					formatter:function(value,row,index){
	 					  var str= '<a href="javascript:void(0);" onclick="to_editbanner(\''+row.id+'\');">编辑</a>   <a href="javascript:void(0);" onclick="to_delBanner(\''+row.id+'\');">删除</a>';
	 					  return str;
	 					}
	 				}	 				
	 			]
	 	];
/*######################grid columns end##############################*/
	
	$("#tt_Banner").datagrid({
		url:httpUrl+"/banner/listBanner.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_areaBanner').height()-10,
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
			'searchStr': $("#searchbannerForm #searchStr").val(),
			'searchCodeStr':$("#searchbannerForm #searchCodeStr").val()
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
function to_addbanner(){
	to_editbanner('');
}

/**
 * 删除
 */

function to_delBanner(id) {
	if (!id) {
		$.messager.alert("消息", "id不能为空");
		return;
	}
	$.messager.confirm("消息", "确认删除吗，删除后不可恢复", function(r) {
		if (r) {
			$.ajax({
				url : httpUrl + "/banner/deleteBanner.html?id=" + id,
				type : "post",
				data : {},
				success : function(data) {
					if (data.ret == 1) {
						$.messager.alert("消息", "删除成功");
						$('#tt_Banner').datagrid('reload');
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
function to_editbanner(id){
	
	var url = httpUrl+"/banner/addBanner.html?&rand=" + Math.random()+"&id="+id;
	$('#editBannerDiv').dialog({
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
					handler:save_Banner
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editBannerDiv").dialog("close");
				}
		}]
	});
}

function save_Banner(){
//	var formdata = $("#editBannerForm").serialize();
//	console.info("formdata");
//	console.info(formdata);
	// 创建表单数据对象
	
    var obj = new FormData();

    // 获取框中的数据
   // var title = $("#editGoodsForm input[name='title']").val();
//    var title = $("#editGoodsForm #title").val();
    var icoName = document.getElementById("icoName").value;
    var icoType = document.getElementById("icoType").value;
    var linkType =document.getElementById("linkType").value;
    var linkValue =document.getElementById("linkValue").value;
    var hintValue =document.getElementById("hintValue").value;
    var sort =document.getElementById("sort").value;
    var remark =document.getElementById("remark").value;
    var icoImages = document.getElementById("icoImages").files[0];
    
    var id =document.getElementById("id").value;
    var createDate =document.getElementById("createDate").value;
    var createName =document.getElementById("createName").value;
    var modifyDate =document.getElementById("modifyDate").value;
    var modifyName =document.getElementById("modifyName").value;
    var status =document.getElementById("status").value;
    
    if(icoName == null || icoName == ""){
		$.messager.alert("错误", "请填写名称");
		return;
	}
    if(icoType == null || icoType == ""){
		$.messager.alert("错误", "请填写类型");
		return;
	}
    if(icoImages == null || icoImages == ""){
		$.messager.alert("错误", "请上传图片");
		return;
	}
 
    

    // 将数据添加至表单数据对象中
    obj.append("icoName", icoName);
    obj.append("icoImages", icoImages);
    obj.append("icoType", icoType);
    obj.append("linkType", linkType);
    obj.append("linkValue", linkValue);
    obj.append("sort", sort);
    obj.append("remark", remark);
    obj.append("hintValue", hintValue);
    
    obj.append("id", id);
    obj.append("createDate", createDate);
    obj.append("createName", createName);
    obj.append("modifyDate", modifyDate);
    obj.append("modifyName", modifyName);
    obj.append("status", status);
    debugger;
	var  url =httpUrl+"/banner/saveBanner.html?&rand=" + Math.random();
	 $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:obj,
		 processData: false,
		 contentType : false,
		 success: function(data){ 
			 if(data.code ==="0"){
				 $("#editBannerDiv").dialog("close");
				 $('#tt_Banner').datagrid('reload');
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}


function reloadDataGrid()
{
	$("tt_Banner").datagrid("reload");
}




/*##########################公用方法##begin############################*/

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('tt_Banner').datagrid('resize',{  
		height:$("#body").height()-$('#search_areaBanner').height()-5,
		width:$("#body").width()
	});
	$('#search_areaBanner').panel('resize',{
		width:$("#body").width()
	});
	$('#detailLoanDiv').dialog('resize',{  
		height:$("#body").height()-25,
		width:$("#body").width()-30
	});
}
 
/*##########################公用方法##end############################*/