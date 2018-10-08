<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="renderer" content="webkit">

<link rel="stylesheet" href="../css/dce/bootstrap.css">



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
  
<form id="jlzdFrom" action="../awardConfig/save.html" method="post">
<div class="container">
    <div class="dashboard">
       <div class="row">
       
	           <div class="col-xs-12 col-sm-6 col-md-12">
	               <div class="panel panel-default">
	                   <div class="panel-heading">
	                       <i class="fa fa-cubes"></i>单例设置
	                   </div>
	                   <div class="panel-body">
	                       <table class="table table-responsive">
		                       <tbody>	                       	   
	                               <tr>
	                               		<%-- <td style="line-height: -3px;">${FengCang.name } ：
	                               		
	                               			<select class="form-bonus-set" style="width:100px;" name="dictDtlId-remark-${FengCang.id}">
	                               				<option value="0" <c:if test="${FengCang.remark==0 }"> selected="selected" </c:if> >开</option>
	                               				<option value="1" <c:if test="${FengCang.remark==1 }"> selected="selected" </c:if> >关</option>
	                               			</select>
	                               		</td> --%>
	                              		<td style="line-height: -3px;">${ZhiTuiTime.name } ：
	                               			<input type="text" id="d12"  name="dictDtlId-remark-${ZhiTuiTime.id}" value="${ZhiTuiTime.remark }" readonly="readonly" onClick="WdatePicker()">
	                               			<img onclick="WdatePicker({el:'d12'})" src="../js/datepicker/skin/datePicker.gif" width="16" height="22" >
	                               		</td>
	                              		<td style="line-height: -3px;">${Point2RMB.name } ：
	                               			1现金币 = <input type="text"  name="dictDtlId-remark-${Point2RMB.id}" value="${Point2RMB.remark }" >人民币
	                               		</td>
	                               </tr>
	                               <tr>
	                               		<td style="line-height: -3px;">${WithDrawFee.name } ：
	                               			<input type="text" id="d12"  name="dictDtlId-remark-${WithDrawFee.id}-rate100" value="${WithDrawFee.remark }" >%
	                               		</td>
	                               		<td style="line-height: -3px;">${CashToIBAC.name } ：
	                               			1现金币 = <input type="text"  name="dictDtlId-remark-${CashToIBAC.id}" value="${CashToIBAC.remark }" >ATBT币
	                               		</td>
	                               </tr>
	                               <tr>
	                               		<td style="line-height: -3px;">${SecondShiFangRate.name } ：
                               				<input type="text" class="form-bonus-set set-small" name="dictDtlId-remark-${SecondShiFangRate.id}-rate" value="${SecondShiFangRate.remark }">‰
                               			</td>
	                               		<td style="line-height: -3px;">${ScoreToIBAC.name } ：
	                               			1流通币 = <input type="text"  name="dictDtlId-remark-${ScoreToIBAC.id}" value="${ScoreToIBAC.remark }" >ATBT币
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
	                       <i class="fa fa-cubes"></i>报单设置
	                   </div>
	                   <div class="panel-body">
	                       <table class="table table-responsive">
		                       <tbody>	                       	   
	                               <tr>
	                               	<c:forEach var="item" items="${BaoDanFei}" varStatus="no">
	                               		<td>${item.name }</td>
	                               	</c:forEach>
	                               </tr>
	                               <tr>
	                               		<c:forEach var="item" items="${BaoDanFei}" varStatus="no">
	                               			<td>
	                               				<input type="text" class="form-bonus-set" style="width:100px;" name="dictDtlId-remark-${item.id}" value="${item.remark }">
	                               			</td>
	                               			
	                               		</c:forEach>
	                                  	
	                               </tr> 
		                       </tbody>
		                   </table>
	                   </div>
	               </div>
	           </div>
	           <div class="col-xs-12 col-sm-6 col-md-12">
	               <div class="panel panel-default">
	                   <div class="panel-heading">
	                       <i class="fa fa-cubes"></i>封顶设置
	                   </div>
	                   <div class="panel-body">
	                       <table class="table table-responsive">
		                       <tbody>	                       	   
	                               <tr>
	                               	<c:forEach var="item" items="${FengDin}" varStatus="no">
	                               		<td>${item.name }</td>
	                               	</c:forEach>
	                               </tr>
	                               <tr>
	                               		<c:forEach var="item" items="${FengDin}" varStatus="no">
	                               			<td>
	                               				<input type="text" class="form-bonus-set" style="width:100px;" name="dictDtlId-remark-${item.id}" value="${item.remark }">
	                               			</td>
	                               			
	                               		</c:forEach>
	                                  	
	                               </tr> 
		                       </tbody>
		                   </table>
	                   </div>
	               </div>
	           </div>
	           
	           
	           <div class="col-xs-12 col-sm-6 col-md-12">
	               <div class="panel panel-default">
	                   <div class="panel-heading">
	                       <i class="fa fa-cubes"></i>持币生息
	                   </div>
	                   <div class="panel-body">
	                       <table class="table table-responsive">
		                       <tbody>	                       	   
	                               <tr>
	                               	<c:forEach var="item" items="${ProfitRate}" varStatus="no">
	                               		<td>${item.name }</td>
	                               	</c:forEach>
	                               </tr>
	                               <tr>
	                               		<c:forEach var="item" items="${ProfitRate}" varStatus="no">
	                               			<td>
	                               				<input type="text" class="form-bonus-set set-small" name="dictDtlId-remark-${item.id}-rate" value="${item.remark }">‰
	                               			</td>
	                               			
	                               		</c:forEach>
	                                  	
	                               </tr> 
		                       </tbody>
		                   </table>
	                   </div>
	               </div>
	           </div>
	           <div class="col-xs-12 col-sm-6 col-md-12">
	               <div class="panel panel-default">
	                   <div class="panel-heading"> 
	                       <i class="fa fa-cubes"></i>小区累计分享奖
	                   </div>
	                   <div class="panel-body">
	                       <table class="table table-responsive">
		                       <tbody>	                       	   
	                               <tr>
	                               	<c:forEach var="item" items="${ShareRate}" varStatus="no">
	                               		<td>${item.name }</td>
	                               	</c:forEach>
	                               </tr>
	                               <tr>
	                               		<c:forEach var="item" items="${ShareRate}" varStatus="no">
	                               			<td>
	                               				<input type="text" class="form-bonus-set set-small" name="dictDtlId-remark-${item.id}-rate" value="${item.remark }">‰
	                               			</td>
	                               			
	                               		</c:forEach>
	                                  	
	                               </tr> 
		                       </tbody>
		                   </table>
	                   </div>
	               </div>
	           </div>
	           <div class="col-xs-12 col-sm-6 col-md-12">
	               <div class="panel panel-default">
	                   <div class="panel-heading">
	                       <i class="fa fa-cubes"></i>直推奖
	                   </div>
	                   <div class="panel-body">
	                       <table class="table table-responsive">
		                       <tbody>	                       	   
	                               <tr>
	                               	<c:forEach var="item" items="${ZhiTui}" varStatus="no">
	                               		<td>${item.name }</td>
	                               	</c:forEach>
	                               </tr>
	                               <tr>
	                               		<c:forEach var="item" items="${ZhiTui}" varStatus="no">
	                               			<td>
	                               				<input type="text" class="form-bonus-set set-small" name="dictDtlId-remark-${item.id}-rate" value="${item.remark }">‰
	                               			</td>
	                               			
	                               		</c:forEach>
	                                  	
	                               </tr> 
		                       </tbody>
		                   </table>
	                   </div>
	               </div>
	           </div>
	           <div class="col-xs-12 col-sm-6 col-md-12">
	               <div class="panel panel-default">
	                   <div class="panel-heading">
	                       <i class="fa fa-cubes"></i>原始币钱包释放
	                   </div>
	                   <div class="panel-body">
	                       <table class="table table-responsive">
		                       <tbody>	                       	   
	                               <tr>
	                               	<c:forEach var="item" items="${OrigShiFangRate}" varStatus="no">
	                               		<td>${item.name }</td>
	                               	</c:forEach>
	                               </tr>
	                               <tr>
	                               		<c:forEach var="item" items="${OrigShiFangRate}" varStatus="no">
	                               			<td>
	                               				<input type="text" class="form-bonus-set set-small" name="dictDtlId-remark-${item.id}-rate" value="${item.remark }">‰
	                               			</td>
	                               			
	                               		</c:forEach>
	                                  	
	                               </tr> 
		                       </tbody>
		                   </table>
	                   </div>
	               </div>
	           </div>
	           <div class="col-xs-12 col-sm-6 col-md-12">
	               <div class="panel panel-default">
	                   <div class="panel-heading">
	                       <i class="fa fa-cubes"></i>日息币钱包释放
	                   </div>
	                   <div class="panel-body">
	                       <table class="table table-responsive">
		                       <tbody>	                       	   
	                               <tr>
	                               	<c:forEach var="item" items="${DaysShiFangRate}" varStatus="no">
	                               		<td>${item.name }</td>
	                               	</c:forEach>
	                               </tr>
	                               <tr>
	                               		<c:forEach var="item" items="${DaysShiFangRate}" varStatus="no">
	                               			<td>
	                               				<input type="text" class="form-bonus-set set-small" name="dictDtlId-remark-${item.id}-rate" value="${item.remark }">‰
	                               			</td>
	                               			
	                               		</c:forEach>
	                                  	
	                               </tr> 
		                       </tbody>
		                   </table>
	                   </div>
	               </div>
	           </div>
	           <div class="col-xs-12 col-sm-6 col-md-12">
	               <div class="panel panel-default">
	                   <div class="panel-heading">
	                       <i class="fa fa-cubes"></i>奖金币钱包释放
	                   </div>
	                   <div class="panel-body">
	                       <table class="table table-responsive">
		                       <tbody>	                       	   
	                               <tr>
	                               	<c:forEach var="item" items="${AwardShiFangRate}" varStatus="no">
	                               		<td>${item.name }</td>
	                               	</c:forEach>
	                               </tr>
	                               <tr>
	                               		<c:forEach var="item" items="${AwardShiFangRate}" varStatus="no">
	                               			<td>
	                               				<input type="text" class="form-bonus-set set-small" name="dictDtlId-remark-${item.id}-rate" value="${item.remark }">‰
	                               			</td>
	                               			
	                               		</c:forEach>
	                                  	
	                               </tr> 
		                       </tbody>
		                   </table>
	                   </div>
	               </div>
	           </div>
	           <div class="col-xs-12 col-sm-6 col-md-12">
	               <div class="panel panel-default">
	                   <div class="panel-heading">
	                       <i class="fa fa-cubes"></i>报单时流通币最多可使用比例
	                   </div>
	                   <div class="panel-body">
	                       <table class="table table-responsive">
		                       <tbody>	                       	   
	                               <tr>
	                               	<c:forEach var="item" items="${ScoreShiFangRate}" varStatus="no">
	                               		<td>${item.name }</td>
	                               	</c:forEach>
	                               </tr>
	                               <tr>
	                               		<c:forEach var="item" items="${ScoreShiFangRate}" varStatus="no">
	                               			<td>
	                               				<input type="text" class="form-bonus-set set-small" name="dictDtlId-remark-${item.id}-rate100" value="${item.remark }">%
	                               			</td>
	                               			
	                               		</c:forEach>
	                                  	
	                               </tr> 
		                       </tbody>
		                   </table>
	                   </div>
	               </div>
	           </div>
       </div>
   </div>
</div>
<input type="submit" value="提交修改" id="saveButton">
</form> 
<script>
$(document).ready(function(){
	var width = document.body.offsetWidth;
	var halfWidth = width/2;
	
	$("#saveButton").css("margin-left",halfWidth);
});
</script>
</div>

</div>

<!--end-main-container-part-->


<!-- <script type="text/javascript" src="../js/dce/admin.js"></script> -->



</body></html>