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
	String report = request.getParameter("r");
	File file = new File(basePath + "test/report/" + project + "/" +report); 
	
	response.reset();
	response.setContentType("application/x-download");
	response.addHeader("Content-Disposition","attachment;filename=" + report);
  	java.io.OutputStream outp = null;
  	java.io.FileInputStream in = null;
	try {
		outp = response.getOutputStream();
		in = new FileInputStream(file);
	  	byte[] b = new byte[1024];
		int i = 0;
		while((i = in.read(b)) > 0) {
		  outp.write(b, 0, i);
		}
		outp.flush();
		//out.clear();
		out = pageContext.pushBody();
		//outp.close();
		in.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
<body style="padding-left:30px">
</body>
</html>