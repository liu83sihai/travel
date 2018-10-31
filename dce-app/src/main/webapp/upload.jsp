<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>    
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>文件上传</title>
	<script type="text/javascript"  src="${ctx}/js/jquery-1.9.1.min.js"></script>	
	<script type="text/javascript">
 
    //通过FileReader.readAsDataURL获取base64数据
    function uploadFile(file){
        var f = document.getElementById("myImage").files[0];
        var reader = new FileReader(); //新建一个FileReader
        reader.readAsDataURL(f);  // 读取文件base64数据
        reader.onload = function(e){ //数据读取完成产生onload事件
            var data = e.target.result; //获取数据
            if (data.lastIndexOf('data:base64') != -1) {
                  data = data.replace('data:base64', 'data:image/jpeg;base64');
            } else if (data.lastIndexOf('data:,') != -1) {
                  data = data.replace('data:,', 'data:image/jpeg;base64,');
            }
 
            if(isCanvasSupported()){
                 ajaxUploadBase64File(data);
            }else{
                 alert("您的浏览器不支持");
            }
 
        };
 
        reader.onerror = function(){
            console.log("上传失败了 ");
        }
 
        
    }
 
    //ajax异步上传
    function ajaxUploadBase64File(base64Data){
    	console.log(base64Data);
        var url = "${ctx}/commonIntf/uploadImg.do";
        $.ajax({
            url:url,
            type:"post",
            data:{fileData:base64Data,fileName:'a.png'},
            success:function(data){
                   alert("上传成功");
            },
            error:function(){
                alert("上传失败");
            }
        });
    }; 
 
 
    //是否支持canvas
    function isCanvasSupported(){
        var elem = document.createElement('canvas');
        return !!(elem.getContext && elem.getContext('2d'));
    };
	   
	</script>
 
</head>
 
<body>
    <input type="file" id="myImage" name="myImage"/> 
    <input type="button" onclick="uploadFile();" value="上传">
</body>
</html>