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
	<form id="editPathForm" method="post">
		<div>

			<table width="100%" border="0" align="center" cellpadding="3">
				<input type="hidden" id="pathid" name="pathid" value="${path.pathid}" />
				<tr>
					<td align="right"><label for="name">路线名称</label></td>
					<td><input type="text" id="linename"
						value="${path.linename}" /></td>
				</tr>
				<tr>
					<td align="right"><label for="name">状态</label></td>
					<td>
						<!--  <input type="text" id="state" name="state" value="${path.state}"/> -->
						<select id="state" class="easyui-combobox" name="state"
						style="width: 150px;">
								<option value="0" <c:if test="${path.state==0}">selected="selected"</c:if>>已开发</option>
								<option value="1" <c:if test="${path.state==1}">selected="selected"</c:if>>马上推出</option>
								<option value="2" <c:if test="${path.state==2}">selected="selected"</c:if>>正在开发</option>
						
					</select>
					</td>
				</tr>
				<tr>
					<td align="right"><label for="name">备注</label></td>
					<td><input type="text" id="remake" name="remake"
						value="${path.remake}" /></td>
				</tr>

			</table>
		</div>
	</form>
	<script type="text/javascript">
		 function path_submit() {
			$.ajax({
				url : "<c:url value='/path/savePath.html'/>",
				data : $("#editPathForm").serialize(),
				type : "post",
				dataType : "json",
				success : function(ret) {
					if (ret.code === "0") {
						$.messager.confirm("保存成功", '是否继续添加？', function(r) {
							if (r == false) {
								$("#editPathDiv").dialog("close");
							}
						});
					} else {
						$.messager.alert("error", ret.msg);
					}
				}
			});
		} 
	</script>

</body>

</html>