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
		<div data-options="region:'north',title:'查询', border:false" style="height: 100px; background: #F4F4F4;overflow: hidden;">
			<form id="searchForm" class="form" onkeydown="if (event.keyCode == 13) { $('#a_search').click();}">
				<table class="table" style="width: 100%;">
					<tbody>
						<tr>
							<th>姓名</th>
							<td>
								<input class="easyui-validatebox" name="member.name" />
							</td>
							<th>身份证号</th>
							<td><input class="easyui-validatebox" name="member.certId" /></td>
						</tr>
						<tr>
							<th>电话</th>
							<td><input class="easyui-validatebox" name="member.tel" /></td>
							<th>状态</th>
							<td>
								<select id="status" name="member.status" class="easyui-combobox" data-options="dictTypeId: 'STATUS',required:false,defaultIndex: 0">
								</select>  
							</td>
						</tr>
						<tr>
							<th colspan="4" style="text-align: center;">
								<a class="easyui-linkbutton l-btn" id="a_search" onclick="$('#tb-member').datagrid('load', SYS.serializeObject($('#searchForm')));"> 查 找 </a>
								<a class="easyui-linkbutton l-btn" onclick="$('#searchForm').form('clear');"> 清 空 </a>
							</th>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- 成员信息列表-->
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
						<th data-options="field:'id', width: '5%'" class="text-align:center">成员编号</th>
						<th data-options="field:'name', width: '5%'" class="text-align:center">成员姓名 </th>
						<th data-options="field:'certId', width: '5%'" class="text-align:center">证件号码</th>
						<th data-options="field:'genderName', width: '3%'" class="text-align:center">性别</th>
						<th data-options="field:'nationName', width: '5%'">民族</th>
						<th data-options="field:'deptName', width: '5%'">部门</th>
						<th data-options="field:'office', width: '5%'">职务</th>
						<th data-options="field:'degree', width: '5%'">学历</th>
						<th data-options="field:'marriageName', width: '5%'">婚姻</th>
						<th data-options="field:'polityName', width: '5%'">政治面貌</th>
						<th data-options="field:'tel', width: '8%'">联系电话</th>
						<th data-options="field:'address', width: '10%'">地址</th>
						<th data-options="field:'companyAddr', width: '10%'">公司地址</th>
						<th data-options="field:'email', width: '10%'">邮箱地址</th>
						<th data-options="field:'statusName', width: '5%'">状态</th>
						<th data-options="field:'originalPlace', width: '10%'">祖籍</th>
						<th data-options="field:'nativePlace', width: '10%'">籍贯</th>
						<th data-options="field:'health_status', width: '10%'">健康状况</th>
						<th data-options="field:'birthDate', width: '10%'">出生日期</th>
						<th data-options="field:'hobby', width: '10%'">兴趣爱好</th>
						<th data-options="field:'advantage', width: '10%'">特长</th>
						<th data-options="field:'joinDate', width: '10%'">加入时间</th>
						<th data-options="field:'resumePath', width: '10%'">简历</th>
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
				id: 'detail',
				text: '查看',
				iconCls: 'icon-search',
				handler: function() {
					var rows = $('#tb-member').datagrid('getChecked');
					if (rows.length != 1) {
						$.messager.alert('提示', '请选择一行进行查看！', 'warning');
					} else {
						top.SYS.modalDialog({
							title : '查看成员',
							url : SYS.contextPath + '/pages/busin/member/memberInput.jsp?op=detail&id=' + rows[0].id ,
							resizable : true,
							width: '80%',
							height: '80%',
							ctlDom: $('#tb-member')
						});
					}
				}
			},{
				id: 'addMember',
				text: '新增',
				iconCls: 'icon-add',
				handler: function() {
					top.SYS.modalDialog({
						title : '新增成员',
						url : SYS.contextPath + '/pages/busin/member/memberInput.jsp?op=add&op=add',
						resizable : true,
						width: '80%',
						height: '80%',
						ctlDom: $('#tb-member')
					});
				}
			},
			{
				id: 'updateMember',
				text: '修改',
				iconCls: 'icon-edit',
				handler: function() {
					var rows = $('#tb-member').datagrid('getChecked');
					if (rows.length != 1) {
						$.messager.alert('提示', '请选择一行进行修改！', 'warning');
					} else {
						top.SYS.modalDialog({
							title : '编辑成员信息',
							url : SYS.contextPath + '/pages/busin/member/memberInput.jsp?op=edit&id=' + rows[0].id,
							resizable : true,
							width: '80%',
							height: '80%',
							ctlDom: $('#tb-member')
						});
					}
				}
			},
			{
				id: 'deleteMember',
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
								console.log(data);
							    $.ajax({
							       url: SYS.contextPath + "/member/del.action",
							       data: data,
							       dataType: "json",
							       traditional: true,
							       success: function(data, textStatus, jqXHR) {
										if (data.success == "0") {
											$.messager.show({title: "提示", msg: "操作成功" });
											//$('#tb-member').datagrid('reload');
											$('#tb-member').datagrid('reload', SYS.serializeObject($('#searchForm')));
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