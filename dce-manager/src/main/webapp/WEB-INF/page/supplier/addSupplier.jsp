<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
								<input type="text" id="listImages" name="listImages" value="${supplier.listImages}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">banner图</label>
						</td>	
						<td>
								<input type="text" id="bannerImages" name="bannerImages" value="${supplier.bannerImages}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">营业执照</label>
						</td>	
						<td>
								<input type="text" id="busiImage" name="busiImage" value="${supplier.busiImage}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">门店照片</label>
						</td>	
						<td>
								<input type="text" id="shopImage" name="shopImage" value="${supplier.shopImage}"/>												
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
							<label for="name">电话</label>
						</td>	
						<td>
								<input type="text" id="telPhone" name="telPhone" value="${supplier.telPhone}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">联系人</label>
						</td>	
						<td>
								<input type="text" id="linkMan" name="linkMan" value="${supplier.linkMan}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">类型</label>
						</td>	
						<td>
								<input type="text" id="supplierType" name="supplierType" value="${supplier.supplierType}"/>												
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
							<label for="name">经度</label>
						</td>	
						<td>
								<input type="text" id="longitude" name="longitude" value="${supplier.longitude}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">纬度</label>
						</td>	
						<td>
								<input type="text" id="latitude" name="latitude" value="${supplier.latitude}"/>												
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
								<input type="text" id="createName" name="createName" value="${supplier.createName}"/>												
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
								<input type="text" id="modifyName" name="modifyName" value="${supplier.modifyName}"/>												
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