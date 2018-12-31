$(function() {
	
	/**
	 * RSA密钥生成
	 */
	$('#genKeyPairBtn').click(function(){
		
		var keyType = $('#keyType').val();
		var length = $('#length').val();
		
		$.post(fillPath('genKeyPair.do'),
			{'keyType':keyType , 'length':length },
			function(result){
				$('#public_key').val(result.publicKey);
				$('#private_key').val(result.privateKey);
			}, "json");
	});
	
	/**
	 * RSA密钥校验
	 */
	$('#verifyKeyPairBtn').click(function(){
		
		var publicKey = $('#public_key').val();
		var privateKey = $('#private_key').val();
		
		if((publicKey==null||publicKey==undefined||publicKey=="")
			|| (privateKey==null||privateKey==undefined||privateKey=="")){
			
			alert('公钥和私钥都不能为空，请先输入公钥和私钥');
			return;
		}
		
		var keyPair = {};
		$(keyPair).attr('publicKey', publicKey);
		$(keyPair).attr('privateKey', privateKey);
//		$(keyPair).attr('keyType', $('#keyType').val());
//		$(keyPair).attr('length', $('#length').val());
		
		$.post(fillPath('verifyKeyPair.do'),
			{'keyPair' : JSON.stringify(keyPair)},
			function(result){
				alert(result);
			});
	});
	
});