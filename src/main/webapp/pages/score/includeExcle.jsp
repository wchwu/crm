<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>确认数据导入</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<form id="data_form" method="post" target="post_frame" class="form" enctype="multipart/form-data" action="<%=basePath %>/scConvert/importConfExcel.action">
		<fieldset>
			<legend>确认数据导入</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>请选择导入excle</th>
					<td><input name="includeFile" id="includeFile" class="easyui-filebox" data-options="required:true,buttonText: '选择文件',missingMessage:'请选择上传文件'" /></td>
					<td>
						<a href="#" class="easyui-linkbutton" onclick="javascript:submitForm();">导入</a>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	<iframe name='post_frame' id="post_frame" style="display:none;"></iframe> 
	
	<script type="text/javascript">
	function submitForm() {
		if($('#data_form').form('validate')) {
			$("#data_form").submit();
			top.$.messager.progress({ text : '数据导入处理中....' });
		}
	}
	
	//回调方法
	function callBack(status, msg) {
		top.$.messager.progress('close');
		
		if (status == '0') {
			top.$.messager.show({title: "提示", msg: "操作成功" });	
			closeDialog(true);
		} else {
			var opts = {
					title : '错误',
					content : msg,
					resizable : true,
					width : '70%',
					height : '60%'
				};
			top.SYS.modalDialog(opts);
		}
	}
	
	//关闭本身窗口
	function closeDialog(isRefresh) {
		var $dialog = parent.$('#dialog_' + window.name);
		if ($dialog) {
			var opts = $dialog.dialog('options');
			if (isRefresh && opts.ctlDom) {
				opts.ctlDom.datagrid('reload');
			}
			$dialog.dialog('close');
		} else {
			window.close();
		}
	}
	</script>	
</body>
</html>