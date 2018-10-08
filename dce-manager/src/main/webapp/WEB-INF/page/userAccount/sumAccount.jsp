<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />

<jsp:include page="../common_easyui_cus.jsp"></jsp:include>

<link type="text/css" rel="stylesheet" href="<c:url value='/css/flexslider.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/icons.css'/>" />
<script type="text/javascript" src="<c:url value='/js/common/js/jquery-easyui-1.4.1/extension/jquery-easyui-datagridview/datagrid-detailview.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.flexslider-min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/common/formatter.js?v=${jsversion}'/>"></script>
<style type="text/css">
.tdfont{
	font-size: 18px;
	
}
</style>
</head>
<body class="easyui-layout">
<div id="body" region="center" > 
  <!-- 查询条件区域 -->
	<div id="userAccountTable" region="center">
		      <table border="0" align="center" >
		      <c:forEach items="${sumList}" var="li">
		        <tr>
					<td class="tdfont">
						<c:choose>
							<c:when test="${li.accountType eq 'wallet_money' }">
							现金奖励
							</c:when>
						<c:when test="${li.accountType eq 'wallet_travel' }">
							旅游奖励
						</c:when>
						<c:when test="${li.accountType eq 'wallet_active' }">
							活动奖励
						</c:when>
						<c:when test="${li.accountType eq 'wallet_goods' }">
							商品赠送
						</c:when>
						</c:choose>
						    : ${li.amount}
					</td>
		        </tr>
		        </c:forEach>
		        <tr>
		        <td class="tdfont">
		        	收账汇总:${Totalperformance}
		        </td>
		        </tr>
		      </table>
  
  </div>
</div>

  
</body>
<script type="text/javascript">

</script>
</html>