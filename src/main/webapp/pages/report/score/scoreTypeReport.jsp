<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>积分类型汇总报表</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',title:'查询', border:false" style="height: 120px; background: #F4F4F4;overflow: hidden;">
			<form id="searchForm" method="post" class="form" onkeydown="if (event.keyCode == 13) { $('#a_search').click();}">
				<table class="table" style="width: 100%;">
					<tbody>
						<tr>
							<th>开始时间</th>
							<td>
								<input id="iptBeginDate" />
								<input name="beginDate" type="hidden" id="iptRealBeginDate" />
							</td>
							<th>结束时间</th>
							<td>
								<input id="iptEndDate" />
								<input name="endDate" type="hidden" id="iptRealEndDate" />
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
			<table id="tb-stype-result" data-options="
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
						<th data-options="field: 'SCORETYPE',width:100">积分类型</th>
						<th data-options="field: 'SCORE',width:100">生成积分</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

	<script type="text/javascript">
		function subForm() {
			 if ($('form').form('validate')) {
				 $('#tb-stype-result').datagrid('load', SYS.serializeObject($('#searchForm')));
			 }
		}
		
		function exportExcel() {
			 if ($('form').form('validate')) {
				 $('#searchForm').attr('action', SYS.contextPath + '/report/score/exportScoreTypeReport.action');
				 $('#searchForm').submit();
			 }
		}
	
		$(function() {
			var curDateStr1 = SYS.dateToStr('yyyy-MM-dd');
			var curDateStr2 = SYS.dateToStr('yyyyMMdd');
			
			//默认当天
			$('#iptBeginDate').val(curDateStr1);
			$('#iptEndDate').val(curDateStr1);
			$('#iptRealBeginDate').val(curDateStr2);
			$('#iptRealEndDate').val(curDateStr2);
			
			$('#iptBeginDate').my97({
				required:true,
				dateFmt: 'yyyy-MM-dd',
				realDateFmt: 'yyyyMMdd',
				vel: 'iptRealBeginDate',
				maxDate:'#F{$dp.$D(\'iptEndDate\')}'
			});
			
			$('#iptEndDate').my97({
				required:true,
				vel: 'iptRealEndDate',
				dateFmt: 'yyyy-MM-dd',
				realDateFmt: 'yyyyMMdd',
				minDate:'#F{$dp.$D(\'iptBeginDate\')}'
			});
			
			$('#tb-stype-result').datagrid({
				url: SYS.contextPath + '/report/score/scoreTypeReport.action',
				queryParams: {
					beginDate: curDateStr2,
					endDate: curDateStr2	
				}
			});
		});
		
		
	</script>
</body>
</html>