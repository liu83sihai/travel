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
<form id="editApplyTravelForm" method="post" action="<c:url value='applytravel/saveApplyTravel.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="id" name="id" value="${applytravel.id}"/>
						<tr>	
						<td>
								<input type="hidden" id="userid" name="userid" value="${applytravel.userid}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">姓名</label>
						</td>	
						<td>
								<input type="text" id="name" name="name" value="${applytravel.name}"/>												
						</td>						   
					</tr>											
					<tr>	
						<td align="right">
							<label for="name">性别</label>
						</td>	
						<td>
								<select id="sex" class="easyui-combobox" style="width: 150px;">
							<option value="0"
								<c:if test="${applytravel.sex==0}">selected="selected"</c:if>>男</option>
							<option value="1"
								<c:if test="${applytravel.sex==1}">selected="selected"</c:if>>女</option>
					</select>													
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">民族</label>
						</td>	
						<td>
								<input type="text" id="nation" name="nation" value="${applytravel.nation}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">身份证</label>
						</td>	
						<td>
								<input type="text" id="identity" name="identity" value="${applytravel.identity}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">手机号码</label>
						</td>	
						<td>
								<input type="text" id="phone" name="phone" value="${applytravel.phone}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">地址</label>
						</td>	
						<td>
								<input type="text" id="address" name="address" value="${applytravel.address}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">是否去过</label>
						</td>	
						<td>
								<select id="isbeen" class="easyui-combobox"
						style="width: 150px;">
							<option value="0"
								<c:if test="${applytravel.isbeen==0}">selected="selected"</c:if>>是</option>
							<option value="1"
								<c:if test="${applytravel.isbeen==1}">selected="selected"</c:if>>否</option>
					</select>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">同行人数</label>
						</td>	
						<td>
								<select id="people" class="easyui-combobox" style="width: 150px;">
									<option value="1" <c:if test="${applytravel.people==1}">selected="selected"</c:if>>1人</option>
									<option value="2" <c:if test="${applytravel.people==2}">selected="selected"</c:if>>2人</option>
									<option value="3" <c:if test="${applytravel.people==3}">selected="selected"</c:if>>3人</option>
									<option value="4" <c:if test="${applytravel.people==4}">selected="selected"</c:if>>4人</option>									
								</select>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">路线</label>
						</td>	
						<td>
							<select id="pathid" class="easyui-combobox" style="width: 150px;">
								<c:forEach var="path" items="${path }">
									<option value="${path.pathid }" <c:if test="${path.pathid==applytravel.pathid}">selected="selected"</c:if>>${path.linename }</option>
								</c:forEach>
							</select>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">申请状态</label>
						</td>	
						<td>
								<select id="state" class="easyui-combobox"
						style="width: 150px;">
							<option value="0"
								<c:if test="${applytravel.state==0}">selected="selected"</c:if>>通过</option>
							<option value="1"
								<c:if test="${applytravel.state==1}">selected="selected"</c:if>>未通过</option>
								<option value="2"
								<c:if test="${applytravel.state==2}">selected="selected"</c:if>>撤销</option>
					</select>
						</td>						   
					</tr>
		
			</table>	   
		</div>	
	</form>
	<script type="text/javascript">
		
    function applytravel_submit(){
    	$.ajax({ 
    			url: "<c:url value='/applytravel/saveApplyTravel.html'/>", 
    			data: $("#editApplyTravelForm").serialize(),
    			type:"post",
    			dataType:"json",
    			success: function(ret){
    	   	 		if(ret.code==="0"){
    	   	 			$.messager.confirm("保存成功",
    	   	 				           '是否继续添加？', 
    	   	 				           function(r){
						   	   			   if(r==false){
						   	   				$("#editApplyTravelDiv").dialog("close");
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