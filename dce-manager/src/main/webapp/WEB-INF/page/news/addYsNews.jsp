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
<form id="editYsNewsForm" method="post" enctype="multipart/form-data" action="<c:url value='ysnews/saveYsNews.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="id" name="id" value="${ysnews.id}"/>
					 <tr>	
						<td align="right">
							<label for="name">标题</label>
						</td>	
						<td>
								<input type="text" id="title" name="title" value="${ysnews.title}"/>												
						</td>						   
					</tr>
					<tr>
						<td align="right"><label for="name">列表小图：</label></td>
						<td>
							<input type="file" id="imageFileObj" name="imageFileObj"/>
							<input type="button" value="上传" id="btn_upload" onclick="ajaxFileUpload('imageFileObj','image');" />
							<input type="hidden" id="image" name="image" value="${ysnews.image}"/>
							<input type="button" onclick="cleanImg('image');" value="清除原图"/>
						</td>
					</tr>
				
					<tr>	
						<td align="right">
							<label for="name">内容</label>
						</td>	
						<td>
						<textarea rows="5" cols="20"  id="content" name="content">${ysnews.content}</textarea>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">作者</label>
						</td>	
						<td>
								<input type="text" id="author" name="author" value="${ysnews.author}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">置顶新闻</label>
						</td>	
						<td>
						<select id="topNews" class="easyui-combobox" name="topNews" style="width: 150px;">
									<option value="0" <c:if test="${ysnews.topNews==0}">selected="selected"</c:if> >待置顶</option>
									<option value="1" <c:if test="${ysnews.topNews==1}">selected="selected"</c:if>>置顶</option>
							</select>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">备注</label>
						</td>	
						<td>
						<textarea rows="5" cols="20" id="remark" name="remark" >${ysnews.remark}</textarea>
						</td>						   
					</tr>
					
					<tr>	
						<td align="right">
							<label for="name">状态</label>
						</td>	
						<td>
						<select id="status" class="easyui-combobox" name="status" style="width: 150px;">
									<option value="0" <c:if test="${ysnews.status==0}">selected="selected"</c:if> >待发布</option>
									<option value="1" <c:if test="${ysnews.status==1}">selected="selected"</c:if>>发布</option>
							</select>
						</td>						   
					</tr>
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
		<c:if test="${not empty ysnews.image }">
			<img style="width: 300px; height: 300px; align: center" id="img"
				src="${ysnews.image}">
		</c:if>
	</div>
	
</body>

</html>