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
    <title>商品详情</title>
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <link rel="stylesheet" href="<c:url value='/css/travel/ydui.rem.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/travel/main.css'/>">
</head>

<body>
    <div id="app" v-loak>
        <yd-slider :autoplay="4000" pagination-activecolor="#ffffff" class="index-slider">
            <yd-slider-item>
            	<div class="index-slide-item" style="background-image: url(http://via.placeholder.com/350x165)"></div>
	      	</yd-slider-item>
            <yd-slider-item>
            	<div class="index-slide-item" style="background-image: url(http://via.placeholder.com/350x165)"></div>
	        </yd-slider-item>
            <yd-slider-item>
            	<div class="index-slide-item" style="background-image: url(http://via.placeholder.com/350x165)"></div>
	        </yd-slider-item>
        </yd-slider>
        <div class="prodcut-title">
        	<h2>万家福家居日用生活馆万家福家居日用生活馆万家福家居日用生活馆</h2>
        	<div class="num">
        		<span class="price">￥999.99</span>
        	</div>
        </div>

        <div class="product-detail">
        	<div class="title">商品介绍</div>
        	<div class="prodcut-article" id="detail">
        		<p>Vue-ydui 是 YDUI Touch的一个Vue2.x实现版本，专为移动端打造，在追求完美视觉体验的同时也保证了其性能高效。</p>
        		<p>&nbsp;</p>
        		<p>Vue-ydui 是 YDUI Touch的一个Vue2.x实现版本，专为移动端打造，在追求完美视觉体验的同时也保证了其性能高效。</p>
        		<p>&nbsp;</p>

        		<p>Vue-ydui 是 YDUI Touch的一个Vue2.x实现版本，专为移动端打造，在追求完美视觉体验的同移动端打造，在追求完美视觉体验的同移动端打造，在追求完美视觉体验的同时也保证了其性能高效。</p>
				<p>&nbsp;</p>
        		<img src="http://via.placeholder.com/350x165" alt="">
        	</div>
        </div>
    </div>
    <script src="<c:url value='/js/trave/vue.min.js'/>"></script>
    <script src="<c:url value='/js/trave/ydui.rem.js'/>"></script>
    <script src="<c:url value='/js/trave/ydui.flexible.js'/>"></script>
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