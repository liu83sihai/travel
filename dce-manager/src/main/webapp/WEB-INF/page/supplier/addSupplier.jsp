﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>商家管理编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
</head>
<body>

<!-- 内容 -->
<form id="editSupplierForm" method="post" action="<c:url value='/supplier/saveSupplier.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="id" name="id" value="${supplier.id}"/>
					<input type="hidden" id="telPhone" name="telPhone" value="${supplier.telPhone}"/>
					<input type="hidden" id="linkMan" name="linkMan" value="${supplier.linkMan}"/>
					<input type="hidden" id="longitude" name="longitude" value="${supplier.longitude}"/>
					<input type="hidden" id="latitude" name="latitude" value="${supplier.latitude}"/>
					<input type="hidden" id="createDate" name="createDate" value="${supplier.createDate}"/>
					<input type="hidden" id="createName" name="createName" value="${supplier.createName}"/>
					<input type="hidden" id="modifyDate" name="modifyDate" value="${supplier.modifyDate}"/>
					<input type="hidden" id="modifyName" name="modifyName" value="${supplier.modifyName}"/>
					<tr>	
						<td align="right">
							<label for="name">空</label>
						</td>	
						<td>
								<input type="text" id="userId" name="userId" value="${supplier.userId}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">供应商名</label>
						</td>	
						<td>
								<input type="text" id="supplierName" name="supplierName" value="${supplier.supplierName}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">推荐人姓名</label>
						</td>	
						<td>
								<input type="text" id="parentName" name="parentName" value="${supplier.parentName}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">简介</label>
						</td>	
						<td>
								<input type="text" id="synopsis" name="synopsis" value="${supplier.synopsis}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">详情</label>
						</td>	
						<td>
								<input type="text" id="content" name="content" value="${supplier.content}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">链接</label>
						</td>	
						<td>
								<input type="text" id="linkValue" name="linkValue" value="${supplier.linkValue}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">小图片</label>
						</td>	
						<td>
<%-- 								<input type="text" id="listImages" name="listImages" value="${supplier.listImages}"/> --%>
								<img src="${supplier.listImages}" width="100" height="100"	/>											
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">banner图</label>
						</td>	
						<td>
<%-- 								<input type="text" id="bannerImages" name="bannerImages" value="${supplier.bannerImages}"/>												 --%>
								<c:forEach items="${bannerImages}" var ="li" varStatus="status">
									<img src="${li}" width="100" height="100"	/>
							</c:forEach>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">营业执照</label>
						</td>	
						<td>
<%-- 								<input type="text" id="busiImage" name="busiImage" value="${supplier.busiImage}"/>												 --%>
								
							<c:forEach items="${busiImages}" var ="li" varStatus="status">
									<img src="${li}" width="100" height="100"	/>
							</c:forEach>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">门店照片</label>
						</td>	
						<td>
<%-- 								<input type="text" id="shopImage" name="shopImage" value="${supplier.shopImage}"/>												 --%>
								<c:forEach items="${shopImages}" var ="li" varStatus="status">
									<img src="${li}" width="100" height="100"	/>
							</c:forEach>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">供应商类型</label>
						</td>	
						<td>
								<select id="supplierType" name="supplierType" class="easyui-combobox" style="width: 150px;">
								<option value="1"
									<c:if test="${supplier.supplierType==1 || supplier.supplierType == null }">selected="selected"</c:if>>区县代理</option>
								<option value="2"
									<c:if test="${supplier.supplierType==2}">selected="selected"</c:if>>市代</option>
								<option value="2"
									<c:if test="${supplier.supplierType==3}">selected="selected"</c:if>>省代</option>
								</select>	
						
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">省市/城市</label>
						</td>	
						<td>
								<input type="text" id="city" name="city" value="${supplier.city}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">详细地址</label>
						</td>	
						<td>
								<input type="text" id="supplierAddress" name="supplierAddress" value="${supplier.supplierAddress}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">评分</label>
						</td>	
						<td>
								<input type="text" id="grade" name="grade" value="${supplier.grade}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">人均</label>
						</td>	
						<td>
								<input type="text" id="average" name="average" value="${supplier.average}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">点击数</label>
						</td>	
						<td>
								<input type="text" id="hitNum" name="hitNum" value="${supplier.hitNum}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">状态</label>
						</td>	
						<td>
								<input type="text" id="status" name="status" value="${supplier.status}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">备注</label>
						</td>	
						<td>
								<input type="text" id="remark" name="remark" value="${supplier.remark}"/>												
						</td>						   
					</tr>
			</table>	   
		</div>	
	</form>

</body>

</html>