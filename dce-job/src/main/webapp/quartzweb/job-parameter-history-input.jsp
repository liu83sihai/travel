<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>调度参数历史查询</title>
<%@ include file="html-head.jsp" %>
<script type="text/javascript" src="/global/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript" language="javascript">
var jobNames=[<c:forEach items="${jobNameList}" var="item" varStatus="status"><c:if test="${status.index>0}">,</c:if>"${item.jobName}"</c:forEach>];
$(function(){
	$("#jobNameInput").autocomplete(jobNames);	
	$("#queryForm").submit();
});

function resizeIframeHeight(iframeDom){
	var $iframe=$(iframeDom).filter(':visible'),$body=$iframe.contents().find('body');
	if($iframe.size()){
		var w=$body.width(),h=$body.height();
		if(w>0&&h>0){
			$iframe.width(w).height(h+90);
		}
	}
}
</script>
<style>
.Wdate{height:16px;width:120px;}
</style>
</head>
  <body>
  <%@ include file="layout-head.jsp" %>
  <div style="margin-left:10px;marign-right:10px;">
    <div>说明:只有操作类型是修改的才会出现对比链接</div>
	<form action="job-parameter-history-query.do" target="queryResultFrame" id="queryForm">
		任务名称：
		<input type="text" name="parame.jobName" value="${parame.jobName}" id="jobNameInput"/>
		步骤：
		<input type="text" name="parame.step" value="${parame.step}" size="2"/>
		操作类型:
	    <select name="parame.actionType">
	     <option value="-1">不限</option>
	     <!--  <option value="1" ${parame.actionType==1?'selected="selected"':''}>新建</option>-->
	     <option value="2" ${parame.actionType==2?'selected="selected"':''}>删除</option>
	     <option value="4" ${parame.actionType==3?'selected="selected"':''}>修改</option>
	    </select>
	    操作人：
	    <select name="parame.userName">
	     <option value="">不限</option>
	     <c:forEach items="${userList}" var="user">
	        <option value="${user.userName}">${user.userName}</option>
	     </c:forEach>
	    </select>
	    操作时间:
	    <input name="parame.actionTime1" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" value="${parame.actionTime1 }"/> 至
		 <input name="parame.actionTime2" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" value="${parame.actionTime2 }"/>
		<input type="submit" value="查找" />
	</form>
</div>
    <iframe src="about:blank" id="queryResultFrame" name="queryResultFrame" scrolling="no" frameborder="0"  style="width:100%;_width:100%;" onload="resizeIframeHeight(this);"></iframe>
  </body>
</html>
