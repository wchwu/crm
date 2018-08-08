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
		<input type="hidden" id="ipt_scLevId" name="id" value="<%=id %>" />
		<fieldset>
			<legend>会员等级操作</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>会员等级</th>
					<td>
						<select name="code" style="width: 200px;"class="easyui-combobox" id="code"  data-options="dictTypeId: 'SC_LEV',required:true" >
						</select>
					</td>
					<th>积分系数</th>
					<td><input name="scFac" type="text" class="easyui-numberbox" data-options="required:true,precision:2,missingMessage:'该输入项为必输项'" /></td>
				</tr>
				<tr>
					<th>起始积分</th>
					<td>
						<input  name="scMin" type="text" id="iptScMin" class="easyui-numberbox" data-options="required:true,validType:'maxTarget[\'#iptScMax\']'" />
					<th>结束积分</th>
					<td>
						<input name="scMax" type="text" id="iptScMax" class="easyui-numberbox" data-options="required:true,validType:'minTarget[\'#iptScMin\']'" />
					</td>
				</tr>
				<tr>
					<th>网络图片</th>
					<td colspan="3">
						<input name="picUrl" id="picUrl" class="easyui-textbox" data-options="required:true,width:'80%', missingMessage:'该输入项为必输项'" />
					</td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3">
						<input name="memo" id="memo" class="easyui-textbox" data-options="width:'80%',height:50" />
					</td>
				</tr>
				<tr>
					<th colspan="4" style="text-align: center;">
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="javascript:submitForm();">保存</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="javascript:closeDialog();">关闭</a>  
					</th>
				</tr>
			</table>
		</fieldset>
	</form>
	<script type="text/javascript">
	$(function() {
		//修改：根据Id去后台查询数据，让后加载到form
		if ($('#ipt_scLevId').val().length > 0) {
			top.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(SYS.contextPath + '/scLev/getScLevById.action', {
				id : $('#ipt_scLevId').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'code' : result.code,
						'scMin' : result.scMin,
						'scMax' : result.scMax,
						'scFac' : result.scFac,
						'vipgrade.gradename' : result.gradename,
						'picUrl' : result.picUrl,
						'memo' : result.memo
					});
					//$('#iptScMin').val(result.scMin );
					//$('#iptScMax').val(result.scMax);
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
	        if ($('#ipt_scLevId').val().length > 0) {
	            url = SYS.contextPath + '/scLev/updateLev.action';
	        } else {
	        	url = SYS.contextPath + '/scLev/addLev.action';
	        }
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