<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>实名认证</title>
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
		<span class="ltext"><span style="color: red;">*</span>姓名</span>
	</div>
	<div class="litem">
		<span class="lright"><input type="text" id="idNo" value="${idNo}"  placeholder="请输入身份证号码" class="linput"></span>
		<span class="ltext"><span style="color: red;">*</span>身份证号码</span>
	</div>
	
	<div class="btnbox">
		<button class="blockbtn" id="submit">保存</button>
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
  var $submit = $('#submit');
    		
    //绑卡========================
 	$submit.on('click',function(){
		 	cardUserName = $('#cardUserName').val(),
		 	idNo = $('#idNo').val();
  			
			if(cardUserName == null || cardUserName == ''){
				HHN.popup("姓名不能为空", 'danger');
	    		return false;
			}
			if(idNo == null || idNo == ''){
				HHN.popup("身份证不能为空", 'danger');
	    		return false;
			}
			
			var param = {
						 "trueName":cardUserName,
						 "idnumber":idNo
						 };
			submitBindBankInfo(param);
  	});
   		
	//提交绑卡信息
	function submitBindBankInfo(param){
		HHN.loading("请求中,请稍后");
		$.post("<c:url value='/user/authentication.do'/>", param, function(data) {
			if(data.code == '0'){
				HHN.popup(data.msg);
				var fromHidden = $('#fromHidden').val();
				if(formHidden != ""){
					window.location.href = fromHidden;					
				}else{
					HHN.popup("认证成功");
				}
			}else{
				HHN.popup(data.msg);
			}
		},"json");
	}
</script>  
