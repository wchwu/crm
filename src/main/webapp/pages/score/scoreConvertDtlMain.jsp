<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>积分换份额申请明细查询</title>
	<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true, border:false">  
			<div data-options="region:'north',title:'查询', border:false" style="height: 140px; background: #F4F4F4;overflow: hidden;">
				<form id="searchForm" class="form"  onkeydown="if (event.keyCode == 13) { $('#a_search').click();}">
					<table class="table" style="width: 100%;">
						<tr style="width: 100%;">
							<th>申请时间:</th>
							<td>
								<input class="Wdate " id="appBeginDate" name="appBeginDate" />&nbsp;至&nbsp;
								<input class="Wdate " id="appEndDate" name="appEndDate" />
							</td>
							<th>客户号：</th>
							<td>
								<input class="asyui-validatebox validatebox-text" id="custId" name="custId" />
							</td>
						</tr>
						<tr>
							<th>客户姓名:</th>
							<td>
								<input class="asyui-validatebox validatebox-text" id="custName" name="custName" />
							</td>
							<th>证件号码:</th>
							<td>
								<input class="asyui-validatebox validatebox-text" id="certNo" name="certNo" />
							</td>
						</tr>
						<tr>
							<td colspan="4" style="text-align: center;">
								<a class="easyui-linkbutton l-btn" id="a_search" onclick="$('#tb-convert-dtl').datagrid('load', SYS.serializeObject($('#searchForm')));"> 查 找 </a>
								<a class="easyui-linkbutton l-btn" onclick="$('#searchForm').form('clear');"> 清 空 </a>
							</td>
						</tr>
					</table>
				</form>
			</div>
			
			<!-- 积分转换列表 -->
		<div data-options="region:'center',split:false">
			<table id="tb-convert-dtl" class="easyui-datagrid"  data-options="
				url: SYS.contextPath + '/scConvert/queryScConvertDtl.action',
				view: SYS.datagrid.view,
				emptyMsg: '没有数据',
				fit: true,
				border:false,
				singleSelect:true,
				fitColumns: true,
				rownumbers: true,
				pagination: true
			">
				<thead data-options="frozen:true">
					<tr>
						<th data-options="field:'CUST_NAME', width:'10%'">客户姓名</th>
					</tr>
				</thead>
				<thead>
					<tr>
						<th data-options="field: 'certName', width:10">证件类型</th>
						<th data-options="field: 'CUST_ID', width:10">客户号</th>
						<th data-options="field: 'CERT_CODE', width:10">证件号码</th>
						<th data-options="field: 'FUND_CODE', width:10">兑换产品</th>
						<th data-options="field: 'SC_VAL', width:10">兑换积分数量</th>
						<th data-options="field:'DATE_ID', width:'10%'">申请时间</th>
					</tr>
				</thead>
			</table>
		</div>
	</div> 

<script type="text/javascript">
	$(function() {
		$('#appBeginDate').my97({
			dateFmt: 'yyyy-MM-dd',
			maxDate:'#F{$dp.$D(\'appEndDate\')}'
		});
		
		$('#appEndDate').my97({
			dateFmt: 'yyyy-MM-dd',
			minDate:'#F{$dp.$D(\'appBeginDate\')}'
		});
	});
</script>
</body>
</html>