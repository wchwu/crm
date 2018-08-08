<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	String actCode = request.getParameter("actCode");
	if (id == null) {
		id = "";
	}
	if (actCode == null) {
		actCode = "";
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>积分时间修改</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>

	<div class="easyui-layout" data-options="fit:true"
		style="overflow: hidden;">
		<div data-options="region:'center',border:false, width: '100%'"
			style="overflow: hidden;">
			<form id="formdate" method="post" class="form">
				<input type="hidden" id="ipt_scActDaysCfgId" value="<%=id %>" name="id"/>
				<input type="hidden" value="<%=actCode %>" name="actCode"/>
				<fieldset>
					<legend>积分时间录入</legend>
					<table class="table" style="width: 100%;">
						<tr style="width: 100%; line-height: 25px;">
							<th>开始时间</th>
							<td>
								<input name="startDate" id="iptStartDate" class="Wdate"/>
							</td>
						</tr>
						<tr>
							<th>结束时间</th>
							<td>
								<input name="endDate" id="iptEndDate" class="Wdate"/>
							</td>
						</tr>
						<tr>
							<th>积分</th>
							<td><input id="scVal" class="easyui-numberbox" name="scVal" data-options="required:true,precision:4" /></td>
						</tr>
						<tr>
							<th>备注:</th>
							<td><input id="memo" class="easyui-validatebox" name="memo" /></td>
						</tr>
						<tr>
							<td style="text-align: center;" colspan="2">
								<a href="#" class="easyui-linkbutton" onclick="javascript:submitForm();">保存</a>
								<a href="#" class="easyui-linkbutton" onclick="javascript:closeDialog();">关闭</a> 
							</td>
						</tr>
					</table>
				</fieldset>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		
	$(function() {
		$('#iptStartDate').my97({
			required:true,
			dateFmt: 'yyyy-MM-dd',
			maxDate:'#F{$dp.$D(\'iptEndDate\')}'
		});
		
		$('#iptEndDate').my97({
			required:true,
			dateFmt: 'yyyy-MM-dd',
			minDate:'#F{$dp.$D(\'iptStartDate\')}'
		});
		
		//修改：根据Id去后台查询数据，让后加载到form
		if ($('#ipt_scActDaysCfgId').val().length > 0) {
			top.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(SYS.contextPath + '/scAct/getScActDaysCfgById.action', {
				id : $('#ipt_scActDaysCfgId').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'actCode' : result.actCode,
						'startDate' : result.startDate,
						'endDate' : result.endDate,
						'scVal' : result.scVal,
						'memo' : result.memo
					});
					$('#iptStartDate').val(SYS.dateStr8To10Str(result.startDate));
					$('#iptEndDate').val(SYS.dateStr8To10Str(result.endDate));
					
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
	        if ($('#ipt_scActDaysCfgId').val().length > 0) {
	        	url = SYS.contextPath + '/scAct/updateActDaysCfg.action';
	        } else {
	        	url = SYS.contextPath + '/scAct/addActDaysCfg.action';
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
</body>
</html>