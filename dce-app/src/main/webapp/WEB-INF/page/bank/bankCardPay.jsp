<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>银行卡支付</title>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css_v1/public.css?v=${cssversion}">
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css_v1/litem.css?v=${cssversion}">
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css_v1/modal.css?v=${cssversion}" />
	<style type="text/css">
	.credit {
		font-family: "微软雅黑";
	}
	
	.credit span {
		font-size: 10.0000pt;
	}
</style>
</head>
<body>
<div class="header">
	<a class="a-fl" href=""></a>
	<p class="title">银行卡支付</p>
</div>
<div class="content" >
    <input type="hidden" value="${sign}" id = "sign"/>
    <input type="hidden" value="${userId }" id = "userId"/>
    <input type="hidden" value="${ts}" id="ts" >
	<div class="litem">
		<span class="lright">
			<input type="text" id="cardUserName" value="${bank.cardUserName}" placeholder="请输入开卡人姓名"  readonly="readonly" class="linput">
			</span>
		<span class="ltext"><span style="color: red;">*</span>开卡人姓名</span>
	</div>
	<div class="litem">
		<span class="lright"><input type="text" id="idNo" value="${bank.idNo}"  placeholder="请输入身份证号码" readonly="readonly" class="linput"></span>
		<span class="ltext"><span style="color: red;">*</span>身份证号码</span>
	</div>
	<div class="litem">
		<span class="lright"><input type="text" id="bankName" value="${bank.bankName}"  placeholder="开户行" readonly="readonly" class="linput"></span>
		<span class="ltext"><span style="color: red;">*</span>开户行</span>
	</div>
	<div class="litem">
		<span class="lright"><input type="text" id="cardNo" value="${bank.cardNo}" placeholder="请输入银行卡号" readonly="readonly" class="linput"></span>
		<span class="ltext"><span style="color: red;">*</span>银行卡号</span>
	</div>
	

	<div class="litem">
		<span class="lright">
		<input type="hidden" id="orderId" value="${order.orderid }" />
		<input type="text" id="totalprice" value="${order.totalprice}" placeholder="请输入银行卡号" readonly="readonly" class="linput"></span>
		<span class="ltext"><span style="color: red;">*</span>支付金额</span>
	</div>
	
	<div class="litem">
		<span class="lright">
			<input type="text" id="mobile" placeholder="请输入手机号码" class="linput" value="${bank.mobile}" readonly="readonly" >
		</span>
		<span class="ltext"><span style="color: red;">*</span>手机号码</span>
	</div>
	<div class="litem leftinput">
		<span class="lright"><span class="getmsgcode">输入支付短信验证码</span></span>
		<span class="ltext"><input type="number" id="mobileCode" value="" placeholder="短信验证码" class="linput"></span>
	</div>
	<div class="btnbox">
		<button class="blockbtn" id="submit">确认支付</button>
	</div>
</div>

</body>
</html>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/jquery.min.js?v=${jsversion}"></script>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/global.js?v=${jsversion}"></script>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/modal.js?v=${jsversion}"></script>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/distrit.js?v=${jsversion}"></script>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/distritSelector.js?v=${jsversion}"></script>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/bankCardCheck.js?v=${jsversion}"></script>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/lrz.bundle.js?v=${jsversion}"></script>

<script type="text/javascript">

  //全局变量===========================================
  var $getcode = $('.getmsgcode'),$submit = $('#submit');
    	
  //获取验证码 ---1
  /*
  $getcode.on('click', function(){
   	checkCardInfo();
  });
  */
  
   	//获取验证码----2计时
    var interval;
    function countdown() {
    	if(!$getcode.hasClass('disabled')){
    		$getcode.attr('disabled', 'disabled').html('60秒').addClass('disabled');
	        var step = 60;
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
    
    //验卡时所需资料验证==============
    function checkCardInfo(){
	    	var bankId = $('#bankNameSelect').val(),
			 	cardUserName = $('#cardUserName').val(),
			 	idNo = $('#idNo').val(),
			 	cardNo = $('#cardNo').val(),
			    mobile = $('#mobile').val(),
			    userType = $('#userType').val();
	    	
	    	if(bankId == null || bankId == ''){
				HHN.popup("请选择所属银行", 'danger');
	    		return false;
			}

			if(cardUserName == null || cardUserName == ''){
					HHN.popup("户名不能为空", 'danger');
  		    		return false;
			}
			if(idNo == null || idNo == ''){
				HHN.popup("身份证不能为空", 'danger');
 		    		return false;
			}
			if(cardNo == null || cardNo == ''){
					HHN.popup("请填写银行卡号", 'danger');
		    		return false;
			}
			
			//if(!luhnCheck(cardNo)){
			//HHN.popup("银行卡号错误", 'danger');
    		//return false;
    		
			if(!mobile_check(mobile)){
				HHN.popup("请填正确的手机号码", 'danger');
    			return false;
			}
			
			countdown();
			var param = {"bankId":bankId,
						 "cardUserName":cardUserName,
				 		 "cardNo":cardNo,
				 		 "mobile":mobile,
				 		 "idNo":idNo,
				 		 "userType":userType,
				 		 "ts":$("#ts").val(),
				 		 "sign":$("#sign").val(),
				 		 "userId":$("#userId").val()				 		 
				 		 };
			
			checkCardGetCode(param);

	 }
    
    //验证卡获取验证码
    function checkCardGetCode(param){
    	$.post('<c:url value="/bank/getBankCardCode.do"/>', param, function(data) {
   			if(data.code == '0'){
   				$('#externalRefNumber').val(data.data.token);
   				HHN.popup(data.msg);
   			}else{
   				countup();
   				HHN.popup(data.msg);
   			}
   		},"json");
    }
	    
    		
    //绑卡========================
 	$submit.on('click',function(){
  			var orderId = $('#orderId').val(),
		    mobileCode = $('#mobileCode').val(),
		    
			var param = {"orderId":orderId,
						 "mobileCode":mobileCode,
						 "idNo":idNo,
						 "externalRefNumber":externalRefNumber,
						 "from":fromHidden,
						 "userType":userType,
						 "ts":$("#ts").val(),
				 		 "sign":$("#sign").val(),
				 		 "userId":$("#userId").val()
						 };
			submitBindBankInfo(param);
  	});
   		
	//提交绑卡信息
	function submitBindBankInfo(param){
		HHN.loading("请求中,请稍后");
		$.post("<c:url value='/bank/submitPay.do'/>", param, function(data) {
			if(data.code == '0'){
				HHN.popup("支付完成");
			}else{
				HHN.popup(data.msg);
			}
		},"json");
	}
</script>  
