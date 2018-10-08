var basePath="/dce-manager";
$(function(){
	
});

function acitivitUserName(){
	var userName = $("#acitivit_userName").val();
	if(userName == null || userName == "" || userName == undefined){
		$.messager.alert("提示","请输入要激活的用户名!");
		return;
	}
	
	$.ajax({
		url:basePath+"/user/activityUser.html",
		type:"post",
		dataType: 'json',
		data:{"userName":userName},
		success:function(ret){
			if(ret.code==0){
				$.messager.alert("成功","激活成功");
			}else{
				$.messager.alert("失败",ret.msg);
			}
		}
	});
}