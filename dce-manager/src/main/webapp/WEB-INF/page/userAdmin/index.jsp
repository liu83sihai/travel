<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common.jsp"></jsp:include>
<script type="text/javascript" src="<c:url value='/js/userAdmin/index.js?jsverion=${jsversion}457'/>"></script>
	<style type="text/css">
		.tdfont {
			font-size: 12px;
		}
	</style>
</head>
<body>
<!-- 查询条件区域 -->
<div id="search_area" class="easyui-panel" >
	<div id="conditon" >
		<form id="searchForm" style="margin-top:7px;margin-left:5px;">
			<table border="0">
				<tr>
					<td class="tdfont">用户名:
						<input type="text" size="14" id="searchUserName"></td>
					<td class="tdfont">昵称:
						<input type="text" size="14" id="searchNickName"></td>
					<td colspan="2">
						<a href="javascript:void(0)" id="searchButton" class="easyui-linkbutton" iconCls="icon-search"
						   plain="true">查询</a>
						<a href="javascript:void(0)" id="resetButton" class="easyui-linkbutton" iconCls="icon-reset"
						   plain="true">重置</a>
						<a href="javascript:void(0)" id="addButton" class="easyui-linkbutton" iconCls="icon-reset"
						   plain="true">增加</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<span id="openOrClose"></span>
	<input type="hidden" id="selectedRow" value=""/>
</div>
	<div id="tableGrid"></div>
	<div id="addDiv"></div>
	<div id="checkRolesDiv"></div>
	
	<!--修改密码窗口-->
    <div id="restpwd" class="easyui-window" title="重置密码" collapsible="false" minimizable="false"
        maximizable="false" icon="icon-save"  style="width: 300px; height: 200px; padding: 5px;
        background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding=3>                	
                    <tr>
                        <td>新密码：</td>
                        <td><input id="txtNewPass" type="Password" class="txt01" /></td>
                    </tr>
                    <tr>
                        <td>确认密码：</td>
                        <td><input id="txtRePass" type="Password" class="txt01" /></td>
                        <input id="restPwdUserId" type="hidden"  />
                    </tr>
                </table>
            </div>
            <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
                <a id="btnRestPwd" class="easyui-linkbutton"  href="javascript:void(0)" >
                    确定</a> <a id="btnCancel" class="easyui-linkbutton" href="javascript:void(0)">取消</a>
            </div>
        </div>
    </div>
	    
<script type="text/javascript">
var basePath="/dce-manager";
	/*查询和重置按钮事件*/
    $("#resetButton").on("click", function () {

        $("#searchForm").form('reset');
    });
	
    $("#searchButton").on("click", function () {

        $("#tableGrid").datagrid('load', {
            'username': encodeURIComponent($("#searchForm #searchUserName").val()),
            'nickname': encodeURIComponent($("#searchForm #searchNickName").val()),
        });
    });
    
	$("#addButton").click(function () {

        window.userMenu.editUser();
    });
	
	$('#btnCancel').click(function(){$('#restpwd').window('close');});
	 
	$('#btnRestPwd').click(function(){serverLogin();});
	
	function resetPwd (id){
		$("#restPwdUserId").val(id);
		$('#restpwd').window('close');
		$('#restpwd').window('open');
	}
	
	
	function msgShow(title, msgString, msgType) {
		$.messager.alert(title, msgString, msgType);
	}
	
	
	//修改密码
    function serverLogin() {
        var $newpass = $('#txtNewPass');
        var $rePass = $('#txtRePass');
        if ($newpass.val() == '') {
            msgShow('系统提示', '请输入密码！', 'warning');
            return false;
        }
        if ($rePass.val() == '') {
            msgShow('系统提示', '请在一次输入密码！', 'warning');
            return false;
        }

        if ($newpass.val() != $rePass.val()) {
            msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
            return false;
        }
        //密码复杂度
        var message = pwdCheckout($newpass.val());
		if(message.length != 0 && !message==""){
			msgShow('系统提示', message, 'warning');
			return false;
		}
		
        $.ajax({
        	url:basePath + '/homemng/menuAdmin/resetUserPwd.action',
        	type:'post',
        	data:{
        		newPassword:$.trim($newpass.val()),
        		newPasswordAgain:$.trim($rePass.val()),
        		userId:$.trim($("#restPwdUserId").val())
        		},
        	success:function(msg) {
        		if(msg.ret==1){
                    msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + $.trim($newpass.val()), 'info');
                    $newpass.val('');
                    $rePass.val('');
                    $('#restpwd').window('close');
        		}else if(msg.ret==-2){
        			msgShow('系统提示','密码不正确！','info')
        		}
            }
        });
        
    }
	

</script>
</body>
</html>