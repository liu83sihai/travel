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
    
    <title>快捷通交易退款网关接口测试页面</title>
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
  </head>
  <body>
      <h4>快捷通交易退款网关接口测试页面</h4>
      
      <span class="red">
   		使用步骤：<br>
   		1.如有分账信息，点击[添加分账信息]按钮将分润账号集列表参数放入分润账号集列表(royalty_info)参数中<br>
   		2.点击[加密业务参数并设置到请求参数集合中]按钮将业务参数加密后放入请求参数集合(biz_content)参数中<br>
   		3.点击[提交支付]按钮将请求参数提交到快捷通网关
   	  </span>
      
      <div>
      <form id="form" action="https://zgateway.kjtpay.com/recv.do" method="post">
		
		<%		
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		String orderTime = format.format(new java.util.Date());
		java.text.DecimalFormat def = new java.text.DecimalFormat("000000");
		int b=(int)(Math.random()*1000000%1000000);//产生0-1000000的整数随机数
		String lstStr = def.format(b);
		String requestNo = orderTime + lstStr;
		String outTradeNo = orderTime + lstStr;
		
		java.text.SimpleDateFormat timestampFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timestamp = timestampFormat.format(new java.util.Date());
		%>
		<table border="1px;" cellspacing="0px;" cellpadding="0px;" width="100%">
			<tr>
				<th colspan="3">以下是请求公共参数，点[提交支付]按钮后将提交到快捷通网关</th>	
			</tr>
			<tr>
				<td width="15%">商户网站请求号:</td>
				<td width="15%"><input type="text" name="request_no" value="<%=requestNo%>"></td>
				<td>(请求号，字母数字下划线，确保每次请求唯一，非空)</td>
			</tr>
			<tr>
				<td>接口名称:</td>
				<td><input type="text" id="service" name="service" value="trade_refund"></td>
				<td>(例如：trade_refund，非空)</td>
			</tr>
			<tr>
				<td>接口版本:</td>
				<td><input type="text" name="version" value="1.0"></td>
				<td>(目前版本：1.0，非空)</td>
			</tr>
			<tr>
				<td>合作者身份ID:</td>
				<td><input type="text" name="partner_id" value="200000055673"></td>
				<td>(平台或商户ID，商户直接接入快捷通的填商户ID，非空)</td>
			</tr>
			<tr>
				<td>字符集:</td>
				<td><input type="text" id='charset' name="charset" value="UTF-8"></td>
				<td>(类型有：UTF-8;GBK，非空)</td>
			</tr>
			<tr>
				<td>签名方式:</td>
				<td><input type="text" id="sign_type" name="sign_type" value="ITRUS"></td>
				<td>(签名方式只支持ITRUS/RSA，非空)</td>
			</tr>
			<tr>
				<td>签名:</td>
				<td><input type="text" id="sign" name="sign" value=""></td>
				<td>(请求参数签名，非空)<span class="red">对请求公共参数签名,签名结果放入[签名]中,post请求前对所有请求公共参数做urlEncode编码</span></td>
			</tr>
			<tr>
				<td>请求时间:</td>
				<td><input type="text" name="timestamp" value="<%=timestamp%>"></td>
				<td>(格式"yyyy-MM-dd HH:mm:ss"，如2017-06-24 13:07:56，请求时间必须在10分钟内，非空)</td>
			</tr>
			<tr>
				<td>请求参数集合支持的格式:</td>
				<td><input type="text" name="format" value="JSON"></td>
				<td>(业务请求参数集合支持的格式，仅支持JSON，非空)</td>
			</tr>
			<tr>
				<td>请求参数集合:</td>
				<td><input type="text" id="biz_content" name="biz_content" value=""></td>
				<td>(除公共请求参数外所有业务请求参数都必须放在这个参数中传递，非空)</td>
			</tr>
			
			
			<tr>
				<td colspan="3"><input type="button" id="submitBtn" value="提交支付" /></td>
			</tr>
		</table>	
    </form>
    </div>
    
    <div align="center"><hr>===我是分割线===<hr></div>
    
	<div id='bizContentDiv'>
		<table border="1px;" cellspacing="0px;" cellpadding="0px;" width="100%">
			<tr>
				<th colspan="3">以下是业务参数，业务参数都会加密后放入请求参数集合(biz_content)参数中<input type="button" id="encryptBtn" value="加密业务参数并设置到请求参数集合中" /></th>	
			</tr>
			<tr>
				<td width="15%">平台(商户)退款订单号:</td>
				<td width="15%"><input type="text" name="out_trade_no" value='<%=outTradeNo%>'></td>
				<td>(字母数字下划线，确保每笔订单唯一，非空)</td>
			</tr>
			<tr>
				<td>平台(商户)原始订单号:</td>
				<td><input type="text" name="orig_out_trade_no" value=""></td>
				<td>(快捷通合作平台(商户) 原始的网站唯一订单号，非空)</td>
			</tr>
			<tr>
				<td>退款金额:</td>
				<td><input type="text" name="refund_amount" value="0.19"></td>
				<td>(15位以内最大保留2位精度数字，支持部分退款，退款金额不能大于交易金额。如：50.00，非空)</td>
			</tr>
			<tr>
				<td>币种:</td>
				<td><input type="text" name="currency" value=''></td>
				<td>(默认人民币CNY，可空)</td>
			</tr>
			<tr>
				<td>退款订金金额:</td>
				<td><input type="text" name="deposit_amount" value=''></td>
				<td>(金额保留字段，可空)</td>
			</tr>
			<tr>
				<td>退款担保金额:</td>
				<td><input type="text" name="ensure_amount" value=''></td>
				<td>(金额保留字段，可空)</td>
			</tr>
			<tr>
				<td>退款金币金额:</td>
				<td><input type="text" name="gold_coin" value=''></td>
				<td>(金额保留字段，可空)</td>
			</tr>
			<tr>
				<td>服务器异步通知地址:</td>
				<td><input type="text" name="notify_url" value='http://10.255.4.54:8002/AlphaTest/gateway/asyncNotify.do'></td>
				<td>(快捷通主动通知商户网站里指定的URL http/https路径，当订单完成后会回调商户并告知订单状态，可空)</td>
			</tr>
			<tr>
				<td>分润账号集列表:</td>
				<td><input type="text" id='royalty_info' name="royalty_info" value=''></td>
				<td>(最多支持10个分润账号，可空)</td>
			</tr>
		</table>
	</div>
	
	<div align="center"><hr>===我是分割线===<hr></div>
	
	<div id='royaltyDiv'>
		<table border="1px;" cellspacing="0px;" cellpadding="0px;" width="100%">
			<tr>
				<th colspan="3">以下是分润账号集列表参数，该参数都会放入分润账号集列表(royalty_info)参数中<input type="button" id="addRoyaltyBtn" value="添加分账信息" /></th>	
			</tr>
			<tr>
				<td width="15%">分账标识类型:</td>
				<td width="15%"><input type="text" name="payee_identity_type" value='1'></td>
				<td>(分账标识类型，可空，默认1 1-快捷通会员ID 2-快捷通会员登录号)</td>
			</tr>
			<tr>
				<td>分账收款方会员ID:</td>
				<td><input type="text" name="payee_member_id" value=''></td>
				<td>(当分润账号集列表不空时非空)</td>
			</tr>
			<tr>
				<td>分账收款方账户号:</td>
				<td><input type="text" name="payee_account_no" value=''></td>
				<td>(当分润账号集列表不空时非空)</td>
			</tr>
			<tr>
				<td>分账金额:</td>
				<td><input type="text" name="amount" value=''></td>
				<td>(当分润账号集列表不空时非空)</td>
			</tr>
		</table>
	</div>
  </body>
</html>