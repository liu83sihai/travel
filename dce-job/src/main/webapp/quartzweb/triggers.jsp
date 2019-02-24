<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>所有调度</title>
<%@ include file="html-head.jsp" %>
</head>
<body>
<%@ include file="layout-head.jsp" %>
<h2>所有调度</h2>
<table border="1" cellspacing="1" cellpadding="4" width="100%">
  <tr>
    <th>调度全称</th>
    <th>任务全称</th>
    <th>状态</th>
    <th>上次运行时间</th>
    <th>下次运行时间</th>
    <th>优先级</th>
    <th>描述</th>
    <th>操作</th>
  </tr>
  <c:forEach items="${triggers}" var="item">
    <tr>
      <td>${item.fullName}</td>
      <td>${item.fullJobName}</td>
      <td>${triggerStateMap[item.fullName]}</td>
      <td><fmt:formatDate type="both" value="${item.previousFireTime}" /></td>
      <td><fmt:formatDate type="both" value="${item.nextFireTime}" /></td>
      <td>${item.priority}</td>
      <td>${item.description}</td>
      <td align="center"><a href="trigger-view.do?triggerGroup=${item.group}&triggerName=${item.name}&from=triggers">详细信息</a></td>
    </tr>
  </c:forEach>
</table>
</body>
</html>