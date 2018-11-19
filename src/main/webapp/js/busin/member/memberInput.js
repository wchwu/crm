var _memberId = 0;
var authority = 'readOnly' ;
var op = $('#op').val() ;
$(function() {
	var memberId = $('#upt_memberId').val();
	_memberId = memberId;
	console.log("memberId:"+memberId);
	// 修改：根据Id去后台查询数据，让后加载到form
	baseInfo(memberId)
	
	if(isEmpty(memberId)){
		_memberId = getMemberId();
	}
	familyTab(_memberId,'tab_family');
	workExperienceTab(_memberId,'tab_workExperience');
	eduExperienceTab(_memberId,'tab_eduExperience');
	certificateTab(_memberId,'tab_certificate');
	fileTab(_memberId,'tab_file');
	
	upload(_memberId);
	
	if(op == 'detail'){
		$('#fs_submit').hide();
	}
});

function baseInfo(memberId){
	if (memberId.length > 0) {
		top.$.messager.progress({
			text : '数据加载中....'
		});
		$.post(SYS.contextPath + '/member/getMemberById.action', {
			id : memberId
		}, function(result) {
			result = eval("(" + result + ")");
			if (result.memberPo.id != undefined) {
				$('form').form('load', {
					'id' : result.memberPo.id,
					'name' : null2Str(result.memberPo.name),
					'certId' : null2Str(result.memberPo.certId),
					'gender' : null2Str(result.memberPo.gender),
					'nation' : null2Str(result.memberPo.nation),
					'dept' : null2Str(result.memberPo.dept),
					'office' : null2Str(result.memberPo.office),
					'degree' : null2Str(result.memberPo.degree),
					'marriage' : null2Str(result.memberPo.marriage),
					'polity' : null2Str(result.memberPo.polity),
					'tel' : null2Str(result.memberPo.tel),
					'address' : null2Str(result.memberPo.address),
					'companyAddr' : null2Str(result.memberPo.companyAddr),
					'email' : null2Str(result.memberPo.email),
					'status' : null2Str(result.memberPo.status),
					'nativePlace' : null2Str(result.memberPo.nativePlace),
					'originalPlace' : null2Str(result.memberPo.originalPlace),
					'healthStatus' : null2Str(result.memberPo.healthStatus),
					'birthDate' : null2Str(result.memberPo.birthDate),
					'hobby' : null2Str(result.memberPo.hobby),
					'advantage' : null2Str(result.memberPo.advantage),
					'joinReason' : null2Str(result.memberPo.joinReason),
					'wantGain' : null2Str(result.memberPo.wantGain),
					'joinDate' : null2Str(result.memberPo.joinDate)
				});

				// $("select[name='gender']").combobox('setValue',null2Str(result.memberPo.gender));
			}
			top.$.messager.progress('close');
		}, 'json');
	}
}

//家庭背景情况
var dg_family = $('#tab_family');
function familyTab(memberId,tabId) {
	console.log("family_menmberId:"+memberId);
	
	var familyQueryParams = {
		'memberId' : memberId
	};
	var editRow = undefined; // 定义全局变量：当前编辑的行
	dg_family.datagrid(
			{
				title : '家庭情况列表',
				pagination: false,
				url: SYS.contextPath + '/member/getFamilyById.action',
				queryParams: familyQueryParams,
				toolbar: [{
					id: tabId + '_add',
					text: '新增',
					iconCls: 'icon-add',
					handler: function() {
						var $dom = dg_family,
							rows = $dom.datagrid('getRows');
						$dom.datagrid('appendRow', {memberId: memberId});
						$dom.datagrid('beginEdit', rows.length - 1);
					}
				},
				'-',
				{
					id: tabId + '_edit',
					text: '修改',
					iconCls: 'icon-edit',
					handler: function() {
						var row = dg_family.datagrid('getSelected');
						if (row) {
							var rowIndex = dg_family.datagrid('getRowIndex', row);
							dg_family.datagrid('beginEdit', rowIndex);
						}
					}
				},
				'-',
				{
					id: tabId + '_remove',
					text: '删除',
					iconCls: 'icon-remove',
					handler: function() {
						var row = dg_family.datagrid('getSelected');
						if (row) {
							var rowIndex = dg_family.datagrid('getRowIndex', row);
							dg_family.datagrid('deleteRow', rowIndex);
						}
					}
				},
				'-', {
					id: tabId + '_undo',
					text: "取消修改",
					iconCls: "icon-undo",
					handler: function() {
						dg_family.datagrid('rejectChanges');
					}
				},
				'-', {
					id: tabId + '_save',
					text: "保存",
					iconCls: "icon-save",
					handler: function() {
						var rows = dg_family.datagrid('getRows');
						for (var i = 0; i < rows.length; i++) {
							dg_family.datagrid('endEdit', i);
						}
						if (dg_family.datagrid("getChanges").length) {
							var inserted = dg_family.datagrid('getChanges', "inserted");
							var deleted = dg_family.datagrid('getChanges', "deleted");
							var updated = dg_family.datagrid('getChanges', "updated");

							var effectRow = new Object();
							if (inserted.length) {
								effectRow["inserted"] = JSON.stringify(inserted);
							}
							if (deleted.length) {
								effectRow["deleted"] = JSON.stringify(deleted);
							}
							if (updated.length) {
								effectRow["updated"] = JSON.stringify(updated);
							}
							$.post(SYS.contextPath + "/member/saveFamily.action", effectRow,
							function(rsp) {
								if (rsp.success) {
									$.messager.show({title: "提示", msg: "操作成功" });
									editIndex = undefined;
									dg_family.datagrid('acceptChanges').datagrid('reload');
								}
							},
							"JSON").error(function(rsp) {
								$.messager.alert("提示", "提交错误了！" + rsp);
							});
						} else {
							$.messager.alert("提示", "未做修改！");
						}
					}
				}
				],
				onLoadSuccess:function(){
			    	detailButtonHandle(tabId);
				}
			});
}


//工作经历情况
var workExperienceQueryParams;
var dg_workExperience = $('#tab_workExperience');
function workExperienceTab(memberId,tabId) {
	console.log("workExperience_menmberId:"+memberId);
	
	var workExperienceQueryParams = {
		'memberId' : memberId
	};
	var editRow = undefined; // 定义全局变量：当前编辑的行
	dg_workExperience.datagrid(
			{
				title : '工作经历列表',
				pagination: false,
				url: SYS.contextPath + '/member/getWorkExperienceById.action',
				queryParams: workExperienceQueryParams,
				toolbar: [{
					text: '新增',
					iconCls: 'icon-add',
					handler: function() {
						var $dom = dg_workExperience,
							rows = $dom.datagrid('getRows');
						$dom.datagrid('appendRow', {memberId: memberId});
						$dom.datagrid('beginEdit', rows.length - 1);
					}
				},
				'-',
				{
					text: '修改',
					iconCls: 'icon-edit',
					handler: function() {
						var row = dg_workExperience.datagrid('getSelected');
						if (row) {
							var rowIndex = dg_workExperience.datagrid('getRowIndex', row);
							dg_workExperience.datagrid('beginEdit', rowIndex);
						}
					}
				},
				'-',
				{
					text: '删除',
					iconCls: 'icon-remove',
					handler: function() {
						var row = dg_workExperience.datagrid('getSelected');
						if (row) {
							var rowIndex = dg_workExperience.datagrid('getRowIndex', row);
							dg_workExperience.datagrid('deleteRow', rowIndex);
						}
					}
				},
				'-', {
					text: "取消修改",
					iconCls: "icon-undo",
					handler: function() {
						dg_workExperience.datagrid('rejectChanges');
					}
				},
				'-', {
					text: "保存",
					iconCls: "icon-save",
					handler: function() {
						var rows = dg_workExperience.datagrid('getRows');
						for (var i = 0; i < rows.length; i++) {
							dg_workExperience.datagrid('endEdit', i);
						}
						if (dg_workExperience.datagrid("getChanges").length) {
							var inserted = dg_workExperience.datagrid('getChanges', "inserted");
							var deleted = dg_workExperience.datagrid('getChanges', "deleted");
							var updated = dg_workExperience.datagrid('getChanges', "updated");

							var effectRow = new Object();
							if (inserted.length) {
								effectRow["inserted"] = JSON.stringify(inserted);
							}
							if (deleted.length) {
								effectRow["deleted"] = JSON.stringify(deleted);
							}
							if (updated.length) {
								effectRow["updated"] = JSON.stringify(updated);
							}
							$.post(SYS.contextPath + "/member/saveWorkExperience.action", effectRow,
							function(rsp) {
								if (rsp.success) {
									$.messager.show({title: "提示", msg: "操作成功" });
									editIndex = undefined;
									dg_workExperience.datagrid('acceptChanges').datagrid('reload');
								}
							},
							"JSON").error(function(rsp) {
								$.messager.alert("提示", "提交错误了！" + rsp);
							});
						} else {
							$.messager.alert("提示", "未做修改！");
						}
					}
				}
				],
				onLoadSuccess:function(){
		    	detailButtonHandle(tabId);
				}
			});
}





//教育经历情况
var eduExperienceQueryParams;
var dg_eduExperience = $('#tab_eduExperience');
function eduExperienceTab(memberId,tabId) {
	console.log("eduExperience_menmberId:"+memberId);
	
	var eduExperienceQueryParams = {
		'memberId' : memberId
	};
	var editRow = undefined; // 定义全局变量：当前编辑的行
	dg_eduExperience.datagrid(
			{
				title : '教育经历列表',
				pagination: false,
				url: SYS.contextPath + '/member/getEduExperienceById.action',
				queryParams: eduExperienceQueryParams,
				toolbar: [{
					text: '新增',
					iconCls: 'icon-add',
					handler: function() {
						var $dom = dg_eduExperience,
							rows = $dom.datagrid('getRows');
						$dom.datagrid('appendRow', {memberId: memberId});
						$dom.datagrid('beginEdit', rows.length - 1);
					}
				},
				'-',
				{
					text: '修改',
					iconCls: 'icon-edit',
					handler: function() {
						var row = dg_eduExperience.datagrid('getSelected');
						if (row) {
							var rowIndex = dg_eduExperience.datagrid('getRowIndex', row);
							dg_eduExperience.datagrid('beginEdit', rowIndex);
						}
					}
				},
				'-',
				{
					text: '删除',
					iconCls: 'icon-remove',
					handler: function() {
						var row = dg_eduExperience.datagrid('getSelected');
						if (row) {
							var rowIndex = dg_eduExperience.datagrid('getRowIndex', row);
							dg_eduExperience.datagrid('deleteRow', rowIndex);
						}
					}
				},
				'-', {
					text: "取消修改",
					iconCls: "icon-undo",
					handler: function() {
						dg_eduExperience.datagrid('rejectChanges');
					}
				},
				'-', {
					text: "保存",
					iconCls: "icon-save",
					handler: function() {
						var rows = dg_eduExperience.datagrid('getRows');
						for (var i = 0; i < rows.length; i++) {
							dg_eduExperience.datagrid('endEdit', i);
						}
						if (dg_eduExperience.datagrid("getChanges").length) {
							var inserted = dg_eduExperience.datagrid('getChanges', "inserted");
							var deleted = dg_eduExperience.datagrid('getChanges', "deleted");
							var updated = dg_eduExperience.datagrid('getChanges', "updated");

							var effectRow = new Object();
							if (inserted.length) {
								effectRow["inserted"] = JSON.stringify(inserted);
							}
							if (deleted.length) {
								effectRow["deleted"] = JSON.stringify(deleted);
							}
							if (updated.length) {
								effectRow["updated"] = JSON.stringify(updated);
							}
							$.post(SYS.contextPath + "/member/saveEduExperience.action", effectRow,
							function(rsp) {
								if (rsp.success) {
									$.messager.show({title: "提示", msg: "操作成功" });
									editIndex = undefined;
									dg_eduExperience.datagrid('acceptChanges').datagrid('reload');
								}
							},
							"JSON").error(function(rsp) {
								$.messager.alert("提示", "提交错误了！" + rsp);
							});
						} else {
							$.messager.alert("提示", "未做修改！");
						}
					}
				}
				],
				onLoadSuccess:function(){
			    	detailButtonHandle(tabId);
				}
			});
}


//获奖情况
var certificateQueryParams;
var dg_certificate = $('#tab_certificate');
function certificateTab(memberId,tabId) {
	console.log("workExperience_menmberId:"+memberId);
	
	var certificateQueryParams = {
		'memberId' : memberId
	};
	var editRow = undefined; // 定义全局变量：当前编辑的行
	dg_certificate.datagrid(
			{
				title : '获奖经历列表',
				pagination: false,
				url: SYS.contextPath + '/member/getCertificateById.action',
				queryParams: certificateQueryParams,
				toolbar: [{
					text: '新增',
					iconCls: 'icon-add',
					handler: function() {
						var $dom = dg_certificate,
							rows = $dom.datagrid('getRows');
						$dom.datagrid('appendRow', {memberId: memberId});
						$dom.datagrid('beginEdit', rows.length - 1);
					}
				},
				'-',
				{
					text: '修改',
					iconCls: 'icon-edit',
					handler: function() {
						var row = dg_certificate.datagrid('getSelected');
						if (row) {
							var rowIndex = dg_certificate.datagrid('getRowIndex', row);
							dg_certificate.datagrid('beginEdit', rowIndex);
						}
					}
				},
				'-',
				{
					text: '删除',
					iconCls: 'icon-remove',
					handler: function() {
						var row = dg_certificate.datagrid('getSelected');
						if (row) {
							var rowIndex = dg_certificate.datagrid('getRowIndex', row);
							dg_certificate.datagrid('deleteRow', rowIndex);
						}
					}
				},
				'-', {
					text: "取消修改",
					iconCls: "icon-undo",
					handler: function() {
						dg_certificate.datagrid('rejectChanges');
					}
				},
				'-', {
					text: "保存",
					iconCls: "icon-save",
					handler: function() {
						var rows = dg_certificate.datagrid('getRows');
						for (var i = 0; i < rows.length; i++) {
							dg_certificate.datagrid('endEdit', i);
						}
						if (dg_certificate.datagrid("getChanges").length) {
							var inserted = dg_certificate.datagrid('getChanges', "inserted");
							var deleted = dg_certificate.datagrid('getChanges', "deleted");
							var updated = dg_certificate.datagrid('getChanges', "updated");

							var effectRow = new Object();
							if (inserted.length) {
								effectRow["inserted"] = JSON.stringify(inserted);
							}
							if (deleted.length) {
								effectRow["deleted"] = JSON.stringify(deleted);
							}
							if (updated.length) {
								effectRow["updated"] = JSON.stringify(updated);
							}
							$.post(SYS.contextPath + "/member/saveCertificate.action", effectRow,
							function(rsp) {
								if (rsp.success) {
									$.messager.show({title: "提示", msg: "操作成功" });
									editIndex = undefined;
									dg_certificate.datagrid('acceptChanges').datagrid('reload');
								}
							},
							"JSON").error(function(rsp) {
								$.messager.alert("提示", "提交错误了！" + rsp);
							});
						} else {
							$.messager.alert("提示", "未做修改！");
						}
					}
				}
				],
				onLoadSuccess:function(){
			    	detailButtonHandle(tabId);
				}
			});
}

var dg_file = $('#tab_file');
function fileTab(memberId,tabId){
		var fileQueryParams = {
			'memberId' : memberId
		};
		var editRow = undefined; // 定义全局变量：当前编辑的行
		dg_file.datagrid(
				{
					title : '附件列表',
					pagination: false,
					url: SYS.contextPath + '/file/queryListByMemberId.action',
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

function upload(memberId){
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
	      	$dom.datagrid('appendRow', {id:f.id,memberId: memberId,fileName:f.fileName,fileSize:f.fileSize});
	    	}else{
	    		alert(data.result.msg);
	    	}
	    }
	  });
	  
	  //文件上传前触发事件
	  $('#upload').bind('fileuploadsubmit', function (e, data) {
	      data.formData = { memberId: memberId,fileType:'10' };  //如果需要额外添加参数可以在这里添加
	  }).bind('fileuploadadd', function (e, data) {
			//layerIndex = layer.load(0, { shade: [0.1, '#fff'] });
		});
}


// 提交表单
function submitForm() {
	var url;
	if ($('form').form('validate')) {
		var param = {};
		param = SYS.serializeObject($('form'));
		var ipt_memberId = $('#upt_memberId').val();
		
		if (ipt_memberId.length > 0) {
			url = SYS.contextPath + '/member/updateMember.action';
		} else {
			url = SYS.contextPath + '/member/addMember.action';
			param.id = _memberId ;
		}

		console.log(SYS.serializeObject($('form')));
		
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
