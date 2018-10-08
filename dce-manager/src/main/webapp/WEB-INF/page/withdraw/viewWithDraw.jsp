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
<form id="editYsNewsForm" method="post" enctype="multipart/form-data" action="<c:url value='ysnews/saveYsNews.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<tr>	
						<td align="right">
							<label for="name">支付宝转账单据号</label>
						</td>	
						<td>
								<input type="text" id="order_id" name="order_id" value="${queryWithdraw.order_id}"/>												
						</td>						   
					</tr>
	
					<tr>	
						<td align="right">
							<label for="name">转账单据状态</label>
						</td>	
						<td>
						<textarea rows="5" cols="20"  id="status" name="status">${queryWithdraw.status}</textarea>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">支付时间</label>
						</td>	
						<td>
								<input type="text" id="pay_date" name="pay_date" value="${queryWithdraw.pay_date}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">预计到账时间</label>
						</td>	
						<td>
								<input type="text" id="arrival_time_end" name="arrival_time_end" value="${queryWithdraw.arrival_time_end}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">预计收费金额</label>
						</td>	
						<td>
						<textarea rows="5" cols="20" id="order_fee" name="order_fee" >${queryWithdraw.order_fee}</textarea>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">转账单据号</label>
						</td>	
						<td>
								<input type="text" id="out_biz_no" name="out_biz_no" value="${queryWithdraw.out_biz_no}"/>												
						</td>						   
					</tr>
			</table>	   
		</div>	
	</form>
	<script type="text/javascript">
		
    function ysnews_submit(){
    	alert( $("#editYsNewsForm").serialize());
    	$.ajax({ 
    			url: "<c:url value='/ysnews/saveYsNews.html'/>", 
    			data: $("#editYsNewsForm").serialize(),
    			type:"post",
    			dataType:"json",
    			success: function(ret){
    	   	 		if(ret.code==="0"){
    	   	 			$.messager.confirm("保存成功",
    	   	 				           '是否继续添加？', 
    	   	 				           function(r){
						   	   			   if(r==false){
						   	   				$("#editYsNewsDiv").dialog("close");
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