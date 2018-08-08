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
<body style="text-align:center;">
	<form method="post" class="form">
		<fieldset>
			<legend>会员等级操作</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>会员等级</th>
					<td>
					<input id="gradeid" title="不可编辑" name="vipgrade.gradeid" value="<%=id %>" type="hidden" />
					
					<input id="gradename" title="不可编辑" name="vipgrade.gradename" disabled="disabled" />
					</td>
					<th>积分系数</th>
					<td><input name="vipgrade.ratio" class="easyui-textbox" data-options="required:true,missingMessage:'该输入项为必输项'" /></td>
				</tr>
				<tr>
					<th>起始积分</th>
					<td><input name="vipgrade.beginscore" class="easyui-textbox" data-options="required:true,missingMessage:'该输入项为必输项'" /></td>
					<th>结束积分</th>
					<td><input name="vipgrade.endscore" class="easyui-textbox" data-options="required:true,missingMessage:'该输入项为必输项'" /></td>
				</tr>
				<tr>
					<th>网络图片</th>
					<td colspan="3">
						<input name="vipgrade.picurl" id="picurl" class="easyui-textbox" data-options="required:true,width:'80%',missingMessage:'该输入项为必输项'" />
					</td>
					
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
		if ($('#gradeid').val().length > 0) {
			top.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(SYS.contextPath + '/vipLevel/queryById.action', {
				id : $('#gradeid').val(),
			}, function(result) {
				if (result.gradeid != undefined) {
					$('form').form('load', {
						'vipgrade.gradeid' : result.gradeid,
						'vipgrade.beginscore' : result.beginscore,
						'vipgrade.endscore' : result.endscore,
						'vipgrade.ratio' : result.ratio,
						'vipgrade.gradename' : result.gradename,
						'vipgrade.picurl' : result.picurl
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
	        if ($('#gradeid').val().length > 0) {
	            url = SYS.contextPath + '/vipLevel/update.action';
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