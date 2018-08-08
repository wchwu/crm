<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>业务字典管理</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true, border:false">
		<%-- 左侧业务字典类型查询 --%>
		<div data-options="region:'center',border:false"
			style="overflow: hidden;">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'north',title:'查询', border:false"
					style="height: 110px; background: #F4F4F4;">
					<form id="searchForm" class="form" onkeydown="if (event.keyCode == 13) { $('#a_search').click();}">
						<table class="table" style="width: 100%;">
							<tbody>
								<tr>
									<th>类型代码/名称</th>
									<td><input name="dictTypeName" class="easyui-validatebox validatebox-text" style="width: 200px;"/></td>
									<th>字典项名称</th>
									<td><input name="dictName" class="easyui-validatebox validatebox-text" style="width: 200px;" /></td>
								</tr>
								<tr>
									<td colspan="4" style="text-align: center;">
										<a class="easyui-linkbutton l-btn" id="a_search" onclick="searchFunc();"> 查 找 </a>
										<a class="easyui-linkbutton l-btn" onclick="$('#searchForm').form('clear');"> 清 空 </a>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>

				<!-- 业务字典类型列表 -->
				<div data-options="region:'center',split:false">
					<table id="tb-dict"
						data-options="
						url: SYS.contextPath + '/sys/dict/dictTypePageView.action',  
					    iconCls: 'icon-save',
					    singleSelect: true,
					    view: SYS.datagrid.view,
					    emptyMsg: '没有数据',
					    fit: true,
					    border:false,
					    autoRowHeight: true,
					    fitColumns: true,
					    rownumbers: true,
					    pagination: true,
				        onSelect: onSelect
					">
						<thead>
							<tr>
								<th
									data-options="field:'dictTypeId', width:'35%', editor:{
									type: 'textbox', 
									options: {required:true, missingMessage: '不能为空'}
								}">类型代码</th>
								<th
									data-options="field:'dictTypeName', width:'45%', editor: {type: 'textbox', options:{required:true, missingMessage: '不能为空'}}">类型名称</th>
								<th
									data-options="field: '_blank',width:'15%', align: 'center', formatter: formatterDetail">显示字典项</th>
							</tr>
						</thead>
					</table>
				</div>

			</div>
		</div>

		<%--右侧 --%>
		<div
			data-options="region:'east', width:'45%',split: true,collapsible: true"
			style="overflow: hidden;">
			<table id="tb-dict-entry" class="easyui-datagrid"
				data-options="
				    iconCls: 'icon-save',
				    fit: true,
				    autoRowHeight: false,
				    border:false,
				    fitColumns: true,
				    singleSelect: true
				">
				<thead>
					<tr>
						<th data-options="field:'dictTypeId',hidden: true"></th>
						<th
							data-options="field:'dictId', width:'24%', editor:{
									type: 'textbox', 
									options: {required:true, missingMessage: '不能为空'}
								}">字典项代码</th>
						<th
							data-options="field:'dictName', width:'24%', editor: {type: 'textbox', options:{required:true, missingMessage: '不能为空'}}">字典项名称</th>
						<th
							data-options="field:'sortNo', width:'24%', editor: 'numberbox'">排序</th>
						<th
							data-options="field:'status', width:'24%', formatter: formatterStatus, editor:{type:'checkbox', options:{on:'0', off:'1'}}">启用</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<script type="text/javascript">
	//----------------数据字典激情分割线
	$('#tb-dict').datagrid({
		toolbar: [{
			text: '新增',
			iconCls: 'icon-add',
			handler: function() {
				$('#tb-dict').datagrid('appendRow', {
					dictTypeId: '',
					dictTypeName: ''
				});
				var rows = $('#tb-dict').datagrid('getRows');
				$('#tb-dict').datagrid('beginEdit', rows.length - 1);
			}
		},
		{
			text: '修改',
			iconCls: 'icon-edit',
			handler: function() {
				var row = $('#tb-dict').datagrid('getSelected');
				if (row) {
					var rowIndex = $('#tb-dict').datagrid('getRowIndex', row);
					$('#tb-dict').datagrid('editCell', {
						index: rowIndex,
						field: 'dictTypeName'
					});
				}
			}
		},
		{
			text: '删除',
			iconCls: 'icon-remove',
			handler: function() {
				var row = $('#tb-dict').datagrid('getSelected');
				if (row) {
					var rowIndex = $('#tb-dict').datagrid('getRowIndex', row);
					$('#tb-dict').datagrid('deleteRow', rowIndex);
				}
			}
		},
		'-', {
			text: "取消修改",
			iconCls: "icon-undo",
			handler: function() {
				$('#tb-dict').datagrid('rejectChanges');
			}
		},
		'-', {
			text: "保存",
			iconCls: "icon-save",
			handler: function() {
				var rows = $('#tb-dict').datagrid('getRows');
				for (var i = 0; i < rows.length; i++) {
					$('#tb-dict').datagrid('endEdit', i);
				}
				if ($('#tb-dict').datagrid("getChanges").length) {
					var inserted = $('#tb-dict').datagrid('getChanges', "inserted");
					var deleted = $('#tb-dict').datagrid('getChanges', "deleted");
					var updated = $('#tb-dict').datagrid('getChanges', "updated");

					var effectRow = new Object();
					if (inserted.length) {
						effectRow["inserted"] = JSON.stringify(inserted);
					}
					if (deleted.length) {
						effectRow["deleted"] = JSON.stringify(deleted);
					}
					if (updated.length) {
						effectRow["updated"] = JSON.stringify(updated);
					}
					$.post(SYS.contextPath + "/sys/dict/saveDictType.action", effectRow,
					function(rsp) {
						if (rsp.success) {
							$.messager.show({title: "提示", msg: "操作成功" });
							editIndex = undefined;
							$('#tb-dict').datagrid('acceptChanges').datagrid('reload');
						}
					},
					"JSON").error(function(rsp) {
						$.messager.alert("提示", "提交错误了！" + rsp);
					});
				} else {
					$.messager.alert("提示", "未做修改！");
				}
			}
		}]
	});
	//----------------数据字典激情分割线
	//格式化操作列
	function formatterDetail(value, row, index) {
		return '<span class="img-btn iconImg ext-icon-application_view_detail" title="显示字典项" "></span>';
	}

	function formatterStatus(value, row, index) {
		return row.statusName;
	}
	//业务字典查询
	function searchFunc() {
		$('#tb-dict').datagrid('load', SYS.serializeObject($('#searchForm')));
	}
	
	//选择行时就触发显示 数据项
	function onSelect(rowIndex, rowData) {
		showDictEntry(rowData.dictTypeId);
	}

	//--------数据字典有力分割线

	function showDictEntry(tid) {
		$('#tb-dict-entry').datagrid({
			pagination: true,
			url: SYS.contextPath + '/sys/dict/entryPageView.action',
			queryParams: {
				dictTypeId: tid
			},
			toolbar: [{
				text: '新增',
				iconCls: 'icon-add',
				handler: function() {
					var $dom = $('#tb-dict-entry'),
						rows = $dom.datagrid('getRows');
					
					$dom.datagrid('appendRow', {dictTypeId: tid, status: 0});
					$dom.datagrid('beginEdit', rows.length - 1);
				}
			},
			{
				text: '修改',
				iconCls: 'icon-edit',
				handler: function() {
					var $dom = $('#tb-dict-entry'),
						row = $dom.datagrid('getSelected'),
						rowIndex, ed;
					
					if (row) {
						rowIndex = $dom.datagrid('getRowIndex', row);
						
						$dom.datagrid('beginEdit', rowIndex);
						ed = $dom.datagrid('getEditor', {index: rowIndex, field: 'dictId'});
						console.info(ed);
						$(ed.target).textbox('readonly', true);
					}
				}
			},
			{
				text: '删除',
				iconCls: 'icon-remove',
				handler: function() {
					var $dom = $('#tb-dict-entry'),
						row = $dom.datagrid('getSelected');
					if (row) {
						var rowIndex = $dom.datagrid('getRowIndex', row);
						$dom.datagrid('deleteRow', rowIndex);
					}
				}
			},
			'-',
			{
				text: "取消修改",
				iconCls: "icon-undo",
				handler: function() {
					$('#tb-dict-entry').datagrid('rejectChanges');
				}
			},
			'-',
			{
				text: "保存",
				iconCls: "icon-save",
				handler: function() {
					var $dom = $('#tb-dict-entry'),
						rows = $('#tb-dict-entry').datagrid('getRows');
					
					//必须先结束行编辑模式，easyui才能得到 增、删、改的数据
					for (var i = 0; i < rows.length; i++) {
						$dom.datagrid('endEdit', i);
					}
					
					//判断是否需要提交到后台
					if ($dom.datagrid("getChanges").length) {
						var inserted = $dom.datagrid('getChanges', "inserted");
						var deleted = $dom.datagrid('getChanges', "deleted");
						var updated = $dom.datagrid('getChanges', "updated");
	
						var effectRow = new Object();
						if (inserted.length) {
							effectRow["inserted"] = JSON.stringify(inserted);
						}
						if (deleted.length) {
							effectRow["deleted"] = JSON.stringify(deleted);
						}
						if (updated.length) {
							effectRow["updated"] = JSON.stringify(updated);
						}
						$.post(SYS.contextPath + "/sys/dict/saveDictEntry.action", effectRow,
						function(rsp) {
							if (rsp.success) {
								$.messager.show({title: "提示", msg: "操作成功" });
								$dom.datagrid('acceptChanges').datagrid('reload');
							}
						},
						"JSON").error(function(rsp) {
							$.messager.alert("提示", "提交错误了！" + rsp);
						});
					} else {
						$.messager.alert("提示", "未做修改！");
					}
				}
			}]
	
		});
	}
	</script>
</body>
</html>