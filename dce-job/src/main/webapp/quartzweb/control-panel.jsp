<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>quartz web control panel</title>
<%@ include file="html-head.jsp" %>
</head>
<body>
<%@ include file="layout-head.jsp" %>
<div style="border:#ccc solid 1px; background: #E6E6FF; padding:10px;">当前scheduler状态：
  <c:choose>
    <c:when test="${scheduler.inStandbyMode}">暂停 <!--<a href="scheduler-manage.do?operation=resume">恢复</a>--></c:when>
    <c:when test="${scheduler.started}">运行中 <!--<a href="scheduler-manage.do?operation=pause">暂停</a>--></c:when>
    <c:when test="${scheduler.shutdown}">停止</c:when>
  </c:choose>
</div>
<div style="text-align:right; padding:10px;"><a href="triggers.do">所有调度</a> <a href="history-list.do?result=notSuccess">所有日志</a> <a href="calendar-list.do">熄火期</a> <a href="calendar-insert-input.do">新建熄火期</a> <a href="listeners.do">监听器</a></div>
<table border="1" cellspacing="1" cellpadding="4" width="100%">
  <tr>
    <th>任务名称</th>
    <th>任务分类</th>
    <th>Java类</th>
    <th>状态</th>
    <th>描述</th>
    <th>cron表达式</th>
    <th>上次运行时间</th>
    <th>下次运行时间</th>
    <th>详细</th>
  </tr>
  <c:forEach items="${jobAndTriggerBeans}" var="item">
    <tr>
      <td><a href="job-detail.do?group=${item.jobDetail.group}&amp;name=${item.jobDetail.name}">${item.jobDetail.name}</a></td>
      <td>${item.jobDetail.group}</td>
      <td><span style="color:#999">
        <%-- <c:choose>
          <c:when test="${item.class.name eq 'com.hehenian.scheduling.quartzweb.workflow.SimpleWorkFlowJobDetailBean'}">${item.jobDetailBean.jobClass.name}</c:when>
          <c:otherwise>${item.jobClass.name}</c:otherwise>
        </c:choose> --%>
        ${item.jobDetail.jobClass.name}
        </span></td>
      <td>
     	 <c:choose>
          <c:when test="${currentlyExecutingJobsMap[item.jobDetail.fullName] eq true}">运行中</c:when>
          <c:otherwise>等待中</c:otherwise>
        </c:choose>
        </td>
       <!-- cronTrigger.getCronExpression();
						cronTrigger.getNextFireTime();
						cronTrigger.getPreviousFireTime();
						cronTrigger.getStartTime(); -->
	   <td>${item.jobDetail.description}</td>
       <td>
       <c:forEach items="${item.cronTriggers}" var="trigger">
       			<h4>${trigger.cronExpression}</h4>
       </c:forEach>
       </td>
       <td>
       <c:forEach items="${item.triggers}" var="trigger">
       		<h4><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${trigger.previousFireTime}"/></h4>
       </c:forEach>
       </td>
       <td>
        <c:forEach items="${item.triggers}" var="trigger">
       		<h4><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${trigger.nextFireTime}"/></h4>
        </c:forEach>
       </td>
      <td align="center"><a href="job-detail-triggers.do?group=${item.jobDetail.group}&amp;name=${item.jobDetail.name}">详细信息</a></td>
    </tr>
  </c:forEach>
</table>
<br />
version ${applicationScope.edition}
</body>
</html>