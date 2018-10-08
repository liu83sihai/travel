var basePath="/dce-manager";
$(function(){
/*#############################################search form begin#################################*/
    //产品类型

	$("#searchForm #searchButton").on("click",function(){
		var dataUrl = basePath+"/userAccountDetail/list.html";
		$("#userAccountDetailTable").datagrid('options').url = dataUrl;
		$("#userAccountDetailTable").datagrid('load',{
			'userName': $("#searchForm #userName").val(),
			'startDate':$("#searchForm #user_reg_startDate").datebox('getValue'),
			'endDate':$("#searchForm #user_reg_endDate").datebox('getValue'),
			'seacrchAccountType':$("#searchForm #seacrchAccountType").datebox('getValue'),
			'remark':$("#searchForm #remark").val()
		});
	});

	$("#searchForm #resetButton").on("click",function(){
		$("#searchForm").form('reset');
	});

/*#############################################search form end#################################*/

/*##########################grid init begin###################################################*/
	var toolbar_tt = [
	                /* {
	          			iconCls: 'icon-edit',
	          			text:'充值',
	          			handler:export_excel
	          		},'-',{
	          			iconCls: 'icon-edit',
	          			text:'划扣',
	          			handler:syn_loan_data
	          		}*/
	          	];

	/*######################grid toolbar end##############################*/
	var columns_tt = [
      			[
	 			 	{field:'id',title:'id',width:30,halign:"center", align:"left",hidden:true},
	 				{field:"userName",title:"用户名",width:40,align:"center"},
	 				{field:"userLevel",title:"用户等级",width:40,align:"center",
	 					formatter:function(value,row,index){
	 						if(value == "0"){
	 							return "普通用户";
	 						}
	 						if(value == "1"){
	 							return "会员";
	 						}
	 						if(value == "2"){
	 							return "vip";
	 						}
	 						if(value == "3"){
	 							return "城市合伙人";
	 						}
	 						if(value == "4"){
	 							return "分红股东";
	 						}
	 					}
	 				},
	 				{field:"amount",title:"金额",width:40,align:"center"},
	 				{field:"balanceAmount",title:"余额",width:40,align:"center"},
	 				{field:"moreOrLess",title:"增加/减少",width:30,align:"center"},
	 				/*{field:"incomeType",title:"流水类型",width:80,align:"center",
	 					formatter:function(value,row,index){
	 						if(value == "1"){
	 							return "静态释放";
							}
							if (value == "12") {
								return "购买订单";
							}
							if (value == "11") {
								return "卖出订单";
							}
							if (value == "13") {
								return "卖单撤销返还";
							}
							if (value == "14") {
								return "买单撤销返还";
							}
							if (value == "21") {
								return "认购";
							}
							if (value == "22") {
								return "提现";
							}
							if (value == "23") {
								return "提现拒绝";
							}
							if (value == "311") {
								return "推荐奖励";
							}
							if (value == "312") {
								return "领导奖励";
							}
							if (value == "401") {
								return "报单激活";
							}
							if (value == "501") {
								return "挂单卖出";
							}
							if (value == "502") {
								return "挂单买入";
							}
							if (value == "601") {
								return "扫码支付";
							}
							if (value == "702") {
								return "交易手续费";
							}
							if (value == "801") {
								return "转入";
							}
							if (value == "802") {
								return "转出";
							}
							if (value == "1001") {
								return "持币生息";
							}
							if (value == "1002") {
								return "分享奖";
							}
							if (value == "1003") {
								return "奖金币钱包释放";
							}
							if (value == "1004") {
								return "原始币钱包释放";
							}
							if (value == "1005") {
								return "日息币钱包释放";
							}
							if (value == "1006") {
								return "释放币钱包释放";
							}
							if (value == "1011") {
								return "充值奖励";
							}
							if (value == "1012") {
								return "充值代数奖励";
							}
						}
	 				},*/
	 				{field:"createTime",title:"交易时间",width:80,align:"center",
	 					formatter:function(value,row,index){
	 						if(value > 0){
	 							return dateTimeFormatter(value);
	 						}
	 						return value;
	 					}
	 				},
	 				{field:"accountType",title:"账户类型",width:80,align:"center",
	 					formatter:function(value,row,index){
	 						if(value == "wallet_money"){
	 							return "账户余额";
							}
							if (value == "wallet_travel") {
								return "奖励旅游";
							}
							if (value == "wallet_goods") {
								return "奖励商品";
							}
							if (value == "wallet_active") {
								return "奖励活动";
							}
							return value;
						}
	 				},
	 				{field:"remark",title:"交易说明",width:180,align:"center"}
	 				
	 			]
	 	];

	/*######################grid columns end##############################*/

	$("#userAccountDetailTable").datagrid({
		url:basePath+"/userAccountDetail/list.html",
		height:$("#body").height()-$('#search_area').height()-10,
		width:$("#body").width(),
		rownumbers:true,
		fitColumns:true,
		singleSelect:false,//配合根据状态限制checkbox
		autoRowHeight:true,
		striped:true,
		checkOnSelect:true,//配合根据状态限制checkbox
		selectOnCheck:false,//配合根据状态限制checkbox
/*		loadFilter : function(data){
			return {
				'rows' : data.datas,
				'total' : data.total,
				'pageSize' : data.pageSize,
				'pageNumber' : data.page
			};
		},*/

		pagination:true,
		showPageList:true,
		pageSize:20,
		pageList:[10,20,30],
		idField:"id",
//		frozenColumns : [[{field:'ck',checkbox:true}]],
		columns:columns_tt,
		toolbar:toolbar_tt
	});

	/*$(window).resize(function (){
		domresize();
	 }); */
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
	$("#userAccountDetailTable").datagrid("reload");
}

/**
 * 订单明细
 * @param id
 */
function to_edit(id){
	var url = basePath+"/loan/toupdate.html?&rand=" + Math.random()+"&loanId="+id;
	$('#editDiv').dialog({
		title: "编辑订单",
		width: 500,
		height: 400,
		closed: false,
		closable:false,
		cache: false,
		href: url,
		modal: true,
		toolbar:[
				{
					iconCls:"icon-save",text:"保存",
					handler:save_loan
				},
				{
					iconCls:"icon-no",text:"关闭",
					handler:function(){
						$("#editDiv").dialog("close");
				}
		}]
	});
}

/**
 * 格式化日期时间
 */
/*function formatDate(value) {
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
	if (h < 10) {
		h = "0" + h;
	}
	if (m < 10) {
		m = "0" + m;
	}
	if (s < 10) {
		s = "0" + s;
	}
	return year + "-" + month + "-" + day  + " " + h  + ":" + m  + ":" + s;
}*/
/*################***导出**begin*################### */
function export_excel(){
	var searchStr = $("#searchForm #searchStr").val();
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
	exportIframe.src ="/loan/export/excel.html"+ "?searchStr="+ searchStr+ "&productCode="+ productCode+
	"&loanType="+loanType+ "&loanStatus="+ loanStatus+"&cityCode="+ cityCode+"&channelType="+ channelType+ "&startDate="+startDate+ "&endDate="+ endDate+
	"&processNextStep="+processNextStep;

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
	var rows = $('#userAccountDetailTable').datagrid('getChecked');
	if(rows.length == 0){
		$.messager.alert("提示","请选择需要操作的数据", "info");
		return false;
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
	var rows = $('#userAccountDetailTable').datagrid('getChecked');
	if(rows.length == 0){
		$.messager.alert("提示","请选择需要操作的数据","info");
		return false;
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
	$('#userAccountDetailTable').datagrid('resize',{
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