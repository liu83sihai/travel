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

td{
	font-size:16px;
}
</style>
</head>
<body>
	<table style="margin-left: auto;margin-right: auto;border-collapse:separate; border-spacing:10px;width:100%">
		<tr>
			<td style="font-size:16px;width:30%">平台以太坊账号:</td>
			<td><input type="text" class="easyui-validatebox" style="width:100%;height:30px;font-size:16px;" id="centerAccount" name="centerAccount" value="${accountNo}" disabled="disabled"></td>
		</tr>
		<tr>
			<td style="font-size:16px;">平台以太坊账号余额:</td>
			<td><input type="text" class="easyui-validatebox" style="width:100%;height:30px;font-size:16px;" id="centerAccountBalance" name="centerAccountBalance" value="${balance}" disabled="disabled"></td>
		</tr>
		<tr>
			<td style="font-size:16px;">接收人地址:</td>
			<td><input type="text" class="easyui-validatebox" style="width:100%;height:30px;font-size:16px;" id="receiveAddress" name="receiveAddress"></td>
		</tr>
		<tr>
            <td style="font-size:16px;">发送金额:</td>
            <td><input type="text" class="easyui-validatebox" style="width:100%;height:30px;font-size:16px;" id="amount" name="amount">
            </td>
        </tr>
        <tr>
            <td style="font-size:16px;">平台以太坊账号密码:</td>
            <td><input type="text" class="easyui-validatebox" style="width:100%;height:30px;font-size:16px;" id="password" name="password">
            </td>
        </tr>
	</table>
</body></html>