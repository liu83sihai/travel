<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>编辑字典</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
</head>
<body>

<!-- 内容 -->
<form id="editAboutusForm" method="post" action="<c:url value='aboutus/saveAboutus.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="id" name="id" value="${aboutus.id}"/>
					<tr>	
						<td align="right">
							<label for="name">url</label>
						</td>	
						<td>
							<input type="text" id="url" name="url" value="${aboutus.url}"/>												
						</td>						   
					</tr>
					
					<tr>
						<td align="right"><label for="name">关于我们的解释：</label></td>
						<td>
							<input type="text" name="summarry" id="summarry" value="${aboutus.summarry}" />
						</td>
					</tr>
					
					<tr>
						<td align="right"><label for="name">banner图：</label></td>
						<td>
							<input type="file" id="aboutusBannerObj" name="aboutusBannerObj"/>
							<input type="button" value="上传" id="btn_upload" onclick="ajaxFileUpload('aboutusBannerObj','aboutusBanner');" />
							<input type="hidden" id="aboutusBanner" name="aboutusBanner" value="${aboutus.aboutusBanner}"/>
							<input type="button" onclick="cleanImg('aboutusBanner');" value="清除原图"/>
						</td>
					</tr>
					
					<tr>
						<td align="right"><label for="name">详情图：</label></td>
						<td>
							<input type="file" id="detailImgFileObj" name="detailImgFileObj"/>
							<input type="button" value="上传" id="btn_upload" onclick="ajaxFileUpload('detailImgFileObj','detailImg');" />
							<input type="hidden" id="detailImg" name="detailImg" value="${aboutus.detailImg}"/>
							<input type="button" onclick="cleanImg('detailImg');" value="清除原图"/>
						</td>
					</tr>
				
		
				<tr>
					<td colspan="2">
					</td>
				<tr>			 
			</table>	   
		</div>	
	</form>

<script type="text/javascript"	src="<c:url value='/js/ajax-fileupload.js?'/>v=${jsversion}"></script>
	
	<script type="text/javascript">
	 function cleanImg(imgFieldName){
		 $("#"+imgFieldName).val("");
		 alert("已清除");
	 }
	
	 function ajaxFileUpload(fileInputObj,urlObj) {
        $.ajaxFileUpload
        (
            {
                url: '<c:url value="/upload/imgUpload.html"/>', //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: fileInputObj, //文件上传域的ID
                dataType: 'json', //返回值类型 一般设置为json
                async:false,
                success: function (data, status)  //服务器成功响应处理函数
                {
                    var urlVal = $("#"+urlObj).val();
                    if(urlVal == ""){
                    	urlVal = data.url;
                    }else{
	                    urlVal = urlVal+","+data.url;
                    }
                    $("#"+urlObj).val(urlVal);
                    $("#imgDiv").append("<img style='width: 300px; height: 300px; align: center' src='"+data.url+"'>");
                    if (typeof (data.error) != 'undefined') {
                        if (data.error != '') {
                            alert(data.error);
                        } else {
                            alert(data.message);
                        }
                    }
                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                    alert(e);
                }
            }
        )
        return false;
    }
	</script>
	
	<div align="center" id="imgDiv">
	</div>
	
</body>

</html>