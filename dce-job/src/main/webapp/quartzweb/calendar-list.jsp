<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>熄火期列表</title>
<%@ include file="html-head.jsp" %>
</head>
<body>
<%@ include file="layout-head.jsp" %>
<h2>熄火期列表</h2>
<table width="100%" border="1" cellspacing="1" cellpadding="4">
  <tr>
    <th align="center">名称</th>
    <th align="center">Java类</th>
    <th align="center">属性</th>
    <th align="center">描述</th>
    <th align="center">操作</th>
  </tr>
  <c:forEach items="${calendarsMap}" var="item">
    <tr>
      <td>${item.key}</td>
      <td>${item.value.class.name}</td>
      <td><c:forEach items="${item.value.excludedDates}" var="date">
          <fmt:formatDate pattern="yyy-MM-dd" value="${date}" />
          <br />
        </c:forEach></td>
      <td>${item.value.description}</td>
      <td><a href="calendar-update-input.do?calendarName=${item.key}">修改</a></td>
    </tr>
  </c:forEach>
</table>
</body>
</html>