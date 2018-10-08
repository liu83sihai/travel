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
<form id="editGradeForm" method="post" action="<c:url value='grade/saveGrade.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="gradeId" name="gradeId" value="${grade.gradeId}"/>
					<tr>	
						<td align="right">
							<label for="name">gradeName</label>
						</td>	
						<td>
								<input type="text" id="gradeName" name="gradeName" value="${grade.gradeName}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">gradeMark</label>
						</td>	
						<td>
								<input type="text" id="gradeMark" name="gradeMark" value="${grade.gradeMark}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">remark</label>
						</td>	
						<td>
								<input type="text" id="remark" name="remark" value="${grade.remark}"/>												
						</td>						   
					</tr>
		
				<tr>
					<td colspan="2">
						<input id="submitButton" name="submitButton" type="button" onclick="grade_submit();"  value="提交" />	
					</td>
				<tr>			 
			</table>	   
		</div>	
	</form>
	<script type="text/javascript">
		
    function grade_submit(){
    	$.ajax({ 
    			url: "<c:url value='/grade/saveGrade.html'/>", 
    			data: $("#editGradeForm").serialize(),
    			type:"post",
    			dataType:"json",
    			success: function(ret){
    	   	 		if(ret.code==="0"){
    	   	 			$.messager.confirm("保存成功",
    	   	 				           '是否继续添加？', 
    	   	 				           function(r){
						   	   			   if(r==false){
						   	   				$("#editGradeDiv").dialog("close");
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