<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>绑定银行卡</title>
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

<div class="content" >
	<input type="hidden" value="${fromPage}" id = "fromHidden"/>
    <input type="hidden" value="${sign}" id = "sign"/>
    <input type="hidden" value="" id = "externalRefNumber"/>
    <input type="hidden" value="${userId }" id = "userId"/>
    <input type="hidden" value="${ts}" id="ts" >
	<div class="litem">
		<span class="lright"><input type="text" id="cardUserName" value="${realName}" placeholder="请输入开卡人姓名" class="linput"></span>
		<span class="ltext"><span style="color: red;">*</span>开卡人姓名</span>
	</div>
	<div class="litem">
		<span class="lright"><input type="text" id="idNo" value="${idNo}"  placeholder="请输入身份证号码" class="linput"></span>
		<span class="ltext"><span style="color: red;">*</span>身份证号码</span>
	</div>
	<div class="litem">
		<input type="hidden" value=""  id="bankNameSelect" name="bankName" nullmsg="请选择所属银行" >
		<span class="lright" id="yinhang">
			<i class="icon i-next"></i>
			<em>请选择所属银行</em>
		</span>
		<span class="ltext"><span style="color: red;">*</span>所属银行</span>
	</div>
	<div class="litem">
		<span class="lright"><input type="text" id="cardNo" value="" placeholder="请输入银行卡号" class="linput"></span>
		<span class="ltext"><span style="color: red;">*</span>银行卡号</span>
	</div>
	<div class="litem">
		<input type="hidden" value="" id="addressInput" nullmsg="请选择开户行所在地" > 
		<span class="lright" id="yinhangdzhi">
			<i class="icon i-next"></i>
			<em>请选择开户行所在地</em>
		</span>
		<span class="ltext"><span style="color: red;">*</span>开户行所在地</span>
	</div>
	<div class="litem">
		<span class="lright">
			<input type="text" id="branchBankName" value=""  class="linput" datatype="s" nullmsg="请输入支行名称！" errormsg="请输入支行名称！"  placeholder="输入开户支行名称">
		</span>
		<span class="ltext"><span style="color: red;">*</span>支行名称</span>
	</div>

	<div class="litem">
		<span class="lright">
			<input type="text" id="mobile" placeholder="请输入手机号码" class="linput" value="${mobile}" >
		</span>
		<span class="ltext"><span style="color: red;">*</span>手机号码</span>
	</div>
	<div class="litem leftinput">
		<span class="lright"><span class="getmsgcode">获取短信验证码</span></span>
		<span class="ltext"><input type="number" id="mobileCode" value="" placeholder="短信验证码" class="linput"></span>
	</div>
	<div class="btnbox">
		<button class="blockbtn" id="submit">保存</button>
	</div>
</div>
<div class="copybody" id="yinhang-body">
	<div class="header">
		<a class="a-fl closebody"><i class="icon i-back"></i></a>
		<p class="title">所属银行</p>
	</div>
	<div class="content selecting">
		<c:forEach var="item" items="${bankCodes}" varStatus="noBank">
			<div class="litem<c:if test="${noBank.first}"> space</c:if>" id="${item.id}">
				<span class="ltext">${item.bankName}</span>
			</div>
        </c:forEach>
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
$(function(){
	$('#yinhang').click(function(){
		$('#yinhang-body').show();
		$('body').addClass('fixedbody');
	})
	$('#yinhang-body .litem').click(function(){
		$('#yinhang').html('<input type="hidden" value=""  id="bankNameSelect" name="bankName"><i class="icon i-next"></i>'+$(this).text());
		$("#bankNameSelect").val($(this).attr("id")); 
		$(this).parents('.copybody').hide();
		$('body').removeClass('fixedbody');
	})
	$('.accept').click(function(){
		var $ichk=$(this);
		$ichk.hasClass('yes')?$ichk.removeClass('yes'):$ichk.addClass('yes');
	})
	// 所属银行地址
    var myDistrit = new DistritSelector({
        data: district,
        value :'',
        title:'请选择开户行所在地',
        text: $('#yinhangdzhi'),
        input: $('#addressInput'),
        //level:2, 控制选择项的级数 max:3
        callback: function(code, text) {
            if (!code) return;
            this.text.html('<i class="icon i-next"></i>'+text.join('-'));
            this.input.val(code.join(','));
        }
    });

    $('#yinhangdzhi').on('click', function() {
        myDistrit.show();
    });

	$('.closebody').click(function(){
		$(this).parents('.copybody').hide();
		$('body').removeClass('fixedbody');
	})
	$('#empower').click(function(){
		$('#empower-body').show();
		$('body').addClass('fixedbody');
	})
	$('#credit').click(function(){
		$('#credit-body').show();
		$('body').addClass('fixedbody');
	})
})
</script>

<script type="text/javascript">

  //验证手机格式
  function mobile_check(mobile){
  	var msg = verifyMsg(mobile);
  	if(msg != ""){
  		HHN.popup(msg, 'danger');
  		return false;
  	}
  	return true;
  }
  //验证手机格式
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
  
  //全局变量===========================================
  var $getcode = $('.getmsgcode'),$submit = $('#submit');
    	
  //获取验证码 ---1
  $getcode.on('click', function(){
   	checkCardInfo();
  });
	   
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
  			var bankId = $('#bankNameSelect').val(),
		   	branchBankName = $('#branchBankName').val(),
		 	cardUserName = $('#cardUserName').val(),
		 	idNo = $('#idNo').val(),
		 	cardNo = $('#cardNo').val(),
		    mobile = $('#mobile').val(),
		    mobileCode = $('#mobileCode').val(),
		    externalRefNumber = $('#externalRefNumber').val(),
		    addressInput = $('#addressInput').val(),
		    fromHidden = $('#fromHidden').val(),
  			userType = $('#userType').val();	
  			
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
			if(!mobile_check(mobile)){
				HHN.popup("请填正确的手机号码", 'danger');
    			return false;
			}
			if(mobileCode == null || mobileCode == "" || mobileCode.length == 0){//验证码
        		HHN.popup("请输入验证码", 'danger');
        		return false;
        	}
			
        	if(addressInput == null || addressInput == ''){
				HHN.popup("请选择开户所在地", 'danger');
	    		return false;
			}
			if(branchBankName == null || branchBankName == ''){
				HHN.popup("请填写所属银行支行", 'danger');
  		    		return false;
			}
			/*
			if(!luhnCheck(cardNo)){
					HHN.popup("银行卡号必须符合luhn校验", 'danger');
   		    		return false;
			}
			if(!HHN.checkChinese(branchBankName) || branchBankName.length >50){
				HHN.popup("支行名称为长度50以内的汉字", 'danger');
 		    		return false;
			}
			*/
			if(externalRefNumber == null || externalRefNumber == ''){
				HHN.popup('请获取验证码','danger');
				countup();
   				return false;
			}

			var param = {"bankId":bankId,  
						 "branchBankName":branchBankName,
						 "cardUserName":cardUserName,
						 "cardNo":cardNo,
						 "addressInput":addressInput,
						 "mobile":mobile,
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
		$.post("<c:url value='/bank/bindBankCard.do'/>", param, function(data) {
			if(data.code == '0'){
				HHN.popup(data.msg);
				var fromHidden = $('#fromHidden').val();
				if(formHidden != ""){
					window.location.href = fromHidden;					
				}else{
					window.location.href = "<c:url value='/bank/toBindBankCard.do'/>";
				}
			}else{
				HHN.popup(data.msg);
			}
		},"json");
	}
</script>  
