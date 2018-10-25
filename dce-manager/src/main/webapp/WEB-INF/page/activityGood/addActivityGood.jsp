<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>活动点赞编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
</head>
<body>

<!-- 内容 -->
<form id="editActivityGoodForm" method="post" action="<c:url value='/activitygood/saveActivityGood.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="id" name="id" value="${activitygood.id}"/>
					<tr>	
						<td align="right">
							<label for="name">空</label>
						</td>	
						<td>
								<input type="text" id="userId" name="userId" value="${activitygood.userId}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">空</label>
						</td>	
						<td>
								<input type="text" id="activityId" name="activityId" value="${activitygood.activityId}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">创建时间</label>
						</td>	
						<td>
								<input type="text" 
								id="createDate" 
								name="createDate" 
								class="easyui-datebox" size="14" data-options="editable : true"  
								/>
						</td>						   
					</tr>
			</table>	   
		</div>	
	</form>

</body>

</html>