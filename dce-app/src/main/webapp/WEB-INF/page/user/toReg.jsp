<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<title>用户注册</title>
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
	<p class="title">注册</p>
</div>
<div class="content">
	<div class="loginbox">
		<form id="toLoginForm" action="<c:url value='/user/reg.do'/>" method="POST">
			<div class="inputbox"><i class="icon i-user"></i><input type="text" placeholder="手机号码/用户名" id="mobile" name="mobile"></div>
		</form>
		<div class="inputbox hasbtn"><i class="icon i-valid"></i>
			<input type="text" placeholder="图片验证码" id="code" name="code"><span class="getcode">
			<img src="<c:url value='/imageCode/imageCode.do?pageId=register'/>" title="点击更换验证码" id="codeNum" /></span>
		</div>
		<div class="inputbox hasbtn"><i class="icon i-msg"></i><input type="number" placeholder="短信验证码" id="mobileCode"><span class="getmsgcode">获取短信验证码</span></div>
		<div class="inputbox"><i class="icon i-pwd"></i><input type="password" placeholder="设置新密码" id="psw"></div>
		<div class="inputbox"><i class="icon i-pwd"></i><input type="password" placeholder="确认密码" id="psw2"></div>
		<div class="inputbox">
			<i class="icon i-tel"></i>
			<input  id="suggest" type="text" placeholder="推荐人"   value="${refereeid}" >
			<input id="sId" type="hidden" value="${sId}"/>
		</div>
		<div class="btnbox"><button class="blockbtn" id="submit">确认注册</button></div>
		<div class="otherbox clearfix"><a href="javascript:;" class="fr" id="toLogin">已有帐号去登录</a></div>
		<input id="chennelCode" type="hidden" value="${channelCode}" />
	</div>
</div>
</body>
<!-- <script type="text/javascript">
$(function(){
	$('.getmsgcode').click(function(){
		var $code=$(this);
		if(!$code.hasClass('disabled')){
			$code.addClass('disabled');
			var time=60;
			var setTime=setInterval(function(){
				$code.text(time+'S后重试');
				if(time<=0){
					clearInterval(setTime);
					$code.removeClass('disabled').text('重新获取');
					return;
				}
				time--;
			},1000);
		}
	})
})
</script> -->
<script type="text/javascript">
	$(function(){
		//code 点击事件
		$("#codeNum").bind("click",function(){
              $(this).attr("src", "<c:url  value='/imageCode/imageCode.do?pageId=register&d='/>" + new Date().getTime());
          });
      	    
  	    $('.accept').click(function(){
  			var $ichk=$(this);
  			$ichk.hasClass('yes')?$ichk.removeClass('yes'):$ichk.addClass('yes');
  		})
	  		
  		$('#credit').click(function(){
  			$('#credit-body').show();
  			$('body').addClass('fixedbody');
  		})
	  		
  		$('.closebody').click(function(){
  			$(this).parents('.copybody').hide();
  		})
  		
  	    
      });
</script>
<script>
	var $psw = $('#psw'),
	$submit = $('#submit'),
	$mobile = $('#mobile'),
	$getcode = $('.getmsgcode');
	$toLogin = $('#toLogin');
	$code = $("#code");
       
    $psw.on('focus',function(){
    	$('#spanDivPwd').attr('style','display:block;');
    });
    $psw.on('blur',function(){
    	$('#spanDivPwd').attr('style','display:none;');
    }); 

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
       if(isPass){
           $("#codeNum").attr("src", "<c:url value='/imageCode/imageCode.do?pageId=register&d='/>" + new Date().getTime());
       }else{
           $("#code").attr("src", "<c:url value='/imageCode/imageCode.do?pageId=register&d='/>" + new Date().getTime());
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
	var param = {"mobile":mobile,"checkMobile":true,"code":$code.val(),"pageId":"code"};
	
	$.post("<c:url value='/commonIntf/sendMessage.do'/>", param, function(data) {
		if(data.code == '0'){
               refCode(false);
			HHN.popup(data.msg);
		}else{
               refCode(true);
			HHN.popup(data.msg);
			countup();
		}
	},"json");
}

   //提交注册信息----1
   $submit.on('click', function() {
   	var mobile = $mobile.val();
   	if(mobile_check(mobile)){
   		var mobileCode = $('#mobileCode').val();
       	if(mobileCode == null || mobileCode == "" || mobileCode.length == 0){//验证码
       		HHN.popup("请输入验证码", 'danger');
       		return false;
       	}else{
               var code = $code.val();
               var bCode = code_check(code);
       		if(bCode){
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
                               var refferee = $('#suggest').val();//推荐人手机号
                               var chennelCode = $('#chennelCode').val();//渠道ID
                               var sId = $('#sId').val();//分享ID
                               var param = {"userName":mobile,"mobile":mobile,"smsCode":mobileCode,
                            		   		"twoPassword":password,"userPassword":password,
                            		        "refereeUserMobile":refferee,
                            		        "chennelCode":chennelCode,
                            		        "code":code ,"pageId":"code","sId":sId}; 
                               submitReg(param);
                           }
                       }
                   }
               }
       	}
       	
   	}
   	        
   });
	//提交注册信息
   function submitReg(param){
	   	$.post("<c:url value='/user/reg.do'/>", param, function(data) {
			if(data.code == '0'){
				HHN.popup(data.msg);
				setTimeout(function(){
			                     window.location.href="<c:url value='/appdown/down.do'/>";
						         
					},1500);
			}else{
			    refCode(false);
				HHN.popup(data.msg);
			}
		},"json");
	}
   
	//提交登录信息
	function submitLogin(param){
		$.post("<c:url value='/user/login.do'/>", param, function(data) {
			if( data.code == '0'){
	               refCode(true);
				HHN.popup(data.msg);
				//window.location.href=data.model;
				window.location.href=data.model.fromUrl;
			}else{
	               refCode(false);
				HHN.popup(data.msg);
				setTimeout(function(){
					$('#toLoginForm').submit();
				},1500);
			}
		},"json");
	}
   
	$toLogin.on('click', function() {
	   $('#toLoginForm').submit();//这里用POST表单提交方式来跳转
	});
</script>
</html>