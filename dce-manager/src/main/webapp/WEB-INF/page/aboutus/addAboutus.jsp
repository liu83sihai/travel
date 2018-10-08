<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>编辑字典</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
</head>
<body>

<!-- 内容 -->
<form id="editAboutusForm" method="post" action="<c:url value='aboutus/saveAboutus.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="id" name="id" value="${aboutus.id}"/>
					<tr>	
						<td align="right">
							<label for="name">url</label>
						</td>	
						<td>
								<input type="text" id="url" name="url" value="${aboutus.url}"/>												
						</td>						   
					</tr>
		
				<tr>
					<td colspan="2">
					</td>
				<tr>			 
			</table>	   
		</div>	
	</form>

</body>

</html>