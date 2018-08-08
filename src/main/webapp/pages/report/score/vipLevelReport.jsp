<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员等级汇总报表</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',title:'查询', border:false" style="height: 120px; background: #F4F4F4;overflow: hidden;">
			<form id="searchForm" method="post" class="form" onkeydown="if (event.keyCode == 13) { $('#a_search').click();}">
				<table class="table" style="width: 100%;">
					<tbody>
						<tr>
							<th>截止时间</th>
							<td>
								<input id="iptEndDate" />
								<input name="endDate" type="hidden" id="iptRealEndDate" />
							</td>
							<th></th>
							<td>
							</td>
						</tr>
						<tr>
							<th colspan="4" style="text-align: center;">
								<a class="easyui-linkbutton l-btn" id="a_search" onclick="subForm();"> 查 找 </a>
								<a class="easyui-linkbutton l-btn" onclick="exportExcel()"> 导出 </a>
							</th>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<!-- 查询结果展示 -->
		<div data-options="region:'center',split:false">
			<table id="tb-vlevle-result" data-options="
				view: SYS.datagrid.view,
				emptyMsg: '没有数据',
				fit: true,
				border:false,
				autoRowHeight: true,
				fitColumns: true,
				rownumbers: true
			">
				<thead>
					<tr>
						<th data-options="field: 'LEV_NAME',width:100">会员等级</th>
						<th data-options="field: 'SUM_CT',width:100">客户数量</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<script type="text/javascript">
	//提交表单
	function subForm() {
		 if ($('form').form('validate')) {
			 $('#tb-vlevle-result').datagrid('load', SYS.serializeObject($('#searchForm')));
		 }
	}
	
	//导出Excel
	function exportExcel() {
		 if ($('form').form('validate')) {
			 $('#searchForm').attr('action', SYS.contextPath + '/report/score/exportVipLevelReport.action');
			 $('#searchForm').submit();
		 }
	}

	//初始话 查询时间、以及datagrid
	$(function() {
		var curDateStr1 = SYS.dateToStr('yyyy-MM-dd');
		var curDateStr2 = SYS.dateToStr('yyyyMMdd');
		
		//默认当天
		$('#iptEndDate').val(curDateStr1);
		$('#iptRealEndDate').val(curDateStr2);
		
		$('#iptEndDate').my97({
			required:true,
			dateFmt: 'yyyy-MM-dd',
			realDateFmt: 'yyyyMMdd',
			vel: 'iptRealEndDate'
		});
		
		$('#tb-vlevle-result').datagrid({
			url: SYS.contextPath + '/report/score/vipLevelReport.action',
			queryParams: {
				endDate: curDateStr2
			}
		});
	});
		
		
	</script>
</body>
</html>