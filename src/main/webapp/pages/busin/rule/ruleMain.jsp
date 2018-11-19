<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>规章制度管理</title>
	<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
<div class="easyui-layout" data-options="fit:true, border:false">  
		<div data-options="region:'north',title:'查询', border:false" style="height: 100px; background: #F4F4F4;overflow: hidden;">
			<form id="searchForm" class="form" onkeydown="if (event.keyCode == 13) { $('#a_search').click();}">
				<table class="table" style="width: 100%;">
					<tbody>
						<tr>
							<th>制度类型</th>
							<td>
								<select id="ruleType" name="rule.ruleType" class="easyui-combobox" data-options="dictTypeId: 'RULE_TYPE',required:false">
								</select>
							</td>
							<th>制度名称</th>
							<td><input class="easyui-validatebox" name="rule.ruleName" id="ruleName"/></td>
						</tr>
						<tr>
							<th>创建人</th>
							<td><input class="easyui-validatebox" name="rule.creator" id="creator"/></td>
							<th>创建时间</th>
							<td>
								<input id="createTimeBegin"  type="text" class="easyui-datebox">
								~
								<input id="createTimeEnd"  type="text" class="easyui-datebox">
							</td>
						</tr>
						<tr>
							<th colspan="4" style="text-align: center;">
								<a class="easyui-linkbutton l-btn" id="a_search" onclick="queryAjax();"> 查 找 </a>
								<a class="easyui-linkbutton l-btn" onclick="$('#searchForm').form('clear');"> 清 空 </a>
							</th>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- 制度信息列表-->
		<div data-options="region:'center',split:false">
			<table id="tb-rule"  data-options="
				url: SYS.contextPath + '/rule/ruleList.action',
				view: SYS.datagrid.view,
				emptyMsg: '没有数据',
				fit: true,
				border:false,
				autoRowHeight: true,
				fitColumns: true,
				rownumbers: true,
				pagination: true,
				singleSelect: true
			">
				<thead>
					<tr>
						<th data-options="field:'id',hidden: true">成员编号</th>
						<th data-options="field:'ruleTypeName', width: '20%'" class="text-align:center">制度类型</th>
						<th data-options="field:'ruleName', width: '40%'" class="text-align:center">制度名称 </th>
						<th data-options="field:'creator', width: '10%'" class="text-align:center">创建人</th>
						<th data-options="field:'createTime', width: '10%'" class="text-align:center">创建时间</th>
						<th data-options="field:'updater', width: '10%'" class="text-align:center">创建人</th>
						<th data-options="field:'updateTime', width: '10%'" class="text-align:center">创建时间</th>
					</tr>
				</thead>
			</table> 
		</div>
	</div> 

<script type="text/javascript">
function queryAjax(){
	var queryParams = new Object() ;
	queryParams.ruleType = $('#ruleType').combobox('getValue');
	queryParams.ruleName = $('#ruleName').val();
	queryParams.creator = $('#creator').val();
	queryParams.createTimeBegin = $('#createTimeBegin').datebox('getValue');
	queryParams.createTimeEnd = $('#createTimeEnd').datebox('getValue');
  	
	$('#tb-rule').datagrid('load', queryParams);
}

	$(function() {
		$('#tb-rule').datagrid({
			toolbar: [{
				id: 'detail',
				text: '查看',
				iconCls: 'icon-search',
				handler: function() {
					var rows = $('#tb-rule').datagrid('getChecked');
					if (rows.length != 1) {
						$.messager.alert('提示', '请选择一行进行查看！', 'warning');
					} else {
						top.SYS.modalDialog({
							title : '查看规章制度',
							url : SYS.contextPath + '/pages/busin/rule/ruleInput.jsp?op=detail&id=' + rows[0].id ,
							resizable : true,
							width: '80%',
							height: '80%',
							ctlDom: $('#tb-member')
						});
					}
				}
			},{
				id: 'addRule',
				text: '新增',
				iconCls: 'icon-add',
				handler: function() {
					top.SYS.modalDialog({
						title : '新增规章制度',
						url : SYS.contextPath + '/pages/busin/rule/ruleInput.jsp?op=add&op=add',
						resizable : true,
						width: '80%',
						height: '80%',
						ctlDom: $('#tb-rule')
					});
				}
			},
			{
				id: 'updateRule',
				text: '修改规章制度',
				iconCls: 'icon-edit',
				handler: function() {
					var rows = $('#tb-rule').datagrid('getChecked');
					if (rows.length != 1) {
						$.messager.alert('提示', '请选择一行进行修改！', 'warning');
					} else {
						top.SYS.modalDialog({
							title : '编辑成员信息',
							url : SYS.contextPath + '/pages/busin/rule/ruleInput.jsp?op=edit&id=' + rows[0].id,
							resizable : true,
							width: '80%',
							height: '80%',
							ctlDom: $('#tb-rule')
						});
					}
				}
			},
			{
				id: 'deleteRule',
				text: '删除',
				iconCls: 'icon-remove',
				handler: function() {
					var rows = $('#tb-rule').datagrid('getChecked'),
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
							       url: SYS.contextPath + "/rule/delRule.action",
							       data: data,
							       dataType: "json",
							       traditional: true,
							       success: function(data, textStatus, jqXHR) {
										if (data.success == "0") {
											$.messager.show({title: "提示", msg: "操作成功" });
											//$('#tb-member').datagrid('reload');
											$('#tb-rule').datagrid('reload', SYS.serializeObject($('#searchForm')));
										} else {
											top.$.messager.alert('提示', data.errorMsg, 'error');
										}
									}
							    });
							}
						});
					}
				}
			}],
			onLoadSuccess:function(){
		    	buttonHandle();
			}
		});
	});
	
	function buttonHandle(){
		//$('#addMember').hide();
		//$('#updateMember').hide();
		//$('#deleteMember').hide();
	}
</script>
</body>
</html>