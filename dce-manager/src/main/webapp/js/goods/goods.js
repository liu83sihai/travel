
$(function(){
/*#############################################search form begin#################################*/	
		
	$("#searchgoodsForm #searchButton").on("click",function(){
		var dataUrl = httpUrl+"/goods/listGoods.html";
		$("#tt_Goods").datagrid('options').url = dataUrl;
		$("#tt_Goods").datagrid('load',{
			'title': $("#searchgoodsForm #title").val(),
			'startDate':$("#searchgoodsForm #startDate").datebox('getValue'),
			'endDate':$("#searchgoodsForm #endDate").datebox('getValue'),	
		});
	});
	
	$("#searchgoodsForm #resetButton").on("click",function(){
		$("#searchgoodsForm").form('reset');
	});
	
/*#############################################search form end#################################*/		
	
/*##########################grid init begin####################################################*/
/*##########################grid toolbar begin#################################################*/
	var toolbar_tt = [
					{
						iconCls:"icon-edit",
						text:"新增",
						handler:to_addgoods
					}
	          	];
	
/*######################grid toolbar end##############################*/
/*######################grid columns begin##############################*/
	var columns_tt = [
      			[	 				
							{field:'goodsId',title:'id',width:100,hidden:true},						
								{field:"title",title:"商品名称",width:180,align:"center"},
								{field:"shopPrice",title:"价格",width:80,align:"center"},
								{field:"goodsUnit",title:"单位",width:80,align:"center"},
								{field:"goodsDesc",title:"内容",width:180,align:"center"},
								{field:"goodsImg",title:"商品图片地址",width:120,align:"center"},
								{field:"profit",title:"商品利润",width:80,align:"center"},
								{field:"saleCount",title:"已售数量",width:80,align:"center"},
								{field:"status",title:"商品上架的状态",width:80,align:"center",
									formatter:function(value,row,index){
				 						if(row.status == "0"){
				 							return "未上架";
				 						}else if(row.status == "1"){
				 							return "已上架";
				 						}
									}
								},
								{field:"goodsFlag",title:"商品类别",width:80,align:"center",
									formatter:function(value,row,index){
				 						if(row.goodsFlag == "1"){
				 							return "旅游卡";
				 						}else if(row.goodsFlag == "1"){
				 							return "爆款商品";
				 						}else {
				 							return "常规商品";
				 						}
									}
								},
								@apiSuccess {String} hotGoodsList.goodsFlag //商品类别 1： 旅游卡， 2： 爆款商品， 3： 常规商品 
								
								{field:"saleTime",title:"商品上架时间",width:80,align:"center",formatter:dateTimeFormatter},
								{field:"createTime",title:"商品创建时间",width:80,align:"center",formatter:dateTimeFormatter},
					{field:"操作",title:"操作",width:80,align:"left",
	 					formatter:function(value,row,index){
	 					  var str= '<a href="javascript:void(0);" onclick="to_editgoods(\''+row.goodsId+'\');">编辑</a> <a href="javascript:void(0);" onclick="deleteNotice(\''+row.goodsId+'\');">删除</a>';
	 					  return str;
	 					}
	 				}	 				
	 			]
	 	];
/*######################grid columns end##############################*/
	
	$("#tt_Goods").datagrid({
		url:httpUrl+"/goods/listGoods.html?&rand=" + Math.random(),
		height:$("#body").height()-$('#search_areaGoods').height()-10,
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
			'title': $("#searchgoodsForm #title").val(),
			'startDate':$("#searchgoodsForm #startDate").datebox('getValue'),
			'endDate':$("#searchgoodsForm #endDate").datebox('getValue'),
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
function to_addgoods(){
	to_editgoods('');
	$('#editGoodsDiv').dialog({
		title: "新增",
	})
}
/**
 * 编辑
 * @param id
 */
function to_editgoods(id){
	
	var url = httpUrl+"/goods/addGoods.html?&rand=" + Math.random()+"&id="+id;
	$('#editGoodsDiv').dialog({
		title: "编辑",
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
					handler:save_Goods
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editGoodsDiv").dialog("close");
				}
		}]
	});
}


/**
 * 删除
 */

function deleteNotice(id){
	if(!id){
		$.messager.alert("消息","id不能为空");
		return;
	}
	$.messager.confirm("消息","确认删除该商品吗，删除后不可恢复",function(r){
		if(r){
			$.ajax({
				url:httpUrl+"/goods/deleteGoodsById.html?id="+id,
				type:"post",
				data:{},
				success:function(data){
					if(data.ret==1){
						$.messager.alert("消息","删除成功");
						$('#tt_Goods').datagrid('reload');
					}else{
						$.messager.alert("消息","删除失败，请稍后再试");
					}
				}
			});
		}
	});
}


/**
 * 保存更新
 */
function save_Goods(){
	//var formdata = $("#editGoodsForm").serialize();
	//console.info("formdata");
	var  url =httpUrl+"/goods/saveGoods.html?&rand=" + Math.random();
	
	// 创建表单数据对象
    var obj = new FormData();

    // 获取框中的数据
   // var title = $("#editGoodsForm input[name='title']").val();
    var title = $("#editGoodsForm #title").val();
    var goodsId = document.getElementById("goodsId").value;
    var goodsUnit = document.getElementById("goodsUnit").value;
    var shopPrice =document.getElementById("shopPrice").value;
    var goodsDesc =document.getElementById("goodsDesc").value;
    var status =$("#editGoodsForm #status").combobox('getValue');
    var profit =$("#editGoodsForm #profit").val();
    var detailLink =$("#editGoodsForm #detailLink").val();
    var saleCount =$("#editGoodsForm #saleCount").val();
    var goodsImg =$("#editGoodsForm #goodsImg").val();
    var goodsFlag =$("#editGoodsForm #goodsFlag").val();
    //var file = document.getElementById("goodsImg").files[0];
    if(title == null || title == ""){
		$.messager.alert("错误", "请填写商品名称");
		return;
	}
    if(goodsUnit == null || goodsUnit == ""){
		$.messager.alert("错误", "请填写商品单位");
		return;
	}
    if(shopPrice == null || shopPrice == ""){
		$.messager.alert("错误", "请填写商品价格");
		return;
	}
    if(goodsDesc == null || goodsDesc == ""){
		$.messager.alert("错误", "请填写商品内容");
		return;
	}
    

    // 将数据添加至表单数据对象中
    //obj.append("file", file);
    obj.append("title", title);
    obj.append("goodsId", goodsId);
    obj.append("shopPrice", shopPrice);
    obj.append("goodsUnit", goodsUnit);
    obj.append("goodsDesc", goodsDesc);
    obj.append("status", status);
    obj.append("saleCount", saleCount);
    obj.append("profit", profit);
    obj.append("detailLink", detailLink);
    obj.append("goodsImg", goodsImg);
    obj.append("goodsFlag", goodsFlag);
    
    
    $.ajax({   
		 type: 'POST',
		 dataType: 'json',
		 url: url,  
		 data:obj,
		 processData: false,
		 contentType : false,
		 success: function(data){ 
			 if(data.code ==="0"){
				 $("#editGoodsDiv").dialog("close");
				 $('#tt_Goods').datagrid('reload');
				 $.messager.alert("提示","操作成功","info");
			 }else{
				 $.messager.alert("提示","操作失败","error");
			 }   
		 } 
	});
}


function reloadDataGrid()
{
	$("#tt_Goods").datagrid("reload");
}





/*##########################公用方法##begin############################*/

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('tt_Goods').datagrid('resize',{  
		height:$("#body").height()-$('#search_areaGoods').height()-5,
		width:$("#body").width()
	});
	$('#search_areaGoods').panel('resize',{
		width:$("#body").width()
	});
	$('#detailLoanDiv').dialog('resize',{  
		height:$("#body").height()-25,
		width:$("#body").width()-30
	});
}
 
/*##########################公用方法##end############################*/