var _ruleId = 0;
var authority = 'readOnly' ;
var op = $('#op').val() ;
var editor;
$(function() {
	var ruleId = $('#upt_ruleId').val();
	_ruleId = ruleId;
	
	// 修改：根据Id去后台查询数据，让后加载到form
	baseInfo(op,ruleId)
	
	if(isEmpty(ruleId)){
		_ruleId = getRuleId();
		$("#id").val(_ruleId);
	}
	
	fileTab(_ruleId,'tab_file');
	
	upload(_ruleId);
	
	if(op == 'detail'){
		$('#fs_submit').hide();
	}
	
	
	editor = KindEditor.create('textarea[name="content"]',{resizeType : 1,width:"100%",height:"200px",afterChange:function(){
    	this.sync();
	 },afterBlur:function(){
	   this.sync();
	 }});
	
});

function baseInfo(op,ruleId){
	if ( op != 'add') {
		top.$.messager.progress({
			text : '数据加载中....'
		});
		$.post(SYS.contextPath + '/rule/getByRuleId.action', {
			id : ruleId
		}, function(result) {
			result = eval("(" + result + ")");
			if (result != undefined) {
				$('form').form('load', {
					'id' : result.id,
					'ruleType' : null2Str(result.ruleType),
					'ruleName' : null2Str(result.ruleName),
					'ruleUrl' : null2Str(result.ruleUrl)
				});
				KindEditor.html('#content',result.content);
			}
			top.$.messager.progress('close');
		}, 'json');
	}
}


var dg_file = $('#tab_file');
function fileTab(ruleId,tabId){
		var fileQueryParams = {
			'ruleId' : ruleId
		};
		var editRow = undefined; // 定义全局变量：当前编辑的行
		dg_file.datagrid(
				{
					title : '附件列表',
					pagination: false,
					url: SYS.contextPath + '/file/queryListByRuleId.action',
					queryParams: fileQueryParams,
					toolbar: [{
						text: '上传',
						iconCls: 'icon-add',
						handler: function() {
							$("#upload").trigger("click");
						}
					},
					'-',
					{
						text: '删除',
						iconCls: 'icon-remove',
						handler: function() {
							var row = dg_file.datagrid('getSelected');
							if (row) {
								$.post(SYS.contextPath + "/file/delFile.action", {id:row.id},
								function(rsp) {
									if (rsp.success) {
										$.messager.show({title: "提示", msg: "操作成功" });
										dg_file.datagrid('reload');
									}
								},
								"JSON").error(function(rsp) {
									$.messager.alert("提示", "提交错误了！" + rsp);
								});
								
								var rowIndex = dg_file.datagrid('getRowIndex', row);
								dg_file.datagrid('deleteRow', rowIndex);
							}else{
								$.messager.alert("提示", "请选择一条数据！");
							}
						}
					},
					'-',
					{
						id: tabId + '_download',
						text: '下载',
						iconCls: 'icon-filter',
						handler: function() {
							var row = dg_file.datagrid('getSelected');
							if (row) {
								location.href = SYS.contextPath + "/file/download.action?id=" + row.id ;
							}else{
								$.messager.alert("提示", "请选择一条数据！");
							}
						}
					},
					],
					onLoadSuccess:function(){
				    	detailButtonHandle(tabId);
					}
				});
}

function detailButtonHandle(tabId){
	if(op == 'detail'){
		//if(authority.indexOf('readOnly') != -1){
		console.log("XXXXX");
		
		//$('#'+tabId + ' .datagrid-toolbar').hide();
		console.log('#fs_'+ tabId +' .datagrid .datagrid-toolbar');
		
		//$('#fs_'+ tabId +' .datagrid .datagrid-toolbar').hide();
		
		//$('#fs_tab_family .datagrid .datagrid-toolbar').hide();
		$('.datagrid .datagrid-toolbar').hide();
		
		//$('#'+tabId + '_add').hide();
		//$('#'+tabId + '_edit').hide();
//		$('#'+tabId + '_remove').hide();
//		$('#'+tabId + '_undo').hide();
//		$('#'+tabId + '_save').hide();
	}
}

function upload(ruleId){
	//文件上传
  $('#upload').fileupload({
    dataType: 'json',
    maxFileSize: 50 * 1000 * 1024,//单位B
    //acceptFileTypes: /(.|\/)(jpe?g|png|doc|docx|pdf)$/i,//文件格式限制
    url: SYS.contextPath+'/file/upload.action',
    done: function (e, data) {
    	console.log(data);
    	if(null != data && null != data.result.data && data.result.code == '0000'){
    		var f = data.result.data ;
      	
      	var $dom = dg_file,
				rows = $dom.datagrid('getRows');
      	$dom.datagrid('appendRow', {id:f.id,ruleId: ruleId,fileName:f.fileName,fileSize:f.fileSize});
    	}else{
    		alert(data.result.msg);
    	}
    }
  });
  
  //文件上传前触发事件
  $('#upload').bind('fileuploadsubmit', function (e, data) {
      data.formData = { ruleId: ruleId,fileType:'60' };  //如果需要额外添加参数可以在这里添加
  }).bind('fileuploadadd', function (e, data) {
	});
}

// 提交表单
function submitForm() {
	var url;
	if ($('form').form('validate')) {
		var param = {};
		param = SYS.serializeObject($('form'));
		
		param.op = op ;
		param.id = $("#id").val();
		
		url = SYS.contextPath + '/rule/savaRule.action';
		
		$.post(url, param, function(result) {
			if (result.success == '0') {
				top.$.messager.show({
					title : "提示",
					msg : "操作成功"
				});
				closeDialog(true);
			} else {
				top.$.messager.alert('提示', result.errorMsg, 'error');
			}
		}, 'json');
	}
}
