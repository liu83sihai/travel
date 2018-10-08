<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html lang="zh">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>以太坊流水</title>

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
<script type="text/javascript" src=<c:url value='/js/jquery.colorpicker.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/js/layer.js'/>">
</script><link rel="stylesheet" href="<c:url value='/css/layer.css'/>" id="layui_layer_skinlayercss">
<script type="text/javascript" src="<c:url value='/js/layer.ext.js'/>">
</script>
<link rel="stylesheet" href="<c:url value='/css/layer.ext.css'/>" id="layui_layer_skinlayerextcss">
<script type="text/javascript" src="<c:url value='/js/laydate.js'/>">
</script><link type="text/css" rel="stylesheet" href="<c:url value='/css/laydate.css'/>">
<link type="text/css" rel="stylesheet" href="<c:url value='/css/laydate(1).css'/>" id="LayDateSkin">
<script language="javascript" type="text/javascript" src="../js/datejs/WdatePicker.js"></script>
<style type="text/css">
@media (max-width: 767px){
#search {
     display: block;
}
}
</style>
</head>
<body>

  
  <div class="container-fluid">
  	<div class="container">

        <div class="chart">
            <div class="row">
                <%--
				<div class="col-xs-12 col-sm-6 col-md-12">
	              <div class="panel panel-default">
	                   <div class="panel-heading">
	                       <i class="fa fa-tachometer"></i> 快捷操作
	                   </div>
	                   <div class="panel-body">
	                       <div class="card-container col-lg-3 col-sm-6 col-sm-12">
					         <div class="card card-blue hover">
					           <div class="front"> 
					             <div class="media">        
					               <span class="pull-left">
					                 <i class="fa fa-user media-object"></i>
					               </span>
					               <div class="media-body">
					                 <small>13939</small>
					                 <h2>客户总数</h2>
					               </div>
					             </div> 
					           </div>
					           <div class="back">
					             <a href="http://fyt527.w1118.iyumi.pw/index.php?s=/admin/user/index.html">
					               <i class="fa fa-user fa-4x"></i>
					               <span>客户列表</span>
					             </a>  
					           </div>
					         </div>
					       </div>
					       
					       <div class="card-container col-lg-3 col-sm-6 col-sm-12">
					         <div class="card card-blue hover">
					           <div class="front"> 
					             <div class="media">        
					               <span class="pull-left">
					                 <i class="fa fa-user-plus media-object"></i>
					               </span>
					               <div class="media-body">
					                 <small>37</small>
					                 <h2>新增客户</h2>
					               </div>
					             </div> 
					           </div>
					           <div class="back">
					             <a href="http://fyt527.w1118.iyumi.pw/index.php?s=/admin/user/noactiveuserlist.html">
					               <i class="fa fa-user-plus fa-4x"></i>
					               <span>未设置级别客户列表</span>
					             </a>  
					           </div>
					         </div>
					       </div>
					       
					       <div class="card-container col-lg-3 col-sm-6 col-sm-12">
					         <div class="card card-blue hover">
					           <div class="front"> 
					             <div class="media">        
					               <span class="pull-left">
					                 <i class="fa fa-plus-square media-object"></i>
					               </span>
					               <div class="media-body">
					                 <small>24</small>
					                 <h2>申请充值</h2>
					               </div>
					             </div> 
					           </div>
					           <div class="back">
					             <a href="http://fyt527.w1118.iyumi.pw/index.php?s=/admin/recharge/index.html">
					               <i class="fa fa-plus-square fa-4x"></i>
					               <span>充值申请列表</span>
					             </a>  
					           </div>
					         </div>
					       </div>
					       
					       <div class="card-container col-lg-3 col-sm-6 col-sm-12">
					         <div class="card card-blue hover">
					           <div class="front"> 
					             <div class="media">        
					               <span class="pull-left">
					                 <i class="fa fa-anchor media-object"></i>
					               </span>
					               <div class="media-body">
					                 <small>8225</small>
					                 <h2>申请提现</h2>
					               </div>
					             </div> 
					           </div>
					           <div class="back">
					             <a href="http://fyt527.w1118.iyumi.pw/index.php?s=/admin/withdrawals/index.html">
					               <i class="fa fa-anchor fa-4x"></i>
					               <span>提现申请列表</span>
					             </a>  
					           </div>
					         </div>
					       </div>
	                   </div>
	               </div>
           		</div>
           		 --%>
				<div class="col-xs-12 col-sm-6 col-md-12">
	               	<div class="panel panel-default">
	                   <div class="panel-heading">
	                       <i class="fa fa-credit-card"></i> 公司财务
	                   </div>
	                   <div class="panel-body table-responsive">
	                       <table class="table table-condensed">
		                       <tbody>
		                           <tr>
		                               <td>总收入</td>
		                               <td>总支出</td>
		                               <td>总沉淀</td>
		                               <td>拨比</td>
		                           </tr>
		                           <tr>
		                               <td>7245200.00</td>
		                               <td>330174.73</td>
		                               <td>6915025.27</td>
		                               <td>4.56%</td>
		                           </tr>
		                       </tbody>
		                   </table>
	                   </div>
	               </div>
	           </div>
			   <div class="col-xs-12 col-sm-6 col-md-12">
	               	<div class="panel panel-default">
	                   <div class="panel-heading">
	                       <i class="fa fa-bar-chart-o"></i> 近7天内公司财务
	                   </div>
	                   <div class="panel-body table-responsive">
	                       <table class="table table-condensed">
		                       <tbody>
		                           <tr>
		                           	   <td>日期</td>
		                               <td>总收入</td>
		                               <td>总支出</td>
		                               <td>总沉淀</td>
		                               <td>拨比</td>
		                           </tr>
		                           <tr>
		                           	   <td>2018-04-26</td>
		                               <td>0.00</td>
		                               <td>0.00</td>
		                               <td>0</td>
		                               <td>0%</td>
		                           </tr><tr>
		                           	   <td>2018-04-22</td>
		                               <td>0.00</td>
		                               <td>0.00</td>
		                               <td>0</td>
		                               <td>0%</td>
		                           </tr><tr>
		                           	   <td>2018-04-20</td>
		                               <td>0.00</td>
		                               <td>0.00</td>
		                               <td>0</td>
		                               <td>0%</td>
		                           </tr>
		                           </tbody>
		                   </table>
	                   </div>
	               </div>
	           </div>
	           
	           
           </div>
         </div>
      </div>
  </div>

<script type="text/javascript" src="<c:url value='/js/admin.js'/>"></script>
</body>
</html>