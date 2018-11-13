<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户激活卡编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
</head>
<body>

<!-- 内容 -->
<form id="editUserCardForm" method="post" action="<c:url value='/usercard/saveUserCard.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="id" name="id" value="${usercard.id}"/>
					<tr>	
						<td align="right">
							<label for="name">用户ID</label>
						</td>	
						<td>
								<input type="text" id="userId" name="userId" value="${usercard.userId}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">用户名</label>
						</td>	
						<td>
								<input type="text" id="userName" name="userName" value="${usercard.userName}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">用户手机</label>
						</td>	
						<td>
								<input type="text" id="mobile" name="mobile" value="${usercard.mobile}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">流水单号</label>
						</td>	
						<td>
								<input type="text" id="orderNo" name="orderNo" value="${usercard.orderNo}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">用户等级</label>
						</td>	
						<td>
								<input type="text" id="userLevel" name="userLevel" value="${usercard.userLevel}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">性别</label>
						</td>	
						<td>
								<input type="text" id="sex" name="sex" value="${usercard.sex}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">第三方卡号</label>
						</td>	
						<td>
								<input type="text" id="cardNo" name="cardNo" value="${usercard.cardNo}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">银行卡号</label>
						</td>	
						<td>
								<input type="text" id="bankNo" name="bankNo" value="${usercard.bankNo}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">身份证号</label>
						</td>	
						<td>
								<input type="text" id="idNumber" name="idNumber" value="${usercard.idNumber}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">备注</label>
						</td>	
						<td>
								<input type="text" id="remark" name="remark" value="${usercard.remark}"/>												
						</td>						   
					</tr>
					
			</table>	   
		</div>	
	</form>

</body>

</html>