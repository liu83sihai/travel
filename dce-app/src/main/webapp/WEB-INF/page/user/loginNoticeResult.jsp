<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<title>红包</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css_v1/public.css?v=${cssversion}">
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css_v1/modal.css?v=${cssversion}" />
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css_v1/litem.css?v=${cssversion}">
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css_v1/card.css?v=${cssversion}">
    <style type="text/css">
    	
		.empty-tip {
		    text-align: center;
		    font-size: 14px;
		    color: #999;
		    padding: 50px 0 20px;
		}
		.empty-tip i {
		    background-image: url(http://app.zjzwly.com/dce-app/images/succe.jpg);
		    -webkit-background-size: 67px auto;
		    background-size: 67px auto;
		    display: inline-block;
		    width: 67px;
		    height: 60px;
		    background-repeat: no-repeat;
		}
		.empty-tip p {
		    padding: 5px 0;
		    font-size: 16px;
		}
		.center {
		    display: -webkit-box;
		    display: -webkit-flex;
		    display: flex;
		    /*垂直*/
		    
		    -webkit-box-align: center;
		    -webkit-align-items: center;
		    align-items: center;
		    /*水平*/
		    
		    -webkit-box-pack: center;
		    -webkit-justify-content: center;
		    justify-content: center;
		}
    </style> 
</head>
<body>

<div class="content">
	     <div class="empty-tip">
	        <i></i>
	        <c:if test="${ hongbao eq '0' }"> <p>${msg}</p> </c:if>
	        <c:if test="${ hongbao ne '0' }"> <p>${msg} ：${hongbao }元</p> </c:if>
	    </div>
</div>
</body>

</html>
