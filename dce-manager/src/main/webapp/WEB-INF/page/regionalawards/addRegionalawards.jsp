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
<form id="editRegionalawardsForm" method="post" action="<c:url value='regionalawards/saveRegionalawards.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="rewardsareaid" name="rewardsareaid" value="${regionalawards.rewardsareaid}"/>
					<tr>	
						<td align="right">
							<label for="name">区域奖励金额</label>
						</td>	
						<td>
								<input type="text" id="rewardbalance" name="rewardbalance" value="${regionalawards.rewardbalance}"/>												
						</td>						   
					</tr>
					<%-- <tr>	
						<td align="right">
							<label for="name"> 推荐代数</label>
						</td>	
						<td>
								<input type="text" id="algebra" name="algebra" value="${regionalawards.algebra}"/>												
						</td>						   
					</tr> --%>
					<tr>	
						<td align="right">
							<label for="name"> 备注</label>
						</td>	
						<td>
								<input type="text" id="remark" name="remark" value="${regionalawards.remark}"/>												
						</td>						   
					</tr>
		
				<!-- <tr>
					<td colspan="2">
						<input id="submitButton" name="submitButton" type="button" onclick="regionalawards_submit();"  value="提交" />	
					</td>
				<tr> -->			 
			</table>	   
		</div>	
	</form>
	<script type="text/javascript">
		
    function regionalawards_submit(){
    	alert("data---->>"+$("#editRegionalawardsForm").serialize());
    	$.ajax({ 
    			url: "<c:url value='/regionalawards/saveRegionalawards.html'/>", 
    			data: $("#editRegionalawardsForm").serialize(),
    			type:"post",
    			dataType:"json",
    			success: function(ret){
    	   	 		if(ret.code==="0"){
    	   	 			$.messager.confirm("保存成功",
    	   	 				           '是否继续添加？', 
    	   	 				           function(r){
						   	   			   if(r==false){
						   	   				$("#editRegionalawardsDiv").dialog("close");
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