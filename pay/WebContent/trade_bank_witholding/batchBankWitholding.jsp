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
    
    <title>快捷通批量银行卡代扣网关接口测试页面</title>
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
      <h4>快捷通批量银行卡代扣网关接口测试页面</h4>
      
      <span class="red">
   		使用步骤：<br>
   		1.点击[添加代扣信息]按钮将代扣列表参数放入代扣列表(withholding_list)参数中<br>
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
				<td><input type="text" id="service" name="service" value="batch_bank_witholding"></td>
				<td>(例如：batch_bank_witholding，非空)</td>
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
				<td width="15%">平台(商户)批次号:</td>
				<td width="15%"><input type="text" name="out_batch_no" value='<%=outTradeNo%>'></td>
				<td>(字母数字下划线，确保每批次唯一，非空)</td>
			</tr>
			<tr>
				<td>代扣总笔数:</td>
				<td><input type="text" name="withholding_num" value=""></td>
				<td>(每批次最多1000笔，非空)</td>
			</tr>
			<tr>
				<td>代扣总金额:</td>
				<td><input type="text" name="withholding_amount" value=""></td>
				<td>(15位以内最大保留2位精度数字，非空)</td>
			</tr>
			<tr>
				<td>代扣列表:</td>
				<td><input type="text" name="withholding_list" class="list" value=""></td>
				<td>(非空)</td>
			</tr>
			<tr>
				<td>业务产品码:</td>
				<td><input type="text" name="biz_product_code" value='20204'></td>
				<td>(取值参考接口文档，非空)</td>
			</tr>
			<tr>
				<td>备注:</td>
				<td><input type="text" name="memo" value=''></td>
				<td>(可空)</td>
			</tr>
		</table>
	</div>
	
	<div align="center"><hr>===我是分割线===<hr></div>
	
	<div class='detailInfoDiv'>
		<table border="1px;" cellspacing="0px;" cellpadding="0px;" width="100%">
			<tr>
				<th colspan="3">以下是代扣列表参数，该参数都会放入代扣列表(withholding_list)参数中<input type="button" id="addDetailInfoBtn" value="添加代扣信息" /></th>
			</tr>
			<tr>
				<td width="15%">平台(商户)订单号:</td>
				<td width="15%"><input type="text" name="out_trade_no" value='<%=(orderTime + def.format((int)(Math.random()*1000000%1000000)))%>'></td>
				<td>(字母数字下划线，确保每笔订单唯一，非空)</td>
			</tr>
			<tr>
				<td>银行卡账户名:</td>
				<td><input type="text" name="bank_account_name" value=""></td>
				<td>(不能包含数字，与协议号互斥)</td>
			</tr>
			<tr>
				<td>证件类型:</td>
				<td><input type="text" name="certificates_type" value="01"></td>
				<td>(01身份证,02军官证,03护照,04户口簿,05士兵证,06港澳来往内地通行证,07台湾同胞来往内地通行证,08临时身份证,09外国人居住证,10警官证,11营业执照,12组织机构代码证,13税务登记证,99其它，与协议号互斥)</td>
			</tr>
			<tr>
				<td>证件号码:</td>
				<td><input type="text" name="certificates_number" value=""></td>
				<td>(与证件类型匹配使用，与协议号互斥)</td>
			</tr>
			<tr>
				<td>银行卡号:</td>
				<td><input type="text" name="bank_card_no" value=""></td>
				<td>(字母数字，与协议号互斥)</td>
			</tr>
			<tr>
				<td>银行编码:</td>
				<td>
					<select id="bank_code" name="bank_code" class="bizContentParameter">
						<option selected="selected" value='ICBC'>工商银行</option>
						<option value='ABC'>农业银行</option>
						<option value='BOC'>中国银行</option>
						<option value='CCB'>建设银行</option>
						<option value='CMB'>招商银行</option>
						<option value='COMM'>交通银行</option>
						<option value='CITIC'>中信银行</option>
						<option value='PSBC'>中国邮储银行</option>
						<option value='CIB'>兴业银行</option>
						<option value='CMBC'>民生银行</option>
						<option value='SZPAB'>平安银行</option>
						<option value='SPDB'>浦发银行</option>
						<option value='CEB'>光大银行</option>
						<option value='HXB'>华夏银行</option>
						<option value='GDB'>广发银行</option>
						<option value='CBHB'>渤海银行</option>
						<option value=''>更多请参考接口文档</option>
					</select>
				</td>
				<td>(字母，与协议号互斥)</td>
			</tr>
			<tr>
				<td>协议号:</td>
				<td><input type="text" name="token_id" value=""></td>
				<td>(字母数字下划线，当协议号非空时，以协议号匹配的信息为准，与银行卡账户名、证件类型、证件号码、银行卡号、银行编码互斥)</td>
			</tr>
			<tr>
				<td>交易金额:</td>
				<td><input type="text" name="payable_amount" value='0.19'></td>
				<td>(15位以内最大保留2位精度数字，非空)</td>
			</tr>
			<tr>
				<td>币种:</td>
				<td><input type="text" name="currency" value=''></td>
				<td>(默认人民币CNY，可空)</td>
			</tr>
			<tr>
				<td>代扣授权号:</td>
				<td><input type="text" name="authorize_no" value=''></td>
				<td>(字母数字下划线，平台(商户)与客户签订的授权协议号，可空)</td>
			</tr>
			<tr>
				<td>入款快捷通会员标识类型:</td>
				<td><input type="text" name="payee_identity_type" value='1'></td>
				<td>(默认1,1-快捷通会员ID,2-快捷通会员登录号，可空)</td>
			</tr>
			<tr>
				<td>入款账号:</td>
				<td><input type="text" name="payee_identity" value='200000055673'></td>
				<td>(与入款快捷通会员标识类型配合使用，非空)</td>
			</tr>
			<tr>
				<td>支付产品码:</td>
				<td><input type="text" name="pay_product_code" value='61'></td>
				<td>(61-银行卡代扣-借记卡,62-银行卡代扣-信用卡,63-银行卡代扣-对公，非空)</td>
			</tr>
			<tr>
				<td>手机号码:</td>
				<td><input type="text" name="phone_num" value=''></td>
				<td>(可空)</td>
			</tr>
			<tr>
				<td>服务器异步通知地址:</td>
				<td><input type="text" name="notify_url" value='http://10.255.4.54:8002/AlphaTest/gateway/asyncNotify.do'></td>
				<td>(快捷通主动通知商户网站里指定的URL http/https路径，当订单完成后会回调商户并告知订单状态，可空)</td>
			</tr>
		</table>
	</div>
	
  </body>
</html>