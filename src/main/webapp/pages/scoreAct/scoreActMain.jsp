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
								<select name="gpids" style="width: 200px;" class="easyui-combobox" data-options="dictTypeId: 'SC_ACT_GP'">
									<option value="">--请选择 --</option>
								</select>
							</td>
							<th>活动名称</th>
							<td><input class="easyui-validatebox" name="actNames" /></td>
						</tr>
						<tr>
							<th>活动编号</th>
							<td><input class="easyui-validatebox" name="actcodes" /></td>
							<th>是否有效</th>
							<td>
								<select name="valis" style="width: 200px;" class="easyui-combobox" data-options="dictTypeId: 'SC_ACT_VALI'">
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
				url: SYS.contextPath + '/sc/queryScAct.action',
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
						<th data-options="field:'gpname', width:'10%'">活动类别</th>
						<th data-options="field:'actcode', width:'10%'">活动编号</th>
						<th data-options="field:'actName', width:'20%'">活动名称</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field: 'score',formatter:scoreFormatter, width:'10%'">默认积分</th>
						<th data-options="field: 'vali',formatter: valiFormatter, width:'10%'">是否有效</th>
						<th data-options="field: 'remark'">备注</th>
						<th data-options="field: '', formatter: settingFormatter, width:100">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
<script type="text/javascript">
	//积分列的单击设置
	function scoreFormatter(value,row,index) {
		return SYS.strFormat('<a href ="javascript:void(0);" onclick="eidtScore(\'{0}\');" >{1}<a/>', row.actcode, value);
	}
	
	//是否有效列的设置
	function valiFormatter(value,row,index) {
		return value == 1 ? '有效':'无效';
	}

	//操作列的formater
	function settingFormatter(value,row,index) {
		return SYS.strFormat('<a href ="javascript:void(0);" onclick="editScoreAct({0});" >设置<a/>', row.id);
	}

	//修改积分活动的 积分段信息
	function eidtScore(actCode) {
		var dialog = top.SYS.modalDialog({
			title : '积分活动时间段配置',
			url : SYS.contextPath + '/pages/scoreAct/scActDateListMain.jsp?id=' + actCode,
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
	
	//修改积分活动信息
	function editScoreAct(scActId){
		top.SYS.modalDialog({
			title : '积分活动修改',
			url : SYS.contextPath + '/pages/scoreAct/scoreActInput.jsp?id=' + scActId,
			resizable : true,
			width: '65%',
			height: '60%',
			ctlDom: $('#tb_score')
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
							url : SYS.contextPath + '/pages/scoreAct/scoreActInput.jsp',
							resizable : true,
							width: '50%',
							height: '70%',
							ctlDom: $('#tb_score')
						});
					}
				}]
		});
	}); 


</script>
</body>
</html>