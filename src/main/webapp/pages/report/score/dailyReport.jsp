<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>每日汇总报表</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',title:'查询', border:false" style="height: 90px; background: #F4F4F4;overflow: hidden;">
			<form id="searchForm" method="post" class="form" onkeydown="if (event.keyCode == 13) { $('#a_search').click();}">
				<table class="table" style="width: 100%;">
					<tbody>
						<tr>
							<th>查询时间</th>
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
			<div id="divDailyLayout" class="easyui-layout" data-options="fit:true">   
			    <div data-options="region:'west'" style="width:300px;">
			    	  <table class="table" style="width: 100%;">
						<tr>
							<th>当日发放积分</th>
							<td><span id="span_SUM_SC_ADD"></span></td>
						</tr>
						<tr>
							<th>累计发放积分</th>
							<td><span id="span_SUM_SC_ADD_ALL"></span></td>
						</tr>
						<tr>
							<th>当日消耗积分</th>
							<td><span id="span_SUM_SC_OUT"></span></td>
						</tr>
						<tr>
							<th>累计消耗积分</th>
							<td><span id="span_SUM_SC_OUT_ALL"></span></td>
						</tr>
						<tr>
							<th>当日净增加积分</th>
							<td><span id="span_SUM_NET_INCRE"></span></td>
						</tr>
						<tr>
							<th>累计净增加积分</th>
							<td><span id="span_SUM_NET_INCRE_ALL"></span></td>
						</tr>
						<tr>
							<th>当日调整积分（新增）</th>
							<td><span id="span_SUM_SC_ADJ_ADD"></span></td>
						</tr>
						<tr>
							<th>当日调整积分（减少）</th>
							<td><span id="span_SUM_SC_ADJ_OUT"></span></td>
						</tr>
						<tr>
							<th>当日合计调整</th>
							<td><span id="span_SUM_ADJ_ALL"></span></td>
						</tr>
					</table>
			    </div>  
			    <div data-options="region:'center'" style="padding:5px;background:#eee;">
			    	<table id="tb-stype-result" data-options="
						view: SYS.datagrid.view,
						emptyMsg: '没有数据',
						width: '60%',
						fit: true,
						border:false,
						autoRowHeight: true,
						fitColumns: true,
						rownumbers: true
						">
							<thead>
								<tr>
									<th data-options="field: 'ACT_NAME',width:100">积分类型</th>
									<th data-options="field: 'SUM_SC',width:100">当日新增</th>
									<th data-options="field: 'SUM_SC_ALL',width:100">累计新增</th>
									<th data-options="field: 'SUM_CT',width:100">当日户数</th>
									<th data-options="field: 'SUM_CT_ALL',width:100">累计户数</th>
								</tr>
							</thead>
					</table>
			    </div>   
			</div>  
		</div>
	</div>

	<script type="text/javascript">
	//提交表单
	function subForm() {
		 if ($('form').form('validate')) {
			 loadLeftData({endDate: $('#iptRealEndDate').val()});
			 $('#tb-stype-result').datagrid('load', SYS.serializeObject($('#searchForm')));
		 }
	}
	
	//导出Excel
	function exportExcel() {
		 if ($('form').form('validate')) {
			 $('#searchForm').attr('action', SYS.contextPath + '/report/score/exportDailyReport.action');
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
		
		
		//-------------------------------------------加载数据
		loadLeftData({endDate: curDateStr2});
		
		
		//右侧数据查询
		$('#tb-stype-result').datagrid({
			url: SYS.contextPath + '/report/score/dailyReportScTypeData.action',
			queryParams: {
				endDate: curDateStr2
			}
		});
	});
	
	function loadLeftData(queryParam) {
		top.$.messager.progress({
			text : '数据加载中....'
		});
		
		$.post(SYS.contextPath + '/report/score/dailyReportScDtlData.action', queryParam, function(result) {
			if (result) {
				$('#span_SUM_SC_ADD').text(result.SUM_SC_ADD || '');
				$('#span_SUM_SC_ADD_ALL').text(result.SUM_SC_ADD_ALL || '');
				$('#span_SUM_SC_OUT').text(result.SUM_SC_OUT || '');
				$('#span_SUM_SC_OUT_ALL').text(result.SUM_SC_OUT_ALL || '');
				$('#span_SUM_NET_INCRE').text(result.SUM_NET_INCRE || '');
				$('#span_SUM_NET_INCRE_ALL').text(result.SUM_NET_INCRE_ALL || '');
				$('#span_SUM_SC_ADJ_ADD').text(result.SUM_SC_ADJ_ADD || '');
				$('#span_SUM_SC_ADJ_OUT').text(result.SUM_SC_ADJ_OUT || '');
				$('#span_SUM_ADJ_ALL').text(result.SUM_ADJ_ALL || '');
			}
			top.$.messager.progress('close');
		}, 'json');
	}
		
	</script>
</body>
</html>