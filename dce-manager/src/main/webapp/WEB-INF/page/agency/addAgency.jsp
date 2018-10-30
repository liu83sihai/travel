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
					<tr>	
						<td align="right">
							<label for="name">用户ID</label>
						</td>	
						<td>
								<input type="text" id="userId" name="userId" value="${agency.userId}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">姓名</label>
						</td>	
						<td>
								<input type="text" id="userName" name="userName" value="${agency.userName}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">手机号码</label>
						</td>	
						<td>
								<input type="text" id="mobile" name="mobile" value="${agency.mobile}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">身份证</label>
						</td>	
						<td>
								<input type="text" id="idCard" name="idCard" value="${agency.idCard}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">银行卡</label>
						</td>	
						<td>
								<input type="text" id="bankNumber" name="bankNumber" value="${agency.bankNumber}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">银行卡类型</label>
						</td>	
						<td>
								<input type="text" id="bankType" name="bankType" value="${agency.bankType}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">身份证正面照</label>
						</td>	
						<td>
								<input type="text" id="idcardFront" name="idcardFront" value="${agency.idcardFront}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">身份证反面照</label>
						</td>	
						<td>
								<input type="text" id="idcardBack" name="idcardBack" value="${agency.idcardBack}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">省份</label>
						</td>	
						<td>
								<input type="text" id="province" name="province" value="${agency.province}"/>												
						</td>						   
					</tr>
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
								<input type="text" id="createName" name="createName" value="${agency.createName}"/>												
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
								<input type="text" id="modifyName" name="modifyName" value="${agency.modifyName}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">状态(0:删除  1:正常 2:审核通过)</label>
						</td>	
						<td>
								<input type="text" id="status" name="status" value="${agency.status}"/>												
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