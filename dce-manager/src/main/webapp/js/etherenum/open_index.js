var basePath="/dce-manager";
$(function(){
/*#############################################search form begin#################################*/
    //产品类型

	$("#searchForm #searchButton").on("click",function(){
		var dataUrl = basePath+ "/ethopenAcct/list.html";
		$("#ethereOpenAccountTable").datagrid('options').url = dataUrl;
		$("#ethereOpenAccountTable").datagrid('load',{
			'keyword': $("#searchForm #keyword").val()
		});
	});

	$("#searchForm #resetButton").on("click",function(){
		$("#searchForm").form('reset');
	});

/*#############################################search form end#################################*/

/*##########################grid init begin###################################################*/
	var toolbar_tt = [
	                /* {
	          			iconCls: 'icon-excel',
	          			text:'导出',
	          			handler:export_excel
	          		},'-',{
	          			iconCls: 'icon-edit',
	          			text:'同步放款数据',
	          			handler:syn_loan_data
	          		}*/
	          	];

	/*######################grid toolbar end##############################*/
	var columns_tt = [
      			[
	 			 	
	 			 	{field:'id',title:'用户id',width:30,halign:"center", align:"center",hidden:true},
	 				{field:"userName",title:"用户名",width:30,align:"center"},
	 				{field:"trueName",title:"姓名",width:30,align:"center"},
	 				{field:"mobile",title:"手机号码",width:30,align:"center"},
	 				{field:"isOpen",title:"是否已开户",width:30,align:"center",
	 					formatter:function(value,row,index){
	 						if(row.ethAccount != null){
	 							return "已开户";
	 						}
	 						return "未开户";
	 					}
	 				},
	 				{field:"ethAccount",title:"以太坊账户",width:80,align:"center"},
	 				{field:"edit",title:"操作",width:30,align:"center",
	 					formatter:function(value,row,index){
	 						if(row.ethAccount == null || row.ethAccount == ""){
	 							return '<a href="javascript:void(0);"  onclick="creatAccount(\''+row.id+'\');">开户</a>';
	 						}
	 						return "";
	 					}
	 				}
	 			]
	 	];

	/*######################grid columns end##############################*/

	$("#ethereOpenAccountTable").datagrid({
		url: basePath+"/ethopenAcct/list.html",
		height:$("#body").height()-$('#search_area').height()-10,
		width:$("#body").width(),
		rownumbers:true,
		fitColumns:true,
		singleSelect:false,//配合根据状态限制checkbox
		autoRowHeight:true,
		striped:true,
		checkOnSelect:false,//配合根据状态限制checkbox
		selectOnCheck:false,//配合根据状态限制checkbox
		pagination:true,
		showPageList:true,
		pageSize:20,
		pageList:[10,20,30],
		idField:"loanId",
//		frozenColumns : [[{field:'ck',checkbox:true}]],
		columns:columns_tt,
		toolbar:toolbar_tt
	});

/*##########################grid init end###################################################*/
});

/**
 * 使用方法:
 * 开启:MaskUtil.mask();
 * 关闭:MaskUtil.unmask();
 *
 * MaskUtil.mask('其它提示文字...');
 */
var MaskUtil = (function(){

	var $mask,$maskMsg;

	var defMsg = '正在处理，请稍待。。。';

	function init(){
		if(!$mask){
			$mask = $("<div class=\"datagrid-mask mymask\"></div>").appendTo("body");
		}
		if(!$maskMsg){
			$maskMsg = $("<div class=\"datagrid-mask-msg mymask\">"+defMsg+"</div>")
				.appendTo("body").css({'font-size':'12px'});
		}

		$mask.css({width:"100%",height:$(document).height()});

		var scrollTop = $(document.body).scrollTop();

		$maskMsg.css({
			left:( $(document.body).outerWidth(true) - 190 ) / 2
			,top:( ($(window).height() - 45) / 2 ) + scrollTop
		});

	}

	return {
		mask:function(msg){
			init();
			$mask.show();
			$maskMsg.html(msg||defMsg).show();
		}
		,unmask:function(){
			$mask.hide();
			$maskMsg.hide();
		}
	}

}());

function reloadDataGrid()
{
	$("#ethereOpenAccountTable").datagrid("reload");
}

function creatAccount(userid) {
    
    $.messager.confirm("确认", "是否确认开户？" , function (r) {  
        if (r) {  
        	MaskUtil.mask('开户中,请等待...');
        	$.ajax({
        		url:basePath+"/ethopenAcct/create.html",
        		type:"post",
        		dataType: 'json',
        		data:{
        			"userid":userid
        		},
        		success:function(ret){
        			MaskUtil.unmask();
        			if(ret.code==0){
        				$.messager.alert("成功", "开户成功");
        				reloadDataGrid();
        			}else{
        				$.messager.alert("失败",ret.msg);
        			}
        		}
        	});
        }  
    });
}

/*##########################公用方法##begin############################*/
/**
 * 得到选中的行
 * @returns {String}
 */
function get_ids(){
	var ids = '';
	var rows = $('#ethereOpenAccountTable').datagrid('getChecked');
	if(rows.length == 0){
		$.messager.alert("提示","请选择需要操作的数据", "info");
		return;
	}else{
		for(var i=0; i<rows.length; i++){
			ids += rows[i].loanId + ',';
		}
		ids = ids.substring(0, ids.length - 1);
		return ids;
	}
}
/**
 * 得到一条数据
 * @returns {String}
 */
function get_id(){
	var rows = $('#ethereOpenAccountTable').datagrid('getChecked');
	if(rows.length == 0){
		$.messager.alert("提示","请选择需要操作的数据","info");
		return;
	}else if(rows.length > 1){
		$.messager.alert("提示","每次只能操作一条数据","info");
		return;
	}else{
		return rows[0].loanId;
	}
}

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('#ethereOpenAccountTable').datagrid('resize',{
		height:$("#body").height()-$('#search_area').height()-5,
		width:$("#body").width()
	});
	$('#search_area').panel('resize',{
		width:$("#body").width()
	});
	$('#detailLoanDiv').dialog('resize',{
		height:$("#body").height()-25,
		width:$("#body").width()-30
	});
}
