<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新策略分配参数</title>
<%@ include file="html-head.jsp" %>
</head>
<body>
<table width="100%" cellpadding="5" cellspacing="1" border="1">
  <tbody>
   <tr>
    <c:forEach items="${fieldNameList}" var="head">
     <th width="50">${head.title}</th>
    </c:forEach>
   </tr>
  <c:forEach items="${resultList}" var="row">
   <tr>
    <c:forEach items="${fieldNameList}" var="head">
     <td style="width: 50px;word-break : break-all;">${row[head.field]}</td>
    </c:forEach>
   </tr>
  </c:forEach>
  </tbody>
 </table>
</body>
</html>