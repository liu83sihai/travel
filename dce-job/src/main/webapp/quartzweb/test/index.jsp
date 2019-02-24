<%@ page contentType="text/html; charset=utf-8" 
import="java.io.*"
import="java.util.*"
%>
<%@ include file="../jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>测试项目列表</title>
</head>
<%
	String basePath = this.getClass().getClassLoader().getResource("/").getPath();
	System.out.println("path:" + basePath.substring(0, basePath.indexOf("WEB-INF")));
	basePath = basePath.substring(0, basePath.indexOf("WEB-INF"));
	File file = new File(basePath + "test/case"); 
	File[] subDirs = file.listFiles();
	List<String> dirs = new ArrayList<String>();
	if (subDirs != null && subDirs.length > 0) {
		for (int i = subDirs.length - 1; i >= 0; i--) {
			dirs.add(subDirs[i].getName());
		}
	}
	request.setAttribute("projects", dirs);
%>
<body style="padding-left:30px">
	*********测试项目列表*********<br>
	<c:forEach items="${projects }" var="project">
		<a href="cases.jsp?p=${project }">${project }</a>
		[<a href="cases.jsp?p=${project }">查看Case列表</a> | <a href="report.jsp?p=${project }">查看报表</a>]
		<br>
	</c:forEach>
	<c:if test="${empty projects }">
		暂无测试项目！
	</c:if>
</body>
</html>