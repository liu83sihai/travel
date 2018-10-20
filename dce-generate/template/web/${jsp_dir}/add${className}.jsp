<#include "/macro.include"/> 
<#include "/custom.include"/> 
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>${TABLE_INFO}编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
</head>
<body>

<!-- 内容 -->
<form id="edit${className}Form" method="post" action="<c:url value='/${classNameLowerCase}/save${className}.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
			  <#list table.columns as column>
				<#if column.pk>
					<input type="hidden" id="${column.columnNameLower}" name="${column.remarks}" value="<@jspEl classNameLowerCase+"."+column.columnNameLower/>"/>
				</#if>
			  </#list>
			  <#list table.columns as column>
				<#if !column.htmlHidden>
					<tr>	
						<td align="right">
							<label for="name">${column.remarks}</label>
						</td>	
						<td>
							<#if column.isDateTimeColumn>
								<input type="text" 
								id="${column.columnNameLower}" 
								name="${column.columnNameLower}" 
								class="easyui-datebox" size="14" data-options="editable : true"  
								/>
                           <#else>
								<input type="text" id="${column.columnNameLower}" name="${column.columnNameLower}" value="<@jspEl classNameLowerCase+"."+column.columnNameLower/>"/>												
                           </#if>
						</td>						   
					</tr>
				</#if>
			  </#list>
			</table>	   
		</div>	
	</form>

</body>

</html>