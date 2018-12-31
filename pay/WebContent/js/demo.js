$(function() {
	/**
	 * 添加分账信息
	 */
	$('#addRoyaltyBtn').click(function(){
		
		var royaltyInfo = {};
		royaltyInfo.payee_identity_type = $('#royaltyDiv input[name="payee_identity_type"]').val();
		royaltyInfo.payee_member_id = $('#royaltyDiv input[name="payee_member_id"]').val();
		royaltyInfo.payee_account_no = $('#royaltyDiv input[name="payee_account_no"]').val();
		royaltyInfo.amount = $('#royaltyDiv input[name="amount"]').val();
		
		var royaltyInfoArray = [];
		var royaltyInfoStr = $('#royalty_info').val();
		if(!(royaltyInfoStr==null||royaltyInfoStr==undefined||royaltyInfoStr=="")){
			royaltyInfoArray = eval('('+ royaltyInfoStr +')');
		}
		royaltyInfoArray.push(royaltyInfo);
		
		$('#royalty_info').val(JSON.stringify(royaltyInfoArray));
	});
	
	/**
	 * 添加交易信息
	 */
	$('#addTradeInfoBtn').click(function(){
		
		var tradeInfo = {};
		var inputTexts = $('#tradeInfoDiv input[type="text"]');
		
		for(var i=0; i<inputTexts.length; i++){
			var name = inputTexts[i].name;
			$(tradeInfo).attr(name, inputTexts[i].value);
		}
		
		$('#trade_info').val(JSON.stringify(tradeInfo));
	});
	
	/**
	 * 添加批量信息
	 */
	$('#addDetailInfoBtn').click(function(){
		
		var tradeInfo = {};
		var inputTexts = $('.detailInfoDiv input[type="text"], .detailInfoDiv .bizContentParameter');
		
		for(var i=0; i<inputTexts.length; i++){
			var name = inputTexts[i].name;
			$(tradeInfo).attr(name, inputTexts[i].value);
		}
		
		var infoArray = [];
		var infoStr = $('#bizContentDiv .list').val();
		if(!(infoStr==null||infoStr==undefined||infoStr=="")){
			infoArray = eval('('+ infoStr +')');
		}
		infoArray.push(tradeInfo);
		
		
		$('#bizContentDiv .list').val(JSON.stringify(infoArray));
	});
	
	/**
	 * 加密业务参数
	 */
	$('#encryptBtn').click(function(){
		
		var bizContent = {};
		var inputTexts = $('#bizContentDiv input[type="text"], #bizContentDiv .bizContentParameter');
		
		for(var i=0; i<inputTexts.length; i++){
			var name = inputTexts[i].name;
			$(bizContent).attr(name, inputTexts[i].value);
		}
		
		$.post(fillPath('encrypt.do'),
				{ 'req' : JSON.stringify(bizContent), "charset": $('#charset').val(), "service": $('#service').val(), "sign_type": $('#sign_type').val() },
		function(result){
			$('#biz_content').val(result);
		});
	});
	
	/**
	 * 对请求参数加签
	 */
	$('#signBtn').click(function(){
		
		var form = {};
		var inputTexts = $('#form input[type="text"]');
		
		for(var i=0; i<inputTexts.length; i++){
			var name = inputTexts[i].name;
			$(form).attr(name, inputTexts[i].value);
		}
		
		//提交到后台加签
		$.post(fillPath('sign.do'),
				{"signData":JSON.stringify(form)},
		function(result){
			$('#sign').val(result);
		});
		
	});
	
	
	/**
	 * 编码
	 */
	var encodeURL = function (encodeString){
		if(!(encodeString==null||encodeString==undefined||encodeString=="")){
			return encodeURIComponent(encodeString);
		}
	};
	
	
	/**
	 * 提交支付
	 */
	$('#submitBtn').click(function(){
		
		//1.加签，同步请求保证签名设置到form中
		var form = {};
		var inputTexts = $('#form input[type="text"]');
		
		for(var i=0; i<inputTexts.length; i++){
			var name = inputTexts[i].name;
			$(form).attr(name, inputTexts[i].value);
		}
		
		$.ajax({
	         type : "post",  
	         url : fillPath('sign.do'),  
	         data : {"signData": JSON.stringify(form) },
	         async : false,  
	         success : function(result){  
	        	 $('#sign').val(result);
	        	 
	        	//2.编码
	        	 var inputTexts = $('#form input[type="text"]');
	        	 for(var i=0; i<inputTexts.length; i++){
	     			$(inputTexts[i]).val(encodeURL( inputTexts[i].value));
	     		}
	        	 
	        	 //3.提交
	  			 $('#form').submit();
	         }  
	     });
	});
	
	
	/**
	 * 商户验签提交
	 */
	$('#verifyBtn').click(function(){
		$.ajax({
	         type : "post",  
	         url : fillPath('verify.do'),  
	         data : {"verifyData": $('#verifyData').val() },
	         async : false,  
	         success : function(result){  
	        	 
	        	 alert(result);
	         }  
	     });
	});
	
	
	/**
	 * 支付方式选择填充
	 */
	$('#payMethodSelect').change(function(){
		$('#pay_method').val(this.value);
	});
	
	/**
	 * 初始化支付方式输入框值
	 */
	$('#pay_method').ready(function(){
		$('#pay_method').val($('#payMethodSelect').val());
	});
	
});