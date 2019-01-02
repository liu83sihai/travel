<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<title>用户升级</title>
	<jsp:include page="../common/common.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css_v1/login.css?v=${cssversion}">
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css_v1/litem.css?v=${cssversion}">
	<style type="text/css">
	.ltips-common {
	    line-height: 20px;
	    padding: 15px 0px 10px;
	    color: #968c7c;
	    font-size: 14px;
	}
	.ltips-common .icon {
	    background: url(http://rec.gemlends.com/jd/img/icon-common.png);
	    background-size: auto 18px;
	    position: static;!important;
	}
	.ltips-common .i-check {
	    width: 14px;
	    height: 14px;
	    background-position: -87px 0;
	    margin: 3px 5px 0 0;
	}
	.ltips-common .yes {
	    background-position: -101px 0;
	}
	
	
</style>
</head>
<body>
<div class="header">
	<p class="title">用户升级申请</p>
</div>
<div class="content">
	<div class="loginbox">
		<form id="toLoginForm" action="<c:url value='/user/reg.do'/>" method="POST">
			<div class="inputbox"><i class="icon i-user"></i>
			<input type="hidden" name="userId" id="userId" value="${user.id }"/>
			<input type="text" placeholder="升级用户" id="mobile" name="mobile" value="当前升级申请用户：${user.trueName==null?user.mobile:user.trueName}">
			</div>
		</form>
		
		<div class="litem">
			<input type="hidden" value=""  id="userLevel" name="userLevel" nullmsg="选择升级级别" >
			<span class="lright" id="yinhang">
				<em>选择升级级别</em>
			</span>
			<span class="ltext">升级级别</span>
		</div>
	
		<div class="btnbox" style="padding: 0px 0 15px;"><button class="blockbtn" id="submit">提交</button></div>
		
	</div>
</div>

<div class="copybody" id="yinhang-body">
	<div class="header">
		<a class="a-fl closebody"><i class="icon i-back"></i></a>
		<p class="title">升级级别</p>
	</div>
	<div class="content selecting">
        <c:forEach var="item" items="${upgradeLevelLst}" varStatus="no">
           	<div class="litem" id="${item.code}">
				<span class="ltext">${item.name}</span>
			</div>
        </c:forEach>
              		
	</div>
</div>
			
</body>
<script type="text/javascript">
$(function(){
	$('#yinhang').click(function(){
		$('#yinhang-body').show();
		$('body').addClass('fixedbody');
	})
	$('#yinhang-body .litem').click(function(){
		$('#yinhang').html($(this).text());
		$("#userLevel").val($(this).attr("id")); 
		$(this).parents('.copybody').hide();
		$('body').removeClass('fixedbody');
	})
});	
</script>
<script>
   $('#submit').on('click', function() {
		var userId = $("#userId").val();
		var userLevel = $("#userLevel").val();
		if(userLevel==""){
			HHN.popup("请选择升级级别");
			return false;
			
		}
		$.ajax({
			 type: 'POST',
			 dataType: 'json',
			 url: "<c:url value='/user/submitUpgrade.do'/>",
			 data:{
				 userId:userId,
				 userLevel:userLevel
			 },
			 success: function(data){
				 HHN.popup(data.msg);
			 }
		});
   });
</script>
</html>