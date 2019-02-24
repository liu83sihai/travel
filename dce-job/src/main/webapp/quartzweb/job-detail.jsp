<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>任务属性</title>
<%@ include file="html-head.jsp" %>
<script type="text/javascript">
function aaa(){
	if(confirm("确认运行吗？")){
		return true;
	}
	return false;
}
</script>
</head>
<body>
<%@ include file="layout-head.jsp" %>
<h2>任务${param.group}.${param.name}</h2>
<div style="padding:10px;">任务属性 <a href="job-detail-triggers.do?group=${jobDetail.group}&name=${jobDetail.name}">任务调度</a> <a href="history-list.do?group=${jobDetail.group}&name=${jobDetail.name}">任务日志</a> <a href="trigger-insert-input.do?group=${jobDetail.group}&name=${jobDetail.name}">新建调度</a></div>
<c:if test="${not empty jobDetail}">
  <table width="100%" cellpadding="5" cellspacing="1" border="1">
    <tr>
      <th width="100">任务名称</th>
      <td>${jobDetail.name}</td>
    </tr>
    <tr>
      <th>任务分类</th>
      <td>${jobDetail.group}</td>
    </tr>
    <tr>
      <th>Java类</th>
      <td>
      ${jobDetail.jobClass.name}
        <%-- <c:choose>
          <c:when test="${jobDetail.class.name=='com.hehenian.scheduling.quartzweb.workflow.SimpleWorkFlowJobDetailBean'}">${jobDetail.jobDetailBean.jobClass.name}</c:when>
          <c:otherwise>${jobDetail.jobClass.name}</c:otherwise>
        </c:choose> --%>
        </td>
    </tr>
    <tr>
      <th>特性</th>
      <td>是否有状态：${jobDetail.stateful} <%-- 是否易挥发：${jobDetail.volatility}  --%>是否永久存在：${jobDetail.durable}</td>
    </tr>
    <tr>
      <th>说明</th>
      <td><pre>${jobDetail.description}</pre></td>
    </tr>
    <tr>
      <th>数据</th>
      <td><c:forEach items="${jobDetail.jobDataMap}" var="item">${fn:escapeXml(item.key)}=${fn:escapeXml(item.value)}<br />
        </c:forEach></td>
    </tr>
    <tr>
      <th></th>
      <td><form action="job-detail-run.do" method="post" onsubmit="return aaa()">
          <input name="jobGroup" type="hidden" value="${jobDetail.group}" />
          <input name="jobName" type="hidden" value="${jobDetail.name}" />
          <div>
            <c:forEach items="${jobDataKeys}" var="item">${item}:<br />
              <input name="jobData_${item}" type="text" value="${jobDetail.jobDataMap[item]}" style="width:40%" />
              <br />
            </c:forEach>
          </div>
          <input type="submit" value="立刻运行"/>
        </form></td>
    </tr>
  </table>
</c:if>
</body>
</html>