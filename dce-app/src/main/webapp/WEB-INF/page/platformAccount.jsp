<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html lang="zh">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>平台以太坊账户列表</title>

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
            <!--
            <select class="form-control" name="keyselect" id="keyselect">
                <option value="">请选择</option>
                <option value="1">进行中</option><option value="2">已完成</option>            </select>
                -->
         </div>                  <!-- 日期 -->
         <div class="col-xs-12 col-sm-2 s-panel search-form">
             
         </div>
          <div class="col-xs-12 col-sm-2 s-panel search-form">
             
         </div>
         <!-- 搜索框 -->
         <div class="col-xs-12 col-sm-4 s-panel" style="padding-right:0;float:right;">
             <div class="input-group search-form" style="text-align: right;">
             <!--
             <input type="text" name="keyword" class="search-input form-control" value="" placeholder="请输入会员编号/姓名/手机号" id="st">    -->
                 <span class="input-group-btn"><a class="btn btn-default"   id="search" href="javascript:void(0);" onclick="createAccount();"><i class=""></i>添加平台以太坊账户</a></span>
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
                                            <!-- <th><input class="check-all" type="checkbox"></th> -->
                                            <th>编号</th>
                                            <th>以太坊账户</th>
                                            <th>是否默认账户</th>
                                            <th>操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${platformList}" var="item">
                                            <tr>
                                               <%--  <td><input class="ids" type="checkbox" value="${userDo.id }" name="ids[]"></td> --%>
                                                <td>${item.no }</td>
                                                <td>${item.account }</td>
                                                <td> 
                                                    <c:if test="${item.isDefault == 1}">
                                                                                                                                                               是
                                                    </c:if>
                                                    <c:if test="${item.isDefault != 1}">
                                                                                                                                                               否
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <a class="label label-warning" title="设置为默认账户"
                                                    href="javascript:void(0);" onclick="setDefault('${item.no }');">设置为默认账户</a>
                                                    
                                                    <a class="label label-warning" title="发送"
                                                    href="javascript:void(0);" onclick="send('${item.no }');">发送</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                        </div>
                        </div>
                    </div>
                    
<%--                      <ul class="pagination">
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
                    </ul> --%>
                        
                                    </div>
            </div>
        </div>
           </div>
   
   <script>
   $(function() {
        var marB =$('.scrollable-area').width()- $('.pagination').width()-$('#sidebar').width();
    $('#info').css('margin-right',marB);
    })
    
    function setDefault(accountNo) {
        debugger;
       var msg = "是否确认设置为默认账户？"; 
       if (confirm(msg)==true){ 
           var url = "<c:url value='/eth/platform/accounts/default.do'/>";
           var data = {"accountNo":accountNo};
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
                       alert("设置成功");
                       url = "<c:url value='/eth/platform/accounts.do'/>";
                       window.location.href=url;
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
   
   function createAccount() {
       var url="<c:url value='/eth/getCenterAcc.do?'/>";
       window.location.href=url;
   }
   
   function send(no) {
       var url="<c:url value='/eth/queryPlatFormEth.do?accountNo="+no+ "'/>";
       window.location.href=url;
   }
    
    function tiaozhuanA() {
        var pa=$('#pa').val();
        tiaozhuan(pa);
    }
    
   function tiaozhuan(page)
   {debugger;
      // var group="";
       var pa=page;
       var url="<c:url value='/eth/accounts.do?'/>";
       
       //var keyselect=$('#keyselect').children('option:selected').val();
       //var keyselect1=$('#keyselect1').children('option:selected').val(); 
       var query = $('.builder .search-form').find('input').serialize();
       query = query.replace(/(&|^)(\w*?\d*?\-*?_*?)*?=?((?=&)|(?=$))/g, '');
       query = query.replace(/(^&)|(\+)/g, '');
       
       //if((keyselect != '') &&(keyselect != undefined)){
       //     query = 'keyselect=' + keyselect + "&" + query;
       //}
       //if((group != '') &&(group != undefined)){
      //      query = 'group=' + group + "&" + query;
     // }
       if((pa != '') &&(pa != undefined) &&(pa>0)){
            query = 'p=' + pa + "&" + query;
     }
       
      // if((keyselect1 != '')&&(keyselect1 != undefined)){
       //     query = 'keyselect1=' + keyselect1 + "&" + query;
       //}
       
       if (url.indexOf('?') > 0) {
           url += '&' + query;
       } else {
           url += '?' + query;
       }
       
        if(pa>0){
            window.location.href=url;
        }
   }
   </script>
    </div>
    

  
  </div>
</div>

<!--end-main-container-part-->


<script type="text/javascript" src="<c:url value='/js/admin.js'/>"></script>


</body></html>