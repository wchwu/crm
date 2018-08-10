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
<title>成员录入</title>
<jsp:include page="/common.jsp"></jsp:include>
</head>
<body>
	<form method="post" class="form">
		<input type="hidden" id="ipt_memberId" value="<%=id %>" name="id"/>
		<fieldset>
			<legend>基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>姓名</th>
					<td>
						<input class="easyui-validatebox" type="text" name="name" data-options="required:true" />
					</td>
					<th>性别</th>
					<td>
						 <select name="gender" style="width: 200px;"class="easyui-combobox" id="gender"  data-options="dictTypeId: 'SYS_GENDER',required:true" >
						</select>
					</td>
					<th>籍贯</th>
					<td>
						<input class="easyui-validatebox" type="text" name="nativePlace" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<th>身份证号</th>
					<td>
						<input class="easyui-validatebox" type="text" name="certId" data-options="required:true" />
					</td>
					<th>加入日期</th>
					<td>
						 <input id="joinDate" type="text" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser,required:true" name="joinDate">
					</td>
					<th>部门</th>
					<td>
						<select name="dept" style="width: 200px;"class="easyui-combobox" id="dept"  data-options="dictTypeId: 'DEPT',required:true" >
						</select>
					</td>
				</tr>
				<tr>
					<th>民族</th>
					<td>
						<select name="nation" style="width: 200px;"class="easyui-combobox" id="nation"  data-options="dictTypeId: 'NATION',required:true" >
						</select>
					</td>
					<th>出生日期</th>
					<td>
						<input id="birthDate" type="text" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser,required:true" name="birthDate">
					</td>
					<th>祖籍</th>
					<td>
						<input class="easyui-validatebox" type="text" name="originalPlace" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<th>婚姻状况</th>
					<td>
						 <select name="marriage" style="width: 200px;"class="easyui-combobox" id="marriage"  data-options="dictTypeId: 'MARRIAGE',required:true" >
						</select>
					</td>
					<th>政治面貌</th>
					<td>
						 <select name="polity" style="width: 200px;"class="easyui-combobox" id="polity"  data-options="dictTypeId: 'POLITY',required:true" >
						</select>
					</td>
					<th>健康状况</th>
					<td>
						<input class="easyui-validatebox" type="text" name="healthStatus" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<th>联系电话</th>
					<td>
						<input class="easyui-validatebox" type="text" name="tel" data-options="required:true" />
					</td>
					<th>个人邮箱</th>
					<td>
						<input class="easyui-validatebox" type="text" name="email" data-options="required:true" />
					</td>
					<th>状态</th>
					<td>
						<select id="status" name="status" class="easyui-combobox" data-options="dictTypeId: 'STATUS',required:true,defaultIndex: 0">
						</select>  
					<th></th>
				</tr>
			</table>
		</fieldset>
		<hr/>
		<fieldset>
			<legend>地址信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>现住地址</th>
					<td>
						<input class="easyui-validatebox" type="text"  name="address" data-options="required:true" />
					</td>
					<th>公司地址</th>
					<td>
						<input class="easyui-validatebox" type="text" name="companyAddr" data-options="required:true" />
					</td>
				</tr>
			</table>
		</fieldset>
		<hr/>
		<fieldset>
			<legend>家庭情况及主要社会关系 &nbsp;&nbsp;&nbsp;&nbsp;<span class="btnLink" style="color:#0e90d2;">添加</span></legend>
			<table class="table" style="width: 100%;">
				<thead>
					<tr>
					<th>姓名</th>
					<th>关系</th>
					<th>工作单位</th>
					<th>职务</th>
					<th>联系电话</th>
					<th>联系地址</th>
				</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<input class="easyui-validatebox" type="text" />
						</td>
						<td>
							<input class="easyui-validatebox" type="text" />
						</td>
						<td>
							<input class="easyui-validatebox" type="text" />
						</td>
						<td>
							<input class="easyui-validatebox" type="text" />
						</td>
						<td>
							<input class="easyui-validatebox" type="text" />
						</td>
						<td>
							<input class="easyui-validatebox" type="text" />
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		<hr/>
		<fieldset>
			<legend>工  作  经  历&nbsp;&nbsp;&nbsp;&nbsp;<span class="btnLink" style="color:#0e90d2;">添加</span></legend>
			<table class="table" style="width: 100%;">
				<thead>
					<tr>
						<th>起止时间</th>
						<th>工 作 单 位</th>
						<th>职务</th>
						<th>工作内容简述</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<input class="easyui-datebox" type="text" />~
							<input class="easyui-datebox" type="text" />
						</td>
						<td>
							<input class="easyui-validatebox" type="text" />
						</td>
						<td>
							<input class="easyui-validatebox" type="text" />
						</td>
						<td>
							<input class="easyui-validatebox" type="text" />
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		<hr/>
		<fieldset>
			<legend>教  育  经  历&nbsp;&nbsp;&nbsp;&nbsp;<span class="btnLink" style="color:#0e90d2;">添加</span></legend>
			<table class="table" style="width: 100%;">
				<thead>
					<tr>
						<th>起止时间</th>
						<th>毕业/就读 院 校</th>
						<th>专 业</th>
						<th>学 历</th>
						<th>学 位</th>
						<th>是否全日制</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<input class="easyui-datebox" type="text" />~
							<input class="easyui-datebox" type="text" />
						</td>
						<td>
							<input class="easyui-validatebox" type="text" />
						</td>
						<td>
							<input class="easyui-validatebox" type="text" />
						</td>
						<td>
							<input class="easyui-validatebox" type="text" />
						</td>
						<td>
							<input class="easyui-validatebox" type="text" />
						</td>
						<td>
							<input class="easyui-validatebox" type="text" />
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		<hr/>
		<fieldset>
			<legend>取  得  证书/获奖/职业资格认证  情  况&nbsp;&nbsp;&nbsp;&nbsp;<span class="btnLink" style="color:#0e90d2;">添加</span></legend>
			<table class="table" style="width: 100%;">
				<thead>
					<tr>
						<th>证书/奖项 名称</th>
						<th>获得日期</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td width="80%">
							<input class="easyui-validatebox" type="text"    size="50"/>
						</td>
						<td width="20%">
							<input class="easyui-datebox" type="text"   size="5"/>
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
		<hr/>
		<fieldset>
			<legend>爱好特长</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>兴趣爱好</th>
					<td>
						<input class="easyui-validatebox" type="text" name="hobby"/>
					</td>
					<th>特长</th>
					<td>
						<input class="easyui-validatebox" type="text" name="comanyAddr"/>
					</td>
				</tr>
			</table>
		</fieldset>
		<hr/>
		<fieldset>
			<legend>贡献与奉献</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>为什么加入</th>
					<td colspan="3">
						<input name="joinReason" class="easyui-textbox" data-options="multiline:true,width:'60%', height:50" />
					</td>
				</tr>
				<tr>
					<th>从团工委获得</th>
					<td colspan="3">
						<input name="wantGain" class="easyui-textbox" data-options="multiline:true,width:'60%', height:50" />
					</td>
				</tr>
			</table>
		</fieldset>
		<hr/>
		<fieldset>
			<legend></legend>
			<table class="table" style="width: 100%;">
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
function ww4(date){ 
 var y = date.getFullYear(); 
 var m = date.getMonth()+1; 
 var d = date.getDate(); 
 var h = date.getHours(); 
 return y+(m<10?('0'+m):m)+(d<10?('0'+d):d); 
   
} 
function w4(s){ 
 var reg=/[\u4e00-\u9fa5]/ //利用正则表达式分隔 
 var ss = (s.split(reg)); 
 var y = parseInt(ss[0],10); 
 var m = parseInt(ss[1],10); 
 var d = parseInt(ss[2],10); 
 if (!isNaN(y) && !isNaN(m) && !isNaN(d)){ 
  return new Date(y,m-1,d); 
 } else { 
  return new Date(); 
 } 
} 

function myformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+''+(m<10?('0'+m):m)+''+(d<10?('0'+d):d);
}

function myparser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}
	$(function() {
		//修改：根据Id去后台查询数据，让后加载到form
		if ($('#ipt_memberId').val().length > 0) {
			top.$.messager.progress({
				text : '数据加载中....'
			}); 
			$.post(SYS.contextPath + '/member/getMemberById.action', {
				id: $('#ipt_memberId').val()
			}, function(result) {
				console.log(result);
				result = eval("("+result+")");
				if (result.memberPo.id != undefined) {
					$('form').form('load', {
						'id' : result.memberPo.id,
						'name' : null2Str(result.memberPo.name),
						'certId' : null2Str(result.memberPo.certId),
						'gender' : null2Str(result.memberPo.gender),
						'nation' : null2Str(result.memberPo.nation),
						'dept' : null2Str(result.memberPo.dept),
						'office' : null2Str(result.memberPo.office),
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
						'joinDate' : null2Str(result.memberPo.joinDate),
						'joinDate' : null2Str(result.memberPo.joinDate)
					});
					
					//$("select[name='gender']").combobox('setValue',null2Str(result.memberPo.gender));
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
	        if ($('#ipt_memberId').val().length > 0) {
	            url = SYS.contextPath + '/member/updateMember.action';
	        } else {
	        	url = SYS.contextPath + '/member/addMember.action';
	        }
	        var param = {};
	        param.member = SYS.serializeObject($('form'));
	        
	        console.log(SYS.serializeObject($('form')));
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
	
	function isEmpty(str){
		return (str == null || str === "" || str == "undefined" || str == "null");
	}
	
	function null2Str(str){
		if(isEmpty(str)){
			return "";
		}
		return str;
	}
	
	
</script>
</html>