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
  
<div class="container">
    <div class="dashboard">
       <div class="row">
       
           
            <div class="col-xs-12 col-sm-6 col-md-6" style="width:100%">
               <div class="panel panel-default">
                   <div class="panel-heading">
                       <i class="fa fa-cubes"></i>以太坊转账
                   </div>
                   <div class="panel-body">
                       <table class="table table-responsive"> 
	                       <tbody>	                       	   
                               <tr>
                               	   <td>平台以太坊账号<input type="text" class="form-bonus-set set-middle" style="width:100%" id="centerAccount" name="centerAccount" value="${accountNo}" disabled="disabled">
								   </td>
							   </tr>
							   <tr>
								   <td>平台以太坊账号余额<input type="text" class="form-bonus-set set-middle" style="width:100%" id="centerAccountBalance" name="centerAccountBalance" value="${balance}" disabled="disabled">
                                   </td>
                               </tr>
                               <tr>
                                   <td>接收人地址<input type="text" class="form-bonus-set set-middle" style="width:100%" id="receiveAddress" name="receiveAddress">
                                   </td>
                               </tr>
                               <tr>
                                   <td>发送金额<input type="text" class="form-bonus-set set-middle" style="width:100%" id="amount" name="amount">
                                   </td>
                               </tr>
                               <tr>
                                   <td>平台以太坊账号密码<input type="text" class="form-bonus-set set-middle" style="width:100%" id="password" name="password">
                                   </td>
                               </tr>
                              
	                       </tbody>
	                   </table>
                   </div>
               </div>
               <div class="info" id="info" style="margin-left: 30px;margin-top:2px;width: 210px; float: right; ">
                    <a class="tiaozhuan" href="javascript:void(0);" onclick="send();">提交</a>
              </div>  
           </div>
           
       </div>
   </div>
</div>
<script>
$(document).ready(function(){
	/* var width = document.body.offsetWidth;
	var halfWidth = width/2;
	$("#saveButton").css("margin-left",halfWidth);
	var optResult = '${opt}';
	if('0' == optResult){
		alert("操作成功");
	}
	if('1' == optResult){
		alert("操作失败");
	} */
});

function send() {
	var centerAccount = $("#centerAccount").val();
	var centerAccountBalance = $("#centerAccountBalance").val();
	var receiveAddress = $("#receiveAddress").val();
	var amount = $("#amount").val();
	var password = $("#password").val();
	
	if (receiveAddress == "" || receiveAddress == undefined) {
		alert("请输入接收账号");
		return false;
	}
	if (amount == "" || amount == undefined) {
        alert("请输入发送金额");
        return false;
    }
	if (password == "" || password == undefined) {
        alert("请输入平台以太坊账号密码");
        return false;
    }
	if (parseFloat(amount) > parseFloat(centerAccountBalance)) {
		alert("发送金额不能大于平台以太坊账号余额");
		return false;
	}
	
	var msg = "是否确认发送金额？"; 
    if (confirm(msg)==true){ 
        var url = "<c:url value='/eth/tranPlatFormEth.do'/>";
        var data = {"centerAccount":centerAccount,"receiveAddress":receiveAddress,"amount":amount,"password":password};
        $.ajax({
            cache: false,
            type: "POST",
            url:url,  
            data:data,  
            async: false,
            error: function(request) {
                alert("发送请求失败！");},
            success: function(data) {
                //data = $.parseJSON(data);
                if(data.code == '0'){
                    alert("提示", "发送成功", "info");
                    url = "<c:url value='/eth/platform/accounts.do'/>";
                    window.location.href=url;
                } else{
                    var msg = data.msg;
                    if(msg != null){
                        alert("提示",data.msg, "error");
                    }else{
                        alert("提示","操作失败", "error");
                    }
                }
            }});
        return true;
    }else{ 
        return false; 
    } 
}
</script>
</div>

</div>

</body></html>