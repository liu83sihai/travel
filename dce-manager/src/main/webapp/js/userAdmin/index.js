var basePath="/dce-manager";
var UserMenu=function(){
	var grid;
	var that;
	return {
		init:function(){
			that=this;
			$("#tableGrid").datagrid({
				url:basePath+"/homemng/menuAdmin/userDatas.action",
				height:550,
				pagination:true,
				fitColumns:true,
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:true,
				striped:true,
				pageSize:20,
				pageList:[20],
				idField:"id",
				columns:[[
					{field:"username",title:"用户名",width:150,align:"center"},
					{field:"nickname",title:"昵称",width:100,align:"center"},
					{field:"edit",title:"操作",width:80,align:"center",formatter: function(value,row,index){
						return "<a href='javascript:void(0);' onclick='userMenu.checkRoles("+row.id+")'>查看角色</a> <a href='javascript:void(0);' onclick='userMenu.editUser("+row.id+")'>编辑</a> <a href='javascript:void(0);' onclick='resetPwd("+row.id+")'>重置密码</a> <a href='javascript:void(0);' onclick='userMenu.deleteUser("+row.id+")'>删除</a>";
					}
					}
					]]
			});
		},
		editUser:function(id){
			$('#addDiv').dialog({
				title: '新增人员',
				width: 370,
				height: 200,
				closed: false,
				cache: false,
				href: basePath+'/homemng/menuAdmin/addUserDialog.action'+(id?"?userId="+id:""),
				modal: false,
				toolbar:[
					{
						iconCls:"icon-save",text:"保存",
						handler:function(){
							var userId=$("#userId").val();
							var name=$.trim($("#name").val());
							var nickName=$.trim($("#nickName").val());
							var password=$.trim($("#password").val());
							var mobile=$.trim($("#mobile").val());
							var remark=$.trim($("#remark").val());
							if(!name){
								$.messager.alert("账号空","账号不能为空");
								return;
							}
							if(!nickName){
								$.messager.alert("昵称空","昵称不能为空");
								return;
							}
							if(!password){
								$.messager.alert("密码空","密码不能为空");
								return;
							}
							if(!mobile){
								$.messager.alert("手机空","手机号不能为空");
								return;
							}
							/*if(!remark){
								$.messager.alert("备注空","备注不能为空");
								return;
							}
							//密码复杂度
							var message = pwdCheckout(password);
							if(message.length != 0 && !message==""){
								$.messager.alert("密码格式不对",message);
								return;
							}*/
							
							
							var data={username:name,nickname:nickName,password:password,mobile:mobile,remark:remark};
							if(userId){
								data.id=userId;
							}
							$.ajax({
								type:'post',
								url:basePath+'/homemng/menuAdmin/addUser.action',
								data:data,
								success:function(ret){
									if(ret.ret==1){
										$('#addDiv').dialog("close");
										$('#tableGrid').datagrid('reload');
									}else{
										$.messager.alert("新增用户失败", ret.msg);
									}
								}
							});
						}
					},
					{
						iconCls:"icon-cancel",text:"关闭",
						handler:function(){
							$("#addDiv").dialog("close");
						}
					}
					]
			});
		},
		checkRoles:function(id) {

			if(!id){
				$.messager.alert("消息","id不能为空");
				return;
			}
			//xiangxm
			$('#checkRolesDiv').dialog({
				title: '用户所有角色',
				width: 600,
				height: 300,
				closed: false,
				cache: false,
				href: basePath+"/homemng/menuAdmin/checkUserRoles/"+id+".action",
				modal: true,
				toolbar:[{
					iconCls:"icon-cancel",text:"关闭",
					handler:function(){
						$("#checkRolesDiv").dialog("close");
					}
				}]
			});
		},
		deleteUser:function(id){
			if(!id){
				$.messager.alert("消息","id不能为空");
				return;
			}
			$.messager.confirm("消息","确认删除该模块吗，删除后不可恢复",function(r){
				if(r){
					$.ajax({
						url:basePath+"/homemng/menuAdmin/deleteUser/"+id+".action",
						type:"post",
						data:{},
						success:function(data){
							if(data.ret==1){
								$('#tableGrid').datagrid('reload');
							}else{
								$.messager.alert("消息","删除失败，请稍后再试");
							}
						}
					});
				}
			});
		}
	}
}
$(function(){
	var userMenu=new UserMenu();
	userMenu.init();
	window.userMenu=userMenu;
	$('#restpwd').window('close');
});

//在resource-web有了一个这个方法，在common.jsp引入了，因为是用https调用，有时不能找到，所以留一个备用
//function pwdCheckout(password){
//	if(password.length < 6)
//		return "密码长度要>=6";
//	else 
//		return "";
//}