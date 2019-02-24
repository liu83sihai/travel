<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>cron表达式助手</title>
<%@ include file="html-head.jsp" %>
<style type="text/css">
#cronDocs p { font-size:12px; }
</style>
<script type="text/javascript">
var copyToForm=function(){
	if(window.opener==null){
		$e('copyToFormResult').innerHTML='<span style="color:red;">找不到window.opener</span>';
		return;
	}
	window.opener.$e('cronExpression').value=$e('cronExpression').value;
	window.opener.focus();
	$e('copyToFormResult').innerHTML='<span style="color:#363;">复制成功</span>';
}
</script>
</head>
<body>
<%@ include file="layout-head.jsp" %>
<h2>cron表达式测试器</h2>
<form action="" method="get">
  <table>
    <tr>
      <th align="right">cron表达式</th>
      <td><input id="cronExpression" name="cronExpression" type="text" value="${cronExpression}" style="width:250px;" />
        格式“秒 分 时 日 月 周 年(可选)”</td>
    </tr>
    <tr>
      <th align="right">测试时间</th>
      <td><fmt:formatDate var="formatedDate" pattern="yyyy-MM-dd HH:mm:ss" value="${date}"/>
        <input name="date" type="text" value="${formatedDate}" style="width:250px;" />
        如“2009-05-01 18:00:00”</td>
    </tr>
    <tr>
      <th></th>
      <td><input type="submit" value="测试" />
        <input type="button" value="复制到表单" onclick="copyToForm();" />
        <span id="copyToFormResult"></span></td>
    </tr>
  </table>
</form>
<c:if test="${not empty cronExpression}">
  <div>
    <h4 style="color:#363">测试结果</h4>
    <div>
      <s:actionmessage />
    </div>
    <c:if test="${empty actionMessages}">当前时间
      <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${nowTime}"/>
      <table width="400" cellpadding="4" cellspacing="1" border="1">
        <tr>
          <th>测试时间</th>
          <td>${formatedDate}</td>
        </tr>
        <tr>
          <th>是否会执行</th>
          <td>${satisfiedBy?'是':'否'}</td>
        </tr>
        <tr>
          <th>在测试时间之后第1次执行</th>
          <td><span style="color:#363;">
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${nextValidTime1}"/>
            </span></td>
        </tr>
        <tr>
          <th>在测试时间之后第2次执行</th>
          <td><span style="color:#363;">
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${nextValidTime2}"/>
            </span></td>
        </tr>
        <tr>
          <th>在测试时间之后第3次执行</th>
          <td><span style="color:#363;">
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${nextValidTime3}"/>
            </span></td>
        </tr>
        <tr>
          <th>在测试时间之后第4次执行</th>
          <td><span style="color:#363;">
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${nextValidTime4}"/>
            </span></td>
        </tr>
        <tr>
          <th>在测试时间之后第5次执行</th>
          <td><span style="color:#363;">
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${nextValidTime5}"/>
            </span></td>
        </tr>
        <tr>
          <th>在测试时间之后第6次执行</th>
          <td><span style="color:#363;">
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${nextValidTime6}"/>
            </span></td>
        </tr>
        <tr>
          <th>在测试时间之后第7次执行</th>
          <td><span style="color:#363;">
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${nextValidTime7}"/>
            </span></td>
        </tr>
        <tr>
          <th>在测试时间之后第8次执行</th>
          <td><span style="color:#363;">
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${nextValidTime8}"/>
            </span></td>
        </tr>
        <tr>
          <th>在测试时间之后第9次执行</th>
          <td><span style="color:#363;">
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${nextValidTime9}"/>
            </span></td>
        </tr>
        <tr>
          <th>在测试时间之后第10次执行</th>
          <td><span style="color:#363;">
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${nextValidTime10}"/>
            </span></td>
        </tr>
      </table>
    </c:if> </div>
</c:if>
<hr noshade="noshade" size="1" style="margin-top:80px;" />
<h2>cron表达式文档</h2>
<div id="cronDocs">
  <table border="1" cellspacing="1" cellpadding="5">
    <thead>
      <tr>
        <th>名称</th>
        <th>是否必须</th>
        <th>允许值</th>
        <th>特殊字符</th>
      </tr>
    </thead>
    <tr>
      <td>秒</td>
      <td>是</td>
      <td>0-59</td>
      <td>, - * /</td>
    </tr>
    <tr>
      <td>分</td>
      <td>是</td>
      <td>0-59</td>
      <td>, - * /</td>
    </tr>
    <tr>
      <td>时</td>
      <td>是</td>
      <td>0-23</td>
      <td>, - * /</td>
    </tr>
    <tr>
      <td>日</td>
      <td>是</td>
      <td>1-31</td>
      <td>, - * ? / L W C</td>
    </tr>
    <tr>
      <td>月</td>
      <td>是</td>
      <td>1-12 或 JAN-DEC</td>
      <td>, - * /</td>
    </tr>
    <tr>
      <td>周</td>
      <td>是</td>
      <td>1-7 或 SUN-SAT</td>
      <td>, - * ? / L C #</td>
    </tr>
    <tr>
      <td>年</td>
      <td>否</td>
      <td>空 或 1970-2099</td>
      <td>, - * /</td>
    </tr>
  </table>
  <h5>* 星号</h5>
  <p>使用星号(*) 指示着你想在这个域上包含所有合法的值。<br />
    表达式样例：<br />
    0 * 17 * * ?<br />
    意义：每天从下午5点到下午5:59中的每分钟激发一次 trigger。它停在下午 5:59 是因为值 17 在小时域上，在下午 6 点时，小时变为 18 了，也就不再理会这个 trigger，直到下一天的下午5点。</p>
  <h5>? 问号</h5>
  <p>? 号只能用在日和周域上，但是不能在这两个域上同时使用。你可以认为 ? 字符是 "我并不关心在该域上是什么值。" 这不同于星号，星号是指示着该域上的每一个值。? 是说不为该域指定值。<br />
    表达式样例：<br />
    0 10,44 14 ? 3 WEB<br />
    意义：在三月中的每个星期三的下午 2:10 和 下午 2:44 被触发。</p>
  <h5>, 逗号</h5>
  <p>? 号只能用在日和周域上，但是不能在这两个域上同时使用。你可以认为 ? 字符是 "我并不关心在该域上是什么值。" 这不同于星号，星号是指示着该域上的每一个值。? 是说不为该域指定值。<br />
    表达式样例：<br />
    0 10,44 14 ? 3 WEB<br />
    意义：每刻钟触发一次 trigger。</p>
  <h5>/ 斜杠</h5>
  <p>斜杠 (/) 是用于时间表的递增的。<br />
    表达式样例：<br />
    0/15 0/30 * * * ?<br />
    意义：在整点和半点时每15秒触发 trigger。</p>
  <h5>- 中划线</h5>
  <p>中划线 (-) 用于指定一个范围。例如，在小时域上的 3-8 意味着 "3,4,5,6,7 和 8 点。"  域的值不允许回卷，所以像 50-10 这样的值是不允许的。<br />
    表达式样例：<br />
    0 45 3-8 ? * *<br />
    意义：在上午的3点至上午的8点的45分时触发 trigger。</p>
  <h5>L 字母</h5>
  <p>L 说明了某域上允许的最后一个值。它仅被日和周域支持。<br />
    当用在日域上，表示的是在月域上指定的月份的最后一天。<br />
    表达式 0 0 8 L * ? 意义是在每个月最后一天的上午 8:00 触发 trigger。在月域上的 * 说明是 "每个月"。<br />
    当 L 字母用于周域上，指示着周的最后一天，就是星期六 (或者数字7)。<br />
    所以如果你需要在每个月的最后一个星期六下午的 11:59 触发 trigger，你可以用这样的表达式 0 59 23 ? * L。<br />
    当使用于周域上，你可以用一个数字与 L 连起来表示月份的最后一个星期 X。例如，表达式 0 0 12 ? * 2L 说的是在每个月的最后一个星期一触发 trigger。</p>
  <h5>W 字母</h5>
  <p>W 字符代表着平日 (Mon-Fri)，并且仅能用于日域中。它用来指定离指定日的最近的一个平日。大部分的商业处理都是基于工作周的，所以 W 字符可能是非常重要的。<br />
    例如，日域中的 15W 意味着 "离该月15号的最近一个工作日。"<br />
    W 只能用在指定的日域为单天，不能是范围或列表值。</p>
  <h5># 井号</h5>
  <p># 字符仅能用于周域中。它用于指定月份中的第几周的哪一天。<br />
    例如，如果你指定周域的值为 6#3，它意思是某月的第三个周五 (6=星期五，#3意味着月份中的第三周)。<br />
    另一个例子 2#1 意思是某月的第一个星期一 (2=星期一，#1意味着月份中的第一周)。<br />
    注意，假如你指定 #5，然而月份中没有第 5 周，那么该月不会触发。</p>
</div>
</body>
</html>