<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" />
		<meta name="format-detection" content="telephone=no">
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<title>扫描支付</title>
		
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<c:url value ='/res/css/public.css' />">
<link rel="stylesheet" type="text/css" href="<c:url value ='/res/css/modal.css' />">
<link rel="stylesheet" type="text/css" href="<c:url value ='/res/css/litem.css' />">

<style type="text/css">
	.header{border: 1px solid #EDEFF2;}
	.simg{background-size: 100% auto; 
			background-repeat: no-repeat; 
			background-image: url("<c:url value ='/barcode/paybarcode.do' />?userId=${userId}&orderId=${orderId}"); }
	.simg:before{content: ""; 
				display: block; 
				padding-top: 200%;}
</style>
</head>
	
<body>
	
		<div class="header">
			<a class="a-fl" href="<c:url value='/agent/agentIndex.do'/>"><i class="icon i-back"></i></a>
			<p class="title">扫描支付</p>
		</div>
		<div id="shareImg" class="simg">
			<div class="rec_btn" style="position:fixed;bottom:0; width:100%;background: #F7931D;">
	             	请用微信扫描支付
             </div>
		</div>
		
	</body>
	
</html>