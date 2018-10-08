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
<form id="editOrderSendoutForm" method="post" action="<c:url value='ordersendout/saveOrderSendout.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="id" name="id" value="${ordersendout.id}"/>
					<tr>	
						<td align="right">
							<label for="name">orderId</label>
						</td>	
						<td>
								<input type="text" id="orderId" name="orderId" value="${ordersendout.orderId}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">operatorId</label>
						</td>	
						<td>
								<input type="text" id="operatorId" name="operatorId" value="${ordersendout.operatorId}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">address</label>
						</td>	
						<td>
								<input type="text" id="address" name="address" value="${ordersendout.address}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">createTime</label>
						</td>	
						<td>
								<input type="text" 
								id="createTime" 
								name="createTime" 
								value="<fmt:formatDate value="createTime" pattern="yyyy-MM-dd"/>"
								class="easyui-datebox" size="14" data-options="editable : true"  
								/>
						</td>						   
					</tr>
		
				<tr>
					<td colspan="2">
						<input id="submitButton" name="submitButton" type="button" onclick="ordersendout_submit();"  value="提交" />	
					</td>
				<tr>			 
			</table>	   
		</div>	
	</form>
	<script type="text/javascript">
		
    function ordersendout_submit(){
    	$.ajax({ 
    			url: "<c:url value='/ordersendout/saveOrderSendout.html'/>", 
    			data: $("#editOrderSendoutForm").serialize(),
    			type:"post",
    			dataType:"json",
    			success: function(ret){
    	   	 		if(ret.code==="0"){
    	   	 			$.messager.confirm("保存成功",
    	   	 				           '是否继续添加？', 
    	   	 				           function(r){
						   	   			   if(r==false){
						   	   				$("#editOrderSendoutDiv").dialog("close");
						   	   			   }
    	   						});
    	   	 		}else{
    	   	 			$.messager.alert("error",ret.msg);
    	   	 		}
    	      	}
    	        });
    }
	
</script>

</body>

</html>