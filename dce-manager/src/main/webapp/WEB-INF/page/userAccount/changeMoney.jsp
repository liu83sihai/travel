<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<input type="hidden" id="change_money_userId" value="${user.id }"/>
	<input type="hidden" id="change_money_type" value="${type }"/>
	<table style="margin-left: auto;margin-right: auto;">
		<tr>
			<td>用户名:</td>
			<td><input id="name" class="easyui-validatebox" type="text" name="name" readonly="readonly" value="${user.userName }" style="height:30px;"/></td>
		</tr>
		<tr>
			<td>真实姓名:</td>
			<td><input id="nickName" class="easyui-validatebox" type="text" name="trueName" readonly="readonly" value="${user.trueName }" style="height:30px;"/></td>
		</tr>
		<tr>
			<td>账户类型:</td>
			<td>
				<select class="easyui-combobox" id="change_money_accountType" name="accountType" style="width:140px;" style="height:30px;">
		       		<option value="">--请选择账户类型--</option>
		       		<option value="wallet_money">账户余额 </option>
					<option value="wallet_travel">奖励旅游 </option>
					<option value="wallet_goods">奖励商品 </option>
					<option value="wallet_active">奖励活动 </option>
		       </select>
			</td>
		</tr>
		<tr>
			<td><c:if test="${type==0 }">划扣</c:if><c:if test="${type==1 }">充值</c:if></td>
			<td><input class="easyui-validatebox" type="text" name="qty" id="change_money_qty" style="height:30px;" /></td>
		</tr>
	</table>
    
</body>
</html>