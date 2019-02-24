<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="jsp-head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分配任务参数</title>
<%@ include file="html-head.jsp" %>
<script type="text/javascript" language="javascript">
var jobNameOld = "${jobName}"
var stepOld = "${step}"

var paramStr=[ <c:forEach items="${jobParameterTypesLists}" var="item" varStatus="status">
                 <c:if test="${status.index>0}">,</c:if>
                 {orderLetter:"${item.orderLetter}",parameName:"${item.parameName}",parameNameLabel:"${item.parameNameLabel}",parameType:"${item.parameType}",parameValue:"${item.parameValue}",parameValueLabel:"${item.parameValueLabel}",defaultValue:"${item.defaultValue}"}
                 </c:forEach>	
			   ];
var orderLetters=["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"];

function getOptions(checkedParam){
  var str="<option value='-1'>========请选择参数========</option>";
 for(j=0;j<orderLetters.length;j++){
   str+='<optgroup label="'+orderLetters[j]+'">';
   for(i=0;i<paramStr.length;i++){
	 var name=paramStr[i].parameName;
	 var label=paramStr[i].parameNameLabel;
	 if(paramStr[i].orderLetter==orderLetters[j].toLocaleLowerCase()){
		 str+="<option value='"+name+"'";
		  if(name==checkedParam){
	       str += " selected "
	      }
	     str += ">("+name+") "+label+"</option>";
	 }
    }
   str+='</optgroup>';
  }
  return str;
}
var optionHTML=getOptions('');
var rowCount = ${not empty jobParameterList ? fn:length(jobParameterList) : 0};

function addRow(){
rowCount++;
var tab1 = $e('tab1');
var newTr = tab1.insertRow();
var newTd1 = newTr.insertCell();
var newTd2 = newTr.insertCell();
var newTd3 = newTr.insertCell();
//var newTd4 = newTr.insertCell();

newTd1.colSpan=2;
newTd1.align="right";
newTd1.innerHTML = "<select name='parameterName_" + rowCount + "' onchange='updateHtml(this.value,"+rowCount+")'>" + optionHTML + "</select>";
newTd2.innerHTML = "没有选择参数";
//newTd3.innerHTML = "没有选择参数";
newTd3.innerHTML = "<input type='radio' name='isUse_" + rowCount + "' value='1' checked/>是&nbsp;&nbsp;<input type='radio' name='isUse_" + rowCount + "' value='0'/>否";
}

function deleteRow(){
  if(rowCount > 0){
    rowCount--;
    tab1.deleteRow(rowCount + 3);
  }
}

function initRow(){
  if(rowCount == 0){
    for(i = 0; i < 6; i++){
      addRow();
    }
  }
}

function getObjByParameName(parameName){
	for(i=0;i<paramStr.length;i++){
	  if(paramStr[i].parameName==parameName){
		return  paramStr[i];
	  }	
	}
}

function updateHtml(parameName,rowCount){
 var tab1 = $e('tab1');
 if(parameName=="-1"){
  tab1.rows[rowCount+2].cells[1].innerHTML="没有选择参数";
  return ;	 
 }
 var obj=getObjByParameName(parameName);
 var checkedParam=obj.defaultValue;
 html=createChildHtml(obj,rowCount,checkedParam);
 tab1.rows[rowCount+2].cells[1].innerHTML=html;
}

function createChildHtml(obj,rowCount,checkedParam){
 var parameType=obj.parameType;
 var parameValue=obj.parameValue;
 var parameValueLabel=obj.parameValueLabel;
 var html="";
 var hadLabel=false;
 if(parameValue){
  parameValue=parameValue.split("|");	 
 }
 if(parameValueLabel){
   parameValueLabel=parameValueLabel.split("|");	
   hadLabel=true;
 }
 var html="";
 var name='parameterValue_'+rowCount;
 if(parameType=="text"){
	 html='<input type="text" name="'+name+'" ';
	 if(checkedParam){
		 html+='value="'+checkedParam+'"';
	 }
	 html+='/>';
 }else if(parameType=="select"){
	 html='<select name="'+name+'"><option value="-1">请选择</option>';
	     for(i=0;i<parameValue.length;i++){
		   html+='<option value="'+parameValue[i]+'"';
		   if(checkedParam.toLocaleLowerCase()==parameValue[i].toLocaleLowerCase()){
			  html+=' selected ';   
		   }
		   html+='>';
		   html+=hadLabel?parameValueLabel[i]:parameValue[i];
		   html+='</option>';
		 }
	 html+='<select>';
 }else if(parameType=="radio"){
	 for(i=0;i<parameValue.length;i++){
	   html+='<input type="radio" name="'+name+'" value="'+parameValue[i]+'"';
	   if(checkedParam.toLocaleLowerCase()==parameValue[i].toLocaleLowerCase()){
		   html+=' checked ';
	   }
	   html+='/>';
	   html+='<label>'+hadLabel?parameValueLabel[i]:parameValue[i]+'</label>';
	 }
 }else if(parameType=="textarea"){
     html='<textarea name="'+name+'" rows="3" cols="40">';	 
	 if(checkedParam){
		 html+=checkedParam;
	 }
	 html+='</textarea>';
 }else{
	 html='<input type="text" name="'+name+'" ';
	 if(checkedParam){
		 html+='value="'+checkedParam+'"';
	 }
	 html+='/>';
 }
	return html;
}

function delCurRow(obj){
	$(obj).parent().parent().remove();
}
function doSubmit(){
  if(!$e('jobName').value){
    alert('请填写任务名称');
    $e('jobName').focus();
    return false;
  }
  if(!$e('step').value){
    alert('请填写步骤');
    $e('step').focus();
    return false;
  }
  if(jobNameOld == $e('jobName').value && stepOld == $e('step').value){
    if('${operation}' == 'update'){
      if(!confirm('确定要用现有参数替换原有参数吗？')){
        return false;
      }
    }else{
      alert("相同任务的相同步骤已经存在，请输入其它任务名或步骤名。");
      return false;
    }
  }
  
  return true;
}
</script>

</head>
  
  <body onload="initRow()">
  <%@ include file="layout-head.jsp" %>
    <h2>分配任务参数：</h2>
    <form action="${operation=='update'?'job-parameter-update.do':'job-parameter-insert.do'}" method="post" onsubmit="return doSubmit();">
    <table id="tab1" width="100%" cellpadding="5" cellspacing="1" border="1">
    	<tr>
    		<th>任务名称</th><td colspan="3"><input type="text" name="jobName" value="${jobName}" /></td> 
    	</tr>
    	<tr>
    		<th>步骤</th><td colspan="3"><input type="text" name="step" value="${step}" /></td> 
    	</tr>
    	<tr>
    	<th>参数名称(英文)</th><th>参数名称(中文)</th><th>参数值</th><th>是否在用</th>
    	</tr>
    	<c:forEach items="${jobParameterList}" var="item" varStatus="status">
    		<tr>
    		   <td>
    		    <input type="hidden" name="parameterName_${status.index + 1}" value="${item.parameterName}" />${item.parameterName}
    		   </td>
    			<td> 
    			  <script type="text/javascript" language="javascript">
    			      var obj=getObjByParameName("${item.parameterName}");
    			      var html='<label>'+obj.parameNameLabel+'</label>';
    			          document.writeln(html);
    			  </script>
    			</td>
    			<td>
    			<script type="text/javascript" language="javascript">
    			  var obj=getObjByParameName("${item.parameterName}");
    			  var html=createChildHtml(obj,${status.index + 1},"${item.parameterValue}");
    			  document.writeln(html);
    			 </script>
    			</td>
    			<td>
    			<input type="radio" name="isUse_${status.index + 1}" value="1" <c:if test="${item.isUse == 1}">checked</c:if>/>是&nbsp;&nbsp;<input type="radio" name="isUse_${status.index + 1}" value="0" <c:if test="${item.isUse == 0}">checked</c:if>/>否
    			</td>
    		</tr> 
    	</c:forEach>   	
    </table>
    <br />
    <div align="center">
    <input type="button" value="增加一行" onclick="addRow()" />&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" value="删除一行" onclick="deleteRow()" />&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="submit" value="提 交" />
    </div>
    </form>
  </body>
</html>
