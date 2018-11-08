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
	<title>我的</title>
	<jsp:include page="../common/common.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css_v1/litem.css?v=${cssversion}">
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css_v1/card.css?v=${cssversion}">
</head>
<body>
<c:if test="${empty sessionScope.jdGmapInFlag && sessionScope.jdGmapInFlag eq null}">
	<div class="header">
		<p class="title">我的</p>
	</div>
</c:if>
<div class="navbar clearfix">
	<a href="<c:url value='${customerwebdomain}'/>"><i class="icon i-sy"></i>首页</a>
	<%-- <a href="<c:url value='${customerwebdomain}/shouXinOrder/toLoan.do'/>"><i class="icon i-jk"></i>提款</a> --%>
	<a href="<c:url value='${customerwebdomain}/credit/applyProcess.do'/>"><i class="icon i-jk"></i>提款</a>
	<!-- <a href="javascript:void(0)" style="opacity: 0.2;"><i class="icon i-hk"></i>还款</a> -->
	<a href="<c:url value='${customerwebdomain}/repaymentSSB/goToRepaymentHomePage.do'/>"><i class="icon i-hk"></i>还款</a>
	<a href="<c:url value='${basePath}/loanPersonalCenter/toPersonalCenter.do?from=personPage'/>" class="navOn"><i class="icon i-wd"></i>我的</a>
</div>
<div class="content" <c:if test="${not empty sessionScope.jdGmapInFlag && sessionScope.jdGmapInFlag ne null}">style="padding-top: 0px;"</c:if>>
	<div class="gradienttop wode">
		<i class="bgicon bgi1"></i><i class="bgicon bgi2"></i><i class="bgicon bgi3"></i>
		<span class="userhead"><i class="icon i-man"></i></span>
		<%-- <p class="usertel">${userAccount.userShow}</p> --%>
		<p class="usertel"></p>
	</div>
	<div class="litem">
		<a href="<c:url value='${customerwebdomain}/creditReport/creditReportIndex.do'/>"><span class="lright reds"><i class="icon i-next"></i>信用借款必备</span></a>
		<span class="ltext">查询信用</span>
	</div>
	<div class="litem space">
		<a href="<c:url value='${customerwebdomain}/agent/myAgent.do'/>"><span class="lright"><i class="icon i-next"></i></span></a>
		<span class="ltext">我的经纪人</span>
	</div>
	<div class="litem">
		<a href="<c:url value='${customerwebdomain}/shouXinOrder/loanRecordList.do'/>"><span class="lright"><i class="icon i-next"></i></span></a>
		<span class="ltext">账单明细</span>
	</div>
	<div class="litem">
		<a href="<c:url value="/loanPersonalCenter/toLoanSchedule.do?loanId=0"/>"><span class="lright"><i class="icon i-next"></i></span></a>
		<span class="ltext">贷款进度查询</span>
	</div>
	<div class="litem">
		<a href="<c:url value="${basePath}/bank/toBankCardManager.do"/>"><span class="lright"><i class="icon i-next"></i></span></a>
		<span class="ltext">银行卡管理</span>
	</div>
	<div class="litem">
		<a href="<c:url value='${customerwebdomain}/protocol/toIndex.do'/>"><span class="lright"><i class="icon i-next"></i></span></a>
		<span class="ltext">我的合同</span>
	</div>
	<div class="litem">
		<a href="<c:url value='${basePath}/loanuser/toRestPwd.do?fromUrl=${basePath}/loanPersonalCenter/toPersonalCenter.do?from=personPage'/>"><span class="lright"><i class="icon i-next"></i></span></a>
		<span class="ltext">修改密码</span>
	</div>
	<div class="litem">
		<a href="<c:url value='${customerwebdomain}/question/listQuestion.do'/>"><span class="lright"><i class="icon i-next"></i></span></a>
		<span class="ltext">常见问题</span>
	</div>
	<div class="litem">
		<a href="<c:url value='${customerwebdomain}/index/aboutUs.do'/>"><span class="lright"><i class="icon i-next"></i></span></a>
		<span class="ltext">关于我们</span>
	</div>
	<div class="litem">
		 <c:choose>
        <c:when test="${platform ne 'android' && platform ne 'ios' && platform ne 'pad'}">
        	<a href="<c:url value='${basePath}/loanuser/loginout.do?fromUrl=${basePath}/loanPersonalCenter/toPersonalCenter.do?from=personPage'/>"><span class="lright"><i class="icon i-next"></i></span></a>
            <span class="ltext">安全退出</span>
        </c:when>
        <c:otherwise>
			<a href="javascript:;" id="appLogout"><span class="lright"><i class="icon i-next"></i></span></a>
			<span class="ltext">安全退出</span>
        </c:otherwise>
        </c:choose>
	</div>
</div>
</body>
<script type="text/javascript">
$(function(){ 
    var phone = "${userAccount.mobile}";
    var mphone =phone.substr(3,4);//需要替换的位置
    var phone1 =phone.substr(0,3);//截取手机开头前3位
    var phone2 =phone.substr(3,10);//截取手机号后8位
    var lphone = phone2.replace(mphone,"****");//替换后8位
    $(".usertel").html(phone1+lphone);//显示
  });
$(function(){
	$("#appLogout").click(function(){
		//call native method  调用原生APP方法
		window.WebViewJavascriptBridge.callHandler('appLogout', {}, function(responseData) {
           	//alert(responseData);
           	//alert("退出成功！");
		});
	});
	
	function bridgeLog(logContent) {
        document.getElementById("show").innerHTML = logContent;
    }

    function connectWebViewJavascriptBridge(callback) {
        if (window.WebViewJavascriptBridge) {
            callback(WebViewJavascriptBridge)
        } else {
            document.addEventListener(
                'WebViewJavascriptBridgeReady'
                , function() {
                    callback(WebViewJavascriptBridge)
                },
                false
            );
        }
    }

	
    connectWebViewJavascriptBridge(function(bridge) {			
        bridge.init(function(message, responseCallback) {
            console.log('JS got a message', message);
            var data = {
                'Javascript Responds': 'Wee!'
            };
            console.log('JS responding with', data);
            responseCallback(data);
        });
		bridge.send("message from javascript");
        bridge.registerHandler("functionInJs", function(data, responseCallback) {
            document.getElementById("show").innerHTML = ("data from Java: = " + data);
            var responseData = "Javascript Says Right back aka!";
            responseCallback(responseData);
        });
    })
})

</script>
</html>