<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>成员管理</title>
	<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
<div class="easyui-layout" data-options="fit:true, border:false">  
		<div data-options="region:'north',title:'查询', border:false" style="height: 150px; background: #F4F4F4;overflow: hidden;">
			<form id="searchForm" class="form" onkeydown="if (event.keyCode == 13) { $('#a_search').click();}">
				<table class="table" style="width: 100%;">
					<tbody>
						<tr>
							<th>姓名</th>
							<td>
								<input class="easyui-validatebox" name="member.name" />
							</td>
							<th>身份证号</th>
							<td><input class="easyui-validatebox" name="member.certNo" /></td>
						</tr>
						<tr>
							<th>客户号</th>
							<td><input class="easyui-validatebox" name="custCurSc.custId" /></td>
							<th></th>
							<td>
							</td>
						</tr>
						<tr>
							<th>当前积分</th>
							<td>
								<input type="text" name="beginScScore" class="easyui-numberbox"></input>
								至
								<input type="text" name="endScScore" class="easyui-numberbox"></input>
							</td>
							<th>
								冻结积分
							</th>
							<td>
								<input type="text" name="beginScFrz" class="easyui-numberbox"></input>
								至
								<input type="text" name="endScFrz" class="easyui-numberbox"></input>
							</td>
						</tr>
						<tr>
							<th colspan="4" style="text-align: center;">
								<a class="easyui-linkbutton l-btn" id="a_search" onclick="$('#tb-custCurSc').datagrid('load', SYS.serializeObject($('#searchForm')));"> 查 找 </a>
								<a class="easyui-linkbutton l-btn" onclick="$('#searchForm').form('clear');"> 清 空 </a>
							</th>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- 客户信息列表-->
		<div data-options="region:'center',split:false">
			<table id="tb-member"  data-options="
				url: SYS.contextPath + '/member/memberList.action',
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
						<th data-options="field:'id', width: '15%'" class="text-align:center">成员编号</th>
						<th data-options="field:'name', width: '20%'" class="text-align:center">成员姓名 </th>
						<th data-options="field:'certId', width: '20%'" class="text-align:center">证件号码</th>
						<th data-options="field:'genderName', width: '10%'" class="text-align:center">性别</th>
						<th data-options="field:'nation', width: '10%'">民族</th>
						<th data-options="field:'dept', width: '10%'">部门</th>
					</tr>
				</thead>
			</table> 
		</div>
	</div> 
<div id="adjScore"></div>  

<script type="text/javascript">
	//url : SYS.contextPath + '/pages/scoreAct/adjScoretouser.jsp?custid=' + rows[0].custid,
	$(function() {
		$('#tb-member').datagrid({
			toolbar: [{
				text: '新增',
				iconCls: 'icon-add',
				handler: function() {
					top.SYS.modalDialog({
						title : '新增操作员',
						url : SYS.contextPath + '/pages/sys/operatorInput.jsp',
						resizable : true,
						width: '70%',
						height: '65%',
						ctlDom: $('#tb-member')
					});
				}
			},
			{
				text: '修改',
				iconCls: 'icon-edit',
				handler: function() {
					var rows = $('#tb-member').datagrid('getChecked');
					if (rows.length != 1) {
						$.messager.alert('提示', '请选择一行进行修改！', 'warning');
					} else {
						top.SYS.modalDialog({
							title : '编辑操作员信息',
							url : SYS.contextPath + '/pages/sys/operatorInput.jsp?id=' + rows[0].id,
							resizable : true,
							width: '70%',
							height: '65%',
							ctlDom: $('#tb-member')
						});
					}
				}
			},
			{
				text: '删除',
				iconCls: 'icon-remove',
				handler: function() {
					var rows = $('#tb-member').datagrid('getChecked'),
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
							       url: SYS.contextPath + "/sys/operator/del.action",
							       data: data,
							       dataType: "json",
							       traditional: true,
							       success: function(data, textStatus, jqXHR) {
										if (data.success == "0") {
											$.messager.show({title: "提示", msg: "操作成功" });
											$('#tb-member').datagrid('reload');
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