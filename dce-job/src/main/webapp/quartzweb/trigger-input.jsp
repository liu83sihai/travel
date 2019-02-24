<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${operation=='update'?'修改调度':'新建调度'}</title>
<%@ include file="html-head.jsp" %>
<style type="text/css" media="all">
fieldset { border:#ccc solid 1px }
</style>
<script type="text/javascript" language="javascript">
var selectRepeatType=function(id){
	$e('repeatTypeDailyFieldset').style.display=(id=='repeatTypeDailyFieldset'?'':'none');
	$e('repeatTypeWeeklyFieldset').style.display=(id=='repeatTypeWeeklyFieldset'?'':'none');
	$e('repeatTypeMonthlyFieldset').style.display=(id=='repeatTypeMonthlyFieldset'?'':'none');
}
window.onload=function(){
	$e('testCronLink').onclick=function(){
		this.href='test-cron.do?cronExpression='+$e('cronExpression').value;
	};
	$e('repeatCount').onblur=function(){
		if(this.value==0){
			$e('repeatInterval').value=0;
		}
	}
	$e('triggerForm').onsubmit=function(){
		if(!$e('triggerName').value){
			alert('请填写调度名称');
			return false;
		}
		if(!$e('triggerTypeSimple').checked && !$e('triggerTypeCron').checked){
			alert('请选择调度类型');
			return false;
		}
		if($e('triggerTypeSimple').checked){
			if(!$e('repeatCount').value){
				alert('请填写重复次数');
				return false;
			}
			if(!$e('repeatInterval').value){
				alert('请填写重复间隔');
				return false;
			}
		}else if($e('triggerTypeCron').checked){
			if(!$e('cronExpression').value){
				alert('请填写cron表达式');
				return false;
			}
		}
	};
	$e('triggerTypeSimple').onclick=function(){
		$e('repeatCount').style.backgroundColor='white';
		$e('repeatInterval').style.backgroundColor='white';
		$e('repeatIntervalUnit').style.backgroundColor='white';
		$e('cronExpression').style.backgroundColor='#ccc';
		$e('repeatCount').disabled=null;
		$e('repeatInterval').disabled=null;
		$e('repeatIntervalUnit').disabled=null;
		$e('cronExpression').disabled='disabled';
	};
	$e('triggerTypeCron').onclick=function(){
		$e('repeatCount').style.backgroundColor='#ccc';
		$e('repeatInterval').style.backgroundColor='#ccc';
		$e('repeatIntervalUnit').style.backgroundColor='#ccc';
		$e('cronExpression').style.backgroundColor='white';
		$e('repeatCount').disabled='disabled';
		$e('repeatInterval').disabled='disabled';
		$e('repeatIntervalUnit').disabled='disabled';
		$e('cronExpression').disabled=null;
	};
	//初始化控件
	if($e('triggerTypeSimple').checked){
		$e('triggerTypeSimple').click();
	}
	if($e('triggerTypeCron').checked){
		$e('triggerTypeCron').click();
	}
};
</script>
</head>
<body>
<%@ include file="layout-head.jsp" %>
<h2>任务${jobDetail.group}.${jobDetail.name}</h2>
<div style="padding:10px;"><a href="job-detail.do?group=${jobDetail.group}&amp;name=${jobDetail.name}">任务属性</a> <a href="job-detail-triggers.do?group=${jobDetail.group}&amp;name=${jobDetail.name}">任务调度</a> <a href="history-list.do?group=${jobDetail.group}&name=${jobDetail.name}">任务日志</a>
  <c:choose>
    <c:when test="${operation=='update'}"><a href="trigger-insert-input.do?group=${jobDetail.group}&name=${jobDetail.name}">新建调度</a></c:when>
    <c:otherwise>新建调度</c:otherwise>
  </c:choose>
</div>
<form id="triggerForm" action="${operation=='update'?'trigger-update.do':'trigger-insert.do'}" method="post">
  <input name="jobDetailBeanName" type="hidden" value="${jobDetailBeanName}" />
  <c:if test="${operation=='update'}">
    <input name="oldTriggerGroup" type="hidden" value="${oldTriggerGroup}" />
    <input name="oldTriggerName" type="hidden" value="${oldTriggerName}" />
  </c:if>
  <table width="100%" border="1" cellspacing="1" cellpadding="4">
    <tr>
      <th width="100">调度名称</th>
      <td><input name="triggerName" id="triggerName" type="text" value="${triggerName}" />
        <span style="color:red">*</span></td>
    </tr>
    <tr>
      <th width="100">调度分类</th>
      <td><input name="triggerGroup" type="text" value="${triggerGroup}" /></td>
    </tr>
    <tr>
      <th width="100">调度设置</th>
      <td style="background-color: #c8ebc5"><div>
          <c:choose>
            <c:when test="${triggerType=='simpleTrigger'}">
              <input name="triggerType" id="triggerTypeSimple" type="radio" value="simpleTrigger" checked="checked" />
            </c:when>
            <c:otherwise>
              <input name="triggerType" id="triggerTypeSimple" type="radio" value="simpleTrigger" />
            </c:otherwise>
          </c:choose>
          <label for="triggerTypeSimple">简单设置</label>
          ：重复次数
          <input name="repeatCount" id="repeatCount" value="${repeatCount>0 ? repeatCount : '0'}" type="text" style="width:80px;background-color:#ccc" disabled="disabled" />
          重复间隔
          <input name="repeatInterval" id="repeatInterval" value="${repeatInterval>0 ? repeatInterval : '0'}" type="text" style="width:80px;background-color:#ccc" disabled="disabled" />
          <select name="repeatIntervalUnit" id="repeatIntervalUnit" style="background-color:#ccc" disabled="disabled">
            <option value="1" ${repeatIntervalUnit==1?'selected':''}>毫秒</option>
            <option value="1000" ${repeatIntervalUnit==1000?'selected':''}>秒</option>
            <option value="60000" ${repeatIntervalUnit==60000 or empty repeatIntervalUnit?'selected':''}>分</option>
            <option value="3600000" ${repeatIntervalUnit==3600000?'selected':''}>时</option>
            <option value="86400000" ${repeatIntervalUnit==86400000?'selected':''}>天</option>
          </select>
          <span style="color:red">*</span> 重复0次表示执行1次，重复n次表示执行n+1次。</div>
        <div>
          <c:choose>
            <c:when test="${triggerType=='cronTrigger'}">
              <input name="triggerType" id="triggerTypeCron" type="radio" value="cronTrigger" checked="checked" />
            </c:when>
            <c:otherwise>
              <input name="triggerType" id="triggerTypeCron" type="radio" value="cronTrigger" />
            </c:otherwise>
          </c:choose>
          <label for="triggerTypeCron">用cron表达式设置</label>
          ：
          <input name="cronExpression" id="cronExpression" value="${cronExpression}" type="text" style="width:300px;background-color:#ccc" disabled="disabled" />
          <span style="color:red">*</span> <a id="testCronLink" href="test-cron.do" target="test-cron">cron表达式助手</a></div>
        <div style="margin-top:5px; color:red;">每天23:00-23:30是数据库维护时间，请不要设置调度在这段时间执行。</div></td>
    </tr>
    <tr>
      <th width="100">开始时间</th>
      <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${startTime}" var="formatedStartTime" />
        <input name="startTime" type="text" value="${formatedStartTime}" />
        如“2009-5-1 10:00:00”</td>
    </tr>
    <tr>
      <th width="100">结束时间</th>
      <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${endTime}" var="formatedEndTime" />
        <input name="endTime" type="text" value="${formatedEndTime}" />
        如“2009-5-3 20:00:00”</td>
    </tr>
    <tr>
      <th width="100">熄火期</th>
      <td><select name="calendarName">
          <option value="">请选择</option>
          <c:forEach items="${calendarNames}" var="item">
            <c:choose>
              <c:when test="${calendarName==item}">
                <option value="${item}" selected="selected">${item}</option>
              </c:when>
              <c:otherwise>
                <option value="${item}">${item}</option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </select></td>
    </tr>
    <tr>
      <th width="100">优先级</th>
      <td><select name="priority">
          <option value="1" ${priority==1?'selected':''}>最低</option>
          <option value="2" ${priority==2?'selected':''}>2</option>
          <option value="3" ${priority==3?'selected':''}>3</option>
          <option value="4" ${priority==4?'selected':''}>4</option>
          <option value="5" ${(priority==5 or empty priority)?'selected':''}>正常</option>
          <option value="6" ${priority==6?'selected':''}>6</option>
          <option value="7" ${priority==7?'selected':''}>7</option>
          <option value="8" ${priority==8?'selected':''}>8</option>
          <option value="9" ${priority==9?'selected':''}>9</option>
          <option value="10" ${priority==10?'selected':''}>10</option>
          <option value="11" ${priority==11?'selected':''}>11</option>
          <option value="12" ${priority==12?'selected':''}>12</option>
          <option value="13" ${priority==13?'selected':''}>13</option>
          <option value="14" ${priority==14?'selected':''}>14</option>
          <option value="15" ${priority==15?'selected':''}>15</option>
          <option value="16" ${priority==16?'selected':''}>16</option>
          <option value="17" ${priority==17?'selected':''}>17</option>
          <option value="18" ${priority==18?'selected':''}>18</option>
          <option value="19" ${priority==19?'selected':''}>19</option>
          <option value="20" ${priority==20?'selected':''}>20</option>
          <option value="21" ${priority==21?'selected':''}>21</option>
          <option value="22" ${priority==22?'selected':''}>22</option>
          <option value="23" ${priority==23?'selected':''}>23</option>
          <option value="24" ${priority==24?'selected':''}>24</option>
          <option value="25" ${priority==25?'selected':''}>25</option>
          <option value="26" ${priority==26?'selected':''}>26</option>
          <option value="27" ${priority==27?'selected':''}>27</option>
          <option value="28" ${priority==28?'selected':''}>28</option>
          <option value="29" ${priority==29?'selected':''}>29</option>
          <option value="30" ${priority==30?'selected':''}>30</option>
        </select></td>
    </tr>
    <%-- <c:choose>
      <c:when test="${jobDetail.class.name=='com.hehenian.scheduling.quartzweb.workflow.SimpleWorkFlowJobDetailBean'}"></c:when>
      <c:otherwise> --%>
        <tr>
          <th>数据</th>
          <td><c:forEach items="${jobDetail.jobDataKeys}" var="item" varStatus="status"> ${item}:<br />
              <c:set var="parameterKey" value="jobData_${item}" />
              <input name="jobData_${item}" value="${not empty parameters[parameterKey]?parameters[parameterKey][0]:jobDetail.jobDataMap[item]}" style=" width:100%;" />
              <br />
            </c:forEach></td>
        </tr>
     <%--  </c:otherwise>
    </c:choose> --%>
    <tr>
      <th>描述</th>
      <td><textarea name="description" cols="50" rows="5">${fn:escapeXml(description)}</textarea></td>
    </tr>
    <tr>
      <th></th>
      <td><input type="submit" value="提交" /></td>
    </tr>
  </table>
</form>
</body>
</html>