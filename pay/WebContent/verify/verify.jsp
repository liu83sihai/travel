<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,java.io.*,java.sql.*,javax.sql.*,javax.naming.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>快捷通商户验签页面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is test page">
	
	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/jquery.i18n.properties-min-1.0.9.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/demo.js"></script>
	
	<link rel="stylesheet" type="text/css" href="css/demo.css" />
	<style type="text/css">
		.verifyData{
		    width: 99%;
		    height: 300px;
		    font-size: 12px;
		    padding: 5px;
		    border: 1px solid #00cea1;
		    background: #ececec;
		}
	</style>
	
	
  </head>
  <body>
    <h4>快捷通商户验签页面</h4>
    <span style="color: red;">将快捷通同步返回值放入文本框中点提交按钮验签</span>
    
	<div id='rsaDiv'>
		<table border="1px;" cellspacing="0px;" cellpadding="0px;" width="100%">
			<tr>
				<td width="15%">快捷通返回值:</td>
				<td><textarea name="verifyData" id="verifyData" class="verifyData"></textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" id="verifyBtn" value="商户验签提交" />
				</td>
			</tr>
		</table>
	</div>
  </body>
</html>