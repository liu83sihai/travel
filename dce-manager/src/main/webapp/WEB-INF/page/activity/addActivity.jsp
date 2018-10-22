<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>活动风彩编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
</head>
<body>

<!-- 内容 -->
<form id="editActivityForm" method="post" action="<c:url value='/activity/saveActivity.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="id" name="id" value="${activity.id}"/>
					<tr>	
						<td align="right">
							<label for="name">用户ID</label>
						</td>	
						<td>
								<input type="text" id="userId" name="userId" value="${activity.userId}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">描述</label>
						</td>	
						<td>
								<input type="text" id="synopsis" name="synopsis" value="${activity.synopsis}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">内容</label>
						</td>	
						<td>
								<input type="text" id="content" name="content" value="${activity.content}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">图片</label>
						</td>	
						<td>
								<input type="text" id="images" name="images" value="${activity.images}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">点赞数</label>
						</td>	
						<td>
								<input type="text" id="hitNum" name="hitNum" value="${activity.hitNum}"/>												
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
					<tr>	
						<td align="right">
							<label for="name">创建人</label>
						</td>	
						<td>
								<input type="text" id="createName" name="createName" value="${activity.createName}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">更新时间</label>
						</td>	
						<td>
								<input type="text" 
								id="modifyDate" 
								name="modifyDate" 
								class="easyui-datebox" size="14" data-options="editable : true"  
								/>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">更新人</label>
						</td>	
						<td>
								<input type="text" id="modifyName" name="modifyName" value="${activity.modifyName}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">状态</label>
						</td>	
						<td>
								<input type="text" id="status" name="status" value="${activity.status}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">备注</label>
						</td>	
						<td>
								<input type="text" id="remark" name="remark" value="${activity.remark}"/>												
						</td>						   
					</tr>
			</table>	   
		</div>	
	</form>

</body>

</html>