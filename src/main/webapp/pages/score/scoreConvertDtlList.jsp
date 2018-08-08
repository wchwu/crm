<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String custId = request.getParameter("custId");
	String appDate = request.getParameter("appDate");
	
	if (custId == null) {
		custId = "";
	}
	if (appDate == null) {
		appDate = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>积分换份额申请明细查询</title>
	<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<!-- 积分转换明细列表 -->
	<table id="tb-convert-dtl" class="easyui-datagrid"  data-options="
		url: SYS.contextPath + '/scConvert/queryScConvertDtl.action',
		view: SYS.datagrid.view,
		emptyMsg: '没有数据',
		queryParams: {custId: '<%=custId %>', appDate: '<%=appDate %>'},
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
</body>
</html>