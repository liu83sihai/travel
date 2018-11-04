<%@ page language="java" contentType="text/html; UTF-8"    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>奖金统计</title>
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
<script src="../css/dce/jquery.min.js"></script>
<script src="../css/dce/bootstrap.min.js"></script>
<script src="../css/dce/matrix.js"></script>
<link rel="stylesheet" type="text/css" href="../css/dce/bigcolorpicker.css">
<script type="text/javascript" src="../css/dce/jquery.colorpicker.js"></script> 
<script type="text/javascript" src="../css/dce/layer.js"></script><link rel="stylesheet" href="../css/dce/layer.css" id="layui_layer_skinlayercss">
<script type="text/javascript" src="../css/dce/layer.ext.js"></script><link rel="stylesheet" href="../css/dce/layer.ext.css" id="layui_layer_skinlayerextcss">
<script type="text/javascript" src="../css/dce/laydate.js"></script><link type="text/css" rel="stylesheet" href="../css/dce/laydate.css"><link type="text/css" rel="stylesheet" href="../css/dce/laydate(1).css" id="LayDateSkin">

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
  
  <div class="container-fluid">
  
  
    <link rel="stylesheet" type="text/css" href="../css/dce/dataTables.css">
<script type="text/javascript" src="../css/dce/jquery.artZoom.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="../css/dce/jquery.artZoom.css">

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
    <div class="builder-tabs builder-list-tabs">
            <div class="row">
                <div class="col-xs-12">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="http://fyt527.w1118.iyumi.pw/index.php?s=/admin/bonus/index/group/1.html">往日统计</a></li><li class=""><a href="http://fyt527.w1118.iyumi.pw/index.php?s=/admin/bonus/index/group/2.html">今日统计</a></li>                    </ul>
                </div>
            </div>
        </div>    
    <!-- 顶部工具栏按钮 -->
		<div class="builder-toolbar builder-list-toolbar">
	    <div class="row" style="margin-bottom:20px;">
	    <div class="col-xs-12 col-sm-12 button-list">
	    <!-- 工具栏按钮 -->
        <a target-form="ids" class="btn btn-primary" title="导出" href="http://fyt527.w1118.iyumi.pw/index.php?s=/admin/bonus/toexportbonus.html">导出</a>&nbsp;        </div>
		</div>
		
				
				
				
		<div class="row">
		<div class="col-xs-12 col-sm-12 search-list">
		<!-- 下拉搜索框 -->
                          <!-- 日期 -->
         <div class="col-xs-12 col-sm-2 s-panel search-form">
         	<input type="text" id="starttime" name="starttime" placeholder="开始日期" class="text laydate-icon search-input form-control" value=""> 
         </div>
          <div class="col-xs-12 col-sm-2 s-panel search-form">
         	<input type="text" id="endtime" name="endtime" placeholder="结束日期" class="text laydate-icon search-input form-control" value=""> 
         </div>
         <!-- 搜索框 -->
         <div class="col-xs-12 col-sm-4 s-panel" style="padding-right:0;float:right;">
             <div class="input-group search-form" style="text-align: right;">
             <!-- 文本搜索框 -->
             <input type="text" name="keyword" class="search-input form-control" value="" placeholder="请输入ID/客户名/邮箱/手机号" id="st">    
                 <span class="input-group-btn"><a class="btn btn-default" href="javascript:;" id="search" url="/index.php?s=/admin/bonus/index/group/1.html"><i class="fa fa-search"></i>查询</a></span>
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
							    <table class="data-table table table-bordered table-striped" style="margin-bottom:0;">
								    <thead>
                                        <tr>
                                            <th><input class="check-all" type="checkbox"></th>
                                            <th>日期</th><th>客户</th><th>报单赠送</th><th>直推奖</th><th>量碰</th><th>互助奖</th><th>领导奖</th><th>日总计</th><th>累计</th>                                        </tr>
								    </thead>
								    <tbody>
							            <tr>
									            <td>
										            <input class="ids" type="checkbox" value="19" name="ids[]">
									            </td>
									            
											            <td>2018-03-10 00:00</td>
											            <td>魏黎倩【fjm1919】</td>
											            <td></td>
											            <td>5.00</td>
											            <td></td>
											            <td></td>
											            <td></td>
											            <td>5.00</td>
											            <td>5.00</td>								            </tr><tr>
									            <td>
										            <input class="ids" type="checkbox" value="18" name="ids[]">
									            </td>
									            
											            <td>2018-03-10 00:00</td>
											            <td>冯永辉【fl007】</td>
											            <td></td>
											            <td>200.00</td>
											            <td></td>
											            <td></td>
											            <td></td>
											            <td>200.00</td>
											            <td>200.00</td>								            </tr>
							            						            </tbody>
							    </table>
						    </div>
					    </div>
				    </div>
                     			    </div>
            </div>
        </div>
           </div>
   
   <script>
   $(function() {
 		var marB =$('.scrollable-area').width()- $('.pagination').width()-$('#sidebar').width();
   	$('#info').css('margin-right',marB);
 	})
   function tiaozhuan()
   {
	   var group="";
	   var pa=$('#pa').val();
	   var url="/index.php?s=/admin/bonus/index.html";
       var keyselect=$('#keyselect').children('option:selected').val();
       var keyselect1=$('#keyselect1').children('option:selected').val(); 
       var query = $('.builder .search-form').find('input').serialize();
       query = query.replace(/(&|^)(\w*?\d*?\-*?_*?)*?=?((?=&)|(?=$))/g, '');
       query = query.replace(/(^&)|(\+)/g, '');
       
       if((keyselect != '') &&(keyselect != undefined)){
			query = 'keyselect=' + keyselect + "&" + query;
       }
       if((group != '') &&(group != undefined)){
			query = 'group=' + group + "&" + query;
      }
       if((pa != '') &&(pa != undefined) &&(pa>0)){
			query = 'p=' + pa + "&" + query;
     }
       
       if((keyselect1 != '')&&(keyselect1 != undefined)){
			query = 'keyselect1=' + keyselect1 + "&" + query;
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
   </script>
	    <!-- 额外功能代码 -->
    <div style="color:red;font-size:1.5rem;">保单赠送总计：，直推奖总计：205.00，量碰总计：，互助奖总计:，领导奖总计:</div></div>
    

  
  </div>
</div>

<!--end-main-container-part-->



<script type="text/javascript" src="../css/dce/admin.js"></script>
</body></html>