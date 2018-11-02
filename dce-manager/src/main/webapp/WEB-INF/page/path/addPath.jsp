<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	<form id="editPathForm" method="post">
		<div>
			<table width="100%" border="0" align="center" cellpadding="3">			  
					<input type="hidden" id="pathid" name="路线id" value="${travelpath.pathid}"/>
					<tr>	
						<td align="right">
							<label for="name">路线名称</label>
						</td>	
						<td>
								<input type="text" id="linename" name="linename" value="${travelpath.linename}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">开放状态 </label>
						</td>	
						<td>
<%-- 								<input type="text" id="state" name="state" value="${travelpath.state}"/> --%>
								<select id="state" name="state" class="easyui-combobox" style="width: 150px;">
								<option value="0"
									<c:if test="${travelpath.state==0 || travelpath.state == null  }">selected="selected"</c:if>>已开发</option>
								<option value="1"
									<c:if test="${travelpath.state==1  }">selected="selected"</c:if>>马上推出</option>
								<option value="2"
									<c:if test="${travelpath.state==2}">selected="selected"</c:if>>正在开发</option>
								</select>	
																		
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">备注</label>
						</td>	
						<td>
								<input type="text" id="remake" name="remake" value="${travelpath.remake}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">banner图片</label>
						</td>	
						<td>
						<input type="file" id="bannerImg" name="bannerImg" multiple="true"/>
<%-- 								<input type="text" id="bannerImg" name="bannerImg" value="${travelpath.bannerImg}"/>												 --%>
						<div style="margin-bottom:20px"id="addImage"></div>
						</td>						   
					</tr>
					
					<tr>	
						<td align="right">
							<label for="name">详情图片</label>
						</td>	
						<td>
<%-- 								<input type="text" id="detailImg" name="detailImg" value="${travelpath.detailImg}"/>												 --%>
						<input type="file" id="detailImg" name="detailImg" multiple="true"/>
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">列表小图</label>
						</td>	
						<td>
<%-- 								<input type="text" id="iconImg" name="iconImg" value="${travelpath.iconImg}"/>												 --%>
								<input type="file" id="iconImg" name="iconImg" multiple="true"/>
						
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">星级</label>
						</td>	
						<td>
								<input type="text" id="starLevel" name="starLevel" value="${travelpath.starLevel}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">价格</label>
						</td>	
						<td>
								<input type="text" id="price" name="price" value="${travelpath.price}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">评分</label>
						</td>	
						<td>
								<input type="text" id="score" name="score" value="${travelpath.score}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">概要</label>
						</td>	
						<td>
								<input type="text" id="outline" name="outline" value="${travelpath.outline}"/>												
						</td>						   
					</tr>
					<tr>	
						<td align="right">
							<label for="name">详情外部链接	</label>
						</td>	
						<td>
								<input type="text" id="detailUrl" name="detailUrl" value="${travelpath.detailUrl}"/>												
						</td>						   
					</tr>
			</table>
		</div>
	</form>

</body>

</html>