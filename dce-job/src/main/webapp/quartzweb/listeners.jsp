<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>监听器</title>
<%@ include file="html-head.jsp" %>
</head>
<body>
<%@ include file="layout-head.jsp" %>
<h2>全局任务监听器</h2>
<table border="1" cellpadding="4" cellspacing="1" width="100%">
  <tr>
    <th width="300">监听器名称</th>
    <!-- <th>监听器Java类</th> -->
  </tr>
  <c:forEach items="${globalJobListeners}" var="item">
    <tr>
      <td>${item.name}</td>
      <%-- <td>${item.class.name}</td> --%>
    </tr>
  </c:forEach>
</table>
<br />
<h2>全局调度监听器</h2>
<table border="1" cellpadding="4" cellspacing="1" width="100%">
  <tr>
    <th width="300">监听器名称</th>
    <!-- <th>监听器Java类</th> -->
  </tr>
  <c:forEach items="${globalTriggerListeners}" var="item">
    <tr>
      <td>${item.name}</td>
      <%-- <td>${item.class.name}</td> --%>
    </tr>
  </c:forEach>
</table>
<br />
<h2>任务监听器</h2>
<table border="1" cellpadding="4" cellspacing="1" width="100%">
  <tr>
    <th width="300">监听器名称</th>
    <!-- <th>监听器Java类</th> -->
  </tr>
  <c:forEach items="${jobListeners}" var="item">
    <tr>
      <td>${item.name}</td>
      <%-- <td>${item.class.name}</td> --%>
    </tr>
  </c:forEach>
</table>
<br />
<h2>调度监听器</h2>
<table border="1" cellpadding="4" cellspacing="1" width="100%">
  <tr>
    <th width="300">监听器名称</th>
    <!-- <th>监听器Java类</th> -->
  </tr>
  <c:forEach items="${triggerListeners}" var="item">
    <tr>
      <td>${item.name}</td>
      <%-- <td>${item.class.name}</td> --%>
    </tr>
  </c:forEach>
</table>
</body>
</html>