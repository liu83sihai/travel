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
    
    <title>快捷通担保交易网关接口测试页面</title>
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
      <h4>快捷通担保交易网关接口测试页面</h4>
      
   	  <span class="red">
   		使用步骤：<br>
   		1.点击[添加交易信息]按钮将交易详细信息参数放入交易信息(trade_info)参数中<br>
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
				<td><input type="text" id="service" name="service" value="ensure_trade"></td>
				<td>(例如：ensure_trade，非空)</td>
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
				<td width="15%">买家标识类型:</td>
				<td width="15%"><input type="text" name="payer_identity_type" value="1"></td>
				<td>(买家快捷通会员标识类型，默认1，1-快捷通会员ID，2-快捷通会员登入号，可空)</td>
			</tr>
			<tr>
				<td>买家会员ID或登入账号:</td>
				<td><input type="text" name="payer_identity" value="anonymous"></td>
				<td>(如没有快捷通会员ID和登入账号，则填写固定值：anonymous，非空)</td>
			</tr>
			<tr>
				<td>买家平台类型:</td>
				<td><input type="text" name="payer_platform_type" value="1"></td>
				<td>(固定值：1，可空)</td>
			</tr>
			<tr>
				<td>买家公网IP地址:</td>
				<td><input type="text" name="payer_ip" value="122.224.203.210"></td>
				<td>(用户在商户平台下单时候的ip地址，非商户服务器的ip地址，公网IP,6-32位，非空)</td>
			</tr>
			<tr>
				<td>支付方式:</td>
				<td><input type="text" id="pay_method" name="pay_method" value=''></td>
				<td>(根据不同的业务场景选择合适的支付方式，可空)
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span>
						选择支付方式:
						<select id='payMethodSelect'>
							<option value=''>使用快捷通收银台进行支付</option>
							<option selected="selected" value='{"pay_product_code":"20","amount":"0.19","bank_code":"ABC"}'>直接跳转到目标银行网银页面支付(如跳转农行网银)</option>
							<option value='{"pay_product_code":"32","amount":"0.19","bank_code":"WECHAT"}'>微信PC端扫码支付</option>
							<option value='{"pay_product_code":"41","amount":"0.19","bank_code":"WECHATSMQ","std_auth_id":"123456789"}'>微信扫码枪支付</option>
							<option value='{"pay_product_code":"35","amount":"0.19","bank_code":"WECHATAPP"}'>微信APP(SDK)支付</option>
							<option value='{"pay_product_code":"34","amount":"0.19","bank_code":"WECHATH5","terminal_type":"IOS","app_name":"王者荣耀","bundle_id":"com.tencent.wzryIOS"}'>微信H5支付(IOS)</option>
							<option value='{"pay_product_code":"34","amount":"0.19","bank_code":"WECHATH5","terminal_type":"Android","app_name":"王者荣耀","package_name":"com.tencent.tmgp.sgame"}'>微信H5支付(Android)</option>
							<option value='{"pay_product_code":"34","amount":"0.19","bank_code":"WECHATH5","terminal_type":"WAP","wap_url":"https://m.jd.com","wap_name":"京东官网"}'>微信H5支付(WAP)</option>
							<option value='{"pay_product_code":"36","amount":"0.19","bank_code":"ALIPAY"}'>支付宝PC端扫码支付</option>
							<option value='{"pay_product_code":"42","amount":"0.19","bank_code":"ALIPAYSMQ","auth_code":"123456789"}'>支付宝扫码枪支付</option>
							<!-- <option value='{"pay_product_code":"01","amount":"0.19"}'>快捷通账户余额支付</option>
							<option value='{"pay_product_code":"06","amount":"0.19","bank_code":"JX","req_code":"HRY"}'>海融易会员基金支付</option> -->
						</select>
					</span>
				</td>
			</tr>
			<tr>
				<td>不期望使用的支付方式:</td>
				<td><input type="text" name="inexpectant_pay_product_code" value=''></td>
				<td>(多个用,隔开，可空)</td>
			</tr>
			<tr>
				<td>业务产品码:</td>
				<td><input type="text" name="biz_product_code" value='20202'></td>
				<td>(取值参考接口文档，非空)</td>
			</tr>
			<tr>
				<td>收银台类型:</td>
				<td><input type="text" name="cashier_type" value='WEB'></td>
				<td>(SDK-SDK收银台，WEB-WEB收银台，H5-H5收银台，非空)</td>
			</tr>
			<tr>
				<td>订单允许的最晚付款时间:</td>
				<td><input type="text" name="timeout_express" value=''></td>
				<td>(该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：10m～7d。m-分钟，h-小时，d-天。默认2h。该参数数值不接受小数点，如 1.5h，可转换为 90m，可空)</td>
			</tr>
			<tr>
				<td>交易信息:</td>
				<td><input type="text" id="trade_info" name="trade_info" value=''></td>
				<td>(非空)</td>
			</tr>
			<tr>
				<td>终端信息域:</td>
				<td><input type="text" name="terminal_info" value='{"terminal_type":"01","ip":"122.224.203.210"}'></td>
				<td>(存放终端类型(terminal_type)、ip(ip)等信息字段，非空)<br/>
					终端类型，标识发送支付指令的终端类型，00电脑，01手机，02平板设备，03可穿戴设备，04数字电视，99其他
				</td>
			</tr>
			<tr>
				<td>商户自定义域:</td>
				<td><input type="text" name="merchant_custom" value=''></td>
				<td>(商户自定义域：是否自动跳转收银台(go_cashier),默认Y，传N不跳转。商户业务分类(merchant_biz_type)、商户合并标记号（合并账单）(merchant_merge_flag)会在交易记录，统一账单、日账单体现商户特色业务。可空)</td>
			</tr>
			<tr>
				<td>返回页面路径:</td>
				<td><input type="text" id="return_url" name="return_url" value="http://122.224.203.210:18380/demo/instant_trade/instantReturnUrlResponse.jsp"></td>
				<td>(当前页面自动跳转到商户网站里指定页面的http/htts路径，可空)</td>
			</tr>
		</table>
	</div>
	
	<div align="center"><hr>===我是分割线===<hr></div>
	
	<div id='tradeInfoDiv'>
		<table border="1px;" cellspacing="0px;" cellpadding="0px;" width="100%">
			<tr>
				<th colspan="3">以下是交易详细信息参数，该参数都会放入交易信息(trade_info)参数中<input type="button" id="addTradeInfoBtn" value="添加交易信息" /></th>	
			</tr>
			<tr>
				<td width="15%">平台(商户)订单号:</td>
				<td width="15%"><input type="text" name="out_trade_no" value='<%=outTradeNo%>'></td>
				<td>(字母数字下划线，确保每笔订单唯一，非空)</td>
			</tr>
			<tr>
				<td>商品名称:</td>
				<td><input type="text" name="subject" value='苹果'></td>
				<td>(商品的标题/交易标题/订单标题/订单关键字等，非空)</td>
			</tr>
			<tr>
				<td>币种:</td>
				<td><input type="text" name="currency" value=''></td>
				<td>(默认人民币CNY，可空)</td>
			</tr>
			<tr>
				<td>商品单价:</td>
				<td><input type="text" name="price" value='0.19'></td>
				<td>(取值范围为[0.01，100000000000.00]，精确到小数点后两位，非空)</td>
			</tr>
			<tr>
				<td>商品数量:</td>
				<td><input type="text" name="quantity" value='1'></td>
				<td>(数字，非空)</td>
			</tr>
			<tr>
				<td>交易金额:</td>
				<td><input type="text" name="total_amount" value='0.19'></td>
				<td>(交易金额=(商品单价×商品数量)，非空)</td>
			</tr>
			<tr>
				<td>卖家标识类型:</td>
				<td><input type="text" name="payee_identity_type" value='1'></td>
				<td>(1-卖家会员ID，2-卖家登入账号，可空)</td>
			</tr>
			<tr>
				<td>卖家会员ID或登入账号:</td>
				<td><input type="text" name="payee_identity" value='200000055673'></td>
				<td>(非空)</td>
			</tr>
			<tr>
				<td>业务号:</td>
				<td><input type="text" name="biz_no" value=''></td>
				<td>(收支明细的备注，对账用，可空)</td>
			</tr>
			<tr>
				<td>商品展示URL:</td>
				<td><input type="text" name="show_url" value=''></td>
				<td>(收银台页面上，商品展示的超链接，可空)</td>
			</tr>
			<tr>
				<td>服务器异步通知地址:</td>
				<td><input type="text" name="notify_url" value='http://10.255.4.54:8002/AlphaTest/gateway/asyncNotify.do'></td>
				<td>(快捷通主动通知商户网站里指定的URL http/https路径，当订单完成后会回调商户并告知订单状态，可空)</td>
			</tr>
			<tr>
				<td>担保金额:</td>
				<td><input type="text" name="ensure_amount" value='0.19'></td>
				<td>(0&lt;担保金额&lt;=交易金额，非空)</td>
			</tr>
			<tr>
				<td>金币金额:</td>
				<td><input type="text" name="gold_coin" value=''></td>
				<td>(可空)</td>
			</tr>
			<tr>
				<td>使用订金金额:</td>
				<td><input type="text" name="deposit_amount" value=''></td>
				<td>(可空)</td>
			</tr>
			<tr>
				<td>订金下订的平台(商户)网站唯一订单号:</td>
				<td><input type="text" name="deposit_no" value=''></td>
				<td>(可空)</td>
			</tr>
			<tr>
				<td>交易扩展参数:</td>
				<td><input type="text" name="trade_ext" value=''></td>
				<td>(可空)</td>
			</tr>
		</table>
	</div>
	
  </body>
</html>