<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String roleCode = request.getParameter("roleCode");
	if (roleCode == null) {
		roleCode = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选择操作员</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',title:'查询', border:false" style="height: 120px; background: #F4F4F4;overflow: hidden;">
			<form id="searchForm" class="form" onkeydown="if (event.keyCode == 13) { $('#a_search').click();}">
				<input type="hidden" name="notInRoleCode" value="<%=roleCode%>" />
				<table class="table" style="width: 100%;">
					<tbody>
						<tr>
							<th>登录名</th>
							<td><input name="operator.loginName" class="easyui-validatebox validatebox-text" style="width: 200px;" /></td>
							<th>操作员名称</th>
							<td><input name="operator.realName" class="easyui-validatebox validatebox-text" style="width: 200px;" /></td>
						</tr>
						<tr>
							<th>Email</th>
							<td><input name="operator.email" class="easyui-validatebox validatebox-text" style="width: 200px;" /></td>
							<th>操作员状态</th>
							<td>
								<select name="operator.status" style="width: 200px;" class="easyui-combobox" data-options="dictTypeId: 'SC_OPERATOR_STATUS'">
									<option value="">--请选择 --</option>
								</select>
							</td>
						</tr>
						<tr>
							<th colspan="4" style="text-align: center;">
								<a class="easyui-linkbutton l-btn" id="a_search" onclick="$('#tb-operator').datagrid('load', SYS.serializeObject($('#searchForm')));"> 查 找 </a>
								<a class="easyui-linkbutton l-btn" onclick="$('#searchForm').form('clear');"> 清 空 </a>
							</th>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- 操作人员列表 -->
		<div data-options="region:'center',split:false">
			<table id="tb-operator"  data-options="
				url: SYS.contextPath + '/sys/operator/pageView.action',
				queryParams: {notInRoleCode: '<%=roleCode %>'},
				view: SYS.datagrid.view,
				emptyMsg: '没有数据',
				fit: true,
				border:false,
				autoRowHeight: true,
				fitColumns: true,
				rownumbers: true,
				pagination: true
			">
				<thead data-options="frozen:true">
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'loginName', width:'10%'">登录名</th>
						<th data-options="field:'realName', width:'10%'">操作员名称</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field: 'authModeName'">密码认证模式</th>
						<th data-options="field: 'statusName'">操作员状态</th>
						<th data-options="field: 'genderName'">性别</th>
						<th data-options="field: 'email'">Email</th>
						<th data-options="field: 'tel'">tel</th>
						<th data-options="field: 'address'">地址</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

<script type="text/javascript">
$(function() {
	//-----操作员列表分割线---------------
	$('#tb-operator').datagrid({
		toolbar: [{
			text: '确认选择',
			iconCls: 'icon-save',
			handler: function() {
				var rows = $('#tb-operator').datagrid('getChecked'),
					row_len = rows.length;
				if ( row_len < 1) {
					$.messager.alert('提示', '请至少选择一行进行操作！', 'warning');
				} else {
					top.$.messager.progress({
						text : '后台处理中....'
					})
					
					//定义变量，接收参数
					var data = [],
						idArr = [],
						row;
					
					while (row_len--) {
						row = rows[row_len];
						idArr.push(row.id);
					}
					
					for (var i in idArr) data.push({ name: 'ids', value: idArr[i] });
					data.push({name: 'roleCode', value: '<%=roleCode %>'})
				    $.ajax({
				       url: SYS.contextPath + "/sys/role/insertRoleOperator.action",
				       data: data,
				       dataType: "json",
				       traditional: true,
				       success: function(data, textStatus, jqXHR) {
				    	    top.$.messager.progress('close');
							if (data.success == "0") {
								top.$.messager.show({title: "提示", msg: "操作成功" });
								closeDialog(true);
							} else {
								top.$.messager.alert('提示', data.errorMsg, 'error');
							}
						}
				    });
				}
			}
		},
		{
			text: '取消选择',
			iconCls: 'ext-icon-cancel',
			handler: function() {
				closeDialog(false);
			}
		}]
	});
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
</script>
</body>
</html>