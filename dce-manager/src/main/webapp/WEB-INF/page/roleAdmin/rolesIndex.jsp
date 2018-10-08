<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加角色</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common.jsp"></jsp:include>
<script type="text/javascript" src="<c:url value='/js/sys/rolesIndex.js'/>"></script>
<style type="text/css">
    .tdfont {
        font-size: 12px;
    }
</style>
</head>
<body class="easyui-layout">
<!-- 查询条件区域 -->
<div id="search_area" class="easyui-panel" >
    <div id="conditon" >
        <form id="searchForm" style="margin-top:7px;margin-left:5px;">
            <table border="0">
                <tr>
                    <td class="tdfont">名字:
                        <input type="text" size="14" id="rolesName"></td>
                    <td colspan="2">
                        <a href="javascript:void(0)" id="searchButton" class="easyui-linkbutton" iconCls="icon-search"
                           plain="true">查询</a>
                        <a href="javascript:void(0)" id="resetButton" class="easyui-linkbutton" iconCls="icon-reset"
                           plain="true">重置</a>
                        <a href="javascript:void(0)" id="addButton" class="easyui-linkbutton" iconCls="icon-reset"
                           plain="true">增加</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <span id="openOrClose"></span>
    <input type="hidden" id="selectedRow" value=""/>
</div>
<div id="tableGrid"></div>
<div id="addDiv"></div>
<div id="setUserRolesDiv"></div>
<div id="setAuthorityDiv"></div>
<script type="text/javascript">

    /*查询和重置按钮事件*/
    $("#resetButton").on("click",function () {

        $("#searchForm").form('reset');
    });
    $("#searchForm #searchButton").on("click",function () {

        $("#tableGrid").datagrid('load',{
            'name':encodeURIComponent($("#searchForm #rolesName").val())
        });
    });
    $("#addButton").click(function () {

        window.roleIndex.editRole();
    })
</script>
</body>
</html>