<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>图标管理编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
</head>
<body>

<!-- 内容 -->
<form id="editBannerForm" method="post"  enctype="multipart/form-data" action="<c:url value='/banner/saveBanner.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="id" name="id" value="${banner.id}"/>
					<input type="hidden" id="createDate" name="createDate" value="${banner.createDate}"/>
					<input type="hidden" id="createName" name="createName" value="${banner.createName}"/>
					<input type="hidden" id="modifyDate" name="modifyDate" value="${banner.modifyDate}"/>
					<input type="hidden" id="modifyName" name="modifyName" value="${banner.modifyName}"/>
					<input type="hidden" id="status" name="status" value="${banner.status}"/>
					<tr>	
						<td align="right">
							<label for="name">图片名称 </label>
						</td>	
						<td>
							<input type="text" id="icoName" name="icoName" value="${banner.icoName}"/>	
						
						</td>						   
					</tr>
					
					<tr>
						<td align="right"><label for="name">图片：</label></td>
						<td>
							<input type="file" id="imgFileObj" name="imgFileObj"/>
							<input type="button" value="上传" id="btn_upload" onclick="ajaxFileUpload('imgFileObj','icoName');" />
							<input type="hidden" id="icoImage" name="icoImage" value="${banner.icoImage}"/>
							<input type="button" onclick="cleanImg('icoImage');" value="清除原图"/>
						</td>
					</tr>
					<tr>	
						<td align="right">
							<label for="name">图标类型 </label>
						</td>	
						<td>
							<select id="icoType" name="icoType" class="easyui-combobox" style="width: 150px;">
								<option value="1"
									<c:if test="${banner.icoType==1 || banner.icoType == null }">selected="selected"</c:if>>banner图</option>
								<option value="2"
									<c:if test="${banner.icoType==2}">selected="selected"</c:if>>导航小图标</option>
							</select>	
						
						</td>						   
					</tr>
					<tr>
						<td>关联商品</td>
						<td>
							<input type="text" id="goodsId" name="goodsId" value="${banner.goodsId}" readonly="readonly"/>
							<input type="text" id="goodsName" name="goodsName" value="${banner.goodsName}"/>
							<input type="button" onclick="javascript:commonChooseDialog('goodsId','goodsName','banner关联商品','GOODS');" value="选择商品"/>	
						</td>
					</tr>
					<tr>	
						<td align="right">
							<label for="name">链接类型</label>
						</td>	
						<td>
							<select id="linkType" name="linkType" class="easyui-combobox" style="width: 150px;">
								<option value="0"
									<c:if test="${banner.linkType==0 || banner.linkType == null }">selected="selected"</c:if>>无</option>
								<option value="1"
									<c:if test="${banner.linkType==1}">selected="selected"</c:if>>超链接</option>
								<option value="2"
									<c:if test="${banner.linkType==2}">selected="selected"</c:if>>程序链接</option>
							</select>	
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">链接值</label>
						</td>	
						<td>
								<input type="text" id="linkValue" name="linkValue" value="${banner.linkValue}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">提示内容</label>
						</td>	
						<td>
								<input type="text" id="hintValue" name="hintValue" value="${banner.hintValue}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">排序</label>
						</td>	
						<td>
								<input type="text" id="sort" name="sort" value="${banner.sort}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">备注</label>
						</td>	
						<td>
								<input type="text" id="remark" name="remark" value="${banner.remark}"/>												
						</td>						   
					</tr>
			</table>	   
		</div>	
	</form>

<script type="text/javascript"	src="<c:url value='/js/ajax-fileupload.js?'/>v=${jsversion}"></script>

<script type="text/javascript">
	
	/**
	 * 创建一个模态 Dialog
	 * 
	 * @param id divId
	 * @param _url Div链接
	 * @param _title 标题
	 * @param _width 宽度
	 * @param _height 高度
	 * @param _icon ICON图标
	 */
	function createModalDialog(id, _url, _title, _width, _height, _icon){
	    $("body").append("<div id='"+id+"' class='easyui-window'></div>");
	    if (_width == null)
	        _width = 800;
	    if (_height == null)
	        _height = 500;

	    $("#"+id).dialog({
	        title: _title,
	        width: _width,
	        height: _height,
	        cache: false,
	        iconCls: _icon,
	        href: _url,
	        collapsible: false,
	        minimizable:false,
	        maximizable: true,
	        resizable: false,
	        modal: true,
	        closed: true
	    });
	}
	
	function commonChooseDialog(retId,retText,_title,chooseType){
		var dialogDivId = "c_ch_w";
		var url="<c:url value='/choose/index.html?chooseType='/>"+chooseType+"&retId="+retId+"&retText="+retText+"&dialogDivId="+dialogDivId;
		//移除存在的Dialog
		$("#"+dialogDivId).remove();
		//先根据div的id删除，但界面元素还是会存在dialog div，还需执行dialog的销毁操作
		//$("#"+dialogDivId).dialog('destroy');
		//创建窗口
		createModalDialog(dialogDivId,url,_title, 600, 600);
		
		$("#"+dialogDivId).dialog('open');
	}
	
</script>
	
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
		<c:if test="${not empty banner.icoImage }">
			<img style="width: 300px; height: 300px; align: center" id="img"
				src="${banner.icoImage}">
		</c:if>
	</div>
	
</body>

</html>