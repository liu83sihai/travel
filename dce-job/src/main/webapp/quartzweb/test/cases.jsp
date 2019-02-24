<%@ page contentType="text/html; charset=utf-8" 
import="java.io.*"
import="java.util.*"
%>
<%@ include file="../jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TestCase列表</title>
</head>
<%
	String path = request.getContextPath();  
	String webroot = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
	
	String basePath = this.getClass().getClassLoader().getResource("/").getPath();
	basePath = basePath.substring(0, basePath.indexOf("WEB-INF"));
	String project = request.getParameter("p");
	File file = new File(basePath + "test/case/" +project); 
	File[] subDirs = file.listFiles();
	List<String> dirs = new ArrayList<String>();
	if (subDirs != null && subDirs.length > 0) {
		for (int i = subDirs.length - 1; i >= 0; i--) {
			dirs.add( subDirs[i].getName().replaceAll(".xml", ""));
		}
	}
	request.setAttribute("cases", dirs);

%>
<body style="padding-left:30px">
	[<a href="index.jsp">测试首页</a>]<br/>
	*********[<%=project%>]Test Case列表*********<br>
	<c:forEach items="${cases }" var="case">
		<input type="checkbox" name="case" value ="${case }"> <a target="_blank" href="<%=webroot %>test/case/<%=project%>/${case }.xml">${case }</a>
		[<a target="_blank" href="execute.jsp?p=<%=project%>&c=${case}">执行</a>]
		<br>
	</c:forEach>
	<c:if test="${empty cases }">
		暂无测试用例！
	</c:if>
</body>
</html>