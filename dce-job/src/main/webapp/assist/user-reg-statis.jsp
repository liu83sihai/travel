<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../quartzweb/jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户注册数据补录</title>
<%@ include file="../quartzweb/html-head.jsp" %>
<script type="text/javascript">
function statis(){
	if($('#isRunning') && $('#isRunning')=='true'){
		alert("请勿重复提交");
		return false;
	}	
	var beginDate = $('#beginDate').val();
	var endDate = $('#endDate').val();
	if(beginDate==null || beginDate==''){
		alert("起始日期不能为空");
		return false;
	}
	if(endDate==null || endDate==''){
		alert("结束日期不能为空");
		return false;
	}
	var arr=beginDate.split("-");    
	var starttime=new Date(arr[0],arr[1],arr[2]);    
	var starttimes=starttime.getTime();   
	  
	var arrs=endDate.split("-");    
	var lktime=new Date(arrs[0],arrs[1],arrs[2]);    
	var endtimes=lktime.getTime();   
	if(starttimes>endtimes){
		alert("起始日期不能大于结束日期");
		return false;
	}
	if(confirm("确认补录"+beginDate+"至"+endDate+"的注册数据吗？")){
		return true;
	}
	return false;
}
</script>
</head>
<body>
<%@ include file="../quartzweb/layout-head.jsp" %>
<h2>用户注册数据补录</h2>
<div style="padding:10px;">
<input type="hidden" id="isRunning" value="${isRunning }"/>
	<form action="do-user-reg-statis.do" method="post" onsubmit="return statis()">
		起始日期：<input id="beginDate" name="beginDate" type="text" value="${beginDate }" style="width:100px;cursor:hand;" readonly="readonly" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})"/>
		&nbsp;&nbsp; 结束日期：<input id="endDate" name="endDate" type="text" value="${endDate }" style="width:100px;cursor:hand;" readonly="readonly" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})"/>
		&nbsp;&nbsp; 
		<input type="submit" value="提交" />
	</form>
</div>
  
<script type="text/javascript" src="<%=request.getContextPath() %>/global/js/my97datepicker/WdatePicker.js"></script>
<script type="text/javascript">
$(function(){
	if($('#isRunning') && $('#isRunning')=='true'){
		alert("请勿重复提交");
	}				
});			

</script>
</body>
</html>