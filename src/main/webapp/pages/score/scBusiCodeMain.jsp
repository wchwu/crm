<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>积分业务代码管理</title>
	<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true, border:false">  
			<div data-options="region:'north',title:'查询', border:false" style="height: 85px; background: #F4F4F4;overflow: hidden;">
				<form id="searchForm" class="form">
					<table class="table" style="width: 100%;">
						<tr style="width: 100%;">
							<th>业务代码:</th>
							<td><input class="easyui-validatebox " id="busicode" name="busicode" /></td>
							<th>业务名称:</th>
							<td><input class="easyui-validatebox " id="businame" name="businame" /></td>
							<th>业务类型:</th>
							<td>
							<select id="object" name="busitype" class="easyui-combobox" data-options="dictTypeId: 'SC_BUSI_TYPE'">
									<option value="">--请选择 --</option>
								</select>
							
							</td>
							<td ><a class="easyui-linkbutton l-btn" onclick="searchFunc();">查找</a><a class="easyui-linkbutton l-btn" onclick="clearSearch();">清空</a></td>
						</tr>
					</table>
				</form>
			</div>
			
			<!-- 积分活动列表 -->
			<div data-options="region:'center',split:false">
				<table id="tb-score"></table>  
			</div>
	</div> 

<script type="text/javascript">
	
	//当前页面变量定义
	var dg_score,
		dg_score_entry;
	$(function() {
		dg_score = $('#tb-score').datagrid({    
			    url: SYS.contextPath + '/score/queryBusiCode.action',  
			    iconCls: 'icon-save',
			    view: SYS.datagrid.view,
			    emptyMsg: '没有数据',
			    fit: true,
			    border:false,
			    autoRowHeight: true,
			    fitColumns: true,
			    rownumbers: true,
			    singleSelect:true,
			    toolbar: '#toolbar_dict',
			    idField: 'dictTypeId',
			    columns:[[    
					{field:'id', title:'主键', width:'5%',hidden:true},
			        {field:'busicode', title:'业务代码', width:'20%'}, 
			        {field:'businame', title:'业务名称', width:'20%'},
			        {field:'busitypename', title:'业务类型', width:'20%'},
			        {field:'scinout', title:'份额调整方向', width:'10%',formatter: function(value,row){ return value == 1 ?'增加':'减少'; }}, 
			        {field:'memo', title:'备注', width:'25%'}, 
			    ]],
			    pagination: true,
					toolbar: [{
						text: '增加',
						iconCls: 'icon-add',
						handler: function() {
							top.SYS.modalDialog({
								title : '积分业务代码增加',
								url : SYS.contextPath + '/pages/score/addBusiCode.jsp',
								resizable : true,
								width: '55%',
								height: '40%',
								ctlDom: $('#tb-score')
							});
						}
					},
					{
						text: '修改',
						iconCls: 'icon-edit',
						handler: function() {
							var rows = $('#tb-score').datagrid('getChecked');
							if (rows.length != 1) {
								$.messager.alert('提示', '请选择一行进行修改！', 'warning');
							} else {
								top.SYS.modalDialog({
									title : '编辑积分业务代码信息',
									url : SYS.contextPath + '/pages/score/editBusiCode.jsp?id='+rows[0].id,
									resizable : true,
									width: '65%',
									height: '60%',
									ctlDom: $('#tb-score')
								});
							}
						}
					},
					{
						text: '删除',
						iconCls: 'icon-remove',
						handler: function() {
							var rows = $('#tb-score').datagrid('getChecked');
							if (rows.length < 1) {
								$.messager.alert('提示', '请选择要删除的数据！！', 'warning');
							} else {
								$.messager.confirm('确认', '你确定要删除[' + rows.length +  ']条数据吗？', function(r) {
									if (r) {
									    $.ajax({
									       url: SYS.contextPath + "/score/delBusiCode.action",
									       data: { id : rows[0].id },
									       dataType: "json",
									       traditional: true,
									       success: function(data, textStatus, jqXHR) {
												if (data.success == "0") {
													$.messager.show({title: "提示", msg: "操作成功" });
													$('#tb-score').datagrid('reload');
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
	
	function searchFunc() {
		dg_score.datagrid('load',SYS.serializeObject($('#searchForm')));
	}
	
	function clearSearch() {
		$(".easyui-validatebox").val("");
		$(".easyui-combobox").combobox("setValue", "");
	}
	


</script>
</body>
</html>