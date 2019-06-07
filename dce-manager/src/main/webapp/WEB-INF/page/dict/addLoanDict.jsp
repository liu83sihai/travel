<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>编辑字典</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache" />
<jsp:include page="../common_easyui_cus.jsp"></jsp:include>
</head>
<body>

<!-- 内容 -->
<form id="editDictForm" method="post"> 
	<div >
	
    <table width="100%" border="0" align="center" cellpadding="3">

      <tr>
        <td  align="right">
        	<input type="hidden" name="id" value="${dictDo.id }" />
        	<label for="code">字典编码:</label>
        </td>
        <td > 
        	<input type="text" id="code" name="code"   value="${dictDo.code}" />
        </td>
      
        <td align="right">
        	<label for="name">字典名称:</label>
        </td>
       	<td> 
       		<input type="text"  name="name" value="${dictDo.name}"  /> 
       	</td>
      </tr>
      <tr>
        <td align="right">
        	<label for="status">状态:</label>
        </td>
        <td>
       	<select id="status" class="easyui-combobox" name="status" style="width:150px;">
			<c:choose>
			<c:when test="${!empty dictDo }">
			   <option value="T" <c:if test="${'T'==dictDo.status }">selected="selected"</c:if>>有效</option>
			   <option value="F" <c:if test="${'F'==dictDo.status }">selected="selected"</c:if>>无效</option>
			</c:when>
			<c:otherwise>
			  	<option value="T">有效</option>
			    <option value="F">无效</option>
			</c:otherwise>
			</c:choose>
		</select> 
		</td>
		
		<td align="right">
        	<label for="remark">备注:</label>
        </td>
       	<td> 
       		<input type="text"  name="remark" value="${dictDo.remark}"  /> 
       	</td>
       	
      </tr>
    </table>
   
	</div>
	<!-- 明细 -->
	<div id="editDictDetail"></div>
	</form>
	<script type="text/javascript">
		
	 function isEmptyObject( obj ) { 
		for ( var name in obj ) { 
		return false; 
		} 
		return true; 
	} 
	
	var lastIndex;
	
	$('#editDictDetail').datagrid({
	    title:'编辑字典明细',
	    iconCls:'icon-edit',
	    width:745,
	    height:360,
	    singleSelect:true,
	    idField:'id',
	    url:httpUrl+'/loandict/listDictDetail.html?dictId=${dictDo.id }',
	    columns:[[
	        {field:'dictId',title:'Item ID',hidden:true},
	        {field:'code',title:'编码',width:150, editor:{type:'textbox'},align:'center' },
	        {field:'name',title:'名称',width:200,editor:'text',align:'center'},
	        {field:'status',title:'是否有效',width:150,align:'center',
	        	formatter:function(value,row,index){
					  if(value == 'T'){
						  return "有效";
					  }else{
						  return "无效";
					  }
				},
		        editor:{
	                type:'combobox',
	                options:{
	                    valueField:'value',
	                    textField:'text',
	                    data: [{ text: '有效', value: 'T' },{ text: '无效', value: 'F' }],
	                    required:true
	                }
	            }	            
	        },
	        {field:'remark',title:'比例',width:240,editor:'text',align:'center'}	        
	    ]],
	    
	    toolbar:[{
			text:'新增',
			iconCls:'icon-add',
			handler:function(){
				$('#editDictDetail').datagrid('endEdit', lastIndex);
				$('#editDictDetail').datagrid('appendRow',{
					dictId:'${dictDo.id }',
					code:'',
					name:'',
					status:'T',
					remark:''
				});
				lastIndex = $('#editDictDetail').datagrid('getRows').length-1;
				$('#editDictDetail').datagrid('selectRow', lastIndex);
				$('#editDictDetail').datagrid('beginEdit', lastIndex);
			}
		},'-',{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){
				$('#editDictDetail').datagrid('acceptChanges');
			}
		}],
	    onBeforeEdit:function(index,row){
	        row.editing = true;
	        $('#editDictDetail').datagrid('refreshRow', index);
	    },
	    onAfterEdit:function(index,row,changes){
	        row.editing = false;
	        $('#editDictDetail').datagrid('refreshRow', index);
	        //console.info(row);
	        //console.info(changes);
	        if(isEmptyObject(changes) == false){
	        	var  url = httpUrl+"/loandict/saveDictDetail.html?&rand=" + Math.random();
		       	$.ajax({   
		       		 type: 'POST',
		       		 dataType: 'json',
		       		 url: url,  
		       		 data:row,
		       		 success: function(data){ 
		       			 if(data.code == 0){
		       				 $.messager.alert("提示","操作成功","info");
		       			 }else{
		       				 $.messager.alert("提示","操作失败","error");
		       			 }   
		       		 } 
		       	});
	        }
	    },
	    onCancelEdit:function(index,row){
	        row.editing = false;
	        $('#editDictDetail').datagrid('refreshRow', index);
	    },
	    onBeforeLoad:function(){
			$(this).datagrid('rejectChanges');
		},
		onClickRow:function(rowIndex){
			if (lastIndex != rowIndex){
				$('#editDictDetail').datagrid('endEdit', lastIndex);
				$('#editDictDetail').datagrid('beginEdit', rowIndex);
			}
			lastIndex = rowIndex;
		}
	});
</script>

</body>

</html>