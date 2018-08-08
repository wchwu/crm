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
			<table id="tb-scLev"  data-options="
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
						<th data-options="field:'codeName', width: 100" class="text-align:center">会员等级</th>
						<th data-options="field:'scMin', width: 100,formatter: function(value,row){return value + '-' + row.scMax;}" class="text-align:center">积分</th>
						<th data-options="field:'scFac', width: 100" class="text-align:center">积分系数</th>
						<th data-options="field:'picUrl', width: 100,formatter: function(value,row){return '<img src='+row.picUrl+' width=70  height=70 />';}" class="text-align:center">网络图片</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<script type="text/javascript">
	
	$(function() {
		
		//-----操作员列表分割线---------------
		$('#tb-scLev').datagrid({
			url: SYS.contextPath + '/scLev/queryScLev.action',
			toolbar: [{
				text: '新增',
				iconCls: 'icon-add',
				handler: function() {
					top.SYS.modalDialog({
						title : '新增会员等级',
						url : SYS.contextPath + '/pages/scLev/scLevInput.jsp',
						resizable : true,
						width: '60%',
						height: '50%',
						ctlDom: $('#tb-scLev')
					});
				}
			},
			{
				text: '修改',
				iconCls: 'icon-edit',
				handler: function() {
					var rows = $('#tb-scLev').datagrid('getChecked');
					if (rows.length != 1) {
						$.messager.alert('提示', '请选择一行进行修改！', 'warning');
					} else {
						top.SYS.modalDialog({
							title : '编辑会员等级信息',
							url : SYS.contextPath + '/pages/scLev/scLevInput.jsp?id=' + rows[0].id,
							resizable : true,
							width: '60%',
							height: '50%',
							ctlDom: $('#tb-scLev')
						});
					}
				}
			},
			{
				text: '删除',
				iconCls: 'icon-remove',
				handler: function() {
					var rows = $('#tb-scLev').datagrid('getChecked'),
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
							    $.ajax({
							       url: SYS.contextPath + "/scLev/delActLev.action",
							       data: data,
							       dataType: "json",
							       traditional: true,
							       success: function(data, textStatus, jqXHR) {
										if (data.success == "0") {
											$.messager.show({title: "提示", msg: "操作成功" });
											$('#tb-scLev').datagrid('reload');
										} else {
											top.$.messager.alert('提示', data.errorMsg, 'error');
										}
									}
							    });
							}
						});
					}
				}
			},{
				text: '会员重新评级',
				iconCls: 'ext-icon-table_refresh',
				handler: function() {
					$.messager.confirm('确认', '你确定要对会员进行重新评级吗？', function(r) {
						$.messager.progress({
							text : '数据处理中....'
						});
						
						$.ajax({
						       url: SYS.contextPath + '/scLev/rebuildScLv.action',
						       dataType: "json",
						       success: function(data, textStatus, jqXHR) {
						    	   $.messager.progress('close');
									if (data.success == "0") {
										$.messager.show({title: "提示", msg: "操作成功" });
									} else {
										top.$.messager.alert('提示', data.errorMsg, 'error');
									}
								},
								error: function(XMLHttpRequest, textStatus, errorThrown) {
									$.messager.progress('close');
								}
					    });
						
					});
				}
			}]
		});
	});
	</script>
</body>
</html>