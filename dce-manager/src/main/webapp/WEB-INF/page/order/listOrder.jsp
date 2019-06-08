<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>订单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/icons.css'/>" />
<script type="text/javascript"
	src="<c:url value='/js/common/js/jquery-easyui-1.4.1/extension/jquery-easyui-datagridview/datagrid-detailview.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/order/order.js?v='/>"></script>
<script type="text/javascript"
	src="<c:url value='/js/common/formatter.js?v=${jsversion}'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.flexslider-min.js'/>"></script>

<style type="text/css">
.tdfont {
	font-size: 12px;
}
</style>
</head>
<body class="easyui-layout">

	<div id="body" region="center">
		<!-- 查询条件区域 -->
		<div id="search_areaOrder" class="easyui-panel">
			<div id="conditon">
				<form id="searchorderForm"
					style="margin-top: 7px; margin-left: 5px;">
					<table border="0">
						<tr>
							<td class="tdfont">用户名:<input type="text" size="14"
								id="userName" name="userName" placeholder="用户名"></td>
							<td class="tdfont" colspan="2">创建时间: <input type="text"
								id="startDate" name="startDate" class="easyui-datetimebox"
								size="14" placeholder="开始时间" data-options="editable : true" />-
								<input type="text" id="endDate" name="endDate"
								class="easyui-datetimebox" size="14" placeholder="结束时间"
								data-options="editable : true" />
							</td>
							<td class="tdfont">是否已发货: 
							<select id="orderStatus" class="easyui-combobox" style="width: 90px; cursor: pointer;">
									<option value="">全部</option>
									<option value="0">未发货</option>
									<option value="1">已发货</option>
							</select>
							</td>
							<td class="tdfont">支付状态: 
								<select id="payStatus" class="easyui-combobox" style="width: 90px; cursor: pointer;">
										<option value="">全部</option>
										<option value="0">未完成支付</option>
										<option value="1">已支付成功</option>
								</select>
							</td>
							<td><a href="javascript:void(0)" id="searchButton"
								class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
								<a href="javascript:void(0)" id="resetButton"
								class="easyui-linkbutton" iconCls="icon-reset" plain="true">重置</a>
							</td>
						</tr>
					</table>
					<a href="javascript:void(0);" class="easyui-linkbutton" id="excel"
						iconCls="icon-print" onclick="export_excel()">导出excel</a>

				</form>
			</div>
			<span id="openOrClose"></span>
		</div>

		<!-- 数据表格区域 -->
		<div id="tt_Order"></div>

	</div>

	<div id="editOrderDiv"></div>
</body>

</html>