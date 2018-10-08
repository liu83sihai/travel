<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<html lang="zh">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>提现审核</title>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="<c:url value='/css/bootstrap.css'/>">

<link rel="stylesheet" href="<c:url value='/css/bootstrap-responsive.min.css'/>">
<link rel="stylesheet" href="<c:url value='/css/fullcalendar.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/admin.css'/>">
<link rel="stylesheet" href="<c:url value='/css/matrix-style.css'/>">
<link rel="stylesheet" href="<c:url value='/css/matrix-media.css'/>">
<link rel="stylesheet" href="<c:url value='/css/font-awesome.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/news1.css'/>"> 
 
<script src="<c:url value='/js/jquery.min.js'/>"></script>
<script src="<c:url value='/js/bootstrap.min.js'/>"></script>
<script src="<c:url value='/js/matrix.js'/>"></script> 
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bigcolorpicker.css'/>">
   <script type="text/javascript" src<c:url value='/js/jquery.colorpicker.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/js/layer.js'/>">
</script><link rel="stylesheet" href="<c:url value='/css/layer.css'/>" id="layui_layer_skinlayercss">
<script type="text/javascript" src="<c:url value='/js/layer.ext.js'/>">
</script>
<link rel="stylesheet" href="<c:url value='/css/layer.ext.css'/>" id="layui_layer_skinlayerextcss">
<script type="text/javascript" src="<c:url value='/js/laydate.js'/>">
</script><link type="text/css" rel="stylesheet" href="<c:url value='/css/laydate.css'/>">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/laydate(1).css'/>" id="LayDateSkin">

<style type="text/css">
@media (max-width: 767px){
#search {
     display: block;
}
}
</style>
</head>
<body>


<!--main-container-part-->
<div id="content">
<!--breadcrumbs-->
 
  
  <div class="container-fluid">
  
  
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/dataTables.css'/>">
<script type="text/javascript" src="<c:url value='/js/jquery.artZoom.js'/>" charset="utf-8"></script>
<script language="javascript" type="text/javascript" src="../js/datejs/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/jquery.artZoom.css'/>">

<style>
.laydate-icon{
    height: 34px;
    line-height: 1.42857;
    padding-right:0;
}
</style>
<div class="builder builder-list-box">
    <div class="row">
    <div class="col-xs-12 col-sm-12">
            </div>
    </div>
    <!-- Tab导航 -->
        
    <!-- 顶部工具栏按钮 -->
        <div class="builder-toolbar builder-list-toolbar">
        <div class="row" style="margin-bottom:20px;">
        <div class="col-xs-12 col-sm-12 button-list"> 
        <!-- 工具栏按钮 -->
                </div>
        </div>
        
                
        <div class="row">
        <div class="col-xs-12 col-sm-12 search-list">
        <!-- 下拉搜索框 -->
        <div class="col-xs-12 col-sm-2 s-panel uinn-l">
             <div style="text-align: left;">
             <span class="input-group-btn"><a class="btn btn-default"   id="export" href="javascript:void(0);" onclick="exportExcel();"><i class=""></i>导出</a></span>
             </div>
           
         </div>                  <!-- 日期 -->
         <div class="col-xs-12 col-sm-2 s-panel search-form" >
             <span style="display: block;float: left;line-height: 30px;width: 40px;">状态</span>
             <select class="form-control" name="processStatus" id="processStatus" style="width:180px;float:left;">
                <option value="1" <c:if test="${processStatus== '1'}">selected="selected"</c:if>  >待审批</option>
                <option value="2" <c:if test="${processStatus== '2'}">selected="selected"</c:if> >已审批</option>
                <option value="3" <c:if test="${processStatus== '3'}">selected="selected"</c:if> >已拒绝</option>
             </select>
         </div>
          <div class="col-xs-12 col-sm-2 s-panel search-form">
            	开始时间：<input name="startDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${startDate}" />
         </div>
          <div class="col-xs-12 col-sm-2 s-panel search-form">
             	结束时间:<input name="endDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${endDate }" />
         </div>
         <!-- 搜索框 -->
         <div class="col-xs-12 col-sm-4 s-panel" style="padding-right:0;float:right;"> 
             <div class="input-group search-form" style="text-align: right;">
             <!-- 文本搜索框 -->
             <input type="text" name="userName" class="search-input form-control" value="${userName }" placeholder="请输入会员编号" id="userName">    
                 <span class="input-group-btn"><a class="btn btn-default"   id="search" href="javascript:void(0);" onclick="tiaozhuan(1);"><i class=""></i>查询</a></span>
             </div>
             
             
             
         </div>         
        </div>
        </div>
    </div>
        
            
    <!-- 数据列表 pc-->
   <div class="builder-container builder-list-container">
      <div class="row">
            <div class="col-xs-12">
                <div class="box bordered-box orange-border" style="margin-bottom:0;">
                    <div class="box-content box-no-padding">
                        <div class="responsive-table">
                            <div class="scrollable-area">
                                <table class="data-table table table-bordered table-striped" style="margin-bottom: 0;">
                                    <thead>
                                        <tr>
                                            <th><input class="check-all" type="checkbox"></th>
                                            <th>用户名</th>
                                            <th>姓名</th>
                                            <th>手机号码</th>
                                            <th>提现金额</th>
                                            <th>申请时间</th>
                                            <th>审批时间</th>
                                            <th>提现状态</th>
                                            <th>提现手续费</th>
                                            <th>是否到账</th>
                                            <th>到账以太坊金额</th>
                                            <th>到账日期</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${withdrawList}" var="withdrawDo">
                                            <tr>
                                                <td><input class="ids" type="checkbox" value="${withdrawDo.id }" name="ids[]"></td>
                                                <td>${withdrawDo.user_name }</td>
                                                <td>${withdrawDo.true_name }</td>
                                                <td>${withdrawDo.mobile }</td>
                                                <td>${withdrawDo.amount }</td>
                                                <td>${withdrawDo.withdrawDateStr }</td>
                                                <td>${withdrawDo.confirmDateStr }</td>
                                                <c:choose>
                                                <c:when test="${withdrawDo.process_status eq 1}">
                                                    <td>待审批</td>
                                                </c:when>
                                                <c:when test="${withdrawDo.process_status eq 2}">
                                                    <td>已审批</td>
                                                </c:when>
                                                <c:when test="${withdrawDo.process_status eq 3}">
                                                    <td>已拒绝</td>
                                                </c:when>
                                                <c:otherwise><td></td></c:otherwise>
                                                </c:choose>
                                                <td>${withdrawDo.fee }</td>
                                                <td>${withdrawDo.confirmed }</td>
                                                <td>${withdrawDo.confirmedAmt }</td>
                                                <td>${withdrawDo.confirmed_date }</td>
                                                <td>
                                                    <c:if test="${withdrawDo.process_status eq '1'}">
                                                    <a class="label label-warning" title="同意"
                                                                                   href="javascript:void(0);" 
                                                                                   onclick="auditWithdraw(${withdrawDo.id }, '2',this);">同意提现
                                                     </a>
                                                     
                                                     
                                                     
                                                     <span>&nbsp;&nbsp;</span>
                                                     <a class="label" style="background-color: blue;" title="拒绝"
                                                                                   href="javascript:void(0);" 
                                                                                   onclick="auditWithdraw(${withdrawDo.id }, '3',this);">拒绝
                                                     </a>
                                                   </c:if>
                                                   <c:if test="${withdrawDo.process_status eq '2'}">
                                                       <c:if test="${withdrawDo.confirmed eq '未到账'}"> 
	                                                   <span>&nbsp;&nbsp;</span>
	                                                     <a class="label" style="background-color: blue;" title="重做"
	                                                                                   href="javascript:void(0);" 
	                                                                                   onclick="auditWithdraw(${withdrawDo.id }, '4',this);">重做
	                                                     </a>
	                                                     
	                                                     <span>&nbsp;&nbsp;</span>
	                                                     <a class="label" style="background-color: blue;" title="拒绝"
	                                                                                   href="javascript:void(0);" 
	                                                                                   onclick="auditWithdraw(${withdrawDo.id }, '3',this);">拒绝
	                                                     </a>
                                                     
	                                                    </c:if>  
                                                   </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                        </div>
                        </div>
                    </div>
                    
                     <ul class="pagination">
                        <c:forEach var="i" begin="${startPage}" end="${endPage}" step="1">
                            <c:if test="${i eq currentPage}">
                                <li class="active current">
                                <a>${currentPage}</a>
                            </c:if>
                            <c:if test="${i ne currentPage}">
                                <li>
                                <a class="num" href="javascript:void(0);" onclick="tiaozhuan(${i});">${i}</a>
                            </c:if>
                            </li>
                        </c:forEach>
                        <c:if test="${totalPage gt endPage}">
                            <li>
                                <a class="next" href="javascript:void(0);" onclick="tiaozhuan(${currentPage+1});">&gt;&gt;</a>
                            </li>
                            <li>
                                <a class="next" href="javascript:void(0);" onclick="tiaozhuan(${totalPage});">${totalPage}</a>
                            </li>
                        </c:if>
                        <div class="info" id="info" style="margin-left: 30px;margin-top:2px;width: 210px; float: right; ">
                        <input id="pa" size="6" type="number" style="width: 20%;height: 30px;">
                        <a class="tiaozhuan" href="javascript:void(0);" onclick="tiaozhuanA();">跳转</a>
                        </div>  
                    </ul>
                        
                                    </div>
            </div>
        </div>
           </div>
   
   <script type="text/javascript">
   $(function() {
        var marB =$('.scrollable-area').width()- $('.pagination').width()-$('#sidebar').width();
        $('#info').css('margin-right',marB);
    })
    
    function auditWithdraw(withdrawId, optType,thiz) {
       var msg = "是否确认提现？"; 
       if (confirm(msg)==true){ 
           var url = "<c:url value='/eth/auditWithdraw.do'/>";
           var data = {"withdrawId":withdrawId,"auditResult":optType};
           $.ajax({
               cache: false,
               type: "POST",
               url:url,  
               data:data,  
               async: false,
               error: function(request) {
                   alert("发送请求失败！");},
               success: function(data) {
                   //data = $.parseJSON(data);
                   if(data.code == '0'){
                	   if(optType == '2'){
	                	   $(thiz).text("已审批");
                	   }else if(optType == '4'){
	                	   $(thiz).text("已重做");
                	   }else{
	                	   $(thiz).text("已拒绝");                		   
                	   }
                       alert("提现成功");
                   } else{
                       var msg = data.msg;
                       if(msg != null){
                           alert(data.msg);
                       }else{
                           alert("操作失败");
                       }
                   }
               }});
           return true;
       }else{ 
           return false; 
       } 
   }
   
   
   function tiaozhuanA() {
       var pa=$('#pa').val();
       tiaozhuan(pa);
   }
   
  function tiaozhuan(page){
      var pa=page;
      var url="<c:url value='/eth/listWithdraw.do'/>";
      var processStatus = $("#processStatus").val();
      var query = $('.builder .search-form').find('input').serialize();
      query = query.replace(/(&|^)(\w*?\d*?\-*?_*?)*?=?((?=&)|(?=$))/g, '');
//     query = query.replace(/(^&)|(\+)/g, '');
      if((pa != '') &&(pa != undefined) &&(pa>0)){
           query = 'p=' + pa + "&processStatus=" + processStatus +"&" + query;
    }
      
      
      if (url.indexOf('?') > 0) {
          url += '&' + query;
      } else {
          url += '?' + query;
      }
      
       if(pa>0){
           window.location.href=url;
       }
  }
  
    function exportExcel() {
        var exportIframe = document.createElement('iframe');  
        var processStatus = $("#processStatus").val();
        var query = $('.builder .search-form').find('input').serialize();
        query = query.replace(/(&|^)(\w*?\d*?\-*?_*?)*?=?((?=&)|(?=$))/g, '');
        
        exportIframe.src ="../eth/export.do?" + query + "&processStatus=" + processStatus;

        
        exportIframe.style.display = 'none';
        document.body.appendChild(exportIframe);
    }
   
   </script>
    </div>
    

  
  </div>
</div>

<!--end-main-container-part-->


<script type="text/javascript" src="<c:url value='/js/admin.js'/>"></script>


</body></html>