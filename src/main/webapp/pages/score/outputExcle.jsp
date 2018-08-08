<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>导出积分转份额申请数据</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<form id="searchForm" method="post" class="form">
		<fieldset>
			<legend>导出条件选择</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>起始时间</th>
					<td>
						<input id="beginDate" class="Wdate" name="beginDate" />
					</td> 
					<th>结束时间</th>
					<td>
						<input id="endDate" class="Wdate" name="endDate" />
					</td> 
					<th  style="text-align: center;">
						<a href="#" class="easyui-linkbutton" onclick="javascript:exportExcel();">导出</a>
					</th>
				</tr>
			</table>
		</fieldset>
	</form>
	<script type="text/javascript">
	$(function() {
		$('#beginDate').my97({
			required:true,
			dateFmt: 'yyyy-MM-dd',
			maxDate:'#F{$dp.$D(\'endDate\')}'
		});
		
		$('#endDate').my97({
			required:true,
			dateFmt: 'yyyy-MM-dd',
			minDate:'#F{$dp.$D(\'beginDate\')}'
		});
	});
	
	//导出Excel
	function exportExcel() {
		 if ($('#searchForm').form('validate')) {
			 $('#searchForm').attr('action', SYS.contextPath + '/scConvert/exportAppExcel.action');
			 $('#searchForm').submit();
		 }
	}
	</script>	
</body>
</html>