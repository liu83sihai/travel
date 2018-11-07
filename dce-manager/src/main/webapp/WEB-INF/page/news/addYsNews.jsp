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
					<input type="hidden" id="id" name="id" value="${ysnews.id}"/>
					<%--
					<tr>	
						<td align="right">
							<label for="name">标题</label>
						</td>	
						<td>
								<input type="text" id="title" name="title" value="${ysnews.title}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">新闻地址</label>
						</td>	
						<td>
								<input type="text" id="content" name="content" value="${ysnews.content}"/>												
						</td>						   
					</tr>
					 --%>
					
					 <tr>	
						<td align="right">
							<label for="name">标题</label>
						</td>	
						<td>
								<input type="text" id="title" name="title" value="${ysnews.title}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">图片</label>
						</td>	
						<td>
								<input type="file" id="image" name="image"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">内容</label>
						</td>	
						<td>
						<textarea rows="5" cols="20"  id="content" name="content">${ysnews.content}</textarea>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">作者</label>
						</td>	
						<td>
								<input type="text" id="author" name="author" value="${ysnews.author}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">置顶新闻</label>
						</td>	
						<td>
						<select id="topNews" class="easyui-combobox" name="topNews" style="width: 150px;">
									<option value="0" <c:if test="${ysnews.topNews==0}">selected="selected"</c:if> >待置顶</option>
									<option value="1" <c:if test="${ysnews.topNews==1}">selected="selected"</c:if>>置顶</option>
							</select>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">备注</label>
						</td>	
						<td>
						<textarea rows="5" cols="20" id="remark" name="remark" >${ysnews.remark}</textarea>
						</td>						   
					</tr> --%>
					<%-- <tr>	
						<td align="right">
							<label for="name">状态</label>
						</td>	
						<td>
						<select id="status" class="easyui-combobox" name="status" style="width: 150px;">
									<option value="0" <c:if test="${ysnews.status==0}">selected="selected"</c:if> >待发布</option>
									<option value="1" <c:if test="${ysnews.status==1}">selected="selected"</c:if>>发布</option>
							</select>
						</td>						   
					</tr> --%>
					<%-- <tr>	
						<td align="right">
							<label for="name">创建日期</label>
						</td>	
						<td>
								<input type="text" 
								id="createDate" 
								name="createDate" 
								value="<fmt:formatDate value="${ysnews.createDate}" pattern="yyyy-MM-dd"/>"
								class="easyui-datebox" size="14" data-options="editable : true"  
								/>
						</td>						   
					</tr> --%>
					<%-- <tr>	
						<td align="right">
							<label for="name">创建人</label>
						</td>	
						<td>
								<input type="text" id="createName" name="createName" value="${ysnews.createName}"/>												
						</td>						   
					</tr> --%>
					
					<%-- <tr>	
						<td align="right">
							<label for="name">修改日期</label>
						</td>	
						<td>
								<input type="text" 
								id="updateDate" 
								name="updateDate" 
								value="<fmt:formatDate value="${ysnews.updateDate}" pattern="yyyy-MM-dd"/>"
								class="easyui-datebox" size="14" data-options="editable : true"  
								/>
						</td>						   
					</tr> --%>
					<%-- <tr>	
						<td align="right">
							<label for="name">修改人</label>
						</td>	
						<td>
								<input type="text" id="updateName" name="updateName" value="${ysnews.updateName}"/>												
						</td>						   
					</tr> --%>
		
				<!-- <tr>
					<td colspan="2">
						<input id="submitButton" name="submitButton" type="button" onclick="ysnews_submit();"  value="提交" />	
					</td>
				<tr>	 -->		 
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