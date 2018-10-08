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


<script src="../js/dce/jquery.min.js"></script>

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
  
<form id="jlzdFrom" action="<c:url value='/eth/setCenterAcc.do'/>" method="post">
<div class="container">
    <div class="dashboard">
       <div class="row">
       
           
            <div class="col-xs-12 col-sm-6 col-md-6" style="width:100%">
               <div class="panel panel-default">
                   <div class="panel-heading">
                       <i class="fa fa-cubes"></i>中心账户
                   </div>
                   <div class="panel-body">
                       <table class="table table-responsive"> 
	                       <tbody>	                       	   
                               <tr>
                               		<td>中心账户编码<input type="text" class="form-bonus-set set-middle" style="width:100%" id="centerAccount" name="centerAccount" value="${centerAccount}">
								   </td>
								   <td>中心账户密码<input type="text" class="form-bonus-set set-middle" style="width:100%" id="password" name="password" value="${password}">
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
<input type="submit" value="提交修改" id="saveButton">
</form> 
<script>
$(document).ready(function(){
	var width = document.body.offsetWidth;
	var halfWidth = width/2;
	$("#saveButton").css("margin-left",halfWidth);
	var optResult = '${opt}';
	if('0' == optResult){
		alert("操作成功");
	}
	if('1' == optResult){
		alert("操作失败");
	}
});
</script>
</div>

</div>

</body></html>