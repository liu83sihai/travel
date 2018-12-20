<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" />
		<meta name="format-detection" content="telephone=no">
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<title>扫描支付</title>
		
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<c:url value ='/res/css/public.css' />">
<link rel="stylesheet" type="text/css" href="<c:url value ='/res/css/modal.css' />">
<link rel="stylesheet" type="text/css" href="<c:url value ='/res/css/litem.css' />">
<%-- <script src="http://html2canvas.hertzen.com/dist/html2canvas.min.js"></script>--%>

<style type="text/css">
	.header{border: 1px solid #EDEFF2;}
	.simg{background-size: 100% auto; 
			background-repeat: no-repeat; 
			background-image: url(); }
	.simg:before{content: ""; 
				display: block; 
				padding-top: 200%;}
</style>
</head>
	
<body>
	
		<div class="header">
			<a class="a-fl" href="<c:url value='/agent/agentIndex.do'/>"><i class="icon i-back"></i></a>
			<p class="title">扫描支付</p>
		</div>
		<div id="shareImg" class="simg">
			<div class="rec_btn" style="position:fixed;bottom:0; width:100%;background: #F7931D;" onclick="javascript:submitData();">
	             	完成
             </div>
		</div>
		
	</body>
	
	<script type="text/javascript" src="http://rec.gemlends.com/jd/js/jquery.min.js?v=${jsversion}"></script>
	<script type="text/javascript" src="http://rec.gemlends.com/jd/js/global.js?v=${jsversion}"></script>
	<script type="text/javascript" src="http://rec.gemlends.com/jd/js/modal.js?v=${jsversion}"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	
	<script>
  /*
   * 注意：
   * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
   * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
   * 3. 常见问题及完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
   *
   * 开发中遇到问题详见文档“附录5-常见错误及解决办法”解决，如仍未能解决可通过以下渠道反馈：
   * 邮箱地址：weixin-open@qq.com
   * 邮件主题：【微信JS-SDK反馈】具体问题
   * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
   */
  wx.config({
      debug: true,
      appId: ${appId},
      timestamp: ${timestamp},
      nonceStr: ${nonceStr},
      signature: ${signature},
      jsApiList: [
        'chooseWXPay'
      ]
  });
</script>

<script type="text/javascript">

/*
 * 注意：
 * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
 * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
 * 3. 完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
 *
 * 如有问题请通过以下渠道反馈：
 * 邮箱地址：weixin-open@qq.com
 * 邮件主题：【微信JS-SDK反馈】具体问题
 * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
 */
wx.ready(function () {
	
	// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
    //function doPay(jsapiStr){
	//}
	wx.chooseWXPay({
		timestamp: ${time_stamp}, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
		nonceStr: ${nonce_str}, // 支付签名随机串，不长于 32 位
		package: ${prepay_id}, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=\*\*\*）
		signType: "SHA1", // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
		paySign: ${pay_sign}, // 支付签名
		success: function (res) {
			 alert(JSON.stringify(res))
			// this.$router.push({name :'tradingState', query :{'sign' : true}})
			//location.href = location.origin + location.pathname + '?transNo=' + tradeNo + '&sign=1#/tradingState'
		}
	});
	
    wx.error(function(res){
	    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
	    alert('config error')
	    alert(JSON.stringify(res))
	});
 		  
});
</script>

<script type="text/javascript">
	function submitData(params){
    	$.ajax({
            url :'<c:url value="/barcode/submitPay.do"/>',
            data : {"sId":"${sId}"} , 
            type : 'post',
            async: true,
            dataType : 'json', //返回数据类型
            beforeSend : function(){
            	HHN.loading("正在支付");
            },
            success : function(data){
                /*请求成功后*/
                if(data.resultCode == '0'){	                    	
					HHN.removeLoading();	                       
                	//dopay(data.context);							
                }else{
                    /*请求失败*/
                    HHN.removeLoading();
                    HHN.popup(data.resultMessage);
                }
            },
            error : function(){
            	HHN.removeLoading();
                /*出错后*/                
            }
        });
  }
	
</script>
	
</html>