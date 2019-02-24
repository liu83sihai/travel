<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
参数：
pageHolderName：PageHolder在requestScope里面的名字，默认为"pageHolder"。
--%>
<%
	String pageHolderName=request.getParameter("pageHolderName");
	if(pageHolderName==null){
		pageHolderName="pageHolder";
	}
	pageContext.setAttribute("pageHolder",request.getAttribute(pageHolderName));
%>
<div>
  <c:choose>
    <c:when test="${pageHolder.firstPage}">[首页] [上一页]</c:when>
    <c:otherwise>
      <c:url var="firstPage" value="">
        <c:param name="${pageHolder.pageIndexKey}" value="1"/>
      </c:url>
      <c:url var="previousPage" value="">
        <c:param name="${pageHolder.pageIndexKey}" value="${pageHolder.pageIndex-1}"/>
      </c:url>
      [<a href="${firstPage}${pageHolder.params}">首页</a>] [<a href="${previousPage}${pageHolder.params}">上一页</a>]</c:otherwise>
  </c:choose>
  <c:choose>
    <c:when test="${pageHolder.lastPage}">[下一页]</c:when>
    <c:otherwise>
      <c:url var="nextPage" value="">
        <c:param name="${pageHolder.pageIndexKey}" value="${pageHolder.pageIndex+1}"/>
      </c:url>
      <c:url var="lastPage" value="">
        <c:param name="${pageHolder.pageIndexKey}" value="${pageHolder.pageCount}"/>
      </c:url>
      [<a href="${nextPage}${pageHolder.params}">下一页</a>]</c:otherwise>
  </c:choose>
  第${pageHolder.pageIndex}页 
  <a href="javascript:location.reload()">刷新</a></div>
