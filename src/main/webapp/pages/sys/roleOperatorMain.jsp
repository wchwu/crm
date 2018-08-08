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
<title>角色包含的操作员</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<table id="tb-operator"  data-options="
				url: SYS.contextPath + '/sys/operator/pageView.action',
				queryParams: {roleCode: '<%=roleCode %>'},
				view: SYS.datagrid.view,
				emptyMsg: '没有数据',
				fit: true,
				border:false,
				autoRowHeight: true,
				fitColumns: true,
				rownumbers: true,
				pagination: true
			">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'loginName', width:'15%'">登录名</th>
						<th data-options="field:'realName', width:'15%'">操作员名称</th>
						<th data-options="field: 'genderName'">性别</th>
						<th data-options="field: 'email'">Email</th>
						<th data-options="field: 'tel'">tel</th>
					</tr>
				</thead>
	</table>

	<script type="text/javascript">
	
	$(function() {
		
		//-----操作员列表分割线---------------
		$('#tb-operator').datagrid({
			toolbar: [{
				text: '新增',
				iconCls: 'icon-add',
				handler: function() {
					top.SYS.modalDialog({
						title : '选择操作员',
						url : SYS.contextPath + '/pages/sys/operatorSelect.jsp?roleCode=<%=roleCode %>',
						resizable : true,
						width: '70%',
						height: '60%',
						ctlDom: $('#tb-operator')
					});
				}
			},
			{
				text: '删除',
				iconCls: 'icon-remove',
				handler: function() {
					var rows = $('#tb-operator').datagrid('getChecked'),
						row_len = rows.length;
					
					if (row_len < 1) {
						$.messager.alert('提示', '请选择要删除的数据！！', 'warning');
					} else {
						$.messager.confirm('确认', '你确定要删除[' + row_len +  ']条数据吗？', function(r) {
							if (r) {
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
							       url: SYS.contextPath + "/sys/role/deleteRoleOperator.action",
							       data: data,
							       dataType: "json",
							       traditional: true,
							       success: function(data, textStatus, jqXHR) {
										if (data.success == "0") {
											$.messager.show({title: "提示", msg: "操作成功" });
											$('#tb-operator').datagrid('reload');
										} else {
											top.$.messager.alert('提示', data.errorMsg, 'error');
										}
									}
							    });
							}
						});
					}
				}
			}]
		});
	});
	</script>
</body>
</html>