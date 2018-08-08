<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>操作员录入</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
<form method="post" id="modifyPasswordForm" class="form">
		<fieldset>
			<legend>修改密码</legend>
			<table class="table" style="width:100%">
				<tr>
					<th>请输入原密码：</th>
					<td><input id="oldPassword" name="oldPassword" type="password" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>请输入新密码：</th>
					<td><input id="newPassword" name="newPassword" type="password" class="easyui-validatebox" data-options="required:true,validType:'neqPwd[\'#oldPassword\']'" /></td>
				</tr>
				<tr>
					<th>请确认新密码：</th>
					<td><input type="password" class="easyui-validatebox" data-options="required:true,validType:'eqPwd[\'#newPassword\']'" /></td>
				</tr>
				<tr>
					<th colspan="2" style="text-align: center;">
						<a href="#" class="easyui-linkbutton" onclick="javascript:submitForm();">保&nbsp;存 </a>
						<a href="#" class="easyui-linkbutton" onclick="javascript:closeDialog();">关&nbsp;闭 </a>
					</th>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
<script type="text/javascript">
function submitForm() {
	if ($('#modifyPasswordForm').form('validate')) {
		
		$.post(SYS.contextPath + '/sys/operator/modifyPassword.action', {
			'oldPassword' : $('#oldPassword').val(),
			'newPassword' : $('#newPassword').val()
		}, function(result) {
			if (result.success == '0') {
				top.$.messager.show({title: "提示", msg: "操作成功" });
				closeDialog();
			} else {
				top.$.messager.alert('提示', result.errorMsg, 'error');
			}
		}, 'json');
	}
}

function closeDialog() {
	var $dialog = parent.$('#dialog_' + window.name);
	if ($dialog) {
		$dialog.dialog('close');
	} else {
		window.close();
	}
}

</script>
</html>