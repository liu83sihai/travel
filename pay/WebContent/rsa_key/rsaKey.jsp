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
    
    <title>快捷通RSA密钥生成页面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is test page">
	
	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/jquery.i18n.properties-min-1.0.9.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/rsaKey.js"></script>
	
	<link rel="stylesheet" type="text/css" href="css/demo.css" />
	<style type="text/css">
		.private_key{
		    width: 99%;
		    height: 300px;
		    font-size: 12px;
		    padding: 5px;
		    border: 1px solid #00cea1;
		    background: #ececec;
		}
		
		.public_key{
		    width: 99%;
		    height: 100px;
		    font-size: 12px;
		    padding: 5px;
		    border: 1px solid #00cea1;
		    background: #ececec;
		}
	</style>
	
	
  </head>
  <body>
    <h4>快捷通RSA密钥生成页面</h4>
    <span style="color: red;">注:PKCS#8格式一般为java语言格式,PKCS#1格式一般为其他语言格式</span>
    
	<div id='rsaDiv'>
		<table border="1px;" cellspacing="0px;" cellpadding="0px;" width="100%">
			<tr>
				<td colspan="2" align="center">
				<span>密钥格式:<select id='keyType' name='keyType'><option value="PKCS#8">PKCS#8</option><option value="PKCS#1">PKCS#1</option></select></span>
					<span>密钥长度:<select id='length' name='length'><option value="1024">1024</option><option value="2048">2048</option></select></span>
					<input type="button" id="genKeyPairBtn" value="RSA密钥生成" />
					<input type="button" id="verifyKeyPairBtn" value="RSA密钥校验" />
				</td>
			</tr>
			<tr>
				<td width="15%">公钥:</td>
				<td><textarea name="public_key" id="public_key" class="public_key"></textarea></td>
			</tr>
			<tr>
				<td>私钥:</td>
				<td><textarea name="private_key" id="private_key" class="private_key"></textarea></td>
			</tr>
		</table>
	</div>
  </body>
</html>