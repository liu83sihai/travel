<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
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
<c:if test="${empty sessionScope.jdGmapInFlag && sessionScope.jdGmapInFlag eq null}">
	<div class="header">
		<c:choose>
			<c:when test="${ not empty fromPage }">
			<a class="a-fl" href="${fromPage}"><i class="icon i-back"></i></a>
			</c:when>
			<c:otherwise>
			<a class="a-fl" href="<c:url value='/bank/toBankCardManager.do'/>"><i class="icon i-back"></i></a>
			</c:otherwise>
		</c:choose>
		<p class="title">绑定银行卡</p>
	</div>
</c:if>
<div class="content" <c:if test="${not empty sessionScope.jdGmapInFlag && sessionScope.jdGmapInFlag ne null}">style="padding-top: 0px;"</c:if>>
	<input type="hidden" value="${fromPage}" id = "fromHidden"/>
    <input type="hidden" value="" id = "tokenHidden"/>
    <input type="hidden" value="" id = "externalRefNumber"/>
    <input type="hidden" value="0" id = "userType"/>
    <input type="hidden" id="groupType" value="loan">
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
	<%-- <div class="litem">
		<input type="hidden" id="photoCount" value="${fn:length(cdList)}"> 
		<span class="lright">
			<div class="photo">
				<i class="icon i-photo"<c:if test="${fn:length(cdList)>0}"> style="display: none;"</c:if>></i>
				<span class="pcount"><c:if test="${fn:length(cdList)>0}"> <i class="icon i-next"></i>共${fn:length(cdList)}张</c:if></span>
				<input name="BANKCARD" accept="image/*" <c:if test="${empty sessionScope.jdGmapInFlag and sessionScope.jdGmapInFlag eq null}">type="file"</c:if>  class="uploadhide" size="10" multiple>
			</div>
		</span>
		<span class="ltext"><span style="color: red;">*</span>银行卡照片</span>
	</div> --%>
	<div class="litem">
		<span class="lright">
			<input type="text" id="mobile" placeholder="请输入手机号码" class="linput" value="${mobile}" readonly="readonly">
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
			<div class="litem<c:if test="${noBank.first}"> space</c:if>" id="${item.bankCodeId}">
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
document.body.addEventListener('touchstart',function(){}); 
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
        //value:'c101010000,c101010300,c101010302'.split(','),
        //value:'c101030000,c101030300'.split(','),
        //value:'c101210000'.split(','),
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
function displayImg(rst,imgShow,cc){
	var count = imgShow.parents('.ulditem').find('[type="hidden"]').val();
	if(typeof(count)=="undefined"){ 
		count=0;
	} 
	cc = parseInt(count)+parseInt(cc);
	imgShow.find('.i-photo').hide();
	imgShow.find('.pcount').show().html('<i class="icon i-next"></i>共'+cc+'张');
	$("#photoCount").val(cc);
}


function upload(paraMap,lPanel,imgShow){
	$.ajax({
        url: "<c:url value='/certificate/saveUserCertificate.do'/>",
        type: "post",
        data: paraMap,
        dataType: "json",
        beforeSend: function(){
            /*加载锁屏*/
            HHN.loading();
        },
        success: function(data) {
            /*移除加载*/
            HHN.removeLoading();
        	if(data.success && data.resultCode == '0'){
	    		HHN.popup(data.resultMessage);
		    }else{
				HHN.webPopup(data.resultMessage);
		    }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            /*移除加载*/
            HHN.removeLoading();

        	HHN.webPopup("图片上传失败，请控制图片大小！");
        },
        complete: function(XMLHttpRequest, textStatus) {
            this; // 调用本次AJAX请求时传递的options参数
        }
    });
}

function doUpload(obj,imgShow,fileId,attrId,lPanel){
	if(obj.files.length>1){
		//多图上传
		for(var i=0;i<obj.files.length;i++){
			lrz((obj.length == undefined?obj.files[i]:obj[0].files[i]), {
		        width:800
		    })
	        .then(function (rst) {
	            // 处理成功会执行
	            var groupType = $("#groupType").val();
	        	var value = {"id":fileId,"file":rst.base64,"groupType":groupType};
	            if(attrId != null && attrId != '' &&  attrId != undefined){
	            	value.certId = attrId;
	            }
	        	if(rst.base64 == null || rst.base64 == '' || rst.base64 == undefined
	        			||fileId == null || fileId == '' || fileId == undefined ){
	            	HHN.webPopup("请选择照片");
	            	return;
	            }else{
	            	displayImg(rst,imgShow,obj.files.length); 
	            	upload(value,lPanel,imgShow);
	            }
	        })
	        .catch(function (err) {
				console.log(err);
	            // 处理失败会执行
	        	HHN.webPopup("加载图片失败,请重试");
	        });
		}
	}else{
		//单图上传
		lrz((obj.length == undefined?obj.files[0]:obj[0].files[0]), {
	        width:800
	    })
        .then(function (rst) {
            // 处理成功会执行
            var groupType = $("#groupType").val();
        	var value = {"id":fileId,"file":rst.base64,"groupType":groupType};
            if(attrId != null && attrId != '' &&  attrId != undefined){
            	value.certId = attrId;
            }
        	if(rst.base64 == null || rst.base64 == '' || rst.base64 == undefined
        			||fileId == null || fileId == '' || fileId == undefined ){
            	HHN.webPopup("请选择照片");
            	return;
            }else{
            	displayImg(rst,imgShow,'1'); 
            	upload(value,lPanel,imgShow);
            }
        })
        .catch(function (err) {
			console.log(err);
            // 处理失败会执行
        	HHN.webPopup("加载图片失败,请重试");
        });
	}
    
}

function toFixed2 (num) {
    return parseFloat(+num.toFixed(2));
}

 if(${empty sessionScope.jdGmapInFlag}&&${sessionScope.jdGmapInFlag eq null}){
	 $(".uploadhide").on("change" , function(e){
	 	var that = this;
		var fileId = $(this).attr("name");
		var attrId = $(this).attr("attrId");
		var imgShow = $(that).parents();
		var lPanel = $(that).parent().parent();
		if(imgShow == null || imgShow == '' || imgShow == undefined
				|| fileId == null || fileId == '' || fileId == undefined ){
	    	HHN.webPopup("请选择照片");
	    	return;
	    }else{
	   		doUpload(that,imgShow,fileId ,attrId, lPanel);
	    }
	});
}else{
	function callInterface() {
	       var index = 1;
	       var key = setInterval(function () {
	           if (index > 5) {
	               clearInterval(key);
	               return;
	           }
	           try {
	               if ((typeof mapp === "undefined" ? "undefined" : typeof(mapp)) == "object" && mapp && mapp.device && mapp.device['goBack']) {
	                mapp.device.goBack({"flag":2,"url":"${userwebdomain}/bank/toBankCardManager.do"});                        
	                clearInterval(key);
	               }
	           } catch (e) {
	               //alert("异常:"+e);
	           }
	           index++;
	       }, 500);
	   }
	   callInterface();
	
	 $(".uploadhide").on("click" , function(e){
	 	var that = this;
		var fileId = $(this).attr("name");
		var attrId = $(this).attr("attrId");
		var imgShow = $(that).parents();
		var lPanel = $(that).parent().parent();
		
		var maxNum = $(this).attr("size");
		if(typeof(maxNum)=="undefined"){
		 maxNum = 1;
		}
		
		try {
			 mapp.device.selectImages({"maxNum":Number(maxNum),"isCrop":false,"maxWidth":300,"maxHeight":300},function(data){
				var data = JSON.parse(data);
				if(data.result.length>1){
					for(var i=0,l=data.result.length;i<l;i++){
				       $.ajax({
					         url :'<c:url value="/certificate/getBase64.do"/>',
					         data : {"url":data.result[i]}, 
					         type : 'post',
					         dataType : 'json', //返回数据类型
					         success : function(data){
					             /*请求成功后*/
					             if(data.success && data.resultCode == '0'){
					            	// 处理成功会执行
					 	            var groupType = $("#groupType").val();
					 	        	var value = {"id":fileId,"file":data.resultMessage,"groupType":groupType};
					 	            if(attrId != null && attrId != '' &&  attrId != undefined){
					 	            	value.certId = attrId;
					 	            }
					 	        	if(data.resultMessage == null || data.resultMessage == '' || data.resultMessage == undefined
					 	        			||fileId == null || fileId == '' || fileId == undefined ){
					 	            	HHN.webPopup("请选择照片");
					 	            	return;
					 	            }else{
					 	            	displayImg(data.resultMessage,imgShow,data.result.length); 
					 	            	upload(value,lPanel,imgShow);
					 	            }
					             }else{
					                 HHN.popup(data.resultMessage);
					             }
					         },
					         error : function(){
					        	 HHN.popup(data.resultMessage);          
					         }
					     });
					 }
				}else{
					$.ajax({
				         url :'<c:url value="/certificate/getBase64.do"/>',
				         data : {"url":data.result[0]}, 
				         type : 'post',
				         dataType : 'json', //返回数据类型
				         success : function(data){
				             /*请求成功后*/
				             if(data.success && data.resultCode == '0'){
				            	// 处理成功会执行
				 	            var groupType = $("#groupType").val();
				 	        	var value = {"id":fileId,"file":data.resultMessage,"groupType":groupType};
				 	            if(attrId != null && attrId != '' &&  attrId != undefined){
				 	            	value.certId = attrId;
				 	            }
				 	        	if(data.resultMessage == null || data.resultMessage == '' || data.resultMessage == undefined
				 	        			||fileId == null || fileId == '' || fileId == undefined ){
				 	            	HHN.webPopup("请选择照片");
				 	            	return;
				 	            }else{
				 	            	displayImg(data.resultMessage,imgShow,'1'); 
				 	            	upload(value,lPanel,imgShow);
				 	            }
				             }else{
				                 HHN.popup(data.resultMessage);
				             }
				         },
				         error : function(){
				        	 HHN.popup(data.resultMessage);          
				         }
				     });
				}
				 //alert("上传图片回调结果：" + JSON.stringify(data));
			}); 
	     } catch (e) {
	    	 console.log("异常:"+e);
	     }
	});
}
 

</script>

<script>
	  //验证手机格式----1======================
	    function mobile_check(mobile){
	    	var msg = verifyMsg(mobile);
	    	if(msg != ""){
	    		HHN.popup(msg, 'danger');
	    		return false;
	    	}
	    	return true;
	    }
	    //验证手机格式----2===============================
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
    	//验卡获取验证码=======================
    	//获取验证码 ---1
    	if(${valid ne '0'}){
		    $getcode.on('click', function(){
		    	checkCardInfo();
		    });
    	}
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
				}else{
					if(cardUserName == null || cardUserName == ''){
   						HHN.popup("户名不能为空", 'danger');
   	   		    		return false;
   					}else{
   						if(idNo == null || idNo == ''){
   							HHN.popup("身份证不能为空", 'danger');
   	   	   		    		return false;
   						}else{
   							if(cardNo == null || cardNo == ''){
   	   	   						HHN.popup("请填写银行卡号", 'danger');
   	   	   	   		    		return false;
   	   	   					}else{
   	   	   						if(!luhnCheck(cardNo)){
   		   	   						HHN.popup("银行卡号错误", 'danger');
   		   	   	   		    		return false;
   	   	   						}else{
   		   	   						if(mobile_check(mobile)){
   		   	   							countdown();
   		   	   							var param = {"bankId":bankId,"cardUserName":cardUserName,
   		  		  							 "cardNo":cardNo,"mobile":mobile,"idNo":idNo,"userType":userType};
   		   	   							checkCardGetCode(param);
   		   	   						}
   	   	   						}
   	   	   					}
   						}
   					}
				}
	    }
	    //验证卡获取验证码
	    function checkCardGetCode(param){
	    	$.post('<c:url value="/bank/checkCardGetCode.do"/>', param, function(data) {
    			if(data.success && data.resultCode == '0'){
    				$('#tokenHidden').val(data.model.token);
    				$('#externalRefNumber').val(data.model.externalRefNumber);
    				HHN.popup(data.resultMessage);
    			}else{
    				countup();
    				HHN.popup(data.resultMessage);
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
			    token = $('#tokenHidden').val(),
			    externalRefNumber = $('#externalRefNumber').val(),
			    addressInput = $('#addressInput').val(),
			    fromHidden = $('#fromHidden').val(),
   				userType = $('#userType').val();	
   				/* photoCount = $('#photoCount').val();	 */
   			
   				if(bankId == null || bankId == ''){
   					HHN.popup("请选择所属银行", 'danger');
   		    		return false;
   				}else{
   					if(cardUserName == null || cardUserName == ''){
	   						HHN.popup("户名不能为空", 'danger');
	   	   		    		return false;
	   					}else{
	   						if(idNo == null || idNo == ''){
	   							HHN.popup("身份证不能为空", 'danger');
	   	   	   		    		return false;
	   						}else{
	   							if(cardNo == null || cardNo == ''){
		   	   						HHN.popup("请填写银行卡号", 'danger');
		   	   	   		    		return false;
		   	   					}else{
		   	   					if(!luhnCheck(cardNo)){
			   	   						HHN.popup("银行卡号必须符合luhn校验", 'danger');
			   	   	   		    		return false;
		   	   						}else{
			   	   						if(mobile_check(mobile)){
				   	   						if(mobileCode == null || mobileCode == "" || mobileCode.length == 0){//验证码
					   	   		        		HHN.popup("请输入验证码", 'danger');
					   	   		        		return false;
					   	   		        	}else{
						   	   		        	if(addressInput == null || addressInput == ''){
							   						HHN.popup("请选择开户所在地", 'danger');
							   	   		    		return false;
							   					}else{
							   						if(branchBankName == null || branchBankName == ''){
							   	   						HHN.popup("请填写所属银行支行", 'danger');
							   	   	   		    		return false;
							   	   					}else{
							   	   						if(!HHN.checkChinese(branchBankName) || branchBankName.length >50){
							   	   							HHN.popup("支行名称为长度50以内的汉字", 'danger');
								   	   	   		    		return false;
							   	   						}else{
								   	   						if(token == null || token == '' || externalRefNumber == null || externalRefNumber == ''){
											    				HHN.popup('请获取验证码','danger');
											    				countup();
													    		return false;
											    			}else{ 
											    				/* if(photoCount == null || photoCount == '' || photoCount == '0'){
												    				HHN.popup('请上传银行卡照片','danger');
														    		return false;
												    			}else{ */ 
										    				  		/* if(!$('.accept').hasClass('yes')){
											    				    	HHN.popup('请同意《个人征信授权书》与《授信协议》');
											    				        return;
											    				    }else{ */
											    				    	var param = {"bankId":bankId,  "branchBankName":branchBankName,"cardUserName":cardUserName
											   	   								,"cardNo":cardNo,"addressInput":addressInput,"mobile":mobile,"mobileCode":mobileCode
											   	   								,"idNo":idNo,"token":token,"externalRefNumber":externalRefNumber,"from":fromHidden,"userType":userType};
									   			   						submitBindBankInfo(param);
												    			/* 	} */
												    			/* } */
					   			   							 }
							   	   						} 
							   	   					}
							   					 } 
					   	   		        	}
			   	   						}
		   	   						}
		   	   					}
	   						}
	   					}
   				}
   		});
   		
	//提交绑卡信息
	function submitBindBankInfo(param){
		HHN.loading("请求中,请稍后");
		$.post("${basePath}/bank/bindBankCard.do", param, function(data) {
			if(data.success && data.resultCode == '0'){
				HHN.popup(data.resultMessage);
				var fromHidden = $('#fromHidden').val();
				if(formHidden != ""){
					window.location.href = fromHidden;					
				}else{
					window.location.href = "${basePath}/bank/toBankCardManager.do";
				}
			}else{
				HHN.popup(data.resultMessage);
			}
		},"json");
	}
</script>  
