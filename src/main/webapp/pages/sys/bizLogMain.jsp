<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>业务日志管理</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',title:'查询', border:false" style="height: 120px; background: #F4F4F4;overflow: hidden;">
			<form id="searchForm" class="form" onkeydown="if (event.keyCode == 13) { $('#a_search').click();}">
				<table class="table" style="width: 100%;">
					<tbody>
						<tr>
							<th>登录名</th>
							<td><input name="bizLog.optLoginName" class="easyui-validatebox validatebox-text" style="width: 200px;" /></td>
							<th>操作员名称</th>
							<td><input name="bizLog.optRealName" class="easyui-validatebox validatebox-text" style="width: 200px;" /></td>
						</tr>
						<tr>
							<th>功能名称</th>
							<td><input name="bizLog.funcName" class="easyui-validatebox validatebox-text" style="width: 200px;" /></td>
							<th>操作类别</th>
							<td>
								<select name="bizLog.logType" style="width: 200px;" class="easyui-combobox" data-options="dictTypeId: 'SYS_BIZ_LOG_TYPE'">
									<option value="">--请选择 --</option>
								</select>
							</td>
						</tr>
						<tr>
							<th colspan="4" style="text-align: center;">
								<a class="easyui-linkbutton l-btn" id="a_search" onclick="$('#tb-bizLog').datagrid('load', SYS.serializeObject($('#searchForm')));"> 查 找 </a>
								<a class="easyui-linkbutton l-btn" onclick="$('#searchForm').form('clear');"> 清 空 </a>
							</th>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- 操作人员列表 -->
		<div data-options="region:'center',split:false">
			<table id="tb-bizLog" class="easyui-datagrid" data-options="
				url: SYS.contextPath + '/sys/bizLog/pageView.action',
				view: SYS.datagrid.view,
				emptyMsg: '没有数据',
				fit: true,
				border:false,
				autoRowHeight: true,
				fitColumns: true,
				rownumbers: true,
				singleSelect: true,
				pagination: true
			">
				<thead data-options="frozen:true">
					<tr>
						<th data-options="field:'optLoginName', width:'10%'">登录名</th>
						<th data-options="field:'optRealName', width:'10%'">操作员名称</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field: 'insertDate', width: 10">操作时间</th>
						<th data-options="field: 'menuName', hidden:true">所属菜单</th>
						<th data-options="field: 'funcName', width: 10">功能名称</th>
						<th data-options="field: 'reqPath', hidden:true">请求地址</th>
						<th data-options="field: 'reqParams',formatter:reqParamsFormatter, width: 10">请求参数</th>
						<th data-options="field: 'memo',fixed: true">备注</th>
						<th data-options="field: 'logTypeName', width: 10">操作类型</th>
						<th data-options="field: 'clientIp', width: 10">操作人Ip</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<script type="text/javascript">
		function reqParamsFormatter(value, row, index) {
			if (value && value.length > 10) {
				var part1 = value.substr(0, 7) + '...';
				return '<span onclick="top.$.messager.alert(\'请求参数\', $(this).next(\'span\').text(), \'info\');" style="cursor:pointer;">' + part1 + '</span><span style="display:none">' +value+ '</span>';
			} else {
				return value;
			}
		}
	</script>
</body>
</html>