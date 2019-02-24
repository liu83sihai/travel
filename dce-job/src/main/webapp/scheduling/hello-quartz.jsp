<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>hello quartz</title>
</head>
<body>
helloCount=${helloCount}<br />
<br />
<br />
任务列表：
<ul>
  <c:forEach items="${jobDetails}" var="item">
    <li>${item.group}.${item.name}<br />
      ${item.jobClass.name}</li>
  </c:forEach>
</ul>
</body>
</html>