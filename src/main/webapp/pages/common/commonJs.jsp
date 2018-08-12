
<script type="text/javascript">
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
</script>
