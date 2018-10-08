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
<form id="editUserPromoteForm" method="post" action="<c:url value='userpromote/saveUserPromote.html'/>"> 
		<div style="text-align: center;">
		<h3>${userpromote.userLevel}升级到${userpromote.promoteLevel}</h3>
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="promoteId" name="promoteId" value="${userpromote.promoteId}"/>
					<tr>	
						<td align="right">
							<label for="name">最小数量</label>
						</td>	
						<td>
								<input type="text" id="minQty" name="minQty" value="${userpromote.minQty}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">最大数量</label>
						</td>	
						<td>
								<input type="text" id="maxQty" name="maxQty" value="${userpromote.maxQty}"/>												
						</td>						   
					</tr>
			</table>	   
		</div>	
	</form>
	<script type="text/javascript">
		
    function userpromote_submit(){
    	$.ajax({ 
    			url: "<c:url value='/userpromote/saveUserPromote.html'/>", 
    			data: $("#editUserPromoteForm").serialize(),
    			type:"post",
    			dataType:"json",
    			success: function(ret){
    	   	 		if(ret.code==="0"){
    	   	 			$.messager.confirm("保存成功",
    	   	 				           '是否继续添加？', 
    	   	 				           function(r){
						   	   			   if(r==false){
						   	   				$("#editUserPromoteDiv").dialog("close");
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