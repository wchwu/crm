<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统角色管理</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true, border:false">
		<%-- 左侧系统角色查询 --%>
		<div data-options="region:'center',border:false"
			style="overflow: hidden;">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'north',title:'查询', border:false"
					style="height: 115px; background: #F4F4F4;overflow: hidden;">
					<form id="searchForm" class="form" onkeydown="if (event.keyCode == 13) { $('#a_search').click();}">
						<table class="table" style="width: 100%;">
							<tbody>
								<tr>
									<th>角色代码</th>
									<td><input name="roleCode" class="easyui-validatebox validatebox-text" style="width: 200px;"/></td>
								</tr>
								<tr>
									<th>角色名称</th>
									<td><input name="roleName" class="easyui-validatebox validatebox-text" style="width: 200px;" /></td>
								</tr>
								<tr>
									<td colspan="2" style="text-align: center;">
										<a class="easyui-linkbutton l-btn" id="a_search" onclick="searchFunc();"> 查 找 </a>
										<a class="easyui-linkbutton l-btn" onclick="$('#searchForm').form('clear');"> 清 空 </a>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>

				<!-- 系统角色列表 -->
				<div data-options="region:'center',split:false">
					<table id="tb-role"
						data-options="
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
									data-options="field:'roleCode', width:'34%', editor:{
									type: 'textbox', 
									options: {required:true, missingMessage: '不能为空'}
								}">角色代码</th>
								<th
									data-options="field:'roleName', width:'44%', editor: {type: 'textbox', options:{required:true, missingMessage: '不能为空'}}">类型名称</th>
								<th
									data-options="field: '_blank',width:'15%', align: 'center', formatter: formatterDetail">编辑权限</th>
							</tr>
						</thead>
					</table>
				</div>

			</div>
		</div>

		<%--右侧 --%>
		<div
			data-options="region:'east', width:'55%',split: true,collapsible: true"
			style="overflow: hidden;">
			<div id="div_tabs" class="easyui-tabs" data-options="fit:true, border:false">   
			    <div title="菜单功能-权限" style="padding: 5px;">
			    	 <input id="ipt_cur_roleCode" type="hidden"/>
			      	 <a href="#" id="a_save_top" style="display: none;" onclick="saveFuncTree();" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a> 
			      	 <ul id="ul_auth_tree" style="padding-top: 2px; padding-bottom: 2px;"></ul>
			      	 <a href="#" id="a_save_bottom" style="display: none;" onclick="saveFuncTree();" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
			    </div>   
			    <div title="权限分配" style="overflow:auto;"> 
			        <iframe id="ifm_roleOperator" src="" allowTransparency="true" scrolling="auto" width="100%" height="98%" frameBorder="0" ></iframe>
			    </div>   
			</div>
		</div>
	</div>

	<script type="text/javascript">
	//----------------系统角色 开始分割线--------------------------------------------------------
	$('#tb-role').datagrid({
		url: SYS.contextPath + '/sys/role/view.action',  
		toolbar: [{
			text: '新增',
			iconCls: 'icon-add',
			handler: function() {
				$('#tb-role').datagrid('appendRow', {
					roleCode: '',
					roleName: ''
				});
				var rows = $('#tb-role').datagrid('getRows');
				$('#tb-role').datagrid('beginEdit', rows.length - 1);
			}
		},
		{
			text: '修改',
			iconCls: 'icon-edit',
			handler: function() {
				var row = $('#tb-role').datagrid('getSelected');
				if (row) {
					var rowIndex = $('#tb-role').datagrid('getRowIndex', row);
					$('#tb-role').datagrid('editCell', {
						index: rowIndex,
						field: 'roleName'
					});
				}
			}
		},
		{
			text: '删除',
			iconCls: 'icon-remove',
			handler: function() {
				var row = $('#tb-role').datagrid('getSelected');
				if (row) {
					var rowIndex = $('#tb-role').datagrid('getRowIndex', row);
					$('#tb-role').datagrid('deleteRow', rowIndex);
				}
			}
		},
		'-', {
			text: "取消修改",
			iconCls: "icon-undo",
			handler: function() {
				$('#tb-role').datagrid('rejectChanges');
			}
		},
		'-', {
			text: "保存",
			iconCls: "icon-save",
			handler: function() {
				var rows = $('#tb-role').datagrid('getRows');
				for (var i = 0; i < rows.length; i++) {
					$('#tb-role').datagrid('endEdit', i);
				}
				if ($('#tb-role').datagrid("getChanges").length) {
					var inserted = $('#tb-role').datagrid('getChanges', "inserted");
					var deleted = $('#tb-role').datagrid('getChanges', "deleted");
					var updated = $('#tb-role').datagrid('getChanges', "updated");

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
					$.post(SYS.contextPath + "/sys/role/saveBatch.action", effectRow,
					function(rsp) {
						if (rsp.success) {
							$.messager.show({title: "提示", msg: "操作成功" });
							editIndex = undefined;
							$('#tb-role').datagrid('acceptChanges').datagrid('reload');
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
	
	//----------------系统角色结束分割线--------------------------------------------------------
	
	//格式化操作列
	function formatterDetail(value, row, index) {
		return '<span class="img-btn iconImg ext-icon-application_view_detail" title="编辑权限" "></span>';
	}

	//系统角色查询
	function searchFunc() {
		$('#tb-role').datagrid('load', SYS.serializeObject($('#searchForm')));
	}
	
	//选择行时就触发显示 数据项
	function onSelect(rowIndex, rowData) {
		showMenuFuncTree(rowData.roleCode);
	}

	//-----------------------------------------------功能权限树分配---------------------------------------------
	function showMenuFuncTree(roleCode) {
		var $treeDom  = $('#ul_auth_tree');
		//初始化功能树
		$treeDom.tree({
			url : SYS.contextPath + '/sys/func/treeView.action',
			parentField : 'pid',
			queryParams: {roleCode: roleCode},
			checkbox: true,
			lines : true,
			onLoadSuccess: function() {
				$treeDom.tree('collapseAll');
				$('#ipt_cur_roleCode').val(roleCode);
				$('#a_save_top').show();
				$('#a_save_bottom').show();
			}
		});
		
		//显示对应的人员列表
		$('#ifm_roleOperator').attr('src', SYS.contextPath + '/pages/sys/roleOperatorMain.jsp?roleCode=' + roleCode);
	}


	// 保存功能树
	function saveFuncTree() {
		var $treeDom = $('#ul_auth_tree'),
			ckNodes = $treeDom.tree('getChecked'),
			len = ckNodes.length,
			idArr = [],
			data = [];
		while (len--) {
			if ($treeDom.tree('isLeaf', ckNodes[len].target)) {
				idArr.push(ckNodes[len].id);
			}
		}
		
		//ajax 提交
		for (var i in idArr) data.push({ name: 'ids', value: idArr[i] });
		data.push({name: 'roleCode', value: $('#ipt_cur_roleCode').val()});
		$.ajax({
		       url: SYS.contextPath + "/sys/func/saveFuncTree.action",
		       data: data,
		       dataType: "json",
		       traditional: true,
		       success: function(data, textStatus, jqXHR) {
					if (data.success == "0") {
						$.messager.show({title: "提示", msg: "操作成功" });
						$treeDom.tree('reload');
					} else {
						top.$.messager.alert('提示', data.errorMsg, 'error');
					}
				}
	    });
	}
	</script>
</body>
</html>