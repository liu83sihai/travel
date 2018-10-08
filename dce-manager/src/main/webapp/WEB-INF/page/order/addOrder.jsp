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
<script type="text/javascript" src="<c:url value='/js/common/formatter.js?v=${jsversion}'/>"></script>
<!-- 内容 -->
<form id="editOrderForm" method="post" action="<c:url value='order/saveOrder.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="orderId" name="orderId" value="${order.orderId}"/>
					<tr>	
						<td align="right">
							<label for="name">orderCode</label>
						</td>	
						<td>
								<input type="text" id="orderCode" name="orderCode" value="${order.orderCode}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">userId</label>
						</td>	
						<td>
								<input type="text" id="userId" name="userId" value="${order.userId}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">qty</label>
						</td>	
						<td>
								<input type="text" id="qty" name="qty" value="${order.qty}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">totalPrice</label>
						</td>	
						<td>
								<input type="text" id="totalPrice" name="totalPrice" value="${order.totalPrice}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">recAddress</label>
						</td>	
						<td>
								<input type="text" id="recAddress" name="recAddress" value="${order.recAddress}"/>												
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
								value="<fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd"/>"
								class="easyui-datebox" size="14" data-options="editable : true"  
								/>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">orderStatus</label>
						</td>	
						<td>
								<input type="text" id="orderStatus" name="orderStatus" value="${order.orderStatus}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">payStatus</label>
						</td>	
						<td>
								<input type="text" id="payStatus" name="payStatus" value="${order.payStatus}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">payTime</label>
						</td>	
						<td>
								<input type="text" 
								id="payTime" 
								name="payTime" 
								value="<fmt:formatDate value="${order.payTime}" pattern="yyyy-MM-dd"/>"
								class="easyui-datebox" size="14" data-options="editable : true"  
								/>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">orderType</label>
						</td>	
						<td>
								<input type="text" id="orderType" name="orderType" value="${order.orderType}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">matchOrderId</label>
						</td>	
						<td>
								<input type="text" id="matchOrderId" name="matchOrderId" value="${order.matchOrderId}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">salqty</label>
						</td>	
						<td>
								<input type="text" id="salqty" name="salqty" value="${order.salqty}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">accountType</label>
						</td>	
						<td>
								<input type="text" id="accountType" name="accountType" value="${order.accountType}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">goodsId</label>
						</td>	
						<td>
								<input type="text" id="goodsId" name="goodsId" value="${order.goodsId}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">price</label>
						</td>	
						<td>
								<input type="text" id="price" name="price" value="${order.price}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">addressId</label>
						</td>	
						<td>
								<input type="text" id="addressId" name="addressId" value="${order.addressId}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">remark</label>
						</td>	
						<td>
								<input type="text" id="remark" name="remark" value="${order.remark}"/>												
						</td>						   
					</tr>
		
				<tr>
					<td colspan="2">
						<input id="submitButton" name="submitButton" type="button" onclick="order_submit();"  value="提交" />	
					</td>
				<tr>			 
			</table>	   
		</div>	
	</form>
	<script type="text/javascript">
		
    function order_submit(){
    	$.ajax({ 
    			url: "<c:url value='/order/saveOrder.html'/>", 
    			data: $("#editOrderForm").serialize(),
    			type:"post",
    			dataType:"json",
    			success: function(ret){
    	   	 		if(ret.code==="0"){
    	   	 			$.messager.confirm("保存成功",
    	   	 				           '是否继续添加？', 
    	   	 				           function(r){
						   	   			   if(r==false){
						   	   				$("#editOrderDiv").dialog("close");
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