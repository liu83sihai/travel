<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>日志列表</title>
<%@ include file="html-head.jsp" %>
</head>
<body>
<%@ include file="layout-head.jsp" %>
<c:choose>
  <c:when test="${not empty param.group && not empty param.name}">
    <h2>任务${param.group}.${param.name}</h2>
    <div style="padding:10px;"><a href="job-detail.do?group=${param.group}&name=${param.name}">任务属性</a> <a href="job-detail-triggers.do?group=${param.group}&name=${param.name}">任务调度</a> 任务日志 <a href="trigger-insert-input.do?group=${param.group}&name=${param.name}">新建调度</a></div>
  </c:when>
  <c:otherwise>
    <h2>所有日志</h2>
  </c:otherwise>
</c:choose>
<div style="text-align:right">
  <c:choose>
    <c:when test="${not empty param.group && not empty param.name}">
      <c:choose>
        <c:when test="${empty param.result}">所有日志</c:when>
        <c:otherwise><a href="history-list.do?group=${param.group}&name=${param.name}">所有日志</a></c:otherwise>
      </c:choose>
      <c:choose>
        <c:when test="${param.result=='notSuccess'}">出错日志</c:when>
        <c:otherwise> <a href="history-list.do?group=${param.group}&name=${param.name}&result=notSuccess">出错日志</a></c:otherwise>
      </c:choose>
    </c:when>
    <c:otherwise>
      <c:choose>
        <c:when test="${empty param.result}">所有日志</c:when>
        <c:otherwise><a href="history-list.do">所有日志</a></c:otherwise>
      </c:choose>
      <c:choose>
        <c:when test="${param.result=='notSuccess'}">出错日志</c:when>
        <c:otherwise> <a href="history-list.do?result=notSuccess">出错日志</a></c:otherwise>
      </c:choose>
    </c:otherwise>
  </c:choose>
</div>
<span style="font-size:12px">
<jsp:include page="pageholder.jsp" />
</span>
<table width="100%" border="1" cellspacing="1" cellpadding="4">
  <tr>
    <th align="center">触发时间</th>
    <th align="center">运行时间</th>
    <th align="center">任务</th>
    <th align="center">调度</th>
    <th align="center">结果</th>
    <th align="center">报告</th>
  </tr>
  <c:forEach items="${pageHolder.list}" var="item">
    <tr>
      <td><fmt:formatDate type="both" value="${item.fireTime}" /></td>
      <td align="center" title="${item.runTime}毫秒">${item.niceRunTime}</td>
      <td>${item.jobGroup}.${item.jobName}</td>
      <td>${item.triggerGroup}.${item.triggerName}</td>
      <td><c:choose>
          <c:when test="${item.result==1}">成功</c:when>
          <c:when test="${item.result==2}"><span style="color:red">失败</span></c:when>
          <c:when test="${item.result==3}"><span style="color:red">错过</span></c:when>
          <c:when test="${item.result==4}"><span style="color:red">否决</span></c:when>
        </c:choose></td>
      <td><c:if test="${not empty item.report}"><a href="history-view.do?id=${item.id}">查看</a></c:if></td>
    </tr>
  </c:forEach>
</table>
<span style="font-size:12px">
<jsp:include page="pageholder.jsp" />
</span>
</body>
</html>