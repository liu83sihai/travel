<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	<form id="editYsNoticeForm" method="post" enctype="multipart/form-data"
		action="<c:url value='ysnotice/saveYsNotice.html'/>">
		<div>

			<table width="100%" border="0" align="center" cellpadding="3">
				<input type="hidden" id="id" name="id" value="${ysnotice.id}" />
				<tr>
					<td align="right"><label for="name">标题</label></td>
					<td><input type="text" id="title" name="title"
						value="${ysnotice.title}" /></td>
				</tr>
			<!-- 	<tr>
					<td align="right"><label for="name">图片</label></td>
					<td><input type="file" id="image" /></td>
				</tr> -->
				<tr>
					<td align="right"><label for="name">内容</label></td>
					<td><textarea rows="3" cols="20" id="content"
							form="editYsNoticeForms">${ysnotice.content}</textarea></td>
				</tr>
				<tr>
					<td align="right"><label for="name">是否置顶</label></td>
					<td><select id="topNotice" class="easyui-combobox"
						style="width: 150px;">
							<option value="1"
								<c:if test="${ysnotice.topNotice==1}">selected="selected"</c:if>>不置顶</option>
							<option value="0"
								<c:if test="${ysnotice.topNotice==0}">selected="selected"</c:if>>置顶</option>
					</select></td>
				</tr>
				<tr>
					<td align="right"><label for="name">备注</label></td>
					<td><input type="text" id="remark" name="remark"
						value="${ysnotice.remark}" /></td>
				</tr>
			</table>
		</div>
	</form>
	<script type="text/javascript">
		function ysnotice_submit() {
			$.ajax({
				url : "<c:url value='/ysnotice/saveYsNotice.html'/>",
				data : $("#editYsNoticeForm").serialize(),
				type : "post",
				dataType : "json",
				success : function(ret) {
					if (ret.code === "0") {
						$.messager.confirm("保存成功", '是否继续添加？', function(r) {
							if (r == false) {
								$("#editYsNoticeDiv").dialog("close");
							}
						});
					} else {
						$.messager.alert("error", ret.msg);
					}
				}
			});
		}
	</script>
	<div align="center">
	 <c:if test="${not empty ysnotice.image }">
			<img style="width: 300px; height: 300px; align: center" id="img"
				src="${ysnotice.image }">
		</c:if>
	</div>
</body>

</html>