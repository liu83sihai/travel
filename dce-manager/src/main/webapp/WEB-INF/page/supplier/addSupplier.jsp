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
<form id="editSupplierForm" method="post" action="<c:url value='supplier/saveSupplier.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="id" name="id" value="${supplier.id}"/>
					<tr>	
						<td align="right">
							<label for="name">商家名称</label>
						</td>	
						<td>
								<input type="text" id="supplierName" name="supplierName" value="${supplier.supplierName}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">省份</label>
						</td>	
						<td>
								<input type="text" id="province" name="province" value="${supplier.province}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">城市</label>
						</td>	
						<td>
								<input type="text" id="city" name="city" value="${supplier.city}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">营业执照号</label>
						</td>	
						<td>
								<input type="text" id="businessNo" name="businessNo" value="${supplier.businessNo}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">营业执照图</label>
						</td>	
						<td>
								<input type="text" id="businessImage" name="businessImage" value="${supplier.businessImage}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">合作业务</label>
						</td>	
						<td>
								<input type="text" id="cooperative" name="cooperative" value="${supplier.cooperative}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">联系人姓名</label>
						</td>	
						<td>
								<input type="text" id="linkMan" name="linkMan" value="${supplier.linkMan}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">联系人电话</label>
						</td>	
						<td>
								<input type="text" id="linkPhone" name="linkPhone" value="${supplier.linkPhone}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">邮箱</label>
						</td>	
						<td>
								<input type="text" id="linkEmail" name="linkEmail" value="${supplier.linkEmail}"/>												
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
							<label for="name">申请时间</label>
						</td>	
						<td>
								<input type="text" 
								id="createTime" 
								name="createTime" 
								class="easyui-datebox" size="14" data-options="editable : true"  
								/>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">审核时间</label>
						</td>	
						<td>
								<input type="text" 
								id="auditTime" 
								name="auditTime" 
								class="easyui-datebox" size="14" data-options="editable : true"  
								/>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">审核结果</label>
						</td>	
						<td>
								<input type="text" id="auditInfo" name="auditInfo" value="${supplier.auditInfo}"/>												
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