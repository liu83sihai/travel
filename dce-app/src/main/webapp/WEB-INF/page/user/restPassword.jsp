<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
   <!--  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="keywords" content="" />
    <meta name="description" content="" /> -->
    <title>重置密码</title>
	<jsp:include page="../common/common.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css/login.css?v=${cssversion}">
</head>
<body>
<c:if test="${empty sessionScope.jdGmapInFlag && sessionScope.jdGmapInFlag eq null}">
	<div class="header">
		<a class="a-fl" href="javascript:;" onclick="history.back();"><i class="icon i-back"></i></a>
		<p class="title">重置密码</p>
	</div>
</c:if>
<div class="content" <c:if test="${not empty sessionScope.jdGmapInFlag && sessionScope.jdGmapInFlag ne null}">style="padding-top: 0px;"</c:if>>
	<div class="loginbox">
		<form id="toLoginForm" action="<c:url value='${basePath }/loanuser/toLogin.do'/>" method="POST">
			<div class="inputbox"><i class="icon i-user"></i><input type="tel" placeholder="手机号码" id="mobile" name="mobile"  value="${mobile}" /></div>
		</form>
		<div class="inputbox hasbtn"><i class="icon i-valid"></i><input type="text" placeholder="图片验证码" id="code" name="code"><span class="getcode"><img src="${basePath}/imageCode/imageCode.do?pageId=reset" title="点击更换验证码" id="codeNum" /></span></div>
		<div class="inputbox hasbtn"><i class="icon i-msg"></i><input type="number" placeholder="短信验证码" id="mobileCode"><span class="getmsgcode">获取短信验证码</span></div>
		<div class="inputbox"><i class="icon i-pwd"></i><input type="password" placeholder="设置新密码" id="psw"></div>
		<div class="inputbox"><i class="icon i-pwd"></i><input type="password" placeholder="确认密码" id="psw2"></div>
		<div class="btnbox"><button class="blockbtn" id="submit">确定</button></div>
	</div>
</div>
</body>
<script type="text/javascript">
     $(function(){
         //code 点击事件
         $("#codeNum").bind("click",function(){
             $(this).attr("src", "${basePath}/imageCode/imageCode.do?pageId=reset&d=" + new Date().getTime());
         });
     });
</script>
<script>
	var $psw = $('#psw'),
	$submit = $('#submit'),
	$mobile = $('#mobile'),
	$getcode = $('.getmsgcode');
	$toLogin = $('#toLogin');
   	var $code = $("#code");

   //验证手机格式----1
   function mobile_check(mobile){
   	var msg = verifyMsg(mobile);
   	if(msg != ""){
   		HHN.popup(msg, 'danger');
   		return false;
   	}
   	return true;
   }
   //验证手机格式----2
   function verifyMsg(mobile){
   	if (mobile == null || mobile == "" || mobile.length == 0) {
           return "请输入手机号";
       }else{
       	if(!HHN.checkPhone(mobile) || mobile.length != 11){
       		return "手机号码格式错误！";
       	}else{
       		return "";
       	}
       }
   }
   
 //验证密码--------1
   function pwd_check(password){
   	var msg = verPwdMsg(password);
   	if(msg != ""){
   		HHN.popup(msg, 'danger');
   		return false;
   	}
   	return true;
   }
   //验证密码--------2
   function verPwdMsg(password){
   	if(password == null && password =='' || password.length == 0){
		return "请输入密码";
	}else{
		if(password.length < 6 || password.length > 20){
			return "密码长度在6-20之间";
		}
	}
   	return "";
   }

   //图形验证密码--------1
   function code_check(code){
       var msg = codeMsg(code);
       if(msg != ""){
           HHN.popup(msg, 'danger');
           return false;
       }
       return true;
   }
   //图形验证密码--------2
   function codeMsg(code){
       if(code == null || code =='' || code.length == 0 || code.length != 4){
               return "请填写正确的图片验证码";
       }
       return "";
   }

   function refCode(isPass){
       if(true){
           $("#codeNum").attr("src", "${basePath}/imageCode/imageCode.do?pageId=reset&d=" + new Date().getTime());
       }else{
           $("#code").attr("src", "${basePath}/imageCode/imageCode.do?pageId=reset&d=" + new Date().getTime());
       }
   }


   //获取验证码 ---1
   $getcode.on('click', function(){
   	if(mobile_check($mobile.val())){
           if(code_check($code.val())){                
               countdown();
               sendMobileCheckCode($mobile.val());
           }

   	}
   });
 //获取验证码----2计时
   var interval;
   function countdown() {
	   if(!$getcode.hasClass('disabled')){
		   $getcode.attr('disabled', 'disabled').html('120秒').addClass('disabled');
	       var step = 120;
	       interval = setInterval(function() {
	               if (!--step) {
	                   clearInterval(interval);
	                   $getcode.html('重新获取').attr('disabled', null).removeClass('disabled');
	                   return;
	               }
	               $getcode.html(step + '秒');
	           }, 1000);
	   }
   };
   function countup() {
   	clearInterval(interval);
   	$getcode.html('重新获取').attr('disabled', null).removeClass('disabled');
   	return;
   }; 
   //获取验证码----3请求
   function sendMobileCheckCode(mobile) {
	var param = {"mobile":mobile,"code":$code.val(),"pageId":"reset"};
	
	$.post("${basePath}/loanuser/sendMobileCode.do", param, function(data) {
		if(data.success && data.resultCode == '0'){
               refCode(true);
			HHN.popup(data.resultMessage);
		}else{
               refCode(false);
			HHN.popup(data.resultMessage);
			countup();
		}
	},"json");
}

   //提交注册信息----1
   $submit.on('click', function() {
   	var mobile = $mobile.val();
   	if(mobile_check(mobile)){
   		var mobileCode = $('#mobileCode').val();
           var code = $code.val();
           var bCode = code_check(code);
       	if(bCode){
               if(mobileCode == null || mobileCode == "" || mobileCode.length == 0){//验证码
               HHN.popup("请输入验证码", 'danger');
                   return false;
               }else{
                   //验证密码
                   var password = $psw.val();//密码
                   var password2 = $('#psw2').val();//重复密码
                   var bPwd = pwd_check(password);
                   if(bPwd){
                       if(password2 == null || password2 == "" || password2.length == 0){
                           HHN.popup("请输入重复密码", 'danger');
                           return false;
                       }else{
                           if(password != password2){
                               HHN.popup("两次输入的密码不一致", 'danger');
                               return false;
                           }else{
                               var param = {"mobile":mobile,"mobileCode":mobileCode,"password":password,"code":code,"pageId":"reset"};          
                               submitInfo(param);
                           }
                       }
                   }
               }
           }
   	}
   });
   
   //提交信息
   function submitInfo(param){
   	$.post("${basePath}/loanuser/restPassword.do", param, function(data) {
		if(data.success && data.resultCode == '0'){
			HHN.popup(data.resultMessage);
			setTimeout(function(){
                   refCode(true);
				$('#toLoginForm').submit();
			},1500);
		}else{
               refCode(false);
			HHN.popup(data.resultMessage);
		}
	},"json");
   }
   
	$toLogin.on('click', function() {
	   $('#toLoginForm').submit();//这里用POST表单提交方式来跳转
	});
</script>
</html>
