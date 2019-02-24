<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${operation=='insert'?'新建熄火期':'编辑熄火期'}</title>
<%@ include file="html-head.jsp" %>
</head>
<body>
<%@ include file="layout-head.jsp" %>
<h2>${operation=='insert'?'新建熄火期':'编辑熄火期'}</h2>
<form action="calendar-insert.do" method="post">
  <input name="replace" type="hidden" value="${replace}" />
  <table width="100%" border="1" cellspacing="1" cellpadding="4">
    <tr>
      <th width="100">类型</th>
      <td><select name="calendarClass">
          <option value="org.quartz.impl.calendar.HolidayCalendar">org.quartz.impl.calendar.HolidayCalendar</option>
        </select></td>
    </tr>
    <tr>
      <th width="100">名称</th>
      <td><input name="calendarName" type="text" value="${calendarName}" /></td>
    </tr>
    <tr>
      <th>熄火的日期</th>
      <td>每行一个日期<br />
        <textarea name="excludedDates" cols="50" rows="10">${excludedDates}</textarea></td>
    </tr>
    <tr>
      <th>描述</th>
      <td><textarea name="description" cols="50" rows="5">${description}</textarea></td>
    </tr>
    <tr>
      <th></th>
      <td><input type="submit" value="提交" /></td>
    </tr>
  </table>
</form>
</body>
</html>