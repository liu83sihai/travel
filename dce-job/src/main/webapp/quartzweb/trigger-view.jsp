<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>View</title>
<%@ include file="html-head.jsp" %>
</head>
<body>
<%@ include file="layout-head.jsp" %>
<c:choose>
  <c:when test="${from=='triggers'}">
    <h2>调度${trigger.fullName}</h2>
    <div style="padding:10px;"><a href="triggers.do">返回所有调度列表</a></div>
  </c:when>
  <c:otherwise>
    <h2>任务${jobDetail.fullName}</h2>
    <div style="padding:10px;"><a href="job-detail.do?group=${jobDetail.group}&name=${jobDetail.name}">任务属性</a> <a href="job-detail-triggers.do?group=${jobDetail.group}&name=${jobDetail.name}">任务调度</a> <a href="history-list.do?group=${jobDetail.group}&name=${jobDetail.name}">任务日志</a> <a href="trigger-insert-input.do?group=${jobDetail.group}&name=${jobDetail.name}">新建调度</a></div>
  </c:otherwise>
</c:choose>
<table cellpadding="4" cellspacing="1" border="1" width="100%">
  <tr>
    <th width="100">调度名称</th>
    <td>${trigger.name}</td>
  </tr>
  <tr>
    <th>调度分类</th>
    <td>${trigger.group}</td>
  </tr>
  <tr>
    <th>状态</th>
    <td>${triggerState}</td>
  </tr>
  <tr>
    <th>操作</th>
    <td><c:if test="${triggerState=='normal'}"><a href="manage.do?type=trigger&operation=pause&group=${trigger.group}&name=${trigger.name}">暂停</a></c:if>
      <c:if test="${triggerState=='paused'}"><a href="manage.do?type=trigger&operation=resume&group=${trigger.group}&name=${trigger.name}">恢复</a></c:if></td>
  </tr>
  <tr>
    <th>调度Java类</th>
    <td><%-- ${trigger.class.name} --%>
    ${trigger.name}
    </td>
  </tr>
  <tr>
    <th>调度设置</th>
    <td style="background-color: #c8ebc5"><c:choose>
        <c:when test="${triggerType=='simple'}">重复次数：${trigger.repeatCount} 重复间隔：${trigger.repeatInterval}ms 即 ${repeatInterval}${repeatIntervalUnit}</c:when>
        <c:when test="${triggerType=='cron'}">cron表达式：${trigger.cronExpression}</c:when>
      </c:choose></td>
  </tr>
  <tr>
    <th>开始时间</th>
    <td><fmt:formatDate type="both" value="${trigger.startTime}" /></td>
  </tr>
  <tr>
    <th>结束时间</th>
    <td><fmt:formatDate type="both" value="${trigger.endTime}" /></td>
  </tr>
  <tr>
    <th>节假日</th>
    <td>${trigger.calendarName}</td>
  </tr>
  <tr>
    <th>上次触发时间</th>
    <td><fmt:formatDate type="both" value="${trigger.previousFireTime}" /></td>
  </tr>
  <tr>
    <th>下次触发时间</th>
    <td><fmt:formatDate type="both" value="${trigger.nextFireTime}" /></td>
  </tr>
  <tr>
    <th>最后一次触时间</th>
    <td><fmt:formatDate type="both" value="${trigger.finalFireTime}" /></td>
  </tr>
  <%-- <tr>
    <th>挥发性</th>
    <td>${trigger.volatility}</td>
  </tr> --%>
  <tr>
    <th>运行错过策略</th>
    <td>${trigger.misfireInstruction}</td>
  </tr>
  <tr>
    <th>数据</th>
    <td><c:forEach items="${trigger.jobDataMap}" var="item">${item.key}:${fn:escapeXml(item.value)}<br />
      </c:forEach>
      <br />
      <br />
      <a href="trigger-insert-input.do?clone=${trigger.fullName}">用这些数据克隆</a></td>
  </tr>
  <tr>
    <th>描述</th>
    <td>${fn:escapeXml(trigger.description)}</td>
  </tr>
</table>
</body>
</html>