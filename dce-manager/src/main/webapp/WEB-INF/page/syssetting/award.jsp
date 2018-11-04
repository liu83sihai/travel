<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="../css/dce/bootstrap.css">
<link rel="stylesheet" href="../css/dce/bootstrap-responsive.min.css">
<link rel="stylesheet" href="../css/dce/fullcalendar.css">
<link rel="stylesheet" type="text/css" href="../css/dce/admin.css">
<link rel="stylesheet" href="../css/dce/matrix-style.css">
<link rel="stylesheet" href="../css/dce/matrix-media.css">
<link rel="stylesheet" href="../css/dce/font-awesome.css">
<link rel="stylesheet" type="text/css" href="../css/dce/news1.css"> 



<style>
.form-bonus-set {
    background-color: #ffffff;
    background-image: none;
    border: 1px solid #cccccc;
    border-radius: 3px;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
    color: #555555;
    font-size: 14px;
    height: 34px;
    line-height: 1.42857;
    padding: 6px 12px;
    transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
}
.set-small {
width:70px;
}
.set-middle {
width:100px;
}
.set-big {
width:90px;
}

.set-small1{
width:50px;
}
.set-middle1{
width:90px;
}
</style>


<script src="../js/dce/jquery.min.js"></script>
<!-- <script src="../js/dce/bootstrap.min.js"></script>
<script src="../js/dce/matrix.js"></script> 
<script type="text/javascript" src="../js/dce/jquery.colorpicker.js"></script> 
<script type="text/javascript" src="../js/dce/layer.js"></script>
<script type="text/javascript" src="../js/dce/layer.ext.js"></script>
<script type="text/javascript" src="../js/dce/laydate.js"></script> -->

<link rel="stylesheet" type="text/css" href="../css/dce/bigcolorpicker.css">
<link rel="stylesheet" href="../css/dce/layer.css" id="layui_layer_skinlayercss">
<link rel="stylesheet" href="../css/dce/layer.ext.css" id="layui_layer_skinlayerextcss">
<link type="text/css" rel="stylesheet" href="../css/dce/laydate.css">
<link type="text/css" rel="stylesheet" href="../css/dce/laydate1.css" id="LayDateSkin">
<script type="text/javascript" src="<c:url value='../js/common/jquery.easyui.min.js?v=2.2.2'/>"></script>
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
  
<form id="jlzdFrom" action="../award/save.do" method="post">
<div class="container">
    <div class="dashboard">
       <div class="row">
       
           <div class="col-xs-12 col-sm-6 col-md-12">
               <div class="panel panel-default">
                   <div class="panel-heading">
                       <i class="fa fa-cubes"></i>直销制度 
                   </div>
                   <div class="panel-body">
                       <table class="table table-responsive">
	                       <tbody>
	                       	   <tr>
                                   <td colspan="6">模式：双轨不公排</td>
                               </tr> 
                               <tr>
                                   <td>
                                   	客户级别:
                                   </td>
                                   <c:forEach var="item" items="${KHJB}" varStatus="no">
                                   
	                                  	<td>
	                                  	 <input type="text" class="form-bonus-set set-middle" name="dictDtlId-${item.id}" id="name-3-user_level" value="${item.remark }">
	                                  	</td>
                                  	</c:forEach>
                               </tr>
                               <tr>
                               		<td>报单费:</td>
                               		<c:forEach var="item" items="${BaoDanFei}" varStatus="no">
	                               		<td>
	                                  	 <input type="text" class="form-bonus-set set-middle" id="value-1" name="dictDtlId-${item.id}" value="${item.remark }">个DCE
	                                  	</td>
                                  	</c:forEach>
                               </tr> 
                               <tr>
                               		<td>报单赠送:</td>
                               		
                               		<c:forEach var="item" items="${BaoDanZengSong}" varStatus="no">
	                               		<td>
	                                  	 <input type="text" class="form-bonus-set set-small" id="value-6" name="dictDtlId-${item.id}" value="${item.remark }">倍
	                                  	</td>
                                  	</c:forEach>
                               </tr>
                                <tr>
                               		<td>产能:</td>
                               		<c:forEach var="item" items="${CHANNENG}" varStatus="no">
	                               		<td>
	                                  	 <input type="text" class="form-bonus-set set-small" id="ext-6" name="dictDtlId-${item.id}" value="${item.remark }">倍
	                                  	</td>
                                  	</c:forEach>
                               </tr>
                               <tr>
                               		<td>量碰奖:</td>
                               		<c:forEach var="item" items="${LiangPeng}" varStatus="no">
	                               		<td>
	                                  	 <input type="text" class="form-bonus-set set-small" id="value-12" name="dictDtlId-${item.id}-0.0" value="${item.remark }">%
	                                  	</td>
                                  	</c:forEach>
                               </tr>
                               <tr>
                               		<td>量碰日封顶:</td>
                               		<c:forEach var="item" items="${LiangPengFengDing}" varStatus="no">
	                               		<td>
	                                  	 <input type="text" class="form-bonus-set set-small" id="value-41" name="dictDtlId-${item.id}" value="${item.remark }">
	                                  	</td>
                                  	</c:forEach>
                               </tr>
                               <tr>
                               		<td>量碰多少层:</td>
                               		<td colspan="5">
                                  	 <input type="text" class="form-bonus-set set-small" id="value-410" name="dictDtlId-${LPCJ.id}" value="${LPCJ.remark }">
                                  	</td>
                               </tr>
                               <tr>
                               		<td colspan="6">PS：量奖分<input type="text" class="form-bonus-set set-small" id="value-17" name="dictDtlId-${LiangPengRate.id }-0.0" value="${LiangPengRate.remark }">%进入现持仓，${100- LiangPengRate.remark}%进入锁仓
                               		不释放,只能在商城里消费。
                               		<%-- <input type="text" class="form-bonus-set set-small" id="cap-17" name="dictDtlId-${SCSFCXSJ.id }" value="${SCSFCXSJ.remark }">个月后全部释放这${SCSFCXSJ.remark }个月的锁仓
                               		，直推<input type="text" class="form-bonus-set set-small" id="ext-17" name="dictDtlId-${ZTDS.id }" value="${ZTDS.remark }">
                               		代才可以拿量奖 --%>
                               		</td>
                               </tr> 
                               <tr>
                               		<td>加金产能:</td>
                               		<c:forEach var="item" items="${JiaJin}" varStatus="no">
	                               		<td>
	                                  	 <input type="text" class="form-bonus-set set-small" id="value-32" name="dictDtlId-${item.id}" value="${item.remark }">倍
	                                  	</td>
                                  	</c:forEach>
                               </tr>                                                
	                       </tbody>
	                   </table>
                   </div>
               </div>
           </div>
	  	<div class="col-xs-12 col-sm-6 col-md-12">
	               <div class="panel panel-default">
	                   <div class="panel-heading">
	                       <i class="fa fa-cubes"></i>直推奖
	                   </div>
	                   <div class="panel-body">
	                       <table class="table table-responsive">
		                       <tbody>	                       	   
	                               <tr>
	                               		<td>直推人数</td>
	                               		<td>直推1人</td>
	                               		<td>直推2人</td>
	                               		<td>直推3人</td>
	                               		<td>直推4人</td>
	                               		<td>直推5人</td>
	                               </tr>
	                               <tr>
	                               		<td>拿奖比例</td>
	                               		<c:forEach var="item" items="${ZhiTui}" varStatus="no">
		                               		<td>
		                                  	 <input type="text" class="form-bonus-set set-small" id="value-25" name="dictDtlId-${item.id}-0.0" value="${item.remark }">%
		                                  	</td>
	                                  	</c:forEach>
	                                  	
	                               </tr> 
		                       </tbody>
		                   </table>
	                   </div>
	               </div>
	           </div>
           <div class="col-xs-12 col-sm-6 col-md-6">
               <div class="panel panel-default">
                   <div class="panel-heading">
                       <i class="fa fa-cubes"></i>互助奖
                   </div>
                   <div class="panel-body">
                       <table class="table table-responsive">
	                       <tbody>
                               <tr>
                               		<td>
                                   	拿上一代量碰奖<input type="text" class="form-bonus-set set-small" id="value-30" name="dictDtlId-${HuZhuJiaQuan.id }-0.0" value="${HuZhuJiaQuan.remark }">%的加权
								   </td>
                               </tr>
                              
	                       </tbody>
	                   </table>
                   </div>
               </div>
           </div>
            <div class="col-xs-12 col-sm-6 col-md-6">
               <div class="panel panel-default">
                   <div class="panel-heading">
                       <i class="fa fa-cubes"></i>领导奖
                   </div>
                   <div class="panel-body">
                       <table class="table table-responsive"> 
	                       <tbody>	                       	   
                               <tr>
                               		<td>下拿5代量碰奖的<input type="text" class="form-bonus-set set-small" id="value-31" name="dictDtlId-${LingDao.id }-0.0" value="${LingDao.remark }">%（直推几人拿几代）
								   </td>
                               </tr>
                              
	                       </tbody>
	                   </table>
                   </div>
               </div>
           </div>
           <div class="col-xs-12 col-sm-6 col-md-12">
               <div class="panel panel-default">
                   <div class="panel-heading">
                       <i class="fa fa-cubes"></i>交易规则
                   </div>
                   <div class="panel-body">
                       <table class="table table-responsive">
	                       <tbody>	                       	   
                               <tr>
                               		<td>
                                   	分为挂卖价格和成交价格，最终价格以成交价格计算
								   </td>
                               </tr>
                               <tr>
                               		<td>
                                   	交易手续费：
                                   	<input type="text" class="form-bonus-set set-small" id="value-18" name="dictDtlId-${TRANS_RATE.id }-0.0" value="${TRANS_RATE.remark }">%
								   </td>
                               </tr> 
                               <tr>
                               		<td>
                                   	挂牌价格范围：
                                   	<c:forEach var="item" items="${GPJGFW}" varStatus="no">
                                   		<input type="text" class="form-bonus-set set-small" id="value-37" name="dictDtlId-${item.id}-0.0" value="${item.remark }">%
                                   		<c:if test="${no.index == 0}">
                                   		~
                                   		</c:if> 
                                   	</c:forEach>
									例如：当前价格是10元则挂牌价格范围是9~11元之间
								   </td>
                               </tr> 
	                       </tbody>
	                   </table>
                   </div>
               </div>
           </div>
           
           <div class="col-xs-12 col-sm-6 col-md-12">
               <div class="panel panel-default">
                   <div class="panel-heading">
                       <i class="fa fa-cubes"></i>DCE涨跌规则
                   </div>
                   <div class="panel-body">
                       <table class="table table-responsive">
	                       <tbody>	                       	   
                               <tr>
                               		<td>
                                   	当卖出的数量比买进的数量每多
                                   	<input type="text" class="form-bonus-set set-small" id="value-19" name="dictDtlId-${MCBMJMD.id }-0.0" value="${MCBMJMD.remark }">
									%,涨跌最高<input type="text" class="form-bonus-set set-small" id="max_layer-19" name="dictDtlId-${MCBMJMDZDZG.id }-0.0" value="${MCBMJMDZDZG.remark }">
									%,
									价格下跌<input type="text" class="form-bonus-set set-small" id="cap-19" name="dictDtlId-${MCBMJMDXD.id }" value="${MCBMJMDXD.remark }">
									美金，当买入的数量可比卖出的数量每多
									<input type="text" class="form-bonus-set set-small" id="ext-19" name="dictDtlId-${MRBMCMD.id }-0.0" value="${MRBMCMD.remark }">%,
									涨跌最高<input type="text" class="form-bonus-set set-small" id="max_layer-19" name="dictDtlId-${MRBMCMDZDZG.id }-0.0" value="${MRBMCMDZDZG.remark }">%,
									价格上涨<input type="text" class="form-bonus-set set-small" id="exts-19" name="dictDtlId-${MRBMCMDXD.id }" value="${MRBMCMDXD.remark }">美金
								   </td>
                               </tr> 
	                       </tbody>
	                   </table>
                   </div>
               </div>
           </div>
           
           <div class="col-xs-12 col-sm-6 col-md-12">
               <div class="panel panel-default">
                   <div class="panel-heading">
                       <i class="fa fa-cubes"></i>备注
                   </div>
                   <div class="panel-body">
                       <table class="table table-responsive">
	                       <tbody>	                       	   
                               <tr>
                               		<td>
                                   	动态奖金备注:1.按照币结算,每次在交易平台购买的DCE不参与二次动态奖金结算，不累计，不增加级别<br>
                                   			  
								   </td>
                               </tr>
                               <tr>
                               		<td>
                                   	现金币账户：可以交易，每次交易只能卖出账户余额的
                                   	<input type="text" class="form-bonus-set set-small" id="value-22" name="dictDtlId-${JYYEZB.id }-0.0" value="${JYYEZB.remark }">%
                                   	一周挂卖<input type="text" class="form-bonus-set set-small" id="ext-22" name="dictDtlId-${YZGMCS.id }" value="${YZGMCS.remark }">次
								   </td>
                               </tr>  
                               <tr>
                               		<td>
                                   	原始仓账户：每日释放1次
									<input type="text" class="form-bonus-set set-small" id="value-23" name="dictDtlId-${YSCSFTS.id }" value="${YSCSFTS.remark }">天释放完一笔，释放方式：产能*级别除以365为每日释放								   </td>
                               </tr>
                               <tr>
                               		<td>
                                   	余额账户：只能提现，显示为美金，提现时显示美金数量和人民币数量，&nbsp;&nbsp;&nbsp;&nbsp;1:
									<input type="text" class="form-bonus-set set-small" id="value-24" name="dictDtlId-${Point2RMB.id }" value="${Point2RMB.remark }">换算,
									<input type="text" class="form-bonus-set set-small" id="ext-11" name="dictDtlId-${ZSBTX.id }" value="${ZSBTX.remark }">
                                   	$整数倍提现，
                                   	<input type="text" class="form-bonus-set set-small" id="value-11" name="dictDtlId-${TXSXF.id }-0.0" value="${TXSXF.remark }">
                                   	%手续费$。
								   </td>
                               </tr>
                               <tr>
                               		<td>
                                   		充值美元数量：人民币=1：
                                   	<input type="text" class="form-bonus-set set-small" id="value-38" name="dictDtlId-${RMB2Point.id }" value="${RMB2Point.remark }">
									
								   </td>
                               </tr> 
                                <tr>
                               		<td>
                                   		提现开关
	                                   	<select id="value-39" class="form-bonus-set select" name="dictDtlId-${TXKG.id }">
											<option value="0" <c:if test="${TXKG.remark=='0' }"> selected="selected" </c:if>>关闭</option>
											<option value="1" <c:if test="${TXKG.remark=='1' }"> selected="selected" </c:if>>开启</option>
										</select>
								   </td>
                               </tr> 
                               <tr>
                               		<td>
                                   		美元点：现持仓=
                                   	<input type="text" class="form-bonus-set set-small" id="value-40" name="dictDtlId-${MYDBXCC.id }" value="${MYDBXCC.remark }">
									：1
								   </td>
                               </tr> 
	                       </tbody>
	                   </table>
                   </div>
               </div>
           </div>
           
       </div>
   </div>
</div>
<input type="submit" value="提交修改" id="saveButton">
</form> 
<script>
$(document).ready(function(){
	var width = document.body.offsetWidth;
	var halfWidth = width/2;
	$("#saveButton").css("margin-left",halfWidth);
});
</script>
</div>

</div>

<!--end-main-container-part-->


<!-- <script type="text/javascript" src="../js/dce/admin.js"></script> -->



</body></html>