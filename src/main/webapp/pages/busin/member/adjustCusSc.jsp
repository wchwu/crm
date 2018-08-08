<%@page import="java.net.URLDecoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String custId = request.getParameter("custId");
	String custName = URLDecoder.decode(request.getParameter("custName"), "UTF-8");;
	String scScore = request.getParameter("scScore");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>调整客户积分</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<form method="post" class="form">
		<input type="hidden" name="custId" id="ipt_custId" value="<%=custId %>"/>
		<fieldset>
			<legend>调整客户积分</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>客户姓名</th>
					<td><%=custName %></td>
				</tr>
				<tr>
					<th>当前积分</th>
					<td><%=scScore %></td>
				</tr>
				<tr>
					<th>调整方向</th>
					<td>
						<select id="ipt_sel" class="easyui-combobox" data-options="required:true, panelHeight: 60" name="adjusDirect" style="width:180px;">   
						    <option value="0">调增</option>   
						    <option value="1">调减</option>   
						</select> 
					</td>
				</tr>
				<tr>
					<th>调整积分</th>
					<td>
						<input name="score" type="text" id="iptScore" class="easyui-numberbox" style="width:180px;" data-options="required:true,precision:5" />
					</td>
				</tr>
				<tr>
					<th>备注</th>
					<td>
						<input name="memo" class="easyui-textbox" data-options="multiline:true,width:'80%', height:50" />
					</td>
				</tr>
				<tr>
					<th colspan="2" style="text-align: center;">
						<a href="#" class="easyui-linkbutton" onclick="javascript:submitForm();">确认调整</a>
						<a href="#" class="easyui-linkbutton" onclick="javascript:closeDialog();">关闭</a>  
					</th>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
<script type="text/javascript">
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
	    var url = SYS.contextPath + '/custCurSc/adjustCustSc.action';
	    if ($('form').form('validate')) {
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