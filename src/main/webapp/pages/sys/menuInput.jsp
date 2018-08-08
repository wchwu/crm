<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	if (id == null) {
		id = "";
	}
	String pid = request.getParameter("pid");
	if (pid == null) {
		pid = "";
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
			<input id="ipt_id" name="menu.id" type="hidden" value="<%=id%>"/>
			<input id="ipt_pid" name="menu.pid" type="hidden" value="<%=pid%>"/>
			<fieldset>
				<legend>菜单信息录入</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>菜单名称</th>
						<td><input name="menu.text" class="easyui-textbox" data-options="required:true,missingMessage:'该输入项为必输项'" /></td>
						<th>打开方式</th>
						<td>
							<select id="slt_openModel" name="menu.openModel" class="easyui-combobox"  data-options="dictTypeId: 'SYS_OPENMODE',defaultIndex: 0">
							</select>
						</td>
					</tr>
					<tr>
						<th>是否启用</th>
						<td>
							<select id="slt_status" name="menu.status" class="easyui-combobox" data-options="dictTypeId: 'SYS_YESORNO',required:true,defaultIndex: 0">
							</select>  
						<th>是否为叶子菜单</th>
						<td>
							<select id="slt_status" name="menu.isleaf" class="easyui-combobox" data-options="dictTypeId: 'SYS_ISLEAF',required:true,defaultIndex: 0">
							</select>  
						</td>
					</tr>
					<tr>
						<th>链接地址</th>
						<td colspan="3">
							<input name="menu.url" class="easyui-textbox" data-options="missingMessage:'该输入项为必输项'" style="width:400px;" />
						</td>
					</tr>
					<tr>
						<th>显示顺序</th>
						<td><input name="menu.rank" class="easyui-numberbox" /></td>
						<th></th>
						<td></td>
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
		//修改：根据Id去后台查询数据，让后加载到form
		if ($('#ipt_id').val().length > 0) {
			top.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(SYS.contextPath + '/sys/menu/getById.action', {
				id : $('#ipt_id').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'menu.id' : result.id,
						'menu.pid' : result.pid,
						'menu.text' : result.text,
						'menu.openModel' : result.openModel,
						'menu.status' : result.status,
						'menu.isleaf' : result.isleaf,
						'menu.url' : result.url,
						'menu.rank' : result.rank
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
				opts.ctlDom.tree('reload');
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
	            url = SYS.contextPath + '/sys/menu/update.action';
	        } else {
	        	url = SYS.contextPath + '/sys/menu/add.action';
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