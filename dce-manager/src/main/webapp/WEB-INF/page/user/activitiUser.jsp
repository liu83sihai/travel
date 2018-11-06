<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户信息认证</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<style type="text/css">
.center{
  text-align: center;
  margin-top:5px;
}
</style>
</head>
<body>
	<input type="hidden" id="act_level_userId" value="${user.id }"/>
	<table style="margin-left: auto;margin-right: auto;">
		<tr>
			<td>真实姓名:</td>
			<td><input id="act_trueName" class="easyui-validatebox"
				type="text" name="trueName" value="${user.trueName }"
				style="height: 30px;" /></td>
		</tr>
		<tr>
			<td>手机号码:</td>
			<td><input id="act_user_mobile" class="easyui-validatebox"
				type="text" name="trueName" value="${user.mobile }"
				style="height: 30px;" /></td>
		</tr>
		<tr>
			<td>身份证号:</td>
			<td><input id="act_user_idnumber" class="easyui-validatebox"
				type="text" name="trueName" value="${user.idnumber }"
				style="height: 30px;" /></td>
		</tr>
		<tr>
			<td>银行卡号:</td>
			<td><input id="act_user_banknumber" class="easyui-validatebox"
				type="text" name="trueName" value="${user.banknumber }"
				style="height: 30px;" /></td>
		</tr>
		<tr>
			<td>银行卡开户行:</td>
			<td><input id="act_user_banktype" class="easyui-validatebox"
				type="text" name="trueName" value="${user.banktype }"
				style="height: 30px;" /></td>
		</tr>
		<tr>
			<td>用户性别:</td>
			<td><select class="easyui-combobox" id="act_change_sex"
				name="levelType" style="width: 140px;" style="height:30px;">
					<option value="">--请选择用户性别--</option>
					<option value="1"
						<c:if test="${user.sex==1 }">selected="selected"</c:if>>男</option>
					<option value="2"
						<c:if test="${user.sex==2 }">selected="selected"</c:if>>女</option>
			</select></td>
		</tr>
		<tr>
			<td>会员等级:</td>
			<td><select class="easyui-combobox" id="act_change_level"
				name="levelType" style="width: 140px;" style="height:30px;">
					<option value="">--请选择用户级别--</option>
					<option value="0"
						<c:if test="${user.userLevel==0 }">selected="selected"</c:if>>普通会员</option>
					<option value="1"
						<c:if test="${user.userLevel==1 }">selected="selected"</c:if>>VIP</option>
					<option value="2"
						<c:if test="${user.userLevel==2 }">selected="selected"</c:if>>商家</option>
					<option value="3"
						<c:if test="${user.userLevel==3 }">selected="selected"</c:if>>社区合伙人</option>
					<option value="4"
						<c:if test="${user.userLevel==4 }">selected="selected"</c:if>>城市合伙人</option>
					<option value="5"
						<c:if test="${user.userLevel==5 }">selected="selected"</c:if>>省级合伙人</option>
					<option value="6"
						<c:if test="${user.userLevel==6 }">selected="selected"</c:if>>股东</option>
			</select></td>
		</tr>
	</table>
    
</body>
</html>