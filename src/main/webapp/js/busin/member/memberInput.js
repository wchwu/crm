var _memberId = 0;
$(function() {
	var memberId = $('#ipt_memberId').val();
	_memberId = memberId;
	console.log("memberId:"+memberId);
	// 修改：根据Id去后台查询数据，让后加载到form
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
	
	if(isEmpty(memberId)){
		_memberId = getMemberId();
	}
	familyTab(_memberId);
	workExperienceTab(_memberId);
	eduExperienceTab(_memberId);
	certificateTab(_memberId);
});

//家庭背景情况
var familyQueryParams;
var dg_family = $('#tab_family');
function familyTab(memberId) {
	console.log("family_menmberId:"+memberId);
	
	familyQueryParams = {
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
					text: "取消修改",
					iconCls: "icon-undo",
					handler: function() {
						dg_family.datagrid('rejectChanges');
					}
				},
				'-', {
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
				]
			});
}


//工作经历情况
var workExperienceQueryParams;
var dg_workExperience = $('#tab_workExperience');
function workExperienceTab(memberId) {
	console.log("workExperience_menmberId:"+memberId);
	
	workExperienceQueryParams = {
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
				]
			});
}





//教育经历情况
var eduExperienceQueryParams;
var dg_eduExperience = $('#tab_eduExperience');
function eduExperienceTab(memberId) {
	console.log("eduExperience_menmberId:"+memberId);
	
	eduExperienceQueryParams = {
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
				]
			});
}


//获奖情况
var certificateQueryParams;
var dg_certificate = $('#tab_certificate');
function certificateTab(memberId) {
	console.log("workExperience_menmberId:"+memberId);
	
	certificateQueryParams = {
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
				]
			});
}
// 提交表单
function submitForm() {
	var url;
	if ($('form').form('validate')) {
		var param = {};
		param = SYS.serializeObject($('form'));
		var ipt_memberId = $('#ipt_memberId').val();
		
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
