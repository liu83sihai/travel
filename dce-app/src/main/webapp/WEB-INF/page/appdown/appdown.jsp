<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no,minimal-ui">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <meta name="apple-touch-fullscreen" content="yes">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="full-screen" content="yes">
    <meta name="browsermode" content="application">
    <meta name="x5-orientation" content="portrait">
    <meta name="x5-fullscreen" content="true">
    <meta name="x5-page-mode" content="app">
    <meta name="format-detection" content="telephone=no" />
    <title>app下载</title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <link rel="stylesheet" href="<c:url value='/css/travel/ydui.rem.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/travel/main.css'/>">
</head>

<body>
    <div id="app" v-loak>

        <div class="product-detail">
        	<div class="prodcut-article" id="detail">
        		<p style="text-align: -webkit-center;">Android下载</p>
				<p>&nbsp;</p>
				<img style="width:100%;" src="<c:url value='/images/androiddown.png'/>" alt="">
        	</div>
        	
        	<div class="prodcut-article" id="detail">
        		<p style="text-align: -webkit-center;">IOS下载</p>
				<p>&nbsp;</p>
				<img style="width:100%;" src="<c:url value='/images/iosdown.png'/>" alt="">
        	</div>
        	
        </div>
    </div>
    <script type="text/javascript">
    </script>
</body>

</html>