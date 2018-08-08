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
<title>积分活动录入</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<form method="post" class="form">
		<input type="hidden" id="ipt_scActId" value="<%=id %>" name="id"/>
		<input type="hidden" value=">" name="busiCode"/>
		<input type="hidden" value=">" name="jobSql"/>
		<fieldset>
			<legend>积分活动录入</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>活动类型</th>
					<td>
						 <select name="gpId" style="width: 200px;"class="easyui-combobox" id="gpid"  data-options="dictTypeId: 'SC_ACT_GP',required:true" >
						</select>
					</td>
					<th>活动编号</th>
					<td>
						<input class="easyui-validatebox" type="text" name="actCode" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<th>活动名称</th>
					<td>
						<input class="easyui-validatebox" type="text" name="actName" data-options="required:true"/>
					</td>
					<th>活动积分</th>
					<td><input type="text" class="easyui-numberbox" data-options="required:true,precision:4" name="scDef"/></td>
				</tr>
				<tr>
					<th>是否有效</th>
					<td>
						<select id="slt_vali" name="vali" class="easyui-combobox" data-options="dictTypeId: 'SC_ACT_VALI',required:true,defaultIndex: 0">
						</select>  
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3">
						<input name="memo" class="easyui-textbox" data-options="multiline:true,width:'60%', height:50" />
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
</body>
<script type="text/javascript">
	$(function() {
		//修改：根据Id去后台查询数据，让后加载到form
		if ($('#ipt_scActId').val().length > 0) {
			top.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(SYS.contextPath + '/scAct/getScActById.action', {
				id: $('#ipt_scActId').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'id' : result.id,
						'busiCode' : result.busiCode,
						'jobSql' : result.jobSql,
						'gpId' : result.gpId,
						'actCode' : result.actCode,
						'actName' : result.actName,
						'scDef' : result.scDef,
						'vali' : result.vali,
						'memo' : result.memo
					});
					
				}
				top.$.messager.progress('close');
			}, 'json');
		}
	});
	
	//关闭窗口
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
	
	//提交表单
	function submitForm() {
	    var url;
	    if ($('form').form('validate')) {
	        if ($('#ipt_scActId').val().length > 0) {
	            url = SYS.contextPath + '/scAct/updateAct.action';
	        } else {
	        	url = SYS.contextPath + '/scAct/addAct.action';
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
	}
</script>
</html>