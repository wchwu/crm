<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>积分活动管理</title>
	<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',title:'查询', border:false" style="height: 120px; background: #F4F4F4;overflow: hidden;">
			<form id="searchForm" class="form" onkeydown="if (event.keyCode == 13) { $('#a_search').click();}">
				<table class="table" style="width: 100%;">
					<tbody>
						<tr>
							<th>活动类别</th>
							<td>
								<select name="gpId" style="width: 200px;" class="easyui-combobox" data-options="dictTypeId: 'SC_ACT_GP'">
									<option value="">--请选择 --</option>
								</select>
							</td>
							<th>活动名称</th>
							<td><input class="easyui-validatebox" name="actName" /></td>
						</tr>
						<tr>
							<th>活动编号</th>
							<td><input class="easyui-validatebox" name="actCode" /></td>
							<th>是否有效</th>
							<td>
								<select name="vali" style="width: 200px;" class="easyui-combobox" data-options="dictTypeId: 'SC_ACT_VALI'">
									<option value="">--请选择 --</option>
								</select>
							</td>
						</tr>
						<tr>
							<th colspan="4" style="text-align: center;">
								<a class="easyui-linkbutton l-btn" id="a_search" onclick="$('#tb_score').datagrid('load', SYS.serializeObject($('#searchForm')));"> 查 找 </a>
								<a class="easyui-linkbutton l-btn" onclick="$('#searchForm').form('clear');"> 清 空 </a>
							</th>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- 积分活动列表 -->
		<div data-options="region:'center',split:false">
			<table id="tb_score"  data-options="
				url: SYS.contextPath + '/scAct/queryScAct.action',
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
						<th data-options="field:'gpName', width:'10%'">活动类别</th>
						<th data-options="field:'actCode', width:'10%'">活动编号</th>
						<th data-options="field:'actName', width:'20%'">活动名称</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field: 'scDef', width:'10%'">默认积分</th>
						<th data-options="field: 'valiName', width:'10%'">是否有效</th>
						<th data-options="field: 'memo'">备注</th>
						<th data-options="field: '', formatter: settingFormatter, width:100">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
<script type="text/javascript">
	//操作列的formater
	function settingFormatter(value,row,index) {
		return SYS.strFormat('<a href ="javascript:void(0);" onclick="setActDaysCfg(\'{0}\');" >设置<a/>', row.actCode);
	}

	//活动积分日期配置
	function setActDaysCfg(actCode) {
		var dialog = top.SYS.modalDialog({
			title : '活动积分日期配置',
			url : SYS.contextPath + '/pages/scAct/scActDaysCfgMain.jsp?actCode=' + actCode,
			resizable : true,
			width: '65%',
			height: '60%',
			buttons : [ {
				text : '关闭',
				handler : function() {
					dialog.dialog('close');
				}
			} ]
		});
	}
	
	//datagrid 初始化
	$(function() {
		$('#tb_score').datagrid({
				toolbar: [{
					text: '新增',
					iconCls: 'icon-add',
					handler: function() {
						top.SYS.modalDialog({
							title : '活动积分录入',
							url : SYS.contextPath + '/pages/scAct/scActInput.jsp',
							resizable : true,
							width: '70%',
							height: '50%',
							ctlDom: $('#tb_score')
						});
					}
				},
				{
					text: '修改',
					iconCls: 'icon-edit',
					handler: function() {
						var rows = $('#tb_score').datagrid('getChecked');
						if (rows.length != 1) {
							$.messager.alert('提示', '请选择一行进行修改！', 'warning');
						} else {
							top.SYS.modalDialog({
								title : '积分活动修改',
								url : SYS.contextPath + '/pages/scAct/scActInput.jsp?id=' + rows[0].id,
								resizable : true,
								width: '65%',
								height: '60%',
								ctlDom: $('#tb_score')
							});
						}
					}
				},
				{
					text: '删除',
					iconCls: 'icon-remove',
					handler: function() {
						var rows = $('#tb_score').datagrid('getChecked');
						if (rows.length < 1) {
							$.messager.alert('提示', '请选择要删除的数据！！', 'warning');
						} else {
							$.messager.confirm('确认', '你确定要删除[' + rows.length +  ']条数据吗？', function(r) {
								if (r) {
								    $.ajax({
								       url: SYS.contextPath + "/scAct/delAct.action",
								       data: { ids : rows[0].id },
								       dataType: "json",
								       traditional: true,
								       success: function(data, textStatus, jqXHR) {
											if (data.success == "0") {
												$.messager.show({title: "提示", msg: "操作成功" });
												$('#tb_score').datagrid('reload');
											} else {
												top.$.messager.alert('提示', data.errorMsg, 'error');
											}
										}
								    });
								}
							});
						}
					}
				}
				]
		});
	}); 


</script>
</body>
</html>