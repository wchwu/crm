<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	if (id == null) {
		id = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员等级操作</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<form method="post" class="form">
		<input id="id" name="scBusiCode.id" value="<%=id %>" type="hidden" />
		<fieldset>
			<legend>积分业务代码录入</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>业务类型</th>
					<td>
					<select name="scBusiCode.busitype" id="busitype"  style="width: 200px;" class="easyui-combobox" data-options="required:true,dictTypeId: 'SC_BUSI_TYPE',panelHeight: 120">
						<option value="">--请选择 --</option>
					</select>
					<th>业务代码</th>
					<td><input name="scBusiCode.busicode" id="busicode" class="easyui-validatebox" data-options="required:true,missingMessage:'该输入项为必输项'" /></td>
				</tr>
				<tr>
					<th>业务名称</th>
					<td><input name="scBusiCode.businame" id="businame" class="easyui-validatebox" data-options="required:true,missingMessage:'该输入项为必输项'" /></td>
					<th>份额调整方向</th>
					<td>
						<select name="scBusiCode.scinout" id="scinout" data-options="required:true,panelHeight: 80" style="width: 200px;" class="easyui-combobox">
							<option value="1">增加</option>
							<option value="-1">减少</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3"><input name="scBusiCode.memo" id="memo" class="easyui-validatebox" /></td>
					
				</tr>
				<tr>
					<th colspan="4" style="text-align: center;">
						<a href="#" class="easyui-linkbutton" onclick="javascript:submitForm();">保存</a>
						<a href="#" class="easyui-linkbutton" onclick="javascript:closeDialog();">关闭</a>  
					</th>
				</tr>
			</table>
		</fieldset>
	</form>
	<script type="text/javascript">
	$(function() {
		//修改：根据Id去后台查询数据，让后加载到form
		if ($('#id').val().length > 0) {
			top.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(SYS.contextPath + '/score/queryBusiCode.action', {
				id : $('#id').val()
			}, function(result) {
				if (result.rows[0].id != undefined) {
					$('form').form('load', {
						'scBusiCode.id' : result.rows[0].id,
						'scBusiCode.busicode' : result.rows[0].busicode,
						'scBusiCode.businame' : result.rows[0].businame,
						'scBusiCode.busitype' : result.rows[0].busitype,
						'scBusiCode.scinout' : result.rows[0].scinout,
						'scBusiCode.memo' : result.rows[0].memo
					});
					
				}
				top.$.messager.progress('close');
			}, 'json');
		}
	});
	
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
	
	function submitForm() {
	    var url;
	    if ($('form').form('validate')) {
	        if ($('#id').val().length > 0) {
	            url = SYS.contextPath + '/score/editBusiCode.action';
	        }
	        console.info(url);
	        $.post(url, SYS.serializeObject($('form')), function(result) {
				if (result.success == '0') {
					top.$.messager.show({title: "提示", msg: "操作成功" });
					closeDialog(true);
				} else {
					top.$.messager.alert('提示', result.errorMsg, 'error');
				}
			}, 'json');
	    }
	};
	</script>	
</body>
</html>