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
		//var uniqueId = encodeURIComponent("xMcA3QMTeho=");
		//window.location.href="${basePath}/loanuser/toJCX.do?uniqueId=" + uniqueId;
		HHN.loading("跳转中");
		callInterface();
	});
	
	 /* function callInterface() {
		var gmapRes;
        try {
        	 HHN.webPopup(mapp);
             if ((typeof mapp === "undefined" ? "undefined" : _typeof(mapp)) == "object" && mapp && mapp.device && mapp.device['getSessionInfo']) {
            	 HHN.webPopup(1);
            	 mapp.device.getSessionInfo(function (data) {
            		HHN.webPopup(2);
            	    HHN.webPopup("回调结果：" + JSON.stringify(data));
                    gmapRes = JSON.stringify(data);
               });
             }
           HHN.webPopup(3); 
           gmapRes = encodeURIComponent('{\"sessionId\":\"DjBFiN-7LUw=\","projectCode\":\"44039999\"}');
           window.location.href="${basePath}/loanuser/toJCX.do?gmapRes=" + gmapRes;
        } catch (e) {
        	HHN.webPopup("异常:"+e);
            window.location.href="${customerwebdomain}";
        }
        
       // var uniqueId = encodeURIComponent("xMcA3QMTeho=");
		
    }  */
	
	
	
	 function callInterface() {
		var gmapRes;
        var index = 1;
        var key = setInterval(function () {
            if (index > 5) {
                clearInterval(key);
                return;
            }
            try {
                if ((typeof mapp === "undefined" ? "undefined" : typeof(mapp)) == "object" && mapp && mapp.device && mapp.device['getSessionInfo']) {
                	mapp.device.getSessionInfo(function (data) {
                		//alert(data);
                       // alert("回调结果：" + JSON.stringify(data));
                        gmapRes = JSON.stringify(data);
                        if(gmapRes){
                        	//alert("${basePath}/loanuser/toJCX.do?gmapRes=" + encodeURIComponent(gmapRes));
                        	//window.location.href="${customerwebdomain}";
                        	window.location.href="${basePath}/loanuser/toJCX.do?gmapRes=" + encodeURIComponent(gmapRes);
                        }
                    });
                    
                    clearInterval(key);
                    
                }
            } catch (e) {
                alert("异常:"+e);
            	window.location.href="${customerwebdomain}";
            }
            index++;
        }, 500);
    }
	
	
</script>

</html>