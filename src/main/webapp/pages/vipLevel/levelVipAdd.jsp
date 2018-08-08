<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员等级录入</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>会员等级录入</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>会员等级</th>
					<td>
					<select name="vipgrade.gradeid" id="gradeid"  style="width: 200px;" class="easyui-combobox" data-options="dictTypeId: 'SC_LEV'">
						<option value="">--请选择 --</option>
					</select>
					<th>积分系数</th>
					<td><input name="vipgrade.ratio" id="ratio" class="easyui-textbox" data-options="required:true,missingMessage:'该输入项为必输项'" /></td>
				</tr>
				<tr>
					<th>起始积分</th>
					<td><input name="vipgrade.beginscore" id="beginscore" class="easyui-textbox" data-options="required:true,missingMessage:'该输入项为必输项'" /></td>
					<th>结束积分</th>
					<td><input name="vipgrade.endscore" class="easyui-textbox" data-options="required:true,missingMessage:'该输入项为必输项'" /></td>
				</tr>
				<tr>
					<th>网络图片</th>
					<td colspan="3"><input name="vipgrade.picurl" id="picurl" class="easyui-textbox" data-options="required:true,missingMessage:'该输入项为必输项'" /></td>
					
				</tr>
				<tr>
					<th colspan="4" style="text-align: center;">
						<a href="#" class="easyui-linkbutton" onclick="javascript:submitForm();">保存</a>
						<a href="#" class="easyui-linkbutton" onclick="javascript:closeDialog();">关闭</a>  
					</th>
				</tr>
			</table>
		</fieldset>
	</form>
	<script type="text/javascript">
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
	        url = SYS.contextPath + '/vipLevel/add.action';
	        console.info(url);
	        $.post(url, SYS.serializeObject($('.form')), function(result) {
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