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
	<title>用户登录</title>
	<jsp:include page="../common/common.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css/login.css?v=${cssversion}">
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
	<!-- <a class="a-fl" href="javascript:;" onclick="history.back();">返回</a> -->
	<p class="title">短信登录</p>
</div>
<div class="content">
	<div class="login-logo"><img src="http://rec.gemlends.com/jd/img/login-logo.png" alt=""></div>
	<div class="loginbox">
		<form id="loginForm" action="<c:url value='${basePath}/loanuser/login.do'/>" method="post" >
			<%-- <input type="hidden" name="fromUrl" id="fromUrl" value="${fromUrl}" > --%>
			<input type="hidden" name="fromUrl" id="fromUrl" value="${customerwebdomain}" >
			<div class="inputbox"><i class="icon i-user"></i><input type="text" placeholder="手机号码/用户名" id="mobile" value="${mobile}"></div>
			<div class="inputbox hasbtn"><i class="icon i-msg"></i><input type="number" placeholder="短信验证码" id="mobileCode"><span class="getmsgcode">获取短信验证码</span></div>
		</form>
		<!-- <p class="ltips-common"><i class="icon i-check accept yes"></i>我已阅读并同意<a id="credit" style="text-decoration: underline;">《用户协议》</a></p> -->
		<div class="btnbox"><button class="blockbtn" id="submit">确认登录</button></div>  
		<div class="otherbox clearfix"><a href="<c:url value='${basePath }/loanuser/toReg.do?channelCode=${channelCode}'/>" class="fr">立即注册</a><a href="<c:url value='${basePath }/loanuser/toPwdLogin.do'/>" class="fl" id="restPsw">密码登录</a></div>
	</div>
</div>
<div class="copybody credit" id="credit-body">
<c:if test="${empty sessionScope.jdGmapInFlag && sessionScope.jdGmapInFlag eq null}">
	<div class="header">
		<a class="a-fl closebody"><i class="icon i-back"></i></a>
		<p class="title">用户协议</p>
	</div>
</c:if>
	<div class="content" <c:if test="${not empty sessionScope.jdGmapInFlag && sessionScope.jdGmapInFlag ne null}">style="padding-top: 0px;"</c:if>>
		<p style="text-indent:20.0000pt;">
			<span style="font-weight:bold;">特别提示</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">深圳市金诚信小额贷款有限责任公司（以下简称“金诚信”）在此特别提醒您（用户）在注册成为用户之前，请认真阅读本《用户协议》（以下简称“协议”），确保您充分理解本协议中各条款。请您审慎阅读并选择接受或不接受本协议。除非您接受本协议所有条款，否则您无权注册、登录或使用本协议所涉服务。您的注册、登录、使用等行为将视为对本协议的接受，并同意接受本协议各项条款的约束。</span>
		</p>
		<p style="text-indent:20.0000pt;">
			<span style="font-weight:bold;">协议修改</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">本协议约定金诚信与用户之间关于“金诚信”软件服务（以下简称“服务”）的权利义务。“用户”是指注册、登录、使用本服务的个人。金诚信将通过“金诚信”贷款平台公布最新的协议，已签约的用户无需再次签约。如用户不同意金诚信对本协议所做的修改，应停止使金诚信贷款平台提供的服务；如用户继续使用服务或未主动终止服务，则视为同意履行修改后的协议，并应依照修改后的协议履行应尽义务。</span>
		</p>
		<p style="text-indent:20.0000pt;">
			<span style="font-weight:bold;">一、用户账户</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">1.1用户必须为符合中华人民共和国法律规定的具有完全民事权利和民事行为能力，能够独立承担民事责任的自然人、法人或其他组织。若用户违反前述限制注册使用金诚信提供的服务的，其监护人应承担所有责任。</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">1.2用户在使用本服务前需用手机号注册一个账户，获得金诚信账号的使用权。用户应提供详尽、准确的个人资料，并不断更新注册信息。因注册信息不真实而引起的问题，有用户自行承担相应后果。</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">1.3 用户不应将其帐号、密码转让、出售或出借予他人使用，如果金诚信发现使用者并非帐号注册者本人，有权停止继续服务。如用户发现其帐号遭他人非法使用，应立即通知金诚信客服。因黑客行为或用户违反本协议规定导致帐号、密码遭他人非法使用的，由用户本人承担因此导致的损失和一切法律责任，金诚信不承担任何责任。 </span>
		</p>
		<p style="text-indent:20.0000pt;">
			<span style="font-weight:bold;">二、服务内容</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">2.1本平台提供的服务包括但不限于：申请借款、查询个人信用、签订合同、绑定银行卡、快捷支付、维护个人信息等，具体内容以平台展示的服务内容信息为准。</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">2.2金诚信可根据国家法律法规规章政策变化及维护交易秩序等原因，不定时变更服务内容，变更后的服务内容将直接更新在服务平台，用户理解并同意按实际需求使用服务内容。</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">2.3如果金诚信发现了因系统故障或其他任何原因导致的处理错误，无论有利于哪一方，金诚信都有权纠正该错误。如果该错误导致用户实际收到的款项多于应获得的金额，则无论错误的性质和原因为何，用户应根据金诚信向用户发出的有关纠正错误的通知的具体要求返还多收的款项或进行其他操作，且金诚信有权自行采取措施纠正以上错误交易。用户理解并同意，用户因前述处理错误 而多付或少付的款项均不计利息，金诚信不承担因前述处理错误而导致的任何损失或责任（包括用户可能因前述错误导致的利息、汇率等损失）。</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">2.4因相关实名验证的需要，用户同意并授权金诚信向包括但不限于：第三方数字证书服务机构、第三支付机构、银行、政府部门机构等合法机构，提供用户相关信息用于用户身份核实或交易；用户承诺该实名信息真实、完整、有效，并自行承担相应的法律后果。</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">2.5用户遵循借贷自愿、诚实守信、责任自负、风险自担的原则承担借贷风险。</span>
		</p>
		<p style="text-indent:20.0000pt;">
			<span style="font-weight:bold;">三、用户信息收集和保护</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">3.1用户同意并授权金诚信平台收集、整理、分析用户在该平台商提供的个人信息，以保障用户顺利、有效、安全地进行平台业务操作。</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">3.2用户同意并授权金诚信平台将其身份、手机号码等个人信息提供给相应机构进行校验，以核实期身份和信息的真实性。</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">3.3用户同意并授权金诚信平台将其用户信息、联系方式、校验、分析结果提供给金诚信合作第三方以维护用户的合法权益或处理用户与他人的交易纠纷或争议。</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">3.4用户同意金诚信利用用户信息与用户联络，包括但不限于电话、短信、邮件等方式，向用户推广其相关产品、贷后管理等。</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">3.5  为避免用户通过金诚信平台从事违法、违规或犯罪活动，保护金诚信及其他用户的合法权益，金诚信有权通过任何合法途径收集用户的额外信息，或通过人工或自动程序对用户信息进行审核及评价。</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">3.6金诚信制定了严格的用户信息处理规则和采取必要技术措施保护用户的信息安全，确保用户信息不被滥用。除征得用户明确同意和法律法规另有规定或国家司法、行政机关、行业协会、相应监管机构要求，或用于核实用户信息真实性要求外，本平台不会向任何第三方提供或对外披露用户信息。</span>
		</p>
		<p style="text-indent:20.0000pt;">
			<span style="font-weight:bold;">四、知识产权</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">金诚信平台上所有内容的知识产权均由金诚信及其权利人依法拥有，包括但不限于文本、数据、文章、图片、资讯、平台架构、网页设计等。未经金诚信或其权利人书面同意，任何人不得擅自使用、修改、复制、公开发布相关内容；如有违反，用户应对金诚信及其权利人承担损害赔偿等法律责任。</span>
		</p>
		<p style="text-indent:20.0000pt;">
			<span style="font-weight:bold;">五、免责条款</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">5.1由于互联网本身所具有的不稳定性，金诚信无法保证服务不会中断，如出现下列任一状况而无法正常运作，致使无法向用户提供本协议项下的各项服务，金诚信不承担任何违约或赔偿责任，该状况包括但不限于：</span>
		</p>
		<p style="text-indent:30.0000pt;">
			<span style="font-size:10.5000pt;">5.1.1服务平台维护期间。</span>
		</p>
		<p style="text-indent:30.0000pt;">
			<span style="font-size:10.5000pt;">5.1.2电信设备出现故障不能进行数据传输的。</span>
		</p>
		<p style="text-indent:30.0000pt;">
			<span style="font-size:10.5000pt;">5.1.3由于黑客攻击、网络相关服务供应商技术调整或故障、升级、银行方面的问题等原因造成的本服务中断或延迟。</span>
		</p>
		<p style="text-indent:30.0000pt;">
			<span style="font-size:10.5000pt;">5.1.4因台风、地震、海啸、洪水、停电、战争、恐怖袭击等不可抗力之因素，造成系统障碍不能执行业务的； </span>
		</p>
		<p style="text-indent:30.0000pt;">
			<span style="font-size:10.5000pt;">5.1.5因用户的过错导致的任何损失，该过错包括但不限于：决策失误、操作不当、遗忘或泄露密码、密码 被他人破解、用户使用的计算机系统被第三方侵入、用户委托他人代理交易时他人恶意或不当操作而造成的损失。</span>
		</p>
		<p style="text-indent:30.0000pt;">
			<span style="font-size:10.5000pt;">5.1.6因国家相关主管部门颁布、变更的法令、政策导致平台不能提供约定服务的，不视为其违约，双方可根据相关的法令、政策变更协议内容或提前终止本协议。</span>
		</p>
		<p style="text-indent:20.0000pt;">
			<span style="font-weight:bold;">六、适用法律及争议解决</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">6.1本协议的订立、执行和解释及争议的解决均应适用中华人民共和国法律。</span>
		</p>
		<p style="text-indent:10.0000pt;">
			<span style="font-size:10.5000pt;">6.2如双方就本协议内容或其执行发生任何争议，双方应尽量友好协商解决；协商不成时，任何一方均可向金诚信注册地址所在地有管辖权的人民法院提起诉讼。</span>
		</p>
	</div>
</div>
</body>
<script type="text/javascript">
	$(function(){
	    
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
	$submit  = $('#submit'),
	$mobile   = $('#mobile'),
	$restPsw = $('#restPsw');
	$getcode = $('.getmsgcode');
	
	

	var $Ipt=$('#exit-wrap input');
	$Ipt.on('focus',function(){
	    $(this).parent().addClass('borColor');
	})
	$Ipt.on('blur',function(){
	    $(this).parent().removeClass('borColor');
	})
	$Ipt.on('keyup',function(){
	    if($mobile.val()!='' && $psw.val()!=''){
	        $submit.removeClass('normal');
	    }
	    if($mobile.val()=='' || $psw.val()==''){
	        $submit.addClass('normal');
	    }
	})
  
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
  
	
	//获取验证码 ---1
   $getcode.on('click', function(){
   	if(mobile_check($mobile.val())){
               countdown();
               sendMobileCheckCode($mobile.val());
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
		var param = {"mobile":mobile,"checkMobile":false,"checkCode":false,"pageId":"userlogin"};
		
		$.post("${basePath}/loanuser/sendMobileCode.do", param, function(data) {
			if(data.success && data.resultCode == '0'){
				HHN.popup(data.resultMessage);
			}else{
				HHN.popup(data.resultMessage);
				countup();
			}
		},"json");
	}
	
	
	
	


	//处理登录数据
	$submit.on('click', function(){
		var mobile = $mobile.val();
		if(mobile_check($mobile.val())){
			var mobileCode = $('#mobileCode').val();
	       	if(mobileCode == null || mobileCode == "" || mobileCode.length == 0){//验证码
	       		HHN.popup("请输入验证码", 'danger');
	       		return false;
	       	}else{
	       		/* if(!$('.accept').hasClass('yes')){
			    	HHN.popup('请同意用户协议');
			        return;
			    }else{ */
	            	var psw = "111111";
	                var param = {"mobile":mobile,"mobileCode":mobileCode,"password":psw,"fromUrl":$('#fromUrl').val() ,"code":"" , "pageId":"userlogin","codelogin":"true"};
	                submitLogin(param);
			   /*  } */
	       	}
		}
	});
 	 //提交登录信息
	function submitLogin(param){
	  	$.post("${basePath}/loanuser/login.do", param, function(data) {
			if(data.success && data.resultCode == '0'){
				HHN.popup(data.resultMessage);
				//window.location.href=data.model;
				window.location.href=data.model.fromUrl;
			}else{
				HHN.popup(data.resultMessage);
			}
		},"json");
	  }
  
  
	 //处理微信登录数据
	 $('#wxLogin').on('click', function(){
	     	var oauthurl="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx08bf99f5386e1c72";
			var redirectUrl = "http://user.gemlends.com/wx/getWxOpenId.do";
			var url = encodeURIComponent(redirectUrl);
	window.location.href=oauthurl+"&redirect_uri="+url+"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
	 });
  
  
	//去重置页面
	$restPsw.on('click', function() {
		$('#restPswForm').submit();//这里用POST表单提交方式来跳转
		//var mobile = $mobile.val();
		//var param = {"mobile":mobile};
		//window.location.href="toLogin.do?mobile="+mobile;
	});
</script>
</html>