<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>代理管理编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
</head>
<body>

<!-- 内容 -->
<form id="editAgencyForm" method="post" action="<c:url value='/agency/saveAgency.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="id" name="id" value="${agency.id}"/>
					<input type="hidden" id="userId" name="userId" value="${agency.userId}"/>
					<tr>	
						<td align="right">
							<label for="name">城市</label>
						</td>	
						<td>
								<input type="text" id="city" name="city" value="${agency.city}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">城市代码</label>
						</td>	
						<td>
								<input type="text" id="cityCode" name="cityCode" value="${agency.cityCode}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">备注</label>
						</td>	
						<td>
								<input type="text" id="remark" name="remark" value="${agency.remark}"/>												
						</td>						   
					</tr>
			</table>	   
		</div>	
	</form>

</body>

</html>