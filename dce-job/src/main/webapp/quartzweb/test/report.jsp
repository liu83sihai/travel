<%@ page contentType="text/html; charset=utf-8" 
import="java.io.*"
import="java.util.*"
%>
<%@ include file="../jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Report下载</title>
</head>
<%
	String path = request.getContextPath();  
	String webroot = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
	
	String basePath = this.getClass().getClassLoader().getResource("/").getPath();
	basePath = basePath.substring(0, basePath.indexOf("WEB-INF"));
	String project = request.getParameter("p");
	File file = new File(basePath + "test/report/" +project); 
	File[] subDirs = file.listFiles();
	List<String> dirs = new ArrayList<String>();
	if (subDirs != null && subDirs.length > 0) {
		for (int i = subDirs.length - 1; i >= 0; i--) {
			dirs.add(subDirs[i].getName());
		}
	}
	request.setAttribute("reports", dirs);

%>
<body style="padding-left:30px">
	[<a href="index.jsp">测试首页</a>]<br/>
	*********[<%=project%>]Report列表*********<br>
	<c:forEach items="${reports }" var="report" varStatus="i">
		${i.index + 1}、<a target="_blank" href="download.jsp?p=<%=project %>&r=${report }">${report }</a>
		<br>
	</c:forEach>
	<c:if test="${empty reports }">
		暂无测试报表！
	</c:if>
</body>
</html>