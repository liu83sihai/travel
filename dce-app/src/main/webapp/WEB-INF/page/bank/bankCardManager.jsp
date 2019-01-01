<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<title>银行卡管理</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css_v1/public.css?v=${cssversion}">
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css_v1/modal.css?v=${cssversion}" />
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css_v1/litem.css?v=${cssversion}">
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css_v1/card.css?v=${cssversion}">
    <style type="text/css">
    	.add-bank-btn i {
		    background-image: url(http://rec.gemlends.com/res/images/app/login.png);
		}
		/*银行卡*/
		.empty-tip {
		    text-align: center;
		    font-size: 14px;
		    color: #999;
		    padding: 50px 0 20px;
		}
		.empty-tip i {
		    background-image: url(http://rec.gemlends.com/res/images/app/empty.png);
		    -webkit-background-size: 67px auto;
		    background-size: 67px auto;
		    display: inline-block;
		    width: 67px;
		    height: 60px;
		    background-repeat: no-repeat;
		}
		.empty-tip p {
		    padding: 5px 0;
		    font-size: 16px;
		}
		
		/** 个人中心银行卡  ****/
		.add-bank-btn {
		    margin: 10px 15px;
		    border-radius: 8px;
		    border: 2px dashed #999;
		    height: 90px;
		    padding: 10px;
		    text-align: center;
		    color: #999;
		    font-size: 14px;
		}
		.add-bank-btn i {
		    display: inline-block;
		    width: 40px;
		    height: 40px;
		    background-image: url(http://rec.gemlends.com/res/images/app/icons.png);
		    -webkit-background-size: 140px auto;
		    background-size: 140px auto;
		    background-position: -80px -40px;
		}
		/*银行卡管理*/
		.bank-list{
		    margin: 0 15px;
		}
		.bank-list>a{
		    display: block;
		    margin-top: 10px;
		    background: #27a2f0;
		    border-radius: 8px;
		    padding:10px;
		    color: #fff;
		}
		.bank-list .logo{
		    display: inline-block;
		    margin-right: 10px;
		    width: 37px;
		    height: 37px;
		    border-radius: 19px;
		    overflow: hidden;
		}
		.bank-list h3{
		    display: -webkit-box;
		    display: -webkit-flex;
		    display: flex;
		    /*垂直*/
		    
		    -webkit-box-align: center;
		    -webkit-align-items: center;
		    align-items: center;
		}
		.bank-list p{
		    text-align: right;
		    padding-top: 10px;
		    font-size: 29px;
		}
		.add-bank-btn{
		    margin: 10px 15px;
		    border-radius: 8px;
		    border: 2px dashed #999;
		    height: 90px;
		    padding: 10px;
		    text-align: center;
		    color: #999;
		    font-size: 14px;
		}
		.add-bank-btn i{
		    display: inline-block;
		    width: 40px;
		    height: 40px;
		    background-image: url(http://rec.gemlends.com/res/images/app/login.png);
		    -webkit-background-size: 140px auto;
		    background-size: 140px auto;
		    background-position: -80px -40px;
		}
		.center {
		    display: -webkit-box;
		    display: -webkit-flex;
		    display: flex;
		    /*垂直*/
		    
		    -webkit-box-align: center;
		    -webkit-align-items: center;
		    align-items: center;
		    /*水平*/
		    
		    -webkit-box-pack: center;
		    -webkit-justify-content: center;
		    justify-content: center;
		}
    </style> 
</head>
<body>
<%--
<div class="header">
	<a class="a-fl" href="<c:url value='${userwebdomain}/loanPersonalCenter/toPersonalCenter.do?from=personPage'/>"><i class="icon i-back"></i></a>
	<p class="title">银行卡管理</p>
</div>
 --%>
<div class="content">
		
		    <input type="hidden" value="${sign}" id = "sign"/>
		    <input type="hidden" value="${userId }" id = "userId"/>
		    <input type="hidden" value="${ts}" id="ts" >
    
	    <!-- 有银行卡 -->
	    <c:if test="${!empty bankCardList}">
	    	
				<div class="litem">
					<c:forEach var="item" items="${bankCardList}" varStatus="noBank">
						<div class="tikuanedubox" id="${item.id}">
							<p class="logo"></p>
							<p class="edu">${item.cardNoShow}</p>
							<p class="star" style="font-size: 16px;">${item.bankName}</p>
							<p></p>
						</div>
					</c:forEach>
				</div>
				<%--
				<div class="btnbox">
					<button class="blockbtn" onclick="deleteBind()">解除绑定</button>
				</div>
				 --%>
	    </c:if>
	    
	     <!-- 没有银行卡  -->
	    <c:if test="${empty bankCardList}">
	    	<div class="empty-tip">
		        <i></i>
		        <p>未绑定银行卡哦！</p>
		    </div>
	    </c:if>
	    <c:url value='/bank/toAddBankCard.do'/>?ts=${ts}&sign=${sign}&userId=${userId}
	    <a href="<c:url value='/bank/toAddBankCard.do'/>?ts=${ts}&sign=${sign}&userId=${userId}" class="add-bank-btn center">
	        <div>
	            <i></i>
	            <p>添加新的银行卡</p>
	        </div>
		</a>
</div>
</body>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/jquery.min.js?v=${jsversion}"></script>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/global.js?v=${jsversion}"></script>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/modal.js?v=${jsversion}"></script>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/distrit.js?v=${jsversion}"></script>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/distritSelector.js?v=${jsversion}"></script>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/bankCardCheck.js?v=${jsversion}"></script>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/lrz.bundle.js?v=${jsversion}"></script>
<script type="text/javascript">
	function deleteBind(){
		var options={title:"",
	            content:"是否确定解除绑定银行卡?",
	            cancel:true,
	            okText:'确定',
	            ok:function(){
	            	$.ajax({
	       	         url :"<c:url value='/bank/deleteBindCard.do'/>",
	       	         data : {"userBankId":$(".tikuanedubox").eq(0).attr("id")} , 
	       	         type : 'post',
	       	         dataType : 'json', //返回数据类型
	       	         beforeSend : function(){
	       	         	var u = navigator.userAgent;
		     			var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
		     			if(!isAndroid){
		     				HHN.loading("请求中,请稍后");
		     			}
	       	         },
	       	         success : function(data){
	       	             /*请求成功后*/
	       	             if(data.success && data.resultCode == '0'){
	       	             	HHN.removeLoading();
	       	             	HHN.popup(data.resultMessage);
	       	                setTimeout(function(){location.href='<c:url value="/bank/toBindBankCard.do"/>'},1000);//提示后延时1s
	       	             }else{
	       	                 /*请求失败*/
	       	                 HHN.removeLoading();
	       	                 HHN.popup(data.resultMessage);
	       	             }
	       	         },
	       	         error : function(){
	       	         	HHN.removeLoading();
	       	             /*出错后*/     
	       	         	HHN.popup("系统异常，请稍后重试");
	       	         }
	       	     });
	               }
	            }
	    HHN.webPopup("",options);
	}
</script>
</html>
