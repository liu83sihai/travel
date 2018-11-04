<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>字典管理编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
</head>
<body>

<!-- 内容 -->
<form id="editLoanDictForm" method="post" action="<c:url value='/loandict/saveLoanDict.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="id" name="id" value="${loandict.id}"/>
					<input type="hidden" id="status" name="status" value="${loandict.status}"/>
					<input type="hidden" id="createUserId" name="createUserId" value="${loandict.createUserId}"/>
					<input type="hidden" id="updateUserId" name="updateUserId" value="${loandict.updateUserId}"/>
					<input type="hidden" id="ldDtl" name="ldDtl" value="${loandict.dtlList}"/>
<%-- 					<input type="hidden" id="updateTime" name="updateTime" value="${loandict.updateTime}"/> --%>
					<tr>	
						<td align="right">
							<label for="name">编码</label>
						</td>	
						<td>
								<input type="text" id="code" name="code" value="${loandict.code}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">名称</label>
						</td>	
						<td>
								<input type="text" id="name" name="name" value="${loandict.name}"/>												
						</td>						   
					</tr>
				
					<tr>	
						<td align="right">
							<label for="name">备注</label>
						</td>	
						<td>
								<input type="text" id="remark" name="remark" value="${loandict.remark}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">旅游卡</label>
						</td>	
						<td>
							<label><input name="travelCard"  type="checkbox"  style="width:40px" value="1" />现金 </label> 
							<label><input name="travelCard"  type="checkbox" style="width:40px" value="2" />积分 </label> 
							<label><input name="travelCard" type="checkbox"  style="width:40px" value="3" />现金账户 </label> 
							<label><input name="travelCard" type="checkbox"  style="width:40px" value="4" />券 </label> 
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">旅游路线</label>
						</td>	
						<td>
							<label><input name="travelLine"  type="checkbox"  style="width:40px" value="1" />现金 </label> 
							<label><input name="travelLine"  type="checkbox" style="width:40px" value="2" />积分 </label> 
							<label><input name="travelLine" type="checkbox"  style="width:40px" value="3" />现金账户 </label> 
							<label><input name="travelLine" type="checkbox"  style="width:40px" value="4" />券 </label> 
						
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">积分商品</label>
						</td>	
						<td>
							<label><input name="pointGood"  type="checkbox"  style="width:40px" value="1" />现金 </label> 
							<label><input name="pointGood"  type="checkbox" style="width:40px" value="2" />积分 </label> 
							<label><input name="pointGood" type="checkbox"  style="width:40px" value="3" />现金账户 </label> 
							<label><input name="pointGood" type="checkbox"  style="width:40px" value="4" />券 </label> 
					
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">会员商品</label>
						</td>	
						<td>
							<label><input name="vipGood"  type="checkbox"  style="width:40px" value="1" />现金 </label> 
							<label><input name="vipGood"  type="checkbox" style="width:40px" value="2" />积分 </label> 
							<label><input name="vipGood" type="checkbox"  style="width:40px" value="3" />现金账户 </label> 
							<label><input name="vipGood" type="checkbox"  style="width:40px" value="4" />券 </label> 
						
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">商家消费</label>
						</td>	
						<td>
							<label><input name="supplierSale"  type="checkbox"  style="width:40px" value="1" />现金 </label> 
							<label><input name="supplierSale"  type="checkbox" style="width:40px" value="2" />积分 </label> 
							<label><input name="supplierSale" type="checkbox"  style="width:40px" value="3" />现金账户 </label> 
							<label><input name="supplierSale" type="checkbox"  style="width:40px" value="4" />券 </label> 
						
						</td>						   
					</tr>
			</table>	   
		</div>	
	</form>


</body>
<script type="text/javascript">
$(document).ready(function () {
		$("input[value=<%=checkbox[is]%>]").attr("checked",true);    //根据jquery的选择器来添加选中复选框的属性
})
</script>
</html>