var basePath="/dce-manager";
$(function(){
/*#############################################search form begin#################################*/
    //产品类型

	$("#searchForm #searchButton").on("click",function(){
		var dataUrl = basePath+"/withdraw/list.html";
		$("#withDrawTable").datagrid('options').url = dataUrl;
		$("#withDrawTable").datagrid('load',{
			'userName': $("#searchForm #userName").val(),
			'startDate':$("#searchForm #user_reg_startDate").datebox('getValue'),
			'endDate':$("#searchForm #user_reg_endDate").datebox('getValue'),
			'type':$("#searchForm #type").datebox('getValue')
		});
	});

	$("#searchForm #resetButton").on("click",function(){
		$("#searchForm").form('reset');
	});

/*#############################################search form end#################################*/

/*##########################grid init begin###################################################*/
	var toolbar_tt = [];

	/*######################grid toolbar end##############################*/
	var columns_tt = [
      			[
	 				{field:"user_name",title:"用户名",width:30,align:"center"},
	 				{field:"true_name",title:"姓名",width:30,align:"center"},
	 				{field:"mobile",title:"手机号码",width:30,align:"center"},
	 				{field:"amount",title:"提现金额",width:30,align:"center"},
	 				{field:"withdrawDateStr",title:"申请时间",width:80,align:"center"},
	 				{field:"confirmDateStr",title:"审批时间",width:80,align:"center"},
	 				{field:"processStatusStr",title:"提现状态",width:30,align:"center",
	 					formatter:function(value,row,index){
	 						if(row.process_status == "1"){
	 							return "待审批";
	 						}else if(row.process_status == "2"){
	 							return "已审批";
	 						}else if(row.process_status == "3"){
	 							return "已拒绝";
	 						}
	 					}
	 				},
	 				{field:"fee",title:"提现手续费",width:35,align:"center"},
	 				{field:"withdrawStatus",title:"是否到账",width:30,align:"center"},
	 				{field:"type",title:"提现方式",width:45,align:"center",
	 					formatter:function(value,row,index){
	 						if(row.type == "1"){
	 							return "支付宝";
	 						}else if(row.type == "2"){
	 							return "银行卡";
	 						}else if(row.type == "3"){
	 							return "微信";
	 						}
	 					}
	 				},
	 				{field:"bank_no",title:"提现账号",width:45,align:"center"},
	 				{field:"bank",title:"提现银行",width:45,align:"center"},
	 				{field:"bankContent",title:"提现支行",width:45,align:"center"},
	 				{field:"paymentDateStr",title:"到账日期",width:80,align:"center"},
	 				
	 				{field:"edit",title:"审核",width:80,align:"center",
	 					formatter:function(value,row,index){
	 						if(row.type=='2'){
	 							if(row.process_status=="1"){
	 								return '<a href="javascript:void(0);"  onclick="auditWithdraw('+row.id+',\''+2+'\');">通过</a> |' +
	 								'<a href="javascript:void(0);"  onclick="auditWithdraw('+row.id+',\''+3+'\');">拒绝</a>';
	 							}
	 						}else{
	 							if(row.process_status=="1"){
		 							return '<a href="javascript:void(0);"  onclick="auditWithdraw('+row.id+',\''+2+'\');">通过</a> |' +
		 							'<a href="javascript:void(0);"  onclick="auditWithdraw('+row.id+',\''+3+'\');">拒绝</a>';
		 						}else if(row.process_status == "2"){
		 							if(row.withdrawStatus == "未到账"){
		 								return '<a href="javascript:void(0);"  onclick="auditWithdraw('+row.id+',\''+4+'\');">重做</a> |'+
		 								'<a href="javascript:void(0);"  onclick="auditWithdraw('+row.id+',\''+3+'\');">拒绝</a>';
		 							}else{
		 								return '<a href="javascript:void(0);"  onclick="to_viewTrans('+row.id+');">查看进展</a>';
		 							}
		 						}
	 						}
	 						
		 				}
	 				}
	 			]
	 	];

	/*######################grid columns end##############################*/

	$("#withDrawTable").datagrid({
		url: basePath+"/withdraw/list.html",
		height:$("#body").height()-$('#search_area').height()-10,
		width:$("#body").width(),
		rownumbers:true,
		fitColumns:true,
		singleSelect:false,//配合根据状态限制checkbox
		autoRowHeight:true,
		striped:true,
		checkOnSelect:false,//配合根据状态限制checkbox
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
	$("#withDrawTable").datagrid("reload");
}


/**
 * 查看转账详情
 * @param id
 */

function to_viewTrans(withdrawId){
	var url = httpUrl+"/withdraw/queryWithdraw.html?&withdrawId="+withdrawId;
	$('#editDiv').dialog({
		title: "查看转账详情",
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
						$("#editDiv").dialog("close");
				}
		}]
	});
}


function auditWithdraw(withdrawId, optType){
	var msg = optType=='2'?'提现通过':optType=='3'?'提现拒绝':'重做'; 
	$.messager.confirm("确认", "确认" + msg + "？", function (r) {  
        if (r) {  
        	$.ajax({
        		url:basePath+"/withdraw/auditWithdraw.html",
        		type:"post",
        		dataType: 'json',
        		data:{
        			"withdrawId":withdrawId,
        			"auditResult":optType
        		},
        		success:function(ret){
        			if(ret.code==0){
        				$.messager.alert("成功",msg + "成功");
        				reloadDataGrid();
        			}else{
        				$.messager.alert("失败",ret.msg);
        			}
        		}
        	});
        }  
    }); 
	
}

function auditWithdraw_bank(withdrawId, optType){
	var msg = optType=='2'?'提现通过':optType=='3'?'提现拒绝':'重做'; 
	$.messager.confirm("确认", "确认" + msg + "？", function (r) {  
        if (r) {  
        	$.ajax({
        		url:basePath+"/withdraw/auditWithdraw_bank.html",
        		type:"post",
        		dataType: 'json',
        		data:{
        			"withdrawId":withdrawId,
        		},
        		success:function(ret){
        			if(ret.code==0){
        				$.messager.alert("成功",msg + "成功");
        				reloadDataGrid();
        			}else{
        				$.messager.alert("失败",ret.msg);
        			}
        		}
        	});
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
	var type=$("#searchForm #type").datebox('getValue');
	var userName= $("#searchForm #userName").val();
	var startDate=$("#searchForm #user_reg_startDate").datebox('getValue');
	var endDate=$("#searchForm #user_reg_endDate").datebox('getValue');

	var exportIframe = document.createElement('iframe');
	exportIframe.src =basePath+"/withdraw/export.html"+"?type="+type+"&userName"+userName+"&startDate"+startDate+"&endDate"+endDate;
	exportIframe.style.display = 'none';
	document.body.appendChild(exportIframe);
}

$("#searchForm #resetButton").on("click",function(){
	$("#searchForm").form('reset');
});
/*################***导出**end*################### */



/*##########################公用方法##begin############################*/
/**
 * 得到选中的行
 * @returns {String}
 */
function get_ids(){
	var ids = '';
	var rows = $('#withDrawTable').datagrid('getChecked');
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
	var rows = $('#withDrawTable').datagrid('getChecked');
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
	$('#withDrawTable').datagrid('resize',{
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