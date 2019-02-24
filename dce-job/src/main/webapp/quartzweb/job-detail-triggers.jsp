<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>任务调度</title>
<%@ include file="html-head.jsp" %>
</head>
<body>
<%@ include file="layout-head.jsp" %>
<h2>任务${param.group}.${param.name}</h2>
<div style="padding:10px;"><a href="job-detail.do?group=${jobDetail.group}&name=${jobDetail.name}">任务属性</a> 任务调度 <a href="history-list.do?group=${jobDetail.group}&name=${jobDetail.name}">任务日志</a> <a href="trigger-insert-input.do?group=${jobDetail.group}&name=${jobDetail.name}">新建调度</a></div>
<table width="100%" border="1" cellspacing="1" cellpadding="4">
  <tr>
    <th>调度名称</th>
    <th>调度分类</th>
    <th>状态</th>
    <th>上次运行时间</th>
    <th>下次运行时间</th>
    <th>优先级</th>
    <th>描述</th>
    <th>操作</th>
  </tr>
  <c:forEach items="${triggers}" var="item">
    <tr>
      <td>${item.name}</td>
      <td>${item.group}</td>
      <td>${triggerStateMap[item.fullName]}</td>
      <td><fmt:formatDate type="both" value="${item.previousFireTime}" /></td>
      <td><fmt:formatDate type="both" value="${item.nextFireTime}" /></td>
      <td>${item.priority}</td>
      <td>${item.description}</td>
      <td align="center">
      	<a href="trigger-view.do?triggerGroup=${item.group}&triggerName=${item.name}">详细信息</a>
		<a href="trigger-update-input.do?triggerGroup=${item.group}&amp;triggerName=${item.name}">修改</a>
		<a href="trigger-insert-input.do?clone=${item.group}.${item.name}">克隆</a>
		<a href="trigger-run-now.do?triggerGroup=${item.group}&amp;triggerName=${item.name}" onclick="return confirm('真的要立即触发调度${item.fullName}吗？')">立即触发</a>
		<a href="trigger-delete.do?triggerGroup=${item.group}&amp;triggerName=${item.name}" onclick="return confirm('真的要删除调度${item.fullName}吗？')">删除</a>
	  </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>