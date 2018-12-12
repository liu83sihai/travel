var basePath="/dce-manager";
$(function(){
/*#############################################search form begin#################################*/
    //产品类型

	$("#searchForm #searchButton").on("click",function(){
		var dataUrl = basePath+ "/userAccount/list.html";
		$("#userAccountTable").datagrid('options').url = dataUrl;
		$("#userAccountTable").datagrid('load',{
			'userName': $("#searchForm #userName").val()
			
		});
	});

	$("#searchForm #resetButton").on("click",function(){
		$("#searchForm").form('reset');
	});

/*#############################################search form end#################################*/

/*##########################grid init begin###################################################*/
	var toolbar_tt = [
	                 {
	          			iconCls: 'icon-edit',
	          			text:'增加',
	          			handler:addMoney
	          		},'-',{
	          			iconCls: 'icon-edit',
	          			text:'减少',
	          			handler:subMoney
	          		}
	          	];

	/*######################grid toolbar end##############################*/
	var columns_tt = [
      			[
	 			 	{field:'userId',title:'id',width:30,halign:"center", align:"left",checkbox:true},
	 				{field:"userName",title:"用户名",width:30,align:"center"},
	 				{field:"wallet_money",title:"现金余额(元)",width:80,align:"center"},
	 				{field:"wallet_travel",title:"积分",width:80,align:"center"},
	 				{field:"wallet_goods",title:"抵用券",width:80,align:"center"},
	 				{field:"wallet_active",title:"旅游卡",width:80,align:"center",hidden:true}
	 				/*{field:"wallet_score",title:"流通币钱包",width:80,align:"center"},
	 				{field:"wallet_cash",title:"现金币钱包",width:80,align:"center"},
	 				{field:"wallet_release_release",title:"可提币钱包",width:80,align:"center"}
	 				 */		
	 			]
	 	];

	/*######################grid columns end##############################*/

	$("#userAccountTable").datagrid({
		url:basePath+"/userAccount/list.html",
		height:$("#body").height()-$('#search_area').height()-10,
		width:$("#body").width(),
		rownumbers:true,
		fitColumns:true,
		singleSelect:true,//配合根据状态限制checkbox
		autoRowHeight:true,
		striped:true,
		checkOnSelect:true,//配合根据状态限制checkbox
		selectOnCheck:true,//配合根据状态限制checkbox

		pagination:true,
		showPageList:true,
		pageSize:20,
		pageList:[10,20,30],
		idField:"userId",
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
	$("#userAccountTable").datagrid("reload");
}

function addMoney(){
	changeMoney(1);
}
function subMoney(){
	changeMoney(0);
}
/**
 * 订单明细
 * @param id
 */
function changeMoney(type){
	var id = get_id();
	if(id == null || id == ""){
		return ;
	}
	var url = basePath+"/userAccount/changeMoney.html?&rand=" + Math.random()+"&userId="+ id + "&type=" + type;
	$('#editDiv').dialog({
		title: type == 1?"增加":"减少",
		width: 400,
		height: 300,
		closed: false,
		closable:false,
		cache: false,
		href: url,
		modal: true,
		toolbar:[
				{
					iconCls:"icon-save",text:"保存",
					handler:save_change
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editDiv").dialog("close");
				}
		}]
	});
}

function save_change(){
	var userId = $("#change_money_userId").val();
	var type = $("#change_money_type").val();
	var accountType = $("#change_money_accountType").combobox('getValue');
	var qty = $("#change_money_qty").val();
	
	if(accountType == null || accountType == ""){
		
		$.messager.alert("错误", "请选择账户类型");
		return;
	}
	if(qty == null || qty == ""){
		$.messager.alert("错误", "请输入金额成功");
		return;
	}
	$.ajax({
		url:basePath+"/userAccount/addAmount.html",
		type:"post",
		dataType: 'json',
		data:{
			"userId":userId,
			"accountType":accountType,
			"qty":qty,
			"type":type
		},
		success:function(ret){
			if(ret.code==0){
				$.messager.alert("成功",(type=='0'?"减少":"增加" ) + "成功");
				$("#editDiv").dialog("close");
				reloadDataGrid();
			}else{
				$.messager.alert("失败",ret.msg);
			}
		}
	});
}

/**
 * 格式化日期时间
 */
function formatDate(value) {
	var date = new Date(value);//long转换成date
	var year = date.getFullYear().toString();
	var month = (date.getMonth() + 1);
	var day = date.getDate().toString();
	var h = date.getHours();
	var m = date.getMinutes()
	var s = date.getSeconds();
	if (month < 10) {
		month = "0" + month;
	}
	if (day < 10) {
		day = "0" + day;
	}
	return year + "-" + month + "-" + day  + " " + h  + ":" + m  + ":" + s;
}
/*################***导出**begin*################### */
function export_excel(){
	/*var searchStr = $("#searchForm #searchStr").val();
	var productCode = $("#searchForm #search_productCode").combobox('getValue');
	var loanStatus =  $("#searchForm #search_loanStatus").combobox('getValue');
	var loanType = $("#searchForm #search_loanType").combobox('getValue');
    var cityCode= $("#searchForm #search_city").combobox('getValue');
	var channelType=$("#searchForm #search_channelType").combobox('getValue');
	var startDate =  $("#searchForm #search_startDate").datebox('getValue');
	var endDate =  $("#searchForm #search_endDate").datebox('getValue');
	var processNextStep = $("#searchForm #search_processNextStep").combobox('getValue');
	//document.getElementById("exportExcel").disabled = true;
	//document.getElementById("exportExcel").value = "正在导出";
	var exportIframe = document.createElement('iframe');
	exportIframe.src = basePath+"/loan/export/excel.html"+ "?searchStr="+ searchStr+ "&productCode="+ productCode+
	"&loanType="+loanType+ "&loanStatus="+ loanStatus+"&cityCode="+ cityCode+"&channelType="+ channelType+ "&startDate="+startDate+ "&endDate="+ endDate+
	"&processNextStep="+processNextStep;

	exportIframe.style.display = 'none';
	document.body.appendChild(exportIframe);*/
	
	
	var exportIframe = document.createElement('iframe');
	exportIframe.src = httpUrl + "/userAccount/export.html";

	exportIframe.style.display = 'none';
	document.body.appendChild(exportIframe);
	
}
/*################***导出**end*################### */


/*##########################公用方法##begin############################*/
/**
 * 得到选中的行
 * @returns {String}
 */
function get_ids(){
	var ids = '';
	var rows = $('#userAccountTable').datagrid('getChecked');
	if(rows.length == 0){
		$.messager.alert("提示","请选择需要操作的数据", "info");
		return "";
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
	var rows = $('#userAccountTable').datagrid('getChecked');
	if(rows.length == 0){
		$.messager.alert("提示","请选择需要操作的数据","info");
		return "";
	}else if(rows.length > 1){
		$.messager.alert("提示","每次只能操作一条数据","info");
		return "";
	}else{
		return rows[0].userId;
	}
}

//监听窗口大小变化
window.onresize = function(){
	setTimeout(domresize,300);
};
//改变表格和查询表单宽高
function domresize(){
	$('#userAccountTable').datagrid('resize',{
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
/*##########################公用方法##end############################*/