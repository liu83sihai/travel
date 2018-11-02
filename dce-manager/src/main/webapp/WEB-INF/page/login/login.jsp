<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/login/base.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/login/main.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/login/icons.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/login/modal.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/login/dlsywglst.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/login/pagination.css'/>">
    
    <link href="<c:url value='/css/login/login.css'/>" rel="stylesheet" type="text/css" /> 
	<link href="<c:url value='/css/login/hhydConfirm.css'/>" rel="stylesheet" type="text/css" /> 
</head>
<style>
    input{
        border:none;
        background: none;
        -webkit-appearance: none;
        outline: none;
        flex: 1;
    }
    html,body{
        width: 100%;
        height: 100%;
        overflow: hidden;
    }
    body{
        background-color: #27a2f0;
        background-image: url(<c:url value='/images/banner.png'/>);
        background-repeat: no-repeat;
        background-position: bottom;
        background-size: 100%;
    }
    .login{
        width: 800px;
        height: 450px;
        margin: 0 auto;
        position: relative;
        top:50%;
        margin-top: -200px;
    }
    .logo{
        margin-top: 120px;
    }
    .login-wapper{
        width: 300px;
        background: #fff;
        float: right;
        overflow: hidden;
    }
    .login-head{
        height: 40px;
        line-height: 40px;
        text-indent: 14px;
        font-size: 18px;
        border-bottom: 1px solid #dbdbdb;
    }
    .form{
        width: 90%;
        margin: 20px auto;
    }
    .item{
        margin-bottom: 20px;
        height: 38px;
        line-height: 38px;
        width: 100%;
        border: 1px solid #dbdbdb;
        position: relative;
        display: flex;
    }
    .item .login-label{
        position: absolute;
        top: 0;
        left: 0;
        width: 38px;
        height: 38px;
        border-right: 1px solid #bdbdbd;
        background: url(<c:url value='/images/icons.png'/>) no-repeat;
    }
    .item .userid-label{
        background-position: -6px -6px;
    }
    .item .pwd-label{
        background-position: -5px -41px;
    }
    .item-focus{
        border-color:#63bee8 !important;
    }
    .item-focus label{
        background-position-x:-40px !important;
        border-color:#63bee8 !important;
    }
    .itxt{
        width: 230px;
        height: 38px;
        line-height: 38px;
        margin-left: 39px;
        text-indent: 10px;
        overflow: hidden;
    }
    #mobileCode{
        /*flex:1;*/
        width: 150px;
        height: 38px;
        border: 1px solid #dbdbdb;
        text-indent: 10px;
    }
    .iptBtn{
        /*padding:8px 20px 9px;
        border-radius: 5px;
        background: #27A2F0;
        color: #fff;*/
        width: 110px;
        height: 38px;
        border-radius: 5px;
        background: #27A2F0;
        color: #fff;
    }
    .btn{
        width: 100%;
        border-radius: 5px;
        height: 40px;
        line-height: 40px;
    }
    .btn{
        width: 100%;
        border-radius: 5px;
        height: 40px;
        line-height: 40px;
    }
    p{
        
        bottom: 0;
        left: 0;
        right: 0;
        text-align: center;
        color: #eaf7ff;
        font-size: 12px;
    }
    @media screen and (max-width: 1024px) {
        .login{
            width: 740px;
        }
        .logo{     
            width: 300px;
        }
        .login-wapper{
            width: 260px;
        }
        #mobileCode{
            width: 130px;
        }
        #getCodeBtn{
            width: 95px;
        }
    }
    @media screen and (max-height: 768px) {
        .login{
            height: 420px;
        }
        .logo{     
            height: 40px;
        }
    }
</style>
<body>
<div class="login">
    <div  class="logo" style="float:left;width:400px;">
     	<font style="color:#FFFFFF;font-size:35px;font-family: serif;font-weight: bold;">享游环球后台管理系统</font>
    </div>
<%--     <img src="<c:url value='/images/login_logo.png'/>" class="logo"> --%>
    <div class="login-wapper">
        <div class="login-head" style="color: gray;">
            	享游环球
        </div>
        <form method="post"  id="submitForm">
        <div class="form">
            <!-- 用户名	 -->
            <div class="item">
                <label for="mobileId" class="login-label userid-label "></label>
                <input id="mobileId" type="text" class="itxt" name="mobile" tabindex="1" autocomplete="off" value="" placeholder="用户名">
                <span class="clear-btn"></span>
            </div>
            <!-- 密码 -->
            <div class="item">
                <label for="password" class="login-label pwd-label"></label>
                <input id="password" type="password" class="itxt" name="password" tabindex="2" autocomplete="off" value="" placeholder="请输入登陆密码">
            </div>
            <!-- 手机验证码 -->
    	<!-- 	<div class="item" style="border: none;">
    			<div class="sel_txt">
    				<input id="mobileCode" type="text" name="mobileCode" tabindex="3" autocomplete="off" value="" placeholder="请输入手机验证码" maxlength="8">       
    				<input type="button" onclick="getCode(this)" class="iptBtn" id="getCodeBtn" value="获取验证码" />
    			</div>                
    		</div> -->
            <span style="color: red">
                <c:if test="${!empty errorMsg}">
                    ${errorMsg}
                </c:if>
            </span>
            <a href="javascript:;" title="" class="btn" onclick="submit()">登录</a>
        </div>
        </form>
    </div>
    <p style="position: absolute;">Powered by CW v1.0.2 Copyright © CW All rights reserved.</p>
</div>
</body>
</html>
<script type="text/javascript" src="<c:url value='/js/login/jquery-1.9.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/login/global.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/login/modal.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/common/hhydConfirm.js'/>"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $(".form .item .itxt").on("focus" , function(){
            $(this).parent().addClass("item-focus");
        }).on("blur" , function(){
            $(this).parent().removeClass("item-focus");
        });
    });

    function submit(){
        var mobile = $("#mobileId").val();
        var password = $("#password").val();

        if(HHN.checkEmpty(mobile)){
            HHN.webPopup("请输入用户名！");
            return false;
        }
        if(HHN.checkEmpty(password)){
            HHN.webPopup("请填写正确密码！");
            return false;
        }
        
        var param = {"username":mobile,"password":password};
        var loginUrl = "<c:url value='/login'/>";
        $.post(loginUrl, param, function(data) {
            if(data.success && data.resultCode == '0'){
                if (data.resultMessage != undefined && data.resultMessage != '') {
                    window.hhyd.info(data.resultMessage);
                }
                window.location.href = "<c:url value='/menu/index.html'/>";
            } else {
                window.hhyd.info(data.resultMessage);
            }
        },"json");
        
    /*     $.post("<c:url value='/auth/activityLogin.html'/>", {"mobile":mobile,"password":password}, function(data) {
        	console.log(data);
        	if(data.resultCode == "0"){
            	//HHN.webPopup(data.msg);
                window.location.href="<c:url value='/auth/resetPwd.html' />";
            }else{
            	HHN.webPopup(data.msg);
            }
	    },"json"); */
        
    }
</script>