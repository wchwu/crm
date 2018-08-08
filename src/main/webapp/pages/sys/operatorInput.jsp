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
<script type="text/javascript">
	$(function() {
		//修改：根据Id去后台查询数据，让后加载到form
		if ($('#ipt_operatorId').val().length > 0) {
			top.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(SYS.contextPath + '/sys/operator/getById.action', {
				id : $('#ipt_operatorId').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'operator.id' : result.id,
						'operator.loginName' : result.loginName,
						'operator.authMode' : result.authMode,
						'operator.password' : result.password,
						'operator.status' : result.status,
						'operator.realName' : result.realName,
						'operator.tel' : result.tel,
						'operator.gender' : result.gender,
						'operator.email' : result.email,
						'operator.address' : result.address
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
	        if ($('#ipt_operatorId').val().length > 0) {
	            url = SYS.contextPath + '/sys/operator/update.action';
	        } else {
	        	url = SYS.contextPath + '/sys/operator/add.action';
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
	}
	</script>	
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>操作人信息录入</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>编号</th>
					<td><input id="ipt_operatorId" title="编号不可编辑" name="operator.id" value="<%=id %>" class="easyui-textbox easyui-tooltip" readonly="readonly" /></td>
					<th>登录名</th>
					<td><input name="operator.loginName" class="easyui-textbox" data-options="required:true,missingMessage:'该输入项为必输项',iconCls:'icon-man'" /></td>
				</tr>
				<tr>
					<th>认证模式</th>
					<td>
						<select id="slt_authMode" name="operator.authMode" class="easyui-combobox"  data-options="dictTypeId: 'SYS_AUTHMODE',required:true,defaultIndex: 0">
						</select>
					</td>
					<th>密码</th>
					<td><input type="password" id="ipt_password" name="operator.password" class="easyui-textbox" data-options="type: 'password',required:true,missingMessage:'该输入项为必输项',iconCls:'icon-lock'" /></td>
				</tr>
				<tr>
					<th>操作员状态</th>
					<td>
						<select id="slt_status" name="operator.status" class="easyui-combobox" data-options="dictTypeId: 'SC_OPERATOR_STATUS',required:true,defaultIndex: 0">
						</select>  
					<th>操作员名称</th>
					<td><input name="operator.realName" class="easyui-textbox" data-options="required:true,missingMessage:'该输入项为必输项'"/></td>
				</tr>
				<tr>
					<th>联系电话</th>
					<td><input name="operator.tel" class="easyui-textbox" /></td>
					<th>性别</th>
					<td>
						<select id="slt_gender"  name="operator.gender" class="easyui-combobox" data-options="dictTypeId: 'SYS_GENDER',required:true,defaultIndex: 0">
						</select>  
					</td>
				</tr>
				<tr>
					<th>邮箱</th>
					<td><input name="operator.email" class="easyui-textbox" data-options="validType:'email'" /></td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<th>住址</th>
					<td colspan="3">
						<input name="operator.address" class="easyui-textbox" data-options="multiline:true,width:'60%', height:50" />
					</td>
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
</body>
</html>