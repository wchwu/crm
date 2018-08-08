<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	if (id == null) {
		id = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>操作员录入</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<form method="post" class="form">
			<input type="hidden" id="ipt_id" name="func.id" value="<%=id %>"/>
			<input type="hidden" name="func.sysFlag" value="1"/>
			<fieldset>
				<legend>功能信息录入</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>所属菜单</th>
						<td>
							<select id="slt_menuId" name="func.menuId" style="width:200px;">
        					</select>  
						<th>功能名称</th>
						<td>
							<input name="func.funcName" class="easyui-textbox" data-options="required:true, missingMessage:'该输入项为必输项'" />
						</td>
					</tr>
					<tr>
						<th>功能访问地址</th>
						<td>
							<input name="func.funcUrlPath" class="easyui-textbox" data-options="required:true, missingMessage:'该输入项为必输项'" style="width:200px;" />
						</td>
						<th>是否启用</th>
						<td>
							<select id="slt_status" name="func.status" class="easyui-combobox" data-options="dictTypeId: 'SYS_YESORNO',required:true,defaultIndex: 0">
							</select>  
						</td>
					</tr>
					<tr>
						<th>是否启用日志</th>
						<td>
							<select id="slt_status" name="func.logStatus" class="easyui-combobox" data-options="dictTypeId: 'SYS_YESORNO',required:true,defaultIndex: 1">
							</select>  
						</td>
						<th>功能类型</th>
						<td>
							<select id="slt_logType" name="func.logType" class="easyui-combobox" data-options="dictTypeId: 'SYS_BIZ_LOG_TYPE',required:true,defaultIndex: 0">
							</select>  
						</td>
					</tr>
					<tr>
						<th>日志表达式</th>
						<td colspan="3">
							<input name="func.logExpress" class="easyui-textbox" data-options="multiline:true,width:'80%', height:70" />
						</td>
					</tr>
					<tr>
						<th>功能备注信息</th>
						<td colspan="3">
							<input name="func.memo" class="easyui-textbox" data-options="multiline:true,width:'80%', height:70" />
						</td>
					</tr>
					<tr>
						<th colspan="4" style="text-align: center;">
							<a href="#" class="easyui-linkbutton" onclick="javascript:submitForm();"> 保 存 </a>
							<a href="#" class="easyui-linkbutton" onclick="javascript:closeDialog();">关闭</a>  
						</th>
					</tr>
				</table>
			</fieldset>
	</form>
	<script type="text/javascript">
	$(function() {
		$('#slt_menuId').combotree({
			url : SYS.contextPath + '/sys/menu/tree.action',
			parentField : 'pid',
			onLoadSuccess: function(node, data) {
		       $('#slt_menuId').combotree('tree').tree('expandAll');
		    },
		    onBeforeSelect: function(node) {
		    	console.info(node);
		    	if (node.isleaf != 'y') {
		    		top.$.messager.show({title: "提示", msg: "只能选择叶子菜单" });
		    		return false;
		    	}
		    	return true;
		    }
		});
		
		
		//修改：根据Id去后台查询数据，让后加载到form
		if ($('#ipt_id').val().length > 0) {
			top.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(SYS.contextPath + '/sys/func/getById.action', {
				id : $('#ipt_id').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'func.id' : result.id,
						'func.menuId' : result.menuId,
						'func.funcName' : result.funcName,
						'func.funcUrlPath' : result.funcUrlPath,
						'func.status' : result.status,
						'func.logStatus' : result.logStatus,
						'func.logType' : result.logType,
						'func.logExpress' : result.logExpress,
						'func.memo' : result.memo
					});
					
				}
				top.$.messager.progress('close');
			}, 'json');
		}
	});
	
	function closeDialog(isRefresh) {
		var $dialog = parent.$('#dialog_' + window.name);
		if ($dialog) {
			var opts = $dialog.dialog('options');
			if (isRefresh && opts.ctlDom) {
				opts.ctlDom.datagrid('reload');
			}
			$dialog.dialog('close');
		} else {
			window.close();
		}
	}
	
	function submitForm() {
	    var url;
	    if ($('form').form('validate')) {
	        if ($('#ipt_id').val().length > 0) {
	            url = SYS.contextPath + '/sys/func/updateFunc.action';
	        } else {
	        	url = SYS.contextPath + '/sys/func/addFunc.action';
	        }
	        $.post(url, SYS.serializeObject($('form')), function(result) {
				if (result.success == '0') {
					top.$.messager.show({title: "提示", msg: "操作成功" });
					closeDialog(true);
				} else {
					top.$.messager.alert('提示', result.errorMsg, 'error');
				}
			}, 'json');
	    }
	};
	</script>	
</body>
</html>