<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>调度参数历史查询结果</title>
<%@ include file="html-head.jsp" %>
<link href="/css/colorbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/jquery.colorbox-min.js"></script>
<script type="text/javascript" language="javascript">
function showColorBox(jobName,step,actionTime,userName){
	var url="job-parameter-history-compare.do?parame.jobName="+jobName+"&parame.step="+step+"&parame.actionTime="+actionTime+"&parame.userName="+userName;
	url=encodeURI(url);
	 $.colorbox({
		 width:'700', 
		 innerWidth:'680',
		 height:'400', 
		 opacity:'0.25',
		 iframe:true,
		 href:url, 
	     overlayClose:false,
	     transition:"none",
	     scrolling:true,
	     preloading:true
	     }
	 );
}
</script>
</head>
  <body>
    <div class="lbtit">
    <jsp:include page="/global/pageholder-default.jsp"/>
    </div>
    <table width="100%" cellpadding="5" cellspacing="1" border="1">
         <thead>
         <tr>
          <th>任务名称</th>
          <th>步骤</th>
          <th>参数名称</th>
          <th>参数值</th>
          <th>操作人</th>
          <th>操作时间</th>
          <th>操作类型</th>
          </tr>
         </thead>
         <tbody>
    	<c:forEach items="${pageHolder.list}" var="item">
    	    <tr>
              <td>${item.jobName}</td>
              <td>${item.step}</td>
              <td>${item.parameterName}</td>
              <td style="width:600px;word-break : break-all;">${item.parameterValue}</td>
              <td>${item.userName}</td>
              <td>
                <fmt:formatDate value='${item.actionTime}' pattern='yyyy-MM-dd HH:mm:ss' var="actionTime"/>
                ${actionTime }
               </td>
               <td width="100">
                  <c:if test="${item.actionType==1}">新建</c:if>
                  <c:if test="${item.actionType==2}">删除</c:if>
                  <c:if test="${item.actionType==3}">修改前</c:if>
                  <c:if test="${item.actionType==4}">修改后</c:if>
                  <c:if test="${item.actionType==4 ||item.actionType==3 }">
                   <a  href="#"  onclick="showColorBox('${item.jobName}','${item.step}','${actionTime}','${item.userName}')">对比一下</a>
                  </c:if>
               </td>
              </tr>
    	</c:forEach>
    	</tbody>
    </table>
     <div class="lbtit">
    <jsp:include page="/global/pageholder-default.jsp"/>
    </div>
  </body>
</html>
