<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	String op = request.getParameter("op");
	if (id == null) {
		id = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>规章制度录入</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<form method="post" class="form">
		<input type="hidden" id="upt_ruleId" value="<%=id %>" name="id"/>
		<input type="hidden" id="op" value="<%=op %>" name="op"/>
		<input type="hidden" id="id" name="id" value="<%=id %>"/>
		
		<fieldset>
			<legend>制度信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th width="20%">制度类型</th>
					<td width="80%">
						<select id="ruleType" name="ruleType" class="easyui-combobox" data-options="dictTypeId: 'RULE_TYPE',required:false">
								</select>
					</td>
				</tr>
				<tr>
					<th>制度名称</th>
					<td>
						<input class="easyui-validatebox" type="text" name="ruleName" data-options="required:true" style="width: 700px;"/>
					</td>
				</tr>
				<tr>
					<th>url路径</th>
					<td>
						<input class="easyui-validatebox" type="text" name="ruleUrl" style="width: 700px;"/>
					</td>
				</tr>
			</table>
		</fieldset>
		
		<fieldset>
			<legend>内容</legend>
			<textarea name="content" id="content" class="easyui-validatebox" data-options="multiline:true,width:'98%', height:400"></textarea>
		</fieldset>
		<hr/>
		<fieldset>
			<legend>附件<input type="file" name="upload" id="upload" style="display: none;"> </legend>
			<table class="table" style="width: 100%;" id="tab_file" class="easyui-datagrid" 
				data-options="
				    iconCls: 'icon-save',
				    fit: false,
				    autoRowHeight: false,
				    border:false,
				    fitColumns: true,
				    rownumbers:true,
				    singleSelect: true
				">
				<thead>
					<tr>
						<th data-options="field:'id',hidden: true"></th>
						<th data-options="field:'ruleId',hidden: true"></th>
						<!-- 
						<th data-options="field:'fileType', width:'30%', editor: {type:'combobox', options:{dictTypeId: 'FILE_TYPE',required:true, missingMessage: '不能为空'}}">文件类型</th>
						 -->
						<th data-options="field:'fileName', width:'76%'">文件名称</th>
						<th	data-options="field:'fileSize', width:'20%'">文件大小(KB)</th>
					</tr>
				</thead>
			</table>
		</fieldset>
		<fieldset id="fs_submit">
			<legend></legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th colspan="4" style="text-align: center;">
						<a href="#" class="easyui-linkbutton" onclick="javascript:submitForm();">保存</a>
						<a href="#" class="easyui-linkbutton" onclick="javascript:closeDialog();">关闭</a>  
					</th>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
<jsp:include page="/pages/common/commonJs.jsp"></jsp:include>
<script type="text/javascript" src="${ctx}/js/util/util.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}/js/busin/rule/ruleInput.js?version=${version}"></script>
<script type="text/javascript" src="../../../js/fileupload/jquery.ui.widget.js?version=${version}"></script>
<script type="text/javascript" src="../../../js/fileupload/jquery.iframe-transport.js?version=${version}"></script>
<script type="text/javascript" src="../../../js/fileupload/jquery.fileupload.js?version=${version}"></script>

<link rel="stylesheet" href="../../../js/kindeditor-4.1.10/themes/default/default.css" />
<script src="../../../js/kindeditor-4.1.10/kindeditor.js"></script>
<script src="../../../js/kindeditor-4.1.10/kindeditor-all.js"></script>
<script src="../../../js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script charset="utf-8" src="../../../js/kindeditor-4.1.10/kindeditor-min.js"></script>
<script charset="utf-8" src="../../../js/kindeditor-4.1.10/lang/zh_CN.js"></script>


</html>