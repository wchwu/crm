<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员等级管理</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		
		<div data-options="region:'center',split:false">
			<table id="tb-operator"  data-options="
				url: SYS.contextPath + '/vipLevel/vipLevelQuery.action',
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
						<th data-options="field:'gradeid',hidden:true" class="text-align:center"></th>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'gradename', width: 100" class="text-align:center">会员等级</th>
						<th data-options="field:'beginscore', width: 100,formatter: function(value,row){return value + '-' + row.endscore;}" class="text-align:center">积分</th>
						<th data-options="field:'ratio', width: 100" class="text-align:center">积分系数</th>
						<th data-options="field:'picurl', width: 100,formatter: function(value,row){return '<img src='+row.picurl+' width=70  height=70 />';}" class="text-align:center">网络图片</th>
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
				text: '新增',
				iconCls: 'icon-add',
				handler: function() {
					top.SYS.modalDialog({
						title : '新增会员等级',
						url : SYS.contextPath + '/pages/vipLevel/levelVipAdd.jsp',
						resizable : true,
						width: '50%',
						height: '30%',
						ctlDom: $('#tb-operator')
					});
				}
			},
			{
				text: '修改',
				iconCls: 'icon-edit',
				handler: function() {
					var rows = $('#tb-operator').datagrid('getChecked');
					if (rows.length != 1) {
						$.messager.alert('提示', '请选择一行进行修改！', 'warning');
					} else {
						top.SYS.modalDialog({
							title : '编辑会员等级信息',
							url : SYS.contextPath + '/pages/vipLevel/levelVipEdit.jsp?id=' + rows[0].gradeid,
							resizable : true,
							width: '60%',
							height: '45%',
							ctlDom: $('#tb-operator')
						});
					}
				}
			},
			{
				text: '删除',
				iconCls: 'icon-remove',
				handler: function() {
					var rows = $('#tb-operator').datagrid('getChecked');
					if (rows.length < 1) {
						$.messager.alert('提示', '请选择要删除的数据！！', 'warning');
					} else {
						$.messager.confirm('确认', '你确定要删除[' + rows.length +  ']条数据吗？', function(r) {
							if (r) {
							    $.ajax({
							       url: SYS.contextPath + "/vipLevel/del.action",
							       data: { ids : rows[0].gradeid },
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