<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>人员设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common.jsp"></jsp:include>
</head>
<body class="easyui-layout" style="margin-left:10px;">
<input type="hidden" id="roleId" value="${role.id }"/>
    <div style="margin:10px 0;"></div>
    <div class="easyui-layout" style="width:1000px;height:350px;">
        <div data-options="region:'north'" style="height:50px">
            <form id="searchForm">
                <table style="margin-top: 10px;margin-left: 10px;font-size: 13px">
                    <tr>
                        <td>用户名：</td>
                        <td>
                            <input id="filterUserName" style="height:25px" class="easyui-textbox" searcher="" prompt="请输入用户名" placeHolder="请输入用户名" menu="#mm" />
                        </td>
                        <td>昵称：</td>
                        <td>
                            <input id="filterNickName" style="height:25px" class="easyui-textbox" searcher="" prompt="请输入昵称" placeHolder="请输入昵称" menu="#mm" />
                        </td>
                        <td>
                            <a  href="javascript:void(0);" id="searchButton" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
                        </td>
                        <td>
                            <a  href="javascript:void(0);" id="resetButton" class="easyui-linkbutton" iconCls="icon-reset" plain="true" >重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <c:set var="basePath" value="/dce-manager"></c:set>
        <div data-options="region:'south',split:true" style="height:20px;"></div>
        <div data-options="region:'east',split:true,iconCls:'icon-ok'" title="已加入人员(双击可删除)" style="width:450px;">
          <table class="easyui-datagrid" id="hasAdd"
          pagination="true" striped="true" 
            rownumbers="true" fitColumns="true"
                    data-options="url:'${basePath }/homemng/menuAdmin/users2Roles.action?roleId=${role.id }&inOrNot=1&pageSize=10',method:'get',border:false,singleSelect:true,fit:true,fitColumns:true
                    ,onDblClickRow:removeResources">
                <thead>
                    <tr>
                        <th field="username" width="40">用户名</th>
                        <th field="nickname" width="50">昵称</th>
                    </tr>
                </thead>
            </table>
        </div>
        <div data-options="region:'west',split:true" title="权限" style="width:100px;">
        ${role.name }
        <div>
        ${role.roleDesc }
        </div>
        </div>
        <div data-options="region:'center',title:'人员（双击可添加到右边）'" >
            <table id="unAdd" class="easyui-datagrid"
            pagination="true"
            striped="true" 
            rownumbers="true" fitColumns="true"
                    data-options="url:'${basePath }/homemng/menuAdmin/users2Roles.action?roleId=${role.id }&inOrNot=2&pageSize=10',method:'get',border:false,singleSelect:true,fit:true,fitColumns:true
                    ,onDblClickRow:addUsers">
                <thead>
                    <tr>
                        <th field="username" width="40">用户名</th>
                        <th field="nickname" width="50">昵称</th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
    <script type="text/javascript">
    var basePath="/dce-manager";
        /*查询和重置按钮事件*/
        $("#resetButton").on("click",function () {

            $("#searchForm").form('reset');
        });

        $("#searchForm #searchButton").on("click",function () {

            $("#unAdd").datagrid('reload',{
                'username':encodeURIComponent($("#searchForm #filterUserName").val()),
                'nickname':encodeURIComponent($("#searchForm #filterNickName").val()),
            });
        });
    function reloadTwoTables(){
    	$('#hasAdd').datagrid('reload');
    	$('#unAdd').datagrid('reload');
    }
    function addUsers(index,row){
    	var roleId=$("#roleId").val();
    	$.messager.confirm("消息","确认在该权限下加入该菜单",function(r){
    		if(r){
    			$.ajax({
        			url:basePath + "/homemng/menuAdmin/addUserRoles.action",
        			type:"post",
        			data:{roleId:roleId,userId:row.id},
        			success:function(ret){
        				if(ret.ret==1){
        					reloadTwoTables();
        				}else{
        					$.messager.alert("添加失败","添加失败，请稍后再试");
        				}
        			}
        		});
    		}
    	});
    }
    function removeResources(index,row){
    	var roleId=$("#roleId").val();
    	$.messager.confirm("消息","确认删除该权限下的菜单吗，删除后不可恢复",function(r){
    		if(r){
    			$.ajax({
        			url:basePath + "/homemng/menuAdmin/deleteUserRoles.action",
        			type:"post",
        			data:{roleId:roleId,userId:row.id},
        			success:function(ret){
        				if(ret.ret==1){
        					reloadTwoTables();
        				}else{
        					$.messager.alert("添加失败","添加失败，请稍后再试");
        				}
        			}
        		});
    		}
    	});
    }
    </script>
</body>
</html>