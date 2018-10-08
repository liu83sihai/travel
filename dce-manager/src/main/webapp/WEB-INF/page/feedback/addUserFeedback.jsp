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
<form id="editUserFeedbackForm" method="post" action="<c:url value='userfeedback/saveUserFeedback.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="feedbackid" name="feedbackid" value="${userfeedback.feedbackid}"/>
					<tr>	
						<td align="right">
							<label for="name">userid</label>
						</td>	
						<td>
								<input type="text" id="userid" name="userid" value="${userfeedback.userid}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">feedbackcontent</label>
						</td>	
						<td>
								<textarea rows="3" cols="20" id="feedbackcontent"
							form="editUserFeedbackForm">${userfeedback.feedbackcontent}</textarea>												
						</td>						   
					</tr>
					 <%-- <tr>	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
						<td align="right">
							<label for="name">createtime</label>
						</td>	
						<td>
								<input type="text" 
								id="createtime" 
								name="createtime" 
								value="<fmt:formatDate value="${userfeedback.createtime} " pattern="yyyy-MM-dd"/>"
								class="easyui-datebox" size="14" data-options="editable : true"  
								/>
						</td>						   
					</tr>  --%>
		
			</table>	   
		</div>	
	</form>
	<script type="text/javascript">
		
    function userfeedback_submit(){
    	$.ajax({ 
    			url: "<c:url value='/userfeedback/saveUserFeedback.html'/>", 
    			data: $("#editUserFeedbackForm").serialize(),
    			type:"post",
    			dataType:"json",
    			success: function(ret){
    	   	 		if(ret.code==="0"){
    	   	 			$.messager.confirm("保存成功",
    	   	 				           '是否继续添加？', 
    	   	 				           function(r){
						   	   			   if(r==false){
						   	   				$("#editUserFeedbackDiv").dialog("close");
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