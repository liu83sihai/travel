<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>编辑字典</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
</head>
<body>

<!-- 内容 -->
<form id="editAwardlistForm" method="post" action="<c:url value='awardlist/saveAwardlist.html'/>"> 
		<div>
		
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="awardid" name="awardid" value="${awardlist.awardid}"/>
					<tr>	
						<td align="right">
							<label for="name">购买者等级：</label>
						</td>	
						<td>
								<input type="text" id="buyerLecel" name="buyerLecel" value="${awardlist.buyerLecel}" style="width: 237px"/>												
						</td>	
						<td>输入格式：0(普通用户) 1(会员用户) 2(VIP用户) 3(城市合伙人) 4(股东)</td>					   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">第一代推荐人普通等级奖励：</label>
						</td>	
						<td>
								<input type="text" id="p1Level0" name="p1Level0" value="${awardlist.p1Level0}" style="width: 237px"/>												
						</td>						   
						<td>输入格式："0,waller_money",无如何奖励</td>
					</tr>
					<tr>	
						<td align="right">
							<label for="name">第一代推荐人会员等级奖励：</label>
						</td>	
						<td>
								<input type="text" id="p1Level1" name="p1Level1" value="${awardlist.p1Level1}" style="width: 237px"/>												
						</td>
						<td>输入格式："300,wallet_money",无论买多少商品数量奖励300元</td>							   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">第一代推荐人VIP等级奖励：</label>
						</td>	
						<td>
								<input type="text" id="p1Level2" name="p1Level2" value="${awardlist.p1Level2}" style="width: 237px"/>												
						</td>	
						<td>输入格式："300+(n-1)*300,wallet_money",首先奖励300元，对于多余的数量按300元每盒奖励</td>					   
					</tr>
					<tr>	
						<td align="right" style="width: 185px;">
							<label for="name">第一代推荐人城市合伙人等级奖励:</label>
						</td>	
						<td>
								<input type="text" id="p1Level3" name="p1Level3" value="${awardlist.p1Level3}" style="width: 237px"/>												
						</td>	
						<td>输入格式："300+(n-1)*300,wallet_money",首先奖励300元，对于多余的数量按300元每盒奖励</td>					   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">第一代推荐人股东等级奖励:</label>
						</td>	
						<td>
								<input type="text" id="p1Level4" name="p1Level4" value="${awardlist.p1Level4}" style="width: 237px"/>												
						</td>	
						<td>输入格式："300+(n-1)*300,wallet_money",首先奖励300元，对于多余的数量按300元每盒奖励</td>					   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">第二代推荐人普通等级奖励:</label>
						</td>	
						<td>
								<input type="text" id="p2Level0" name="p2Level0" value="${awardlist.p2Level0}" style="width: 237px"/>												
						</td>
						<td>输入格式："300+(n-1)*300,wallet_money",首先奖励300元，对于多余的数量按300元每盒奖励</td>					   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">第二代推荐人会员等级奖励:</label>
						</td>	
						<td>
								<input type="text" id="p2Level1" name="p2Level1" value="${awardlist.p2Level1}" style="width: 237px"/>												
						</td>	
						<td>输入格式："300+(n-1)*300,wallet_money",首先奖励300元，对于多余的数量按300元每盒奖励</td>					   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">第二代推荐人VIP等级奖励:</label>
						</td>	
						<td>
								<input type="text" id="p2Level2" name="p2Level2" value="${awardlist.p2Level2}" style="width: 237px"/>												
						</td>
						<td>输入格式："300+(n-1)*300,wallet_money",首先奖励300元，对于多余的数量按300元每盒奖励</td>					   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">第二代推荐人城市合伙人等级奖励:</label>
						</td>	
						<td>
								<input type="text" id="p2Level3" name="p2Level3" value="${awardlist.p2Level3}" style="width: 237px"/>												
						</td>	
						<td>输入格式："300+(n-1)*300,wallet_money",首先奖励300元，对于多余的数量按300元每盒奖励</td>					   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">第二代推荐人股东等级奖励:</label>
						</td>	
						<td>
								<input type="text" id="p2Level4" name="p2Level4" value="${awardlist.p2Level4}" style="width: 237px"/>												
						</td>	
						<td>输入格式："300+(n-1)*300,wallet_money",首先奖励300元，对于多余的数量按300元每盒奖励</td>					   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">购买者奖励:</label>
						</td>	
						<td>
								<input type="text" id="buyerAward" name="buyerAward" value="${awardlist.buyerAward}" style="width: 237px"/>												
						</td>
						<td>输入格式："1-wallet_travel-isFirst-A001-2人港澳游 ",1 代表次数 "2人港澳游" 代表奖励活动名称</td>					   
					</tr>
					<%-- <tr>	
						<td align="right">
							<label for="name">buyQty</label>
						</td>	
						<td>
								<input type="text" id="buyQty" name="buyQty" value="${awardlist.buyQty}"/>												
						</td>						   
					</tr> --%>
		
			</table>	   
		</div>	
	</form>
	<script type="text/javascript">
		
    function awardlist_submit(){
    	$.ajax({ 
    			url: "<c:url value='/awardlist/saveAwardlist.html'/>", 
    			data: $("#editAwardlistForm").serialize(),
    			type:"post",
    			dataType:"json",
    			success: function(ret){
    	   	 		if(ret.code==="0"){
    	   	 			$.messager.confirm("保存成功",
    	   	 				           '是否继续添加？', 
    	   	 				           function(r){
						   	   			   if(r==false){
						   	   				$("#editAwardlistDiv").dialog("close");
						   	   			   }
    	   						});
    	   	 		}else{
    	   	 			$.messager.alert("error",ret.msg);
    	   	 		}
    	      	}
    	        });
    }
	
</script>


</body>

</html>