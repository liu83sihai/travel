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
	<form id="editGoodsForm" method="post" enctype="multipart/form-data"
		action="<c:url value='goods/saveGoods.html'/>">
		<div>

			<table width="100%" border="0" align="center" cellpadding="3">
				<input type="hidden" id="goodsId" name="goodsId"
					value="${goods.goodsId}" />

				<tr>
					<td align="right"><label for="name">商品名称：</label></td>
					<td><input type="text" name="title" id="title"
						value="${goods.title}" /></td>
				</tr>
				<tr>
					<td align="right"><label for="name">商品图片地址：</label></td>
					<td><input type="file" id="goodsImg" name="goodsImg"/></td>
				</tr>
				<tr>
					<td align="right"><label for="name">商品单位：</label></td>
					<td><input type="text" id="goodsUnit" name="goodsUnit"
						value="${goods.goodsUnit}" /></td>
				</tr>

				<tr>
					<td align="right"><label for="name">商品价格：</label></td>
					<td><input type="text" id="shopPrice" name="shopPrice"
						value="${goods.shopPrice}" /></td>
				</tr>
				<tr>
					<td align="right"><label for="name">商品内容：</label></td>
					<td><input type="text" id="goodsDesc" name="goodsDesc"
						value="${goods.goodsDesc}" /></td>
				</tr>

				<tr>
					<td align="right"><label for="name">商品上架状态·：</label></td>
					<td><select id="status" class="easyui-combobox"
						style="width: 150px;">
							<option value="0"
								<c:if test="${goods.status==0}">selected="selected"</c:if>>未上架</option>
							<option value="1"
								<c:if test="${goods.status==1}">selected="selected"</c:if>>已上架</option>
					</select></td>
				</tr>
			</table>
		</div>
	</form>

	<script type="text/javascript">
		function goods_submit() {
			$.ajax({
				url : "<c:url value='/goods/saveGoods.html'/>",
				data : $("#editGoodsForm").serialize(),
				type : "post",
				dataType : "json",
				success : function(ret) {
					if (ret.code === "0") {
						$.messager.confirm("保存成功", '是否继续添加？', function(r) {
							if (r == false) {
								$("#editGoodsDiv").dialog("close");
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
		<c:if test="${not empty goods.goodsImg }">
			<img style="width: 300px; height: 300px; align: center" id="img"
				src="${goods.goodsImg }">
		</c:if>
	</div>
</body>

</html>