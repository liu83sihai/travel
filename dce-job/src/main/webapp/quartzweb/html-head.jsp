<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="jsp-head.jsp"%>
<style type="text/css">
body { font-size:14px; font-family:"Courier New", Courier, monospace; }
td, th { font-size:12px; }
h1 { font-size:20px; }
h2 { margin:0; padding:0; line-height:20px; }
form { margin:0; padding:0; }
table,table td,table th{border:1px solid gray;border-collapse:collapse;}
.trClass{background:#E5F6BF};
.lbtit{padding:5px;}
</style>
<link href="/css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/jquery.autocomplete.min.js"></script>
<script type="text/javascript" language="javascript">
var $e=function(id){
	return document.getElementById(id);
}
$(document).ready(function(){
 $("table tr").hover(
		 function(){
			$(this).addClass("trClass");
		 },
		 function(){
			$(this).removeClass("trClass");
         }
		 );
});
</script>
