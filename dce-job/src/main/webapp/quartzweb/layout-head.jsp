<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="jsp-head.jsp"%>
<div>
	<h1>
		<a href="control-panel.do">任务列表</a>&nbsp;&nbsp;
		<!-- <a href="job-parameter.do">参数列表</a>&nbsp;&nbsp;
		<a href="job-parameter-history-input.do">参数修改历史</a> -->
		<!--  <a href="allot-parameter-list.do">新策略参数列表</a>&nbsp;&nbsp;-->  
		<span style="font-size:12px;margin-left:5px;">
		  <c:if test="${currentUser.userName!=null}">${currentUser.userName},已登录</c:if>
		  <c:if test="${currentUser.userName==null}"><a href="/login/login-input.do">登录</a></c:if>
		</span>
	</h1>
</div>

