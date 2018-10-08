function pwdCheckout(password){
	
	var lowercase = /[a-z]{1,}/;
	var capital = /[A-Z]{1,}/;
	var num = /[0-9]{1,}/;
	
	if(password.length <6){
		console.log("密码至少要有6位数");
		return "密码至少要有6位数";
	}
	
	if(!lowercase.test(password) || !capital.test(password) || !num.test(password)){
		console.log("密码必须同时包含大小写字母及数字");
		return "密码必须同时包含大小写字母及数字" ;
	}
	return "";
}
