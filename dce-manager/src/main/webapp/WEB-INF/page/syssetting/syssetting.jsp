<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="renderer" content="webkit">

<%-- <jsp:include page="../common_easyui_cus.jsp"></jsp:include> --%>

<link rel="stylesheet" href="../css/dce/bootstrap.css">
<link rel="stylesheet" type="text/css" href="<c:url value='/js/common/js/jquery-easyui-1.4.1/themes/default/custom-easyui.css?v=${cssversion}'/>" />


<style>
.form-bonus-set {
    background-color: #ffffff;
    background-image: none;
    border: 1px solid #cccccc;
    border-radius: 3px;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
    color: #555555;
    font-size: 14px;
    height: 34px;
    line-height: 1.42857;
    padding: 6px 12px;
    transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
}
.set-small {
width:70px;
}
.set-middle {
width:100px;
}
.set-big {
width:90px;
}

.set-small1{
width:50px;
}
.set-middle1{
width:90px;
}
</style>


<script src="../js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<c:url value='../js/jquery-easyui/jquery.easyui.min.js?v=2.2.2'/>"></script>
<script language="javascript" type="text/javascript" src="../js/datepicker/WdatePicker.js"></script>
<style type="text/css">
@media (max-width: 767px){
#search {
     display: block;
}
}
</style>
</head>
<body>





<!--main-container-part-->
<div id="content">
<!--breadcrumbs-->
  
  <div  style="width:1000">
  
<form id="sysSetForm" action="../sysSet/save.html" method="post">
<input type="hidden" name="id" value="${ct.id }">
<div class="container">
    <div class="dashboard">
       <div class="row">
       
	           <div class="col-xs-12 col-sm-6 col-md-12">
	               <div class="panel panel-default">
	                   <div class="panel-heading">
	                       <i class="fa fa-cubes"></i>价格设置
	                   </div>
	                   <div class="panel-body" style="border-color: #ddd">
	                       <table class="table table-responsive">
		                       <tbody>	                       	   
	                               <tr>
	                              		<td style="line-height: -3px;">当前价 ：
	                               			<input type="text" id="d12"  name="price_open" value="${ct.price_open }" >
	                               		</td>
	                               </tr>
	                               <tr>
	                              		<td style="line-height: -3px;">最高价 ：
	                               			<input type="text" id="d12"  name="price_up" value="${ct.price_up }" >
	                               		</td>
	                              		<td style="line-height: -3px;">最低价 ：
	                               			<input type="text" id="d12"  name="price_down" value="${ct.price_down }" >
	                               		</td>
	                               </tr>
	                               
		                       </tbody>
		                   </table>
	                   </div>
	               </div>
	           </div>
	           <div class="col-xs-12 col-sm-6 col-md-12">
	               <div class="panel panel-default">
	                   <div class="panel-heading">
	                       <i class="fa fa-cubes"></i>交易开关设置
	                   </div>
	                   <div class="panel-body" style="border-color: #ddd">
	                       <table class="table table-responsive">
	                       		<tbody>	                       	   
	                               <tr>
	                               		<td style="line-height: -3px;">奖金开关 ：
	                               		
	                               			<select class="form-bonus-set" style="width:100px;" name="is_award_switch">
	                               				<option value="0" <c:if test="${ct.is_award_switch==0 }"> selected="selected" </c:if> >关</option>
	                               				<option value="1" <c:if test="${ct.is_award_switch==1 }"> selected="selected" </c:if> >开</option>
	                               			</select>
	                               		</td>
	                               		<td style="line-height: -3px;">释放开关 ：
	                               		
	                               			<select class="form-bonus-set" style="width:100px;" name="is_shifang_switch">
	                               				<option value="0" <c:if test="${ct.is_shifang_switch==0 }"> selected="selected" </c:if> >关</option>
	                               				<option value="1" <c:if test="${ct.is_shifang_switch==1 }"> selected="selected" </c:if> >开</option>
	                               			</select>
	                               		</td>
	                               	</tr>
	                               	<tr>
	                               		<td style="line-height: -3px;">充值开关 ：
	                               			<select class="form-bonus-set" style="width:100px;" name="is_ctstatus">
	                               				<option value="0" <c:if test="${ct.is_ctstatus==0 }"> selected="selected" </c:if> >关</option>
	                               				<option value="1" <c:if test="${ct.is_ctstatus==1 }"> selected="selected" </c:if> >开</option>
	                               			</select>
	                               		</td>
	                               		<td style="line-height: -3px;">提现开关 ：
	                               			<select class="form-bonus-set" style="width:100px;" name="is_tbstatus">
	                               				<option value="0" <c:if test="${ct.is_tbstatus==0 }"> selected="selected" </c:if> >关</option>
	                               				<option value="1" <c:if test="${ct.is_tbstatus==1 }"> selected="selected" </c:if> >开</option>
	                               			</select>
	                               		</td>
	                               	</tr>
	                               	<tr>
	                               		<td style="line-height: -3px;">挂单买卖开关 ：
	                               		
	                               			<select class="form-bonus-set" style="width:100px;" name="is_lock">
	                               				<option value="0" <c:if test="${ct.is_lock==0 }"> selected="selected" </c:if> >关</option>
	                               				<option value="1" <c:if test="${ct.is_lock==1 }"> selected="selected" </c:if> >开</option>
	                               			</select>
	                               		</td>
	                               		<td>
	                               		</td>
	                               </tr>
	                            </tbody>
		                   </table>
	                   </div>
	               </div>
	           </div>
	           <div class="col-xs-12 col-sm-6 col-md-12">
	               <div class="panel panel-default">
	                   <div class="panel-heading">
	                       <i class="fa fa-cubes"></i>收费设置
	                   </div>
	                   <div class="panel-body" style="border-color: #ddd">
	                       <table class="table table-responsive">
		                       <tbody>	  
		                            <tr>
	                              		<td style="line-height: -3px;">卖单手续费 ：
	                               			<input type="text" id="d12"  name="currency_sell_fee" value="${ct.currency_sell_fee }" >%
	                               		</td>
	                              		<td style="line-height: -3px;">以太坊交易费 ：
	                               			<input type="text" id="d12"  name="with_fee" value="<fmt:formatNumber value="${ct.with_fee }" pattern="0.00000000#"/>" >个以太币/笔
	                               		</td>
	                               </tr>      	   
		                       </tbody>
		                   </table>
	                   </div>
	               </div>
	           </div>
       </div>
   </div>
</div>
<input type="button" value="提交修改" id="saveButton" style="margin-top:14px;">

</form> 
<script>
$(document).ready(function(){
	var width = document.body.offsetWidth;
	var halfWidth = width/2;
	
	$("#saveButton").css("margin-left",halfWidth);
	
	$("#saveButton").click(function(){
		
		var url = "../sysSet/save.html?&rand=" + Math.random();
		var param = $("#sysSetForm").serialize();

	    $.ajax({
			type : 'POST',
			dataType : 'json',
			url : url,
			data : param,
			success : function(data) {
				if (data.code == 0) {
					$.messager.alert("提示", "操作成功", "info");
				} else {
					$.messager.alert("提示", data.msg, "error");
				}
			}
		});
	});
});
</script>
</div>

</div>

<!--end-main-container-part-->


<!-- <script type="text/javascript" src="../js/dce/admin.js"></script> -->



</body></html>