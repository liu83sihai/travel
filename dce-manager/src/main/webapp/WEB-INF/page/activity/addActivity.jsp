<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>活动风彩编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
</head>
<body>

<!-- 内容 -->
<form id="editActivityForm" method="post" action="<c:url value='/activity/saveActivity.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="id" name="id" value="${activity.id}"/>
					<tr>	
						<td align="right">
							<label for="name">用户ID</label>
						</td>	
						<td>
								<input type="text" id="userId" name="userId" value="${activity.userId}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">描述</label>
						</td>	
						<td>
								<input type="text" id="synopsis" name="synopsis" value="${activity.synopsis}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">内容</label>
						</td>	
						<td>
								<input type="text" id="content" name="content" value="${activity.content}"/>												
						</td>						   
					</tr>
					
					<tr>
						<td align="right"><label for="name">列表小图：</label></td>
						<td>
							<input type="file" id="imagesFileObj" name="imagesFileObj"/>
							<input type="button" value="上传" id="btn_upload" onclick="ajaxFileUpload('imagesFileObj','images');" />
							<input type="hidden" id="images" name="images" value="${activity.images}"/>
							<input type="button" onclick="cleanImg('images');" value="清除原图"/>
						</td>
					</tr>
				
					<tr>	
						<td align="right">
							<label for="name">点赞数</label>
						</td>	
						<td>
								<input type="text" id="hitNum" name="hitNum" value="${activity.hitNum}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">创建时间</label>
						</td>	
						<td>
								<input type="text" 
								id="createDate" 
								name="createDate" 
								class="easyui-datebox" size="14" data-options="editable : true"  
								/>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">更新时间</label>
						</td>	
						<td>
								<input type="text" 
								id="modifyDate" 
								name="modifyDate" 
								class="easyui-datebox" size="14" data-options="editable : true"  
								/>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">状态</label>
						</td>	
						<td>
								<select id="status" class="easyui-combobox"
									style="width: 150px;">
									<option value="1"
											<c:if test="${activity.status==1}">selected="selected"</c:if>>发布</option>
										<option value="0"
											<c:if test="${activity.status==0}">selected="selected"</c:if>>删除</option>
										
								</select>
																	
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">备注</label>
						</td>	
						<td>
								<input type="text" id="remark" name="remark" value="${activity.remark}"/>												
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
	<img style="width: 300px; height: 300px; align: center" id="img" src="">
</div>
	
</body>

</html>