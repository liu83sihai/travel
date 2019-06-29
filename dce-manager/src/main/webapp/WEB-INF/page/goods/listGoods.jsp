<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>

<link type="text/css" rel="stylesheet"
	href="<c:url value='/css/flexslider.css'/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/icons.css'/>" />
<script type="text/javascript"
	src="<c:url value='/js/common/js/jquery-easyui-1.4.1/extension/jquery-easyui-datagridview/datagrid-detailview.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/goods/goods.js?v=${jsversion}29'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/jquery.flexslider-min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/common/formatter.js?v=${jsversion}'/>"></script>

<style type="text/css">
.tdfont {
	font-size: 12px;
}
</style>
</head>
<body class="easyui-layout">

	<div id="body" region="center">
		<!-- 查询条件区域 -->
		<div id="search_areaGoods" class="easyui-panel">
			<div id="conditon">
				<form id="searchgoodsForm"
					style="margin-top: 7px; margin-left: 5px;">
					<table border="0">
						<tr>
							<td class="tdfont">查询条件: <input type="text" size="14"
								id="title" name="title" placeholder="商品名称">
								 <input type="hidden" id="searchGoodsFlag" name="searchGoodsFlag" value="${searchGoodsFlag }" >
							<td class="tdfont" colspan="2">创建时间: <input type="text"
								id="startDate" name="startDate" class="easyui-datetimebox"
								size="14" placeholder="开始时间" data-options="editable : true" />-
								<input type="text" id="endDate" name="endDate"
								class="easyui-datetimebox" size="14" placeholder="结束时间"
								data-options="editable : true" />
							</td>
							<td><a href="javascript:void(0)" id="searchButton"
								class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
								<a href="javascript:void(0)" id="resetButton"
								class="easyui-linkbutton" iconCls="icon-reset" plain="true">重置</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<span id="openOrClose"></span>
		</div>

		<!-- 数据表格区域 -->
		<div id="tt_Goods"></div>

	</div>

	<div id="editGoodsDiv"></div>


</body>

</html>