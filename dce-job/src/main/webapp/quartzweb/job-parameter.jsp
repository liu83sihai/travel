<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分配任务参数</title>
<%@ include file="html-head.jsp" %>
<script type="text/javascript" language="javascript">
var jobNames=[<c:forEach items="${jobNameList}" var="item" varStatus="status"><c:if test="${status.index>0}">,</c:if>"${item.jobName}"</c:forEach>];

var paramStr=[ <c:forEach items="${jobParameterTypesLists}" var="item" varStatus="status">
                <c:if test="${status.index>0}">,</c:if>
                 {parameName:"${item.parameName}",parameNameLabel:"${item.parameNameLabel}",parameType:"${item.parameType}",parameValue:"${item.parameValue}",parameValueLabel:"${item.parameValueLabel}",defaultValue:"${item.defaultValue}"}
               </c:forEach>	
];
function getObjByParameName(parameName){
	for(i=0;i<paramStr.length;i++){
	  if(paramStr[i].parameName==parameName){
		return  paramStr[i];
	  }	
	}
	return null;
}
function getParameLabel(parameName,parameValue){
	var parameObj=getObjByParameName(parameName);
	var obj={};
	if(parameObj!=null){
	   obj.parameNameLabel=parameObj.parameNameLabel;
	if(parameObj.parameType=="text"||parameObj.parameType=="textarea"){
	    obj.parameValueLabel=parameValue;
	}else{
		var valueLabel=$.trim(parameObj.parameValueLabel).split("|");
		var value=$.trim(parameObj.parameValue).split("|");
		for(i=0;i<value.length;i++){
			if(value[i].toLocaleLowerCase()==$.trim(parameValue.toLocaleLowerCase())){
				obj.parameValueLabel=valueLabel[i];
				break;
			}
		}
	 }
	}
	return obj;
}

function delParame(autoId,jobName,step){
	var url="job-parameter-delete-one.do?";
	if(autoId!="undefined"){
		url+="autoId="+autoId;
	}
	if(jobName!="undefined"){
		url+"&jobName="+jobName;
	}
	if(step!="undefined"){
		url+"&step="+step;
	}
	//alert(url);
}
function isNumber(obj){
	var re=/^\d+$/;
	if(obj.value!=''){
	if(!re.test(obj.value)){
		alert("请输入正整数");
		obj.value="";
		return;
	}
	}
}
$(function(){
	$("#jobNameInput").autocomplete(jobNames);	
});
</script>
</head>
  
  <body>
  <%@ include file="layout-head.jsp" %>
  <div style="padding-bottom:10px;">
	<form action="job-parameter.do">
		任务名称
		<input type="text" name="jobName" value="${jobName}" id="jobNameInput"/>
		步骤:<input type="text" name="step" size="4" value="${step}" onblur="isNumber(this)"/>
		是否使用:
		<input type="radio" name="isUse" value="-1"} <c:if test="${isUse==-1||empty isUse}">checked="checked"</c:if>/>不限
		<input type="radio" name="isUse" value="1"} <c:if test="${isUse==1}">checked="checked"</c:if>/>是
		<input type="radio" name="isUse" value="0" <c:if test="${isUse==0}">checked="checked"</c:if>/>否
		<input type="submit" value="查找" />
	</form>
</div>
    <h2>分配任务参数：</h2>
    <div class="lbtit">
    <jsp:include page="/global/pageholder-default.jsp"/>
    </div>
    <table width="100%" cellpadding="5" cellspacing="1" border="1">
    	<tr>
    		<th>任务名称</th><th>步骤</th><th>参数名称</th><th>参数值</th><th>是否在用</th><th>操作</th>
    	</tr>
    	 <c:forEach items="${pageHolder.list}" var="item">
    	 <tr>
    			<td><a href="job-parameter.do?jobName=${item.jobName}">${item.jobName}</a></td>
    			<td><a href="job-parameter.do?jobName=${item.jobName}&step=${item.step}">${item.step}</a></td>
    	
    			<td>
    			<!-- [${item.parameterName }]-->
    			 <script>
    			   var ob=getParameLabel("${item.parameterName}","${item.parameterValue}");
    			       document.writeln(ob.parameNameLabel+" (${item.parameterName})");
    			 </script>
    			</td>
    			<td style="width: 500px;word-break : break-all;">
    			 <script>
    			     document.writeln(getParameLabel("${item.parameterName}","${item.parameterValue}").parameValueLabel);
    			 </script>
    			<!--  [${item.parameterValue}]-->
    			</td>
    	  
    			<td><c:if test="${item.isUse == 1}">是</c:if><c:if test="${item.isUse == 0}">否</c:if></td>
    			<td>
    			 <c:choose>
    			  <c:when test="${currentUser.userName==null}">请先登录</c:when>
    			  <c:otherwise>
    			   <a href="job-parameter-insert-input.do?jobName=${item.jobName}&step=${item.step}&operation=update">修改</a>&nbsp;&nbsp;
    				<a href="job-parameter-delete-one.do?autoId=${item.autoId}&jobName=${item.jobName}&step=${step}" onclick="return confirm('真的要删除吗？')">删除</a>
    			  </c:otherwise>
    			 </c:choose>
    				
    			</td>
    		</tr>
    	</c:forEach>
    </table>
    <div class="lbtit">
    <jsp:include page="/global/pageholder-default.jsp"/>
    </div>
    <br />
    <div align="center">
    	<a href="job-parameter-insert-input.do">新建</a>
    	<c:if test="${not empty jobName}">&nbsp;&nbsp;
    		<c:if test="${not empty step}">
	    	<a href="job-parameter-insert-input.do?jobName=${jobName}&step=${step}">克隆</a>&nbsp;&nbsp;
	    	</c:if>
	    	<c:choose>
	    	 <c:when test="${currentUser.userName==null}"></c:when>
	    	 <c:otherwise>
	    	   <a href="job-parameter-delete.do?jobName=${jobName}&step=${step}" onclick="return confirm('真的要删除 ${jobName} 第 ${step} 步的所有参数吗？')">全部删除</a>
	    	 </c:otherwise>
	    	</c:choose> 
    	</c:if>
    </div>
  </body>
</html>
