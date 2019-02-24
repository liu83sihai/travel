<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@ include file="html-head.jsp"%>
		<title>修改详情</title>
<style>
 body{
  padding:5px;
  width:650px;
 }
.colorBlock {
	margin-top:10px;
}
.colorBlock ul{
   padding:0px;
   margin:0px;
   }
.colorBlock ul li{
   list-style-type: none; 
   float:left; 
   font-size:12px;
   margin-right:5px;
 }
.colorBlock ul li div{
   font-size:2px;
   float:left;
   width:10px;
   height:10px;
   padding:0px;
   border:1px solid #000;
   }
</style>
<script>
 $(function(){
	 var tableObj = document.getElementById("myTable"); 
	 for(var i=1;i<tableObj.rows.length;i++)
	 {
		 var oldValue=tableObj.rows[i].cells[1].innerHTML;
		 var newValue=tableObj.rows[i].cells[2].innerHTML;
		 if(oldValue!=""&&newValue!=""){
		  if(oldValue!=newValue){
			 tableObj.rows[i].cells[2].style.color="red";
			 tableObj.rows[i].cells[2].style.fontWeight="bold";
		  }
		 }else if(oldValue!=""&&newValue==""){
			 tableObj.rows[i].style.background="#FFE7E7";   
			 tableObj.rows[i].cells[0].style.textDecoration="line-through";
		 }else if(oldValue==""&&newValue!=""){
			 tableObj.rows[i].style.background="#CCE8CF";  
		 }
	 }
 });
</script>
	</head>
	<body>
		<div class="topDiv">
		    <div>调度名称：${parame.jobName} 步骤：${parame.step} 修改人：${parame.userName} 时间：${parame.actionTime}</div>
		</div>
		<div class="colorBlock">
			<ul>
				<li>
					<div style="background: red;"></div>修改
				</li>
				<li>
					<div style="background: #CCE8CF;"></div>添加
				</li>
				<li>
					<div style="background: #FFE7E7;"></div>删除
				</li>
				<li>
					<div style="background: #FFF;"></div>不变
				</li>
			</ul>
		</div>
		<table width="100%" cellpadding="5" cellspacing="1" border="1" id="myTable">
			<thead>
				<tr>
					<th>
						参数名称
					</th>
					<th>
						旧参数值
					</th>
					<th>
						新参数值
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${parameterNameSet}" var="itemKey">
					<tr>
						<td width="120">
							${itemKey}
						</td>
						<td style="width:260px;word-break : break-all;">
							<c:forEach items="${leftList}" var="old">
							     <c:if test="${itemKey==old.parameterName}">
				                     ${old.parameterValue}
			                      </c:if>
								
							</c:forEach>
						</td>
						<td style="width:260px;word-break : break-all;">
							<c:forEach items="${rightList}" var="new">
								<c:if test="${itemKey==new.parameterName}">
						           ${new.parameterValue}
						        </c:if>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</body>
</html>