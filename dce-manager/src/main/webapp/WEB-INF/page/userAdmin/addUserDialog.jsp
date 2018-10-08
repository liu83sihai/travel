<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>添加用户</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
</head>
<body>
<style type="text/css">
.center{
  text-align: center;
  margin-top:5px;
}
</style>
<input type="hidden" id="userId" value="${user.id }"/>
    <div class="center">
        <label for="name">账号:</label>
        <input id="name" class="easyui-validatebox" type="text" name="name"  value="${user.username }"/>
    </div>
    <div class="center" style="">
       <label for="nickName">昵称:</label>
       <input id="nickName" class="easyui-validatebox" type="text" name="nickName" value="${user.nickname }"/>
    </div>
    <div class="center">
       <label for="password">密码:</label>
       <input id="password" class="easyui-validatebox" type="password" name="password" />
    </div>
         <div class="center">
       <label for="mobile">手机:</label>
       <input id="mobile" class="easyui-validatebox" type="text" name="mobile" value="${user.mobile}"/>
    </div>
    <div class="center">
       <label for="remark">备注:</label>
       <input id="remark" class="easyui-validatebox" type="text" name="remark" />
    </div>
</body>
</html>