<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	String op = request.getParameter("op");
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
		<input type="hidden" id="upt_memberId" value="<%=id %>" name="id"/>
		<input type="hidden" id="op" value="<%=op %>" name="op"/>
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
						 <select name="gender" style="width: 200px;" class="easyui-combobox" id="gender"  data-options="dictTypeId: 'SYS_GENDER',required:true" >
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
					<th>出生日期</th>
					<td>
						<input id="birthDate" type="text" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser,required:true" name="birthDate">
					</td>
					<th>个人邮箱</th>
					<td>
						<input class="easyui-validatebox" type="text" name="email" data-options="required:true" />
					</td>
				</tr>
				<tr>
					<th>民族</th>
					<td>
						<select name="nation" style="width: 200px;"class="easyui-combobox" id="nation"  data-options="dictTypeId: 'NATION',required:true" >
						</select>
					</td>
					<th>祖籍</th>
					<td>
						<input class="easyui-validatebox" type="text" name="originalPlace" data-options="required:true" />
					</td>
					<th>联系电话</th>
					<td>
						<input class="easyui-validatebox" type="text" name="tel" data-options="required:true" />
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
					<th>学历</th>
					<td>
						<select name="degree" style="width: 200px;"class="easyui-combobox" id="degree"  data-options="dictTypeId: 'DEGREE'" >
						</select>
					</td>
					<th>加入日期</th>
					<td>
						 <input id="joinDate" type="text" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" name="joinDate">
					</td>
					<th>状态</th>
					<td>
						<select id="status" name="status" class="easyui-combobox" data-options="dictTypeId: 'STATUS',defaultIndex: 0">
						</select>  
					</td>
					
				</tr>
			</table>
		</fieldset>
		<hr/>
		<fieldset>
			<legend>部门信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>部门</th>
					<td>
						<select name="dept" style="width: 200px;"class="easyui-combobox" id="dept"  data-options="dictTypeId: 'DEPT',required:true" >
						</select>
					</td>
					<th>职务</th>
					<td>
						<input class="easyui-validatebox" type="text" name="office" data-options="required:true" />
					</td>
					<th></th>
					<td></td>
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
		<fieldset id="ft_tab_family">
			<legend>家庭情况及主要社会关系 </legend>
			<table id="tab_family" class="easyui-datagrid"
				data-options="
				    iconCls: 'icon-save',
				    fit: false,
				    autoRowHeight: false,
				    border:false,
				    fitColumns: true,
				    rownumbers:true,
				    singleSelect: true
				">
				<thead>
					<tr>
						<th data-options="field:'memberId',hidden: true"></th>
						<th	data-options="field:'name', width:'10%', editor:{type: 'textbox', options: {required:true, missingMessage: '不能为空'}}">姓名</th>
						<th	data-options="field:'relation', width:'10%', editor: {type: 'textbox', options:{required:true, missingMessage: '不能为空'}}">关系</th>
						<th data-options="field:'workUnit', width:'20%', editor: {type: 'textbox', options:{required:true, missingMessage: '不能为空'}}">工作单位</th>
						<th data-options="field:'office', width:'10%', editor: {type: 'textbox', options:{required:true, missingMessage: '不能为空'}}">职务</th>
						<th data-options="field:'tel', width:'15%', editor: {type: 'textbox', options:{required:true, missingMessage: '不能为空'}}">联系电话</th>
						<th data-options="field:'address', width:'20%', editor: {type: 'textbox', options:{required:true, missingMessage: '不能为空'}}">联系地址</th>
						<th	data-options="field:'sort', width:'5%', editor: {type:'numberbox'}">排序</th>
					</tr>
				</thead>
			</table>
		</fieldset>
		<hr/>
		<fieldset id="ft_tab_workExperience">
			<legend>工  作  经  历</legend>
			<table id="tab_workExperience" class="easyui-datagrid" 
				data-options="
				    iconCls: 'icon-save',
				    fit: false,
				    autoRowHeight: false,
				    border:false,
				    fitColumns: true,
				    rownumbers:true,
				    singleSelect: true
				">
				<thead>
					<tr>
						<th data-options="field:'memberId',hidden: true"></th>
						<th data-options="field:'startDate',width:'10%',validType:'length[6,6]',editor: {type: 'textbox'}">时间（起）</th>
						<th data-options="field:'endDate',width:'10%',validType:'length[6,6]',editor: {type: 'textbox'}">时间（止）</th>
						<th data-options="field:'workUnit', width:'15%', editor: {type: 'textbox', options:{required:true, missingMessage: '不能为空'}}">工 作 单 位</th>
						<th data-options="field:'office', width:'15%', editor: {type: 'textbox', options:{required:true, missingMessage: '不能为空'}}">职务</th>
						<th data-options="field:'workDesc', width:'35%', editor: {type: 'textbox', options:{required:true, missingMessage: '不能为空'}}">工作内容简述</th>
						<th	data-options="field:'sort', width:'5%', editor: {type:'numberbox'}">排序</th>
					</tr>
				</thead>
			</table>
		</fieldset>
		<hr/>
		<fieldset id="ft_tab_eduExperience">
			<legend>教  育  经  历</legend>
			<table id="tab_eduExperience" class="easyui-datagrid" 
				data-options="
				    iconCls: 'icon-save',
				    fit: false,
				    autoRowHeight: false,
				    border:false,
				    fitColumns: true,
				    rownumbers:true,
				    singleSelect: true
				">
				<thead>
					<tr>
						<th data-options="field:'memberId',hidden: true"></th>
						<th data-options="field:'startDate',width:'10%',validType:'length[6,6]',editor: {type: 'textbox'}">时间（起）</th>
						<th data-options="field:'endDate',width:'10%',validType:'length[6,6]',editor: {type: 'textbox'}">时间（止）</th>
						<th data-options="field:'school', width:'15%', editor: {type: 'textbox', options:{required:true, missingMessage: '不能为空'}}">毕业/就读 院 校</th>
						<th data-options="field:'major', width:'15%', editor: {type: 'textbox', options:{required:true, missingMessage: '不能为空'}}">专 业</th>
						<th data-options="field:'degree', width:'15%', editor: {type: 'textbox', options:{required:true, missingMessage: '不能为空'}}">学 历</th>
						<th data-options="field:'fullTimeFlag',dictTypeId: 'YES_OR_NO',required:true,defaultIndex: 0,editor: {type: 'combobox', options:{required:true, missingMessage: '不能为空'}}">是否全日制</th>
						<th	data-options="field:'sort', width:'5%', editor: {type:'numberbox'}">排序</th>
					</tr>
				</thead>
			</table>
		</fieldset>
		<hr/>
		<fieldset id="fs_tab_eduExperience">
			<legend>取  得  证书/获奖/职业资格认证  情  况</legend>
			<table id="tab_certificate" class="easyui-datagrid" 
				data-options="
				    iconCls: 'icon-save',
				    fit: false,
				    autoRowHeight: false,
				    border:false,
				    fitColumns: true,
				    rownumbers:true,
				    singleSelect: true
				">
				<thead>
					<tr>
						<th data-options="field:'memberId',hidden: true"></th>
						<th data-options="field:'prize', width:'70%', editor: {type: 'textbox', options:{required:true, missingMessage: '不能为空'}}">证书/奖项 名称</th>
						<th data-options="field:'gainDate',width:'10%',validType:'length[6,6]',editor: {type: 'textbox'}">获得日期</th>
						<th	data-options="field:'sort', width:'5%', editor: {type:'numberbox'}">排序</th>
					</tr>
				</thead>
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
						<input class="easyui-validatebox" type="text" name="advantage"/>
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
		<hr/>
		<fieldset>
			<legend>附件<input type="file" name="upload" id="upload" style="display: none;"> </legend>
			<table class="table" style="width: 100%;" id="tab_file" class="easyui-datagrid" 
				data-options="
				    iconCls: 'icon-save',
				    fit: false,
				    autoRowHeight: false,
				    border:false,
				    fitColumns: true,
				    rownumbers:true,
				    singleSelect: true
				">
				<thead>
					<tr>
						<th data-options="field:'id',hidden: true"></th>
						<th data-options="field:'memberId',hidden: true"></th>
						<!-- 
						<th data-options="field:'fileType', width:'30%', editor: {type:'combobox', options:{dictTypeId: 'FILE_TYPE',required:true, missingMessage: '不能为空'}}">文件类型</th>
						 -->
						<th data-options="field:'fileName', width:'76%'">文件名称</th>
						<th	data-options="field:'fileSize', width:'20%'">文件大小(KB)</th>
					</tr>
				</thead>
			</table>
		</fieldset>
		<fieldset id="fs_submit">
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
<jsp:include page="/pages/common/commonJs.jsp"></jsp:include>
<script type="text/javascript" src="${ctx}/js/util/util.js?version=${version}"></script>
<script type="text/javascript" src="${ctx}/js/busin/member/memberInput.js?version=${version}"></script>
<script type="text/javascript" src="../../../js/fileupload/jquery.ui.widget.js?version=${version}"></script>
<script type="text/javascript" src="../../../js/fileupload/jquery.iframe-transport.js?version=${version}"></script>
<script type="text/javascript" src="../../../js/fileupload/jquery.fileupload.js?version=${version}"></script>

</html>