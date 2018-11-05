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
    <title>旅游路线详情</title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <link rel="stylesheet" href="<c:url value='/css/travel/ydui.rem.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/travel/main.css'/>">
</head>

<body>
    <div id="app" v-loak>
        <yd-slider :autoplay="4000" pagination-activecolor="#ffffff" class="index-slider">
            <c:if test="${not empty bannerImgs }">
			<c:forEach items="${bannerImgs}"  var="oneImg">
       			<yd-slider-item>
            		<div class="index-slide-item" style="background-image: url(${oneImg})"></div>
	      		</yd-slider-item>
       		</c:forEach>
       		</c:if>
        		
            
        </yd-slider>
        <div class="prodcut-title">
        	<h2>${travelPath.linename}</h2>
        	<div class="num">
        		<span class="price">￥${travelPath.price}</span>
        	</div>
        </div>

        <div class="product-detail">
        	<div class="title">旅游路线介绍</div>
        	<div class="prodcut-article" id="detail">
        		<p>${travelPath.outline}</p>
				<p>&nbsp;</p>
				<c:if test="${not empty detailImgs }">
				<c:forEach items="${detailImgs}"  var="oneImg">
        		<img src="${oneImg}" alt="">
        		</c:forEach>
        		</c:if>
        	</div>
        </div>
    </div>
    <script src="<c:url value='/js/travel/vue.min.js'/>"></script>
    <script src="<c:url value='/js/travel/ydui.rem.js'/>"></script>
    <script src="<c:url value='/js/travel/ydui.flexible.js'/>"></script>
    <script>
    new Vue({
        el: '#app',
        mounted: function(){
        	document.querySelectorAll('#detail img').forEach(function(el){
        		el.width = el.style.width="100%"
        		el.height = el.style.height="auto"
        	})
        }
    })
    </script>
</body>

</html>