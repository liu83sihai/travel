<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>用户所有角色</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Cache-Control" content="no-cache"/>
</head>
<body>
<input type="hidden" id="userId" value="${user.id }"/>
<div  id="userRolesGrid"/>
<div data-options="region:'north'" style="height:20px;margin-left: 10px;color:red;margin-top: 10px">
    <label>用户：</label>${user.username}
</div>
<script type="text/javascript">
var basePath="/dce-manager";
    var userId = $('#userId').val();
    $("#userRolesGrid").datagrid({
        url: basePath + "/homemng/menuAdmin/getUserAllRoles.action?userId=" + userId,
        height: 200,
        pagination: false,
        fitColumns: true,
        rownumbers: true,
        singleSelect: true,
        autoRowHeight: true,
        striped: true,
        idField: "id",
        columns: [[
            {field: "name", title: "角色名", width: 80, align: "center"},
            {field: "roleDesc", title: "昵称", width: 80, align: "center"}
        ]]
    });
</script>
</body>
</html>