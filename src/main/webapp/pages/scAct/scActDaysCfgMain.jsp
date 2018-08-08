<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String actCode = request.getParameter("actCode");
	if (actCode == null) {
		actCode = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>开户活动积分列表</title>
	<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
<div class="easyui-layout" data-options="fit:true, border:false">  
	<div data-options="region:'center',border:false, width: '55%'" style="overflow: hidden;">
		<div class="easyui-layout" data-options="fit:true">
			
			<!-- 积分活动时间列表 -->
			<div data-options="region:'center',split:false">
				<table id="tb-datelist"  data-options="
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
							<th data-options="field:'',checkbox:true"></th>
							<th data-options="field: 'startDate',width:100">开始时间</th>
							<th data-options="field: 'endDate',width:100">结束时间</th>
							<th data-options="field: 'scVal',width:100">积分</th>
							<th data-options="field: 'memo'">备注</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>  
</div> 


<script type="text/javascript">
	//当前页面变量定义
	$(function() {
		$('#tb-datelist').datagrid({    
			    url: SYS.contextPath + '/scAct/queryScActDaysCfg.action?actCode=<%=actCode %>',
				toolbar: [{
					text: '新增',
					iconCls: 'icon-add',
					handler: function() {
						top.SYS.modalDialog({
							title : '积分时间新增',
							url : SYS.contextPath + '/pages/scAct/scActDaysCfgInput.jsp?actCode=<%=actCode %>',
							resizable : true,
							width: '500',
							height: '200',
							ctlDom: $('#tb-datelist')
						});
					}
				},
				{
					text: '修改',
					iconCls: 'icon-edit',
					handler: function() {
						var rows = $('#tb-datelist').datagrid('getChecked');
						if (rows.length != 1) {
							$.messager.alert('提示', '请选择一行进行修改！', 'warning');
						} else {
							top.SYS.modalDialog({
								title : '积分时间更新',
								url : SYS.contextPath + '/pages/scAct/scActDaysCfgInput.jsp?id=' + rows[0].id,
								resizable : true,
								width: '500',
								height: '200',
								ctlDom: $('#tb-datelist')
							});
						}
					}
				},
				{
					text: '删除',
					iconCls: 'icon-remove',
					handler: function() {

						var rows = $('#tb-datelist').datagrid('getChecked'),
							row_len = rows.length;
						
						if (row_len < 1) {
							top.$.messager.alert('提示', '请选择要删除的数据！！', 'warning');
						} else {
							top.$.messager.confirm('确认', '你确定要删除[' + row_len +  ']条数据吗？', function(r) {
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
								       url: SYS.contextPath + '/scAct/delActDaysCfg.action',
								       data: data,
								       dataType: "json",
								       traditional: true,
								       success: function(data, textStatus, jqXHR) {
											if (data.success == "0") {
												top.$.messager.show({title: "提示", msg: "操作成功" });
												$('#tb-datelist').datagrid('reload');
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