<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>积分换份额查询</title>
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
							<th>确认时间:</th>
							<td>
								<input class="Wdate " id="confBeginDate" name="confBeginDate" />&nbsp;至&nbsp;
								<input class="Wdate " id="confEndDate" name="confEndDate" />
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
							<th>处理状态:</th>
							<td>
								<select name="status" style="width: 200px;" class="easyui-combobox" data-options="dictTypeId: 'SC_SC_ACT_APP_STA'">
								</select>
							</td>
							<th>客户号：</th>
							<td>
								<input class="asyui-validatebox validatebox-text" id="custId" name="custId" />
							</td>
						</tr>
						<tr>
							<td colspan="4" style="text-align: center;">
								<a class="easyui-linkbutton l-btn" id="a_search" onclick="$('#tb-convert').datagrid('load', SYS.serializeObject($('#searchForm')));"> 查 找 </a>
								<a class="easyui-linkbutton l-btn" onclick="$('#searchForm').form('clear');"> 清 空 </a>
							</td>
						</tr>
					</table>
				</form>
			</div>
			
			<!-- 积分转换列表 -->
		<div data-options="region:'center',split:false">
			<table id="tb-convert"  data-options="
				url: SYS.contextPath + '/scConvert/queryScConvert.action',
				view: SYS.datagrid.view,
				emptyMsg: '没有数据',
				fit: true,
				border:false,
				singleSelect:true,
				autoRowHeight: true,
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
						<th data-options="field: 'certName'">证件类型</th>
						<th data-options="field: 'CUST_ID'">客户号</th>
						<th data-options="field: 'CERT_CODE'">证件号码</th>
						<th data-options="field: 'FUND_CODE'">兑换产品</th>
						<th data-options="field: 'SHARES'">兑换份额</th>
						<th data-options="field: 'SC_VAL'">兑换积分数量</th>
						<th data-options="field:'APP_DATE', width:'10%'">申请时间</th>
						<th data-options="field: 'CONF_DATE'">确认时间</th>
						<th data-options="field: 'statusName'">当前状态</th>
						<th data-options="field: '',formatter:scConvertFormatter">申请明细</th>
					</tr>
				</thead>
			</table>
		</div>
	</div> 

<script type="text/javascript">
	//申请明细formatter
	function scConvertFormatter(value,row,index) {
		return SYS.strFormat('<a href ="javascript:void(0);" onclick="showScConvertDtl(\'{0}\', \'{1}\');" >查看明细<a/>', row.CUST_ID, row.APP_DATE);
	}
	//申请明细展示
	function showScConvertDtl(custId, appDate) {
		top.SYS.modalDialog({
			title : '申请明细数据',
			url : SYS.contextPath + '/pages/score/scoreConvertDtlList.jsp?custId=' + custId + '&appDate=' + appDate,
			resizable : true,
			width: '80%',
			height: '60%'
		});
	}

	$(function() {
		$('#appBeginDate').my97({
			dateFmt: 'yyyy-MM-dd',
			maxDate:'#F{$dp.$D(\'appEndDate\')}'
		});
		
		$('#appEndDate').my97({
			dateFmt: 'yyyy-MM-dd',
			minDate:'#F{$dp.$D(\'appBeginDate\')}'
		});
		
		$('#confBeginDate').my97({
			dateFmt: 'yyyy-MM-dd',
			maxDate:'#F{$dp.$D(\'confEndDate\')}'
		});
		
		$('#confEndDate').my97({
			dateFmt: 'yyyy-MM-dd',
			minDate:'#F{$dp.$D(\'confBeginDate\')}'
		});
	});
	
	
	//当前页面变量定义
	$(function() {
		$('#tb-convert').datagrid({    
			    pagination: true,
					toolbar: [
					{
						text: '申请数据导出',
						iconCls: 'ext-icon-cust_export',
						handler: function() {
							top.SYS.modalDialog({
								title : '申请数据导出',
								url : SYS.contextPath + '/pages/score/outputExcle.jsp',
								resizable : true,
								width: '50%',
								height: '30%'
							});
						}
					},
					{
						text: '处理数据导入',
						iconCls: 'ext-icon-cust_import',
						handler: function() {
							top.SYS.modalDialog({
								title : '确认数据导入',
								url : SYS.contextPath + '/pages/score/includeExcle.jsp',
								resizable : true,
								width: '50%',
								height: '30%',
								ctlDom: $('#tb-convert')
							});
						}
					}]
		});
	});
</script>
</body>
</html>