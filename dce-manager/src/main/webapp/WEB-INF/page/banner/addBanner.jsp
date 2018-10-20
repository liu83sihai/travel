<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>图标管理编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
</head>
<body>

<!-- 内容 -->
<form id="editBannerForm" method="post"  enctype="multipart/form-data" action="<c:url value='/banner/saveBanner.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="id" name="id" value="${banner.id}"/>
					<input type="hidden" id="createDate" name="createDate" value="${banner.createDate}"/>
					<input type="hidden" id="createName" name="createName" value="${banner.createName}"/>
					<input type="hidden" id="modifyDate" name="modifyDate" value="${banner.modifyDate}"/>
					<input type="hidden" id="modifyName" name="modifyName" value="${banner.modifyName}"/>
					<input type="hidden" id="status" name="status" value="${banner.status}"/>
					<tr>	
						<td align="right">
							<label for="name">图标名称</label>
						</td>	
						<td>
								<input type="text" id="icoName" name="icoName" value="${banner.icoName}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">图片</label>
						</td>	
						<td>
<%-- 								<input type="text" id="icoImage" name="icoImage" value="${banner.icoImage}"/>												 --%>
								<input type="file" id="icoImages" name="icoImages"/>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">图标类型 </label>
						</td>	
						<td>
							<select id="icoType" name="icoType" class="easyui-combobox" style="width: 150px;">
								<option value="1"
									<c:if test="${banner.icoType==1 || banner.icoType == null }">selected="selected"</c:if>>banner图</option>
								<option value="2"
									<c:if test="${banner.icoType==2}">selected="selected"</c:if>>导航小图标</option>
							</select>	
						
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">链接类型</label>
						</td>	
						<td>
							<select id="linkType" name="linkType" class="easyui-combobox" style="width: 150px;">
								<option value="0"
									<c:if test="${banner.linkType==0 || banner.linkType == null }">selected="selected"</c:if>>无</option>
								<option value="1"
									<c:if test="${banner.linkType==1}">selected="selected"</c:if>>超链接</option>
								<option value="2"
									<c:if test="${banner.linkType==2}">selected="selected"</c:if>>程序链接</option>
							</select>	
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">链接值</label>
						</td>	
						<td>
								<input type="text" id="linkValue" name="linkValue" value="${banner.linkValue}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">提示内容</label>
						</td>	
						<td>
								<input type="text" id="hintValue" name="hintValue" value="${banner.hintValue}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">排序</label>
						</td>	
						<td>
								<input type="text" id="sort" name="sort" value="${banner.sort}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">备注</label>
						</td>	
						<td>
								<input type="text" id="remark" name="remark" value="${banner.remark}"/>												
						</td>						   
					</tr>
			</table>	   
		</div>	
	</form>

</body>

</html>