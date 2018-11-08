<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<title>首页</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css_v1/public.css?v=${cssversion}">
	<link rel="stylesheet" type="text/css" href="http://rec.gemlends.com/jd/css_v1/modal.css?v=${cssversion}" />
</head>
<body>
</body>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/jquery.min.js?v=${jsversion}"></script>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/flexslider.min.js?v=${jsversion}"></script>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/global.js?v=${jsversion}"></script>
<script type="text/javascript" src="http://rec.gemlends.com/jd/js/modal.js?v=${jsversion}"></script>
<script type="text/javascript">

	$(function() {
		HHN.loading("登录中");
		//addMenu("getCredit", "menu1", "icon-no1", "获取额度");
		var isLogin = "${isLogin}";
		//alert(isLogin);
		if(isLogin=="1"){
			var mobile = "${mobile}";
			var psw = "${psw}";
	        var param = {"mobile":mobile,"password":psw,"fromUrl":"${customerwebdomain}","pageId":"userlogin","isWuYe":"true"};
	        submitLogin(param);
		}else{
			window.location.href="${customerwebdomain}";
		}
	});
	
	function submitLogin(param){
	  	$.post("${basePath}/loanuser/login.do", param, function(data) {
			if(data.success && data.resultCode == '0'){
				console.log(1);
				//HHN.popup(data.resultMessage);
				//window.location.href=data.model;
				window.location.href=data.model.fromUrl;
			}else{
				console.log(2);
				window.location.href="${customerwebdomain}";
				//HHN.popup(data.resultMessage);
			}
		},"json");
	  }

	


</script>

	
	<!-- <script type="text/javascript">
    function callInterface() {
        var index = 1;
        var key = setInterval(function () {
            if (index > 5) {
                clearInterval(key);
                return;
            }
            try {
                if ((typeof mapp === "undefined" ? "undefined" : _typeof(mapp)) == "object" && mapp && mapp.device && mapp.device['getSessionInfo']) {
                    app.device.getSessionInfo(function (data) {
                        alert("回调结果：" + JSON.stringify(data));
                    });
                    clearInterval(key);
                }
            } catch (e) {
                //alert("异常:"+e);
            }
            index++;
        }, 500);
    }
    callInterface();
</script> -->

</html>