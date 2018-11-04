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
<link rel="stylesheet" href="../css/dce/bootstrap-responsive.min.css">
<link rel="stylesheet" href="../css/dce/fullcalendar.css">
<link rel="stylesheet" type="text/css" href="../css/dce/admin.css">
<link rel="stylesheet" href="../css/dce/matrix-style.css">
<link rel="stylesheet" href="../css/dce/matrix-media.css">
<link rel="stylesheet" href="../css/dce/font-awesome.css">
<link rel="stylesheet" type="text/css" href="../css/dce/news1.css"> 



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


<script src="../js/jquery-1.9.1.min.js"></script>

<link rel="stylesheet" type="text/css" href="../css/dce/bigcolorpicker.css">
<link rel="stylesheet" href="../css/dce/layer.css" id="layui_layer_skinlayercss">
<link rel="stylesheet" href="../css/dce/layer.ext.css" id="layui_layer_skinlayerextcss">
<link type="text/css" rel="stylesheet" href="../css/dce/laydate.css">
<link type="text/css" rel="stylesheet" href="../css/dce/laydate1.css" id="LayDateSkin">
<script type="text/javascript" src="<c:url value='../js/common/jquery.easyui.min.js?v=2.2.2'/>"></script>
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
  
  <div class="container-fluid">
  
<div class="container">
    <div class="dashboard">
       <div class="row">
       
           <div class="col-xs-12 col-sm-6 col-md-12">
               <div class="panel panel-default">
                   <div class="panel-heading">
                       <i class="fa fa-cubes"></i>空单激活
                   </div>
                   <div class="panel-body">
                       <table class="table table-responsive">
	                       <tbody>
	                       		<tr>
                               		<td align="right">客户编号:</td>
                               		<td>
                                  	 <input type="text" class="form-bonus-set" name="userCode" id="userCode" value="${item.remark }" style="widht:200px;">
                                    </td>
                               </tr>
                               <tr>
                                   <td align="right">客户级别:</td>
                                   <td>
                                   <select name="userLevel" id="userLevel" class="form-bonus-set" style="widht:200px;">
                                   		<option value="">--请选择客户级别--</option>
	                                   <c:forEach var="item" items="${KHJB}" varStatus="no">
	                                   		<option value="${item.code }">${item.remark }</option>
	                                   </c:forEach>
                                   
                                   </select>
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
<input type="button" value="提交设置" id="saveButton">
<script>
$(document).ready(function(){
	var width = document.body.offsetWidth;
	var halfWidth = width/2;
	$("#saveButton").css("margin-left",halfWidth);
	
	$("#saveButton").click(function(){
		var userCode = $("#userCode").val();
		var userLevel = $("#userLevel").val();
		$.ajax({
			 type: 'POST',
			 dataType: 'json',
			 url: "../user/setUserLevel.do",
			 data:{
				 userCode:userCode,
				 userLevel:userLevel
			 },
			 success: function(data){
				 alert(data.msg);
			 }
		});
	});
});
</script>
</div>

</div>



</body></html>