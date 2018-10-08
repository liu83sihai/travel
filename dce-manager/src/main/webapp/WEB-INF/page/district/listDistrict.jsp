<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>区域代表</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/icons.css'/>" />
<script type="text/javascript" src="<c:url value='/js/common/js/jquery-easyui-1.4.1/extension/jquery-easyui-datagridview/datagrid-detailview.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/district/district.js?v='/>12121"></script>
<style type="text/css">
.tdfont{
	font-size: 12px;
}
</style>
</head>
<body class="easyui-layout">

<div id="body" region="center" > 
  <!-- 查询条件区域 -->
	<div id="search_areaDistrict"  class="easyui-panel" >
		<div id="conditon" >
			<form id="searchdistrictForm" style="margin-top:7px;margin-left:5px;" >
			      <table border="0">
			        <tr>
			        <td class="tdfont">区域:
			          	<input type="text" size="14" id="distrct_name" name="searchStr" placeholder="区域" >
			          </td>
			          <td class="tdfont">用户真实姓名:
			          	<input type="text" size="14" id="true_name" name="searchStr" placeholder="真实姓名" >
			          </td>
			          
			          <td >
			              <a  href="javascript:void(0)" id="searchButton" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a> 
			              <a  href="javascript:void(0)" id="resetButton" class="easyui-linkbutton" iconCls="icon-reset" plain="true" >重置</a>
				      </td>
			        </tr>
			      </table>
		     </form>
	     </div>
    	<span id="openOrClose"></span> 
	</div>
  
  	<!-- 数据表格区域 -->
  <div id="tt_District"></div>
  
</div>

  <div id="editDistrictDiv"></div>  
</body>

</html>