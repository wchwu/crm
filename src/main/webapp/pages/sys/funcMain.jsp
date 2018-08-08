<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="width:100%;height:100%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统功能管理</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body style="width:100%;height:100%;">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',title:'查询', border:false" style="height: 140px; background: #F4F4F4; overflow: hidden;">
			<form id="searchForm" class="form" onkeydown="if (event.keyCode == 13) { $('#a_search').click();}">
				<table class="table" style="width: 100%;">
					<tbody>
						<tr>
							<th>所属菜单</th>
							<td>
								<select id="slt_menuId" name="func.menuId" style="width:200px;">
        						</select>  
							</td>
							<th>功能名称</th>
							<td>
								<input name="func.funcName" class="easyui-validatebox validatebox-text" style="width: 200px;" />
							</td>
						</tr>
						<tr>
							<th>功能地址</th>
							<td><input name="func.funcUrlPath" class="easyui-validatebox validatebox-text" style="width: 200px;" /></td>
							<th>是否启用</th>
							<td>
								<select id="slt_status" name="func.status" class="easyui-combobox" data-options="dictTypeId: 'SYS_YESORNO'">
								</select>  
							</td>
						</tr>
						<tr>
							<th>是否系统生成</th>
							<td>
								<select id="slt_sysFLag" name="func.sysFlag" class="easyui-combobox" data-options="dictTypeId: 'SYS_YESORNO'">
								</select>  
							</td>
							<th></th>
							<td>
							</td>
						</tr>
						<tr>
							<th colspan="4" style="text-align: center;">
								<a class="easyui-linkbutton l-btn" id="a_search" onclick="$('#tb-func').datagrid('load', SYS.serializeObject($('#searchForm')));"> 查 找 </a>
								<a class="easyui-linkbutton l-btn" onclick="$('#searchForm').form('clear');"> 清 空 </a>
							</th>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<div data-options="region:'center',split:false">
			<!-- 功能列表 -->
			<table id="tb-func" data-options="
					url: SYS.contextPath + '/sys/func/pageView.action',
					view: SYS.datagrid.view,
					emptyMsg: '没有数据',
					fit: true,
					border:false,
					rownumbers: true,
					pagination: true
				">
				<thead data-options="frozen:true">
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'menuName',width:'10%'">所属菜单</th>
						<th data-options="field: 'funcName',width:'10%'">功能名称</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field: 'funcUrlPath'">功能地址</th>
						<th data-options="field: 'statusName'">是否启用</th>
						<th data-options="field: 'logStatusName'">是否启用日志</th>
						<th data-options="field: 'logExpress'">日志表达式</th>
						<th data-options="field: 'sysFlagName'">是否系统生成</th>
						<th data-options="field: 'memo'">备注</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<script type="text/javascript">
	$('#slt_menuId').combotree({
		url : SYS.contextPath + '/sys/menu/tree.action',
		parentField : 'pid',
		editable: true,
		onLoadSuccess: function(node, data) {
	       $('#slt_menuId').combotree('tree').tree('expandAll');
	    },
	    onBeforeSelect: function(node) {
	    	if (node.isleaf != 'y') {
	    		top.$.messager.show({title: "提示", msg: "只能选择叶子菜单" });
	    		return false;
	    	}
	    	return true;
	    }
	});
	 
	$(function() {
		//-------------------------------------------------功能操作分割线
		$('#tb-func').datagrid({
			toolbar: [{
				text: '新增',
				iconCls: 'icon-add',
				handler: function() {
					top.SYS.modalDialog({
						title : '新增功能',
						url : SYS.contextPath + '/pages/sys/funcInput.jsp',
						resizable : true,
						width: '70%',
						height: '65%',
						ctlDom: $('#tb-func')
					});
				}
			},
			{
				text: '修改',
				iconCls: 'icon-edit',
				handler: function() {
					var rows = $('#tb-func').datagrid('getChecked');
					if (rows.length != 1) {
						$.messager.alert('提示', '请选择一行进行修改！', 'warning');
					} else {
						if (rows[0].sysFlag == '0') {
							$.messager.alert('提示', '系统自动生成的功能不支持编辑！', 'warning');
						} else {
							top.SYS.modalDialog({
								title : '编辑功能信息',
								url : SYS.contextPath + '/pages/sys/funcInput.jsp?id=' + rows[0].id,
								resizable : true,
								width: '70%',
								height: '65%',
								ctlDom: $('#tb-func')
							});
						}
					}
				}
			},
			{
				text: '删除',
				iconCls: 'icon-remove',
				handler: function() {
					var rows = $('#tb-func').datagrid('getChecked'),
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
									if (rows[row_len].sysFlag == '0') {
										$.messager.alert('提示', '系统自动生成的功能不能删除！', 'warning');
										return;
									}
									row = rows[row_len];
									idArr.push(row.id);
									
								}
								
								for (var i in idArr) data.push({ name: 'ids', value: idArr[i] });
							    $.ajax({
							       url: SYS.contextPath + "/sys/func/delFunc.action",
							       data: data,
							       dataType: "json",
							       traditional: true,
							       success: function(data, textStatus, jqXHR) {
										if (data.success == "0") {
											$.messager.show({title: "提示", msg: "操作成功" });
											$('#tb-func').datagrid('reload');
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